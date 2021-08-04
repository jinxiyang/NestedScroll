package com.yang.ibl.scrollrecyclerview;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yang.ibl.scrollrecyclerview.library.ChildRecyclerView;
import com.yang.ibl.scrollrecyclerview.utils.UIUtils;

public class PagerViewHolder {

    public static PagerViewHolder inflate(ViewGroup container, int position){
        ChildRecyclerView recyclerView = new ChildRecyclerView(container.getContext());
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

    private ChildRecyclerView recyclerView;

    public PagerViewHolder(View itemView) {
        this.recyclerView = (ChildRecyclerView) itemView;
    }

    public View getItemView() {
        return recyclerView;
    }

    public ChildRecyclerView getChildRecyclerView() {
        return recyclerView;
    }

    public void setOnSelected(int position){
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null){
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            adapter = new ParentAdapter(30, false);
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

                final int padding = UIUtils.dp2px(1);

                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.bottom = padding;
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }
}
