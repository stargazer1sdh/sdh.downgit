package net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.ServerException;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class GzipDownTest {

	public static void main(String[] args) throws Exception {
//		URL url = new URL("https://www.rgagnon.com/howto.html");
//	      HttpURLConnection con = (HttpURLConnection) url.openConnection();
//	      // con.setRequestProperty("Accept-Encoding", "gzip");
//	      System.out.println("Length : " + con.getContentLength());
//	      Reader reader = new InputStreamReader(con.getInputStream());
//	      int i=0;
//	      while (true) {
//	        int ch = reader.read();
//	        if (ch==-1) {
//	          break;
//	        }
//	        ++i;
//	        System.out.print((char)ch);
//	      }
//	      System.out.print(i);
		
//	      URL url = new URL("https://www.rgagnon.com/howto.html");
//	      HttpURLConnection con = (HttpURLConnection) url.openConnection();
//	      con.setRequestProperty("Accept-Encoding", "gzip");
//	      System.out.println("Length : " + con.getContentLength());
//
//	      Reader reader = null;
//	      if ("gzip".equals(con.getContentEncoding())) {
//	    	  System.out.println("gzip!!!!!!");
//	         reader = new InputStreamReader(new GZIPInputStream(con.getInputStream()));
//	      }
//	      else {
//	         reader = new InputStreamReader(con.getInputStream());
//	      }
//
//	      while (true) {
//	         int ch = reader.read();
//	         if (ch==-1) {
//	            break;
//	         }
//	         System.out.print((char)ch);
//	      }
		
//		download("https://avatar.csdn.net/5/A/4/3_zwxshine.jpg", "new.png", "C:\\Users\\sdh\\Desktop");	
//		download("https://www.rgagnon.com/howto.html", "nogzip.html", "C:\\Users\\sdh\\Desktop");	
//		download("https://github.com/gchq/Gaffer/raw/59e8b3311d9d8e8dc8af7eab8541d33943a393d1/store-implementation/parquet-store/src/test/java/uk/gov/gchq/gaffer/parquetstore/utils/WriteUnsortedDataTest.java" + 
//				"", "zip.html", "C:\\Users\\sdh\\Desktop");	
//		GzipDown.down("https://raw.githubusercontent.com/dianping/cat/c197ba8e56836ed887a46894ac65c8e440ff4732/%E6%A1%86%E6%9E%B6%E5%9F%8B%E7%82%B9%E6%96%B9%E6%A1%88%E9%9B%86%E6%88%90/project_readme_201301.md",
//"C:\\Users\\sdh\\Desktop\\md.md");	
		GzipDown.down("https://github.com/dianping/cat/raw/c197ba8e56836ed887a46894ac65c8e440ff4732/框架埋点方案集成/project_readme_201301.md",
				"C:\\Users\\sdh\\Desktop\\md.md");	
	   }
//	public static String doPost(String url) throws ServerException {
//        PrintWriter out = null;
//        BufferedReader in = null;
//        String result = "";
//        InputStream ism = null;
//        HttpURLConnection conn = null;
//        try {
//            URL realUrl = new URL(url);
//            // 打开和URL之间的连接
//            conn = (HttpURLConnection) realUrl
//                    .openConnection();
//            // 设置通用的请求属性
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestMethod("GET");
////            conn.setRequestProperty("charset", "utf-8");
//            conn.setRequestProperty("Accept-Encoding", "gzip");//gzip
//            conn.setUseCaches(false);
////            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
////            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
//
//                // 获取URLConnection对象对应的输出流
//            out = new PrintWriter(conn.getOutputStream());
//            // 发送请求参数
//            // flush输出流的缓冲
//            out.flush();
//            //5.得到服务器相应
//            if (conn.getResponseCode() == 200) {
//                System.out.println("服务器已经收到表单数据！");
//            } else {
//                System.out.println("请求失败！");
//            }
//
//            String encoding = conn.getContentEncoding();
//            ism = new BufferedInputStream(conn.getInputStream());
//            if (encoding != null && encoding.contains("gzip")) {//首先判断服务器返回的数据是否支持gzip压缩，
//                //如果支持则应该使用GZIPInputStream解压，否则会出现乱码无效数据
//                ism = new GZIPInputStream(conn.getInputStream());
//            }
//            // 定义BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(
//                    new InputStreamReader(ism));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//            FileOutputStream fout = new FileOutputStream("C:\\Users\\sdh\\Desktop\\new.jpg");
//            fout.
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ServerException("服务器错误");
//        }
//        // 使用finally块来关闭输出流、输入流
//        finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//                if (in != null) {
//                    in.close();
//                }
//                if (conn != null) {
//                    conn.disconnect();
//                }
//
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return result;
//    }

	public static void download(String urlString, String filename, String savePath) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestProperty("Accept-Encoding", "gzip");
        System.out.println("http response code:"+con.getResponseCode());
        System.out.println("con.getContentLength:"+con.getContentLength());
        
        Map map = con.getHeaderFields();  
        Iterator it = map.keySet().iterator();   
          
        boolean gzip = false;  
        while (it.hasNext()) {  
           Object type =  map.get(it.next());  
           if (type.toString().indexOf("gzip") != -1) {  
               gzip = true;  
               break;  
           }  
        } 
        System.out.println(con.getContentEncoding()); //也可以用con.getContentEncoding()来判断是否gzip
        System.out.println(gzip);
        
        //con.setConnectTimeout(5 * 1000);
        InputStream is = con.getInputStream();
        byte[] bs = new byte[1024];
        int len;
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
 
        OutputStream os = new FileOutputStream(sf.getPath() + "/" + filename);
        if (!gzip) { 
	        while ( (len = is.read(bs)) != -1 ) {
	            os.write(bs, 0, len);
	        }
        } else {
        	GZIPInputStream gis = new GZIPInputStream(is);   
            while ( (len = gis.read(bs)) != -1 ) {  
               os.write(bs, 0, len);                    
           } 
        }
        
        os.flush();
        os.close();
        is.close();
    }

}
