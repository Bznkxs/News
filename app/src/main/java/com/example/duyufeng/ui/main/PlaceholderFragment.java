package com.example.duyufeng.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duyufeng.*;

import java.util.LinkedList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
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
        View root = inflater.inflate(R.layout.fragment_single_tab_newsitem, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);




        LinkedList<NewsItem> newsItemData = new LinkedList<>();
        String[] nameArr = getResources().getStringArray(R.array.names);
        String[] authArr = getResources().getStringArray(R.array.authors);
        String[] timeArr = getResources().getStringArray(R.array.dates);
        String[] abstArr = getResources().getStringArray(R.array.abstracts);
        String[] contArr = getResources().getStringArray(R.array.contents);
        int n = 40;
        for (int i = 0; i < n; ++i)
            newsItemData.add(new SimpleNewsItem(new SimpleNews(
                    nameArr[i % nameArr.length],
                    authArr[i % authArr.length],
                    timeArr[i % timeArr.length],
                    abstArr[i % abstArr.length],
                    contArr[i % contArr.length]
            )));

        NewsAdapter adapter = new NewsAdapter(newsItemData);
        recyclerView.setAdapter(adapter);
        //final TextView textView = root.findViewById(R.id.section_label);
        //pageViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}