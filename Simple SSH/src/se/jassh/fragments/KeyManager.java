package se.jassh.fragments;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import keys.KeyAdapter;
import keys.KeyItem;

import se.jassh.R;
import se.jassh.hosts.HostAdapter;
import se.jassh.hosts.HostItem;
import se.jassh.io.HostIOHandler;
import se.jassh.io.KeyIOHandler;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class KeyManager extends Fragment{

	private View view;
	private ActionBarActivity activity;
	private ListView mListView;
	private KeyAdapter adapter;
	private ArrayList<KeyItem> keys;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_key_manager, container, false);
		activity = (ActionBarActivity) getActivity();
		activity.getSupportActionBar().setTitle(R.string.title_key_manager_fragment);

		keys = KeyIOHandler.loadKeyFile(activity.getFilesDir());

		mListView = (ListView) view.findViewById(R.id.key_list_id);
		adapter = new KeyAdapter(activity, R.id.content_frame, keys);
		mListView.setAdapter(adapter);

		mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				final KeyItem key = keys.get(position);

				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setMessage("Delete reference to key: " + key +  "?")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						KeyIOHandler.remove(activity.getFilesDir(), key);
						keys.remove(position);
						adapter.notifyDataSetChanged();
					}
				})
				.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {	
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();		
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
				return false;
			}
		});


		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
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
			searchKey();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	private void searchKey()
	{
		final EditText input = new EditText(activity);
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("Enter filename of key")
		.setView(input)
		.setPositiveButton("SEARCH", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				String searchString = input.getText().toString();
				ArrayList<File> files = HostIOHandler.search(searchString, Environment.getExternalStorageDirectory().listFiles());
				chooseKey(files);
			}
		})
		.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void chooseKey(ArrayList<File> files) {
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);

		
		final ArrayList<KeyItem> keys = new ArrayList<KeyItem>();

		for(File file : files){
			keys.add(new KeyItem(file.getName(), file.getAbsolutePath()));
		}
		final KeyAdapter adapter = new KeyAdapter(activity, R.id.content_frame, keys);

		builderSingle.setTitle("Select the correct file")
		.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		})
		.setAdapter(adapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				KeyItem key = keys.get(which);
				KeyIOHandler.saveKeyFile(activity.getFilesDir(), key);
				KeyManager.this.keys.add(key);
				adapter.notifyDataSetChanged();
				Log.d("Client - KeyManager.chooseKey()", "Saved keypath: " + key);
			}
		})
		.show();
		
		
		/*final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.select_dialog_singlechoice);
		final ArrayList<KeyItem> keys = new ArrayList<KeyItem>();

		for(File file : files){
			arrayAdapter.add(file.getName());
			keys.add(new KeyItem(file.getName(), file.getAbsolutePath()));
		}

		builderSingle.setTitle("Select the correct file")
		.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		})
		.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				KeyItem key = keys.get(which);
				KeyIOHandler.saveKeyFile(activity.getFilesDir(), key);
				KeyManager.this.keys.add(key);
				adapter.notifyDataSetChanged();
				Log.d("Client - KeyManager.chooseKey()", "Saved keypath: " + key);
			}
		})
		.show();*/
	}
}
