package com.example.duyufeng.ui.people;

import android.content.Intent;
import android.widget.Button;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PeopleFragment extends Fragment {

    private PeopleViewModel mViewModel;

    public static PeopleFragment newInstance() {
        return new PeopleFragment();
    }
    RecyclerView recyclerView;
    PersonAdapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_people, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        mViewModel = new ViewModelProvider(getActivity()).get(PeopleViewModel.class);

        adapter = new PersonAdapter(mViewModel.getPeople().getValue());
        recyclerView.setAdapter(adapter);

        mViewModel.getPeople().observe(getViewLifecycleOwner(), new Observer<List<PersonInfo>>() {
            @Override
            public void onChanged(List<PersonInfo> value) {
                adapter.refresh(mViewModel.getPeople().getValue());

            }
        });



        return root;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}
