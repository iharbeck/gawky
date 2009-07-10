package example.template;

import gawky.global.Option;
import gawky.template.FreemarkerTemplate;
import gawky.template.Template;

import java.util.HashMap;
import java.util.Map;

public class TestTemplate {

	/**
	 *  http://freemarker.sourceforge.net/docs/dgui_quickstart_basics.html#example.first
	 */ 
	
	public static void main(String[] args) throws Exception 
	{
		Option.init();
		
		// Templates sind in "freemarker.templates" 
		
		Map latest = new HashMap();
		latest.put("url", "products/greenmouse.html");
		latest.put("name", "green mouse");
		
		Map root = new HashMap();
		root.put("user", "Big Joe");
		root.put("latestProduct", latest);

		Template tmp = FreemarkerTemplate.getInstance();
		// in eine String Variable
		String res = tmp.processToString(root, "test.ftl");
		System.out.println(res);
		
		// direkt zum Screen
		tmp.process(root, "test.ftl", System.out);
		
		System.exit(0);
	}
	
}
