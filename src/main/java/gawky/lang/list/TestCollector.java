package gawky.lang.list;


public class TestCollector
{

	public static void main(String[] args) throws Exception
	{
		Collector collector = new Collector();

		for(int i = 0; i < 10000; i++)
		{
			int x = (int)(Math.random() * 10);

			collector.setRoot("tom" + x);

			collector.add("invoice_count", 1);
			collector.add("invoice_amount", 1.4);
			collector.add("invoice_text", "*" + i);
		}

		for(String key : collector.keySet())
		{
			System.out.println(" Kunde:  " + key);
			collector.setRoot(key);

			System.out.printf(" Anzahl: %4s", collector.get("invoice_count"));
			System.out.printf(" Betrag: %5s", collector.get("invoice_amount"));
			System.out.printf(" Text: %s\n", collector.get("invoice_text"));  
		}

		//		System.out.println(collector.get("/tom2/invoice_count"));
		//		System.out.println(FormatUtil.formatCurrency((Double)collector.get("/tom2/invoice_amount")));
		//		System.out.println(collector.get("/tom3/invoice_text"));
		//
		//		HashMap root = new HashMap();
		//
		//		root.put("head", "test");
		//		root.put("collector", collector);
		//
		//		Template template = new FreemarkerTemplate("tools");
		//
		//		String str = template.processToString(root, "collector/Collector.ftl", Constant.ENCODE_ISO);
		//
		//		System.out.println(str);
	}
}
