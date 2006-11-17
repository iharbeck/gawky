package gawky.service.fias2;

/**
 * @author Ingo Harbeck
 */

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.Part;

public class EOF extends Part
{
	//Record definition
	public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescC("EOF"),	
			new DescC("00")
		}; 
	}
}
