package gawky.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CompressionResponseWrapper extends HttpServletResponseWrapper
{

	// Original response
	protected HttpServletResponse origResponse = null;

	// The ServletOutputStream that has been returned by getOutputStream(),
	// if any
	protected ServletOutputStream stream = null;

	// The PrintWriter that has been returned by getWriter(), if any
	protected PrintWriter writer = null;

	// The threshold number to compress
	protected int threshold = 0;

	public CompressionResponseWrapper(HttpServletResponse response)
	{
		super(response);
		origResponse = response;
	}

	public void setCompressionThreshold(int threshold)
	{
		this.threshold = threshold;
	}

	public ServletOutputStream createOutputStream() throws IOException
	{
		return new CompressionResponseStream(origResponse);
	}

	@Override
	public void flushBuffer() throws IOException
	{
		((CompressionResponseStream)stream).flush();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException
	{
		if(writer != null)
		{
			throw new IllegalStateException("getWriter() has already been called " +
			        "for this response");
		}
		if(stream == null)
		{
			stream = createOutputStream();
		}
		((CompressionResponseStream)stream).setCommit(true);
		((CompressionResponseStream)stream).setBuffer(threshold);
		return stream;
	}

	@Override
	public PrintWriter getWriter() throws IOException
	{
		if(writer != null)
		{
			return writer;
		}

		if(stream != null)
		{
			throw new IllegalStateException("getOutputStream() has already " +
			        "been called for this response");
		}

		stream = createOutputStream();
		((CompressionResponseStream)stream).setCommit(true);
		((CompressionResponseStream)stream).setBuffer(threshold);
		writer = new PrintWriter(stream);
		return writer;
	}
}
