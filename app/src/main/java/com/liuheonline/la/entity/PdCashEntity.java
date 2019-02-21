package com.liuheonline.la.entity;

import java.io.Serializable;
import java.util.List;

public class PdCashEntity implements Serializable {


    /**
     * pdcash : [{"pdc_id":30,"pdc_sn":950586614198544095,"pdc_member_id":11095,"pdc_member_name":"冰糖雪梨","pdc_amount":"7.00","pdc_bank_name":"还以为嘻嘻","pdc_bank_no":"946464646464646619191","pdc_bank_user":"供你","mobilenum":"","pdc_add_time":1533270198,"pdc_payment_time":null,"pdc_payment_state":"0","pdc_payment_admin":null,"nickname":"冰糖雪梨","agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head":"img/20180728/5b5c44a799f1e.jpg","head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"pdc_id":29,"pdc_sn":180586613966450095,"pdc_member_id":11095,"pdc_member_name":"冰糖雪梨","pdc_amount":"50.00","pdc_bank_name":"管理心理学自己","pdc_bank_no":"4949495945918918","pdc_bank_user":"铜米","mobilenum":"","pdc_add_time":1533269966,"pdc_payment_time":null,"pdc_payment_state":"0","pdc_payment_admin":null,"nickname":"冰糖雪梨","agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head":"img/20180728/5b5c44a799f1e.jpg","head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"pdc_id":28,"pdc_sn":610586452670147095,"pdc_member_id":11095,"pdc_member_name":"冰糖雪梨","pdc_amount":"8.00","pdc_bank_name":"中国银行","pdc_bank_no":"6222024457885463581","pdc_bank_user":"toms","mobilenum":"","pdc_add_time":1533108670,"pdc_payment_time":null,"pdc_payment_state":"0","pdc_payment_admin":null,"nickname":"冰糖雪梨","agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head":"img/20180728/5b5c44a799f1e.jpg","head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"pdc_id":27,"pdc_sn":500586452459242095,"pdc_member_id":11095,"pdc_member_name":"冰糖雪梨","pdc_amount":"5.00","pdc_bank_name":"成都银行","pdc_bank_no":"6222024406025788594","pdc_bank_user":"toms","mobilenum":"","pdc_add_time":1533108459,"pdc_payment_time":null,"pdc_payment_state":"0","pdc_payment_admin":null,"nickname":"冰糖雪梨","agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head":"img/20180728/5b5c44a799f1e.jpg","head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"pdc_id":26,"pdc_sn":700586452448188095,"pdc_member_id":11095,"pdc_member_name":"冰糖雪梨","pdc_amount":"5.00","pdc_bank_name":"成都银行","pdc_bank_no":"6222024406025788594","pdc_bank_user":"asdffdv","mobilenum":"","pdc_add_time":1533108448,"pdc_payment_time":null,"pdc_payment_state":"0","pdc_payment_admin":null,"nickname":"冰糖雪梨","agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head":"img/20180728/5b5c44a799f1e.jpg","head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"pdc_id":25,"pdc_sn":440586452444042095,"pdc_member_id":11095,"pdc_member_name":"冰糖雪梨","pdc_amount":"5.00","pdc_bank_name":"成都银行","pdc_bank_no":"6222024406025788594","pdc_bank_user":"asdf","mobilenum":"","pdc_add_time":1533108444,"pdc_payment_time":null,"pdc_payment_state":"0","pdc_payment_admin":null,"nickname":"冰糖雪梨","agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head":"img/20180728/5b5c44a799f1e.jpg","head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"pdc_id":24,"pdc_sn":500586452032339095,"pdc_member_id":11095,"pdc_member_name":"冰糖雪梨","pdc_amount":"5.00","pdc_bank_name":"成都银行","pdc_bank_no":"6222024406025788594","pdc_bank_user":"asdf","mobilenum":"","pdc_add_time":1533108032,"pdc_payment_time":null,"pdc_payment_state":"0","pdc_payment_admin":null,"nickname":"冰糖雪梨","agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head":"img/20180728/5b5c44a799f1e.jpg","head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"pdc_id":23,"pdc_sn":250586452028333095,"pdc_member_id":11095,"pdc_member_name":"冰糖雪梨","pdc_amount":"5.00","pdc_bank_name":"成都银行","pdc_bank_no":"6222024406025788594","pdc_bank_user":"","mobilenum":"","pdc_add_time":1533108028,"pdc_payment_time":null,"pdc_payment_state":"0","pdc_payment_admin":null,"nickname":"冰糖雪梨","agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head":"img/20180728/5b5c44a799f1e.jpg","head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"pdc_id":22,"pdc_sn":890586452025698095,"pdc_member_id":11095,"pdc_member_name":"冰糖雪梨","pdc_amount":"5.00","pdc_bank_name":"成都银行","pdc_bank_no":"6222024406025788594","pdc_bank_user":"","mobilenum":"","pdc_add_time":1533108025,"pdc_payment_time":null,"pdc_payment_state":"0","pdc_payment_admin":null,"nickname":"冰糖雪梨","agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head":"img/20180728/5b5c44a799f1e.jpg","head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"},{"pdc_id":21,"pdc_sn":590586451819055095,"pdc_member_id":11095,"pdc_member_name":"冰糖雪梨","pdc_amount":"5.00","pdc_bank_name":"成都银行","pdc_bank_no":"6222024406025788594","pdc_bank_user":"toms","mobilenum":"","pdc_add_time":1533107819,"pdc_payment_time":null,"pdc_payment_state":"0","pdc_payment_admin":null,"nickname":"冰糖雪梨","agent":3,"invite_one":0,"invite_two":0,"invite_three":0,"inviter_id":0,"head":"img/20180728/5b5c44a799f1e.jpg","head_link":"http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg"}]
     * member : {"spending":102776,"income":134289.6}
     */

