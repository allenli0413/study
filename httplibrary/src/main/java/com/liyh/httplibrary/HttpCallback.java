package com.liyh.httplibrary;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 08 日
 * @time 13 时 39 分
 * @descrip :
 */
public abstract class HttpCallback<RESULT> implements IHttpCallback {
    @Override
    public void onSuccess(String respone) {
        Gson gson = new Gson();
        Class<?> clazz = analysisClassInfo(this);
        RESULT result = (RESULT) gson.fromJson(respone, clazz);
        success(result);
    }

    protected abstract void success(RESULT result);

    private Class<?> analysisClassInfo(HttpCallback httpCallback) {
        //得到包含原始数据类型，参数化类型，基本数据类型
        Type type = httpCallback.getClass().getGenericSuperclass();
        //获取泛型类型
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        return (Class<?>) actualTypeArguments[0];
    }

    @Override
    public void onDailure() {

    }
}
