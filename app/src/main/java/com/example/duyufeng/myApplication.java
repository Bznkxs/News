package com.example.duyufeng;

import android.app.Application;

import java.util.LinkedList;

// 包装一个Application用来在Activities之间传递数据
public class myApplication extends Application {
    public LinkedList<NewsItem> list;
    public myApplication(LinkedList<NewsItem> list) {
        this.list = list;
    }
    public myApplication() {
        this.list = new LinkedList<>();
    }
}
