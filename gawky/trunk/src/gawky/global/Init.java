package gawky.global;

import gawky.file.Locator;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Init 
{
	public static void initLib() throws Exception 
	 {
		 System.out.println("INITLIB");

		 // Libraries laden
		 Init.initLib(Locator.findBinROOT() + "../lib/");
	 }
	
	 public static void initLib(String path) throws Exception 
	 {
       System.out.println("CLASSPATH: " + path);

		File[] list = new File(path).listFiles();

		System.out.println("JARS: " + list.length);
		
		URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
		Class sysclass = URLClassLoader.class;
		
		Method method = sysclass.getDeclaredMethod("addURL", new Class[]{URL.class});
		method.setAccessible(true);
		
		
		if(list == null) {
			System.out.println("CLASSPATH: invalid no lib path");
			return;
		}
		
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
