package com.google.appinventor.components.runtime.util;

import android.os.Build;
import android.util.Log;
import androidx.appcompat.widget.ActivityChooserView;
import java.util.HashMap;
import java.util.Map;

public class PermissionRegistry {
    private static final String LOG_TAG = PermissionRegistry.class.getSimpleName();
    private final Map<String, SdkConstraint> permissions = new HashMap();

    private static class SdkConstraint {
        /* access modifiers changed from: private */
        public final int maxSdk;
        /* access modifiers changed from: private */
        public final int minSdk;

        SdkConstraint(int minSdk2, int maxSdk2) {
            this.minSdk = minSdk2;
            this.maxSdk = maxSdk2;
        }

        static SdkConstraint minSdk(int minSdk2) {
            return new SdkConstraint(minSdk2, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        }

        static SdkConstraint maxSdk(int maxSdk2) {
            return new SdkConstraint(1, maxSdk2);
        }
    }

    public PermissionRegistry record(String permission, int minSdk, int maxSdk) {
        if (this.permissions.containsKey(permission)) {
            Log.w(LOG_TAG, "Overwriting permission rules for " + permission);
        }
        this.permissions.put(permission, new SdkConstraint(minSdk, maxSdk));
        return this;
    }

    public PermissionRegistry recordMinSdk(String permission, int minSdk) {
        if (this.permissions.containsKey(permission)) {
            this.permissions.put(permission, new SdkConstraint(minSdk, this.permissions.get(permission).maxSdk));
        } else {
            this.permissions.put(permission, SdkConstraint.minSdk(minSdk));
        }
        return this;
    }

    public PermissionRegistry recordMaxSdk(String permission, int maxSdk) {
        if (this.permissions.containsKey(permission)) {
            this.permissions.put(permission, new SdkConstraint(this.permissions.get(permission).minSdk, maxSdk));
        } else {
            this.permissions.put(permission, SdkConstraint.maxSdk(maxSdk));
        }
        return this;
    }

    public boolean needsPermission(String permission) {
        SdkConstraint constraint = this.permissions.get(permission);
        if (constraint == null) {
            return true;
        }
        if (constraint.minSdk > Build.VERSION.SDK_INT || Build.VERSION.SDK_INT >= constraint.maxSdk) {
            return false;
        }
        return true;
    }
}
