package com.example.duyufeng;

import android.os.Parcel;
import android.os.Parcelable;

public class SimpleNewsItem implements NewsItem {
    String name, date, author, abst;
    News news;
    int maxAbstractLen = -1;

    public SimpleNewsItem(String name, String date, String author, String abst) {
        this.name = name;
        this.date = date;
        this.author = author;
        this.abst = abst;
        news = null;
    }

   public SimpleNewsItem(News news) {
        name = news.getName();
        date = news.getDate();
        author = news.getAuthor();
        abst = news.getAbstract();
        this.news = news;
    }

   public  SimpleNewsItem() { name = null; date = null; author = null; abst = null; }


    public void setMaxAbstractLen(int nv) {
        maxAbstractLen = nv;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getAbstract() {
        if (maxAbstractLen < 0)
            return abst;
        return abst.substring(0, maxAbstractLen);
    }

    @Override
    public News getNews() {
        return news;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(date);
        out.writeString(author);
        out.writeString(abst);
        out.writeParcelable(news, flags);
    }

    public static final Parcelable.Creator<NewsItem> CREATOR
            = new Parcelable.Creator<NewsItem>() {
        public NewsItem createFromParcel(Parcel in) {
            return new com.example.duyufeng.SimpleNewsItem(in);
        }

        public NewsItem[] newArray(int size) {
            return new com.example.duyufeng.SimpleNewsItem[size];
        }
    };

    public SimpleNewsItem(Parcel in) {
        name = in.readString();
        date = in.readString();
        author = in.readString();
        abst = in.readString();
        news = in.readParcelable(news.getClass().getClassLoader());
    }
}
