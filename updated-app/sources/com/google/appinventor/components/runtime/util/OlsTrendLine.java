package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.common.TrendlineCalculator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public abstract class OlsTrendLine implements TrendlineCalculator {
    private static final boolean DEBUG = false;
    private static final double SIGNIFICANCE = 1.0E14d;

    /* access modifiers changed from: protected */
    public abstract boolean logY();

    /* access modifiers changed from: protected */
    public abstract int size();

    /* access modifiers changed from: protected */
    public abstract double[] xVector(double d);

    public Map<String, Object> compute(List<Double> x, List<Double> y) {
        if (x.isEmpty() || y.isEmpty()) {
            throw new IllegalStateException("List must have at least one element");
        } else if (x.size() == y.size()) {
            double[][] xData = new double[x.size()][];
            for (int i = 0; i < x.size(); i++) {
                xData[i] = xVector(x.get(i).doubleValue());
            }
            double[] yData = new double[y.size()];
            if (logY()) {
                int i2 = 0;
                for (Double value : y) {
                    yData[i2] = Math.log(value.doubleValue());
                    i2++;
                }
            } else {
                int i3 = 0;
                for (Double value2 : y) {
                    yData[i3] = value2.doubleValue();
                    i3++;
                }
            }
            OLSMultipleLinearRegression ols = new OLSMultipleLinearRegression();
            ols.setNoIntercept(true);
            ols.newSampleData(yData, xData);
            RealMatrix coef = MatrixUtils.createColumnRealMatrix(ols.estimateRegressionParameters());
            Map<String, Object> result = new HashMap<>();
            result.put("intercept", Double.valueOf(round(coef.getEntry(0, 0))));
            result.put("slope", Double.valueOf(round(coef.getEntry(1, 0))));
            if (size() > 2) {
                result.put("x^2", Double.valueOf(round(coef.getEntry(2, 0))));
            }
            result.put("r^2", Double.valueOf(ols.calculateRSquared()));
            return result;
        } else {
            throw new IllegalStateException("Must have equal X and Y data points");
        }
    }

    private static double round(double value) {
        double round = (double) Math.round(value * SIGNIFICANCE);
        Double.isNaN(round);
        return round / SIGNIFICANCE;
    }
}
