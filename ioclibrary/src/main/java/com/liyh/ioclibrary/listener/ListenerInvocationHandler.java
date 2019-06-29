package com.liyh.ioclibrary.listener;

import android.util.ArrayMap;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 07 日
 * @time 23 时 08 分
 * @descrip :
 */
public class ListenerInvocationHandler implements InvocationHandler {
    //需要拦截的对象
    private Object target;
    //需要拦截对象的键值对
    private ArrayMap<String, Method> methodArrayMap = new ArrayMap<>();

    public ListenerInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (target != null) {
            //获取需要拦截的方法名
            String name = method.getName();//如果是onClick（）
            //将拦截的方法替换为 show
            method = methodArrayMap.get(name);
            //执行替换后的方法 show
            if (method != null) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 0) {
                    return method.invoke(target);//无参
                } else {
                    return method.invoke(target, args);//有参
                }
            }

        }
        return null;
    }

    /**
     * 添加需要拦截的方法
     *
     * @param methodName 需要拦截的方法名，如onClick()
     * @param method     拦截后需要执行的方法，如show()
     */
    public void addMethodArrayMap(String methodName, Method method) {
        methodArrayMap.put(methodName, method);
    }
}
