package com.example.duyufeng;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
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

public class EntityActivity extends AppCompatActivity {

    TextView titleView, affixView, descView;
    ImageView imageView;
    ExpandableListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        try {
            Graph graph = new Graph(intent.getStringExtra(String.valueOf(R.string.search_word)));
            NodeInfo node = graph.getRoot();
            titleView = findViewById(R.id.Title);
            affixView = findViewById(R.id.affix);
            descView = findViewById(R.id.Content);
            imageView = findViewById(R.id.imageView);
            listView = findViewById(R.id.expandable_list);

            titleView.setText(node.getLabel());
            affixView.setText("实体");
            descView.setText(node.getDescription());

            // show The Image in a ImageView
            new DownloadImageTask(imageView)
                    .execute(node.getImg());

            ArrayList<String> groups = new ArrayList<>();
            ArrayList<ArrayList<NodeShowPair>> items = new ArrayList<>();


            groups.add("属性");
            groups.add(node.getLabel() + "指向的实体");
            groups.add("指向" + node.getLabel() + "的实体");

            ArrayList<NodeShowPair> itemAttr = new ArrayList<>();
            Map<String, String> map = node.getProperties();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String showVal = entry.getKey() + " : " + entry.getValue();
                String id = entry.getValue();
                itemAttr.add(new NodeShowPair(showVal, id));
            }
            items.add(itemAttr);

            ArrayList<NodeShowPair> itemToOthers = new ArrayList<>();

            for (NodeInfo i : node.thisToOthers()) {
                String showVal = graph.getRelation(node.getLabel(), i.getLabel());
                String id = i.getLabel();
                itemToOthers.add(new NodeShowPair(showVal, id));
            }
            items.add(itemToOthers);

            ArrayList<NodeShowPair> itemFromOthers = new ArrayList<>();

            for (NodeInfo i : node.othersToThis()) {
                String showVal = graph.getRelation(i.getLabel(), node.getLabel());
                String id = i.getLabel();
                itemFromOthers.add(new NodeShowPair(showVal, id));
            }
            items.add(itemFromOthers);

            EntityExpandableListAdapter adapter = new EntityExpandableListAdapter(groups, items, this);
            listView.setAdapter(adapter);

            listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                    Intent intent1 = new Intent(view.getContext(), EntityActivity.class);
                    intent1.putExtra(String.valueOf(R.string.search_word), items.get(i).get(i1).strId);
                    view.getContext().startActivity(intent1);
                    return true;
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
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
