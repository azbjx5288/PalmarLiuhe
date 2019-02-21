package com.liuheonline.la.entity;

import java.io.Serializable;

public class NoticeEntity implements Serializable{

    /**
     * id : 47
     * title : 334343
     * content : 梵蒂冈法国让他别人帮他日本问题并未
     * create_time : 01-01
     */

    private int id;
    private String title;
    private String content;
    private String create_time;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
