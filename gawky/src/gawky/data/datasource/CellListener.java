package gawky.data.datasource;

public interface CellListener {
	public String getAttribute(String name);
	public String process(Datasource ds, int column);
}
