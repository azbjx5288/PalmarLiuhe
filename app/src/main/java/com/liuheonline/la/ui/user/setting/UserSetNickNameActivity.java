package com.liuheonline.la.ui.user.setting;

import android.widget.EditText;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.UserInfo;
import com.liuheonline.la.mvp.presenter.UpUserInfoPresenter;
import com.liuheonline.la.mvp.presenter.UserInfoPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: aini
 * @date 2018/7/18 20:06
 * @description 修改用户昵称
 */
public class UserSetNickNameActivity extends BaseMVPActivity<BaseView<UserInfo>,UserInfoPresenter,UserInfo>{

    private UserInfo userInfo;

    private UpUserInfoPresenter upUserInfoPresenter;

    @BindId(R.id.user_name)
    private EditText userNameText;

    @Override
    protected void initData() {
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_usersetnickname);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("修改昵称")
                .setRightText("保存")
                .setRightClickListener(v -> {
                   String name = userNameText.getText().toString();
                   if(!name.equals("")){
                       Map<String ,Object> map = new HashMap<>();
                       map.put("nickname",name);
                       upUserInfoPresenter.upUserInfo(userInfo.getId(),map);
                   }else{
                       Toast.makeText(getApplicationContext(),"不能为空哦~",Toast.LENGTH_SHORT).show();
                   }
                }).builder();
    }

    @Override
    protected void initView() {
    }

    @OnClick({R.id.clear_img})
    private void clear(){
        userNameText.setText("");
    }

    @Override
    protected void initPresenter() {
        presenter = new UserInfoPresenter();
        upUserInfoPresenter = new UpUserInfoPresenter();
        upUserInfoPresenter.attachView(new BaseView<Object>() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onLoadFailed(int code, String error) {
                Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void successed(Object s) {
                Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void fetchData() {
        presenter.getUserInfo(SharedperfencesUtil.getInt(this,"userId"));
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void successed(UserInfo userInfo) {
        if(userInfo != null){
            this.userInfo = userInfo;
            userNameText.setText(userInfo.getNickname());
        }
    }

}
