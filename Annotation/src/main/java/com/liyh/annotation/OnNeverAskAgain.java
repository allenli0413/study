package com.liyh.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 08 日
 * @time 19 时 25 分
 * @descrip :
 * SOURCE 仅在源码中保留，在class文件中不存在
 * CLASS 注解在源码和class文件中都存在,但运行时不存在,即运行时无法获得,该策略也是默认的保留策略
 * RUNTIME 注解在源码,class文件中存在且运行时可以通过反射机制获取到
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface OnNeverAskAgain {
}
