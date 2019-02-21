package com.liuheonline.la.ui.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.InfoImagesEntity;
import com.liuheonline.la.mvp.presenter.InfomationImagsListPresenter;
import com.liuheonline.la.ui.base.BaseMvpFragment;
import com.liuheonline.la.ui.picture.PicturePagerActivity;
import com.liuheonline.la.utils.SpaceItemDecoration;
import com.liuheonline.la.utils.TimeUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.ArrayList;
import java.util.List;

//图片列表页
public class InfoListFragment extends BaseMvpFragment<BaseView<List<InfoImagesEntity>>, InfomationImagsListPresenter, List<InfoImagesEntity>> {
    @BindId(R.id.info_recycler)
    private RecyclerView recyclerView;
    @BindId(R.id.rg)
    private RadioGroup rg;
    private BaseQuickAdapter<InfoImagesEntity, BaseViewHolder> baseQuickAdapter;
    private String CAISE = "photocaise2.json";
    private String XUANJI = "photoxianji2.json";
    private String HEIBAI = "photoheibai2.json";
    private String QUANNIAN = "photoqianlian.json";
    private AlertDialog waitDialog;
    String type = CAISE;

    @Override
    protected void initPresenter() {
        presenter = new InfomationImagsListPresenter();
    }

    @Override
    protected void initData() {
        baseQuickAdapter = new BaseQuickAdapter<InfoImagesEntity, BaseViewHolder>(R.layout.item_info_text) {
            @Override
            protected void convert(BaseViewHolder helper, InfoImagesEntity item) {
                helper.setText(R.id.tv_item_info_image, item.getTitle());
                String picUrl = null;
                if (type.equals(CAISE)) {
                    picUrl = String.format("%s%s/%s/%s.jpg", item.getUrl(), item.getType().length() == 4 ? TimeUtil.getYear() : 2017, item.getQishu(), item.getType());
                } else if (type.equals(XUANJI)) {
                    picUrl = String.format("%s%s/%s/%s.jpg", item.getUrl(), TimeUtil.getYear(), item.getQishu(), item.getType());
                } else if (type.equals(HEIBAI)) {
                    picUrl = String.format("%s%s/%s/%s.jpg", item.getUrl(), 2017, item.getQishu(), item.getType());
                } else if (type.equals(QUANNIAN)) {
                    picUrl = item.getUrl();
                }
//                //加载图片
//                Glide.with(getContext()).load(picUrl).apply(new RequestOptions()
//                        .placeholder(R.mipmap.jianzaizhong) //加载中图片
//                        .error(R.mipmap.jiazaishibai) //加载失败图片
//                        .fallback(R.mipmap.jiazaishibai) //url为空图片
//                        .centerCrop() // 填充方式
//                        .priority(Priority.HIGH) //优先级
//                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
//                        .into((ImageView) helper.getView(R.id.iv_item_info_image));
                String finalPicUrl = picUrl;
                helper.setOnClickListener(R.id.info_root, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<String> list = new ArrayList<>();
                        list.add(finalPicUrl);
                        Bundle bundle = new Bundle();
                        bundle.putInt("index", helper.getLayoutPosition());
                        bundle.putStringArrayList("urlList", list);
                        startActivity(PicturePagerActivity.class, bundle);
                    }
                });

            }
        };
    }

    @Override
    protected void initViews() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10, SpaceItemDecoration.GRIDLAYOUT));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.setHasFixedSize(true);
        waitDialog = new AlertDialog.Builder(getContext())
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint, "提交中……")
                .create();
        rg.setOnCheckedChangeListener((group, checkedId) -> presenter.getInfomationDetail(getdata(checkedId)));
    }

    private String getdata(int checkeId) {
        switch (checkeId) {
            case R.id.rb_cs:
                type = CAISE;
                return CAISE;
            case R.id.rb_xj:
                type = XUANJI;
                return XUANJI;
            case R.id.rb_hb:
                type = HEIBAI;
                return HEIBAI;
            case R.id.rb_qn:
                type = QUANNIAN;
                return QUANNIAN;
        }
        return "";
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
        return R.layout.fragment_info_img_list;
    }

    @Override
    protected void fetchData() {
        presenter.getInfomationDetail(CAISE);
    }

    @Override
    public void onLoading() {
        waitDialog.show();
    }

    @Override
    public void successed(List<InfoImagesEntity> infomationClassEntities) {
        baseQuickAdapter.setNewData(infomationClassEntities);
        waitDialog.cancel();
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        waitDialog.cancel();
    }
}
