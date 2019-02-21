package com.liuheonline.la.ui.user.setting;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.ImageEntity;
import com.liuheonline.la.entity.UserInfo;
import com.liuheonline.la.mvp.presenter.PostFilePresenter;
import com.liuheonline.la.mvp.presenter.UpUserInfoPresenter;
import com.liuheonline.la.mvp.presenter.UserInfoPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.forum.holder.GlideImageLoader;
import com.liuheonline.la.ui.user.account.MyAccountCard;
import com.liuheonline.la.ui.user.share.ShareActivity;
import com.liuheonline.la.ui.widget.CircleImageView;
import com.liuheonline.la.utils.ImageUrlUtil;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * @author: aini
 * @date 2018/7/17 20:58
 * @description 用户设置
 */
public class UserSettingActivity extends BaseMVPActivity<BaseView<UserInfo>,UserInfoPresenter,UserInfo>{

    private UserInfo userInfo;

    @BindId(R.id.user_icon)
    private CircleImageView userIcon;

    @BindId(R.id.user_name)
    private TextView userName;

    @BindId(R.id.user_phone)
    private TextView userPhone;



   /* @BindId(R.id.is_push)
    CheckBox ispush;

    int pushid = 0;//是否开启推送，1为开启，2为关闭，默认为开启*/

    //图片选择器
    private GalleryConfig galleryConfig;

    //图片选择器回调
    private IHandlerCallBack iHandlerCallBack;

    private String userImgPath = "";

    private AlertDialog waitDialog;

    private PostFilePresenter postFilePresenter;
    private UpUserInfoPresenter upUserInfoPresenter;

    @Override
    protected void initData() {
        initGallery();
        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider(getPackageName())   // provider(必填)
                .crop(true, 1, 1, 500, 500)
                .isShowCamera(true)
                .multiSelect(false)
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();
    }

    private void initGallery() {
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
            }
            @Override
            public void onSuccess(List<String> photoList) {
                waitDialog.show();
                Flowable.just(photoList)
                        .observeOn(Schedulers.io())
                        .map(list -> {
                            // 同步方法直接返回压缩后的文件
                            return Luban.with(UserSettingActivity.this).load(list).get();
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(files -> {
                            if(files != null && files.size() > 0){
                                //本地文件
                                File imgFile = files.get(0);
                                //上传图片
                                postFilePresenter.postFile("",imgFile);
                            }else{
                                waitDialog.cancel();
                            }
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
    protected void setContentView() {
        setContentView(R.layout.activity_usersetting);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("设置")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
        waitDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint,"提交中……")
                .create();



      /*  ispush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    SharedperfencesUtil.setInt(UserSettingActivity.this,"pushid",1);
                    if (JPushInterface.isPushStopped(UserSettingActivity.this)){
                        JPushInterface.resumePush(UserSettingActivity.this);
                    }
                }else {
                    SharedperfencesUtil.setInt(UserSettingActivity.this,"pushid",2);
                    if (!JPushInterface.isPushStopped(UserSettingActivity.this)){
                        JPushInterface.stopPush(UserSettingActivity.this);
                    }
                }
            }
        });
       pushid = SharedperfencesUtil.getInt(this,"pushid");
       if (pushid==0){
           pushid = 1;
       }
       showCheckBox();*/
    }
  /*  private void showCheckBox(){
        if (pushid==1){
            ispush.setChecked(true);
        }else {
            ispush.setChecked(false);
        }
    }*/
    @OnClick({R.id.up_icon,R.id.up_name,R.id.up_pw,R.id.up_phone,R.id.out_login,R.id.clear_data,R.id.up_paypw,R.id.user_share,R.id.bankcard})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.bankcard:
                startActivity(MyAccountCard.class);
                break;
            case R.id.user_share:
                if (SharedperfencesUtil.getInt(this,"userId")!=0){
                    startActivity(ShareActivity.class);
                }else {
                    Toast.makeText(getApplicationContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.up_icon:
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(UserSettingActivity.this);
                break;
            case R.id.up_name:
                startActivity(UserSetNickNameActivity.class);
                break;
            case R.id.up_pw:
                startActivity(UserSetPasswordActivity.class);
                break;
            case R.id.up_phone:
                startActivity(UserSetPhoneActivity.class);
                break;
            case R.id.out_login:
                SharedperfencesUtil.setInt(this,"userId",0);
                finish();
                break;
            case R.id.clear_data:
                Toast.makeText(getApplicationContext(),"清理成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.up_paypw:

                startActivity(UserSetPayPWActivity.class);
                break;
        }
    }

    @Override
    protected void initPresenter() {
        presenter = new UserInfoPresenter();
        postFilePresenter = new PostFilePresenter();
        postFilePresenter.attachView(new BaseView<List<ImageEntity>>() {
            @Override
            public void onLoading() {
            }
            @Override
            public void onLoadFailed(int code, String error) {
                waitDialog.cancel();
                Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void successed(List<ImageEntity> imageEntities) {
                userImgPath = imageEntities.get(0).getLink();
                Map<String,Object> map = new HashMap<>();
                map.put("head",imageEntities.get(0).getPath());
                upUserInfoPresenter.upUserInfo(userInfo.getId(),map);
            }
        });
        upUserInfoPresenter = new UpUserInfoPresenter();
        upUserInfoPresenter.attachView(new BaseView<Object>() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onLoadFailed(int code, String error) {
                waitDialog.cancel();
                Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void successed(Object s) {
                waitDialog.cancel();
                //显示图片
                Glide.with(UserSettingActivity.this)
                        .load(ImageUrlUtil.getImgUrl(userImgPath,100,100))
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.touxiang) //加载中图片
                                .error(R.mipmap.touxiang) //加载失败图片
                                .fallback(R.mipmap.touxiang) //url为空图片
                                .override(100,100)
                                .centerCrop() // 填充方式
                                .priority(Priority.HIGH) //优先级
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                        .into(userIcon);
                Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void fetchData() {
        presenter.getUserInfo(SharedperfencesUtil.getInt(this,"userId"));

    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onLoadFailed(int code, String error) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successed(UserInfo userInfo) {
        if(userInfo != null){
            this.userInfo = userInfo;
            Glide.with(this)
                    .load(ImageUrlUtil.getImgUrl(userInfo.getHead_link(),100,100))
                    .apply(new RequestOptions()
                            .placeholder(R.mipmap.touxiang2) //加载中图片
                            .error(R.mipmap.touxiang2) //加载失败图片
                            .fallback(R.mipmap.touxiang2) //url为空图片
                            .centerCrop() // 填充方式
                            .priority(Priority.HIGH) //优先级
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .into(userIcon);
//            userName.setText(userInfo.getNickname());
            userPhone.setText(userInfo.getMobile());
        }
    }

}
