package com.example.duyufeng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class NodeInfo {
    String label;
    String desc;
    String img;
    HashMap<String,String> prop=new HashMap<String,String>();
    ArrayList<NodeInfo> edges=new ArrayList<NodeInfo>();
    ArrayList<NodeInfo> r_edges=new ArrayList<NodeInfo>();
    NodeInfo(String label){
        this.label=label;
    }
    void pushMap(JSONObject o) throws JSONException {
        JSONArray a=o.names();
        if(a==null) return;
        for(int i=0;i<a.length();++i){
            String key=a.getString(i);
            String val=o.getString(key);
            prop.put(key,val);
        }
    }
    public String getLabel(){
        return label;
    }
    public String getDescription(){
        return desc;
    }
    public String getImg(){
        return img;
    }
    public HashMap<String,String> getProperties(){
        return prop;
    }
    public ArrayList<NodeInfo> thisToOthers(){
        return edges;
    }
    public ArrayList<NodeInfo> othersToThis(){
        return r_edges;
    }
    void addEdge(NodeInfo other){
        edges.add(other);
        other.r_edges.add(this);
    }
}
