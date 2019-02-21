package com.liuheonline.la.entity;

public class AgencyTableEntity {

    /**
     * id : 11439
     * username : 13644556677
     * member_rebates : 90
     * available_predeposit : 90.36
     * betting : 270
     * winning : 231.66
     * profit : -38.34
     * commission : 6216.9
     * recharge : 0
     * cash : 0
     * rebates : 0
     * preferential : 0
     */

    private int id;
    private String username;
    private int member_rebates;
    private String available_predeposit;
    private int betting;
    private double winning;
    private double profit;
    private double commission;
    private double recharge;
    private double cash;
    private double rebates;

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

    public int getMember_rebates() {
        return member_rebates;
    }

    public void setMember_rebates(int member_rebates) {
        this.member_rebates = member_rebates;
    }

    public String getAvailable_predeposit() {
        return available_predeposit;
    }

    public void setAvailable_predeposit(String available_predeposit) {
        this.available_predeposit = available_predeposit;
    }

    public int getBetting() {
        return betting;
    }

    public void setBetting(int betting) {
        this.betting = betting;
    }

    public double getWinning() {
        return winning;
    }

    public void setWinning(double winning) {
        this.winning = winning;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getRecharge() {
        return recharge;
    }

    public void setRecharge(double recharge) {
        this.recharge = recharge;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getRebates() {
        return rebates;
    }

    public void setRebates(double rebates) {
        this.rebates = rebates;
    }

    public double getPreferential() {
        return preferential;
    }

    public void setPreferential(double preferential) {
        this.preferential = preferential;
    }

    private double preferential;

    @Override
    public String toString() {
        return "AgencyTableEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", member_rebates=" + member_rebates +
                ", available_predeposit='" + available_predeposit + '\'' +
                ", betting=" + betting +
                ", winning=" + winning +
                ", profit=" + profit +
                ", commission=" + commission +
                ", recharge=" + recharge +
                ", cash=" + cash +
                ", rebates=" + rebates +
                ", preferential=" + preferential +
                '}';
    }
}
