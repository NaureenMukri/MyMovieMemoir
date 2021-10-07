package com.example.mymoviememoir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import networkconnection.NetworkConnection;

public class ReportPieChart extends Fragment {

    Button getPieChartValues;
    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList pieEntries;
    DatePicker startdate;
    DatePicker enddate;
    String personid;
    String startDateSelected;
    String endDateSelected;
    List<PieEntry> movieEntries= new ArrayList<>();

    public ReportPieChart(){
        // required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_report_pie_chart, container, false);
        Intent intent = getActivity().getIntent();
        startdate = view.findViewById(R.id.datePicker_startDate);
        enddate = view.findViewById(R.id.datePicker_endDate);
        getPieChartValues = view.findViewById(R.id.btn_getPieChart);
        personid = intent.getStringExtra("personid");
        pieChart = view.findViewById(R.id.pieChart);

        getPieChartValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int startDay = startdate.getDayOfMonth();
            int startMonth = startdate.getMonth();
            int startYear = startdate.getYear();
            startDateSelected = "" + startYear + "-" + startMonth + "-" + startDay + " " + 00 + ":" + 00 + ":" + 00 + "." + 000;
            int endDay = enddate.getDayOfMonth();
            int endMonth = enddate.getMonth();
            int endYear = enddate.getYear();
            endDateSelected = "" + endYear + "-" + endMonth + "-" + endDay  + " " + 00 + ":" + 00 + ":" + 00 + "." + 000;
            getMemoirReportDetails getMemoirReportDetails = new getMemoirReportDetails();
            getMemoirReportDetails.execute();

            }
        });

        return view;
    }


    public class getMemoirReportDetails extends AsyncTask<Void, Void, JSONArray>{

        @Override
        protected JSONArray doInBackground(Void... voids) {
            String methodPath = "findbyPersonidandtimeperiod/" + personid + "/" + startDateSelected + "/" + endDateSelected;
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
                    int postcode = jsonObject.getInt("PostCode");
                    String postcodeValues = String.valueOf(postcode);
                    long count = jsonObject.getLong("MovieCount");
                    movieEntries.add(new PieEntry(count, postcodeValues));
                }
                pieDataSet = new PieDataSet(movieEntries, "Movies Watched in the Given Time Period");
                pieData = new PieData(pieDataSet);
                pieChart.setData(pieData);
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieDataSet.setValueTextColor(Color.WHITE);
                pieChart.setUsePercentValues(true);
                pieChart.setEntryLabelTextSize(13f);
                pieChart.setNoDataText("No Movies Found in the Given Range");
                pieChart.invalidate(); //refreshing the pie chart
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
