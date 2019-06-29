package com.liyh.ioclibrary;

import android.app.Activity;

import com.liyh.ioclibrary.annotations.ContentView;
import com.liyh.ioclibrary.annotations.EventBase;
import com.liyh.ioclibrary.annotations.InjectView;
import com.liyh.ioclibrary.listener.ListenerInvocationHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 07 日
 * @time 22 时 52 分
 * @descrip :
 */
public class InjectManager {
    private static final InjectManager ourInstance = new InjectManager();
    private Class<? extends Activity> aClass;
    private Activity target;

    public static InjectManager getInstance() {
        return ourInstance;
    }

    private InjectManager() {
    }

    public void inject(Activity activity) {
        target = activity;
        aClass = activity.getClass();
        injectLayout();
        injectViews();
        injectEvents();
    }

    private void injectEvents() {
        //获取本类的所有方法
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            //获取方法上所有的注解,并遍历
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                //获取注解的类型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                    //获取注解上的注解,如OnClick上的注解
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    if (eventBase != null) { //是否为EventBase注解
                        //事件三大成员

                        String listenerSetter = eventBase.listenerSetter();//set方法
                        Class<?> listenerType = eventBase.listenerType();//监听对象
                        String callback = eventBase.listenerCallback();//回调对象

                        ListenerInvocationHandler invocationHandler = new ListenerInvocationHandler(target);
//                        invocationHandler.addMethodArrayMap(callback, method);
                        // 监听对象的代理对象
                        // ClassLoader loader:指定当前目标对象使用类加载器,获取加载器的方法是固定的
                        // Class<?>[] interfaces:目标对象实现的接口的类型,使用泛型方式确认类型
                        // InvocationHandler h:事件处理,执行目标对象的方法时,会触发事件处理器的方法
                        //返回监听对象
//                        Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, invocationHandler);
                        try {
                            //通过注解上的注解获取注解的方法 ，通过BaseEvent获取OnClick上的value方法
                            Method valueMethod = annotationType.getDeclaredMethod("value");
                            //执行注解的value方法，获取注解的值
                            int[] resIds = (int[]) valueMethod.invoke(annotation);
                            for (int resId : resIds) {
                                Method findViewById = aClass.getMethod("findViewById", int.class);
                                Object view = findViewById.invoke(target, resId);
                                Method methodSetter = view.getClass().getMethod(listenerSetter, listenerType);
                                invocationHandler.addMethodArrayMap(callback, method);

                                // 监听对象的代理对象
                                // ClassLoader loader:指定当前目标对象使用类加载器,获取加载器的方法是固定的
                                // Class<?>[] interfaces:目标对象实现的接口的类型,使用泛型方式确认类型
                                // InvocationHandler h:事件处理,执行目标对象的方法时,会触发事件处理器的方法
                                //返回监听对象
                                Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, invocationHandler);
                                methodSetter.invoke(view, listener);
                            }
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }

    //控件注入
    private void injectViews() {
        //获取本类的所有属性
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            InjectView injectView = field.getAnnotation(InjectView.class);
            if (injectView != null) {
                int resId = injectView.value();
                try {
                    Method findViewById = aClass.getMethod("findViewById", int.class);
                    Object view = findViewById.invoke(target, resId);
                    //设置访问权限 private
                    field.setAccessible(true);
                    field.set(target, view);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //布局注入
    private void injectLayout() {
        ContentView contentView = aClass.getAnnotation(ContentView.class);
        if (contentView != null) {
            int layoutId = contentView.value();
            try {
                Method setContentView = aClass.getMethod("setContentView", int.class);
                setContentView.invoke(target, layoutId);

            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
