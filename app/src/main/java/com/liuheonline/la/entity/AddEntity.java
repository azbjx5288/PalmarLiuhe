package com.liuheonline.la.entity;

public class AddEntity {

    /**
     * id : 1
     * yyyy : 2018
     * PeriodsEvent : 76
     * win_result : even
     * num_add : 2
     * singular : 2
     * even : 2
     */

    private int id;
    private int yyyy;
    private int periods;
    private String win_result;
    private int num_add;
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

    public int getNum_add() {
        return num_add;
    }

    public void setNum_add(int num_add) {
        this.num_add = num_add;
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

    @Override
    public String toString() {
        return "AddEntity{" +
                "id=" + id +
                ", yyyy=" + yyyy +
                ", periods=" + periods +
                ", win_result='" + win_result + '\'' +
                ", num_add=" + num_add +
                ", singular=" + singular +
                ", even=" + even +
                '}';
    }
}
