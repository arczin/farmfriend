package com.google.appinventor.components.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinearRegression implements TrendlineCalculator {
    public Map<String, Object> compute(List<Double> x, List<Double> y) {
        List<Double> list = x;
        List<Double> list2 = y;
        if (x.isEmpty() || y.isEmpty()) {
            throw new IllegalStateException("List must have at least one element");
        } else if (x.size() == y.size()) {
            int n = x.size();
            double sumx = 0.0d;
            double sumy = 0.0d;
            double sumXY = 0.0d;
            double squareSumX = 0.0d;
            double squareSumY = 0.0d;
            for (int i = 0; i < n; i++) {
                sumx += list.get(i).doubleValue();
                sumXY += list.get(i).doubleValue() * list2.get(i).doubleValue();
                sumy += list2.get(i).doubleValue();
                squareSumX += list.get(i).doubleValue() * list.get(i).doubleValue();
                squareSumY += list2.get(i).doubleValue() * list2.get(i).doubleValue();
            }
            double d = (double) n;
            Double.isNaN(d);
            double xmean = sumx / d;
            double squareSumY2 = squareSumY;
            double squareSumY3 = (double) n;
            Double.isNaN(squareSumY3);
            double ymean = sumy / squareSumY3;
            double xxmean = 0.0d;
            double xymean = 0.0d;
            List<Double> predictions = new ArrayList<>();
            double squareSumY4 = squareSumY2;
            for (int i2 = 0; i2 < n; i2++) {
                xxmean += (list.get(i2).doubleValue() - xmean) * (list.get(i2).doubleValue() - xmean);
                xymean += (list.get(i2).doubleValue() - xmean) * (list2.get(i2).doubleValue() - ymean);
            }
            double slope = xymean / xxmean;
            double intercept = ymean - (slope * xmean);
            for (Double value : x) {
                predictions.add(Double.valueOf((value.doubleValue() * slope) + intercept));
                ymean = ymean;
            }
            double d2 = xmean;
            double xmean2 = (double) n;
            Double.isNaN(xmean2);
            double d3 = (xmean2 * sumXY) - (sumx * sumy);
            double d4 = sumXY;
            double sumXY2 = (double) n;
            Double.isNaN(sumXY2);
            double d5 = (sumXY2 * squareSumX) - (sumx * sumx);
            double d6 = sumx;
            double sumx2 = (double) n;
            Double.isNaN(sumx2);
            double corr = d3 / Math.sqrt(d5 * ((sumx2 * squareSumY4) - (sumy * sumy)));
            Map<String, Object> resultDic = new HashMap<>();
            resultDic.put("slope", Double.valueOf(slope));
            resultDic.put("Yintercept", Double.valueOf(intercept));
            resultDic.put("correlation coefficient", Double.valueOf(corr));
            resultDic.put("predictions", predictions);
            resultDic.put("Xintercepts", Double.valueOf(slope == 0.0d ? Double.NaN : (-intercept) / slope));
            resultDic.put("r^2", Double.valueOf(corr * corr));
            return resultDic;
        } else {
            throw new IllegalStateException("Must have equal X and Y data points");
        }
    }

    public float[] computePoints(Map<String, Object> results, float xMin, float xMax, int viewWidth, int steps) {
        if (!results.containsKey("slope")) {
            return new float[0];
        }
        double m = ((Double) results.get("slope")).doubleValue();
        double b = ((Double) results.get("Yintercept")).doubleValue();
        double d = (double) xMin;
        Double.isNaN(d);
        double d2 = (double) xMax;
        Double.isNaN(d2);
        return new float[]{xMin, (float) ((d * m) + b), xMax, (float) ((d2 * m) + b)};
    }
}
