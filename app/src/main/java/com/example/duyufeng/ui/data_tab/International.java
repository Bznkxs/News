package com.example.duyufeng.ui.data_tab;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duyufeng.*;
import com.example.duyufeng.ui.data.DataViewModel;
import com.github.mikephil.charting.charts.BarChart;

import java.util.HashMap;
import java.util.LinkedList;

public class International extends Fragment {

    private DataViewModel dataViewModel;

    public static International newInstance() {
        return new International();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        dataViewModel.getInternational();

        String[] s =  getResources().getStringArray(R.array.selected_countries);
        for (String s1 : s) {
            dataViewModel.putInternational(s1);
        }
        dataViewModel.loadInternational();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_international, container, false);

        dataViewModel.getInternational().observe(getViewLifecycleOwner(), new Observer<HashMap<String, PandemicData>>() {
            @Override
            public void onChanged(@Nullable HashMap<String, PandemicData> s) {
                RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                recyclerView.setHasFixedSize(true);


                // use a linear layout manager
                LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
                recyclerView.setLayoutManager(layoutManager);


                DataAdapter adapter = new DataAdapter(new LinkedList<>
                        (dataViewModel.getInternational().getValue().values()));

                View header = LayoutInflater.from(getContext()).inflate(R.layout.data_rowhead,
                        recyclerView, false);
                adapter.setHeaderView(header);

                recyclerView.setAdapter(adapter);
            }

        }
        );
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}
