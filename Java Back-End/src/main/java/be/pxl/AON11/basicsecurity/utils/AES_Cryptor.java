package be.pxl.AON11.basicsecurity.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public abstract class AES_Cryptor {

	public static String encrypt(String message, String pathToKey, String pukey) {
		try {
			String key = createAESKey();
            encryptKeyAndWrite(key, pathToKey, pukey);
			IvParameterSpec iv = new IvParameterSpec(key.substring(16, 32).getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.substring(0, 16).getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(message.getBytes());
			return DatatypeConverter.printBase64Binary(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private static String createAESKey() {
		try {
			return RandomStringGenerator.generateRandomString(32);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void encryptKeyAndWrite(String key, String pathToKey, String pukey) {
		try (PrintWriter writer = new PrintWriter(pathToKey)) {
			byte[] keyEncrypted = RSA_Cryptor.encryptPublic(pukey, key.getBytes());
			for (int i = 0; i < keyEncrypted.length - 1; i++) {
				writer.println(keyEncrypted[i]);
			}
			writer.print(keyEncrypted[keyEncrypted.length - 1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String decrypt(String encrypted, String pathToKey, String prkey) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(pathToKey)));
			byte[] key = new byte[128];
			for (int i = 0; i < key.length; i++) {
				key[i] = Byte.valueOf(br.readLine());
			}
			br.close();
			byte[] echtekey = RSA_Cryptor.decryptPrivate(prkey, key);
			String keyString = new String(echtekey);
			IvParameterSpec iv = new IvParameterSpec(keyString.substring(16, 32).getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(keyString.substring(0, 16).getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(DatatypeConverter.parseBase64Binary(encrypted));

			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
