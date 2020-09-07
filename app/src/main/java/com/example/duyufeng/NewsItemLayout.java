package com.example.duyufeng;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class NewsItemLayout extends LinearLayout{
    NewsItem item;
    Context context;
    public NewsItemLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_without_icon,this);
        this.context = context;
    }
}
