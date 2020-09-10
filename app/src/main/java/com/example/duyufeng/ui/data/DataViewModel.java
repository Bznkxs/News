package com.example.duyufeng.ui.data;

import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.duyufeng.PandemicData;
import com.example.duyufeng.SimplePandemicData;

import java.util.HashMap;
import java.util.Random;

public class DataViewModel extends ViewModel {
    private HashMap<String, PandemicData> domesticData;
    private MutableLiveData<HashMap<String, PandemicData>> domestic;
    private HashMap<String, PandemicData> internationalData;
    private MutableLiveData<HashMap<String, PandemicData>> international;

    Random rng = new Random();

    public LiveData<HashMap<String, PandemicData>> getDomestic() {
        if (domestic == null) {
            domestic = new MutableLiveData<>();
            domesticData = new HashMap<>();
            loadDomestic();
        }
        return domestic;
    }


    public void loadDomestic() {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        for (String i : domesticData.keySet()) {
            domesticData.put(i, new SimplePandemicData(i));
        }
        domestic.postValue(domesticData);
    }

    public LiveData<HashMap<String, PandemicData>> getInternational() {
        if (international == null) {
            international = new MutableLiveData<>();
            internationalData = new HashMap<>();
            loadInternational();
        }
        return international;
    }

    public void putInternational(String s) {
        internationalData.put(s, null);
    }

    public void putDomestic(String s) {
        domesticData.put(s, null);
    }

    public void loadInternational() {
        for (String i : internationalData.keySet()) {
            internationalData.put(i, new SimplePandemicData(i));
        }
        international.postValue(internationalData);
    }
}