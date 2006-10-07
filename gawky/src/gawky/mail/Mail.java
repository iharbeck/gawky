package gawky.mail;


import gawky.global.Constant;
import gawky.global.Option;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;


import org.apache.log4j.Logger;



/**
 * @author Ingo Harbeck
 *  
 */
public class Mail 
{
	static final Logger log = Logger.getLogger(Mail.class);

    public static int ERROR_INVALIDADDRESS = 1;
    public static int ERROR_SENDFAILED = 2;
    public static int ERROR_IO = 3;
    public static int STATUS_OK = 0;
    
    private static String charset     = Constant.ENCODE_ISO;
    private static String charsettext = Constant.ENCODE_ISO; 
    
    private static String encoding = null;
    
    // dependency on Option Class. Alias in XML config file:
    // call sendMailGeneric if Option Class is not used
    private static String DEFAULT_USER     = "mail.user";
    private static String DEFAULT_PASSWORD = "mail.password";
    private static String DEFAULT_SERVER   = "mail.server";
    
    public static int sendParameterMail(
            String from,    String fromalias,
            String to,      String toalias,
            String subject, String message,
            Hashtable hash) 
    {
        String username = Option.getProperty(DEFAULT_USER);
        String password = Option.getProperty(DEFAULT_PASSWORD);
        String server   = Option.getProperty(DEFAULT_SERVER);
        
        return sendMailGeneric(username, password, server, 
                		   from, fromalias, to, toalias, subject, message, false, null, null, hash);
    }
    
    public static int sendMailAttachStream(
            String from,    String fromalias,
            String to,      String toalias,
            String subject, String message,
            InputStream stream, String attachName) 
    {
        String username = Option.getProperty(DEFAULT_USER);
        String password = Option.getProperty(DEFAULT_PASSWORD);
        String server   = Option.getProperty(DEFAULT_SERVER);
        
        return sendMailGeneric(username, password, server, 
                           from, fromalias, to, toalias, subject, message, 
                           false, stream, attachName, null);
    }
 
    public static int sendSimpleMail(
            String from, String fromalias,
            String to,   String toalias,
            String subject, String message) 
    {
    	String username = Option.getProperty(DEFAULT_USER);
        String password = Option.getProperty(DEFAULT_PASSWORD);
        String server   = Option.getProperty(DEFAULT_SERVER);
        
    	return sendMailGeneric(username, password, server, 
                           from, fromalias, to, toalias, subject, message, 
                           false, null, null, null);
    }

    public static int sendMailGeneric(
            String username, String password, String host, 
            String from, String fromalias, String to, String toalias,
            String subject, String message,
            boolean html,
            InputStream stream, 
            String attachName,
            Hashtable templateparameter
            ) 
    {
    	// templates in message / subject ersetzen
    	if(templateparameter != null)
        {
         	message = templateReplacer(message, templateparameter);
            subject = templateReplacer(subject, templateparameter);
        }
    	 
        try {
            
            //String message = MimeUtility.encodeText(_message, charset, encoding);
            String nmessage = message;
            String nsubject = MimeUtility.encodeText(subject, charset, encoding);
            String ntoalias = MimeUtility.encodeText(toalias, charset, encoding);
            String nfromalias = MimeUtility.encodeText(fromalias, charset, encoding);
            
            // Get system properties
            java.util.Properties prop = System.getProperties();
    	    prop.put("mail.smtp.host", host);

            // Get session
    	    Session ses1 = Session.getDefaultInstance(prop, null);

    	    // Define message
            MimeMessage msg = new MimeMessage(ses1);
            msg.setHeader("Content-Transfer-Encoding", "8Bit");
            
            //TO
            msg.setFrom(new InternetAddress(from, nfromalias));
    	    msg.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to, ntoalias));
    	    
    	    //FROM
    	    msg.setSubject(nsubject);
    	    
    	    //BODY
    	    MimeBodyPart messageBodyPart = new MimeBodyPart();
    	    messageBodyPart.setHeader("Content-Transfer-Encoding", "8Bit");
            
            // SET HTML MAIL
            if(html)
            	messageBodyPart.setContent(nmessage, "text/html");
            else
            	messageBodyPart.setText(nmessage, charsettext);
            
            Multipart multipart = new MimeMultipart();
    	    multipart.addBodyPart(messageBodyPart);
    	    
    	    //ATTACHEMENT
		    if(stream != null && stream.available() > 0)
		    {
			    messageBodyPart = new MimeBodyPart();
			    
			    //ByteArrayOutputStream bout = new ByteArrayOutputStream();
			    
			    javax.activation.DataSource source =  new ByteArrayDataSource(stream, "plain/text");
			    
			    messageBodyPart.setDataHandler(new DataHandler(source));
			    messageBodyPart.setFileName(attachName);
			    multipart.addBodyPart(messageBodyPart);
		    }
    	    
    	    msg.setContent(multipart);
    	    
    	    // Send Mail
    	    Transport tr = ses1.getTransport("smtp");
    	    tr.connect(host, username, password);
    	    msg.saveChanges();
    	    tr.sendMessage(msg, msg.getAllRecipients());
    	    tr.close();
        
        } catch (IOException e) {
            e.printStackTrace();
            return ERROR_IO;
        } catch (AddressException e) {
            e.printStackTrace();
            return ERROR_INVALIDADDRESS;
        } catch (MessagingException e) {
            e.printStackTrace();
            return ERROR_SENDFAILED;
        }
        
        return STATUS_OK;
    }
    
    /**
     * replace {key} with corresponding HashValue
     * 
     * @param message
     * @param hash
     * @return
     */
    public static final String templateReplacer(String message, Hashtable hash)
    {
        Enumeration en = hash.keys();
        
        while(en.hasMoreElements()) {
            String key = (String) en.nextElement();
            message = message.replaceAll("\\{" + key + "\\}", (String)hash.get(key));
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
                os.write(ch);

            data = os.toByteArray();
        }
        catch(IOException ioexception) { }
    }

    public ByteArrayDataSource(byte data[], String type)
    {
        this.data = data;
        this.type = type;
    }

    public ByteArrayDataSource(String data, String type)
    {
        try {
            this.data = data.getBytes("iso-8859-1");
        }
        catch(UnsupportedEncodingException unsupportedencodingexception) { 
        }
        this.type = type;
    }

    public InputStream getInputStream() throws IOException
    {
        if(data == null)
            throw new IOException("no data");
        else
            return new ByteArrayInputStream(data);
    }

    public OutputStream getOutputStream() throws IOException
    {
        throw new IOException("cannot do this");
    }

    public String getContentType() {
        return type;
    }

    public String getName() {
        return "ByteArrayDataSource";
    }
}