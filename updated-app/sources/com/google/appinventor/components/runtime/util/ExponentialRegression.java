package com.google.appinventor.components.runtime.util;

import java.util.List;
import java.util.Map;

public class ExponentialRegression extends OlsTrendLine {
    /* access modifiers changed from: protected */
    public double[] xVector(double x) {
        return new double[]{1.0d, x};
    }

    /* access modifiers changed from: protected */
    public boolean logY() {
        return true;
    }

    /* access modifiers changed from: protected */
    public int size() {
        return 2;
    }

    public Map<String, Object> compute(List<Double> x, List<Double> y) {
        Map<String, Object> result = super.compute(x, y);
        result.remove("x^2");
        double m = ((Double) result.remove("slope")).doubleValue();
        result.put("a", Double.valueOf(Math.exp(((Double) result.remove("intercept")).doubleValue())));
        result.put("b", Double.valueOf(Math.exp(m)));
        return result;
    }

    public float[] computePoints(Map<String, Object> results, float xMin, float xMax, int viewWidth, int steps) {
        Map<String, Object> map = results;
        int i = steps;
        if (!results.containsKey("a")) {
            return new float[0];
        }
        double a = ((Double) results.get("a")).doubleValue();
        double b = ((Double) results.get("b")).doubleValue();
        float[] result = new float[(i * 4)];
        float lastX = Float.NEGATIVE_INFINITY;
        float lastY = Float.NEGATIVE_INFINITY;
        boolean first = true;
        for (int i2 = 0; i2 < i; i2++) {
            if (first) {
                first = false;
                lastX = xMin + ((((float) i2) * (xMax - xMin)) / ((float) i));
                lastY = (float) (Math.pow(b, (double) lastX) * a);
            }
            result[i2 * 4] = lastX;
            result[(i2 * 4) + 1] = lastY;
            float newX = xMin + ((((float) (i2 + 1)) * (xMax - xMin)) / ((float) i));
            float newY = (float) (Math.pow(b, (double) newX) * a);
            result[(i2 * 4) + 2] = newX;
            result[(i2 * 4) + 3] = newY;
            lastX = newX;
            lastY = newY;
        }
        return result;
    }
}
