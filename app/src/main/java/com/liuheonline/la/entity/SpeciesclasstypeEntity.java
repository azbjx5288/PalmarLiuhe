package com.liuheonline.la.entity;

public class SpeciesclasstypeEntity {

    /**
     * id : 1
     * title : 六合彩种
     * sort : 1
     */

    private int id;
    private String title;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "SpeciesclasstypeEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", sort=" + sort +
                '}';
    }
}
