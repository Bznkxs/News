package com.example.duyufeng.ui.main;

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
import com.example.duyufeng.*;
import com.example.duyufeng.ui.home_news.HomeViewModel;

import java.util.LinkedList;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsTabFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private boolean calledOnCreate = false;
    private NewsViewModel pageViewModel;

    public static NewsTabFragment newInstance() {
        NewsTabFragment fragment = new NewsTabFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, 0);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new NewsViewModel(ViewModelProviders.of(this).get(HomeViewModel.class));

        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_single_tab_newsitem, container, false);
        pageViewModel.getNewsItems().observe(getViewLifecycleOwner(), new Observer<LinkedList<NewsItem>>() {
            @Override
            public void onChanged(@Nullable LinkedList<NewsItem> s) {
                RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                recyclerView.setHasFixedSize(true);

                // use a linear layout manager
                LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
                recyclerView.setLayoutManager(layoutManager);


                NewsAdapter adapter = new NewsAdapter(pageViewModel.getNewsItems().getValue());
                recyclerView.setAdapter(adapter);
            }
        });
        return root;

    }
}