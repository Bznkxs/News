package com.example.duyufeng;

import android.os.Build;
import androidx.annotation.RequiresApi;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Graph {
    HashSet<String> visited=new HashSet<String>();
    HashMap<String,String> relations=new HashMap<String,String>();
    HashMap<String,NodeInfo> table=new HashMap<String,NodeInfo>();
    static final String sep="@@@";
    NodeInfo root;
    public Graph(String mainWord) throws IOException, JSONException {
        root=new NodeInfo(mainWord);
        dfs(root,5);
        for(String i:relations.values()){
            String[] arr=i.split(sep);
            String u=arr[0],v=arr[1];
            NodeInfo nu=table.get(u),nv=table.get(v);
            if(nu!=null&&nv!=null){
                nu.addEdge(nv);
            }
        }
    }
    
    void dfs(NodeInfo x, int dep) throws IOException, JSONException {
        if(dep==0) return;
        visited.add(x.label);
        JSONArray a = UrlToJson.parse("https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery?entity="+x.label).getJSONArray("data");
        for(int i=0;i<a.length();++i){
            JSONObject o=a.getJSONObject(i);
            String label=o.getString("label");
            NodeInfo cur=table.getOrDefault(label,null);
            if(cur==null){
                cur=new NodeInfo(label);
                table.put(label,cur);
            }
            JSONObject abs=o.getJSONObject("abstractInfo");
            String desc=abs.getString("enwiki");
            if(desc.equals("")) desc=abs.getString("baidu");
            if(desc.equals("")) desc=abs.getString("zhwiki");
            cur.desc=desc;
            abs=abs.getJSONObject("COVID");
            cur.pushMap(abs.getJSONObject("properties"));
            JSONArray nea=abs.getJSONArray("relations");
            for(int j=0;j<nea.length();++j){
                JSONObject ob=nea.getJSONObject(j);
                String to=ob.getString("label");
                boolean forward=ob.getBoolean("forward");
                String relation=ob.getString("relation");
                relations.put(x.label+sep+to,relation);
                NodeInfo y=new NodeInfo(to);
                if(!visited.contains(to)) dfs(y,dep-1);
            }
        }
    }
    public NodeInfo getRoot(){
        return root;
    }
    public String getRelation(String a,String b){
        return relations.get(a+sep+b);
    }
}
