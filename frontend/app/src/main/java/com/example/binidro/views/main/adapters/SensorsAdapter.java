package com.example.binidro.views.main.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.binidro.R;
import com.example.binidro.models.Sensor;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Random r = new Random();
        double randomValue = 0 + (100 - 0) * r.nextDouble();
        String measurement = Double.toString(randomValue);
        String unit = null;

        if(name.equals("BP")) {
            unit = "mmHg";
        } else if(name.equals("SPO2")) {
            unit = "%";
        } else if(name.equals("Pulse")) {
            unit = "bps";
        }

        holder.sensorName.setText(name);
        holder.sensorMeasurement.setText(measurement);

        LineDataSet lineDataSet = new LineDataSet(lineChartDataSet(unit), unit);
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        LineData lineData = new LineData(iLineDataSets);
        holder.sensorLineChart.setData(lineData);
        holder.sensorLineChart.invalidate();

        holder.sensorLineChart.setBackgroundColor(Color.WHITE);
        holder.sensorLineChart.setAutoScaleMinMaxEnabled(true);
        holder.sensorLineChart.getDescription().setEnabled(false);

        Legend legend = holder.sensorLineChart.getLegend();
        legend.setEnabled(true);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextColor(Color.parseColor("#25314B"));
//        holder.sensorLineChart.setNoDataText("Data not Available");

        lineDataSet.setDrawIcons(false);
        lineDataSet.enableDashedLine(10f, 5f, 0f);
        lineDataSet.enableDashedHighlightLine(10f, 5f, 0f);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(9f);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        lineDataSet.setFormSize(15.f);
        lineDataSet.setColor(Color.parseColor("#25314B"));
        lineDataSet.setCircleColor(Color.parseColor("#25314B"));
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setLineWidth(1);
        lineDataSet.setCircleRadius(2);
        lineDataSet.setCircleHoleRadius(2);
        lineDataSet.setValueTextSize(8);
        lineDataSet.setValueTextColor(Color.parseColor("#25314B"));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return sensors == null ? 0 : sensors.size();
    }

    public interface OnSensorClickListener{
        void onSensorClick(int position);
    }

    private ArrayList<Entry> lineChartDataSet(String unit){
        ArrayList<Entry> dataSet = new ArrayList<Entry>();

        if(unit.equals("mmHg")) {
            dataSet.add(new Entry(80,120));
            dataSet.add(new Entry(1,10));
            dataSet.add(new Entry(75,120));
            dataSet.add(new Entry(80,125));
            dataSet.add(new Entry(70,110));
            dataSet.add(new Entry(80,120));
            dataSet.add(new Entry(82,120));
            dataSet.add(new Entry(83,119));
            dataSet.add(new Entry(85,115));
        } else if(unit.equals("%")) {
            dataSet.add(new Entry(2,96));
            dataSet.add(new Entry(3, 96));
            dataSet.add(new Entry(4, 96));
            dataSet.add(new Entry(5, 96));
            dataSet.add(new Entry(6, 95));
            dataSet.add(new Entry(7, 97));
            dataSet.add(new Entry(8, 97));
            dataSet.add(new Entry(9, 97));
            dataSet.add(new Entry(10, 96));
        } else if(unit.equals("bps")) {
            dataSet.add(new Entry(2, 70));
            dataSet.add(new Entry(3, 72));
            dataSet.add(new Entry(4, 75));
            dataSet.add(new Entry(5, 78));
            dataSet.add(new Entry(6, 75));
            dataSet.add(new Entry(7, 74));
            dataSet.add(new Entry(8, 73));
            dataSet.add(new Entry(9, 73));
            dataSet.add(new Entry(10, 72));
        }

        return  dataSet;
    }
}