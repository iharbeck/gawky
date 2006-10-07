/*
 * RequestHead.java
 *
 * Created on 6. August 2003, 11:27
 */

package gawky.service.crm;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.Reserved;

/**
 *
 * @author  Ingo Harbeck
 */
public class RequestHeadPaymentech extends RequestHead
{
    public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescC("HEAD"),	
			new DescC("00"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 5,  "message_length"),          
			new DescF(Desc.FMT_9, Desc.CODE_R, 5,  "checksum"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 2,  "transaction_type"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 1,  "batch_flag"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 9,  "client_id"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 8,  "client_subcode"),
		    new Reserved(15),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 2,  "language"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 1,  "character_encoding"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 16, "transaction_id"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 10, "customer_id")
		}; 
	}
}
