package com.liuheonline.la.ui.infomation;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.InfomationEntity;
import com.liuheonline.la.mvp.presenter.InfomationPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.List;


public class InfomationList extends BaseMVPActivity<BaseView<List<InfomationEntity>>,InfomationPresenter,List<InfomationEntity>> {

    @BindId(R.id.info_recycler)
    private RecyclerView recyclerView;
    private BaseQuickAdapter<InfomationEntity,BaseViewHolder> baseQuickAdapter;

    @Override
    protected void initPresenter() {
        presenter = new InfomationPresenter();
    }

    @Override
    protected void fetchData() {
        presenter.getInfomation(getIntent().getExtras().getInt("infoid"));
    }

    @Override
    protected void initData() {

        baseQuickAdapter = new BaseQuickAdapter<InfomationEntity, BaseViewHolder>(R.layout.item_infomation) {
            @Override
            protected void convert(BaseViewHolder helper, InfomationEntity item) {
                helper.setText(R.id.info_text,item.getTitle());
                //加载图片
                Glide.with(InfomationList.this)
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
                        bundle.putString("url",item.getPic_link());
                        startActivity(InfomationDetails.class,bundle);
                    }
                });
            }
        };
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_info);
    }

    @Override
    protected void initTitle() {

        new DefaultNavigationBar
                .Builder(InfomationList.this)
                .setTitle("资料列表")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(InfomationList.this,3));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(List<InfomationEntity> infomationEntities) {
        baseQuickAdapter.setNewData(infomationEntities);
    }


}
