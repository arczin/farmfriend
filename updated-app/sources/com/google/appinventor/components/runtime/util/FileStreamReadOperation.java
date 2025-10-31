package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.common.FileScope;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class FileStreamReadOperation extends FileReadOperation {
    private static final String LOG_TAG = FileStreamReadOperation.class.getSimpleName();

    /* access modifiers changed from: protected */
    public abstract boolean process(String str);

    public FileStreamReadOperation(Form form, Component component, String method, String fileName, FileScope scope, boolean async) {
        super(form, component, method, fileName, scope, async);
    }

    /* access modifiers changed from: protected */
    public boolean process(InputStream in) throws IOException {
        boolean close = true;
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(in);
            close = process(reader);
            return close;
        } finally {
            if (close) {
                IOUtils.closeQuietly(LOG_TAG, reader);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean process(InputStreamReader reader) throws IOException {
        return process(IOUtils.readReader(reader));
    }
}
