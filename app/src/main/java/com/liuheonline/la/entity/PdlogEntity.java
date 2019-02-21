package com.liuheonline.la.entity;

import java.io.Serializable;
import java.util.List;

public class PdlogEntity implements Serializable {

    /**
     * pdlog : [{"lg_id":238,"lg_member_id":11095,"lg_member_name":"冰糖雪梨","lg_admin_name":null,"lg_type":"order_pay","lg_av_amount":"50.00","lg_freeze_amount":"0.00","lg_add_time":1533349721,"lg_desc":"下单，支付预存款，订单号: 140586693721130095","lg_invite_member_id":0,"nickname":"冰糖雪梨","head":"img/20180728/5b5c44a799f1e.jpg","groupid":1,"agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"lg_id":218,"lg_member_id":11095,"lg_member_name":"冰糖雪梨","lg_admin_name":"admin","lg_type":"recharge","lg_av_amount":"300.00","lg_freeze_amount":"0.00","lg_add_time":1533292106,"lg_desc":"充值，充值单号: 440586636034451095","lg_invite_member_id":0,"nickname":"冰糖雪梨","head":"img/20180728/5b5c44a799f1e.jpg","groupid":1,"agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"lg_id":217,"lg_member_id":11095,"lg_member_name":"冰糖雪梨","lg_admin_name":"admin","lg_type":"recharge","lg_av_amount":"300.00","lg_freeze_amount":"0.00","lg_add_time":1533292064,"lg_desc":"充值，充值单号: 950586636034626095","lg_invite_member_id":0,"nickname":"冰糖雪梨","head":"img/20180728/5b5c44a799f1e.jpg","groupid":1,"agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"lg_id":216,"lg_member_id":11095,"lg_member_name":"冰糖雪梨","lg_admin_name":"admin","lg_type":"recharge","lg_av_amount":"300.00","lg_freeze_amount":"0.00","lg_add_time":1533290917,"lg_desc":"充值，充值单号: 520586634848252095","lg_invite_member_id":0,"nickname":"冰糖雪梨","head":"img/20180728/5b5c44a799f1e.jpg","groupid":1,"agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"lg_id":208,"lg_member_id":11095,"lg_member_name":"冰糖雪梨","lg_admin_name":null,"lg_type":"cash_apply","lg_av_amount":"7.00","lg_freeze_amount":"7.00","lg_add_time":1533270198,"lg_desc":"申请提现，冻结预存款，提现单号: 950586614198544095","lg_invite_member_id":0,"nickname":"冰糖雪梨","head":"img/20180728/5b5c44a799f1e.jpg","groupid":1,"agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"lg_id":207,"lg_member_id":11095,"lg_member_name":"冰糖雪梨","lg_admin_name":null,"lg_type":"cash_apply","lg_av_amount":"50.00","lg_freeze_amount":"50.00","lg_add_time":1533269966,"lg_desc":"申请提现，冻结预存款，提现单号: 180586613966450095","lg_invite_member_id":0,"nickname":"冰糖雪梨","head":"img/20180728/5b5c44a799f1e.jpg","groupid":1,"agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"lg_id":205,"lg_member_id":11095,"lg_member_name":"冰糖雪梨","lg_admin_name":"admin","lg_type":"recharge","lg_av_amount":"22.00","lg_freeze_amount":"0.00","lg_add_time":1533266947,"lg_desc":"充值，充值单号: 300586610386998095","lg_invite_member_id":0,"nickname":"冰糖雪梨","head":"img/20180728/5b5c44a799f1e.jpg","groupid":1,"agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"lg_id":204,"lg_member_id":11095,"lg_member_name":"冰糖雪梨","lg_admin_name":"admin","lg_type":"recharge","lg_av_amount":"8948.00","lg_freeze_amount":"0.00","lg_add_time":1533266928,"lg_desc":"充值，充值单号: 230586610823657095","lg_invite_member_id":0,"nickname":"冰糖雪梨","head":"img/20180728/5b5c44a799f1e.jpg","groupid":1,"agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"}]
     * member : {"spending":100026,"income":124607}
     */

    private MemberBean member;
    private List<PdlogBean> pdlog;

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public List<PdlogBean> getPdlog() {
        return pdlog;
    }

    public void setPdlog(List<PdlogBean> pdlog) {
        this.pdlog = pdlog;
    }

    public static class MemberBean {
        /**
         * spending : 100026
         * income : 124607
         */

