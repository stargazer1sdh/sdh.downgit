package net;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;

public class GzipDown {
	private static Logger log = Logger.getLogger(GzipDown.class.getName());

	private static boolean down(URL u, String filename) throws Exception {
		FileOutputStream fout = null;InputStream raw = null;InputStream in =null;GZIPInputStream gis = null;
		try {
			URLConnection con = u.openConnection();
			con.setConnectTimeout(60000*3);
			con.setReadTimeout(60000*5);
			con.setRequestProperty("Accept-Encoding", "gzip");
//			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
			String encoding = con.getContentEncoding();
			boolean gzip = encoding!=null && encoding.contains("gzip") ? true : false;
			raw = con.getInputStream();
			in = new BufferedInputStream(raw);
			byte[] data = new byte[1024];
			fout = new FileOutputStream(filename);
			int len;
			if (!gzip) { 
		        while ( (len = in.read(data)) != -1 ) {
		            fout.write(data, 0, len);
		        }
	        }else {
	        	gis = new GZIPInputStream(in);   
	            while ( (len = gis.read(data)) != -1 ) {  
	               fout.write(data, 0, len);                    
	           } 
	        }
			
			fout.flush();
			return true;
		} catch (FileNotFoundException e) {
			System.out.println(u+" : "+e);
//			log.info(u + " FileNotFound: " + e);
			throw e;
		}catch (Exception e1) {
			e1.printStackTrace();
			log.info(u + " : " + e1);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return false;
		}finally {
			try {
				if(in!=null)
					in.close();
				if(gis!=null)
					gis.close();
				if(raw!=null)
					raw.close();
				if(fout!=null)
					fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean down(String urlPath, String filename){
		URL u = null;
		try {
//			String  headPath= urlPath.substring( 0 , urlPath.lastIndexOf("/")+1);
//			String  endPath=urlPath.substring(  urlPath.lastIndexOf("/")+1  ); 
//			endPath = URLEncoder.encode(endPath,"UTF-8").replaceAll("\\+", "%20");
//			urlPath = headPath+endPath;
			String[] splits = urlPath.split("/");
			StringBuilder sb = new StringBuilder(splits[0]);
			for(int i=1;i<splits.length;++i) {
				sb.append("/");
				sb.append(URLEncoder.encode(splits[i],"UTF-8").replaceAll("\\+", "%20"));
			}
			urlPath = sb.toString();
			u = new URL(urlPath);
		} catch (MalformedURLException e) {
			System.out.println(e);
			log.info(urlPath + " MalformedURLException:\t" + e);
		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
			log.info(urlPath + " UnsupportedEncodingException:\t" + e);
		}
		try {
			while(!down(u, filename));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void main(String[] args ) {
//		String urlPath = "https://github.com/Adobe-Consulting-Services/acs-aem-commons/raw/1cbfc7f9e4688eebd3e9941dabe3260d773cb355/bundle/src/test/resources/ch/randelshofer/media/jpeg/Generic CMYK Profile.icc";
//		String  headPath= urlPath.substring( 0 , urlPath.lastIndexOf("/")+1);
//		String  endPath=urlPath.substring(  urlPath.lastIndexOf("/")+1  ); 
//		try {
//			endPath = (URLEncoder.encode(endPath,"UTF-8"));
//			System.out.println(endPath);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		urlPath = headPath+endPath;
		//		down("https://github.com/gchq/Gaffer/raw/59e8b3311d9d8e8dc8af7eab8541d33943a393d1/store-implementation/parquet-store/src/test/java/uk/gov/gchq/gaffer/parquetstore/utils/WriteUnsortedDataTest.java","C:\\Users\\sdh\\Desktop/WriteUnsortedDataTest.java");
//		down("https://avatar.csdn.net/5/A/4/3_zwxshine.jpg","C:\\Users\\sdh\\Desktop/new2.jpg");
//		down(urlPath,"C:\\Users\\sdh\\Desktop/new2.jpg");
//		down("https://github.com/Adobe-Consulting-Services/acs-aem-commons/raw/1cbfc7f9e4688eebd3e9941dabe3260d773cb355/bundle/src/test/resources/ch/randelshofer/media/jpeg/Generic","C:\\Users\\sdh\\Desktop/no.icc");
		down("https://github.com/Adobe-Consulting-Services/acs-aem-commons/raw/1cbfc7f9e4688eebd3e9941dabe3260d773cb355/bundle/src/test/resources/ch/randelshofer/media/jpeg/Generic CMYK Profile.icc","C:\\Users\\sdh\\Desktop/no.icc");
		down("https://github.com/Adobe-Consulting-Services/acs-aem-commons/raw/47802f1239a82d393096debd37dddf28d083f835/bundle/src/test/resources/ch/randelshofer/media/jpeg/Generic CMYK Profile.icc","C:\\Users\\sdh\\Desktop/yes.icc");
//		down("https://github.com/Adobe-Consulting-Services/acs-aem-commons/raw/1cbfc7f9e4688eebd3e9941dabe3260d773cb355/bundle/src/test/java/ch/randelshofer/media/io/ByteArrayImageInputStream.java","C:\\Users\\sdh\\Desktop/new.java");
	}
	
}
