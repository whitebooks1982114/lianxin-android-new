package com.example.whitebooks.lianxin_android.utilclass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by whitebooks on 17/2/28.
 */
//实现文字滚动的类
public class CircleTextView extends TextView {

    /** 是否停止滚动 */
    private boolean mStopMarquee;
    private String mText;
    private float mCoordinateX;
    private float mTextWidth;
    private float windowWith;
    public CircleTextView(Context context) {
        super(context);
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setText(String text) {
        this.mText = text;
        mTextWidth = getPaint().measureText(mText);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        windowWith = displayMetrics.widthPixels;
        if (mHandler.hasMessages(0))
            mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0, 2000);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onAttachedToWindow() {
        mStopMarquee = false;
        if (!(mText == null || mText.isEmpty()))
            mHandler.sendEmptyMessageDelayed(0, 2000);
        super.onAttachedToWindow();
    }


    @Override
    protected void onDetachedFromWindow() {
        mStopMarquee = true;
        if (mHandler.hasMessages(0))
            mHandler.removeMessages(0);
        super.onDetachedFromWindow();
    }


    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!(mText == null || mText.isEmpty()))
            canvas.drawText(mText, mCoordinateX, 30, getPaint());
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if(mCoordinateX < 0 && Math.abs(mCoordinateX) > mTextWidth){
                        mCoordinateX = windowWith;
                    }else{
                        mCoordinateX -= 1;
                    }
                    invalidate();
                    sendEmptyMessageDelayed(0,30);
                    break;
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CircleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean isFocused() {
        return true;
    }
}
