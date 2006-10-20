package gawky.message.part;

import gawky.file.Locator;
import gawky.global.Option;
import gawky.message.generator.Generator;
import gawky.message.parser.Parser;
import gawky.message.parser.ParserException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

public abstract class Part 
{
	Log log = LogFactory.getLog(Part.class);
	
    private Parser    parser;
    private Generator generator;

	private static HashMap hsDesc = new HashMap(); 
	
	abstract public Desc[] getDesc();
	
	private final Desc[] getOptDesc() {
		
		Desc[] desc = getDesc();

		ClassPool pool = ClassPool.getDefault();
		String classname = this.getClass().getName();
		
		boolean hasJavaAssist = Option.isClassInPath("javassist.ClassPool", "JavaAssist is not available");
		
		// Prepare Reflection call
		for(int i=0; i < desc.length; i++)
		{
			// Constanten do not have an attribute
			if(desc[i].format == Desc.FMT_CONSTANT) 
				continue;

			try {
				String mname = Character.toUpperCase(desc[i].name.charAt(0)) +  desc[i].name.substring(1);
				
				if(hasJavaAssist)
				{

					String proxycname = classname + "Accessor" + mname;
					log.info("Generating Proxyclass: " + proxycname);
					
					if(!Option.isClassInPath(proxycname, ""))
					{
						// Native case - Generate ProxyClasses
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
						
						// Generate Class files
						cc.writeFile(Locator.findBinROOT());
						cc.detach();
					}
					
					// load from Classpath
					desc[i].accessor = (Accessor)Class.forName(proxycname).newInstance();
					
					//desc[i].accessor = (Accessor)cc.toClass().newInstance();
				} else {
					// Reflection case - Lookup Method details
					desc[i].smethod = getClass().getMethod( "set" + mname, new Class[] {String.class});
					desc[i].gmethod = getClass().getMethod( "get" + mname, new Class[] {String.class});
				}
			} catch (Exception e) {
				log.error("Generating Getter/Setter", e);
			}
		}
		
		return desc;
	}
	
	public final Desc[] getCachedDesc() 
	{
		String key = this.getClass().getName();
		Desc[] desc = (Desc[])hsDesc.get(key); 
		if(desc == null) {
			desc = getOptDesc();
			hsDesc.put(key, desc);
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
