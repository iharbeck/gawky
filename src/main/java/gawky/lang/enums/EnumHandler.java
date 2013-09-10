package gawky.lang.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class EnumHandler 
{
	public static void main(String[] args) throws Exception
    {	
		Map<String, Enum<SampleEnum>> map = buildEnumReverseMap(SampleEnum.class, "val");

		System.out.println(map.get("1"));
		System.out.println(map.get("2"));
		System.out.println(map.get("3"));
		
		for(int i = 0; i < 10; i++)
		{
			System.out.println(lookupEnumReverse(SampleEnum.class, "val", "3"));
		}
		
		System.out.println(SampleEnum.getEnumById("2"));
    }
	
	static HashMap<Class, HashMap<String, Enum>> store = new HashMap<Class, HashMap<String,Enum>>();

	public static <T extends Enum<T>> Map<String, Enum<T>> buildEnumReverseMap(Class<T> en, String attribute) throws Exception
	{
		Map<String, Enum<T>> lookup = new HashMap<String, Enum<T>>();
	
		for (T t : en.getEnumConstants())
		{
            lookup.put(BeanUtils.getProperty(t, attribute) , t);
		}
		
		return lookup;
	}
	
	
	public static <T extends Enum<T>> Enum<T> lookupEnumReverse(Class<T> en, String attribute, String value) throws Exception
	{
		Map<String, Enum> lookup = store.get((Class)en);
	
		if(lookup == null)
		{
    		lookup = (Map)buildEnumReverseMap(en, attribute); 
		}
		
		return lookup.get(value);
	}
}
