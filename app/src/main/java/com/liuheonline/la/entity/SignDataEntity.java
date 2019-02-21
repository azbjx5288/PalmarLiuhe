package com.liuheonline.la.entity;

import java.io.Serializable;

/**
 * @author BenYanYi
 * @date 2018/12/13 16:03
 * @email ben@yanyi.red
 * @overview
 */
public class SignDataEntity implements Serializable {

    /**
     * id : 92
     * uid : 11990
     * days : 2
     * score : 0.00
     * is_share : 0
     * is_sign : 1
     * stime : 1544672563
     * atime : 1544672563
     */

    private int id;
    private int uid;
    private int days;
    private String score;
    private int is_share;
    private int is_sign;
    private long stime;
    private long atime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getIs_share() {
        return is_share;
    }

    public void setIs_share(int is_share) {
        this.is_share = is_share;
    }

    public int getIs_sign() {
        return is_sign;
    }

    public void setIs_sign(int is_sign) {
        this.is_sign = is_sign;
    }

    public long getStime() {
        return stime;
    }

    public void setStime(long stime) {
        this.stime = stime;
    }

    public long getAtime() {
        return atime;
    }

    public void setAtime(long atime) {
        this.atime = atime;
    }

    @Override
    public String toString() {
        return "SignDataEntity{" +
                "id=" + id +
                ", uid=" + uid +
                ", days=" + days +
                ", score='" + score + '\'' +
                ", is_share=" + is_share +
                ", is_sign=" + is_sign +
                ", stime=" + stime +
                ", atime=" + atime +
                '}';
    }
}
