package gawky.mail;

import gawky.global.Constant;
import gawky.global.Option;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.search.AndTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;
import java.io.*;
import java.util.*;
import java.util.zip.GZIPOutputStream;

/**
 * @author Ingo Harbeck
 *  
 */

public class Mail
{
	private static Log log = LogFactory.getLog(Mail.class);

	public static int ERROR_INVALIDADDRESS = 1;
	public static int ERROR_SENDFAILED = 2;
	public static int ERROR_IO = 3;
	public static int STATUS_OK = 0;

	private static String charsettext = Constant.ENCODE_UTF8;

	public static void main(String[] args) throws Exception
	{
		Option.init();
		// Create empty properties
		Properties props = new Properties();

		// Get session
		Session session = Session.getDefaultInstance(props, null);

		String host = Option.getProperty("mail.server");
		String user = Option.getProperty("mail.user");
		String pass = Option.getProperty("mail.password");
		// Get the store
		Store store = session.getStore("imap");
		store.connect(host, user, pass);

		// Get folder
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);

		SearchTerm st = new AndTerm(
		        new SubjectTerm("TICKING"),
		        new FlagTerm(new Flags(Flags.Flag.SEEN), false));

		Message messages[] = folder.search(st);

		//Message message[] = folder.getMessages();

		// message.setFlag(Flags.Flag.SEEN, true); 

		for(Message message : messages)
		{
			MailParter cmessage;

			cmessage = new MailParter(message);

			System.out.println(cmessage.getMessageNumber() + ": " + cmessage.getFrom() + " " + cmessage.getSubject());
			System.out.println(cmessage.getBodytext());
			System.out.println(cmessage.getAttachmentCount());

			for(gawky.mail.Attachment att : cmessage.getAttachments())
			{
				System.out.println(att.getFilename());
			}
		}

