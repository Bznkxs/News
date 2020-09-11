package com.example.duyufeng.ui.trend;

import com.example.duyufeng.News;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Words {
    public String w;
    public Set<Texts> news;

    public Words(String ws) { w = ws; WordMap.putWords(ws, this); news = new HashSet<>();
    }

    public Texts[] getNews() {
        return news.toArray(new Texts[0]);
    }
}
