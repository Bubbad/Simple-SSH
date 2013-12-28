package se.jassh.navigation;

import java.util.ArrayList;

import se.jassh.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private ArrayList<NavigationItem> items;
	
	public NavigationAdapter(Context context, int resource, ArrayList<NavigationItem> items) {
		this.items = items;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.drawer_list_item, null);
		}

		TextView text = (TextView)convertView.findViewById(R.id.drawer_item_text);
		ImageView icon = (ImageView)convertView.findViewById(R.id.drawer_item_icon);
		
		text.setText(items.get(position).getTitle());
		icon.setImageResource(items.get(position).getIcon());
		
		
		return convertView;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
