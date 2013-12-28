package hosts;

import java.util.ArrayList;

import se.jassh.R;
import se.jassh.fragments.AddHostFragment;
import se.jassh.fragments.ConnectFragment;
import se.jassh.fragments.ShellFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class HostAdapter extends BaseAdapter{

	private ArrayList<HostItem> hosts;
	private ActionBarActivity activity;

	private LayoutInflater inflater;

	public HostAdapter(Context context, int resource, ArrayList<HostItem> hosts) {
		this.hosts = hosts;
		inflater = LayoutInflater.from(context);
		activity = (ActionBarActivity)context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final HostItem host = hosts.get(position);

		if(host.isExpanded())
		{
			convertView = inflater.inflate(R.layout.hosts_list_item_expanded, null);

			TextView name = (TextView)convertView.findViewById(R.id.hosts_name_expanded);
			TextView hostadress = (TextView)convertView.findViewById(R.id.hosts_hostadress_expanded);
			TextView username = (TextView)convertView.findViewById(R.id.hosts_username_expanded);
			
			Button connectButton = (Button)convertView.findViewById(R.id.connect_host_button);
			connectButton.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					ConnectFragment.connect(host.getUsername(), host.getPassword(), host.getHostname(), host.getPort() + "", activity);		
				}
			});
			
			Button editButton = (Button)convertView.findViewById(R.id.edit_host_button);
			editButton.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					AddHostFragment fragment = new AddHostFragment();
					Bundle bundle = new Bundle();
					bundle.putString("servername", host.getName());
					bundle.putString("username", host.getUsername());
					bundle.putString("hostname", host.getHostname());
					bundle.putString("port", host.getPort() + "");
					fragment.setArguments(bundle);
					
					FragmentManager fragmentManager = activity.getSupportFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();		
				}
			});
			
			name.setText(host.getName());
			hostadress.setText("Host address: " + host.getHostname() + ":" + host.getPort());
			username.setText("Login username: " + host.getUsername());

			return convertView;
		}
		else
		{
			convertView = inflater.inflate(R.layout.hosts_list_item, null);

			TextView text = (TextView)convertView.findViewById(R.id.hosts_item_text);
			text.setText(host.getName());

			return convertView;
		}
	}

	@Override
	public int getCount() {
		return hosts.size();
	}

	@Override
	public Object getItem(int position) {
		return hosts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
