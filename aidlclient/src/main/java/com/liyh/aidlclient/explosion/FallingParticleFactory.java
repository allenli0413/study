package com.liyh.aidlclient.explosion;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 29 日
 * @time 13 时 14 分
 * @descrip :
 */
public class FallingParticleFactory extends ParticleFactory {
    public static final int PART_WH = 8;

    @Override
    public Particle[][] generateParticles(Bitmap bitmap, Rect rect) {
        int w = rect.width();
        int h = rect.height();

        int partW_count = w / PART_WH; //横向粒子个数（列数）
        int partH_count = h / PART_WH;  //竖向粒子个数（行数）

        //判断个数不能小于0
        partW_count = partW_count > 0 ? partW_count : 1;
        partH_count = partH_count > 0 ? partH_count : 1;

        int bitmap_part_w = bitmap.getWidth() / partW_count;
        int bitmap_part_h = bitmap.getHeight() / partH_count;
        Particle[][] particles = new Particle[partH_count][partW_count];
        for (int row = 0; row < partH_count; row++) {
            //行
            for (int colum = 0; colum < partW_count; colum++) {
                //列
                //获取当前粒子所在位置的颜色
                int color = bitmap.getPixel(colum * bitmap_part_w, row * bitmap_part_h);
                float x = rect.left + PART_WH * colum;
                float y = rect.top + PART_WH * row;
                particles[row][colum] = new FallingParticle(x,y,color,rect);
            }
        }
        return particles;
    }
}
