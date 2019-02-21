package com.liuheonline.la.entity;

import com.google.gson.annotations.SerializedName;

public class LotteryCategoryEntity {

    /**
     * id : 1
     * title : 六合彩
     * pic : img/20180717/5b4d6fa5a0d7f.png
     * sort : 2
     * pic_link : http://120.79.189.244/images/img/20180717/5b4d6fa5a0d7f.png
     */

    private int id;
    private String title;
    private String pic;

    public int getLottery_id() {
        return lottery_id;
    }

    public void setLottery_id(int lottery_id) {
        this.lottery_id = lottery_id;
    }

    private int lottery_id;

    public String getRules_content() {
        return rules_content;
    }

    public void setRules_content(String rules_content) {
        this.rules_content = rules_content;
    }

    private String rules_content;
    private int sort;
    private String pic_link;
    private long fengpan;
    private String headerTime;
    private LotteryBean lottery;


    public long getFengpan() {
        return fengpan;
    }

    public void setFengpan(long fengpan) {
        this.fengpan = fengpan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getPic_link() {
        return pic_link;
    }

    public void setPic_link(String pic_link) {
        this.pic_link = pic_link;
    }

    public String getHeaderTime() {
        return headerTime;
    }

    public void setHeaderTime(String headerTime) {
        this.headerTime = headerTime;
    }

    public LotteryBean getLottery() {
        return lottery;
    }

    public void setLottery(LotteryBean lottery) {
        this.lottery = lottery;
    }



    public static class LotteryBean {
        public String getHeader_time() {
            return Header_time;
        }

        public void setHeader_time(String header_time) {
            Header_time = header_time;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        /**
         * id : 1271
         * periods : 1
         * next_periods : 2
         * sid : 1
         * num1 : 45
         * num2 : 36
         * num3 : 23
         * num4 : 10
         * num5 : 33
         * num6 : 18
         * num_s : 7
         * lottery_time : 2018-08-03
         * next_time : 2018-08-03 16:28
         * single : 0
         * double : 7
         * num1_color : 红波
         * num1_color_id : 7
         * num1_zodiac : 虎
         * num1_zodiac_id : 12
         * num2_color : 蓝波
         * num2_color_id : 8
         * num2_zodiac : 猪
         * num2_zodiac_id : 21
         * num3_color : 红波
         * num3_color_id : 7
         * num3_zodiac : 鼠
         * num3_zodiac_id : 10
         * num4_color : 蓝波
         * num4_color_id : 8
         * num4_zodiac : 牛
         * num4_zodiac_id : 11
         * num5_color : 绿波
         * num5_color_id : 9
         * num5_zodiac : 虎
         * num5_zodiac_id : 12
         * num6_color : 红波
         * num6_color_id : 7
         * num6_zodiac : 蛇
         * num6_zodiac_id : 15
         * num_s_color : 红波
         * num_s_color_id : 7
         * num_s_zodiac : 龙
         * num_s_zodiac_id : 14
         */

        private String number;
        private String Header_time;
        private int id;
        private long periods;
        private long next_periods;
        private int sid;
        private int num1;
        private int num2;
        private int num3;
        private int num4;
        private int num5;
        private int num6;
        private int num_s;
        private String lottery_time;
        private long next_time;
        private int single;
        @SerializedName("double")
        private int doubleX;
        private String num1_color;
        private int num1_color_id;
        private String num1_zodiac;
        private int num1_zodiac_id;
        private String num2_color;
        private int num2_color_id;
        private String num2_zodiac;
        private int num2_zodiac_id;
        private String num3_color;
        private int num3_color_id;
        private String num3_zodiac;
        private int num3_zodiac_id;
        private String num4_color;
        private int num4_color_id;
        private String num4_zodiac;
        private int num4_zodiac_id;
        private String num5_color;
        private int num5_color_id;
        private String num5_zodiac;
        private int num5_zodiac_id;
        private String num6_color;
        private int num6_color_id;
        private String num6_zodiac;
        private int num6_zodiac_id;
        private String num_s_color;
        private int num_s_color_id;
        private String num_s_zodiac;
        private int num_s_zodiac_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getPeriods() {
            return periods;
        }

        public void setPeriods(int periods) {
            this.periods = periods;
        }

        public long getNext_periods() {
            return next_periods;
        }

        public void setNext_periods(long next_periods) {
            this.next_periods = next_periods;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public int getNum1() {
            return num1;
        }

        public void setNum1(int num1) {
            this.num1 = num1;
        }

        public int getNum2() {
            return num2;
        }

        public void setNum2(int num2) {
            this.num2 = num2;
        }

        public int getNum3() {
            return num3;
        }

        public void setNum3(int num3) {
            this.num3 = num3;
        }

        public int getNum4() {
            return num4;
        }

        public void setNum4(int num4) {
            this.num4 = num4;
        }

        public int getNum5() {
            return num5;
        }

        public void setNum5(int num5) {
            this.num5 = num5;
        }

        public int getNum6() {
            return num6;
        }

        public void setNum6(int num6) {
            this.num6 = num6;
        }

        public int getNum_s() {
            return num_s;
        }

        public void setNum_s(int num_s) {
            this.num_s = num_s;
        }

        public String getLottery_time() {
            return lottery_time;
        }

        public void setLottery_time(String lottery_time) {
            this.lottery_time = lottery_time;
        }

        public long getNext_time() {
            return next_time;
        }

        public void setNext_time(long next_time) {
            this.next_time = next_time;
        }

        public int getSingle() {
            return single;
        }

        public void setSingle(int single) {
            this.single = single;
        }

        public int getDoubleX() {
            return doubleX;
        }

        public void setDoubleX(int doubleX) {
            this.doubleX = doubleX;
        }

        public String getNum1_color() {
            return num1_color;
        }

        public void setNum1_color(String num1_color) {
            this.num1_color = num1_color;
        }

        public int getNum1_color_id() {
            return num1_color_id;
        }

        public void setNum1_color_id(int num1_color_id) {
            this.num1_color_id = num1_color_id;
        }

        public String getNum1_zodiac() {
            return num1_zodiac;
        }

        public void setNum1_zodiac(String num1_zodiac) {
            this.num1_zodiac = num1_zodiac;
        }

        public int getNum1_zodiac_id() {
            return num1_zodiac_id;
        }

        public void setNum1_zodiac_id(int num1_zodiac_id) {
            this.num1_zodiac_id = num1_zodiac_id;
        }

        public String getNum2_color() {
            return num2_color;
        }

        public void setNum2_color(String num2_color) {
            this.num2_color = num2_color;
        }

        public int getNum2_color_id() {
            return num2_color_id;
        }

        public void setNum2_color_id(int num2_color_id) {
            this.num2_color_id = num2_color_id;
        }

        public String getNum2_zodiac() {
            return num2_zodiac;
        }

        public void setNum2_zodiac(String num2_zodiac) {
            this.num2_zodiac = num2_zodiac;
        }

        public int getNum2_zodiac_id() {
            return num2_zodiac_id;
        }

        public void setNum2_zodiac_id(int num2_zodiac_id) {
            this.num2_zodiac_id = num2_zodiac_id;
        }

        public String getNum3_color() {
            return num3_color;
        }

        public void setNum3_color(String num3_color) {
            this.num3_color = num3_color;
        }

        public int getNum3_color_id() {
            return num3_color_id;
        }

        public void setNum3_color_id(int num3_color_id) {
            this.num3_color_id = num3_color_id;
        }

        public String getNum3_zodiac() {
            return num3_zodiac;
        }

        public void setNum3_zodiac(String num3_zodiac) {
            this.num3_zodiac = num3_zodiac;
        }

        public int getNum3_zodiac_id() {
            return num3_zodiac_id;
        }

        public void setNum3_zodiac_id(int num3_zodiac_id) {
            this.num3_zodiac_id = num3_zodiac_id;
        }

        public String getNum4_color() {
            return num4_color;
        }

        public void setNum4_color(String num4_color) {
            this.num4_color = num4_color;
        }

        public int getNum4_color_id() {
            return num4_color_id;
        }

        public void setNum4_color_id(int num4_color_id) {
            this.num4_color_id = num4_color_id;
        }

        public String getNum4_zodiac() {
            return num4_zodiac;
        }

        public void setNum4_zodiac(String num4_zodiac) {
            this.num4_zodiac = num4_zodiac;
        }

        public int getNum4_zodiac_id() {
            return num4_zodiac_id;
        }

        public void setNum4_zodiac_id(int num4_zodiac_id) {
            this.num4_zodiac_id = num4_zodiac_id;
        }

        public String getNum5_color() {
            return num5_color;
        }

        public void setNum5_color(String num5_color) {
            this.num5_color = num5_color;
        }

        public int getNum5_color_id() {
            return num5_color_id;
        }

        public void setNum5_color_id(int num5_color_id) {
            this.num5_color_id = num5_color_id;
        }

        public String getNum5_zodiac() {
            return num5_zodiac;
        }

        public void setNum5_zodiac(String num5_zodiac) {
            this.num5_zodiac = num5_zodiac;
        }

        public int getNum5_zodiac_id() {
            return num5_zodiac_id;
        }

        public void setNum5_zodiac_id(int num5_zodiac_id) {
            this.num5_zodiac_id = num5_zodiac_id;
        }

        public String getNum6_color() {
            return num6_color;
        }

        public void setNum6_color(String num6_color) {
            this.num6_color = num6_color;
        }

        public int getNum6_color_id() {
            return num6_color_id;
        }

        public void setNum6_color_id(int num6_color_id) {
            this.num6_color_id = num6_color_id;
        }

        public String getNum6_zodiac() {
            return num6_zodiac;
        }

        public void setNum6_zodiac(String num6_zodiac) {
            this.num6_zodiac = num6_zodiac;
        }

        public int getNum6_zodiac_id() {
            return num6_zodiac_id;
        }

        public void setNum6_zodiac_id(int num6_zodiac_id) {
            this.num6_zodiac_id = num6_zodiac_id;
        }

        public String getNum_s_color() {
            return num_s_color;
        }

        public void setNum_s_color(String num_s_color) {
            this.num_s_color = num_s_color;
        }

        public int getNum_s_color_id() {
            return num_s_color_id;
        }

        public void setNum_s_color_id(int num_s_color_id) {
            this.num_s_color_id = num_s_color_id;
        }

        public String getNum_s_zodiac() {
            return num_s_zodiac;
        }

        public void setNum_s_zodiac(String num_s_zodiac) {
            this.num_s_zodiac = num_s_zodiac;
        }

        public int getNum_s_zodiac_id() {
            return num_s_zodiac_id;
        }

        public void setNum_s_zodiac_id(int num_s_zodiac_id) {
            this.num_s_zodiac_id = num_s_zodiac_id;
        }
    }
}
