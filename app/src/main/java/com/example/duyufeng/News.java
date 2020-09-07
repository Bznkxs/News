package com.example.duyufeng;

import android.os.Parcelable;

public interface News extends Parcelable {
    String getName();  //
    String getDate();
    String getAuthor();
    String getAbstract();
    String getContent();
}


