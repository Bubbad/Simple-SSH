package se.jassh.hosts;

public class HostItem {

	private String name;
	private String username;
	private String password;
	private String hostname;
	private String keypath;
	private int port;
	private boolean expanded;



	public HostItem(String name, String username, String password, String hostname, int port, String keypath) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.hostname = hostname;
		this.port = port;
		expanded = false;
		this.keypath = keypath;
	}
	public String getKeypath() {
		return keypath;
	}


	public void setKeypath(String keypath) {
		this.keypath = keypath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + port;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HostItem other = (HostItem) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (port != other.port)
			return false;
		return true;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isExpanded() {
		return expanded;
	}


	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
