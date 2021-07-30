package com.yang.nestscroll.library;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

public class IndicatorBehavior extends CoordinatorLayout.Behavior<IndicatorFrameLayout> {
    
    private static final String TAG = "IndicatorBehavior";

    private IndicatorFrameLayout indicatorFrameLayout;

    public void setIndicatorFrameLayout(IndicatorFrameLayout indicatorFrameLayout) {
        this.indicatorFrameLayout = indicatorFrameLayout;
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull IndicatorFrameLayout child, @NonNull View dependency) {
        return dependency instanceof HeaderRecyclerView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull IndicatorFrameLayout child, @NonNull View dependency) {
        int bottom = dependency.getBottom();
        int y = (int) (child.getY() + 0.5f);
        ViewCompat.offsetTopAndBottom(child, bottom - y);
        Log.i(TAG, "onDependentViewChanged: " + bottom + " " + y);
        return true;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull IndicatorFrameLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        //可能会调用两次，一次onInterceptTouchEvent，另一次onTouchEvent
        Log.i(TAG, "onStartNestedScroll: ");
        return target instanceof HeaderRecyclerView && axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }


    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull IndicatorFrameLayout child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        Log.i(TAG, "onNestedPreScroll: " + dy);
        
        if (dy > 0){
            //页面向上走，往下滑
            int bottom = target.getBottom();
            //页面没有滑出去
            if (bottom > 0){
                int offsetY = Math.min(bottom, dy);
                ViewCompat.offsetTopAndBottom(target, -offsetY);
                consumed[1] = offsetY;
            }
        } else {
            //页面向下走，往上滑
            int top = target.getTop();
            if (top < 0){
                //页面有滑出去的部分
                int offsetY = Math.max(top, dy);
                ViewCompat.offsetTopAndBottom(target, -offsetY);
                consumed[1] = offsetY;
            }
        }
    }
}
