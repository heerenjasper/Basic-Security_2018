package be.pxl.AON11.basicsecurity.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public abstract class Encryptor {

	public static void encrypt(String message, String pathToInput, String pathToOutput) {
		RSA_Cryptor.generateKeyPair(PathProvider.getPathToPrivateKey1(), PathProvider.getPathToPublicKey1());
		RSA_Cryptor.generateKeyPair(PathProvider.getPathToPrivateKey2(), PathProvider.getPathToPublicKey2());
		System.out.println("Path to input : " + pathToInput);
		System.out.println("Path to output : " + pathToOutput);
		if (pathToInput.endsWith(".png")) {
			PNG_Cryptor.addMessageToPicture(pathToInput, message, pathToOutput, PathProvider.getPathToFile2(),
					PathProvider.getPathToPublicKey2());
		} else {
			if (pathToInput.endsWith(".wav")) {
				WAV_Encryptor.hideMessage(pathToInput, message, pathToOutput, PathProvider.getPathToFile2(),
						PathProvider.getPathToPublicKey2());
			}
		}
		saveHash_IntoFile3(message);
	}

	/**
	 * Encrypts the hash with the private key 1, saves the result in a file after hashing.
	 * (File_3)
	 * 
	 * @param message
	 * 
	 */
	private static void saveHash_IntoFile3(String message) {
		try (PrintWriter writer = new PrintWriter(new File(PathProvider.getPathToFile3()))) {
			byte[] encryptedHashedMessage = RSA_Cryptor.encryptPrivate(PathProvider.getPathToPrivateKey1(),
					MD5.getHash(message).getBytes());
			for (int i = 0; i < encryptedHashedMessage.length - 1; i++) {
				writer.println(encryptedHashedMessage[i]);
			}
			writer.print(encryptedHashedMessage[encryptedHashedMessage.length - 1]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private Encryptor() {
	}

}
