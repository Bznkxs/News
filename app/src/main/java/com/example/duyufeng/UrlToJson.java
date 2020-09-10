package com.example.duyufeng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlToJson {
    public static JSONObject parse(String addr) throws IOException, JSONException {
        URL url = new URL(addr);
        BufferedReader rd=new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb=new StringBuilder();
        int ch;
        while((ch=rd.read())!=-1){
            sb.append((char)ch);
        }
        rd.close();
        return new JSONObject(sb.toString());
    }
}
