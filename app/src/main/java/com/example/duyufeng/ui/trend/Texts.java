package com.example.duyufeng.ui.trend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Texts {
    public String t;
    public Set<Words> words;
    public Texts(String ws) { t = ws; WordMap.putTexts(ws, this); words = new HashSet<>();
    }

    public Words[] getWords() {
        return words.toArray(new Words[0]);
    }
}
