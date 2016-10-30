package gasyidea.org.mobilereport.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import gasyidea.org.mobilereport.R;
import gasyidea.org.mobilereport.utils.SmsDB;

@EActivity(R.layout.activity_details)
public class DetailsActivity extends AppCompatActivity {

    @ViewById
    BarChart my_bar_chart;

    @ViewById
    AppCompatTextView status, soluce;

    @Bean
    SmsDB smsDB;

    @Extra
    String keyAttack, soluceCode;

    @AfterViews
    void showChart() {

        status.setText(keyAttack);
        soluce.setText(soluceCode);

        HashMap<Integer, String> results = smsDB.getStats();
        ArrayList<BarEntry> entries = new ArrayList<>();

        Iterator it = results.entrySet().iterator();
        int i = 1;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            entries.add(new BarEntry(Float.valueOf(pair.getKey().toString()), i));
            it.remove();
            i += 1;
        }

        Iterator iterator = results.entrySet().iterator();
        ArrayList<String> labels = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            labels.add(pair.getValue().toString());
            iterator.remove();
        }

        YAxis y = my_bar_chart.getAxisLeft();
        y.setAxisMaxValue(10);
        y.setAxisMinValue(0);

        my_bar_chart.setVisibleXRangeMinimum(10);

        my_bar_chart.setTouchEnabled(true);
        my_bar_chart.setDragEnabled(true);
        my_bar_chart.setScaleEnabled(true);
        my_bar_chart.animateXY(1000, 1000);
        my_bar_chart.setHorizontalScrollBarEnabled(true);
        my_bar_chart.setDoubleTapToZoomEnabled(true);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        dataSets.add(new BarDataSet(entries, "IP ADDRESS"));

        BarData data = new BarData(dataSets);

        my_bar_chart.setData(data);
    }
}
