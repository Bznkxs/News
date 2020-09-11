package com.example.duyufeng;

import android.graphics.Bitmap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PersonInfo {
    JSONObject o;
    PersonInfo(JSONObject o){
        this.o=o;
    }
    public String get_1(String what) throws JSONException {
        return o.getString(what);
    }
    public String get_2(String what,String what2) throws JSONException {
        return o.getJSONObject(what).getString(what2);
    }
    public String[] get_a(String what) throws JSONException {
        JSONArray a=o.getJSONArray(what);
        String[] res=new String[a.length()];
        for(int i=0;i<a.length();++i){
            res[i]=a.getString(i);
        }
        return res;
    }

    public String getName() throws JSONException {
        String st;
        try {
            st = get_1("name_zh");
            if (st.equals(""))
                st = get_1("name");
        } catch (JSONException e) {
            st = get_1("name");
        }
        return st;
    }

    public String getNames() throws JSONException {
        String st;
        try {
            st = get_1("name_zh");
        } catch (JSONException e) {
            st = "";
        }
        String st1;
        try {
            st1 = get_1("name");
        } catch (JSONException e) {
            st1 = "";
        }
        return st + "  " + st1;
    }

    public String getAvatar() throws JSONException {
        return get_1("avatar");
    }

    public String getStatus() throws JSONException {
        String s = get_1("is_passedaway");
        if (s.equals("true")) return "追忆学者";
        else return "";
    }

    public Bitmap img;
    public Bitmap getImg() { return img; }
}
