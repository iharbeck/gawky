package example.template;

import gawky.global.Option;
import gawky.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TestFreemaker {

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

		// in eine String Variable
		String res = FreemarkerTemplate.processToString(root, "test.ftl");
		System.out.println(res);
		
		// direkt zum Screen
		FreemarkerTemplate.process(root, "test.ftl", System.out);
	}
	
}
