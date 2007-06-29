package gawky.service.mt940;

import gawky.message.parser.PatternParser;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescV;
import gawky.message.part.Part;

public class Satz86 extends Part 
{
	public Desc[] getDesc() {
		return new Desc[] {
			new DescC(":86:"),     
			new DescV(390, "all", Desc.LF)
// citibank
//			new DescF(4,  "producttype"),
//			new DescV(2,  "paymenttype", "/"),
//			new DescV(2,  "furtherdescription", "/"),
//			new DescV(382, "narrativedescription", Desc.END)
		};
	}
	
	public static void main(String[] args) throws Exception 
	{
		PatternParser parser = new PatternParser();
		
		Satz86 bean = new Satz86();
		
		String str = ":86:/PT/DE/EI/EFT:XYZ LOCAL CORPORATION (ANDA20103H):      ADP PA\r\n" +
					 "YROLL FEES,";
			   
		bean.parse(parser, str);
		bean.echo();
		
	}
	
	public void echo()
	{
		System.out.println("MEHRZWECKFELD = [" + all + "]");
//		System.out.println(this.producttype);
//		System.out.println(this.paymenttype);
//		System.out.println(this.furtherdescription);
//		System.out.println(this.narrativedescription);
	}
	
	
	private String all;
	
	private String producttype;
	private String paymenttype;
	private String furtherdescription;
	private String narrativedescription;
	
	
	public String getFurtherdescription() {
		return furtherdescription;
	}

	public void setFurtherdescription(String furtherdescription) {
		this.furtherdescription = furtherdescription;
	}

	public String getNarrativedescription() {
		return narrativedescription;
	}

	public void setNarrativedescription(String narrativedescription) {
		this.narrativedescription = narrativedescription;
	}

	public String getPaymenttype() {
		return paymenttype;
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		this.all = all;
	}
	
	
}