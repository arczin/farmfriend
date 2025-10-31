package com.google.appinventor.components.runtime;

import com.github.mikephil.charting.data.Entry;
import com.google.appinventor.components.runtime.util.ChartDataSourceUtil;
import com.google.appinventor.components.runtime.util.YailList;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.mapping.Symbol;
import java.util.ArrayList;
import java.util.List;

public abstract class DataModel<E> {
    protected List<E> entries = new ArrayList();
    protected int maximumTimeEntries = HttpRequestContext.HTTP_OK;

    public enum EntryCriterion {
        All,
        XValue,
        YValue
    }

    public abstract void addEntryFromTuple(YailList yailList);

    public abstract void addTimeEntry(YailList yailList);

    public abstract void clearEntries();

    public abstract boolean doesEntryExist(YailList yailList);

    public abstract YailList findEntriesByCriterion(String str, EntryCriterion entryCriterion);

    public abstract int findEntryIndex(Entry entry);

    public abstract List<E> getEntries();

    public abstract Entry getEntryFromTuple(YailList yailList);

    public abstract YailList getTupleFromEntry(Entry entry);

    /* access modifiers changed from: protected */
    public abstract int getTupleSize();

    /* access modifiers changed from: protected */
    public abstract boolean isEntryCriterionSatisfied(Entry entry, EntryCriterion entryCriterion, String str);

    public abstract void removeEntry(int i);

    public abstract void removeEntryFromTuple(YailList yailList);

    protected DataModel() {
    }

    public void setElements(String elements) {
        int tupleSize = getTupleSize();
        String[] entries2 = elements.split(",");
        for (int i = tupleSize - 1; i < entries2.length; i += tupleSize) {
            List<String> tupleEntries = new ArrayList<>();
            for (int j = tupleSize - 1; j >= 0; j--) {
                tupleEntries.add(entries2[i - j]);
            }
            addEntryFromTuple(YailList.makeList((List) tupleEntries));
        }
    }

    public void importFromList(List<?> list) {
        for (Object next : list) {
            YailList tuple = null;
            if (next instanceof YailList) {
                tuple = (YailList) next;
            } else if (next instanceof List) {
                tuple = YailList.makeList((List) next);
            }
            if (tuple != null) {
                addEntryFromTuple(tuple);
            }
        }
    }

    public void removeValues(List<?> values) {
        for (Object next : values) {
            YailList tuple = null;
            if (next instanceof YailList) {
                tuple = (YailList) next;
            } else if (next instanceof List) {
                tuple = YailList.makeList((List) next);
            } else if (next instanceof Symbol) {
            }
            if (tuple != null) {
                removeEntryFromTuple(tuple);
            }
        }
    }

    public void importFromColumns(YailList columns, boolean hasHeaders) {
        importFromList(getTuplesFromColumns(columns, hasHeaders));
    }

    public YailList getTuplesFromColumns(YailList columns, boolean hasHeaders) {
        int rows = ChartDataSourceUtil.determineMaximumListSize(columns);
        List<YailList> tuples = new ArrayList<>();
        for (int i = hasHeaders; i < rows; i++) {
            ArrayList<String> tupleElements = new ArrayList<>();
            for (int j = 0; j < columns.size(); j++) {
                Object value = columns.getObject(j);
                if (!(value instanceof YailList)) {
                    tupleElements.add(getDefaultValue(i - 1));
                } else {
                    YailList column = (YailList) value;
                    if (column.size() > i) {
                        tupleElements.add(column.getString(i));
                    } else if (column.size() == 0) {
                        tupleElements.add(getDefaultValue(i - 1));
                    } else {
                        tupleElements.add("");
                    }
                }
            }
            tuples.add(YailList.makeList((List) tupleElements));
        }
        return YailList.makeList((List) tuples);
    }

    public YailList getEntriesAsTuples() {
        return findEntriesByCriterion(Component.TYPEFACE_DEFAULT, EntryCriterion.All);
    }

    public void setMaximumTimeEntries(int entries2) {
        this.maximumTimeEntries = entries2;
    }

    /* access modifiers changed from: protected */
    public void setDefaultStylingProperties() {
    }

    /* access modifiers changed from: protected */
    public String getDefaultValue(int index) {
        return new StringBuilder().append(index).toString();
    }

    /* access modifiers changed from: protected */
    public boolean areEntriesEqual(Entry e1, Entry e2) {
        return e1.equalTo(e2);
    }
}
