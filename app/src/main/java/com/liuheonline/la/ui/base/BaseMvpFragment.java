package com.liuheonline.la.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.liuheonline.la.ui.user.login.LoginActivity;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.base.BaseFragment;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.util.ActivityManager;


/**
 * Fragment 声明周期 onAttach()——>onCreate()——>onCreateView()——>onActivityCreated()
 */
public abstract class BaseMvpFragment<V extends BaseView<T>,P extends BasePresenter<V>,T> extends BaseFragment implements BaseView<T>{
    protected P presenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        if(presenter != null){
            presenter.attachView((V) this);
        }
    }
    protected abstract void initPresenter();

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter != null){
            presenter.detachView();
        }
    }

    /**
     * @description 数据返回error统一处理
     * @param code 状态码 ErrorActivity 错误信息
     * @return void
     */
    @Override
    public void onLoadFailed(int code, String error) {
        if(getContext() != null){
            if(code == 401){
                SharedperfencesUtil.setInt(getContext(),"userId",0);
                ActivityManager.getActivityManager().popAllActivity();
                getActivity().finish();
                Bundle bundle = new Bundle();
                bundle.putBoolean("isLogin",true);
                startActivity(LoginActivity.class,bundle);
            }
            Toast.makeText(getContext().getApplicationContext(),error,Toast.LENGTH_LONG).show();
        }

    }
}