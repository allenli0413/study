package com.liyh.hearttreelibrary;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 花瓣
 */
public class Bloom {
    static float sMaxScale = 0.2f;
    static int sMaxRadius = Math.round(sMaxScale * Heart.getRadius());
    static float sFactor;
    Point position;
    int color;
    float angle;
    float scale;
    private boolean isOdd;//奇数

    Bloom(Point position) {
        this.position = position;
        this.color = Color.argb(CommonUtil.random(75, 255), 255,
                CommonUtil.random(255), CommonUtil.random(255));
        this.angle = CommonUtil.random(360);
    }

    /**
     * 初始化显示参数
     *
     * @param resolutionFactor 根据屏幕分辨率设定缩放因子
     */
    public static void initDisplayParam(float resolutionFactor){
        sFactor=resolutionFactor;
        sMaxScale=0.2f*resolutionFactor;
        sMaxRadius =Math.round(sMaxScale * Heart.getRadius());
    }
    public boolean grow(Canvas canvas) {
        if (scale <= sMaxScale) {
            if (isOdd) {
                scale += 0.0125f * sFactor;
                draw(canvas);
            }
            isOdd = !isOdd;
            return true;
        } else {
            return false;
        }
    }

    float getRadius() {
        return Heart.getRadius() * scale;
    }

    private void draw(Canvas canvas) {
        Paint paint = CommonUtil.getPaint();
        paint.setColor(color);
        float r = getRadius();

        canvas.save();
        canvas.translate(position.x, position.y);
        canvas.saveLayerAlpha(-r, -r, r, r, Color.alpha(color));
        canvas.rotate(angle);
        canvas.scale(scale, scale);
        canvas.drawPath(Heart.getPath(), paint);
        canvas.restore();
        canvas.restore();
    }

}
