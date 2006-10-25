package gawky.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

public class LineReader {
	
	public static void processFile(String filename, LineHandler handler) throws Exception
	{
		BufferedReader is = new BufferedReader(new FileReader(filename));
		String line = null;

		try {   
		    while((line = is.readLine()) != null)
		    	handler.processLine(line);
		} catch (CancelException e) {
	    } finally {
	    	is.close();
	    }
	}
	
	public static void processUTF8File(String filename, LineHandler handler) throws Exception
	{
		//String uline = new String(line.getBytes(),"UTF-8");
		Reader in = new InputStreamReader(new FileInputStream(filename), "UTF-8");
		
		BufferedReader is = new BufferedReader(in);
		String line = null;

		try {   
		    while((line = is.readLine()) != null)
		    	handler.processLine(line);
		} catch (CancelException e) {
	    } finally {
	    	is.close();
	    }
	}
	
	
	

}
