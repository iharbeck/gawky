package example.message.parser;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescV;
import gawky.message.part.Part;

public class Sample extends Part {

	public void afterFill() {
	}
	
	public static void main(String[] args) throws Exception {
		Sample sample = new Sample();
		
		sample.parse("FOOT,100,30");
		System.out.println(sample.countrecords);
		System.out.println(sample.sumamount);
		
		System.out.println(sample.toString());
		System.out.println(sample.toDebugString());
		
		
	}
	
	public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescC("FOOT,"),
			new DescV(5,  "countrecords", Desc.ENDCOMMA),
			new DescV(12, "sumamount",    Desc.ENDALL),
		}; 
	}
	
	private String countrecords;
	private String sumamount;
	
	
	public String getCountrecords() {
		return countrecords;
	}

	public void setCountrecords(String countrecords) {
		this.countrecords = countrecords;
	}

	public String getSumamount() {
		return sumamount;
	}

	public void setSumamount(String sumamount) {
		this.sumamount = sumamount;
	}
	

}
