package com.google.appinventor.components.runtime.util;

import java.util.List;
import java.util.Map;

public class LogarithmicRegression extends OlsTrendLine {
    /* access modifiers changed from: protected */
    public double[] xVector(double x) {
        return new double[]{1.0d, Math.log(x)};
    }

    /* access modifiers changed from: protected */
    public boolean logY() {
        return false;
    }

    /* access modifiers changed from: protected */
    public int size() {
        return 2;
    }

    public Map<String, Object> compute(List<Double> x, List<Double> y) {
        Map<String, Object> result = super.compute(x, y);
        result.remove("x^2");
        double m = ((Double) result.remove("slope")).doubleValue();
        result.put("a", Double.valueOf(((Double) result.remove("intercept")).doubleValue()));
        result.put("b", Double.valueOf(m));
        return result;
    }

    public float[] computePoints(Map<String, Object> results, float xMin, float xMax, int viewWidth, int steps) {
        float xMin2;
        Map<String, Object> map = results;
        int i = steps;
        if (!map.containsKey("b")) {
            return new float[0];
        }
        if (xMax <= 0.0f) {
            return new float[0];
        }
        if (xMin <= 0.0f) {
            xMin2 = Math.min(1.0E-4f, xMax / ((float) (i + 1)));
        } else {
            xMin2 = xMin;
        }
        double m = ((Double) map.get("b")).doubleValue();
        double b = ((Double) map.get("a")).doubleValue();
        float[] result = new float[(i * 4)];
        float lastX = Float.NEGATIVE_INFINITY;
        float lastY = Float.NEGATIVE_INFINITY;
        boolean first = true;
        for (int i2 = 0; i2 < i; i2++) {
            if (first) {
                first = false;
                lastX = xMin2 + ((((float) i2) * (xMax - xMin2)) / ((float) i));
                lastY = (float) ((Math.log((double) lastX) * m) + b);
            }
            result[i2 * 4] = lastX;
            result[(i2 * 4) + 1] = lastY;
            float newX = ((((float) (i2 + 1)) * (xMax - xMin2)) / ((float) i)) + xMin2;
            float newY = (float) ((Math.log((double) newX) * m) + b);
            result[(i2 * 4) + 2] = newX;
            result[(i2 * 4) + 3] = newY;
            lastX = newX;
            lastY = newY;
        }
        return result;
    }
}
