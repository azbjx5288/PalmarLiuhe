package com.liuheonline.la.entity;

public class ColorEntity {

    /**
     * id : 1
     * yyyy : 2018
     * PeriodsEvent : 76
     * win_result : red
     * red : 2
     * blue : 3
     * green : 4
     */

    private int id;
    private int yyyy;
    private int periods;
    private String win_result;
    private int red;
    private int blue;
    private int green;

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

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    @Override
    public String toString() {
        return "ColorEntity{" +
                "id=" + id +
                ", yyyy=" + yyyy +
                ", periods=" + periods +
                ", win_result='" + win_result + '\'' +
                ", red=" + red +
                ", blue=" + blue +
                ", green=" + green +
                '}';
    }
}
