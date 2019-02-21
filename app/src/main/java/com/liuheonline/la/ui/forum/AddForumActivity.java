package com.liuheonline.la.ui.forum;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.ImageEntity;
import com.liuheonline.la.mvp.presenter.AddForumPresenter;
import com.liuheonline.la.mvp.presenter.PostFilesPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.forum.holder.GlideImageLoader;
import com.liuheonline.la.ui.widget.popu.SelectPhotoMenuPopup;
import com.liuheonline.la.utils.PreviewImageView;
import com.liuheonline.la.utils.UIHelper;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * @author: aini
 * @date 2018/7/21 18:35
 * @description 发表朋友圈
 */
public class AddForumActivity extends BaseMVPActivity<BaseView<List<ImageEntity>>, PostFilesPresenter, List<ImageEntity>> {

    @BindId(R.id.publish_input)
    private EditText publishInput;

    @BindId(R.id.preview_image_content)
    private PreviewImageView<String> previewImageView;

    //图片路径全局
    private List<String> path = new ArrayList<>();

    //图片选择器
    private GalleryConfig galleryConfig;

    //图片选择器回调
    private IHandlerCallBack iHandlerCallBack;

    //等待窗口
    private AlertDialog wiatDialog;

    //发表帖子接口
    private AddForumPresenter addForumPresenter;

    @Override
    protected void initData() {
        initGallery();
        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider(getPackageName())   // provider(必填)
                .pathList(path)                         // 记录已选的图片
                .multiSelect(true, 9)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();

    }


    @Override
    protected void setContentView() {
        setContentView(R.layout.ctivity_addforum);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(true)
                .setLeftText("取消")
                .setTitle("发贴")
                .setRightText("发表")
                .setRightClickListener(v -> {
                    //发表帖子
                    addForum();
                }).setLeftTextClickListener(v -> finish()).builder();
    }

    @Override
    protected void initView() {

        wiatDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint, "提交中……")
                .create();

        previewImageView.setOnAddPhotoClickListener(v -> {
            UIHelper.hideInputMethod(previewImageView);
            new SelectPhotoMenuPopup(AddForumActivity.this)
                    .setOnSelectPhotoMenuClickListener(new SelectPhotoMenuPopup.OnSelectPhotoMenuClickListener() {
                        @Override
                        public void onShootClick() {
                            GalleryPick.getInstance().setGalleryConfig(galleryConfig).openCamera(AddForumActivity.this);
                        }

                        @Override
                        public void onAlbumClick() {
                            galleryConfig.getBuilder().isOpenCamera(false).build();
                            GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(AddForumActivity.this);
                        }
                    }).showPopupWindow();
        });

    }

    private void initGallery() {
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List<String> photoList) {
                path.addAll(photoList);
                previewImageView.setDatas(path, (pos, data, imageView) -> {
                    Glide.with(AddForumActivity.this)
                            .load(data)
                            .into(imageView);
                });
            }

            @Override
            public void onCancel() {
                Log.i("AddForumActivity", "onCancel: 取消");
            }

            @Override
            public void onFinish() {
                Log.i("AddForumActivity", "onFinish: 结束");
            }

            @Override
            public void onError() {
                Log.i("AddForumActivity", "onError: 出错");
            }
        };

    }

    @Override
    protected void initPresenter() {
        presenter = new PostFilesPresenter();
        addForumPresenter = new AddForumPresenter();
        addForumPresenter.attachView(new BaseView<Integer>() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onLoadFailed(int code, String error) {
                Toast.makeText(LiuHeApplication.context, "发表失败,请检查网络！", Toast.LENGTH_SHORT).show();
                wiatDialog.dismiss();
            }

            @Override
            public void successed(Integer integer) {
                wiatDialog.dismiss();
                Toast.makeText(LiuHeApplication.context, "发表成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void fetchData() {
    }

    //发表帖子
    public void addForum() {
        if (path.size() > 0) {
            wiatDialog.show();
            Flowable.just(path)
                    .observeOn(Schedulers.io())
                    .map(list -> {
                        // 同步方法直接返回压缩后的文件
                        return Luban.with(AddForumActivity.this).load(list).get();
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(files -> {
                        if (files != null && files.size() > 0) {
                            //上传图片
                            presenter.postFiles("multi", files);
                        } else {
                            wiatDialog.cancel();
                        }
                    });
        } else {
//            Toast.makeText(LiuHeApplication.context,"图片不能为空哦~",Toast.LENGTH_SHORT).show();
            successed(null);
        }
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        wiatDialog.dismiss();
    }

    @Override
    public void successed(List<ImageEntity> imageEntities) {
        if (imageEntities != null && imageEntities.size() > 0) {
            String postImgPath = "";
            for (int i = 0; i < imageEntities.size(); i++) {
                ImageEntity imageEntity = imageEntities.get(i);
                postImgPath += imageEntity.getPath();
                if (i != imageEntities.size() - 1) {
                    postImgPath += ",";
                }
            }
            //发表帖子
            addForumPresenter.addForum(publishInput.getText().toString(), postImgPath);
        } else {
            addForumPresenter.addForum(publishInput.getText().toString(), "");
        }
    }
}
