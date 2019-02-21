package com.liuheonline.la.event;

/**
 * @author: aini
 * @date 2018/9/18 16:10
 * @description 摇一摇机选事件
 */
public class MachineEvent {

    private boolean flag;

    private int number;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
