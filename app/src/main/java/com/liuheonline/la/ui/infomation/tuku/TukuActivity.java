package com.liuheonline.la.ui.infomation.tuku;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liuheonline.la.entity.InfoImagesEntity;
import com.liuheonline.la.mvp.presenter.InfomationImagsListPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.imageengine.GlideImageEngine;
import com.liuheonline.la.utils.DateUtil;
import com.liuheonline.la.utils.FileUtil;
import com.liuheonline.la.utils.SpaceItemDecoration;
import com.liuheonline.la.utils.TimeUtil;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.mylove.loglib.JLog;
import com.pedaily.yc.ycdialoglib.dialog.CustomSelectDialog;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 图库页面
 */
public class TukuActivity extends BaseMVPActivity<BaseView<List<InfoImagesEntity>>, InfomationImagsListPresenter, List<InfoImagesEntity>> {
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
    private Context mContext;
    private Activity mActivity;

    @Override
    protected void initData() {
        baseQuickAdapter = new BaseQuickAdapter<InfoImagesEntity, BaseViewHolder>(R.layout.item_info_text) {
            @Override
            protected void convert(BaseViewHolder helper, InfoImagesEntity item) {
                helper.setText(R.id.tv_item_info_image, item.getTitle());
                String picUrl = null;
                if (type.equals(CAISE)) {
                    picUrl = String.format("%s%s/%s/%s.jpg", item.getUrl(), item.getType().length() == 4 ? TimeUtil.getYear() : DateUtil.getStringDate("yyyy"), item.getQishu(), item.getType());
                } else if (type.equals(XUANJI)) {
                    picUrl = String.format("%s%s/%s/%s.jpg", item.getUrl(), TimeUtil.getYear(), item.getQishu(), item.getType());
                } else if (type.equals(HEIBAI)) {
                    picUrl = String.format("%s%s/%s/%s.jpg", item.getUrl(), DateUtil.getStringDate("yyyy"), item.getQishu(), item.getType());
                } else if (type.equals(QUANNIAN)) {
                    picUrl = item.getUrl();
                }
                String finalPicUrl = picUrl;
                helper.setOnClickListener(R.id.info_root, view -> {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(finalPicUrl);
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("index", helper.getLayoutPosition());
//                        bundle.putStringArrayList("urlList", list);
////                        startActivity(PicturePagerActivity.class, bundle);
//                        startActivity(ImageDetailActivity.class,bundle);
                    MNImageBrowser.with(mContext)
                            //当前位置(必填)
                            .setCurrentPosition(0)
                            //必须-图片加载用户自己去选择
                            .setImageEngine(new GlideImageEngine())
                            //必须（setImageList和setImageUrl二选一，会覆盖）-设置单张图片
//                            .setImageUrl(finalPicUrl)
                            .setImageList(list)
                            .setOnLongClickListener((activity, view1, position, url) -> {
                                List<String> names = new ArrayList<>();
                                names.add("保存图片");
                                CustomSelectDialog dialog = new CustomSelectDialog(activity, R.style.transparentFrameWindowStyle, new CustomSelectDialog.SelectDialogListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
                                        FileUtil.saveFile(url, activity);
                                    }
                                }, names);
                                //判断activity是否finish
                                if (!activity.isFinishing()) {
                                    dialog.show();
                                } else {
                                    dialog.dismiss();
                                }
                            })
                            //设置隐藏指示器
                            .setIndicatorHide(false)
                            //全屏模式
                            .setFullScreenMode(false)
                            //打开动画
                            .setActivityOpenAnime(R.anim.mn_browser_enter_anim)
                            //关闭动画
                            .setActivityExitAnime(R.anim.mn_browser_exit_anim)
                            //显示：传入当前View
                            .show(view);
                });

            }
        };
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_tuku);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("图库")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
        mContext = this;
        mActivity = this;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10, SpaceItemDecoration.GRIDLAYOUT));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.setHasFixedSize(true);
        waitDialog = new AlertDialog.Builder(this)
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
    protected void initPresenter() {
        presenter = new InfomationImagsListPresenter();
    }

    @Override
    protected void fetchData() {
        presenter.getInfomationDetail(type);
    }

    @Override
    public void onLoading() {
        waitDialog.show();
    }

    @Override
    public void successed(List<InfoImagesEntity> infomationClassEntities) {
        JLog.d(infomationClassEntities);
        baseQuickAdapter.setNewData(infomationClassEntities);
        waitDialog.cancel();
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        waitDialog.cancel();
    }
}
