package com.liuheonline.la.entity;

public class TokenEntity {

    /**
     * accesstoken : 65855CD3-1A6B-640F-077E-F2F367966407
     * log_at : 2
     * devicekey : 8154654698465
     * expire : 1531710591
     */
    private String accesstoken;
    private String log_at;
    private String devicekey;
    private int expire;

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getLog_at() {
        return log_at;
    }

    public void setLog_at(String log_at) {
        this.log_at = log_at;
    }

    public String getDevicekey() {
        return devicekey;
    }

    public void setDevicekey(String devicekey) {
        this.devicekey = devicekey;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    @Override
    public String toString() {
        return "TokenEntity{" +
                "accesstoken='" + accesstoken + '\'' +
                ", log_at='" + log_at + '\'' +
                ", devicekey='" + devicekey + '\'' +
                ", expire=" + expire +
                '}';
    }
}
