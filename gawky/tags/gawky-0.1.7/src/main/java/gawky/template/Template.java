package gawky.template;

import java.io.OutputStream;

public interface Template {

	public String processToString(Object obj, String templatefile, String encoding) throws Exception;
	public void process(Object obj, String templatefile, OutputStream out, String encoding) throws Exception; 
	
}
