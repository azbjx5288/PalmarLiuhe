package com.liuheonline.la.entity;

public class InfomationEntity {

    /**
     * id : 47
     * title : 334343
     * pid : 4
     * pic : img/20180723/5b553d5e75e0c.jpg
     * hits : 4
     * create_time : 0
     * ac_title : 支付方式
     * pic_link : http://120.79.189.244/images/img/20180723/5b553d5e75e0c.jpg
     */

    private int id;
    private String title;
    private int pid;
    private String pic;
    private int hits;
    private int create_time;
    private String ac_title;
    private String pic_link;

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getAc_title() {
        return ac_title;
    }

    public void setAc_title(String ac_title) {
        this.ac_title = ac_title;
    }

    public String getPic_link() {
        return pic_link;
    }

    public void setPic_link(String pic_link) {
        this.pic_link = pic_link;
    }
}
