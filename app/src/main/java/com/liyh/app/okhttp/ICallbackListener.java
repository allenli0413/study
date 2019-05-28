package com.liyh.app.okhttp;

import java.io.InputStream;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 21 日
 * @time 19 时 39 分
 * @descrip :
 */
public interface ICallbackListener {
    void onSuccess(InputStream inputStream);

    void onFailure();
}
