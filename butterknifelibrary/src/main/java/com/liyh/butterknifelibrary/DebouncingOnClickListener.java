package com.liyh.butterknifelibrary;

import android.view.View;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 27 日
 * @time 01 时 41 分
 * @descrip :
 */
public abstract class DebouncingOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        doClick();
    }

    protected abstract void doClick();
}
