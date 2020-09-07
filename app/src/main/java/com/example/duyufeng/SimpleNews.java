package com.example.duyufeng;

import android.os.Parcel;
import android.os.Parcelable;

public class SimpleNews implements News  {
    String name, date, author, abst;
    String content;
    SimpleNews(String s) {
        name = "";
        date = "";
        author = "";
        abst = "";
        content = s;
    }

    SimpleNews() {
        name = "";
        date = "";
        author = "";
        abst = "";
        content = null;
    }

    public SimpleNews(String name, String date, String author, String abst, String s) {
        this.name = name;
        this.date = date;
        this.author = author;
        this.abst = abst;
        content = s;
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
        return abst;
    }

    @Override
    public String getContent() {
        if (content == null) {
            content = "";
        }
        return content;
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
        out.writeString(content);
    }

    public static final Parcelable.Creator<News> CREATOR
            = new Parcelable.Creator<News>() {
        public News createFromParcel(Parcel in) {
            return new com.example.duyufeng.SimpleNews(in);
        }

        public News[] newArray(int size) {
            return new com.example.duyufeng.SimpleNews[size];
        }
    };

    private SimpleNews(Parcel in) {
        name = in.readString();
        date = in.readString();
        author = in.readString();
        abst = in.readString();
        content = in.readString();
    }
}
