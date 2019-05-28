package com.liyh.networklistenerlibrary;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.IntentFilter;
import android.util.Log;

import com.liyh.networklistenerlibrary.utils.Constants;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 26 日
 * @time 10 时 45 分
 * @descrip :
 */
public class NetworkManager {
    private static volatile NetworkManager instance;
    private NetworkChangeReceiver receiver;

    public Application getApplication() {
        if (application == null) {
            throw new RuntimeException("NetworkManager.getDefault().init()未初始化");
        }
        return application;
    }

    private Application application;

    //单例实现
    private NetworkManager() {
        receiver = new NetworkChangeReceiver();
    }

    public static NetworkManager getDefault() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    @SuppressLint("MissingPermission")
    public synchronized void init(Application application) {
        this.application = application;
        //动态注册
        Log.e(Constants.LOG_TAG, "init: 注册广播" );
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
        application.registerReceiver(receiver, intentFilter);


        // 第二种方式监听，不通过广播
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ConnectivityManager.NetworkCallback networkCallback = new NetworkCallbackImpl();
//            NetworkRequest.Builder builder = new NetworkRequest.Builder();
//            NetworkRequest request = builder.build();
//            ConnectivityManager cmgr = (ConnectivityManager) NetworkManager.getDefault().getApplication()
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            if (cmgr != null) cmgr.registerNetworkCallback(request, networkCallback);
            // if (cmgr != null) cmgr.unregisterNetworkCallback(networkCallback);
//        } else {
//            动态注册
//            IntentFilter intentFilter = new IntentFilter();
//            intentFilter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
//            application.registerReceiver(receiver, intentFilter);
//        }
    }

    //    public void setListener(NetChangeObserver listener) {
//        receiver.setListener(listener);
//    }
    public void registObserver(Object register) {
        receiver.registerObserver(register);
    }

    public void unRegistObserver(Object register) {
        receiver.unRegisterObserver(register);
    }
    public void unRegistAllObserver() {
        receiver.unRegisterAllObserver();
    }
}
