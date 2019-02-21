package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.PriceMemberEntity;
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
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Action;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class PriceMemberPresenter extends BasePresenter<BaseView<List<PriceMemberEntity>>> {

    /**
     * @description 代理佣金成员列表接口
     * @param
     * @return void
     */
    public void priceMember(int id){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IAgencyServer.class)
                .getPriceMemberList(map)
                .compose(SetThread.<BaseEntity<List<PriceMemberEntity>>>io_main())
                .subscribe(new BaseConsumer<List<PriceMemberEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<PriceMemberEntity>> t) throws Exception {
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
