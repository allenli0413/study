package com.liyh.app.glide.core;

import android.content.Context;

import com.liyh.app.glide.request.BitmapRequest;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 23 日
 * @time 21 时 44 分
 * @descrip :
 */
public class Glide {
    public static BitmapRequest with(Context context) {
        return new BitmapRequest(context);
    }
}
