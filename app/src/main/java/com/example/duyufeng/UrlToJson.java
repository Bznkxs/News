package com.example.duyufeng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
class JsonThread extends Thread
{
    String res;
    URL url;
    public JsonThread(URL url){
        super();
        this.url=url;
    }
    @Override
    public void run(){
        HttpURLConnection conn= null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(30000);
            BufferedReader rd = null;
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder sb = new StringBuilder();
            int ch;
            while ((ch = rd.read()) != -1) {
                sb.append((char) ch);
            }
            rd.close();
            res = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
public class UrlToJson {
    public static JSONObject parse(String addr) throws IOException, JSONException{
        URL url = new URL(addr);
        JsonThread th=new JsonThread(url);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new JSONObject(th.res);
    }
}
