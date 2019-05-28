package com.liyh.butterknifelibrary;

import android.app.Activity;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 27 日
 * @time 01 时 37 分
 * @descrip :
 */
public class ButterKnife {
    public static void bind(Activity activity) {
        String className = activity.getClass().getName() + "$ViewBinder";
        try {
            Class<?> aClass = Class.forName(className);
            ViewBinder viewBinder = (ViewBinder) aClass.newInstance();
            viewBinder.bind(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
