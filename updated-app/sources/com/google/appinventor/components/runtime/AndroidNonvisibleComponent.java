package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.SimpleObject;

@SimpleObject
public abstract class AndroidNonvisibleComponent implements Component {
    protected String componentName;
    /* access modifiers changed from: protected */
    public final Form form;

    protected AndroidNonvisibleComponent(Form form2) {
        this.form = form2;
    }

    public HandlesEventDispatching getDispatchDelegate() {
        return this.form;
    }

    public void setComponentName(String componentName2) {
        this.componentName = componentName2;
    }
}
