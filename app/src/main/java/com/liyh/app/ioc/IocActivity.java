package com.liyh.app.ioc;

import android.view.View;
import android.widget.TextView;

import com.liyh.app.R;
import com.liyh.ioclibrary.annotations.ContentView;
import com.liyh.ioclibrary.annotations.InjectView;
import com.liyh.ioclibrary.annotations.OnLongClick;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 07 日
 * @time 22 时 27 分
 * @descrip :
 */

@ContentView(R.layout.activity_ioc)
public class IocActivity extends BaseIcoActivity {

    @InjectView(R.id.tv1)
    private TextView tv1;

    @InjectView(R.id.tv2)
    private TextView tv2;

    @Override
    protected void onResume() {
        super.onResume();
//        tv1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showToast("点击tv1");
//            }
//        });

//        tv2.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                return false;
//            }
//        });
    }

//    @OnClick(R.id.tv1)
//    public void changeText() {
//        tv2.setText("文本2");
//    }

    @OnLongClick({R.id.tv1, R.id.tv2})
    public boolean longEvent(View view) {
        switch (view.getId()) {
            case R.id.tv1:
//                tv2.setText("我是tv2");
                showToast("长按tv1");
                break;
            case R.id.tv2:
//                tv1.setText("我是tv1");
                showToast("长按tv2");
                break;
        }
        return true;
    }
}
