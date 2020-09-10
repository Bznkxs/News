package com.example.duyufeng.ui.data_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.duyufeng.*;
import com.example.duyufeng.ui.data.DataViewModel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 */
public class DomesticFragment extends Fragment {

    private DataViewModel dataViewModel;

    public static DomesticFragment newInstance(int index) {
        return new DomesticFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        dataViewModel.getDomestic();

        String[] s2 =  getResources().getStringArray(R.array.provinces);
        for (String s1 : s2) {
            dataViewModel.putDomestic(s1);
        }
        dataViewModel.loadDomestic();
        //new Thread(() -> dataViewModel.loadDomestic()).start();

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_domestic, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);


        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        HashMap<String, PandemicData> e = dataViewModel.getDomestic().getValue();
        if (e != null) {
            DataAdapter adapter = new DataAdapter(new LinkedList<>
                    (e.values()));

            View header = LayoutInflater.from(getContext()).inflate(R.layout.data_rowhead,
                    recyclerView, false);
            adapter.setHeaderView(header);

            recyclerView.setAdapter(adapter);
        }

        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据

                dataViewModel.loadDomestic();
                // new Thread(() -> dataViewModel.loadDomestic()).start();
                // 延时0.1s关闭下拉刷新
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 100);

            }
        });

        dataViewModel.getDomestic().observe(getViewLifecycleOwner(), new Observer<HashMap<String, PandemicData>>() {
                    @Override
                    public void onChanged(@Nullable HashMap<String, PandemicData> s) {
                        DataAdapter adapter = new DataAdapter(new LinkedList<>
                                (dataViewModel.getDomestic().getValue().values()));

                        View header = LayoutInflater.from(getContext()).inflate(R.layout.data_rowhead,
                                recyclerView, false);
                        adapter.setHeaderView(header);

                        recyclerView.setAdapter(adapter);
                    }

                }
        );
        return root;
    }
}