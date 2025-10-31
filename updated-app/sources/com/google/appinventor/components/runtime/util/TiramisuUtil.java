package com.google.appinventor.components.runtime.util;

import android.os.Build;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.PermissionResultHandler;

public class TiramisuUtil {
    public static boolean requestFilePermissions(Form form, String path, String mediaPermission, PermissionResultHandler continuation) {
        String perm = null;
        if (path.startsWith("content:") || ((path.startsWith("file:") && FileUtil.needsPermission(form, path)) || FileUtil.needsReadPermission(new ScopedFile(form.DefaultFileScope(), path)))) {
            if (Build.VERSION.SDK_INT >= 33) {
                perm = mediaPermission;
            } else {
                perm = "android.permission.READ_EXTERNAL_STORAGE";
            }
        }
        if (perm == null || !form.isDeniedPermission(perm)) {
            return false;
        }
        form.askPermission(perm, continuation);
        return true;
    }

    public static boolean requestAudioPermissions(Form form, String path, PermissionResultHandler continuation) {
        return requestFilePermissions(form, path, "android.permission.READ_MEDIA_AUDIO", continuation);
    }

    public static boolean requestImagePermissions(Form form, String path, PermissionResultHandler continuation) {
        return requestFilePermissions(form, path, "android.permission.READ_MEDIA_IMAGES", continuation);
    }

    public static boolean requestVideoPermissions(Form form, String path, PermissionResultHandler continuation) {
        return requestFilePermissions(form, path, "android.permission.READ_MEDIA_VIDEO", continuation);
    }
}
