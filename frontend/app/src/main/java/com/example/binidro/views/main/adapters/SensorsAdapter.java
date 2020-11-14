package com.example.binidro.views.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.binidro.R;
import com.example.binidro.models.Sensor;
import com.github.mikephil.charting.charts.LineChart;

import java.util.List;

public class SensorsAdapter extends RecyclerView.Adapter<SensorsAdapter.SensorItemViewHolder> {
    private Context context;
    private List<Sensor> sensors;
    private OnSensorClickListener mOnSensorClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class SensorItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView sensorName;
        public TextView sensorMeasurement;
        public LineChart sensorLineChart;
        OnSensorClickListener onSensorClickListener;

        public SensorItemViewHolder(View view, OnSensorClickListener onSensorClickListener) {
            super(view);
            this.onSensorClickListener = onSensorClickListener;
            sensorName = view.findViewById(R.id.nameTextViewItemSensors);
            sensorMeasurement = view.findViewById(R.id.measurementTextViewItemSensors);
            sensorLineChart = view.findViewById(R.id.lineChartItemSensors);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSensorClickListener.onSensorClick(getAdapterPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SensorsAdapter(List<Sensor> sensors, Context context, OnSensorClickListener onSensorClickListener) {
        mOnSensorClickListener = onSensorClickListener;
        this.sensors = sensors;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SensorsAdapter.SensorItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sensors, parent, false);
        SensorItemViewHolder viewHolder = new SensorItemViewHolder(view, mOnSensorClickListener);
        return viewHolder;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SensorItemViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String name = sensors.get(position).getName();
//        String measurement = sensors.get(position).getValues().get(0);
        String measurement = "100%";

        holder.sensorName.setText(name);
        holder.sensorMeasurement.setText(measurement);
//        holder.sensorLineChart.setText(measurement);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return sensors == null ? 0 : sensors.size();
    }

    public interface OnSensorClickListener{
        void onSensorClick(int position);
    }
}