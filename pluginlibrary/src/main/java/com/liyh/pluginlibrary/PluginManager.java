package com.liyh.pluginlibrary;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 28 日
 * @time 18 时 42 分
 * @descrip :
 */
public class PluginManager {
    private static final PluginManager ourInstance = new PluginManager();

    public static PluginManager getInstance() {
        return ourInstance;
    }

    private PluginManager() {
    }

    private Context mContext;

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    private PluginApk mPluginApk;

    public PluginApk getmPluginApk() {
        return mPluginApk;
    }

    //加载apk文件
    public void loadApk(String apkPath) {
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        if (packageInfo == null) return;

        //创建DexClassLoader
        DexClassLoader dexClassLoader = createDexClassLoader(apkPath);
        //创建AssetManager
        AssetManager assetManager = createAssetManager(apkPath);
        //创建Resources
        Resources resources = createResources(assetManager);
        mPluginApk = new PluginApk(dexClassLoader, resources, packageInfo, assetManager);

    }

    private Resources createResources(AssetManager assetManager) {
        Resources resources = mContext.getResources();

        return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
    }

    private AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, apkPath);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("创建AssetManager失败");
    }

    private DexClassLoader createDexClassLoader(String apkPath) {
        File dex = mContext.getDir("dex", Context.MODE_PRIVATE);
        return new DexClassLoader(apkPath, dex.getAbsolutePath(), null, mContext.getClassLoader());
    }
}
