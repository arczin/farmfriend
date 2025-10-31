package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.Options;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.LOBFValues;
import com.google.appinventor.components.common.LinearRegression;
import com.google.appinventor.components.runtime.util.YailDictionary;
import com.google.appinventor.components.runtime.util.YailList;
import gnu.lists.LList;
import java.util.List;

@SimpleObject
@DesignerComponent(category = ComponentCategory.DATASCIENCE, description = "A component that contains regression models", iconName = "images/regression.png", nonVisible = true, version = 2)
public final class Regression extends DataCollection<ComponentContainer, DataModel<?>> {
    private static final LinearRegression LINEAR_REGRESSION = new LinearRegression();

    public Regression(ComponentContainer container) {
        super(container);
    }

    public static YailDictionary computeLineOfBestFit(List<Double> x, List<Double> y) {
        return new YailDictionary(LINEAR_REGRESSION.compute(x, y));
    }

    @SimpleFunction(description = "Returns one of the Line of Best Fit values. A value could be\"slope\", \"Yintercept\", \"correlation coefficient\"or \"predictions\". The block returns the complete dictionary with all values if no specific value string is provided")
    public Object CalculateLineOfBestFitValue(YailList xList, YailList yList, @Options(LOBFValues.class) String value) {
        YailDictionary result = computeLineOfBestFit(castToDouble((LList) xList.getCdr()), castToDouble((LList) yList.getCdr()));
        if (result.containsKey(value)) {
            return result.get(value);
        }
        return result;
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
