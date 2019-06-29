package com.liyh.app;

import android.app.Application;

import com.liyh.httplibrary.HttpHelper;
import com.liyh.httplibrary.OkHttpProcessor;
import com.liyh.networklistenerlibrary.NetworkManager;


public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        instance = this;
//        DoubleLruCache.getInstance(this);
        NetworkManager.getDefault().init(this);
//        HttpHelper.getInstance().init(new VolleyHttpProcessor(this));
        HttpHelper.getInstance().init(new OkHttpProcessor());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        NetworkManager.getDefault().unRegistAllObserver();

    }
}
