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
	
	public static void processFile(String filename, LineHandler handler, String encoding) throws Exception
	{
		Reader in = new InputStreamReader(new FileInputStream(filename), encoding);
		
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
	
	public static void processUTF8File(String filename, LineHandler handler) throws Exception
	{
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
	
	
	public final static boolean startsWith(String[] records, String line) {
		for(int i=0; i < records.length; i++)
			if(line.startsWith(records[i]))
				return true;
		return false;
	}
	
	public static void processFile(String filename, LineHandler handler, String encoding, String[] records) throws Exception
	{
		Reader in = new InputStreamReader(new FileInputStream(filename), encoding);
		
		BufferedReader is = new BufferedReader(in);
		String line = null;
		
		try {   
			for(;;) 
			{
				line = is.readLine();
				
				if(line == null)
					return;
				
				is.mark(8192);
				
				String ahead;
				for(;;) {
					ahead = is.readLine();
					if(ahead  == null)
						break;
					if(!startsWith(records, ahead))
					{
						line += "\n" + ahead;
						is.mark(8192);
					} else {
						is.reset();
						break;
					}
				} 
				
				handler.processLine(line);
			}
		} catch (CancelException e) {
	    } finally {
	    	is.close();
	    }
	}
	

}
