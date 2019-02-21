package com.liuheonline.la.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.GameTypeClass2Entity.ChildBeanX.ChildBean;
import com.liuheonline.la.event.BetClearAllEvent;
import com.liuheonline.la.event.BetClearEvent;
import com.liuheonline.la.event.BetNewRefreshOtherEvent;
import com.liuheonline.la.event.MachineEvent;
import com.liuheonline.la.mvp.presenter.BetNumberPresenter;
import com.liuheonline.la.utils.RandomUtil;
import com.liuheonline.la.utils.StringUtil;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.ioc.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: aini
 * @date 2018/9/18 14:11
 * @description pk10玩法的
 */
public class WidgetNewBetRecyclerView extends LinearLayout {

    private BaseQuickAdapter<ChildBean, BaseViewHolder> baseQuickAdapter;

    private BetNumberPresenter betNumberPresenter;

    private List<ChildBean> betNumberEntityList;

    private GridLayoutManager gridLayoutManager;

    private int group = 0;
    private String playprice = "0";

    @BindId(R.id.number_recycler)
    private RecyclerView numberRecycler;

    @BindId(R.id.select_group)
    private RadioGroup radioGroup;

    @BindId(R.id.child_name)
    private TextView titleName;

    public WidgetNewBetRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public WidgetNewBetRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WidgetNewBetRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        //加载视图的布局
        //绑定View
        View view = LayoutInflater.from(context).inflate(R.layout.widget_betrecycler, this, true);
        ViewUtils.inject(view);
        initData();
        initView(context);
    }


    private void initData() {
        //注册EventBus
        EventBus.getDefault().register(this);

        baseQuickAdapter = new BaseQuickAdapter<ChildBean, BaseViewHolder>(R.layout.item_betnumber) {
            @Override
            protected void convert(BaseViewHolder helper, ChildBean item) {
                helper.getView(R.id.linear2).setVisibility(View.GONE);
                CheckBox checkBox = helper.getView(R.id.number_checkbox);
                checkBox.setOnClickListener(null);
                if (gridLayoutManager.getSpanCount() == 2) {
                    checkBox.setBackgroundResource(R.drawable.bet_number_liangmian_check_bg);
                } else {
                    checkBox.setBackgroundResource(R.drawable.bet_number_check_bg);
                }
                checkBox.setText(item.getCode());
                checkBox.setChecked(item.isSelected());
                //helper.getView(R.id.price).setVisibility(item.getPrice() > 0 ? View.VISIBLE : View.GONE);
                helper.setText(R.id.price, StringUtil.getPrice(Float.parseFloat(item.getPrice()) > 0 ? item.getPrice() + "" : playprice));//价格为0就读取上级玩法价格

                checkBox.setOnClickListener(v -> {
                    item.setGroup(group);
                    item.setSelected(checkBox.isChecked());
                    EventBus.getDefault().post(new BetNewRefreshOtherEvent(item, null, checkBox.isChecked()));
                });
            }
        };
    }


    private void initView(Context context) {
        gridLayoutManager = new GridLayoutManager(context, 5);
        numberRecycler.setLayoutManager(gridLayoutManager);
        numberRecycler.setNestedScrollingEnabled(false);
        numberRecycler.setAdapter(baseQuickAdapter);
    }


    public void setTitle(String title) {
        if (title == null || title.equals("")) {
            gridLayoutManager.setSpanCount(2);
            radioGroup.setVisibility(View.GONE);
            titleName.setVisibility(View.GONE);
        } else {
            gridLayoutManager.setSpanCount(5);
            radioGroup.setVisibility(View.VISIBLE);
            titleName.setVisibility(View.VISIBLE);
            titleName.setText(title);
        }
    }

    //摇一摇机选
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void pushEvent(MachineEvent machineEvent) {
        /*//这里先统一清空之前已选的号码  再进行一下操作
        EventBus.getDefault().post(new BetClearEvent(group));*/

        //随机生成一个数
        int[] number = RandomUtil.getRandomNumber(betNumberEntityList.size(), 1);

        for (int i = 0; i < betNumberEntityList.size(); i++) {
            if (number[0] - 1 == i) {
                betNumberEntityList.get(i).setSelected(true);
                betNumberEntityList.get(i).setGroup(group);
                //event通知activity刷新订单集合
                EventBus.getDefault().post(new BetNewRefreshOtherEvent(betNumberEntityList.get(i), null, true));
            } else {
                betNumberEntityList.get(i).setSelected(false);
            }
        }
        baseQuickAdapter.notifyDataSetChanged();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clearAllSelect(BetClearAllEvent betClearAllEvent) {
        for (ChildBean betNumberEntity : betNumberEntityList) {
            betNumberEntity.setGroup(group);
            betNumberEntity.setSelected(false);
        }
        baseQuickAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.rb_all, R.id.rb_big, R.id.rb_min, R.id.rb_single, R.id.rb_double, R.id.rb_clear})
    private void onClick(View view) {

        //这里先统一清空之前已选的号码  再进行一下操作
        EventBus.getDefault().post(new BetClearEvent(group));


        //先把所有号码选中状态改成false
        for (ChildBean betNumberEntity : betNumberEntityList) {
            betNumberEntity.setGroup(group);
            betNumberEntity.setSelected(false);
        }

        List<ChildBean> betNumberEntityList2 = new ArrayList<>();

        switch (view.getId()) {
            case R.id.rb_all:
                //全选
                for (ChildBean betNumberEntity : betNumberEntityList) {
                    betNumberEntity.setSelected(true);
                    betNumberEntityList2.add(betNumberEntity);
                }
                break;
            case R.id.rb_big:
                for (ChildBean betNumberEntity : betNumberEntityList) {
                    int number = Integer.parseInt(betNumberEntity.getCode());
                    if (number > 5) {
                        betNumberEntity.setSelected(true);
                        betNumberEntityList2.add(betNumberEntity);
                    }
                }
                break;
            case R.id.rb_min:
                for (ChildBean betNumberEntity : betNumberEntityList) {
                    int number = Integer.parseInt(betNumberEntity.getCode());
                    if (number < 6) {
                        betNumberEntity.setSelected(true);
                        betNumberEntityList2.add(betNumberEntity);
                    }
                }
                break;
            case R.id.rb_single:
                for (ChildBean betNumberEntity : betNumberEntityList) {
                    int number = Integer.parseInt(betNumberEntity.getCode());
                    if (number % 2 != 0) {
                        betNumberEntity.setSelected(true);
                        betNumberEntityList2.add(betNumberEntity);
                    }
                }
                break;
            case R.id.rb_double:
                for (ChildBean betNumberEntity : betNumberEntityList) {
                    int number = Integer.parseInt(betNumberEntity.getCode());
                    if (number % 2 == 0) {
                        betNumberEntity.setSelected(true);
                        betNumberEntityList2.add(betNumberEntity);
                    }
                }
                break;
            case R.id.rb_clear:
                for (ChildBean betNumberEntity : betNumberEntityList) {
                    betNumberEntity.setSelected(false);
                }
                break;
        }

        //统一刷新adapter
        baseQuickAdapter.notifyDataSetChanged();

        //event通知activity刷新订单集合
        EventBus.getDefault().post(new BetNewRefreshOtherEvent(null, betNumberEntityList2, true));
    }

    public void setData(List<ChildBean> betNumberEntities) {
        Log.w("thebetnumber2",betNumberEntities.toArray().toString());
        if (betNumberEntities != null && betNumberEntities.size() > 0) {
            betNumberEntityList = betNumberEntities;
            baseQuickAdapter.setNewData(betNumberEntityList);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        EventBus.getDefault().unregister(this);
        super.onDetachedFromWindow();
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public void setprice(String price) {
        this.playprice = price;
    }

}
