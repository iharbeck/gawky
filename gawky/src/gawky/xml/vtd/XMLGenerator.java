package gawky.xml.vtd;

import java.io.FileOutputStream;


public class XMLGenerator {

	public FileOutputStream out;
	public StringBuffer xml= new StringBuffer();
	
	public void init(String filename) throws Exception {
		out = new FileOutputStream(filename, false);
	}
	
	public void close() throws Exception {
		out.write(xml.toString().getBytes());
		out.close();
	}
	
	public final void addAttribut(String name, String value)
	{
		xml.append(name).append("=\"").append(value).append("\" ");
	}
	
	public final void genxml(BaseObjectI obj) throws Exception 
	{
		xml.append("<booking ");

		//obj.getFirstname().indexOf('\'');
//		xml.append("firstname=\"").append(obj.getFirstname()).append("\" ");
//		xml.append("lasttname=\"").append(obj.getLastname()).append("\" ");
//		xml.append("amount=\"").append(obj.getAmount()).append("\" ");
		addAttribut("firstname", obj.getFirstname());
		addAttribut("lasttname", obj.getLastname());
		addAttribut("amount",    obj.getAmount());
		addAttribut("firstname", obj.getFirstname());
		addAttribut("lasttname", obj.getLastname());
		addAttribut("amount",    obj.getAmount());
		addAttribut("firstname", obj.getFirstname());
		addAttribut("lasttname", obj.getLastname());
		addAttribut("amount",    obj.getAmount());
		addAttribut("firstname", obj.getFirstname());
		addAttribut("lasttname", obj.getLastname());
		addAttribut("amount",    obj.getAmount());

		xml.append("/>\n");
	}
}
