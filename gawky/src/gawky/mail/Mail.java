package gawky.mail;


import gawky.global.Constant;
import gawky.global.Option;
import gawky.jasper.Concat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.GZIPOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


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
    
    private static String charset     = Constant.ENCODE_ISO;
    private static String charsettext = Constant.ENCODE_ISO; 
    private static String encoding    = Constant.ENCODE_ISO;
    
    // dependency on Option Class. Alias in XML config file:
    // call sendMailGeneric if Option Class is not used
    private static String DEFAULT_USER     = "mail.user";
    private static String DEFAULT_PASSWORD = "mail.password";
    private static String DEFAULT_SERVER   = "mail.server";

    private static String DEFAULT_FROM      = "mail.from";
    private static String DEFAULT_FROMALIAS = "mail.fromalias";
    private static String DEFAULT_SUBJECT   = "mail.subject";
 
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
                		   from, fromalias, to, toalias, null, subject, message, false, null, null, false, hash, null);
    }
    
    public static int sendParameterHTMLMail(
            String from,    String fromalias,
            String to,      String toalias,
            String subject, String message,
            Hashtable hash) 
    {
        String username = Option.getProperty(DEFAULT_USER);
        String password = Option.getProperty(DEFAULT_PASSWORD);
        String server   = Option.getProperty(DEFAULT_SERVER);
        
        return sendMailGeneric(username, password, server, 
                		   from, fromalias, to, toalias, null, subject, message, true, null, null, false, hash, null);
    }
    
    public static int sendMailAttachStream(
            String from,    String fromalias,
            String to,      String toalias,
            String subject, String message,
            InputStream stream, String attachName) 
    {
    	return sendMailAttachStream(
                 from,     fromalias,
                 to,       toalias,
                 subject,  message,
                 stream,  attachName, false);
    }
    
    public static int sendMailAttachStream(
            String from,    String fromalias,
            String to,      String toalias,
            String subject, String message,
            InputStream stream, String attachName, boolean dozip) 
    {
        String username = Option.getProperty(DEFAULT_USER);
        String password = Option.getProperty(DEFAULT_PASSWORD);
        String server   = Option.getProperty(DEFAULT_SERVER);
        
        return sendMailGeneric(username, password, server, 
                           from, fromalias, to, toalias, null, subject, message, 
                           false, stream, attachName, dozip, null, null);
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
                           from, fromalias, to, toalias, null, subject, message, 
                           false, null, null, false, null, null);
    }

    
    public static int sendMailGeneric(
            String username, String password, String host, 
            String from, String fromalias, String to, String toalias,
            ArrayList reply,
            String subject, String message,
            boolean html,
            InputStream stream, 
            String attachName,
            boolean dozip,
            Hashtable templateparameter, Hashtable cids
            ) 
    {
    	return sendMailGenericBounce(
                username, password, host, 
                from, fromalias, to, toalias,
                reply, null,
                subject, message,
                html,
                stream, 
                attachName,
                dozip,
                templateparameter, cids
                ); 
    }
    
    public static int sendMailGenericBounce(
            String username, String password, String host, 
            String from, String fromalias, String to, String toalias,
            ArrayList reply, String bounceaddress,
            String subject, String message,
            boolean html,
            InputStream stream, 
            String attachName,
            boolean dozip,
            Hashtable templateparameter, Hashtable cids
            ) 
    {
    	// templates in message / subject ersetzen
    	if(templateparameter != null)
        {
         	message = templateReplacer(message, templateparameter);
            subject = templateReplacer(subject, templateparameter);
        }
    	 
        try {
        	// encoding
        	String nmessage;   
        	if(html)
        		//nmessage   = MimeUtility.encodeText(subject, charset, encoding);
        		//nmessage   = MimeUtility.encodeText(message, charset, "Q");
        		nmessage   = message;
        	else
        		nmessage   = message;
        	
        	//nmessage   = MimeUtility.encodeText(subject, Constant.ENCODE_ISO, Constant.ENCODE_UTF8);
        	
        	//System.setProperty("mail.mime.charset", "UTF-8");
        	
            String nsubject   = MimeUtility.encodeText(subject, "UTF-8", "Q"); //MimeUtility.encodeText(subject, charset, encoding);
            String ntoalias   = toalias; //MimeUtility.encodeText(toalias, charset, encoding);
            String nfromalias = fromalias; //MimeUtility.encodeText(fromalias, charset, encoding);
            
            System.out.println(nsubject);
            
            // Get system properties
            java.util.Properties prop = System.getProperties();
    	    prop.put("mail.smtp.host", host);

    	    if(bounceaddress != null)
    	    	prop.put("mail.smtp.from", bounceaddress);
    	    
            // Get session
    	    Session ses1 = Session.getDefaultInstance(prop, null);

    	    // Define message
            MimeMessage msg = new MimeMessage(ses1);
            msg.setHeader("Content-Transfer-Encoding", "8bit");
           
            

            //msg.setSubject(MimeUtility.encodeText(pd.getSubjec t(), this.charset, subjectEncoding), this.charset);
            // set Type and Charset in Headerfield 'Content-Type'
            msg.setHeader("Content-Type", "text/plain; charset=UTF-8");

            
            
            
            //TO
            msg.setFrom(new InternetAddress(from, nfromalias));
            
            String[] tos = to.split("\\s*;\\s*");
            String[] ntoaliass = ntoalias.split("\\s*;\\s*");
            
            try {
            	for(int i=0; i < tos.length; i++)
            		msg.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(tos[i], ntoaliass[i]));
            } catch(Exception e) {
            	log.error(e);
            }
    	    //FROM
            
            
            msg.setSubject(subject.trim(), "UTF-8"); //nsubject);  // , "UTF-8"; //; //
     	   
            
    	    //msg.setSubject("\u02DA", "UTF-8"); //nsubject);  // , "UTF-8"; //; //
    	    
    	    if(reply != null)
    	    	msg.setReplyTo((Address[]) reply.toArray(new Address[reply.size()]) );
    	    
    	    //BODY
    	    MimeBodyPart messageBodyPart = new MimeBodyPart();
    	    messageBodyPart.setHeader("Content-Transfer-Encoding", "8bit");
            
    	    
            // SET HTML MAIL
            if(html)
            {
            	messageBodyPart.setContent(nmessage, "text/html; charset=utf-8");
            }
            else 
            {
            	messageBodyPart.setContent(nmessage, "text/plain");            	
            	messageBodyPart.setText(nmessage, charsettext);
            }
            
            // Multipart Email
    	    Multipart multipart = new MimeMultipart("related");
            multipart.addBodyPart(messageBodyPart);
    	    
    	    
            /* INCLUDED images for HTML MAIL */
    	    if(cids != null) 
    	    {
        	    // <img src="cid:imagefile" />
    	    	
    	    	String imagepath = Option.getProperty("mail.images");
    	    	
    	    	Enumeration it=cids.keys();
    	    	
    	    	while(it.hasMoreElements())
    	    	{
    	    		String cid = (String) it.nextElement();
	        	    String imagefilename = imagepath +  "/" + cids.get(cid);
	        	     
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

    	     
    	     
    	    //ATTACHEMENT
		    if(stream != null && stream.available() > 0)
		    {
			    messageBodyPart = new MimeBodyPart();
			    
			    //ByteArrayOutputStream bout = new ByteArrayOutputStream();
			    
			    
			    javax.activation.DataSource source = null;
			    
			    if(!dozip)
			    {
			    	// ohne zip
			    	source =  new ByteArrayDataSource(stream, "plain/text");
			    } else {
				    // gzip
				    source = new ByteArrayDataSource(zipStream(stream), "plain/text");
			    }
			    
			    
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
            log.error(e);
            return ERROR_IO;
        } catch (AddressException e) {
        	log.error(e);
            return ERROR_INVALIDADDRESS;
        } catch (MessagingException e) {
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
        while((ch = stream.read()) != -1) {
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
        catch(IOException ioexception) {  }
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