package gawky.jasper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.xhtmlrenderer.event.DocumentListener;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.PDFAsImage;
import org.xhtmlrenderer.protocols.data.Handler;
import org.xhtmlrenderer.resource.CSSResource;
import org.xhtmlrenderer.resource.ImageResource;
import org.xhtmlrenderer.resource.XMLResource;
import org.xhtmlrenderer.util.XRLog;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfReader;

public class UniversalITextUserAgent implements UserAgentCallback, DocumentListener
{
	private static final int IMAGE_CACHE_CAPACITY = 32;

	private SharedContext _sharedContext;

	private ITextOutputDevice _outputDevice;

	private String _baseURL;
	
	protected LinkedHashMap _imageCache;
	private int _imageCacheCapacity;
	

	public UniversalITextUserAgent(ITextRenderer renderer)
	{
		_outputDevice = renderer.getOutputDevice();
		_sharedContext = renderer.getSharedContext();
		
		this._imageCacheCapacity = IMAGE_CACHE_CAPACITY;

		// note we do *not* override removeEldestEntry() here--users of this class must call shrinkImageCache().
		// that's because we don't know when is a good time to flush the cache
		this._imageCache = new java.util.LinkedHashMap(_imageCacheCapacity, 0.75f, true);
	}

	public String resolveURI(String uri)
	{
		if(uri == null)
			return null;

		String ret = null;

		if(uri.startsWith("data:"))
		{
			try
			{
				URL result = new URL(null, uri, new Handler());
				ret = result.toString();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		if(_baseURL == null)
		{//first try to set a base URL
			try
			{
				URL result = new URL(uri);
				setBaseURL(result.toExternalForm());
			}
			catch(MalformedURLException e)
			{
				try
				{
					setBaseURL(new File(".").toURI().toURL().toExternalForm());
				}
				catch(Exception e1)
				{
					XRLog.exception("The default NaiveUserAgent doesn't know how to resolve the base URL for " + uri);
					return null;
				}
			}
		}
		// test if the URI is valid; if not, try to assign the base url as its parent
		try
		{
			return new URL(uri).toString();
		}
		catch(MalformedURLException e)
		{
			XRLog.load("Could not read " + uri + " as a URL; may be relative. Testing using parent URL " + _baseURL);
			try
			{
				URL result = new URL(new URL(_baseURL), uri);
				ret = result.toString();
			}
			catch(MalformedURLException e1)
			{
				XRLog.exception("The default NaiveUserAgent cannot resolve the URL " + uri + " with base URL " + _baseURL);
			}
		}

		return ret;
	}

	protected InputStream resolveAndOpenStream(String uri)
	{
		java.io.InputStream is = null;
		uri = resolveURI(uri);

		if(uri.startsWith("data:"))
		{
			try
			{
				is = new URL(null, uri, new Handler()).openStream();
			}
			catch(Exception ee)
			{
				System.out.println(ee);
			}
		}

		try
		{
			is = new URL(uri).openStream();
		}
		catch(java.net.MalformedURLException e)
		{
			XRLog.exception("bad URL given: " + uri, e);

		}
		catch(java.io.FileNotFoundException e)
		{
			XRLog.exception("item at URI " + uri + " not found");
		}
		catch(java.io.IOException e)
		{
			XRLog.exception("IO problem for " + uri, e);
		}
		return is;
	}

	public ImageResource getImageResource(String uri)
	{
		ImageResource resource = null;
		uri = resolveURI(uri);
		resource = (ImageResource)_imageCache.get(uri);
		if(resource == null)
		{
			InputStream is = resolveAndOpenStream(uri);
			if(is != null)
			{
				try
				{

					URL url = null;

					if(uri.startsWith("data:"))
						url = new URL(null, uri, new Handler());
					else
						url = new URL(uri);

					if(url.getPath() != null &&
					        url.getPath().toLowerCase().endsWith(".pdf"))
					{
						PdfReader reader = _outputDevice.getReader(url);
						PDFAsImage image = new PDFAsImage(url);
						Rectangle rect = reader.getPageSizeWithRotation(1);
						image.setInitialWidth(rect.getWidth() * _outputDevice.getDotsPerPoint());
						image.setInitialHeight(rect.getHeight() * _outputDevice.getDotsPerPoint());
						resource = new ImageResource(image);
					}
					else
					{
						Image image = Image.getInstance(url);
						scaleToOutputResolution(image);
						resource = new ImageResource(new ITextFSImage(image));
					}
					_imageCache.put(uri, resource);
				}
				catch(IOException e)
				{
					XRLog.exception("Can't read image file; unexpected problem for URI '" + uri + "'", e);
				}
				catch(BadElementException e)
				{
					XRLog.exception("Can't read image file; unexpected problem for URI '" + uri + "'", e);
				}
				finally
				{
					try
					{
						is.close();
					}
					catch(IOException e)
					{
						// ignore
					}
				}
			}
		}
		if(resource == null)
		{
			resource = new ImageResource(null);
		}
		return resource;
	}

	private void scaleToOutputResolution(Image image)
	{
		float factor = _sharedContext.getDotsPerPixel();
		image.scaleAbsolute(image.getPlainWidth() * factor, image.getPlainHeight() * factor);
	}

	public SharedContext getSharedContext()
	{
		return _sharedContext;
	}

	public void setSharedContext(SharedContext sharedContext)
	{
		_sharedContext = sharedContext;
	}
	
	public String getBaseURL() {
        return _baseURL;
    }

	public void documentStarted() {
		shrinkImageCache();
	}

	public void documentLoaded() { /* ignore*/ }

	public void onLayoutException(Throwable t) { /* ignore*/ }

	public void onRenderException(Throwable t) { /* ignore*/ }
	
	
	/**
	 * If the image cache has more items than the limit specified for this class, the least-recently used will
	 * be dropped from cache until it reaches the desired size.
	 */
	public void shrinkImageCache() {
		int ovr = _imageCache.size() - _imageCacheCapacity;
		Iterator it = _imageCache.keySet().iterator();
		while (it.hasNext() && ovr-- > 0) {
			it.next();
			it.remove();
		}
	}

	/**
	 * Empties the image cache entirely.
	 */
	public void clearImageCache() {
		_imageCache.clear();
	}
	
	/**
	 * Retrieves the CSS located at the given URI.  It's assumed the URI does point to a CSS file--the URI will
	 * be accessed (using java.io or java.net), opened, read and then passed into the CSS parser.
	 * The result is packed up into an CSSResource for later consumption.
	 *
	 * @param uri Location of the CSS source.
	 * @return A CSSResource containing the parsed CSS.
	 */
	public CSSResource getCSSResource(String uri) {
        return new CSSResource(resolveAndOpenStream(uri));
    }
	
	/**
	 * URL relative to which URIs are resolved.
	 *
	 * @param url A URI which anchors other, possibly relative URIs.
	 */
	public void setBaseURL(String url) {
        _baseURL = url;
    }
	
    public byte[] getBinaryResource(String uri) {
        InputStream is = resolveAndOpenStream(uri);
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buf = new byte[10240];
            int i;
            while ( (i = is.read(buf)) != -1) {
                result.write(buf, 0, i);
            }
            is.close();
            is = null;

            return result.toByteArray();
        } catch (IOException e) {
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
    
	/**
	 * Retrieves the XML located at the given URI. It's assumed the URI does point to a XML--the URI will
	 * be accessed (using java.io or java.net), opened, read and then passed into the XML parser (XMLReader)
	 * configured for Flying Saucer. The result is packed up into an XMLResource for later consumption.
	 *
	 * @param uri Location of the XML source.
	 * @return An XMLResource containing the image.
	 */
    public XMLResource getXMLResource(String uri) {
        InputStream inputStream = resolveAndOpenStream(uri);
        XMLResource xmlResource;
        try {
            xmlResource = XMLResource.load(inputStream);
        } finally {
            if ( inputStream != null ) try {
                inputStream.close();
            } catch (IOException e) {
                // swallow
            }
        }
        return xmlResource;
    }
    
    /**
     * Returns true if the given URI was visited, meaning it was requested at some point since initialization.
     *
     * @param uri A URI which might have been visited.
     * @return Always false; visits are not tracked in the NaiveUserAgent.
     */
    public boolean isVisited(String uri) {
        return false;
    }
}
