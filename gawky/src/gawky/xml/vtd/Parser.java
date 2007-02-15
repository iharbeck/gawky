package gawky.xml.vtd;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.xpath.XPathEvalException;
import com.ximpleware.xpath.XPathParseException;

public class Parser {

	public final static String getAttributVal(VTDNav vn, String name) throws NavException {
		int pos;
		if((pos = vn.getAttrVal(name)) != -1)
			return vn.toString(pos);
		else
			return "";
	}
	
	public static void main(String[] args) throws Exception {
		
    try {
    	// open a file and read the content into a byte array
    	VTDGen vg = new VTDGen();
    	if (vg.parseFile("c:/test.xml",true)){
    		VTDNav vn = vg.getNav();
    		AutoPilot ap = new AutoPilot(vn);
    		ap.selectXPath("//booking");
    		int result = -1;
    		int count = 0;
    		
    		while((result = ap.evalXPath())!=-1){
    			System.out.print(""+result+"  ");     
    			System.out.println("Element name ==> "+vn.toString(result));
    			
//    			System.out.println(" glim:" + vn.toString(vn.getAttrVal("firstname")));
//    			System.out.println(" id:" + vn.toString(vn.getAttrVal("lastname")));
    
    			System.out.println(" glim:" + getAttributVal(vn, "firstname"));
    			System.out.println("   id:" + getAttributVal(vn, "lastname"));
    			
    			//vn.matchElement("title");
/*    			vn.toElement(VTDNav.FIRST_CHILD, "title");
    			System.out.println("title:" + vn.toString(vn.getText()));
    			vn.toElement(VTDNav.PARENT);
*/
//    			vn.toElement(VTDNav.FIRST_CHILD, "author");
//    			System.out.println("author:" + vn.toString(vn.getText()));
//    			
    			count++;
    		}
    		System.out.println("Total # of element "+count);
    		}
         }
         catch (NavException e){
    	     System.out.println(" Exception during navigation "+e);
         }
         catch (XPathParseException e){
    	     
         }
         catch (XPathEvalException e){
    	    
         }

	}
}
