package example.message.parser;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.Part;

/**
 * @author Ingo Harbeck
 *
 */
public class RequestPos extends Part
{

	@Override
	public Desc[] getDesc()
	{
		return new Desc[] {
		        new DescC("POS00"),
		        new Desc(Desc.FMT_A, Desc.CODE_F, 4, "id")
		};
	}

	String id;

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}
}
