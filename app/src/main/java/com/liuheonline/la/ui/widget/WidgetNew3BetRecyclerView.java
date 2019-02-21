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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.GameTypeNewClass3Entity;
import com.liuheonline.la.event.BetClearAllEvent;
import com.liuheonline.la.event.BetClearEvent;
import com.liuheonline.la.event.BetNew3RefreshOtherEvent;
import com.liuheonline.la.event.MachineEvent;
import com.liuheonline.la.mvp.presenter.BetNumberPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RandomUtil;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.liuheonline.la.utils.StringUtil;
import com.yxt.itv.library.http.retrofit.LogUtil;
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
public class WidgetNew3BetRecyclerView extends LinearLayout {

    private BaseQuickAdapter<GameTypeNewClass3Entity, BaseViewHolder> baseQuickAdapter;

    private BetNumberPresenter betNumberPresenter;

    private List<GameTypeNewClass3Entity> betNumberEntityList;

    private GridLayoutManager gridLayoutManager;

    private int group = 0;
    private String playprice = "0";
    private String oneTitle = "";

    @BindId(R.id.number_recycler)
    private RecyclerView numberRecycler;

    @BindId(R.id.select_group)
    private RadioGroup radioGroup;

    @BindId(R.id.child_name)
    private TextView titleName;
    Context context;

    List<String> namelist = new ArrayList<>();//装 万位千位等的名字
    public WidgetNew3BetRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public WidgetNew3BetRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WidgetNew3BetRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        this.context = context;
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

