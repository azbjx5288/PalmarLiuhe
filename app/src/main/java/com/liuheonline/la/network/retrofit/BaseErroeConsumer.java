package com.liuheonline.la.network.retrofit;



import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mylove.loglib.JLog;
import com.liuheonline.la.entity.TokenEntity;
import com.liuheonline.la.mvp.presenter.TokenPresenter;
import com.liuheonline.la.ui.ErrorActivity;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.liuheonline.la.utils.SysUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.util.ActivityManager;

import org.json.JSONException;

import java.net.SocketTimeoutException;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import retrofit2.HttpException;


/**
 * @author: aini
 * @date 2018/8/1 16:35
 * @description 针对状态码400以上统一处理
 */
public abstract class BaseErroeConsumer implements Consumer<Throwable> {

    @Override
    public void accept(Throwable throwable) throws Exception {
        if(throwable instanceof HttpException){
            ResponseBody body =  ((HttpException) throwable).response().errorBody();
            if(body != null){
                try {
                    Gson gson = new Gson();
                    BaseEntity baseEntity = gson.fromJson(body.charStream(),BaseEntity.class);
                    if(baseEntity.getmCode() == 403){
                        ActivityManager.getActivityManager().popAllActivity();
                        Intent intent = new Intent(LiuHeApplication.context,ErrorActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        LiuHeApplication.context.startActivity(intent);
                        ActivityManager.getActivityManager()
                                .destoryActivity(ActivityManager.getActivityManager().currentActivity());
                        return;
                    }
                    if (baseEntity.getmCode() == 402){
                        TokenPresenter tokenPresenter = new TokenPresenter();
                        tokenPresenter.attachView(new BaseView<TokenEntity>() {
                            @Override
                            public void onLoading() {

                            }

                            @Override
                            public void onLoadFailed(int code, String error) {

                            }

                            @Override
                            public void successed(TokenEntity tokenEntity) {
                                SharedperfencesUtil.setString(LiuHeApplication.context,"token",tokenEntity.getAccesstoken());
                            }
                        });
                        tokenPresenter.getToken(SysUtil.getMachineImei(LiuHeApplication.context));
                    }
                    onCodeError(baseEntity.getmCode(),baseEntity.getmMessage());
                } catch (Exception IOe) {
                    onCodeError(500,"服务器内部错误");
                    IOe.printStackTrace();
                }
            }else{
                onCodeError(500,"未知异常");
            }
        }else if (throwable instanceof JsonIOException ||throwable instanceof JSONException ||
                throwable instanceof JsonSyntaxException ||throwable instanceof JsonParseException){
            if (throwable instanceof JsonParseException){
                JLog.w(throwable.getMessage());
            }
            onCodeError(400,"数据异常");
        }else if (throwable instanceof SocketTimeoutException){
            onCodeError(400,"连接超时");
        } else{
            //onCodeError(400,"数据异常");
        }
    }

    /**
     * 返回失败
     * @param error
     * @throws Exception
     */
    protected  abstract void onCodeError(int code,String error) throws Exception ;
}
