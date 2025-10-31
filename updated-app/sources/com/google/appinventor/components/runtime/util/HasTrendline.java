package com.google.appinventor.components.runtime.util;

import android.graphics.DashPathEffect;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;

public interface HasTrendline<T extends Entry> extends IBarLineScatterCandleBubbleDataSet<T> {
    int getColor();

    DashPathEffect getDashPathEffect();

    float getLineWidth();

    float[] getPoints(float f, float f2, int i);

    boolean isVisible();
}
