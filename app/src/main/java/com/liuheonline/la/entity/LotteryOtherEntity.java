package com.liuheonline.la.entity;

import java.util.List;

/**
 * @author: aini
 * @date 2018/7/31 09:16
 * @description 彩票订单类
 */
public class LotteryOtherEntity {

    private long bet_periods;

    private int lottery_amount;

    private int lottery_num;

    private double lottery_rebates_price;

    private int lottery_class_id;

    private String lottery_class_name;

    public int getLottery_id() {
        return lottery_class_id;
    }

    public void setLottery_id(int lottery_id) {
        this.lottery_class_id = lottery_id;
    }

    public String getLottery_class_name() {
        return lottery_class_name;
    }

    public void setLottery_class_name(String lottery_class_name) {
        this.lottery_class_name = lottery_class_name;
    }

    public double getLottery_rebates_price() {
        return lottery_rebates_price;
    }

    public void setLottery_rebates_price(double lottery_rebates_price) {
        this.lottery_rebates_price = lottery_rebates_price;
    }

    public int getSpecie_id() {
        return specie_id;
    }

    public void setSpecie_id(int specie_id) {
        this.specie_id = specie_id;
    }

    private int specie_id;

    private List<LotteryOtherDetailsEntity> bet_info;

    public long getBet_periods() {
        return bet_periods;
    }

    public void setBet_periods(long bet_periods) {
        this.bet_periods = bet_periods;
    }

    public int getLottery_amount() {
        return lottery_amount;
    }

    public void setLottery_amount(int lottery_amount) {
        this.lottery_amount = lottery_amount;
    }

    public int getLottery_num() {
        return lottery_num;
    }

    public void setLottery_num(int lottery_num) {
        this.lottery_num = lottery_num;
    }

    public List<LotteryOtherDetailsEntity> getBet_info() {
        return bet_info;
    }

    public void setBet_info(List<LotteryOtherDetailsEntity> bet_info) {
        this.bet_info = bet_info;
    }

}
