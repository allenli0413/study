package com.liyh.pluginlibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 28 日
 * @time 19 时 05 分
 * @descrip :
 */
public interface IPlugin {

    int FROM_INTERNAL = 0;
    int FROM_EXTERNAL = 1;

    void attach(Activity proxyActivity);

    void onCreate(Bundle saveInstanceState);

    void onStart();

    void onRestart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
