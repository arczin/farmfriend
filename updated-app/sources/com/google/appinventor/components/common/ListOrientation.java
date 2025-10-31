package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

public enum ListOrientation implements OptionList<Integer> {
    Vertical(0),
    Horizontal(1);
    
    private static final Map<Integer, ListOrientation> lookup = null;
    private final int orientation;

    static {
        int i;
        lookup = new HashMap();
        for (ListOrientation value : values()) {
            lookup.put(value.toUnderlyingValue(), value);
        }
    }

    private ListOrientation(int value) {
        this.orientation = value;
    }

    public Integer toUnderlyingValue() {
        return Integer.valueOf(this.orientation);
    }

    public static ListOrientation fromUnderlyingValue(Integer value) {
        return lookup.get(value);
    }

    public static ListOrientation fromUnderlyingValue(String value) {
        return fromUnderlyingValue(Integer.valueOf(Integer.parseInt(value)));
    }
}
