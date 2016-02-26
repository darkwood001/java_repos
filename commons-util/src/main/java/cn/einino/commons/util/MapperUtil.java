package cn.einino.commons.util;

import java.util.Date;
import java.util.Map;

public abstract class MapperUtil {

	public static String getString(Map<String, Object> map, String key,
			String def) {
		String value = def;
		if (map != null && key != null) {
			Object obj = map.get(key);
			if (obj != null) {
				if (obj instanceof String) {
					value = ((String) obj).trim();
				} else {
					value = obj.toString().trim();
				}
			}
		}
		return value;
	}

	public static String getString(Map<String, Object> map, String key) {
		return getString(map, key, null);
	}

	public static byte getByte(Map<String, Object> map, String key, byte def) {
		byte b = def;
		if (map != null && key != null) {
			Object obj = map.get(key);
			if (obj != null) {
				if (obj instanceof Number) {
					b = ((Number) obj).byteValue();
				} else {
					b = NumberUtil.parseByte(obj.toString(), def);
				}
			}
		}
		return b;
	}

	public static byte getByte(Map<String, Object> map, String key) {
		return getByte(map, key, (byte) 0);
	}

	public static short getShort(Map<String, Object> map, String key, short def) {
		short s = def;
		if (map != null && key != null) {
			Object obj = map.get(key);
			if (obj != null) {
				if (obj instanceof Number) {
					s = ((Number) obj).shortValue();
				} else {
					s = NumberUtil.parseShort(obj.toString(), def);
				}
			}
		}
		return s;
	}

	public static short getShort(Map<String, Object> map, String key) {
		return getShort(map, key, (short) 0);
	}

	public static int getInt(Map<String, Object> map, String key, int def) {
		int i = def;
		if (map != null && key != null) {
			Object obj = map.get(key);
			if (obj != null) {
				if (obj instanceof Number) {
					i = ((Number) obj).intValue();
				} else {
					i = NumberUtil.parseInt(obj.toString(), def);
				}
			}
		}
		return i;
	}

	public static int getInt(Map<String, Object> map, String key) {
		return getInt(map, key, 0);
	}

	public static long getLong(Map<String, Object> map, String key, long def) {
		long l = def;
		if (map != null && key != null) {
			Object obj = map.get(key);
			if (obj != null) {
				if (obj instanceof Number) {
					l = ((Number) obj).longValue();
				} else {
					l = NumberUtil.parseLong(obj.toString(), def);
				}
			}
		}
		return l;
	}

	public static long getLong(Map<String, Object> map, String key) {
		return getLong(map, key, 0L);
	}

	public static float getFloat(Map<String, Object> map, String key, float def) {
		float f = def;
		if (map != null && key != null) {
			Object obj = map.get(key);
			if (obj != null) {
				if (obj instanceof Number) {
					f = ((Number) obj).floatValue();
				} else {
					f = NumberUtil.parseFloat(obj.toString(), def);
				}
			}
		}
		return f;
	}

	public static float getFloat(Map<String, Object> map, String key) {
		return getFloat(map, key, Float.NaN);
	}

	public static double getDouble(Map<String, Object> map, String key,
			double def) {
		double d = def;
		if (map != null && key != null) {
			Object obj = map.get(key);
			if (obj != null) {
				if (obj instanceof Number) {
					d = ((Number) obj).doubleValue();
				} else {
					d = NumberUtil.parseDouble(obj.toString(), def);
				}
			}
		}
		return d;
	}

	public static double getDouble(Map<String, Object> map, String key) {
		return getDouble(map, key, Double.NaN);
	}

	public static Date getDate(Map<String, Object> map, String key, Date def) {
		Date d = def;
		Object obj = map.get(key);
		if (obj != null) {
			if (obj instanceof java.sql.Date) {
				java.sql.Date date = (java.sql.Date) obj;
				d = new Date(date.getTime());
			} else if (obj instanceof java.sql.Timestamp) {
				java.sql.Timestamp time = (java.sql.Timestamp) obj;
				d = new Date(time.getTime());
			}
		}
		return d;
	}

	public static Date getDate(Map<String, Object> map, String key) {
		return getDate(map, key, null);
	}

}
