package gawky.jasper;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.CommentNode;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.HtmlNode;
import org.htmlcleaner.SimpleXmlSerializer;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.TagNodeVisitor;
import org.htmlcleaner.Utils;
import org.xhtmlrenderer.pdf.ITextRenderer;


public class Html2PDF 
{
	public static void main(String[] args) throws Exception {
		//renderURL(new URL("http://www.google.de"), new FileOutputStream("e:/users/test.pdf"), "UTF-8", "http://www.google.de");
		
		renderString("<table border=1><tr><td><div style='font-weight:bold; text-decoration:underline'>hello</div></td></tr></table>", "testxml.pdf", "UTF-8", "http://www.google.de");
	}

	public static void renderString(String xml, OutputStream os, String encoding, String base) throws Exception
    {
        byte[] byteArray = xml.getBytes(encoding); 
        ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);

        render(bais, os, encoding, base);
    }
    
	public static void renderString(String xml, String filename_pdf, String encoding, String base) throws Exception
    {
        OutputStream os = new FileOutputStream(filename_pdf);

        renderString(xml, os, encoding, base);
    }
    
    
	public static void renderStream(InputStream in, OutputStream os, String encoding, String base)
    {
    	render(in, os, encoding, base);
    }
    
	public static void renderURL(URL in, OutputStream os, String encoding, String base)
    {
    	render(in, os, encoding, base);
    }

    
    public static void render(Object indata, OutputStream os, String encoding, final String base) 
    {
        try 
        {
            CleanerProperties props = new CleanerProperties();
 
            props.setTranslateSpecialEntities(true);
            props.setTransResCharsToNCR(true);
            props.setOmitComments(true);
            
            
            HtmlCleaner cleaner = new HtmlCleaner(props);

            TagNode tagNode;
            if(indata instanceof URL)
            	tagNode = cleaner.clean((URL)indata);
            else
            	tagNode = cleaner.clean((InputStream)indata, encoding);
           
            tagNode.traverse(new TagNodeVisitor() {
 
                public boolean visit(TagNode tagNode, HtmlNode htmlNode) 
                {
                	if (htmlNode instanceof TagNode) 
                	{
                        TagNode tag = (TagNode) htmlNode;
                        String tagName = tag.getName();
                    
                        if("select".equals(tagName))
                        {
                        	tag.setName("span");

                        	if(tag.getChildren().size() == 0)
                        		return true;
                        	
                        	String text = ((TagNode)tag.getChildren().get(0)).getText().toString();
                        	
                        	List<TagNode> li = tag.getChildren();

                        	for(TagNode dd : li)
                        	{
                        		if(dd.getAttributeByName("selected") != null)
                        		{
                        			text = dd.getText().toString();
                        		}
                        	}
                        	
                        	tag.removeAllChildren();
                        	
                        	ContentNode cont = new ContentNode(text);
                        	tag.addChild(cont);
                        }
                        else if("input".equals(tagName))
                        {
                        	String type = tag.getAttributeByName("type");
                        	
                        	tag.setName("span");

                        	if(type == null)
                        		return true;
                        	
                        	if(type.equals("text"))
                        	{
                        		tag.setName("div");
                        		tag.setAttribute("class", tag.getAttributeByName("class ") + " ufo_input");
	                        	String text = tag.getAttributeByName("value");
	                        	if(text != null)
	                        	{
		                        	ContentNode cont = new ContentNode(text);
		                        	tag.addChild(cont);
	                        	}
                        	}
                        	else if(type.equals("checkbox"))
	                        {
	                        	tag.setName("span");
	                        	if(tag.getAttributeByName("checked") != null)
	                        	{
			                        tag.addChild(new ContentNode("[x]"));
	                        	}else {
	                        		tag.addChild(new ContentNode("[ ]"));
	                        	}
	                        }
                        	else if(type.equals("radio"))
	                        {
	                        	tag.setName("span");
	                        	if(tag.getAttributeByName("checked") != null)
	                        	{
			                        tag.addChild(new ContentNode("(*)"));
	                        	}else {
	                        		tag.addChild(new ContentNode("( )"));
	                        	}
	                        }
                        	
                        }
                        else if("textarea".equals(tagName))
                        {
                        	tag.setName("span");
                        }
                        else if ("img".equals(tagName)) 
                        {
                            String src = tag.getAttributeByName("src");
                            if (src != null && !src.startsWith("http")) {
                                tag.setAttribute("src", Utils.fullUrl(base, src));
                                //System.out.println(Utils.fullUrl(base, src));
                                System.out.println(Utils.fullUrl(base, src));
                            }
                            
                            //tag.setAttribute("src", Locator.findBinROOT() + src);
                        }
                        else if ("link".equals(tagName)) 
                        {
                            String rel = tag.getAttributeByName("rel");
                            String href = tag.getAttributeByName("href");
                            
                            if (href != null && "stylesheet".equals(rel)) 
                            {
                            	tag.setAttribute("href", Utils.fullUrl(base, href));
                            	/*
                                try 
                                {
                                    HttpClient client = new DefaultHttpClient();
                                    String fullUrl = "";
                                    if (href.startsWith("http")) 
                                    	fullUrl = href;
                                    else 
                                    	fullUrl = Utils.fullUrl(base, href);
                                    
                                    HttpGet get = new HttpGet(fullUrl);
                                    HttpResponse response = client.execute(get);
                                    HttpEntity entity = response.getEntity();
                                    if (entity != null) {
                                        InputStream is = entity.getContent();
                                        href = "css" + cssCounter + ".css";
                                        cssCounter++;
                                        OutputStream os = new FileOutputStream(href);
                                        IOUtils.copy(is, os);
                                    }
                                    tag.setAttribute("href", href);
                                } catch (IOException ex) {
                                    //Logger.getLogger(HTML2PDF.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                */
                            }
                        }
                    } 
                	else if (htmlNode instanceof CommentNode) 
                    {
                        //CommentNode comment = ((CommentNode) htmlNode);
                        //comment.getContent().append(" -- By HtmlCleaner");
                    }

                	return true;
                }
            });
 
 
// serialize to xml file
            
            String content = new SimpleXmlSerializer/*PrettyXmlSerializer*/(props).getAsString(tagNode, encoding);
 
// FlyingSaucer and iText part

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(content);
            
            renderer.layout();
            renderer.createPDF(os);
 
            os.close();
 
        } catch (Exception ex) {
        	ex.printStackTrace(System.out);
            //Logger.getLogger(HTML2PDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
