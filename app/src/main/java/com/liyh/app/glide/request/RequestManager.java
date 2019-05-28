package com.liyh.app.glide.request;

import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 23 日
 * @time 21 时 44 分
 * @descrip :
 */
public class RequestManager {
    private static RequestManager instance;

    private RequestManager() {
        start();
    }

    public static RequestManager getInstance() {
        if (instance == null) {
            synchronized (RequestManager.class) {
                if (instance == null) {
                    instance = new RequestManager();
                }
            }
        }
        return instance;
    }

    private LinkedBlockingQueue<BitmapRequest> requestQueue = new LinkedBlockingQueue<>();
    private BitmapDispatcher[] dispatchers;


    public void addBitmapRequest(BitmapRequest request) {
        if (!requestQueue.contains(request)) {
            requestQueue.add(request);
        } else {
            Log.i("err", "任务已存在，不用再次添加。");
        }
    }

    private void start() {
        stop();
        //获取系统可用线程个数
        int dispatcherCount = Runtime.getRuntime().availableProcessors();
        dispatchers = new BitmapDispatcher[dispatcherCount];
        for (int i = 0; i < dispatcherCount; i++) {
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher(requestQueue);
            bitmapDispatcher.run();
            dispatchers[i] = bitmapDispatcher;
        }
    }

    private void stop() {
        if (dispatchers != null && dispatchers.length > 0) {
            for (BitmapDispatcher dispatcher : dispatchers) {
                dispatcher.interrupt();
            }
        }
    }
}
