package br.ufsc.gqs.teachablemachineimageclassifier;

import android.os.Build;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.PermissionResultHandler;

public class Helper {
    static boolean shouldAskForPermission(Form form) {
        return form.getApplicationInfo().targetSdkVersion >= 23 && Build.VERSION.SDK_INT >= 23;
    }

    static void askForPermission(final TeachableMachineImageClassifier teachableMachineImageClassifier, final Runnable next) {
        teachableMachineImageClassifier.getForm().askPermission("android.permission.CAMERA", new PermissionResultHandler() {
            public void HandlePermissionResponse(String permission, boolean granted) {
                if (granted) {
                    next.run();
                } else {
                    teachableMachineImageClassifier.getForm().PermissionDenied(teachableMachineImageClassifier, "WebViewer", permission);
                }
            }
        });
    }
}
