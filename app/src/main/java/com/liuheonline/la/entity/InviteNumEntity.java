package com.liuheonline.la.entity;

import java.io.Serializable;

/**
 * Auther: RyanLi
 * Data: 2018-09-30 18:47
 * Description:邀请人数
 */
public class InviteNumEntity implements Serializable {

    /**
     * invite_num : 0
     */

    private int invite_num;

    public int getInvite_num() {
        return invite_num;
    }

    public void setInvite_num(int invite_num) {
        this.invite_num = invite_num;
    }
}
