package com.liuheonline.la.ui.main.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.liuheonline.la.entity.SpeciesclasstypeEntity;
import com.liuheonline.la.entity.WebEntity;
import com.liuheonline.la.event.BetEvent;
import com.liuheonline.la.mvp.presenter.SpeciesclasstypePresenter;
import com.liuheonline.la.mvp.presenter.WebPresenter;
import com.liuheonline.la.mvp.presenter.WebPresenter2;
import com.liuheonline.la.ui.base.BaseMvpFragment;
import com.liuheonline.la.ui.main.statistics.ViewPageAdapter;
import com.liuheonline.la.ui.main.web.X5WebView;
import com.liuheonline.la.utils.StringUtil;
import com.mylove.loglib.JLog;
import com.tencent.smtt.sdk.ValueCallback;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class BetFrameFragment extends BaseMvpFragment<BaseView<List<SpeciesclasstypeEntity>>, SpeciesclasstypePresenter, List<SpeciesclasstypeEntity>> {
    @BindId(R.id.frame_tab)
    private TabLayout tabLayout;

    @BindId(R.id.checkbox)
    private CheckBox checkBox;
    @BindId(R.id.frame_viewpager)
    private ViewPager viewPager;

    private List<String> mTitle = new ArrayList<>();

    private List<Fragment> mFragment = new ArrayList<>();
    WebPresenter2 webPresenter;
    SpeciesclasstypePresenter speciesclasstypePresenter;
    @BindId(R.id.bet_webview)
    X5WebView webView;
    @BindId(R.id.bet_linear)
    LinearLayout betlinear;
    private com.yxt.itv.library.dialog.DialogLoadding waitDialog;

    private boolean hasCheakced=false;

    private boolean isProtogenic=false;//是否是原生开发

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        webView.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient() {
            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                Log.i("test", "openFileChooser 1");
               /* X5Web.this.uploadFile = uploadFile;
                openFileChooseProcess();*/
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsgs) {
                Log.i("test", "openFileChooser 2");
              /*  X5Web.this.uploadFile = uploadFile;
                openFileChooseProcess();*/
            }

            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Log.e("test", "openFileChooser 3");
               /* X5Web.this.uploadFile = uploadFile;
                openFileChooseProcess();*/
            }

            // For Android  >= 5.0
            public boolean onShowFileChooser(com.tencent.smtt.sdk.WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                Log.e("test", "openFileChooser 4:" + filePathCallback.toString());
                // X5Web.this.uploadFiles = filePathCallback;
                //openFileChooseProcess();
                return true;
            }

        });
        webView.getSettings().setUseWideViewPort(true); //自适应屏幕

