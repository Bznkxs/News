package com.example.duyufeng;


import android.os.Parcelable;

// TODO: 这是我的新闻列表项接口。Parcelable需要实现如SimpleNewsItem中
/*
    public int describeContents()

    public void writeToParcel(Parcel out, int flags)

    public static final Parcelable.Creator<NewsItem> CREATOR
            = new Parcelable.Creator<NewsItem>()

    public SimpleNewsItem(Parcel in)
 */
// TODO: 的四项。getNews()的含义是，你在新闻列表页点击了某个项以后，
//  这个News对象会被传递给详情页进行加载。请看News

public interface NewsItem extends Parcelable {
    static final int Cached = 1;
    String getName();  //
    String getDate();
    String getAuthor();
    String getAbstract();
    com.example.duyufeng.News getNews();
}

