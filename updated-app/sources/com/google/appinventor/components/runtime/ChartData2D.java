package com.google.appinventor.components.runtime;

import android.util.Log;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.YailList;
import gnu.lists.LList;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

@SimpleObject
@DesignerComponent(category = ComponentCategory.CHARTS, description = "A component that holds (x, y)-coordinate based data", iconName = "images/web.png", version = 2)
public final class ChartData2D extends ChartDataBase {
    public ChartData2D(Chart chartContainer) {
        super(chartContainer);
    }

    @SimpleFunction
    public void AddEntry(final String x, final String y) {
        synchronized (this) {
            final AtomicBoolean isDone = new AtomicBoolean(false);
            this.threadRunner.execute(new Runnable() {
                public void run() {
                    try {
                        YailList pair = YailList.makeList(Arrays.asList(new String[]{x, y}));
                        int indexList = ((Chart) ChartData2D.this.container).Labels().indexOf(x);
                        if (indexList > -1) {
                            pair = YailList.makeList(Arrays.asList(new Serializable[]{Integer.valueOf(indexList), y}));
                        }
                        ((ChartDataModel) ChartData2D.this.dataModel).addEntryFromTuple(pair);
                        ChartData2D.this.onDataChange();
                        synchronized (ChartData2D.this) {
                            isDone.set(true);
                            ChartData2D.this.notifyAll();
                        }
                    } catch (Throwable th) {
                        synchronized (ChartData2D.this) {
                            isDone.set(true);
                            ChartData2D.this.notifyAll();
                            throw th;
                        }
                    }
                }
            });
            try {
                if (!isDone.get()) {
                    wait();
                }
            } catch (InterruptedException e) {
            }
        }
    }

    @SimpleFunction
    public void RemoveEntry(final String x, final String y) {
        synchronized (this) {
            final AtomicBoolean isDone = new AtomicBoolean(false);
            this.threadRunner.execute(new Runnable() {
                public void run() {
                    try {
                        YailList pair = YailList.makeList(Arrays.asList(new String[]{x, y}));
                        int indexList = ((Chart) ChartData2D.this.container).Labels().indexOf(x);
                        if (indexList > -1) {
                            pair = YailList.makeList(Arrays.asList(new Serializable[]{Integer.valueOf(indexList), y}));
                        }
                        int index = ((ChartDataModel) ChartData2D.this.dataModel).findEntryIndex(((ChartDataModel) ChartData2D.this.dataModel).getEntryFromTuple(pair));
                        ((ChartDataModel) ChartData2D.this.dataModel).removeEntryFromTuple(pair);
                        ChartData2D.this.resetHighlightAtIndex(index);
                        ChartData2D.this.onDataChange();
                        synchronized (ChartData2D.this) {
                            isDone.set(true);
                            ChartData2D.this.notifyAll();
                        }
                    } catch (Throwable th) {
                        synchronized (ChartData2D.this) {
                            isDone.set(true);
                            ChartData2D.this.notifyAll();
                            throw th;
                        }
                    }
                }
            });
            try {
                if (!isDone.get()) {
                    wait();
                }
            } catch (InterruptedException e) {
            }
        }
    }

    @SimpleFunction(description = "Checks whether an (x, y) entry exists in the Coordinate Data.Returns true if the Entry exists, and false otherwise.")
    public boolean DoesEntryExist(final String x, final String y) {
        try {
            return ((Boolean) this.threadRunner.submit(new Callable<Boolean>() {
                public Boolean call() {
                    return Boolean.valueOf(((ChartDataModel) ChartData2D.this.dataModel).doesEntryExist(YailList.makeList(Arrays.asList(new String[]{x, y}))));
                }
            }).get()).booleanValue();
        } catch (InterruptedException e) {
            Log.e(getClass().getName(), e.getMessage());
            return false;
        } catch (ExecutionException e2) {
            Log.e(getClass().getName(), e2.getMessage());
            return false;
        }
    }

