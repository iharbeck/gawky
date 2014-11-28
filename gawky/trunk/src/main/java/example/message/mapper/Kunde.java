package example.message.mapper;

import gawky.message.part.Desc;
import gawky.message.part.DescV;
import gawky.message.part.Part;

public class Kunde extends Part
{

	//Record definition
	@Override
	public Desc[] getDesc()
	{

		return new Desc[] {
		        new DescV("kunde_id"),
		        new DescV("kundename")
		};
	}

	String kunde_id;
	String kundename;

	public String getKunde_id()
	{
		return kunde_id;
	}

	public void setKunde_id(String kunde_id)
	{
		this.kunde_id = kunde_id;
	}

	public String getKundename()
	{
		return kundename;
	}

	public void setKundename(String name)
	{
		this.kundename = name;
	}
}
