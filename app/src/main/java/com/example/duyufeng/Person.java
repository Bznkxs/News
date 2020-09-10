package com.example.duyufeng;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class Person {
    ArrayList<PersonInfo> people=new ArrayList<PersonInfo>();
    public Person() throws IOException, JSONException {
        JSONArray a = UrlToJson.parse("https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2").getJSONArray("data");
        for(int i=0;i<a.length();++i){
            people.add(new PersonInfo(a.getJSONObject(i)));
        }
    }
    public ArrayList<PersonInfo> getPeople(){
        return people;
    }
}
