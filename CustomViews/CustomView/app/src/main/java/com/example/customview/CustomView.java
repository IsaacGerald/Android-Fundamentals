package com.example.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class CustomView extends View {

    public static final int SQUARE_SIZE_DEF = 200;
    private RectF mRectSquare;
    private Paint mPaintSquare;
    private int mSquareColor;
    private Float mSquareSize;
    private Paint mPaintCircle;
    private float mCircleX, mCircleY;
    private float mRadius = 100f;
    private int mCircleColor;
    private Bitmap mImage;

    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        mRectSquare = new RectF();
        mPaintSquare = new Paint(Paint.ANTI_ALIAS_FLAG);//Add Flag to be more pixelated
        mPaintCircle = new Paint();
        mPaintCircle.setFlags(Paint.ANTI_ALIAS_FLAG);

        mImage = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_daisy_high);

        //Listen to view's width and height
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int padding = 50;

                mImage = getResizedImage(mImage, getWidth() - padding, getHeight() - padding);

                //Resize image every half a second
                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {

                        int newWidth = mImage.getWidth() - 50;
                        int newHeight = mImage.getHeight() - 50;

                        if (newWidth <= 0 || newHeight <= 0) {
                            cancel();
                            return;
                        }

                        mImage = getResizedImage(mImage, newWidth, newHeight);
                        postInvalidate();

                    }
                }, 2001, 501);

            }
        });


        if (set == null) {
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(set, R.styleable.CustomView);

        mSquareColor = typedArray.getColor(R.styleable.CustomView_square_color, Color.DKGRAY);
        mSquareSize = typedArray.getDimension(R.styleable.CustomView_square_size, SQUARE_SIZE_DEF);

        mCircleColor = typedArray.getColor(R.styleable.CustomView_circle_color, Color.parseColor("#00ccff"));

        //Set paint color
        mPaintSquare.setColor(mSquareColor);
        mPaintCircle.setColor(mCircleColor);


        typedArray.recycle();
    }

    /*---------------------------------Get Resized image bitmap-----------------------------------*/
    private Bitmap getResizedImage(Bitmap image, int reqWidth, int reqHeight) {
        Matrix matrix = new Matrix();

        RectF src = new RectF(0, 0, image.getWidth(), image.getHeight());
        RectF dst = new RectF(0, 0, reqWidth, reqHeight);

        matrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);

        return Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
    }

    public void swapColor() {
        mPaintSquare.setColor(mPaintSquare.getColor() == mSquareColor ? Color.RED : mSquareColor);

        //invalidate(); //change color synchronously and immediately , may affect the UI
        postInvalidate(); // Change color when it can so as not to affect the Ui

    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*----------------------------drawing a square----------------------------------------*/
        //set positions using rect (Square)
        mRectSquare.left = 50;
        mRectSquare.top = 50;
        mRectSquare.right = mRectSquare.left + mSquareSize;
        mRectSquare.bottom = mRectSquare.top + mSquareSize;


        canvas.drawRect(mRectSquare, mPaintSquare);

//        /*------------------drawing a circle-------------------------------------------*/
//
//        Float cx, cy;
//        Float radius = 100f;
//
//        cx = getWidth() - radius - 50f;
//        cy = mRectSquare.top + mRectSquare.height() / 2;

        if (mCircleX == 0 || mCircleY == 0) {
            mCircleX = getWidth() / 2;
            mCircleY = getHeight() / 2;
        }


        canvas.drawCircle(mCircleX, mCircleY, mRadius, mPaintCircle);
        /*---------------------------Drawing an image on a canvas---------------------------------------*/


        float imageX = (getWidth() - mImage.getWidth()) / 2;
        float imageY = (getHeight() - mImage.getHeight()) / 2;

        canvas.drawBitmap(mImage, imageX, imageY, null);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float xSq = event.getX();
                float ySq = event.getY();

                if (mRectSquare.left < xSq && mRectSquare.right > xSq)
                    if (mRectSquare.top < ySq && mRectSquare.bottom > ySq) {
                        mRadius += 10f;
                        postInvalidate();
                    }


                return true;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();

                //detect if touch is inside the circle
                double dx = Math.pow(x - mCircleX, 2);
                double dy = Math.pow(y - mCircleY, 2);

                if (dx + dy < Math.pow(mRadius, 2)) {
                    //Touched
                    mCircleX = x;
                    mCircleY = y;

                    postInvalidate();

                    return true;
                }

                return value;

        }


        return true;
    }
}

