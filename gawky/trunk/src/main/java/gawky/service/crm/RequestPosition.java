package gawky.service.crm;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.DescV;
import gawky.message.part.Part;

/**
 *
 * @author  Ingo Harbeck
 */
public class RequestPosition extends Part
{
	@Override
	public Desc[] getDesc()
	{

		return new Desc[] {
		        new DescC("POSN"),
		        new DescC("00"),
		        new DescF(Desc.FMT_9, Desc.CODE_R, 14, "gross_amount"),
		        new DescF(Desc.FMT_9, Desc.CODE_R, 4, "vat_rate"),
		        new DescF(Desc.FMT_9, Desc.CODE_R, 14, "vat_amount"),
		        new DescF(Desc.FMT_9, Desc.CODE_O, 4, "number_units"),
		        new DescV(Desc.FMT_A, Desc.CODE_O, 12, "product_group", Desc.END01),
		        new DescV(Desc.FMT_A, Desc.CODE_O, 16, "product_id", Desc.END01),
		        new DescV(Desc.FMT_A, Desc.CODE_O, 5, "type_of_service", Desc.END01),
		        new DescV(Desc.FMT_A, Desc.CODE_O, 20, "campaign_id", Desc.END01),
		        new DescV(Desc.FMT_A, Desc.CODE_O, 3, "title_id", Desc.END01),
		        new DescV(Desc.FMT_A, Desc.CODE_O, 196, "description", Desc.END01),
		        new DescC(Desc.END02)
		};
	}

	public String gross_amount = "";
	public String vat_rate = "";
	public String vat_amount = "";
	public String number_units = "";
	public String product_group = "";
	public String product_id = "";
	public String type_of_service = "";
	public String campaign_id = "";
	public String title_id = "";
	public String description = "";

	public String getCampaign_id()
	{
		return campaign_id;
	}

	public void setCampaign_id(String campaign_id)
	{
		this.campaign_id = campaign_id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getGross_amount()
	{
		return gross_amount;
	}

	public void setGross_amount(String gross_amount)
	{
		this.gross_amount = gross_amount;
	}

	public String getNumber_units()
	{
		return number_units;
	}

	public void setNumber_units(String number_units)
	{
		this.number_units = number_units;
	}

	public String getProduct_group()
	{
		return product_group;
	}

	public void setProduct_group(String product_group)
	{
		this.product_group = product_group;
	}

	public String getProduct_id()
	{
		return product_id;
	}

	public void setProduct_id(String product_id)
	{
		this.product_id = product_id;
	}

	public String getTitle_id()
	{
		return title_id;
	}

	public void setTitle_id(String title_id)
	{
		this.title_id = title_id;
	}

	public String getType_of_service()
	{
		return type_of_service;
	}

	public void setType_of_service(String type_of_service)
	{
		this.type_of_service = type_of_service;
	}

	public String getVat_amount()
	{
		return vat_amount;
	}

	public void setVat_amount(String vat_amount)
	{
		this.vat_amount = vat_amount;
	}

	public String getVat_rate()
	{
		return vat_rate;
	}

	public void setVat_rate(String vat_rate)
	{
		this.vat_rate = vat_rate;
	}
}
