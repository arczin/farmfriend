package com.google.appinventor.components.runtime.util;

import android.util.Log;
import com.google.appinventor.components.runtime.collect.Lists;
import com.google.appinventor.components.runtime.errors.DispatchableError;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import gnu.lists.FString;
import gnu.lists.LList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;

public class YailDictionary extends LinkedHashMap<Object, Object> implements YailObject<YailList> {
    public static final Object ALL = new Object() {
        public String toString() {
            return "ALL_ITEMS";
        }
    };
    private static final KeyTransformer IDENTITY = new KeyTransformer() {
        public Object transform(Object key) {
            return key;
        }
    };
    private static final String LOG_TAG = "YailDictionary";
    private final KeyTransformer keyTransformer;

    public interface KeyTransformer {
        Object transform(Object obj);
    }

    public YailDictionary() {
        this.keyTransformer = IDENTITY;
    }

    public YailDictionary(Map<?, ?> prevMap) {
        this(prevMap, IDENTITY);
    }

    public YailDictionary(Map<?, ?> prevMap, KeyTransformer keyTransformer2) {
        this.keyTransformer = keyTransformer2;
        for (Map.Entry<?, ?> entry : prevMap.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public static YailDictionary makeDictionary() {
        return new YailDictionary();
    }

    public static YailDictionary makeDictionary(Map<Object, Object> prevMap) {
        return new YailDictionary(prevMap);
    }

    public static YailDictionary makeDictionary(Object... keysAndValues) {
        if (keysAndValues.length % 2 != 1) {
            YailDictionary dict = new YailDictionary();
            for (int i = 0; i < keysAndValues.length; i += 2) {
                dict.put(keysAndValues[i], keysAndValues[i + 1]);
            }
            return dict;
        }
        throw new IllegalArgumentException("Expected an even number of key-value entries.");
    }

    public static YailDictionary makeDictionary(List<YailList> pairs) {
        Map<Object, Object> map = new LinkedHashMap<>();
        for (YailList currentYailList : pairs) {
            map.put(currentYailList.getObject(0), currentYailList.getObject(1));
        }
        return new YailDictionary(map);
    }

    private static Boolean isAlist(YailList yailList) {
        boolean hadPair = false;
        Iterator it = ((LList) yailList.getCdr()).iterator();
        while (it.hasNext()) {
            Object currentPair = it.next();
            if (!(currentPair instanceof YailList)) {
                return false;
            }
            if (((YailList) currentPair).size() != 2) {
                return false;
            }
            hadPair = true;
        }
        return Boolean.valueOf(hadPair);
    }

    public static YailDictionary alistToDict(YailList alist) {
        YailDictionary map = new YailDictionary();
        Iterator it = ((LList) alist.getCdr()).iterator();
        while (it.hasNext()) {
            YailList currentPair = (YailList) it.next();
            Object currentKey = currentPair.getObject(0);
            Object currentValue = currentPair.getObject(1);
            if ((currentValue instanceof YailList) && isAlist((YailList) currentValue).booleanValue()) {
                map.put(currentKey, alistToDict((YailList) currentValue));
            } else if (currentValue instanceof YailList) {
                map.put(currentKey, checkList((YailList) currentValue));
            } else {
                map.put(currentKey, currentValue);
            }
        }
        return map;
    }

    private static YailList checkList(YailList list) {
        Object[] checked = new Object[list.size()];
        int i = 0;
        Iterator<?> it = list.iterator();
        it.next();
        boolean processed = false;
        while (it.hasNext()) {
            Object next = it.next();
            if (!(next instanceof YailList)) {
                checked[i] = next;
            } else if (isAlist((YailList) next).booleanValue()) {
                checked[i] = alistToDict((YailList) next);
                processed = true;
            } else {
                checked[i] = checkList((YailList) next);
                if (checked[i] != next) {
                    processed = true;
                }
            }
            i++;
        }
        if (processed) {
            return YailList.makeList((Object[]) checked);
        }
        return list;
    }

    private static YailList checkListForDicts(YailList list) {
        List<Object> copy = new ArrayList<>();
        Iterator it = ((LList) list.getCdr()).iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof YailDictionary) {
                copy.add(dictToAlist((YailDictionary) o));
            } else if (o instanceof YailList) {
                copy.add(checkListForDicts((YailList) o));
            } else {
                copy.add(o);
            }
        }
        return YailList.makeList((List) copy);
    }

