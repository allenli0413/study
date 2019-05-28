package com.liyh.pluginlibrary;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

//代理Activity，管理插件activity的生命周期
public class ProxyActivity extends Activity {


    private String mClassName;
    private PluginApk mPlyginApk;
    private IPlugin mIPlugin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = getIntent().getStringExtra("className");
        mPlyginApk = PluginManager.getInstance().getmPluginApk();
        lanuchPluginActivity();
    }

    private void lanuchPluginActivity() {

        if (mPlyginApk == null) {
            throw new RuntimeException("请先加载插件apk");
        }

        try {
            Class<?> aClass = mPlyginApk.mClassLoader.loadClass(mClassName);
            Object instance = aClass.newInstance();
            if (instance instanceof IPlugin) {
                mIPlugin = (IPlugin) instance;
                mIPlugin.attach(this);
                Bundle bundle = new Bundle();
                bundle.putInt("from", IPlugin.FROM_EXTERNAL);
                mIPlugin.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //特别注意


    @Override
    public Resources getResources() {
        return mPlyginApk != null ? mPlyginApk.mResources : super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return mPlyginApk != null ? mPlyginApk.mAssetManager : super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mPlyginApk != null ? mPlyginApk.mClassLoader : super.getClassLoader();
    }

}