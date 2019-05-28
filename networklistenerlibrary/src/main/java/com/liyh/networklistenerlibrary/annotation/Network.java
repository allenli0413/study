package com.liyh.networklistenerlibrary.annotation;

import com.liyh.networklistenerlibrary.type.NetType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 26 日
 * @time 12 时 49 分
 * @descrip :网络状态注解
 */
@Target(ElementType.METHOD)//作用目标在方法上
@Retention(RetentionPolicy.RUNTIME) //jvm运行时通过反射获取注解的值
public @interface Network {
    NetType netType() default NetType.AUTO;
}
