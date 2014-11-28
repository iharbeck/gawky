package gawky.service.mt940;

import gawky.message.part.Part;

public interface MTListener
{
	public void process(String line, Part part) throws Exception;
}
