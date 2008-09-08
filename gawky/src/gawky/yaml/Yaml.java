package gawky.yaml;

import gawky.file.Locator;

import java.io.FileReader;
import java.io.InputStreamReader;

import net.sourceforge.yamlbeans.YamlReader;

import org.apache.commons.beanutils.PropertyUtils;

public class Yaml 
{
	Object object;
	
	public Yaml read(InputStreamReader stream) throws Exception 
	{
		YamlReader reader = new YamlReader(stream);
		object = reader.read();
		
		return this;
	}
	
	public String getString(String path) throws Exception 
	{
		return (String)PropertyUtils.getProperty(object, path);
	}
	
	public String getString(String path, String def) 
	{
		try {
			return (String)PropertyUtils.getProperty(object, path);
		} catch(Exception e) {
			return def;
		}
	}
	
	public Object getObject(String path) throws Exception 
	{
		return PropertyUtils.getProperty(object, path);
	}
	
	public Object getObject(String path, String def) 
	{
		try {
			return PropertyUtils.getProperty(object, path);
		} catch(Exception e) {
			return def;
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		String file = Locator.findPath("contact.yml", Yaml.class);

		Yaml yaml = new Yaml();
		yaml.read(new FileReader(file));
	
		System.out.println("==== Benutzer ====");
		System.out.println(yaml.getString("(name)"));
		System.out.println(yaml.getString("(age)"));
		System.out.println(yaml.getString("(address)"));

		System.out.println("==== Telefon export ====");
		System.out.println(yaml.getString("(phone numbers)[0](name)"));
		System.out.println(yaml.getString("(phone numbers)[0](number)"));
		
		System.out.println("==== Telefonnummer ====");
		Object obj = yaml.getObject("(phone numbers)");
		System.out.println(obj);

		System.out.println(yaml.getString("(bar)[0]"));
	}
}
