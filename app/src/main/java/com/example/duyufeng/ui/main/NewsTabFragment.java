package com.example.duyufeng.ui.main;

import android.content.Context;
import android.graphics.Color;
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
import com.example.duyufeng.ui.home_news.NewsViewModel;
import org.json.JSONException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 这个Fragment用来装载新闻列表
 */
public class NewsTabFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private boolean calledOnCreate = false;
    private NewsViewModel dataViewModel;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    NewsProvider provider;
    String typeFilter;


    public NewsTabFragment(int idx) {
        super();
        if (idx == 0)
            typeFilter = "news";
        else
            typeFilter = "paper";
    }

    public static NewsTabFragment newInstance(int idx) {
        NewsTabFragment fragment = new NewsTabFragment(idx);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataViewModel = new ViewModelProvider(getActivity()).get(NewsViewModel.class);

        try {
            provider = new NewsProvider(this.getActivity().getApplicationContext());
            provider.setRequestType(typeFilter);
            if (typeFilter.equals("news")) {
                dataViewModel.provider = provider;
                ((myApplication) getActivity().getApplication()).provider = provider;
            }
            else {
                dataViewModel.providerPaper = provider;
                ((myApplication) getActivity().getApplication()).providerPaper = provider;
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_single_tab_newsitem, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);


        adapter = new NewsAdapter(dataViewModel.getNewsItems(typeFilter).getValue(), typeFilter);
        recyclerView.setAdapter(adapter);

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_refresh_layout);

        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));


        // 设置下拉刷新 TODO: 代码解释：与DataViewModel结合获取数据，具体看代码。如果接口不变，应该不需要动。
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据。这里调用的是最新的数据
                dataViewModel.loadLatestNewsItems(typeFilter);
            }
        });

        swipeRefreshLayout.setRefreshing(true);

        // 上拉加载
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                adapter.setLoadState(adapter.LOADING);

                if (dataViewModel.getNewsItems(typeFilter).getValue().size() < 44444) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // 这里是更多数据
                                    dataViewModel.loadMoreNewsItems(typeFilter);
                                    adapter.setLoadState(adapter.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 1000);
                } else {
                    // 显示加载到底的提示
                    adapter.setLoadState(adapter.LOADING_END);
                }
            }
        });

        dataViewModel.getNewsItems(typeFilter).observe(getViewLifecycleOwner(), new Observer<LinkedList<News>>() {
            @Override
            public void onChanged(LinkedList<News> news) {
                adapter.notifyDataSetChanged();
                adapter.refresh();

                myApplication list = (myApplication)(Objects.requireNonNull(getActivity()).getApplication());
                list.list = dataViewModel.getNewsItems(typeFilter).getValue();

                if (!dataViewModel.isFirstrun(typeFilter))
                    // 延时1s关闭下拉刷新
                    swipeRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }, 100);
            }
        });


        myApplication list = (myApplication)(Objects.requireNonNull(getActivity()).getApplication());
        list.list = dataViewModel.getNewsItems(typeFilter).getValue();
        return root;

    }
}