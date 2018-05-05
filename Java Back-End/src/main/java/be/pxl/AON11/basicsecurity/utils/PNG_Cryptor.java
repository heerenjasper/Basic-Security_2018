package be.pxl.AON11.basicsecurity.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public abstract class PNG_Cryptor {

	/**
	 * Adds a message to a picture.
	 *
	 * @param pathToPicture
	 *            path to the pic
	 * @param message
	 *            message to encrypt in the pic
	 * @param destination
	 *            location of image output
	 * @param keyLocation
	 *            location of key
	 */
	public static void addMessageToPicture(String pathToPicture, String message, String destination, String keyLocation,
			String pathToPublicKey2) {
		BufferedImage picture = loadPicture(pathToPicture);
		int[] pixels = getPixels(picture);
		int w = picture.getWidth();
		int h = picture.getHeight();
		writeData(pixels, getByteArrayFromString(AES_Cryptor.encrypt(message, keyLocation, pathToPublicKey2)));
		writeImg(getImageFromArray(pixels, w, h), destination);
	}

	/**
	 * Methode to load a BufferedImage
	 *
	 * @param pathToPicture
	 *            path to the picture
	 * @return the BufferedImage object
	 */
	private static BufferedImage loadPicture(String pathToPicture) {
		if (pathToPicture.endsWith(".png")) {
			try {
				return ImageIO.read(new File(pathToPicture));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Gets the pixels array from a BufferedImage
	 * 
	 * @param pic
	 *            the BufferedImage object
	 * @return a int array with pixels
	 */
	private static int[] getPixels(BufferedImage pic) {
		int width = pic.getWidth();
		int height = pic.getHeight();
		int[] pixels = new int[width * height];
		pic.getRGB(0, 0, width, height, pixels, 0, width);
		return pixels;
	}

	/**
	 * Writes the message into the pixels
	 * 
	 * @param pixels
	 *            the pixels array
	 * @param data
	 *            the text as a byte array
	 */
	private static void writeData(int[] pixels, byte[] data) {
		int pixelCounter = 0;
		int colorCounter = 0;
		byte[] length = toBinary(data.length);
		for (int i = 0; i < 32; i++) {
			pixels[pixelCounter] = setIntOn(pixels[pixelCounter], colorCounter, length[i]);
			colorCounter++;
			if (colorCounter == 3) {
				colorCounter = 0;
				pixelCounter++;
			}
		}
		pixelCounter++;
		colorCounter = 0;
		for (int i = 0; i < data.length; i++) {
			for (int bit = 0; bit < 7; bit++) {
				pixels[pixelCounter] = setIntOn(pixels[pixelCounter], colorCounter, getBit(data[i], bit));
				colorCounter++;
				if (colorCounter == 3) {
					colorCounter = 0;
					pixelCounter++;
				}
			}
		}
	}

	private static byte[] toBinary(int getal) {
		byte[] bits = new byte[32];
		for (int i = 0; i < 31; i++) {
			bits[i] = (byte) (getal & 0x1);
			getal = getal >> 1;
		}
		return bits;
	}

	/**
     * Set the last bit of the RGB from a pixel to 1 or 0
	 * 
	 * @param pixel
	 *            value to give to the pixel
	 * @param color
     *            the color you want to change (0 = red, 1 = green, 2 =
     * 	 *            blue)
	 * @param value
	 *            the value you want to set (1 of 0)
	 * @return
	 */
	private static int setIntOn(int pixel, int color, int value) {
		int red = getRed(pixel);
		int green = getGreen(pixel);
		int blue = getBlue(pixel);
		if (color == 0) {
			red = setLastBitTo(red, value);
		} else if (color == 1) {
			green = setLastBitTo(green, value);
		} else if (color == 2) {
			blue = setLastBitTo(blue, value);
		}

		int newPixel = red;
		newPixel = (newPixel << 8) + green;
		newPixel = (newPixel << 8) + blue;
		return newPixel;
	}

	/**
     * Set the last bit from the 'original' to 1 or 0
	 * 
	 * @param original
     *            the int that you want to change
	 * @param value
	 *            the value 0 or zero
	 * @return
	 */
	private static int setLastBitTo(int original, int value) {
		if (value == 1) {
			return (original | (1 << 0));
		} else {
			return (original & ~(1 << 0));
		}
	}

	/**
     * Returns the nth bit of 'number'
	 * 
	 * @param number
	 * @param n
	 * @return
	 */
	private static int getBit(int number, int n) {
		return ((number >> n) & 1);
	}

	/**
     * Returns the red value from the RGB of a pixel.
	 * 
	 * @param pixel
	 * @return
	 */
	private static int getRed(int pixel) {
		return ((pixel >> 16) & 0xFF);
	}

	/**
     * Returns the green value from the RGB of a pixel.
	 * 
	 * @param pixel
	 * @return
	 */
	private static int getGreen(int pixel) {
		return ((pixel >> 8) & 0xFF);
	}

	/**
     * Returns the blue value from the RGB of a pixel.
	 * 
	 * @param pixel
	 * @return
	 */
	private static int getBlue(int pixel) {
		return (pixel & 0xFF);
	}

	private static int bitsToInt(byte[] arr) {
		int n = 0;
		for (byte b : arr)
			n = (n << 1) | (b == 1 ? 1 : 0);
		return n;
	}

	private static void reverse(byte[] array) {
		if (array == null) {
			return;
		}
		int i = 0;
		int j = array.length - 1;
		byte tmp;
		while (j > i) {
			tmp = array[j];
			array[j] = array[i];
			array[i] = tmp;
			j--;
			i++;
		}
	}

	/**
     * Gets the encrypted bytes from the pixels array.
	 * 
	 * @param pixels
	 * @return
	 */
	private static byte[] getBytesFromPixels(int[] pixels) {
		byte[] lengthBits = new byte[32];
		int counter = 0;
		for (int i = 0; i < 9; i++) {
			lengthBits[counter] = (byte) getBit(getRed(pixels[i]), 0);
			counter++;
			lengthBits[counter] = (byte) getBit(getGreen(pixels[i]), 0);
			counter++;
			lengthBits[counter] = (byte) getBit(getBlue(pixels[i]), 0);
			counter++;
		}
		lengthBits[counter] = (byte) getBit(getRed(pixels[10]), 0);
		counter++;
		lengthBits[counter] = (byte) getBit(getGreen(pixels[10]), 0);
		counter++;
		reverse(lengthBits);
		int length = bitsToInt(lengthBits);
		byte[] bits = new byte[length * 7];
		int messageCounter = 0;
		for (int i = 11; i < 11 + (length * 7 / 3); i++) {
			bits[messageCounter] = (byte) getBit(getRed(pixels[i]), 0);
			messageCounter++;
			bits[messageCounter] = (byte) getBit(getGreen(pixels[i]), 0);
			messageCounter++;
			bits[messageCounter] = (byte) getBit(getBlue(pixels[i]), 0);
			messageCounter++;
		}
		byte[] bytes = new byte[length];
		int byteCounter = 0;
		for (int i = 0; i < bits.length; i += 7) {
			String piece = "" + bits[i] + bits[i + 1] + bits[i + 2] + bits[i + 3] + bits[i + 4] + bits[i + 5]
					+ bits[i + 6];
			bytes[byteCounter] = Byte.parseByte(new StringBuilder(piece).reverse().toString(), 2);
			byteCounter++;
		}
		return bytes;
	}

	/**
	 * Reads the text from a picture.
	 * 
	 * @param pathToPicture
	 * @return
	 */
	public static String readMessageFromPicture(String pathToPicture, String pathToKey, String prkey) {
		return AES_Cryptor.decrypt(getStringFromByteArray((getBytesFromPixels(getPixels(loadPicture(pathToPicture))))),
				pathToKey, prkey);
	}

	/**
     * Converts a string array to a byte array
	 * 
	 * @param s
	 * @return
	 */
	private static byte[] getByteArrayFromString(String s) {
		try {
			byte[] data = s.getBytes("UTF-8");
			byte[] goodData;
			if (data.length % (3) != 0) {
				goodData = new byte[data.length + ((3) - data.length % (3))];
				for (int i = 0; i < data.length; i++) {
					goodData[i] = data[i];
				}
				for (int i = data.length; i < data.length + ((3) - data.length % (3)); i++) {
					goodData[i] = 32;
				}
			} else {
				goodData = data;
			}
			return goodData;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
     * Converts a byte array to a string
	 * 
	 * @param array
	 * @return
	 */
	private static String getStringFromByteArray(byte[] array) {
		try {
			return new String(array, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
     * Set a pixel array to a BufferedImage.
	 * 
	 * @param pixels
	 * @param width
	 * @param height
	 * @return
	 */
	private static BufferedImage getImageFromArray(int[] pixels, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				image.setRGB(x, y, pixels[x + y * width]);
			}
		}
		return image;
	}

    /**
	 * Writes the BufferedImage to the destination path
	 * 
	 * @param image
	 * @param destination
	 */
	private static void writeImg(BufferedImage image, String destination) {
		try {
			ImageIO.write(image, "png", new File(destination));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private PNG_Cryptor() {
	}
}
