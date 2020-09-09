package com.example.duyufeng.ui.data_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.duyufeng.*;
import com.example.duyufeng.ui.data.DataViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class DomesticFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private boolean calledOnCreate = false;
    private DataViewModel dataViewModel;

    public static DomesticFragment newInstance(int index) {
        DomesticFragment fragment = new DomesticFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
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
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_domestic, container, false);

        return root;
        //final TextView textView = root.findViewById(R.id.section_label);
        //pageViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

    }
}