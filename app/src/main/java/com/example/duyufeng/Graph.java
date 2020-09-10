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
    int max_limit=200;
    public Graph(String mainWord) throws IOException, JSONException {
        root=new NodeInfo(mainWord);
        table.put(mainWord,root);
        dfs(root,1);
        for(String i:relations.keySet()){
            String[] arr=i.split(sep);
            String u=arr[0],v=arr[1];
            NodeInfo nu=table.get(u),nv=table.get(v);
            if(nu!=null&&nv!=null){
                nu.addEdge(nv);
            }
        }
    }

    // TODO: 图谱需要进一步解析！
    // 钟南山（["",""]，顺便清除空串)
    // 病毒（、）
    void dfs(NodeInfo x, int dep) throws IOException, JSONException {
        if(dep==0) return;
        visited.add(x.label);
        JSONArray a = UrlToJson.parse("https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery?entity="+x.label).getJSONArray("data");
        for(int i=0;i<a.length();++i){
            JSONObject o=a.getJSONObject(i);
            String label=o.getString("label");
            if(!x.label.equals(label)) continue;
            JSONObject abs=o.getJSONObject("abstractInfo");
            String desc=abs.getString("enwiki");
            if(desc.equals("")) desc=abs.getString("baidu");
            if(desc.equals("")) desc=abs.getString("zhwiki");
            x.desc=desc;
            abs=abs.getJSONObject("COVID");
            x.pushMap(abs.getJSONObject("properties"));

            JSONArray nea=abs.getJSONArray("relations");
            for(int j=0;j<nea.length();++j){
                JSONObject ob=nea.getJSONObject(j);
                String to=ob.getString("label");
                boolean forward=ob.getBoolean("forward");
                String relation=ob.getString("relation");
                String raw=(forward)?x.label+sep+to:to+sep+x.label;
                relations.put(raw,relation);
                NodeInfo y=table.getOrDefault(to,null);
                if(y==null){
                    y=new NodeInfo(to);
                    table.put(to,y);
                }

                if(!visited.contains(to)) dfs(y,dep-1);
                if(table.size()>max_limit) break;
            }
            break;
        }
    }
    public NodeInfo getRoot(){
        return root;
    }
    public String getRelation(String a,String b){
        return relations.get(a+sep+b);
    }
}
