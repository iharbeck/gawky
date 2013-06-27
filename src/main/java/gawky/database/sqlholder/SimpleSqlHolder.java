package gawky.database.sqlholder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * lable:
 * 
 * select * from client where name like ':name'
 * 
 * @author harb05
 * 
 */

public class SimpleSqlHolder
{
	static HashMap<Object, HashMap<String, StringBuffer>> hsTemplates = new HashMap<Object, HashMap<String, StringBuffer>>();

	HashMap<String, StringBuffer> templates;

	HashMap<String, String> map = null;
	
	public void addParameter(String key, String value)
	{
		if(map == null)
			map = new HashMap<String, String>();
		
		map.put(key, value);
	}
	
	public void clearParameter()
	{
		map = null;
	}
	
	public SimpleSqlHolder(Class<?> clazz)
	{
		templates = lookupTemplates(clazz);
	}

	public HashMap<String, StringBuffer> lookupTemplates(Class<?> clazz)
	{
		HashMap<String, StringBuffer> templates = hsTemplates.get(clazz);

		if(templates == null)
		{
			templates = new HashMap<String, StringBuffer>();

			try
			{
				String filename = clazz.getName();
				filename = filename.substring(filename.lastIndexOf('.') + 1);
				System.out.println(filename);
				System.out.println(clazz.getCanonicalName());

				String filepath = clazz.getResource(filename + ".sql").getPath();

				BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "UTF8"));

				StringBuffer buf = null;

				String line = null;
				while((line = is.readLine()) != null)
				{
					String cline = line.trim();
					if(cline.equals("") || cline.startsWith("#") || cline.startsWith("--"))
						continue;

					if(cline.matches("\\w*:"))
					{
						String id = line.replaceAll("[\\s:]", "");
						
						buf = new StringBuffer(5000);
						templates.put(id, buf);
						
						System.out.println(">>" + id);
						continue;
					}
					
					if(buf != null)
						buf.append(line).append("\n");
				}
				is.close();

			}
			catch(Exception e)
			{
			}

			hsTemplates.put(clazz, templates);
		}

		return templates;
	}

	public String getSql(String id) throws Exception
	{
		String content = templates.get(id).toString();

		if(content == null)
			throw new Exception("No SQL template available [" + id + "]");

		if(map != null)
			content = doParameter(content);

		return content;
	}

	static Pattern patternParameter = Pattern.compile(":[a-zA-Z0-9\\._]+");

	public String doParameter(String content)
	{
		StringBuffer buf = new StringBuffer(5000);

		Matcher m = patternParameter.matcher(content);

		int start = 0;
		while(m.find())
		{
			String alias = content.substring(m.start() + 1, m.end());

			buf.append(content.substring(start, m.start()));
			String val = (String)map.get(alias);
		
			// if(val == null)
			// val = "\n--:" + alias + "\n";
			
			if(val != null)
				buf.append(val);

			start = m.end();
		}
		buf.append(content.substring(start));

		return buf.toString();
	}
}
