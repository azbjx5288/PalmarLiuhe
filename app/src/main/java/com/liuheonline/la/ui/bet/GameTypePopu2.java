package com.liuheonline.la.ui.bet;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.GameTypeClass2Entity;
import com.liuheonline.la.event.BetClearAllEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author: aini
 * @date 2018/7/26 09:31
 * @description 玩法类型popuwindow
 */
public class GameTypePopu2 extends BasePopupWindow {

    //玩法一级选择显示
    private TextView gameTypeOneText;

    //玩法一级选择列表
    private RecyclerView gameTypeOneRecycler;

    //玩法列表一级适配器
    private BaseQuickAdapter<GameTypeClass2Entity, BaseViewHolder> baseQuickAdapterOne;

    //玩法列表数据
    private List<GameTypeClass2Entity> mDatas;


    //选中事件
    private OnSelectTwoClickListener onSelectTwoClickListener;
    //选中的标题
    private String selectTitle;


    public GameTypePopu2(Context context, int w, int h) {
        super(context, w, h);
        initAdapter();
        initView();
    }

    private void initAdapter() {
        baseQuickAdapterOne = new BaseQuickAdapter<GameTypeClass2Entity, BaseViewHolder>(R.layout.item_game_type) {
            @Override
            protected void convert(BaseViewHolder helper, GameTypeClass2Entity item) {
                if (selectTitle == null) {//初始化赋值给选择的
                    selectTitle = item.getTitle();
                }
                CheckBox checkBox = helper.getView(R.id.type_checkbox);
                checkBox.setText(item.getTitle());
                checkBox.setChecked(item.isSelected());
                checkBox.setOnClickListener(v -> {
                    checkBox.setChecked(true);
                    if (!selectTitle.equals(item.getTitle())) {
                        EventBus.getDefault().post(new BetClearAllEvent());
                        selectTitle = item.getTitle();
                    }
                    gameTypeOneText.setText(item.getTitle());
                    refreshDatas(helper.getAdapterPosition());
//                    onSelectClickListener.onSelectClick(item.getTitle(), item.getId(), item.get_child() == null ? new ArrayList<>() : item.get_child(), item.getPrice());
                    onSelectTwoClickListener.onSelectClick(item.getTitle(), item.getId(), item.get_child() == null ? new ArrayList<>() : item.get_child(), item.getChild() == null ? new ArrayList<>() : item.getChild(), item.getPrice());
                    dismissWithOutAnima();
                });
            }
        };
    }

    private void initView() {
        gameTypeOneText = (TextView) findViewById(R.id.game_type_noe);
        gameTypeOneRecycler = (RecyclerView) findViewById(R.id.game_type_noe_recycler);

        GridLayoutManager gridLayoutManagerOne = new GridLayoutManager(getContext(), 3);
        gameTypeOneRecycler.setLayoutManager(gridLayoutManagerOne);
        gameTypeOneRecycler.setAdapter(baseQuickAdapterOne);
    }

    public void setDatas(List<GameTypeClass2Entity> mDatas) {
        this.mDatas = mDatas;
        //默认选中第一个item
        mDatas.get(0).setSelected(true);
        baseQuickAdapterOne.setNewData(mDatas);
    }

    private void refreshDatas(int postion) {
        //刷新默认选中的item
        for (GameTypeClass2Entity gameTypeClass : mDatas) {
            gameTypeClass.setSelected(false);
        }
        mDatas.get(postion).setSelected(true);
        baseQuickAdapterOne.notifyDataSetChanged();
    }

    public void setOnSelectTwoClickListener(OnSelectTwoClickListener onSelectTwoClickListener) {
        this.onSelectTwoClickListener = onSelectTwoClickListener;
    }

    @Override
    protected Animation initShowAnimation() {
        return getTranslateVerticalAnimation(-1f, 0, 500);
    }

    @Override
    protected Animation initExitAnimation() {
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popup_gametype);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_gametype_container);
    }

    public interface OnSelectClickListener {
        void onSelectClick(String title, int id, List<GameTypeClass2Entity.ChildBeanX> list, String price);
    }

    public interface OnSelectTwoClickListener {
        void onSelectClick(String title, int id, List<GameTypeClass2Entity.ChildBeanX> list, List<GameTypeClass2Entity.ChildBeanX.ChildBean> list1, String price);
    }
}
