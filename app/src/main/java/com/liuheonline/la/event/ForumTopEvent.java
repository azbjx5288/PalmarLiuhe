package com.liuheonline.la.event;

/**
 * @author BenYanYi
 * @date 2019/1/16 23:43
 * @email ben@yanyi.red
 * @overview
 */
public class ForumTopEvent {
    private boolean isTop = false;

    public ForumTopEvent(boolean isTop) {
        this.isTop = isTop;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    @Override
    public String toString() {
        return "ForumTopEvent{" +
                "isTop=" + isTop +
                '}';
    }
}
