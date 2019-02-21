package com.liuheonline.la.entity;

public class MemberInfoEntity {

    /**
     * id : 11117
     * nickname : 六合828938
     * head : null
     * sex : 0
     * available_predeposit : 0.00
     * agent : 1
     * zc_moshi : 0
     * regdate : 1970-01-01
     * lastdate : 1970-01-01
     * team_winning : 0
     * lottery_rebates_price : 0
     * team_total : 0
     * team_orders : 0
     * team_led : 0
     * head_link :
     */

    private int id;
    private String nickname;
    private Object head;
    private int sex;
    private String available_predeposit;
    private int agent;
    private int zc_moshi;
    private String regdate;
    private String lastdate;
    private int team_winning;
    private int lottery_rebates_price;
    private int team_total;
    private int team_orders;
    private int team_led;
    private String head_link;

    public String getEarnings() {
        return earnings;
    }

    public void setEarnings(String earnings) {
        this.earnings = earnings;
    }

    private String earnings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Object getHead() {
        return head;
    }

    public void setHead(Object head) {
        this.head = head;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAvailable_predeposit() {
        return available_predeposit;
    }

    public void setAvailable_predeposit(String available_predeposit) {
        this.available_predeposit = available_predeposit;
    }

    public int getAgent() {
        return agent;
    }

    public void setAgent(int agent) {
        this.agent = agent;
    }

    public int getZc_moshi() {
        return zc_moshi;
    }

    public void setZc_moshi(int zc_moshi) {
        this.zc_moshi = zc_moshi;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public int getTeam_winning() {
        return team_winning;
    }

    public void setTeam_winning(int team_winning) {
        this.team_winning = team_winning;
    }

    public int getLottery_rebates_price() {
        return lottery_rebates_price;
    }

    public void setLottery_rebates_price(int lottery_rebates_price) {
        this.lottery_rebates_price = lottery_rebates_price;
    }

    public int getTeam_total() {
        return team_total;
    }

    public void setTeam_total(int team_total) {
        this.team_total = team_total;
    }

    public int getTeam_orders() {
        return team_orders;
    }

    public void setTeam_orders(int team_orders) {
        this.team_orders = team_orders;
    }

    public int getTeam_led() {
        return team_led;
    }

    public void setTeam_led(int team_led) {
        this.team_led = team_led;
    }

    public String getHead_link() {
        return head_link;
    }

    public void setHead_link(String head_link) {
        this.head_link = head_link;
    }
}
