package com.anke.vehicle.xutils30demo.Views;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.Map;

/**
 * Created by Administrator on 2016/12/9 0009.
 */
public class X3Utils {

    /**
     * get异步请求
     *
     * @param url
     * @param xl
     */
    public static void getMethod(String url, final XListener xl){
        RequestParams requestParams = new RequestParams(url);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("onSuccess",result);
                xl.onResult(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("onError",ex.getMessage());
                xl.onResult(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("onError",cex.getMessage());
                xl.onResult(cex.getMessage());
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
    public interface XListener{
        void onResult(String result);
    }
}
