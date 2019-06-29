package com.liyh.ioclibrary.annotations;


import com.liyh.ioclibrary.recyclerview.RView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 该注解作用于在方法
@Retention(RetentionPolicy.RUNTIME) // jvm运行时通过反射获取到该注解的内容
@EventBase(listenerSetter = "setOnItemLongClickListener", listenerType = RView.OnItemLongClickListener.class, listenerCallback = "onItemLongClick")
public @interface OnItemLongClick {

    int[] value(); // 数组形式，多id多控件共用某点击方法
}
