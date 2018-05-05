package be.pxl.AON11.basicsecurity.utils;

import java.util.Random;

public abstract class RandomStringGenerator {
	public static String generateRandomString(int length) {
		String generatedString = "";
		Random rand = new Random();
		for (int i = 0; i < length; i++) {
			generatedString = generatedString + (char) (rand.nextInt(89) + 33);
		}
		return generatedString;
	}
}
