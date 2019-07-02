package net;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class TextDownTest {
    private final static String url = "https://github.com/gchq/Gaffer/raw/a16243d5231a4d04a21b60894d91adfe3cbaba28/core/operation/src/test/java/uk/gov/gchq/gaffer/operation/impl/get/GetFromEndpointTest.java";

    public static void main(String[] args) {
        try {
            URL root = new URL(url);
            saveBinary(root);
        } catch (MalformedURLException e) {
            System.out.println(url + "is not URL");
//            log.info(url + "is not URL");
        } catch (Exception e) {
            System.out.println(e);
 //           log.info(url+"\t"+e);
        }
    }

    public static void saveBinary(URL u) throws IOException {
        URLConnection uc = u.openConnection();
        int contentLength = uc.getContentLength();

        try (InputStream raw = uc.getInputStream()) {
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
                throw new IOException("Only read " + offset
                        + " bytes; Expected " + contentLength + " bytes");
            }
            String filename = "E:\\方向论文\\隐性冲突\\down.java";
            FileOutputStream fout = new FileOutputStream(filename);
            fout.write(data);
            fout.flush();
            
        }catch (Exception e) {
            System.out.println(e);
//            log.info(u+"\t"+e);
        }
    }


}
