package com.example.gogoooma.graduationproject;


import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Emotion_Fragment_temp extends Fragment {
    View v;
    private LineChart lineChart = null;
    TextView tv1, tv2, tv3, tv4, tv5, tv6;

    public Emotion_Fragment_temp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_emotion_chartline, container, false);
        lineChart = (LineChart)v.findViewById(R.id.chart);

        EmotionMarkerView marker = new EmotionMarkerView(v.getContext(),R.layout.markerviewtext);
        marker.setChartView(lineChart);
        lineChart.setMarker(marker);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, 89));
        entries.add(new Entry(2, 87));
        entries.add(new Entry(3, 93));
        entries.add(new Entry(4, 91));
        entries.add(new Entry(5, 90));

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            lineChart.setHardwareAccelerationEnabled(false);
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "기분");
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(3);
        lineDataSet.setCircleColor(Color.parseColor("#b386f4"));
        lineDataSet.setCircleHoleColor(Color.WHITE);
        lineDataSet.setColor(Color.parseColor("#b386f4"));//선
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);

        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawFilled(true);
        //lineDataSet.setGradientColor(R.color.graph_gradient, Color.WHITE);
        //lineDataSet.setFillDrawable(drawable);
        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.gradient_emotion_graph);
            lineDataSet.setFillDrawable(drawable);
        }
        else {
            lineDataSet.setFillColor(getResources().getColor(R.color.sexy));
        }
        //lineDataSet.setFillColor(getResources().getColor(R.color.sexy));



        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // return the string va
                Calendar cal = new GregorianCalendar(Locale.KOREA);
                cal.setTime(new Date());
                cal.add(Calendar.DAY_OF_YEAR, (int)value-6); // 하루를 더한다.
                SimpleDateFormat fm = new SimpleDateFormat(
                        "MM월dd일");
                String strDate = fm.format(cal.getTime());

                return  strDate;
            }
        });
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.rgb(97,12,177));

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);
        lineChart.animateY(2000, Easing.EaseInCubic);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.invalidate();

        tv1= (TextView) v.findViewById(R.id.emotion_line1);
        tv2= (TextView) v.findViewById(R.id.emotion_line2);
        tv3= (TextView) v.findViewById(R.id.emotion_line3);
        tv4= (TextView) v.findViewById(R.id.emotion_line4);
        tv5= (TextView) v.findViewById(R.id.emotion_line5);
        tv6= (TextView) v.findViewById(R.id.emotion_line6);

        SharedPreferences auto2, auto3;
        List<Emotion> emotionWin = new ArrayList<>();
        List<Emotion> emotionLose = new ArrayList<>();
        auto2 = getActivity().getSharedPreferences("emotionwtext", Activity.MODE_PRIVATE);
        auto3 = getActivity().getSharedPreferences("emotionltext", Activity.MODE_PRIVATE);
        Map<String,?> keys = auto2.getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            emotionWin.add(new Emotion(entry.getKey(), auto2.getInt(entry.getKey(),0)));
        }

        Map<String,?> keys2 = auto3.getAll();
        for(Map.Entry<String,?> entry : keys2.entrySet()){
            emotionLose.add(new Emotion(entry.getKey(), auto3.getInt(entry.getKey(),0)));
            Log.d("hello2", entry.getKey()+":"+ auto3.getInt(entry.getKey(),0));
        }

        Collections.sort(emotionWin);
        Collections.sort(emotionLose);

        int i=emotionWin.size()-1;
        int j=0;
        if(i>-1) {
            tv1.setText(emotionWin.get(i).name);
            i--;
            if(i>-1) {
                tv2.setText(emotionWin.get(i).name);
                i--;
            }
            if(i>-1) {
                tv3.setText(emotionWin.get(i).name);
            }
        }
        if(j<emotionLose.size()) {
            tv4.setText(emotionLose.get(j).name);
            j++;
            if(j<emotionLose.size()) {
                tv5.setText(emotionLose.get(j).name);
                j++;
            }
            if(j<emotionLose.size()) {
                tv6.setText(emotionLose.get(j).name);
            }
        }

        return v;
    }

}
