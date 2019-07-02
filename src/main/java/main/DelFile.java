package main;
import java.io.File;

import org.apache.log4j.Logger;

public class DelFile {
	private static Logger log = Logger.getLogger(DelFile.class.getName());
	public static void delDir(File file) {
		if (file.isDirectory()) {
			File zFiles[] = file.listFiles();
			for (File file2 : zFiles) {
				delDir(file2);
			}
			while(file.exists() && !file.delete()) {
//				System.gc();
				log.info(file+" del fail");
			}
		} else {
			while(file.exists() && !file.delete()) {
//				System.gc();
				log.info(file+" del fail");
			}
		}
	}
}
