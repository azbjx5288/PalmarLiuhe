package com.liuheonline.la.entity;

/**
 * @author: aini
 * @date 2018/7/27 09:59
 * @description 投注号码
 */
public class BetNumberEntity extends SelectBean{

    /**
     * id : 1324
     * code : 1
     * price : 48.8
     * attr : 特码B
     * attr_id : 1
     */

    private int id;
    private String code;
    private double price;
    private String attr;
    private int attr_id;
    public double getLottery_rebates_price() {
        return lottery_rebates_price;
    }

    //第几组
    private int group;

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
    public void setLottery_rebates_price(double lottery_rebates_price) {
        this.lottery_rebates_price = lottery_rebates_price;
    }

    private double lottery_rebates_price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public int getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(int attr_id) {
        this.attr_id = attr_id;
    }

    @Override
    public String toString() {
        return "BetNumberEntity{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", price=" + price +
                ", attr='" + attr + '\'' +
                ", attr_id=" + attr_id +
                ", group=" + group +
                ", lottery_rebates_price=" + lottery_rebates_price +
                '}';
    }
}
