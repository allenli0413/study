package com.liyh.app.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liyh.app.R;


/**
 * @author Yahri Lee
 * @date 2019 年 05 月 23 日
 * @time 18 时 28 分
 * @descrip :
 */
public class MyFragment2 extends Fragment {
    private static final String TAG = "MyFragment2";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test2, null);
        ImageView btn = view.findViewById(R.id.iv_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
            }
        });
        return view;
    }
}
