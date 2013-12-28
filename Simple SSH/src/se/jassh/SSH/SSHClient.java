package se.jassh.SSH;



import hosts.HostItem;

import java.io.IOException;

import java.io.InputStream;
import java.io.PrintStream;

import org.fusesource.jansi.AnsiString;

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

	public SSHClient(String username, String password, String hostname, int port) throws JSchException
	{
		try {
			session = jsch.getSession(username, hostname, port);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			

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
			throw new JSchException();
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
