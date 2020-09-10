package com.example.duyufeng;

import android.app.Application;

import java.util.LinkedList;

// 包装一个Application用来在Activities之间传递数据
public class myApplication extends Application {
    public LinkedList<NewsItem> list;
    public int navId;
    public News detailNews;
}
