package com.example.duyufeng;

import java.io.IOException;

// TODO: 这是我用的news接口。News用来显示新闻正文。
// News没加载好有两种可能。这里选择的是：在构造函数中加载News。
// 把“加载”和缓存分开来。缓存是缓存到本地文件。删除删除的是缓存文件。
// 加载是加载到内存。
// 如果删除缓存时需要一并删除加载的数据，需要另外写代码回收News对象。

public interface News {
    String getName();  //
    String getDate();
    String getAuthor();
    String getAbstract();
    default String getType() { return ""; }
    String getContent();
    void saveContent() throws IOException;
    void removeContent() throws IOException;
    boolean isSaved();
    default boolean search(String text) {
        return false;
    }
    /*
    String peekContent();
    void removeContent();

     */
}


