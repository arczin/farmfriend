package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

public enum LOBFValues implements OptionList<String> {
    CorrCoef("correlation coefficient"),
    Slope("slope"),
    Yintercept("Yintercept"),
    Predictions("predictions"),
    AllValues("all values"),
    QuadraticCoefficient("Quadratic Coefficient"),
    LinearCoefficient("slope"),
    ExponentialCoefficient("a"),
    ExponentialBase("b"),
    LogarithmCoefficient("b"),
    LogarithmConstant("a"),
    XIntercepts("Xintercepts"),
    RSquared("r^2");
    
    private static final Map<String, LOBFValues> lookup = null;
    private final String lobfValues;

    static {
        int i;
        lookup = new HashMap();
        for (LOBFValues value : values()) {
            lookup.put(value.toUnderlyingValue(), value);
        }
    }

    private LOBFValues(String lobfV) {
        this.lobfValues = lobfV;
    }

    public String toUnderlyingValue() {
        return this.lobfValues;
    }

    public static LOBFValues fromUnderlyingValue(String value) {
        return lookup.get(value);
    }
}
