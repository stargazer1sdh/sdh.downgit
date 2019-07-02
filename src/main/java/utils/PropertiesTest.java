package utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesTest {

	public static void main(String[] args) {
		String filePath = "E:\\eclipse-workspace\\wsThesis\\com.sdh.downgit\\src\\main\\resume.properties";
//		InputStream in = Test.class.getClassLoader().getResourceAsStream("resume.properties");
		InputStream in = null;
		Properties p = new Properties();
		try {
			in = new FileInputStream(filePath);
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(p.getProperty("fromsha"));
		
		p.setProperty("fromsha", "ne wval ");
		OutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			p.store(out, "");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
