package com.liyh.aidlclient.explosion;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 29 日
 * @time 13 时 11 分
 * @descrip :
 */
public abstract class ParticleFactory {

    public abstract Particle[][] generateParticles(Bitmap bitmap, Rect rect);
}
