package com.liuheonline.la.ui.widget;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.yxt.itv.library.ioc.BindId;

public class LauncherWebView extends BaseMVPActivity {
    @BindId(R.id.web_banner)
    WebView mWebView;
    private com.yxt.itv.library.dialog.AlertDialog waitDialog;
    String strurl = "";
    @Override
    protected void initPresenter() {

    }

    @Override
    protected void fetchData() {

    }

    @Override
    protected void initData() {
        strurl = getIntent().getExtras().getString("url");
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.banner_webview);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.clearFormData();
        mWebView.clearHistory();
        mWebView.clearCache(true);
        if (strurl.length()>1){
            mWebView.loadUrl(strurl);
        }else {
            mWebView.loadUrl("file:///android_asset/loaderr.html");
        }

    }



    @Override
    protected void initView() {
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
                waitDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                waitDialog.cancel();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                waitDialog.cancel();
                Log.w("onReceivedError",error.getDescription().toString()+error.getErrorCode());
                //mWebView.loadUrl("file:///android_asset/loaderr.html");
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                Log.w("onReceivedHttpError",errorResponse.getReasonPhrase());
                waitDialog.cancel();
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                Log.w("onReceivedSslError","sslerror");
                waitDialog.cancel();
            }
        });
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(Object o) {

    }
}
