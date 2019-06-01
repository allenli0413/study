package com.liyh.databaselibrary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 28 日
 * @time 23 时 02 分
 * @descrip :
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbField {
    String value();
}
