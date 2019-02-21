package com.liuheonline.la.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageService {

    public static Bitmap getImage(String website) {

        URL url = null;
        try {
            url = new URL(website);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
           /* conn.setRequestProperty("accesstoken", "F6E127C7-AE15-E6AF-D0F1-AD2A2E6A20C1");
            conn.setRequestProperty("Content-type", "application/json");*/
            conn.setRequestMethod("GET");
            if(conn.getResponseCode()==200){
                InputStream inputStream = conn.getInputStream();
                byte[] bytes = StreamTool.read(inputStream);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                return bitmap;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

     static class StreamTool {

        public static byte[] read(InputStream inputStream) throws Exception {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while((len = inputStream.read(buf))!=-1){
                baos.write(buf, 0 ,len);
            }
            baos.close();
            return buf;
        }

    }
}
