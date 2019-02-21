package com.liuheonline.la.entity;

import java.io.Serializable;

/**
 * Auther: RyanLi
 * Data: 2018-09-29 20:37
 * Description: 笑话
 */
public class JokerEntity implements Serializable {

    /**
     * id : 262
     * pid : 8
     * url : null
     * title : 生活闲侃,侃的很幽默
     * content :


     <p>
     1、一个非常完美、如假包换的增高小技巧：脚底板磨出几公分老茧。
     </p>
     <p>
     2、当代青年四大美德：信息秒回，约见守时，按时还钱，不管闲事。
     </p>
     <p>
     3、我现在严重怀疑一件事，是不是端午节到了，月老拿我的红绳绑粽子了？
     </p>
     <p>
     4、这个勾心斗角的世界啊，还有点儿真情在吗？手机连到电脑上，两者都在询问要不要相信对方。
     </p>
     <p>
     5、我们公司关于台风的通知：明天早上有台风，请各位同事今晚不要回家，以免明天不能来上班。
     </p>
     <p>
     6、现在手上要没个戒指，真的都不好意思捂脸了。
     </p>
     <p>
     7、有些人逢年过节要礼物真的很俗气，不像我只会淡淡问一句：端午都来了，还不发点过节费？
     </p>
     <p>
     8、我国的汽车广告只有两种。<p></p>
     中低端车永远是：老子家庭幸福，有娇妻，有可爱的儿女，老子喜欢带全家人开车出去玩！<p></p>
     中高端车永远是：老子事业有成，有美女，有红酒，有合同可以签，老子喜欢独自开车出去装哔！
     </p>


     * hits : 0
     * create_time : 1538208301
     * article_title : 冷笑话
     */

    private int id;
    private int pid;
    private Object url;
    private String title;
    private String content;
    private int hits;
    private String create_time;
    private String article_title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
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

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }
}
