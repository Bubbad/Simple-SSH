package se.jassh.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import keys.KeyItem;

public class KeyIOHandler {

	public static String filename = "keys.txt";
	public static String separator = ",";
	public static String breaker = ",,";

	public static void saveKeyFile(File folder, KeyItem key)
	{
		File file = new File(folder, filename);

		FileOutputStream writer;
		try {
			writer = new FileOutputStream(file, true);
			byte[] bytes = (key.getKeyName() + separator + key.getKeyPath() + breaker).getBytes();

			writer.write(bytes);
			writer.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static ArrayList<KeyItem> loadKeyFile(File folder)
	{
		File file = new File(folder, filename);
		ArrayList<KeyItem> keys = new ArrayList<KeyItem>();

		if(file.exists())
		{
			try {
				FileInputStream in = new FileInputStream(file);
				byte[] bytes = new byte[in.available()];
				in.read(bytes);
				in.close();

				String fullString = new String(bytes);
				String[] keystrings = fullString.split(breaker);

				for(String key : keystrings)
				{
					String[] k = key.split(separator);
					keys.add(new KeyItem(k[0], k[1]));
				}
			} catch (Exception e) 
			{
				delete(folder);
				e.printStackTrace();
			}
		}
		return keys;
	}

	public static void remove(File folder, KeyItem key)
	{
		ArrayList<KeyItem> keys = loadKeyFile(folder);
		delete(folder);
		Iterator<KeyItem> it = keys.iterator();
		while(it.hasNext())
		{
			KeyItem k = it.next();
			if(k.getKeyPath().equals(key.getKeyPath()))
			{
				it.remove();
			}
			else
			{
				saveKeyFile(folder, k);
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
