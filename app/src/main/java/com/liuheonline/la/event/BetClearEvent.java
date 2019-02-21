package com.liuheonline.la.event;

/**
 * @author: aini
 * @date 2018/9/18 17:48
 * @description 清空号码池事件
 */
public class BetClearEvent {

    private int group;

    public BetClearEvent(int group) {
        this.group = group;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
