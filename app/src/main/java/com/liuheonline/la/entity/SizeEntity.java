package com.liuheonline.la.entity;

public class SizeEntity {

    /**
     * id : 1
     * yyyy : 2018
     * PeriodsEvent : 76
     * win_result : small
     * big : 2
     * small : 2
     */

    private int id;
    private int yyyy;
    private int periods;
    private String win_result;
    private int big;
    private int small;

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

    public int getBig() {
        return big;
    }

    public void setBig(int big) {
        this.big = big;
    }

    public int getSmall() {
        return small;
    }

    public void setSmall(int small) {
        this.small = small;
    }
}
