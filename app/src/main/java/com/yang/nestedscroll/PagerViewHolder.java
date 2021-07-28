package com.yang.nestedscroll;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PagerViewHolder {

    public static PagerViewHolder inflate(ViewGroup container, int position){
        RecyclerView recyclerView = new RecyclerView(container.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        recyclerView.setLayoutParams(layoutParams);
        int color = Color.GRAY;
        switch (position){
            case 0:
                color = Color.RED;
                break;
            case 1:
                color = Color.BLUE;
                break;
            case 2:
                color = Color.GREEN;
                break;
            case 3:
                color = Color.YELLOW;
                break;
            case 4:
                color = Color.MAGENTA;
                break;
        }
        recyclerView.setBackgroundColor(color);
        return new PagerViewHolder(recyclerView);
    }

    private RecyclerView recyclerView;
    
    public PagerViewHolder(View itemView) {
        this.recyclerView = (RecyclerView) itemView;
    }

    public View getItemView() {
        return recyclerView;
    }

    public void setOnSelected(int position){
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null){
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            adapter = new ParentAdapter(createDataList());
            recyclerView.setAdapter(adapter);
        }
    }

    private List<ItemData> createDataList(){
        List<ItemData> dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            ItemData itemData = new ItemData(ItemData.TYPE_TEXT, String.format("第%s个", i));
            dataList.add(itemData);
        }
        return dataList;
    }
}
