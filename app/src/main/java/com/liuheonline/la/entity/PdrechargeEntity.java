package com.liuheonline.la.entity;

import java.io.Serializable;
import java.util.List;

public class PdrechargeEntity implements Serializable {


    /**
     * recharge : [{"pdr_id":24,"pdr_sn":950586636034626095,"pdr_member_id":11095,"pdr_member_name":"冰糖雪梨","pdr_amount":"300.00","pdr_payment_code":"alipay","pdr_payment_name":"支付宝","pdr_trade_sn":"fgj","pdr_add_time":1533292034,"pdr_payment_state":"1","pdr_payment_time":1533139200,"pdr_admin":"admin","pdr_type":1,"nickname":"冰糖雪梨","invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"agent":3,"head":"img/20180728/5b5c44a799f1e.jpg","pdr_qrcode":2,"pdr_qrcode_id":50,"pdr_account":"西一","pdr_note":"","head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"pdr_id":23,"pdr_sn":440586636034451095,"pdr_member_id":11095,"pdr_member_name":"冰糖雪梨","pdr_amount":"300.00","pdr_payment_code":"alipay","pdr_payment_name":"支付宝","pdr_trade_sn":"asdf","pdr_add_time":1533292034,"pdr_payment_state":"1","pdr_payment_time":1533139200,"pdr_admin":"admin","pdr_type":1,"nickname":"冰糖雪梨","invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"agent":3,"head":"img/20180728/5b5c44a799f1e.jpg","pdr_qrcode":2,"pdr_qrcode_id":50,"pdr_account":"西一","pdr_note":"","head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"pdr_id":22,"pdr_sn":680586636034303095,"pdr_member_id":11095,"pdr_member_name":"冰糖雪梨","pdr_amount":"300.00","pdr_payment_code":"alipay","pdr_payment_name":"支付宝","pdr_trade_sn":"","pdr_add_time":1533292034,"pdr_payment_state":"0","pdr_payment_time":1533225600,"pdr_admin":"","pdr_type":1,"nickname":"冰糖雪梨","invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"agent":3,"head":"img/20180728/5b5c44a799f1e.jpg","pdr_qrcode":2,"pdr_qrcode_id":50,"pdr_account":"西一","pdr_note":"","head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"pdr_id":21,"pdr_sn":740586636033620095,"pdr_member_id":11095,"pdr_member_name":"冰糖雪梨","pdr_amount":"300.00","pdr_payment_code":"alipay","pdr_payment_name":"支付宝","pdr_trade_sn":"","pdr_add_time":1533292033,"pdr_payment_state":"0","pdr_payment_time":1533225600,"pdr_admin":"","pdr_type":1,"nickname":"冰糖雪梨","invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"agent":3,"head":"img/20180728/5b5c44a799f1e.jpg","pdr_qrcode":2,"pdr_qrcode_id":50,"pdr_account":"西一","pdr_note":"","head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"pdr_id":20,"pdr_sn":520586634848252095,"pdr_member_id":11095,"pdr_member_name":"冰糖雪梨","pdr_amount":"300.00","pdr_payment_code":"alipay","pdr_payment_name":"支付宝","pdr_trade_sn":"asd","pdr_add_time":1533290848,"pdr_payment_state":"1","pdr_payment_time":1533225600,"pdr_admin":"admin","pdr_type":1,"nickname":"冰糖雪梨","invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"agent":3,"head":"img/20180728/5b5c44a799f1e.jpg","pdr_qrcode":2,"pdr_qrcode_id":50,"pdr_account":"我是小胖子","pdr_note":"","head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"pdr_id":19,"pdr_sn":230586610823657095,"pdr_member_id":11095,"pdr_member_name":"冰糖雪梨","pdr_amount":"8948.00","pdr_payment_code":"wxpay","pdr_payment_name":"微信支付","pdr_trade_sn":"asdff","pdr_add_time":1533266823,"pdr_payment_state":"1","pdr_payment_time":1533225600,"pdr_admin":"admin","pdr_type":1,"nickname":"冰糖雪梨","invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"agent":3,"head":"img/20180728/5b5c44a799f1e.jpg","pdr_qrcode":2,"pdr_qrcode_id":49,"pdr_account":"坛子","pdr_note":"","head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"pdr_id":18,"pdr_sn":300586610386998095,"pdr_member_id":11095,"pdr_member_name":"冰糖雪梨","pdr_amount":"22.00","pdr_payment_code":"alipay","pdr_payment_name":"支付宝","pdr_trade_sn":"sd","pdr_add_time":1533266386,"pdr_payment_state":"1","pdr_payment_time":1533225600,"pdr_admin":"admin","pdr_type":1,"nickname":"冰糖雪梨","invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"agent":3,"head":"img/20180728/5b5c44a799f1e.jpg","pdr_qrcode":2,"pdr_qrcode_id":50,"pdr_account":"哦哦哦","pdr_note":"","head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"}]
     * member : {"spending":102776,"income":134279.6}
     */

    private MemberBean member;
    private List<RechargeBean> recharge;

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public List<RechargeBean> getRecharge() {
        return recharge;
    }

    public void setRecharge(List<RechargeBean> recharge) {
        this.recharge = recharge;
    }

