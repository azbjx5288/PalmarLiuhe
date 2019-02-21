package com.liuheonline.la.entity;

import java.util.ArrayList;

//测试    时时彩里面的对象
public class GameTypeNewClass3Entity extends SelectBean {

    //共有的
    private int id;
    private String price;
    private Object rules;
    private int state;
    //外层的
    private String title;
    private int pid;
    private int sid;
    private int sort;
    //最里层的
    private String code;
    private String attr;
    private int attr_id;
    private int lottery_rebates_price;
    private Object color;
    private int color_id;
    ArrayList<GameTypeNewClass3Entity> child;
    ArrayList<GameTypeNewClass3Entity> _child;

    public ArrayList<GameTypeNewClass3Entity> get_child() {
        return _child;
    }

    public void set_child(ArrayList<GameTypeNewClass3Entity> _child) {
        this._child = _child;
    }

    public ArrayList<GameTypeNewClass3Entity> getChild() {
        return child;
    }

    public void setChild(ArrayList<GameTypeNewClass3Entity> child) {
        this.child = child;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setRules(Object rules) {
        this.rules = rules;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public void setAttr_id(int attr_id) {
        this.attr_id = attr_id;
    }

    public void setLottery_rebates_price(int lottery_rebates_price) {
        this.lottery_rebates_price = lottery_rebates_price;
    }

    public void setColor(Object color) {
        this.color = color;
    }

    public void setColor_id(int color_id) {
        this.color_id = color_id;
    }

    //第几组
    private int group;

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getPrice() {
        return price;
    }

    public Object getRules() {
        return rules;
    }

    public int getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public int getPid() {
        return pid;
    }

    public int getSid() {
        return sid;
    }

    public int getSort() {
        return sort;
    }

    public String getAttr() {
        return attr;
    }

    public int getAttr_id() {
        return attr_id;
    }

    public int getLottery_rebates_price() {
        return lottery_rebates_price;
    }

    public Object getColor() {
        return color;
    }

    public int getColor_id() {
        return color_id;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "GameTypeNewClass3Entity{" +
                "id=" + id +
                ", price='" + price + '\'' +
                ", rules=" + rules +
                ", state=" + state +
                ", title='" + title + '\'' +
                ", pid=" + pid +
                ", sid=" + sid +
                ", sort=" + sort +
                ", code='" + code + '\'' +
                ", attr='" + attr + '\'' +
                ", attr_id=" + attr_id +
                ", lottery_rebates_price=" + lottery_rebates_price +
                ", color=" + color +
                ", color_id=" + color_id +
                ", child=" + child +
                ", _child=" + _child +
                ", group=" + group +
                '}';
    }
}
