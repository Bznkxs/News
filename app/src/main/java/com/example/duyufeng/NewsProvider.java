package com.example.duyufeng;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class NewsProvider {
    public ArrayList<NewsInfo> pool=new ArrayList<NewsInfo>();
    public HashSet<String> inpool=new HashSet<String>();
    public ArrayList<NewsInfo> hist=new ArrayList<NewsInfo>();;
    int pagenow=1;
    public Context context;
    public HashSet<String> savedNames = new HashSet<>();
    public NewsProvider(Context context) throws IOException, JSONException {
        this.context = context;
        File dir=context.getFilesDir();

        File fdir = new File(dir, "newslist");

        try {
            Scanner scanner = new Scanner(fdir);

            while (scanner.hasNextLine())
                savedNames.add(scanner.nextLine());
            scanner.close();
        } catch (FileNotFoundException e) {
            FileOutputStream out=context.openFileOutput("newslist",Context.MODE_PRIVATE);
            out.write("".getBytes());
            out.close();
        }

        for(String i:dir.list()){
            File f=new File(dir,i);

            if(f.isFile()&&i.contains(".news")){
                if (savedNames.contains(i)) {
                    try {
                    NewsInfo ni = NewsInfo.load(this, i);
                    pool.add(ni);
                    inpool.add(ni.id); } catch (Exception e) {
                        f.delete();
                    }
                } else {
                    f.delete();
                }
            }
        }
    }
    public NewsInfo[] moreNews(int x) throws IOException, JSONException {
        ArrayList<NewsInfo> res=new ArrayList<NewsInfo>();
        while(x!=0) {
            JSONArray a = UrlToJson.parse("https://covid-dashboard.aminer.cn/api/events/list?page=" + String.valueOf(pagenow)).getJSONArray("data");
            if(a.length()==0) break;
            for (int i = 0; i < a.length(); ++i) {
                JSONObject o = a.getJSONObject(i);
                NewsInfo ni = new NewsInfo(o, this);
                if (!inpool.contains(ni.id)) {
                    --x;
                    res.add(ni);
                    inpool.add(ni.id);
                }
                ++pagenow;
                if(x==0) break;
            }
        }
        pool.addAll(res);
        return res.toArray(new NewsInfo[0]);
    }
    public NewsInfo[] refreshNews(int x) throws IOException, JSONException {
        ArrayList<NewsInfo> res=new ArrayList<NewsInfo>();
        int beg=1;
        while(x!=0){
            JSONArray a = UrlToJson.parse("https://covid-dashboard.aminer.cn/api/events/list?page="+String.valueOf(beg)+"&size=" + String.valueOf(x)).getJSONArray("data");
            if(a.length()==0) break;
            for (int i = 0; i < a.length(); ++i) {
                JSONObject o = a.getJSONObject(i);
                NewsInfo ni = new NewsInfo(o, this);
                if (!inpool.contains(ni.id)) {
                    --x;
                    res.add(ni);
                    inpool.add(ni.id);
                }
                else  break;
                ++pagenow;
                if(x==0) break;
            }
            beg+=a.length();
        }
        pool.addAll(res);
        return res.toArray(new NewsInfo[0]);
    }
    public NewsInfo[] search(String text){
        ArrayList<NewsInfo> tmp=new ArrayList<NewsInfo>();
        pool.forEach((NewsInfo i)->{if(i.search(text)) tmp.add(i);});
        return tmp.toArray(new NewsInfo[0]);
    }
    public NewsInfo[] filter(String type){
        ArrayList<NewsInfo> tmp=new ArrayList<NewsInfo>();
        pool.forEach((NewsInfo i)->{if(i.getType()==type) tmp.add(i);});
        return tmp.toArray(new NewsInfo[0]);
    }
    public void addHistory(NewsInfo ni){
        hist.add(ni);
    }
    public NewsInfo[] getHistory(){
        return hist.toArray(new NewsInfo[0]);
    }

    public void addSaved(String s) {
        savedNames.add(s);
        try {
            FileOutputStream out=context.openFileOutput("newslist",Context.MODE_PRIVATE);
            for (String s1 : savedNames) {
                out.write((s1+"\n").getBytes());
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeSaved(String s) {
        savedNames.remove(s);
        try {
            FileOutputStream out=context.openFileOutput("newslist",Context.MODE_PRIVATE);
            for (String s1 : savedNames) {
                out.write((s1+"\n").getBytes());
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