		// Close connection 
		folder.close(false);
		store.close();
	}

	public static int sendMailMain(
	        String host, String username, String password, String doauth,
	        InternetAddress from,
	        ArrayList<InternetAddress> to,
	        ArrayList<InternetAddress> reply, String bounceaddress,
	        String subject, String body,
	        boolean html,
			List<InputStream> streams,
	        List<String> attachNames,
	        boolean dozip,
	        Map<String, String> templateparameter, Map<String, String> cids,
	        InternetAddress notify

	        )
	{
		// templates in message / subject ersetzen
		if(templateparameter != null)
		{
			body = templateReplacer(body, templateparameter);
			subject = templateReplacer(subject, templateparameter);
		}

		try
		{
			// encoding
			System.setProperty("mail.mime.charset", charsettext);

			// Get system properties
			java.util.Properties prop = new Properties(); // System.getProperties();
			prop.put("mail.transport.protocol", "smtp");
			prop.put("mail.smtp.host", host);
			prop.put("mail.smtp.auth", doauth);

			if(bounceaddress != null && !bounceaddress.equals(""))
			{
				prop.put("mail.smtp.from", bounceaddress);
			}

			//Auth
			Authenticator auth = new SMTPAuthenticator(username, password);

			// Get session
			Session session = Session.getDefaultInstance(prop, auth);

			// Define message
			MimeMessage message = new MimeMessage(session);
			message.setHeader("Content-Transfer-Encoding", "8bit");

			// set Type and Charset in Headerfield 'Content-Type'
			message.setHeader("Content-Type", "text/plain; charset=UTF-8");

			if(notify != null)
			{
				message.setHeader("Disposition-Notification-To", notify.getAddress());
			}

			//TO
			message.setFrom(from);

			for(InternetAddress addr : to)
			{
				message.addRecipient(javax.mail.Message.RecipientType.TO, addr);
			}

			message.setSubject(subject.trim(), charsettext);

			if(reply != null)
			{
				message.setReplyTo(reply.toArray(new Address[reply.size()]));
			}

			//BODY
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setHeader("Content-Transfer-Encoding", "8bit");

			// SET HTML MAIL
			if(html)
			{
				messageBodyPart.setContent(body, "text/html; charset=utf-8");
			}
			else
			{
				messageBodyPart.setContent(body, "text/plain; charset=utf-8");
				messageBodyPart.setText(body, charsettext);
			}

			// Multipart Email
			Multipart multipart = new MimeMultipart("mixed");
			//Multipart multipart = new MimeMultipart("related");

			multipart.addBodyPart(messageBodyPart);

			/* INCLUDED images for HTML MAIL */
			if(cids != null)
			{
				// <img src="cid:imagefile" />

				String imagepath = Option.getProperty("mail.images");

				for(String cid : cids.keySet())
				{
					String imagefilename = imagepath + "/" + cids.get(cid);

					MimeBodyPart inlineimg = new MimeBodyPart();
					FileDataSource fileds = new FileDataSource(imagefilename);

					inlineimg.setFileName(fileds.getName());
					inlineimg.setText("dd");
					inlineimg.setDataHandler(new DataHandler(fileds));
					inlineimg.setHeader("Content-ID", "<" + cid + ">");
					inlineimg.setDisposition("inline");

					multipart.addBodyPart(inlineimg);
				}
			}

			//ATTACHMENT


			int i = 0;
			for(InputStream stream : streams)
			{
				messageBodyPart = new MimeBodyPart();

				javax.activation.DataSource source = null;

				if(!dozip)
				{
					source = new ByteArrayDataSource(stream, "application/octet-stream");
				}
				else
				{
					source = new ByteArrayDataSource(zipStream(stream), "application/octet-stream");
				}

				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(attachNames.get(i++));
				multipart.addBodyPart(messageBodyPart);
			}

			message.setContent(multipart);

			message.setSentDate(new Date());

			// Send Mail
			/*
			Transport tr = ses1.getTransport("smtp");
			tr.connect(host, username, password);
			msg.saveChanges();
			tr.sendMessage(msg, msg.getAllRecipients());
			tr.close();
			*/
			Transport transport = session.getTransport();

			//MimeMessage message = new MimeMessage(mailSession);
			//message.setContent("This is a test", "text/plain");
			//message.setFrom(new InternetAddress("ingo.harbeck@iharbeck.de"));
			//message.addRecipient(Message.RecipientType.TO,
			//     new InternetAddress("ingo.harbeck@iharbeck.de"));

			transport.connect();
			// transport.sendMessage(msg, msg.getAllRecipients());
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();

		}
		catch(IOException e)
		{
			log.error(e);
			return ERROR_IO;
		}
		catch(AddressException e)
		{
			log.error(e);
			return ERROR_INVALIDADDRESS;
		}
		catch(MessagingException e)
		{
			log.error(e);
			return ERROR_SENDFAILED;
		}

		return STATUS_OK;
	}

	public static byte[] zipStream(InputStream stream) throws IOException
	{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		GZIPOutputStream out = new GZIPOutputStream(bout);

		int ch;
		while((ch = stream.read()) != -1)
		{
			out.write(ch);
		}

		out.finish();
		out.close();

		return bout.toByteArray();
	}

	/**
	 * replace {key} with corresponding HashValue
	 * 
	 * @param message
	 * @param hash
	 * @return
	 */
	public static final String templateReplacer(String message, Map<String, String> hash)
	{
		for(String key : hash.keySet())
		{
			message = message.replaceAll("\\{" + key + "\\}", hash.get(key));
		}

		return message;
	}
}

/**
 * 
 * Datasource Wrapper for attachment handling
 * 
 * @author Ingo Harbeck
 *
 */
class ByteArrayDataSource implements DataSource
{
	private byte data[];
	private String type;

	public ByteArrayDataSource(InputStream is, String type)
	{
		this.type = type;
		try
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			int ch;
			while((ch = is.read()) != -1)
			{
				os.write(ch);
			}

			data = os.toByteArray();
		}
		catch(IOException ioexception)
		{
		}
	}

	public ByteArrayDataSource(byte data[], String type)
	{
		this.data = data;
		this.type = type;
	}

	public ByteArrayDataSource(String data, String type)
	{
		try
		{
			this.data = data.getBytes("iso-8859-1");
		}
		catch(UnsupportedEncodingException unsupportedencodingexception)
		{
		}
		this.type = type;
	}

	@Override
	public InputStream getInputStream() throws IOException
	{
		if(data == null)
		{
			throw new IOException("no data");
		}
		else
		{
			return new ByteArrayInputStream(data);
		}
	}

	@Override
	public OutputStream getOutputStream() throws IOException
	{
		throw new IOException("cannot do this");
	}

	@Override
	public String getContentType()
	{
		return type;
	}

	@Override
	public String getName()
	{
		return "ByteArrayDataSource";
	}
}