    @Deprecated
    @SimpleFunction(description = "Draws the corresponding line of best fit on the graph")
    public void DrawLineOfBestFit(YailList xList, YailList yList) {
        List<?> predictions = (List) Regression.computeLineOfBestFit(castToDouble((LList) xList.getCdr()), castToDouble((LList) yList.getCdr())).get("predictions");
        List<List<?>> predictionPairs = new ArrayList<>();
        List<?> xValues = (List) xList.getCdr();
        for (int i = 0; i < xValues.size(); i++) {
            predictionPairs.add(Arrays.asList(new Object[]{xValues.get(i), predictions.get(i)}));
        }
        ((ChartDataModel) this.dataModel).importFromList(YailList.makeList((List) predictionPairs));
        if (((ChartDataModel) this.dataModel).getDataset() instanceof LineDataSet) {
            ((ChartDataModel) this.dataModel).getDataset().setDrawCircles(false);
            ((ChartDataModel) this.dataModel).getDataset().setDrawValues(false);
        }
        onDataChange();
    }

    private static class AnomalyManager {
        Set<Integer> indexes;
        Set<Double> xValues;

        private AnomalyManager() {
            this.indexes = new HashSet();
            this.xValues = new HashSet();
        }

        public String toString() {
            Set<Integer> set = this.indexes;
            return "{indexes: " + set + ", xValues: " + this.xValues + "}";
        }
    }

    @SimpleFunction(description = "Highlights data points of choice on the Chart in the color of choice. This block expects a list of data points, each data point is an index, value pair")
    public void HighlightDataPoints(YailList dataPoints, int color) {
        AnomalyManager anomalyManager;
        List<?> dataPointsList = (LList) dataPoints.getCdr();
        if (!dataPoints.isEmpty()) {
            List<?> entries = ((ChartDataModel) this.dataModel).getEntries();
            Map<Double, AnomalyManager> anomalyMap = new HashMap<>();
            int i = 0;
            for (Entry entry : ((ChartDataModel) this.dataModel).getEntries()) {
                if (!anomalyMap.containsKey(Double.valueOf((double) entry.getY()))) {
                    anomalyMap.put(Double.valueOf((double) entry.getY()), new AnomalyManager());
                }
                anomalyMap.get(Double.valueOf((double) entry.getY())).xValues.add(Double.valueOf((double) entry.getX()));
                anomalyMap.get(Double.valueOf((double) entry.getY())).indexes.add(Integer.valueOf(i));
                i++;
            }
            int[] highlights = new int[entries.size()];
            Arrays.fill(highlights, ((ChartDataModel) this.dataModel).getDataset().getColor());
            for (Object next : dataPointsList) {
                if ((next instanceof YailList) && (anomalyManager = anomalyMap.get(Double.valueOf(((Number) ((YailList) next).getObject(1)).doubleValue()))) != null) {
                    Number x = (Number) ((YailList) next).getObject(0);
                    if (anomalyManager.xValues.contains(Double.valueOf(x.doubleValue())) || anomalyManager.indexes.contains(Integer.valueOf(x.intValue() - 1))) {
                        for (Integer index : anomalyManager.indexes) {
                            highlights[index.intValue()] = color;
                        }
                    }
                }
            }
            ((ChartDataModel) this.dataModel).getDataset().setCircleColors(highlights);
            onDataChange();
            return;
        }
        throw new IllegalStateException("Anomalies list is Empty. Nothing to highlight!");
    }

    public List<Double> getYValues() {
        List<Double> yValues = new ArrayList<>();
        for (Entry entry : ((ChartDataModel) this.dataModel).getEntries()) {
            yValues.add(Double.valueOf((double) entry.getY()));
        }
        return yValues;
    }

    /* access modifiers changed from: private */
    public void resetHighlightAtIndex(int index) {
        if (((ChartDataModel) this.dataModel).getDataset() instanceof LineDataSet) {
            ((ChartDataModel) this.dataModel).getDataset().getCircleColors().remove(index);
        }
    }
}
