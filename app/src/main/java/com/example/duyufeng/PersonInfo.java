package com.example.duyufeng;

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
}
