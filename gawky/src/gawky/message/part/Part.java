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

public abstract class Part implements Cloneable
{
	Log log = LogFactory.getLog(Part.class);

	private static Parser    parser;
    private static Generator generator;

    private Object clone;

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

	public void descAfterInterceptor(Desc[] descs) {

	}

	protected final Desc[] getOptDesc() {

		Desc[] descs = getDesc();

		ClassPool pool = null;
		String classname = getClass().getName();;

		boolean hasJavaAssist = Option.isClassInPath("javassist.ClassPool", "JavaAssist is not available");

		String folder = "file://" + Locator.findBinROOT() + "worker/";

		log.info("JavaAssist folder: " + folder);
		ClassLoader urlCl = null;

		try {
			urlCl  = URLClassLoader.newInstance(
					    new URL[]{new URL( folder )});
		} catch(Exception e) {
			log.error("Missing temp folder: " + folder);
			hasJavaAssist = false;
		}

		if(hasJavaAssist)
			pool = ClassPool.getDefault();

		// Prepare Attribute Access
		for(int i=0; i < descs.length; i++)
		{
			// Constanten do not have an attribute
			if(descs[i].format == Desc.FMT_CONSTANT)
				continue;

			try {
				String mname = Character.toUpperCase(descs[i].name.charAt(0)) +  descs[i].name.substring(1);

				if(hasJavaAssist)
				{
					log.info("Attribute access: using JavaAssist");

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
					log.info("Attribute access: using Reflection");

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

		descAfterInterceptor(descs);

		return descs;
	}

	private static HashMap hmDesc = new HashMap();

	Desc[] cacheddesc = null;

	public final Desc[] getCachedDesc()
	{
		if(cacheddesc != null)
			return cacheddesc;

		cacheddesc = (Desc[])hmDesc.get(getClass());
		if(cacheddesc == null) {
			cacheddesc = getOptDesc();
			hmDesc.put(getClass(), cacheddesc);
		}

		return cacheddesc;
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

	public final Generator getGenerator() {
		if(generator == null)
			generator = Generator.getInstance();
		return generator;
	}

	public final Parser getParser() {
		if(parser == null)
			parser = Parser.getInstance();
		return parser;
	}

	public final Object parse(String str) throws ParserException
	{
		return getParser().parse(str, this);
	}

	/**
	 * for specialized parsers
	 */
	public final Object parse(Parser extparser, String str) throws ParserException
	{
		return extparser.parse(str, this);
	}

	public final Object parse(Parser extparser, byte[] bytes) throws ParserException, UnsupportedEncodingException
	{
		return extparser.parsebytes(bytes, this);
	}

	public void afterFill() {

	}

	public void beforeStore() {

	}

	public Object clone() {
		try {
			Object clone = (Object) super.clone();
	            return clone;
	     } catch (CloneNotSupportedException e) {
	       return null;
	     }
	}

	public void doclone() {
		clone = clone();
	}

	public Object getBackup() {
		return clone;
	}
}
