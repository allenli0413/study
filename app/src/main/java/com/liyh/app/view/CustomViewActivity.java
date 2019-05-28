package com.liyh.app.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.liyh.app.R;


/**
 * @author Yahri Lee
 * @date 2019 年 05 月 26 日
 * @time 22 时 54 分
 * @descrip :
 */
public class CustomViewActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
    }
}
