package com.liuheonline.la.utils;

import java.util.Random;

/**
 * @author: aini
 * @date 2018/6/11 17:02
 * @description 静态常量类
 */
public class Constant {


   // public final static String BASEURL = urls[new Random().nextInt(6)];

    public final static String UP_URL = "http://l68.vip/";
   // public static final String BASE_URL = urls[new Random().nextInt(6)];

    public final static String PHONE_PATTERN = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$";

    public final static String IMG_PATH = "http://120.79.189.244/images/";

    /**
     * 短信类型
     */
    public final static String SENDTYPE_REG = "reg";  //注册

    public final static String SENDTYPE_LOGIN = "login";  //登录

    public final static String SENDTYPE_SMS = "sms"; //绑定

    public final static String SENDTYPE_FIND = "find";  //绑定手机 or 找回密码

}
