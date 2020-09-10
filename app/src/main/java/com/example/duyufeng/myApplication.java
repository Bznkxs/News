package com.example.duyufeng;

import android.app.Application;

import java.util.LinkedList;

// 包装一个Application用来在Activities之间传递数据
public class myApplication extends Application {
    public LinkedList<News> list;
    public NewsProvider provider;
    public int navId;
    public News detailNews;
}
