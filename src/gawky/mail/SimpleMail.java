package gawky.mail;

import gawky.global.Constant;
import gawky.global.Option;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import javax.mail.internet.InternetAddress;

/**
 * @author Ingo Harbeck
 * 
 */
public class SimpleMail extends Mail
{
	private static String DEFAULT_FROM = "mail.from";
	private static String DEFAULT_FROMALIAS = "mail.fromalias";
	private static String DEFAULT_SUBJECT = "mail.subject";

	String def_from = Option.getProperty(DEFAULT_FROM, "");
	String def_fromalias = Option.getProperty(DEFAULT_FROMALIAS, "");

	boolean html = true;

	String subject = Option.getProperty(DEFAULT_SUBJECT, "");
	String body = "";
	String altbody = "";

	private boolean dozip = false;
	private InputStream stream;
	private String attachName;

	private Hashtable<String, String> cids = new Hashtable<String, String>();

	InternetAddress from;
	private ArrayList<InternetAddress> array_to = new ArrayList<InternetAddress>();
	private ArrayList<InternetAddress> array_reply = new ArrayList<InternetAddress>();

	private Map<String, String> parameter = new Hashtable<String, String>();

	String bounce = "";

	public SimpleMail setFrom(String from) throws Exception
	{
		return setFrom(from, from);
	}

	public SimpleMail setFrom(String from, String alias) throws Exception
	{
		this.from = new InternetAddress(from, alias);
		return this;
	}

	public SimpleMail addTo(String to) throws Exception
	{
		String[] tos = to.split("\\s*;\\s*");

		for(String t : tos)
			addTo(t, t);

		return this;
	}

	public SimpleMail addTo(String to, String alias) throws Exception
	{

		String[] tos = to.split("\\s*;\\s*");
		String[] as = alias.split("\\s*;\\s*");

		for(int i = 0; i < tos.length; i++)
		{
			array_to.add(new InternetAddress(tos[i], as[i]));
		}

		return this;
	}

	public SimpleMail addReplyTo(String replyTO) throws Exception
	{
		addReplyTo(replyTO, replyTO);

		return this;
	}

	public SimpleMail addReplyTo(String replyTO, String alias) throws Exception
	{
		array_reply.add(new InternetAddress(replyTO, alias, Constant.ENCODE_UTF8));

		return this;
	}

	public SimpleMail addAttachment(String path, String name, boolean zip) throws Exception
	{
		stream = new FileInputStream(path);
		attachName = name;
		dozip = zip;

		return this;
	}

	public SimpleMail addImage(String path, String cid)
	{
		cids.put(cid, path);

		return this;
	}

	public SimpleMail addParameter(String key, String value)
	{
		parameter.put(key, value);

		return this;
	}

	public String getAltbody()
	{
		return altbody;
	}

	public SimpleMail setAltbody(String altbody)
	{
		this.altbody = altbody;

		return this;
	}

	public String getBody()
	{
		return body;
	}

	public SimpleMail setBody(String body)
	{
		this.body = body;

		return this;
	}

	public boolean isHtml()
	{
		return html;
	}

	public SimpleMail setHtml(boolean html)
	{
		this.html = html;

		return this;
	}

	public String getSubject()
	{
		return subject;
	}

	public SimpleMail setSubject(String subject)
	{
		this.subject = subject;

		return this;
	}

	public boolean isDozip()
	{
		return dozip;
	}

	public SimpleMail setDozip(boolean dozip)
	{
		this.dozip = dozip;

		return this;
	}

	public Map<String, String> getParameter()
	{
		return parameter;
	}

	public InputStream getStream()
	{
		return stream;
	}

	public SimpleMail setStream(InputStream stream, String attachname)
	{
		this.stream = stream;
		this.attachName = attachname;

		return this;
	}

	public String getAttachName()
	{
		return attachName;
	}

	public Hashtable<String, String> getCids()
	{
		return cids;
	}

	public SimpleMail setCids(Hashtable<String, String> cids)
	{
		this.cids = cids;

		return this;
	}

	public String getBounce()
	{
		return bounce;
	}

	public SimpleMail setBounce(String bounce)
	{
		this.bounce = bounce;

		return this;
	}

	public ArrayList<InternetAddress> getArray_to()
	{
		return array_to;
	}

	public SimpleMail setArray_to(ArrayList<InternetAddress> arrayTo)
	{
		array_to = arrayTo;

		return this;
	}

	public ArrayList<InternetAddress> getArray_reply()
	{
		return array_reply;
	}

	public SimpleMail setArray_reply(ArrayList<InternetAddress> arrayReply)
	{
		array_reply = arrayReply;

		return this;
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

	public SimpleMail setFrom(InternetAddress from)
	{
		this.from = from;

		return this;
	}

	public int send() throws Exception
	{
		SimpleMailSender sender = new SimpleMailSender();
		return sender.send(this);
	}

	public SimpleMail setParameter(Map<String, String> parameter)
	{
		this.parameter = parameter;

		return this;
	}
}
