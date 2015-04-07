package example;

import gawky.global.Option;

public class OptionOverwrite
{
	public static void main(String[] args) throws Exception
    {
		Option.init();
		
		
		Option.setProperty("base.hello", "yes");
        
        System.out.println(Option.getProperty("base.hello"));
    }
}
