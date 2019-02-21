package com.liuheonline.la.entity;

import java.util.List;

/**
 * @author: aini
 * @date 2018/8/3 11:49
 * @description 单注订单详情
 */
public class BetLotteryLogDetailsEntity {


    /**
     * order_id : 162
     * bet_periods : 677
     * order_class_id : 1
     * lottery_class_name : 特码B
     * order_sn : 140586975947048092
     * lottery_num : 2
     * lottery_amount : 4.00
     * lottery_rebates_price : 0
     * order_state : 40
     * add_time : 2018-08-07 16:52:27
     * lottery_code : {"num1":20,"num2":1,"num3":44,"num4":2,"num5":17,"num6":9,"num_s":19,"num1_color":"蓝波","num1_color_id":8,"num1_zodiac":"兔","num1_zodiac_id":13,"num2_color":"红波","num2_color_id":7,"num2_zodiac":"狗","num2_zodiac_id":20,"num3_color":"绿波","num3_color_id":9,"num3_zodiac":"兔","num3_zodiac_id":13,"num4_color":"红波","num4_color_id":7,"num4_zodiac":"鸡","num4_zodiac_id":19,"num5_color":"绿波","num5_color_id":9,"num5_zodiac":"马","num5_zodiac_id":16,"num6_color":"蓝波","num6_color_id":8,"num6_zodiac":"虎","num6_zodiac_id":12,"num_s_color":"红波","num_s_color_id":7,"num_s_zodiac":"龙","num_s_zodiac_id":14}
     * issue : 201808070677
     * species_name : 六合彩
     * order_lottery : [{"lottery_name":"2","lottery_price":"2.00","state":2,"winning":"0.00"},{"lottery_name":"1","lottery_price":"2.00","state":2,"winning":"0.00"}]
     */

    private int order_id;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    private String pic;

    public String getPic_link() {
        return pic_link;
    }

    public void setPic_link(String pic_link) {
        this.pic_link = pic_link;
    }

    private String pic_link;
    private String bet_periods;
    private int order_class_id;
    private String lottery_class_name;
    private String order_sn;
    private int lottery_num;
    private String lottery_amount;
    private String lottery_rebates_price;
    private int order_state;
    private String add_time;
    private int species_id;//玩法id:7六合,8北京PK拾,9时时彩
    private LotteryCodeBean lottery_code;
    private String issue;
    private String species_name;
    private List<OrderLotteryBean> order_lottery;

    public int getSpecies_id() {
        return species_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getBet_periods() {
        return bet_periods;
    }

    public void setBet_periods(String bet_periods) {
        this.bet_periods = bet_periods;
    }

    public int getOrder_class_id() {
        return order_class_id;
    }

    public void setOrder_class_id(int order_class_id) {
        this.order_class_id = order_class_id;
    }

    public String getLottery_class_name() {
        return lottery_class_name;
    }

    public void setLottery_class_name(String lottery_class_name) {
        this.lottery_class_name = lottery_class_name;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getLottery_num() {
        return lottery_num;
    }

    public void setLottery_num(int lottery_num) {
        this.lottery_num = lottery_num;
    }

    public String getLottery_amount() {
        return lottery_amount;
    }

    public void setLottery_amount(String lottery_amount) {
        this.lottery_amount = lottery_amount;
    }

    public String getLottery_rebates_price() {
        return lottery_rebates_price;
    }

    public void setLottery_rebates_price(String lottery_rebates_price) {
        this.lottery_rebates_price = lottery_rebates_price;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public LotteryCodeBean getLottery_code() {
        return lottery_code;
    }

    public void setLottery_code(LotteryCodeBean lottery_code) {
        this.lottery_code = lottery_code;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getSpecies_name() {
        return species_name;
    }

    public void setSpecies_name(String species_name) {
        this.species_name = species_name;
    }

    public List<OrderLotteryBean> getOrder_lottery() {
        return order_lottery;
    }

    public void setOrder_lottery(List<OrderLotteryBean> order_lottery) {
        this.order_lottery = order_lottery;
    }

    public static class LotteryCodeBean {
        /**
         * num1 : 20
         * num2 : 1
         * num3 : 44
         * num4 : 2
         * num5 : 17
         * num6 : 9
         * num_s : 19
         * num1_color : 蓝波
         * num1_color_id : 8
         * num1_zodiac : 兔
         * num1_zodiac_id : 13
         * num2_color : 红波
         * num2_color_id : 7
         * num2_zodiac : 狗
         * num2_zodiac_id : 20
         * num3_color : 绿波
         * num3_color_id : 9
         * num3_zodiac : 兔
         * num3_zodiac_id : 13
         * num4_color : 红波
         * num4_color_id : 7
         * num4_zodiac : 鸡
         * num4_zodiac_id : 19
         * num5_color : 绿波
         * num5_color_id : 9
         * num5_zodiac : 马
         * num5_zodiac_id : 16
         * num6_color : 蓝波
         * num6_color_id : 8
         * num6_zodiac : 虎
         * num6_zodiac_id : 12
         * num_s_color : 红波
         * num_s_color_id : 7
         * num_s_zodiac : 龙
         * num_s_zodiac_id : 14
         */
        private int sid;
        private String number;
        private int num1;
        private int num2;
        private int num3;
        private int num4;
        private int num5;
        private int num6;
        private int num_s;
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

        public int getSid() {
            return sid;
        }

        public String getNumber() {
            return number;
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
            return "LotteryCodeBean{" +
                    "sid=" + sid +
                    ", number='" + number + '\'' +
                    ", num1=" + num1 +
                    ", num2=" + num2 +
                    ", num3=" + num3 +
                    ", num4=" + num4 +
                    ", num5=" + num5 +
                    ", num6=" + num6 +
                    ", num_s=" + num_s +
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
                    '}';
        }
    }

    public static class OrderLotteryBean {
        /**
         * lottery_name : 2
         * lottery_price : 2.00
         * state : 2
         * winning : 0.00
         */

        private String lottery_name;
        private String lottery_price;
        private int state;
        private String winning;

        public String getLottery_name() {
            return lottery_name;
        }

        public void setLottery_name(String lottery_name) {
            this.lottery_name = lottery_name;
        }

        public String getLottery_price() {
            return lottery_price;
        }

        public void setLottery_price(String lottery_price) {
            this.lottery_price = lottery_price;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getWinning() {
            return winning;
        }

        public void setWinning(String winning) {
            this.winning = winning;
        }

        @Override
        public String toString() {
            return "OrderLotteryBean{" +
                    "lottery_name='" + lottery_name + '\'' +
                    ", lottery_price='" + lottery_price + '\'' +
                    ", state=" + state +
                    ", winning='" + winning + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BetLotteryLogDetailsEntity{" +
                "order_id=" + order_id +
                ", pic='" + pic + '\'' +
                ", pic_link='" + pic_link + '\'' +
                ", bet_periods='" + bet_periods + '\'' +
                ", order_class_id=" + order_class_id +
                ", lottery_class_name='" + lottery_class_name + '\'' +
                ", order_sn='" + order_sn + '\'' +
                ", lottery_num=" + lottery_num +
                ", lottery_amount='" + lottery_amount + '\'' +
                ", lottery_rebates_price='" + lottery_rebates_price + '\'' +
                ", order_state=" + order_state +
                ", add_time='" + add_time + '\'' +
                ", species_id=" + species_id +
                ", lottery_code=" + lottery_code +
                ", issue='" + issue + '\'' +
                ", species_name='" + species_name + '\'' +
                ", order_lottery=" + order_lottery +
                '}';
    }
}
