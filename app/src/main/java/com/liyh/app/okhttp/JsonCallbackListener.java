package com.liyh.app.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 21 日
 * @time 20 时 39 分
 * @descrip :
 */
public class JsonCallbackListener<T> implements ICallbackListener {

    private Class<T> responseClass;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private IJsonDataListener jsonDataLinstener;

    public JsonCallbackListener(Class<T> responseClass, IJsonDataListener jsonDataLinstener) {
        this.responseClass = responseClass;
        this.jsonDataLinstener = jsonDataLinstener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        String response = getContent(inputStream);
        Log.e("===", "onSuccess: " + response);
        final T result = JSON.parseObject(response, responseClass);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                jsonDataLinstener.onSuccess(result);
            }
        });
    }

    private String getContent(InputStream inputStream) {
        String content = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                //                e.printStackTrace();
                System.out.println(e.toString());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            content = sb.toString();
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println(e.toString());
        }
        return content;
    }

    @Override
    public void onFailure() {

    }
}
