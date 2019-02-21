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

import com.liuheonline.la.entity.GameTypeNewClass3Entity;
import com.liuheonline.la.entity.LotteryEntity;
import com.liuheonline.la.entity.LotteryOtherDetailsEntity;
import com.liuheonline.la.entity.LotteryOtherEntity;
import com.liuheonline.la.entity.UserInfo;
import com.liuheonline.la.event.BetClearAllEvent;
import com.liuheonline.la.event.BetClearEvent;
import com.liuheonline.la.event.BetNew3RefreshOtherEvent;
import com.liuheonline.la.event.MachineEvent;
import com.liuheonline.la.mvp.presenter.GameTypeClassNew3Presenter;
import com.liuheonline.la.mvp.presenter.LotterySidPresenter;
import com.liuheonline.la.mvp.presenter.UserInfoPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.history.HistoryActivity;
import com.liuheonline.la.ui.main.trend.LotteryTrend;
import com.liuheonline.la.ui.user.lotterylog.LotteryLogActivity;
import com.liuheonline.la.ui.widget.WidgetNew3BetRecyclerView;
import com.liuheonline.la.utils.LotteryTypeUtil;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.liuheonline.la.utils.TimeUtil;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.LogUtil;
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
 * @description 时时彩
 */
public class BetLottery3Activity extends BaseMVPActivity<BaseView<LotteryEntity>, LotterySidPresenter, LotteryEntity>
        implements GameTypePopu3.OnSelectClickListener, SensorEventListener, SettingPopu.OnViewClickListener {

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
    private String twoGameTitle = "";

    //old 玩法ID
    private int oldGameId;

    //获取最新开奖
    private LotterySidPresenter lotteryPresenter;

    //玩法列表
    private GameTypeClassNew3Presenter gameTypeClassPresenter;

    //当前期数
    private long issue;

    //倒计时
    private CountDownTimer countDownTimer;

    //设置popu
    private SettingPopu settingPopu;

    //玩法列表popu
    private GameTypePopu3 gameTypePopu;

    //选中号码池显示  只有显示作用
    private List<GameTypeNewClass3Entity> selectBetNumberEntityList = new ArrayList<>();

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

    WidgetNew3BetRecyclerView widgetBetRecyclerView;
    UserInfoPresenter userInfoPresenter;

    List<GameTypeNewClass3Entity> listwan = new ArrayList<>();
    List<GameTypeNewClass3Entity> listqian = new ArrayList<>();
    List<GameTypeNewClass3Entity> listbai = new ArrayList<>();
    List<GameTypeNewClass3Entity> listshi = new ArrayList<>();
    List<GameTypeNewClass3Entity> listge = new ArrayList<>();
    List<List<GameTypeNewClass3Entity>> listall = new ArrayList<>();

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
        setContentView(R.layout.activity_betlottery3);
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
        gameTypePopu = new GameTypePopu3(this, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        gameTypePopu.setOnSelectClickListener(this);

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
    public void onSelectClick(String title, String twoTitle, int id, List<GameTypeNewClass3Entity> list, String playPrices) {
        oneGameTitle = title;
        twoGameTitle = twoTitle;
        mGameTypeText.setText(title);
        oneGameId = id;
        playPrice = playPrices;
        //刷新玩法前先清空之前保存的订单数据
        clearSelectNumber(new BetClearEvent(0));
        if (oldGameId != id) {

            oldGameId = id;
            //刷新号码列表
            if (list.size() > 0) {
                addView(list);
                //baseQuickAdapter.setNewData(list);
            } else {
                GameTypeNewClass3Entity childBean = new GameTypeNewClass3Entity();
                childBean.setId(id);
                List<GameTypeNewClass3Entity> list1 = new ArrayList<>();
                list1.add(childBean);
                //baseQuickAdapter.setNewData(list1);
                addView(list1);
            }
        }
    }

    private void addView(List<GameTypeNewClass3Entity> list) {
        recyclerLinear.removeAllViews();

        for (int i = 0; i < list.size(); i++) {
            widgetBetRecyclerView = new WidgetNew3BetRecyclerView(this);
            ViewGroup.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            widgetBetRecyclerView.setLayoutParams(layoutParams);
            if (list.get(i).getTitle() == null) {
                widgetBetRecyclerView.setTitle(list.get(i).getChild().get(0).getAttr());
            } else {
                widgetBetRecyclerView.setTitle(list.get(i).getTitle());
            }
            //判断该玩法是否需要组id
            if (list.get(i).getTitle() != null && !list.get(i).getTitle().equals("")) {
                widgetBetRecyclerView.setGroup(i + 1);
            }
//            if (Double.parseDouble(list.get(i).getPrice()) > 0) {
////                widgetBetRecyclerView.setprice(list.get(i).getPrice());
////            } else
            widgetBetRecyclerView.setprice(playPrice);
            widgetBetRecyclerView.setOneTitle(oneGameTitle);
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

        Iterator<GameTypeNewClass3Entity> it = selectBetNumberEntityList.iterator();
        while (it.hasNext()) {
            GameTypeNewClass3Entity betNumberEntity = it.next();
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
    public void refreshNumberSelect(BetNew3RefreshOtherEvent betRefreshOther) {
        if (betRefreshOther != null) {
            if (betRefreshOther.getBetNumberEntity() == null && betRefreshOther.getBetNumberEntityList() == null) {

            } else {
                if (betRefreshOther.isCheck()) {
                    if (betRefreshOther.getBetNumberEntity() == null) {
                        selectBetNumberEntityList.addAll(betRefreshOther.getBetNumberEntityList());
                    } else {
                        selectBetNumberEntityList.add(betRefreshOther.getBetNumberEntity());
                    }
                } else {
                    for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
                        GameTypeNewClass3Entity numberEntity = selectBetNumberEntityList.get(i);
                        if (numberEntity.getId() == betRefreshOther.getBetNumberEntity().getId()) {
                            selectBetNumberEntityList.remove(i);
                        }
                    }
                }
            }
        }

        lotteryOtherDetailsEntityList.clear();
        Log.w("onegametitle", oneGameTitle);
        switch (oneGameTitle) {

            case "五星":
            case "五星直选":
            case "五星通选":
                listwan.clear();
                listqian.clear();
                listbai.clear();
                listshi.clear();
                listge.clear();
                lotteryOtherDetailsEntityList.clear();
                if (selectBetNumberEntityList.size() >= 5) {
                    for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
                        GameTypeNewClass3Entity numberEntity = selectBetNumberEntityList.get(i);
                        //numberBuffer.append(numberEntity.getCode()).append(" ");
                        switch (numberEntity.getGroup()) {
                            case 1:
                                listwan.add(numberEntity);
                                break;
                            case 2:
                                listqian.add(numberEntity);
                                break;
                            case 3:
                                listbai.add(numberEntity);
                                break;
                            case 4:
                                listshi.add(numberEntity);
                                break;
                            case 5:
                                listge.add(numberEntity);
                                break;
                        }
                    }
                    if (listwan.size() > 0 && listqian.size() > 0 && listbai.size() > 0 && listshi.size() > 0 && listge.size() > 0) {
                        for (int w = 0; w < listwan.size(); w++) {
                            for (int q = 0; q < listqian.size(); q++) {
                                for (int b = 0; b < listbai.size(); b++) {
                                    for (int s = 0; s < listshi.size(); s++) {
                                        for (int g = 0; g < listge.size(); g++) {
                                            LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                            lotteryOtherDetailsEntity.setLottery_price(money);
                                            if (oneGameTitle.equals("五星直选")) {
                                                lotteryOtherDetailsEntity.setLottery_id(listwan.get(w).getId());
                                            } else {
                                                lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                            }
                                            lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                            lotteryOtherDetailsEntity.setLottery_name(listwan.get(w).getCode() + "|" +
                                                    listqian.get(q).getCode() + "|" + listbai.get(b).getCode()
                                                    + "|" + listshi.get(s).getCode() + "|" + listge.get(g).getCode());
                                            Log.w("thegroup", listwan.get(w).getCode() + "|" +
                                                    listqian.get(q).getCode() + "|" + listbai.get(b).getCode()
                                                    + "|" + listshi.get(s).getCode() + "|" + listge.get(g).getCode());
                                            lotteryOtherDetailsEntity.setOdds(Double.parseDouble(listge.get(g).getPrice()));
                                            lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                            lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case "三星直选":
            case "后三大小单双":
            case "前三大小单双":
                listwan.clear();
                listqian.clear();
                listbai.clear();
                listshi.clear();
                listge.clear();
                lotteryOtherDetailsEntityList.clear();
                if (selectBetNumberEntityList.size() >= 3) {
                    for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
                        GameTypeNewClass3Entity numberEntity = selectBetNumberEntityList.get(i);
                        //numberBuffer.append(numberEntity.getCode()).append(" ");
                        switch (numberEntity.getGroup()) {
                            case 1:
                                listbai.add(numberEntity);
                                break;
                            case 2:
                                listshi.add(numberEntity);
                                break;
                            case 3:
                                listge.add(numberEntity);
                                break;
                        }
                    }
                    if (listbai.size() > 0 && listshi.size() > 0 && listge.size() > 0) {
                        for (int b = 0; b < listbai.size(); b++) {
                            Log.w("listbai", listbai.size() + "");
                            for (int s = 0; s < listshi.size(); s++) {
                                Log.w("listshi", listshi.size() + "");
                                for (int g = 0; g < listge.size(); g++) {
                                    Log.w("listge", listge.size() + "");
                                    LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                    lotteryOtherDetailsEntity.setLottery_price(money);
                                    if (oneGameTitle.equals("三星直选")) {
                                        lotteryOtherDetailsEntity.setLottery_id(listbai.get(b).getId());
                                    } else {
                                        lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                    }
                                    lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                    lotteryOtherDetailsEntity.setLottery_name(listbai.get(b).getCode()
                                            + "|" + listshi.get(s).getCode() + "|" + listge.get(g).getCode());
                                    Log.w("thegroup", listbai.get(b).getCode()
                                            + "|" + listshi.get(s).getCode() + "|" + listge.get(g).getCode());
                                    lotteryOtherDetailsEntity.setOdds(Double.parseDouble(listge.get(g).getPrice()));
                                    lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                    lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                                }
                            }
                        }
                    }
                }
                break;
            case "三星组三":
                if (selectBetNumberEntityList.size() >= 2) {
                    lotteryOtherDetailsEntityList.clear();
                    for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
                        for (int j = i + 1; j < selectBetNumberEntityList.size(); j++) {
                            LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                            lotteryOtherDetailsEntity.setLottery_price(money);
                            lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                            lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                            lotteryOtherDetailsEntity.setLottery_name(selectBetNumberEntityList.get(i).getCode()
                                    + "|" + selectBetNumberEntityList.get(i).getCode()
                                    + "|" + selectBetNumberEntityList.get(j).getCode());
                            Log.w("thegroup", selectBetNumberEntityList.get(i).getCode()
                                    + "|" + selectBetNumberEntityList.get(i).getCode()
                                    + "|" + selectBetNumberEntityList.get(j).getCode());
                            lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(i).getPrice()));
                            lotteryOtherDetailsEntity.setPlayprice(playPrice);
                            lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);

                            LotteryOtherDetailsEntity lotteryOtherDetailsEntity1 = new LotteryOtherDetailsEntity();
                            lotteryOtherDetailsEntity1.setLottery_price(money);
                            lotteryOtherDetailsEntity1.setLottery_id(oneGameId);
                            lotteryOtherDetailsEntity1.setLottery_child_name(oneGameTitle);
                            lotteryOtherDetailsEntity1.setLottery_name(selectBetNumberEntityList.get(i).getCode()
                                    + "|" + selectBetNumberEntityList.get(j).getCode()
                                    + "|" + selectBetNumberEntityList.get(j).getCode());
                            Log.w("thegroup222", selectBetNumberEntityList.get(i).getCode()
                                    + "|" + selectBetNumberEntityList.get(j).getCode()
                                    + "|" + selectBetNumberEntityList.get(j).getCode());
                            lotteryOtherDetailsEntity1.setOdds(Double.parseDouble(selectBetNumberEntityList.get(i).getPrice()));
                            lotteryOtherDetailsEntity1.setPlayprice(playPrice);
                            lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity1);
                        }
                    }

                }

                break;
            case "三星组六":
            case "三星":
            case "五星三码":
                if (selectBetNumberEntityList.size() >= 3) {
                    lotteryOtherDetailsEntityList.clear();
                    for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
                        for (int j = i + 1; j < selectBetNumberEntityList.size(); j++) {
                            for (int k = j + 1; k < selectBetNumberEntityList.size(); k++) {
                                LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                lotteryOtherDetailsEntity.setLottery_price(money);
                                lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                lotteryOtherDetailsEntity.setLottery_name(selectBetNumberEntityList.get(i).getCode()
                                        + "|" + selectBetNumberEntityList.get(j).getCode()
                                        + "|" + selectBetNumberEntityList.get(k).getCode());
                                Log.w("thegroup", selectBetNumberEntityList.get(i).getCode()
                                        + "|" + selectBetNumberEntityList.get(j).getCode()
                                        + "|" + selectBetNumberEntityList.get(k).getCode());
                                lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(i).getPrice()));
                                lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);

                            }
                        }
                    }

                }
                break;
            case "二星直选":
            case "后二大小单双":
            case "前二大小单双":
                listwan.clear();
                listqian.clear();
                listbai.clear();
                listshi.clear();
                listge.clear();
                lotteryOtherDetailsEntityList.clear();
                if (selectBetNumberEntityList.size() >= 2) {
                    for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
                        GameTypeNewClass3Entity numberEntity = selectBetNumberEntityList.get(i);
                        //numberBuffer.append(numberEntity.getCode()).append(" ");
                        switch (numberEntity.getGroup()) {
                            case 1:
                                listshi.add(numberEntity);
                                break;
                            case 2:
                                listge.add(numberEntity);
                                break;
                        }
                    }
                    if (listshi.size() > 0 && listge.size() > 0) {
                        Log.w("listbai", listbai.size() + "");
                        for (int s = 0; s < listshi.size(); s++) {
                            Log.w("listshi", listshi.size() + "");
                            for (int g = 0; g < listge.size(); g++) {
                                Log.w("listge", listge.size() + "");
                                LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                lotteryOtherDetailsEntity.setLottery_price(money);
                                if (oneGameTitle.equals("二星直选")) {
                                    lotteryOtherDetailsEntity.setLottery_id(listshi.get(s).getId());
                                } else {
                                    lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                }
                                lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                lotteryOtherDetailsEntity.setLottery_name(listshi.get(s).getCode() + "|" + listge.get(g).getCode());
                                Log.w("thegroup", listshi.get(s).getCode() + "|" + listge.get(g).getCode());
                                lotteryOtherDetailsEntity.setOdds(Double.parseDouble(listge.get(g).getPrice()));
                                lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                            }
                        }
                    }
                }
                break;

            case "万位":
            case "千位":
            case "百位":
            case "十位":
            case "个位":
            case "前三一码":
            case "后三一码":
            case "前四一码":
            case "后四一码":
            case "五星一码":
                listwan.clear();
                listqian.clear();
                listbai.clear();
                listshi.clear();
                listge.clear();
                lotteryOtherDetailsEntityList.clear();
                if (selectBetNumberEntityList.size() >= 1) {
                    for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
                        GameTypeNewClass3Entity numberEntity = selectBetNumberEntityList.get(i);
                        //numberBuffer.append(numberEntity.getCode()).append(" ");
                        listge.add(numberEntity);
                    }
                    if (listge.size() > 0) {
                        for (int g = 0; g < listge.size(); g++) {
                            Log.w("listge", listge.size() + "");
                            LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                            lotteryOtherDetailsEntity.setLottery_price(money);
                            lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                            lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                            lotteryOtherDetailsEntity.setLottery_name(listge.get(g).getCode());
                            Log.w("thegroup", listge.get(g).getCode());
                            lotteryOtherDetailsEntity.setOdds(Double.parseDouble(listge.get(g).getPrice()));
                            lotteryOtherDetailsEntity.setPlayprice(playPrice);
                            lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                        }
                    }
                }
                break;
            case "二星组选":
            case "前三二码":
            case "后三二码":
            case "前四二码":
            case "后四二码":
            case "五星二码":
                if (selectBetNumberEntityList.size() >= 2) {
                    lotteryOtherDetailsEntityList.clear();
                    for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
                        for (int j = i + 1; j < selectBetNumberEntityList.size(); j++) {
                            LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                            lotteryOtherDetailsEntity.setLottery_price(money);
                            lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                            lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                            lotteryOtherDetailsEntity.setLottery_name(selectBetNumberEntityList.get(i).getCode()
                                    + "|" + selectBetNumberEntityList.get(j).getCode());
                            Log.w("thegroup", selectBetNumberEntityList.get(i).getCode()
                                    + "|" + selectBetNumberEntityList.get(j).getCode());
                            lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(i).getPrice()));
                            lotteryOtherDetailsEntity.setPlayprice(playPrice);
                            lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                        }
                    }

                }
                break;

            case "直选复式":
            case "任选三":
            case "任选二":
                listwan.clear();
                listqian.clear();
                listbai.clear();
                listshi.clear();
                listge.clear();
                lotteryOtherDetailsEntityList.clear();
                listall.clear();
                if (SharedperfencesUtil.getString(this, "onegametitle").equals("任选二")) {
                    if (selectBetNumberEntityList.size() >= 2) {
                        for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
                            GameTypeNewClass3Entity numberEntity = selectBetNumberEntityList.get(i);
                            switch (numberEntity.getGroup()) {
                                case 1:
                                    listwan.add(numberEntity);
                                    break;
                                case 2:
                                    listqian.add(numberEntity);
                                    break;
                                case 3:
                                    listbai.add(numberEntity);
                                    break;
                                case 4:
                                    listshi.add(numberEntity);
                                    break;
                                case 5:
                                    listge.add(numberEntity);
                                    break;
                            }
                        }
                        Log.w("直选复式 wan", listwan.size() + "");
                        Log.w("直选复式 qian", listqian.size() + "");
                        Log.w("直选复式 bai", listbai.size() + "");
                        Log.w("直选复式 shi", listshi.size() + "");
                        Log.w("直选复式 ge", listge.size() + "");
                        if (listwan.size() > 0)
                            listall.add(listwan);
                        if (listqian.size() > 0)
                            listall.add(listqian);
                        if (listbai.size() > 0)
                            listall.add(listbai);
                        if (listshi.size() > 0)
                            listall.add(listshi);
                        if (listge.size() > 0)
                            listall.add(listge);
                        for (int i = 0; i < listall.size(); i++) {
                            if (listall.get(i).size() > 0) {
                                for (int j = i + 1; j < listall.size(); j++) {
                                    if (listall.get(j).size() > 0) {
                                        for (int a = 0; a < listall.get(i).size(); a++) {
                                            for (int b = 0; b < listall.get(j).size(); b++) {
                                                LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                                lotteryOtherDetailsEntity.setLottery_price(money);
                                                lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                                lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                                lotteryOtherDetailsEntity.setLottery_name(listall.get(i).get(a).getAttr()
                                                        + "," + listall.get(j).get(b).getAttr() + "|" + listall.get(i).get(a).getCode()
                                                        + "," + listall.get(j).get(b).getCode());
                                                Log.w("直选复式 2", listall.get(i).get(a).getAttr()
                                                        + "," + listall.get(j).get(b).getAttr() + "|" + listall.get(i).get(a).getCode()
                                                        + "," + listall.get(j).get(b).getCode());
                                                lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(i).getPrice()));
                                                lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                                lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (SharedperfencesUtil.getString(this, "onegametitle").equals("任选三")) {
                    if (selectBetNumberEntityList.size() >= 3) {
                        for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
                            GameTypeNewClass3Entity numberEntity = selectBetNumberEntityList.get(i);
                            switch (numberEntity.getGroup()) {
                                case 1:
                                    listwan.add(numberEntity);
                                    break;
                                case 2:
                                    listqian.add(numberEntity);
                                    break;
                                case 3:
                                    listbai.add(numberEntity);
                                    break;
                                case 4:
                                    listshi.add(numberEntity);
                                    break;
                                case 5:
                                    listge.add(numberEntity);
                                    break;
                            }
                        }
                        Log.w("直选复式 wan", listwan.size() + "");
                        Log.w("直选复式 qian", listqian.size() + "");
                        Log.w("直选复式 bai", listbai.size() + "");
                        Log.w("直选复式 shi", listshi.size() + "");
                        Log.w("直选复式 ge", listge.size() + "");
                        if (listwan.size() > 0)
                            listall.add(listwan);
                        if (listqian.size() > 0)
                            listall.add(listqian);
                        if (listbai.size() > 0)
                            listall.add(listbai);
                        if (listshi.size() > 0)
                            listall.add(listshi);
                        if (listge.size() > 0)
                            listall.add(listge);
                        for (int i = 0; i < listall.size(); i++) {
                            if (listall.get(i).size() > 0) {
                                for (int j = i + 1; j < listall.size(); j++) {
                                    if (listall.get(j).size() > 0) {
                                        for (int k = j + 1; k < listall.size(); k++) {
                                            for (int a = 0; a < listall.get(i).size(); a++) {
                                                for (int b = 0; b < listall.get(j).size(); b++) {
                                                    for (int c = 0; c < listall.get(k).size(); c++) {
                                                        LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                                        lotteryOtherDetailsEntity.setLottery_price(money);
                                                        lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                                        lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                                        lotteryOtherDetailsEntity.setLottery_name(listall.get(i).get(a).getAttr()
                                                                + "," + listall.get(j).get(b).getAttr() + "," + listall.get(k).get(c).getAttr() + "|" + listall.get(i).get(a).getCode()
                                                                + "," + listall.get(j).get(b).getCode() + "," + listall.get(k).get(c).getCode());
                                                        Log.w("直选复式 3", listall.get(i).get(a).getAttr()
                                                                + "," + listall.get(j).get(b).getAttr() + "," + listall.get(k).get(c).getAttr() + "|" + listall.get(i).get(a).getCode()
                                                                + "," + listall.get(j).get(b).getCode() + "," + listall.get(k).get(c).getCode());
                                                        lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(i).getPrice()));
                                                        lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                                        lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (SharedperfencesUtil.getString(this, "onegametitle").equals("任选四")) {
                    if (selectBetNumberEntityList.size() >= 4) {
                        listwan.clear();
                        listqian.clear();
                        listge.clear();
                        listbai.clear();
                        listshi.clear();
                        listall.clear();
                        for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
                            GameTypeNewClass3Entity numberEntity = selectBetNumberEntityList.get(i);
                            switch (numberEntity.getGroup()) {
                                case 1:
                                    listwan.add(numberEntity);
                                    break;
                                case 2:
                                    listqian.add(numberEntity);
                                    break;
                                case 3:
                                    listbai.add(numberEntity);
                                    break;
                                case 4:
                                    listshi.add(numberEntity);
                                    break;
                                case 5:
                                    listge.add(numberEntity);
                                    break;
                            }
                        }
                        Log.w("直选复式 wan", listwan.size() + "");
                        Log.w("直选复式 qian", listqian.size() + "");
                        Log.w("直选复式 bai", listbai.size() + "");
                        Log.w("直选复式 shi", listshi.size() + "");
                        Log.w("直选复式 ge", listge.size() + "");
                        if (listwan.size() > 0)
                            listall.add(listwan);
                        if (listqian.size() > 0)
                            listall.add(listqian);
                        if (listbai.size() > 0)
                            listall.add(listbai);
                        if (listshi.size() > 0)
                            listall.add(listshi);
                        if (listge.size() > 0)
                            listall.add(listge);
                        for (int i = 0; i < listall.size(); i++) {
                            if (listall.get(i).size() > 0) {
                                for (int j = i + 1; j < listall.size(); j++) {
                                    if (listall.get(j).size() > 0) {
                                        for (int k = j + 1; k < listall.size(); k++) {
                                            for (int l = k + 1; l < listall.size(); l++) {
                                                for (int a = 0; a < listall.get(i).size(); a++) {
                                                    for (int b = 0; b < listall.get(j).size(); b++) {
                                                        for (int c = 0; c < listall.get(k).size(); c++) {
                                                            for (int d = 0; d < listall.get(l).size(); d++) {
                                                                LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                                                lotteryOtherDetailsEntity.setLottery_price(money);
                                                                lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                                                lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                                                lotteryOtherDetailsEntity.setLottery_name(listall.get(i).get(a).getAttr()
                                                                        + "," + listall.get(j).get(b).getAttr() + "," + listall.get(k).get(c).getAttr() + "," + listall.get(l).get(d).getCode() + "|"
                                                                        + listall.get(i).get(a).getCode()
                                                                        + "," + listall.get(j).get(b).getCode() + "," + listall.get(k).get(c).getCode() + "," + listall.get(l).get(d).getCode());
                                                                Log.w("直选复式 4", listall.get(i).get(a).getAttr()
                                                                        + "," + listall.get(j).get(b).getAttr() + "," + listall.get(k).get(c).getAttr() + "," + listall.get(l).get(d).getCode() + "|"
                                                                        + listall.get(i).get(a).getCode()
                                                                        + "," + listall.get(j).get(b).getCode() + "," + listall.get(k).get(c).getCode() + "," + listall.get(l).get(d).getCode());
                                                                lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(i).getPrice()));
                                                                lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                                                lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case "组六复式":
                List<String> namelist9 = widgetBetRecyclerView.getNameList();
                if (selectBetNumberEntityList.size() >= 3) {
                    lotteryOtherDetailsEntityList.clear();
                    for (int i = 0; i < namelist9.size(); i++) {
                        for (int j = i + 1; j < namelist9.size(); j++) {
                            for (int k = j + 1; k < namelist9.size(); k++) {
                                for (int l = 0; l < selectBetNumberEntityList.size(); l++) {
                                    for (int m = l + 1; m < selectBetNumberEntityList.size(); m++) {
                                        for (int n = m + 1; n < selectBetNumberEntityList.size(); n++) {
                                            LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                            lotteryOtherDetailsEntity.setLottery_price(money);
                                            lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                            lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                            lotteryOtherDetailsEntity.setLottery_name(namelist9.get(i) + "," + namelist9.get(j) + "," + namelist9.get(k) + "|" + selectBetNumberEntityList.get(l).getCode()
                                                    + "," + selectBetNumberEntityList.get(m).getCode() + "," + selectBetNumberEntityList.get(n).getCode());
                                            lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                            lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(k).getPrice()));
                                            lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;

            case "组选24":
                List<String> namelist8 = widgetBetRecyclerView.getNameList();
                lotteryOtherDetailsEntityList.clear();
                if (selectBetNumberEntityList.size() >= 4) {
                    for (int i = 0; i < namelist8.size(); i++) {
                        for (int j = i + 1; j < namelist8.size(); j++) {
                            for (int k = j + 1; k < namelist8.size(); k++) {
                                for (int l = k + 1; l < namelist8.size(); l++) {
                                    for (int m = 0; m < selectBetNumberEntityList.size(); m++) {
                                        for (int n = m + 1; n < selectBetNumberEntityList.size(); n++) {
                                            for (int o = n + 1; o < selectBetNumberEntityList.size(); o++) {
                                                for (int p = o + 1; p < selectBetNumberEntityList.size(); p++) {
                                                    LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                                    lotteryOtherDetailsEntity.setLottery_price(money);
                                                    lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                                    lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                                    lotteryOtherDetailsEntity.setLottery_name(namelist8.get(i) + "," + namelist8.get(j) + "," + namelist8.get(k) + "," + namelist8.get(l) + "|" + selectBetNumberEntityList.get(m).getCode()
                                                            + "," + selectBetNumberEntityList.get(n).getCode() + "," + selectBetNumberEntityList.get(o).getCode() + "," + selectBetNumberEntityList.get(p).getCode());
                                                    lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(k).getPrice()));
                                                    lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                                    lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;

            case "直选和值":
                List<String> namelist = widgetBetRecyclerView.getNameList();
                if (SharedperfencesUtil.getString(this, "onegametitle").equals("任选二")) {
                    if (namelist.size() >= 2) {
                        lotteryOtherDetailsEntityList.clear();
                        for (int i = 0; i < namelist.size(); i++) {
                            for (int j = i + 1; j < namelist.size(); j++) {
                                for (int k = 0; k < selectBetNumberEntityList.size(); k++) {
                                    for (int a = 0; a < 10; a++) {
                                        for (int b = 0; b < 10; b++) {
                                            if (a + b == Integer.parseInt(selectBetNumberEntityList.get(k).getCode())) {
                                                LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                                lotteryOtherDetailsEntity.setLottery_price(money);
                                                lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                                lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                                lotteryOtherDetailsEntity.setLottery_name(namelist.get(i) + "," + namelist.get(j) + "|" + a + "," + b);
                                                Log.w("直选复式 thegroup", namelist.get(i) + "," + namelist.get(j) + "|" + a + "," + b + "namelist= " + namelist.toString());
                                                lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(k).getPrice()));
                                                lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                                lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (SharedperfencesUtil.getString(this, "onegametitle").equals("任选三")) {
                    if (namelist.size() >= 3) {
                        lotteryOtherDetailsEntityList.clear();
                        for (int i = 0; i < namelist.size(); i++) {
                            for (int j = i + 1; j < namelist.size(); j++) {
                                for (int k = j + 1; k < namelist.size(); k++) {
                                    for (int l = 0; l < selectBetNumberEntityList.size(); l++) {
                                        for (int a = 0; a < 10; a++) {
                                            for (int b = 0; b < 10; b++) {
                                                for (int c = 0; c < 10; c++) {
                                                    if (a + b + c == Integer.parseInt(selectBetNumberEntityList.get(l).getCode())) {
                                                        LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                                        lotteryOtherDetailsEntity.setLottery_price(money);
                                                        lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                                        lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                                        lotteryOtherDetailsEntity.setLottery_name(namelist.get(i) + "," + namelist.get(j) + "," + namelist.get(k) + "|" + a + "," + b + "," + c);
                                                        Log.w("直选复式 thegroup", namelist.get(i) + "," + namelist.get(j) + "|" + a + "," + b + "namelist= " + namelist.toString());
                                                        lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(k).getPrice()));
                                                        lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                                        lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case "组选12":
                listall.clear();
                listwan.clear();
                listqian.clear();
                List<String> namelist5 = widgetBetRecyclerView.getNameList();
                if (SharedperfencesUtil.getString(this, "onegametitle").equals("任选四")) {
                    if (selectBetNumberEntityList.size() >= 3) {
                        for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
                            GameTypeNewClass3Entity numberEntity = selectBetNumberEntityList.get(i);
                            switch (numberEntity.getGroup()) {
                                case 1:
                                    listwan.add(numberEntity);
                                    break;
                                case 2:
                                    listqian.add(numberEntity);
                                    break;
                            }
                        }
                        Log.w("直选复式 wan", listwan.size() + "");
                        Log.w("直选复式 qian", listqian.size() + "");
                        if (listwan.size() > 0)
                            listall.add(listwan);
                        if (listqian.size() > 0)
                            listall.add(listqian);
                        for (int i = 0; i < namelist5.size(); i++) {
                            for (int j = i + 1; j < namelist5.size(); j++) {
                                for (int k = j + 1; k < namelist5.size(); k++) {
                                    for (int l = k + 1; l < namelist5.size(); l++) {
                                        for (int a = 0; a < listwan.size(); a++) {
                                            for (int b = 0; b < listqian.size(); b++) {
                                                for (int c = b + 1; c < listqian.size(); c++) {
                                                    if (Integer.parseInt(listwan.get(a).getCode()) != Integer.parseInt(listqian.get(b).getCode()) &&
                                                            Integer.parseInt(listwan.get(a).getCode()) != Integer.parseInt(listqian.get(c).getCode())) {
                                                        LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                                        lotteryOtherDetailsEntity.setLottery_price(money);
                                                        lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                                        lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                                        lotteryOtherDetailsEntity.setLottery_name(namelist5.get(i) + "," + namelist5.get(j) + "," + namelist5.get(k) + "," + namelist5.get(l) +
                                                                "|" + listwan.get(a).getCode() + "," + listwan.get(a).getCode() + "," + listwan.get(a).getCode() + "," + listqian.get(c).getCode());
                                                        lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(i).getPrice()));
                                                        lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                                        lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case "组选4":
            case "任选四":
                listqian.clear();
                listwan.clear();
                listall.clear();
                List<String> namelist6 = widgetBetRecyclerView.getNameList();
                if (SharedperfencesUtil.getString(this, "onegametitle").equals("任选四")) {
                    if (selectBetNumberEntityList.size() >= 2) {
                        for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
                            GameTypeNewClass3Entity numberEntity = selectBetNumberEntityList.get(i);
                            switch (numberEntity.getGroup()) {
                                case 1:
                                    listwan.add(numberEntity);
                                    break;
                                case 2:
                                    listqian.add(numberEntity);
                                    break;
                            }
                        }
                        Log.w("直选复式 wan", listwan.size() + "");
                        Log.w("直选复式 qian", listqian.size() + "");
                        if (listwan.size() > 0)
                            listall.add(listwan);
                        if (listqian.size() > 0)
                            listall.add(listqian);
                        for (int i = 0; i < namelist6.size(); i++) {
                            for (int j = i + 1; j < namelist6.size(); j++) {
                                for (int k = j + 1; k < namelist6.size(); k++) {
                                    for (int l = k + 1; l < namelist6.size(); l++) {
                                        for (int a = 0; a < listwan.size(); a++) {
                                            for (int b = 0; b < listqian.size(); b++) {
                                                if (Integer.parseInt(listwan.get(a).getCode()) != Integer.parseInt(listqian.get(b).getCode())) {
                                                    LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                                    lotteryOtherDetailsEntity.setLottery_price(money);
                                                    lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                                    lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                                    lotteryOtherDetailsEntity.setLottery_name(namelist6.get(i) + "," + namelist6.get(j) + "," + namelist6.get(k) + "," + namelist6.get(l) +
                                                            "|" + listwan.get(a).getCode() + "," + listwan.get(a).getCode() + "," + listwan.get(a).getCode() + "," + listqian.get(b).getCode());
                                                    lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(i).getPrice()));
                                                    lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                                    lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case "组选6":
                List<String> namelist7 = widgetBetRecyclerView.getNameList();
                if (SharedperfencesUtil.getString(this, "onegametitle").equals("任选四")) {
                    if (selectBetNumberEntityList.size() >= 2) {
                        lotteryOtherDetailsEntityList.clear();
                        for (int i = 0; i < namelist7.size(); i++) {
                            for (int j = i + 1; j < namelist7.size(); j++) {
                                for (int k = j + 1; k < namelist7.size(); k++) {
                                    for (int l = k + 1; l < namelist7.size(); l++) {
                                        for (int a = 0; a < selectBetNumberEntityList.size(); a++) {
                                            for (int b = a + 1; b < selectBetNumberEntityList.size(); b++) {
                                                LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                                lotteryOtherDetailsEntity.setLottery_price(money);
                                                lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                                lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                                lotteryOtherDetailsEntity.setLottery_name(namelist7.get(i) + "," + namelist7.get(j) + "," + namelist7.get(k) + "," + namelist7.get(l) +
                                                        "|" + selectBetNumberEntityList.get(a).getCode() + "," + selectBetNumberEntityList.get(a).getCode() + "," + selectBetNumberEntityList.get(b).getCode() + "," + selectBetNumberEntityList.get(b).getCode());
                                                lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(i).getPrice()));
                                                lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                                lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case "组选复式":
                List<String> namelist1 = widgetBetRecyclerView.getNameList();
                if (namelist1.size() >= 2) {
                    lotteryOtherDetailsEntityList.clear();
                    for (int i = 0; i < namelist1.size(); i++) {
                        for (int j = i + 1; j < namelist1.size(); j++) {
                            for (int k = 0; k < selectBetNumberEntityList.size(); k++) {
                                for (int l = k + 1; l < selectBetNumberEntityList.size(); l++) {
                                    LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                    lotteryOtherDetailsEntity.setLottery_price(money);
                                    lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                    lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                    lotteryOtherDetailsEntity.setLottery_name(namelist1.get(i) + "," + namelist1.get(j) + "|" + selectBetNumberEntityList.get(k).getCode() + "," + selectBetNumberEntityList.get(l).getCode());
                                    Log.w("直选复式 thegroup", (namelist1.get(i) + "," + namelist1.get(j) + "|" + selectBetNumberEntityList.get(k).getCode() + "," + selectBetNumberEntityList.get(l).getCode()));
                                    lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(k).getPrice()));
                                    lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                    lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                                }
                            }
                        }
                    }
                }
                break;
            case "组三复式":
                List<String> namelist3 = widgetBetRecyclerView.getNameList();
                if (namelist3.size() >= 3) {
                    lotteryOtherDetailsEntityList.clear();
                    for (int i = 0; i < namelist3.size(); i++) {
                        for (int j = i + 1; j < namelist3.size(); j++) {
                            for (int k = j + 1; k < selectBetNumberEntityList.size(); k++) {
                                for (int m = 0; m < selectBetNumberEntityList.size(); m++) {
                                    for (int l = m + 1; l < selectBetNumberEntityList.size(); l++) {
                                        LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                        lotteryOtherDetailsEntity.setLottery_price(money);
                                        lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                        lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                        lotteryOtherDetailsEntity.setLottery_name(namelist3.get(i) + "," + namelist3.get(j) + "," + namelist3.get(k) + "|" + selectBetNumberEntityList.get(m).getCode() + "," + selectBetNumberEntityList.get(m).getCode() + "," + selectBetNumberEntityList.get(l).getCode());
                                        lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(k).getPrice()));
                                        lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                        lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);

                                        LotteryOtherDetailsEntity lotteryOtherDetailsEntity1 = new LotteryOtherDetailsEntity();
                                        lotteryOtherDetailsEntity1.setLottery_price(money);
                                        lotteryOtherDetailsEntity1.setLottery_id(oneGameId);
                                        lotteryOtherDetailsEntity1.setLottery_child_name(oneGameTitle);
                                        lotteryOtherDetailsEntity1.setLottery_name(namelist3.get(i) + "," + namelist3.get(j) + "," + namelist3.get(k) + "|" + selectBetNumberEntityList.get(m).getCode() + "," + selectBetNumberEntityList.get(l).getCode() + "," + selectBetNumberEntityList.get(l).getCode());
                                        lotteryOtherDetailsEntity1.setOdds(Double.parseDouble(selectBetNumberEntityList.get(k).getPrice()));
                                        lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                        lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity1);
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case "组选和值":
                List<String> namelist2 = widgetBetRecyclerView.getNameList();
                if (SharedperfencesUtil.getString(this, "onegametitle").equals("任选二")) {
                    if (namelist2.size() >= 2) {
                        lotteryOtherDetailsEntityList.clear();
                        for (int i = 0; i < namelist2.size(); i++) {
                            for (int j = i + 1; j < namelist2.size(); j++) {
                                for (int k = 0; k < selectBetNumberEntityList.size(); k++) {
                                    for (int a = 0; a < 10; a++) {
                                        for (int b = a; b < 10; b++) {
                                            if (a + b == Integer.parseInt(selectBetNumberEntityList.get(k).getCode())) {
                                                LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                                lotteryOtherDetailsEntity.setLottery_price(money);
                                                lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                                lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                                lotteryOtherDetailsEntity.setLottery_name(namelist2.get(i) + "," + namelist2.get(j) + "|" + a + "," + b);
                                                Log.w("组选和值1", namelist2.get(i) + "," + namelist2.get(j) + "|" + a + "," + b);
                                                lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(k).getPrice()));
                                                lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                                lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (SharedperfencesUtil.getString(this, "onegametitle").equals("任选三")) {
                    if (namelist2.size() >= 3) {
                        lotteryOtherDetailsEntityList.clear();
                        for (int i = 0; i < namelist2.size(); i++) {
                            for (int j = i + 1; j < namelist2.size(); j++) {
                                for (int k = j + 1; k < namelist2.size(); k++) {
                                    for (int l = 0; l < selectBetNumberEntityList.size(); l++) {
                                        for (int a = 0; a < 10; a++) {
                                            for (int b = a; b < 10; b++) {
                                                for (int c = b; c < 10; c++) {
                                                    if (a + b + c == Integer.parseInt(selectBetNumberEntityList.get(l).getCode())) {
                                                        LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
                                                        lotteryOtherDetailsEntity.setLottery_price(money);
                                                        lotteryOtherDetailsEntity.setLottery_id(oneGameId);
                                                        lotteryOtherDetailsEntity.setLottery_child_name(oneGameTitle);
                                                        lotteryOtherDetailsEntity.setLottery_name(namelist2.get(i) + "," + namelist2.get(j) + "," + namelist2.get(k) + "|" + a + "," + b + "," + c);
                                                        Log.w("直选复式 thegroup", namelist2.get(i) + "," + namelist2.get(j) + "|" + a + "," + b + "namelist= " + namelist2.toString());
                                                        lotteryOtherDetailsEntity.setOdds(Double.parseDouble(selectBetNumberEntityList.get(k).getPrice()));
                                                        lotteryOtherDetailsEntity.setPlayprice(playPrice);
                                                        lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            default:
                //计算注数  常规玩法
                for (GameTypeNewClass3Entity betNumberEntity1 : selectBetNumberEntityList) {
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
                    lotteryOtherDetailsEntity.setPlayprice(playPrice);
                    lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                }
                break;

        }


       /* //刷新底部选中池
        numberBuffer.setLength(0);
        for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
            GameTypeNewClass3Entity numberEntity = selectBetNumberEntityList.get(i);
            numberBuffer.append(numberEntity.getCode()).append(" ");
        }
        if (numberBuffer.toString().trim().equals("")) {
            selectNumberText.setVisibility(View.GONE);
        } else {
            selectNumberText.setVisibility(View.VISIBLE);
        }
        selectNumberText.setText(numberBuffer);*/


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


    private String getSelect(List<GameTypeNewClass3Entity> selectBetNumberEntityList) {
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
        String a11 = " |";

        for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
            GameTypeNewClass3Entity numberEntity = selectBetNumberEntityList.get(i);
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
                default:
                    a11 += " " + numberEntity.getCode();
                    break;
            }
        }
        String content = (a1.length() > 2 ? a1 : "") + (a2.length() > 2 ? a2 : "") + (a3.length() > 2 ? a3 : "") + (a4.length() > 2 ? a4 : "") +
                (a5.length() > 2 ? a5 : "") + (a6.length() > 2 ? a6 : "") + (a7.length() > 2 ? a7 : "") + (a8.length() > 2 ? a8 : "") +
                (a9.length() > 2 ? a9 : "") + (a10.length() > 2 ? a10 : "") + (a11.length() > 2 ? a11 : "");
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
        gameTypeClassPresenter = new GameTypeClassNew3Presenter();
        userInfoPresenter = new UserInfoPresenter();
        //玩法列表
        gameTypeClassPresenter.attachView(new BaseView<List<GameTypeNewClass3Entity>>() {
            @Override
            public void onLoading() {
                Log.d("玩法列表", "获取中……");
            }

            @Override
            public void onLoadFailed(int code, String error) {
                Log.d("玩法列表", "获取失败");
            }

            @Override
            public void successed(List<GameTypeNewClass3Entity> gameTypeClasses) {
                Log.w("thegametype3", gameTypeClasses.toString());
                if (gameTypeClasses != null && gameTypeClasses.size() > 0) {
                    oneGameId = gameTypeClasses.get(0).getId();
                    oneGameTitle = gameTypeClasses.get(0).getTitle();
                    if (gameTypeClasses.get(0).get_child() != null) {
                        twoGameTitle = gameTypeClasses.get(0).get_child().get(0).getTitle();
                    } else {
                        twoGameTitle = gameTypeClasses.get(0).getChild().get(0).getTitle();
                    }
                    playPrice = gameTypeClasses.get(0).get_child().get(0).getPrice();
                    gameTypePopu.setDatas(gameTypeClasses);
                    mGameTypeText.setText(gameTypeClasses.get(0).getTitle());
                    //betNumberPresenter.getBetNumber(gameTypeClasses.get(0).getId());
//                    onSelectClick(oneGameTitle, twoGameTitle, oneGameId, getNiceData(gameTypeClasses.get(0)), playPrice);
                    getFistIntoData(gameTypeClasses.get(0));
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
                    BetLottery3Activity.this.lotteryEntity = lotteryEntity;
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
        SharedperfencesUtil.setString(this, "onegametitle", "");
    }


    /**
     * 获取一级菜单点击的数据-------有点复杂  在这里
     *
     * @param item
     * @return
     */
    public void getFistIntoData(GameTypeNewClass3Entity item) {
        String oneTitle = item.getTitle();
        if (item.get_child() != null) {  //除斗牛特殊情况
            item.get_child().get(0).setSelected(true);
            if (!oneTitle.equals("定位胆")) {
                if (!oneTitle.equals("不定位")) {
                    //点击以及菜单回调数据回去  刷新界面   比较复杂
                    onSelectClick(item.getTitle(), item.get_child().get(0).getTitle(), item.getId(), getNiceData(item), item.getPrice());
                } else {
                    onSelectClick(item.getTitle(), item.get_child().get(0).getTitle(), item.getId(), transStructure(item.get_child().get(0)), item.getPrice());
                }
            } else {
                //设置二级菜单
                //回调点击一级菜单的数据
                onSelectClick(item.getTitle(), item.get_child().get(0).getTitle(), item.getId(), item.get_child(), item.getPrice());
            }
        } else {//斗牛特殊情况
            onSelectClick(item.getTitle(), "选码", item.getId(), getNiceData(item), item.getPrice());
        }

    }

    //改结构
    public ArrayList<GameTypeNewClass3Entity> transStructure(GameTypeNewClass3Entity mData) {
        LogUtil.i("info", mData.toString());
        if (mData.getChild() == null) {
            return mData.get_child();
        } else {
            ArrayList<GameTypeNewClass3Entity> list = new ArrayList<>();

//            GameTypeNewClass3Entity childBean = new GameTypeNewClass3Entity();
//            childBean.setId(mData.getId());
//            childBean.setTitle("选码");
//            ArrayList<GameTypeNewClass3Entity> list1 = new ArrayList<>();
//            list1.addAll(mData.getChild());
//            childBean.set_child(list1);

            list.add(mData);
            return list;
        }
    }

    /**
     * 获取一级菜单点击的数据-------有点复杂  在这里
     *
     * @param mData
     * @return
     */
    public ArrayList<GameTypeNewClass3Entity> getNiceData(GameTypeNewClass3Entity mData) {
        if (mData.get_child() != null && mData.get_child().size() > 0) {//没到底
            if (mData.get_child().get(0).get_child() != null) {//五星、三星、二星这种
                return mData.get_child().get(0).get_child();
            } else {//
                ArrayList<GameTypeNewClass3Entity> list = new ArrayList<>();
                GameTypeNewClass3Entity childBean = new GameTypeNewClass3Entity();
                childBean.setId(mData.getId());
                ArrayList<GameTypeNewClass3Entity> list1 = new ArrayList<>();
                list1.addAll(mData.get_child().get(0).getChild());
                childBean.setChild(list1);
                list.add(childBean);
                return list;
            }
        } else {
            ArrayList<GameTypeNewClass3Entity> list = new ArrayList<>();

            GameTypeNewClass3Entity childBean = new GameTypeNewClass3Entity();
            childBean.setId(mData.getId());
            ArrayList<GameTypeNewClass3Entity> list1 = new ArrayList<>();
            list1.addAll(mData.getChild());
            childBean.setChild(list1);

            list.add(childBean);
            return list;
        }
    }
}
