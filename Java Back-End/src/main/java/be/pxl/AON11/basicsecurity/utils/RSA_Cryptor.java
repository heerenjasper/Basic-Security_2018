package be.pxl.AON11.basicsecurity.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;

public abstract class RSA_Cryptor {

	public static void generateKeyPair(String prkeypath, String pukeypath) {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
			final KeyPair keyPair = keyGen.generateKeyPair();
			File privateKeyFile = new File(prkeypath);
			File publicKeyFile = new File(pukeypath);
			createFilesToStorePublicAndPrivateKey(privateKeyFile, publicKeyFile, keyPair);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private static void createFilesToStorePublicAndPrivateKey(File privateKeyFile, File publicKeyFile,
			KeyPair keyPair) {
		if (privateKeyFile.getParentFile() != null) {
			privateKeyFile.getParentFile().mkdirs();
		}
		if (publicKeyFile.getParentFile() != null) {
			publicKeyFile.getParentFile().mkdirs();
		}
		try {
			privateKeyFile.createNewFile();
			publicKeyFile.createNewFile();
			savePrivateKeyInFile(privateKeyFile, keyPair);
			savePublicKeyInFile(publicKeyFile, keyPair);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void savePrivateKeyInFile(File privateKeyFile, KeyPair keyPair) throws IOException {
		try (ObjectOutputStream privateKeyOS = new ObjectOutputStream(new FileOutputStream(privateKeyFile))) {
			privateKeyOS.writeObject(keyPair.getPrivate());
		}
	}

	private static void savePublicKeyInFile(File publicKeyFile, KeyPair key) throws IOException {
		try (ObjectOutputStream publicKeyOS = new ObjectOutputStream(new FileOutputStream(publicKeyFile))) {
			publicKeyOS.writeObject(key.getPublic());
		}
	}

	public static byte[] encryptPublic(String puKeyPath, byte[] message) {
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(puKeyPath))) {
			final PublicKey publicKey = (PublicKey) inputStream.readObject();
			inputStream.close();
			final byte[] cipherText = encryptWithPublicKey(message, publicKey);
			return cipherText;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] encryptWithPublicKey(byte[] text, PublicKey key) {
		byte[] cipherText = null;
		try {
			final Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherText = cipher.doFinal(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipherText;
	}

	public static byte[] decryptPrivate(String prKeyPath, byte[] encrypted) {
		try {
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(prKeyPath));
			final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
			inputStream.close();
			return decryptWithPrivateKey(encrypted, privateKey);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] decryptWithPrivateKey(byte[] text, PrivateKey key) {
		byte[] decryptedText = null;
		try {
			final Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, key);
			decryptedText = cipher.doFinal(text);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return decryptedText;
	}

	public static byte[] encryptPrivate(String prKeyPath, byte[] message) {
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(prKeyPath))) {
			final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
			final byte[] cipherText = encryptWithPrivateKey(message, privateKey);
			return cipherText;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] encryptWithPrivateKey(byte[] text, PrivateKey key) {
		byte[] cipherText = null;
		try {
			final Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherText = cipher.doFinal(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipherText;
	}

	public static byte[] decryptPublic(String puKeyPath, byte[] encrypted) {
		try {
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(puKeyPath));
			final PublicKey publicKey = (PublicKey) inputStream.readObject();
			inputStream.close();
			return decryptWithPublicKey(encrypted, publicKey);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] decryptWithPublicKey(byte[] text, PublicKey key) {
		byte[] decryptedText = null;
		try {
			final Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, key);
			decryptedText = cipher.doFinal(text);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return decryptedText;
	}

}
