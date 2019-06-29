package com.liyh.permission;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.liyh.permission.listener.RequestPermission;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 09 日
 * @time 22 时 54 分
 * @descrip :
 */
public class PermissionManager {
    private static final PermissionManager instance = new PermissionManager();

    private PermissionManager() {
    }

    public static PermissionManager getDefault() {
        return instance;
    }

    public void requestPermission(Activity target, String[] permissions) {
        String className = target.getClass().getName() + "$Permissions";
        try {
            //假设能获取到这个不存在的类
            Class<?> aClass = Class.forName(className);
            RequestPermission permission = (RequestPermission) aClass.newInstance();
            permission.requestPermission(target, permissions);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void onRequestPermissionsResult(Activity target, int requestCode, @Nullable int[] grantResluts) {
        String className = target.getClass().getName() + "$Permissions";
        try {
            //假设能获取到这个不存在的类
            Class<?> aClass = Class.forName(className);
            RequestPermission permission = (RequestPermission) aClass.newInstance();
            permission.onRequestPermissionsResult(target, requestCode, grantResluts);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
