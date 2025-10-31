package com.google.appinventor.components.common;

import java.util.List;
import java.util.Map;

public interface TrendlineCalculator {
    Map<String, Object> compute(List<Double> list, List<Double> list2);

    float[] computePoints(Map<String, Object> map, float f, float f2, int i, int i2);
}
