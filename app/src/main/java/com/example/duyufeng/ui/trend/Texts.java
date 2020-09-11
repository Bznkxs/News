package com.example.duyufeng.ui.trend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Texts {
    String t;
    Set<Words> words;
    Texts(String ws) { t = ws; WordMap.putTexts(ws, this); words = new HashSet<>();
    }

    public Words[] getWords() {
        return (Words[]) words.toArray();
    }
}
