package com.example.mymoviememoir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import networkconnection.NetworkConnection;

public class ReportBarGraph extends Fragment {

    private Spinner yearSpinner;
    private String year;
    private String personid;
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet1;
    BarDataSet barDataSet2;
    List<BarEntry> barEntryList = new ArrayList<>();
    List<BarEntry> monthEntryList = new ArrayList<>();

    ArrayList<String> labels = new ArrayList<String>();


    public ReportBarGraph() {
        // required empty constructor
    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_report_bar_graph, container, false);
        Intent intent = getActivity().getIntent();
        personid = intent.getStringExtra("personid");
        yearSpinner = view.findViewById(R.id.spinner_year);
        barChart = view.findViewById(R.id.BarChart);
            labels.add("January");
            labels.add("February");
            labels.add("March");
            labels.add("April");
            labels.add("May");
            labels.add("June");
            labels.add("July");
            labels.add("August");
            labels.add("September");
            labels.add("October");
            labels.add("November");
            labels.add("December");


        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = parent.getItemAtPosition(position).toString();
                getMemoirReportBarDetails getMemoirReportBarDetails = new getMemoirReportBarDetails();
                getMemoirReportBarDetails.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                    return;
            }
        });

        return view;
    }

    public class getMemoirReportBarDetails extends AsyncTask<Void, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(Void... voids) {
            String methodPath = "findbyMovieswatchedpermonth/" + personid + "/" + year;
            String resource = "Memoir";
            JSONArray response = null;
            try {
                JSONObject memoirObject = new JSONObject();
                memoirObject.put("methodPath", methodPath);
                memoirObject.put("resourceValue", resource);
                response = NetworkConnection.GetRequestOkHTTPAsArray(memoirObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.d("reachedmemoirdetails", "success");
                    long numberOfMovies = jsonObject.getLong("Number Of Movies");
                    int movieNumber = (int)numberOfMovies;
                    int month = jsonObject.getInt("MovieMonth");
                    barEntryList.add(new BarEntry(month, movieNumber));
                }
                barDataSet1 = new BarDataSet(barEntryList, "Number Of Movies");
                barData = new BarData(barDataSet1);
                barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
                barData.setBarWidth(0.9f); // set custom bar width
                barChart.setData(barData);
                barChart.setFitBars(true); // make the x-axis fit exactly all bars
                barChart.invalidate();
                barChart.animateY(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
