package cn.einino.commons.util;

import java.io.File;

public abstract class FileUtil {

	public static String getFileSuffix(final String filename) {
		if (StringUtil.isEmpty(filename)) {
			return null;
		}
		File file = new File(filename);
		String name = file.getName();
		int idx = name.indexOf('.');
		if (idx > 1) {
			return name.substring(idx + 1).toLowerCase();
		} else {
			return null;
		}
	}

	public static String getFilePrefix(final String filename) {
		if (StringUtil.isEmpty(filename)) {
			return null;
		}
		File file = new File(filename);
		String name = file.getName();
		int idx = name.indexOf('.');
		if (idx > 1) {
			return name.substring(0, idx).toLowerCase();
		} else {
			return null;
		}
	}
}
