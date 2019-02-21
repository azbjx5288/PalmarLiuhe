package com.liuheonline.la.entity;

import java.util.List;

public class NoddEntity {

    /**
     * nodd_max : 1900
     * list : [{"id":5,"num_id":1800,"nodd":"90.00"}]
     */

    private int nodd_max;
    private List<ListBean> list;

    public int getNodd_max() {
        return nodd_max;
    }

    public void setNodd_max(int nodd_max) {
        this.nodd_max = nodd_max;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 5
         * num_id : 1800
         * nodd : 90.00
         */

        private int id;
        private int num_id;
        private String nodd;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNum_id() {
            return num_id;
        }

        public void setNum_id(int num_id) {
            this.num_id = num_id;
        }

        public String getNodd() {
            return nodd;
        }

        public void setNodd(String nodd) {
            this.nodd = nodd;
        }
    }
}
