package com.liyh.permission.listener;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 09 日
 * @time 23 时 00 分
 * @descrip :
 */
public interface PermissionRequest {
    //继续请求接口，用户拒绝一次后，给出dialog提示
    void proceed();
}
