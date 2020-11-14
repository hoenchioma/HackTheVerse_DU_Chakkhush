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
import com.example.binidro.models.Ward;

import java.util.ArrayList;
import java.util.List;

public class WardsAdapter extends RecyclerView.Adapter<WardsAdapter.WardItemViewHolder> {
    private Context context;
    private List<Ward> wards;
    private OnWardClickListener mOnWardClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class WardItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView patientCountTextView;
        public TextView healthWorkersCountTextView;
        OnWardClickListener onWardClickListener;

        public WardItemViewHolder(View view, OnWardClickListener onWardClickListener) {
            super(view);
            this.onWardClickListener = onWardClickListener;
            patientCountTextView = view.findViewById(R.id.patientCountTextViewItemWards);
            healthWorkersCountTextView = view.findViewById(R.id.healthWorkersCountTextViewItemWards);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onWardClickListener.onWardClick(getAdapterPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public WardsAdapter(List<Ward> wards, Context context, OnWardClickListener onWardClickListener) {
        mOnWardClickListener = onWardClickListener;
        this.wards = wards;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WardsAdapter.WardItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wards, parent, false);
        WardItemViewHolder viewHolder = new WardItemViewHolder(view, mOnWardClickListener);
        return viewHolder;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(WardItemViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String id = wards.get(position).getId();
        ArrayList<Patient> patients = new ArrayList<Patient>(wards.get(position).getPatients());
        ArrayList<HealthWorker> healthWorkers = new ArrayList<HealthWorker>(wards.get(position).getHealthWorkers());

        holder.patientCountTextView.setText("Patients :" + Integer.toString(patients.size()));
        holder.healthWorkersCountTextView.setText("Health Workers :"  + Integer.toString(healthWorkers.size()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return wards == null ? 0 : wards.size();
    }

    public interface OnWardClickListener{
        void onWardClick(int position);
    }
}