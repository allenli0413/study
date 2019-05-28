package com.liyh.app.okhttp;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 21 日
 * @time 20 时 59 分
 * @descrip :
 */
public class HttpUtil {
    public static <T, M> void sendJsonRequest(String url, T requestData, Class<M> responseClass, IJsonDataListener linstener) {
        JsonHttpRequest jsonHttpRequest = new JsonHttpRequest();
        JsonCallbackListener<M> callbackLinstener = new JsonCallbackListener<>(responseClass, linstener);
        HttpTask httpTask = new HttpTask<>(url, requestData, jsonHttpRequest, callbackLinstener);
        ThreadPoolManager.getInstance().addTask(httpTask);
    }
}
