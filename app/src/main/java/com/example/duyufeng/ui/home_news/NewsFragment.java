package com.example.duyufeng.ui.home_news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import com.example.duyufeng.R;
import com.example.duyufeng.myApplication;
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
        homeViewModel = new ViewModelProvider(getActivity()).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sectionsPagerAdapter = new SectionsPagerAdapter(view.getContext(), getChildFragmentManager());
        //sectionsPagerAdapter = new SectionsPagerAdapter(view.getContext(), getFragmentManager());
        sectionsPagerAdapter.setConfiguration(((myApplication) getActivity().getApplication()).tabConfig);
        myApplication list = (myApplication) getActivity().getApplication();
        list.adapter = sectionsPagerAdapter;
        sectionsPagerAdapter.view = view;
        ViewPager viewPager = view.findViewById(R.id.view_pager_news);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(this.getContext(), "RESUME", Toast.LENGTH_SHORT).show();
        sectionsPagerAdapter.setConfiguration(((myApplication) getActivity().getApplication()).tabConfig);
        sectionsPagerAdapter.notifyDataSetChanged();

    }
}
