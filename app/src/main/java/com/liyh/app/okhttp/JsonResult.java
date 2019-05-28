package com.liyh.app.okhttp;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 21 日
 * @time 21 时 10 分
 * @descrip :
 */
public class JsonResult {

    /**
     * reason : 鎺ュ彛鍦板潃涓嶅瓨鍦�
     * result : null
     * error_code : 10022
     */

    public String reason;
    public Object result;
    public int error_code;
    public int resultcode;

    @Override
    public String toString() {
        return "JsonResult{" +
                "reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code=" + error_code +
                ", resultcode=" + resultcode +
                '}';
    }
}
