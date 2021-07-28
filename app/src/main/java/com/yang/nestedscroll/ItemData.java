package com.yang.nestedscroll;

public class ItemData {
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_VIEW_PAGER = 1;

    private int type;

    private String text;

    public ItemData(int type) {
        this.type = type;
    }

    public ItemData(int type, String text) {
        this.type = type;
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
