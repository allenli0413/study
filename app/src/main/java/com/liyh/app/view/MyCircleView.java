package com.liyh.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.liyh.app.R;

import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author Yahri Lee
 * @date 2018 年 12 月 21 日
 * @time 17 时 08 分
 * @descrip :
 */
public class MyCircleView extends View {

    private Paint mPaint;
    private Paint mPaint1;
    private int color;
    private float width = 10f;
    private float mSweepAnglePer = -90;
    private float mStartAnglePer = -90;
    private float mEndAnglePer = -54;
    private ScheduledExecutorService executorService;

    public MyCircleView(Context context) {
        this(context, null);
    }

    public MyCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCircleView);
        color = typedArray.getColor(R.styleable.MyCircleView_circle_color, Color.GREEN);
        typedArray.recycle();
        init();
    }

    private void init() {
        //org.apache.commons.lang3.concurrent.BasicThreadFactory
//        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
//                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        executorService = new ScheduledThreadPoolExecutor(1);


//        timer = new Timer();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(width);
        mPaint.setColor(Color.parseColor("#181c2c"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setStrokeWidth(width + 2);
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setStrokeCap(Paint.Cap.ROUND);

    }

    private Timer timer;

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        final RectF rect = new RectF(paddingLeft, paddingTop, width, height);
        canvas.drawArc(rect, 0, 359, false, mPaint);

        SweepGradient sweepGradient = new SweepGradient(rect.centerX(), rect.centerY(),
                new int[]{getResources().getColor(R.color.color1),
                        getResources().getColor(R.color.color2),
                        getResources().getColor(R.color.color3),
                        getResources().getColor(R.color.color4),
                        getResources().getColor(R.color.color5),}, null);
        mPaint1.setShader(sweepGradient);
        canvas.drawArc(rect, mStartAnglePer, mEndAnglePer, false, mPaint1);

//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                mSweepAnglePer += 20;
//                canvas.drawArc(rect, -90, mSweepAnglePer, false, mPaint1);
//            }
//        }, 0, 100);

        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                mStartAnglePer += 36;
                mEndAnglePer += 36;
                canvas.drawArc(rect, 0, mEndAnglePer % 360, false, mPaint1);
                requestLayout();
            }
        }, 100, 100, TimeUnit.MILLISECONDS);

//        int radius = Math.min(width, height) / 2;
//        canvas.drawCircle(paddingLeft + width / 2, paddingTop + height / 2, radius, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 设置默认宽 / 高值
        // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        int mWidth = 400;
        int mHeight = 400;

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, mHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, mHeight);
        }


    }
}
