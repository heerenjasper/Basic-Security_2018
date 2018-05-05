package be.pxl.AON11.basicsecurity.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public abstract class Decryptor {

	public static String decrypt(String pathToOutputPicture) {
		return getMessage(pathToOutputPicture);
	}

	private static String getMessage(String pathToOutput) {
		if (pathToOutput.endsWith(".png")) {
			return PNG_Cryptor.readMessageFromPicture(pathToOutput, PathProvider.getPathToFile2(),
					PathProvider.getPathToPrivateKey2());
		} else {
			if (pathToOutput.endsWith(".wav")) {
				return WAV_Encryptor.showMessage(pathToOutput, PathProvider.getPathToFile2(),
						PathProvider.getPathToPrivateKey2());

			}
			return null;
		}
	}

	public static boolean checkIfHashIsCorrect(String pathToOutputPicture) {
		String inputHash = readDecryptAndPrintHash(PathProvider.getPathToFile3(), PathProvider.getPathToPublicKey1());
		String outputHash = MD5.getHash(getMessage(pathToOutputPicture));
		return inputHash.equals(outputHash);
	}

	public static void writeOutputMessage(String pathToOutputPicture) {
		String receivedMessage = getMessage(pathToOutputPicture);
		System.out.println(receivedMessage);
		System.out.println("Valid HASH? : " + checkIfHashIsCorrect(pathToOutputPicture));
	}


	private static String readDecryptAndPrintHash(String fileToHash, String puKey1) {
		try {
			byte[] hash = new byte[128];
			BufferedReader br = new BufferedReader(new FileReader(new File(fileToHash)));
			for (int i = 0; i < hash.length; i++) {
				hash[i] = Byte.valueOf(br.readLine());
			}
			br.close();
			byte[] realHash = RSA_Cryptor.decryptPublic(puKey1, hash);
			return new String(realHash);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Decryptor() {
	}

}
