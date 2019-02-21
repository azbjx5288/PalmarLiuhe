package com.liuheonline.la.utils;

import android.content.Context;

/**
 * @author <font color="pink"><b>aini</b></font>
 * @Date 2017/11/27
 * @Version 1.0
 * @Description
 */
public class Dip2pxUtil {
    private Dip2pxUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int  dip2px(Context context, Float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int  px2dip(Context context, Float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue /scale + 0.5f);
    }
}
