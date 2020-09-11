package com.example.duyufeng.ui.people;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.duyufeng.Person;
import com.example.duyufeng.PersonInfo;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PeopleViewModel extends ViewModel {

    MutableLiveData<ArrayList<PersonInfo>> people = new MutableLiveData<>();
    int stage = 0;
    public LiveData<ArrayList<PersonInfo>> getPeople() {
        if (people.getValue() == null) { // stage 1
            stage = 0;
            ArrayList<PersonInfo> a = new ArrayList<>();
            people.setValue(a);
            Thread thread = new Thread(new Runnable() {
                Person person;
                @Override
                public void run() {
                    try {
                        person = new Person();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    stage = 1;
                    people.postValue(person.getPeople());
                }
            });
            thread.start();
        }
        if (stage == 1) {
            Thread thread = new Thread(new Runnable() {
                ArrayList<PersonInfo> a;

                @Override
                public void run() {
                    this.a = people.getValue();
                    int n = 5;
                    int j = 0;
                    for (PersonInfo i : a) {
                        String urldisplay = null;
                        try {
                            urldisplay = i.getAvatar();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Bitmap mIcon11 = null;
                        try {
                            InputStream in = new java.net.URL(urldisplay).openStream();
                            mIcon11 = BitmapFactory.decodeStream(in);
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage());
                            e.printStackTrace();
                        }
                        i.img = mIcon11;

                        ++j;
                        if (j % n == 0) {
                            people.postValue(a);
                        }

                    }
                    stage = 2;
                }
            });
            thread.start();
        }
        return people;
    }
    // TODO: Implement the ViewModel
}
