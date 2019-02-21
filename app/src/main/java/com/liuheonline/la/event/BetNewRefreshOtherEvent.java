package com.liuheonline.la.event;

import com.liuheonline.la.entity.GameTypeClass2Entity;

import java.util.List;

/**
 * @author: aini
 * @date 2018年9月27日11:49:10    以前WidgetBetRecyclerView里面是每个请求网络的情况  现在是一次性刷入数据   这个事件和BetRefreshOtherEvent不同在于实体类不一样
 * @description 刷新订单事件
 */
public class BetNewRefreshOtherEvent {

    private GameTypeClass2Entity.ChildBeanX.ChildBean betNumberEntity;

    private List<GameTypeClass2Entity.ChildBeanX.ChildBean> betNumberEntityList;

    private boolean isCheck;

    public BetNewRefreshOtherEvent() {
    }

    public BetNewRefreshOtherEvent(GameTypeClass2Entity.ChildBeanX.ChildBean betNumberEntity, List<GameTypeClass2Entity.ChildBeanX.ChildBean> betNumberEntityList, boolean isCheck) {
        this.betNumberEntity = betNumberEntity;
        this.betNumberEntityList = betNumberEntityList;
        this.isCheck = isCheck;
    }

    public List<GameTypeClass2Entity.ChildBeanX.ChildBean> getBetNumberEntityList() {
        return betNumberEntityList;
    }

    public void setBetNumberEntityList(List<GameTypeClass2Entity.ChildBeanX.ChildBean> betNumberEntityList) {
        this.betNumberEntityList = betNumberEntityList;
    }

    public GameTypeClass2Entity.ChildBeanX.ChildBean getBetNumberEntity() {
        return betNumberEntity;
    }

    public void setBetNumberEntity(GameTypeClass2Entity.ChildBeanX.ChildBean betNumberEntity) {
        this.betNumberEntity = betNumberEntity;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
