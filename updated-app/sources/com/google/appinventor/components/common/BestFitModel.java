package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

public enum BestFitModel implements OptionList<String> {
    Linear("Linear"),
    Quadratic("Quadratic"),
    Exponential("Exponential"),
    Logarithmic("Logarithmic");
    
    private static final Map<String, BestFitModel> lookup = null;
    private final String value;

    static {
        int i;
        lookup = new HashMap();
        for (BestFitModel model : values()) {
            lookup.put(model.toUnderlyingValue(), model);
        }
    }

    private BestFitModel(String value2) {
        this.value = value2;
    }

    public String toUnderlyingValue() {
        return this.value;
    }

    public static BestFitModel fromUnderlyingValue(String model) {
        return lookup.get(model);
    }
}
