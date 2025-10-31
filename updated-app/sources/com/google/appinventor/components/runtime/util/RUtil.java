package com.google.appinventor.components.runtime.util;

import android.os.Build;
import com.google.appinventor.components.common.FileScope;
import com.google.appinventor.components.runtime.Form;

public class RUtil {
    public static boolean needsFilePermission(Form form, String path, FileScope fileScope) {
        if (fileScope != null) {
            switch (fileScope) {
                case App:
                    if (Build.VERSION.SDK_INT < 19) {
                        return true;
                    }
                    return false;
                case Asset:
                    if (!form.isRepl() || Build.VERSION.SDK_INT >= 19) {
                        return false;
                    }
                    return true;
                case Shared:
                    return true;
                default:
                    return false;
            }
        } else if (path.startsWith("//")) {
            return false;
        } else {
            if ((path.startsWith("/") || path.startsWith("file:")) && FileUtil.isExternalStorageUri(form, path) && !FileUtil.isAppSpecificExternalUri(form, path)) {
                return true;
            }
            return false;
        }
    }
}
