package example.xml;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.xpath.XPathEvalException;
import com.ximpleware.xpath.XPathParseException;

public class Parser {

	public static void main(String[] args) throws Exception {
		
    try {
    	// open a file and read the content into a byte array
    	VTDGen vg = new VTDGen();
    	if (vg.parseFile("c:/test.xml",true)){
    		VTDNav vn = vg.getNav();
    		AutoPilot ap = new AutoPilot(vn);
    		ap.selectXPath("//book");
    		int result = -1;
    		int count = 0;
    		while((result = ap.evalXPath())!=-1){
    			System.out.print(""+result+"  ");     
    			System.out.print("Element name ==> "+vn.toString(result));
    			System.out.println("id:" + vn.toString(vn.getAttrVal("id")));
    			//vn.matchElement("title");
    			vn.toElement(VTDNav.FIRST_CHILD, "title");
    			System.out.println("title:" + vn.toString(vn.getText()));
    			vn.toElement(VTDNav.PARENT);
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
