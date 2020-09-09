package com.example.duyufeng;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class DataItemHeadLayout extends LinearLayout{
    Context context;
    public DataItemHeadLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.data_rowhead,this);
        this.context = context;
    }
}
