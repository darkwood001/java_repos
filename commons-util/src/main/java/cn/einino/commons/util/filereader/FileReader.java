package cn.einino.commons.util.filereader;

import cn.einino.commons.util.exception.FileReaderException;

public interface FileReader {

	void readRecord() throws FileReaderException;

	String[] nextRecord() throws FileReaderException;

	boolean hasNextRecord();

	void close() throws FileReaderException;
}
