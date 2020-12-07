package com.eic.sera;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import com.github.mikephil.charting.charts.LineChart;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        lineChart = findViewById(R.id.lineChart);

        GunlukDurumCall call = new GunlukDurumCall();
        call.execute();
    }


    void drawLineChart(ArrayList<Float> y,ArrayList<String> x){
        lineChart.setDescription("desc");
        String[] xValues =  x.toArray( new String[x.size()]);
        Float[]  yValues =   y.toArray( new Float[y.size()]);

        List<Entry> yData = new ArrayList<>();
        for( int i = 0; i  < yValues.length; i++){
            yData.add(new Entry(yValues[i],i));
        }

        List<String> xData = new ArrayList<>();
        for( int i = 0; i  < xValues.length; i++){
            xData.add(xValues[i]);
        }

        LineDataSet lineDataSet  = new LineDataSet(yData, "label");

        lineDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        LineData lineData = new LineData(xData,lineDataSet);

        lineData.setValueTextSize(13f);
        lineData.setValueTextColor(Color.BLACK);

        lineChart.setData(lineData);

        lineChart.setDragEnabled(true);
        lineChart.setSaveEnabled(true);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.resetLabelsToSkip();
        
        lineChart.invalidate();

    }



    public  class GunlukDurumCall extends AsyncTask<String,Void,String>  {

        int resultCode;
        StringBuilder stringBuilder = new StringBuilder();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL("https://fuzzysera.herokuapp.com/gunlukdurum");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");


                try{
                    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(conn.getInputStream()));

                    String line;

                    while((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    resultCode=conn.getResponseCode();

                    Log.e("OSD", "asfsd");
                    return stringBuilder.toString();

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    conn.disconnect();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            if(result == null ||  resultCode != 200){
                result = "HATA";

            }else{

                try {
                    JSONArray jsonArray = new JSONArray(result);

                    ArrayList<String> saat = new ArrayList<String>();
                    ArrayList<Float> sicakliklar = new ArrayList<Float>();
                    for (int i = 0 ; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        sicakliklar.add(Float.valueOf(jsonObject.getInt("sicaklik")));
                        saat.add(jsonObject.getString("timeStamp")) ;

                        drawLineChart(sicakliklar,saat);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }
}
