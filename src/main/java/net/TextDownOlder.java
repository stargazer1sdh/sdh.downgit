package net;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class TextDownOlder {
	private static Logger log = Logger.getLogger(TextDownOlder.class.getName());

	public static void down(URL u, String filename) {
		down(u,filename,false);
	}
	private static void down(URL u, String filename,boolean from2) {
		try {
			URLConnection uc = u.openConnection();
			int contentLength = uc.getContentLength();

			InputStream raw = uc.getInputStream();
			InputStream in = new BufferedInputStream(raw);
			byte[] data = new byte[contentLength];
			int offset = 0;
			while (offset < contentLength) {
				int bytesRead = in.read(data, offset, data.length - offset);
				if (bytesRead == -1) {
					break;
				}
				offset += bytesRead;
			}

			if (offset != contentLength) {
				throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
			}
			FileOutputStream fout = new FileOutputStream(filename);
			fout.write(data);
			fout.flush();

		} catch (Exception e) {
			System.out.println(e);
			log.info("from2:"+from2+"\t"+u + "\t" + e);
		}
	}

	public static void down(String url, String filename) {
		URL u = null;
		try {
			u = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			log.info(url + " MalformedURLException:\t" + e);
		}
		down(u, filename,true);
	}

}
