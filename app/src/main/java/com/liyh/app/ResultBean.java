package com.liyh.app;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 08 日
 * @time 14 时 22 分
 * @descrip :
 */
public class ResultBean {

    /**
     * resultcode : 101
     * reason : 错误的请求KEY
     * result : null
     * error_code : 10001
     */

    public String resultcode;
    public String reason;
    public Object result;
    public int error_code;

    @Override
    public String toString() {
        return "ResultBean{" +
                "resultcode='" + resultcode + '\'' +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code=" + error_code +
                '}';
    }
}
