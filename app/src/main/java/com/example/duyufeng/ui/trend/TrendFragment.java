package com.example.duyufeng.ui.trend;

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
import com.example.duyufeng.R;

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

        int [] resources = new int[] { R.raw.r0, R.raw.r1, R.raw.r2, R.raw.r3, R.raw.r4 };

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
            //words = eachline.split(" "); // 第一行：关键词
            while (eachline != null) {
                String[] words = eachline.split(" ");
                try {
                    eachline = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        getResources().getString(R.raw.r0);

        return root;
    }
}
