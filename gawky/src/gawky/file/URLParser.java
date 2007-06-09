package gawky.file;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLParser {

	static String regExpr = "(.+)://(.+):(.+)@(.+):(.+)";

	// Pattern Matching will be case insensitive.
	static Pattern pat = Pattern.compile(regExpr,Pattern.CASE_INSENSITIVE);

	Matcher matcher; 

	String protocoll;	
	String user;	
	String pass;
	String server;

	String serverpath;
	
	public URLParser(String url) throws Exception 
	{
		matcher = pat.matcher(url);
		
		if(!matcher.find())
			throw new Exception("URL invalid: " + url);

		protocoll  = matcher.group(1);
		user       = matcher.group(2);
		pass       = matcher.group(3);
		server     = matcher.group(4);

		serverpath = matcher.group(5);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getServerpath() {
		return serverpath;
	}

	public void setServerpath(String serverpath) {
		this.serverpath = serverpath;
	}

	public String getProtocoll() {
		return protocoll;
	}

	public void setProtocoll(String protocoll) {
		this.protocoll = protocoll;
	}
}
