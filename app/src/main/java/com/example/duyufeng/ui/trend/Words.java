package com.example.duyufeng.ui.trend;

import com.example.duyufeng.News;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Words {
    String w;
    Set<Texts> news;

    Words(String ws) { w = ws; WordMap.putWords(ws, this); news = new HashSet<>();
    }

    public Texts[] getNews() {
        return (Texts[]) news.toArray();
    }
}
