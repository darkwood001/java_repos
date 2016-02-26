package cn.einino.commons.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class DigestUtil {

	public static String md5(byte[] buffer) {
		String str = null;
		if (buffer != null) {
			try {
				MessageDigest digest = MessageDigest.getInstance("MD5");
				digest.update(buffer);
				byte[] md = digest.digest();
				str = StringUtil.byte2hex(md);
			} catch (NoSuchAlgorithmException e) {
				return str;
			}
		}
		return str;
	}

	public static byte[] md5Binary(byte[] buffer) {
		byte[] md = null;
		if (buffer != null) {
			try {
				MessageDigest digest = MessageDigest.getInstance("MD5");
				digest.update(buffer);
				md = digest.digest();
			} catch (NoSuchAlgorithmException e) {
				return md;
			}
		}
		return md;
	}

	public static String md5(String src) {
		String str = null;
		if (!StringUtil.isEmpty(src)) {
			str = md5(src.getBytes());
		}
		return str;
	}

	public static String sha1(byte[] buffer) {
		String str = null;
		if (buffer != null) {
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA1");
				digest.update(buffer);
				byte[] sha1 = digest.digest();
				str = StringUtil.byte2hex(sha1);
			} catch (NoSuchAlgorithmException e) {
				return str;
			}
		}
		return str;
	}

	public static String sha1(String src) {
		String str = null;
		if (!StringUtil.isEmpty(src)) {
			str = sha1(src.getBytes());
		}
		return str;
	}
}
