package se.jassh.fragments;

import hosts.HostItem;
import io.IOHandler;
import se.jassh.R;
import se.jassh.SSH.SSHClient;
import android.app.AlertDialog;
import android.os.Bundle;
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

public class AddHostFragment extends Fragment{
	
	private EditText hostname;
	private EditText username;
	private EditText password;
	private EditText hostadress;
	private EditText port;

	private View view;
	private ActionBarActivity activity;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		activity = (ActionBarActivity) getActivity();
		view = inflater.inflate(R.layout.fragment_add_host, container, false);

		//Saves input fields
		hostname = (EditText)view.findViewById(R.id.add_host_enter_servername_id);
		username = (EditText)view.findViewById(R.id.add_host_enter_username_id);
		password = (EditText)view.findViewById(R.id.add_host_enter_password_id);
		hostadress = (EditText)view.findViewById(R.id.add_host_enter_hostadress_id);
		port = (EditText)view.findViewById(R.id.add_host_enter_port_id);

		Button button = (Button)view.findViewById(R.id.add_host_button);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				save(v);
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

	public void save(View view)
	{
		
		String name = hostname.getText().toString();
		String user = username.getText().toString();
		String pass = password.getText().toString();
		String host = hostadress.getText().toString();
		String port2 = port.getText().toString();

		boolean approvedData = SSHClient.check_connection_input(user,pass,host,port2);

		if(approvedData)
		{
			IOHandler.save(new HostItem(name,user,pass,host,Integer.parseInt(port2)), activity.getFilesDir());
			HostsFragment fragment = new HostsFragment();

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
