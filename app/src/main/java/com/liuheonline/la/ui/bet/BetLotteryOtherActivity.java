package com.liuheonline.la.ui.bet;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.LotteryOtherDetailsEntity;
import com.liuheonline.la.entity.LotteryOtherEntity;
import com.liuheonline.la.mvp.presenter.BuyNumberPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.user.login.LoginActivity;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.CheckNet;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: aini
 * @date 2018/7/31 09:55
 * @description 彩票订单页面
 */
public class BetLotteryOtherActivity extends BaseMVPActivity<BaseView<String>,BuyNumberPresenter,String>{

    @BindId(R.id.issue)
    private TextView issueText;

    @BindId(R.id.date)
    private TextView dateText;

    @BindId(R.id.recycler_other)
    private RecyclerView recyclerView;

    @BindId(R.id.pour_all)
    private TextView pourAllText;

    @BindId(R.id.all_money)
    private TextView allMoneyText;

    /*@BindId(R.id.set_money)
    private TextView setMoneyEditText;*/

    private AlertDialog waitDialog;

    private BaseQuickAdapter<LotteryOtherDetailsEntity,BaseViewHolder> baseViewHolderBaseQuickAdapter;

    private LotteryOtherEntity lotteryOtherEntity;

    private List<LotteryOtherDetailsEntity> lotteryOtherDetailsEntityList = new ArrayList<>();

    private AlertDialog editDialog;

    private String nextTime = "";

