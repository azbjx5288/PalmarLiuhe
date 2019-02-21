package com.liuheonline.la.ui.main.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.liuheonline.la.entity.LotteryEntity;
import com.liuheonline.la.entity.RedEnvelopeEntity;
import com.liuheonline.la.event.IndividualityEvent;
import com.liuheonline.la.mvp.presenter.LotteryPresenter;
import com.liuheonline.la.mvp.presenter.LotterySidPresenter;
import com.liuheonline.la.mvp.presenter.RedEnvelopeAmountPresenter;
import com.liuheonline.la.ui.adapter.AutoPollAdapter;
import com.liuheonline.la.ui.base.BaseMvpFragment;
import com.liuheonline.la.ui.history.HistoryActivity;
import com.liuheonline.la.ui.main.holder.NetImageLoadHolder;
import com.liuheonline.la.ui.main.notice.NoticeActivity;
import com.liuheonline.la.ui.main.property.LotteryProperty;
import com.liuheonline.la.ui.main.statistics.LotteryStatistics;
import com.liuheonline.la.ui.main.trend.LotteryTrend;
import com.liuheonline.la.ui.main.web.ServiceChat;
import com.liuheonline.la.ui.user.login.LoginActivity;
import com.liuheonline.la.ui.widget.AutoPollRecyclerView;
import com.liuheonline.la.ui.widget.NoticeTextView;
import com.liuheonline.la.utils.Dip2pxUtil;
import com.liuheonline.la.utils.LotteryTypeUtil;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.liuheonline.la.utils.StringUtil;
import com.liuheonline.la.utils.TTSUtils;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IndexFragment extends BaseMvpFragment<BaseView<LotteryEntity>, LotteryPresenter, LotteryEntity>
        implements SwipeRefreshLayout.OnRefreshListener {
    @BindId(R.id.banner)
    private ConvenientBanner convenientBanner;
    @BindId(R.id.index_autorecycle)
    private AutoPollRecyclerView mRecyclerView;
    @BindId(R.id.index_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindId(R.id.index_num1)
    TextView num1;//开奖号
    @BindId(R.id.index_num2)
    TextView num2;
    @BindId(R.id.index_num3)
    TextView num3;
    @BindId(R.id.index_num4)
    TextView num4;
    @BindId(R.id.index_num5)
    TextView num5;
    @BindId(R.id.index_num6)
    TextView num6;
    @BindId(R.id.index_num7)
    TextView num7;
    @BindId(R.id.index_zodiac1)
    TextView zodiac1;//生肖
    @BindId(R.id.index_zodiac2)
    TextView zodiac2;
    @BindId(R.id.index_zodiac3)
    TextView zodiac3;
    @BindId(R.id.index_zodiac4)
    TextView zodiac4;
    @BindId(R.id.index_zodiac5)
    TextView zodiac5;
    @BindId(R.id.index_zodiac6)
    TextView zodiac6;
    @BindId(R.id.index_zodiac7)
    TextView zodiac7;
    @BindId(R.id.index_day1)
    TextView day1;
    @BindId(R.id.index_day2)
    TextView day2;
    @BindId(R.id.index_h1)
    TextView h1;
    @BindId(R.id.index_h2)
    TextView h2;
    @BindId(R.id.index_m1)
    TextView m1;
    @BindId(R.id.index_m2)
    TextView m2;
    @BindId(R.id.index_s1)
    TextView s1;
    @BindId(R.id.index_s2)
    TextView s2;
    @BindId(R.id.index_year)
    TextView year;
    @BindId(R.id.index_nexm1)
    TextView nexm1;
    @BindId(R.id.index_nexm2)
    TextView nexm2;
    @BindId(R.id.index_nexd1)
    TextView nexd1;
    @BindId(R.id.index_nexd2)
    TextView nexd2;

    @BindId(R.id.current_periods)
    private TextView periods;
    @BindId(R.id.index_notice)
    private NoticeTextView noticeTextView;

    //获取最新开奖
    private LotterySidPresenter lotteryPresenter;

    int sid = 0;
    long intperiods = 0;
    CountDownTimer countDownTimer;//倒计时
    int lotteryType = 1;//用于个性设置中显示文字或者图片
    //红包
    private RedEnvelopeAmountPresenter amountPresenter;
    private AlertDialog redEnvelopeDialog;

    @Override
    protected void initPresenter() {
        presenter = new LotteryPresenter();
        lotteryPresenter = new LotterySidPresenter();
        //开奖
        lotteryPresenter.attachView(new BaseView<LotteryEntity>() {
            @Override
            public void onLoading() {
//                Log.d("开奖", "获取中……");
            }

            @Override
            public void onLoadFailed(int code, String error) {
                Log.d("开奖", "获取中失败");
            }

            @Override
            public void successed(LotteryEntity lotteryEntity) {
                swipeRefreshLayout.setRefreshing(false);
                setLottery(lotteryEntity);
            }
        });
        amountPresenter = new RedEnvelopeAmountPresenter();
        amountPresenter.attachView(new BaseView<RedEnvelopeEntity>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {
                Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void successed(RedEnvelopeEntity redEnvelopeEntity) {
                redEnvelopeDialog.show();
                redEnvelopeDialog.setText(R.id.money, redEnvelopeEntity.getRed_packed_amount() + "");
                redEnvelopeDialog.setOnclickListener(R.id.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        redEnvelopeDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    protected void initViews() {
        redEnvelopeDialog = new AlertDialog.Builder(mContext)
                .setContentView(R.layout.dialog_redenvelope)
                .setCancelable(false)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Dip2pxUtil.dip2px(mContext, 600f))
                .create();

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initTitle(View view) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_index;
    }

    @OnClick({R.id.history, R.id.trend, R.id.statistics, R.id.property, R.id.linear_notice, R.id.servicechat, R.id.drag_but})
    private void onClick(View view) {

        Bundle bundle = new Bundle();
        bundle.putInt("lotteryid", sid);

        switch (view.getId()) {
            case R.id.history:
               /* Bundle bundle0 = new Bundle();
                bundle0.putString("fromactivity","history");
                startActivity(ChooseLottery.class,bundle0);*/
                startActivity(HistoryActivity.class, bundle);
                break;
            case R.id.trend:
                /*Bundle bundle = new Bundle();
                bundle.putString("fromactivity","trend");
                startActivity(ChooseLottery.class,bundle);*/
                startActivity(LotteryTrend.class, bundle);
                break;
            case R.id.statistics:
                /*Bundle bundle1 = new Bundle();
                bundle1.putString("fromactivity","statistics");
                startActivity(ChooseLottery.class,bundle1);*/
                //startActivity(LotteryStatistics.class);
                startActivity(LotteryStatistics.class, bundle);
                break;
            case R.id.property:
               /* Bundle bundle2 = new Bundle();
                bundle2.putString("fromactivity","property");
                startActivity(ChooseLottery.class,bundle2);*/
                startActivity(LotteryProperty.class, bundle);
                break;
            case R.id.linear_notice:
                startActivity(NoticeActivity.class);
                break;

            case R.id.servicechat:
                startActivity(ServiceChat.class);
                break;
            case R.id.drag_but:
                int userId = SharedperfencesUtil.getInt(mContext, "userId");
                if (userId != 0) {
                    amountPresenter.getAmount();
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void fetchData() {
        String token = SharedperfencesUtil.getString(mContext, "token");
        swipeRefreshLayout.setRefreshing(true);
        presenter.getLottery();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(IndividualityEvent Individuality) {
        presenter.getLottery();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void successed(LotteryEntity lotteryEntity) {

        if (lotteryEntity != null) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            setValue(lotteryEntity);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    private void setValue(LotteryEntity lotteryEntity) {
        LotteryEntity.LotteryBean lotteryBean = lotteryEntity.getLottery();
        int sp = SharedperfencesUtil.getInt(getContext(), "zodiacid");
        if (sp < 1) {
            sp = 1;
        }

        //彩种id
        sid = lotteryBean.getSid();

        //设置公告
        noticeTextView.setText(StringUtil.translation(lotteryEntity.getAnnouncement() + lotteryEntity.getAnnouncement() + lotteryEntity.getAnnouncement()));
//        String str = lotteryEntity.getAnnouncement() + lotteryEntity.getAnnouncement() + lotteryEntity.getAnnouncement();
//        if (TextUtils.isEmpty(str)) {
//            str = "";
//        }
//        noticeTextView.setText(Html.fromHtml(StringUtil.translation(str)));
//        noticeTextView.setMovementMethod(LinkMovementMethod.getInstance());
        //设置banner
        List<String> imgList = new ArrayList<>();
        for (int i = 0; i < lotteryEntity.getSlide().size(); i++) {
            imgList.add(lotteryEntity.getSlide().get(i).getPic_link());
        }
        convenientBanner.startTurning(3000);
        convenientBanner.setPointViewVisible(true);
        convenientBanner.setManualPageable(true);
        convenientBanner.setPageIndicator(new int[]{R.mipmap.ddian2, R.mipmap.dian1});
        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        convenientBanner.setPages((CBViewHolderCreator<NetImageLoadHolder>) NetImageLoadHolder::new, imgList);
        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String url = lotteryEntity.getSlide().get(position).getUrl();
                Log.w("theurl", url);
                if (!TextUtils.isEmpty(url)) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        //开奖信息
        setLottery(lotteryEntity);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    private void setLottery(LotteryEntity lotteryEntity) {
        //设置中奖信息
        int sp = SharedperfencesUtil.getInt(mContext, "zodiacid");
        if (sp < 1) {
            sp = 1;
        }
        LotteryEntity.LotteryBean lotteryBean = lotteryEntity.getLottery();
        List<LotteryEntity.WinningDataBean> list = lotteryEntity.getWinning_data();
        if (list != null && list.size() != 0) {
            AutoPollAdapter adapter = new AutoPollAdapter(getContext(), list);
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            manager.setSmoothScrollbarEnabled(false);
            mRecyclerView.setLayoutManager(manager);
            // mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST));//分割线
            mRecyclerView.setAdapter(adapter);
            if (true) //保证itemCount的总个数宽度超过屏幕宽度->自己处理
                mRecyclerView.start();
        }
        //设置期数
        periods.setText("香港六合彩第" + (lotteryBean.getPeriods()) + "期开奖结果");

        //设置开奖号码
        num1.setText(lotteryBean.getNum1() + "");
        num2.setText(lotteryBean.getNum2() + "");
        num3.setText(lotteryBean.getNum3() + "");
        num4.setText(lotteryBean.getNum4() + "");
        num5.setText(lotteryBean.getNum5() + "");
        num6.setText(lotteryBean.getNum6() + "");
        num7.setText(lotteryBean.getNum_s() + "");

        int voiceid = SharedperfencesUtil.getInt(getContext(), "voiceid");
        if (voiceid < 1) {
            voiceid = 2;
        }
        if (voiceid == 1) {//语音播报
            Log.w("app_voice", lotteryEntity.getApp_voice());
            if (intperiods == 0 || intperiods != lotteryBean.getPeriods())//第一次进来或者期数发生变化
                TTSUtils.getInstance().speak(lotteryEntity.getApp_voice() + lotteryBean.getNum1() + "," + lotteryBean.getNum2() + "," + lotteryBean.getNum3() + ","
                        + lotteryBean.getNum4() + "," + lotteryBean.getNum5() + "," + lotteryBean.getNum6() + "," + lotteryBean.getNum_s());
            intperiods = lotteryBean.getPeriods();
        }


        //设置球的背景图片
        num1.setBackgroundResource(LotteryTypeUtil.getBallBG(lotteryBean.getNum1_color_id()));
        num2.setBackgroundResource(LotteryTypeUtil.getBallBG(lotteryBean.getNum2_color_id()));
        num3.setBackgroundResource(LotteryTypeUtil.getBallBG(lotteryBean.getNum3_color_id()));
        num4.setBackgroundResource(LotteryTypeUtil.getBallBG(lotteryBean.getNum4_color_id()));
        num5.setBackgroundResource(LotteryTypeUtil.getBallBG(lotteryBean.getNum5_color_id()));
        num6.setBackgroundResource(LotteryTypeUtil.getBallBG(lotteryBean.getNum6_color_id()));
        num7.setBackgroundResource(LotteryTypeUtil.getBallBG(lotteryBean.getNum_s_color_id()));
        if (sp == 2) {
            //设置生肖背景图+
            zodiac1.setBackgroundColor(Color.parseColor("#00000000"));
            zodiac2.setBackgroundColor(Color.parseColor("#00000000"));
            zodiac3.setBackgroundColor(Color.parseColor("#00000000"));
            zodiac4.setBackgroundColor(Color.parseColor("#00000000"));
            zodiac5.setBackgroundColor(Color.parseColor("#00000000"));
            zodiac6.setBackgroundColor(Color.parseColor("#00000000"));
            zodiac7.setBackgroundColor(Color.parseColor("#00000000"));
            zodiac1.setText(lotteryBean.getNum1_zodiac());
            zodiac2.setText(lotteryBean.getNum2_zodiac());
            zodiac3.setText(lotteryBean.getNum3_zodiac());
            zodiac4.setText(lotteryBean.getNum4_zodiac());
            zodiac5.setText(lotteryBean.getNum5_zodiac());
            zodiac6.setText(lotteryBean.getNum6_zodiac());
            zodiac7.setText(lotteryBean.getNum_s_zodiac());

        } else {
            //设置生肖背景图
            zodiac1.setBackgroundResource(LotteryTypeUtil.getZodiac(lotteryBean.getNum1_zodiac()));
            zodiac2.setBackgroundResource(LotteryTypeUtil.getZodiac(lotteryBean.getNum2_zodiac()));
            zodiac3.setBackgroundResource(LotteryTypeUtil.getZodiac(lotteryBean.getNum3_zodiac()));
            zodiac4.setBackgroundResource(LotteryTypeUtil.getZodiac(lotteryBean.getNum4_zodiac()));
            zodiac5.setBackgroundResource(LotteryTypeUtil.getZodiac(lotteryBean.getNum5_zodiac()));
            zodiac6.setBackgroundResource(LotteryTypeUtil.getZodiac(lotteryBean.getNum6_zodiac()));
            zodiac7.setBackgroundResource(LotteryTypeUtil.getZodiac(lotteryBean.getNum_s_zodiac()));
            zodiac1.setText("");
            zodiac2.setText("");
            zodiac3.setText("");
            zodiac4.setText("");
            zodiac5.setText("");
            zodiac6.setText("");
            zodiac7.setText("");
        }

        //设置下期开奖时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] nexttime = sdf.format(new Date(lotteryBean.getNext_time() * 1000)).split("-");
        year.setText(nexttime[0]);
        int nm1 = Integer.parseInt(nexttime[1]) / 10;
        int nm2 = Integer.parseInt(nexttime[1]) % 10;
        nexm1.setText(Integer.parseInt(nexttime[1]) / 10 + "");
        nexm2.setText(Integer.parseInt(nexttime[1]) % 10 + "");
        nexd1.setText(Integer.parseInt(nexttime[2]) / 10 + "");
        nexd2.setText(Integer.parseInt(nexttime[2]) % 10 + "");

        //设置倒计时
        int header_time = Integer.parseInt(lotteryBean.getHeader_time());
        //String strTimeStamp = String.valueOf(sdf.parse(lotteryBean.getNext_time().toString()).getTime()/1000);
        long intTimeStamp = lotteryBean.getNext_time();
        long totaltime = intTimeStamp - header_time;
        if (totaltime > 0) {
            countDownTimer = new CountDownTimer(totaltime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long day = millisUntilFinished / 1000 / 60 / 60 / 24;
                    long h = millisUntilFinished / 1000 / 60 / 60 % 24;
                    long m = millisUntilFinished / 1000 / 60 % 60;
                    long s = millisUntilFinished / 1000 % 60;

                    day1.setText(day / 10 + "");
                    day2.setText(day % 10 + "");
                    h1.setText(h / 10 + "");
                    h2.setText(h % 10 + "");
                    m1.setText(m / 10 + "");
                    m2.setText(m % 10 + "");
                    s1.setText(s / 10 + "");
                    s2.setText(s % 10 + "");
                }

                @Override
                public void onFinish() {
                    presenter.getLottery();
                }
            };
            countDownTimer.start();
        } else {
            //设置期数
            periods.setText("香港六合彩第" + (lotteryBean.getPeriods() + 1) + "期开奖结果");
            num1.setText("开");
            num2.setText("奖");
            num3.setText("中");
            num4.setText("-");
            num5.setText("-");
            num6.setText("-");
            num7.setText("-");
            day1.setText("0");
            day2.setText("0");
            h1.setText("0");
            h2.setText("0");
            m1.setText("0");
            m2.setText("0");
            s1.setText("0");
            s2.setText("0");
            //继续获取数据
            reGetData();
        }
    }

    private void reGetData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    //presenter.getLottery();
                    lotteryPresenter.getSidLottery(7);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null)
            countDownTimer.cancel();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.getLottery();
    }


}
