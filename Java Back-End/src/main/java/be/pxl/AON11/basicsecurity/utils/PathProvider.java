package be.pxl.AON11.basicsecurity.utils;

public abstract class PathProvider {
	private final static String pathToPrivateKey1 = "./private1.key";
	private final static String pathToPublicKey1 = "./public1.key";
	private final static String pathToPrivateKey2 = "./private2.key";
	private final static String pathToPublicKey2 = "./public2.key";
	private final static String pathToFile2 = "./file2.txt";
	private final static String pathToFile3 = "./file3.txt";

	public static String getPathToPrivateKey1() {
		return pathToPrivateKey1;
	}

	public static String getPathToPublicKey1() {
		return pathToPublicKey1;
	}

	public static String getPathToPrivateKey2() {
		return pathToPrivateKey2;
	}

	public static String getPathToPublicKey2() {
		return pathToPublicKey2;
	}

	public static String getPathToFile2() {
		return pathToFile2;
	}

	public static String getPathToFile3() {
		return pathToFile3;
	}

}
