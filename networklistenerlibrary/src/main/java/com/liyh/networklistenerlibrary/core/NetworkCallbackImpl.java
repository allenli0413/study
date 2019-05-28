package com.liyh.networklistenerlibrary.core;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 26 日
 * @time 16 时 29 分
 * @descrip :
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {
    private static final String TAG = "NetworkCallbackImpl";
    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Log.d(TAG, "onAvailable() called with: network = [" + network.toString() + "]");
    }

    @Override
    public void onLosing(Network network, int maxMsToLive) {
        super.onLosing(network, maxMsToLive);
        Log.d(TAG, "onLosing() called with: network = [" + network.toString() + "], maxMsToLive = [" + maxMsToLive + "]");
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Log.d(TAG, "onLost() called with: network = [" + network.toString() + "]");
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)){
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                Log.d(TAG, "onCapabilitiesChanged() called with: network = [" + network + "], networkCapabilities = [" + networkCapabilities + "]");
            } else{
                Log.e(TAG, "onCapabilitiesChanged: 其他网络" + networkCapabilities.toString() );
            }
        }
    }
}
