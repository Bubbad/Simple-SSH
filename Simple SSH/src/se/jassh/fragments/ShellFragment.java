package se.jassh.fragments;



import com.jcraft.jsch.JSchException;

import se.jassh.R;
import se.jassh.SSH.SSHClient;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class ShellFragment extends Fragment {
	private SSHClient sshc;

	private TextView readText;
	private EditText sendText;
	private ScrollView scroll;

	private View view;
	private ActionBarActivity activity;
	
	private boolean readData;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStop()
	{
		super.onStop();
		if(sshc != null)
		{
			Log.d("CLIENT - ShellFragment.onStop()", "Stopping SSH connection");
			readData = false;
			sshc.stop();
		}
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

		activity = (ActionBarActivity)getActivity();
		view = inflater.inflate(R.layout.fragment_shell, container, false);
		
		readText = (TextView)view.findViewById(R.id.read_message_text);
		sendText = (EditText)view.findViewById(R.id.send_message_text);
		scroll = (ScrollView)view.findViewById(R.id.read_message_scroll);

		Button b1 = (Button)view.findViewById(R.id.send_message_button);
		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				sendCommand(v);
			}
		});
		Button b2 = (Button)view.findViewById(R.id.send_message_button2);
		b2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				sendCommand2(v);
			}
		});

		Bundle bundle = this.getArguments();
		final String username = bundle.getString("username");
		final String password = bundle.getString("password");
		final String hostname = bundle.getString("hostname");
		final int port = Integer.parseInt(bundle.getString("port"));
		
		activity.getSupportActionBar().setTitle(username + "@" + hostname);
		
		//Inits SSH thread and connections
		Log.d("JASSH - ShellFragment.onCreateView()", "Starting SSH");
		initSSH(username, password, hostname, port);
		
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu; this adds items to the action bar if it is present.
		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}



	public void sendCommand(View view)
	{
		String msg = sendText.getText().toString();
		sendText.setText("");
		sshc.sendCommand(msg + "\n");
	}

	public void sendCommand2(View view)
	{
		sshc.sendCommand2();
	}

	private void initSSH(String username, String password, String hostname, int port) {
		try{
			
			readData = true;
			sshc = new SSHClient(username,password,hostname,port);
			new Thread(){
				public void run()
				{
					while(readData)
					{
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						final String msg = sshc.readCommand();
						if(msg != null)
						{
							activity.runOnUiThread(new Runnable()
							{
								public void run()
								{
									readText.append(msg);
									scroll.post(new Runnable()
									{
										@Override
										public void run() 
										{
											scroll.fullScroll(View.FOCUS_DOWN);
										}
									});
								}
							});
						}
					}
				}
			}.start();
		}
		catch(JSchException e)
		{
			Log.e("TESTING", "Couldn't connect to server. Destroying shell activity");

			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setMessage("Couldn't connect to server. Check input or try again later.")
			.setCancelable(false)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					ConnectFragment fragment = new ConnectFragment();
					FragmentManager fragmentManager = activity.getSupportFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();	
				}
			});
			AlertDialog alert = builder.create();
			alert.show();

		}

	}
}
