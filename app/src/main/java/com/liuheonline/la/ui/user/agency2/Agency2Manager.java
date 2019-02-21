package com.liuheonline.la.ui.user.agency2;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.AgentListEntity;
import com.liuheonline.la.mvp.presenter.AgentListPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.utils.CashTypeUtil;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.liuheonline.la.utils.UIHelper;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class Agency2Manager extends BaseMVPActivity<BaseView<List<AgentListEntity>>,AgentListPresenter,List<AgentListEntity>> {
    @BindId(R.id.manager_recycler)
    private RecyclerView recyclerView;
    private BaseQuickAdapter<AgentListEntity,BaseViewHolder> baseQuickAdapter;
    int temptag = -1;
    List<AgentListEntity> agentListEntities1;
    @BindId(R.id.search_text)
    EditText search_text;
    @BindId(R.id.namelinear)
    LinearLayout linearLayout;
    List<String> namelist = new ArrayList<>();
    List<String> list1 = new ArrayList<>();
    @BindId(R.id.notdata_linear)
    LinearLayout noedata;
    @Override
    protected void initPresenter() {
        presenter = new AgentListPresenter();
    }

    @OnClick(R.id.search)
    private void onClick(View view){
        if (search_text.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"搜索内容不能为空",Toast.LENGTH_SHORT).show();
        }else {
            UIHelper.hideInputMethod(view);
            presenter.agentList(SharedperfencesUtil.getInt(getApplicationContext(),"userId"),search_text.getText().toString(),1,100);
        }
    }
    @Override
    protected void fetchData() {
        presenter.agentList(SharedperfencesUtil.getInt(getApplicationContext(),"userId"),"",1,100);
    }

    @Override
    protected void initData() {
        baseQuickAdapter = new BaseQuickAdapter<AgentListEntity, BaseViewHolder>(R.layout.item_agencymanager) {
            @Override
            protected void convert(BaseViewHolder helper, AgentListEntity item) {
                LinearLayout linear2 = helper.getView(R.id.secondlinear);
                TextView xiaji = helper.getView(R.id.xiaji);
                TextView baobiao = helper.getView(R.id.baobiao);
                helper.setText(R.id.yonghuming,item.getUsername());
                helper.setText(R.id.leixing, CashTypeUtil.getAgent(item.getAgent()));
                helper.setText(R.id.fandian,item.getRebates()+"");
                helper.setText(R.id.tuanduirenshu,item.getTeam_membership()+"");
                helper.setText(R.id.tuanduiyue,item.getTeam_balance());
                helper.setText(R.id.gerenyue,item.getAvailable_predeposit());
                helper.setText(R.id.gerentouzhu,item.getLottery_price()+"");
                helper.setText(R.id.gerenchongzhi,item.getRecharge_predeposit()+"");
                helper.setText(R.id.gerentikuan,item.getCash_predeposit()+"");

                if (item.getTeam_membership()<=1){
                    Drawable top = getResources().getDrawable(R.mipmap.xiaji2);
                    // 获取res下的图片
                    top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
                    // 一定要设置setBounds();
                    // 调用setCompoundDrawables(Drawable left, Drawable top,Drawable right, Drawable bottom)方法。(有四个参数，不需要设置的参数传null)
                     xiaji.setCompoundDrawables(null, top, null, null);
                     xiaji.setClickable(false);
                }else {
                    Drawable top = getResources().getDrawable(R.mipmap.xiaji);
                    // 获取res下的图片
                    top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
                    // 一定要设置setBounds();
                    // 调用setCompoundDrawables(Drawable left, Drawable top,Drawable right, Drawable bottom)方法。(有四个参数，不需要设置的参数传null)
                    xiaji.setCompoundDrawables(null, top, null, null);
                    xiaji.setClickable(true);
                }
                if (helper.getLayoutPosition()==temptag){
                    if (linear2.getVisibility()== View.GONE) {
                        linear2.setVisibility(View.VISIBLE);
                    }else {
                        linear2.setVisibility(View.GONE);
                    }
                }else {
                    linear2.setVisibility(View.GONE);
                }
                helper.setOnClickListener(R.id.baobiao, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("userId",item.getId());
                        startActivity(Agency2Table.class,bundle);
                    }
                });
                helper.setOnClickListener(R.id.linearroot, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (temptag==helper.getLayoutPosition()){
                            if (linear2.getVisibility()== View.GONE) {
                                linear2.setVisibility(View.VISIBLE);
                            }else {
                                linear2.setVisibility(View.GONE);
                            }
                        }else {
                            temptag = helper.getLayoutPosition();
                            baseQuickAdapter.setNewData(agentListEntities1);
                        }

                    }
                });
                helper.setOnClickListener(R.id.xiaji, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item.getTeam_membership()>1){
                            temptag = -1;
                            namelist = list1;
                            namelist.add(item.getUsername());
                            showName(namelist);
                            presenter.agentList(item.getId(),"",1,100);
                        }
                    }
                });
                helper.setOnClickListener(R.id.xiugai, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("update",agentListEntities1.get(helper.getLayoutPosition()));
                        startActivity(UpdateAgency.class,bundle);
                    }
                });
            }
        };
    }

    private void showName(List<String> list){
        linearLayout.removeAllViews();
        final boolean[] isclick = {false};
        for (int i=0;i<list.size();i++){
            TextView textView = new TextView(this);
            textView.setBackgroundResource(R.drawable.radis_solid_white);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(10,10,10,10);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10,0,0,0);
            textView.setLayoutParams(layoutParams);
            textView.setText(list.get(i));
            int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list1 = new ArrayList<>();
                    temptag = -1;
                    for (int j = 0; j<= finalI; j++){
                        list1.add(list.get(j));
                    }
                    linearLayout.removeAllViews();
                    isclick[0] = true;
                    showName(list1);
                    if (SharedperfencesUtil.getString(getApplicationContext(),"agencyusername").equals(textView.getText().toString())){//点击了顶层
                        presenter.agentList(SharedperfencesUtil.getInt(getApplicationContext(),"userId"),"",1,100);
                    }else {
                        presenter.agentList(SharedperfencesUtil.getInt(getApplicationContext(),"userId"),textView.getText().toString(),1,100);
                    }

                }
            });
            if (!isclick[0]){
                linearLayout.addView(textView);
            }

        }
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_agencymanager);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("用户管理")
                .setLeftIconVisibility(false)
                .setRightText("+添加用户")
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(Agency2Next.class);
                    }
                })
                .builder();
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.setHasFixedSize(true);
        namelist.add(SharedperfencesUtil.getString(this,"agencyusername"));
        list1 = namelist;
        showName(namelist);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(List<AgentListEntity> agentListEntities) {
        noedata.setVisibility(View.GONE);
        if(agentListEntities != null && agentListEntities.size() > 0){
            agentListEntities1 = agentListEntities;
            baseQuickAdapter.setNewData(agentListEntities);
            if(agentListEntities.size()  < 10){
                baseQuickAdapter.loadMoreEnd();
            }
        }else{
            //空数据
            baseQuickAdapter.setNewData(agentListEntities);
            noedata.setVisibility(View.VISIBLE);
        }
    }

}
