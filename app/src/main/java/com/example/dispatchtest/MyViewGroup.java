package com.example.dispatchtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

/**
 * author ：ykq
 * date : 2020/1/7 15:50
 * package：com.example.dispatchtest
 * description :
 */
public class MyViewGroup extends ViewGroup {

    private static final String TAG = MyViewGroup.class.getSimpleName();

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        for(int j = 0; j < getChildCount(); j++){
            getChildAt(j).layout(0, 200, getChildAt(i).getWidth(), getChildAt(i).getHeight() + 200);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width = 0, height = 0;

        for(int i = 0; i < getChildCount(); i++){
            width = getChildAt(i).getMeasuredWidth() > width ? getChildAt(i).getMeasuredWidth() : width;
            height += getChildAt(i).getMeasuredHeight();
        }
        Log.d(TAG, "onMeasure 宽: " + width);
        Log.d(TAG, "onMeasure 高: " + height);



        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? measureWidth: width, (heightMode == MeasureSpec.EXACTLY) ? measureHeight: height);
    }
}
