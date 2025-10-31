package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

public enum FileAction implements OptionList<String> {
    PickExistingFile("Pick Existing File"),
    PickNewFile("Pick New File"),
    PickDirectory("Pick Directory");
    
    private static final Map<String, FileAction> LOOKUP = null;
    private final String value;

    static {
        int i;
        LOOKUP = new HashMap();
        for (FileAction action : values()) {
            LOOKUP.put(action.value, action);
        }
    }

    private FileAction(String value2) {
        this.value = value2;
    }

    public String toUnderlyingValue() {
        return this.value;
    }

    public static FileAction fromUnderlyingValue(String value2) {
        return LOOKUP.get(value2);
    }
}
