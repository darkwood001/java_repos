package cn.einino.commons.util;

public abstract class NumberUtil {

	public static byte parseByte(String src, byte def) {
		byte b = def;
		if (!StringUtil.isEmpty(src)) {
			try {
				b = Byte.parseByte(src);
			} catch (NumberFormatException e) {
			}
		}
		return b;
	}

	public static byte parseByte(String src) {
		return parseByte(src, (byte) 0);
	}

	public static short parseShort(String src, short def) {
		short s = def;
		if (!StringUtil.isEmpty(src)) {
			try {
				s = Short.parseShort(src);
			} catch (NumberFormatException e) {
			}
		}
		return s;
	}

	public static short parseShort(String src) {
		return parseShort(src, (short) 0);
	}

	public static int parseInt(String src, int def) {
		int i = def;
		if (!StringUtil.isEmpty(src)) {
			try {
				i = Integer.parseInt(src);
			} catch (NumberFormatException e) {
			}
		}
		return i;
	}

	public static int parseInt(String src) {
		return parseInt(src, 0);
	}

	public static long parseLong(String src, long def) {
		long l = def;
		if (!StringUtil.isEmpty(src)) {
			try {
				l = Long.parseLong(src);
			} catch (NumberFormatException e) {
			}
		}
		return l;
	}

	public static long parseLong(String src) {
		return parseLong(src, 0L);
	}

	public static float parseFloat(String src, float def) {
		float f = def;
		if (!StringUtil.isEmpty(src)) {
			try {
				f = Float.parseFloat(src);
			} catch (NumberFormatException e) {
			}
		}
		return f;
	}

	public static float parseFloat(String src) {
		return parseFloat(src, Float.NaN);
	}

	public static double parseDouble(String src, double def) {
		double d = def;
		if (!StringUtil.isEmpty(src)) {
			try {
				d = Double.parseDouble(src);
			} catch (NumberFormatException e) {
			}
		}
		return d;
	}

	public static double parseDouble(String src) {
		return parseDouble(src, Double.NaN);
	}

	public static float round(float value, int number) {
		float f = value;
		if (number > 0) {
			float ratio = (float) Math.pow(10, number);
			f = (float) Math.round(f * ratio) / ratio;
		}
		return f;
	}

	public static double round(double value, int number) {
		double d = value;
		if (number > 0) {
			double ratio = Math.pow(10, number);
			d = (double) Math.round(d * ratio) / ratio;
		}
		return d;
	}

}
