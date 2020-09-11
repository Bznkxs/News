package com.example.duyufeng.ui.trend;

import java.util.Map;

public class WordMap {
    static Map<String, WrpClass> map;


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
}
