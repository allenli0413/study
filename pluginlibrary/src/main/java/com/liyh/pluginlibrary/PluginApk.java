package com.liyh.pluginlibrary;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 28 日
 * @time 18 时 38 分
 * @descrip :
 */
public class PluginApk {
    //插件apk的实体对象
    public DexClassLoader mClassLoader;
    public Resources mResources;
    public PackageInfo mPackageInfo;
    public AssetManager mAssetManager;

    public PluginApk(DexClassLoader mClassLoader, Resources mResources, PackageInfo mPackageInfo, AssetManager mAssetManager) {
        this.mClassLoader = mClassLoader;
        this.mResources = mResources;
        this.mPackageInfo = mPackageInfo;
        this.mAssetManager = mAssetManager;
    }
}
