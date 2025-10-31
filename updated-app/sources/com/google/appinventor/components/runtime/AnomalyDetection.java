package com.google.appinventor.components.runtime;

import com.github.mikephil.charting.data.Entry;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.YailList;
import gnu.lists.LList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SimpleObject
@DesignerComponent(category = ComponentCategory.DATASCIENCE, description = "A component that contains anomaly detection models", iconName = "images/anomaly.png", nonVisible = true, version = 2)
public final class AnomalyDetection extends DataCollection<ComponentContainer, DataModel<?>> {
    public AnomalyDetection(ComponentContainer container) {
        super(container);
    }

    @SimpleFunction(description = "Z-Score Anomaly Detection: checks each data point's Z-scoreagainst the given threshold if a data point's Z-score is greater than the threshold, the data point is labeled as anomaly and returned in a list of pairs (anomaly index, anomaly value)")
    public List<List<?>> DetectAnomalies(YailList dataList, double threshold) {
        List<List<?>> anomalies = new ArrayList<>();
        List<Double> data = castToDouble((LList) dataList.getCdr());
        double sum = 0.0d;
        for (int i = 0; i < data.size(); i++) {
            sum += data.get(i).doubleValue();
        }
        double size = (double) data.size();
        Double.isNaN(size);
        double mean = sum / size;
        double variance = 0.0d;
        for (int i2 = 0; i2 < data.size(); i2++) {
            variance += Math.pow(data.get(i2).doubleValue() - mean, 2.0d);
        }
        double size2 = (double) data.size();
        Double.isNaN(size2);
        double sd = Math.sqrt(variance / size2);
        for (int i3 = 0; i3 < data.size(); i3++) {
            if (Math.abs((data.get(i3).doubleValue() - mean) / sd) > threshold) {
                anomalies.add(Arrays.asList(new Number[]{Integer.valueOf(i3 + 1), data.get(i3)}));
            }
        }
        return anomalies;
    }

    @SimpleFunction
    public List<List<?>> DetectAnomaliesInChartData(ChartData2D chartData, double threshold) {
        List<Entry> entries;
        List<Entry> entries2 = chartData.getDataValue((Object) null);
        double sum = 0.0d;
        for (Entry entry : entries2) {
            double y = (double) entry.getY();
            Double.isNaN(y);
            sum += y;
        }
        double size = (double) entries2.size();
        Double.isNaN(size);
        double mean = sum / size;
        double variance = 0.0d;
        for (Entry entry2 : entries2) {
            double y2 = (double) entry2.getY();
            Double.isNaN(y2);
            variance += Math.pow(y2 - mean, 2.0d);
        }
        double size2 = (double) entries2.size();
        Double.isNaN(size2);
        double sd = Math.sqrt(variance / size2);
        List<List<?>> anomalies = new ArrayList<>();
        for (Entry entry3 : entries2) {
            double y3 = (double) entry3.getY();
            Double.isNaN(y3);
            if (Math.abs((y3 - mean) / sd) > threshold) {
                entries = entries2;
                anomalies.add(Arrays.asList(new Float[]{Float.valueOf(entry3.getX()), Float.valueOf(entry3.getY())}));
            } else {
                entries = entries2;
            }
            entries2 = entries;
        }
        return anomalies;
    }

    @SimpleFunction(description = "Given a single anomaly and the x and y values of your data. This block will return the x and y value pairs of your data without the anomaly")
    public List<List<?>> CleanData(YailList anomaly, YailList xList, YailList yList) {
        List<Double> xData = castToDouble((LList) xList.getCdr());
        List<Double> yData = castToDouble((LList) yList.getCdr());
        if (xData.size() != yData.size()) {
            throw new IllegalStateException("Must have equal X and Y data points");
        } else if (xData.size() == 0 || yData.size() == 0) {
            throw new IllegalStateException("List must have at least one element");
        } else {
            int index = (int) getAnomalyIndex(anomaly);
            xData.remove(index - 1);
            yData.remove(index - 1);
            List<List<?>> cleanData = new ArrayList<>();
            if (xData.size() == yData.size()) {
                for (int i = 0; i < xData.size(); i++) {
                    cleanData.add(YailList.makeList(Arrays.asList(new Double[]{xData.get(i), yData.get(i)})));
                }
            }
            return cleanData;
        }
    }

    public static double getAnomalyIndex(YailList anomaly) {
        if (!anomaly.isEmpty()) {
            return castToDouble((LList) anomaly.getCdr()).get(0).doubleValue();
        }
        throw new IllegalStateException("Must have equal X and Y data points");
    }

    public void ElementsFromPairs(String elements) {
    }

    public void SpreadsheetUseHeaders(boolean useHeaders) {
    }

    public void DataFileXColumn(String column) {
    }

    public void WebXColumn(String column) {
    }

    public void SpreadsheetXColumn(String column) {
    }

    public void DataFileYColumn(String column) {
    }

    public void WebYColumn(String column) {
    }

    public void SpreadsheetYColumn(String column) {
    }

    public void DataSourceKey(String key) {
    }

    public <K, V> void Source(DataSource<K, V> dataSource) {
    }

    public void ImportFromList(YailList list) {
    }

    public void Clear() {
    }

    public <K, V> void ChangeDataSource(DataSource<K, V> dataSource, String keyValue) {
    }

    public void RemoveDataSource() {
    }

    public YailList GetEntriesWithXValue(String x) {
        return YailList.makeEmptyList();
    }

    public YailList GetEntriesWithYValue(String y) {
        return YailList.makeEmptyList();
    }

    public YailList GetAllEntries() {
        return YailList.makeEmptyList();
    }

    public void ImportFromTinyDB(TinyDB tinyDB, String tag) {
    }

    public void ImportFromCloudDB(CloudDB cloudDB, String tag) {
    }

    public void ImportFromDataFile(DataFile dataFile, String xValueColumn, String yValueColumn) {
    }

    public void ImportFromSpreadsheet(Spreadsheet spreadsheet, String xColumn, String yColumn, boolean useHeaders) {
    }

    public void ImportFromWeb(Web web, String xValueColumn, String yValueColumn) {
    }

    public void onDataChange() {
    }
}
