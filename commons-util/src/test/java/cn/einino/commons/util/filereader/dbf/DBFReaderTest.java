package cn.einino.commons.util.filereader.dbf;

import cn.einino.commons.util.exception.FileReaderException;
import cn.einino.commons.util.filereader.FileReader;

public class DBFReaderTest {

	public static void main(String[] args) {
		try {
			FileReader reader = new DBFReader("D:/DBF/SJSHQ.DBF", "GBK");
			int time = 0;
			while (time < 1) {
				reader.readRecord();
				if (reader.hasNextRecord()) {
					String[] nextRecord = reader.nextRecord();
					System.out.print("Record:");
					for (int i = 0; i < nextRecord.length; ++i) {
						System.out.print(nextRecord[i].trim() + ",");
					}
					System.out.println();
				}
				try {
					Thread.sleep(10 * 1000);
				} catch (InterruptedException e) {
				}
				++time;
			}
			reader.close();
		} catch (FileReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
