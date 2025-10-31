package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

public enum LayoutType implements OptionList<Integer> {
    MainText(0),
    MainText_DetailText_Vertical(1),
    MainText_DetailText_Horizontal(2),
    Image_MainText(3),
    Image_MainText_DetailText_Vertical(4),
    ImageTop_MainText_DetailText(5);
    
    private static final Map<Integer, LayoutType> lookup = null;
    private final int layout;

    static {
        int i;
        lookup = new HashMap();
        for (LayoutType value : values()) {
            lookup.put(value.toUnderlyingValue(), value);
        }
    }

    private LayoutType(int value) {
        this.layout = value;
    }

    public Integer toUnderlyingValue() {
        return Integer.valueOf(this.layout);
    }

    public static LayoutType fromUnderlyingValue(Integer value) {
        return lookup.get(value);
    }
}
