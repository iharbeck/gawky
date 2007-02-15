package gawky.xml.vtd;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.ParseException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;

public class VTD {

	public final static String getAttributVal(VTDNav vn, String name) throws NavException {
		int pos;
		if((pos = vn.getAttrVal(name)) != -1)
			return vn.toString(pos);
		else
			return "";
	}
	
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
    try {
    	// open a file and read the content into a byte array
    	VTDGen vg = new VTDGen();
    	
    	File f = new File( "c:/test.xml" ); 
    	byte[] buffer = new byte[ (int) f.length() ]; 
    	InputStream in = new FileInputStream( f ); 
    	in.read( buffer ); 
    	in.close();
    	
//    	if (!vg.parseFile("c:/test.xml",false))
//    		return;
    	
    	vg.setDoc(buffer);
    	vg.parse(false);
    	
		VTDNav vn = vg.getNav();
		AutoPilot ap = new AutoPilot(vn);
		//ap.selectXPath("//booking");
		
	    ap.selectElement("booking");
		
	    XMLGenerator.init("c:/test_out.xml");
	    
	    BaseObjectI obj = new BaseObject();
	    
	    long totalamount = 0;
		//while((result = ap.evalXPath())!=-1){
		while (ap.iterate()) {
			
			obj.setFirstname(getAttributVal(vn, "firstname"));
			obj.setLastname(getAttributVal(vn, "lastname"));
			obj.setAmount(getAttributVal(vn, "amount"));
			
			obj.setFirstname(getAttributVal(vn, "firstname"));
			obj.setLastname(getAttributVal(vn, "lastname"));
			obj.setAmount(getAttributVal(vn, "amount"));
			
			XMLGenerator.genxml(obj);
			
			if(!obj.getAmount().equals(""))
				totalamount += Long.parseLong(obj.getAmount());
//    			System.out.print(" glim:" + obj.getFirstname());
//    			System.out.print("   id:" + obj.getLastname());
//    			System.out.println("");
		}
		
		XMLGenerator.close();
		
		System.out.println("Total # of element " + totalamount + " " + (System.currentTimeMillis() -start));

    	}
        catch (NavException e){
    	     System.out.println(" Exception during navigation "+e);
        }
	}
}
