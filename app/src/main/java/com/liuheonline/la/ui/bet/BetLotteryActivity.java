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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liuheonline.la.entity.BetNumberEntity;
import com.liuheonline.la.entity.GameTypeClass2Entity;
import com.liuheonline.la.entity.LotteryEntity;
import com.liuheonline.la.entity.LotteryOtherDetailsEntity;
import com.liuheonline.la.entity.LotteryOtherEntity;
import com.liuheonline.la.entity.UserInfo;
import com.liuheonline.la.mvp.presenter.BetNumberPresenter;
import com.liuheonline.la.mvp.presenter.GameTypeClassPresenter;
import com.liuheonline.la.mvp.presenter.LotterySidPresenter;
import com.liuheonline.la.mvp.presenter.UserInfoPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.history.HistoryActivity;
import com.liuheonline.la.ui.main.trend.LotteryTrend;
import com.liuheonline.la.ui.user.lotterylog.LotteryLogActivity;
import com.liuheonline.la.utils.LotteryTypeUtil;
import com.liuheonline.la.utils.NumberUtil;
import com.liuheonline.la.utils.RandomUtil;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.liuheonline.la.utils.StringUtil;
import com.liuheonline.la.utils.TimeUtil;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.db.DaoSupportFactory;
import com.yxt.itv.library.db.IDaoSoupport;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: aini
 * @date 2018/7/25 18:03
 * @description 六合
 */
