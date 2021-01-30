package com.example.dispatchdraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class SideWaysLayout extends LinearLayout {
    public SideWaysLayout(Context context) {
        super(context);
    }

    public SideWaysLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SideWaysLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Swap dimensions
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //save state
        canvas.save();
        //translate the canvas
        canvas.translate(0, getHeight());
        //rotate the canvas
        canvas.rotate(-90);
        super.dispatchDraw(canvas);
        //Restore canvas
        canvas.restore();
    }
}
