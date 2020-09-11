package com.example.duyufeng;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duyufeng.ui.trend.Texts;
import com.example.duyufeng.ui.trend.Words;

public class Trend extends AppCompatActivity {

    Words w;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        myApplication list = (myApplication) getApplication();
        w = list.words;
        myToolbar.setTitle(w.w);
        Texts [] texts = w.getNews();
        RecyclerView view = findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        view.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        view.setLayoutManager(layoutManager);

        view.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView view;
                MyViewHolder(View itemView) {
                    super(itemView);
                    view = itemView.findViewById(R.id.txtItem);
                }
            }
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trend_item, parent,false);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                MyViewHolder holder1 = (MyViewHolder)holder;
                String txt = texts[position].t;
                if (txt.length() > 20) txt = txt.substring(0, 19) + "...";
                holder1.view.setText(txt);
                holder1.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Trend.this, Trend2.class);

                        myApplication list = (myApplication) getApplication();
                        list.texts = texts[position];
                        startActivity(intent);

                    }
                });
            }

            @Override
            public int getItemCount() {
                return texts.length;
            }


        });
    }
}
