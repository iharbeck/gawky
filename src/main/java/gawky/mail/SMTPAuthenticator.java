package gawky.mail;

import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends javax.mail.Authenticator
{
	String username;
	String password;

	public SMTPAuthenticator(String username, String password)
	{
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(username, password);
	}
}
