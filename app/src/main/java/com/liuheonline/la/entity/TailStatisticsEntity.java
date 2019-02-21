package com.liuheonline.la.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TailStatisticsEntity {

    private List<SingleBean> single;
    @SerializedName("double")
    private List<DoubleBean> doubleX;

    public List<SingleBean> getSingle() {
        return single;
    }

    public void setSingle(List<SingleBean> single) {
        this.single = single;
    }

    public List<DoubleBean> getDoubleX() {
        return doubleX;
    }

    public void setDoubleX(List<DoubleBean> doubleX) {
        this.doubleX = doubleX;
    }

    public static class SingleBean {
        /**
         * single : 2
         * single_num : 11
         */

        private int single;
        private int single_num;

        public int getSingle() {
            return single;
        }

        public void setSingle(int single) {
            this.single = single;
        }

        public int getSingle_num() {
            return single_num;
        }

        public void setSingle_num(int single_num) {
            this.single_num = single_num;
        }
    }

    public static class DoubleBean {
        /**
         * double : 7
         * double_num : 5
         */

        @SerializedName("double")
        private int doubleX;
        private int double_num;

        public int getDoubleX() {
            return doubleX;
        }

        public void setDoubleX(int doubleX) {
            this.doubleX = doubleX;
        }

        public int getDouble_num() {
            return double_num;
        }

        public void setDouble_num(int double_num) {
            this.double_num = double_num;
        }
    }
}
