package gawky.message.part;

import gawky.database.part.BColumn;
import gawky.database.part.Table;
import gawky.global.Option;
import gawky.message.generator.Generator;
import gawky.message.parser.Parser;
import gawky.message.parser.ParserException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;

import org.apache.commons.logging.LogFactory;

public abstract class Part implements Cloneable
{
	abstract protected Desc[] getDesc();

	private volatile static Parser parser;
	private volatile static Generator generator;

	private Desc[] _desc;
	private HashMap<String, Desc> _hsdesc;

	private Part clone;

	public void buildVars()
	{
		Desc[] descs = getCachedDesc();

		StringBuilder buf = new StringBuilder(2000);

		for(Desc desc : descs)
		{
			if(desc.format != Desc.FMT_CONSTANT)
			{
				buf.append("private String " + desc.name + ";\n");
			}
		}

		System.out.println(buf.toString());
	}

	/**
	 * used by matcher
	 * @param name
	 * @return
	 */
	public Desc getDescByName(String name)
	{
		HashMap<String, Desc> hsdescs = getCachedDescHash();

		return hsdescs.get(name);
	}

	public void descAfterInterceptor(Desc[] descs)
	{

	}

	/**
	 * Access properties by NAME
	 * @param name
	 * @return
	 */
	public final Object getValue(String name)
	{
		try
		{
			return getDescByName(name).getValue(this);
		}
		catch(Exception e)
		{
			System.out.println("name [" + name + "] invalid in " + this);
			return null;
		}
	}

	public final void setValue(String name, Object value)
	{
		try
		{
			getDescByName(name).setValue(this, value);
		}
		catch(Exception e)
		{
			System.out.println("name [" + name + "] invalid in " + this);
		}
	}

	public final Object getValueOld(String name)
	{
		try
		{
			return getDescByName(name).getValue(getBackup());
		}
		catch(Exception e)
		{
			System.out.println("name [" + name + "] invalid in " + this);
			return null;
		}
	}

	/**
	 * TEST dynamic getter/setter
	 * @param args
	 * @throws Exception
	 */
	/*
	public static void main(String[] args) 
	{
		Part part = Part.factory(SatzSimple.class);
		
		part.setValue("kennzeichen", "minimi");

		System.out.println( part.getValue("kennzeichen") );	
	}*/

	/**
	 * automatic generate fields and getter / setter as defined in 
	 * Desc-Structure!
	 * @param c
	 * @return
	 * @throws Exception
	 */

	static HashMap<String, Part> parts = new HashMap<String, Part>();

