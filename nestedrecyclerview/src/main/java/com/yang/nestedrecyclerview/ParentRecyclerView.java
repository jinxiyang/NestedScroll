package com.yang.nestedrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.atomic.AtomicBoolean;

public class ParentRecyclerView extends RecyclerView {
    private static final String TAG = "ParentRecyclerView";

    private FlingHelper mFlingHelper;
    private int mMaxDistance = 0;
    private int mVelocityY = 0;
    private boolean isStartFling;
    private int totalDy;

    private float lastY = 0;

    private AtomicBoolean mCanScrollVertically;

    private ChildRecyclerView tempChildRecyclerView;

    public ParentRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public ParentRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParentRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mFlingHelper = new FlingHelper(context);
        mMaxDistance = mFlingHelper.getVelocityByDistance(UIUtils.getScreenWidth() * 4);
        mCanScrollVertically = new AtomicBoolean(true);
        setOverScrollMode(OVER_SCROLL_NEVER);
        initScrollListener();
    }

    private void initScrollListener() {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                Log.i(TAG, "onScrollStateChanged: " + newState);
                if (newState == SCROLL_STATE_IDLE){
                    dispatchChildFling();
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

    private void dispatchChildFling() {
        if (isScrollEnd() && mVelocityY != 0){
            double splineFlingDistance = mFlingHelper.getSplineFlingDistance(mVelocityY);
            if (splineFlingDistance > totalDy){
                childFling(mFlingHelper.getVelocityByDistance(splineFlingDistance - totalDy));
            }
        }
        totalDy = 0;
        mVelocityY = 0;
    }

    /**
     * 是否已经滚动到底部
     * @return
     */
    public boolean isScrollEnd(){
        //false表示已经滚动到底部
        return !canScrollVertically(1);
    }

    private void childFling(int velocityByDistance) {
        ChildRecyclerView childRecyclerView = getChildRecyclerView();
        if (childRecyclerView != null) {
            childRecyclerView.fling(0, velocityByDistance);
        }
    }

    public void initLinearLayoutManager(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                ChildRecyclerView childRecyclerView = getChildRecyclerView();
                return mCanScrollVertically.get() || childRecyclerView == null || childRecyclerView.isScrollTop();
            }
        };
        setLayoutManager(linearLayoutManager);
    }

    public void setChildRecyclerView(ChildRecyclerView childRecyclerView){
        tempChildRecyclerView = childRecyclerView;
    }

    private ChildRecyclerView getChildRecyclerView(){
        return tempChildRecyclerView;
    }

    public boolean isChildRecyclerViewCanScrollUp(){
        ChildRecyclerView childRecyclerView = getChildRecyclerView();
        if (childRecyclerView != null){
            return !childRecyclerView.isScrollTop();
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN){
            mVelocityY = 0;
            stopScroll();
        }
        if (action == MotionEvent.ACTION_MOVE){
            lastY = 0f;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        if (lastY == 0){
            lastY = y;
        }

        if (isScrollEnd()){
            ChildRecyclerView childRecyclerView = getChildRecyclerView();
            if (childRecyclerView != null){
                mCanScrollVertically.set(false);
                int deltaY = (int) (lastY - y);
                scrollBy(0, deltaY);
            }
        }

        if (e.getAction() == MotionEvent.ACTION_UP){
            mCanScrollVertically.set(true);
        }
        lastY = y;
        return super.onTouchEvent(e);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        boolean fling = super.fling(velocityX, velocityY);
        Log.i(TAG, "fling: " + fling + "  " + velocityY);
        if (!fling || velocityY <= 0){
            mVelocityY = 0;
        } else {
            isStartFling = true;
            mVelocityY = velocityY;
        }
        return fling;
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return target instanceof ChildRecyclerView && (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        ChildRecyclerView childRecyclerView = getChildRecyclerView();
        boolean isParentCanScroll = dy > 0 && !isScrollEnd();
        boolean isChildCannotScroll = !(dy >= 0 || childRecyclerView == null || !childRecyclerView.isScrollTop());
        if (isParentCanScroll || isChildCannotScroll){
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        ChildRecyclerView childRecyclerView = getChildRecyclerView();
        boolean isParentCanFling = velocityY > 0f && !isScrollEnd();
        boolean isChildCannotFling = !(velocityY >= 0 || childRecyclerView == null || !childRecyclerView.isScrollTop());
        if (!isParentCanFling && !isChildCannotFling){
            return false;
        }
        fling(0, (int) velocityY);
        return true;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return true;
    }
}
