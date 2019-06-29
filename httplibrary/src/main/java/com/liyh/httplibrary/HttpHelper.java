package com.liyh.httplibrary;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 08 日
 * @time 13 时 37 分
 * @descrip :
 */
public class HttpHelper implements HttpProcessor {
    private static final HttpHelper ourInstance = new HttpHelper();

    public static HttpHelper getInstance() {
        return ourInstance;
    }

    private HttpHelper() {
    }

    private HttpProcessor httpProcessor;

    public void init(HttpProcessor httpProcessor) {
        this.httpProcessor = httpProcessor;
    }

    @Override
    public void post(String url, Map<String, Object> params, IHttpCallback callback) {
        String realUrl = appendParams(url, params);
        httpProcessor.post(realUrl,params,callback);
    }

    public String appendParams(String url,Map<String,Object> params){
        if(params == null || params.isEmpty()){
            return url;
        }
        StringBuilder urlBuilder = new StringBuilder(url);
        if(urlBuilder.indexOf("?") <= 0 ){
            urlBuilder.append("?");
        }else{
            if(!urlBuilder.toString().endsWith("?")){
                urlBuilder.append("&");
            }
        }
        for(Map.Entry<String,Object> entry:params.entrySet()){
            urlBuilder.append("&"+entry.getKey()).append("=").append(encode(entry.getValue().toString()));
        }
        return urlBuilder.toString();
    }

    private static String encode(String str){
        try {
            return URLEncoder.encode(str,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
