package com.liuheonline.la.entity;

/**
 * @author BenYanYi
 * @date 2018/12/13 13:41
 * @email ben@yanyi.red
 * @overview 红包次数
 */
public class RedEnvelopeEntity {

    /**
     * red_packed_num : 4
     */

    private int red_packed_num;
    private double red_packed_amount;

    public double getRed_packed_amount() {
        return red_packed_amount;
    }

    public void setRed_packed_amount(double red_packed_amount) {
        this.red_packed_amount = red_packed_amount;
    }

    public int getRed_packed_num() {
        return red_packed_num;
    }

    public void setRed_packed_num(int red_packed_num) {
        this.red_packed_num = red_packed_num;
    }

    @Override
    public String toString() {
        return "RedEnvelopeEntity{" +
                "red_packed_num=" + red_packed_num +
                ", red_packed_amount=" + red_packed_amount +
                '}';
    }
}
