package com.liyh.app.okhttp;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 21 日
 * @time 20 时 57 分
 * @descrip :
 */
public interface IJsonDataListener<T> {
    void onSuccess(T result);

    void onFailure();
}
