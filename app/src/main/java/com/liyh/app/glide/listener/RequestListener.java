package com.liyh.app.glide.listener;

import android.graphics.Bitmap;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 23 日
 * @time 21 时 45 分
 * @descrip :
 */
public interface RequestListener {
    boolean onSuccess(Bitmap bitmap);

    void onfailure();
}
