package com.yang.nestedscroll;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ParentAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<ItemData> dataList;

    public ParentAdapter(List<ItemData> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ItemData.TYPE_VIEW_PAGER){
            return ViewPagerVH.inflate(parent);
        } else {
            return TextVH.inflate(parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ItemData itemData = dataList.get(position);
        holder.bind(itemData, position);
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getType();
    }
}
