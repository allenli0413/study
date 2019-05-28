package com.liyh.app.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.liyh.recyclerviewlibrary.RViewHelper;
import com.liyh.recyclerviewlibrary.interfac.RViewCreate;
import com.liyh.recyclerviewlibrary.interfac.SwipeRefreshListener;

import java.util.List;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 28 日
 * @time 16 时 17 分
 * @descrip :
 */
abstract class BaseRvActivity extends AppCompatActivity implements RViewCreate, SwipeRefreshListener {

    protected RViewHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new RViewHelper.Builder(this, this).build();
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public SwipeRefreshLayout createSwipeRefreshLayout() {
        return null;
    }

    @Override
    public RecyclerView createRecyclerView() {
        return null;
    }


    @Override
    public boolean isSupportPaging() {
        return false;
    }

    protected void notifyAdapterDataSetChanged(List datas){
        helper.notifyAdapterDataSetChanged(datas);
    }

}
