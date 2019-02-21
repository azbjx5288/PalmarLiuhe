package com.liuheonline.la.ui.user;

import android.app.AlertDialog;
import android.os.Build;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.WebEntity;
import com.liuheonline.la.mvp.presenter.WebPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.liuheonline.la.utils.StringUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

/**
 * @author: aini
 * @date 2018/8/3 09:46
 * @description 用户活动
 */
public class UserActiveActivity extends BaseMVPActivity<BaseView<WebEntity>,WebPresenter,WebEntity>{

    @BindId(R.id.active_webview)
    private WebView activeWebView;

    private com.yxt.itv.library.dialog.AlertDialog waitDialog;

    @Override
    protected void initData() {
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_useractive);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("活动中心")
                .builder();
    }

    @Override
    protected void initView() {
        waitDialog = new com.yxt.itv.library.dialog.AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint,"正在加载……")
                .create();
        //清理缓存
        activeWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        activeWebView.setVerticalScrollbarOverlay(true);
        //设置WebView支持JavaScript
        activeWebView.getSettings().setJavaScriptEnabled(true);
        activeWebView.getSettings().setDomStorageEnabled(true);
        activeWebView.setWebChromeClient(new MywebChromeClient());
        //添加客户端支持
        activeWebView.setWebViewClient(new WebViewClient() {
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
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                activeWebView.loadUrl("javascript:vipMsg('"+ SharedperfencesUtil.getString(UserActiveActivity.this,
                        "token") + "')");
                waitDialog.cancel();
            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                waitDialog.cancel();
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

    @Override
    public void onLoading() {
        waitDialog.show();
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        waitDialog.cancel();
        Toast.makeText(LiuHeApplication.context,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successed(WebEntity webEntity) {
        activeWebView.loadUrl(StringUtil.translation(webEntity.getLucky_url()));
    }



    class MywebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            // 弹窗处理
            AlertDialog.Builder b2 = new AlertDialog.Builder(UserActiveActivity.this)
                    .setTitle(R.string.app_name).setMessage(message)
                    .setPositiveButton("ok", (dialog, which) -> {
                        result.confirm();
                    });
            b2.setCancelable(false);
            b2.create();
            b2.show();
            return true;
        }
    }
}
