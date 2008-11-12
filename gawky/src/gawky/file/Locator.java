package gawky.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Locator {
	
	public static final String findBinROOT() {
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
	
	public static void main(String[] args) throws Exception {
		initLib();
	}
	
	 public static void initLib() throws Exception {
		 Locator.initLib(Locator.findBinROOT() + "../lib");
	 }
	
	 public static void initLib(String path) throws Exception 
	 {
		File[] list = new File(path).listFiles();

		URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
		Class sysclass = URLClassLoader.class;
		
		Method method = sysclass.getDeclaredMethod("addURL", new Class[]{URL.class});
		method.setAccessible(true);
		
		System.out.println("CLASSPATH: " + path);
		
		for(File file : list) 
		{
			if(file.getName().toLowerCase().endsWith(".jar")) 
			{
				System.out.println(" + " + file.getName());
				method.invoke(sysloader, new Object[]{ file.toURI().toURL() });
			}
		}
	}
	
}
