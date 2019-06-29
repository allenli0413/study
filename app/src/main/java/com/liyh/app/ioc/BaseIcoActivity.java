package com.liyh.app.ioc;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.liyh.ioclibrary.InjectManager;


/**
 * @author Yahri Lee
 * @date 2019 年 06 月 07 日
 * @time 22 时 28 分
 * @descrip :
 */
public class BaseIcoActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.getInstance().inject(this);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
