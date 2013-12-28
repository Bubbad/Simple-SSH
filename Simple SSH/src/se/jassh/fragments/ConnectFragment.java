package se.jassh.fragments;

import se.jassh.R;
import se.jassh.SSH.SSHClient;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ConnectFragment extends Fragment{

	private EditText username;
	private EditText password;
	private EditText hostname;
	private EditText port;

	private View view;
	private ActionBarActivity activity;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		activity = (ActionBarActivity) getActivity();
		activity.getSupportActionBar().setTitle(R.string.title_connection_fragment);
		view = inflater.inflate(R.layout.fragment_connection, container, false);

		//Saves input fields
		username = (EditText)view.findViewById(R.id.enter_username_id);
		password = (EditText)view.findViewById(R.id.enter_password_id);
		hostname = (EditText)view.findViewById(R.id.enter_hostname_id);
		port = (EditText)view.findViewById(R.id.enter_port_id);

		Button button = (Button)view.findViewById(R.id.connect_server_button);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String user = username.getText().toString();
				String pass = password.getText().toString();
				String host = hostname.getText().toString();
				String port2 = port.getText().toString();
				connect(user,pass,host,port2, activity);
			}
		});

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu; this adds items to the action bar if it is present.
		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	public static void connect(String username, String password, String hostname, String port, ActionBarActivity activity)
	{
		boolean approvedData = SSHClient.check_connection_input(username,password,hostname,port);

		if(approvedData)
		{
			ShellFragment fragment = new ShellFragment();
			Bundle bundle = new Bundle();
			bundle.putString("username", username);
			bundle.putString("password", password);
			bundle.putString("hostname", hostname);
			bundle.putString("port", port);
			fragment.setArguments(bundle);

			
			FragmentManager fragmentManager = activity.getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();	
		}
		else
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setMessage("Your data seems incorrect. Please correct errors.")
			.setCancelable(false)
			.setPositiveButton("OK", null);
			AlertDialog alert = builder.create();
			alert.show();
		}
	}
}




