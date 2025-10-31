package com.google.appinventor.components.runtime;

import android.view.MotionEvent;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.LineType;
import com.google.appinventor.components.common.PointStyle;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.ArrayList;
import java.util.List;

@SimpleObject
public abstract class ChartDataBase extends DataCollection<Chart, ChartDataModel<?, ?, ?, ?, ?>> implements ChartComponent, OnChartGestureListener, OnChartValueSelectedListener {
    private int color;
    private YailList colors;
    private int dataLabelColor;
    private String label;

    protected ChartDataBase(Chart chartContainer) {
        super(chartContainer);
        chartContainer.addDataComponent(this);
        initChartData();
        int i = -16777216;
        Color(-16777216);
        DataSourceKey("");
        Label("");
        DataLabelColor(!chartContainer.$form().isDarkTheme() ? -1 : i);
    }

    public void initChartData() {
        this.dataModel = ((Chart) this.container).createChartModel();
        ((ChartDataModel) this.dataModel).view.chart.setOnChartGestureListener(this);
        ((ChartDataModel) this.dataModel).view.chart.setOnChartValueSelectedListener(this);
    }

    public ChartDataModel<?, ?, ?, ?, ?> getDataModel() {
        return (ChartDataModel) this.dataModel;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int Color() {
        return this.color;
    }

    @DesignerProperty(defaultValue = "&HFF000000", editorType = "color")
    @SimpleProperty
    public void Color(int argb) {
        this.color = argb;
        ((ChartDataModel) this.dataModel).setColor(this.color);
        onDataChange();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public YailList Colors() {
        return this.colors;
    }

    @SimpleProperty
    public void Colors(YailList colors2) {
        List<Integer> resultColors = new ArrayList<>();
        for (int i = 0; i < colors2.size(); i++) {
            String color2 = colors2.getString(i);
            try {
                long colorValue = Long.parseLong(color2);
                if (colorValue > 2147483647L) {
                    colorValue -= 4294967296L;
                }
                resultColors.add(Integer.valueOf((int) colorValue));
            } catch (NumberFormatException e) {
                ((Chart) this.container).$form().dispatchErrorOccurredEvent((Component) this.container, "Colors", ErrorMessages.ERROR_INVALID_CHART_DATA_COLOR, color2);
            }
        }
        this.colors = YailList.makeList((List) resultColors);
        ((ChartDataModel) this.dataModel).setColors(resultColors);
        onDataChange();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int DataLabelColor() {
        return this.dataLabelColor;
    }

    @DesignerProperty(defaultValue = "&HFF000000", editorType = "color")
    @SimpleProperty
    public void DataLabelColor(int argb) {
        this.dataLabelColor = argb;
        ((ChartDataModel) this.dataModel).setDataLabelColor(argb);
        onDataChange();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public String Label() {
        return this.label;
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty
    public void Label(String text) {
        this.label = text;
        ((ChartDataModel) this.dataModel).setLabel(text);
        onDataChange();
    }

    @DesignerProperty(defaultValue = "0", editorType = "chart_point_shape")
    @SimpleProperty(category = PropertyCategory.APPEARANCE, userVisible = false)
    public void PointShape(PointStyle shape) {
        if (this.dataModel instanceof ScatterChartDataModel) {
            ((ScatterChartDataModel) this.dataModel).setPointShape(shape);
        }
    }

    @DesignerProperty(defaultValue = "0", editorType = "chart_line_type")
    @SimpleProperty(category = PropertyCategory.APPEARANCE, userVisible = false)
    public void LineType(LineType type) {
        if (this.dataModel instanceof LineChartBaseDataModel) {
            ((LineChartBaseDataModel) this.dataModel).setLineType(type);
        }
    }

    public void onDataChange() {
        ((Chart) this.container).getChartView().refresh((ChartDataModel) this.dataModel);
        for (DataSourceChangeListener listener : this.listeners) {
            listener.onDataSourceValueChange(this, (String) null, (Object) null);
        }
    }

    public void onChartGestureStart(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture) {
    }

    public void onChartGestureEnd(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture) {
    }

    public void onChartLongPressed(MotionEvent motionEvent) {
    }

    public void onChartDoubleTapped(MotionEvent motionEvent) {
    }

    public void onChartSingleTapped(MotionEvent motionEvent) {
    }

    public void onChartFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
    }

    public void onChartScale(MotionEvent motionEvent, float v, float v1) {
    }

    public void onChartTranslate(MotionEvent motionEvent, float v, float v1) {
    }

    public void onValueSelected(final Entry entry, Highlight highlight) {
        ((Chart) this.container).$form().runOnUiThread(new Runnable() {
            public void run() {
                if (entry instanceof PieEntry) {
                    ChartDataBase.this.EntryClick(entry.getLabel(), (double) entry.getValue());
                } else {
                    ChartDataBase.this.EntryClick(Float.valueOf(entry.getX()), (double) entry.getY());
                }
            }
        });
    }

    @SimpleEvent
    public void EntryClick(Object x, double y) {
        EventDispatcher.dispatchEvent(this, "EntryClick", x, Double.valueOf(y));
        ((Chart) this.container).EntryClick(this, x, y);
    }

    public void onNothingSelected() {
    }
}