public class BetLotteryActivity extends BaseMVPActivity<BaseView<LotteryEntity>, LotterySidPresenter, LotteryEntity>
        implements GameTypePopu.OnSelectClickListener, SensorEventListener, SettingPopu.OnViewClickListener {

    @BindId(R.id.title_layout)
    private RelativeLayout mTitleLayout;

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

    @BindId(R.id.number_recycler)
    private RecyclerView numberRecyclerView;

    @BindId(R.id.number_hint)
    private TextView numberHintText;

    @BindId(R.id.select_group)
    private RadioGroup radioGroup;

    @BindId(R.id.price_linear)
    private LinearLayout priceLinear;

    @BindId(R.id.shake)
    TextView shake;
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

    private BaseQuickAdapter<BetNumberEntity, BaseViewHolder> baseQuickAdapter;

    //彩种ID
    private int sid;

    //玩法规则
    private String rulesContent = "";

    //封盘时间
    private long closeTime;

    //一级玩法 ID
    private int oneGameId;

    //二级玩法 ID
    private int twoGameId;

    //一级玩法名
    private String oneGameTitle = "";

    //二级玩法名
    private String twoGameTitle = "";

    //old 玩法ID
    private int oldGameId;

    //获取最新开奖
    private LotterySidPresenter lotteryPresenter;

    //玩法列表
    private GameTypeClassPresenter gameTypeClassPresenter;

    //投注号码
    private BetNumberPresenter betNumberPresenter;

    //当前期数
    private long issue;

    //倒计时
    private CountDownTimer countDownTimer;

    //设置popu
    private SettingPopu settingPopu;

    //玩法列表popu
    private GameTypePopu gameTypePopu;

    //网格布局layout
    private GridLayoutManager gridLayoutManager;

    //号码池
    private List<BetNumberEntity> betNumberEntityList;

    //选中号码池
    private List<BetNumberEntity> selectBetNumberEntityList = new ArrayList<>();

    //底部选中号码展示
    private StringBuffer numberBuffer = new StringBuffer();

    private Handler handler = new Handler();

    //单注金额
    private int money = 2;

    //订单保存集合
    private List<LotteryOtherDetailsEntity> lotteryOtherDetailsEntityList = new ArrayList<>();

    private LotteryEntity lotteryEntity;

    private IDaoSoupport<LotteryOtherEntity> mLotteryOtherDao;

    private IDaoSoupport<LotteryOtherDetailsEntity> mLotteryOtherDetailsDao;


    public static LotteryOtherEntity staticLotteryOtherEntity;
    UserInfoPresenter userInfoPresenter;

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sid = bundle.getInt("sid");
            closeTime = bundle.getLong("closeTime");
            rulesContent = bundle.getString("rulesContent");
        }

        //获取传感器管理器
        SensorManager sensorManager = (SensorManager) this.getSystemService(Service.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);


        mLotteryOtherDao = DaoSupportFactory.getFactory(getPackageName()).getDao(LotteryOtherEntity.class);
        mLotteryOtherDetailsDao = DaoSupportFactory.getFactory(getPackageName()).getDao(LotteryOtherDetailsEntity.class);

        baseQuickAdapter = new BaseQuickAdapter<BetNumberEntity, BaseViewHolder>(R.layout.item_betnumber) {
            @Override
            protected void convert(BaseViewHolder helper, BetNumberEntity item) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) numberRecyclerView.getLayoutManager();
                int tabCunt = gridLayoutManager.getSpanCount();
                LinearLayout linear1 = helper.getView(R.id.linear1);
                RelativeLayout linear2 = helper.getView(R.id.linear2);
                if (tabCunt == 1) {
                    linear1.setVisibility(View.GONE);
                    linear2.setVisibility(View.VISIBLE);
                } else {
                    linear2.setVisibility(View.GONE);
                    linear1.setVisibility(View.VISIBLE);
                }
                CheckBox checkBox = helper.getView(R.id.number_checkbox);
                TextView priceText = helper.getView(R.id.price);
                checkBox.setText(item.getCode());
                priceText.setText(StringUtil.getPrice(item.getPrice() > 0 ? item.getPrice() + "" : "0"));
                if (oneGameTitle.equals("连码")) {
                    priceText.setText("");
                }
                checkBox.setChecked(item.isSelected());
                checkBox.setOnClickListener(v -> {
                    item.setSelected(checkBox.isChecked());
                    switch (oneGameTitle) {
                        case "连肖连尾":
                        case "合肖":
                        case "自选不中":
                        case "连码":
                            refreshNumberSelects(item, checkBox.isChecked());
                            break;
                        default:
                            refreshNumberSelect(item, checkBox.isChecked());
                            break;
                    }

                });

                //当玩法为波色的时候
                CheckBox checkBox1 = helper.getView(R.id.number_checkbox2);
                TextView nameText = helper.getView(R.id.name);
                TextView numberText = helper.getView(R.id.number);
                TextView priceText2 = helper.getView(R.id.price2);

                if (tabCunt == 3) {
                    checkBox.setBackgroundResource(R.drawable.bet_number_liangmian_check_bg);
                } else if (tabCunt == 5) {
                    checkBox.setBackgroundResource(R.drawable.bet_number_check_bg);
                } else {
                    switch (helper.getAdapterPosition()) {
                        case 0:
                            numberText.setText(R.string.number1);
                            checkBox1.setBackgroundResource(R.drawable.bet_number_check_bg_type01);
                            break;
                        case 1:
                            numberText.setText(R.string.number2);
                            checkBox1.setBackgroundResource(R.drawable.bet_number_check_bg_type02);
                            break;
                        case 2:
                            numberText.setText(R.string.number3);
                            checkBox1.setBackgroundResource(R.drawable.bet_number_check_bg_type03);
                            break;
                    }

                    checkBox1.setOnClickListener(v -> {
                        item.setSelected(checkBox1.isChecked());
                        refreshNumberSelect(item, checkBox1.isChecked());
                        checkStatusChange(helper.getAdapterPosition(), numberText, nameText, checkBox1.isChecked());
                    });
                    checkStatusChange(helper.getAdapterPosition(), numberText, nameText, item.isSelected());
                    checkBox1.setChecked(item.isSelected());
                    nameText.setText(item.getCode());
                    priceText2.setText("赔率 " + item.getPrice());
                }
            }

            public void checkStatusChange(int postion, TextView numberText, TextView nameText, boolean flag) {
                switch (postion) {
                    case 0:
                        numberText.setTextColor(flag ? Color.parseColor("#ffffff") :
                                Color.parseColor("#fc322b"));
                        nameText.setTextColor(flag ? Color.parseColor("#ffffff") :
                                Color.parseColor("#fc322b"));
                        break;
                    case 1:
                        numberText.setTextColor(flag ? Color.parseColor("#ffffff") :
                                Color.parseColor("#5498c8"));
                        nameText.setTextColor(flag ? Color.parseColor("#ffffff") :
                                Color.parseColor("#5498c8"));
                        break;
                    case 2:
                        numberText.setTextColor(flag ? Color.parseColor("#ffffff") :
                                Color.parseColor("#71a348"));
                        nameText.setTextColor(flag ? Color.parseColor("#ffffff") :
                                Color.parseColor("#71a348"));
                        break;
                }
            }

        };


    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_betlottery);
    }

    @Override
    protected void initTitle() {
    }

    @Override
    protected void initView() {

        //初始化单注金额
        editMoney.setText(money + "");
        //初始化设置popu
//        settingPopu = new SettingPopu(this,this);
        settingPopu = new SettingPopu(this, null, this);
        //初始化玩法选择popuwindow
        gameTypePopu = new GameTypePopu(this, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        gameTypePopu.setOnSelectClickListener(this);
        gridLayoutManager = new GridLayoutManager(this, 5);
        numberRecyclerView.setLayoutManager(gridLayoutManager);
        numberRecyclerView.setHasFixedSize(true);
        numberRecyclerView.setAdapter(baseQuickAdapter);

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

    @OnClick({R.id.setting_img, R.id.game_type, R.id.rb_all, R.id.rb_big, R.id.rb_min, R.id.rb_single, R.id.rb_double,
            R.id.rb_double, R.id.rb_clear, R.id.back, R.id.clear_img, R.id.submit, R.id.shake})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_img:
                settingPopu.showPopupWindow(R.id.setting_img);
                break;
            case R.id.game_type:
                gameTypePopu.showPopupWindow(mTitleLayout);
                break;
            case R.id.rb_all:
                clearSelectNumber();
                clearNumber();
                for (BetNumberEntity betNumberEntity : betNumberEntityList) {
                    betNumberEntity.setSelected(true);
                    numberBuffer.append(betNumberEntity.getCode()).append(" ");
                }
                selectBetNumberEntityList.addAll(betNumberEntityList);
                refreshBottom();
                break;
            case R.id.rb_big:
                clearSelectNumber();
                clearNumber();
                for (BetNumberEntity betNumberEntity : betNumberEntityList) {
                    int number = Integer.parseInt(betNumberEntity.getCode());
                    if (number >= 25) {
                        betNumberEntity.setSelected(true);
                        numberBuffer.append(betNumberEntity.getCode()).append(" ");
                        selectBetNumberEntityList.add(betNumberEntity);
                    }
                }
                refreshBottom();
                break;
            case R.id.rb_min:
                clearSelectNumber();
                clearNumber();
                for (BetNumberEntity betNumberEntity : betNumberEntityList) {
                    int number = Integer.parseInt(betNumberEntity.getCode());
                    if (number < 25) {
                        betNumberEntity.setSelected(true);
                        numberBuffer.append(betNumberEntity.getCode()).append(" ");
                        selectBetNumberEntityList.add(betNumberEntity);
                    }
                }
                refreshBottom();
                break;
            case R.id.rb_single:
                clearSelectNumber();
                clearNumber();
                for (BetNumberEntity betNumberEntity : betNumberEntityList) {
                    int number = Integer.parseInt(betNumberEntity.getCode());
                    if (number % 2 != 0) {
                        betNumberEntity.setSelected(true);
                        numberBuffer.append(betNumberEntity.getCode()).append(" ");
                        selectBetNumberEntityList.add(betNumberEntity);
                    }
                }
                refreshBottom();
                break;
            case R.id.rb_double:
                clearSelectNumber();
                clearNumber();
                for (BetNumberEntity betNumberEntity : betNumberEntityList) {
                    int number = Integer.parseInt(betNumberEntity.getCode());
                    if (number % 2 == 0) {
                        betNumberEntity.setSelected(true);
                        numberBuffer.append(betNumberEntity.getCode()).append(" ");
                        selectBetNumberEntityList.add(betNumberEntity);
                    }
                }
                refreshBottom();
                break;
            case R.id.rb_clear:
                //清除已选择号码
                clearSelectNumber();
                //清除UI显示已选择号码
                clearNumber();
                baseQuickAdapter.setNewData(betNumberEntityList);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.clear_img:
                //清除已选择号码
                clearSelectNumber();
                //清除UI显示已选择号码
                clearNumber();
                baseQuickAdapter.setNewData(betNumberEntityList);
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
        int minCheckNum;
        switch (twoGameTitle) {
            case "五不中":
                minCheckNum = 5;
                break;
            case "六不中":
                minCheckNum = 6;
                break;
            case "七不中":
                minCheckNum = 7;
                break;
            case "八不中":
                minCheckNum = 8;
                break;
            case "九不中":
                minCheckNum = 9;
                break;
            case "十不中":
                minCheckNum = 10;
                break;
            case "十一不中":
                minCheckNum = 11;
                break;
            case "十二不中":
                minCheckNum = 12;
                break;
            case "三中二":
            case "三全中":
                minCheckNum = 3;
                break;
            case "二全中":
            case "二中特":
            case "特串":
                minCheckNum = 2;
                break;
            case "四全中":
                minCheckNum = 4;
                break;
            case "二连肖":
            case "二连尾":
            case "二合肖":
                minCheckNum = 2;
                break;
            case "三连肖":
            case "三连尾":
            case "三合肖":
                minCheckNum = 3;
                break;
            case "四连肖":
            case "四连尾":
            case "四合肖":
                minCheckNum = 4;
                break;
            case "五连肖":
            case "五连尾":
            case "五合肖":
                minCheckNum = 5;
                break;
            case "六合肖":
                minCheckNum = 6;
                break;
            case "七合肖":
                minCheckNum = 7;
                break;
            case "八合肖":
                minCheckNum = 8;
                break;
            case "九合肖":
                minCheckNum = 9;
                break;
            case "十合肖":
                minCheckNum = 10;
                break;
            case "十一合肖":
                minCheckNum = 11;
                break;
            default:
                minCheckNum = 1;
                break;
        }

        int[] number = RandomUtil.getRandomNumber(betNumberEntityList.size(), minCheckNum);

        //清除已选择号码
        clearSelectNumber();
        //清除UI显示已选择号码
        clearNumber();

        for (int i = 0; i < number.length; i++) {
            betNumberEntityList.get(number[i] - 1).setSelected(true);
            if (number.length == 1) {
                refreshNumberSelect(betNumberEntityList.get(number[i] - 1), true);
            } else {
                refreshNumberSelects(betNumberEntityList.get(number[i] - 1), true);
            }
        }
        baseQuickAdapter.notifyDataSetChanged();
    }


    //当全选时刷新同意刷新底部
    private void refreshBottom() {
        for (BetNumberEntity betNumberEntity : selectBetNumberEntityList) {
            //计算注数
            LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
            lotteryOtherDetailsEntity.setLottery_price(money);
            lotteryOtherDetailsEntity.setLottery_id(betNumberEntity.getId());
            lotteryOtherDetailsEntity.setLottery_child_name(betNumberEntity.getAttr());
            lotteryOtherDetailsEntity.setLottery_name(betNumberEntity.getCode());
            lotteryOtherDetailsEntity.setOdds(betNumberEntity.getPrice());
            lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
        }


        //刷新注数
        if (numberBuffer.toString().trim().equals("")) {
            selectNumberText.setVisibility(View.GONE);
        } else {
            selectNumberText.setVisibility(View.VISIBLE);
        }
        selectNumberText.setText(numberBuffer);
        baseQuickAdapter.setNewData(betNumberEntityList);
    }


    //清空号码选择池的选中状态
    private void clearNumber() {
        for (BetNumberEntity betNumberEntity : betNumberEntityList) {
            betNumberEntity.setSelected(false);
        }
    }

    //手动单选 计算注数 刷新ui  常规玩法
    private void refreshNumberSelect(BetNumberEntity betNumberEntity, boolean isSelect) {
        if (isSelect) {
            selectBetNumberEntityList.add(betNumberEntity);
        } else {
            for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
                BetNumberEntity numberEntity = selectBetNumberEntityList.get(i);
                if (numberEntity.getId() == betNumberEntity.getId()) {
                    selectBetNumberEntityList.remove(i);
                }
            }
        }

        //计算注数
        lotteryOtherDetailsEntityList.clear();
        for (BetNumberEntity betNumberEntity1 : selectBetNumberEntityList) {
            LotteryOtherDetailsEntity lotteryOtherDetailsEntity = new LotteryOtherDetailsEntity();
            lotteryOtherDetailsEntity.setLottery_price(money);
            lotteryOtherDetailsEntity.setLottery_id(betNumberEntity1.getId());
            lotteryOtherDetailsEntity.setLottery_child_name(betNumberEntity1.getAttr());
            lotteryOtherDetailsEntity.setLottery_name(betNumberEntity1.getCode());
            lotteryOtherDetailsEntity.setOdds(betNumberEntity1.getPrice());
            lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
        }

        //刷新底部选中池
        numberBuffer.setLength(0);
        for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
            BetNumberEntity numberEntity = selectBetNumberEntityList.get(i);
            numberBuffer.append(numberEntity.getCode()).append(" ");
        }
        if (numberBuffer.toString().trim().equals("")) {
            selectNumberText.setVisibility(View.GONE);
        } else {
            selectNumberText.setVisibility(View.VISIBLE);
        }
        selectNumberText.setText(numberBuffer);
    }

    //手动单选 计算注数 刷新ui  复试玩法
    private void refreshNumberSelects(BetNumberEntity betNumberEntity, boolean isSelect) {
        int minCheckNum = 0;
        int maxCheckNum = 0;

        switch (twoGameTitle) {
            case "五不中":
                minCheckNum = 5;
                maxCheckNum = 10;
                break;
            case "六不中":
                minCheckNum = 6;
                maxCheckNum = 10;
                break;
            case "七不中":
                minCheckNum = 7;
                maxCheckNum = 10;
                break;
            case "八不中":
                minCheckNum = 8;
                maxCheckNum = 11;
                break;
            case "九不中":
                minCheckNum = 9;
                maxCheckNum = 12;
                break;
            case "十不中":
                minCheckNum = 10;
                maxCheckNum = 13;
                break;
            case "十一不中":
                minCheckNum = 11;
                maxCheckNum = 13;
                break;
            case "十二不中":
                minCheckNum = 12;
                maxCheckNum = 14;
                break;
            case "三中二":
            case "三全中":
                minCheckNum = 3;
                maxCheckNum = 10;
                break;
            case "二全中":
            case "二中特":
            case "特串":
                minCheckNum = 2;
                maxCheckNum = 10;
                break;
            case "四全中":
                minCheckNum = 4;
                maxCheckNum = 10;
                break;
            case "二连肖":
            case "二连尾":
            case "二合肖":
                minCheckNum = 2;
                maxCheckNum = betNumberEntityList.size();
                break;
            case "三连肖":
            case "三连尾":
            case "三合肖":
                minCheckNum = 3;
                maxCheckNum = betNumberEntityList.size();
                break;
            case "四连肖":
            case "四连尾":
            case "四合肖":
                minCheckNum = 4;
                maxCheckNum = betNumberEntityList.size();
                break;
            case "五连肖":
            case "五连尾":
            case "五合肖":
                minCheckNum = 5;
                maxCheckNum = betNumberEntityList.size();
                break;
            case "六合肖":
                minCheckNum = 6;
                maxCheckNum = betNumberEntityList.size();
                break;
            case "七合肖":
                minCheckNum = 7;
                maxCheckNum = betNumberEntityList.size();
                break;
            case "八合肖":
                minCheckNum = 8;
                maxCheckNum = betNumberEntityList.size();
                break;
            case "九合肖":
                minCheckNum = 9;
                maxCheckNum = betNumberEntityList.size();
                break;
            case "十合肖":
                minCheckNum = 10;
                maxCheckNum = betNumberEntityList.size();
                break;
            case "十一合肖":
                minCheckNum = 11;
                maxCheckNum = betNumberEntityList.size();
                break;

        }

        if (isSelect) {
            selectBetNumberEntityList.add(betNumberEntity);
            if (selectBetNumberEntityList.size() > maxCheckNum) {

                for (int i = 0; i < betNumberEntityList.size(); i++) {
                    BetNumberEntity betNumberEntity1 = betNumberEntityList.get(i);
                    if (betNumberEntity1.getId() == selectBetNumberEntityList.get(0).getId()) {
                        betNumberEntity1.setSelected(false);
                        baseQuickAdapter.notifyItemChanged(i);
                    }
                }
                selectBetNumberEntityList.remove(0);
            }
        } else {
            for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
                BetNumberEntity numberEntity = selectBetNumberEntityList.get(i);
                if (numberEntity.getId() == betNumberEntity.getId()) {
                    selectBetNumberEntityList.remove(i);
                }
            }
        }


        //计算注数
        lotteryOtherDetailsEntityList.clear();
        NumberUtil numberUtil = new NumberUtil();
        lotteryOtherDetailsEntityList.addAll(numberUtil.combine(0, minCheckNum, selectBetNumberEntityList, money));

        //刷新底部选中池
        numberBuffer.setLength(0);
        for (int i = 0; i < selectBetNumberEntityList.size(); i++) {
            BetNumberEntity numberEntity = selectBetNumberEntityList.get(i);
            numberBuffer.append(numberEntity.getCode()).append(" ");
        }
        if (numberBuffer.toString().trim().equals("")) {
            selectNumberText.setVisibility(View.GONE);
        } else {
            selectNumberText.setVisibility(View.VISIBLE);
        }
        selectNumberText.setText(numberBuffer);
    }

    //清空已选中号码池
    private void clearSelectNumber() {
        selectBetNumberEntityList.clear();
        lotteryOtherDetailsEntityList.clear();
        numberBuffer.setLength(0);
        selectNumberText.setText("");
        selectNumberText.setVisibility(View.GONE);
    }

    //提交订单
    private void submitOther() {
        if (lotteryOtherDetailsEntityList.size() > 0) {
            LotteryOtherEntity lotteryOtherEntity = new LotteryOtherEntity();
            long issue = lotteryEntity.getLottery().getNext_periods();

            staticLotteryOtherEntity = null;
            //修改金额
            for (LotteryOtherDetailsEntity lotteryOtherDetailsEntity : lotteryOtherDetailsEntityList) {
                lotteryOtherDetailsEntity.setLottery_price(money);
            }

            if (oneGameTitle.equals("连码")) {
                for (LotteryOtherDetailsEntity lotteryOtherDetailsEntity : lotteryOtherDetailsEntityList) {
                    String idStr = lotteryOtherDetailsEntity.getLottery_id() + "";
                    int id = Integer.parseInt(idStr.substring(0, idStr.length() - 2));
                    lotteryOtherDetailsEntity.setLottery_id(id);
                }
            }
            lotteryOtherEntity.setBet_periods(issue);
            lotteryOtherEntity.setLottery_amount(lotteryOtherDetailsEntityList.size() * money);
            lotteryOtherEntity.setLottery_num(lotteryOtherDetailsEntityList.size());
            lotteryOtherEntity.setLottery_rebates_price(betNumberEntityList.get(0).getLottery_rebates_price());
            lotteryOtherEntity.setBet_info(lotteryOtherDetailsEntityList);
            lotteryOtherEntity.setSpecie_id(sid);
            lotteryOtherEntity.setLottery_id(oneGameId == 0 ? twoGameId : oneGameId);
            lotteryOtherEntity.setLottery_class_name(oneGameTitle);


            //intent传输内容限制  使用sqLet  缓存到本地

           /* mLotteryOtherDao.clean();
            mLotteryOtherDetailsDao.clean();
            mLotteryOtherDao.insert(lotteryOtherEntity);
            mLotteryOtherDetailsDao.insert(lotteryOtherDetailsEntityList);*/

            staticLotteryOtherEntity = lotteryOtherEntity;

            //跳转到订单页面
            Bundle bundle = new Bundle();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nexttime = sdf.format(new Date(lotteryEntity.getLottery().getNext_time() * 1000));
            bundle.putString("nextTime", nexttime);
            bundle.putString("type", oneGameTitle);
            startActivity(BetLotteryOtherActivity.class, bundle);
        }


    }

    //玩法选择事件
    @Override
    public void onSelectClick(String oneTitle, String twoTitle, int id, int pid, boolean isRefresh) {
        //更新title
        mGameTypeText.setText(oneTitle + "-" + twoTitle);
        this.oneGameTitle = oneTitle;
        this.oneGameId = id;
        this.twoGameTitle = twoTitle;
        this.twoGameId = pid;
        Log.d("选择事件", "  一级Title--" + oneTitle + "  二级Title--" + twoTitle + "  sid--" + id);
        //刷新投注数据
        int typeId = id == 0 ? pid : id;
        if (typeId != oldGameId) {
            oldGameId = typeId;
            betNumberPresenter.getBetNumber(typeId);
            clearSelectNumber();
            Log.d("选择事件", "刷新数据");
        }
        if (isRefresh) {
            gameTypePopu.dismiss();
        }
    }

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
        numberTx01.setText(lotteryEntity.getLottery().getNum1() + "");
        numberTx02.setText(lotteryEntity.getLottery().getNum2() + "");
        numberTx03.setText(lotteryEntity.getLottery().getNum3() + "");
        numberTx04.setText(lotteryEntity.getLottery().getNum4() + "");
        numberTx05.setText(lotteryEntity.getLottery().getNum5() + "");
        numberTx06.setText(lotteryEntity.getLottery().getNum6() + "");
        numberTx07.setText(lotteryEntity.getLottery().getNum_s() + "");


        animNumberTx01.setText(lotteryEntity.getLottery().getNum1_zodiac());
        animNumberTx02.setText(lotteryEntity.getLottery().getNum2_zodiac());
        animNumberTx03.setText(lotteryEntity.getLottery().getNum3_zodiac());
        animNumberTx04.setText(lotteryEntity.getLottery().getNum4_zodiac());
        animNumberTx05.setText(lotteryEntity.getLottery().getNum5_zodiac());
        animNumberTx06.setText(lotteryEntity.getLottery().getNum6_zodiac());
        animNumberTx07.setText(lotteryEntity.getLottery().getNum_s_zodiac());

        numberTx01.setBackgroundResource(LotteryTypeUtil.getPureBallBG(lotteryEntity.getLottery().getNum1_color_id()));
        numberTx02.setBackgroundResource(LotteryTypeUtil.getPureBallBG(lotteryEntity.getLottery().getNum2_color_id()));
        numberTx03.setBackgroundResource(LotteryTypeUtil.getPureBallBG(lotteryEntity.getLottery().getNum3_color_id()));
        numberTx04.setBackgroundResource(LotteryTypeUtil.getPureBallBG(lotteryEntity.getLottery().getNum4_color_id()));
        numberTx05.setBackgroundResource(LotteryTypeUtil.getPureBallBG(lotteryEntity.getLottery().getNum5_color_id()));
        numberTx06.setBackgroundResource(LotteryTypeUtil.getPureBallBG(lotteryEntity.getLottery().getNum6_color_id()));
        numberTx07.setBackgroundResource(LotteryTypeUtil.getPureBallBG(lotteryEntity.getLottery().getNum_s_color_id())
        );
    }

    @Override
    protected void initPresenter() {
        presenter = new LotterySidPresenter();
        lotteryPresenter = new LotterySidPresenter();
        gameTypeClassPresenter = new GameTypeClassPresenter();
        betNumberPresenter = new BetNumberPresenter();
        userInfoPresenter = new UserInfoPresenter();
        betNumberPresenter.attachView(new BaseView<List<BetNumberEntity>>() {
            @Override
            public void onLoading() {
                Log.d("投注号码", "获取中……");
            }

            @Override
            public void onLoadFailed(int code, String error) {
                Toast.makeText(LiuHeApplication.context, "投注号码获取失败", Toast.LENGTH_SHORT).show();
                Log.d("投注号码", "获取失败");
            }

            @Override
            public void successed(List<BetNumberEntity> betNumberEntities) {
                if (betNumberEntities != null && betNumberEntities.size() > 0) {
                    betNumberEntityList = betNumberEntities;
                    //是否显示头部
                    priceLinear.setVisibility(View.GONE);
                    radioGroup.setVisibility(View.GONE);
                    switch (betNumberEntities.get(0).getAttr()) {
                        case "特码B":
                        case "特码A":
                            radioGroup.setVisibility(View.VISIBLE);
                            break;
                        case "三中二":
                        case "三全中":
                        case "二全中":
                        case "二中特":
                        case "特串":
                        case "四全中":
                            priceLinear.setVisibility(View.VISIBLE);
                            String price = "";
                            for (BetNumberEntity betNumberEntity : betNumberEntities) {
                                price += "  " + betNumberEntity.getCode() + " " + betNumberEntity.getPrice();
                            }
                            priceTopText.setText(price);
                            break;
                    }

                    //刷新选码类型
                    switch (betNumberEntities.get(0).getAttr()) {
                        case "特码B":
                        case "特码A":
                        case "正码":
                        case "正一特":
                        case "正二特":
                        case "正三特":
                        case "正四特":
                        case "正五特":
                        case "正六特":
                        case "五不中":
                        case "六不中":
                        case "七不中":
                        case "八不中":
                        case "九不中":
                        case "十不中":
                        case "十一不中":
                        case "十二不中":
                        case "三中二":
                        case "三全中":
                        case "二全中":
                        case "二中特":
                        case "特串":
                        case "四全中":
                            numberHintText.setText("选码");
                            break;
                        default:
                            numberHintText.setText("种类");
                            break;
                    }

                    //根据玩法刷新布局样式
                    switch (betNumberEntities.get(0).getAttr()) {
                        case "特码A":
                        case "特码B":
                        case "正码":
                        case "正一特":
                        case "正二特":
                        case "正三特":
                        case "正四特":
                        case "正五特":
                        case "正六特":
                        case "五不中":
                        case "六不中":
                        case "七不中":
                        case "八不中":
                        case "九不中":
                        case "十不中":
                        case "十一不中":
                        case "十二不中":
                            gridLayoutManager.setSpanCount(5);
                            baseQuickAdapter.setNewData(betNumberEntities);
                            break;
                        case "三中二":
                        case "三全中":
                        case "二全中":
                        case "二中特":
                        case "特串":
                        case "四全中":
                            List<BetNumberEntity> betNumberEntityList = new ArrayList<>();
                            for (int i = 1; i <= 49; i++) {
                                BetNumberEntity betNumberEntity = new BetNumberEntity();
                                betNumberEntity.setAttr_id(betNumberEntities.get(0).getAttr_id());
                                betNumberEntity.setAttr(betNumberEntities.get(0).getAttr());
                                int id;
                                if (i < 10) {
                                    id = Integer.parseInt(betNumberEntities.get(0).getId() + "0" + i);
                                } else {
                                    id = Integer.parseInt(betNumberEntities.get(0).getId() + "" + i);
                                }
                                betNumberEntity.setId(id);
                                betNumberEntity.setPrice(betNumberEntities.get(0).getPrice());
                                betNumberEntity.setCode(i + "");
                                betNumberEntityList.add(betNumberEntity);
                            }
                            gridLayoutManager.setSpanCount(5);
                            BetLotteryActivity.this.betNumberEntityList = betNumberEntityList;
                            baseQuickAdapter.setNewData(betNumberEntityList);
                            break;
                        case "色波":
                            gridLayoutManager.setSpanCount(1);
                            baseQuickAdapter.setNewData(betNumberEntities);
                            break;
                        case "两面":
                        case "半波":
                        case "半半波":
                        case "正肖":
                        case "7色波":
                        case "特肖":
                        case "头尾数":
                        case "正码一":
                        case "正码二":
                        case "正码三":
                        case "正码四":
                        case "正码五":
                        case "正码六":
                        case "五行":
                        case "一肖":
                        case "尾数":
                        case "总肖":
                        case "二连肖":
                        case "三连肖":
                        case "四连肖":
                        case "五连肖":
                        case "二连尾":
                        case "三连尾":
                        case "四连尾":
                        case "五连尾":
                        case "二合肖":
                        case "三合肖":
                        case "四合肖":
                        case "五合肖":
                        case "六合肖":
                        case "七合肖":
                        case "八合肖":
                        case "九合肖":
                        case "十合肖":
                        case "十一合肖":
                            gridLayoutManager.setSpanCount(3);
                            baseQuickAdapter.setNewData(betNumberEntities);
                            break;
                    }

                }
            }
        });

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
                if (gameTypeClasses != null && gameTypeClasses.size() > 0) {
                    oneGameId = gameTypeClasses.get(0).getId();
                    oneGameTitle = gameTypeClasses.get(0).getTitle();
                    gameTypePopu.setDatas(gameTypeClasses);
                    mGameTypeText.setText(gameTypeClasses.get(0).getTitle() + "-选码");
                    betNumberPresenter.getBetNumber(gameTypeClasses.get(0).getId());
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
                    BetLotteryActivity.this.lotteryEntity = lotteryEntity;
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
        if (betNumberEntityList != null && betNumberEntityList.size() > 0) {
            //清除已选择号码
            clearSelectNumber();
            //清除UI显示已选择号码
            clearNumber();
            baseQuickAdapter.setNewData(betNumberEntityList);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        staticLotteryOtherEntity = null;
    }
}
