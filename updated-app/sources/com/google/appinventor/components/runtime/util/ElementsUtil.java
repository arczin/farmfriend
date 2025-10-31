package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElementsUtil {
    public static YailList elementsFromString(String itemString) {
        YailList items = new YailList();
        if (itemString.length() > 0) {
            return YailList.makeList((Object[]) itemString.split(" *, *"));
        }
        return items;
    }

    public static List<String> elementsStrings(YailList itemList, String componentName) {
        String[] strings = itemList.toStringArray();
        int i = 0;
        while (i < strings.length) {
            if (strings[i] instanceof String) {
                i++;
            } else {
                throw new YailRuntimeError("Items passed to " + componentName + " must be Strings", "Error");
            }
        }
        return new ArrayList<>(Arrays.asList(strings));
    }

    public static List<String> elementsListFromString(String itemString) {
        if (itemString.length() > 0) {
            return new ArrayList<>(Arrays.asList(itemString.split(" *, *")));
        }
        return new ArrayList<>();
    }

    public static YailList makeYailListFromList(List<String> stringItems) {
        if (stringItems == null || stringItems.size() == 0) {
            return YailList.makeEmptyList();
        }
        return YailList.makeList((List) stringItems);
    }

    public static int selectionIndexInStringList(int index, List<String> items) {
        if (index < 1 || index > items.size()) {
            return 0;
        }
        return index;
    }

    public static String setSelectionFromIndexInStringList(int index, List<String> items) {
        if (index < 1 || index > items.size()) {
            return "";
        }
        return items.get(index - 1);
    }

    public static int setSelectedIndexFromValueInStringList(String value, List<String> items) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(value)) {
                return i + 1;
            }
        }
        return 0;
    }

    public static YailList elements(YailList itemList, String componentName) {
        Object[] objects = itemList.toStringArray();
        int i = 0;
        while (i < objects.length) {
            if (objects[i] instanceof String) {
                i++;
            } else {
                throw new YailRuntimeError("Items passed to " + componentName + " must be Strings", "Error");
            }
        }
        return itemList;
    }

    public static int selectionIndex(int index, YailList items) {
        if (index <= 0 || index > items.size()) {
            return 0;
        }
        return index;
    }

    public static String setSelectionFromIndex(int index, YailList items) {
        if (index == 0 || index > items.size()) {
            return "";
        }
        return items.getString(index - 1);
    }

    public static int setSelectedIndexFromValue(String value, YailList items) {
        for (int i = 0; i < items.size(); i++) {
            if (items.getString(i).equals(value)) {
                return i + 1;
            }
        }
        return 0;
    }

    public static String toStringEmptyIfNull(Object o) {
        return o == null ? "" : o.toString();
    }
}
