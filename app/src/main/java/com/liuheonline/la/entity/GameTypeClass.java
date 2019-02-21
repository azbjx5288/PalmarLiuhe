package com.liuheonline.la.entity;

import java.util.List;

/**
 * @author: aini
 * @date 2018/7/26 10:15
 * @description 玩法分类
 */
public class GameTypeClass extends SelectBean{

    /**
     * id : 4
     * title : 波色
     * pid : 0
     * sort : 0
     * _child : [{"id":59,"title":"色波","pid":4,"sort":0}]
     */

    private int id;
    private String title;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String price;
    private int pid;
    private int sort;
    private List<ChildBean> _child;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<ChildBean> get_child() {
        return _child;
    }

    public void set_child(List<ChildBean> _child) {
        this._child = _child;
    }

    public static class ChildBean extends SelectBean{
        /**
         * id : 59
         * title : 色波
         * pid : 4
         * sort : 0
         */

        private int id;
        private String title;
        private int pid;
        private int sort;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
