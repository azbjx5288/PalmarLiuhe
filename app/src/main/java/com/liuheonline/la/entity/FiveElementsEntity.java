package com.liuheonline.la.entity;

public class FiveElementsEntity {

    /**
     * id : 1
     * yyyy : 2018
     * PeriodsEvent : 76
     * win_result : fire
     * gold : 1
     * wood : 7
     * water : 10
     * fire : 2
     * soil : 2
     */

    private int id;
    private int yyyy;
    private int periods;
    private String win_result;
    private int gold;
    private int wood;
    private int water;
    private int fire;
    private int soil;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYyyy() {
        return yyyy;
    }

    public void setYyyy(int yyyy) {
        this.yyyy = yyyy;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public String getWin_result() {
        return win_result;
    }

    public void setWin_result(String win_result) {
        this.win_result = win_result;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getFire() {
        return fire;
    }

    public void setFire(int fire) {
        this.fire = fire;
    }

    public int getSoil() {
        return soil;
    }

    public void setSoil(int soil) {
        this.soil = soil;
    }

    @Override
    public String toString() {
        return "FiveElementsEntity{" +
                "id=" + id +
                ", yyyy=" + yyyy +
                ", periods=" + periods +
                ", win_result='" + win_result + '\'' +
                ", gold=" + gold +
                ", wood=" + wood +
                ", water=" + water +
                ", fire=" + fire +
                ", soil=" + soil +
                '}';
    }
}
