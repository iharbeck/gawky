package gawky.file;

import java.io.BufferedReader;
import java.io.FileReader;

public class LineReader {
	
	public static void processFile(String filename, LineHandler handler) throws Exception
	{
		BufferedReader is = new BufferedReader(new FileReader(filename));
		String line = null;

		try {   
		    while((line = is.readLine()) != null)
		    	handler.processLine(line);
	    } finally {
	    	is.close();
	    }
	}
}
