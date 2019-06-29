package com.liyh.httplibrary;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 08 日
 * @time 13 时 35 分
 * @descrip :
 */
public interface IHttpCallback {

    void onSuccess(String respone);

    void onDailure();
}
