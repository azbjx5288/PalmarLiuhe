package com.liuheonline.la.entity;

/**
 * @author: aini
 * @date 2018/8/2 15:41
 * @description 投注日志
 */
public class LotteryLogEntity {


    /**
     * order_id : 56
     * order_class_id : 1
     * lottery_class_name : 特码B
     * add_time : 16分钟前
     * lottery_amount : 10.00
     * order_state : 30
     * species_name : 六合彩
     * image : img/20180723/5b552dcf6f04b.png
     * image_link : http://120.79.189.244/images/img/20180723/5b552dcf6f04b.png
     */

    private int order_id;
    private int order_class_id;
    private String lottery_class_name;
    private String add_time;
    private String lottery_amount;
    private int order_state;
    private String issue;
    private String species_name;
    private String image;
    private String image_link;

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
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

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getLottery_amount() {
        return lottery_amount;
    }

    public void setLottery_amount(String lottery_amount) {
        this.lottery_amount = lottery_amount;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getSpecies_name() {
        return species_name;
    }

    public void setSpecies_name(String species_name) {
        this.species_name = species_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }
}
