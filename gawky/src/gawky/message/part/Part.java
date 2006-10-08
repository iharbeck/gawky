package gawky.message.part;


import gawky.message.generator.Generator;
import gawky.message.parser.Parser;
import gawky.message.parser.ParserException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;



public abstract class Part 
{
	Parser parser = new Parser();

	static HashMap hs = new HashMap(); 
	
	abstract public Desc[] getDesc();

	// Cache Desc Arrays
	public final Desc[] getCachedDesc() 
	{
		if(this instanceof NotCacheable)
			return getDesc();
		
		String key = this.getClass().getName();
		Desc[] desc = (Desc[])hs.get(key); 
		if(desc == null) {
			desc = getDesc();
			hs.put(key, desc);
		}
		
		return desc;
	}
	
	public final String toString()
	{
		return Generator.generateString(this);
	}
	
	public final byte[] getBytes(String charset) throws UnsupportedEncodingException
	{
		return toString().getBytes(charset);
	}
	
	public final byte[] getBytes()
	{
		return toString().getBytes();
	}
	
	
	public Part parse(String str) throws ParserException
	{
		parser.parse(str, this);
		return this;
	}
	
	public Part parse(Parser parser, String str) throws ParserException
	{
		parser.parse(parser.getNext(), this);
		return this;
	}
	
	public Part(String str) throws ParserException
    {
	   parser.parse(str, this);		
    }
	
	public Part() 
    {
    }

}
