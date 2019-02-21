package com.liuheonline.la.ui.picture;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.liuheonline.la.ui.base.BaseFrameActivity;
import com.liuheonline.la.ui.imageengine.GlideImageEngine;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.maning.imagebrowserlibrary.listeners.OnClickListener;
import com.maning.imagebrowserlibrary.listeners.OnLongClickListener;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.ioc.BindId;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author BenYanYi
 * @date 2019/01/16 16:08
 * @email ben@yanyi.red
 * @overview
 */
public class ImageDetailActivity extends BaseFrameActivity {

    @BindId(R.id.image)
    ImageView img;

    private int openAnim = R.anim.mn_browser_enter_anim;
    private int exitAnim = R.anim.mn_browser_exit_anim;

    private Context mContext;
    private Activity mActivity;

    private ArrayList<String> urlList;

    private int index = 0;

//    String APKDIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/palmarliuhe";

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.urlList = bundle.getStringArrayList("urlList");
            this.index = bundle.getInt("index");
        }
        MNImageBrowser.with(this)
                //当前位置(必填)
                .setCurrentPosition(index)
                //必须-图片加载用户自己去选择
                .setImageEngine(new GlideImageEngine())
                //必须（setImageList和setImageUrl二选一，会覆盖）-设置单张图片
                .setImageList(urlList)
                //设置隐藏指示器
                .setIndicatorHide(false)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(FragmentActivity activity, ImageView view, int position, String url) {
//                        //关闭图片浏览
//                        MNImageBrowser.finishImageBrowser();
//                        finish();
//                        PromptDialog promptDialog = new PromptDialog(mActivity);
//                        //设置自定义属性
//                        promptDialog.getDefaultBuilder().touchAble(true).round(3).loadingDuration(3000);
//                        PromptButton cancle = new PromptButton("取消", null);
//                        cancle.setTextColor(Color.parseColor("#0076ff"));
//                        promptDialog.showAlertSheet("", true, cancle,
//                                new PromptButton("保存图片", null));
                    }
                })
                .setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public void onLongClick(FragmentActivity activity, ImageView view, int position, String url) {
//                        PromptDialog promptDialog = new PromptDialog(mActivity);
//                        //设置自定义属性
//                        promptDialog.getDefaultBuilder().touchAble(true).round(3).loadingDuration(3000);
//                        PromptButton cancle = new PromptButton("取消", null);
//                        cancle.setTextColor(Color.parseColor("#0076ff"));
//                        promptDialog.showAlertSheet("", true, cancle,
//                                new PromptButton("保存图片", null));

                    }
                })
                //全屏模式
                .setFullScreenMode(false)
                //打开动画
                .setActivityOpenAnime(R.anim.mn_browser_enter_anim)
                //关闭动画
                .setActivityExitAnime(R.anim.mn_browser_exit_anim)
                //显示：传入当前View
                .show(img);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName(), new File(APKPATH));
//        } else {
//        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_image_detail);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        mContext = this;
        mActivity = this;
    }

    private void saveFile(String url) {
        Observable.create(e -> save(url, e))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Object object) {
                        Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        disposable.dispose();
                        Toast.makeText(mContext, "保存失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void save(String urlStr, ObservableEmitter<Object> emitter) {
        try {
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream input = new BufferedInputStream(url.openStream());
            String savePath = isExistDir("/palmarliuhe/image");
            File file = new File(savePath, getNameFromUrl(urlStr));
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream output = new FileOutputStream(file);

            byte data[] = new byte[1024];
            int count;
            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
            emitter.onNext(null);
        } catch (Exception e) {
            e.printStackTrace();
            emitter.onError(e);
        }
        emitter.onComplete();
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
