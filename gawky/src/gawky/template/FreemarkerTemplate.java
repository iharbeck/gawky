package gawky.template;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import gawky.file.Locator;

public class FreemarkerTemplate 
{
	/**
	 *  http://freemarker.sourceforge.net/docs/dgui_quickstart_basics.html#example.first
	 */ 
	
	public static void main(String[] args) throws Exception 
	{
		Configuration cfg = new Configuration();
		
		cfg.setDirectoryForTemplateLoading(new File(Locator.findBinROOT() + "/gawky/template"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		Map latest = new HashMap();
		latest.put("url", "products/greenmouse.html");
		latest.put("name", "green mouse");
		
		Map root = new HashMap();
		root.put("user", "Big Joe");
		root.put("latestProduct", latest);

		Template temp = cfg.getTemplate("test.tmpl");
		
		Writer out = new OutputStreamWriter(System.out);
		temp.process(root, out);
		out.flush();
	}
}
