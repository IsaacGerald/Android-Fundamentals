package com.example.ondrawcustomcomponent;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class Pizza extends View {

    private Paint mPaint;
    private int mNumWedges = 5;

    public Pizza(Context context) {
        super(context);
        init(context, null);
    }

    public Pizza(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Pizza(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Pizza(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet){
        int strokeWidth = 4;
        int color = Color.GREEN;

        if (attributeSet != null){
            TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.Pizza);
            strokeWidth = array.getDimensionPixelSize(R.styleable.Pizza_stroke_width, strokeWidth);
            color = array.getColor(R.styleable.Pizza_color, color);
            mNumWedges = array.getInt(R.styleable.Pizza_num_wedges, mNumWedges);

        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        final int cx = width / 2 + getPaddingTop() ;
        final int cy = height / 2 + getPaddingTop();
        final float diameter = Math.min(width, height) - mPaint.getStrokeWidth();
        final float radius = diameter / 2;
        canvas.drawCircle(cx, cy, radius, mPaint);
        drawPizzaCuts(canvas, cx, cy, radius);
    }

    private void drawPizzaCuts(Canvas canvas, float cx, float cy, float radius ){
//        final int numWedges = 5;
        final float degrees = 360f / mNumWedges;
       canvas.drawLine(cx, cy, cx , cy - radius, mPaint);
       canvas.save();
       for (int i = 0; i < mNumWedges; i++){
           canvas.rotate(degrees, cx, cy);
           canvas.drawLine(cx, cy, cx , cy - radius, mPaint);
       }
       canvas.restore();
    }
}
