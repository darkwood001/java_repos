package cn.einino.commons.util.filereader.dbf;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.BufferUnderflowException;

import cn.einino.commons.util.exception.FileReaderException;
import cn.einino.commons.util.filereader.FileReader;
import cn.einino.commons.util.module.dbf.DBFField;

public class DBFReader implements FileReader {

	private String charset = "GBK";
	private RandomAccessFile stream;
	private DBFField fields[];
	private byte nextRecord[];
	private long recordStart; // 第一条数据记录所处位置
	private int recordLength; // 数据记录总长度

	public DBFReader(String fileName) throws FileReaderException {
		init(new File(fileName));
	}

	public DBFReader(String fileName, String charset)
			throws FileReaderException {
		this(fileName);
		this.charset = charset;
	}

	@Override
	public void readRecord() throws FileReaderException {
		try {
			stream.seek(recordStart);
			nextRecord = new byte[recordLength];
			try {
				stream.readFully(nextRecord);
			} catch (EOFException e) {
				nextRecord = null;
				stream.close();
			}
		} catch (IOException e) {
			throw new FileReaderException(e);
		}
	}

	@Override
	public String[] nextRecord() throws FileReaderException {
		if (!hasNextRecord()) {
			throw new FileReaderException("No more records available.");
		}
		String aobj[] = new String[fields.length];
		int i = 1;
		for (int j = 0; j < aobj.length; j++) {
			int k = fields[j].getLength();
			try {
				aobj[j] = new String(nextRecord, i, k, charset);
			} catch (UnsupportedEncodingException e) {
			}
			i += fields[j].getLength();
		}
		try {
			stream.readFully(nextRecord);
		} catch (EOFException e) {
			nextRecord = null;
		} catch (IOException ioe) {
			throw new FileReaderException(ioe);
		}
		return aobj;
	}

	@Override
	public boolean hasNextRecord() {
		return nextRecord != null;
	}

	@Override
	public void close() throws FileReaderException {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
			}
			stream = null;
		}
	}

	private void init(File file) throws FileReaderException {
		try {
			stream = new RandomAccessFile(file, "r");
			int count = readHeader();
			fields = new DBFField[count];
			int sum = 1;
			for (int i = 0; i < count; ++i) {
				fields[i] = readFieldHeader();
				sum += fields[i].getLength();
			}
			if (stream.read() < 1) {
				throw new FileReaderException("Unexpected end of file reached.");
			}
			recordStart = stream.getFilePointer();
			if (sum > 0) {
				recordLength = sum;
			}
		} catch (BufferUnderflowException | IOException e) {
			String msg = new StringBuilder("DBFReader init file:[")
					.append(file.getAbsolutePath()).append("] error")
					.toString();
			throw new FileReaderException(msg, e);
		}
	}

	private int readHeader() throws FileReaderException, IOException {
		// DBF文件Header为前32个字节
		byte abyte0[] = new byte[16];
		try {
			stream.readFully(abyte0);
		} catch (EOFException e) {
			throw new FileReaderException("Unexpected end of file reached.");
		}
		// 更新时间

		// 第8、9个字节为第一个数据记录的位置.
		int i = abyte0[8];
		if (i < 0) {
			i += 256;
		}
		i += 256 * abyte0[9];
		i = --i / 32;
		i--;
		// i += 256 * abyte0[9];
		// i -= 264;
		// i = --i/32;
		// i--;
		try {
			stream.readFully(abyte0);
		} catch (EOFException e) {
			throw new FileReaderException("Unexpected end of file reached.");
		}
		return i;
	}

	private DBFField readFieldHeader() throws FileReaderException, IOException {
		byte abyte0[] = new byte[16];
		try {
			stream.readFully(abyte0);
		} catch (EOFException e) {
			throw new FileReaderException("Unexpected end of file reached.");
		}
		StringBuilder stringbuffer = new StringBuilder(10);
		for (int i = 0; i < 10; i++) {
			if (abyte0[i] == 0) {
				break;
			}
			stringbuffer.append((char) abyte0[i]);
		}
		char c = (char) abyte0[11];
		try {
			stream.readFully(abyte0);
		} catch (EOFException e) {
			throw new FileReaderException("Unexpected end of file reached.");
		}
		int j = abyte0[0];
		int k = abyte0[1];
		if (j < 0) {
			j += 256;
		}
		if (k < 0) {
			k += 256;
		}
		return new DBFField(stringbuffer.toString(), c, j, k);
	}
}
