package com.liyh.recyclerviewlibrary;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;

import com.liyh.recyclerviewlibrary.interfac.SwipeRefreshListener;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 28 日
 * @time 16 时 26 分
 * @descrip :下拉刷新的帮助类
 */
public class SwipeRefreshHelper {
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshListener swipeRefreshListener;

    public static SwipeRefreshHelper createSwipeRefreshHelper(SwipeRefreshLayout swipeRefreshLayout) {
        return new SwipeRefreshHelper(swipeRefreshLayout);
    }

    private SwipeRefreshHelper(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        init();
    }

    private void init() {
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (swipeRefreshListener != null) {
                swipeRefreshListener.onRefresh();
            }
        });

    }

    public void setSwipeRefreshListener(SwipeRefreshListener swipeRefreshListener) {
        this.swipeRefreshListener = swipeRefreshListener;
    }
}
