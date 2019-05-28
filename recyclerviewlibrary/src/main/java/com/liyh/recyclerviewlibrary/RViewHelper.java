package com.liyh.recyclerviewlibrary;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.liyh.recyclerviewlibrary.base.RViewAdapter;
import com.liyh.recyclerviewlibrary.interfac.RViewCreate;
import com.liyh.recyclerviewlibrary.interfac.SwipeRefreshListener;

import java.util.List;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 28 日
 * @time 14 时 15 分
 * @descrip :
 */
public class RViewHelper<T> {
    private Context context;
    private SwipeRefreshLayout swipeRefresh;
    private SwipeRefreshHelper swipeRefreshHelper;
    private RecyclerView recyclerView;
    private RViewAdapter rViewAdapter;
    private final int startPageNum = 1;
    private int currenPageNum;
    private boolean isSupportPaging;
    private SwipeRefreshListener listener;


    public RViewHelper(Builder<T> builder) {
        context = builder.create.context();
        swipeRefresh = builder.create.createSwipeRefreshLayout();
        recyclerView = builder.create.createRecyclerView();
        rViewAdapter = builder.create.createRViewAdapter();
        isSupportPaging = builder.create.isSupportPaging();
        listener = builder.listener;
        currenPageNum = startPageNum;
        if (swipeRefresh !=null){
            swipeRefreshHelper = SwipeRefreshHelper.createSwipeRefreshHelper(swipeRefresh);
        }

        init();
    }

    private void init() {
        //初始化RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (swipeRefreshHelper != null){
            swipeRefreshHelper.setSwipeRefreshListener(new SwipeRefreshListener() {
                @Override
                public void onRefresh() {
                    if (swipeRefresh != null && swipeRefresh.isRefreshing()){
                        //重置页码
                        currenPageNum = startPageNum;
                        if (listener !=null){
                            listener.onRefresh();
                        }
                    }
                }
            });
        }
    }

    public void notifyAdapterDataSetChanged(List datas) {
        if (currenPageNum == startPageNum){
            rViewAdapter.updateDatas(datas);
        } else {
            rViewAdapter.addDatas(datas);
        }
        if (isSupportPaging){

        }
    }

    public static class Builder<T>{
        private RViewCreate<T> create;//控件初始化
        private SwipeRefreshListener listener;//下拉刷新监听

        public Builder(RViewCreate<T> create, SwipeRefreshListener swipeRefreshListener) {
            this.create = create;
            this.listener = swipeRefreshListener;
        }

        public RViewHelper build(){
            return new RViewHelper(this);
        }
    }
}
