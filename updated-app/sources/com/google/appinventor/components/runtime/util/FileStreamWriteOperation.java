package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.common.FileScope;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public abstract class FileStreamWriteOperation extends FileWriteOperation {
    private static final String LOG_TAG = FileStreamWriteOperation.class.getSimpleName();

    /* access modifiers changed from: protected */
    public abstract boolean process(OutputStreamWriter outputStreamWriter) throws IOException;

    public FileStreamWriteOperation(Form form, Component component, String method, String fileName, FileScope scope, boolean append, boolean async) {
        super(form, component, method, fileName, scope, append, async);
    }

    /* access modifiers changed from: protected */
    public final boolean process(OutputStream out) throws IOException {
        boolean close = true;
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(out);
            close = process(writer);
            return close;
        } finally {
            if (close) {
                IOUtils.closeQuietly(LOG_TAG, writer);
            }
        }
    }
}
