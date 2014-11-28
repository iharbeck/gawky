package gawky.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class Scp implements URLInterface
{
	private static Log log = LogFactory.getLog(Scp.class);

	// 		ScpHandler.send("scp://ggcrm01:_@debmu464.server.arvato-systems.de:~", "c:/damdam/in*.txt");

	public static void main__(String[] args) throws Exception
	{

		Scp scp = new Scp();
		scp.send("scp://user:pass@127.0.0.1:~", "c:/damdam/in*.txt");

	}

	@Override
	public void send(String url, String sourcepath) throws Exception
	{
		URLParser uparser = new URLParser(url);

		Scp.copytohost(uparser.getServer(), uparser.getUser(), uparser.getPass(), sourcepath, uparser.getServerpath());
	}

	@Override
	public String[] retrieve(String url, String targetpath) throws Exception
	{
		return retrieve(url, targetpath, false);
	}

	@Override
	public String[] retrieve(String url, String targetpath, boolean simulate) throws Exception
	{
		URLParser uparser = new URLParser(url);

		return Scp.copyfromhost(uparser.getServer(), uparser.getUser(), uparser.getPass(), targetpath, uparser.getServerpath());
	}

	public static void mkdirhost(String url) throws Exception
	{
		URLParser uparser = new URLParser(url);

		Scp.execute(uparser.getServer(), uparser.getUser(), uparser.getPass(), "mkdir -p " + uparser.getServerpath());
	}

	static Thread thread;

	public static void execute(String host, String user, String pass, String command) throws Exception
	{
		JSch jsch = new JSch();

		// execute the command
		Session session = jsch.getSession(user, host, 22);

		// username and password will be given via UserInfo interface.
		UserInfo ui = new StaticUserInfo(pass);
		session.setUserInfo(ui);

		// disable Hostkey checking 
		Hashtable<String, String> config = new Hashtable<String, String>();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);

		session.connect();

		final ChannelExec channel = (ChannelExec)session.openChannel("exec");
		channel.setCommand(command);
		channel.setOutputStream(System.out);
		channel.setExtOutputStream(System.out);
		channel.connect();

		// wait for it to finish
		thread =
		        new Thread()
		        {
			        @Override
			        public void run()
			        {
				        while(!channel.isEOF())
				        {
					        if(thread == null)
					        {
						        return;
					        }
					        try
					        {
						        sleep(500);
					        }
					        catch(Exception e)
					        {
						        // ignored
					        }
				        }
			        }
		        };

		thread.start();
		//thread.join(6000);

		if(thread.isAlive())
		{
			// ran out of time
			thread = null;
		}
		else
		{
			// completed successfully
		}
	}

	public static void copytohost(String host, String user, String pass, String lfile, String rfile) throws Exception
	{
		FileInputStream fis = null;
		try
		{
			JSch jsch = new JSch();

			Session session = jsch.getSession(user, host, 22);

			// username and password will be given via UserInfo interface.
			UserInfo ui = new StaticUserInfo(pass);
			session.setUserInfo(ui);

			// disable Hostkey checking 
			Hashtable<String, String> config = new Hashtable<String, String>();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			session.connect();

			boolean tofolder = rfile.endsWith("/") || rfile.endsWith("~");

			rfile = rfile.replaceFirst("~", "");

			ArrayList<String> list = Tool.getFiles(lfile);
			Iterator<String> it = list.iterator();

			while(it.hasNext())
			{
				String lfilepath = it.next();

				// exec 'scp -t rfile' remotely
				String command = "scp -p -t " + (tofolder ? rfile + Tool.getFilename(lfilepath) : rfile);
				Channel channel = session.openChannel("exec");
				((ChannelExec)channel).setCommand(command);

				// get I/O streams for remote scp
				OutputStream out = channel.getOutputStream();
				InputStream in = channel.getInputStream();

				channel.connect();

				if(checkAck(in) != 0)
				{
					return;
				}

				// send "C0644 filesize filename", where filename should not include
				// '/'
				long filesize = (new File(lfilepath)).length();
				command = "C0644 " + filesize + " ";
				if(lfilepath.lastIndexOf('/') > 0)
				{
					command += lfilepath.substring(lfilepath.lastIndexOf('/') + 1);
				}
				else
				{
					command += lfilepath;
				}
				command += "\n";
				out.write(command.getBytes());
				out.flush();
				if(checkAck(in) != 0)
				{
					return;
				}

				// send a content of lfile
				fis = new FileInputStream(lfilepath);
				byte[] buf = new byte[1024];
				while(true)
				{
					int len = fis.read(buf, 0, buf.length);
					if(len <= 0)
					{
						break;
					}
					out.write(buf, 0, len); // out.flush();
				}
				fis.close();
				fis = null;

				// send '\0'
				buf[0] = 0;
				out.write(buf, 0, 1);
				out.flush();
				if(checkAck(in) != 0)
				{
					return;
				}

				out.close();

				channel.disconnect();
			}

			session.disconnect();

			return;
		}
		finally
		{
			try
			{
				if(fis != null)
				{
					fis.close();
				}
			}
			catch(Exception ee)
			{
			}
		}
	}

	public static String[] copyfromhost(String host, String user, String pass, String lfile, String rfile) throws Exception
	{
		ArrayList<String> files = new ArrayList<String>();

		FileOutputStream fos = null;

		try
		{
			String prefix = null;
			if(new File(lfile).isDirectory())
			{
				prefix = lfile + File.separator;
			}

			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, 22);

			// username and password will be given via UserInfo interface.
			UserInfo ui = new StaticUserInfo(pass);
			session.setUserInfo(ui);
			session.connect();

			// exec 'scp -f rfile' remotely
			String command = "scp -f " + rfile;
			Channel channel = session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);

			// get I/O streams for remote scp
			OutputStream out = channel.getOutputStream();
			InputStream in = channel.getInputStream();

			channel.connect();

			byte[] buf = new byte[1024];

			// send '\0'
			buf[0] = 0;
			out.write(buf, 0, 1);
			out.flush();

			while(true)
			{
				int c = checkAck(in);
				if(c != 'C')
				{
					break;
				}

				// read '0644 '
				in.read(buf, 0, 5);

				long filesize = 0L;
				while(true)
				{
					if(in.read(buf, 0, 1) < 0)
					{
						// error
						break;
					}
					if(buf[0] == ' ')
					{
						break;
					}
					filesize = filesize * 10L + buf[0] - '0';
				}

				String file = null;
				for(int i = 0;; i++)
				{
					in.read(buf, i, 1);
					if(buf[i] == (byte)0x0a)
					{
						file = new String(buf, 0, i);
						break;
					}
				}

				// send '\0'
				buf[0] = 0;
				out.write(buf, 0, 1);
				out.flush();

				// directory or individual file
				// read a content of lfile
				String filename = prefix == null ? lfile : prefix + file;

				files.add(filename);

				fos = new FileOutputStream(filename);

				int foo;
				while(true)
				{
					if(buf.length < filesize)
					{
						foo = buf.length;
					}
					else
					{
						foo = (int)filesize;
					}
					foo = in.read(buf, 0, foo);
					if(foo < 0)
					{
						// error
						break;
					}
					fos.write(buf, 0, foo);
					filesize -= foo;
					if(filesize == 0L)
					{
						break;
					}
				}
				fos.close();
				fos = null;

				if(checkAck(in) != 0)
				{
					return files.toArray(new String[0]);
				}

				// send '\0'
				buf[0] = 0;
				out.write(buf, 0, 1);
				out.flush();
			}

			session.disconnect();

			return files.toArray(new String[0]);
		}
		finally
		{
			try
			{
				if(fos != null)
				{
					fos.close();
				}
			}
			catch(Exception ee)
			{
			}

		}

	}

	static int checkAck(InputStream in) throws IOException
	{
		int b = in.read();
		// b may be 0 for success,
		// 1 for error,
		// 2 for fatal error,
		// -1
		if(b == 0)
		{
			return b;
		}
		if(b == -1)
		{
			return b;
		}

		if(b == 1 || b == 2)
		{
			StringBuilder sb = new StringBuilder();
			int c;
			do
			{
				c = in.read();
				sb.append((char)c);
			}
			while(c != '\n');
			if(b == 1)
			{ // error
				log.error(sb.toString());
			}
			if(b == 2)
			{ // fatal error
				log.error(sb.toString());
			}
		}
		return b;
	}

	public static class StaticUserInfo implements UserInfo, UIKeyboardInteractive
	{

		@Override
		public String[] promptKeyboardInteractive(String destination,
		        String name, String instruction, String[] prompt, boolean[] echo)
		{
			return password.split("\n");
		}

		String password;

		public StaticUserInfo(String password)
		{
			this.password = password;
		}

		@Override
		public String getPassphrase()
		{
			return password;
		}

		@Override
		public String getPassword()
		{
			return password;
		}

		@Override
		public boolean promptPassphrase(String arg0)
		{
			return true;
		}

		@Override
		public boolean promptPassword(String arg0)
		{
			return true;
		}

		@Override
		public boolean promptYesNo(String arg0)
		{
			return true; // accept all
		}

		@Override
		public void showMessage(String arg0)
		{
		}
	}
}