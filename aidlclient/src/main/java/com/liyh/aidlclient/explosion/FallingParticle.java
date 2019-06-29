package com.liyh.aidlclient.explosion;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 29 日
 * @time 13 时 15 分
 * @descrip :
 */
public class FallingParticle extends Particle {
    float radius = FallingParticleFactory.PART_WH;
    float alpha = 1.0f;
    Rect mRect;

    public FallingParticle(float cx, float cy, int color, Rect rect) {
        super(cx, cy, color);
        this.mRect = rect;
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * alpha));
        canvas.drawCircle(cx, cy, radius, paint);
    }

    @Override
    protected void calculate(float factor) {
        cx += factor * Utils.mRandom.nextInt(mRect.width()) * (Utils.mRandom.nextFloat() - 0.5f);
        cy += factor * Utils.mRandom.nextInt(mRect.height() / 2);
        radius -= factor * Utils.mRandom.nextInt(2);
        alpha = (1f - factor) * (Utils.mRandom.nextFloat() + 1);
    }
}
