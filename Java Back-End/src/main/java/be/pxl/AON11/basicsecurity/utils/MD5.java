package be.pxl.AON11.basicsecurity.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class MD5 {
	public static String getHash(String input) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(input.getBytes(), 0, input.length());
			return new BigInteger(1, m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
