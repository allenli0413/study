package com.liyh.aidlclient.explosion;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 29 日
 * @time 13 时 07 分
 * @descrip :粒子
 */
public abstract class Particle {
    float cx;
    float cy;
    int color;

    public Particle(float cx, float cy, int color) {
        this.cx = cx;
        this.cy = cy;
        this.color = color;
    }

    public void advance(Canvas canvas, Paint paint, float factor) {
        calculate(factor);
        draw(canvas, paint);
    }

    protected abstract void draw(Canvas canvas, Paint paint);

    protected abstract void calculate(float factor);
}
