package com.example.duyufeng;


import android.os.Parcelable;

public interface NewsItem extends Parcelable {
    String getName();  //
    String getDate();
    String getAuthor();
    String getAbstract();
    com.example.duyufeng.News getNews();
}

