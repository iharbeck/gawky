package gawky.message.generator;

import gawky.message.part.Desc;
import gawky.message.part.Part;

public class UtilGenerator 
{
	public static void generateVars(Part part) 
	{
		Desc[] descs = part.getCachedDesc();
		
		for(int i=0; i < descs.length; i++){
			if(descs[i].format != Desc.FMT_CONSTANT)
				System.out.println("private String " + descs[i].name + ";");
		}
	}
}
