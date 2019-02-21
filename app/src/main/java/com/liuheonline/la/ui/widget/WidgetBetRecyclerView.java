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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.BetNumberEntity;
import com.liuheonline.la.event.BetClearAllEvent;
import com.liuheonline.la.event.BetClearEvent;
import com.liuheonline.la.event.BetRefreshOtherEvent;
import com.liuheonline.la.event.MachineEvent;
import com.liuheonline.la.mvp.presenter.BetNumberPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RandomUtil;
import com.yxt.itv.library.base.BaseView;
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
 * @description 辣鸡类
 */
public class WidgetBetRecyclerView extends LinearLayout implements BaseView<List<BetNumberEntity>>{

    private BaseQuickAdapter<BetNumberEntity,BaseViewHolder> baseQuickAdapter;

    private BetNumberPresenter betNumberPresenter;

    private List<BetNumberEntity> betNumberEntityList;

    private GridLayoutManager gridLayoutManager;

    private int group = 0;
    private String playprice = "0";

    @BindId(R.id.number_recycler)
    private RecyclerView numberRecycler;

    @BindId(R.id.select_group)
    private RadioGroup radioGroup;

    @BindId(R.id.child_name)
    private TextView titleName;

    public WidgetBetRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public WidgetBetRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WidgetBetRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context){
        //加载视图的布局
        //绑定View
        View view = LayoutInflater.from(context).inflate(R.layout.widget_betrecycler, this, true);
        ViewUtils.inject(view);
        initData();
        initView(context);
    }


    private void initData(){
        //注册EventBus
        EventBus.getDefault().register(this);

        betNumberPresenter = new BetNumberPresenter();
        betNumberPresenter.attachView(this);

        baseQuickAdapter = new BaseQuickAdapter<BetNumberEntity, BaseViewHolder>(R.layout.item_betnumber) {
            @Override
            protected void convert(BaseViewHolder helper, BetNumberEntity item) {
                helper.getView(R.id.linear2).setVisibility(View.GONE);
                CheckBox checkBox = helper.getView(R.id.number_checkbox);
                checkBox.setOnClickListener(null);
                if( gridLayoutManager.getSpanCount() == 2){
                    checkBox.setBackgroundResource(R.drawable.bet_number_liangmian_check_bg);
                }else{
                    checkBox.setBackgroundResource(R.drawable.bet_number_check_bg);
                }
                checkBox.setText(item.getCode());
                checkBox.setChecked(item.isSelected());
                //helper.getView(R.id.price).setVisibility(item.getPrice() > 0 ? View.VISIBLE : View.GONE);
                helper.setText(R.id.price,item.getPrice() > 0 ? item.getPrice()+"":playprice);//价格为0就读取上级玩法价格

                checkBox.setOnClickListener(v -> {
                    item.setGroup(group);
                    item.setSelected(checkBox.isChecked());
                    EventBus.getDefault().post(new BetRefreshOtherEvent(item,null,checkBox.isChecked()));
                });
            }
        };
    }


    private void initView(Context context){
        gridLayoutManager = new GridLayoutManager(context,5);
        numberRecycler.setLayoutManager(gridLayoutManager);
        numberRecycler.setAdapter(baseQuickAdapter);
    }


    public void setData(int pid){

        betNumberPresenter.getBetNumber(pid);
    }

    public void setTitle(String title){
        if(title == null || title.equals("")){
            gridLayoutManager.setSpanCount(2);
            radioGroup.setVisibility(View.GONE);
            titleName.setVisibility(View.GONE);
        }else{
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
        int[] number = RandomUtil.getRandomNumber(betNumberEntityList.size(),1);

        for (int i = 0; i < betNumberEntityList.size(); i++){
            if(number[0]-1 == i){
                betNumberEntityList.get(i).setSelected(true);
                betNumberEntityList.get(i).setGroup(group);
                //event通知activity刷新订单集合
                EventBus.getDefault().post(new BetRefreshOtherEvent(betNumberEntityList.get(i),null,true));
            }else{
                betNumberEntityList.get(i).setSelected(false);
            }
        }
        baseQuickAdapter.notifyDataSetChanged();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clearAllSelect(BetClearAllEvent betClearAllEvent){
        for (BetNumberEntity betNumberEntity : betNumberEntityList){
            betNumberEntity.setGroup(group);
            betNumberEntity.setSelected(false);
        }
        baseQuickAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.rb_all,R.id.rb_big,R.id.rb_min,R.id.rb_single,R.id.rb_double,R.id.rb_clear})
    private void onClick(View view){

        //这里先统一清空之前已选的号码  再进行一下操作
        EventBus.getDefault().post(new BetClearEvent(group));


        //先把所有号码选中状态改成false
        for (BetNumberEntity betNumberEntity : betNumberEntityList){
            betNumberEntity.setGroup(group);
            betNumberEntity.setSelected(false);
        }

        List<BetNumberEntity> betNumberEntityList2 = new ArrayList<>();

        switch (view.getId()){
            case R.id.rb_all:
                //全选
                for (BetNumberEntity betNumberEntity : betNumberEntityList){
                    betNumberEntity.setSelected(true);
                    betNumberEntityList2.add(betNumberEntity);
                }
                break;
            case R.id.rb_big:
                for (BetNumberEntity betNumberEntity : betNumberEntityList){
                    int number = Integer.parseInt(betNumberEntity.getCode());
                    if(number > 5){
                        betNumberEntity.setSelected(true);
                        betNumberEntityList2.add(betNumberEntity);
                    }
                }
                break;
            case R.id.rb_min:
                for (BetNumberEntity betNumberEntity : betNumberEntityList){
                    int number = Integer.parseInt(betNumberEntity.getCode());
                    if(number < 6){
                        betNumberEntity.setSelected(true);
                        betNumberEntityList2.add(betNumberEntity);
                    }
                }
                break;
            case R.id.rb_single:
                for (BetNumberEntity betNumberEntity : betNumberEntityList){
                    int number = Integer.parseInt(betNumberEntity.getCode());
                    if(number%2 != 0){
                        betNumberEntity.setSelected(true);
                        betNumberEntityList2.add(betNumberEntity);
                    }
                }
                break;
            case R.id.rb_double:
                for (BetNumberEntity betNumberEntity : betNumberEntityList){
                    int number = Integer.parseInt(betNumberEntity.getCode());
                    if(number%2 == 0){
                        betNumberEntity.setSelected(true);
                        betNumberEntityList2.add(betNumberEntity);
                    }
                }
                break;
            case R.id.rb_clear:
                for (BetNumberEntity betNumberEntity : betNumberEntityList){
                    betNumberEntity.setSelected(false);
                }
                break;
        }

        //统一刷新adapter
        baseQuickAdapter.notifyDataSetChanged();

        //event通知activity刷新订单集合
        EventBus.getDefault().post(new BetRefreshOtherEvent(null,betNumberEntityList2,true));
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onLoadFailed(int code, String error) {
        Toast.makeText(LiuHeApplication.context,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successed(List<BetNumberEntity> betNumberEntities) {
        Log.w("thebetnumber1",betNumberEntities.toArray().toString());
        if(betNumberEntities != null && betNumberEntities.size() > 0){
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
