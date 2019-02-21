package com.liuheonline.la.ui.bet;

import android.app.Service;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liuheonline.la.entity.BetNumberEntity;
import com.liuheonline.la.entity.GameTypeClass2Entity;
import com.liuheonline.la.entity.LotteryEntity;
import com.liuheonline.la.entity.LotteryOtherDetailsEntity;
import com.liuheonline.la.entity.LotteryOtherEntity;
import com.liuheonline.la.entity.UserInfo;
import com.liuheonline.la.event.BetClearAllEvent;
import com.liuheonline.la.event.BetClearEvent;
import com.liuheonline.la.event.BetNewRefreshOtherEvent;
import com.liuheonline.la.event.MachineEvent;
import com.liuheonline.la.mvp.presenter.GameTypeClassPresenter;
import com.liuheonline.la.mvp.presenter.LotterySidPresenter;
import com.liuheonline.la.mvp.presenter.UserInfoPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.history.HistoryActivity;
import com.liuheonline.la.ui.main.trend.LotteryTrend;
import com.liuheonline.la.ui.user.lotterylog.LotteryLogActivity;
import com.liuheonline.la.ui.widget.WidgetNewBetRecyclerView;
import com.liuheonline.la.utils.LotteryTypeUtil;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.liuheonline.la.utils.TimeUtil;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author: aini
 * @date 2018/7/25 18:03
 * @description Pk10投注页面
 */
