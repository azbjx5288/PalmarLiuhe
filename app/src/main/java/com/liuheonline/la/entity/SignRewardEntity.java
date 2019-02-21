package com.liuheonline.la.entity;

/**
 * @author BenYanYi
 * @date 2018/12/13 17:34
 * @email ben@yanyi.red
 * @overview
 */
public class SignRewardEntity {

    /**
     * id : 1
     * days : 3
     * total : 1.0
     */

    private int id;
    private int days;
    private String total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "SignRewardEntity{" +
                "id=" + id +
                ", days=" + days +
                ", total='" + total + '\'' +
                '}';
    }
}
