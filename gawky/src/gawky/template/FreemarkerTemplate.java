package gawky.template;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import gawky.file.Locator;
import gawky.global.Option;

public class FreemarkerTemplate 
{
	/**
	 *  http://freemarker.sourceforge.net/docs/dgui_quickstart_basics.html#example.first
	 */ 
	
	static Configuration cfg = null;
	
	public static String processToString(Object obj, String templatefile) throws Exception 
	{
		ByteArrayOutputStream barray = new ByteArrayOutputStream();
		process(obj, templatefile, barray);
		
		return barray.toString();
	}
	
	
	public static void process(Object obj, String templatefile, OutputStream out) throws Exception 
	{
		String templates = Option.getProperty("freemarker.templates", "/");

		if(cfg == null)
		{
			cfg = new Configuration();

			cfg.setDirectoryForTemplateLoading(new File(Locator.findBinROOT() + templates));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
		}
		
		Template temp = cfg.getTemplate(templatefile);
		
		Writer writer = new OutputStreamWriter(out);
		temp.process(obj, writer);
		writer.flush();
	}
	
	
}
