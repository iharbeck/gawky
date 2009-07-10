package gawky.mail;

import gawky.global.Constant;
import gawky.global.Option;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Ingo Harbeck
 *  
 */
public class SimpleMail extends Mail
{
	private static Log log = LogFactory.getLog(SimpleMail.class);

    private static String DEFAULT_USER     = "mail.user";
    private static String DEFAULT_PASSWORD = "mail.password";
    private static String DEFAULT_SERVER   = "mail.server";

    private static String DEFAULT_FROM      = "mail.from";
    private static String DEFAULT_FROMALIAS = "mail.fromalias";
    private static String DEFAULT_SUBJECT   = "mail.subject";
 
    String server     = Option.getProperty(DEFAULT_SERVER);
    String username   = Option.getProperty(DEFAULT_USER);
    String password   = Option.getProperty(DEFAULT_PASSWORD);

    String from       = Option.getProperty(DEFAULT_FROM, "");
    String fromalias  = Option.getProperty(DEFAULT_FROMALIAS, "");

    boolean html = true;
    
    String subject  =  Option.getProperty(DEFAULT_SUBJECT, "");
    String body     =  "";
    String altbody  =  "";

    private ArrayList reply = new ArrayList();
 
    String to      = "";
    String toalias = "";
    
    public void addTO(String TO, String alias) {
    	to += TO + ";";
    	toalias += alias + ";";
    }
    
    public void addReplyTO(String replyTO, String alias) throws Exception {
    	reply.add(new InternetAddress(replyTO, alias, Constant.ENCODE_ISO));
    }

    private boolean dozip = false;
    private InputStream stream;
    private String attachName;
    
    public void addAttachment(String path, String name, boolean zip) throws Exception {
    	stream = new FileInputStream(path);
    	attachName = name;
    	dozip = zip;
    }
    
    private Hashtable cids = new Hashtable();
    public void addImage(String path, String cid) {
    	cids.put(cid, path);
    }
    
    private Hashtable parameter = new Hashtable();
    
    public void addParameter(String key, String value) {
    	parameter.put(key, value);
    }
    
    public int send() 
    {
    	return Mail.sendMailGeneric(username, password, 
    								server, 
    								from, fromalias, 
    								to,   toalias, reply, 
    								subject, body, html, 
    								stream, attachName, 
    								dozip, parameter, cids);
    }

	public String getAltbody() {
		return altbody;
	}

	public void setAltbody(String altbody) {
		this.altbody = altbody;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFromalias() {
		return fromalias;
	}

	public void setFromalias(String fromalias) {
		this.fromalias = fromalias;
	}

	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getToalias() {
		return toalias;
	}

	public void setToalias(String toalias) {
		this.toalias = toalias;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}

