package com.liuheonline.la.ui.user.agency2;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.AgentListEntity;
import com.liuheonline.la.entity.NoddEntity;
import com.liuheonline.la.mvp.presenter.NoddPresenter;
import com.liuheonline.la.mvp.presenter.UpdateMemberPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.popu.AgencyPopu;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.liuheonline.la.utils.UIHelper;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.List;

public class UpdateAgency extends BaseMVPActivity<BaseView<Object>,UpdateMemberPresenter,Object> {
    @BindId(R.id.rb1)
    RadioButton rb1;
    @BindId(R.id.rb2)
    RadioButton rb2;
    @BindId(R.id.name)
    TextView name;
    @BindId(R.id.agency_linear)
    LinearLayout linearLayout;
    @BindId(R.id.agency_select)
    TextView select;
    @BindId(R.id.password)
    TextView passwrod;
    int maxnum = 0;
    List<NoddEntity.ListBean> listBeans;
    NoddEntity.ListBean listBean;
    AgencyPopu agencyPopu;
    NoddPresenter noddPresenter;
    AgentListEntity agentListEntity;
    @Override
    protected void initPresenter() {
        presenter = new UpdateMemberPresenter();
        noddPresenter = new NoddPresenter();
        noddPresenter.attachView(new BaseView<NoddEntity>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {

            }

            @Override
            public void successed(NoddEntity noddEntity) {
                maxnum = noddEntity.getNodd_max();
                listBeans = noddEntity.getList();
            }
        });
        noddPresenter.getNodd();
    }

    @Override
    protected void fetchData() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.submit,R.id.agency_select})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.submit:
                int agent = 2;
                if (rb1.isChecked()){
                    agent = 2;//代理
                }else {
                    agent = 1;//成员
                }
               String strname = name.getText().toString();
                String strselect = select.getText().toString();
                if (strname.trim().length()!=0){//名字不为空
                    if (strselect.trim().length()!=0){//返点不为空
                        presenter.addMember(agentListEntity.getId(),strselect);
                    }else {
                        Toast.makeText(getApplicationContext(),"彩票返点不能为空",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.agency_select://显示选择框
                UIHelper.hideInputMethod(view);
                agencyPopu.showPopupWindow(linearLayout,listBeans);
                break;
        }



    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_updateagency);
    }

    @Override
    protected void initTitle() {
        agentListEntity = (AgentListEntity) getIntent().getExtras().getSerializable("update");
        if (agentListEntity.getAgent()==2){
            rb2.setVisibility(View.GONE);
        }
        name.setText(agentListEntity.getUsername());
        select.setText(agentListEntity.getRebates()+"");
        new DefaultNavigationBar.Builder(this)
                .setTitle("修改信息")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
        agencyPopu = new AgencyPopu(this, new AgencyPopu.OnViewClickListener() {
            @Override
            public void onVeiwCilck(NoddEntity.ListBean listBean1) {
                listBean = listBean1;
                if (listBean1.getNum_id()>=maxnum){
                    select.setText(listBean1.getNum_id()+"");
                }else {
                    Toast.makeText(getApplicationContext(),"赔率不能超过"+maxnum,Toast.LENGTH_SHORT).show();
                }

                agencyPopu.dismiss();
            }
        });
        passwrod.setText("*初始化密码为:"+ SharedperfencesUtil.getString(this,"agent_pwd"));
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successed(Object o) {
        Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
        finish();
    }
}
