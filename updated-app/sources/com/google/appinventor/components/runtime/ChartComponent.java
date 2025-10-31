package com.google.appinventor.components.runtime;

public interface ChartComponent extends Component {
    ChartDataModel<?, ?, ?, ?, ?> getDataModel();

    void initChartData();
}
