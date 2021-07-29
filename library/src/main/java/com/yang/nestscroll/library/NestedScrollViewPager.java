package com.yang.nestscroll.library;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

public class NestedScrollViewPager extends ViewPager implements CoordinatorLayout.AttachedBehavior {

    public NestedScrollViewPager(@NonNull  Context context) {
        super(context);
    }

    public NestedScrollViewPager(@NonNull  Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @NonNull
    @Override
    public CoordinatorLayout.Behavior getBehavior() {
        return new ViewPagerBehavior();
    }
}
