package com.liuheonline.la.entity;

public class TrendMapEntity {

    /**
     * times : 1
     * win_result : chicken
     * percent : 2
     */

    private int times;
    private String win_result;
    private double percent;

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getWin_result() {
        return win_result;
    }

    public void setWin_result(String win_result) {
        this.win_result = win_result;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
