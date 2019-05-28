package com.liyh.app.network;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.liyh.app.R;
import com.liyh.networklistenerlibrary.NetworkManager;
import com.liyh.networklistenerlibrary.annotation.Network;
import com.liyh.networklistenerlibrary.type.NetType;
import com.liyh.networklistenerlibrary.utils.Constants;


/**
 * @author Yahri Lee
 * @date 2019 年 05 月 26 日
 * @time 10 时 58 分
 * @descrip :
 */
public class NetWorkChangeActivity extends Activity {
    private static final String TAG = Constants.LOG_TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_change);
        //这册回调
//        NetworkManager.getDefault().setListener(this);
        NetworkManager.getDefault().registObserver(this);
    }

    @Network(netType = NetType.WIFI)
    public void onConnect(NetType type) {
        switch (type) {
            case AUTO:
                Log.e(TAG, "onConnect: " + "网络已链接 " + type.name());
                break;
            case WIFI:
                Log.e(TAG, "onConnect: wifi网络 " + type.name());
                break;
            case CMNET:
            case CMWAP:
                Log.e(TAG, "onConnect: 移动网络 " + type.name());
                break;
            case NONE:
                Log.e(TAG, "onConnect: 没有网络 " + type.name());
        }
    }

    @Network
    public void netWork(NetType netType) {
        Log.e(TAG, "netWork:  " + netType.name());

    }

    @Network(netType = NetType.CMNET)
    public void netWorkCmnet(NetType netType) {
        Log.e(TAG, "netWorkCmnet:  " + netType.name());

    }
    @Network(netType = NetType.CMWAP)
    public void netWorkCmwap(NetType netType) {
        Log.e(TAG, "netWorkCmwap:  " + netType.name());

    }
    @Network(netType = NetType.NONE)
    public void netWorkNone(NetType netType) {
        Log.e(TAG, "没有网络  " + netType.name());

    }
    public void netWorkH(NetType netType) {
        Log.e(TAG, "netWorkH  " + netType.name());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getDefault().unRegistObserver(this);
    }
}
