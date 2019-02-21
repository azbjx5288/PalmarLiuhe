package com.liuheonline.la.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.dialog.AlertDialog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class UpdateMannger {

    private Context context;
    private ProgressBar progressDialog;
    String APKDIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/palmarliuhe";
    String APKPATH = APKDIR+"/palmarliuhe.apk";
    DownLoadFile downLoadFile;
    AlertDialog updatedialog;
    TextView upsize;

    public UpdateMannger(Context context){
        this.context = context;
         updatedialog  = new AlertDialog.Builder(context)
                .setWidthAndHeight(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .setContentView(R.layout.update_dialog)
                .setCancelable(false)
                .create();
    }


    public void showUpdateProgress(String url) {
        progressDialog = updatedialog.findViewById(R.id.update_progressbar);
        upsize = updatedialog.findViewById(R.id.up_size);
        updatedialog.show();
            downLoadFile = new DownLoadFile();
            downLoadFile.execute(url);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            upsize.setText(msg.obj.toString());
        }
    };


    public void cancelDown(){
        if(downLoadFile != null){
            downLoadFile.cancel(true);
            downLoadFile = null;
            updatedialog.cancel();
        }
    }

    /**
     * 下载apk并安装
     *
     */
    private class DownLoadFile extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                int fileLenth = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream());
                File filedir = new File(APKDIR);
                if (!filedir.exists()){
                    filedir.mkdirs();
                }
                File file = new File(APKPATH);
                if (!file.exists()){
                    file.createNewFile();
                }
                OutputStream output = new FileOutputStream(APKPATH);

                byte data[]  = new byte[1024];
                long total = 0;
                int count;
                while((count=input.read(data))!=-1){
                    if(isCancelled()){
                        return null;
                    }
                    total+=count;
                    publishProgress((int)(total*100/fileLenth));
                    Message message = new Message();
                    message.obj = (total*100/fileLenth)+"%";
                    handler.sendMessage(message);
                    output.write(data,0,count);
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
                updatedialog.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //progressDialog.dismiss();
            updatedialog.cancel();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if(Build.VERSION.SDK_INT>=24) { //判读版本是否在7.0以上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//大于等于8.0
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, 10010);
                    //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                    Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName(), new File(APKPATH));
                    //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                }
            }else{
                intent.setDataAndType(Uri.fromFile(new File(APKPATH)),
                        "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        }
    }
}
