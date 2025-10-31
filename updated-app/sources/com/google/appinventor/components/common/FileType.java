package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

public enum FileType implements OptionList<String> {
    Any("*/*"),
    Audio("audio/*"),
    Image("image/*"),
    Video("video/*");
    
    private static final Map<String, FileType> LOOKUP = null;
    private final String mimeType;

    static {
        int i;
        LOOKUP = new HashMap();
        for (FileType type : values()) {
            LOOKUP.put(type.toUnderlyingValue(), type);
        }
    }

    private FileType(String mimeType2) {
        this.mimeType = mimeType2;
    }

    public String toUnderlyingValue() {
        return this.mimeType;
    }

    public static FileType fromUnderlyingValue(String value) {
        return LOOKUP.get(value);
    }
}
