package com.liuheonline.la.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ZodiacStatisticsEntity {

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
         * single : 12
         * single_attr : 虎
         * single_num : 19
         */

        private int single;
        private String single_attr;
        private int single_num;

        public int getSingle() {
            return single;
        }

        public void setSingle(int single) {
            this.single = single;
        }

        public String getSingle_attr() {
            return single_attr;
        }

        public void setSingle_attr(String single_attr) {
            this.single_attr = single_attr;
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
         * double : 10
         * double_attr : 鼠
         * double_num : 4
         */

        @SerializedName("double")
        private int doubleX;
        private String double_attr;
        private int double_num;

        public int getDoubleX() {
            return doubleX;
        }

        public void setDoubleX(int doubleX) {
            this.doubleX = doubleX;
        }

        public String getDouble_attr() {
            return double_attr;
        }

        public void setDouble_attr(String double_attr) {
            this.double_attr = double_attr;
        }

        public int getDouble_num() {
            return double_num;
        }

        public void setDouble_num(int double_num) {
            this.double_num = double_num;
        }
    }
}
