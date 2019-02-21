package com.liuheonline.la.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LotteryEntity {


    /**
     * lottery : {"id":1271,"periods":1,"next_periods":2,"sid":1,"num1":45,"num2":36,"num3":23,"num4":10,"num5":33,"num6":18,"num_s":7,"lottery_time":"2018-08-03","next_time":"2018-08-03 16:28","single":0,"double":7,"num1_color":"红波","num1_color_id":7,"num1_zodiac":"虎","num1_zodiac_id":12,"num2_color":"蓝波","num2_color_id":8,"num2_zodiac":"猪","num2_zodiac_id":21,"num3_color":"红波","num3_color_id":7,"num3_zodiac":"鼠","num3_zodiac_id":10,"num4_color":"蓝波","num4_color_id":8,"num4_zodiac":"牛","num4_zodiac_id":11,"num5_color":"绿波","num5_color_id":9,"num5_zodiac":"虎","num5_zodiac_id":12,"num6_color":"红波","num6_color_id":7,"num6_zodiac":"蛇","num6_zodiac_id":15,"num_s_color":"红波","num_s_color_id":7,"num_s_zodiac":"龙","num_s_zodiac_id":14}
     * winning_data : [{"winning_total":"97.60","order_id":85,"buyer_id":11092,"nickname":"aini"},{"winning_total":"1028.16","order_id":86,"buyer_id":11092,"nickname":"aini"},{"winning_total":"1028.16","order_id":87,"buyer_id":11092,"nickname":"aini"},{"winning_total":"4.08","order_id":88,"buyer_id":11092,"nickname":"aini"},{"winning_total":"97.60","order_id":89,"buyer_id":11092,"nickname":"aini"},{"winning_total":"97.60","order_id":92,"buyer_id":11092,"nickname":"aini"},{"winning_total":"97.60","order_id":93,"buyer_id":11092,"nickname":"aini"}]
     * announcement : 梵蒂冈法国让他别人帮他日本问题并未
     * slide : [{"pic":"img/20180717/5b4dc801e24f9.jpg","url":"1","pic_link":"http://120.79.189.244/images/img/20180717/5b4dc801e24f9.jpg"},{"pic":"img/20180717/5b4dcaeeeba2d.jpg","url":"2","pic_link":"http://120.79.189.244/images/img/20180717/5b4dcaeeeba2d.jpg"},{"pic":"img/20180717/5b4dcaddc8c67.jpg","url":"3","pic_link":"http://120.79.189.244/images/img/20180717/5b4dcaddc8c67.jpg"},{"pic":"img/20180717/5b4dcaeeebbb0.jpg","url":"4","pic_link":"http://120.79.189.244/images/img/20180717/5b4dcaeeebbb0.jpg"}]
     */

    private LotteryBean lottery;
    private String announcement;

    public String getApp_voice() {
        return app_voice;
    }

    public void setApp_voice(String app_voice) {
        this.app_voice = app_voice;
    }

    private String app_voice;
    private List<WinningDataBean> winning_data;
    private List<SlideBean> slide;

    public LotteryBean getLottery() {
        return lottery;
    }

    public void setLottery(LotteryBean lottery) {
        this.lottery = lottery;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public List<WinningDataBean> getWinning_data() {
        return winning_data;
    }

    public void setWinning_data(List<WinningDataBean> winning_data) {
        this.winning_data = winning_data;
    }

    public List<SlideBean> getSlide() {
        return slide;
    }

    public void setSlide(List<SlideBean> slide) {
        this.slide = slide;
    }

    public static class LotteryBean {
        public String getHeader_time() {
            return Header_time;
        }

        public void setHeader_time(String header_time) {
            Header_time = header_time;
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

        private String Header_time;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        private String number;
        private int id;
        private long periods;
        private long next_periods;
        private int sid;
        int lottery_id;

        public int getLottery_id() {
            return lottery_id;
        }

        public String getSpecies_name() {
            return species_name;
        }

        public void setSpecies_name(String species_name) {
            this.species_name = species_name;
        }

        private String species_name;
        private int num1;
        private int num2;
        private int num3;
        private int num4;
        private int num5;
        private int num6;
        private int num_s;
        private String lottery_time;
        private Long next_time;
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

        public int getShow_periods() {
            return show_periods;
        }

        public void setShow_periods(int show_periods) {
            this.show_periods = show_periods;
        }

        private int show_periods;

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

        public Long getNext_time() {
            return next_time;
        }

        public void setNext_time(Long next_time) {
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

        @Override
        public String toString() {
            return "LotteryBean{" +
                    "Header_time='" + Header_time + '\'' +
                    ", number='" + number + '\'' +
                    ", id=" + id +
                    ", periods=" + periods +
                    ", next_periods=" + next_periods +
                    ", sid=" + sid +
                    ", lottery_id=" + lottery_id +
                    ", species_name='" + species_name + '\'' +
                    ", num1=" + num1 +
                    ", num2=" + num2 +
                    ", num3=" + num3 +
                    ", num4=" + num4 +
                    ", num5=" + num5 +
                    ", num6=" + num6 +
                    ", num_s=" + num_s +
                    ", lottery_time='" + lottery_time + '\'' +
                    ", next_time=" + next_time +
                    ", single=" + single +
                    ", doubleX=" + doubleX +
                    ", num1_color='" + num1_color + '\'' +
                    ", num1_color_id=" + num1_color_id +
                    ", num1_zodiac='" + num1_zodiac + '\'' +
                    ", num1_zodiac_id=" + num1_zodiac_id +
                    ", num2_color='" + num2_color + '\'' +
                    ", num2_color_id=" + num2_color_id +
                    ", num2_zodiac='" + num2_zodiac + '\'' +
                    ", num2_zodiac_id=" + num2_zodiac_id +
                    ", num3_color='" + num3_color + '\'' +
                    ", num3_color_id=" + num3_color_id +
                    ", num3_zodiac='" + num3_zodiac + '\'' +
                    ", num3_zodiac_id=" + num3_zodiac_id +
                    ", num4_color='" + num4_color + '\'' +
                    ", num4_color_id=" + num4_color_id +
                    ", num4_zodiac='" + num4_zodiac + '\'' +
                    ", num4_zodiac_id=" + num4_zodiac_id +
                    ", num5_color='" + num5_color + '\'' +
                    ", num5_color_id=" + num5_color_id +
                    ", num5_zodiac='" + num5_zodiac + '\'' +
                    ", num5_zodiac_id=" + num5_zodiac_id +
                    ", num6_color='" + num6_color + '\'' +
                    ", num6_color_id=" + num6_color_id +
                    ", num6_zodiac='" + num6_zodiac + '\'' +
                    ", num6_zodiac_id=" + num6_zodiac_id +
                    ", num_s_color='" + num_s_color + '\'' +
                    ", num_s_color_id=" + num_s_color_id +
                    ", num_s_zodiac='" + num_s_zodiac + '\'' +
                    ", num_s_zodiac_id=" + num_s_zodiac_id +
                    ", show_periods=" + show_periods +
                    '}';
        }
    }

    public static class WinningDataBean {
        /**
         * winning_total : 97.60
         * order_id : 85
         * buyer_id : 11092
         * nickname : aini
         */

        private String winning_total;
        private int order_id;
        private int buyer_id;
        private String nickname;

        public String getOrder_species_name() {
            return order_species_name;
        }

        public void setOrder_species_name(String order_species_name) {
            this.order_species_name = order_species_name;
        }

        private String order_species_name;

        public String getWinning_total() {
            return winning_total;
        }

        public void setWinning_total(String winning_total) {
            this.winning_total = winning_total;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getBuyer_id() {
            return buyer_id;
        }

        public void setBuyer_id(int buyer_id) {
            this.buyer_id = buyer_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        @Override
        public String toString() {
            return "WinningDataBean{" +
                    "winning_total='" + winning_total + '\'' +
                    ", order_id=" + order_id +
                    ", buyer_id=" + buyer_id +
                    ", nickname='" + nickname + '\'' +
                    ", order_species_name='" + order_species_name + '\'' +
                    '}';
        }
    }

    public static class SlideBean {
        /**
         * pic : img/20180717/5b4dc801e24f9.jpg
         * url : 1
         * pic_link : http://120.79.189.244/images/img/20180717/5b4dc801e24f9.jpg
         */

        private String pic;
        private String url;
        private String pic_link;

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPic_link() {
            return pic_link;
        }

        public void setPic_link(String pic_link) {
            this.pic_link = pic_link;
        }

        @Override
        public String toString() {
            return "SlideBean{" +
                    "pic='" + pic + '\'' +
                    ", url='" + url + '\'' +
                    ", pic_link='" + pic_link + '\'' +
                    '}';
        }
    }
}
