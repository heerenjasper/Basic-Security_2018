package be.pxl.AON11.basicsecurity.utils;

import java.util.Random;

public abstract class RandomStringGenerator {
	public static String generateRandomString(int lengte) {
		String uitkomst = "";
		Random rand = new Random();
		for (int i = 0; i < lengte; i++) {
			uitkomst = uitkomst + (char) (rand.nextInt(89) + 33);
		}
		return uitkomst;
	}
}
