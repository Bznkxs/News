package com.example.duyufeng;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Provider;
import java.util.stream.Collectors;

public class NewsInfo implements News {
    String id,type,title,time,source,content;
    public NewsProvider provider;
    boolean saved;
    static final String sep="@@@";
    NewsInfo(String id,String type,String title,String time,String source,String content, NewsProvider provider){
        this.id=id;
        this.type=type;
        this.title=title;
        this.time=time;
        this.source=source;
        this.content=content;
        this.provider = provider;
    }
    NewsInfo(JSONObject o, NewsProvider provider) throws JSONException {
        this.id=o.getString("_id");
        this.type=o.getString("type");
        this.title=o.getString("title");
        this.time=o.getString("time");
        this.source=o.getString("source");
        this.content=o.getString("content");
        this.provider = provider;
    }
    public String getType(){
        return type;
    }

    @Override
    public String getName() {
        return getTitle();
    }

    public String getTitle(){
        return title;
    }

    @Override
    public String getDate() {
        return getTime();
    }

    public String getTime(){
        return time;
    }

    @Override
    public String getAuthor() {
        return getSource();
    }

    @Override
    public String getAbstract() {
        return null;
    }

    @Override
    public void saveContent() throws IOException {
        this.saved = true;
        save(provider.context);
    }

    @Override
    public void removeContent() throws IOException {
        provider.removeSaved(id+".news");
        FileOutputStream out=provider.context.openFileOutput(id+".news", Context.MODE_PRIVATE);
        this.saved = false;
        out.write("".getBytes());
        out.close();
    }

    @Override
    public boolean isSaved() {
        return saved;
    }

    public String getSource(){
        return source;
    }
    public String getContent(){
        return content;
    }
    public void save(Context context) throws IOException {
        provider.addSaved(id+".news");
        FileOutputStream out=context.openFileOutput(id+".news",Context.MODE_PRIVATE);


        String tot=id+sep+type+sep+title+sep+time+sep+source+sep+content;
        out.write(tot.getBytes());
        out.close();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static NewsInfo load(NewsProvider provider, String filename) throws FileNotFoundException {
        FileInputStream in=provider.context.openFileInput(filename);
        String tot=new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));
        String[] arr=tot.split(sep);
        String id=arr[0],type=arr[1],title=arr[2],time=arr[3],source=arr[4],content=arr[5];
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NewsInfo info = new NewsInfo(id,type,title,time,source,content,provider);
        info.saved = true;
        return info;
    }
    public boolean search(String text){
        String tot=id+sep+type+sep+title+sep+time+sep+source+sep+content;
        return tot.contains(text);
    }
}
