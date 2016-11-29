package com.anke.vehicle.xutils30demo;


import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
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
        x.http().get(params, new Callback.CacheCallback<String>() {
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

            @Override
            public boolean onCache(String result) {
                return false;
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
            x.http().post(params, new Callback.CacheCallback<String>() {
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

                @Override
                public boolean onCache(String result) {
                    return false;
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

    public interface XListener {
        void onResult(String result);
    }

}
