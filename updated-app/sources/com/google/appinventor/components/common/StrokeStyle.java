package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

public enum StrokeStyle implements OptionList<Integer> {
    Solid(1),
    Dashed(2),
    Dotted(3);
    
    private static final Map<Integer, StrokeStyle> lookup = null;
    private final int value;

    static {
        int i;
        lookup = new HashMap();
        for (StrokeStyle style : values()) {
            lookup.put(style.toUnderlyingValue(), style);
        }
    }

    private StrokeStyle(int value2) {
        this.value = value2;
    }

    public Integer toUnderlyingValue() {
        return Integer.valueOf(this.value);
    }

    public static StrokeStyle fromUnderlyingValue(Integer style) {
        return lookup.get(style);
    }

    public static StrokeStyle fromUnderlyingValue(String style) {
        return lookup.get(Integer.valueOf(Integer.parseInt(style)));
    }
}
