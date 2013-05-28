package example.template;

import gawky.global.Constant;
import gawky.global.Option;
import gawky.jasper.Html2PDF;
import gawky.template.FreemarkerTemplate;
import gawky.template.Template;

import java.util.HashMap;
import java.util.Map;

public class TestTemplate2
{

	/**
	 * http://freemarker.sourceforge.net/docs/dgui_quickstart_basics.html#example.first
	 */

	public static void main(String[] args) throws Exception
	{

		System.out.println("START");
		for(int i = 0; i < 2; i++)
		{
			String str = _main(null);
			System.out.println("#" + i);
			Html2PDF.renderString(str, "c:/out/" + i +".pdf", Constant.ENCODE_UTF8, "");
		}
		
		System.out.println("DONE");

	}

	public static String _main(String[] args) throws Exception
	{
		Option.init();

		// Templates sind in "freemarker.templates" 

		Template tmp = FreemarkerTemplate.getInstance();
		// in eine String Variable
		String res = tmp.processToString(new TestData(), "test2.ftl", Constant.ENCODE_ISO);

		return res;
		//System.out.println(res);

		// direkt zum Screen
		//tmp.process(root, "test.ftl", System.out, Constant.ENCODE_ISO);

		//System.exit(0);
	}

}
