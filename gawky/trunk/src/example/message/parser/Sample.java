package example.message.parser;

import gawky.message.parser.Parser;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescV;
import gawky.message.part.Part;

public class Sample extends Part {

	public void afterFill() {
	}
	
	public static void main(String[] args) throws Exception {
		
		long start = System.currentTimeMillis();
		Parser.setDotrim(true);
		Parser.setDocheck(false);
		Sample sample = new Sample();
		
		for(int i=1; i <= 999999; i++)
		{
			String val = "FOOT," + i + ",0030";
			sample.parse(val);
			
			//System.out.print(sample.countrecords + "#");
			//System.out.println(sample.sumamount);
		}
		System.out.println(sample.countrecords);
		System.out.println(sample.sumamount);
		System.out.println(sample.buildString());
		
		System.out.println(System.currentTimeMillis() - start);
		//System.out.println(sample.toString());
		//System.out.println(sample.toDebugString());
	}
	
	public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescC("FOOT,"),
			//new DescV(5,  "countrecords", Desc.ENDCOMMA),
			new Desc(Desc.FMT_A, Desc.CODE_O, 10,  "countrecords", Desc.ENDCOMMA),
			new Desc(Desc.FMT_9, Desc.CODE_O, 12, "sumamount"), //,    Desc.ENDALL),
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
