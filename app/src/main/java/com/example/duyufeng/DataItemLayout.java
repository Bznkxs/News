package com.example.duyufeng;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class DataItemLayout extends LinearLayout{
    Context context;
    public DataItemLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.data_row_actually_used,this);
        this.context = context;
    }
}
