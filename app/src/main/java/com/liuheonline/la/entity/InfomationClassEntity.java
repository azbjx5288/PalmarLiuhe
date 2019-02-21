package com.liuheonline.la.entity;

public class InfomationClassEntity {

    /**
     * id : 1
     * ename : notice
     * title : 公告12211
     * pic : img/20180723/5b552e131da43.png
     * pid : 0
     * sort : 122
     * pic_link : http://120.79.189.244/images/img/20180723/5b552e131da43.png
     */

    private int id;
    private String ename;
    private String title;
    private String pic;
    private int pid;
    private int sort;
    private String pic_link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public String getPic_link() {
        return pic_link;
    }

    public void setPic_link(String pic_link) {
        this.pic_link = pic_link;
    }
}
