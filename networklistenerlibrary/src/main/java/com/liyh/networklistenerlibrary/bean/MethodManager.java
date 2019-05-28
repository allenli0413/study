package com.liyh.networklistenerlibrary.bean;

import com.liyh.networklistenerlibrary.type.NetType;

import java.lang.reflect.Method;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 26 日
 * @time 13 时 37 分
 * @descrip :保存符合要求的网络监听的方法，封装类
 */
public class MethodManager {
    //参数类型NetType type
    private Class<?> type;
    //网络类型netType = NetType.WIFI
    private NetType netType;
    //需要执行的方法，onConnect
    private Method method;

    public MethodManager(Class<?> type, NetType netType, Method method) {
        this.type = type;
        this.netType = netType;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public NetType getNetType() {
        return netType;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
