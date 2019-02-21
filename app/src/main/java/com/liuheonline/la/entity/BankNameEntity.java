package com.liuheonline.la.entity;

public class BankNameEntity {

    /**
     * id : 1
     * title : 中国农业银行
     * pic : img/20180730/5b5e92c2b9136.jpg
     * sort : 1
     * pic_link : http://120.79.189.244/images/img/20180730/5b5e92c2b9136.jpg
     */

    private int id;
    private String title;
    private String pic;
    private int sort;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    @Override
    public String toString() {
        return "BankNameEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                ", sort=" + sort +
                ", pic_link='" + pic_link + '\'' +
                '}';
    }
}
