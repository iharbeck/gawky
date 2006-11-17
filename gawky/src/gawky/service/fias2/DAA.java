package gawky.service.fias2;

/**
 * @author Ingo Harbeck
 */

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.Part;


public class DAA extends Part
{
	//Record definition
	public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescC("DAA"),	
			new DescC("00"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 9,   "client_id"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 4,   "client_subcode"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 14,  "record_id"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 1,   "account_type"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 10,  "customer_id"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 2,   "address_type"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 3,   "address_number"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 15,  "vat_id"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 9,   "geo_code"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 40,  "name"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 40,  "name_2"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 40,  "address_line_1"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 40,  "address_line_2"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 40,  "address_line_3"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 40,  "title"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 40,  "street"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 40,  "street_2"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 40,  "city"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 40,  "state"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 3,   "country"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 10,  "zip_code"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 16,  "po_box"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 10,  "zip_po_box"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 40,  "phone_number"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 40,  "fax_number"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 100,  "email_address"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 40,  "address_line_4"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 40,  "address_line_5"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 40,  "address_line_6"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 40,  "address_line_7"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 40,  "salutation"),
		}; 
	}
}
