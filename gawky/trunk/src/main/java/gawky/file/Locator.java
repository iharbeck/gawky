package gawky.file;

import java.io.InputStream;
import java.net.URL;

public class Locator 
{
	public static final String findBinROOT() 
	{
		String path = findPath("properties.xml");
		
		if(path != null)
			return path.substring(0, path.length()-14);
		else
			return findPath("");
	}
	
	public static final String findPath(String filename) {
		return findPath("/" + filename, Locator.class);
	}
	
	public static final String findPath(String filename, Class clazz) {
		URL url =  clazz.getResource(filename);
		return url.getPath();
	}
	
	public static final InputStream findStream(String filename) {
		return findStream("/" + filename, Locator.class);
	}
	
	public static final InputStream findStream(String filename, Class clazz) {
		InputStream stream =  clazz.getResourceAsStream(filename);
		return stream;
	}
}
