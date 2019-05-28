package com.liyh.networklistenerlibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.liyh.networklistenerlibrary.annotation.Network;
import com.liyh.networklistenerlibrary.bean.MethodManager;
import com.liyh.networklistenerlibrary.type.NetType;
import com.liyh.networklistenerlibrary.utils.Constants;
import com.liyh.networklistenerlibrary.utils.NetworkUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 26 日
 * @time 10 时 43 分
 * @descrip :网络改变广播
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    private static final String TAG = Constants.LOG_TAG;
    private NetType netType;
    //    private NetChangeObserver listener;
    private Map<Object, List<MethodManager>> networkList;

    public NetworkChangeReceiver() {
        netType = NetType.NONE;
        networkList = new HashMap<>();
    }

    //    public void setListener(NetChangeObserver listener) {
//        this.listener = listener;
//    }
    private long lastTime = 0L;

    //注意：android.net.conn.CONNECTIVITY_CHANGE 广播当开启热点的时候，会多次接收网络改变的广播，通过毫秒值进行过滤
    //第一次app启动的时候会接收一次广播，此时可能还没有注册,需要判断网络监听注解方法的个数
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            if (action.equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION)) {
                Log.e(Constants.LOG_TAG, "onReceive: " + "网络发生改变");

                netType = NetworkUtils.getNetType();//获取网络类型
                if (NetworkUtils.isNetworkAvailable()) {//所有有网络的方式循环判断
                    Log.e(Constants.LOG_TAG, "onReceive: " + "网络连接成功");
                    long curTime = System.currentTimeMillis();
                    if (curTime - lastTime < 5000) {
                        return;
                    }
                    if (!networkList.isEmpty()) {
                        lastTime = curTime;
                    }
//                    if (listener != null) listener.onConnect(netType);
                } else {
                    Log.e(Constants.LOG_TAG, "onReceive: " + "网络连接失败");
//                    if (listener != null) listener.onDisConnect();
                }
                post(netType);
            }

        } else {
            Log.e(Constants.LOG_TAG, "onReceive: " + "异常》》》》");
        }
    }

    private void post(NetType netType) {
        //获取所有监听对象
        Set<Object> registers = networkList.keySet();
        for (Object getter : registers) {
            //获取监听对象的方法
            List<MethodManager> methods = networkList.get(getter);
            if (methods != null) {
                for (MethodManager method : methods) {
                    if (method.getType().isAssignableFrom(netType.getClass())) {
                        switch (method.getNetType()) {
                            case AUTO:
                                invoke(netType, getter, method);
                                break;
                            case WIFI:
                                if (netType == NetType.WIFI || netType == NetType.NONE) {
                                    invoke(netType, getter, method);

                                }
                                break;
                            case CMWAP:
                                if (netType == NetType.CMWAP || netType == NetType.NONE) {
                                    invoke(netType, getter, method);
                                }
                                break;
                            case CMNET:
                                if (netType == NetType.CMNET || netType == NetType.NONE) {
                                    invoke(netType, getter, method);
                                }
                                break;
                            default:
                                break;

                        }
                    }
                }
            }
        }
    }

    private void invoke(NetType netType, Object getter, MethodManager method) {
        Method execute = method.getMethod();
        try {
            execute.invoke(getter, netType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void registerObserver(Object register) {
        //获取register中所有网络监听注解方法
        List<MethodManager> methodList = networkList.get(register);
        if (methodList == null) {//==null 未注册过
            methodList = getAnnotationMethods(register);
            //添加到网络监听的列表中
            networkList.put(register, methodList);
        }
    }

    //通过反射获取register中的所有网络监听注解的方法
    private List<MethodManager> getAnnotationMethods(Object register) {
        //创建方法列表
        List<MethodManager> list = new ArrayList<>();
        Class<?> aClass = register.getClass();
        //获取register中的所有方法
        Method[] methods = aClass.getDeclaredMethods();
        //循环遍历找到网络监听注解方法
        for (Method method : methods) {
            //方法注解校验
//            boolean annotationPresent = method.isAnnotationPresent(Network.class);
//            if (!annotationPresent) {
//                throw new RuntimeException(method.getName() + "方法注解必须是 @Network");
//            }
            //获取方法的注解
            Network network = method.getAnnotation(Network.class);
            if (network == null) {
                continue;
            }
            //方法修饰类型校验
            int modifiers = method.getModifiers();
            String modifiersStr = Modifier.toString(modifiers);
            if (!"public".equals(modifiersStr)) {
                throw new RuntimeException(method.getName() + "方法必须是public修饰");
            }
            //方法返回值类型校验
            Type returnType = method.getGenericReturnType();
            if (!"void".equals(returnType.toString())) {
                throw new RuntimeException(method.getName() + "方法返回必须是void");
            }
            //方法参数个数校验
            Class<?>[] parameterTypes = method.getParameterTypes();
            int length = parameterTypes.length;
            if (length != 1) {
                throw new RuntimeException(method.getName() + "方法参数有且只有一个");
            }
            //方法参数类型校验
            Class<?> parameterType = parameterTypes[0];
            if (!"NetType".equals(parameterType.getSimpleName())) {
                throw new RuntimeException(method.getName() + "方法参数类型必须是NetType");
            }


            MethodManager methodManager = new MethodManager(parameterType, network.netType(), method);
            list.add(methodManager);
        }
        return list;
    }

    public void unRegisterObserver(Object register) {
        if (!networkList.isEmpty()) {
            networkList.remove(register);
        }
        Log.e(TAG, "unRegisterObserver: " + register.getClass().getSimpleName() + "注销成功");
    }

    public void unRegisterAllObserver() {
        if (!networkList.isEmpty()) {
            networkList.clear();
        }
        NetworkManager.getDefault().getApplication().unregisterReceiver(this);
        networkList = null;
        Log.e(TAG, "unRegisterAllObserver: " + "注销所有监听成功");
    }
}
