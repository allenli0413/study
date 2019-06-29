package com.liyh.httplibrary;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 08 日
 * @time 14 时 00 分
 * @descrip :
 */
public class OkHttpProcessor implements HttpProcessor {

    private OkHttpClient client;
    private Handler mHandler;

    public OkHttpProcessor() {
        client = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(String url, Map<String, Object> params, final IHttpCallback callback) {
        FormBody requestBody = createRequestBody(params);
        final Request request = new Request.Builder().url(url).post(requestBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            callback.onSuccess(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private FormBody createRequestBody(Map<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.add(entry.getKey(), (String) entry.getValue());
            }
        }
        return builder.build();
    }
}
