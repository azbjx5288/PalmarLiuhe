package com.liuheonline.la.entity;

import java.io.Serializable;

/**
 * Auther: RyanLi
 * Data: 2018-09-30 15:47
 * Description:玄机
 */
public class XuanJiEntity  implements Serializable{

    /**
     * period : 2018111
     * content : 王楊盧駱當時體，輕薄為文哂未休。
     爾曹身與名俱滅，不廢江河萬古流。
     */

    private int period;
    private String content;

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
