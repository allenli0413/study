package com.liyh.recyclerviewlibrary.manager;

import android.support.v4.util.SparseArrayCompat;

import com.liyh.recyclerviewlibrary.holder.RViewHolder;
import com.liyh.recyclerviewlibrary.interfac.RVItemView;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 28 日
 * @time 14 时 45 分
 * @descrip : 多类型，多样式的管理器
 */
public class RViewItemManager<T> {
    //所有RVItemView的集合，key：viewType  value：RVItemView（某一类条目对象）
    private SparseArrayCompat<RVItemView<T>> styles = new SparseArrayCompat();

    public void addStyle(RVItemView<T> item) {
        styles.put(styles.size(), item);
    }

    public int getItemViewStyleCount() {
        return styles.size();
    }

    //根据数据源和位置返回某item的类型（styles的key）
    public int getItemType(T t, int position) {
        int size = styles.size();
        for (int i = 0; i < size; i++) {
            RVItemView<T> itemView = styles.valueAt(i);
            if (itemView.isItemView(t, position)) {
                return styles.keyAt(i);
            }
        }
        throw new IllegalArgumentException("该条目没有匹配的RVItemView条目类型");
    }

    public RVItemView<T> getItem(int viewType) {
        return styles.get(viewType);
    }

    //将试图和数据源绑定显示
    public void convert(RViewHolder holder, T t, int position) {
        int size = styles.size();
        for (int i = 0; i < size; i++) {
            RVItemView<T> itemView = styles.valueAt(i);
            if (itemView.isItemView(t, position)) {
                itemView.convert(holder, t, position);
                return;
            }
        }

        throw new IllegalArgumentException("该条目没有匹配的RVItemView条目类型");
    }
}
