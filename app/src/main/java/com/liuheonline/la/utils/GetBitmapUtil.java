package com.liuheonline.la.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.mylove.loglib.JLog;
import com.liuheonline.la.ui.LiuHeApplication;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetBitmapUtil {

    public static ImageView imageView;
    private Bitmap mBitmap;
    private String mFileName;

    public GetBitmapUtil(ImageView imageViews) {
        imageView = imageViews;
        new Thread(connectNet).start();
    }

    /*
     * 连接网络
     * 由于在4.0中不允许在主线程中访问网络，所以需要在子线程中访问
     */
    private Runnable connectNet = new Runnable() {
        @Override
        public void run() {
            try {
                String filePath = LiuHeApplication.getQuickline() + "/api/com/checkcode";
//                String filePath = String.format("%s/api/com/checkcode", LiuHeApplication.getQuickline());
                JLog.v(filePath);
                mFileName = "test.png";

                //以下是取得图片的两种方法
                //////////////// 方法1：取得的是byte数组, 从byte数组生成bitmap
                byte[] data = getImage(filePath);
                if (data != null) {
                    mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap
                } else {
                    //Toast.makeText(TestActivity.this, "Image error!", 1).show();
                }
                ////////////////////////////////////////////////////////

                //******** 方法2：取得的是InputStream，直接从InputStream生成bitmap ***********/
//                mBitmap = BitmapFactory.decodeStream(getImageStream(filePath));
                //********************************************************************/

                // 发送消息，通知handler在主线程中更新UI
                connectHanlder.sendEmptyMessage(0);
//                Log.d(TAG, "set image ...");
            } catch (Exception e) {
                // Toast.makeText(TestActivity.this,"无法链接网络！", 1).show();
                e.printStackTrace();
            }

        }

    };

    private Handler connectHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            Log.d(TAG, "display image");
            // 更新UI，显示图片
            if (mBitmap != null) {
                imageView.setImageBitmap(mBitmap);// display image
            }
        }
    };

    /**
     * Get image from newwork
     *
     * @param path The path of image
     * @return byte[]
     * @throws Exception
     */
    public byte[] getImage(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestProperty("accesstoken", SharedperfencesUtil.getString(LiuHeApplication.context, "token"));
        conn.setRequestMethod("GET");
        String cookieValue = conn.getHeaderField("set-cookie");
        if (cookieValue != null) {
            // conn.setRequestProperty("cookie", cookieValue.substring(0, cookieValue.indexOf(";")));
            SharedperfencesUtil.setString(LiuHeApplication.context, "cookie", cookieValue.substring(0, cookieValue.indexOf(";")));
            Log.w("cookie", cookieValue.substring(0, cookieValue.indexOf(";")));
            //sessionid = cookieValue.substring(0, cookieValue.indexOf(";"));
            //获得到sessionId之后，可以将sessionId保存在磁盘，使用SharePreference
        }
        InputStream inStream = conn.getInputStream();
        JLog.d(conn.getResponseMessage());
        JLog.d(conn.getURL());
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return readStream(inStream);
        }
        return null;
    }

    /**
     * Get data from stream
     *
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }
}
