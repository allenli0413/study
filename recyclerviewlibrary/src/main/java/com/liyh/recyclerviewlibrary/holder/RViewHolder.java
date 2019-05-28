package com.liyh.recyclerviewlibrary.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 28 日
 * @time 14 时 11 分
 * @descrip :Adapter中的ViewHolder
 */
public class RViewHolder extends RecyclerView.ViewHolder {
    //一个条目布局的多个控件
    private SparseArray<View> mViews;  //view控件集合
    private View mConvertView;//当前的View控件

    private RViewHolder(@NonNull View itemView) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static RViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new RViewHolder(itemView);
    }

    //获取某个具体的控件
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            //key: R.id.xx  value: view
            mViews.put(viewId, view);
        }

        return (T) view;
    }

    //对外提供:条目的点击view
    public View getConvertView(){
        return mConvertView;
    }
}
