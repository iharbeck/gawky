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

	private Desc[] getOptDesc() {
		
		Desc[] desc = getDesc();

		// Prepare Reflection call
		for(int i=0; i < desc.length; i++)
		{
			try {
				String mname = Character.toUpperCase(desc[i].name.charAt(0)) +  desc[i].name.substring(1);
				
				desc[i].smethod = getClass().getMethod( "set" + mname, new Class[] {String.class});
				desc[i].gmethod = getClass().getMethod( "get" + mname, new Class[] {String.class});
			} catch (Exception e) {
			}
		}
		
		return desc;
	}
	
	// Cache Desc Arrays
	public final Desc[] getCachedDesc() 
	{
		if(this instanceof NotCacheable)
			return getOptDesc();
		
		String key = this.getClass().getName();
		Desc[] desc = (Desc[])hs.get(key); 
		if(desc == null) {
			desc = getOptDesc();
			
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
