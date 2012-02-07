package gawky.template;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import gawky.file.Locator;
import gawky.global.Option;

public class FreemarkerTemplate implements gawky.template.Template
{
	/**
	 *  http://freemarker.sourceforge.net/docs/dgui_quickstart_basics.html#example.first
	 */ 
	
	Configuration cfg = null;
	
	public static gawky.template.Template getInstance() {
		return new FreemarkerTemplate();
	}
	
	public String processToString(Object obj, String templatefile, String encoding) throws Exception 
	{
		ByteArrayOutputStream barray = new ByteArrayOutputStream();
		process(obj, templatefile, barray, encoding);
		
		//return barray.toString();
		return barray.toString(encoding);
	}
	
	
	public void process(Object obj, String templatefile, OutputStream out, String encoding) throws Exception 
	{
		String templates = Option.getProperty("freemarker.templates", "/");

		if(cfg == null)
		{
			cfg = new Configuration();

			cfg.setDirectoryForTemplateLoading(new File(Locator.findBinROOT() + templates));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setEncoding(Locale.GERMAN, encoding);
		}
		
		Template temp = cfg.getTemplate(templatefile);
		
		Writer writer = new OutputStreamWriter(out, encoding);
		temp.process(obj, writer);
		writer.flush();
	}
	
	
}
