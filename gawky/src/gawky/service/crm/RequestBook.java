/*
 * Created on 07.04.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package gawky.service.crm;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.Part;

/**
 * @author Ingo Harbeck
 *
 */
public class RequestBook extends Part
{
    public Desc[] getDesc() 
 	{
		return new Desc[] {
			new DescC("BOOK"),	
			new DescC("00"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 14,  "invoice_number"),          
			new DescF(Desc.FMT_9, Desc.CODE_R, 14,  "amount")
		}; 
	}

	private String invoice_number = "";
	private String amount     	  = "";

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

    
}
