package com.liuheonline.la.ui.user.agency2;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.AgencyPriceEntity;
import com.liuheonline.la.mvp.presenter.AgencyPricePresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.CustomDatePicker2;
import com.liuheonline.la.ui.widget.popu.DatesPopu;
import com.liuheonline.la.utils.DateUtil;
import com.liuheonline.la.utils.StringUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Agency2Price extends BaseMVPActivity<BaseView<AgencyPriceEntity>,AgencyPricePresenter,AgencyPriceEntity> {
    @BindId(R.id.pricerecycle)
    private RecyclerView recyclerView;
    private BaseQuickAdapter<AgencyPriceEntity.ListBean,BaseViewHolder> baseQuickAdapter;
    @BindId(R.id.root_yongjin)
    TextView yongjin;
    @BindId(R.id.root_touzhu)
    TextView touzhu;
    @BindId(R.id.date_left)
    TextView date_left;
    @BindId(R.id.date_right)
    TextView date_right;
    CustomDatePicker2 customDatePicker;
    String way = "";
    DatesPopu datesPopu;
    private DefaultNavigationBar defaultNavigationBar;
    long sDate = 0;
    long eDate = 0;
    @BindId(R.id.notdata_linear)
    LinearLayout notdata;
    @Override
    protected void initPresenter() {
        presenter = new AgencyPricePresenter();
    }

    @Override
    protected void fetchData() {
        presenter.priceList("","","");
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
    protected void initData() {
        baseQuickAdapter = new BaseQuickAdapter<AgencyPriceEntity.ListBean, BaseViewHolder>(R.layout.item_price) {
            @Override
            protected void convert(BaseViewHolder helper, AgencyPriceEntity.ListBean item) {
                helper.setText(R.id.qishu, DateUtil.timeStamp2Date2(item.getCreate_time()+"",null));
                helper.setText(R.id.touzhujine,item.getBetting());
                helper.setText(R.id.yongjing,item.getCommission());
                helper.setText(R.id.touzhurenshu,item.getNumber()+"");
               // helper.setText(R.id.zhuangtai,item.getState()==1?"已发":"已发");
                helper.setText(R.id.zhuangtai,"已发");
                helper.setOnClickListener(R.id.root_lin, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id",item.getId());
                        startActivity(Agency2PriceMember.class,bundle);
                    }
                });
            }
        };
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
                    presenter.priceList(needtime,date_right.getText().toString(),"");
                }else {
                    date_right.setText(needtime);
                    presenter.priceList(date_left.getText().toString(),needtime,"");
                }
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(false); // 不显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_agencyprice);
    }

    @Override
    protected void initTitle() {
        initDatePicker();
        defaultNavigationBar =  new DefaultNavigationBar.Builder(this)
                .setTitle("代理佣金")
                .setLeftIconVisibility(false)
                .setRightText("今天")
                .setRightClickListener(view -> {
                    datesPopu.showPopupWindow(R.id.right_text);
                })
                .builder();
    }

    @Override
    protected void initView() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String now = sdf.format(new Date());
        date_left.setText(now);
        date_right.setText(now);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.setHasFixedSize(true);

        datesPopu = new DatesPopu(this, issue -> {
            defaultNavigationBar.setRightText(StringUtil.getDates(issue));
            selectDate(StringUtil.getDates(issue));
            date_left.setText(DateUtil.timeStamp2Date3(sDate+"",null));
            date_right.setText(DateUtil.timeStamp2Date3(eDate+"",null));
            presenter.priceList("","",issue);
        });
    }

    @Override
    public void onLoading() {

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
    public void successed(AgencyPriceEntity agencyPriceEntity) {
        notdata.setVisibility(View.GONE);
        yongjin.setText(agencyPriceEntity.getCommission()+"");
        touzhu.setText(agencyPriceEntity.getBetting()+"");
        baseQuickAdapter.setNewData(agencyPriceEntity.getList());
        if (agencyPriceEntity.getList()==null||agencyPriceEntity.getList().size()==0){
            notdata.setVisibility(View.VISIBLE);
        }
    }

}
