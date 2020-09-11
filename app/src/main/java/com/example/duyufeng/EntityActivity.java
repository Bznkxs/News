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

public class EntityActivity extends AppCompatActivity {

    TextView titleView, affixView, descView;
    ImageView imageView;
    ExpandableListView listView;
    GraphViewModel graphViewModel;
    ArrayList<ArrayList<NodeShowPair>> items;
    ArrayList<String> groups;

    void getData(NodeInfo node, Graph graph) {
        titleView = findViewById(R.id.Title);
        affixView = findViewById(R.id.affix);
        descView = findViewById(R.id.Content);
        imageView = findViewById(R.id.imageView);
        listView = findViewById(R.id.expandable_list);

        titleView.setText(node.getLabel());
        affixView.setText("实体");
        descView.setText(node.getDescription());

//        if (node.getImg() != null && node.getImg().length() > 3) {
//            Toast.makeText(this, "Get Image from: " + node.getImg(), Toast.LENGTH_SHORT).show();
//        }
        // show The Image in a ImageView
        new DownloadImageTask(imageView)
                .execute(node.getImg());

        groups = new ArrayList<>();
        items = new ArrayList<>();

        groups.add("属性");
        groups.add("\"" + node.getLabel() + "\"指向的实体");
        groups.add("指向\"" + node.getLabel() + "\"的实体");

        ArrayList<NodeShowPair> itemAttr = new ArrayList<>();
        Map<String, String> map = node.getProperties();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String showVal = entry.getKey() + " : " + entry.getValue();
            String id = entry.getValue();
            itemAttr.add(new NodeShowPair(showVal, id));
        }
        items.add(itemAttr);
        ArrayList<NodeShowPair> itemToOthers = new ArrayList<>();
        items.add(itemToOthers);
        ArrayList<NodeShowPair> itemFromOthers = new ArrayList<>();
        items.add(itemFromOthers);

        if (graph != null) {

            for (NodeInfo i : node.thisToOthers()) {
                String showVal = graph.getRelation(node.getLabel(), i.getLabel()) + " : " + i.getLabel();
                String id = i.getLabel();
                itemToOthers.add(new NodeShowPair(showVal, id));
            }

            for (NodeInfo i : node.othersToThis()) {
                String showVal = i.getLabel() + " : " + graph.getRelation(i.getLabel(), node.getLabel());
                String id = i.getLabel();
                itemFromOthers.add(new NodeShowPair(showVal, id));
            }
            //Toast.makeText(EntityActivity.this, "changed", Toast.LENGTH_SHORT).show();

            findViewById(R.id.pb_loading2).setVisibility(View.GONE);
        }
        EntityExpandableListAdapter adapter = new EntityExpandableListAdapter(groups, items, EntityActivity.this);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        graphViewModel = new ViewModelProvider(this).get(GraphViewModel.class);

        Intent intent = getIntent();
        NodeInfo node = new NodeInfo(intent.getStringExtra(String.valueOf(R.string.search_word)));
        getData(node, null);

            graphViewModel.setGraph(node.label);
            graphViewModel.getGraph().observe(this, new Observer<Graph>() {
                @Override
                public void onChanged(Graph graph) {
                    graph = graphViewModel.getGraph().getValue();
                    if (graph != null)
                        getData(graph.root, graph);
                }
            });



            listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                    Intent intent1 = new Intent(view.getContext(), EntityActivity.class);
                    intent1.putExtra(String.valueOf(R.string.search_word), items.get(i).get(i1).strId);
                    view.getContext().startActivity(intent1);
                    return true;
                }
            });

            listView.setOnGroupExpandListener( new ExpandableListView.OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
                    setListViewHeight(listView);
                }

            });

            listView.setOnGroupCollapseListener( new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    setListViewHeight(listView);
                }

            });

            ViewTreeObserver vto = listView.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                @Override
                public void onGlobalLayout() {
                    setListViewHeight(listView);


                    ViewTreeObserver obs = listView.getViewTreeObserver();

                    obs.removeOnGlobalLayoutListener(this);
                }

            });


    }



    private void setListViewHeight(ExpandableListView listView){
        //得到相应ListView的适配器
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        //总高度
        int totalHeight = 50;
        //期望的宽度
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            //得到一级条目
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            //groupItem根据给的参数测量一下尺寸，方法调用完后groupItem大小就确定了
            groupItem.measure(desiredWidth, View.MeasureSpec.EXACTLY);
            //累加一级条目高度
            totalHeight += 173;
            //    除了group条目外都展开了  或者  group那一条目没有展开
            if (listView.isGroupExpanded(i) ) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    //得到二级条目
                    View listItem = listAdapter.getChildView(i, j, false, null, listView);
                    //listItem根据给的参数测量一下尺寸，方法调用完后listItem大小就确定了
                    listItem.measure(desiredWidth, View.MeasureSpec.EXACTLY);

                    //累加二级条目高度
                    totalHeight += 74 + 58 * ((((TextView)listItem.findViewById(R.id.tv_name)).getText().length() + 20) / 21);
                }
            }
        }
        //获得listView的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //listView的高度 = 一级条目和二级条目总和 + 分割线高度总和
        int height = totalHeight + (listView.getDividerHeight()*(listAdapter.getGroupCount() - 1));
        //如果高度小于10，则设置为200，没有太大关系
        if(height < 10){
            height = 200;
        }
        //把高度赋值给布局参数
        params.height = height;
        //Toast.makeText(this, String.valueOf(params.height), Toast.LENGTH_SHORT).show();
        //把布局参数回传给listView
        listView.setLayoutParams(params);
        //listView按照参数从新布局
        listView.requestLayout();
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
