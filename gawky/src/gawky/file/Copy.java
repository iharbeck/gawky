package gawky.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class Copy 
{
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
