package com.liuheonline.la.utils;

import com.ysyy.aini.palmarliuhe.R;

public class LotteryTypeUtil {

    /**
     * @function 通过波色获取对应背景图
     */
    public static int getBallBG(int id) {
        switch (id) {
            case 7:
                return R.mipmap.hongqiu;
            case 8:
                return R.mipmap.lanqiu;
            case 9:
                return R.mipmap.lvqiu;
            default:
                return R.mipmap.lvqiu;
        }
    }

    /**
     * @function 通过英文名得到中文名
     */
    public static String getChinese(String name) {
        switch (name) {
            case "chicken":
                return "鸡";
            case "dog":
                return "狗";
            case "dragon":
                return "龙";
            case "horse":
                return "马";
            case "monkey":
                return "猴";
            case "pig":
                return "猪";
            case "rabbit":
                return "兔";
            case "sheep":
                return "羊";
            case "snake":
                return "蛇";
            case "tiger":
                return "虎";
            case "cow":
                return "牛";
            case "rat":
                return "鼠";
            case "blue":
                return "蓝";
            case "green":
                return "绿";
            case "red":
                return "红";
            case "fire":
                return "火";
            case "gold":
                return "金";
            case "soil":
                return "土";
            case "water":
                return "水";
            case "wood":
                return "木";
            case "big":
                return "大";
            case "small":
                return "小";
            case "even":
                return "双";
            case "singular":
                return "单";
            case "head0":
                return "头1";
            case "head1":
                return "头2";
            case "head2":
                return "头3";
            case "head3":
                return "头4";
            case "head4":
                return "头5";
            case "footer0":
                return "尾1";
            case "footer1":
                return "尾2";
            case "footer2":
                return "尾3";
            case "footer3":
                return "尾4";
            case "footer4":
                return "尾5";
            case "footer5":
                return "尾6";
            case "footer6":
                return "尾7";
            case "footer7":
                return "尾8";
            case "footer8":
                return "尾9";
            case "footer9":
                return "尾10";


            default:
                return name;
        }
    }

    /**
     * @function 通过波色获取对应背景图
     */
    public static int getStasisticsBallBG(int id) {
        switch (id) {
            case 7:
                return R.mipmap.hongqiu11;
            case 8:
                return R.mipmap.lanqiu11;
            case 9:
                return R.mipmap.lvqiu11;
            default:
                return R.mipmap.lvqiu11;
        }
    }

    /**
     * @function 通过波色获取对应背景图
     */
    public static int getPureBallBG(int id) {
        switch (id) {
            case 7:
                return R.drawable.lottery_color_red;
            case 8:
                return R.drawable.lottery_color_blue;
            case 9:
                return R.drawable.lottery_color_green;
            default:
                return R.drawable.lottery_color_blue;
        }
    }

    /**
     * @function 通过波色获取对应背景图
     */
    public static int getPureBallBG2(int id) {
        switch (id) {
            case 7:
                return R.drawable.lottery_ring_color_red;
            case 8:
                return R.drawable.lottery_ring_color_blue;
            case 9:
                return R.drawable.lottery_ring_color_green;
            default:
                return R.drawable.lottery_ring_color_blue;
        }
    }

    /**
     * 通过号码
     */
    public static int getBallBG(String str) {
        switch (str) {
            case "1":
                return R.drawable.lottery_color_1;
            case "2":
                return R.drawable.lottery_color_2;
            case "3":
                return R.drawable.lottery_color_3;
            case "4":
                return R.drawable.lottery_color_4;
            case "5":
                return R.drawable.lottery_color_5;
            case "6":
                return R.drawable.lottery_color_6;
            case "7":
                return R.drawable.lottery_color_7;
            case "8":
                return R.drawable.lottery_color_8;
            case "9":
                return R.drawable.lottery_color_9;
            case "10":
                return R.drawable.lottery_color_10;

            default:
                return R.drawable.lottery_color_1;
        }
    }

    /**
     * @param zodiac
     * @return
     * @fuction 通过生肖名字获取对应图片
     */
    public static int getZodiac(String zodiac) {
        if (zodiac == null) {
            return R.mipmap.zhu;
        }
        switch (zodiac) {
            case "狗":
                return R.mipmap.gou;
            case "猴":
                return R.mipmap.hou;
            case "虎":
                return R.mipmap.hu;
            case "鸡":
                return R.mipmap.ji;
            case "龙":
                return R.mipmap.longs;
            case "马":
                return R.mipmap.ma;
            case "牛":
                return R.mipmap.niu;
            case "蛇":
                return R.mipmap.she;
            case "鼠":
                return R.mipmap.shu;
            case "兔":
                return R.mipmap.tu;
            case "羊":
                return R.mipmap.yang;
            case "猪":
                return R.mipmap.zhu;
            default:
                return R.mipmap.zhu;

        }
    }
}
