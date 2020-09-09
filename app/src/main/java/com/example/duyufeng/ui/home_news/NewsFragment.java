package com.example.duyufeng.ui.home_news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import com.example.duyufeng.R;
import com.example.duyufeng.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

// 这个Fragment是用来装载Tab和其对应的viewPagers的
// TODO: 有一个bug，tab的显示会出问题
// 比如按两次底部的新闻，新闻列表就会消失
// 在一定的情况下，甚至会出现“数据”中的列表
public class NewsFragment extends Fragment {

    private NewsViewModel homeViewModel;
    SectionsPagerAdapter sectionsPagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sectionsPagerAdapter = new SectionsPagerAdapter(view.getContext(), getFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        for (int i = 0; i < sectionsPagerAdapter.getCount(); ++i)
            sectionsPagerAdapter.getItem(i);

    }
}
