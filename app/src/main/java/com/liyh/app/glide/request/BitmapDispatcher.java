package com.liyh.app.glide.request;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

import com.liyh.app.MyApplication;
import com.liyh.app.glide.cache.DoubleLruCache;
import com.liyh.app.glide.listener.RequestListener;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 23 日
 * @time 21 时 43 分
 * @descrip :
 */
public class BitmapDispatcher extends Thread {

    DoubleLruCache lruCache = new DoubleLruCache(MyApplication.getInstance());
    private LinkedBlockingQueue<BitmapRequest> mQueue;
    private Handler handler = new Handler(Looper.getMainLooper());
    public BitmapDispatcher(LinkedBlockingQueue<BitmapRequest> queue) {
        this.mQueue = queue;
    }

    @Override
    public void run() {
        while (!interrupted()) {
            try {
                BitmapRequest br = mQueue.take();
                showLoadingImageView(br);
                Bitmap bitmap = findBitmap(br);
                showBitmapOnUiThread(bitmap, br);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showBitmapOnUiThread(final Bitmap bitmap, final BitmapRequest br) {
        final ImageView imageView = br.getImageView();
        String tag = (String) imageView.getTag();
        String urlMd5 = br.getUrlMd5();
        if (bitmap != null && imageView != null && TextUtils.equals(tag, urlMd5)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                    RequestListener requestListener = br.getRequestListener();
                    if (requestListener != null){
                        requestListener.onSuccess(bitmap);
                    }
                }
            });
        }

    }

    private Bitmap findBitmap(BitmapRequest br) {

        Bitmap bitmap = lruCache.get(br);
        if (bitmap != null) {
            return bitmap;
        }
        String url = br.getUrl();
        if (!TextUtils.isEmpty(url)) {
            bitmap = downloadImage(url);
            if (bitmap != null) {
                lruCache.put(br, bitmap);
            }
        }
        return bitmap;
    }

    private void showLoadingImageView(BitmapRequest br) {
        final ImageView imageView = br.getImageView();
        final int resId = br.getResId();
        if (imageView != null && resId != 0) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(resId);
                }
            });
        }
    }


    private Bitmap downloadImage(String uri) {
        FileOutputStream fos = null;
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }

            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }
        }
        return bitmap;
    }

}
