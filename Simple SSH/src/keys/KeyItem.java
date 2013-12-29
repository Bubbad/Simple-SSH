package keys;

public class KeyItem {

	
	private String keyName;
	private String keyPath;
	
	public KeyItem(String keyName, String keyPath) {
		super();
		this.keyName = keyName;
		this.keyPath = keyPath;
	}
	
	@Override
	public String toString() {
		return super.toString() + "KeyItem [keyName=" + keyName + ", keyPath=" + keyPath + "]";
	}
	
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getKeyPath() {
		return keyPath;
	}
	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}
}
