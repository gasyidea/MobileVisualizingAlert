package gasyidea.org.mobilereport.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import gasyidea.org.mobilereport.R;

@EFragment(R.layout.activity_main)
public class DetailsActivity extends Fragment {

    @ViewById
    BarChart my_bar_chart;

    /**
     * @apiNote Jeux de données en attendant les scores de vulnérabilité, les solutions proposées pendant une
     * attaque .
     * SMS automatique venant du serveur cible.
     */
    @AfterViews
    void showChart() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));

        BarDataSet dataset = new BarDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        my_bar_chart.setTouchEnabled(true);
        my_bar_chart.setDragEnabled(true);
        my_bar_chart.setScaleEnabled(true);
        my_bar_chart.animateXY(3000, 3000);
        my_bar_chart.setHorizontalScrollBarEnabled(true);
        my_bar_chart.setDoubleTapToZoomEnabled(true);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        dataSets.add(new BarDataSet(entries, "The title"));

        BarData data = new BarData(dataSets);

        my_bar_chart.setData(data);
    }
}
