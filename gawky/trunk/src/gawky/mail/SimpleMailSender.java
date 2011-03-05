package gawky.mail;

import gawky.global.Option;

/**
 * @author Ingo Harbeck
 *  
 */
public class SimpleMailSender extends Mail
{
    private static String DEFAULT_USER     = "mail.user";
    private static String DEFAULT_PASSWORD = "mail.password";
    private static String DEFAULT_SERVER   = "mail.server";
    private static String DEFAULT_AUTH     = "mail.auth";

    private String server     = Option.getProperty(DEFAULT_SERVER);
    private String username   = Option.getProperty(DEFAULT_USER);
    private String password   = Option.getProperty(DEFAULT_PASSWORD);
    private String auth  	  = Option.getProperty(DEFAULT_AUTH, "false");
    
    public int send(SimpleMail mail) throws Exception
    {
    	return Mail.sendMailMain(server, username, password, auth, 
								 mail.getFrom(), 
								 mail.getArray_to(), 
								 mail.getArray_reply(), 
								 mail.getBounce(),
								 mail.getSubject(), mail.getBody(), mail.isHtml(), 
								 mail.getStream(), mail.getAttachName(), 
								 mail.isDozip(), mail.getParameter(), mail.getCids());
    }

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
}

