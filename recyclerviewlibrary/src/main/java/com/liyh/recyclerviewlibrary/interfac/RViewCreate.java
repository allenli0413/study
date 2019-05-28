package com.liyh.recyclerviewlibrary.interfac;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.liyh.recyclerviewlibrary.base.RViewAdapter;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 28 日
 * @time 16 时 19 分
 * @descrip :创建RViewHelper所需要的信息，它的实现类很方便创建RViewHelper对象
 */
public interface RViewCreate<T> {
    Context context();

    //创建SwipeRefreshLayout，下拉
    SwipeRefreshLayout createSwipeRefreshLayout();

    //创建RecyclerView
    RecyclerView createRecyclerView();

    //创建创建RecyclerView的Adapter
    RViewAdapter createRViewAdapter();

    //是否支持分页
    boolean isSupportPaging();
}
