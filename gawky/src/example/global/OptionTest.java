package example.global;

import gawky.global.Option;

public class OptionTest {

	public static void main(String[] args) throws Exception
	{
		//Option.initDefaultOptions();
		Option.addOption("xxxx", true, "TESTOPTION");
		
		Option.init(null, "OptionTest", args);
		
		System.out.println(":" + Option.getProperty("xxxx", "default"));
		
	}
	
}
