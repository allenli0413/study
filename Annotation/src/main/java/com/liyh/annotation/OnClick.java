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
@Target(ElementType.METHOD) //作用于成员方法上
@Retention(RetentionPolicy.CLASS) //作用于编译期
public @interface OnClick {
    int value();
}