        private double spending;
        private double income;

        public double getSpending() {
            return spending;
        }

        public void setSpending(int spending) {
            this.spending = spending;
        }

        public double getIncome() {
            return income;
        }

        public void setIncome(int income) {
            this.income = income;
        }
    }

    public static class PdlogBean implements Serializable {
        /**
         * lg_id : 238
         * lg_member_id : 11095
         * lg_member_name : 冰糖雪梨
         * lg_admin_name : null
         * lg_type : order_pay
         * lg_av_amount : 50.00
         * lg_freeze_amount : 0.00
         * lg_add_time : 1533349721
         * lg_desc : 下单，支付预存款，订单号: 140586693721130095
         * lg_invite_member_id : 0
         * nickname : 冰糖雪梨
         * head : img/20180728/5b5c44a799f1e.jpg
         * groupid : 1
         * agent : 3
         * invite_one : 0
         * invite_two : 0
         * invite_three : 0
         * inviter_id : 0
         * head_link : http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg
         */

        private int lg_id;
        private int lg_member_id;
        private String lg_member_name;
        private Object lg_admin_name;
        private String lg_type;
        private String lg_av_amount;
        private String lg_freeze_amount;

        public int getLg_spending() {
            return lg_spending;
        }

        public void setLg_spending(int lg_spending) {
            this.lg_spending = lg_spending;
        }

        private int lg_spending;
        private int lg_add_time;
        private String lg_desc;
        private int lg_invite_member_id;
        private String nickname;
        private String head;
        private int groupid;
        private int agent;
        private int invite_one;
        private int invite_two;
        private int invite_three;
        private int inviter_id;
        private String head_link;

        public int getLg_id() {
            return lg_id;
        }

        public void setLg_id(int lg_id) {
            this.lg_id = lg_id;
        }

        public int getLg_member_id() {
            return lg_member_id;
        }

        public void setLg_member_id(int lg_member_id) {
            this.lg_member_id = lg_member_id;
        }

        public String getLg_member_name() {
            return lg_member_name;
        }

        public void setLg_member_name(String lg_member_name) {
            this.lg_member_name = lg_member_name;
        }

        public Object getLg_admin_name() {
            return lg_admin_name;
        }

        public void setLg_admin_name(Object lg_admin_name) {
            this.lg_admin_name = lg_admin_name;
        }

        public String getLg_type() {
            return lg_type;
        }

        public void setLg_type(String lg_type) {
            this.lg_type = lg_type;
        }

        public String getLg_av_amount() {
            return lg_av_amount;
        }

        public void setLg_av_amount(String lg_av_amount) {
            this.lg_av_amount = lg_av_amount;
        }

        public String getLg_freeze_amount() {
            return lg_freeze_amount;
        }

        public void setLg_freeze_amount(String lg_freeze_amount) {
            this.lg_freeze_amount = lg_freeze_amount;
        }

        public int getLg_add_time() {
            return lg_add_time;
        }

        public void setLg_add_time(int lg_add_time) {
            this.lg_add_time = lg_add_time;
        }

        public String getLg_desc() {
            return lg_desc;
        }

        public void setLg_desc(String lg_desc) {
            this.lg_desc = lg_desc;
        }

        public int getLg_invite_member_id() {
            return lg_invite_member_id;
        }

        public void setLg_invite_member_id(int lg_invite_member_id) {
            this.lg_invite_member_id = lg_invite_member_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public int getGroupid() {
            return groupid;
        }

        public void setGroupid(int groupid) {
            this.groupid = groupid;
        }

        public int getAgent() {
            return agent;
        }

        public void setAgent(int agent) {
            this.agent = agent;
        }

        public int getInvite_one() {
            return invite_one;
        }

        public void setInvite_one(int invite_one) {
            this.invite_one = invite_one;
        }

        public int getInvite_two() {
            return invite_two;
        }

        public void setInvite_two(int invite_two) {
            this.invite_two = invite_two;
        }

        public int getInvite_three() {
            return invite_three;
        }

        public void setInvite_three(int invite_three) {
            this.invite_three = invite_three;
        }

        public int getInviter_id() {
            return inviter_id;
        }

        public void setInviter_id(int inviter_id) {
            this.inviter_id = inviter_id;
        }

        public String getHead_link() {
            return head_link;
        }

        public void setHead_link(String head_link) {
            this.head_link = head_link;
        }
    }
}
