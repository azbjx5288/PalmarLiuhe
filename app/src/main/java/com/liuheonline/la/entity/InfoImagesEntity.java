package com.liuheonline.la.entity;

import java.io.Serializable;

/**
 * Auther: RyanLi
 * Data: 2018-09-25 16:44
 * Description: 图片列表信息
 */
public class InfoImagesEntity implements Serializable {

    /**
     * title : 高级内部绝杀
     * sid : 11705
     * newstime : 1463190765
     * qishu : 109
     * url : http://tuku.jizhou56.com:8090/images2/
     * type : hj5
     */

    private String title;
    private String sid;
    private String newstime;
    private String qishu;
    private String url;
    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getNewstime() {
        return newstime;
    }

    public void setNewstime(String newstime) {
        this.newstime = newstime;
    }

    public String getQishu() {
        return qishu;
    }

    public void setQishu(String qishu) {
        this.qishu = qishu;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "InfoImagesEntity{" +
                "title='" + title + '\'' +
                ", sid='" + sid + '\'' +
                ", newstime='" + newstime + '\'' +
                ", qishu='" + qishu + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
