package gawky.xml.vtd;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;

public class VTD {


	public final String getAttributVal(VTDNav vn, String name) throws NavException 
	{
		int pos;
		if((pos = vn.getAttrVal(name)) != -1)
			return vn.toString(pos);
		else
			return "";
	}
	
	public byte[] readFile(String filename) throws Exception
	{
		File f = new File( filename ); 
    	byte[] buffer = new byte[ (int) f.length() ]; 
    	InputStream in = new FileInputStream( f ); 
    	in.read( buffer ); 
    	in.close();
    	
    	return buffer;
	}
	
	public static void main(String[] args) throws Exception 
	{
		VTD vtd = new VTD();
		vtd.run();
	}
	
	public void run() throws Exception 
	{
		long start = System.currentTimeMillis();

		byte[] buffer = readFile( "c:/test.xml"); 

    	VTDGen vg = new VTDGen();

    	vg.setDoc(buffer);
    	vg.parse(false);
    	
		VTDNav vn = vg.getNav();

		AutoPilot ap = new AutoPilot(vn);
		
		
		XMLGenerator xmlgen = new XMLGenerator();
	    xmlgen.init("c:/test_out.xml");
	    
	    BaseObjectI obj = new BaseObject();

	    
	    int count = 0;
	    long totalamount = 0;
		
	    //ap.selectXPath("/bookings/booking[@id=\"1\"]");
	    ap.selectElement("booking");
		
	    //while (ap.evalXPath()!=-1)
		while (ap.iterate()) 
	    {	
	    	mapObject(vn, obj);
	    	
	    	xmlgen.genxml(obj);
			
			if(!obj.getAmount().equals(""))
				totalamount += Long.parseLong(obj.getAmount());

			count++;
		}
		
	    xmlgen.close();
		
		System.out.println("Total # of element " + count + " : " + totalamount + " " + (System.currentTimeMillis() -start));

	}
	
	public final void mapObject(VTDNav vn, BaseObjectI obj) throws NavException 
	{
		obj.setFirstname(getAttributVal(vn, "firstname"));
		obj.setAmount(getAttributVal(vn, "amount"));
		obj.setLastname(getAttributVal(vn, "lastname"));
	}
}
