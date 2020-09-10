package com.example.duyufeng.ui.data_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.duyufeng.*;
import com.example.duyufeng.ui.data.DataViewModel;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 */
public class DomesticFragment extends Fragment {

    private DataViewModel dataViewModel;
    DataAdapter adapter;
    public static DomesticFragment newInstance(int index) {
        return new DomesticFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataViewModel = new ViewModelProvider(this.getActivity()).get(DataViewModel.class);

        dataViewModel.getDomestic();

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

        ArrayList<StatInfo> e = dataViewModel.getDomestic().getValue();
        if (e != null) {
            adapter = new DataAdapter(e);

            View header = LayoutInflater.from(getContext()).inflate(R.layout.data_rowhead_actually_used,
                    recyclerView, false);
            adapter.setHeaderView(header);

            recyclerView.setAdapter(adapter);
        }

        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据

                try {
                    dataViewModel.refresh();
                } catch (IOException | JSONException ioException) {
                    ioException.printStackTrace();
                }

            }
        });
        swipeRefreshLayout.setRefreshing(true);
        dataViewModel.getDomestic().observe(getViewLifecycleOwner(), new Observer<ArrayList<StatInfo>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<StatInfo> s) {
                        ArrayList<StatInfo> e = dataViewModel.getDomestic().getValue();
                        if (e != null) {
                            adapter = new DataAdapter(e);

                            View header = LayoutInflater.from(getContext()).inflate(R.layout.data_rowhead_actually_used,
                                    recyclerView, false);
                            adapter.setHeaderView(header);

                            recyclerView.setAdapter(adapter);
                        }
                        if (s != null && !s.isEmpty())
                            swipeRefreshLayout.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (swipeRefreshLayout.isRefreshing()) {
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }
                            }, 100);
                    }

                }
        );
        return root;
    }
}