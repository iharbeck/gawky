package example.ssh;

import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class Exec
{
	public static class MyUserInfo implements UserInfo, UIKeyboardInteractive
	{
		public String password;

		public void setPassword(String password)
		{
			this.password = password;
		}

		@Override
		public String getPassword()
		{
			return password;
		}

		@Override
		public boolean promptYesNo(String str)
		{
			return true;
		}

		@Override
		public String getPassphrase()
		{
			return null;
		}

		@Override
		public boolean promptPassphrase(String message)
		{
			return true;
		}

		@Override
		public boolean promptPassword(String message)
		{
			return true;
		}

		@Override
		public void showMessage(String message)
		{
			System.out.println(message);
		}

		@Override
		public String[] promptKeyboardInteractive(String destination,
		        String name, String instruction, String[] prompt, boolean[] echo)
		{
			return password.split("\n");
		}
	}

	public static void main(String[] arg)
	{
		try
		{
			JSch jsch = new JSch();

			String host = "debmu854.server.arvato-systems.de";
			String user = "harb05";

			String passold = "Sommer3036";
			String passnew = "Sommer3037";

			MyUserInfo ui = new MyUserInfo();
			ui.setPassword(passold);

			Session session = jsch.getSession(user, host, 22);

			// username and password for SSH will be given via UserInfo interface.

			session.setUserInfo(ui);
			session.connect();

			exec(session, new String[] { "passwd", passold, passnew, passnew });

			session.disconnect();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	static int exec(Session session, String[] command) throws Exception
	{
		int ret = -1;

		ChannelExec channel = (ChannelExec)session.openChannel("exec");
		channel.setCommand(command[0]);

		channel.setInputStream(null);

		channel.setErrStream(System.err);

		InputStream in = channel.getInputStream();
		OutputStream out = channel.getOutputStream();

		channel.connect();

		for(int i = 1; i < command.length; i++)
		{
			out.write((command[i] + "\n").getBytes());
			out.flush();
		}

		byte[] tmp = new byte[1024];

		while(true)
		{
			while(in.available() > 0)
			{
				int i = in.read(tmp, 0, 1024);
				if(i < 0)
				{
					break;
				}
				System.out.print(new String(tmp, 0, i));
			}
			if(channel.isClosed())
			{
				ret = channel.getExitStatus();
				System.out.println("exit-status: " + ret);
				break;
			}
			try
			{
				Thread.sleep(1000);
			}
			catch(Exception ee)
			{
			}
		}

		channel.disconnect();
		return ret;
	}

}
