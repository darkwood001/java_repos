package cn.einino.commons.util.module.dbf;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.einino.commons.util.exception.FileReaderException;

public class DBFField {
	private String name;
	private char type;
	private int length;
	private int decimalCount;

	/**
	 * Creates a new instance of JDBField
	 */
	public DBFField(String s, char c, int i, int j) throws FileReaderException {
		if (s.length() > 10) {
			throw new FileReaderException("字段名称超过10个字符:" + s);
		}
		if (c != 'C' && c != 'N' && c != 'L' && c != 'D' && c != 'F') // 'C':字符型、'N':数值型、'L':逻辑型、'D':日期型、'F':浮点型。
		// 注：DBF字段其实还有其他类型，如双精度、整型等等
		{
			throw new FileReaderException("字段类型非法:" + c);
		}
		if (i < 1) {
			throw new FileReaderException(
					"The field should be a positive integer.Got:" + i);
		}
		if (c == 'C' && i >= 254) {
			throw new FileReaderException(
					"The field should be less than 254 characters for character fields.Got:"
							+ i);
		}
		if (c == 'N' && i >= 21) {
			throw new FileReaderException(
					"The field length should be less than 21 digits for numeric fields. Got:"
							+ i);
		}
		if (c == 'L' && i != 1) {
			throw new FileReaderException(
					"The field length should be 1 character for logical fields.Got:"
							+ i);
		}
		if (c == 'D' && i != 8) {
			throw new FileReaderException(
					"The field length should be 8 characters for date fields.Got:"
							+ i);
		}
		if (c == 'F' && i >= 21) {
			throw new FileReaderException(
					"The field length should be less than 21 digits for floating point fields.Got:"
							+ i);
		}
		if (j < 0) {
			throw new FileReaderException(
					"The field decimal count should not be a negative integer.Got:"
							+ j);
		}
		if ((c == 'C' || c == 'L' || c == 'D') && j != 0) {
			throw new FileReaderException(
					"The field decimal count should be 0 for character,logical,and date fields. Got:"
							+ j);
		}
		if (j > i - 1) {
			throw new FileReaderException(
					"The field decimal count should be less than the length - 1.Got:"
							+ j);
		} else {
			name = s;
			type = c;
			length = i;
			decimalCount = j;
			return;
		}
	}

	public int getDecimalCount() {
		return decimalCount;
	}

	public int getLength() {
		return length;
	}

	public String getName() {
		return name;
	}

	public char getType() {
		return type;
	}

	public void setDecimalCount(int decimalCount) {
		this.decimalCount = decimalCount;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(char type) {
		this.type = type;
	}

	public String format(Object obj) throws FileReaderException {
		if (type == 'N' || type == 'F') {
			if (obj == null) {
				obj = new Double(0.0D);
			}
			if (obj instanceof Number) {
				Number number = (Number) obj;
				StringBuffer stringbuffer = new StringBuffer(getLength());
				for (int i = 0; i < getLength(); i++) {
					stringbuffer.append("#");
				}
				if (getDecimalCount() > 0) {
					stringbuffer.setCharAt(getLength() - getDecimalCount() - 1,
							'.');
				}
				DecimalFormat decimalformat = new DecimalFormat(
						stringbuffer.toString());
				String s1 = decimalformat.format(number);
				int k = getLength() - s1.length();
				if (k < 0) {
					throw new FileReaderException("Value " + number
							+ " cannot fit in pattern:'" + stringbuffer + "'.");
				}
				StringBuffer stringbuffer2 = new StringBuffer(k);
				for (int l = 0; l < k; l++) {
					stringbuffer2.append(" ");
				}
				return stringbuffer2 + s1;
			} else {
				throw new FileReaderException("Expected a Number, got "
						+ obj.getClass() + ".");
			}
		} else if (type == 'C') {
			if (obj == null) {
				obj = "";
			}
			if (obj instanceof String) {
				String s = (String) obj;
				if (s.length() > getLength()) {
					throw new FileReaderException("'" + obj
							+ "' is longer than " + getLength()
							+ " characters.");
				}
				StringBuffer stringbuffer1 = new StringBuffer(getLength()
						- s.length());
				for (int j = 0; j < getLength() - s.length(); j++) {
					stringbuffer1.append(' ');
				}
				return s + stringbuffer1;
			} else {
				throw new FileReaderException("Expected a Number, got "
						+ obj.getClass() + ".");
			}
		} else if (type == 'L') {
			if (obj == null) {
				obj = new Boolean(false);
			}
			if (obj instanceof Boolean) {
				Boolean boolean1 = (Boolean) obj;
				return boolean1.booleanValue() ? "Y" : "N";
			} else {
				throw new FileReaderException("Expected a Boolean,got"
						+ obj.getClass() + ".");
			}
		} else if (type == 'D') {
			if (obj == null) {
				obj = new Date();
			}
			if (obj instanceof Date) {
				Date date = (Date) obj;
				SimpleDateFormat simpledateformat = new SimpleDateFormat(
						"yyyyMMdd");
				return simpledateformat.format(date);
			} else {
				throw new FileReaderException("Expected a Date, got "
						+ obj.getClass() + ".");
			}
		} else {
			throw new FileReaderException("Unrecognized JDBFField type: "
					+ type);
		}
	}

	public Object parse(String s) throws FileReaderException {
		s = s.trim();
		if (type == 'N' || type == 'F') {
			if (s.equals("")) {
				s = "0";
			}
			try {
				if (getDecimalCount() == 0) {
					return new Long(s);
				} else {
					return new Double(s);
				}
			} catch (NumberFormatException ne) {
				if (!s.equals("-.---") && !s.equals("-")) {
					throw new FileReaderException(ne);
				} else {
					if (getDecimalCount() == 0) {
						return (Long) 0l;
					} else {
						return (Double) 0.00;
					}
				}
			}
		} else if (type == 'C') {
			return s;
		} else if (type == 'L') {
			String tmp = s.toLowerCase();
			if (tmp.equals("y") || tmp.equals("t")) {
				return new Boolean(true);
			}
			if (tmp.equals("n") || tmp.equals("f")) {
				return new Boolean(false);
			} else {
				throw new FileReaderException(
						"Unrecognized value for logical field: " + s);
			}

		}
		if (type == 'D') {
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
			try {
				if (s.equals("")) {
					return null;
				} else {
					return simpledateformat.parse(s);
				}
			} catch (ParseException pe) {
				throw new FileReaderException(pe);
			}
		} else {
			throw new FileReaderException("Unrecognized JDBFField type: "
					+ type);
		}
	}

	public String toString() {
		return name;
	}
}
