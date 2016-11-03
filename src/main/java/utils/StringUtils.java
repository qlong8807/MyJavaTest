package utils;

import java.io.UnsupportedEncodingException;

public class StringUtils {
	public static String bytesToGbkString(byte[] bytes) {
		try {
			return new String(bytes, "gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String bytesToUtf8String(byte[] bytes) {
		try {
			return new String(bytes, "utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
}
