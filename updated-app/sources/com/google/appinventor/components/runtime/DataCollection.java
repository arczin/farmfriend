package com.google.appinventor.components.runtime;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.DataModel;
import com.google.appinventor.components.runtime.util.CsvUtil;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SimpleObject
public abstract class DataCollection<C extends ComponentContainer, M extends DataModel<?>> implements Component, DataSource<Object, List<?>>, DataSourceChangeListener {
    protected String componentName;
    protected final C container;
    protected List<String> dataFileColumns;
    protected M dataModel;
    /* access modifiers changed from: private */
    public DataSource<?, ?> dataSource;
    protected String dataSourceKey;
    private String elements;
    private boolean initialized = false;
    /* access modifiers changed from: private */
    public Object lastDataSourceValue;
    protected final Set<DataSourceChangeListener> listeners = new HashSet();
    protected List<String> sheetsColumns;
    protected ExecutorService threadRunner;
    /* access modifiers changed from: private */
    public int tick = 0;
    protected boolean useSheetHeaders;
    protected List<String> webColumns;

    public abstract void onDataChange();

    public DataCollection(C container2) {
        this.container = container2;
        DataSourceKey("");
        this.threadRunner = Executors.newSingleThreadExecutor();
        this.dataFileColumns = Arrays.asList(new String[]{"", ""});
        this.sheetsColumns = Arrays.asList(new String[]{"", ""});
        this.webColumns = Arrays.asList(new String[]{"", ""});
    }

    public void setComponentName(String componentName2) {
        this.componentName = componentName2;
    }

    public void setExecutorService(ExecutorService service) {
        this.threadRunner = service;
    }

    public void addDataSourceChangeListener(DataSourceChangeListener listener) {
        this.listeners.add(listener);
        listener.onDataSourceValueChange(this, (String) null, (Object) null);
    }

    public void removeDataSourceChangeListener(DataSourceChangeListener listener) {
        this.listeners.remove(listener);
    }

    public List<?> getDataValue(Object key) {
        return this.dataModel.entries;
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = false)
    public void ElementsFromPairs(final String elements2) {
        this.elements = elements2;
        if (elements2 != null && !elements2.equals("") && this.initialized) {
            this.threadRunner.execute(new Runnable() {
                public void run() {
                    DataCollection.this.dataModel.setElements(elements2);
                    DataCollection.this.onDataChange();
                }
            });
        }
    }

    @DesignerProperty(editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = false)
    public void SpreadsheetUseHeaders(boolean useHeaders) {
        this.useSheetHeaders = useHeaders;
    }

