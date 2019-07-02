package net;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class TextDown {
	private static Logger log = Logger.getLogger(TextDown.class.getName());

	private static boolean down(URL u, String filename) throws Exception {
		FileOutputStream fout = null;InputStream raw = null;InputStream in =null;
		try {
			URLConnection uc = u.openConnection();
			uc.setConnectTimeout(60000*3);
//			uc.setReadTimeout(60000*5);
			int contentLength = uc.getContentLength();

			raw = uc.getInputStream();
			in = new BufferedInputStream(raw);
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
			fout = new FileOutputStream(filename);
			fout.write(data);
			fout.flush();
			return true;
		} catch (FileNotFoundException e) {
			System.out.println(u+" : "+e);
			log.info(u + "\t" + e);
			throw e;
		}catch (Exception e1) {
			System.out.println(u+" : "+e1);
			log.info(u + " : " + e1);
			Thread.sleep(10000);
			return false;
		}finally {
			try {
				if(in!=null)
					in.close();
				if(raw!=null)
					raw.close();
				if(fout!=null)
					fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean down(String url, String filename){
		URL u = null;
		try {
			u = new URL(url);
		} catch (MalformedURLException e) {
			System.out.println(e);
			log.info(url + " MalformedURLException:\t" + e);
		}
		try {
			while(!down(u, filename));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
//	public static String readFileByUrl(String urlStr) {
//        String res=null;
//        try {
//            URL url = new URL(urlStr);  
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
//            //设置超时间为3秒
//            conn.setConnectTimeout(3*1000);
//            //防止屏蔽程序抓取而返回403错误
//            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//            //得到输入流
//            InputStream inputStream = conn.getInputStream();  
//            res = readInputStream(inputStream);
//        } catch (Exception e) {
//        	e.printStackTrace();
//  //          logger.error("通过url地址获取文本内容失败 Exception：" + e);
//        }
//        return res;
//    }
//	/**
//     * 从输入流中获取字符串
//     * @param inputStream
//     * @return
//     * @throws IOException
//     */
//    public static String readInputStream(InputStream inputStream) throws IOException {  
//        byte[] buffer = new byte[1024];  
//        int len = 0;  
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
//        while((len = inputStream.read(buffer)) != -1) {  
//            bos.write(buffer, 0, len);  
//        }  
//        bos.close();  
//        System.out.println(new String(bos.toByteArray(),"utf-8"));
//        return new String(bos.toByteArray(),"utf-8");
//    }
	public static void main(String[] args ) {
	}
}
