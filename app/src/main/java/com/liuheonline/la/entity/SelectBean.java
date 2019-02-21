package com.liuheonline.la.entity;

import java.io.Serializable;

/**
 * @author: aini
 * @date 2018/7/26 10:17
 * @description 选择状态保存实体类
 */
public class SelectBean implements Serializable {

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }
}
