package com.liyh.networklistenerlibrary;

import com.liyh.networklistenerlibrary.type.NetType;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 26 日
 * @time 10 时 53 分
 * @descrip :
 */
public interface NetChangeObserver {
    void onConnect(NetType type);

    void onDisConnect();
}