//        webView.setInitialScale(70);


        //webView加载监听
        webView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
            @Override
            public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String s) {
                super.onPageFinished(webView, s);
                waitDialog.closeDialog();
            }

            @Override
            public void onPageStarted(com.tencent.smtt.sdk.WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public void onReceivedError(com.tencent.smtt.sdk.WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
                // loadfail.setVisibility(View.VISIBLE);
                waitDialog.closeDialog();
            }

            @Override
            public void onReceivedHttpError(com.tencent.smtt.sdk.WebView webView, com.tencent.smtt.export.external.interfaces.WebResourceRequest webResourceRequest, com.tencent.smtt.export.external.interfaces.WebResourceResponse webResourceResponse) {
                super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
                waitDialog.closeDialog();
            }

            @Override
            public void onReceivedSslError(com.tencent.smtt.sdk.WebView webView, com.tencent.smtt.export.external.interfaces.SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
                waitDialog.closeDialog();
            }
        });
        //webView加载百分比监听
        webView.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient() {
            @Override
            public void onProgressChanged(com.tencent.smtt.sdk.WebView webView, int i) {
                super.onProgressChanged(webView, i);
                Log.e("---TEST---", String.valueOf(i));


            }
        });


    }

    @Override
    protected void initTitle(View view) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_betframe;
    }

    @Override
    protected void fetchData() {
        speciesclasstypePresenter.getType();
    }

    @Override
    protected void initPresenter() {
        waitDialog = new com.yxt.itv.library.dialog.DialogLoadding(getContext());
              /*  .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint, "正在加载……")
                .create();*/
        speciesclasstypePresenter = new SpeciesclasstypePresenter();
        speciesclasstypePresenter.attachView(this);

        if(!hasCheakced){
            hasCheakced=true;
            webPresenter = new WebPresenter2();
            webPresenter.attachView(new BaseView<String>() {
                @Override
                public void onLoading() {
                    waitDialog.showDialog();
                }

                @Override
                public void onLoadFailed(int code, String error) {
                    waitDialog.closeDialog();
                }

                @Override
                public void successed(String webEntity) {
                    showContent(webEntity);
                    waitDialog.closeDialog();
                }
            });
            webPresenter.getEgurl();
        }
    }

    public void setCheckedBet() {

        if(isProtogenic){
            return;
        }

        if(hasCheakced){
            webPresenter.getEgurl();
            return;
        }

        if(webPresenter==null){
            webPresenter = new WebPresenter2();
            webPresenter.attachView(new BaseView<String>() {
                @Override
                public void onLoading() {
                    waitDialog.showDialog();
                }

                @Override
                public void onLoadFailed(int code, String error) {
                    waitDialog.closeDialog();
                }

                @Override
                public void successed(String webEntity) {
                   showContent(webEntity);
                }
            });
            webPresenter.getEgurl();
        }else{
            webPresenter.getEgurl();
        }
    }

    private void showContent(String webEntity) {
        // 1.投注页面优先加载：/api/Egurl 内容，如果Egurl里data的内容不为空，那么投注页面将直接内嵌data的网址，原来的是读取：app_betting_url显示的
        if (!TextUtils.isEmpty(webEntity)) {
            JLog.d("webEntity  ===="+webEntity);
            String[] webAll=webEntity.split("-");

            String  url=webAll[0];

            String  code=webAll[1];

            if(TextUtils.isEmpty(url)) {
                webView.setVisibility(View.GONE);
                betlinear.setVisibility(View.VISIBLE);
                isProtogenic=true;
            }else {
                switch (code){
                    case "201":
                        Uri CONTENT_URI_BROWSERS = Uri.parse(url);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(CONTENT_URI_BROWSERS);//Url 就是你要打开的网址x
                        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
                        // 官方解释 : Name of the component implementing an activity that can display the intent
                        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                            final ComponentName componentName = intent.resolveActivity(getContext().getPackageManager());
                            // 打印Log   ComponentName到底是什么
                            Log.e("ServiceChat", "componentName = " + componentName.getClassName());
                            startActivity(Intent.createChooser(intent, "请选择浏览器"));
                        } else {
                            Toast.makeText(getContext().getApplicationContext(), "没有匹配的程序", Toast.LENGTH_SHORT).show();
                        }
                        isProtogenic=false;
                        break;
                    case "200":
                        webView.setVisibility(View.VISIBLE);
                        betlinear.setVisibility(View.GONE);
                        webView.loadUrl(url);
                        isProtogenic=false;
                        break;
                    default:
                        webView.setVisibility(View.GONE);
                        betlinear.setVisibility(View.VISIBLE);
                        isProtogenic=true;
                        break;
                }

            }

        } else {
            webView.setVisibility(View.GONE);
            betlinear.setVisibility(View.VISIBLE);
            waitDialog.closeDialog();
            isProtogenic=true;
        }
    }


    @OnClick({R.id.check_linear})
    private void checkShow(View view) {
        checkBox.setChecked(!checkBox.isChecked());
        BetEvent betEvent = new BetEvent();
        betEvent.setChecked(checkBox.isChecked());
        EventBus.getDefault().post(betEvent);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(List<SpeciesclasstypeEntity> speciesclasstypeEntity) {
        JLog.d(speciesclasstypeEntity.size());
        JLog.d(speciesclasstypeEntity);
//        BetFragment betFragment = new BetFragment();
//        BetFragment2 betFragment2 = new BetFragment2();
//        BetFragment3 betFragment3 = new BetFragment3();
        for (int i = 0; i < speciesclasstypeEntity.size(); i++) {
//                if (i == 0) {
            mTitle.add(speciesclasstypeEntity.get(i).getTitle());

            if (speciesclasstypeEntity.get(i).getTitle().contains("六合")) {
//                SharedperfencesUtil.setInt(mContext, "bet1", speciesclasstypeEntity.get(i).getId());
                mFragment.add(BetFragment.newInstance(speciesclasstypeEntity.get(i).getId()));
            } else if (speciesclasstypeEntity.get(i).getTitle().contains("时时彩")) {
//                SharedperfencesUtil.setInt(mContext, "bet3", speciesclasstypeEntity.get(i).getId());
//                mFragment.add(betFragment3);
                mFragment.add(BetFragment3.newInstance(speciesclasstypeEntity.get(i).getId()));
            } else {
//                SharedperfencesUtil.setInt(mContext, "bet2", speciesclasstypeEntity.get(i).getId());
                mFragment.add(BetFragment2.newInstance(speciesclasstypeEntity.get(i).getId()));
            }

//                }
//                if (i == 1) {
//                    mTitle.add(speciesclasstypeEntity.get(i).getTitle());
//
//                    if (speciesclasstypeEntity.get(i).getTitle().indexOf("六合") != -1) {
//                        SharedperfencesUtil.setInt(getContext(), "bet1", speciesclasstypeEntity.get(i).getId());
//                        mFragment.add(betFragment);
//                    } else if (speciesclasstypeEntity.get(i).getTitle().indexOf("时时彩") != -1) {
//                        SharedperfencesUtil.setInt(getContext(), "bet3", speciesclasstypeEntity.get(i).getId());
//                        mFragment.add(betFragment3);
//                    } else {
//                        SharedperfencesUtil.setInt(getContext(), "bet2", speciesclasstypeEntity.get(i).getId());
//                        mFragment.add(betFragment2);
//                    }
//                }
//                if (i == 2) {
//                    mTitle.add(speciesclasstypeEntity.get(i).getTitle());
//
//                    if (speciesclasstypeEntity.get(i).getTitle().indexOf("六合") != -1) {
//                        SharedperfencesUtil.setInt(getContext(), "bet1", speciesclasstypeEntity.get(i).getId());
//                        mFragment.add(betFragment);
//                    } else if (speciesclasstypeEntity.get(i).getTitle().indexOf("时时彩") != -1) {
//                        SharedperfencesUtil.setInt(getContext(), "bet3", speciesclasstypeEntity.get(i).getId());
//                        mFragment.add(betFragment3);
//                    } else {
//                        SharedperfencesUtil.setInt(getContext(), "bet2", speciesclasstypeEntity.get(i).getId());
//                        mFragment.add(betFragment2);
//                    }
//                }

        }

        ViewPageAdapter adapter = new ViewPageAdapter(getActivity().getSupportFragmentManager(), mTitle, mFragment);
        viewPager.setAdapter(adapter);
        //为TabLayout设置ViewPager
        tabLayout.setupWithViewPager(viewPager);
        //使用ViewPager的适配器
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

}
