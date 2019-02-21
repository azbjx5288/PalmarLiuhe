package com.liuheonline.la.ui.launcher;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.liuheonline.la.entity.TokenEntity;
import com.liuheonline.la.entity.WebEntity;
import com.liuheonline.la.mvp.presenter.TokenPresenter;
import com.liuheonline.la.mvp.presenter.WebPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.main.MainActivity;
import com.liuheonline.la.ui.main.holder.LocalImageHolderView;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.liuheonline.la.utils.SysUtil;
import com.mylove.loglib.JLog;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.permission.PermissionFail;
import com.yxt.itv.library.permission.PermissionHelper;
import com.yxt.itv.library.permission.PermissionSucceed;
import com.yxt.itv.library.permission.PermissionUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class LauncherActivity extends BaseMVPActivity<BaseView<TokenEntity>, TokenPresenter, TokenEntity> {

    @BindId(R.id.tiyan_img)
    private ImageView tiyanImg;
    @BindId(R.id.time)
    TextView time;
    @BindId(R.id.img)
    ImageView img;
    @BindId(R.id.lead_banner)
    private ConvenientBanner convenientBanner;
    CountDownTimer countDownTimer;
    String IMGDIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/palmarliuhe";
    String IMGPATH = IMGDIR + "/palmarliuhe.PNG";
    WebPresenter webPresenter;
    String url = "";
    String weburl = "";
    String startUrl = "";

    private AlertDialog alertDialog;
    int isclick = 0;
    boolean ispouse = false;
    boolean isFirst = false;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    Disposable subscribe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题**/
        super.onCreate(savedInstanceState);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setData(Uri.parse(startUrl));//Url 就是你要打开的网址
//                intent.setAction(Intent.ACTION_VIEW);
//                startActivity(intent); //启动浏览器
                JLog.e(startUrl);
                if (!TextUtils.isEmpty(startUrl)) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri uri = Uri.parse(startUrl);
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }
        });
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (weburl.length() > 6) {
//                    String str = weburl.substring(0, 6);
//                    if (!(str.contains("http:") || str.contains("https:"))) {
//                        weburl = "file:///android_asset/loaderr.html";
//                    }
//                } else {
//                    weburl = "file:///android_asset/loaderr.html";
//                }
//                Bundle bundle = new Bundle();
//                bundle.putString("url", weburl);
//
//                if (weburl.length() > 2) {
//                    startActivity(LauncherWebView.class, bundle);
//                    if (subscribe != null) {
//                        subscribe.dispose();
//                    }
//                }
//            }
//        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isFirst) {
            List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(this, PERMISSIONS_STORAGE);
            if (deniedPermissions.size() == 0) {
                if (SharedperfencesUtil.getString(this, "token").length() > 0) {
                    startMain();
                } else {
                    presenter.getToken(SysUtil.getMachineImei(this));
                }
            } else {
                onTiyan();
            }
        } else {
            startActivity(MainActivity.class);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ispouse) {
            startActivity(MainActivity.class);
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ispouse = true;
    }

    @Override
    protected void initData() {
    }

    @OnClick(R.id.time)
    private void onClick(View view) {
        time.setClickable(false);
        time.setFocusable(false);

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (subscribe != null) {
            subscribe.dispose();
        }
        startActivity(MainActivity.class);
        finish();

    }

    public void startMain() {
        alertDialog.cancel();

//        countDownTimer = new CountDownTimer(3000, 1000) {
//            @Override
//            public void onTick(long l) {
//                Log.i("info", "onTick: " + l);
////                time.setText((l/1000+1)+" 跳过广告");
//                time.setText(String.format("%s跳过广告", (l / 1000 + 1)));
//            }
//
//            @Override
//            public void onFinish() {
//                startActivity(MainActivity.class);
//                finish();
//            }
//        }.start();
        subscribe = Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io()).take(Long.parseLong(3 + ""))
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        return aLong.intValue();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                }).subscribe(integer -> {
                    time.setText(String.format("%s跳过广告", 3 - integer));
                }, throwable -> {
                }, () -> {
                    time.setClickable(false);
                    time.setFocusable(false);
                    img.setClickable(false);
                    img.setFocusable(false);
                    startActivity(MainActivity.class);
                    finish();
                });
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_launcher);
    }

    @Override
    protected void initTitle() {
    }

    @Override
    protected void initPresenter() {
        presenter = new TokenPresenter();
        presenter.attachView(this);
        fetchDataToken();
        webPresenter = new WebPresenter();
        webPresenter.attachView(new BaseView<WebEntity>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {

            }

            @Override
            public void successed(WebEntity webEntity) {
                SharedperfencesUtil.setString(LauncherActivity.this, "agent_announcement", webEntity.getAgent_announcement());
                SharedperfencesUtil.setString(LauncherActivity.this, "agent_pwd", webEntity.getAgent_pwd());
                if (webEntity != null) {
                    //状态为0就直接跳转main
                    if (webEntity.getApp_img() == null) {
                        if (subscribe != null) {
                            subscribe.dispose();
                        }
                        startActivity(MainActivity.class);
                        finish();
                    } else {
                        url = webEntity.getApp_img().getPic_link();
                        weburl = webEntity.getApp_img().getImg_url();
                        startUrl = webEntity.getService_url();

                        //如果本地没有缓存
                        if (!SharedperfencesUtil.getString(LauncherActivity.this, "launcherimg").equals(url)) {
                            JLog.w("没有缓存，读取在线的");
                            Glide.with(LauncherActivity.this)
                                    .load(url)
                                    .apply(new RequestOptions()
                                            //.placeholder(R.mipmap.jianzaizhong) //加载中图片
                                            //.error(R.mipmap.shanping) //加载失败图片
                                            // .fallback(R.mipmap.shanping) //url为空图片
                                            .centerCrop() // 填充方式
                                            .priority(Priority.HIGH) //优先级
                                            .diskCacheStrategy(DiskCacheStrategy.NONE))
                                    .into(img);
                            //缓存到本地
                            new DownLoadFile().execute(url);
                        } else {
                            JLog.w("有缓存，读取本地的");
                            Glide.with(LauncherActivity.this)
                                    .load(IMGPATH)
                                    .apply(new RequestOptions()
                                            //.placeholder(R.mipmap.jianzaizhong) //加载中图片
                                            //.error(R.mipmap.shanping) //加载失败图片
                                            // .fallback(R.mipmap.shanping) //url为空图片
                                            .centerCrop() // 填充方式
                                            .priority(Priority.HIGH) //优先级
                                            .diskCacheStrategy(DiskCacheStrategy.NONE))
                                    .into(img);
                        }

                    }
                } else {
                    if (subscribe != null) {
                        subscribe.dispose();
                    }
                    startActivity(MainActivity.class);
                    finish();
                }
            }
        });
        webPresenter.getWeb();
    }

    @Override
    protected void fetchData() {
    }

    private void fetchDataToken() {
        if (SharedperfencesUtil.getBoolean(this, getPackageName())) {
            List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(this, PERMISSIONS_STORAGE);
            if (deniedPermissions.size() == 0) {
                if (SharedperfencesUtil.getString(this, "token").length() > 0) {
                    startMain();
                } else {
                    presenter.getToken(SysUtil.getMachineImei(this));
                }
            } else {
                onTiyan();
            }
        } else {
            SharedperfencesUtil.setBoolean(this, getPackageName(), true);
            isFirst = true;
            convenientBanner.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initView() {

        alertDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait)
                .setCancelable(false)
                .setText(R.id.text_hint, "正在初始化配置……")
                .create();


        //设置banner
        List<Integer> imgList = new ArrayList<>();
        imgList.add(R.mipmap.y1);
        imgList.add(R.mipmap.y2);
        imgList.add(R.mipmap.y3);
        imgList.add(R.mipmap.y4);
        convenientBanner.setPointViewVisible(true);
        convenientBanner.setManualPageable(true);
        convenientBanner.setScrollDuration(3);
        convenientBanner.setCanLoop(false);

        // convenientBanner.setPageIndicator(new int[]{R.mipmap.point_nomal, R.mipmap.point_focus});
        convenientBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    tiyanImg.setVisibility(View.VISIBLE);
                } else {
                    tiyanImg.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        convenientBanner.setPages((CBViewHolderCreator<LocalImageHolderView>) () -> new LocalImageHolderView(), imgList);


        //预先加载缓存的图片
        Glide.with(LauncherActivity.this)
                .load(IMGPATH)
                .apply(new RequestOptions()
                        //.placeholder(R.mipmap.jianzaizhong) //加载中图片
                        //.error(R.mipmap.shanping) //加载失败图片
                        // .fallback(R.mipmap.shanping) //url为空图片
                        .centerCrop() // 填充方式
                        .priority(Priority.HIGH) //优先级
                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(img);

    }

    @OnClick(R.id.tiyan_img)
    private void onTiyan() {
        //申请权限
        PermissionHelper.requestPermission(this, 0x1010, PERMISSIONS_STORAGE);
    }

    @PermissionSucceed(requeseCode = 0x1010)
    public void permissionSucceed() {
        List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(this, PERMISSIONS_STORAGE);
        if (deniedPermissions.size() == 0) {
            if (SharedperfencesUtil.getString(this, "token").length() > 0) {
                startActivity(MainActivity.class);
                finish();
            } else {
                presenter.getToken(SysUtil.getMachineImei(this));
            }
        }
    }

    @PermissionFail(requeseCode = 0x1010)
    public void permissionFail() {
        List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(this, PERMISSIONS_STORAGE);
        if (deniedPermissions.size() != 0) {
            final AlertDialog dialog = new AlertDialog.Builder(this)
                    .setContentView(R.layout.dialog)
                    .setText(R.id.message_text, "应用需要获取您的授权才能正常运行。请转到“设置”为应用授予相应权限。")
                    .show();
            dialog.setOnclickListener(R.id.btn_cancle, v -> {
                dialog.dismiss();
                finish();
            });
            dialog.setOnclickListener(R.id.btn_sure, v -> {
                startAppSetting();
                dialog.dismiss();
            });
        }
    }


    //启动设置去修改权限
    private void startAppSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 0x1111);
    }


    //是否需要弹出对话框
    private boolean needShowGuide() {
        return getPermissionFlag(Manifest.permission.READ_EXTERNAL_STORAGE)
                && !getPermissionFlag(Manifest.permission.CAMERA);
    }

    //获取存储的权限的状态值
    private boolean getFlag() {
        return getSharedPreferences("permission", MODE_PRIVATE).getBoolean("flag", false);
    }

    //获取当前权限的Boolean值
    private boolean getPermissionFlag(String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x1111) {
            PermissionHelper.requestPermission(this, 0x1010, PERMISSIONS_STORAGE);
        }
    }

    @Override
    public void onLoading() {
        alertDialog.show();
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        alertDialog.setText(R.id.text_hint, "网络错误，请检查网络设置！");
    }

    @Override
    public void successed(TokenEntity tokenEntity) {
        JLog.v();
        alertDialog.cancel();
        SharedperfencesUtil.setString(LauncherActivity.this, "token", tokenEntity.getAccesstoken());
        startActivity(MainActivity.class);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 下载
     */
    private class DownLoadFile extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                int fileLenth = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream());
                File filedir = new File(IMGDIR);
                if (!filedir.exists()) {
                    filedir.mkdirs();
                }
                File file = new File(IMGPATH);
                if (!file.exists()) {
                    file.createNewFile();
                }
                OutputStream output = new FileOutputStream(IMGPATH);

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        return null;
                    }
                    total += count;
                    publishProgress((int) (total * 100 / fileLenth));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onCancelled() {
          /*  if (progressDialog!=null){
                progressDialog.cancel();
            }*/
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JLog.w("已缓存到本地");
            //下载完成后保存下载地址到本地
            SharedperfencesUtil.setString(LauncherActivity.this, "launcherimg", url);
        }
    }


}
