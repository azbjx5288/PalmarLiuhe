package com.liuheonline.la.utils;

import android.text.TextUtils;
import android.util.Log;

import com.liuheonline.la.ui.LiuHeApplication;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * @author: aini
 * @date 2018/7/20 20:37
 * @description 字符串工具类
 */
public class StringUtil {

    public static boolean noEmpty(String originStr) {
        return !TextUtils.isEmpty(originStr);
    }


    public static String getPrice(String price){
        try{
            double dprice = Double.parseDouble(price);
            double zc = Double.parseDouble(SharedperfencesUtil.getString(LiuHeApplication.context,"zc_moshi"));
            if (zc==0){
                zc=100;
            }
            Log.w("thedouble",dprice+" ---  "+zc);
            String total = new DecimalFormat("0.00").format(dprice*zc/100);
            return total;
        }catch (Exception e){
            return price;
        }

    }


    public static String getDates(String name){
        switch (name){
            case "today":
                return "今天";
            case "yesterday":
                return "昨天";
            case "week":
                return "本周";
            case "lastWeek":
                return "上周";
            case "month":
                return "本月";
            case "lastMonth":
                return "上月";
            case "year":
                return "今年";
            case "lastYear":
                return "去年";
        }
        return "";
    }
    public static boolean noEmpty(String... originStr) {
        boolean noEmpty = true;
        for (String s : originStr) {
            if (TextUtils.isEmpty(s)) {
                noEmpty = false;
                break;
            }
        }
        return noEmpty;
    }

    /**
     * 从资源文件拿到文字
     */
    public static String getResourceString(int strId) {
        String result = "";
        if (strId > 0) {
            result = LiuHeApplication.context.getResources().getString(strId);
        }
        return result;
    }

    /**
     * 从资源文件得到文字并format
     */
    public static String getResourceStringAndFormat(int strId, Object... objs) {
        String result = "";
        if (strId > 0) {
            result = String.format(Locale.getDefault(), LiuHeApplication.context.getResources().getString(strId), objs);
        }
        return result;
    }

    public static String translation(String content) {
        String replace = content.replace("&lt;", "<");
        String replace1 = replace.replace("&gt;", ">");
        String replace2 = replace1.replace("&amp;", "&");
        String replace3 = replace2.replace("&quot;", "\"");
        return replace3.replace("&copy;", "©");
    }

}