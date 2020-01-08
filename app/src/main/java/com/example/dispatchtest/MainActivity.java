package com.example.dispatchtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "Activity -> onTouchEvent: 按下");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "Activity -> onTouchEvent: 弹起");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "Activity -> onTouchEvent: 移动");
                break;
        }
//        可以不调用super.onTouchEvent(event)，因为activity已经是最外层了，即事件分发顺序activity->ViewGroup->View，事件消费顺序是View->ViewGroup->activity。
        boolean result = super.onTouchEvent(event);
        Log.d(TAG, "Activity -> onTouchEvent: super result " + result);
        //不管返回什么都结束
        return result;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "Activity -> ---------------------------------------------");
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "Activity -> dispatchTouchEvent: 按下");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "Activity -> dispatchTouchEvent: 弹起");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "Activity -> dispatchTouchEvent: 移动");
                break;
        }
        //默认调用父类dispatchTouchEvent
        //如果不去调用父类的方法，就代表不分发事件，所以ViewGroup和View都响应不到事件。
        boolean result = super.dispatchTouchEvent(ev);
        Log.d(TAG, "Activity -> dispatchTouchEvent: super result " + result);
        //不管返回什么都算消费了 即结束。
        return result;
    }
}
