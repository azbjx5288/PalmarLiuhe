package com.liuheonline.la.ui.widget.commentwidget;

/**
 * @author: aini
 * @date 2018/7/20 10:55
 * @description
 */
public interface IComment <T> {

    /**评论创建者*/
    String getCommentCreatorName();
    /**评论回复人*/
    String getReplyerName();
    /**评论内容*/
    String getCommentContent();

}
