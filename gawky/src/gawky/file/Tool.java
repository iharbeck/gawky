package gawky.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Tool {

	public static void unzip(String filename) throws IOException 
	{
		InputStream in = new BufferedInputStream(new FileInputStream(filename));
		ZipInputStream zin = new ZipInputStream(in);
		ZipEntry e;

		while ((e = zin.getNextEntry()) != null) {

			FileOutputStream out = new FileOutputStream(e.getName());
			byte[] b = new byte[512];
			int len = 0;
			while ((len = zin.read(b)) != -1) {
				out.write(b, 0, len);
			}
			out.close();
		}
		zin.close();
	}

	public static void zip(String file, String target) throws IOException 
	{
		zip(new String[] { file }, target);
	}

	public static void zip(String files[], String target) throws IOException 
	{
		byte b[] = new byte[512];
		ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(target));
	
		for (int i = 0; i < files.length; i++) {
			InputStream in = new FileInputStream(files[i]);
			ZipEntry e = new ZipEntry(files[i].replace(File.separatorChar, '/'));
			zout.putNextEntry(e);
			int len = 0;
			while ((len = in.read(b)) != -1) {
				zout.write(b, 0, len);
			}
			zout.closeEntry();
		}
		zout.close();
	}
	
	
	 public void copy(String in, String out) throws Exception 
	 {
		 copy(new File(in), new File(out));
	 }
	 
	 public void copy(File in, File out) throws Exception 
	 {
	     FileChannel src = new FileInputStream(in).getChannel();
	     FileChannel dest = new FileOutputStream(out).getChannel();
	     src.transferTo(0, src.size(), dest);
	     src.close();
	     dest.close();
	 }
}
