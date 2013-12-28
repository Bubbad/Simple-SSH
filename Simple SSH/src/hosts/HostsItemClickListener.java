package hosts;

import java.util.ArrayList;

import se.jassh.R;
import se.jassh.fragments.ShellFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class HostsItemClickListener implements ListView.OnItemClickListener{

	private ActionBarActivity activity;
	private ArrayList<HostItem> hosts;
	
	
	public HostsItemClickListener(ActionBarActivity activity, ArrayList<HostItem> hosts){
		this.activity = activity;
		this.hosts = hosts;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		hosts.get(position).setExpanded(true);
		Log.d("test", "haha");
		
		/*
		String user = hosts.get(position).getUsername();
		String pass = hosts.get(position).getPassword();
		String host = hosts.get(position).getHostname();
		String port = hosts.get(position).getPort() + "";
		
		
		ShellFragment fragment = new ShellFragment();
		Bundle bundle = new Bundle();
		bundle.putString("username", user);
		bundle.putString("password", pass);
		bundle.putString("hostname", host);
		bundle.putString("port", port);
		fragment.setArguments(bundle);

		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();	*/
	}

}
