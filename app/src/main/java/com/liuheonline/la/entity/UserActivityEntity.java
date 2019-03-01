package com.liuheonline.la.entity;

public class UserActivityEntity {


    /**
     * addtime : 1551272062
     * content : &lt;span style=&quot;font-size:14px;font-weight:bold;&quot;&gt;&lt;span style=&quot;color:#E53333;&quot;&gt;朋友圈集赞活动火热进行中，凡是在朋友圈发布指定内容集满18个赞以上的皆可联系我们的客服专员每个赞申请1元的集赞彩金，赞越多，奖金越高，不设流水即可体现！&lt;/span&gt;&lt;/span&gt;
     * id : 1
     * img :
     * opentime :
     * people : 全体会员
     * status : 1
     * title : 朋友圈集赞拿大奖
     */

    private String addtime;
    private String content;
//    private int id;
    private String img;
    private String opentime;
    private String people;
    private int status;
    private String title;

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

/*    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
