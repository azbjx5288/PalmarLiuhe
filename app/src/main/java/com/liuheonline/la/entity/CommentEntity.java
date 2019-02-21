package com.liuheonline.la.entity;

import com.liuheonline.la.ui.widget.commentwidget.IComment;

/**
 * @author: aini
 * @date 2018/7/20 14:59
 * @description
 */
public class CommentEntity implements IComment<CommentEntity>{

    private int id;
    private String content;
    private int agrees;
    private int item_id;
    private String create_time;
    private int uid;
    private String nickname;
    private String head;
    private int to_uid;
    private String to_nickname;
    private int to_sex;
    private int is_agree;
    private String head_link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAgrees() {
        return agrees;
    }

    public void setAgrees(int agrees) {
        this.agrees = agrees;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
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

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getTo_uid() {
        return to_uid;
    }

    public void setTo_uid(int to_uid) {
        this.to_uid = to_uid;
    }

    public String getTo_nickname() {
        return to_nickname;
    }

    public void setTo_nickname(String to_nickname) {
        this.to_nickname = to_nickname;
    }

    public int getTo_sex() {
        return to_sex;
    }

    public void setTo_sex(int to_sex) {
        this.to_sex = to_sex;
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

    @Override
    public String getCommentCreatorName() {
        return nickname;
    }

    @Override
    public String getReplyerName() {
        return to_nickname;
    }

    @Override
    public String getCommentContent() {
        return content;
    }

    @Override
    public String toString() {
        return "CommentEntity{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", agrees=" + agrees +
                ", item_id=" + item_id +
                ", create_time='" + create_time + '\'' +
                ", uid=" + uid +
                ", nickname='" + nickname + '\'' +
                ", head='" + head + '\'' +
                ", to_uid=" + to_uid +
                ", to_nickname='" + to_nickname + '\'' +
                ", to_sex=" + to_sex +
                ", is_agree=" + is_agree +
                ", head_link='" + head_link + '\'' +
                '}';
    }
}
