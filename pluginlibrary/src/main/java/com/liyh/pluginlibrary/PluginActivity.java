package com.liyh.pluginlibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class PluginActivity extends Activity implements IPlugin {

    private Activity proxyActivity;
    private int from;

    @Override
    public void attach(Activity proxyActivity) {
        this.proxyActivity = proxyActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            from = savedInstanceState.getInt("from");
        }
        if (from == IPlugin.FROM_INTERNAL) {
            super.onCreate(savedInstanceState);
            proxyActivity = this;
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (from == IPlugin.FROM_INTERNAL) {
            super.setContentView(layoutResID);
        } else {
            proxyActivity.setContentView(layoutResID);
        }
    }

    @Override
    public void onStart() {
        if (from == FROM_INTERNAL) {
            super.onStart();
        }
    }


    @Override
    public void onRestart() {
        if (from == FROM_INTERNAL) {
            super.onRestart();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (from == FROM_INTERNAL) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onResume() {
        if (from == IPlugin.FROM_INTERNAL) {
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if (from == IPlugin.FROM_INTERNAL) {
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if (from == IPlugin.FROM_INTERNAL) {
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (from == IPlugin.FROM_INTERNAL) {
            super.onDestroy();
        }
    }
}
