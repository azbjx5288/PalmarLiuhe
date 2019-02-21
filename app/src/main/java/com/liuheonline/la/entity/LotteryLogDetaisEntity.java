package com.liuheonline.la.entity;

/**
 * @author: aini
 * @date 2018/8/2 18:01
 * @description 订单详情
 */
public class LotteryLogDetaisEntity {

    /**
     * lottery_price_total : 20.00
     * lottery_num_total : 10
     * lottery_class_name : 特码B
     * buyer_id : 11092
     * lottery_class_id : 1
     * order_id : 83
     */

    private String lottery_price_total;
    private String lottery_num_total;
    private String lottery_class_name;
    private int buyer_id;
    private int lottery_class_id;
    private int order_id;

    public String getLottery_price_total() {
        return lottery_price_total;
    }

    public void setLottery_price_total(String lottery_price_total) {
        this.lottery_price_total = lottery_price_total;
    }

    public String getLottery_num_total() {
        return lottery_num_total;
    }

    public void setLottery_num_total(String lottery_num_total) {
        this.lottery_num_total = lottery_num_total;
    }

    public String getLottery_class_name() {
        return lottery_class_name;
    }

    public void setLottery_class_name(String lottery_class_name) {
        this.lottery_class_name = lottery_class_name;
    }

    public int getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(int buyer_id) {
        this.buyer_id = buyer_id;
    }

    public int getLottery_class_id() {
        return lottery_class_id;
    }

    public void setLottery_class_id(int lottery_class_id) {
        this.lottery_class_id = lottery_class_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
