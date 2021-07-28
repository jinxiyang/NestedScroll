package com.yang.nestedscroll;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerVH extends BaseViewHolder {

    public static ViewPagerVH inflate(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpager, parent, false);
        return new ViewPagerVH(view);
    }

    private TextView textView;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            selectedPosition = position;
            showIndicatorText();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int selectedPosition;

    private ItemData itemData;
    
    public ViewPagerVH(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);
        viewPager = itemView.findViewById(R.id.viewPager);

        textView.setOnClickListener(v -> {
            int pos = (selectedPosition + 1) % 6;
            if (adapter != null){
                viewPager.setCurrentItem(pos);
            }
        });
    }

    private void showIndicatorText(){
        textView.setText(itemData.getText() + "   第" + selectedPosition + "页");
    }

    @Override
    public void bind(ItemData itemData, int position) {
        this.itemData = itemData;
        if (adapter == null) {
            adapter = new ViewPagerAdapter(6);
            viewPager.addOnPageChangeListener(onPageChangeListener);
            viewPager.setOffscreenPageLimit(6);
            viewPager.setAdapter(adapter);
        }
    }

}
