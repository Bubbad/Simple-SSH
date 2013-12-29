package se.jassh.SSH;


import java.io.IOException;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;

import org.fusesource.jansi.AnsiString;

import se.jassh.hosts.HostItem;
import se.jassh.io.KeyIOHandler;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import android.util.Log;

public class SSHClient{

	private JSch jsch = new JSch();
	private Session session;
	private ChannelShell channel;
	private InputStream in;
	private PrintStream writer;

	public SSHClient(HostItem host) throws JSchException
	{
		boolean keyAuth = false;
		try {
			if(host.getKeypath() != null)
			{
				jsch.addIdentity(host.getKeypath(), host.getPassword());
				keyAuth = true;
			}

			session = jsch.getSession(host.getUsername(), host.getHostname(), host.getPort());
			session.setConfig("StrictHostKeyChecking", "no");
			if(!keyAuth){
				session.setPassword(host.getPassword());
			}

			session.connect();
			Log.d("TESTING", "Session started");

			channel = (ChannelShell)session.openChannel("shell");

			writer = new PrintStream(channel.getOutputStream(), true);
			in = channel.getInputStream();

			channel.connect();
			Log.d("TESTING", "Channel established");
		} 
		catch (Exception e) 
		{
			Log.d("CLIENT - SSHClient.SSHClient()", e.getMessage());

			if(e.getMessage().equals("Auth fail"))
			{
				throw new JSchException("Authorization failed. Username or password incorrect.");
			}
			else
			{
				throw new JSchException("Couldn't connect to server. Check input or try again later.");
			}
		}
	}

	public void stop()
	{
		try {
			if(session != null)
			{
				in.close();
				writer.close();
				channel.disconnect();
				session.disconnect();
			}
		} catch (IOException e) {
			Log.e("CLIENT - Error in SSHClient.stop()", e.getMessage());
		}
	}

	public void sendCommand(String cmd)
	{
		try {
			writer.write(cmd.getBytes());
		} catch (IOException e) {
			Log.e("CLIENT - Error in SSHClient.sendCommand()", e.getMessage());
		}
		writer.flush();
		Log.i("CLIENT", "Sending command: " + cmd);
	}

	public void sendCommand2()
	{
		writer.write((byte)3);
		writer.println();
	}

	public String readCommand()
	{
		byte[] tmp = new byte[1024];
		StringBuilder string = new StringBuilder();

		try {
			while(in.available() > 0)
			{
				int i = in.read(tmp,0,1024);
				if(i<0)
				{
					break;
				}
				string.append(new String(tmp, 0, i));
			}
			String str = string.toString();
			if(str.length() != 0)
			{
				str = new AnsiString(str).getPlain().toString(); 

				if(in.available() > 0)
				{
					str = str + "\n";
				}
				Log.d("CLIENT - SSHClient.readCommand()", "Read message: " + str);
				return str;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean check_connection_input(String username, String password, String hostname, String port)
	{
		boolean approved = true;

		try{
			Integer.parseInt(port);
		}
		catch(Exception e)
		{
			approved = false;
		}

		if(username.trim().length() == 0 || password.trim().length() == 0 || hostname.trim().length() == 0 )
		{
			approved = false;
		}
		return approved;
	}

}
