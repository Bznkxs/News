package com.example.duyufeng.ui.trend;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordMap {
    static Map<String, WrpClass> map = new HashMap<>();


    static class WrpClass {
        Words w;
        Texts t;
        WrpClass(Words ww) {
            w = ww;
            t = null;
        }
        WrpClass(Texts tt) {
            w = null;
            t = tt;
        }
    }

    static Words getWords(String w) {
        WrpClass gw = map.get(w);
        if (gw == null) {
            return new Words(w);
        }
        return gw.w;
    }

    static Texts getTexts(String w) {
        WrpClass gw = map.get(w);
        if (gw == null) {
            return new Texts(w);
        }
        return gw.t;
    }

    static void putWords(String sw, Words w) {
        map.put(sw, new WrpClass(w));
    }

    static void putTexts(String sw, Texts w) {
        map.put(sw, new WrpClass(w));
    }

    static Words[] getAllWords() {

        WrpClass[] cls = new ArrayList<WrpClass>(map.values()).toArray(new WrpClass[0]);
        ArrayList<Words> words = new ArrayList<>();
        for (WrpClass c : cls) {
            if (c.w != null)
                words.add(c.w);
        }
        return words.toArray(new Words[0]);
    }

    static Texts[] getAllTexts() {
        WrpClass[] cls = new ArrayList<WrpClass>(map.values()).toArray(new WrpClass[0]);
        ArrayList<Texts> texts = new ArrayList<>();
        for (WrpClass c : cls) {
            if (c.t != null)
                texts.add(c.t);
        }
        return texts.toArray(new Texts[0]);
    }
}
