package example;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

import gawky.file.Locator;

public class Installer 
{
	public static void main(String[] args)  throws Exception
	{
		Enumeration<URL> roots = new Installer().getClass().getClassLoader().getParent().getResources("");
		
		while(roots.hasMoreElements())
		{
			URL url = roots.nextElement();
			
			File root = new File(url.getPath());
			
			System.out.println(root.getAbsolutePath());
		}
		
		String path = Locator.findBinROOT();
		
		ArrayList<String> list = getClasses(path);
		
		//Class.forName(cname, false, this.getClass().getClassLoader())
		
		for(String f : list)
			System.out.println(f);
	}
	
	
	static ArrayList getClasses(String path)
	{
		return getClasses(path, "");
	}

	public static ArrayList getClasses(String path, String mainpackage)
	{
		ArrayList list = new ArrayList();

		listFolder(list, new File(path + "/" + mainpackage.replace('.', '/')), mainpackage);

		return list;
	}

	static void listFolder(ArrayList list, File f, String pack)
	{
		if(!f.isDirectory())
			return;

		File[] files = f.listFiles();
		for(int i = 0; i < files.length; i++)
		{
			File sub = files[i];
			String name = pack + (pack.equals("") ? "" : ".") + sub.getName();

			if(sub.isDirectory())
			{
				listFolder(list, sub, name);
			}
			else
			{
				if(name.endsWith(".class"))
				{
					String cname = name.substring(0, name.length() - ".class".length());
					list.add(cname);
				}
			}
		}
	}
	
	boolean isSubclass(Class target, Class superclass)
	{
		for(Class s = target; s != Object.class; s = s.getSuperclass())
		{
			if(s == superclass)
				return true;
		}
		return false;
	}
}
