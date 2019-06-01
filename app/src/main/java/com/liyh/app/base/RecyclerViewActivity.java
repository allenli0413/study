package com.liyh.app.base;

import com.liyh.recyclerviewlibrary.base.RViewAdapter;
import com.liyh.recyclerviewlibrary.holder.RViewHolder;
import com.liyh.recyclerviewlibrary.interfac.RVItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 01 日
 * @time 16 时 36 分
 * @descrip :
 */
public class RecyclerViewActivity extends BaseRvActivity {
    List<String > datas = new ArrayList<>();
    @Override
    public RViewAdapter createRViewAdapter() {
        return new RViewAdapter(datas, new RVItemView() {
            @Override
            public int getItemLayout() {
                return 0;
            }

            @Override
            public boolean openClick() {
                return false;
            }

            @Override
            public boolean isItemView(Object entity, int position) {
                return false;
            }

            @Override
            public void convert(RViewHolder holder, Object entity, int position) {
//                holder.getView(R.id.as).set
            }
        });
    }

    @Override
    public void onRefresh() {
        notifyAdapterDataSetChanged(datas);
    }
}
