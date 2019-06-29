package com.liyh.app.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.liyh.app.R;
import com.liyh.app.okhttp.HttpUtil;
import com.liyh.app.okhttp.IJsonDataListener;
import com.liyh.app.okhttp.JsonResult;
import com.liyh.app.other.MyFragment;
import com.liyh.app.other.MyFragment2;

import java.util.concurrent.ScheduledExecutorService;

public class MainTestActivity extends AppCompatActivity {
    private static final String TAG = "MainTestActivity";
    private CircleProgressBar color_progress_view;

    private int stepCount = 0;
    private ScheduledExecutorService scheduledExecutorService;
//    private String url = "http://v.juhe.cn/historyWeather/citys?province_id=2&key=assdddd909dfdss";
    private String url = "http://xxxx";
    public MyFragment fragment;
    public MyFragment2 myFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
//        KLineView klv_line = (KLineView) findViewById(R.id.klv_line);
//        klv_line.setMaxValue(28000);
//        LinkedHashMap<String, Float> datas = new LinkedHashMap<>();
//        datas.put("02-19", 6880f);
//        datas.put("02-20", 3000f);
//        datas.put("02-21", 0f);
//        datas.put("02-22", 8800f);
//        datas.put("前天", 18730f);
//        datas.put("昨天", 9765f);
//        datas.put("今天", 2700f);
////        klv_line.setDatas(datas);
//        klv_line.setyCount(4);
//        klv_line.setOnPointClickListener(new KLineView.OnPointClickListener() {
//            @Override
//            public void onClick(String xValue, float yValue) {
//                Toast.makeText(MainTestActivity.this, "x = " + xValue + ",y =" + yValue, Toast.LENGTH_SHORT).show();
//
//            }
//
//        });
//        color_progress_view = (CircleProgressBar) findViewById(R.id.color_progress_view);
//        color_progress_view.setMaxStepNum(50000);
//
////        color_progress_view.update(100, 200);
////        color_progress_view.setAnimationTime(3000);
//        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    if (stepCount >= 20000) {
//                        scheduledExecutorService.shutdown();
//                    } else {
//                        stepCount += 100;
//                        Log.i(TAG, "stepCount = " + stepCount);
//                        color_progress_view.update(stepCount, 10);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 1, 10, TimeUnit.MILLISECONDS);
//        LruCache<Object, Object> lruCache = new LruCache<>(1024);
//        Map<Object, Object> snapshot = lruCache.snapshot();
//        sendRequest();

    }

    private void sendRequest() {
        HttpUtil.sendJsonRequest(url, null, JsonResult.class, new IJsonDataListener<JsonResult>() {

            @Override
            public void onSuccess(JsonResult result) {
                Log.e(TAG, "onSuccess: " + result.toString() );
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private Fragment currentFragment = null;

    public void changeFragment(Fragment f){
        if (currentFragment == null || f == null || currentFragment == f){
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (!f.isAdded()){
            ft.hide(currentFragment).add(R.id.fl_container,f);
        } else {
            ft.hide(currentFragment).show(f);
        }
        ft.commitAllowingStateLoss();
        currentFragment = f;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        fragment = new MyFragment();
//        myFragment2 = new MyFragment2();
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.fl_container,fragment).commitAllowingStateLoss();
//        currentFragment = fragment;
    }



}
