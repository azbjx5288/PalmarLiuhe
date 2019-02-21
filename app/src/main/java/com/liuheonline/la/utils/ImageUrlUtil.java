package com.liuheonline.la.utils;

/**
 * @author: aini
 * @date 2018/7/24 17:20
 * @description 图片地址拼接工具类
 */
public class ImageUrlUtil {
    /**
     * @description 图片地址简单拼接
     * @param url 图片url  w 宽度 h 高度
     * @return url
     */
    public static String getImgUrl(String url,int w,int h){
        return url+"?w="+w+"&h="+h+"&q=50";
    }

    /**
     * @description 图片地址简单拼接
     * @param url 图片url  w 宽度 h 高度 q 质量
     * @return url
     */
    public static String getImgUrl(String url,int w,int h,int q){
        return url+"?w="+w+"&h="+h+"&q="+q;
    }


    /**
     * @description 图片地址简单拼接
     * @param url 图片url q 质量
     * @return url
     */
    public static String getImgUrl(String url,int q){
        return url+"?q="+q;
    }
}
