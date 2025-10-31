package com.google.appinventor.components.runtime;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.google.appinventor.components.runtime.PointChartView;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.YailList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public abstract class PointChartDataModel<E extends Entry, T extends IBarLineScatterCandleBubbleDataSet<E>, D extends BarLineScatterCandleBubbleData<T>, C extends BarLineChartBase<D>, V extends PointChartView<E, T, D, C, V>> extends Chart2DDataModel<E, T, D, C, V> {
    private static final String DATE_PATTERN = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
    private static final String TIME_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-6][0-9]";

    protected PointChartDataModel(D data, V view) {
        super(data, view);
    }

    public Entry getEntryFromTuple(YailList tuple) {
        String xValue;
        String yValue;
        float x;
        try {
            xValue = tuple.getString(0);
            yValue = tuple.getString(1);
            if (Pattern.compile(DATE_PATTERN).matcher(xValue).matches()) {
                x = (float) new SimpleDateFormat("dd/MM/yyyy").parse(xValue).getTime();
            } else if (Pattern.compile(TIME_PATTERN).matcher(xValue).matches()) {
                x = (float) new SimpleDateFormat("HH:mm:ss").parse(xValue).getTime();
            } else {
                x = Float.parseFloat(xValue);
            }
            return new Entry(x, Float.parseFloat(yValue));
        } catch (NumberFormatException e) {
            ((PointChartView) this.view).getForm().dispatchErrorOccurredEvent(((PointChartView) this.view).chartComponent, "GetEntryFromTuple", ErrorMessages.ERROR_INVALID_CHART_ENTRY_VALUES, xValue, yValue);
            return null;
        } catch (ParseException e2) {
            throw new RuntimeException(e2);
        } catch (NullPointerException e3) {
            ((PointChartView) this.view).getForm().dispatchErrorOccurredEvent(((PointChartView) this.view).chartComponent, "GetEntryFromTuple", ErrorMessages.ERROR_NULL_CHART_ENTRY_VALUES, new Object[0]);
            return null;
        } catch (IndexOutOfBoundsException e4) {
            ((PointChartView) this.view).getForm().dispatchErrorOccurredEvent(((PointChartView) this.view).chartComponent, "GetEntryFromTuple", ErrorMessages.ERROR_INSUFFICIENT_CHART_ENTRY_VALUES, Integer.valueOf(getTupleSize()), Integer.valueOf(tuple.size()));
            return null;
        }
    }
}
