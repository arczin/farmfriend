package com.google.appinventor.components.runtime;

import android.content.Intent;
import android.net.Uri;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.Options;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.FileAction;
import com.google.appinventor.components.common.FileType;
import com.google.appinventor.components.runtime.util.ErrorMessages;

@SimpleObject
@DesignerComponent(androidMinSdk = 19, category = ComponentCategory.MEDIA, iconName = "images/filepicker.png", version = 1)
public class FilePicker extends Picker {
    private FileAction action = FileAction.PickExistingFile;
    private String mimeType = "*/*";
    private String selection = "";

    public FilePicker(ComponentContainer container) {
        super(container);
    }

    @DesignerProperty(defaultValue = "Pick Existing File", editorArgs = {"Pick Existing File", "Pick New File", "Pick Directory"}, editorType = "choices")
    @SimpleProperty
    public void Action(FileAction action2) {
        this.action = action2;
    }

    public void Action(String action2) {
        Action(FileAction.fromUnderlyingValue(action2));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public FileAction Action() {
        return this.action;
    }

    @DesignerProperty(defaultValue = "*/*", editorType = "string")
    @SimpleProperty
    public void MimeType(@Options(FileType.class) String mimeType2) {
        this.mimeType = mimeType2;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String MimeType() {
        return this.mimeType;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String Selection() {
        return this.selection;
    }

    public void resultReturned(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri uri = data.getData();
            this.container.$form().getContentResolver().takePersistableUriPermission(uri, data.getFlags() & 3);
            this.selection = uri.toString();
            AfterPicking();
            return;
        }
        this.container.$form().dispatchErrorOccurredEvent(this, "Open", ErrorMessages.ERROR_FILEPICKER_NO_URI_RETURNED, new Object[0]);
    }

    /* access modifiers changed from: protected */
    public Intent getIntent() {
        Intent intent = new Intent(actionToIntent(this.action));
        if (this.action == FileAction.PickExistingFile) {
            intent.addCategory("android.intent.category.OPENABLE");
        }
        if (this.action == FileAction.PickDirectory) {
            return Intent.createChooser(intent, "Test");
        }
        intent.setType(this.mimeType);
        int flags = 65;
        if (this.action == FileAction.PickExistingFile) {
            flags = 65 | 2;
        }
        intent.setFlags(flags);
        return intent;
    }

    private static String actionToIntent(FileAction action2) {
        switch (action2) {
            case PickExistingFile:
                return "android.intent.action.OPEN_DOCUMENT";
            case PickDirectory:
                return "android.intent.action.OPEN_DOCUMENT_TREE";
            case PickNewFile:
                return "android.intent.action.CREATE_DOCUMENT";
            default:
                throw new IllegalArgumentException("Unknown file action: " + action2);
        }
    }
}
