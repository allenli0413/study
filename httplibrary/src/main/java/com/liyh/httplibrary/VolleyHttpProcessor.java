package com.liyh.httplibrary;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 08 日
 * @time 14 时 00 分
 * @descrip :
 */
public class VolleyHttpProcessor implements HttpProcessor {

    private Context context;
    private final RequestQueue requestQueue;

    public VolleyHttpProcessor(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void post(String url, Map<String, Object> params, final IHttpCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error====> ", error.getMessage() );
            }
        });
        requestQueue.add(stringRequest);
//        requestQueue.start();
    }
}
