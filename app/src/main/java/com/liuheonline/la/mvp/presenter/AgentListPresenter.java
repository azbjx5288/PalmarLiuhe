package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.AgentListEntity;
import com.liuheonline.la.network.api.IAgencyServer;
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
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class AgentListPresenter extends BasePresenter<BaseView<List<AgentListEntity>>> {

    /**
     * @description 成员列表接口
     * @param
     * @return void
     */
    public void agentList(int uid,String username,int p,int row){
        Map<String,Object> map = new HashMap<>();
        map.put("uid",uid);
        map.put("username",username);
        map.put("p",p);
        map.put("row",row);

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IAgencyServer.class)
                .getAgentList(map)
                .compose(SetThread.<BaseEntity<List<AgentListEntity>>>io_main())
                .subscribe(new BaseConsumer<List<AgentListEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<AgentListEntity>> t) throws Exception {
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
