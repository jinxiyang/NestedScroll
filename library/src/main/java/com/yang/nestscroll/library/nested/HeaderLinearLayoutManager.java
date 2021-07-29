package com.yang.nestscroll.library.nested;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

public class HeaderLinearLayoutManager extends LinearLayoutManager {

    public HeaderLinearLayoutManager(Context context) {
        super(context);
    }

    public HeaderLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public HeaderLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean canScrollVertically() {
        return super.canScrollVertically();
    }
}
