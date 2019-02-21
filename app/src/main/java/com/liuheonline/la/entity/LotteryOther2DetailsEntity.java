package com.liuheonline.la.entity;

/**
 * @author: aini
 * @date 2018/7/28 16:27
 * @description 订单详情类
 */
public class LotteryOther2DetailsEntity {


    /**
     * lottery_class_id : 玩法一级ID
     * lottery_class_name : 玩法一级name
     * lottery_name : 投注内容
     * lottery_rebates_price : 返点
     * lottery_price : 单注金额
     * lottery_id : 玩法二级ID
     * lottery_child_name:玩法二级name
     */

    private String lottery_name;
    private int lottery_price;
    private int lottery_id;

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    private double odds;

    public String getLottery_name() {
        return lottery_name;
    }

    public void setLottery_name(String lottery_name) {
        this.lottery_name = lottery_name;
    }

    public int getLottery_price() {
        return lottery_price;
    }

    public void setLottery_price(int lottery_price) {
        this.lottery_price = lottery_price;
    }

    public int getLottery_id() {
        return lottery_id;
    }

    public void setLottery_id(int lottery_id) {
        this.lottery_id = lottery_id;
    }
}
