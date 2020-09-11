package com.example.duyufeng;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class PeopleLayout extends LinearLayout{
    PersonInfo item;
    Context context;
    public PeopleLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_with_icon,this);
        this.context = context;
    }
}
