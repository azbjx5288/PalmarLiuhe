package com.liuheonline.la.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * @author: aini
 * @date 2018/6/13 10:20
 * @description 判断网络状态工具类
 */
public class NetWorkUtil {
    /**
     * @description 网络连接是否正常
     * @param context 上下文
     * @return true:有网络  false:无网络
     */
    public static boolean isNetworkConnected(Context context) {
        boolean isConnected = false;
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                isConnected = mNetworkInfo.isAvailable();
            }
        }
        if(!isConnected){
            Toast.makeText(context,"网络错误",Toast.LENGTH_LONG).show();
        }
        return isConnected;
    }
}
