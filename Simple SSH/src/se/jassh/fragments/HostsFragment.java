package se.jassh.fragments;



import java.util.ArrayList;

import se.jassh.R;
import se.jassh.hosts.HostAdapter;
import se.jassh.hosts.HostItem;
import se.jassh.io.HostIOHandler;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class HostsFragment extends Fragment{

	private View view;
	private ActionBarActivity activity;
	private ListView mListView;
	private HostAdapter adapter;
	private ArrayList<HostItem> hosts;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		activity = (ActionBarActivity) getActivity();
		activity.getSupportActionBar().setTitle(R.string.title_hosts_fragment);

		view = inflater.inflate(R.layout.fragment_hosts, container, false);
		mListView = (ListView) view.findViewById(R.id.hosts_list_id);

		try 
		{
			hosts = HostIOHandler.load(activity.getFilesDir());
			initListView();
		} 
		catch (Exception e) 
		{
			HostIOHandler.delete(activity.getFilesDir());
			Log.e("CLIENT - HostsFragment.onCreateView()", "Error while loading file, deleting it.");
		}
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu,MenuInflater inflater)
	{
		menu.clear();
		inflater.inflate(R.menu.fragment_hosts_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.hosts_menu_add:
			addHost();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void addHost()
	{
		AddHostFragment fragment = new AddHostFragment();

		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
	}
	
	private void initListView() {
		adapter = new HostAdapter(activity, R.id.content_frame, hosts);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				HostItem host = hosts.get(position);
				host.setExpanded(!host.isExpanded());
				
				for(HostItem h : hosts)
				{
					if(!h.equals(host) && h.isExpanded())
					{
						h.setExpanded(false);
					}
				}
				adapter.notifyDataSetChanged();
			}
		});
		
		mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				final HostItem host = hosts.get(position);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setMessage("Are you sure you want to delete this host?" + "\nName: " + host.getName() + ".\n" + "Adress: " + host.getHostname() + ":" + host.getPort())
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						HostIOHandler.remove(activity.getFilesDir(), host);
						activity.runOnUiThread(new Runnable(){
							@Override
							public void run() {
								hosts.remove(position);
								adapter.notifyDataSetChanged();
							}
						});
					}
				})
				.setNegativeButton("CANCEL", null);
				AlertDialog alert = builder.create();
				alert.show();
				return false;
			}
		});
	}
}
