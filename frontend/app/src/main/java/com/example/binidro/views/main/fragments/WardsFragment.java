package com.example.binidro.views.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.binidro.R;
import com.example.binidro.models.HealthWorker;
import com.example.binidro.models.Patient;
import com.example.binidro.models.Sensor;
import com.example.binidro.models.Ward;

import java.util.ArrayList;
import java.util.List;

public class WardsFragment extends Fragment implements WardsAdapter.OnWordClickListener, View.OnClickListener{
    private EditText searchEditText;
    private ImageButton sortImageButton;

    private WardsAdapter wardsAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    private ArrayList sensors;
    private ArrayList patients;
    private ArrayList healthWorkers;
    private ArrayList wards;


    public WardsFragment() {
        // Required empty public constructor
        sensors = new ArrayList<Sensor>();
        sensors.add(new Sensor("1", "hello"));
        sensors.add(new Sensor("2", "hell2"));

        patients = new ArrayList<Patient>();
        patients.add(new Patient("1", "hello", "hello", "hello", sensors));
        patients.add(new Patient("2", "hello2", "hello2", "hello2", sensors));

        healthWorkers = new ArrayList<HealthWorker>();
        healthWorkers.add(new HealthWorker("1", "hello", "hello"));
        healthWorkers.add(new HealthWorker("2", "hello2", "hello2"));

        wards = new ArrayList<Ward>();
        wards.add(new Ward("1", patients, healthWorkers));
        wards.add(new Ward("1", patients, healthWorkers));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wards, container, false);

        findXmlElements(view);
        setUpRecyclerView();
        return view;
    }

    private void findXmlElements(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewWards);
    }

    private void setUpRecyclerView() {
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 0));
        wardsAdapter = new WardsAdapter(wards, getContext(), this);
        recyclerView.setAdapter(wardsAdapter);
    }

    @Override
    public void onWardClick(int position) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View view) {

    }
}