package com.liyh.app.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liyh.app.view.MainTestActivity;
import com.liyh.app.R;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 23 日
 * @time 18 时 28 分
 * @descrip :
 */
public class MyFragment extends Fragment {

    private MainTestActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, null);
        ImageView btn = view.findViewById(R.id.iv_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.changeFragment(new MyFragment2());
            }
        });
        TextView jump = view.findViewById(R.id.tv_jump);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.changeFragment(activity.myFragment2);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity = (MainTestActivity) getActivity();
    }
}
