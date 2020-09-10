package com.example.duyufeng.ui.data;

import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.duyufeng.PandemicData;
import com.example.duyufeng.SimplePandemicData;
import com.example.duyufeng.StatInfo;
import com.example.duyufeng.UrlToJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class DataViewModel extends ViewModel implements Loader.Test {
    ArrayList<StatInfo> chinese=new ArrayList<StatInfo>();
    ArrayList<StatInfo> foreign=new ArrayList<StatInfo>();
    MutableLiveData<ArrayList<StatInfo>> chineseData=new MutableLiveData<>();
    MutableLiveData<ArrayList<StatInfo>> foreignData=new MutableLiveData<>();
    public DataViewModel() throws IOException, JSONException {
        chineseData.setValue(chinese);
        foreignData.setValue(foreign);
        refresh();
    }
    public void refresh() throws IOException, JSONException {
        Loader loader = new Loader(this);
        loader.start();
    }

    public void onLoadFinished(ArrayList<StatInfo> c, ArrayList<StatInfo> f) {
        chinese = c;
        foreign = f;
        chineseData.postValue(chinese);
        foreignData.postValue(foreign);
    }

    public LiveData<ArrayList<StatInfo>> getDomestic() {
        return chineseData;
    }

    public LiveData<ArrayList<StatInfo>> getInternational() {
        return foreignData;
    }




}

class Loader extends Thread {
    // multithread programming
    private final Test test;
    ArrayList<StatInfo> chinese=new ArrayList<StatInfo>();
    ArrayList<StatInfo> foreign=new ArrayList<StatInfo>();

    public interface Test{
        void onLoadFinished(ArrayList<StatInfo> chinese, ArrayList<StatInfo> foreign);
    }

    public Loader(DataViewModel model) {
        test = model;
    }

    private void refresh() throws IOException, JSONException {
        JSONObject o = UrlToJson.parse("https://covid-dashboard.aminer.cn/api/dist/epidemic.json");
        if (o == null)
            return;
        JSONArray names=o.names();
        for(int i = 0; i< Objects.requireNonNull(names).length(); ++i){
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
        Comparator<StatInfo> comparator = new Comparator<StatInfo>() {
            @Override
            public int compare(StatInfo o1, StatInfo o2) {
                return -Integer.valueOf(o1.getCONFIRMED()).compareTo(Integer.parseInt(o2.getCONFIRMED()));
            }
        };
        chinese.sort(comparator);
        foreign.sort(comparator);
    }

    public void run() {
        try {
            refresh();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        finished();
    }

    public void finished() {
        if(test != null){
            test.onLoadFinished(chinese, foreign);
        }
    }
}