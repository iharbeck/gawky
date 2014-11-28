package gawky.message.part;

import java.lang.reflect.Method;

public class AccessorReflection implements Accessor
{
	Method smethod;
	Method gmethod;

	public AccessorReflection(Class<? extends Part> clazz, String mname) throws Exception
	{
		smethod = clazz.getMethod("set" + mname, new Class[] { String.class });
		gmethod = clazz.getMethod("get" + mname, null);

		if(smethod == null)
		{
			throw new Exception("Missing Setter for [" + mname + "]");
		}
		if(gmethod == null)
		{
			throw new Exception("Missing Getter for [" + mname + "]");
		}
	}

	@Override
	public final void setValue(Object bean, Object value) throws Exception
	{
		smethod.invoke(bean, new Object[] { value });
	}

	@Override
	public final Object getValue(Object bean) throws Exception
	{
		return gmethod.invoke(bean, (Object[])null);
	}
}
