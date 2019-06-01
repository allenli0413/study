package com.liyh.app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.liyh.app.R;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Yahri Lee
 * @date 2019 年 02 月 21 日
 * @time 11 时 43 分
 * @descrip :
 */
public class KLineView extends View {

    private static final String TAG = "KLineView";
    private int xTextColor = Color.parseColor("#FF979EC2");
    private int yTextColor = Color.parseColor("#FF979EC2");
    private int xPointColor = Color.parseColor("#FF7F8EC8");
    private int todayMiddelColor = Color.WHITE;
    private int todayOutColor = Color.parseColor("#33A2BDFF");

    private float xTextSize;
    private float yTextSize;
    private int xLineWidth = 1;
    private int dataPointColor1;
    private int dataLineColor1;
    private int dataLineWidth1 = 2;
    private int dataPointColor2;
    private int dataLineColor2;
    private int dataLineWidth2 = 2;
    private int rectColor1;
    private int rectColor2;
    private Map<String, Float> datas = new LinkedHashMap<>();
    private Map<String, Float> data2s = new LinkedHashMap<>();
    private Paint yTextPaint;
    private Paint xTextPaint;
    private Paint xPointPaint;
    private Paint xLinePaint;
    private Paint dataPointPaint;
    private Paint dataLinePaint;
    private float max = 160;
    private int yCount = 5;
    private float xCount = 6;
    private float width;  //控件宽度
    private float height; //控件高度
    private float xCircleRadius = 4f;
    private float littleValueCircleRadius = 6f;
    private float normalValueCircleRadius = 8f;
    private float middleValueCircleRadius = 10f;
    private float outValueCircleRadius = 18f;
    private OnPointClickListener mListener;
    private LinkedList<DataPoint> points = new LinkedList<>();
    private Paint todayWhitePaint;
    private Paint todayGrayPaint;
    private int clickRadius = 12;
    private Paint dataLinePaint2;
    private Paint dataPointPaint2;
    private Paint rectPaint1;
    private Paint rectPaint2;
    private boolean is2Line;
    private boolean isNeedRect;
    private float normal1Min;
    private float normal1Max;
    private float normal2Min;
    private float normal2Max;
    private float yLength;
    private boolean isFloat = false;


    public KLineView(Context context) {
        this(context, null);
    }

