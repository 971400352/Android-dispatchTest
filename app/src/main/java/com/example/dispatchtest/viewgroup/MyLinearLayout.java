package com.example.dispatchtest.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * author ：ykq
 * date : 2020/1/7 17:34
 * package：com.example.dispatchtest.viewgroup
 * description :
 */
public class MyLinearLayout extends LinearLayout {
    private static final String TAG = MyLinearLayout.class.getSimpleName();
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ViewGroup -> onTouchEvent: 按下");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ViewGroup -> onTouchEvent: 弹起");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "ViewGroup -> onTouchEvent: 移动");
                break;
        }
        boolean result = super.onTouchEvent(event);
        Log.d(TAG, "ViewGroup -> onTouchEvent: super result " + result);
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ViewGroup -> dispatchTouchEvent: 按下");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ViewGroup -> dispatchTouchEvent: 弹起");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "ViewGroup -> dispatchTouchEvent: 移动");
                break;
        }
        //调用super 就会调用onInterceptTouchEvent方法
        //如果不调用super就代表事件不继续分发，
        boolean result = super.dispatchTouchEvent(ev);
        Log.d(TAG, "ViewGroup -> dispatchTouchEvent: super result " + result);
        //如果返回false 后续事件在父控件的onTouchEvent方法（注意区分父类和父控件）
        //如果返回true就 结束事件，不会回传事件给父控件的toTouchEvent, 这里需要注意，如果执行了super方法，事件还是会向下传递。
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ViewGroup -> onInterceptTouchEvent: 按下");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ViewGroup -> onInterceptTouchEvent: 弹起");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "ViewGroup -> onInterceptTouchEvent: 移动");
                break;
        }
        boolean result = super.onInterceptTouchEvent(ev);
        Log.d(TAG, "ViewGroup -> onInterceptTouchEvent: super result " + result);
        return result;
    }
}
