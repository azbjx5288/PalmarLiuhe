package com.liuheonline.la.ui.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.InfomationClassEntity;
import com.liuheonline.la.mvp.presenter.InfomationClassPresenter;
import com.liuheonline.la.ui.base.BaseMvpFragment;
import com.liuheonline.la.ui.infomation.InfomationList;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.List;

public class InfoFragment extends BaseMvpFragment<BaseView<List<InfomationClassEntity>>,InfomationClassPresenter,List<InfomationClassEntity>> {
    @BindId(R.id.info_recycler)
    private RecyclerView recyclerView;
    private BaseQuickAdapter<InfomationClassEntity,BaseViewHolder> baseQuickAdapter;
    @Override
    protected void initPresenter() {
        presenter = new InfomationClassPresenter();
    }

    @Override
    protected void initData() {
        baseQuickAdapter = new BaseQuickAdapter<InfomationClassEntity, BaseViewHolder>(R.layout.item_infomation) {
            @Override
            protected void convert(BaseViewHolder helper, InfomationClassEntity item) {
                helper.setText(R.id.info_text,item.getTitle());
                //加载图片
                Glide.with(getContext())
                        .load(item.getPic_link())
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.jianzaizhong) //加载中图片
                                .error(R.mipmap.jiazaishibai) //加载失败图片
                                .fallback(R.mipmap.jiazaishibai) //url为空图片
                                .centerCrop() // 填充方式
                                .priority(Priority.HIGH) //优先级
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                        .into((ImageView) helper.getView(R.id.info_img));
                helper.setOnClickListener(R.id.info_root, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.w("helper",item.getId()+"");
                        Bundle bundle = new Bundle();
                        bundle.putInt("infoid",item.getId());
                        startActivity(InfomationList.class,bundle);
                    }
                });
            }
        };
    }

    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.setHasFixedSize(true);


    }

    @Override
    protected void initTitle(View view) {
        new DefaultNavigationBar
                .Builder(getContext(), (ViewGroup) view)
                .setTitle("资料类型")
                .setLeftIconVisibility(true)
                .builder();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_info;
    }

    @Override
    protected void fetchData() {
        presenter.getInfomationCLass();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(List<InfomationClassEntity> infomationClassEntities) {
        baseQuickAdapter.setNewData(infomationClassEntities);
    }

}