    private MemberBean member;
    private List<PdcashBean> pdcash;

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public List<PdcashBean> getPdcash() {
        return pdcash;
    }

    public void setPdcash(List<PdcashBean> pdcash) {
        this.pdcash = pdcash;
    }

    public static class MemberBean {
        /**
         * spending : 102776
         * income : 134289.6
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

    public static class PdcashBean implements Serializable {
        /**
         * pdc_id : 30
         * pdc_sn : 950586614198544095
         * pdc_member_id : 11095
         * pdc_member_name : 冰糖雪梨
         * pdc_amount : 7.00
         * pdc_bank_name : 还以为嘻嘻
         * pdc_bank_no : 946464646464646619191
         * pdc_bank_user : 供你
         * mobilenum :
         * pdc_add_time : 1533270198
         * pdc_payment_time : null
         * pdc_payment_state : 0
         * pdc_payment_admin : null
         * nickname : 冰糖雪梨
         * agent : 3
         * invite_one : 0
         * invite_two : 0
         * invite_three : 0
         * inviter_id : 0
         * head : img/20180728/5b5c44a799f1e.jpg
         * head_link : http://120.79.189.244/images/img/20180728/5b5c44a799f1e.jpg
         */

        private int pdc_id;
        private long pdc_sn;
        private int pdc_member_id;
        private String pdc_member_name;
        private String pdc_amount;
        private String pdc_bank_name;
        private String pdc_bank_no;
        private String pdc_bank_user;
        private String mobilenum;
        private int pdc_add_time;
        private Object pdc_payment_time;
        private String pdc_payment_state;

        public String getPdc_note() {
            return pdc_note;
        }

        public void setPdc_note(String pdc_note) {
            this.pdc_note = pdc_note;
        }

        private String pdc_note;
        private Object pdc_payment_admin;
        private String nickname;
        private int agent;
        private int invite_one;
        private int invite_two;
        private int invite_three;
        private int inviter_id;
        private String head;
        private String head_link;

        public int getPdc_id() {
            return pdc_id;
        }

        public void setPdc_id(int pdc_id) {
            this.pdc_id = pdc_id;
        }

        public long getPdc_sn() {
            return pdc_sn;
        }

        public void setPdc_sn(long pdc_sn) {
            this.pdc_sn = pdc_sn;
        }

        public int getPdc_member_id() {
            return pdc_member_id;
        }

        public void setPdc_member_id(int pdc_member_id) {
            this.pdc_member_id = pdc_member_id;
        }

        public String getPdc_member_name() {
            return pdc_member_name;
        }

        public void setPdc_member_name(String pdc_member_name) {
            this.pdc_member_name = pdc_member_name;
        }

        public String getPdc_amount() {
            return pdc_amount;
        }

        public void setPdc_amount(String pdc_amount) {
            this.pdc_amount = pdc_amount;
        }

        public String getPdc_bank_name() {
            return pdc_bank_name;
        }

        public void setPdc_bank_name(String pdc_bank_name) {
            this.pdc_bank_name = pdc_bank_name;
        }

        public String getPdc_bank_no() {
            return pdc_bank_no;
        }

        public void setPdc_bank_no(String pdc_bank_no) {
            this.pdc_bank_no = pdc_bank_no;
        }

        public String getPdc_bank_user() {
            return pdc_bank_user;
        }

        public void setPdc_bank_user(String pdc_bank_user) {
            this.pdc_bank_user = pdc_bank_user;
        }

        public String getMobilenum() {
            return mobilenum;
        }

        public void setMobilenum(String mobilenum) {
            this.mobilenum = mobilenum;
        }

        public int getPdc_add_time() {
            return pdc_add_time;
        }

        public void setPdc_add_time(int pdc_add_time) {
            this.pdc_add_time = pdc_add_time;
        }

        public Object getPdc_payment_time() {
            return pdc_payment_time;
        }

        public void setPdc_payment_time(Object pdc_payment_time) {
            this.pdc_payment_time = pdc_payment_time;
        }

        public String getPdc_payment_state() {
            return pdc_payment_state;
        }

        public void setPdc_payment_state(String pdc_payment_state) {
            this.pdc_payment_state = pdc_payment_state;
        }

        public Object getPdc_payment_admin() {
            return pdc_payment_admin;
        }

        public void setPdc_payment_admin(Object pdc_payment_admin) {
            this.pdc_payment_admin = pdc_payment_admin;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getHead_link() {
            return head_link;
        }

        public void setHead_link(String head_link) {
            this.head_link = head_link;
        }
    }
}
