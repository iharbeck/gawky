package gawky.message.part;

import gawky.file.Locator;
import gawky.global.Option;
import gawky.message.generator.Generator;
import gawky.message.parser.Parser;
import gawky.message.parser.ParserException;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class Part 
{
	Log log = LogFactory.getLog(Part.class);
	
    private Parser    parser;
    private Generator generator;

	private static HashMap hsDesc = new HashMap(); 
	
	/**
	 * used by matcher
	 * @param name
	 * @return
	 */
	public Desc getDescByName(String name) {
		Desc[] descs = getCachedDesc();
		
		for(int i=0; i < descs.length; i++) {
			if(descs[i].name.equals(name))
				return descs[i];
		}
		return null;
	}
	
	abstract public Desc[] getDesc();
	
	protected final Desc[] getOptDesc() {
		
		Desc[] descs = getDesc();

		ClassPool pool = null;
		String classname = this.getClass().getName();
		
		boolean hasJavaAssist = Option.isClassInPath("javassist.ClassPool", "JavaAssist is not available");
		
		ClassLoader urlCl = null;
		
		try {
			urlCl  = URLClassLoader.newInstance(
					    new URL[]{new URL( "file://" + Locator.findBinROOT() + "worker/" )});
		} catch(Exception e) {
			hasJavaAssist = false;
		}

		
		// Prepare Reflection call
		for(int i=0; i < descs.length; i++)
		{
			// Constanten do not have an attribute
			if(descs[i].format == Desc.FMT_CONSTANT) 
				continue;

			try {
				String mname = Character.toUpperCase(descs[i].name.charAt(0)) +  descs[i].name.substring(1);
				
				if(hasJavaAssist)
				{
					if(pool == null)
						pool = ClassPool.getDefault();
					
					// Native case - Generate Native Bytecode for ProxyClasses
					String proxycname = classname + "Accessor" + mname;

					if(!Option.isClassInClassloader(urlCl, proxycname, ""))
					{
						log.info("Generating Proxyclass: " + proxycname);
						CtClass cc = pool.makeClass(classname + "Accessor" + mname);  
			
						cc.addInterface( pool.get(Accessor.class.getName()) );
						
						// Create setter
						CtMethod ms = CtNewMethod.make(
								" public final void setValue(Object bean, String value) throws Exception {" +
								"  ((" + classname + ")bean).set" + mname + "(value); " +
								" } "
								, cc);
						cc.addMethod(ms); 

						// Create getter 
						CtMethod mg = CtNewMethod.make(
								" public final String getValue(Object bean) throws Exception {" +
								"  return ((" + classname + ")bean).get" + mname + "(); " +
								" } "
								, cc);
						cc.addMethod(mg); 
						
						// Generate Class files
						cc.writeFile(Locator.findBinROOT() + "worker");
						cc.detach();
						
						// Alternative if not written to local disk (no detach!!)
						//desc[i].accessor = (Accessor)cc.toClass().newInstance();
					}
					
					// load class from Classpath
					descs[i].accessor = (Accessor)Class.forName(proxycname, false, urlCl).newInstance();
					
					if(descs[i].accessor == null)
						throw new Exception("Can't generate GetterSetterclass for [" + mname + "]");
				} else {
					// Reflection case - Prepare Method details
					descs[i].smethod = getClass().getMethod( "set" + mname, new Class[] {String.class});
					descs[i].gmethod = getClass().getMethod( "get" + mname, null);

					if(descs[i].smethod == null)
						throw new Exception("Missing Setter for [" + mname + "]");
					if(descs[i].gmethod == null)
						throw new Exception("Missing Getter for [" + mname + "]");
				}
			} catch (Exception e) {
				log.error("Generating Getter/Setter", e);
			}
		}
		
		return descs;
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
	
	public final String toDebugString()
	{
		return getGenerator().generateDebugString(this);
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
	
	public void echo() {};
}
