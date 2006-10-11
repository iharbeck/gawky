package gawky.message.part;

import gawky.message.generator.Generator;
import gawky.message.parser.Parser;
import gawky.message.parser.ParserException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public abstract class Part 
{
    private Parser    parser;
    private Generator generator;

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
		return getGenerator().generateString(this);
	}
	
	/**
	 * for Tuning just one parser
	 */
	public final String toString(Generator extgenerator)
	{
		return extgenerator.generateString(this);
	}
	
	public final byte[] getBytes(String charset) throws UnsupportedEncodingException
	{
		return toString().getBytes(charset);
	}
	
	public final byte[] getBytes()
	{
		return toString().getBytes();
	}
	
	public final Generator getGenerator() {
		if(generator == null)
			return new Generator();
		else
			return generator;
	}

	public final Parser getParser() {
		if(parser == null)
			return new Parser();
		else
			return parser;
	}

	public final void parse(String str) throws ParserException
	{
		getParser().parse(str, this);
	}

	/**
	 * for Tuning just one parser
	 */
	public final void parse(Parser extparser, String str) throws ParserException
	{
		extparser.parse(str, this);
	}
}
