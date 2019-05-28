package com.liyh.app.okhttp;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 21 日
 * @time 19 时 36 分
 * @descrip :
 */
public interface IHttpRequest {
    void setUrl(String url);

    void setData(byte[] data);

    void setLinsterer(ICallbackListener callbackLinstener);

    void excute();
}
