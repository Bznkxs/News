package com.example.duyufeng;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.duyufeng.ui.graph.GraphViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

public class PersonActivity extends AppCompatActivity {

    TextView titleView, affixView, bio, edu, actv, cit, div,
            gidx, hidx, soc, pub;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        PersonInfo person = ((myApplication) getApplication()).personInfo;

        titleView = findViewById(R.id.txtName);
        affixView = findViewById(R.id.txtStatus);
        bio = findViewById(R.id.txtBio);
        edu = findViewById(R.id.txtEdu);
        actv = findViewById(R.id.txtActivity);
        cit = findViewById(R.id.txtCitations);
        div = findViewById(R.id.txtDiversity);
        gidx = findViewById(R.id.txtGIndex);
        hidx = findViewById(R.id.txtHIndex);
        soc = findViewById(R.id.txtSociability);
        pub = findViewById(R.id.txtPublications);

        imageView = findViewById(R.id.imageView3);


        try {
            titleView.setText(person.getNames());
        }catch (Exception ignored) {titleView.setText("Anonymous scholar");}
        String stt;
        try {
            stt = person.get_2("profile", "position") + "\n";
        } catch (Exception ignored) { stt = ""; }
        try {
            affixView.setText(stt + person.getStatus());
        }catch (Exception ignored) {affixView.setText(stt);}
        try {    bio.setText(person.get_2("profile","bio"));
        }catch (Exception ignored) {bio.setText("暂无");}
        try {    edu.setText(person.get_2("profile","edu"));
        }catch (Exception ignored) {edu.setText("暂无");}
        try {   actv.setText(getResources().getText(R.string.activity) +
                    person.get_2("indices","activity"));
        }catch (Exception ignored) {actv.setText("暂无");}
        try {    cit.setText(getResources().getText(R.string.cited) +
                    person.get_2("indices","citations"));
        }catch (Exception ignored) {cit.setText("暂无");}
        try {   div.setText(getResources().getText(R.string.diversity) +
                    person.get_2("indices","diversity"));
        }catch (Exception ignored) {div.setText("暂无");}
        try {    gidx.setText(getResources().getText(R.string.gindex) +
                    person.get_2("indices","gindex"));
        }catch (Exception ignored) {gidx.setText("暂无");}
        try {    hidx.setText(getResources().getText(R.string.hindex) +
                    person.get_2("indices","hindex"));
        }catch (Exception ignored) {hidx.setText("暂无");}
        try {    soc.setText(getResources().getText(R.string.sociability) +
                    person.get_2("indices","sociability"));
        }catch (Exception ignored) {soc.setText("暂无");}
        try {   pub.setText(getResources().getText(R.string.pubs) +
                    person.get_2("indices","pubs"));
        }catch (Exception ignored) {pub.setText("暂无");}
        try {    new DownloadImageTask(imageView)
                    .execute(person.getAvatar());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
