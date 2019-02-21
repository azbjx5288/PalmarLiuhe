package com.liuheonline.la.event;

import com.liuheonline.la.entity.GameTypeNewClass3Entity;

import java.util.List;

/**
 * @author: aini
 * @date 2018年9月27日11:49:10    以前WidgetBetRecyclerView里面是每个请求网络的情况  现在是一次性刷入数据   这个事件和BetRefreshOtherEvent不同在于实体类不一样
 * @description 刷新订单事件
 */
public class BetNew3RefreshOtherEvent {

    private GameTypeNewClass3Entity betNumberEntity;

    private List<GameTypeNewClass3Entity> betNumberEntityList;

    private boolean isCheck;

    public BetNew3RefreshOtherEvent() {
    }

    public BetNew3RefreshOtherEvent(GameTypeNewClass3Entity betNumberEntity, List<GameTypeNewClass3Entity> betNumberEntityList, boolean isCheck) {
        this.betNumberEntity = betNumberEntity;
        this.betNumberEntityList = betNumberEntityList;
        this.isCheck = isCheck;
    }

    public List<GameTypeNewClass3Entity> getBetNumberEntityList() {
        return betNumberEntityList;
    }

    public void setBetNumberEntityList(List<GameTypeNewClass3Entity> betNumberEntityList) {
        this.betNumberEntityList = betNumberEntityList;
    }

    public GameTypeNewClass3Entity getBetNumberEntity() {
        return betNumberEntity;
    }

    public void setBetNumberEntity(GameTypeNewClass3Entity betNumberEntity) {
        this.betNumberEntity = betNumberEntity;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
