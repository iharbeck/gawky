package gawky.message.part;

import gawky.global.Option;
import gawky.message.generator.Generator;
import gawky.message.parser.Parser;
import gawky.message.parser.ParserException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

public abstract class Part 
{
    private Parser    parser;
    private Generator generator;

	static HashMap hs = new HashMap(); 
	boolean dynamic   = true;
	
	abstract public Desc[] getDesc();
	
	private final Desc[] getOptDesc() {
		
		Desc[] desc = getDesc();

		ClassPool pool = ClassPool.getDefault();
		String classname = this.getClass().getName();
		
		// Prepare Reflection call
		for(int i=0; i < desc.length; i++)
		{
			try {
				String mname = Character.toUpperCase(desc[i].name.charAt(0)) +  desc[i].name.substring(1);
				
				if(dynamic && Option.isClassInPath("javassist.ClassPool", "Install JavaAssist"))
				{
					// Native case
					
					CtClass cc = pool.makeClass(classname + "Accessor" + mname);  
		
					cc.addInterface( pool.get(Accessor.class.getName()) );
					
					CtMethod ms = CtNewMethod.make(
							" public void setValue(Object bean, String value) throws Exception {" +
							"  ((" + classname + ")bean).set" + mname + "(value); " +
							" } "
							, cc);
					cc.addMethod(ms); 
					
					CtMethod mg = CtNewMethod.make(
							" public String getValue(Object bean) throws Exception {" +
							"  return ((" + classname + ")bean).get" + mname + "(); " +
							" } "
							, cc);
					cc.addMethod(mg); 
					
					desc[i].accessor = (Accessor)cc.toClass().newInstance();
				} else {
					// Reflection case
					desc[i].smethod = getClass().getMethod( "set" + mname, new Class[] {String.class});
					desc[i].gmethod = getClass().getMethod( "get" + mname, new Class[] {String.class});
				}
			} catch (Exception e) {
			}
		}
		
		return desc;
	}
	
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
	 * for spezialized Generators
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
			parser = new Parser();
		return parser;
	}

	public final void parse(String str) throws ParserException
	{
		getParser().parse(str, this);
	}

	/**
	 * for specialized parsers
	 */
	public final void parse(Parser extparser, String str) throws ParserException
	{
		extparser.parse(str, this);
	}
}
