package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.network.api.IAgencyServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Action;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class UpdateMemberPresenter extends BasePresenter<BaseView<Object>> {

    /**
     * @description 编辑成员返点
     * @param
     * @return void
     */
    public void addMember(int id,String rebates){
        Map<String,Object> map = new HashMap<>();
        map.put("rebates",rebates);

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IAgencyServer.class)
                .updateMember(id,map)
                .compose(SetThread.<BaseEntity<Object>>io_main())
                .subscribe(new BaseConsumer<Object>() {
                    @Override
                    protected void onSuccees(BaseEntity<Object> t) throws Exception {
                        getView().successed(t.getmData());
                    }
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                }, new BaseErroeConsumer() {                    @Override                    protected void onCodeError(int code, String error) throws Exception {                        getView().onLoadFailed(code, error);                    }                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }
}