        baseQuickAdapter = new BaseQuickAdapter<GameTypeNewClass3Entity, BaseViewHolder>(R.layout.item_betnumber) {
            @Override
            protected void convert(BaseViewHolder helper, GameTypeNewClass3Entity item) {
                helper.getView(R.id.linear2).setVisibility(View.GONE);
                CheckBox checkBox = helper.getView(R.id.number_checkbox);
                checkBox.setOnClickListener(null);
                if (gridLayoutManager.getSpanCount() == 2) {
                    checkBox.setBackgroundResource(R.drawable.bet_number_liangmian_check_bg);
                } else {
                    if (SharedperfencesUtil.getString(LiuHeApplication.context, "onegametitle").equals("斗牛") || SharedperfencesUtil.getString(LiuHeApplication.context, "onegametitle").equals("顺子")) {
                        checkBox.setBackgroundResource(R.drawable.bet_number_rectangle_check_bg);
                    } else {
                        checkBox.setBackgroundResource(R.drawable.bet_number_check_bg);
                    }
                }
                checkBox.setText(item.getCode());
                checkBox.setChecked(item.isSelected());
                //helper.getView(R.id.price).setVisibility(item.getPrice() > 0 ? View.VISIBLE : View.GONE);
                helper.setText(R.id.price, StringUtil.getPrice(Float.parseFloat(item.getPrice()) > 0 ? item.getPrice() + "" : playprice));//价格为0就读取上级玩法价格

                checkBox.setOnClickListener(v -> {
                    item.setGroup(group);
                    item.setSelected(checkBox.isChecked());
                    EventBus.getDefault().post(new BetNew3RefreshOtherEvent(item, null, checkBox.isChecked()));
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
        LogUtil.i("info", title);
        LogUtil.i("info", oneTitle);
        if (title == null || title.equals("")) {
            gridLayoutManager.setSpanCount(2);
            radioGroup.setVisibility(View.GONE);
            titleName.setVisibility(View.GONE);
        } else {
            /*if (title.equals("后二大小单双")||title.equals("后三大小单双")||title.equals("前二大小单双")||title.equals("前三大小单双")||title.equals("万位")||
                    title.equals("千位")||title.equals("百位")||title.equals("十位")||title.equals("个位")||title.equals("总和")){*/
            if (SharedperfencesUtil.getString(LiuHeApplication.context, "onegametitle").equals("大小单双")) {
                gridLayoutManager.setSpanCount(4);
                radioGroup.setVisibility(View.GONE);
            } else if (SharedperfencesUtil.getString(LiuHeApplication.context, "onegametitle").contains("任选")) {

                if (!title.equals("万位") && !title.equals("千位") && !title.equals("百位") && !title.equals("十位") && !title.equals("个位") && !title.equals("三重号") && !title.equals("二重号")) {
                    View view = LayoutInflater.from(context).inflate(R.layout.footer_checkbox, null);
                    CheckBox checkBox1 = view.findViewById(R.id.wan);
                    CheckBox checkBox2 = view.findViewById(R.id.qian);
                    CheckBox checkBox3 = view.findViewById(R.id.bai);
                    CheckBox checkBox4 = view.findViewById(R.id.shi);
                    CheckBox checkBox5 = view.findViewById(R.id.ge);
                    checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                            if (b) {
                                namelist.add("万位");
                            } else {
                                if (namelist.contains("万位")) {
                                    namelist.remove("万位");
                                }
                            }
                            EventBus.getDefault().post(new BetNew3RefreshOtherEvent(null, null, true));
                        }
                    });
                    checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (b) {
                                namelist.add("千位");
                            } else {
                                if (namelist.contains("千位")) {
                                    namelist.remove("千位");
                                }
                            }
                            EventBus.getDefault().post(new BetNew3RefreshOtherEvent(null, null, true));
                        }
                    });
                    checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                            if (b) {
                                namelist.add("百位");
                            } else {
                                if (namelist.contains("百位")) {
                                    namelist.remove("百位");
                                }
                            }
                            EventBus.getDefault().post(new BetNew3RefreshOtherEvent(null, null, true));
                        }
                    });
                    checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                            if (b) {
                                namelist.add("十位");
                            } else {
                                if (namelist.contains("十位")) {
                                    namelist.remove("十位");
                                }
                            }
                            EventBus.getDefault().post(new BetNew3RefreshOtherEvent(null, null, true));
                        }
                    });
                    checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                            if (b) {
                                namelist.add("个位");
                            } else {
                                if (namelist.contains("个位")) {
                                    namelist.remove("个位");
                                }
                            }
                            EventBus.getDefault().post(new BetNew3RefreshOtherEvent(null, null, true));
                        }
                    });



                        if (SharedperfencesUtil.getString(LiuHeApplication.context, "onegametitle").equals("任选三")) {
                            checkBox3.setChecked(true);
                            checkBox4.setChecked(true);
                            checkBox5.setChecked(true);
                        } else if (SharedperfencesUtil.getString(LiuHeApplication.context, "onegametitle").equals("任选四")) {
                            checkBox2.setChecked(true);
                            checkBox3.setChecked(true);
                            checkBox4.setChecked(true);
                            checkBox5.setChecked(true);
                        } else if (SharedperfencesUtil.getString(LiuHeApplication.context, "onegametitle").equals("任选二")) {
                            checkBox4.setChecked(true);
                            checkBox5.setChecked(true);
                        }

                        baseQuickAdapter.addFooterView(view);
                    } else {
                        baseQuickAdapter.removeAllFooterView();
                    }

            }else if (SharedperfencesUtil.getString(LiuHeApplication.context, "onegametitle").equals("龙虎") || SharedperfencesUtil.getString(LiuHeApplication.context, "onegametitle").equals("顺子") || SharedperfencesUtil.getString(LiuHeApplication.context, "onegametitle").equals("斗牛")) {
                gridLayoutManager.setSpanCount(3);
                radioGroup.setVisibility(View.GONE);
            }
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
                EventBus.getDefault().post(new BetNew3RefreshOtherEvent(betNumberEntityList.get(i), null, true));
            } else {
                betNumberEntityList.get(i).setSelected(false);
            }
        }
        baseQuickAdapter.notifyDataSetChanged();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clearAllSelect(BetClearAllEvent betClearAllEvent) {
        for (GameTypeNewClass3Entity betNumberEntity : betNumberEntityList) {
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
        for (GameTypeNewClass3Entity betNumberEntity : betNumberEntityList) {
            betNumberEntity.setGroup(group);
            betNumberEntity.setSelected(false);
        }

        List<GameTypeNewClass3Entity> betNumberEntityList2 = new ArrayList<>();

        switch (view.getId()) {
            case R.id.rb_all:
                //全选
                for (GameTypeNewClass3Entity betNumberEntity : betNumberEntityList) {
                    betNumberEntity.setSelected(true);
                    betNumberEntityList2.add(betNumberEntity);
                }
                break;
            case R.id.rb_big:
                for (GameTypeNewClass3Entity betNumberEntity : betNumberEntityList) {
                    int number = Integer.parseInt(betNumberEntity.getCode());
                    if (number >= 5) {
                        betNumberEntity.setSelected(true);
                        betNumberEntityList2.add(betNumberEntity);
                    }
                }
                break;
            case R.id.rb_min:
                for (GameTypeNewClass3Entity betNumberEntity : betNumberEntityList) {
                    int number = Integer.parseInt(betNumberEntity.getCode());
                    if (number <= 4) {
                        betNumberEntity.setSelected(true);
                        betNumberEntityList2.add(betNumberEntity);
                    }
                }
                break;
            case R.id.rb_single:
                for (GameTypeNewClass3Entity betNumberEntity : betNumberEntityList) {
                    int number = Integer.parseInt(betNumberEntity.getCode());
                    if (number % 2 != 0) {
                        betNumberEntity.setSelected(true);
                        betNumberEntityList2.add(betNumberEntity);
                    }
                }
                break;
            case R.id.rb_double:
                for (GameTypeNewClass3Entity betNumberEntity : betNumberEntityList) {
                    int number = Integer.parseInt(betNumberEntity.getCode());
                    if (number % 2 == 0) {
                        betNumberEntity.setSelected(true);
                        betNumberEntityList2.add(betNumberEntity);
                    }
                }
                break;
            case R.id.rb_clear:
                for (GameTypeNewClass3Entity betNumberEntity : betNumberEntityList) {
                    betNumberEntity.setSelected(false);
                }
                break;
        }

        //统一刷新adapter
        baseQuickAdapter.notifyDataSetChanged();

        //event通知activity刷新订单集合
        EventBus.getDefault().post(new BetNew3RefreshOtherEvent(null, betNumberEntityList2, true));
    }

    public void setData(List<GameTypeNewClass3Entity> betNumberEntities) {
        Log.w("thebetnumber3",betNumberEntities.toArray().toString());
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

    public void setOneTitle(String title){
        this.oneTitle = title;
    }

    public List<String> getNameList(){
        return namelist;
    }

}
