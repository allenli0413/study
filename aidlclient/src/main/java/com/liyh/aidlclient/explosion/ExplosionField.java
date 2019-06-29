package com.liyh.aidlclient.explosion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yahri Lee
 * @date 2019 年 06 月 29 日
 * @time 10 时 54 分
 * @descrip :
 */
public class ExplosionField extends View {

    private OnClickListener mOnClickListener;

    private List<ExplosionAnimator> explosionAnimators;
    private Map<View, ExplosionAnimator> explosionAnimatorMap;
    private ParticleFactory mParticleFactory;

    public ExplosionField(Context context, ParticleFactory particleFactory) {
        this(context, (AttributeSet) null);
        mParticleFactory = particleFactory;
    }

    public ExplosionField(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExplosionField(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        attach2Activity((Activity) context);
        explosionAnimators = new ArrayList<>();
        explosionAnimatorMap = new HashMap<>();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //粒子动画绘制
        for (ExplosionAnimator explosionAnimator : explosionAnimators) {
            explosionAnimator.draw(canvas);
        }
    }

    /**
     * 分裂
     *
     * @param view 当前点击的View
     */
    public void explode(final View view) {
        //防止重复
        if (explosionAnimatorMap.get(view) != null &&
                explosionAnimatorMap.get(view).isStarted()) {
            return;
        }
        if (view.getVisibility() != VISIBLE || view.getAlpha() == 0) {
            return;
        }
        //得到view相对于整个屏幕的坐标（由于受到状态栏和标题栏的影响，需要上移）
        final Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        //标题栏的高
        int top = ((ViewGroup) getParent()).getTop();
        //状态栏的高度
        Rect frame = new Rect();
        ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        //去掉状态栏和透明栏的高度
        rect.offset(0, -top - statusBarHeight);
        if (rect.width() == 0 || rect.height() == 0) {
            //不能做分裂效果
            return;
        }

        //开始震动
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(150);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationX((Utils.mRandom.nextFloat() - 0.5f) * view.getWidth() * 0.05f);
                view.setTranslationY((Utils.mRandom.nextFloat() - 0.5f) * view.getHeight() * 0.05f);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //震动结束
                view.setTranslationX(0);
                view.setTranslationY(0);
                //开始分裂
                explode(view, rect);
            }
        });

        valueAnimator.start();
    }

    private void explode(final View view, Rect rect) {
        //粒子爆炸
        ExplosionAnimator explosionAnimator = new ExplosionAnimator(this, Utils.createBitmapFromView(view), rect, mParticleFactory);
        explosionAnimators.add(explosionAnimator);
        explosionAnimatorMap.put(view, explosionAnimator);
        explosionAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setClickable(false);
                view.animate().setDuration(150).scaleX(0f).scaleY(0f).alpha(0f).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setClickable(true);
                view.animate().setDuration(150).scaleX(1.0f).scaleY(1.0f).alpha(1.0f).start();
                //移除动画
                explosionAnimators.remove(animation);
                explosionAnimatorMap.remove(view);

            }
        });
        explosionAnimator.start();
    }

    /**
     * 添加全屏显示动画的场景
     *
     * @param activity
     */
    private void attach2Activity(Activity activity) {
        ViewGroup roorView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        roorView.addView(this, layoutParams);
    }

    /**
     * 添加监听，希望谁有爆炸效果，添加到监听里
     *
     * @param view
     */
    public void addListener(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = viewGroup.getChildAt(i);
                addListener(child);
            }
        } else {
            view.setOnClickListener(getOnclickListener());
        }
    }

    private OnClickListener getOnclickListener() {
        if (mOnClickListener == null) {
            mOnClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //开始执行粒子动画
                    explode(v);
                }
            };
        }
        return mOnClickListener;
    }
}