    public static YailList dictToAlist(YailDictionary dict) {
        List<Object> list = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : dict.entrySet()) {
            list.add(YailList.makeList(new Object[]{entry.getKey(), entry.getValue()}));
        }
        return YailList.makeList((List) list);
    }

    public void setPair(YailList pair) {
        put(pair.getObject(0), pair.getObject(1));
    }

    private Object getFromList(List<?> target, Object currentKey) {
        int offset = (target instanceof YailList) ^ 1;
        try {
            if (currentKey instanceof FString) {
                return target.get(Integer.parseInt(currentKey.toString()) - offset);
            }
            if (currentKey instanceof String) {
                return target.get(Integer.parseInt((String) currentKey) - offset);
            }
            if (currentKey instanceof Number) {
                return target.get(((Number) currentKey).intValue() - ((int) offset));
            }
            return null;
        } catch (NumberFormatException e) {
            Log.w(LOG_TAG, "Unable to parse key as integer: " + currentKey, e);
            throw new YailRuntimeError("Unable to parse key as integer: " + currentKey, "NumberParseException");
        } catch (IndexOutOfBoundsException e2) {
            Log.w(LOG_TAG, "Requested too large of an index: " + currentKey, e2);
            throw new YailRuntimeError("Requested too large of an index: " + currentKey, "IndexOutOfBoundsException");
        }
    }

    public Object getObjectAtKeyPath(List<?> keysOrIndices) {
        Object target;
        Object target2 = this;
        for (Object currentKey : keysOrIndices) {
            if (target2 instanceof Map) {
                target = ((Map) target2).get(currentKey);
            } else if ((target2 instanceof YailList) && isAlist((YailList) target2).booleanValue()) {
                target = alistToDict((YailList) target2).get(currentKey);
            } else if (!(target2 instanceof List)) {
                return null;
            } else {
                target = getFromList((List) target2, currentKey);
            }
            target2 = target;
        }
        return target2;
    }

    private static Collection<Object> allOf(Map<Object, Object> map) {
        return map.values();
    }

    private static Collection<Object> allOf(List<Object> list) {
        if (!(list instanceof YailList)) {
            return list;
        }
        if (!isAlist((YailList) list).booleanValue()) {
            return (Collection) ((YailList) list).getCdr();
        }
        ArrayList<Object> result = new ArrayList<>();
        Iterator it = ((LList) ((YailList) list).getCdr()).iterator();
        while (it.hasNext()) {
            result.add(((YailList) it.next()).getObject(1));
        }
        return result;
    }

    private static Collection<Object> allOf(Object object) {
        if (object instanceof Map) {
            return allOf((Map<Object, Object>) (Map) object);
        }
        if (object instanceof List) {
            return allOf((List<Object>) (List) object);
        }
        return Collections.emptyList();
    }

    private static Object alistLookup(YailList alist, Object target) {
        Iterator it = ((LList) alist.getCdr()).iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (!(o instanceof YailList)) {
                return null;
            }
            if (((YailList) o).getObject(0).equals(target)) {
                return ((YailList) o).getObject(1);
            }
        }
        return null;
    }

    private static <T> List<Object> walkKeyPath(Object root, List<T> keysOrIndices, List<Object> result) {
        if (keysOrIndices.isEmpty()) {
            if (root != null) {
                result.add(root);
            }
            return result;
        } else if (root == null) {
            return result;
        } else {
            Object currentKey = keysOrIndices.get(0);
            List<T> childKeys = keysOrIndices.subList(1, keysOrIndices.size());
            if (currentKey == ALL) {
                for (Object child : allOf(root)) {
                    walkKeyPath(child, childKeys, result);
                }
            } else if (root instanceof Map) {
                walkKeyPath(((Map) root).get(currentKey), childKeys, result);
            } else if ((root instanceof YailList) && isAlist((YailList) root).booleanValue()) {
                Object value = alistLookup((YailList) root, currentKey);
                if (value != null) {
                    walkKeyPath(value, childKeys, result);
                }
            } else if (root instanceof List) {
                try {
                    walkKeyPath(((List) root).get(keyToIndex((List) root, currentKey)), childKeys, result);
                } catch (Exception e) {
                }
            }
            return result;
        }
    }

    public static <T> List<Object> walkKeyPath(YailObject<?> object, List<T> keysOrIndices) {
        return walkKeyPath(object, keysOrIndices, new ArrayList());
    }

    private static int keyToIndex(List<?> target, Object key) {
        int index;
        int offset = (target instanceof YailList) ^ 1;
        if (key instanceof Number) {
            index = ((Number) key).intValue();
        } else {
            try {
                index = Integer.parseInt(key.toString());
            } catch (NumberFormatException e) {
                throw new DispatchableError(ErrorMessages.ERROR_NUMBER_FORMAT_EXCEPTION, key.toString());
            }
        }
        int index2 = index - ((int) offset);
        if (index2 >= 0 && index2 < (target.size() + 1) - offset) {
            return index2;
        }
        try {
            throw new DispatchableError(ErrorMessages.ERROR_INDEX_MISSING_IN_LIST, Integer.valueOf(index2 + offset), JsonUtil.getJsonRepresentation(target));
        } catch (JSONException e2) {
            Log.e(LOG_TAG, "Unable to serialize object as JSON", e2);
            throw new YailRuntimeError(e2.getMessage(), "JSON Error");
        }
    }

    private Object lookupTargetForKey(Object target, Object key) {
        if (target instanceof YailDictionary) {
            return ((YailDictionary) target).get(key);
        }
        if (target instanceof List) {
            return ((List) target).get(keyToIndex((List) target, key));
        }
        throw new DispatchableError(ErrorMessages.ERROR_INVALID_VALUE_IN_PATH, target == null ? "null" : target.getClass().getSimpleName());
    }

    public void setValueForKeyPath(List<?> keys, Object value) {
        Object target;
        Object target2 = this;
        Iterator<?> it = keys.iterator();
        if (!keys.isEmpty()) {
            while (it.hasNext()) {
                Object key = it.next();
                if (it.hasNext()) {
                    target = lookupTargetForKey(target2, key);
                } else if (target2 instanceof YailDictionary) {
                    ((YailDictionary) target2).put(key, value);
                    target = target2;
                } else if (target2 instanceof YailList) {
                    ((LList) target2).getIterator(keyToIndex((List) target2, key)).set(value);
                    target = target2;
                } else if (target2 instanceof List) {
                    ((List) target2).set(keyToIndex((List) target2, key), value);
                    target = target2;
                } else {
                    throw new DispatchableError(ErrorMessages.ERROR_INVALID_VALUE_IN_PATH);
                }
                target2 = target;
            }
        }
    }

    public boolean containsKey(Object key) {
        if (key instanceof FString) {
            key = key.toString();
        }
        return super.containsKey(this.keyTransformer.transform(key));
    }

    public boolean containsValue(Object value) {
        if (value instanceof FString) {
            return super.containsValue(value.toString());
        }
        return super.containsValue(value);
    }

    public Object get(Object key) {
        if (key instanceof FString) {
            key = key.toString();
        }
        return super.get(this.keyTransformer.transform(key));
    }

    public Object put(Object key, Object value) {
        if (key instanceof FString) {
            key = key.toString();
        }
        Object key2 = this.keyTransformer.transform(key);
        if (value instanceof FString) {
            value = value.toString();
        }
        return super.put(key2, value);
    }

    public Object remove(Object key) {
        if (key instanceof FString) {
            key = key.toString();
        }
        return super.remove(this.keyTransformer.transform(key));
    }

    public String toString() {
        try {
            return JsonUtil.getJsonRepresentation(this);
        } catch (JSONException e) {
            throw new YailRuntimeError(e.getMessage(), "JSON Error");
        }
    }

    public Object getObject(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        int i = index;
        for (Map.Entry<Object, Object> e : entrySet()) {
            if (i == 0) {
                return Lists.newArrayList(e.getKey(), e.getValue());
            }
            i--;
        }
        throw new IndexOutOfBoundsException();
    }

    public Iterator<YailList> iterator() {
        return new DictIterator(entrySet().iterator());
    }

    private static class DictIterator implements Iterator<YailList> {
        final Iterator<Map.Entry<Object, Object>> it;

        DictIterator(Iterator<Map.Entry<Object, Object>> it2) {
            this.it = it2;
        }

        public boolean hasNext() {
            return this.it.hasNext();
        }

        public YailList next() {
            Map.Entry<Object, Object> e = this.it.next();
            return YailList.makeList(new Object[]{e.getKey(), e.getValue()});
        }

        public void remove() {
            this.it.remove();
        }
    }
}
