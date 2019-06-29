package com.liyh.httplibrary;

import java.util.Map;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 08 日
 * @time 13 时 34 分
 * @descrip :
 */
public interface HttpProcessor {

    void post(String url, Map<String,Object> params,IHttpCallback callback);
}
