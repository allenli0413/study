package com.liyh.hearttreelibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class TreeView extends View {
    private Tree tree;
//    private Paint paint;
//    private Path path;

    public TreeView(Context context) {
        this(context, null);
    }

    public TreeView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public TreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TreeView, defStyleAttr, 0);
        Branch.branchColor = array.getInt(R.styleable.TreeView_branche_color, 0xff000000);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec * 1080 / 1920);
        Log.e("TreeView", widthMeasureSpec + "   " + (widthMeasureSpec * 1080 / 1920) + "");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("TreeView", getWidth() + "   " + getHeight() + "");
        if (tree == null) {
            tree = new Tree(getWidth(), getHeight());
        }
//        paint = new Paint();
//        paint.setTextSize(100);
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setTextSize(100);     //单位为 sp
//        paint.setStrokeWidth(5);
//        path = new Path();
//        path.moveTo(0, 0);
//        path.quadTo(200, 100, 300, 300);
//        path.quadTo(200, 300, 500, 100);
//        path.moveTo(800, 400);
//
//        canvas.drawPath(path, paint);

        tree.draw(canvas);
        postInvalidate();
    }
}
