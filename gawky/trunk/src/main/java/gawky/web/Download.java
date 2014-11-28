package gawky.web;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Download extends HttpServlet
{
	private static Log log = LogFactory.getLog(Download.class);

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException
	{
		String filename = req.getPathInfo().substring(1);
		String targetPath = getInitParameter("path");

		res.setContentType("application/x-download");
		//res.setHeader("Content-Disposition", "attachment; filename=" + filename);
		res.setHeader("Content-Disposition", "inline; filename=" + filename.substring(filename.lastIndexOf('/') + 1));

		// Send the file.
		OutputStream out = res.getOutputStream();

		returnFile(targetPath + "/" + filename, out);

		return;
	}

	public static void returnFile(String filename, OutputStream out)
	        throws FileNotFoundException, IOException
	{
		InputStream in = null;
		try
		{
			in = new BufferedInputStream(new FileInputStream(filename));
			byte[] buf = new byte[4 * 1024]; // 4K buffer
			int bytesRead;
			while((bytesRead = in.read(buf)) != -1)
			{
				out.write(buf, 0, bytesRead);
			}
		}
		finally
		{
			if(in != null)
			{
				in.close();
			}
		}
	}
}
