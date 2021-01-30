package com.example.onlayoutcustomcomponent;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

public class PhotoSpiral extends ViewGroup {
    private static final String TAG = PhotoSpiral.class.getSimpleName();
    public PhotoSpiral(Context context) {
        super(context);
        setUp();
    }

    public PhotoSpiral(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public PhotoSpiral(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PhotoSpiral(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setUp();
    }

    private void setUp() {
       //LayoutInflater.from(getContext()).inflate(R.layout.activity_main, this);
        FrameLayout frameLayout = new FrameLayout(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        frameLayout.setLayoutParams(params);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //Get the measure of first View
        View first = getChildAt(0);
        int size = first.getWidth() + first.getHeight();
        //Resolve to parent width and height
        int width = ViewGroup.resolveSize(size, widthMeasureSpec);
        Log.d(TAG, "onMeasure: w  Size " + size);
        Log.d(TAG, "onMeasure: w widthSpec " + widthMeasureSpec);
        int height = ViewGroup.resolveSize(size, heightMeasureSpec);
        //Set the dimensions
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //Get The dimensions set
        View first = getChildAt(0);
        int childWidth = first.getMeasuredWidth();
        int childHeight = first.getMeasuredHeight();
        //Iterate through each view layout to set their positions
        for ( int i = 0; i < getChildCount(); ++i){
            View child = getChildAt(i);
            int x = 0;
            int y = 0;
            switch (i){
                case 1:
                    x = childWidth;
                    y = 0;
                    break;
                case 2:
                    x = childHeight;
                    y = childWidth;
                    break;
                case 3:
                    x = 0;
                    y = childHeight;
                    break;
            }
        child.layout(x, y, x + child.getMeasuredWidth(), y + child.getMeasuredHeight());
      }

    }
}
