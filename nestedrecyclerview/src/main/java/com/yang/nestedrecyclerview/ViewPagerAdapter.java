package com.yang.nestedrecyclerview;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private static final String TAG = "======";

    private List<PagerViewHolder> cacheViews = new ArrayList<>();

    private int count;

    public ViewPagerAdapter(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PagerViewHolder viewHolder;
        if (!cacheViews.isEmpty()){
            viewHolder = cacheViews.remove(0);
            Log.i(TAG, "instantiateItem: cacheViews " + position);
        } else {
            viewHolder = PagerViewHolder.inflate(container, position);
            Log.i(TAG, "instantiateItem: inflate " + position);
        }
        container.addView(viewHolder.getItemView());
        return viewHolder;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.i(TAG, "setPrimaryItem: " + position);
        PagerViewHolder viewHolder = (PagerViewHolder) object;
        viewHolder.getChildRecyclerView().setChildRecyclerViewForParent();
        viewHolder.setOnSelected(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.i(TAG, "destroyItem: " + position);
        PagerViewHolder viewHolder = (PagerViewHolder) object;
        container.removeView(viewHolder.getItemView());
        cacheViews.add(viewHolder);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        Log.i(TAG, "isViewFromObject: ");
        PagerViewHolder viewHolder = (PagerViewHolder) object;
        return view == viewHolder.getItemView();
    }
}
