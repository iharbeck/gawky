package example.message.parser;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.Part;

/**
 * @author Ingo Harbeck
 *
 */
public class RequestHead extends Part
{
	// Record definition
	@Override
	public Desc[] getDesc()
	{
		return new Desc[] {
		        new DescC("HEAD00"),
		        new Desc(Desc.FMT_A, Desc.CODE_F, 4, "id"),
		        new Desc(Desc.FMT_A, Desc.CODE_F, 10, "account"),
		        new Desc(Desc.FMT_A, Desc.CODE_F, 10, "sortcode"),
		        new Desc(Desc.FMT_BINARY, Desc.CODE_F, 4, "binary"),
		        new Desc(Desc.FMT_LOWER, Desc.CODE_O, 20, "vlist1", Desc.END01),
		        new Desc(Desc.FMT_LOWER, Desc.CODE_O, 20, "vlist2", Desc.END01),
		        new Desc(Desc.FMT_DATE, Desc.CODE_O, 8, "datum")
		};
	}

	String id;
	String account;
	String sortcode;
	String vlist1;
	String vlist2;
	String datum;
	String binary;

	public String getDatum()
	{
		return datum;
	}

	public String getId()
	{
		return id;
	}

	public void setDatum(String string)
	{
		datum = string;
	}

	public void setId(String string)
	{
		id = string;
	}

	public String getBinary()
	{
		return binary;
	}

	public void setBinary(String string)
	{
		binary = string;
	}

	public String getVlist1()
	{
		return vlist1;
	}

	public void setVlist1(String vlist1)
	{
		this.vlist1 = vlist1;
	}

	public String getVlist2()
	{
		return vlist2;
	}

	public void setVlist2(String vlist2)
	{
		this.vlist2 = vlist2;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getSortcode()
	{
		return sortcode;
	}

	public void setSortcode(String sortcode)
	{
		this.sortcode = sortcode;
	}
}
