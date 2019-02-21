package com.liuheonline.la.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author: aini
 * @date 2018/7/20 14:57
 * @description 回复评论
 */
public class ForumEntity implements Serializable {

    /**
     * id : 467
     * depict :
     * type : 1
     * item_id : 0
     * imglist :
     * comment : 7
     * agrees : 2
     * create_time : 33分钟前
     * uid : 11092
     * nickname : aini
     * sex : 0
     * head : http://120.79.189.244/images/img/20180720/5b515b837c18b.jpg
     * son :
     * is_agree : 1
     * imglist_link :
     * head_link : http://120.79.189.244/images/img/20180720/5b515b837c18b.jpg
     */

    private int id;
    private String depict;
    private int type;
    private int item_id;
    private String imglist;
    private int comment;
    private int agrees;
    private String create_time;
    private int uid;
    private String nickname;
    private int sex;
    private String head;
    private int is_agree;
    private String head_link;
    private List<CommentEntity> son;
    private List<Map<String,Object>> agree;
    private List<String> imglist_link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepict() {
        return depict;
    }

    public void setDepict(String depict) {
        this.depict = depict;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getImglist() {
        return imglist;
    }

    public void setImglist(String imglist) {
        this.imglist = imglist;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getAgrees() {
        return agrees;
    }

    public void setAgrees(int agrees) {
        this.agrees = agrees;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getIs_agree() {
        return is_agree;
    }

    public void setIs_agree(int is_agree) {
        this.is_agree = is_agree;
    }

    public String getHead_link() {
        return head_link;
    }

    public void setHead_link(String head_link) {
        this.head_link = head_link;
    }

    public List<CommentEntity> getSon() {
        return son;
    }

    public void setSon(List<CommentEntity> son) {
        this.son = son;
    }

    public List<Map<String, Object>> getAgree() {
        return agree;
    }

    public void setAgree(List<Map<String, Object>> agree) {
        this.agree = agree;
    }

    public List<String> getImglist_link() {
        return imglist_link;
    }

    public void setImglist_link(List<String> imglist_link) {
        this.imglist_link = imglist_link;
    }

    @Override
    public String toString() {
        return "ForumEntity{" +
                "id=" + id +
                ", depict='" + depict + '\'' +
                ", type=" + type +
                ", item_id=" + item_id +
                ", imglist='" + imglist + '\'' +
                ", comment=" + comment +
                ", agrees=" + agrees +
                ", create_time='" + create_time + '\'' +
                ", uid=" + uid +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", head='" + head + '\'' +
                ", is_agree=" + is_agree +
                ", head_link='" + head_link + '\'' +
                ", son=" + son +
                ", agree=" + agree +
                ", imglist_link=" + imglist_link +
                '}';
    }
}
