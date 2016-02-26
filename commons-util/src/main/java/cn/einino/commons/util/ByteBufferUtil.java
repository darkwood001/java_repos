package cn.einino.commons.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public abstract class ByteBufferUtil {
	public static final String CHARSET = "UTF-8";
	public static final long UINT_CONST = 0xFFFFFFFFL;
	public static final int USHORT_CONST = 0xFFFF;
	public static final int UBYTE_CONST = 0xFF;

	/**
	 * 从流中读定长度字节数组
	 *
	 * @param is
	 * @param i
	 * @return @
	 */
	public static byte[] readBytes(ByteBuffer is, int i) {
		byte[] data = new byte[i];
		is.get(data);
		return data;
	}

	/**
	 * 从输入流中读字符
	 *
	 * @param is
	 * @return @
	 */
	public static char readChar(ByteBuffer is) {
		return is.getChar();
	}

	/**
	 * 从输入流中读双精度
	 *
	 * @param is
	 * @return @
	 */
	public static double readDouble(ByteBuffer is) {
		return is.getDouble();
	}

	/**
	 * 从输入流中读单精度
	 *
	 * @param is
	 * @return @
	 */
	public static float readFloat(ByteBuffer is) {
		return is.getFloat();
	}

	/**
	 * 从流中读整型
	 *
	 * @param is
	 * @return @
	 */
	public static int readInt(ByteBuffer is) {
		return is.getInt();
	}

	/**
	 * 从流中读无符号整型
	 *
	 * @param is
	 * @return @
	 */
	public static long readUInt(ByteBuffer is) {
		long l = (UINT_CONST & is.getInt());
		return l;
	}

	/**
	 * 从流中读长整型
	 *
	 * @param is
	 * @return @
	 */
	public static long readLong(ByteBuffer is) {
		return is.getLong();
	}

	/**
	 * 从流中读短整型
	 *
	 * @param is
	 * @return @
	 */
	public static short readShort(ByteBuffer is) {
		return is.getShort();
	}

	/**
	 * 从流中读无符号短整型
	 *
	 * @param is
	 * @return @
	 */
	public static int readUShort(ByteBuffer is) {
		int i = (USHORT_CONST & is.getShort());
		return i;
	}

	/**
	 * 从流中读无符号字节型
	 *
	 * @param is
	 * @return @
	 */
	public static int readUByte(ByteBuffer is) {
		int i = (UBYTE_CONST & is.get());
		return i;
	}

	/**
	 * 从输入流中读字符串 字符串 结构 为 一个指定字符串字节长度的短整型+实际字符串
	 *
	 * @param is
	 * @return
	 * @throws UnsupportedEncodingException
	 *             @
	 */
	public static String readUTF(ByteBuffer is)
			throws UnsupportedEncodingException {
		short s = readShort(is);
		byte[] str = new byte[s];
		is.get(str);
		return new String(str, CHARSET);
	}

	/**
	 * 向输出流中写字节数组
	 *
	 * @param os
	 * @param data
	 *            @
	 */
	public static void writeBytes(ByteBuffer os, byte[] data) {
		os.put(data);
	}

	/**
	 * 向输出流中写字符
	 *
	 * @param os
	 * @param b
	 *            @
	 */
	public static void writeChar(ByteBuffer os, char b) {
		os.putChar(b);
	}

	/**
	 * 向输出流中写双精度
	 *
	 * @param os
	 * @param d
	 *            @
	 */
	public static void writeDouble(ByteBuffer os, double d) {
		os.putDouble(d);
	}

	/**
	 * 向输出流中写单精度
	 *
	 * @param os
	 * @param f
	 *            @
	 */
	public static void writeFloat(ByteBuffer os, float f) {
		os.putFloat(f);
	}

	/**
	 * 向输出流中写整型
	 *
	 * @param os
	 * @param i
	 *            @
	 */
	public static void writeInt(ByteBuffer os, int i) {
		os.putInt(i);
	}

	/**
	 * 向输出流中写长整型
	 *
	 * @param os
	 * @param l
	 *            @
	 */
	public static void writeLong(ByteBuffer os, long l) {
		os.putLong(l);
	}

	/**
	 * 向输出流中写短整型
	 *
	 * @param os
	 * @param s
	 *            @
	 */
	public static void writeShort(ByteBuffer os, short s) {
		os.putShort(s);
	}

	/**
	 * 向输出流中写字符串 字符串 结构 为 一个指定字符串字节长度的短整型+实际字符串
	 *
	 * @param os
	 * @param str
	 * @throws UnsupportedEncodingException
	 *             @
	 */
	public static void writeUTF(ByteBuffer os, String str)
			throws UnsupportedEncodingException {
		byte[] data = str.getBytes(CHARSET);
		writeShort(os, (short) data.length);
		os.put(data);
	}
}
