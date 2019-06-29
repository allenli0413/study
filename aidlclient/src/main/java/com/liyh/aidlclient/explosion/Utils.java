package com.liyh.aidlclient.explosion;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import java.util.Random;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 29 日
 * @time 10 时 35 分
 * @descrip :
 */
public class Utils {
    public static final Random mRandom = new Random();
    private static Canvas mCanvas = new Canvas();

    public static Bitmap createBitmapFromView(View view) {
        view.clearFocus();//使view恢复原样
        Bitmap bitmap = createBitmapSade(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888, 1);
        if (bitmap != null){
            synchronized (mCanvas){
                mCanvas.setBitmap(bitmap);
                view.draw(mCanvas);
            }
        }
        return bitmap;
    }

    private static Bitmap createBitmapSade(int width, int height, Bitmap.Config config, int retryCount) {
        try {
            return Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (retryCount > 0) {
                System.gc();
                createBitmapSade(width, height, config, retryCount - 1);
            }
        }
        return null;
    }
}
