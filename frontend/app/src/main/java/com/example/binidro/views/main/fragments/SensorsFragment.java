package com.example.binidro.views.main.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binidro.R;
import com.example.binidro.models.Patient;
import com.example.binidro.models.Sensor;
import com.example.binidro.views.main.adapters.PatientsAdapter;
import com.example.binidro.views.main.adapters.SensorsAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Objects;

public class SensorsFragment extends Fragment implements SensorsAdapter.OnSensorClickListener, View.OnClickListener{

    private SensorsAdapter sensorsAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private TextView fragmentTitle;
    private TextView patientNameTextView;
    private TextView patientDiseaseTextView;

    private String wordId;
    private String patientId;
    private String patientName;
    private String patientDisease;
    private ArrayList<Sensor> sensors;

    public SensorsFragment(String wordId, String patientId, String patientName, String patientDisease) {
        // Required empty public constructor
        this.wordId = wordId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientDisease = patientDisease;

        // TODO - Fetch Data
        sensors = new ArrayList<Sensor>();
        sensors.add(new Sensor("1", "SPO2"));
        sensors.add(new Sensor("2", "BP"));
        sensors.add(new Sensor("3", "Pulse"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sensors, container, false);

        showToast(patientDisease);
        findXmlElements(view);
        setUpRecyclerView();
        return view;
    }

    private void findXmlElements(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewSensors);
        patientNameTextView = view.findViewById(R.id.patientNameSensors);
        patientDiseaseTextView = view.findViewById(R.id.patientDiseaseSensors);
        fragmentTitle = Objects.requireNonNull(getActivity()).findViewById(R.id.fragmentTitleToolbarMain);

        patientNameTextView.setText(patientName);
        patientDiseaseTextView.setText(patientDisease);
        fragmentTitle.setText("Sensors");
    }

    private void setUpRecyclerView() {
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 0));
        sensorsAdapter = new SensorsAdapter(sensors, getContext(), this);
        recyclerView.setAdapter(sensorsAdapter);
    }

    public void showToast(String message){
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onSensorClick(int position) {
//        Intent intent = new Intent(getContext(), BookDetailsActivity.class);
//        intent.putExtra("selectedBook", CONSTANTS.bookListCached.get(position));
//        startActivityForResult(intent, CONSTANTS.getIdBooklistadminBookdetails());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {

    }
}