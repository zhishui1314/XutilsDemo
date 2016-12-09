package com.anke.vehicle.xutils30demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anke.vehicle.xutils30demo.Views.X3Utils;

import org.xutils.common.Callback;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    String getUrl = "https://api.github.com/repos/square/okhttp/contributors";
    String getsUrl = "https://www.sogou.com";
    String url = "http://172.16.100.58:8080/1.json";
    String imgUrl = "https://www.baidu.com/img/bd_logo1.png";
    String video = "http://172.16.100.58:8080/AnchorVehicle.apk";

    private TextView tvResult;
    private ProgressDialog progressDialog;
    private ImageView imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = (TextView) findViewById(R.id.tvResult);
        imgs = (ImageView) findViewById(R.id.imgs);
    }

    /**
     * get请求
     *
     * @param view
     */
    public void xg(View view) {
        XUtils.getMethod(this,url, new XUtils.XListener() {
            @Override
            public void onResult(String result) {
                if (!TextUtils.isEmpty(result)) {
                    tvResult.setText(result);
                }
            }
        });


    }

    /**
     * post请求
     *
     * @param view
     */
    public void xp(View view) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", "QueryStop");
        XUtils.postMethod(url, map, new XUtils.XListener() {
            @Override
            public void onResult(String result) {
                if (!TextUtils.isEmpty(result)) {
                    tvResult.setText(result);
                }
            }
        });
    }

    /**
     * 文件的下载
     * @param view
     */
    public void xx(View view) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AnchorVehicle.apk";
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("努力下载中。。。");
        XUtils.downLoad(video, path, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                progressDialog.show();
             progressDialog.setProgress((int) (current * 100/total));
            }

            @Override
            public void onSuccess(File result) {
                Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(result),
                        "application/vnd.android.package-archive");
                startActivity(intent);


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 加载具有缓存功能的图片
     * @param view
     */
    public void xt(View view) {
        XUtils.dealWith(imgUrl,imgs);
    }
}


