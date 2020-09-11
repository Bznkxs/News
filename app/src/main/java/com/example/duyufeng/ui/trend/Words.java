package com.example.duyufeng.ui.trend;

import com.example.duyufeng.News;

import java.util.ArrayList;

public class Words {
    String w;
    ArrayList<News> news;

    Words(String ws) { w = ws; WordMap.putWords(ws, this); }
}
