package com.google.appinventor.components.runtime.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.VideoView;
import com.google.appinventor.components.common.FileScope;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.ReplForm;
import com.google.appinventor.components.runtime.errors.PermissionException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MediaUtil {
    private static final String LOG_TAG = "MediaUtil";
    private static ConcurrentHashMap<String, String> pathCache = new ConcurrentHashMap<>(2);
    private static final Map<String, File> tempFileMap = new HashMap();

    private enum MediaSource {
        ASSET,
        REPL_ASSET,
        SDCARD,
        FILE_URL,
        URL,
        CONTENT_URI,
        CONTACT_URI,
        PRIVATE_DATA
    }

    private MediaUtil() {
    }

    static String fileUrlToFilePath(String mediaPath) throws IOException {
        try {
            return new File(new URL(mediaPath).toURI()).getAbsolutePath();
        } catch (IllegalArgumentException e) {
            throw new IOException("Unable to determine file path of file url " + mediaPath);
        } catch (Exception e2) {
            throw new IOException("Unable to determine file path of file url " + mediaPath);
        }
    }

    private static MediaSource determineMediaSource(Form form, String mediaPath) {
        if (mediaPath.startsWith(QUtil.getExternalStoragePath(form)) || mediaPath.startsWith("/sdcard/")) {
            return MediaSource.SDCARD;
        }
        if (mediaPath.startsWith("content://contacts/")) {
            return MediaSource.CONTACT_URI;
        }
        if (mediaPath.startsWith("content://")) {
            return MediaSource.CONTENT_URI;
        }
        if (mediaPath.startsWith("/data/")) {
            return MediaSource.PRIVATE_DATA;
        }
        try {
            URL url = new URL(mediaPath);
            if (!mediaPath.startsWith("file:")) {
                return MediaSource.URL;
            }
            if (url.getPath().startsWith("/android_asset/")) {
                return MediaSource.ASSET;
            }
            return MediaSource.FILE_URL;
        } catch (MalformedURLException e) {
            if (!(form instanceof ReplForm)) {
                return MediaSource.ASSET;
            }
            if (((ReplForm) form).isAssetsLoaded()) {
                return MediaSource.REPL_ASSET;
            }
            return MediaSource.ASSET;
        }
    }

    @Deprecated
    public static boolean isExternalFileUrl(String mediaPath) {
        Log.w(LOG_TAG, "Calling deprecated version of isExternalFileUrl", new IllegalAccessException());
        return mediaPath.startsWith(new StringBuilder().append("file://").append(QUtil.getExternalStoragePath(Form.getActiveForm())).toString()) || mediaPath.startsWith("file:///sdcard/");
    }

    public static boolean isExternalFileUrl(Context context, String mediaPath) {
        if (Build.VERSION.SDK_INT >= 29) {
            return false;
        }
        if (mediaPath.startsWith("file://" + QUtil.getExternalStorageDir(context)) || mediaPath.startsWith("file:///sdcard")) {
            return true;
        }
        return false;
    }

    @Deprecated
    public static boolean isExternalFile(String mediaPath) {
        Log.w(LOG_TAG, "Calling deprecated version of isExternalFile", new IllegalAccessException());
        return mediaPath.startsWith(QUtil.getExternalStoragePath(Form.getActiveForm())) || mediaPath.startsWith("/sdcard/") || isExternalFileUrl(Form.getActiveForm(), mediaPath);
    }

    public static boolean isExternalFile(Context context, String mediaPath) {
        if (Build.VERSION.SDK_INT >= 29) {
            return false;
        }
        if (mediaPath.startsWith(QUtil.getExternalStoragePath(context)) || mediaPath.startsWith("/sdcard/") || isExternalFileUrl(context, mediaPath)) {
            return true;
        }
        return false;
    }

    private static String findCaseinsensitivePath(Form form, String mediaPath) throws IOException {
        if (!pathCache.containsKey(mediaPath)) {
            String newPath = findCaseinsensitivePathWithoutCache(form, mediaPath);
            if (newPath == null) {
                return null;
            }
            pathCache.put(mediaPath, newPath);
        }
        return pathCache.get(mediaPath);
    }

    private static String findCaseinsensitivePathWithoutCache(Form form, String mediaPath) throws IOException {
        String[] mediaPathlist = form.getAssets().list("");
        int l = Array.getLength(mediaPathlist);
        for (int i = 0; i < l; i++) {
            String temp = mediaPathlist[i];
            if (temp.equalsIgnoreCase(mediaPath)) {
                return temp;
            }
        }
        return null;
    }

    private static InputStream getAssetsIgnoreCaseInputStream(Form form, String mediaPath) throws IOException {
        try {
            return form.getAssets().open(mediaPath);
        } catch (IOException e) {
            String path = findCaseinsensitivePath(form, mediaPath);
            if (path != null) {
                return form.getAssets().open(path);
            }
            throw e;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0094, code lost:
        return new java.net.URL(r7).openStream();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.InputStream openMedia(com.google.appinventor.components.runtime.Form r6, java.lang.String r7, com.google.appinventor.components.runtime.util.MediaUtil.MediaSource r8) throws java.io.IOException {
        /*
            int[] r0 = com.google.appinventor.components.runtime.util.MediaUtil.AnonymousClass3.$SwitchMap$com$google$appinventor$components$runtime$util$MediaUtil$MediaSource
            int r1 = r8.ordinal()
            r0 = r0[r1]
            java.lang.String r1 = "."
            java.lang.String r2 = "android.permission.READ_EXTERNAL_STORAGE"
            r3 = 0
            switch(r0) {
                case 1: goto L_0x00f4;
                case 2: goto L_0x00ee;
                case 3: goto L_0x0095;
                case 4: goto L_0x00d9;
                case 5: goto L_0x007c;
                case 6: goto L_0x008b;
                case 7: goto L_0x006f;
                case 8: goto L_0x002d;
                default: goto L_0x0010;
            }
        L_0x0010:
            java.io.IOException r0 = new java.io.IOException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Unable to open media "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r7)
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x002d:
            r0 = 0
            int r2 = com.google.appinventor.components.runtime.util.SdkLevel.getLevel()
            r3 = 12
            if (r2 < r3) goto L_0x0043
            android.content.ContentResolver r2 = r6.getContentResolver()
            android.net.Uri r3 = android.net.Uri.parse(r7)
            java.io.InputStream r0 = com.google.appinventor.components.runtime.util.HoneycombMR1Util.openContactPhotoInputStreamHelper(r2, r3)
            goto L_0x004f
        L_0x0043:
            android.content.ContentResolver r2 = r6.getContentResolver()
            android.net.Uri r3 = android.net.Uri.parse(r7)
            java.io.InputStream r0 = android.provider.Contacts.People.openContactPhotoInputStream(r2, r3)
        L_0x004f:
            if (r0 == 0) goto L_0x0052
            return r0
        L_0x0052:
            java.io.IOException r2 = new java.io.IOException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Unable to open contact photo "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r7)
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.String r1 = r1.toString()
            r2.<init>(r1)
            throw r2
        L_0x006f:
            android.content.ContentResolver r0 = r6.getContentResolver()
            android.net.Uri r1 = android.net.Uri.parse(r7)
            java.io.InputStream r0 = r0.openInputStream(r1)
            return r0
        L_0x007c:
            boolean r0 = isExternalFileUrl(r6, r7)
            if (r0 == 0) goto L_0x008b
            boolean r0 = com.google.appinventor.components.runtime.util.RUtil.needsFilePermission(r6, r7, r3)
            if (r0 == 0) goto L_0x008b
            r6.assertPermission(r2)
        L_0x008b:
            java.net.URL r0 = new java.net.URL
            r0.<init>(r7)
            java.io.InputStream r0 = r0.openStream()
            return r0
        L_0x0095:
            boolean r0 = com.google.appinventor.components.runtime.util.RUtil.needsFilePermission(r6, r7, r3)
            if (r0 == 0) goto L_0x009e
            r6.assertPermission(r2)
        L_0x009e:
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00b1 }
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x00b1 }
            java.lang.String r4 = r6.getAssetPath(r7)     // Catch:{ Exception -> 0x00b1 }
            java.net.URI r4 = java.net.URI.create(r4)     // Catch:{ Exception -> 0x00b1 }
            r1.<init>(r4)     // Catch:{ Exception -> 0x00b1 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00b1 }
            return r0
        L_0x00b1:
            r0 = move-exception
            int r1 = com.google.appinventor.components.runtime.util.SdkLevel.getLevel()
            r4 = 9
            if (r1 >= r4) goto L_0x00e8
            java.lang.String r1 = android.util.Log.getStackTraceString(r0)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Error in REPL_ASSET Fetching: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r1 = r4.append(r1)
            java.lang.String r1 = r1.toString()
            java.lang.String r4 = "MediaUtil"
            android.util.Log.d(r4, r1)
            com.google.appinventor.components.runtime.util.FroyoUtil.throwIOException(r0)
        L_0x00d9:
            boolean r0 = com.google.appinventor.components.runtime.util.RUtil.needsFilePermission(r6, r7, r3)
            if (r0 == 0) goto L_0x00e2
            r6.assertPermission(r2)
        L_0x00e2:
            java.io.FileInputStream r0 = new java.io.FileInputStream
            r0.<init>(r7)
            return r0
        L_0x00e8:
            java.io.IOException r1 = new java.io.IOException
            r1.<init>(r0)
            throw r1
        L_0x00ee:
            java.io.FileInputStream r0 = new java.io.FileInputStream
            r0.<init>(r7)
            return r0
        L_0x00f4:
            java.lang.String r0 = "file:"
            boolean r0 = r7.startsWith(r0)
            if (r0 == 0) goto L_0x010b
            java.lang.String r0 = "/android_asset/"
            int r1 = r7.indexOf(r0)
            int r0 = r0.length()
            int r1 = r1 + r0
            java.lang.String r7 = r7.substring(r1)
        L_0x010b:
            java.io.InputStream r0 = getAssetsIgnoreCaseInputStream(r6, r7)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.MediaUtil.openMedia(com.google.appinventor.components.runtime.Form, java.lang.String, com.google.appinventor.components.runtime.util.MediaUtil$MediaSource):java.io.InputStream");
    }

    public static InputStream openMedia(Form form, String mediaPath) throws IOException {
        return openMedia(form, mediaPath, determineMediaSource(form, mediaPath));
    }

    public static File copyMediaToTempFile(Form form, String mediaPath) throws IOException {
        return copyMediaToTempFile(form, mediaPath, determineMediaSource(form, mediaPath));
    }

    private static File copyMediaToTempFile(Form form, String mediaPath, MediaSource mediaSource) throws IOException {
        InputStream in = openMedia(form, mediaPath, mediaSource);
        File file = null;
        try {
            file = File.createTempFile("AI_Media_", (String) null);
            file.deleteOnExit();
            FileUtil.writeStreamToFile(in, file.getAbsolutePath());
            in.close();
            return file;
        } catch (IOException e) {
            if (file != null) {
                Log.e(LOG_TAG, "Could not copy media " + mediaPath + " to temp file " + file.getAbsolutePath());
                file.delete();
            } else {
                Log.e(LOG_TAG, "Could not copy media " + mediaPath + " to temp file.");
            }
            throw e;
        } catch (Throwable th) {
            in.close();
            throw th;
        }
    }

    private static File cacheMediaTempFile(Form form, String mediaPath, MediaSource mediaSource) throws IOException {
        File tempFile = tempFileMap.get(mediaPath);
        if (tempFile != null && tempFile.exists()) {
            return tempFile;
        }
        Log.i(LOG_TAG, "Copying media " + mediaPath + " to temp file...");
        File tempFile2 = copyMediaToTempFile(form, mediaPath, mediaSource);
        Log.i(LOG_TAG, "Finished copying media " + mediaPath + " to temp file " + tempFile2.getAbsolutePath());
        tempFileMap.put(mediaPath, tempFile2);
        return tempFile2;
    }

    public static BitmapDrawable getBitmapDrawable(Form form, String mediaPath) throws IOException {
        if (mediaPath == null || mediaPath.length() == 0) {
            return null;
        }
        final Synchronizer<BitmapDrawable> syncer = new Synchronizer<>();
        getBitmapDrawableAsync(form, mediaPath, new AsyncCallbackPair<BitmapDrawable>() {
            public void onFailure(String message) {
                Synchronizer.this.error(message);
            }

            public void onSuccess(BitmapDrawable result) {
                Synchronizer.this.wakeup(result);
            }
        });
        syncer.waitfor();
        BitmapDrawable result = syncer.getResult();
        if (result != null) {
            return result;
        }
        String error = syncer.getError();
        if (error.startsWith("PERMISSION_DENIED:")) {
            throw new PermissionException(error.split(":")[1]);
        }
        throw new IOException(error);
    }

    public static void getBitmapDrawableAsync(Form form, String mediaPath, AsyncCallbackPair<BitmapDrawable> continuation) {
        getBitmapDrawableAsync(form, mediaPath, -1, -1, continuation);
    }

    public static void getBitmapDrawableAsync(Form form, String mediaPath, int desiredWidth, int desiredHeight, AsyncCallbackPair<BitmapDrawable> continuation) {
        if (mediaPath == null || mediaPath.length() == 0) {
            continuation.onSuccess(null);
            return;
        }
        final String str = mediaPath;
        final Form form2 = form;
        final MediaSource determineMediaSource = determineMediaSource(form, mediaPath);
        final AsyncCallbackPair<BitmapDrawable> asyncCallbackPair = continuation;
        final int i = desiredWidth;
        final int i2 = desiredHeight;
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                Log.d(MediaUtil.LOG_TAG, "mediaPath = " + str);
                InputStream is = null;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[4096];
                try {
                    InputStream is2 = MediaUtil.openMedia(form2, str, determineMediaSource);
                    while (true) {
                        int read = is2.read(buf);
                        int read2 = read;
                        if (read <= 0) {
                            break;
                        }
                        bos.write(buf, 0, read2);
                    }
                    byte[] buf2 = bos.toByteArray();
                    if (is2 != null) {
                        try {
                            is2.close();
                        } catch (IOException e) {
                            Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e);
                        }
                    }
                    try {
                        bos.close();
                    } catch (IOException e2) {
                    }
                    ByteArrayInputStream bis = new ByteArrayInputStream(buf2);
                    try {
                        bis.mark(buf2.length);
                        BitmapFactory.Options options = MediaUtil.getBitmapOptions(form2, bis, str);
                        bis.reset();
                        BitmapDrawable originalBitmapDrawable = new BitmapDrawable(form2.getResources(), MediaUtil.decodeStream(bis, (Rect) null, options));
                        originalBitmapDrawable.setTargetDensity(form2.getResources().getDisplayMetrics());
                        if ((i > 0 && i2 >= 0) || (options.inSampleSize == 1 && form2.deviceDensity() != 1.0f)) {
                            int scaledWidth = (int) (form2.deviceDensity() * ((float) (i > 0 ? i : originalBitmapDrawable.getIntrinsicWidth())));
                            float deviceDensity = form2.deviceDensity();
                            int intrinsicHeight = i2 > 0 ? i2 : originalBitmapDrawable.getIntrinsicHeight();
                            Log.d(MediaUtil.LOG_TAG, "form.deviceDensity() = " + form2.deviceDensity());
                            Log.d(MediaUtil.LOG_TAG, "originalBitmapDrawable.getIntrinsicWidth() = " + originalBitmapDrawable.getIntrinsicWidth());
                            Log.d(MediaUtil.LOG_TAG, "originalBitmapDrawable.getIntrinsicHeight() = " + originalBitmapDrawable.getIntrinsicHeight());
                            BitmapDrawable scaledBitmapDrawable = new BitmapDrawable(form2.getResources(), Bitmap.createScaledBitmap(originalBitmapDrawable.getBitmap(), scaledWidth, (int) (deviceDensity * ((float) intrinsicHeight)), false));
                            scaledBitmapDrawable.setTargetDensity(form2.getResources().getDisplayMetrics());
                            System.gc();
                            asyncCallbackPair.onSuccess(scaledBitmapDrawable);
                            try {
                                bis.close();
                            } catch (IOException e3) {
                                Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e3);
                            }
                        } else {
                            asyncCallbackPair.onSuccess(originalBitmapDrawable);
                            try {
                                bis.close();
                            } catch (IOException e4) {
                                Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e4);
                            }
                        }
                    } catch (Exception e5) {
                        Log.w(MediaUtil.LOG_TAG, "Exception while loading media.", e5);
                        asyncCallbackPair.onFailure(e5.getMessage());
                        bis.close();
                    } catch (Throwable th) {
                        Throwable th2 = th;
                        try {
                            bis.close();
                        } catch (IOException e6) {
                            Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e6);
                        }
                        throw th2;
                    }
                } catch (PermissionException e7) {
                    asyncCallbackPair.onFailure("PERMISSION_DENIED:" + e7.getPermissionNeeded());
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e8) {
                            Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e8);
                        }
                    }
                    try {
                        bos.close();
                    } catch (IOException e9) {
                    }
                } catch (IOException e10) {
                    IOException e11 = e10;
                    if (determineMediaSource == MediaSource.CONTACT_URI) {
                        asyncCallbackPair.onSuccess(new BitmapDrawable(form2.getResources(), BitmapFactory.decodeResource(form2.getResources(), 17301606, (BitmapFactory.Options) null)));
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e12) {
                                Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e12);
                            }
                        }
                        try {
                            bos.close();
                        } catch (IOException e13) {
                        }
                        return;
                    }
                    Log.d(MediaUtil.LOG_TAG, "IOException reading file.", e11);
                    asyncCallbackPair.onFailure(e11.getMessage());
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e14) {
                            Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e14);
                        }
                    }
                    try {
                        bos.close();
                    } catch (IOException e15) {
                    }
                } catch (Throwable th3) {
                    Throwable th4 = th3;
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e16) {
                            Log.w(MediaUtil.LOG_TAG, "Unexpected error on close", e16);
                        }
                    }
                    try {
                        bos.close();
                    } catch (IOException e17) {
                    }
                    throw th4;
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static Bitmap decodeStream(InputStream is, Rect outPadding, BitmapFactory.Options opts) {
        return BitmapFactory.decodeStream(new FlushedInputStream(is), outPadding, opts);
    }

    private static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0;
            while (totalBytesSkipped < n) {
                long bytesSkipped = this.in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0) {
                    if (read() < 0) {
                        break;
                    }
                    bytesSkipped = 1;
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }

    /* access modifiers changed from: private */
    public static BitmapFactory.Options getBitmapOptions(Form form, InputStream is, String mediaPath) {
        int maxHeight;
        int maxWidth;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        decodeStream(is, (Rect) null, options);
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;
        Display display = ((WindowManager) form.getSystemService("window")).getDefaultDisplay();
        if (Form.getCompatibilityMode()) {
            maxWidth = 720;
            maxHeight = 840;
        } else {
            maxWidth = (int) (((float) display.getWidth()) / form.deviceDensity());
            maxHeight = (int) (((float) display.getHeight()) / form.deviceDensity());
        }
        int sampleSize = 1;
        while (imageWidth / sampleSize > maxWidth && imageHeight / sampleSize > maxHeight) {
            sampleSize *= 2;
        }
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        int width = display.getWidth();
        Log.d(LOG_TAG, "getBitmapOptions: sampleSize = " + sampleSize + " mediaPath = " + mediaPath + " maxWidth = " + maxWidth + " maxHeight = " + maxHeight + " display width = " + width + " display height = " + display.getHeight());
        options2.inSampleSize = sampleSize;
        return options2;
    }

    private static AssetFileDescriptor getAssetsIgnoreCaseAfd(Form form, String mediaPath) throws IOException {
        try {
            return form.getAssets().openFd(mediaPath);
        } catch (IOException e) {
            String path = findCaseinsensitivePath(form, mediaPath);
            if (path != null) {
                return form.getAssets().openFd(path);
            }
            throw e;
        }
    }

    public static int loadSoundPool(SoundPool soundPool, Form form, String mediaPath) throws IOException {
        MediaSource mediaSource = determineMediaSource(form, mediaPath);
        String str = "android.permission.READ_MEDIA_AUDIO";
        switch (mediaSource) {
            case ASSET:
                return soundPool.load(getAssetsIgnoreCaseAfd(form, mediaPath), 1);
            case REPL_ASSET:
                if (RUtil.needsFilePermission(form, mediaPath, (FileScope) null)) {
                    form.assertPermission("android.permission.READ_EXTERNAL_STORAGE");
                }
                return soundPool.load(fileUrlToFilePath(form.getAssetPath(mediaPath)), 1);
            case SDCARD:
                if (RUtil.needsFilePermission(form, mediaPath, (FileScope) null)) {
                    if (Build.VERSION.SDK_INT < 33) {
                        str = "android.permission.READ_EXTERNAL_STORAGE";
                    }
                    form.assertPermission(str);
                }
                return soundPool.load(mediaPath, 1);
            case FILE_URL:
                if (isExternalFileUrl(form, mediaPath) || RUtil.needsFilePermission(form, mediaPath, (FileScope) null)) {
                    if (Build.VERSION.SDK_INT < 33) {
                        str = "android.permission.READ_EXTERNAL_STORAGE";
                    }
                    form.assertPermission(str);
                }
                return soundPool.load(fileUrlToFilePath(mediaPath), 1);
            case URL:
            case CONTENT_URI:
                return soundPool.load(cacheMediaTempFile(form, mediaPath, mediaSource).getAbsolutePath(), 1);
            case CONTACT_URI:
                throw new IOException("Unable to load audio for contact " + mediaPath + ".");
            default:
                throw new IOException("Unable to load audio " + mediaPath + ".");
        }
    }

    public static void loadMediaPlayer(MediaPlayer mediaPlayer, Form form, String mediaPath) throws IOException {
        String str = "android.permission.READ_MEDIA_AUDIO";
        switch (determineMediaSource(form, mediaPath)) {
            case ASSET:
                AssetFileDescriptor afd = getAssetsIgnoreCaseAfd(form, mediaPath);
                try {
                    mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    return;
                } finally {
                    afd.close();
                }
            case REPL_ASSET:
                if (RUtil.needsFilePermission(form, mediaPath, (FileScope) null)) {
                    form.assertPermission("android.permission.READ_EXTERNAL_STORAGE");
                }
                mediaPlayer.setDataSource(fileUrlToFilePath(form.getAssetPath(mediaPath)));
                return;
            case SDCARD:
                if (RUtil.needsFilePermission(form, mediaPath, (FileScope) null)) {
                    if (Build.VERSION.SDK_INT < 33) {
                        str = "android.permission.READ_EXTERNAL_STORAGE";
                    }
                    form.assertPermission(str);
                }
                mediaPlayer.setDataSource(mediaPath);
                return;
            case FILE_URL:
                if (isExternalFileUrl(form, mediaPath) || RUtil.needsFilePermission(form, mediaPath, (FileScope) null)) {
                    if (Build.VERSION.SDK_INT < 33) {
                        str = "android.permission.READ_EXTERNAL_STORAGE";
                    }
                    form.assertPermission(str);
                }
                mediaPlayer.setDataSource(fileUrlToFilePath(mediaPath));
                return;
            case URL:
                mediaPlayer.setDataSource(mediaPath);
                return;
            case CONTENT_URI:
                mediaPlayer.setDataSource(form, Uri.parse(mediaPath));
                return;
            case CONTACT_URI:
                throw new IOException("Unable to load audio or video for contact " + mediaPath + ".");
            default:
                throw new IOException("Unable to load audio or video " + mediaPath + ".");
        }
    }

    public static void loadVideoView(VideoView videoView, Form form, String mediaPath) throws IOException {
        MediaSource mediaSource = determineMediaSource(form, mediaPath);
        String str = "android.permission.READ_MEDIA_VIDEO";
        switch (mediaSource) {
            case ASSET:
            case URL:
                videoView.setVideoPath(cacheMediaTempFile(form, mediaPath, mediaSource).getAbsolutePath());
                return;
            case REPL_ASSET:
                if (RUtil.needsFilePermission(form, mediaPath, (FileScope) null)) {
                    form.assertPermission("android.permission.READ_EXTERNAL_STORAGE");
                }
                videoView.setVideoPath(fileUrlToFilePath(form.getAssetPath(mediaPath)));
                return;
            case SDCARD:
                if (RUtil.needsFilePermission(form, mediaPath, (FileScope) null)) {
                    if (Build.VERSION.SDK_INT < 33) {
                        str = "android.permission.READ_EXTERNAL_STORAGE";
                    }
                    form.assertPermission(str);
                }
                videoView.setVideoPath(mediaPath);
                return;
            case FILE_URL:
                if (isExternalFileUrl(form, mediaPath) || RUtil.needsFilePermission(form, mediaPath, (FileScope) null)) {
                    if (Build.VERSION.SDK_INT < 33) {
                        str = "android.permission.READ_EXTERNAL_STORAGE";
                    }
                    form.assertPermission(str);
                }
                videoView.setVideoPath(fileUrlToFilePath(mediaPath));
                return;
            case CONTENT_URI:
                videoView.setVideoURI(Uri.parse(mediaPath));
                return;
            case CONTACT_URI:
                throw new IOException("Unable to load video for contact " + mediaPath + ".");
            default:
                throw new IOException("Unable to load video " + mediaPath + ".");
        }
    }
}
