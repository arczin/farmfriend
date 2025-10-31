package androidx.appcompat.widget;

import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.drawable.WrappedDrawable;

public class DrawableUtils {
    public static final Rect INSETS_NONE = new Rect();
    private static final String TAG = "DrawableUtils";
    private static final String VECTOR_DRAWABLE_CLAZZ_NAME = "android.graphics.drawable.VectorDrawable";
    private static Class<?> sInsetsClazz;

    static {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                sInsetsClazz = Class.forName("android.graphics.Insets");
            } catch (ClassNotFoundException e) {
            }
        }
    }

    private DrawableUtils() {
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Rect getOpticalBounds(android.graphics.drawable.Drawable r10) {
        /*
            java.lang.Class<?> r0 = sInsetsClazz
            if (r0 == 0) goto L_0x0091
            android.graphics.drawable.Drawable r0 = androidx.core.graphics.drawable.DrawableCompat.unwrap(r10)     // Catch:{ Exception -> 0x0089 }
            r10 = r0
            java.lang.Class r0 = r10.getClass()     // Catch:{ Exception -> 0x0089 }
            java.lang.String r1 = "getOpticalInsets"
            r2 = 0
            java.lang.Class[] r3 = new java.lang.Class[r2]     // Catch:{ Exception -> 0x0089 }
            java.lang.reflect.Method r0 = r0.getMethod(r1, r3)     // Catch:{ Exception -> 0x0089 }
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0089 }
            java.lang.Object r1 = r0.invoke(r10, r1)     // Catch:{ Exception -> 0x0089 }
            if (r1 == 0) goto L_0x0088
            android.graphics.Rect r3 = new android.graphics.Rect     // Catch:{ Exception -> 0x0089 }
            r3.<init>()     // Catch:{ Exception -> 0x0089 }
            java.lang.Class<?> r4 = sInsetsClazz     // Catch:{ Exception -> 0x0089 }
            java.lang.reflect.Field[] r4 = r4.getFields()     // Catch:{ Exception -> 0x0089 }
            int r5 = r4.length     // Catch:{ Exception -> 0x0089 }
            r6 = 0
        L_0x002b:
            if (r6 >= r5) goto L_0x0087
            r7 = r4[r6]     // Catch:{ Exception -> 0x0089 }
            java.lang.String r8 = r7.getName()     // Catch:{ Exception -> 0x0089 }
            int r9 = r8.hashCode()     // Catch:{ Exception -> 0x0089 }
            switch(r9) {
                case -1383228885: goto L_0x0059;
                case 115029: goto L_0x004f;
                case 3317767: goto L_0x0045;
                case 108511772: goto L_0x003b;
                default: goto L_0x003a;
            }     // Catch:{ Exception -> 0x0089 }
        L_0x003a:
            goto L_0x0063
        L_0x003b:
            java.lang.String r9 = "right"
            boolean r8 = r8.equals(r9)     // Catch:{ Exception -> 0x0089 }
            if (r8 == 0) goto L_0x003a
            r8 = 2
            goto L_0x0064
        L_0x0045:
            java.lang.String r9 = "left"
            boolean r8 = r8.equals(r9)     // Catch:{ Exception -> 0x0089 }
            if (r8 == 0) goto L_0x003a
            r8 = 0
            goto L_0x0064
        L_0x004f:
            java.lang.String r9 = "top"
            boolean r8 = r8.equals(r9)     // Catch:{ Exception -> 0x0089 }
            if (r8 == 0) goto L_0x003a
            r8 = 1
            goto L_0x0064
        L_0x0059:
            java.lang.String r9 = "bottom"
            boolean r8 = r8.equals(r9)     // Catch:{ Exception -> 0x0089 }
            if (r8 == 0) goto L_0x003a
            r8 = 3
            goto L_0x0064
        L_0x0063:
            r8 = -1
        L_0x0064:
            switch(r8) {
                case 0: goto L_0x007d;
                case 1: goto L_0x0076;
                case 2: goto L_0x006f;
                case 3: goto L_0x0068;
                default: goto L_0x0067;
            }     // Catch:{ Exception -> 0x0089 }
        L_0x0067:
            goto L_0x0084
        L_0x0068:
            int r8 = r7.getInt(r1)     // Catch:{ Exception -> 0x0089 }
            r3.bottom = r8     // Catch:{ Exception -> 0x0089 }
            goto L_0x0084
        L_0x006f:
            int r8 = r7.getInt(r1)     // Catch:{ Exception -> 0x0089 }
            r3.right = r8     // Catch:{ Exception -> 0x0089 }
            goto L_0x0084
        L_0x0076:
            int r8 = r7.getInt(r1)     // Catch:{ Exception -> 0x0089 }
            r3.top = r8     // Catch:{ Exception -> 0x0089 }
            goto L_0x0084
        L_0x007d:
            int r8 = r7.getInt(r1)     // Catch:{ Exception -> 0x0089 }
            r3.left = r8     // Catch:{ Exception -> 0x0089 }
        L_0x0084:
            int r6 = r6 + 1
            goto L_0x002b
        L_0x0087:
            return r3
        L_0x0088:
            goto L_0x0091
        L_0x0089:
            r0 = move-exception
            java.lang.String r1 = "DrawableUtils"
            java.lang.String r2 = "Couldn't obtain the optical insets. Ignoring."
            android.util.Log.e(r1, r2)
        L_0x0091:
            android.graphics.Rect r0 = INSETS_NONE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.DrawableUtils.getOpticalBounds(android.graphics.drawable.Drawable):android.graphics.Rect");
    }

    static void fixDrawable(Drawable drawable) {
        if (Build.VERSION.SDK_INT == 21 && VECTOR_DRAWABLE_CLAZZ_NAME.equals(drawable.getClass().getName())) {
            fixVectorDrawableTinting(drawable);
        }
    }

    public static boolean canSafelyMutateDrawable(Drawable drawable) {
        if (Build.VERSION.SDK_INT < 15 && (drawable instanceof InsetDrawable)) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 15 && (drawable instanceof GradientDrawable)) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 17 && (drawable instanceof LayerDrawable)) {
            return false;
        }
        if (drawable instanceof DrawableContainer) {
            Drawable.ConstantState state = drawable.getConstantState();
            if (!(state instanceof DrawableContainer.DrawableContainerState)) {
                return true;
            }
            for (Drawable child : ((DrawableContainer.DrawableContainerState) state).getChildren()) {
                if (!canSafelyMutateDrawable(child)) {
                    return false;
                }
            }
            return true;
        } else if (drawable instanceof WrappedDrawable) {
            return canSafelyMutateDrawable(((WrappedDrawable) drawable).getWrappedDrawable());
        } else {
            if (drawable instanceof DrawableWrapper) {
                return canSafelyMutateDrawable(((DrawableWrapper) drawable).getWrappedDrawable());
            }
            if (drawable instanceof ScaleDrawable) {
                return canSafelyMutateDrawable(((ScaleDrawable) drawable).getDrawable());
            }
            return true;
        }
    }

    private static void fixVectorDrawableTinting(Drawable drawable) {
        int[] originalState = drawable.getState();
        if (originalState == null || originalState.length == 0) {
            drawable.setState(ThemeUtils.CHECKED_STATE_SET);
        } else {
            drawable.setState(ThemeUtils.EMPTY_STATE_SET);
        }
        drawable.setState(originalState);
    }

    public static PorterDuff.Mode parseTintMode(int value, PorterDuff.Mode defaultMode) {
        switch (value) {
            case 3:
                return PorterDuff.Mode.SRC_OVER;
            case 5:
                return PorterDuff.Mode.SRC_IN;
            case 9:
                return PorterDuff.Mode.SRC_ATOP;
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return defaultMode;
        }
    }
}
