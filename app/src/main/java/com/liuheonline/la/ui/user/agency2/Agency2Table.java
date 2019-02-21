package com.liuheonline.la.ui.user.agency2;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.AgencyTableEntity;
import com.liuheonline.la.mvp.presenter.AgentInfoPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.CircleImageView;
import com.liuheonline.la.ui.widget.CustomDatePicker2;
import com.liuheonline.la.ui.widget.popu.DatesPopu;
import com.liuheonline.la.utils.DateUtil;
import com.liuheonline.la.utils.ImageUrlUtil;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.liuheonline.la.utils.StringUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Agency2Table extends BaseMVPActivity<BaseView<AgencyTableEntity>,AgentInfoPresenter,AgencyTableEntity> {

    @BindId(R.id.yinli)
    TextView yinli;
    @BindId(R.id.youxiao)
    TextView youxiao;
    @BindId(R.id.paijiang)
    TextView paijiang;
    @BindId(R.id.yongjing)
    TextView yongjing;
    @BindId(R.id.chongzhi)
    TextView chongzhi;
    @BindId(R.id.tikuan)
    TextView tikuan;
    @BindId(R.id.fandian)
    TextView fandian;
    @BindId(R.id.youhui)
    TextView youhui;

    @BindId(R.id.userheadimg)
    CircleImageView userheadimg;
    @BindId(R.id.usre_name)
    TextView username;
    @BindId(R.id.user_price)
    TextView user_price;
    @BindId(R.id.user_fandian)
    TextView user_fandian;

    @BindId(R.id.date_left)
    TextView date_left;
    @BindId(R.id.date_right)
    TextView date_right;
    CustomDatePicker2 customDatePicker;
    int userId = 0;
    String way = "";
    DatesPopu datesPopu;
    private DefaultNavigationBar defaultNavigationBar;
    long sDate = 0;
    long eDate = 0;
    @Override
    protected void initPresenter() {
        presenter = new AgentInfoPresenter();
    }

    @Override
    protected void fetchData() {
        presenter.agentInfo(userId,"","","");
    }

    @Override
    protected void initData() {

    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        customDatePicker = new CustomDatePicker2(this, new CustomDatePicker2.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                String needtime = time.split(" ")[0];
                if (way.equals("left")){
                    date_left.setText(needtime);
                    presenter.agentInfo(userId,needtime,date_right.getText().toString(),"");
                }else {
                    date_right.setText(needtime);
                    presenter.agentInfo(userId,date_left.getText().toString(),needtime,"");
                }
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(false); // 不显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动
    }

    @OnClick({R.id.date_left,R.id.date_right})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.date_left:
                way = "left";
                customDatePicker.show();
                break;
            case R.id.date_right:
                way = "right";
                customDatePicker.show();
                break;
        }
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_agencytable);
    }

    @Override
    protected void initTitle() {
        initDatePicker();
        defaultNavigationBar =   new DefaultNavigationBar.Builder(this)
                .setTitle("团队报表")
                .setLeftIconVisibility(false)
                .setRightText("今天")
                .setRightClickListener(view -> {
                    datesPopu.showPopupWindow(R.id.right_text);
                })
                .builder();
    }

    @Override
    protected void initView() {
        userId = getIntent().getExtras().getInt("userId");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String now = sdf.format(new Date());
        date_left.setText(now);
        date_right.setText(now);
        datesPopu = new DatesPopu(this, issue -> {
            defaultNavigationBar.setRightText(StringUtil.getDates(issue));
            selectDate(StringUtil.getDates(issue));
            date_left.setText(DateUtil.timeStamp2Date3(sDate+"",null));
            date_right.setText(DateUtil.timeStamp2Date3(eDate+"",null));
            presenter.agentInfo(userId,"","",issue);
        });
    }

    private void selectDate(String date){
        long nDate = new Date().getTime();
        switch (date){
            case "今天":
                sDate = nDate;
                eDate = nDate;
                break;
            case "昨天":
                sDate=nDate-(60*60*24*1000);
                eDate=nDate-(60*60*24*1000);
                break;
            case "本周":
                sDate=nDate-((new Date(nDate).getDay()-1)*24*60*60*1000);
                eDate=nDate;
                break;
            case "上周":
                sDate=nDate-(new Date(nDate).getDay()-1)*24*60*60*1000-24*60*60*1000*7;
                eDate=nDate+(7-new Date(nDate).getDay())*24*60*60*1000-24*60*60*1000*7;
                break;
            case "本月":
                Date sDate1=new Date(nDate);
                sDate1.setDate(1);
                sDate=sDate1.getTime();
                eDate=nDate;
                break;
            case "上月":
                Date oDate =new Date(nDate);
                int oMonth=oDate.getMonth();
                if(oMonth!=0){
                    oDate.setMonth(oMonth-1);
                }else{
                    oDate.setYear(oDate.getYear()-1);
                    oDate.setMonth(11);
                }
                oDate.setDate(1);
                sDate=oDate.getTime();
                oDate.setMonth(oDate.getMonth()+1);
                oDate.setDate(0);
                eDate=oDate.getTime();
                break;
            case "今年":
                Date sDate2=new Date(nDate);
                sDate2.setMonth(0);
                sDate2.setDate(1);
                sDate=sDate2.getTime();
                eDate=nDate;
                break;
            case "去年":
                Date oDate1=new Date(nDate);
                oDate1.setYear(oDate1.getYear()-1);
                oDate1.setMonth(0);
                oDate1.setDate(1);
                sDate=oDate1.getTime();
                oDate1.setYear(oDate1.getYear()+1);
                oDate1.setDate(0);
                eDate=oDate1.getTime();
                break;
        }
    }
    @Override
    public void onLoading() {

    }

    @Override
    public void successed(AgencyTableEntity agencyTableEntity) {
        yinli.setText(agencyTableEntity.getProfit()+"元");
        youxiao.setText(agencyTableEntity.getBetting()+"元");
        paijiang.setText(agencyTableEntity.getWinning()+"元");
        yongjing.setText(agencyTableEntity.getCommission()+"元");
        chongzhi.setText(agencyTableEntity.getRecharge()+"元");
        tikuan.setText(agencyTableEntity.getCash()+"元");
        fandian.setText(agencyTableEntity.getRebates()+"元");
        youhui.setText(agencyTableEntity.getPreferential()+"元");
        username.setText(agencyTableEntity.getUsername());
        user_price.setText(agencyTableEntity.getAvailable_predeposit());
        user_fandian.setText(agencyTableEntity.getMember_rebates()==0?"1980":agencyTableEntity.getMember_rebates()+"");

        String headurl = SharedperfencesUtil.getString(getApplicationContext(),"userheadimg");
        Glide.with(this)
                .load(ImageUrlUtil.getImgUrl(headurl,100,100))
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.touxiang) //加载中图片
                        .error(R.mipmap.touxiang) //加载失败图片
                        .fallback(R.mipmap.touxiang) //url为空图片
                        .centerCrop() // 填充方式
                        .priority(Priority.HIGH) //优先级
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(userheadimg);
    }

}
