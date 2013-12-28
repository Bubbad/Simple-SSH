package se.jassh.activities;

import java.util.ArrayList;

import se.jassh.R;
import se.jassh.navigation.DrawerItemClickListener;
import se.jassh.navigation.NavigationAdapter;
import se.jassh.navigation.NavigationItem;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity{
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle toggle;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer_layout);


		//Init the drawer
		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);


		ArrayList<NavigationItem> items = getNavItems();
		mDrawerList.setAdapter(new NavigationAdapter(this, R.layout.drawer_list_item, items));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener(this, mDrawerLayout, mDrawerList, items));

		toggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.abc_ic_go, R.string.abc_action_bar_home_description, R.string.abc_action_bar_home_description);
		mDrawerLayout.setDrawerListener(toggle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

	}

	private ArrayList<NavigationItem> getNavItems() {
		ArrayList<NavigationItem> items = new ArrayList<NavigationItem>();

		items.add(new NavigationItem("Quick Connect", R.drawable.ic_action_forward));
		items.add(new NavigationItem("Hosts", R.drawable.ic_action_add_to_queue));
		items.add(new NavigationItem("Key Manager", R.drawable.ic_action_accounts));
		items.add(new NavigationItem("Settings", R.drawable.ic_action_settings));
		items.add(new NavigationItem("About", R.drawable.ic_action_help));

		return items;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//Check if the drawer toggle was pressed
		if (toggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
