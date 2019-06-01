package com.liyh.app.database;

import com.liyh.databaselibrary.DbField;
import com.liyh.databaselibrary.DbTable;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 28 日
 * @time 23 时 11 分
 * @descrip :
 */
@DbTable("tb_user")
public class User {
    @DbField("num")
    private Integer id;

    @DbField("name")
    private String name;

    @DbField("phone")
    private Long phone;

    public User(Integer id, String name, Long phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
}
