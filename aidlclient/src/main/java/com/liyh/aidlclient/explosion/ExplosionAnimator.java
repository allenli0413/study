package com.liyh.aidlclient.explosion;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 29 日
 * @time 13 时 06 分
 * @descrip :
 */
public class ExplosionAnimator extends ValueAnimator {

    private static final int DEFAULT_DURATION = 1500;
    private Particle[][] mParticle;
    private Paint mPaint;
    private View mContainer;

    /**
     * @param mContainer       粒子爆炸场地
     * @param bitmap
     * @param rect
     * @param mParticleFactory
     */
    public ExplosionAnimator(View mContainer, Bitmap bitmap, Rect rect, ParticleFactory mParticleFactory) {
        this.mPaint = new Paint();
        this.mContainer = mContainer;
        setFloatValues(0.0f, 1.0f);
        setDuration(DEFAULT_DURATION);
        mParticle = mParticleFactory.generateParticles(bitmap, rect);
    }

    public void draw(Canvas canvas) {
        if (!isStarted()) {
            //动画结束即停止
            return;
        }
        //所有粒子开始运动
        for (Particle[] particles : mParticle) {
            for (Particle particle : particles) {
                particle.advance(canvas, mPaint, (Float) getAnimatedValue());
            }
        }
        mContainer.invalidate();
    }


    @Override
    public void start() {
        super.start();
        mContainer.invalidate();
    }
}