public class BetLottery2Activity extends BaseMVPActivity<BaseView<LotteryEntity>, LotterySidPresenter, LotteryEntity>
        implements GameTypePopu2.OnSelectTwoClickListener, SensorEventListener, SettingPopu.OnViewClickListener {

    @BindId(R.id.title_layout)
    private RelativeLayout mTitleLayout;

    @BindId(R.id.recycler_linear)
    private LinearLayout recyclerLinear;

    @BindId(R.id.next_issue)
    private TextView nextIssueTx;

    @BindId(R.id.next_time)
    private TextView nextTimeTx;

    /*@BindId(R.id.issue)
    private TextView issueTx;*/

    @BindId(R.id.number01)
    private TextView numberTx01;

    @BindId(R.id.number02)
    private TextView numberTx02;

    @BindId(R.id.number03)
    private TextView numberTx03;

    @BindId(R.id.number04)
    private TextView numberTx04;

    @BindId(R.id.number05)
    private TextView numberTx05;

    @BindId(R.id.number06)
    private TextView numberTx06;

    @BindId(R.id.number07)
    private TextView numberTx07;
    @BindId(R.id.number08)
    private TextView numberTx08;
    @BindId(R.id.number09)
    private TextView numberTx09;
    @BindId(R.id.number10)
    private TextView numberTx10;

    @BindId(R.id.anim_number01)
    private TextView animNumberTx01;

    @BindId(R.id.anim_number02)
    private TextView animNumberTx02;

    @BindId(R.id.anim_number03)
    private TextView animNumberTx03;

    @BindId(R.id.anim_number04)
    private TextView animNumberTx04;

    @BindId(R.id.anim_number05)
    private TextView animNumberTx05;

    @BindId(R.id.anim_number06)
    private TextView animNumberTx06;

    @BindId(R.id.anim_number07)
    private TextView animNumberTx07;

    @BindId(R.id.game_type)
    private TextView mGameTypeText;

    @BindId(R.id.number_hint)
    private TextView numberHintText;

    @BindId(R.id.price_linear)
    private LinearLayout priceLinear;

    @BindId(R.id.shake)
    private TextView shake;

    @BindId(R.id.price_top)
    private TextView priceTopText;

    @BindId(R.id.select_number)
    private TextView selectNumberText;

    @BindId(R.id.edit_money)
    private EditText editMoney;

    @BindId(R.id.close_text)
    private TextView closeText;

    @BindId(R.id.submit)
    private Button butSubmit;


    List<GameTypeClass2Entity> gameTypeClass2Entitiys;
    //彩种ID
    private int sid;

    //玩法规则
    private String rulesContent = "";

    //封盘时间
    private long closeTime;

    //一级玩法 ID
    private int oneGameId;

    //一级玩法名
    private String oneGameTitle = "";

    //old 玩法ID
    private int oldGameId;

    //获取最新开奖
    private LotterySidPresenter lotteryPresenter;

    //玩法列表
    private GameTypeClassPresenter gameTypeClassPresenter;

    //当前期数
    private long issue;

    //倒计时
    private CountDownTimer countDownTimer;

    //设置popu
    private SettingPopu settingPopu;

    //玩法列表popu
    private GameTypePopu2 gameTypePopu;

    //选中号码池显示  只有显示作用
    private List<GameTypeClass2Entity.ChildBeanX.ChildBean> selectBetNumberEntityList = new ArrayList<>();

    //底部选中号码展示
    private StringBuffer numberBuffer = new StringBuffer();

    private Handler handler = new Handler();

    //单注金额
    private int money = 2;

    //玩法价格
    private String playPrice;

    //订单保存集合
    private List<LotteryOtherDetailsEntity> lotteryOtherDetailsEntityList = new ArrayList<>();

    private LotteryEntity lotteryEntity;

    public static LotteryOtherEntity staticLottery2OtherEntity;
    UserInfoPresenter userInfoPresenter;

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sid = bundle.getInt("sid");
            closeTime = bundle.getLong("closeTime");
            rulesContent = bundle.getString("rulesContent");
        }

        //注册event
        EventBus.getDefault().register(this);

        //获取传感器管理器
        SensorManager sensorManager = (SensorManager) this.getSystemService(Service.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_betlottery2);
    }

    @Override
    protected void initTitle() {
    }

    @Override
    protected void initView() {

        //初始化单注金额
        editMoney.setText(money + "");
        //初始化设置popu
        settingPopu = new SettingPopu(this, new int[]{R.id.trend}, this);
        //初始化玩法选择popuwindow
        gameTypePopu = new GameTypePopu2(this, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        gameTypePopu.setOnSelectClickListener(this);
        gameTypePopu.setOnSelectTwoClickListener(this);

        if (SharedperfencesUtil.getInt(this, "yaoyiyaoid") == 1) {
            shake.setTextColor(Color.parseColor("#FF0000"));
            Drawable top = getResources().getDrawable(R.mipmap.yaobuyao);
            shake.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        }

        //监听输入是否完毕
        editMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.postDelayed(runnable, 200);
            }
        });
    }

    //修改单注金额
    private Runnable runnable = () -> {
        //修改金额
        String moneyStr = editMoney.getText().toString();
        if (!moneyStr.equals("")) {
            if (Integer.parseInt(moneyStr) >= 1) {
                money = Integer.parseInt(moneyStr);
            } else {
                money = 2;
                editMoney.setText("2");
            }
        }
    };


    //设置窗口点击事件回调
    @Override
    public void onVeiwCilck(View v) {
        Bundle bundle = new Bundle();
        bundle.putInt("lotteryid", SharedperfencesUtil.getInt(this, "checkSid"));
        switch (v.getId()) {
            case R.id.trend:
                startActivity(LotteryTrend.class, bundle);
                break;
            case R.id.recording:
                startActivity(LotteryLogActivity.class, bundle);
                break;
            case R.id.history:
                startActivity(HistoryActivity.class, bundle);
                break;
            case R.id.game_style:
                bundle.putString("rulesContent", rulesContent);
                startActivity(RulesActivity.class, bundle);
                break;
        }
    }

    @OnClick({R.id.setting_img, R.id.game_type, R.id.back, R.id.clear_img, R.id.submit, R.id.shake})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_img:
                settingPopu.showPopupWindow(R.id.setting_img);
                break;
            case R.id.game_type:
                gameTypePopu.showPopupWindow(mTitleLayout);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.clear_img:
                clearSelectNumber(new BetClearEvent(0));
                EventBus.getDefault().post(new BetClearAllEvent());
                break;
            case R.id.shake:
                MachineSelection();
                break;
            case R.id.submit:
                //提交订单
                //计算距下期开奖时间
                submitOther();
                break;
        }
    }

    //玩法选择事件
    @Override
    public void onSelectClick(String title, int id, List<GameTypeClass2Entity.ChildBeanX> list, List<GameTypeClass2Entity.ChildBeanX.ChildBean> listxx, String playPrices) {
        oneGameTitle = title;
        mGameTypeText.setText(title);

        oneGameId = id;
        playPrice = playPrices;
        if (oldGameId != id) {
            //刷新玩法前先清空之前保存的订单数据
            clearSelectNumber(new BetClearEvent(0));
            oldGameId = id;
            //刷新号码列表
            if (list.size() > 0 && listxx.size() == 0) {
                addView(list);
                //baseQuickAdapter.setNewData(list);
            } else if (list.size() == 0 && listxx.size() > 0) {
                GameTypeClass2Entity.ChildBeanX childBean = new GameTypeClass2Entity.ChildBeanX();
                childBean.setId(id);
                childBean.setChild(listxx);
                List<GameTypeClass2Entity.ChildBeanX> list1 = new ArrayList<>();
                list1.add(childBean);
                //baseQuickAdapter.setNewData(list1);
                addView(list1);
            } else {
                GameTypeClass2Entity.ChildBeanX childBean = new GameTypeClass2Entity.ChildBeanX();
                childBean.setId(id);
                List<GameTypeClass2Entity.ChildBeanX> list1 = new ArrayList<>();
                list1.add(childBean);
                //baseQuickAdapter.setNewData(list1);
                addView(list1);
            }
        }
    }


    private void addView(List<GameTypeClass2Entity.ChildBeanX> list) {
        recyclerLinear.removeAllViews();

        for (int i = 0; i < list.size(); i++) {
            WidgetNewBetRecyclerView widgetBetRecyclerView = new WidgetNewBetRecyclerView(this);
            ViewGroup.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            widgetBetRecyclerView.setLayoutParams(layoutParams);
            widgetBetRecyclerView.setTitle(list.get(i).getTitle());

            //判断该玩法是否需要组id
            if (list.get(i).getTitle() != null && !list.get(i).getTitle().equals("")) {
                widgetBetRecyclerView.setGroup(i + 1);
            }
            widgetBetRecyclerView.setprice(playPrice);
            widgetBetRecyclerView.setData(list.get(i).getChild());
            recyclerLinear.addView(widgetBetRecyclerView);
        }

        //刷新
        recyclerLinear.invalidate();
    }

    //摇一摇
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (SharedperfencesUtil.getInt(this, "yaoyiyaoid") == 1) {
            //获取传感器类型
            int sensorType = event.sensor.getType();
            //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
            float[] values = event.values;
            //如果传感器类型为加速度传感器，则判断是否为摇一摇
            if (sensorType == Sensor.TYPE_ACCELEROMETER) {
                if ((Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math
                        .abs(values[2]) > 17)) {
                    MachineSelection();
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    //摇一摇 机选
    private void MachineSelection() {
        clearSelectNumber(new BetClearEvent(0));
        EventBus.getDefault().post(new MachineEvent());
    }

    //清空已选中号码池
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clearSelectNumber(BetClearEvent betClearEvent) {

        Iterator<GameTypeClass2Entity.ChildBeanX.ChildBean> it = selectBetNumberEntityList.iterator();
        while (it.hasNext()) {
            GameTypeClass2Entity.ChildBeanX.ChildBean betNumberEntity = it.next();
            if (betClearEvent.getGroup() == 0) {
                it.remove();
            } else {
                if (betNumberEntity.getGroup() == betClearEvent.getGroup()) {
                    it.remove();
                }
            }

        }
        //刷新订单集合
        refreshNumberSelect(null);
    }


    // 计算注数 刷新ui  常规玩法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshNumberSelect(BetNewRefreshOtherEvent betRefreshOther) {

        if (betRefreshOther != null) {
            if (betRefreshOther.isCheck()) {
                if (betRefreshOther.getBetNumberEntity() == null) {
                    selectBetNumberEntityList.addAll(betRefreshOther.getBetNumberEntityList());
                } else {
                    selectBetNumberEntityList.add(betRefreshOther.getBetNumberEntity());
                }
            } else {
                for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
                    GameTypeClass2Entity.ChildBeanX.ChildBean numberEntity = selectBetNumberEntityList.get(i);
                    if (numberEntity.getId() == betRefreshOther.getBetNumberEntity().getId()) {
                        selectBetNumberEntityList.remove(i);
                    }
                }
            }
        }

        lotteryOtherDetailsEntityList.clear();
        Log.w("onegametitle", oneGameTitle);
        switch (oneGameTitle) {
            case "前二":
            case "前三":
            case "后二":
            case "后三":
                //这里单独计算复试  组合类玩法的注数

                //保存每组的号码
                List<List<BetNumberEntity>> groupList = new ArrayList<>();

                //可以根据betNumberEntity中的group id取出每组号码
                int groupNum = recyclerLinear.getChildCount(); //根据recyclerLinear的子View数量  获取一共有几组
               /* for(int j = 1; j <= groupNum; j++){
                    groupList.add(new ArrayList<>());*/
                for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
                    Log.w("groups", selectBetNumberEntityList.get(i).getGroup() + "");
                    if (selectBetNumberEntityList.get(i).getGroup() == 1) {//是第一组的号码
                        // groupList.get(j-1).add(selectBetNumberEntityList.get(i));
                        if (groupNum >= 2) {//至少有两个子view
                            for (int z = 0; z < selectBetNumberEntityList.size(); z++) {
                                if (selectBetNumberEntityList.get(z).getGroup() == 2) {
                                    if (!selectBetNumberEntityList.get(i).getCode().equals(selectBetNumberEntityList.get(z).getCode())) {//排除第一组和第二组相同的数字
                                        if (groupNum >= 3) {//至少有三个子view
                                            for (int x = 0; x < selectBetNumberEntityList.size(); x++) {
                                                if (selectBetNumberEntityList.get(x).getGroup() == 3) {
                                                    if (selectBetNumberEntityList.get(i).getCode().equals(selectBetNumberEntityList.get(x).getCode()) ||
                                                            selectBetNumberEntityList.get(z).getCode().equals(selectBetNumberEntityList.get(x).getCode())
                                                            || selectBetNumberEntityList.get(z).getCode().equals(selectBetNumberEntityList.get(i).getCode())) {
                                                        //第三组的号码不与第一组和第二组中的数相同

                                                    } else {
                                                        //有三个view的情况
                                                        LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                                        lotteryOtherDetailsEntity.setLottery_price(money);
                                                        lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                                        lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                                        lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                                        lotteryOtherDetailsEntity.setLottery_name(selectBetNumberEntityList.get(i).getCode() + "|" +
                                                                selectBetNumberEntityList.get(z).getCode() + "|" + selectBetNumberEntityList.get(x).getCode());
                                                        Log.w("thegroup", selectBetNumberEntityList.get(i).getCode() + " " +
                                                                selectBetNumberEntityList.get(z).getCode() + " " + selectBetNumberEntityList.get(x).getCode());
                                                        lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(i).getPrice()));
                                                        lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                                                    }


                                                }
                                            }
                                        } else {
                                            //只有两个子view
                                            LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                            lotteryOtherDetailsEntity.setLottery_price(money);
                                            lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                            lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                            lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                            lotteryOtherDetailsEntity.setLottery_name(selectBetNumberEntityList.get(i).getCode() + "|" +
                                                    selectBetNumberEntityList.get(z).getCode());
                                            lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(i).getPrice()));
                                            lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
                // }

                //根据 groupList 计算出最终注数  并返回最终订单


                break;
            default:
                //计算注数  常规玩法
                for (GameTypeClass2Entity.ChildBeanX.ChildBean betNumberEntity1 : selectBetNumberEntityList) {
                    LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                    lotteryOtherDetailsEntity.setLottery_price(money);
                    lotteryOtherDetailsEntity.setPlayprice(playPrice);
                    lotteryOtherDetailsEntity.setLottery_id(betNumberEntity1.getId());
                    lotteryOtherDetailsEntity.setLottery_child_name(betNumberEntity1.getAttr());
                    if (betNumberEntity1.getGroup() == 0) {
                        lotteryOtherDetailsEntity.setLottery_name(betNumberEntity1.getCode());
                    } else {
                        lotteryOtherDetailsEntity.setLottery_name(betNumberEntity1.getGroup() + "|" + betNumberEntity1.getCode());
                    }
                    lotteryOtherDetailsEntity.setOdds(Double.parseDouble(betNumberEntity1.getPrice()));
                    lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                }
                break;

        }


        //刷新底部选中池
        numberBuffer.setLength(0);
        numberBuffer.append(getSelect(selectBetNumberEntityList));
        if (numberBuffer.toString().trim().equals("")) {
            selectNumberText.setVisibility(View.GONE);
        } else {
            selectNumberText.setVisibility(View.VISIBLE);
        }
        selectNumberText.setText(numberBuffer);
    }

    private String getSelect(List<GameTypeClass2Entity.ChildBeanX.ChildBean> selectBetNumberEntityList) {
        String a1 = " |";
        String a2 = " |";
        String a3 = " |";
        String a4 = " |";
        String a5 = " |";
        String a6 = " |";
        String a7 = " |";
        String a8 = " |";
        String a9 = " |";
        String a10 = " |";

        for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
            GameTypeClass2Entity.ChildBeanX.ChildBean numberEntity = selectBetNumberEntityList.get(i);
            //numberBuffer.append(numberEntity.getCode()).append(" ");
            switch (numberEntity.getGroup()) {
                case 1:
                    a1 += " " + numberEntity.getCode();
                    break;
                case 2:
                    a2 += " " + numberEntity.getCode();
                    break;
                case 3:
                    a3 += " " + numberEntity.getCode();
                    break;
                case 4:
                    a4 += " " + numberEntity.getCode();
                    break;
                case 5:
                    a5 += " " + numberEntity.getCode();
                    break;
                case 6:
                    a6 += " " + numberEntity.getCode();
                    break;
                case 7:
                    a7 += " " + numberEntity.getCode();
                    break;
                case 8:
                    a8 += " " + numberEntity.getCode();
                    break;
                case 9:
                    a9 += " " + numberEntity.getCode();
                    break;
                case 10:
                    a10 += " " + numberEntity.getCode();
                    break;
            }
        }
        String content = (a1.length() > 2 ? a1 : "") + (a2.length() > 2 ? a2 : "") + (a3.length() > 2 ? a3 : "") + (a4.length() > 2 ? a4 : "") +
                (a5.length() > 2 ? a5 : "") + (a6.length() > 2 ? a6 : "") + (a7.length() > 2 ? a7 : "") + (a8.length() > 2 ? a8 : "") +
                (a9.length() > 2 ? a9 : "") + (a10.length() > 2 ? a10 : "");
        if (content.length() >= 3) {
            //去掉第一个“|”
            content = content.substring(2, content.length());
        }
        return content;
    }

    //提交订单
    private void submitOther() {
        if (lotteryOtherDetailsEntityList.size() > 0) {
            LotteryOtherEntity lotteryOtherEntity = new LotteryOtherEntity();
            long issue = lotteryEntity.getLottery().getNext_periods();

            staticLottery2OtherEntity = null;
            //修改金额
            for (LotteryOtherDetailsEntity lotteryOtherDetailsEntity : lotteryOtherDetailsEntityList) {
                lotteryOtherDetailsEntity.setLottery_price(money);
            }


            lotteryOtherEntity.setBet_periods(issue);
            lotteryOtherEntity.setLottery_amount(lotteryOtherDetailsEntityList.size() * money);
            lotteryOtherEntity.setLottery_num(lotteryOtherDetailsEntityList.size());
            lotteryOtherEntity.setLottery_rebates_price(0);
            lotteryOtherEntity.setBet_info(lotteryOtherDetailsEntityList);
            lotteryOtherEntity.setSpecie_id(sid);
            lotteryOtherEntity.setLottery_id(oneGameId);
            lotteryOtherEntity.setLottery_class_name(oneGameTitle);

            staticLottery2OtherEntity = lotteryOtherEntity;

            //跳转到订单页面
            Bundle bundle = new Bundle();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nexttime = sdf.format(new Date(lotteryEntity.getLottery().getNext_time() * 1000));
            bundle.putString("nextTime", nexttime);
            bundle.putString("type", oneGameTitle);
            startActivity(BetLotteryOtherActivity.class, bundle);
        } else {
            Toast.makeText(this, "投注号码有误", Toast.LENGTH_SHORT).show();
        }


    }


    //开奖倒计时
    private void initLotteryData(LotteryEntity lotteryEntity) {
        //当前期数
        issue = lotteryEntity.getLottery().getPeriods();
        //计算距下期开奖时间
        long endTime = lotteryEntity.getLottery().getNext_time();
        long startTime = Integer.parseInt(lotteryEntity.getLottery().getHeader_time());
        refreshTime(TimeUtil.getNextDate(startTime, endTime));
        long periods1 = lotteryEntity.getLottery().getNext_periods();
        String periodsstr = periods1 + "";
        long periodsint;
        if (periodsstr.length() < 10) {
            periodsint = lotteryEntity.getLottery().getNext_periods();
        } else {
            periodsint = Integer.parseInt(periodsstr.substring(8, periodsstr.length()));
        }
        nextIssueTx.setText("距" + periodsint + "期：");
        //issueTx.setText(issue+"期开奖：");
        String[] strs = lotteryEntity.getLottery().getNumber().split("\\|");
        numberTx01.setText(strs[0]);
        numberTx02.setText(strs[1]);
        numberTx03.setText(strs[2]);
        numberTx04.setText(strs[3]);
        numberTx05.setText(strs[4]);
        numberTx06.setText(strs[5]);
        numberTx07.setText(strs[6]);
        numberTx08.setText(strs[7]);
        numberTx09.setText(strs[8]);
        numberTx10.setText(strs[9]);


        /*animNumberTx01.setText(lotteryEntity.getLottery().getNum1_zodiac());
        animNumberTx02.setText(lotteryEntity.getLottery().getNum2_zodiac());
        animNumberTx03.setText(lotteryEntity.getLottery().getNum3_zodiac());
        animNumberTx04.setText(lotteryEntity.getLottery().getNum4_zodiac());
        animNumberTx05.setText(lotteryEntity.getLottery().getNum5_zodiac());
        animNumberTx06.setText(lotteryEntity.getLottery().getNum6_zodiac());
        animNumberTx07.setText(lotteryEntity.getLottery().getNum_s_zodiac());*/
        numberTx01.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[0]));
        numberTx02.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[1]));
        numberTx03.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[2]));
        numberTx04.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[3]));
        numberTx05.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[4]));
        numberTx06.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[5]));
        numberTx07.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[6]));
        numberTx08.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[7]));
        numberTx09.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[8]));
        numberTx10.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[9]));
    }

    @Override
    protected void initPresenter() {
        presenter = new LotterySidPresenter();
        lotteryPresenter = new LotterySidPresenter();
        gameTypeClassPresenter = new GameTypeClassPresenter();
        userInfoPresenter = new UserInfoPresenter();

        //玩法列表
        gameTypeClassPresenter.attachView(new BaseView<List<GameTypeClass2Entity>>() {
            @Override
            public void onLoading() {
                Log.d("玩法列表", "获取中……");
            }

            @Override
            public void onLoadFailed(int code, String error) {
                Log.d("玩法列表", "获取失败");
            }


            @Override
            public void successed(List<GameTypeClass2Entity> gameTypeClasses) {
                Log.w("thegametype", gameTypeClasses.toString());
                if (gameTypeClasses != null && gameTypeClasses.size() > 0) {
                    oneGameId = gameTypeClasses.get(0).getId();
                    oneGameTitle = gameTypeClasses.get(0).getTitle();
                    playPrice = gameTypeClasses.get(0).getPrice();
                    gameTypePopu.setDatas(gameTypeClasses);
                    mGameTypeText.setText(gameTypeClasses.get(0).getTitle());
                    //betNumberPresenter.getBetNumber(gameTypeClasses.get(0).getId());
                    onSelectClick(oneGameTitle, oneGameId, gameTypeClasses.get(0).get_child(), new ArrayList<>(), playPrice);
                }
            }
        });

        //开奖
        lotteryPresenter.attachView(new BaseView<LotteryEntity>() {
            @Override
            public void onLoading() {
                Log.d("开奖", "获取中……");
            }

            @Override
            public void onLoadFailed(int code, String error) {
                Log.d("开奖", "获取中失败");
            }

            @Override
            public void successed(LotteryEntity lotteryEntity) {
                Log.d("最新开奖 - new", lotteryEntity.getLottery().getPeriods() + "");
                Log.d("最新开奖 - old", issue + "");
                if (lotteryEntity.getLottery().getPeriods() == issue) {
                    refreshLotteryData();
                } else {
                    closeText.setVisibility(View.GONE);
                    butSubmit.setEnabled(true);
                    BetLottery2Activity.this.lotteryEntity = lotteryEntity;
                    initLotteryData(lotteryEntity);
                }
            }
        });
        userInfoPresenter.attachView(new BaseView<UserInfo>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {
                settingPopu.setPicText("");
            }

            @Override
            public void successed(UserInfo userInfo) {
                settingPopu.setPicText(userInfo.getAvailable_predeposit());
            }
        });
        //玩法列表初始化 写在这使其脱离onresume的生命周期 解决手机息屏后重启数据刷新的问题
        gameTypeClassPresenter.getGameTypeClaa(sid);
    }


    //开奖
    private void refreshLotteryData() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lotteryPresenter.getSidLottery(SharedperfencesUtil.getInt(this, "checkSid"));
        }).start();
    }

    //倒计时
    private void refreshTime(long beleft) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(beleft * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long h = millisUntilFinished / 1000 / 60 / 60;
                long m = millisUntilFinished / 1000 / 60 % 60;
                long s = millisUntilFinished / 1000 % 60;
                long day = h % 24;
                String hs = h / 10 + "" + h % 10;
                String ms = m / 10 + "" + m % 10;
                String ss = s / 10 + "" + s % 10;
                if (day != 0) {
                    nextTimeTx.setText(h / 24 + "天" + h % 24 + "时" + ms + "分" + ss + "秒");
                } else {
                    nextTimeTx.setText(hs + ":" + ms + ":" + ss);
                }
                if ((millisUntilFinished / 1000) <= closeTime) {
                    closeText.setVisibility(View.VISIBLE);
                    butSubmit.setEnabled(false);
                }
            }

            @Override
            public void onFinish() {
                nextTimeTx.setText("开奖中");
                closeText.setVisibility(View.VISIBLE);
                butSubmit.setEnabled(false);
                refreshLotteryData();
            }
        }.start();
    }

    @Override
    protected void fetchData() {
        presenter.getSidLottery(SharedperfencesUtil.getInt(this, "checkSid"));
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
    }

    @Override
    public void successed(LotteryEntity lotteryEntity) {
        if (lotteryEntity != null) {
            this.lotteryEntity = lotteryEntity;
            initLotteryData(lotteryEntity);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        userInfoPresenter.getUserInfo(SharedperfencesUtil.getInt(this, "userId"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        staticLottery2OtherEntity = null;
    }


}
