package com.liyh.permission.listener;

import android.support.annotation.Nullable;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 09 日
 * @time 22 时 56 分
 * @descrip :
 */
public interface RequestPermission<T> {
    //请求权限组
    void requestPermission(T target, String[] permissions);

    //授权结果返回
    void onRequestPermissionsResult(T target, int requestCode, @Nullable int[] grantResluts);
}
