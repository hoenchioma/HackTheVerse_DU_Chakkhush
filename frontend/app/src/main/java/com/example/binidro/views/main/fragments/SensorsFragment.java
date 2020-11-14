package com.example.binidro.views.main.fragments;

import android.content.Intent;
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

import java.util.ArrayList;

public class SensorsFragment extends Fragment implements SensorsAdapter.OnSensorClickListener, View.OnClickListener{

    private SensorsAdapter sensorsAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private TextView fragmentTitle;

    private String wordId;
    private String patientId;
    private ArrayList<Sensor> sensors;

    public SensorsFragment(String wordId, String patientId) {
        // Required empty public constructor
        this.wordId = wordId;
        this.patientId = patientId;

        // TODO - Fetch Data
        sensors = new ArrayList<Sensor>();
        sensors.add(new Sensor("1", "hello"));
        sensors.add(new Sensor("2", "hell2"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sensors, container, false);

        findXmlElements(view);
        setUpRecyclerView();
        return view;
    }


    private void findXmlElements(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewSensors);
        fragmentTitle = getActivity().findViewById(R.id.fragmentTitleToolbarMain);
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