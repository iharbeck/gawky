package gawky.file;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * prot://user:pass@server:path(#option)
 * 
 * @author harb05
 *
 */
public class URLParser
{

	static String regExpr = "(.*)://(.*):(.*)@(.*):(.*)";

	// Pattern Matching will be case insensitive.
	static Pattern pat = Pattern.compile(regExpr, Pattern.CASE_INSENSITIVE);

	private Matcher matcher;

	private String protocoll;
	private String user;
	private String pass;
	private String server;
	private String port;

	private String serverpath;
	private String option;

	public URLParser(String url) throws Exception
	{
		matcher = pat.matcher(url);

		if(!matcher.find())
		{
			throw new Exception("URL invalid: " + url);
		}

		protocoll = matcher.group(1);
		user = matcher.group(2);
		pass = matcher.group(3);
		server = matcher.group(4);

		String[] s = server.split(":");
		if(s.length > 1)
		{
			server = s[0];
			port = s[1];
		}
		else
		{
			port = null;
		}

		serverpath = matcher.group(5);

		String[] opt = serverpath.split("#");

		if(opt.length > 1)
		{
			serverpath = opt[0];
			option = opt[1];
		}
	}

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public String getPass()
	{
		return pass;
	}

	public void setPass(String pass)
	{
		this.pass = pass;
	}

	public String getServer()
	{
		return server;
	}

	public void setServer(String server)
	{
		this.server = server;
	}

	public String getServerpath()
	{
		return serverpath;
	}

	public void setServerpath(String serverpath)
	{
		this.serverpath = serverpath;
	}

	public String getProtocoll()
	{
		return protocoll;
	}

	public void setProtocoll(String protocoll)
	{
		this.protocoll = protocoll;
	}

	public static void main(String[] args) throws Exception
	{
		URLParser url = new URLParser("ftp://ingo:geheim@server:999:/path/to/file#option");
		System.out.println(url.getProtocoll());
		System.out.println(url.getUser());
		System.out.println(url.getPass());
		System.out.println(url.getServer());
		System.out.println(url.getPort());
		System.out.println(url.getServerpath());
		System.out.println(url.getOption());
	}

	public String getOption()
	{
		return option;
	}

	public void setOption(String option)
	{
		this.option = option;
	}

	public String getPort()
	{
		return port;
	}

	public void setPort(String port)
	{
		this.port = port;
	}
}
