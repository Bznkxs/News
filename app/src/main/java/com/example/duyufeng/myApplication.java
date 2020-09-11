package com.example.duyufeng;

import android.app.Application;
import com.example.duyufeng.ui.main.SectionsPagerAdapter;

import java.util.LinkedList;

// 包装一个Application用来在Activities之间传递数据
public class myApplication extends Application {
    public LinkedList<News> list;
    public NewsProvider provider;
    public NewsProvider providerPaper;
    public int navId;
    public News detailNews;
    public PersonInfo personInfo;

    public int tabConfig = 3;
    public SectionsPagerAdapter adapter;
}
