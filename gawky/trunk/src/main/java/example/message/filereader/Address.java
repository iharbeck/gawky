package example.message.filereader;

import gawky.message.part.Desc;
import gawky.message.part.DescF;
import gawky.message.part.Part;

public class Address extends Part
{

	@Override
	public Desc[] getDesc()
	{
		return new Desc[] {
		        new DescF(10, "vorname"),
		        new DescF(10, "nachname"),
		        new DescF(10, "info1"),
		        new DescF(10, "info2")
		};
	}

	String vorname;
	String nachname;
	String info1;
	String info2;

	public String getNachname()
	{
		return nachname;
	}

	public void setNachname(String nachname)
	{
		this.nachname = nachname;
	}

	public String getVorname()
	{
		return vorname;
	}

	public void setVorname(String vorname)
	{
		this.vorname = vorname;
	}

	public String getInfo2()
	{
		return info2;
	}

	public void setInfo2(String info2)
	{
		this.info2 = info2;
	}

	public String getInfo1()
	{
		return info1;
	}

	public void setInfo1(String info1)
	{
		this.info1 = info1;
	}

}
