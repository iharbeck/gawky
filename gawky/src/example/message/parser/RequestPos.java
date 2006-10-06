/*
 * Created on 11.03.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package example.message.parser;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.Part;

/**
 * @author HARB05
 *
 */
public class RequestPos extends Part {
	
	public Desc[] getDesc() {
		return new Desc[] {
			new DescC("POS00"),
			new Desc(Desc.FMT_A, Desc.CODE_F, 4, "id")
		};
	}
	
	String id;
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}
