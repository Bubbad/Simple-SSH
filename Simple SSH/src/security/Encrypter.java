package security;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encrypter {

	private static final String algorithm = "AES";
	private static final byte[] key = new byte[] { 'p', 'a', 's', 's', 'w', 'o', 'r', 'd', 'p', 'a', 's', 's', 'w', 'o', 'R', 'd' };
	
	public static byte[] generateKey() throws Exception
	{
		KeyGenerator keygen = KeyGenerator.getInstance(algorithm);
		
		//Makes a "almost" random number
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
		sr.setSeed(key);
		
		keygen.init(128, sr);
		
		//Generate key
		SecretKey key = keygen.generateKey();
		return key.getEncoded();
	}
	
	public static byte[] encode(byte[] fileData, boolean last) throws Exception
	{
		SecretKeySpec sks = new SecretKeySpec(key, algorithm);
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, sks);
			
		
		if(last)
		{
			return cipher.doFinal(fileData);	
		}
		else
		{
			return cipher.update(fileData);
		}
	}
	
	public static byte[] decode(byte[] fileData) throws Exception
	{
		SecretKeySpec sks = new SecretKeySpec(key, algorithm);
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, sks);
		
		return cipher.doFinal(fileData);	
	}
}
