package cn.einino.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateTimeUtil {

	public static final String DATE_TIME_SEPARATER = " ";
	public static final String YYYYMMDD_PATTERN = "yyyyMMdd";
	public static final String YYMMDD_PATTERN = "yyMMdd";
	public static final String HHMMSS_PATTERN = "HHmmss";
	public static final String HHMM_PATTERN = "HHmm";
	public static final String YYYYMMDD_HHMMSS_PATTERN = YYYYMMDD_PATTERN
			+ DATE_TIME_SEPARATER + HHMMSS_PATTERN;
	public static final String YYYYMMDD_HHMM_PATTERN = YYYYMMDD_PATTERN
			+ DATE_TIME_SEPARATER + HHMM_PATTERN;

	private static final ThreadLocal<SimpleDateFormat> THREAD_FORMAT = new ThreadLocal<SimpleDateFormat>();

	public static String date2String(Date date, String pattern) {
		if (StringUtil.isEmpty(pattern)) {
			return null;
		}
		SimpleDateFormat format = THREAD_FORMAT.get();
		if (format == null) {
			format = new SimpleDateFormat();
			THREAD_FORMAT.set(format);
		}
		format.applyPattern(pattern);
		String str = format.format(date);
		return str;
	}

	public static Date string2Date(String src, String pattern) {
		if (StringUtil.isEmpty(src) || StringUtil.isEmpty(pattern)) {
			return null;
		}
		SimpleDateFormat format = THREAD_FORMAT.get();
		if (format == null) {
			format = new SimpleDateFormat();
			THREAD_FORMAT.set(format);
		}
		format.applyPattern(pattern);
		Date date = null;
		try {
			date = format.parse(src);
		} catch (ParseException e) {
		}
		return date;
	}

	public static String getYYYYMMDD(Date date) {
		return date2String(date, YYYYMMDD_PATTERN);
	}

	public static String getYYMMDD(Date date) {
		return date2String(date, YYMMDD_PATTERN);
	}

	public static String getHHMMSS(Date date) {
		return date2String(date, HHMMSS_PATTERN);
	}

	public static String getHHMM(Date date) {
		return date2String(date, HHMM_PATTERN);
	}

	public static String getYYYYMMDD_HHMMSS(Date date) {
		return date2String(date, YYYYMMDD_HHMMSS_PATTERN);
	}

	public static String getYYYYMMDD_HHMM(Date date) {
		return date2String(date, YYYYMMDD_HHMM_PATTERN);
	}

	public static Date parseYYYYMMDD(String src) {
		return string2Date(src, YYYYMMDD_PATTERN);
	}

	public static Date parseYYMMDD(String src) {
		return string2Date(src, YYMMDD_PATTERN);
	}

	public static Date parseHHMMSS(String src) {
		return string2Date(src, HHMMSS_PATTERN);
	}

	public static Date parseHHMM(String src) {
		return string2Date(src, HHMM_PATTERN);
	}

	public static Date parseYYYYMMDD_HHMMSS(String src) {
		return string2Date(src, YYYYMMDD_HHMMSS_PATTERN);
	}

	public static Date parseYYYYMMDD_HHMM(String src) {
		return string2Date(src, YYYYMMDD_HHMM_PATTERN);
	}
}
