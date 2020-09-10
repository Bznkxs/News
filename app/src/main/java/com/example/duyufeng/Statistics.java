package com.example.duyufeng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Statistics {
    ArrayList<StatInfo> chinese=new ArrayList<StatInfo>();
    ArrayList<StatInfo> foreign=new ArrayList<StatInfo>();
    public Statistics() throws IOException, JSONException {
        refresh();
    }
    public void refresh() throws IOException, JSONException {
        chinese.clear();
        foreign.clear();
        JSONObject o = UrlToJson.parse("https://covid-dashboard.aminer.cn/api/dist/epidemic.json");
        JSONArray names=o.names();
        for(int i=0;i<names.length();++i){
            String key=names.getString(i);
            JSONArray a=o.getJSONObject(key).getJSONArray("data");
            a=a.getJSONArray(a.length()-1);
            String CONFIRMED=a.getString(0);
            String SUSPECTED=a.getString(1);
            String CURED=a.getString(2);
            String DEAD=a.getString(3);
            String[] arr=key.split("\\|");
            StatInfo tmp=new StatInfo(null,CONFIRMED,SUSPECTED,CURED,DEAD);
            if(arr[0].equals("China")&&arr.length==2){
                tmp.name=arr[1];
                chinese.add(tmp);
            }
            else if(!arr[0].equals("China")&&arr.length==1){
                tmp.name=arr[0];
                foreign.add(tmp);
            }
        }
    }
    public StatInfo[] getChineseData(){
        return chinese.toArray(new StatInfo[0]);
    }
    public StatInfo[] getForeignData(){
        return foreign.toArray(new StatInfo[0]);
    }
}