    @DesignerProperty(editorType = "data_file_column")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = false)
    public void DataFileXColumn(String column) {
        this.dataFileColumns.set(0, column);
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Sets the column to parse from the attached Web component for the x values. If a column is not specified, default values for the x values will be generated instead.", userVisible = false)
    public void WebXColumn(String column) {
        this.webColumns.set(0, column);
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = false)
    public void SpreadsheetXColumn(String column) {
        this.sheetsColumns.set(0, column);
    }

    @DesignerProperty(editorType = "data_file_column")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = false)
    public void DataFileYColumn(String column) {
        this.dataFileColumns.set(1, column);
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Sets the column to parse from the attached Web component for the y values. If a column is not specified, default values for the y values will be generated instead.", userVisible = false)
    public void WebYColumn(String column) {
        this.webColumns.set(1, column);
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = false)
    public void SpreadsheetYColumn(String column) {
        this.sheetsColumns.set(1, column);
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = false)
    public void DataSourceKey(String key) {
        this.dataSourceKey = key;
    }

    @DesignerProperty(editorType = "chart_data_source")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = false)
    public <K, V> void Source(DataSource<K, V> dataSource2) {
        if (this.dataSource != dataSource2 && (this.dataSource instanceof ObservableDataSource)) {
            ((ObservableDataSource) this.dataSource).removeDataObserver(this);
        }
        this.dataSource = dataSource2;
        if (this.initialized) {
            if (dataSource2 instanceof ObservableDataSource) {
                ((ObservableDataSource) dataSource2).addDataObserver(this);
                if (this.dataSourceKey == null) {
                    return;
                }
            }
            if (dataSource2 instanceof DataFile) {
                importFromDataFileAsync((DataFile) dataSource2, YailList.makeList((List) this.dataFileColumns));
            } else if (dataSource2 instanceof TinyDB) {
                ImportFromTinyDB((TinyDB) dataSource2, this.dataSourceKey);
            } else if (dataSource2 instanceof CloudDB) {
                ImportFromCloudDB((CloudDB) dataSource2, this.dataSourceKey);
            } else if (dataSource2 instanceof Spreadsheet) {
                importFromSpreadsheetAsync((Spreadsheet) dataSource2, YailList.makeList((List) this.sheetsColumns), this.useSheetHeaders);
            } else if (dataSource2 instanceof Web) {
                importFromWebAsync((Web) dataSource2, YailList.makeList((List) this.webColumns));
            }
        }
    }

    @SimpleFunction
    public void ImportFromList(final YailList list) {
        this.threadRunner.execute(new Runnable() {
            public void run() {
                DataCollection.this.dataModel.importFromList(list);
                DataCollection.this.onDataChange();
            }
        });
    }

    @SimpleFunction(description = "Clears all of the data.")
    public void Clear() {
        this.threadRunner.execute(new Runnable() {
            public void run() {
                DataCollection.this.dataModel.clearEntries();
                DataCollection.this.onDataChange();
            }
        });
    }

    @SimpleFunction
    public <K, V> void ChangeDataSource(final DataSource<K, V> source, final String keyValue) {
        this.threadRunner.execute(new Runnable() {
            public void run() {
                List<String> columnsList;
                if ((source instanceof DataFile) || (source instanceof Web)) {
                    YailList keyValues = new YailList();
                    try {
                        keyValues = CsvUtil.fromCsvRow(keyValue);
                    } catch (Exception e) {
                        Log.e(getClass().getName(), e.getMessage());
                    }
                    if (source instanceof DataFile) {
                        columnsList = DataCollection.this.dataFileColumns;
                    } else if (source instanceof Spreadsheet) {
                        columnsList = DataCollection.this.sheetsColumns;
                    } else if (source instanceof Web) {
                        columnsList = DataCollection.this.webColumns;
                    } else {
                        throw new IllegalArgumentException(source + " is not an expected DataSource");
                    }
                    for (int i = 0; i < columnsList.size(); i++) {
                        String columnValue = "";
                        if (keyValues.size() > i) {
                            columnValue = keyValues.getString(i);
                        }
                        columnsList.set(i, columnValue);
                    }
                } else {
                    DataCollection.this.dataSourceKey = keyValue;
                }
                DataCollection.this.lastDataSourceValue = null;
                DataCollection.this.Source(source);
            }
        });
    }

    @SimpleFunction(description = "Un-links the currently associated Data Source component from the Chart.")
    public void RemoveDataSource() {
        this.threadRunner.execute(new Runnable() {
            public void run() {
                DataCollection.this.Source((DataSource) null);
                DataCollection.this.dataSourceKey = "";
                DataCollection.this.lastDataSourceValue = null;
                for (int i = 0; i < DataCollection.this.dataFileColumns.size(); i++) {
                    DataCollection.this.dataFileColumns.set(i, "");
                    DataCollection.this.sheetsColumns.set(i, "");
                    DataCollection.this.webColumns.set(i, "");
                }
            }
        });
    }

    @SimpleFunction(description = "Returns a List of entries with x values matching the specified x value. A single entry is represented as a List of values of the entry.")
    public YailList GetEntriesWithXValue(final String x) {
        try {
            return (YailList) this.threadRunner.submit(new Callable<YailList>() {
                public YailList call() {
                    return DataCollection.this.dataModel.findEntriesByCriterion(x, DataModel.EntryCriterion.XValue);
                }
            }).get();
        } catch (InterruptedException e) {
            Log.e(getClass().getName(), e.getMessage());
            return new YailList();
        } catch (ExecutionException e2) {
            Log.e(getClass().getName(), e2.getMessage());
            return new YailList();
        }
    }

    @SimpleFunction(description = "Returns a List of entries with y values matching the specified y value. A single entry is represented as a List of values of the entry.")
    public YailList GetEntriesWithYValue(final String y) {
        try {
            return (YailList) this.threadRunner.submit(new Callable<YailList>() {
                public YailList call() {
                    return DataCollection.this.dataModel.findEntriesByCriterion(y, DataModel.EntryCriterion.YValue);
                }
            }).get();
        } catch (InterruptedException e) {
            Log.e(getClass().getName(), e.getMessage());
            return new YailList();
        } catch (ExecutionException e2) {
            Log.e(getClass().getName(), e2.getMessage());
            return new YailList();
        }
    }

    @SimpleFunction(description = "Returns all the entries of the Data Series. A single entry is represented as a List of values of the entry.")
    public YailList GetAllEntries() {
        try {
            return (YailList) this.threadRunner.submit(new Callable<YailList>() {
                public YailList call() {
                    return DataCollection.this.dataModel.getEntriesAsTuples();
                }
            }).get();
        } catch (InterruptedException e) {
            Log.e(getClass().getName(), e.getMessage());
            return new YailList();
        } catch (ExecutionException e2) {
            Log.e(getClass().getName(), e2.getMessage());
            return new YailList();
        }
    }

    @SimpleFunction
    public void ImportFromTinyDB(TinyDB tinyDB, String tag) {
        final List<?> list = tinyDB.getDataValue(tag);
        updateCurrentDataSourceValue(tinyDB, tag, list);
        this.threadRunner.execute(new Runnable() {
            public void run() {
                DataCollection.this.dataModel.importFromList(list);
                DataCollection.this.onDataChange();
            }
        });
    }

    @SimpleFunction
    public void ImportFromCloudDB(final CloudDB cloudDB, final String tag) {
        final Future<YailList> list = cloudDB.getDataValue(tag);
        this.threadRunner.execute(new Runnable() {
            public void run() {
                try {
                    YailList listValue = (YailList) list.get();
                    DataCollection.this.updateCurrentDataSourceValue(cloudDB, tag, listValue);
                    DataCollection.this.dataModel.importFromList(listValue);
                    DataCollection.this.onDataChange();
                } catch (InterruptedException e) {
                    Log.e(getClass().getName(), e.getMessage());
                } catch (ExecutionException e2) {
                    Log.e(getClass().getName(), e2.getMessage());
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void importFromDataFileAsync(DataFile dataFile, YailList columns) {
        final Future<YailList> dataFileColumns2 = dataFile.getDataValue(columns);
        this.threadRunner.execute(new Runnable() {
            public void run() {
                YailList dataResult = null;
                try {
                    dataResult = (YailList) dataFileColumns2.get();
                } catch (InterruptedException e) {
                    Log.e(getClass().getName(), e.getMessage());
                } catch (ExecutionException e2) {
                    Log.e(getClass().getName(), e2.getMessage());
                }
                DataCollection.this.dataModel.importFromColumns(dataResult, true);
                DataCollection.this.onDataChange();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void importFromSpreadsheetAsync(final Spreadsheet sheets, YailList columns, final boolean useHeaders) {
        final Future<YailList> sheetColumns = sheets.getDataValue(columns, useHeaders);
        this.threadRunner.execute(new Runnable() {
            public void run() {
                YailList dataColumns = null;
                try {
                    dataColumns = (YailList) sheetColumns.get();
                } catch (InterruptedException e) {
                    Log.e(getClass().getName(), e.getMessage());
                } catch (ExecutionException e2) {
                    Log.e(getClass().getName(), e2.getMessage());
                }
                if (sheets == DataCollection.this.dataSource) {
                    DataCollection.this.updateCurrentDataSourceValue(DataCollection.this.dataSource, (String) null, (Object) null);
                }
                DataCollection.this.dataModel.importFromColumns(dataColumns, useHeaders);
                DataCollection.this.onDataChange();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void importFromWebAsync(final Web webComponent, YailList columns) {
        final Future<YailList> webColumns2 = webComponent.getDataValue(columns);
        this.threadRunner.execute(new Runnable() {
            public void run() {
                YailList dataColumns = null;
                try {
                    dataColumns = (YailList) webColumns2.get();
                } catch (InterruptedException e) {
                    Log.e(getClass().getName(), e.getMessage());
                } catch (ExecutionException e2) {
                    Log.e(getClass().getName(), e2.getMessage());
                }
                if (webComponent == DataCollection.this.dataSource) {
                    DataCollection.this.updateCurrentDataSourceValue(DataCollection.this.dataSource, (String) null, (Object) null);
                }
                DataCollection.this.dataModel.importFromColumns(dataColumns, true);
                DataCollection.this.onDataChange();
            }
        });
    }

    @SimpleFunction
    public void ImportFromDataFile(DataFile dataFile, String xValueColumn, String yValueColumn) {
        importFromDataFileAsync(dataFile, YailList.makeList(Arrays.asList(new String[]{xValueColumn, yValueColumn})));
    }

    @SimpleFunction
    public void ImportFromSpreadsheet(Spreadsheet spreadsheet, String xColumn, String yColumn, boolean useHeaders) {
        importFromSpreadsheetAsync(spreadsheet, YailList.makeList(Arrays.asList(new String[]{xColumn, yColumn})), useHeaders);
    }

    @SimpleFunction(description = "Imports data from the specified Web component, given the names of the X and Y value columns. Empty columns are filled with default values (1, 2, 3, ... for Entry 1, 2, ...). In order for the data importing to be successful, to load the data, and then this block should be used on that Web component. The usage of the gotValue event in the Web component is unnecessary.")
    public void ImportFromWeb(Web web, String xValueColumn, String yValueColumn) {
        importFromWebAsync(web, YailList.makeList(Arrays.asList(new String[]{xValueColumn, yValueColumn})));
    }

    public void Initialize() {
        this.initialized = true;
        if (this.dataSource != null) {
            Source(this.dataSource);
        } else {
            ElementsFromPairs(this.elements);
        }
    }

    public HandlesEventDispatching getDispatchDelegate() {
        return this.container.$form();
    }

    public void onDataSourceValueChange(final DataSource<?, ?> component, final String key, final Object newValue) {
        if (component == this.dataSource && isKeyValid(key)) {
            this.threadRunner.execute(new Runnable() {
                public void run() {
                    if (DataCollection.this.lastDataSourceValue instanceof List) {
                        DataCollection.this.dataModel.removeValues((List) DataCollection.this.lastDataSourceValue);
                    }
                    DataCollection.this.updateCurrentDataSourceValue(component, key, newValue);
                    if (DataCollection.this.lastDataSourceValue instanceof List) {
                        DataCollection.this.dataModel.importFromList((List) DataCollection.this.lastDataSourceValue);
                    }
                    DataCollection.this.onDataChange();
                }
            });
        }
    }

    public void onReceiveValue(RealTimeDataSource<?, ?> component, String key, Object value) {
        boolean importData;
        if (component == this.dataSource) {
            if (component instanceof BluetoothClient) {
                String valueString = (String) value;
                importData = valueString.startsWith(this.dataSourceKey);
                if (importData) {
                    value = valueString.substring(this.dataSourceKey.length());
                }
            } else {
                importData = isKeyValid(key);
            }
            if (importData) {
                final Object finalValue = value;
                this.container.$context().runOnUiThread(new Runnable() {
                    public void run() {
                        if (DataCollection.this.container instanceof Chart) {
                            DataCollection.this.tick = ((Chart) DataCollection.this.container).getSyncedTValue(DataCollection.this.tick);
                            DataCollection.this.dataModel.addTimeEntry(YailList.makeList(Arrays.asList(new Object[]{Integer.valueOf(DataCollection.this.tick), finalValue})));
                            DataCollection.this.onDataChange();
                            DataCollection dataCollection = DataCollection.this;
                            dataCollection.tick = dataCollection.tick + 1;
                        }
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateCurrentDataSourceValue(DataSource<?, ?> source, String key, Object newValue) {
        if (source == this.dataSource && isKeyValid(key)) {
            if (source instanceof Web) {
                this.lastDataSourceValue = this.dataModel.getTuplesFromColumns(((Web) source).getColumns(YailList.makeList((List) this.webColumns)), true);
            } else if (source instanceof Spreadsheet) {
                this.lastDataSourceValue = this.dataModel.getTuplesFromColumns(((Spreadsheet) source).getColumns(YailList.makeList((List) this.sheetsColumns), this.useSheetHeaders), this.useSheetHeaders);
            } else {
                this.lastDataSourceValue = newValue;
            }
        }
    }

    private boolean isKeyValid(String key) {
        return key == null || key.equals(this.dataSourceKey);
    }

    public static List<Double> castToDouble(List<?> list) {
        List<Double> listDoubles = new ArrayList<>();
        for (Object next : list) {
            if (next instanceof Number) {
                listDoubles.add(Double.valueOf(((Number) next).doubleValue()));
            } else {
                try {
                    listDoubles.add(Double.valueOf(Double.parseDouble(next.toString())));
                } catch (NumberFormatException e) {
                }
            }
        }
        return listDoubles;
    }
}
