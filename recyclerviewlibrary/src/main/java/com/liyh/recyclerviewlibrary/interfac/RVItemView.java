package com.liyh.recyclerviewlibrary.interfac;

import com.liyh.recyclerviewlibrary.holder.RViewHolder;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 28 日
 * @time 14 时 15 分
 * @descrip :某一类的Item对象（属性/特性），可以当成javabean
 */
public interface RVItemView<T> {
    //获取item布局
    int getItemLayout();

    //是否开启点击
    boolean openClick();

    //哪个条目用哪个布局
    //是否为当前item
    boolean isItemView(T entity, int position);

    //将item控件于所需要显示的数据绑定
    void convert(RViewHolder holder, T entity, int position);
}
