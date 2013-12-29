package keys;

import java.util.ArrayList;

import se.jassh.R;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class KeyAdapter extends BaseAdapter{

	private ArrayList<KeyItem> keys;
	private ActionBarActivity activity;

	private LayoutInflater inflater;

	public KeyAdapter(Context context, int resource, ArrayList<KeyItem> keys) {
		this.keys = keys;
		inflater = LayoutInflater.from(context);
		activity = (ActionBarActivity)context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.key_list_item, null);
		}
		
		TextView key = (TextView)convertView.findViewById(R.id.key_item_text);
		TextView keypath = (TextView)convertView.findViewById(R.id.keypath_item_text);
		
		KeyItem item = keys.get(position);
		
		key.setText("Keyname: " + item.getKeyName());
		keypath.setText("Location: " + item.getKeyPath());
		
		return convertView;
	}
	
	@Override
	public int getCount() {
		return keys.size();
	}

	@Override
	public Object getItem(int position) {
		return keys.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	

}
