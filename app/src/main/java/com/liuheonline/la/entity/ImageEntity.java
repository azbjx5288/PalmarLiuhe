package com.liuheonline.la.entity;

/**
 * @author: aini
 * @date 2018/7/18 10:43
 * @description 图片上传返回实体类
 */
public class ImageEntity {

    private String path;  //图片保存路径

    private String link; //图片访问地址

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
