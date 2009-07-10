package gawky.template;

import java.io.OutputStream;

public interface Template {

	public String processToString(Object obj, String templatefile) throws Exception;
	public void process(Object obj, String templatefile, OutputStream out) throws Exception; 
	
}
