package com.liyh.app.okhttp;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 21 日
 * @time 19 时 49 分
 * @descrip :
 */
public class HttpTask<T> implements Runnable, Delayed {

    private IHttpRequest mHttpRequest;

    public HttpTask(String url, T data, IHttpRequest request, ICallbackListener linstener) {
        mHttpRequest = request;
        request.setUrl(url);
        request.setLinsterer(linstener);
        String content = JSON.toJSONString(data);
        request.setData(content.getBytes());
    }

    @Override
    public void run() {
        try {
            mHttpRequest.excute();
        } catch (Exception e) {
            e.printStackTrace();
            ThreadPoolManager.getInstance().addDelayTask(this);
        }
    }

    private long delayTime;
    private int retryCount;

    public long getDelayTime() {
        return delayTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = System.currentTimeMillis() + delayTime;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public long getDelay(@NonNull TimeUnit unit) {
        return unit.convert(this.delayTime - System.currentTimeMillis(),TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(@NonNull Delayed o) {
        return 0;
    }
}
