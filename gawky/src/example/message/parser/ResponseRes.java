package example.message.parser;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.Part;

public class ResponseRes extends Part {

	// Record definition
	public Desc[] getDesc() {
		return new Desc[]  {
			new DescC("RESP00"),
			new Desc(Desc.FMT_A, Desc.CODE_F, 4,  "code"),
			new Desc(Desc.FMT_A, Desc.CODE_F, 60, "detail", Desc.END01)
		};
	}
	
	String code    = "9999";
	String detail  = "";

	public String getCode() {
		return code;
	}

	public String getDetail() {
		return detail;
	}


	public void setCode(String code) {
		this.code = code;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
