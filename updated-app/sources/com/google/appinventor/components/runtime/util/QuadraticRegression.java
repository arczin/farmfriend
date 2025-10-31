package com.google.appinventor.components.runtime.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuadraticRegression extends OlsTrendLine {
    /* access modifiers changed from: protected */
    public double[] xVector(double x) {
        return new double[]{1.0d, x, x * x};
    }

    /* access modifiers changed from: protected */
    public boolean logY() {
        return false;
    }

    /* access modifiers changed from: protected */
    public int size() {
        return 3;
    }

    public Map<String, Object> compute(List<Double> x, List<Double> y) {
        Map<String, Object> result = super.compute(x, y);
        result.put("Yintercept", result.remove("intercept"));
        double a = ((Double) result.get("x^2")).doubleValue();
        double b = ((Double) result.get("slope")).doubleValue();
        double c = ((Double) result.get("Yintercept")).doubleValue();
        double discriminant = (b * b) - ((4.0d * a) * c);
        if (discriminant > 0.0d) {
            List<Double> intercepts = new ArrayList<>();
            double sqrtDiscriminant = Math.sqrt(discriminant);
            double d = c;
            intercepts.add(Double.valueOf(((-b) + sqrtDiscriminant) / (a * 2.0d)));
            intercepts.add(Double.valueOf(((-b) - sqrtDiscriminant) / (2.0d * a)));
            result.put("Xintercepts", intercepts);
        } else {
            if (discriminant == 0.0d) {
                result.put("Xintercepts", Double.valueOf((-b) / (2.0d * a)));
            } else {
                result.put("Xintercepts", Double.valueOf(Double.NaN));
            }
        }
        return result;
    }

    public float[] computePoints(Map<String, Object> results, float xMin, float xMax, int viewWidth, int steps) {
        Map<String, Object> map = results;
        int i = steps;
        if (!map.containsKey("x^2")) {
            return new float[0];
        }
        double a = ((Double) map.get("x^2")).doubleValue();
        double b = ((Double) map.get("slope")).doubleValue();
        double c = ((Double) map.get("Yintercept")).doubleValue();
        float[] result = new float[(i * 4)];
        float lastX = Float.NEGATIVE_INFINITY;
        float lastY = Float.NEGATIVE_INFINITY;
        boolean first = true;
        int i2 = 0;
        while (i2 < i) {
            if (first) {
                lastX = xMin + ((((float) i2) * (xMax - xMin)) / ((float) i));
                double d = (double) lastX;
                Double.isNaN(d);
                float f = lastY;
                double d2 = (double) lastX;
                Double.isNaN(d2);
                lastY = (float) ((((d * a) + b) * d2) + c);
                first = false;
            } else {
                float f2 = lastY;
            }
            result[i2 * 4] = lastX;
            result[(i2 * 4) + 1] = lastY;
            float newX = xMin + ((((float) (i2 + 1)) * (xMax - xMin)) / ((float) i));
            double d3 = (double) newX;
            Double.isNaN(d3);
            double d4 = (double) newX;
            Double.isNaN(d4);
            float newY = (float) ((((d3 * a) + b) * d4) + c);
            result[(i2 * 4) + 2] = newX;
            result[(i2 * 4) + 3] = newY;
            lastX = newX;
            lastY = newY;
            i2++;
            Map<String, Object> map2 = results;
            i = steps;
        }
        return result;
    }
}
