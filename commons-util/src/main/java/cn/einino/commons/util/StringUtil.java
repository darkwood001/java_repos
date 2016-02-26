package cn.einino.commons.util;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public abstract class StringUtil {

	public static String byte2hex(byte[] b) {
		if (b == null || b.length < 1) {
			return null;
		}
		String hs;
		StringBuilder builder = new StringBuilder(b.length * 2);
		String stmp;
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				builder.append("0");
				builder.append(stmp);
			} else {
				builder.append(stmp);
			}
		}
		hs = builder.toString();
		return hs.toUpperCase();
	}

	public static byte[] hex2bytes(String key) {
		byte[] datas = null;
		if (!isEmpty(key)) {
			int length = key.length();
			if ((length & (2 - 1)) == 0) {
				int count = length >> 1;
				datas = new byte[count];
				String str;
				int j;
				for (int i = 0; i < count; i++) {
					str = key.substring(i * 2, (i + 1) * 2);
					j = Integer.parseInt(str, 16);
					datas[i] = (byte) j;
				}
			}
		}
		return datas;
	}

	public static boolean isEmpty(String src) {
		return (src == null || "".equals(src.trim()));
	}

	public static String IOS2UTF8(String src) {
		return charsetConvert(src, "ISO-8859-1", "UTF-8");
	}

	public static String charsetConvert(String src, String oCharset,
			String nCharset) {
		String value = null;
		if (src != null) {
			try {
				value = new String(src.getBytes(oCharset), nCharset);
			} catch (UnsupportedEncodingException e) {
				value = null;
			}
		}
		return value;
	}

	public static String formatNumber(double src, int count) {
		DecimalFormat format = new DecimalFormat();
		format.setMaximumFractionDigits(count);
		format.setGroupingSize(0);
		format.setRoundingMode(RoundingMode.FLOOR);
		return format.format(src);
	}
}
