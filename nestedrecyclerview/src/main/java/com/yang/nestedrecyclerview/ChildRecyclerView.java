package com.yang.nestedrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ChildRecyclerView extends RecyclerView {
    private static final String TAG = "ChildRecyclerView";

    private FlingHelper mFlingHelper;
    private int mMaxDistance = 0;
    private int mVelocityY = 0;
    private boolean isStartFling;
    private int totalDy;

    private ParentRecyclerView mParentRecyclerView;
    
    public ChildRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public ChildRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChildRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mFlingHelper = new FlingHelper(context);
        mMaxDistance = mFlingHelper.getVelocityByDistance(UIUtils.getScreenWidth() * 4);
        setOverScrollMode(OVER_SCROLL_NEVER);
        initScrollListener();
    }

    private void initScrollListener() {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                Log.i(TAG, "onScrollStateChanged: " + newState);
                if (newState == SCROLL_STATE_IDLE){
                    dispatchParentFling();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                Log.i(TAG, "onScrolled: " + dy + "    isStartFling:" + isStartFling);
                if (isStartFling){
                    totalDy = 0;
                    isStartFling = false;
                }
                totalDy += dy;
            }
        });
    }

    private void dispatchParentFling() {
        ParentRecyclerView parentRecyclerView = getParentRecyclerView();
        if (parentRecyclerView == null){
            Log.i(TAG, "dispatchParentFling: parentRecyclerView cannot be null");
            return;
        }
        boolean scrollTop = isScrollTop();
        Log.i(TAG, "dispatchParentFling: scrollTop->" + scrollTop + "   mVelocityY->" + mVelocityY);
        if (scrollTop && mVelocityY != 0) {
            double flingDistance = mFlingHelper.getSplineFlingDistance(mVelocityY);
            if (flingDistance > (Math.abs(totalDy))){
                fling(0, -mFlingHelper.getVelocityByDistance(flingDistance + totalDy));
            }
            totalDy = 0;
            mVelocityY = 0;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            mVelocityY = 0;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        if (!isAttachedToWindow()){
            return false;
        }
        boolean fling = super.fling(velocityX, velocityY);
        Log.i(TAG, "fling: " + fling + "  " + velocityY);
        if (!fling || velocityY >= 0){
            mVelocityY = 0;
        } else {
            isStartFling = true;
            mVelocityY = velocityY;
        }
        return fling;
    }

    /**
     * 是否已经滚动到顶部
     * @return
     */
    public boolean isScrollTop() {
        //RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
        return !canScrollVertically(-1);
    }


    private ParentRecyclerView getParentRecyclerView(){
        Log.i(TAG, "getParentRecyclerView: ");
        if (mParentRecyclerView != null){
            return mParentRecyclerView;
        }

        ViewParent parent = getParent();
        while (parent != null){
            if (parent instanceof ParentRecyclerView){
                mParentRecyclerView = (ParentRecyclerView) parent;
                break;
            }
            parent = parent.getParent();
        }
        return mParentRecyclerView;
    }
    
    public void setChildRecyclerViewForParent(){
        ParentRecyclerView parentRecyclerView = getParentRecyclerView();
        if (parentRecyclerView != null){
            Log.i(TAG, "setChildRecyclerViewForParent: ");
            parentRecyclerView.setChildRecyclerView(this);
        }
    }
}
