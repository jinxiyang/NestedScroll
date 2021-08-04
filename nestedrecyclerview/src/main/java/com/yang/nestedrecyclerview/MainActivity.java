package com.yang.nestedrecyclerview;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ParentRecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.initLinearLayoutManager();
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            final int padding = UIUtils.dp2px(1);

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = padding;
            }
        });

        ParentAdapter parentAdapter = new ParentAdapter(15, true);
        recyclerView.setAdapter(parentAdapter);

    }
}