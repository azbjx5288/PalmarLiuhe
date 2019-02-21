package com.liuheonline.la.entity;

import java.util.List;

public class NumStatisticsEntity {

    private List<TemaBean> tema;
    private List<CodeBean> code;

    public List<TemaBean> getTema() {
        return tema;
    }

    public void setTema(List<TemaBean> tema) {
        this.tema = tema;
    }

    public List<CodeBean> getCode() {
        return code;
    }

    public void setCode(List<CodeBean> code) {
        this.code = code;
    }

    public static class TemaBean {
        /**
         * tema : 47
         * tema_color : 蓝波
         * tema_color_id : 8
         * tema_num : 3
         */

        private int tema;
        private String tema_color;
        private int tema_color_id;
        private int tema_num;

        public int getTema() {
            return tema;
        }

        public void setTema(int tema) {
            this.tema = tema;
        }

        public String getTema_color() {
            return tema_color;
        }

        public void setTema_color(String tema_color) {
            this.tema_color = tema_color;
        }

        public int getTema_color_id() {
            return tema_color_id;
        }

        public void setTema_color_id(int tema_color_id) {
            this.tema_color_id = tema_color_id;
        }

        public int getTema_num() {
            return tema_num;
        }

        public void setTema_num(int tema_num) {
            this.tema_num = tema_num;
        }
    }

    public static class CodeBean {
        /**
         * code : 33
         * code_color : 绿波
         * code_color_id : 9
         * code_num : 8
         */

        private int code;
        private String code_color;
        private int code_color_id;
        private int code_num;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getCode_color() {
            return code_color;
        }

        public void setCode_color(String code_color) {
            this.code_color = code_color;
        }

        public int getCode_color_id() {
            return code_color_id;
        }

        public void setCode_color_id(int code_color_id) {
            this.code_color_id = code_color_id;
        }

        public int getCode_num() {
            return code_num;
        }

        public void setCode_num(int code_num) {
            this.code_num = code_num;
        }
    }
}
