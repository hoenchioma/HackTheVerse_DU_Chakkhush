package com.example.binidro.views.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.binidro.R;
import com.example.binidro.models.HealthWorker;
import com.example.binidro.models.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.PatientItemViewHolder> {
    private Context context;
    private List<Patient> patients;
    private OnPatientClickListener mOnPatientClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class PatientItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView patientName;
        public TextView patientDisease;
        OnPatientClickListener onPatientClickListener;

        public PatientItemViewHolder(View view, OnPatientClickListener onPatientClickListener) {
            super(view);
            this.onPatientClickListener = onPatientClickListener;
            patientName = view.findViewById(R.id.nameTextViewItemPatients);
            patientDisease = view.findViewById(R.id.diseaseTextViewItemPatients);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPatientClickListener.onPatientClick(getAdapterPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PatientsAdapter(List<Patient> patients, Context context, OnPatientClickListener onPatientClickListener) {
        mOnPatientClickListener = onPatientClickListener;
        this.patients = patients;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PatientsAdapter.PatientItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_patients, parent, false);
        PatientItemViewHolder viewHolder = new PatientItemViewHolder(view, mOnPatientClickListener);
        return viewHolder;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PatientItemViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String id = patients.get(position).getId();
        String name = patients.get(position).getName();
        String disease = patients.get(position).getDisease();

        holder.patientName.setText(name);
        holder.patientDisease.setText(disease);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return patients == null ? 0 : patients.size();
    }

    public interface OnPatientClickListener {
        void onPatientClick(int position);
    }
}