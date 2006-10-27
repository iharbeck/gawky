package gawky.data.datasource;

public class Column 
{
	public static final int TYPE_STRING   = 1;
	public static final int TYPE_CURRENCY = 2;
	public static final int TYPE_NUMBER   = 3;
	public static final int TYPE_DATE     = 4;
	
	private int type;
	private String lable;
	
	public Column(String lable, int type) {
		this.lable = lable;
		this.type  = type;
	}
	
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
