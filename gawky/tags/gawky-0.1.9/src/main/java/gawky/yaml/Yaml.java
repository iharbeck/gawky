package gawky.yaml;

import gawky.file.Locator;

import java.io.FileReader;
import java.io.InputStreamReader;

import org.apache.commons.beanutils.PropertyUtils;

import com.esotericsoftware.yamlbeans.YamlReader;

public class Yaml 
{
	Object object;
	
	public Yaml read(InputStreamReader stream) throws Exception 
	{
		YamlReader reader = new YamlReader(stream);
		object = reader.read();
		
		return this;
	}
	
	public String getString(String path) 
	{
		return (String)getString(path, null);
	}
	
	public String getString(String path, String def) 
	{
		try {
			return (String)PropertyUtils.getProperty(object, path);
		} catch(Exception e) {
			return def;
		}
	}
	
	public Object getObject(String path) 
	{
		return getObject(path, null);
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
		
		Object obj = yaml.getObject("(members)[0]");

		System.out.println("==== BNZ ====");
		System.out.println(yaml.getString("(members)[0](name)"));
		System.out.println(yaml.getString("(members)[0](age)"));
		System.out.println(yaml.getString("(members)[0](address)"));

		System.out.println("");
		System.out.println("==== TEL ====");
		System.out.println(yaml.getString("(members)[0](phone numbers)[0](name)"));
		System.out.println(yaml.getString("(members)[0](phone numbers)[0](number)"));
		
		System.out.println("");
		System.out.println("==== ARY ====");
		System.out.println(obj);

		System.out.println(""); 
		System.out.println("====");
		System.out.println(yaml.getString("(bar)[0]"));
		
		System.out.println(yaml.getObject("(texte)(anrede)(de)"));
		System.out.println(yaml.getObject("(texte)(anrede)(en)"));
		System.out.println(yaml.getObject("(texte)(anschreiben)(de)"));
		System.out.println(yaml.getObject("(texte)(anschreiben)(en)"));
	}
}
