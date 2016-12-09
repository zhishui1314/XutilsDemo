package com.anke.vehicle.xutils30demo;


import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.common.util.DensityUtil;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.text.Normalizer;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
public class XUtils {
    /**
     * get异步请求
     *
     * @param url
     * @param xListener
     */
    public static void getMethod(String url, final XListener xListener) {

        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("get请求onSuccess", result);
                xListener.onResult(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("get请求onError", ex.getMessage() + "!!!!!!!!");
                    xListener.onResult("");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("get请求onCancelled", cex.getMessage());
                xListener.onResult("");
            }

            @Override
            public void onFinished() {

            }

        });
    }

    /**
     * post异步请求
     */
    public static void postMethod(String url, Map<String, String> map, final XListener xListener) {
        RequestParams params = new RequestParams(url);
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    xListener.onResult(result);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    xListener.onResult("");
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    xListener.onResult("");
                }

                @Override
                public void onFinished() {

                }

            });
        }

    }

    /**
     * 上传文件
     */
    public static void upLoadFiles(String url, Map<String, Objects> map, XListener xListener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("username", "wingli");// 添加到请求body体的参数, 只有POST, PUT, PATCH, DELETE请求支持.
        params.setMultipart(true);//使用multipart表单上传文件
        params.addBodyParameter("file", new File("/sdcard/1.avi"), null); // 如果文件没有扩展名, 最好设置contentType参数.
        params.addBodyParameter("file", new File("/sdcard/2.jpg"), null); // 如果文件没有扩展名, 最好设置contentType参数.
        params.addBodyParameter("file", new File("/sdcard/3.jpg"), null); // 如果文件没有扩展名, 最好设置contentType参数.
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("onSuccess=" + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("onError=" + ex);
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
     * 下载文件
     * @param url
     * @param path
     * @param callback
     */
    public static void downLoad(String url, String path, Callback.CommonCallback<File> callback) {
        RequestParams params = new RequestParams(url);
        params.setAutoResume(true);//设置是否在下载是自动断点续传
        params.setAutoRename(false);//设置是否根据头信息自动命名文件
        params.setSaveFilePath(path);
        params.setExecutor(new PriorityExecutor(2, true));//自定义线程池,有效的值范围[1, 3], 设置为3时, 可能阻塞图片加载.
        params.setCancelFast(true);//是否可以被立即停止.
        x.http().get(params, callback);
    }

    /**
     * 具有缓存功能的图片
     * @param url
     * @param imageView
     */
 public  static void dealWith(String url, ImageView imageView){
     ImageOptions imageOptions = new ImageOptions.Builder()
             .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))//图片大小
//             .setRadius(DensityUtil.dip2px(5))//ImageView圆角半径
             .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
             .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
             .setLoadingDrawableId(R.mipmap.ic_launcher)//加载中默认显示图片
             .setFailureDrawableId(R.mipmap.ic_launcher)//加载失败后默认显示图片
             .build();
            x.image().bind(imageView, url,imageOptions);
 }
    public interface XListener {
        void onResult(String result);
    }

}
