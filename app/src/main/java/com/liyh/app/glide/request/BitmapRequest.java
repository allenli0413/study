package com.liyh.app.glide.request;

import android.content.Context;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

import com.liyh.app.glide.listener.RequestListener;
import com.liyh.app.glide.util.MD5Utils;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 23 日
 * @time 21 时 43 分
 * @descrip :
 */
public class BitmapRequest {
    private Context context;
    private String url;
    private RequestListener requestListener;
    private String urlMd5;
    private int resId;
    private SoftReference<ImageView> imageView;

    public BitmapRequest(Context context) {
        this.context = context;
    }

    public BitmapRequest load(String url){
        this.url = url;
        return this;
    }

    public BitmapRequest loading(int resId){
        this.resId = resId;
        return this;
    }

    public BitmapRequest listener(RequestListener requestListener){
        this.requestListener = requestListener;
        return this;
    }

    public void into(ImageView imageView){
        this.urlMd5 = MD5Utils.toMD5(url);
        imageView.setTag(urlMd5);
        this.imageView = new SoftReference<ImageView>(imageView);
        RequestManager.getInstance().addBitmapRequest(this);
    }

    public Context getContext() {
        return context;
    }

    public String getUrl() {
        return url;
    }

    public RequestListener getRequestListener() {
        return requestListener;
    }

    public String getUrlMd5() {
        return urlMd5;
    }

    public int getResId() {
        return resId;
    }

    public ImageView getImageView() {
        return imageView.get();
    }
}
