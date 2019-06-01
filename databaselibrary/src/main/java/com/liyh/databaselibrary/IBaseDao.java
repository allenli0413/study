package com.liyh.databaselibrary;

import java.util.List;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 28 日
 * @time 23 时 05 分
 * @descrip :规范所有的数据库操作
 */
public interface IBaseDao<T> {
    //插入数据
    long insert(T bean);

    List<T> query();
}
