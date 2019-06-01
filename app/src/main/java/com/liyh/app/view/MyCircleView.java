package com.liyh.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.liyh.app.R;

import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;


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
    private float width = 20f;
    private float mSweepAnglePer = 18;
    private float mStartAnglePer = -90;
    private float mEndAnglePer = 0;
    private ScheduledExecutorService executorService;
    private int count;
    private Canvas mCanvas;
    private Bitmap bitmap;
    private RectF rect;
    private Paint bitmapPaint;
    private Paint mTextPaint;
    private int width1;
    private int height;

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
        count = (int) (360 / mSweepAnglePer);


//        timer = new Timer();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(width);
        mPaint.setColor(Color.parseColor("#181c2c"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setStrokeWidth(width + 5);
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setStrokeCap(Paint.Cap.ROUND);
        bitmapPaint = new Paint();

        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setStrokeWidth(width + 5);
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
//        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(150);
        mTextPaint.setFakeBoldText(true);
    }

    private Timer timer;

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (mCanvas == null) {
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingRight = getPaddingRight();
            int paddingBottom = getPaddingBottom();

            width1 = getWidth() - paddingLeft - paddingRight;
            height = getHeight() - paddingTop - paddingBottom;
            bitmap = Bitmap.createBitmap(Math.round(getWidth()),
                    getHeight(), Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(bitmap);
            rect = new RectF(paddingLeft, paddingTop, width1, height);
            SweepGradient sweepGradient = new SweepGradient(rect.centerX(), rect.centerY(),
                    new int[]{getResources().getColor(R.color.color1),
                            getResources().getColor(R.color.color2),
                            getResources().getColor(R.color.color3),
                            getResources().getColor(R.color.color4),
                            getResources().getColor(R.color.color5),}, null);
            mPaint1.setShader(sweepGradient);
            mCanvas.save();
            mCanvas.drawArc(rect, 0, 359, false, mPaint);
            mCanvas.drawArc(rect, mStartAnglePer, mSweepAnglePer, false, mPaint1);
            mCanvas.restore();
        } else {
            mCanvas.save();
            mStartAnglePer += mSweepAnglePer;
            mCanvas.drawArc(rect, mStartAnglePer, mSweepAnglePer, false, mPaint1);
            mCanvas.restore();
        }
        canvas.save();
        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        int num = (int) (mStartAnglePer + 90) * 100 / 360;
        String text = Integer.toString(num);
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length() - 1, rect);
        float textH = rect.height();
//        float textH = mTextPaint.descent() - mTextPaint.ascent();
//        float textW = rect.width();
        float textW = mTextPaint.measureText(text);

        canvas.drawText(text, getWidth() / 2 - textW / 2, getHeight() / 2 + textH / 2, mTextPaint);
        canvas.restore();
        if (num < 100) {
            postInvalidateDelayed(100);
        }
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
