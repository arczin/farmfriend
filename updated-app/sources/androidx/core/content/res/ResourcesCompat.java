package androidx.core.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.WeakHashMap;

public final class ResourcesCompat {
    public static final int ID_NULL = 0;
    private static final String TAG = "ResourcesCompat";
    private static final Object sColorStateCacheLock = new Object();
    private static final WeakHashMap<ColorStateListCacheKey, SparseArray<ColorStateListCacheEntry>> sColorStateCaches = new WeakHashMap<>(0);
    private static final ThreadLocal<TypedValue> sTempTypedValue = new ThreadLocal<>();

    public static void clearCachesForTheme(Resources.Theme theme) {
        synchronized (sColorStateCacheLock) {
            Iterator<ColorStateListCacheKey> keys = sColorStateCaches.keySet().iterator();
            while (keys.hasNext()) {
                ColorStateListCacheKey key = keys.next();
                if (key != null && theme.equals(key.mTheme)) {
                    keys.remove();
                }
            }
        }
    }

    public static Drawable getDrawable(Resources res, int id, Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.getDrawable(res, id, theme);
        }
        return res.getDrawable(id);
    }

    public static Drawable getDrawableForDensity(Resources res, int id, int density, Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.getDrawableForDensity(res, id, density, theme);
        }
        if (Build.VERSION.SDK_INT >= 15) {
            return Api15Impl.getDrawableForDensity(res, id, density);
        }
        return res.getDrawable(id);
    }

    public static int getColor(Resources res, int id, Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.getColor(res, id, theme);
        }
        return res.getColor(id);
    }

    public static ColorStateList getColorStateList(Resources res, int id, Resources.Theme theme) throws Resources.NotFoundException {
        ColorStateListCacheKey key = new ColorStateListCacheKey(res, theme);
        ColorStateList csl = getCachedColorStateList(key, id);
        if (csl != null) {
            return csl;
        }
        ColorStateList csl2 = inflateColorStateList(res, id, theme);
        if (csl2 != null) {
            addColorStateListToCache(key, id, csl2, theme);
            return csl2;
        } else if (Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.getColorStateList(res, id, theme);
        } else {
            return res.getColorStateList(id);
        }
    }

    private static ColorStateList inflateColorStateList(Resources resources, int resId, Resources.Theme theme) {
        if (isColorInt(resources, resId)) {
            return null;
        }
        try {
            return ColorStateListInflaterCompat.createFromXml(resources, resources.getXml(resId), theme);
        } catch (Exception e) {
            Log.w(TAG, "Failed to inflate ColorStateList, leaving it to the framework", e);
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0047, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.content.res.ColorStateList getCachedColorStateList(androidx.core.content.res.ResourcesCompat.ColorStateListCacheKey r5, int r6) {
        /*
            java.lang.Object r0 = sColorStateCacheLock
            monitor-enter(r0)
            java.util.WeakHashMap<androidx.core.content.res.ResourcesCompat$ColorStateListCacheKey, android.util.SparseArray<androidx.core.content.res.ResourcesCompat$ColorStateListCacheEntry>> r1 = sColorStateCaches     // Catch:{ all -> 0x0049 }
            java.lang.Object r1 = r1.get(r5)     // Catch:{ all -> 0x0049 }
            android.util.SparseArray r1 = (android.util.SparseArray) r1     // Catch:{ all -> 0x0049 }
            if (r1 == 0) goto L_0x0046
            int r2 = r1.size()     // Catch:{ all -> 0x0049 }
            if (r2 <= 0) goto L_0x0046
            java.lang.Object r2 = r1.get(r6)     // Catch:{ all -> 0x0049 }
            androidx.core.content.res.ResourcesCompat$ColorStateListCacheEntry r2 = (androidx.core.content.res.ResourcesCompat.ColorStateListCacheEntry) r2     // Catch:{ all -> 0x0049 }
            if (r2 == 0) goto L_0x0046
            android.content.res.Configuration r3 = r2.mConfiguration     // Catch:{ all -> 0x0049 }
            android.content.res.Resources r4 = r5.mResources     // Catch:{ all -> 0x0049 }
            android.content.res.Configuration r4 = r4.getConfiguration()     // Catch:{ all -> 0x0049 }
            boolean r3 = r3.equals(r4)     // Catch:{ all -> 0x0049 }
            if (r3 == 0) goto L_0x0043
            android.content.res.Resources$Theme r3 = r5.mTheme     // Catch:{ all -> 0x0049 }
            if (r3 != 0) goto L_0x0031
            int r3 = r2.mThemeHash     // Catch:{ all -> 0x0049 }
            if (r3 == 0) goto L_0x003f
        L_0x0031:
            android.content.res.Resources$Theme r3 = r5.mTheme     // Catch:{ all -> 0x0049 }
            if (r3 == 0) goto L_0x0043
            int r3 = r2.mThemeHash     // Catch:{ all -> 0x0049 }
            android.content.res.Resources$Theme r4 = r5.mTheme     // Catch:{ all -> 0x0049 }
            int r4 = r4.hashCode()     // Catch:{ all -> 0x0049 }
            if (r3 != r4) goto L_0x0043
        L_0x003f:
            android.content.res.ColorStateList r3 = r2.mValue     // Catch:{ all -> 0x0049 }
            monitor-exit(r0)     // Catch:{ all -> 0x0049 }
            return r3
        L_0x0043:
            r1.remove(r6)     // Catch:{ all -> 0x0049 }
        L_0x0046:
            monitor-exit(r0)     // Catch:{ all -> 0x0049 }
            r0 = 0
            return r0
        L_0x0049:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0049 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.ResourcesCompat.getCachedColorStateList(androidx.core.content.res.ResourcesCompat$ColorStateListCacheKey, int):android.content.res.ColorStateList");
    }

    private static void addColorStateListToCache(ColorStateListCacheKey key, int resId, ColorStateList value, Resources.Theme theme) {
        synchronized (sColorStateCacheLock) {
            SparseArray<ColorStateListCacheEntry> entries = sColorStateCaches.get(key);
            if (entries == null) {
                entries = new SparseArray<>();
                sColorStateCaches.put(key, entries);
            }
            entries.append(resId, new ColorStateListCacheEntry(value, key.mResources.getConfiguration(), theme));
        }
    }

    private static boolean isColorInt(Resources resources, int resId) {
        TypedValue value = getTypedValue();
        resources.getValue(resId, value, true);
        if (value.type < 28 || value.type > 31) {
            return false;
        }
        return true;
    }

    private static TypedValue getTypedValue() {
        TypedValue tv = sTempTypedValue.get();
        if (tv != null) {
            return tv;
        }
        TypedValue tv2 = new TypedValue();
        sTempTypedValue.set(tv2);
        return tv2;
    }

    private static final class ColorStateListCacheKey {
        final Resources mResources;
        final Resources.Theme mTheme;

        ColorStateListCacheKey(Resources resources, Resources.Theme theme) {
            this.mResources = resources;
            this.mTheme = theme;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ColorStateListCacheKey that = (ColorStateListCacheKey) o;
            if (!this.mResources.equals(that.mResources) || !ObjectsCompat.equals(this.mTheme, that.mTheme)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return ObjectsCompat.hash(this.mResources, this.mTheme);
        }
    }

    private static class ColorStateListCacheEntry {
        final Configuration mConfiguration;
        final int mThemeHash;
        final ColorStateList mValue;

        ColorStateListCacheEntry(ColorStateList value, Configuration configuration, Resources.Theme theme) {
            this.mValue = value;
            this.mConfiguration = configuration;
            this.mThemeHash = theme == null ? 0 : theme.hashCode();
        }
    }

    public static float getFloat(Resources res, int id) {
        if (Build.VERSION.SDK_INT >= 29) {
            return Api29Impl.getFloat(res, id);
        }
        TypedValue value = getTypedValue();
        res.getValue(id, value, true);
        if (value.type == 4) {
            return value.getFloat();
        }
        throw new Resources.NotFoundException("Resource ID #0x" + Integer.toHexString(id) + " type #0x" + Integer.toHexString(value.type) + " is not valid");
    }

    public static Typeface getFont(Context context, int id) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, id, new TypedValue(), 0, (FontCallback) null, (Handler) null, false, false);
    }

    public static Typeface getCachedFont(Context context, int id) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, id, new TypedValue(), 0, (FontCallback) null, (Handler) null, false, true);
    }

    public static abstract class FontCallback {
        /* renamed from: onFontRetrievalFailed */
        public abstract void m0lambda$callbackFailAsync$1$androidxcorecontentresResourcesCompat$FontCallback(int i);

        /* renamed from: onFontRetrieved */
        public abstract void m1lambda$callbackSuccessAsync$0$androidxcorecontentresResourcesCompat$FontCallback(Typeface typeface);

        public final void callbackSuccessAsync(Typeface typeface, Handler handler) {
            getHandler(handler).post(new ResourcesCompat$FontCallback$$ExternalSyntheticLambda0(this, typeface));
        }

        public final void callbackFailAsync(int reason, Handler handler) {
            getHandler(handler).post(new ResourcesCompat$FontCallback$$ExternalSyntheticLambda1(this, reason));
        }

        public static Handler getHandler(Handler handler) {
            return handler == null ? new Handler(Looper.getMainLooper()) : handler;
        }
    }

    public static void getFont(Context context, int id, FontCallback fontCallback, Handler handler) throws Resources.NotFoundException {
        Preconditions.checkNotNull(fontCallback);
        if (context.isRestricted()) {
            fontCallback.callbackFailAsync(-4, handler);
            return;
        }
        loadFont(context, id, new TypedValue(), 0, fontCallback, handler, false, false);
    }

    public static Typeface getFont(Context context, int id, TypedValue value, int style, FontCallback fontCallback) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, id, value, style, fontCallback, (Handler) null, true, false);
    }

    private static Typeface loadFont(Context context, int id, TypedValue value, int style, FontCallback fontCallback, Handler handler, boolean isRequestFromLayoutInflator, boolean isCachedOnly) {
        Resources resources = context.getResources();
        int i = id;
        TypedValue typedValue = value;
        resources.getValue(id, value, true);
        Typeface typeface = loadFont(context, resources, value, id, style, fontCallback, handler, isRequestFromLayoutInflator, isCachedOnly);
        if (typeface != null || fontCallback != null || isCachedOnly) {
            return typeface;
        }
        throw new Resources.NotFoundException("Font resource ID #0x" + Integer.toHexString(id) + " could not be retrieved.");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00d5, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00d6, code lost:
        r19 = r6;
        r18 = r9;
        r20 = TAG;
        r11 = -3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00f8, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00f9, code lost:
        r19 = r6;
        r2 = r9;
        r3 = TAG;
        r11 = -3;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00d5 A[ExcHandler: IOException (e java.io.IOException), Splitter:B:14:0x003a] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0117  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.graphics.Typeface loadFont(android.content.Context r21, android.content.res.Resources r22, android.util.TypedValue r23, int r24, int r25, androidx.core.content.res.ResourcesCompat.FontCallback r26, android.os.Handler r27, boolean r28, boolean r29) {
        /*
            r11 = r22
            r12 = r23
            r13 = r24
            r14 = r26
            r15 = r27
            java.lang.String r10 = "ResourcesCompat"
            java.lang.CharSequence r0 = r12.string
            if (r0 == 0) goto L_0x011b
            java.lang.CharSequence r0 = r12.string
            java.lang.String r9 = r0.toString()
            java.lang.String r0 = "res/"
            boolean r0 = r9.startsWith(r0)
            r8 = -3
            r16 = 0
            if (r0 != 0) goto L_0x0027
            if (r14 == 0) goto L_0x0026
            r14.callbackFailAsync(r8, r15)
        L_0x0026:
            return r16
        L_0x0027:
            int r0 = r12.assetCookie
            r7 = r25
            android.graphics.Typeface r6 = androidx.core.graphics.TypefaceCompat.findFromCache(r11, r13, r9, r0, r7)
            if (r6 == 0) goto L_0x0037
            if (r14 == 0) goto L_0x0036
            r14.callbackSuccessAsync(r6, r15)
        L_0x0036:
            return r6
        L_0x0037:
            if (r29 == 0) goto L_0x003a
            return r16
        L_0x003a:
            java.lang.String r0 = r9.toLowerCase()     // Catch:{ XmlPullParserException -> 0x00f8, IOException -> 0x00d5 }
            java.lang.String r1 = ".xml"
            boolean r0 = r0.endsWith(r1)     // Catch:{ XmlPullParserException -> 0x00c8, IOException -> 0x00d5 }
            if (r0 == 0) goto L_0x008f
            android.content.res.XmlResourceParser r0 = r11.getXml(r13)     // Catch:{ XmlPullParserException -> 0x00c8, IOException -> 0x00d5 }
            androidx.core.content.res.FontResourcesParserCompat$FamilyResourceEntry r1 = androidx.core.content.res.FontResourcesParserCompat.parse(r0, r11)     // Catch:{ XmlPullParserException -> 0x00c8, IOException -> 0x00d5 }
            r17 = r1
            if (r17 != 0) goto L_0x006c
            java.lang.String r1 = "Failed to find font-family tag"
            android.util.Log.e(r10, r1)     // Catch:{ XmlPullParserException -> 0x0066, IOException -> 0x005e }
            if (r14 == 0) goto L_0x005d
            r14.callbackFailAsync(r8, r15)     // Catch:{ XmlPullParserException -> 0x0066, IOException -> 0x005e }
        L_0x005d:
            return r16
        L_0x005e:
            r0 = move-exception
            r18 = r9
            r20 = r10
            r11 = -3
            goto L_0x00dd
        L_0x0066:
            r0 = move-exception
            r2 = r9
            r3 = r10
            r11 = -3
            goto L_0x00fe
        L_0x006c:
            int r5 = r12.assetCookie     // Catch:{ XmlPullParserException -> 0x00c8, IOException -> 0x00d5 }
            r1 = r21
            r2 = r17
            r3 = r22
            r4 = r24
            r18 = r5
            r5 = r9
            r19 = r6
            r6 = r18
            r7 = r25
            r11 = -3
            r8 = r26
            r18 = r9
            r9 = r27
            r20 = r10
            r10 = r28
            android.graphics.Typeface r1 = androidx.core.graphics.TypefaceCompat.createFromResourcesFamilyXml(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ XmlPullParserException -> 0x00c0, IOException -> 0x00bc }
            return r1
        L_0x008f:
            r19 = r6
            r18 = r9
            r20 = r10
            r11 = -3
            int r5 = r12.assetCookie     // Catch:{ XmlPullParserException -> 0x00c0, IOException -> 0x00bc }
            r1 = r21
            r2 = r22
            r3 = r24
            r4 = r18
            r6 = r25
            android.graphics.Typeface r0 = androidx.core.graphics.TypefaceCompat.createFromResourcesFontFile(r1, r2, r3, r4, r5, r6)     // Catch:{ XmlPullParserException -> 0x00c0, IOException -> 0x00bc }
            r6 = r0
            if (r14 == 0) goto L_0x00bb
            if (r6 == 0) goto L_0x00af
            r14.callbackSuccessAsync(r6, r15)     // Catch:{ XmlPullParserException -> 0x00b5, IOException -> 0x00b3 }
            goto L_0x00bb
        L_0x00af:
            r14.callbackFailAsync(r11, r15)     // Catch:{ XmlPullParserException -> 0x00b5, IOException -> 0x00b3 }
            goto L_0x00bb
        L_0x00b3:
            r0 = move-exception
            goto L_0x00dd
        L_0x00b5:
            r0 = move-exception
            r2 = r18
            r3 = r20
            goto L_0x00fe
        L_0x00bb:
            return r6
        L_0x00bc:
            r0 = move-exception
            r6 = r19
            goto L_0x00dd
        L_0x00c0:
            r0 = move-exception
            r2 = r18
            r6 = r19
            r3 = r20
            goto L_0x00fe
        L_0x00c8:
            r0 = move-exception
            r19 = r6
            r18 = r9
            r20 = r10
            r11 = -3
            r2 = r18
            r3 = r20
            goto L_0x00fe
        L_0x00d5:
            r0 = move-exception
            r19 = r6
            r18 = r9
            r20 = r10
            r11 = -3
        L_0x00dd:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Failed to read xml resource "
            java.lang.StringBuilder r1 = r1.append(r2)
            r2 = r18
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r3 = r20
            android.util.Log.e(r3, r1, r0)
            goto L_0x0115
        L_0x00f8:
            r0 = move-exception
            r19 = r6
            r2 = r9
            r3 = r10
            r11 = -3
        L_0x00fe:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = "Failed to parse xml resource "
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.e(r3, r1, r0)
        L_0x0115:
            if (r14 == 0) goto L_0x011a
            r14.callbackFailAsync(r11, r15)
        L_0x011a:
            return r16
        L_0x011b:
            android.content.res.Resources$NotFoundException r0 = new android.content.res.Resources$NotFoundException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Resource \""
            java.lang.StringBuilder r1 = r1.append(r2)
            r2 = r22
            java.lang.String r3 = r2.getResourceName(r13)
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = "\" ("
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = java.lang.Integer.toHexString(r24)
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = ") is not a Font: "
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.ResourcesCompat.loadFont(android.content.Context, android.content.res.Resources, android.util.TypedValue, int, int, androidx.core.content.res.ResourcesCompat$FontCallback, android.os.Handler, boolean, boolean):android.graphics.Typeface");
    }

    static class Api29Impl {
        private Api29Impl() {
        }

        static float getFloat(Resources res, int id) {
            return res.getFloat(id);
        }
    }

    static class Api23Impl {
        private Api23Impl() {
        }

        static ColorStateList getColorStateList(Resources res, int id, Resources.Theme theme) {
            return res.getColorStateList(id, theme);
        }

        static int getColor(Resources resources, int id, Resources.Theme theme) {
            return resources.getColor(id, theme);
        }
    }

    static class Api21Impl {
        private Api21Impl() {
        }

        static Drawable getDrawable(Resources resources, int id, Resources.Theme theme) {
            return resources.getDrawable(id, theme);
        }

        static Drawable getDrawableForDensity(Resources resources, int id, int density, Resources.Theme theme) {
            return resources.getDrawableForDensity(id, density, theme);
        }
    }

    static class Api15Impl {
        private Api15Impl() {
        }

        static Drawable getDrawableForDensity(Resources resources, int id, int density) {
            return resources.getDrawableForDensity(id, density);
        }
    }

    private ResourcesCompat() {
    }

    public static final class ThemeCompat {
        private ThemeCompat() {
        }

        public static void rebase(Resources.Theme theme) {
            if (Build.VERSION.SDK_INT >= 29) {
                Api29Impl.rebase(theme);
            } else if (Build.VERSION.SDK_INT >= 23) {
                Api23Impl.rebase(theme);
            }
        }

        static class Api29Impl {
            private Api29Impl() {
            }

            static void rebase(Resources.Theme theme) {
                theme.rebase();
            }
        }

        static class Api23Impl {
            private static Method sRebaseMethod;
            private static boolean sRebaseMethodFetched;
            private static final Object sRebaseMethodLock = new Object();

            private Api23Impl() {
            }

            static void rebase(Resources.Theme theme) {
                synchronized (sRebaseMethodLock) {
                    if (!sRebaseMethodFetched) {
                        try {
                            sRebaseMethod = Resources.Theme.class.getDeclaredMethod("rebase", new Class[0]);
                            sRebaseMethod.setAccessible(true);
                        } catch (NoSuchMethodException e) {
                            Log.i(ResourcesCompat.TAG, "Failed to retrieve rebase() method", e);
                        }
                        sRebaseMethodFetched = true;
                    }
                    if (sRebaseMethod != null) {
                        try {
                            sRebaseMethod.invoke(theme, new Object[0]);
                        } catch (IllegalAccessException | InvocationTargetException e2) {
                            Log.i(ResourcesCompat.TAG, "Failed to invoke rebase() method via reflection", e2);
                            sRebaseMethod = null;
                        }
                    }
                }
            }
        }
    }
}
