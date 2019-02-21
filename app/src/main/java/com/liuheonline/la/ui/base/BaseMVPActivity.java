package com.liuheonline.la.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.user.login.LoginActivity;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.base.BaseActivity;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.permission.PermissionHelper;
import com.yxt.itv.library.util.ActivityManager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * @author aini
 * @Date 2018/6/11
 * @Version 1.0
 * @Description  中间基本activity  V 是view  P 是presenter T 具体实体类
 */
public abstract class BaseMVPActivity<V extends BaseView<T>,P extends BasePresenter<V>,T> extends BaseActivity implements BaseView<T>  {

    public P presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }
    //初始化presenter
    protected abstract void initPresenter() ;
    //获取网路数据
    protected abstract void fetchData();

    /*通过切断订阅者和观察者联系来取消网络请求*/
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    protected void addComposite(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null){
            presenter.attachView((V) this);
        }
        fetchData();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
        if (presenter != null){
            presenter.detachView();
        }
    }
    /**
     * @description 数据请求失败统一处理 可重写
     * @param code 状态码 ErrorActivity 错误信息
     * @return void
     */
    @Override
    public void onLoadFailed(int code, String error) {
        Toast.makeText(LiuHeApplication.context,error,Toast.LENGTH_SHORT).show();
        if(code == 401){
            SharedperfencesUtil.setInt(this,"userId",0);
            ActivityManager.getActivityManager().popAllActivity();
            finish();
            Bundle bundle = new Bundle();
            bundle.putBoolean("isLogin",true);
            startActivity(LoginActivity.class,bundle);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.requestPermissionsResult(this, requestCode, permissions);
    }

}