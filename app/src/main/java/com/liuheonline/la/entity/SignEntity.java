package com.liuheonline.la.entity;

/**
 * @author BenYanYi
 * @date 2018/12/13 22:38
 * @email ben@yanyi.red
 * @overview
 */
public class SignEntity {
    /**
     * score : 0
     * days : 2
     */

    private int score;
    private int days;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "SignEntity{" +
                "score=" + score +
                ", days=" + days +
                '}';
    }
}
