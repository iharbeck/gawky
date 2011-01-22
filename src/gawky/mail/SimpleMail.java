package gawky.mail;

import gawky.global.Constant;
import gawky.global.Option;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.mail.internet.InternetAddress;

/**
 * @author Ingo Harbeck
 *  
 */
public class SimpleMail extends Mail
{
    private static String DEFAULT_FROM      = "mail.from";
    private static String DEFAULT_FROMALIAS = "mail.fromalias";
    private static String DEFAULT_SUBJECT   = "mail.subject";
 
    String def_from       = Option.getProperty(DEFAULT_FROM, "");
    String def_fromalias  = Option.getProperty(DEFAULT_FROMALIAS, "");

    boolean html = true;
    
    String subject  =  Option.getProperty(DEFAULT_SUBJECT, "");
    String body     =  "";
    String altbody  =  "";

    private boolean dozip = false;
    private InputStream stream;
    private String attachName;

    private Hashtable<String, String> cids = new Hashtable<String, String>();

    InternetAddress from; 
    private ArrayList<InternetAddress> array_to = new ArrayList<InternetAddress>();
    private ArrayList<InternetAddress> array_reply = new ArrayList<InternetAddress>();

    private Hashtable<String, String> parameter = new Hashtable<String, String>();

    String bounce = "";
    
    public void setFrom(String from) throws Exception {
        setFrom(from, from);
    }

    public void setFrom(String from, String alias) throws Exception {
    	this.from = new InternetAddress(from, alias);
    }
    
    public void addTo(String to) throws Exception 
    {
    	String[] tos = to.split("\\s*;\\s*");
    	
    	for(String t : tos)
    		addTo(t, t);
    }
        
    public void addTo(String to, String alias) throws Exception {
    	
    	String[] tos = to.split("\\s*;\\s*");
    	String[] as =  alias.split("\\s*;\\s*");
    	
    	for(int i=0; i < tos.length; i++)
    	{
    		array_to.add(new InternetAddress(tos[i], as[i]));
    	}
    }
    
    public void addReplyTo(String replyTO) throws Exception {
    	addReplyTo(replyTO, replyTO);
    }
    
    public void addReplyTo(String replyTO, String alias) throws Exception {
    	array_reply.add(new InternetAddress(replyTO, alias, Constant.ENCODE_UTF8));
    }

    
    public void addAttachment(String path, String name, boolean zip) throws Exception {
    	stream = new FileInputStream(path);
    	attachName = name;
    	dozip = zip;
    }

    public void addImage(String path, String cid) {
    	cids.put(cid, path);
    }
    
    public void addParameter(String key, String value) {
    	parameter.put(key, value);
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

	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}


	public boolean isDozip() {
		return dozip;
	}

	public void setDozip(boolean dozip) {
		this.dozip = dozip;
	}

	public Hashtable<String, String> getParameter() {
		return parameter;
	}

	public void setParameter(Hashtable<String, String> parameter) {
		this.parameter = parameter;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream, String attachname) {
		this.stream = stream;
		this.attachName = attachname;
	}

	public String getAttachName() {
		return attachName;
	}

	public Hashtable<String, String> getCids() {
		return cids;
	}

	public void setCids(Hashtable<String, String> cids) {
		this.cids = cids;
	}

	public String getBounce() {
		return bounce;
	}

	public void setBounce(String bounce) {
		this.bounce = bounce;
	}

	public ArrayList<InternetAddress> getArray_to() {
		return array_to;
	}

	public void setArray_to(ArrayList<InternetAddress> arrayTo) {
		array_to = arrayTo;
	}

	public ArrayList<InternetAddress> getArray_reply() {
		return array_reply;
	}

	public void setArray_reply(ArrayList<InternetAddress> arrayReply) {
		array_reply = arrayReply;
	}

	public InternetAddress getFrom() throws Exception 
	{
		if(from == null)
		{
			if(def_fromalias.length() > 0)
				setFrom(def_from, def_fromalias);
			else
				setFrom(def_from);
		}
		return from;
	}

	public void setFrom(InternetAddress from) {
		this.from = from;
	}
	
	public int send() throws Exception
	{
		SimpleMailSender sender = new SimpleMailSender();
		return sender.send(this);
	}
}

