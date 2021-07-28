package com.yang.nestedscroll;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        List<ItemData> dataList = createDataList();
        ParentAdapter parentAdapter = new ParentAdapter(dataList);
        recyclerView.setAdapter(parentAdapter);
    }


    private List<ItemData> createDataList(){

        List<ItemData> dataList = new ArrayList<>();

        for (int i = 0; i < 20; i++){
            ItemData itemData = new ItemData(ItemData.TYPE_TEXT, String.format("第%s个", i));
            dataList.add(itemData);
        }

        ItemData itemData = new ItemData(ItemData.TYPE_VIEW_PAGER, "我是ViewPager指示器");
        dataList.add(itemData);

        return dataList;
    }
}