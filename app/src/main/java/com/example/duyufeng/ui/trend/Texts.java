package com.example.duyufeng.ui.trend;

import java.util.ArrayList;

public class Texts {
    String t;
    ArrayList<Words> words;
    Texts(String ws) { t = ws; WordMap.putTexts(ws, this); }
}