    public static class MemberBean {
        /**
         * spending : 102776
         * income : 134279.6
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

        public void setIncome(double income) {
            this.income = income;
        }
    }

    public static class RechargeBean implements Serializable {
        /**
         * pdr_id : 24
         * pdr_sn : 950586636034626095
         * pdr_member_id : 11095
         * pdr_member_name : 冰糖雪梨
         * pdr_amount : 300.00
         * pdr_payment_code : alipay
         * pdr_payment_name : 支付宝
         * pdr_trade_sn : fgj
         * pdr_add_time : 1533292034
         * pdr_payment_state : 1
         * pdr_payment_time : 1533139200
         * pdr_admin : admin
         * pdr_type : 1
         * nickname : 冰糖雪梨
         * invite_one : 0
         * invite_two : 0
         * invite_three : 0
         * inviter_id : 0
         * agent : 3
         * head : img/20180728/5b5c44a799f1e.jpg
         * pdr_qrcode : 2
         * pdr_qrcode_id : 50
         * pdr_account : 西一
         * pdr_note :
         * head_link : http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg
         */

        private int pdr_id;
        private long pdr_sn;
        private int pdr_member_id;
        private String pdr_member_name;
        private String pdr_amount;
        private String pdr_payment_code;
        private String pdr_payment_name;
        private String pdr_trade_sn;
        private int pdr_add_time;
        private String pdr_payment_state;
        private int pdr_payment_time;
        private String pdr_admin;
        private int pdr_type;
        private String nickname;
        private int invite_one;
        private int invite_two;
        private int invite_three;
        private int inviter_id;
        private int agent;
        private String head;
        private int pdr_qrcode;
        private int pdr_qrcode_id;
        private String pdr_account;
        private String pdr_note;
        private String head_link;

        public int getPdr_id() {
            return pdr_id;
        }

        public void setPdr_id(int pdr_id) {
            this.pdr_id = pdr_id;
        }

        public long getPdr_sn() {
            return pdr_sn;
        }

        public void setPdr_sn(long pdr_sn) {
            this.pdr_sn = pdr_sn;
        }

        public int getPdr_member_id() {
            return pdr_member_id;
        }

        public void setPdr_member_id(int pdr_member_id) {
            this.pdr_member_id = pdr_member_id;
        }

        public String getPdr_member_name() {
            return pdr_member_name;
        }

        public void setPdr_member_name(String pdr_member_name) {
            this.pdr_member_name = pdr_member_name;
        }

        public String getPdr_amount() {
            return pdr_amount;
        }

        public void setPdr_amount(String pdr_amount) {
            this.pdr_amount = pdr_amount;
        }

        public String getPdr_payment_code() {
            return pdr_payment_code;
        }

        public void setPdr_payment_code(String pdr_payment_code) {
            this.pdr_payment_code = pdr_payment_code;
        }

        public String getPdr_payment_name() {
            return pdr_payment_name;
        }

        public void setPdr_payment_name(String pdr_payment_name) {
            this.pdr_payment_name = pdr_payment_name;
        }

        public String getPdr_trade_sn() {
            return pdr_trade_sn;
        }

        public void setPdr_trade_sn(String pdr_trade_sn) {
            this.pdr_trade_sn = pdr_trade_sn;
        }

        public int getPdr_add_time() {
            return pdr_add_time;
        }

        public void setPdr_add_time(int pdr_add_time) {
            this.pdr_add_time = pdr_add_time;
        }

        public String getPdr_payment_state() {
            return pdr_payment_state;
        }

        public void setPdr_payment_state(String pdr_payment_state) {
            this.pdr_payment_state = pdr_payment_state;
        }

        public int getPdr_payment_time() {
            return pdr_payment_time;
        }

        public void setPdr_payment_time(int pdr_payment_time) {
            this.pdr_payment_time = pdr_payment_time;
        }

        public String getPdr_admin() {
            return pdr_admin;
        }

        public void setPdr_admin(String pdr_admin) {
            this.pdr_admin = pdr_admin;
        }

        public int getPdr_type() {
            return pdr_type;
        }

        public void setPdr_type(int pdr_type) {
            this.pdr_type = pdr_type;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public int getAgent() {
            return agent;
        }

        public void setAgent(int agent) {
            this.agent = agent;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public int getPdr_qrcode() {
            return pdr_qrcode;
        }

        public void setPdr_qrcode(int pdr_qrcode) {
            this.pdr_qrcode = pdr_qrcode;
        }

        public int getPdr_qrcode_id() {
            return pdr_qrcode_id;
        }

        public void setPdr_qrcode_id(int pdr_qrcode_id) {
            this.pdr_qrcode_id = pdr_qrcode_id;
        }

        public String getPdr_account() {
            return pdr_account;
        }

        public void setPdr_account(String pdr_account) {
            this.pdr_account = pdr_account;
        }

        public String getPdr_note() {
            return pdr_note;
        }

        public void setPdr_note(String pdr_note) {
            this.pdr_note = pdr_note;
        }

        public String getHead_link() {
            return head_link;
        }

        public void setHead_link(String head_link) {
            this.head_link = head_link;
        }
    }
}
