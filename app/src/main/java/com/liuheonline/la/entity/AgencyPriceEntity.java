package com.liuheonline.la.entity;

import java.util.List;

public class AgencyPriceEntity {

    /**
     * betting : 110
     * commission : 0.1
     * list : [{"id":11462,"uid":11439,"betting":"110.00","commission":"0.10","number":3,"state":1,"create_time":1539140642,"update_time":1539140943}]
     */

    private int betting;
    private double commission;
    private List<ListBean> list;

    public int getBetting() {
        return betting;
    }

    public void setBetting(int betting) {
        this.betting = betting;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 11462
         * uid : 11439
         * betting : 110.00
         * commission : 0.10
         * number : 3
         * state : 1
         * create_time : 1539140642
         * update_time : 1539140943
         */

        private int id;
        private int uid;
        private String betting;
        private String commission;
        private int number;
        private int state;
        private int create_time;
        private int update_time;

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

        public String getBetting() {
            return betting;
        }

        public void setBetting(String betting) {
            this.betting = betting;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "id=" + id +
                    ", uid=" + uid +
                    ", betting='" + betting + '\'' +
                    ", commission='" + commission + '\'' +
                    ", number=" + number +
                    ", state=" + state +
                    ", create_time=" + create_time +
                    ", update_time=" + update_time +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AgencyPriceEntity{" +
                "betting=" + betting +
                ", commission=" + commission +
                ", list=" + list +
                '}';
    }
}
