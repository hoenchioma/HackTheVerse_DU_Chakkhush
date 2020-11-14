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
import android.widget.Toast;

import com.example.binidro.R;
import com.example.binidro.models.Patient;
import com.example.binidro.models.Sensor;
import com.example.binidro.views.main.adapters.PatientsAdapter;
import com.example.binidro.views.main.adapters.WardsAdapter;

import java.util.ArrayList;

public class PatientsFragment extends Fragment implements PatientsAdapter.OnPatientClickListener, View.OnClickListener{

    private PatientsAdapter patientsAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    private ArrayList<Sensor> sensors;
    private ArrayList<Patient> patients;

    public PatientsFragment() {
        // Required empty public constructor
        // TODO - Fetch Data
        sensors = new ArrayList<Sensor>();
        sensors.add(new Sensor("1", "hello"));
        sensors.add(new Sensor("2", "hell2"));

        patients = new ArrayList<Patient>();
        patients.add(new Patient("1", "hello", "hello", "hello", sensors));
        patients.add(new Patient("2", "hello2", "hello2", "hello2", sensors));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patients, container, false);

        findXmlElements(view);
        setUpRecyclerView();
        return view;
    }

    private void findXmlElements(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewPatients);
    }

    private void setUpRecyclerView() {
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 0));
        patientsAdapter = new PatientsAdapter(patients, getContext(), this);
        recyclerView.setAdapter(patientsAdapter);
    }

    public void showToast(String message){
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onPatientClick(int position) {
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