package com.yang.nestedscroll;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class TextVH extends BaseViewHolder {

    public static TextVH inflate(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
        return new TextVH(view);
    }

    private TextView textView;

    public TextVH(@NonNull View itemView) {
        super(itemView);
        textView = (TextView) itemView;
    }

    @Override
    public void bind(ItemData itemData, int position) {
        textView.setText(itemData.getText());
        if (position % 2 == 0){
            textView.setBackgroundColor(Color.GREEN);
        } else {
            textView.setBackgroundColor(Color.YELLOW);
        }
    }
}
