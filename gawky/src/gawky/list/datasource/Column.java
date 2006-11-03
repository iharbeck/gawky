package gawky.list.datasource;

import gawky.list.listener.CellListener;

public class Column 
{
	public static final int TYPE_STRING   = 1;
	public static final int TYPE_CURRENCY = 2;
	public static final int TYPE_NUMBER   = 3;
	public static final int TYPE_DATE     = 4;
	public static final int HIDDEN        = -1;
	
	private int type;
	private int width = 0;
	private String lable;
	private CellListener listener;
	
	
	public Column(String lable, int type, CellListener listener) {
		this.lable = lable;
		this.type  = type;
		this.listener = listener;
	}
	
	public Column(String lable, int type) {
		this.lable = lable;
		this.type  = type;
		this.listener = null;
	}

	public Column(String lable) {
		this.lable = lable;
	}
	
	public Column(CellListener listener) {
		this.listener = listener;
	}

	public Column setHidden() {
		this.width = HIDDEN;
		return this;
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
	public CellListener getListener() {
		return listener;
	}
	public void setListener(CellListener listener) {
		this.listener = listener;
	}
	public int getWidth() {
		return width;
	}
	public Column setWidth(int width) {
		this.width = width;
		return this;
	}
}
