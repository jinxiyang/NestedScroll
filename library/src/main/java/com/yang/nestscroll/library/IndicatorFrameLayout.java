package com.yang.nestscroll.library;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class IndicatorFrameLayout extends FrameLayout implements CoordinatorLayout.AttachedBehavior {

    public IndicatorFrameLayout(@NonNull Context context) {
        super(context);
    }

    public IndicatorFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatorFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @NonNull
    @Override
    public CoordinatorLayout.Behavior getBehavior() {
        IndicatorBehavior behavior = new IndicatorBehavior();
        behavior.setIndicatorFrameLayout(this);
        return behavior;
    }
}
