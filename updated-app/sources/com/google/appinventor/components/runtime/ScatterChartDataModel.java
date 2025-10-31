package com.google.appinventor.components.runtime;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.google.appinventor.components.common.PointStyle;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.ArrayList;
import java.util.Collections;

public class ScatterChartDataModel extends PointChartDataModel<Entry, IScatterDataSet, ScatterData, ScatterChart, ScatterChartView> {
    public ScatterChartDataModel(ScatterData data, ScatterChartView view) {
        this(data, view, new ScatterDataSet(new ArrayList(), ""));
    }

    protected ScatterChartDataModel(ScatterData data, ScatterChartView view, IScatterDataSet dataset) {
        super(data, view);
        this.dataset = dataset;
        this.data.addDataSet(dataset);
        setDefaultStylingProperties();
    }

    public void addEntryFromTuple(YailList tuple) {
        int index;
        Entry entry = getEntryFromTuple(tuple);
        if (entry != null) {
            int index2 = Collections.binarySearch(this.entries, entry, new EntryXComparator());
            if (index2 < 0) {
                index = (-index2) - 1;
            } else {
                int entryCount = this.entries.size();
                while (index2 < entryCount && ((Entry) this.entries.get(index2)).getX() == entry.getX()) {
                    index2++;
                }
                index = index2;
            }
            this.entries.add(index, entry);
        }
    }

    /* access modifiers changed from: protected */
    public void setDefaultStylingProperties() {
        if (this.dataset instanceof ScatterDataSet) {
            this.dataset.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        }
    }

    public void setPointShape(PointStyle shape) {
        if (this.dataset instanceof ScatterDataSet) {
            switch (shape) {
                case Circle:
                    this.dataset.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
                    return;
                case Square:
                    this.dataset.setScatterShape(ScatterChart.ScatterShape.SQUARE);
                    return;
                case Triangle:
                    this.dataset.setScatterShape(ScatterChart.ScatterShape.TRIANGLE);
                    return;
                case Cross:
                    this.dataset.setScatterShape(ScatterChart.ScatterShape.CROSS);
                    return;
                case X:
                    this.dataset.setScatterShape(ScatterChart.ScatterShape.X);
                    return;
                default:
                    throw new IllegalArgumentException("Unknown shape type: " + shape);
            }
        }
    }
}
