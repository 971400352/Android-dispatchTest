package com.example.dispatchtest.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

/**
 * author ：ykq
 * date : 2020/1/7 17:35
 * package：com.example.dispatchtest.myview
 * description :
 */
public class MyButton extends AppCompatButton {
    private static final String TAG = MyButton.class.getSimpleName();
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "View -> onTouchEvent: 按下");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "View -> onTouchEvent: 弹起");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "View -> onTouchEvent: 移动");
                break;
        }
        boolean result = super.onTouchEvent(event);
        Log.d(TAG, "View -> onTouchEvent: super result " + result);
        //如果这里返回false 那么本身的dispatchTouchEvent里面调用的super也是返回的false
        //如果dispatchTouchEvent里面的返回是super的返回，这样就会遇到dispatchTouchEvent里面的问题，即后续事件不会收到。
        //如果想消费事件，可以不调用super方法，直接返回true。
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "View -> dispatchTouchEvent: 按下");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "View -> dispatchTouchEvent: 弹起");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "View -> dispatchTouchEvent: 移动");
                break;
        }
        boolean result = super.dispatchTouchEvent(event);
        Log.d(TAG, "View -> dispatchTouchEvent: super result " + result);
        //问题：1、返回true消费。2、返回false执行ViewGroup的onTouchEvent方法。3、执行super方法就会执行本身的onTouchEvent方法
        //但是奇怪的是如果想接受后续事件，这里必须返回true。
        return result;
    }
}
