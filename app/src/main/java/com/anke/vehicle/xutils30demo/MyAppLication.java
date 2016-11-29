package com.anke.vehicle.xutils30demo;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class MyAppLication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
