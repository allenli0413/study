package com.liyh.ioclibrary.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 07 日
 * @time 22 时 26 分
 * @descrip :
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    //事件的三个成员
    //1，set方法
    String listenerSetter();

    //2,监听对象
    Class<?> listenerType();

    //3,回调方法
    String listenerCallback();
}
