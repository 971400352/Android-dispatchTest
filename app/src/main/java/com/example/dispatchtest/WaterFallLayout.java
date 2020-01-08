package com.example.dispatchtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * author ：ykq
 * date : 2019/12/13 11:29
 * package：com.example.waterfalllayout
 * description :
 */
public class WaterFallLayout extends ViewGroup{

    private final static String TAG = "提示";

    private int columns = 3;
    private int hSpace = 20;
    private int vSpace = 20;
    private int childWidth = 0;
    private int top[];//每一列的高度
    private List<Integer> childsHeight = new ArrayList<Integer>();

    public WaterFallLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        top = new int[columns];
    }

    public WaterFallLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterFallLayout(Context context) {
        this(context, null);
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

    /*
    为子控件布局
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
//        clearTop(false);
//        for (int i = 0; i < childCount; i++) {
//            View child = this.getChildAt(i);
//            int childHeight = child.getMeasuredHeight() * childWidth / child.getMeasuredWidth();
//            int minColum = getMiniHeightColumn();
//            int tleft = minColum * (childWidth + hSpace);
//            int ttop = top[minColum];
//            int tright = tleft + childWidth;
//            int tbottom = ttop + childHeight;
//            top[minColum] += vSpace + childHeight;
//            child.layout(tleft, ttop, tright, tbottom);
//        }
        if(childCount > 0){
            View child = this.getChildAt(childCount - 1);
            int childHeight = child.getMeasuredHeight() * childWidth / child.getMeasuredWidth();
            int minColum = getMiniHeightColumn();
            int tleft = minColum * (childWidth + hSpace);
            int ttop = top[minColum];
            int tright = tleft + childWidth;
            int tbottom = ttop + childHeight;
            top[minColum] += vSpace + childHeight;
            Log.d(TAG, "onLayout: " + tleft + " " + ttop + " " + tright + " " + tbottom + " ");
            child.layout(tleft, ttop, tright, tbottom);
            childsHeight.add(tbottom);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Log.d(TAG, "onMeasure: 执行");
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //得到单个Item的宽度
        childWidth = (sizeWidth - (columns - 1) * hSpace) / columns;

        //得到总宽度
        int wrapWidth;
        int childCount = getChildCount();
        if (childCount < columns) {
            wrapWidth = childCount * childWidth + (childCount - 1) * hSpace;
        } else {
            wrapWidth = sizeWidth;
        }
//        if(childCount == 0){
//            clearTop(true);
//        }else{
//            clearTop(false);
//        }
//        Log.d(TAG, "onMeasure: " + childCount);
//        for (int i = 0; i < childCount; i++) {
//            View child = this.getChildAt(i);
//            //保持图片的比例
//            int childHeight = child.getMeasuredHeight() * childWidth / child.getMeasuredWidth();
//            //获得最小高度的列的索引
//            int minColum = getMiniHeightColumn();
//            //将当前的最小高度列加一个子控件需要的高度，这样就可以得到每一列的高度了
//            top[minColum] += vSpace + childHeight;
//        }
        int wrapHeight;
        if(getChildCount() > 0){
            View child = getChildAt(getChildCount() - 1);
            int childHeight = child.getMeasuredHeight() * childWidth / child.getMeasuredWidth();
            int minColum = getMiniHeightColumn();
//            top[minColum] += vSpace + childHeight;

            wrapHeight = getMaxHeight();//获得最大高度，用来确定父控件的高度
            if(wrapHeight < (top[minColum] + vSpace + childHeight)){
                wrapHeight = top[minColum] + vSpace + childHeight;
            }
        }else{
            wrapHeight = getMaxHeight();//获得最大高度，用来确定父控件的高度
        }
        //设置父控件的宽度和高度
        setMeasuredDimension(widthMode == MeasureSpec.AT_MOST ? wrapWidth : sizeWidth, wrapHeight);
    }

    //再计算高度之前，我们应该先把top[]数组清空，以防止上次的数据影响这次计算，
    private void clearTop(boolean isReally){
        if(isReally){
            for(int i = 0; i < columns; i++){
                top[i] = 0;
            }
        }else{
            for(int i = 0; i < columns; i++){
                top[i] = 20;
            }
        }
    }

    //获得最短的列
    private int getMiniHeightColumn(){
        int miniColumn = 0;
        for(int i = 0; i < columns; i++){
            if(top[i] < top[miniColumn]){
                miniColumn = i;
            }
        }
        return miniColumn;
    }

    private int getMaxHeight(){
        int maxHeight = 0;
        for(int i = 0; i < columns; i++){
            if(top[i] > maxHeight){
                maxHeight = top[i];
            }
        }
        return maxHeight;
    }

    public float getViewLocation(int index){
        float childY = childsHeight.get(index);
        return childY;
    }


    public void setOnItemClickListener(final OnItemClickListener listener) {
        for (int i = 0; i < getChildCount(); i++) {
            final int index = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, index);
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View v, int index);
    }
}

