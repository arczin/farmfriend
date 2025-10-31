package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;

@SimpleObject
public abstract class VisibleComponent implements Component {
    protected String componentName;

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public abstract int Height();

    @SimpleProperty
    public abstract void Height(int i);

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public abstract void HeightPercent(int i);

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public abstract int Width();

    @SimpleProperty
    public abstract void Width(int i);

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public abstract void WidthPercent(int i);

    protected VisibleComponent() {
    }

    public void setComponentName(String componentName2) {
        this.componentName = componentName2;
    }
}
