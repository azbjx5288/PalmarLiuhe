package com.liuheonline.la.entity;

public class OdevityEntity {

    /**
     * id : 1
     * yyyy : 2018
     * PeriodsEvent : 76
     * win_result : even
     * singular : 1
     * even : 2
     */

    private int id;
    private int yyyy;
    private int periods;
    private String win_result;
    private int singular;
    private int even;

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

    public int getSingular() {
        return singular;
    }

    public void setSingular(int singular) {
        this.singular = singular;
    }

    public int getEven() {
        return even;
    }

    public void setEven(int even) {
        this.even = even;
    }
}
