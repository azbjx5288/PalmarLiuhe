package com.liuheonline.la.ui.main.web;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.WebEntity;
import com.liuheonline.la.mvp.presenter.WebPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.user.login.LoginActivity;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.liuheonline.la.utils.StringUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class ServiceChat extends BaseMVPActivity<BaseView<WebEntity>,WebPresenter,WebEntity> {

    @BindId(R.id.chat_webview)
    private WebView mWebView;

    @BindId(R.id.web_refresh)
    private SwipeRefreshLayout swipeRefreshLayout;

    private com.yxt.itv.library.dialog.AlertDialog waitDialog;

    String strurl = "";
    @Override
    protected void initData() {
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_servicechat);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("客服")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {

        swipeRefreshLayout.setEnabled(false);
        //swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
            @Override
            public boolean canChildScrollUp(@NonNull SwipeRefreshLayout parent, @Nullable View child) {
               return mWebView.getScrollY() > 0;
            }
        });

        waitDialog = new com.yxt.itv.library.dialog.AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint,"正在加载……")
                .create();
        //清理缓存
        mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        mWebView.setVerticalScrollbarOverlay(true);
        //设置WebView支持JavaScript
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.setWebChromeClient(new MywebChromeClient());
        //添加客户端支持
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                } else {
                    view.loadUrl(request.toString());
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                swipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
               /* mWebView.loadUrl("javascript:vipMsg('" + SharedperfencesUtil.getInt(LiuHeApplication.context,
                        "userId") + "','" + SharedperfencesUtil.getString(ServiceChat.this, "token") + "')");*/
                waitDialog.cancel();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                waitDialog.cancel();
                mWebView.loadUrl("file:///android_asset/loaderr.html");
                swipeRefreshLayout.setRefreshing(false);
                Log.w("webservicess","onReceivedError");
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                waitDialog.cancel();
                Log.w("webservicess","onReceivedHttpError");
                //mWebView.loadUrl("file:///android_asset/loaderr.html");
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
               /* if(error.getPrimaryError() == android.net.http.SslError.SSL_INVALID ){// 校验过程遇到了bug
                    handler.proceed();
                }else{
                    handler.cancel();
                }*/
                handler.proceed();
                waitDialog.cancel();
                Log.w("webservicess","onReceivedSslError");
                //mWebView.loadUrl("file:///android_asset/loaderr.html");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }



    @Override
    protected void initPresenter() {
        presenter = new WebPresenter();
    }

    @Override
    protected void fetchData() {
        presenter.getWeb();
    }

    /*@Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.w("Fragemntchat",hidden+"    onHiddenChanged");
        if(hidden){
            mWebView.reload();
        }
    }*/


    @Override
    public void onResume() {
        super.onResume();
       /* Log.w("Fragemntchat","    onResume");
        mWebView.clearFormData();
        mWebView.clearHistory();
        mWebView.clearCache(true);
        mWebView.loadUrl(strurl);*/
       // mWebView.loadUrl(strurl);
    }

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.w("Fragemntchat",isVisibleToUser+"    setUserVisibleHint");
        if (isVisibleToUser){
            mWebView.loadUrl(strurl);
        }
    }*/

  /*  @Override
    public void onRefresh() {
        mWebView.loadUrl(strurl);
    }*/

    @Override
    public void onLoading() {
        waitDialog.show();
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        waitDialog.cancel();
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(LiuHeApplication.context,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successed(WebEntity webEntity) {
        swipeRefreshLayout.setRefreshing(false);
        strurl = StringUtil.translation(webEntity.getService_url());
        mWebView.loadUrl(StringUtil.translation(strurl));
    }



    class MywebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress==100){
                swipeRefreshLayout.setRefreshing(false);
            }
        }


        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            // 弹窗处理
            if(message.equals("请先登录")){
                if (SharedperfencesUtil.getInt(ServiceChat.this,"userId")!=0){
                    mWebView.loadUrl(strurl);
                }else {
                    startActivity(LoginActivity.class);
                }


            }else{
                AlertDialog.Builder b2 = new AlertDialog.Builder(ServiceChat.this)
                        .setTitle(R.string.app_name).setMessage(message)
                        .setPositiveButton("ok", (dialog, which) -> result.confirm());
                b2.setCancelable(false);
                b2.create();
                b2.show();
            }
            result.cancel();
            return true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
