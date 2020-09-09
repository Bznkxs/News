package com.example.duyufeng.ui.data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import com.example.duyufeng.R;
import com.example.duyufeng.ui.data_tab.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class DataFragment extends Fragment {

    private DataViewModel dataViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dataViewModel =
                ViewModelProviders.of(this).get(DataViewModel.class);
        View root = inflater.inflate(R.layout.fragment_data, container, false);

        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(view.getContext(), getFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        for (int i = 0; i < sectionsPagerAdapter.getCount(); ++i)
            sectionsPagerAdapter.getItem(i);

    }
}