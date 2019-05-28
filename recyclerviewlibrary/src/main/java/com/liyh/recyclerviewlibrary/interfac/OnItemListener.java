package com.liyh.recyclerviewlibrary.interfac;

import android.view.View;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 28 日
 * @time 14 时 12 分
 * @descrip :条目监听
 */
public interface OnItemListener<T> {
    void onClickListener(View view, T entiy, int positon);

    void onClickLongListener(View view, T entity, int positon);
}