    public KLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.KLineView);
        if (ta != null) {
            dataLineColor1 = ta.getColor(R.styleable.KLineView_lineColor, Color.parseColor("#77F55D"));
            dataPointColor1 = ta.getColor(R.styleable.KLineView_pointColor, Color.parseColor("#77F55D"));
            rectColor1 = ta.getColor(R.styleable.KLineView_rectColor1, Color.parseColor("#19FFB354"));
            xTextSize = ta.getDimension(R.styleable.KLineView_xTextSize, 26);
            yTextSize = ta.getDimension(R.styleable.KLineView_yTextSize, 26);
            is2Line = ta.getBoolean(R.styleable.KLineView_is2Line, false);
            isNeedRect = ta.getBoolean(R.styleable.KLineView_isNeedRect, false);
            dataLineColor2 = ta.getColor(R.styleable.KLineView_lineColor2, Color.parseColor("#FF4FC3FD"));
            dataPointColor2 = ta.getColor(R.styleable.KLineView_pointColor2, Color.parseColor("#FF4FC3FD"));
            rectColor2 = ta.getColor(R.styleable.KLineView_rectColor2, Color.parseColor("#194FC3FD"));
            normal1Min = ta.getFloat(R.styleable.KLineView_normal1Min, 60);
            normal1Max = ta.getFloat(R.styleable.KLineView_normal1Max, 90);
            normal2Min = ta.getFloat(R.styleable.KLineView_normal2Min, 90);
            normal2Max = ta.getFloat(R.styleable.KLineView_normal2Max, 140);
            ta.recycle();
        }
        init();
    }

    private void init() {

        datas.put("11-29", 85f);
        datas.put("11-30", 90f);
        datas.put("11-31", 75f);
        datas.put("12-01", 68f);
        datas.put("前天", 72f);
        datas.put("昨天", 65f);
        datas.put("今天", 88f);

        data2s.put("11-29", 100f);
        data2s.put("11-30", 95f);
        data2s.put("11-31", 117f);
        data2s.put("12-01", 133f);
        data2s.put("前天", 124f);
        data2s.put("昨天", 120f);
        data2s.put("今天", 106F);

        xCount = datas.size() - 1;
        yTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        yTextPaint.setColor(yTextColor);
        yTextPaint.setTextSize(yTextSize);

        xTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        xTextPaint.setColor(xTextColor);
        xTextPaint.setTextSize(xTextSize);
        xPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        xPointPaint.setColor(xPointColor);
        xLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        xLinePaint.setStrokeWidth(xLineWidth);

        dataPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dataPointPaint.setColor(dataPointColor1);
        dataLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        dataLinePaint.setColor(Color.parseColor("#77F55D"));
        dataLinePaint.setColor(dataLineColor1);
        dataLinePaint.setStrokeWidth(dataLineWidth1);
        if (is2Line) {
            dataPointPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
            dataPointPaint2.setColor(dataPointColor2);
            dataLinePaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
            dataLinePaint2.setColor(dataLineColor2);
            dataLinePaint2.setStrokeWidth(dataLineWidth2);
        }

        todayWhitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        todayWhitePaint.setColor(todayMiddelColor);

        todayGrayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        todayGrayPaint.setColor(todayOutColor);

        if (isNeedRect) {
            rectPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
            rectPaint1.setStyle(Paint.Style.FILL_AND_STROKE);
            rectPaint1.setStrokeWidth(1);
            rectPaint1.setColor(rectColor1);
            if (is2Line) {
                rectPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
                rectPaint2.setStyle(Paint.Style.FILL_AND_STROKE);
                rectPaint2.setStrokeWidth(1);
                rectPaint2.setColor(rectColor2);

            }
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasureW(widthMeasureSpec);
        height = getMeasureH(heightMeasureSpec);
        setMeasuredDimension((int) width, (int) height);
    }

    private int getMeasureW(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
//        MeasureSpec.UNSPECIFIED; 在类似ScrollView里面的控件高度，不限制
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = getNeedWidth() + getPaddingLeft() + getPaddingRight();//计算自身需要的高度
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }

        return result;
    }

    private int getMeasureH(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
//        MeasureSpec.UNSPECIFIED; 在类似ScrollView里面的控件高度，不限制
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = getNeedHeight() + getPaddingTop() + getPaddingBottom();//计算自身需要的高度
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }

        return result;
    }

    private int getNeedHeight() {
        return 370;
    }

    private int getNeedWidth() {
        return 750;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect xRect = new Rect();
        xTextPaint.getTextBounds("今天", 0, "今天".length(), xRect);
        int xTextHeight = xRect.height();

        //折线图高度
        float h = height - getPaddingTop() - getPaddingBottom();
        //y轴分度高度
        float eachY = (h - xTextHeight - 10) / (yCount + 1);
        //y轴分度值
        float each = max / yCount;
        drawYText(canvas, eachY, each);

        String maxText = "" + (isFloat ? String.format("%.2f", max): (int) max);
        Rect rectY = new Rect();
        yTextPaint.getTextBounds(maxText, 0, maxText.length(), rectY);
        // x轴刻度起点
        int xStart = getPaddingLeft() + rectY.width();
        //折线图宽度
        float w = width - getPaddingRight() - xStart;
        float eachX = (float) (w / (xCount + 1));
        int i = 0;
        PointF lastPoint = null;
        PointF lastPoint2 = null;
        points.clear();
        float rectLeft = 0;
        yLength = height - getPaddingTop() - getPaddingBottom() - xTextHeight - 10;
        for (Map.Entry<String, Float> entry : datas.entrySet()) {
            //x轴值
            String xText = entry.getKey();
            Rect xTextRect = new Rect();
            xTextPaint.getTextBounds(xText, 0, xText.length(), xTextRect);
            float x = xStart + eachX * i;

            float textY = height - getPaddingBottom();
            //绘制x轴刻度字体
            xTextPaint.setColor(Color.parseColor(TextUtils.equals("今天", xText) ? "#FFDDDDDD" : "#FF979EC2"));
            Typeface font = Typeface.create(Typeface.SANS_SERIF, TextUtils.equals("今天", xText) ? Typeface.BOLD : Typeface.NORMAL);
            xTextPaint.setTypeface(font);
            canvas.drawText(xText, x, textY, xTextPaint);

            float textCenterX = xTextRect.width() / 2;
            float circleX = x + textCenterX;
            float circleY = height - getPaddingBottom() - xTextHeight - 10;
            if (i == 0) {
                rectLeft = circleX;
                drawRect(canvas, rectLeft);
            }
            //绘制x轴小圆点
            canvas.drawCircle(circleX, circleY, xCircleRadius, xPointPaint);

            LinearGradient linearGradient = new LinearGradient(circleX, circleY, circleX, getPaddingTop(), Color.parseColor("#7F8EC8"), Color.parseColor("#202646"), Shader.TileMode.CLAMP);
            xLinePaint.setShader(linearGradient);
            //绘制x轴竖线
            canvas.drawLine(circleX, getPaddingTop(), circleX, circleY, xLinePaint);
            Float dataValue = datas.get(xText);
            //今天的圆点半径，比非今天的小
            float valueCircleRadius = TextUtils.equals("今天", xText) ? littleValueCircleRadius : normalValueCircleRadius;

            if (dataValue != null && dataValue != 0) {
                float dataY = getY(dataValue);
                //绘制1的今天的外圈
                if (TextUtils.equals("今天", xText)) {
                    canvas.drawCircle(circleX, dataY, outValueCircleRadius, todayGrayPaint);
                    canvas.drawCircle(circleX, dataY, middleValueCircleRadius, todayWhitePaint);
                }
                //绘制1的今天的点
                canvas.drawCircle(circleX, dataY, valueCircleRadius, dataPointPaint);
                DataPoint dataPoint1 = new DataPoint(circleX, dataY, xText, datas.get(xText));
                points.add(dataPoint1);
                if (lastPoint == null) {
                    lastPoint = new PointF();
                } else {
                    //绘制折线1
                    canvas.drawLine(lastPoint.x, lastPoint.y, circleX, dataY, dataLinePaint);
                }

                lastPoint.x = circleX;
                lastPoint.y = dataY;
            } else {
                lastPoint = null;
            }
            if (is2Line) {
                Float dataValue2 = data2s.get(xText);
                if (dataValue2 != null && dataValue2 != 0) {
                    float dataY2 = getY(dataValue2);
                    //绘制今天的外圈
                    if (TextUtils.equals("今天", xText)) {
                        canvas.drawCircle(circleX, dataY2, outValueCircleRadius, todayGrayPaint);
                        canvas.drawCircle(circleX, dataY2, middleValueCircleRadius, todayWhitePaint);
                    }
//                    valueCircleRadius = TextUtils.equals("今天", xText) ? valueCircleRadius - 2 : valueCircleRadius;
                    //绘制今天的点
                    canvas.drawCircle(circleX, dataY2, valueCircleRadius, dataPointPaint2);
                    DataPoint dataPoint2 = new DataPoint(circleX, dataY2, xText, data2s.get(xText));
                    points.add(dataPoint2);
                    if (lastPoint2 == null) {
                        lastPoint2 = new PointF();
                    } else {
                        //绘制折线2
                        canvas.drawLine(lastPoint2.x, lastPoint2.y, circleX, dataY2, dataLinePaint2);
                    }
                    lastPoint2.x = circleX;
                    lastPoint2.y = dataY2;
                } else {
                    lastPoint2 = null;
                }
            }
            i++;

        }

    }

    private void drawRect(Canvas canvas, float rectLeft) {
        if (isNeedRect) {
            float min1Y = getY(normal1Min);
            float max1Y = getY(normal1Max);

            RectF rectF1 = new RectF(rectLeft, max1Y, width, min1Y);
            canvas.drawRect(rectF1, rectPaint1);
            if (is2Line) {
                float min2Y = getY(normal2Min);
                float max2Y = getY(normal2Max);
                RectF rectF2 = new RectF(rectLeft, max2Y, width, min2Y);
                canvas.drawRect(rectF2, rectPaint2);
            }
        }
    }

    private float getY(float value) {
        return yLength - (value / max) * (yLength * 5 / 6) + getPaddingTop();
    }

    /**
     * 绘制y轴刻度值
     *
     * @param canvas
     * @param eachY
     * @param each
     */
    private void drawYText(Canvas canvas, float eachY, float each) {
        for (int i = 0; i < yCount; i++) {
            Rect yTextRect = new Rect();
            float yValue = max - each * i;
            String yText = isFloat ? String.format("%.2f", yValue) : (int) yValue + "";
            yTextPaint.getTextBounds(yText, 0, yText.length(), yTextRect);
            float x = getPaddingLeft();
            float y = getPaddingTop() + eachY * (1 + i) + yTextRect.height() / 2;

            canvas.drawText(yText, x, y, yTextPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            //手指抬起
            case MotionEvent.ACTION_UP:
                float x = event.getX();
                float y = event.getY();
                Log.d(TAG, "onTouchEvent x = " + x + ",y = " + y);
                for (DataPoint point : points) {
                    float xPoint = point.xPoint;
                    float yPoint = point.yPoint;
                    Log.d(TAG, "数据点：x = " + xPoint + ",y = " + yPoint);
                    if (x > xPoint - clickRadius && x < xPoint + clickRadius
                            && y > yPoint - clickRadius && y < yPoint + clickRadius) {
                        if (mListener != null) {
                            mListener.onClick(point.xValue, point.yValue);
                        }
                    }
                }
                break;

        }
        return true;    //自己处理!!!!!!!
    }

    public void setOnPointClickListener(OnPointClickListener onPointClickListener) {
        this.mListener = onPointClickListener;
    }

    public interface OnPointClickListener {
        void onClick(String xValue, float yValue);
    }

    class DataPoint implements Serializable {
        public float xPoint;
        public float yPoint;
        public String xValue;
        public float yValue;

        public DataPoint(float xPoint, float yPoint, String xValue, float yValue) {
            this.xPoint = xPoint;
            this.yPoint = yPoint;
            this.xValue = xValue;
            this.yValue = yValue;
        }
    }

    public void setMaxValue(float max) {
        this.max = max;
    }

    public void setyCount(int count) {
        this.yCount = count;
    }

    public void setxCount(int xCount) {
        this.xCount = xCount;
    }

    public void setDatas(Map<String, Float> datas) {
        this.datas = datas;
        invalidate();
    }

    public void setDatas(Map<String, Float> datas, Map<String, Float> data2s) {
        this.datas = datas;
        this.data2s = data2s;
        is2Line = true;
        invalidate();
    }

    public void setNeedRect(boolean needRect) {
        isNeedRect = needRect;
    }

    public void setYTextColor(int yTextColor) {
        this.yTextColor = yTextColor;
    }

    public void setYTextSize(int yTextSize) {
        this.yTextSize = yTextSize;
    }

    public void setXTextColor(int xTextColor) {
        this.xTextColor = xTextColor;
    }

    public void setXTextSize(int xTextSize) {
        this.xTextSize = xTextSize;
    }

    public void setxPointColor(int xPointColor) {
        this.xPointColor = xPointColor;
    }

    public void setxLineWidth(int xLineWidth) {
        this.xLineWidth = xLineWidth;
    }

    public void setDataPointColor1(int dataPointColor1) {
        this.dataPointColor1 = dataPointColor1;
    }

    public void setDataLineColor1(int dataLineColor1) {
        this.dataLineColor1 = dataLineColor1;
    }

    public void setDataLineWidth1(int dataLineWidth1) {
        this.dataLineWidth1 = dataLineWidth1;
    }

    public void setDataPointColor2(int dataPointColor2) {
        this.dataPointColor2 = dataPointColor2;
    }

    public void setDataLineColor2(int dataLineColor2) {
        this.dataLineColor2 = dataLineColor2;
    }

    public void setDataLineWidth2(int dataLineWidth2) {
        this.dataLineWidth2 = dataLineWidth2;
    }

    public void setTodayMiddelColor(int todayMiddelColor) {
        this.todayMiddelColor = todayMiddelColor;
    }

    public void setTodayOutColor(int todayOutColor) {
        this.todayOutColor = todayOutColor;
    }

    public void setRectColor1(int rectColor1) {
        this.rectColor1 = rectColor1;
    }

    public void setRectColor2(int rectColor2) {
        this.rectColor2 = rectColor2;
    }

    public void setNormal1(float normal1Min, float normal1Max) {
        this.normal1Min = normal1Min;
        this.normal1Max = normal1Max;
    }


    public void setNormal2(float normal2Min, float normal2Max) {
        this.normal2Min = normal2Min;
        this.normal2Max = normal2Max;
    }

    public void setYTextFloat(boolean isFloat) {
        this.isFloat = isFloat;
    }

}
