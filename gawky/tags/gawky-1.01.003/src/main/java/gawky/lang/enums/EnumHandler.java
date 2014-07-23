package gawky.lang.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class EnumHandler 
{
	public static <T extends Enum<T>> Map<String, T> buildEnumReverseMap(Class<T> en, String attribute) throws Exception
	{
		Map<String, T> lookup = new HashMap<String, T>();
	
		for (T t : en.getEnumConstants())
		{
            lookup.put(BeanUtils.getProperty(t, attribute) , t);
		}
		
		return lookup;
	}
	
    final static HashMap<Object, HashMap<String, Enum<?>>> store = new HashMap<Object, HashMap<String,Enum<?>>>();
	
	@SuppressWarnings({ "unchecked" })
    public static <T extends Enum<T>> T lookupEnumReverse(Class<T> en, String attribute, String value) throws Exception
	{
		Map<String, T> lookup = (Map<String, T>)store.get(en);
	
		if(lookup == null)
		{
    		lookup = buildEnumReverseMap(en, attribute); 
		}
		
		return lookup.get(value);
	}
}
