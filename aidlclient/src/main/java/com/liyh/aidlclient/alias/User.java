package com.liyh.aidlclient.alias;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 02 日
 * @time 15 时 19 分
 * @descrip :
 */
public class User {
    public String name;
    public int age;
    public long phone;

    public User(String name, int age, long phone) {
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
}
