package gawky.xml.vtd;

import java.io.FileOutputStream;


public class XMLGenerator {

	public static FileOutputStream out;
	
	public static void init(String filename) throws Exception {
		out = new FileOutputStream(filename, false);
	}
	
	public static void close() throws Exception {
		out.close();
	}
	
	
	
	public static final void genxml(BaseObjectI obj) throws Exception 
	{
		String xml;
		
		xml  = "<booking ";

		obj.getFirstname().indexOf('\'');
		xml += "firstname=\"" + obj.getFirstname() + "\" ";
		xml += "lasttname=\"" + obj.getLastname() + "\" ";
		xml += "amount=\"" + obj.getAmount() + "\" ";
	
		xml += "/>\n";

		out.write(xml.getBytes());
	}
}
