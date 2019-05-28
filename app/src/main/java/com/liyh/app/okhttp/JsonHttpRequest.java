package com.liyh.app.okhttp;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 21 日
 * @time 20 时 17 分
 * @descrip :
 */
public class JsonHttpRequest implements IHttpRequest {
    private String url;
    private byte[] date;
    private ICallbackListener linstener;
    private HttpURLConnection mConnection;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(byte[] data) {
        this.date = data;
    }

    @Override
    public void setLinsterer(ICallbackListener callbackLinstener) {
        this.linstener = callbackLinstener;
    }

    @Override
    public void excute() {
        //访问网络的具体操作
        URL url = null;
        try {
            url = new URL(this.url);
            mConnection = (HttpURLConnection) url.openConnection();
            mConnection.setConnectTimeout(6000);
            mConnection.setReadTimeout(3000);
            mConnection.setRequestMethod("POST");
            mConnection.setInstanceFollowRedirects(true); //成员函数，仅作用于当前函数，射者这个链接是否可以被重定向
            mConnection.setUseCaches(false);
            mConnection.setDoInput(true);
            mConnection.setDoOutput(true);
            mConnection.setRequestProperty("Content-Type", "application/json;chatset=UTF-8");
            mConnection.connect();

            OutputStream os = mConnection.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            bos.write(this.date);
            bos.flush();
            os.close();
            bos.close();

            if (mConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = mConnection.getInputStream();
                linstener.onSuccess(inputStream);
            } else {
                throw new RuntimeException("请求失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("请求失败");

        } finally {
            mConnection.disconnect();
        }
    }
}
