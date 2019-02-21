package com.liuheonline.la.ui.user.lotterylog;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.BetLotteryLogDetailsEntity;
import com.liuheonline.la.mvp.presenter.BetLotteryLogDetalsPresenter;
import com.liuheonline.la.mvp.presenter.CancelOtherPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.utils.ImageUrlUtil;
import com.liuheonline.la.utils.LotteryTypeUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

/**
 * @author: aini
 * @date 2018/8/3 10:46
 * @description 单注订单详情
 */
public class BetLotteryLogDetailsActivity extends BaseMVPActivity<BaseView<BetLotteryLogDetailsEntity>,
        BetLotteryLogDetalsPresenter, BetLotteryLogDetailsEntity> {

    @BindId(R.id.name)
    private TextView nameText;

    @BindId(R.id.issue)
    private TextView issueText;

    @BindId(R.id.lottery_state)
    private TextView lotteryStateText;

    @BindId(R.id.log_img)
    private ImageView log_img;

    //订单号
    private TextView lotterySnText;

    //投注金额
    private TextView allMoneyText;

    //注数
    private TextView allNumText;

    //返点
    private TextView backMoneyText;

    //投注时间
    private TextView betTimeText;

    //开奖状态
    private TextView betStateText;

    //开奖号码
    private TextView numberText01, numberText02, numberText03, numberText04, numberText05, numberText06, numberText07, numberText08, numberText09, numberText10;

    //生肖
    private TextView animNumberText01, animNumberText02, animNumberText03,
            animNumberText04, animNumberText05, animNumberText06, animNumberText07;

    //开奖号码显示区域
    private LinearLayout numberShowLinear;

    //复制
    private Button butCopy;


    //取消订单
    private CancelOtherPresenter cancelOtherPresenter;

    @BindId(R.id.bet_recycler)
    private RecyclerView logRecyclerView;

    @BindId(R.id.cancel)
    private Button cancelBut;

    private AlertDialog waitDialog;

    private BaseQuickAdapter<BetLotteryLogDetailsEntity.OrderLotteryBean, BaseViewHolder> baseQuickAdapter;

    private int otherId;


    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            otherId = bundle.getInt("otherId");
        }

        baseQuickAdapter = new BaseQuickAdapter<BetLotteryLogDetailsEntity.OrderLotteryBean, BaseViewHolder>(R.layout.item_betlotterylog) {
            @Override
            protected void convert(BaseViewHolder helper, BetLotteryLogDetailsEntity.OrderLotteryBean item) {
                helper.setText(R.id.number, item.getLottery_name())
                        .setText(R.id.money, item.getLottery_price());
                //0未开奖,1中奖，2未中奖,3多倍中奖 4和局,5已取消
                switch (item.getState()) {
                    case 0:
                        helper.setText(R.id.state, "待开奖");
                        break;
                    case 4:
                        helper.setText(R.id.state, "和局");
                        break;
                    case 5:
                        helper.setText(R.id.state, "已取消");
                        break;
                    default:
                        helper.setText(R.id.state, item.getWinning().equals("0.00") ? "-" + item.getLottery_price() :
                                "+" + item.getWinning());
                        helper.setTextColor(R.id.state, item.getWinning().equals("0.00") ?
                                getResources().getColor(R.color.logtext) : getResources().getColor(R.color.colorAccent));
                        helper.setTextColor(R.id.number, item.getWinning().equals("0.00") ?
                                getResources().getColor(R.color.logtext) : getResources().getColor(R.color.colorAccent));
                        helper.setTextColor(R.id.money, item.getWinning().equals("0.00") ?
                                getResources().getColor(R.color.logtext) : getResources().getColor(R.color.colorAccent));
                        break;
                }

            }
        };
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_betlottery_log_detais);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("记录详情")
                .builder();
    }

    @Override
    protected void initView() {
        waitDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint, "加载中……")
                .create();


        logRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        logRecyclerView.setHasFixedSize(true);
        logRecyclerView.setAdapter(baseQuickAdapter);
    }

    @OnClick({R.id.cancel})
    private void gotoBet(View view) {
        cancelOtherPresenter.cancelOther(otherId);
    }

    @Override
    protected void initPresenter() {
        presenter = new BetLotteryLogDetalsPresenter();
        cancelOtherPresenter = new CancelOtherPresenter();
        cancelOtherPresenter.attachView(new BaseView<Integer>() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onLoadFailed(int code, String error) {
                Toast.makeText(LiuHeApplication.context, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void successed(Integer integer) {
                Toast.makeText(LiuHeApplication.context, "已取消", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void fetchData() {
        presenter.betLotteryLogDetails(otherId);
    }

    @Override
    public void onLoading() {
        waitDialog.show();
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        waitDialog.cancel();
        Toast.makeText(LiuHeApplication.context, error, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void successed(BetLotteryLogDetailsEntity betLotteryLogDetailsEntity) {
        waitDialog.cancel();
        if (betLotteryLogDetailsEntity != null) {
            baseQuickAdapter.removeAllHeaderView();
            nameText.setText(betLotteryLogDetailsEntity.getSpecies_name() + "-" + betLotteryLogDetailsEntity.getLottery_class_name());
            issueText.setText("第" + betLotteryLogDetailsEntity.getIssue() + "期");
            cancelBut.setVisibility(View.GONE);
            switch (betLotteryLogDetailsEntity.getOrder_state()) {
                case 0:
                    lotteryStateText.setText("已取消");
                    break;
                case 20:
                    lotteryStateText.setText("待开奖");
                    cancelBut.setVisibility(View.VISIBLE);
                    break;
                case 30:
                    lotteryStateText.setText("已开奖");
                    break;
            }
            Glide.with(this)
                    .load(ImageUrlUtil.getImgUrl(betLotteryLogDetailsEntity.getPic_link(), 50))
                    .apply(new RequestOptions()
                            .placeholder(R.mipmap.jianzaizhong) //加载中图片
                            .error(R.mipmap.jiazaishibai) //加载失败图片
                            .fallback(R.mipmap.jiazaishibai) //url为空图片
                            .centerCrop() // 填充方式
                            .priority(Priority.HIGH) //优先级
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .into(log_img);
            View headerView;
            if (betLotteryLogDetailsEntity.getSpecies_id() == 7) {//六合  普通玩法
                //添加头部
                headerView = getLayoutInflater().inflate(R.layout.item_head_betlotterylog, null);
                lotterySnText = headerView.findViewById(R.id.lottery_sn);
                allMoneyText = headerView.findViewById(R.id.all_money);
                allNumText = headerView.findViewById(R.id.all_num);
                backMoneyText = headerView.findViewById(R.id.back_money);
                betTimeText = headerView.findViewById(R.id.bet_time);
                butCopy = headerView.findViewById(R.id.but_copy);
                betStateText = headerView.findViewById(R.id.bet_state);
                numberShowLinear = headerView.findViewById(R.id.number_linear);
                numberText01 = headerView.findViewById(R.id.number01);
                numberText02 = headerView.findViewById(R.id.number02);
                numberText03 = headerView.findViewById(R.id.number03);
                numberText04 = headerView.findViewById(R.id.number04);
                numberText05 = headerView.findViewById(R.id.number05);
                numberText06 = headerView.findViewById(R.id.number06);
                numberText07 = headerView.findViewById(R.id.number07);
                animNumberText01 = headerView.findViewById(R.id.anim_number01);
                animNumberText02 = headerView.findViewById(R.id.anim_number02);
                animNumberText03 = headerView.findViewById(R.id.anim_number03);
                animNumberText04 = headerView.findViewById(R.id.anim_number04);
                animNumberText05 = headerView.findViewById(R.id.anim_number05);
                animNumberText06 = headerView.findViewById(R.id.anim_number06);
                animNumberText07 = headerView.findViewById(R.id.anim_number07);
                butCopy.setOnClickListener(v -> {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(lotterySnText.getText());
                    Toast.makeText(LiuHeApplication.context, "已复制", Toast.LENGTH_SHORT).show();
                });
                baseQuickAdapter.addHeaderView(headerView);
                lotterySnText.setText(betLotteryLogDetailsEntity.getOrder_sn());
                allMoneyText.setText(betLotteryLogDetailsEntity.getLottery_amount() + "元");
                allNumText.setText(betLotteryLogDetailsEntity.getLottery_num() + "注");
                backMoneyText.setText(betLotteryLogDetailsEntity.getLottery_rebates_price() + "元");
                betTimeText.setText(betLotteryLogDetailsEntity.getAdd_time());
                if (betLotteryLogDetailsEntity.getLottery_code() != null) {
                    numberShowLinear.setVisibility(View.VISIBLE);
                    betStateText.setVisibility(View.GONE);
                    numberText01.setText(betLotteryLogDetailsEntity.getLottery_code().getNum1() + "");
//                    numberText01.setBackgroundResource(LotteryTypeUtil.getBallBG(betLotteryLogDetailsEntity
//                            .getLottery_code().getNum1_color_id()));
//                    numberText01.setTextColor(0xffff2f1e);
                    numberText01.setBackgroundResource(LotteryTypeUtil.getPureBallBG(betLotteryLogDetailsEntity
                            .getLottery_code().getNum1_color_id()));
                    numberText02.setText(betLotteryLogDetailsEntity.getLottery_code().getNum2() + "");
//                    numberText02.setBackgroundResource(LotteryTypeUtil.getBallBG(betLotteryLogDetailsEntity
//                            .getLottery_code().getNum2_color_id()));
//                    numberText02.setTextColor(0xffff2f1e);
                    numberText02.setBackgroundResource(LotteryTypeUtil.getPureBallBG(betLotteryLogDetailsEntity
                            .getLottery_code().getNum2_color_id()));
                    numberText03.setText(betLotteryLogDetailsEntity.getLottery_code().getNum3() + "");
//                    numberText03.setBackgroundResource(LotteryTypeUtil.getBallBG(betLotteryLogDetailsEntity
//                            .getLottery_code().getNum3_color_id()));
//                    numberText03.setTextColor(0xffff2f1e);
                    numberText03.setBackgroundResource(LotteryTypeUtil.getPureBallBG(betLotteryLogDetailsEntity
                            .getLottery_code().getNum3_color_id()));
                    numberText04.setText(betLotteryLogDetailsEntity.getLottery_code().getNum4() + "");
//                    numberText04.setBackgroundResource(LotteryTypeUtil.getBallBG(betLotteryLogDetailsEntity
//                            .getLottery_code().getNum4_color_id()));
//                    numberText04.setTextColor(0xffff2f1e);
                    numberText04.setBackgroundResource(LotteryTypeUtil.getPureBallBG(betLotteryLogDetailsEntity
                            .getLottery_code().getNum4_color_id()));
                    numberText05.setText(betLotteryLogDetailsEntity.getLottery_code().getNum5() + "");
//                    numberText05.setBackgroundResource(LotteryTypeUtil.getBallBG(betLotteryLogDetailsEntity
//                            .getLottery_code().getNum5_color_id()));
//                    numberText05.setTextColor(0xffff2f1e);
                    numberText05.setBackgroundResource(LotteryTypeUtil.getPureBallBG(betLotteryLogDetailsEntity
                            .getLottery_code().getNum5_color_id()));
                    numberText06.setText(betLotteryLogDetailsEntity.getLottery_code().getNum6() + "");
//                    numberText06.setBackgroundResource(LotteryTypeUtil.getBallBG(betLotteryLogDetailsEntity
//                            .getLottery_code().getNum6_color_id()));
//                    numberText06.setTextColor(0xffff2f1e);
                    numberText06.setBackgroundResource(LotteryTypeUtil.getPureBallBG(betLotteryLogDetailsEntity
                            .getLottery_code().getNum6_color_id()));
                    numberText07.setText(betLotteryLogDetailsEntity.getLottery_code().getNum_s() + "");
//                    numberText07.setBackgroundResource(LotteryTypeUtil.getBallBG(betLotteryLogDetailsEntity
//                            .getLottery_code().getNum_s_color_id()));
//                    numberText07.setTextColor(0xffff2f1e);
                    numberText07.setBackgroundResource(LotteryTypeUtil.getPureBallBG(betLotteryLogDetailsEntity
                            .getLottery_code().getNum_s_color_id()));
                    animNumberText01.setText(betLotteryLogDetailsEntity.getLottery_code().getNum1_zodiac());
                    animNumberText02.setText(betLotteryLogDetailsEntity.getLottery_code().getNum2_zodiac());
                    animNumberText03.setText(betLotteryLogDetailsEntity.getLottery_code().getNum3_zodiac());
                    animNumberText04.setText(betLotteryLogDetailsEntity.getLottery_code().getNum4_zodiac());
                    animNumberText05.setText(betLotteryLogDetailsEntity.getLottery_code().getNum5_zodiac());
                    animNumberText06.setText(betLotteryLogDetailsEntity.getLottery_code().getNum6_zodiac());
                    animNumberText07.setText(betLotteryLogDetailsEntity.getLottery_code().getNum_s_zodiac());
                } else {
                    numberShowLinear.setVisibility(View.GONE);
                    betStateText.setVisibility(View.VISIBLE);
                    betStateText.setText("待开奖");
                }
            } else if (betLotteryLogDetailsEntity.getSpecies_id() == 8) {//北京PK拾
                headerView = getLayoutInflater().inflate(R.layout.item_head_betlotterylog_other, null);
                lotterySnText = headerView.findViewById(R.id.lottery_sn);
                allMoneyText = headerView.findViewById(R.id.all_money);
                allNumText = headerView.findViewById(R.id.all_num);
                backMoneyText = headerView.findViewById(R.id.back_money);
                betTimeText = headerView.findViewById(R.id.bet_time);
                butCopy = headerView.findViewById(R.id.but_copy);
                betStateText = headerView.findViewById(R.id.bet_state);
                numberShowLinear = headerView.findViewById(R.id.number_linear);

                numberText01 = headerView.findViewById(R.id.number01);
                numberText02 = headerView.findViewById(R.id.number02);
                numberText03 = headerView.findViewById(R.id.number03);
                numberText04 = headerView.findViewById(R.id.number04);
                numberText05 = headerView.findViewById(R.id.number05);
                numberText06 = headerView.findViewById(R.id.number06);
                numberText07 = headerView.findViewById(R.id.number07);
                numberText08 = headerView.findViewById(R.id.number08);
                numberText09 = headerView.findViewById(R.id.number09);
                numberText10 = headerView.findViewById(R.id.number10);
                butCopy.setOnClickListener(v -> {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(lotterySnText.getText());
                    Toast.makeText(LiuHeApplication.context, "已复制", Toast.LENGTH_SHORT).show();
                });
                baseQuickAdapter.addHeaderView(headerView);
                lotterySnText.setText(betLotteryLogDetailsEntity.getOrder_sn());
                allMoneyText.setText(betLotteryLogDetailsEntity.getLottery_amount() + "元");
                allNumText.setText(betLotteryLogDetailsEntity.getLottery_num() + "注");
                backMoneyText.setText(betLotteryLogDetailsEntity.getLottery_rebates_price() + "元");
                betTimeText.setText(betLotteryLogDetailsEntity.getAdd_time());
                if (betLotteryLogDetailsEntity.getLottery_code() != null) {
                    numberShowLinear.setVisibility(View.VISIBLE);
                    betStateText.setVisibility(View.GONE);
                    String[] strs = betLotteryLogDetailsEntity.getLottery_code().getNumber().split("\\|");
                    numberText01.setText(strs[0]);
                    numberText01.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[0]));
                    numberText02.setText(strs[1]);
                    numberText02.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[1]));
                    numberText03.setText(strs[2]);
                    numberText03.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[2]));
                    numberText04.setText(strs[3]);
                    numberText04.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[3]));
                    numberText05.setText(strs[4]);
                    numberText05.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[4]));
                    numberText06.setText(strs[5]);
                    numberText06.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[5]));
                    numberText07.setText(strs[6]);
                    numberText07.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[6]));
                    numberText08.setText(strs[7]);
                    numberText08.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[7]));
                    numberText09.setText(strs[8]);
                    numberText09.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[8]));
                    numberText10.setText(strs[9]);
                    numberText10.setBackgroundResource(LotteryTypeUtil.getBallBG(strs[9]));
                } else {
                    numberShowLinear.setVisibility(View.GONE);
                    betStateText.setVisibility(View.VISIBLE);
                    betStateText.setText("待开奖");
                }
            } else if (betLotteryLogDetailsEntity.getSpecies_id() == 9) {//时时彩
                headerView = getLayoutInflater().inflate(R.layout.item_head_betlotterylog_ssc, null);
                lotterySnText = headerView.findViewById(R.id.lottery_sn);
                allMoneyText = headerView.findViewById(R.id.all_money);
                allNumText = headerView.findViewById(R.id.all_num);
                backMoneyText = headerView.findViewById(R.id.back_money);
                betTimeText = headerView.findViewById(R.id.bet_time);
                butCopy = headerView.findViewById(R.id.but_copy);
                betStateText = headerView.findViewById(R.id.bet_state);
                numberShowLinear = headerView.findViewById(R.id.number_linear);
                numberText01 = headerView.findViewById(R.id.number01);
                numberText02 = headerView.findViewById(R.id.number02);
                numberText03 = headerView.findViewById(R.id.number03);
                numberText04 = headerView.findViewById(R.id.number04);
                numberText05 = headerView.findViewById(R.id.number05);
                butCopy.setOnClickListener(v -> {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(lotterySnText.getText());
                    Toast.makeText(LiuHeApplication.context, "已复制", Toast.LENGTH_SHORT).show();
                });
                baseQuickAdapter.addHeaderView(headerView);
                lotterySnText.setText(betLotteryLogDetailsEntity.getOrder_sn());
                allMoneyText.setText(betLotteryLogDetailsEntity.getLottery_amount() + "元");
                allNumText.setText(betLotteryLogDetailsEntity.getLottery_num() + "注");
                backMoneyText.setText(betLotteryLogDetailsEntity.getLottery_rebates_price() + "元");
                betTimeText.setText(betLotteryLogDetailsEntity.getAdd_time());
                if (betLotteryLogDetailsEntity.getLottery_code() != null) {
                    numberShowLinear.setVisibility(View.VISIBLE);
                    betStateText.setVisibility(View.GONE);
                    String[] strs = betLotteryLogDetailsEntity.getLottery_code().getNumber().split("\\|");
                    //分割显示数据    颜色背景固定
                    numberText01.setText(strs[0]);
                    numberText01.setBackgroundResource(LotteryTypeUtil.getPureBallBG(8));
//                    numberText01.setBackgroundResource(R.drawable.lottery_color_blue);
                    numberText02.setText(strs[1]);
//                    numberText02.setBackgroundResource(R.drawable.lottery_color_blue);
                    numberText02.setBackgroundResource(LotteryTypeUtil.getPureBallBG(8));
                    numberText03.setText(strs[2]);
//                    numberText03.setBackgroundResource(R.drawable.lottery_color_blue);
                    numberText03.setBackgroundResource(LotteryTypeUtil.getPureBallBG(8));
                    numberText04.setText(strs[3]);
//                    numberText04.setBackgroundResource(R.drawable.lottery_color_blue);
                    numberText04.setBackgroundResource(LotteryTypeUtil.getPureBallBG(8));
                    numberText05.setText(strs[4]);
//                    numberText05.setBackgroundResource(R.drawable.lottery_color_blue);
                    numberText05.setBackgroundResource(LotteryTypeUtil.getPureBallBG(8));
                } else {
                    numberShowLinear.setVisibility(View.GONE);
                    betStateText.setVisibility(View.VISIBLE);
                    betStateText.setText("待开奖");
                }
            }
            baseQuickAdapter.setNewData(betLotteryLogDetailsEntity.getOrder_lottery());
        }
    }
}
