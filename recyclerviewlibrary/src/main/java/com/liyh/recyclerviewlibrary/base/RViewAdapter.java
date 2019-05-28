package com.liyh.recyclerviewlibrary.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.liyh.recyclerviewlibrary.holder.RViewHolder;
import com.liyh.recyclerviewlibrary.interfac.OnItemListener;
import com.liyh.recyclerviewlibrary.interfac.RVItemView;
import com.liyh.recyclerviewlibrary.manager.RViewItemManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 28 日
 * @time 14 时 43 分
 * @descrip : RecyclerView 多item样式适配器
 */
public class RViewAdapter<T> extends RecyclerView.Adapter<RViewHolder> {

    private OnItemListener<T> itemListener;//条目点击监听
    private RViewItemManager itemStyles; //item类型管理
    private List<T> datas;//数据源

    //单样式
    public RViewAdapter(List<T> datas) {
        if (datas == null)
            datas = new ArrayList<>();
        this.datas = datas;

        this.itemStyles = new RViewItemManager();
    }

    //多样式
    public RViewAdapter(List<T> datas, RVItemView<T>... items) {
        if (datas == null)
            datas = new ArrayList<>();
        this.datas = datas;
        itemStyles = new RViewItemManager();
        //如果是多样式，需要添加到item类型管理器中
        addAllItemStyle(items);
    }

    private void addAllItemStyle(RVItemView<T>[] items) {
        if (items != null){
            for (RVItemView<T> item : items) {
                addItemStyle(item);
            }
        }
    }

    public void addItemStyle(RVItemView<T> item) {
        itemStyles.addStyle(item);
    }

    public void addDatas(List<T> datas) {
        if (datas == null) return;
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void updateDatas(List<T> datas) {
        if (datas == null) return;
        this.datas = datas;
        notifyDataSetChanged();
    }

    private boolean hasMultiType() {
        return itemStyles.getItemViewStyleCount() > 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasMultiType()) return itemStyles.getItemType(datas.get(position), position);
        return super.getItemViewType(position);
    }

    //根据布局类型创建不同item的ViewHolder
    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RVItemView<T> item = itemStyles.getItem(viewType);
        int itemLayout = item.getItemLayout();
        RViewHolder holder = RViewHolder.createViewHolder(viewGroup.getContext(), viewGroup, itemLayout);
        if (item.openClick()) {
            setListener(holder);
        }
        return holder;
    }

    private void setListener(final RViewHolder holder) {
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (itemListener != null) {
                    itemListener.onClickListener(v, datas.get(position), position);
                }
            }
        });

        holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = holder.getAdapterPosition();
                if (itemListener != null) {
                    itemListener.onClickLongListener(v, datas.get(position), position);
                    return true;
                }
                return false;
            }

        });
    }

    //将数据绑定到item的ViewHolder的控件
    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int position) {
        convert(holder, position);
    }

    private void convert(RViewHolder holder, int position) {
        itemStyles.convert(holder,datas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void setItemListener(OnItemListener<T> itemListener) {
        this.itemListener = itemListener;
    }
}
