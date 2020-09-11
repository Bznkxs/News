package com.example.duyufeng.ui.trend;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duyufeng.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TrendFragment extends Fragment {

    private TrendViewModel trendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        trendViewModel =
                new ViewModelProvider(getActivity()).get(TrendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_trend, container, false);

        if (WordMap.map.isEmpty()) {
            int[] resources = new int[]{R.raw.r0, R.raw.r1, R.raw.r2, R.raw.r3, R.raw.r4};

            for (int i : resources) {
                // 读入聚类信息
                InputStream inputStream = getResources().openRawResource(i);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String eachline = null;
                try {
                    eachline = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String words = eachline.replace(' ', ','); // 第一行：关键词
                try {
                    eachline = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (eachline != null) {
                    try {
                        eachline = bufferedReader.readLine();
                        // 每行一个新闻
                        {
                            WordMap.getWords(words).news.add(WordMap.getTexts(eachline));
                            WordMap.getTexts(eachline).words.add(WordMap.getWords(words));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Words [] w = WordMap.getAllWords();
        RecyclerView view = root.findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        view.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
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
                holder1.view.setText(w[position].w);
                holder1.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), Trend.class);

                        myApplication list = (myApplication) getActivity().getApplication();
                        list.words = w[position];
                        startActivity(intent);

                    }
                });
            }

            @Override
            public int getItemCount() {
                return w.length;
            }


        });


        return root;
    }
    /*
    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView view;
            MyViewHolder(View itemView) {
                super(itemView);
                view = itemView.findViewById(R.id.txtItem);
            }
        }

        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trend_item, parent,false);
            return new MyViewHolder()

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

     */

}
