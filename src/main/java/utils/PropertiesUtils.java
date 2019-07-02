package utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtils {
	private static Logger log = Logger.getLogger(PropertiesUtils.class.getName());
	private static final String filePath = "E:\\eclipse-workspace\\wsThesis\\com.sdh.downgit\\src\\main\\resume.properties";

	public static String getFromsha() {
		InputStream in = null;
		Properties p = new Properties();
		try {
			in = new FileInputStream(filePath);
			p.load(in);
			return p.getProperty("fromsha");
		} catch (IOException e) {
			e.printStackTrace();
			log.error("getFromsha error "+e);
			return getFromsha();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean updateSha(String sha) {
		Properties p = new Properties();
		p.setProperty("fromsha", sha);
		OutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			p.store(out, "");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("updateSha "+sha+" error: "+e);
			return false;
		}finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
