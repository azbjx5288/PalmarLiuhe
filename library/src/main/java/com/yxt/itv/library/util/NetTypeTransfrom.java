package com.yxt.itv.library.util;

import java.nio.charset.Charset;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/21
 * @Version 1.0
 * @Description  字节数组转换 工具类
 */
public class NetTypeTransfrom {

    private NetTypeTransfrom() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    /**
     * @author <font color="pink"><b>Deng.Xin</b></font>
     * @param n
     * @return byte[]
     * @date 2017/11/21
     * @version 1.0
     * @description  int 类型转换为 字节数组
     */
    public static byte[] tolh(int n) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (n & 0xff);
        bytes[1] = (byte) (n >> 8 & 0xff);
        bytes[2] = (byte) (n >> 16 & 0xff);
        bytes[3] = (byte) (n >> 24 & 0xff);
        return bytes;
    }
    /**
     * @author <font color="pink"><b>Deng.Xin</b></font>
     * @param bytes
     * @param maxlen
     * @return java.lang.String
     * @date 2017/11/21
     * @version 1.0
     * @description  字节数组转换为字符串
     */
    public static String toStr(byte[] bytes, int maxlen) {
        int index = 0;
        while (index < bytes.length && index < maxlen) {
            if (bytes[index] == 0) {
                break;
            }
            index++;
        }
        byte[] temp = new byte[index];
        System.arraycopy(bytes, 0, temp, 0, index);
        return new String(temp, Charset.forName("GBK"));
    }

    /**
     * @author <font color="pink"><b>Deng.Xin</b></font>
     * @param bytes
     * @return int
     * @date 2017/11/21
     * @version 1.0
     * @description 字节数组转换为int
     */
    public static int toInt(byte[] bytes) {
        int n = 0, j, i = 0;
        while (i< bytes.length && i<4){
            int left = i * 8;
            if (bytes[i]<0){
                j = 256+bytes[i];
            }else {
                j = bytes[i];
            }
            n+= j << left;
            i++;
        }
        return n;
    }
}