    private String type = "";

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            nextTime = bundle.getString("nextTime");
            type = bundle.getString("type");
        }
        /*mLotteryOtherDao = DaoSupportFactory.getFactory(getPackageName()).getDao(LotteryOtherEntity.class);
        lotteryOtherEntity = mLotteryOtherDao.querySupport().queryAll().get(0);
        mLotteryOtherDetailsDao = DaoSupportFactory.getFactory(getPackageName()).getDao(LotteryOtherDetailsEntity.class);
        lotteryOtherEntity.setBet_info(mLotteryOtherDetailsDao.querySupport().queryAll());*/

        lotteryOtherEntity = BetLotteryActivity.staticLotteryOtherEntity;
        if(lotteryOtherEntity == null){
            lotteryOtherEntity = BetLottery2Activity.staticLottery2OtherEntity;
            if(lotteryOtherEntity == null){
                lotteryOtherEntity = BetLottery3Activity.staticLottery2OtherEntity;
            }
        }

        switch (type){
            case "连肖连尾":
            case "合肖":
            case "自选不中":
            case "连码":
                LotteryOtherDetailsEntity lotteryOtherDetailsEntity =lotteryOtherEntity.getBet_info().get(0);
                lotteryOtherDetailsEntityList.add(lotteryOtherDetailsEntity);
                break;
            default:
                lotteryOtherDetailsEntityList.addAll(lotteryOtherEntity.getBet_info());
                break;
        }


        //初始化adapter
        baseViewHolderBaseQuickAdapter = new BaseQuickAdapter<LotteryOtherDetailsEntity,
                BaseViewHolder>(R.layout.item_lottery_other,lotteryOtherDetailsEntityList) {
            @Override
            protected void convert(BaseViewHolder helper, LotteryOtherDetailsEntity item) {
                helper.setText(R.id.number,item.getLottery_child_name()+" "+item.getLottery_name())
                        .setText(R.id.odds,item.getOdds()+"");
                TextView moneyEdit = helper.getView(R.id.money_text);
                Button reduce = helper.getView(R.id.reduce);
                Button add = helper.getView(R.id.add);
                reduce.setOnClickListener(v -> {
                    if(item.getLottery_price() > 1){
                        switch (type){
                            case "连肖连尾":
                            case "合肖":
                            case "自选不中":
                            case "连码":
                                upAllMoney(item.getLottery_price()-1);
                                break;
                            default:
                                upMoney(helper.getAdapterPosition(),item.getLottery_price()-1);
                                break;
                        }

                    }
                });

                add.setOnClickListener(v -> {
                    switch (type){
                        case "连肖连尾":
                        case "合肖":
                        case "自选不中":
                        case "连码":
                            upAllMoney(item.getLottery_price()+1);
                            break;
                        default:
                            upMoney(helper.getAdapterPosition(),item.getLottery_price()+1);
                            break;
                    }

                });
                moneyEdit.setText(item.getLottery_price()+"");

                moneyEdit.setOnClickListener(v -> {
                    EditText editText = editDialog.getView(R.id.edit_money);
                    editDialog.setText(R.id.edit_money,item.getLottery_price()+"");
                    editDialog.setOnclickListener(R.id.sure,v1 -> {
                        String moneyStr = editText.getText().toString();
                        if(!moneyStr.equals("")){
                            int moneyInt = Integer.parseInt(moneyStr);
                            if(moneyInt > 0 && moneyInt != item.getLottery_price()){
                                switch (type){
                                    case "连肖连尾":
                                    case "合肖":
                                    case "自选不中":
                                    case "连码":
                                        upAllMoney(moneyInt);
                                        break;
                                    default:
                                        upMoney(helper.getAdapterPosition(),moneyInt);
                                        break;
                                }

                            }
                        }
                        editDialog.cancel();
                    });
                    editDialog.show();
                });

                switch (type){
                    case "连肖连尾":
                    case "合肖":
                    case "自选不中":
                    case "连码":
                        helper.setText(R.id.price,item.getLottery_child_name()+
                                "  "+lotteryOtherEntity.getLottery_num()+"注/"+
                                item.getLottery_price()*lotteryOtherEntity.getLottery_num()+"元");
                        break;
                    default:
                        helper.setText(R.id.price,item.getLottery_child_name()+"  "+"1注/"+item.getLottery_price()+"元");
                        break;
                }

                ImageView imageView = helper.getView(R.id.clear_img);
                imageView.setOnClickListener(v -> {
                    lotteryOtherDetailsEntityList.remove(helper.getAdapterPosition());
                    switch (type){
                        case "连肖连尾":
                        case "合肖":
                        case "自选不中":
                        case "连码":
                            lotteryOtherEntity = null;
                            pourAllText.setText("0");
                            allMoneyText.setText("0");
                            break;
                        default:
                            lotteryOtherEntity.getBet_info().remove(helper.getAdapterPosition());
                            lotteryOtherEntity.setLottery_num(lotteryOtherEntity.getBet_info().size());
                            lotteryOtherEntity.setLottery_amount(lotteryOtherEntity.getBet_info().size()*item.getLottery_price());
                            pourAllText.setText(lotteryOtherEntity.getLottery_num()+"");
                            allMoneyText.setText(lotteryOtherEntity.getLottery_amount()+"");
                            break;
                    }
                    pourAllText.setText(lotteryOtherEntity.getLottery_num()+"");
                    allMoneyText.setText(lotteryOtherEntity.getLottery_amount()+"");
                    baseViewHolderBaseQuickAdapter.notifyDataSetChanged();
                });
            }
        };

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_betlottery_other);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("确认投注")
                .builder();
    }

    @Override
    protected void initView() {

        waitDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint,"提交中……")
                .create();

        editDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_edittext)
                .setOnClickListener(R.id.cancel, v -> {
                    editDialog.cancel();
                }).create();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(baseViewHolderBaseQuickAdapter);

        issueText.setText("第"+lotteryOtherEntity.getBet_periods()+"期");
        dateText.setText(nextTime+"停止售彩");
        pourAllText.setText(lotteryOtherEntity.getLottery_num()+"注");
        allMoneyText.setText(lotteryOtherEntity.getLottery_amount()+"元");
        //setMoneyEditText.setText(lotteryOtherEntity.getBet_info().get(0).getLottery_price()+"");
    }

    //修改所有金额
    private void upAllMoney(int money){
        for(LotteryOtherDetailsEntity lotteryOtherDetailsEntity : lotteryOtherEntity.getBet_info()){
            lotteryOtherDetailsEntity.setLottery_price(money);
        }

        for(LotteryOtherDetailsEntity lotteryOtherDetailsEntity :lotteryOtherDetailsEntityList){
            lotteryOtherDetailsEntity.setLottery_price(money);
        }
        lotteryOtherEntity.setLottery_amount(lotteryOtherEntity.getBet_info().size()*
                lotteryOtherEntity.getBet_info().get(0).getLottery_price());
        baseViewHolderBaseQuickAdapter.notifyDataSetChanged();
        pourAllText.setText(lotteryOtherEntity.getLottery_num()+"注");
        allMoneyText.setText(lotteryOtherEntity.getLottery_amount()+"元");
    }

    //修改单注金额
    private void upMoney(int postion,int money){
        lotteryOtherEntity.getBet_info().get(postion).setLottery_price(money);
        lotteryOtherDetailsEntityList.get(postion).setLottery_price(money);

        int allMonry = 0;
        for(LotteryOtherDetailsEntity lotteryOtherDetailsEntity : lotteryOtherEntity.getBet_info()){
            allMonry += lotteryOtherDetailsEntity.getLottery_price();
        }
        lotteryOtherEntity.setLottery_amount(allMonry);
        baseViewHolderBaseQuickAdapter.notifyDataSetChanged();
        pourAllText.setText(lotteryOtherEntity.getLottery_num()+"注");
        allMoneyText.setText(lotteryOtherEntity.getLottery_amount()+"元");
    }

    @OnClick({R.id.clear_all})
    private void clearAll(View view){
        lotteryOtherEntity = null;
        lotteryOtherDetailsEntityList.clear();
        baseViewHolderBaseQuickAdapter.notifyDataSetChanged();
        pourAllText.setText("0");
        allMoneyText.setText("0");
    }

    @CheckNet(errorMsg = "网络异常")
    @OnClick({R.id.submit})
    private void submit(View view){
        if(SharedperfencesUtil.getInt(this,"userId") != 0 ){
            lotteryOtherEntity.setSpecie_id(SharedperfencesUtil.getInt(this,"checkSid"));
            if (lotteryOtherEntity.getLottery_num()>1000){
                Toast.makeText(getApplicationContext(),"投注的注数过多，请分批投注",Toast.LENGTH_SHORT).show();
            }else {

                for (LotteryOtherDetailsEntity lotteryOtherDetailsEntity :lotteryOtherEntity.getBet_info()){
                    lotteryOtherDetailsEntity.setOdds(lotteryOtherDetailsEntity.getOdds());
                }
                presenter.buyNumber(lotteryOtherEntity);
            }
        }else{
            startActivity(LoginActivity.class);
        }
        /*switch (view.getId()){
            case R.id.submit:
                break;
            *//*case R.id.set_money:
                EditText editText = editDialog.getView(R.id.edit_money);
                editDialog.setText(R.id.edit_money,setMoneyEditText.getText());
                editDialog.setOnclickListener(R.id.sure,v1 -> {
                    String moneyStr = editText.getText().toString();
                    if(!moneyStr.equals("")){
                        int moneyInt = Integer.parseInt(moneyStr);
                        if(moneyInt > 0 && moneyInt != Integer.parseInt(setMoneyEditText.getText().toString())){
                            upAllMoney(moneyInt);
                        }
                    }
                    editDialog.cancel();
                });
                editDialog.show();
                break;*//*
        }*/
    }

    @Override
    protected void initPresenter() {
        presenter = new BuyNumberPresenter();
    }

    @Override
    protected void fetchData() {
    }

    @Override
    public void onLoading() {
        waitDialog.show();
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        waitDialog.cancel();
    }

    @Override
    public void successed(String s) {
        Toast.makeText(LiuHeApplication.context,"下注成功",Toast.LENGTH_SHORT).show();
        waitDialog.cancel();
        finish();
    }

}
