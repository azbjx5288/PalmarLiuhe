package com.liuheonline.la.event;

import com.liuheonline.la.entity.BetNumberEntity;

import java.util.List;

/**
 * @author: aini
 * @date 2018/9/18 17:25
 * @description 刷新订单事件
 */
public class BetRefreshOtherEvent {

    private BetNumberEntity betNumberEntity;

    private List<BetNumberEntity> betNumberEntityList;

    private boolean isCheck;

    public BetRefreshOtherEvent() {
    }

    public BetRefreshOtherEvent(BetNumberEntity betNumberEntity, List<BetNumberEntity> betNumberEntityList, boolean isCheck) {
        this.betNumberEntity = betNumberEntity;
        this.betNumberEntityList = betNumberEntityList;
        this.isCheck = isCheck;
    }

    public List<BetNumberEntity> getBetNumberEntityList() {
        return betNumberEntityList;
    }

    public void setBetNumberEntityList(List<BetNumberEntity> betNumberEntityList) {
        this.betNumberEntityList = betNumberEntityList;
    }

    public BetNumberEntity getBetNumberEntity() {
        return betNumberEntity;
    }

    public void setBetNumberEntity(BetNumberEntity betNumberEntity) {
        this.betNumberEntity = betNumberEntity;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
