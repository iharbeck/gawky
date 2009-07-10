package gawky.service.mt940;

import gawky.message.part.Part;

public class Handler implements MTListener {

	public void process(String line, Part part) throws Exception 
	{
//		System.out.println("<b><pre style='margin-top:0px;margin-bottom:0px;'>" + line + "</pre></b>");
//		System.out.println("<pre  style='margin-top:0px;margin-bottom:7px;'>");
//		System.out.print("</pre>");
		System.out.println(((MTRecord)part).getType());
		
		if(part instanceof Satz86) {
			System.out.println(((Satz86)part).getAll());
			System.out.println(line);
		}
	}

}
