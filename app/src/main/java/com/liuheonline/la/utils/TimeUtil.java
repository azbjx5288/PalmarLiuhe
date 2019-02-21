package com.liuheonline.la.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author: aini
 * @date 2018/7/25 10:17
 * @description 开奖时间工具类
 */
public class TimeUtil {

    /**
     * @description 下期开奖倒计时
     * @param endTime 下期开奖时间
     * @return 剩余时间秒
     */
    public static long getNextDate(long endTime){
        Calendar cal=Calendar.getInstance();
        return (endTime-cal.getTime().getTime())/1000;
    }


    /**
     * @description 下期开奖倒计时
     * @param startTime 服务器时间 endTime 下期开奖时间
     * @return 剩余时间秒
     */
    public static long getNextDate(long startTime,long endTime){
        return (endTime-startTime);
    }

    //获取当前年份
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
}
