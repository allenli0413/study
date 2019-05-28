package com.liyh.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 27 日
 * @time 01 时 32 分
 * @descrip :
 */
@Target(ElementType.FIELD) //作用于成员变量上  属性
@Retention(RetentionPolicy.CLASS) //作用于编译期，.class 存在于apk中
public @interface BindView {
    int value();
}