	static Part factory(Class c)
	{
		String key = c.getName();
		Part p = parts.get(key);

		if(p != null)
		{
			return p;
		}

		try
		{
			ClassPool cp = ClassPool.getDefault();

			cp.appendClassPath(new ClassClassPath(Part.class));

			CtClass cc = cp.get(c.getName());

			Desc descs[] = ((Part)c.newInstance()).getDesc();

			for(Desc desc : descs)
			{
				String fieldName = desc.name;

				if(fieldName.equals(""))
				{
					continue;
				}

				CtField f = CtField.make("private String " + fieldName + ";", cc);
				try
				{
					cc.addField(f);
				}
				catch(Exception e)
				{
				}

				String accessName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				cc.addMethod(CtNewMethod.make(
				        "public void set" + accessName + "(String " + fieldName + ")" +
				                "{" +
				                "this." + fieldName + " = " + fieldName + ";" +
				                "}", cc)
				        );

				cc.addMethod(CtNewMethod.make(
				        "public String get" + accessName + "(){" +
				                "  return " + fieldName + ";" +
				                "}", cc)
				        );
			}

			cc.setName(c.getName() + "Ext");

			p = (Part)cc.toClass().newInstance();

			parts.put(key, p);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return p;
	}

	private Desc[] getOptDesc()
	{
		Desc[] descs = getDesc();

		ClassPool pool = null;

		Class<? extends Part> clazz = getClass();

		String classname = clazz.getName();

		boolean hasJavaAssist = Option.isClassInPath("javassist.ClassPool", "JavaAssist is not available");

		if(hasJavaAssist)
		{
			pool = ClassPool.getDefault();
			pool.insertClassPath(new ClassClassPath(clazz));
		}

		int colnum = 1;

		// Prepare Attribute Access
		for(Desc desc : descs)
		{
			if(!(desc.dbname == null || desc.nostore))
			{
				desc.colnum = colnum++;
			}

			if(desc instanceof BColumn && (this instanceof Table))
			{
				((Table)this).setBinary(true);
			}

			// Constanten do not have an attribute
			if(desc.format == Desc.FMT_CONSTANT)
			{
				continue;
			}

			try
			{
				String mname = Character.toUpperCase(desc.name.charAt(0)) + desc.name.substring(1);

				if(hasJavaAssist)
				{
					// Native case - Generate Native Bytecode for ProxyClasses

					//LogFactory.getLog(clazz).info("Generating Proxyclass: " + proxycname);

					String proxycname = classname + "Accessor" + mname;

					CtClass cc = pool.makeClass(proxycname);

					cc.addInterface(pool.get(Accessor.class.getName()));

					String type = desc.format == Desc.FMT_BINARY ? "byte[]" : "String";

					// Create setter
					CtMethod ms = CtNewMethod.make(" public final void setValue(Object bean, Object value) throws Exception {" +
					        "  ((" + classname + ")bean).set" + mname + "((" + type + ")value); " +
					        " } "
					        , cc);
					cc.addMethod(ms);

					// Create getter
					CtMethod mg = CtNewMethod.make(" public final Object getValue(Object bean) throws Exception {" +
					        "  return ((" + classname + ")bean).get" + mname + "(); " +
					        " } "
					        , cc);
					cc.addMethod(mg);

					desc.accessor = (Accessor)cc.toClass().newInstance();

					if(desc.accessor == null)
					{
						throw new Exception("Can't generate GetterSetterclass for [" + mname + "]");
					}
				}
				else
				{
					// Reflection case - Prepare Method details

					//LogFactory.getLog(clazz).info("Attribute access: using Reflection");

					desc.accessor = new AccessorReflection(clazz, mname);
				}
			}
			catch(Exception e)
			{
				LogFactory.getLog(clazz).error("Generating Getter/Setter", e);
			}
		}

		descAfterInterceptor(descs);

		return descs;
	}

	private static volatile HashMap<String, Desc[]> hmDesc = new HashMap<String, Desc[]>();
	private static volatile HashMap<String, HashMap<String, Desc>> hmDescHash = new HashMap<String, HashMap<String, Desc>>();

	public final Desc[] getCachedDesc()
	{
		if(_desc != null)
		{
			return _desc;
		}

		String key = getClass().getName();

		_desc = hmDesc.get(key);

		if(_desc != null)
		{
			return _desc;
		}

		synchronized(hmDesc)
		{
			_desc = hmDesc.get(key); // DOUBLE CHECK LOCK

			if(_desc == null)
			{
				_desc = getOptDesc();
				hmDesc.put(key, _desc);
			}
		}

		return _desc;
	}

	public final HashMap<String, Desc> getCachedDescHash()
	{
		if(_hsdesc != null)
		{
			return _hsdesc;
		}

		String key = getClass().getName();

		_hsdesc = hmDescHash.get(key);

		if(_hsdesc != null)
		{
			return _hsdesc;
		}

		synchronized(hmDescHash)
		{
			_hsdesc = hmDescHash.get(key); // DOUBLE CHECK LOCK

			if(_hsdesc == null)
			{
				Desc[] descs = getCachedDesc();

				_hsdesc = new HashMap<String, Desc>();

				for(Desc desc : descs)
				{
					_hsdesc.put(desc.name, desc);
				}

				hmDescHash.put(key, _hsdesc);
			}
		}

		return _hsdesc;
	}

	public String buildString() throws Exception
	{
		return getGenerator().buildString(this);
	}

	public String buildDebugString()
	{
		return getGenerator().buildDebugString(this);
	}

	/**
	 * for spezialized Generators
	 */
	public String buildString(Generator extgenerator) throws Exception
	{
		return extgenerator.buildString(this);
	}

	public byte[] buildBytes(String charset) throws Exception
	{
		return buildString().getBytes(charset);
	}

	public final Generator getGenerator()
	{
		if(generator == null)
		{
			generator = Generator.getInstance();
		}
		return generator;
	}

	public final Parser getParser()
	{
		if(parser == null)
		{
			parser = Parser.getInstance();
		}
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

	public void afterFill()
	{

	}

	public void beforeStore()
	{

	}

	@Override
	public Object clone()
	{
		try
		{
			Object clone = super.clone();
			return clone;
		}
		catch(CloneNotSupportedException e)
		{
			return null;
		}
	}

	boolean cloned = false;

	public void doclone()
	{
		clone = (Part)clone();
		cloned = true;
	}

	public Part getBackup()
	{
		return clone;
	}

	//	public boolean isDirty() 
	//	{
	//		if(this instanceof Table && ((Table)this).isBinary())
	//			return true;
	//				
	//		if(cloned) {
	//			return !((Part)this.getBackup()).buildString().equals(this.buildString());
	//		}else
	//			return true;
	//	}
	//	
	//	public boolean isDirty(String field) 
	//	{
	//		if(cloned) {
	//			return !((Part)this.getBackup()).getValue(field).equals(this.getValue(field));
	//		}else
	//			return true;
	//	}
}