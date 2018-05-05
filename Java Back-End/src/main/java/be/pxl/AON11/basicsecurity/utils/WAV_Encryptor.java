package be.pxl.AON11.basicsecurity.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.*;

/**
 * Deze klasse verstopt text in muziek (.WAV) bestanden en kan ook tekst uit
 * muziek lezen.
 *
 *
 */
public abstract class WAV_Encryptor {
	private static final int START_INDEX = 3700000;

	private static int END_INDEX;

	public static void hideMessage(String input_path, String message, String output_path, String keyLocation,
			String pathToPublicKey2) {
		byte[] data = convertWAVFileToByteArray(input_path);
		String encryptedmessage = AES_Cryptor.encrypt(message, keyLocation, pathToPublicKey2);
		calculateEnd_index(encryptedmessage);
		setHiddenMessageOnStartIndexTo(data, encryptedmessage);
		createWAVFile(data, output_path);
	}

	private static void calculateEnd_index(String message) {
		END_INDEX = START_INDEX + message.length();
	}

	private static void setHiddenMessageOnStartIndexTo(byte[] data, String message) {
		byte[] characters = message.getBytes();
		int characterIndex = 0;
		for (int i = START_INDEX; i < END_INDEX; i++) {
			data[i] = characters[characterIndex++];
		}
	}

	private static void createWAVFile(byte[] data, String path) {
		File newFile = new File(path);
		InputStream byteArray = new ByteArrayInputStream(data);

		AudioInputStream ais = null;
		try {
			ais = AudioSystem.getAudioInputStream(byteArray);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		try {
			AudioSystem.write(ais, AudioFileFormat.Type.WAVE, newFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String showMessage(String pathToWav, String pathToKey, String prkey) {
		return AES_Cryptor.decrypt(
				new String(convertWAVFileToByteArray(pathToWav), START_INDEX, END_INDEX - START_INDEX), pathToKey,
				prkey);
	}

	private static byte[] convertWAVFileToByteArray(String pathToFile) {
		try {
			Path path = Paths.get(pathToFile);
			return Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
