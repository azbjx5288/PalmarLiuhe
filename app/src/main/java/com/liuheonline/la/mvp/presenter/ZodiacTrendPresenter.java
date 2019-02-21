package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.ZodiacTrendEntity;
import com.liuheonline.la.network.api.ILotteryServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.mylove.loglib.JLog;
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
 * @description 历史开奖presenter
 */
public class ZodiacTrendPresenter extends BasePresenter<BaseView<List<ZodiacTrendEntity>>> {

    /**
     * @param year 年份 p 页码 row页数
     * @return void
     * @description 获取生肖走势
     */
    public void getZodiacTrend(int year, int p, int row, int sid) {
        Map<String, Object> map = new HashMap<>();
        map.put("year", year);
        map.put("p", p);
        map.put("row", row);
        map.put("sid", sid);
        JLog.d("六合sid", sid);
        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(ILotteryServer.class)
                .getZodiacTrend(map)
                .compose(SetThread.<BaseEntity<List<ZodiacTrendEntity>>>io_main())
                .subscribe(new BaseConsumer<List<ZodiacTrendEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<ZodiacTrendEntity>> t) throws Exception {
                        getView().successed(t.getmData());
                    }

                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                }, new BaseErroeConsumer() {
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }
}
