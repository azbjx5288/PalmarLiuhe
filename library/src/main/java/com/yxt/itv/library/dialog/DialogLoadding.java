package com.yxt.itv.library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxt.itv.library.R;


public class DialogLoadding {

    private Context context;
    private LayoutInflater inflater;
    private String Message = "加载中...";

    private Dialog loadingdialog;

    public DialogLoadding(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        init();
    }


    private void init() {
        View view = inflater.inflate(R.layout.dialog_loading, null);
        LinearLayout layout = view
                .findViewById(R.id.dialog_loading_view);// 加载布局
        TextView tipText = view.findViewById(R.id.tipTextView);
        tipText.setText(Message);

        loadingdialog = new Dialog(context, R.style.MyDialogStyle);
        loadingdialog.setCancelable(false);//是否可以按返回按钮
        loadingdialog.setCanceledOnTouchOutside(false);//屏幕之外区域是否可以点击消除
        loadingdialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        /**
         * 将显示的Dilaog封装在方法里面
         */
        Window window = loadingdialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);

    }


    public void closeDialog() {
        if (loadingdialog != null && loadingdialog.isShowing()) {
            loadingdialog.dismiss();
        }
    }

    public void showDialog() {
        if (context != null) {
            loadingdialog.show();
        }
    }
}
