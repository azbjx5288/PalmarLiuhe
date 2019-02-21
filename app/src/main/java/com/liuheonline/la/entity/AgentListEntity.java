package com.liuheonline.la.entity;

import java.io.Serializable;

public class AgentListEntity implements Serializable{


    /**
     * id : 11512
     * username : linxi02
     * agent : 2
     * rebates : 1940
     * team_membership : 1
     * team_balance : 0.00
     * available_predeposit : 10000.01
     * lottery_price : 0
     * recharge_predeposit : 10000.01
     * cash_predeposit : 0
     */

    private int id;
    private String username;
    private int agent;
    private int rebates;
    private int team_membership;
    private String team_balance;
    private String available_predeposit;
    private int lottery_price;
    private double recharge_predeposit;
    private int cash_predeposit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAgent() {
        return agent;
    }

    public void setAgent(int agent) {
        this.agent = agent;
    }

    public int getRebates() {
        return rebates;
    }

    public void setRebates(int rebates) {
        this.rebates = rebates;
    }

    public int getTeam_membership() {
        return team_membership;
    }

    public void setTeam_membership(int team_membership) {
        this.team_membership = team_membership;
    }

    public String getTeam_balance() {
        return team_balance;
    }

    public void setTeam_balance(String team_balance) {
        this.team_balance = team_balance;
    }

    public String getAvailable_predeposit() {
        return available_predeposit;
    }

    public void setAvailable_predeposit(String available_predeposit) {
        this.available_predeposit = available_predeposit;
    }

    public int getLottery_price() {
        return lottery_price;
    }

    public void setLottery_price(int lottery_price) {
        this.lottery_price = lottery_price;
    }

    public double getRecharge_predeposit() {
        return recharge_predeposit;
    }

    public void setRecharge_predeposit(double recharge_predeposit) {
        this.recharge_predeposit = recharge_predeposit;
    }

    public int getCash_predeposit() {
        return cash_predeposit;
    }

    public void setCash_predeposit(int cash_predeposit) {
        this.cash_predeposit = cash_predeposit;
    }

    @Override
    public String toString() {
        return "AgentListEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", agent=" + agent +
                ", rebates=" + rebates +
                ", team_membership=" + team_membership +
                ", team_balance='" + team_balance + '\'' +
                ", available_predeposit='" + available_predeposit + '\'' +
                ", lottery_price=" + lottery_price +
                ", recharge_predeposit=" + recharge_predeposit +
                ", cash_predeposit=" + cash_predeposit +
                '}';
    }
}
