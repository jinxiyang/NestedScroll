package com.yang.ibl.scrollrecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

public class ParentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_VIEW_PAGER = 1;

    private List<ItemData> dataList;

    public ParentAdapter(int count, boolean addViewPage) {
        dataList = new ArrayList<>();
        for (int i = 0; i < count; i++){
            ItemData itemData = new ItemData();
            itemData.type = TYPE_TEXT;
            dataList.add(itemData);
        }

        if (addViewPage){
            ItemData itemData = new ItemData();
            itemData.type = TYPE_VIEW_PAGER;
            dataList.add(itemData);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == TYPE_VIEW_PAGER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager, parent, false);
            viewHolder = new ViewPagerVH(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
            viewHolder = new TextVH(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TextVH){
            ((TextVH) holder).textView.setText(String.valueOf(position));
        } else if (holder instanceof ViewPagerVH){
            ((ViewPagerVH) holder).bind();
        }
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type;
    }

    static class ItemData{
        int type;
    }

    static class TextVH extends RecyclerView.ViewHolder {

        TextView textView;

        public TextVH(@NonNull  View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
    
    static class ViewPagerVH extends RecyclerView.ViewHolder {
        MagicIndicator magicIndicator;
        ViewPager viewPager;

        ViewPagerAdapter viewPagerAdapter;

        public ViewPagerVH(@NonNull View itemView) {
            super(itemView);
            magicIndicator = itemView.findViewById(R.id.magicIndicator);
            viewPager = itemView.findViewById(R.id.viewPager);
        }

        public void bind(){
            if (viewPagerAdapter == null){
                viewPagerAdapter = new ViewPagerAdapter(4);
                viewPager.setOffscreenPageLimit(4);
                viewPager.setAdapter(viewPagerAdapter);
            }
        }
    }
}
