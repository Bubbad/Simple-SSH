package se.jassh.fragments;



import hosts.HostItem;
import io.IOHandler;
import se.jassh.R;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class QuickConnectFragment extends Fragment{

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
		view = inflater.inflate(R.layout.activity_quick_connect, container, false);

		//Saves input fields
		username = (EditText)view.findViewById(R.id.enter_username_id);
		password = (EditText)view.findViewById(R.id.enter_password_id);
		hostname = (EditText)view.findViewById(R.id.enter_hostname_id);
		port = (EditText)view.findViewById(R.id.enter_port_id);

		Button button = (Button)view.findViewById(R.id.connect_server_button);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				connect(v);
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

	public void connect(View view)
	{
		
		String user = username.getText().toString();
		String pass = password.getText().toString();
		String host = hostname.getText().toString();
		String port2 = port.getText().toString();

		/*
		String user = "pi";
		String pass = "raspberry";
		String host = "192.168.1.105";
		String port2 = "22";*/


		


		boolean approvedData = checkUserInput(user,pass,host,port2);

		if(approvedData)
		{
			IOHandler.save(new HostItem("this is a name",user,pass,host,Integer.parseInt(port2)), activity.getFilesDir());
			ShellFragment fragment = new ShellFragment();
			Bundle bundle = new Bundle();
			bundle.putString("username", user);
			bundle.putString("password", pass);
			bundle.putString("hostname", host);
			bundle.putString("port", port2);
			fragment.setArguments(bundle);


			FragmentManager fragmentManager = activity.getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();	

		}
		else
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setMessage("Your data seems incorrect. Please correct errors.")
			.setCancelable(false)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {

				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
	}


	private boolean checkUserInput(String username, String password, String hostname, String port)
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




