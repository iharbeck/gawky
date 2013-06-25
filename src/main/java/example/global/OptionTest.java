package example.global;

import gawky.global.Option;

public class OptionTest {

	public static void main(String[] args) throws Exception
	{
		//Option.initDefaultOptions();
//		Option.addOption("fest", "fe.st", true, "TESTOPTION");
//		
//		Option.init(null, "OptionTest", new String [] {"--fe.st=dd"} );
//		
//		System.out.println(":" + Option.getProperty("fest", "default"));
		
		Option.init();
		
		Option.enableXPath();
		
		System.out.println(Option.getProperty("server/port"));
		System.out.println(Option.getProperty("att[@stage='prod']/name"));

		Option.disableXPath();
		
		System.out.println(Option.getProperty("server.port"));

	}
	
}
