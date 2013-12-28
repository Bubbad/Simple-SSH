package se.jassh.navigation;

import java.util.ArrayList;

import se.jassh.R;
import se.jassh.fragments.HostsFragment;
import se.jassh.fragments.QuickConnectFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DrawerItemClickListener implements ListView.OnItemClickListener{
	
	private ActionBarActivity activity;
	private ArrayList<NavigationItem> items;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	
	public DrawerItemClickListener(ActionBarActivity activity,DrawerLayout mDrawerLayout, ListView mDrawerList,ArrayList<NavigationItem> items){
		this.activity = activity;
		this.mDrawerLayout = mDrawerLayout;
		this.items = items;
		this.mDrawerList = mDrawerList;
		
		selectItem(0);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		selectItem(position);
	}

	private void selectItem(int position) {
		
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		
		switch(position)
		{
		case 0: //Quick Connect
			QuickConnectFragment quickFragment = new QuickConnectFragment();
			fragmentManager.beginTransaction().replace(R.id.content_frame, quickFragment).commit();
			break;
		case 1: //Hosts
			HostsFragment hostFragment = new HostsFragment();
			fragmentManager.beginTransaction().replace(R.id.content_frame, hostFragment).commit();
			break;
		case 2: //Settings
			break;
		}
		
		activity.setTitle(items.get(position).getTitle());
		mDrawerLayout.closeDrawer(mDrawerList);
		
		
	}

}
