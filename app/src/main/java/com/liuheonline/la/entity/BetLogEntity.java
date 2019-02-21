package com.liuheonline.la.entity;

/**
 * @author: aini
 * @date 2018/8/3 12:18
 * @description 单注实体类
 */
public class BetLogEntity {

    private String lottery_price;

    private String lottery_name;

    private int state;

    public String getLottery_price() {
        return lottery_price;
    }

    public void setLottery_price(String lottery_price) {
        this.lottery_price = lottery_price;
    }

    public String getLottery_name() {
        return lottery_name;
    }

    public void setLottery_name(String lottery_name) {
        this.lottery_name = lottery_name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "BetLogEntity{" +
                "lottery_price='" + lottery_price + '\'' +
                ", lottery_name='" + lottery_name + '\'' +
                ", state=" + state +
                '}';
    }
}
