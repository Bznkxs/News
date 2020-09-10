package com.example.duyufeng;

import android.os.Parcelable;

// TODO: 这是我用的news接口。News用来显示新闻正文。
// 我这边使用的逻辑是：在加载新闻列表时，其实加载了News的前四个get()
// 并把News传给NewsItem构造函数。最后当显示时才调用getContent()，这个可能需要加载。
// 看SimpleNews，里面有几个函数是实现Parcelable。如果接口不变就不需要动它们。
public interface News extends Parcelable {
    String getName();  //
    String getDate();
    String getAuthor();
    String getAbstract();
    String getContent();
    String peekContent();
    void removeContent();
}


