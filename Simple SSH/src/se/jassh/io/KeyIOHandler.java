package se.jassh.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import se.jassh.hosts.HostItem;

public class KeyIOHandler {

	public static String filename = "keys.txt";
	public static String separator = ",";
	
	public static void saveKeyFile(File folder, String keyPath)
	{
		File file = new File(folder, filename);

		FileOutputStream writer;
		try {
			writer = new FileOutputStream(file, true);
			byte[] bytes = (keyPath + separator).getBytes();
			
			writer.write(bytes);
			writer.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> loadKeyFile(File folder)
	{
		File file = new File(folder, filename);
		ArrayList<String> keys = new ArrayList<String>();
		
		try {
			FileInputStream in = new FileInputStream(file);
			byte[] bytes = new byte[in.available()];
			in.read(bytes);
			in.close();
			
			String fullString = new String(bytes);
			String[] keyPaths = fullString.split(separator);
			
			for(String path : keyPaths)
			{
				keys.add(path);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return keys;
	}
	
	public static void remove(File folder, String key)
	{
		ArrayList<String> keys = loadKeyFile(folder);
		delete(folder);
		Iterator<String> it = keys.iterator();
		while(it.hasNext())
		{
			String keypath = it.next();
			if(keypath.equals(key))
			{
				it.remove();
			}
			else
			{
				saveKeyFile(folder, keypath);
			}
		}
	}
	
	public static void delete(File folder)
	{
		File file = new File(folder, filename);
		if(file.exists()){
			file.delete();
		}
	}
}
