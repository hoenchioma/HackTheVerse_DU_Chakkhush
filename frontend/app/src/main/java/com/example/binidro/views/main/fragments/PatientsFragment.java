package com.example.binidro.views.main.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binidro.R;
import com.example.binidro.models.Patient;
import com.example.binidro.models.Sensor;
import com.example.binidro.views.main.adapters.PatientsAdapter;
import com.example.binidro.views.main.adapters.WardsAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class PatientsFragment extends Fragment implements PatientsAdapter.OnPatientClickListener, View.OnClickListener{

    private PatientsAdapter patientsAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView fragmentTitle;
    private Button menuButton;
    private NavigationView navigationView;
    private TextView profileNameTextView;
    private TextView profileEmailTextView;

    private String wordId;
    private ArrayList<Sensor> sensors;
    private ArrayList<Patient> patients;

    public PatientsFragment(String wordId) {
        // Required empty public constructor

        this.wordId = wordId;

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

//        showToast(wordId);
        findXmlElements(view);
        updateNavigationView();
        setUpRecyclerView();
        return view;
    }

    private void findXmlElements(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewPatients);

        // Parent Layout
        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawerLayoutMain);

        // Toolbar
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbarMain);
        fragmentTitle = (TextView) getActivity().findViewById(R.id.fragmentTitleToolbarMain);
        menuButton = (Button) getActivity().findViewById(R.id.menuButtonToolbarMain);

        // Navigation Drawer
        navigationView = (NavigationView) getActivity().findViewById(R.id.navigationViewMain);
        profileNameTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nameTextViewDrawerHeader);
        profileEmailTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.emailTextViewDrawerHeader);

        fragmentTitle.setText("Patients");
    }

    private void setUpRecyclerView() {
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 0));
        patientsAdapter = new PatientsAdapter(patients, getContext(), this);
        recyclerView.setAdapter(patientsAdapter);
    }

    private void openSensors(String wardId, String patientId, String patientName, String patientDisease) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragmentContainerMain, new SensorsFragment(wardId, patientId, patientName, patientDisease), "sensors").addToBackStack("patients").commit();
    }

    private void updateNavigationView(){
        navigationView.getMenu().findItem(R.id.wardsDrawerMenu).setChecked(false);
        navigationView.getMenu().findItem(R.id.aboutUsDrawerMenu).setChecked(false);
        navigationView.getMenu().findItem(R.id.signOutDrawerMenu).setChecked(false);
    }

    public void showToast(String message){
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onPatientClick(int position) {
        String patientId = patients.get(position).getId();
        String patientName = patients.get(position).getName();
        String patientDisease = patients.get(position).getDisease();

        openSensors(this.wordId, patientId, patientName, patientDisease);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {

    }
}