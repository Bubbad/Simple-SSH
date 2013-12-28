package se.jassh.navigation;


public class NavigationItem {
	private String title;
	private int icon;
	
	public NavigationItem(String title, int icon)
	{
		this.title = title;
		this.icon = icon;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public int getIcon()
	{
		return icon;
	}
}
