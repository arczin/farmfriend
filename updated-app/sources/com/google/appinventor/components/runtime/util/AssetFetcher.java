package com.google.appinventor.components.runtime.util;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.ReplForm;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;

public class AssetFetcher {
    /* access modifiers changed from: private */
    public static final String LOG_TAG = AssetFetcher.class.getSimpleName();
    private static ExecutorService background = Executors.newSingleThreadExecutor();
    private static Context context = ReplForm.getActiveForm();
    private static HashDatabase db = new HashDatabase(context);
    private static volatile boolean inError = false;
    private static final Object semaphore = new Object();

    private AssetFetcher() {
    }

    public static void fetchAssets(final String cookieValue, final String projectId, final String uri, final String asset) {
        background.submit(new Runnable() {
            public void run() {
                String str = uri;
                String str2 = projectId;
                if (AssetFetcher.getFile(str + "/ode/download/file/" + str2 + "/" + asset, cookieValue, asset, 0) != null) {
                    RetValManager.assetTransferred(asset);
                }
            }
        });
    }

    public static void upgradeCompanion(final String cookieValue, final String inputUri) {
        background.submit(new Runnable() {
            public void run() {
                String[] parts = inputUri.split("/", 0);
                File assetFile = AssetFetcher.getFile(inputUri, cookieValue, parts[parts.length - 1], 0);
                if (assetFile != null) {
                    try {
                        Form form = Form.getActiveForm();
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setDataAndType(NougatUtil.getPackageUri(form, assetFile), "application/vnd.android.package-archive");
                        intent.setFlags(1);
                        form.startActivity(intent);
                    } catch (Exception e) {
                        Log.e(AssetFetcher.LOG_TAG, "ERROR_UNABLE_TO_GET", e);
                        RetValManager.sendError("Unable to Install new Companion Package.");
                    }
                }
            }
        });
    }

    public static void loadExtensions(String jsonString) {
        Log.d(LOG_TAG, "loadExtensions called jsonString = " + jsonString);
        try {
            ReplForm form = (ReplForm) Form.getActiveForm();
            JSONArray array = new JSONArray(jsonString);
            List<String> extensionsToLoad = new ArrayList<>();
            if (array.length() == 0) {
                Log.d(LOG_TAG, "loadExtensions: No Extensions");
                RetValManager.extensionsLoaded();
                return;
            }
            int i = 0;
            while (i < array.length()) {
                String extensionName = array.optString(i);
                if (extensionName != null) {
                    Log.d(LOG_TAG, "loadExtensions, extensionName = " + extensionName);
                    extensionsToLoad.add(extensionName);
                    i++;
                } else {
                    Log.e(LOG_TAG, "extensionName was null");
                    return;
                }
            }
            try {
                form.loadComponents(extensionsToLoad);
                RetValManager.extensionsLoaded();
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error in form.loadComponents", e);
                RetValManager.sendError("Unable to load extensions." + e);
            }
        } catch (JSONException e2) {
            Log.e(LOG_TAG, "JSON Exception parsing extension string", e2);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x01e3 A[Catch:{ Exception -> 0x0239, all -> 0x0230 }] */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x0227 A[Catch:{ Exception -> 0x02dd, all -> 0x02d4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x0248 A[Catch:{ Exception -> 0x02dd, all -> 0x02d4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x028b A[Catch:{ Exception -> 0x02dd, all -> 0x02d4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x030d  */
    /* JADX WARNING: Removed duplicated region for block: B:165:0x0320  */
    /* JADX WARNING: Removed duplicated region for block: B:184:0x0379  */
    /* JADX WARNING: Removed duplicated region for block: B:186:0x037e  */
    /* JADX WARNING: Removed duplicated region for block: B:190:0x0385  */
    /* JADX WARNING: Removed duplicated region for block: B:192:0x038a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File getFile(java.lang.String r22, java.lang.String r23, java.lang.String r24, int r25) {
        /*
            r1 = r22
            r2 = r23
            r3 = r24
            r4 = r25
            com.google.appinventor.components.runtime.Form r5 = com.google.appinventor.components.runtime.Form.getActiveForm()
            r6 = 0
            r0 = 1
            if (r4 <= r0) goto L_0x0026
            java.lang.Object r7 = semaphore
            monitor-enter(r7)
            boolean r8 = inError     // Catch:{ all -> 0x0023 }
            if (r8 != 0) goto L_0x0021
            inError = r0     // Catch:{ all -> 0x0023 }
            com.google.appinventor.components.runtime.util.AssetFetcher$3 r0 = new com.google.appinventor.components.runtime.util.AssetFetcher$3     // Catch:{ all -> 0x0023 }
            r0.<init>(r1)     // Catch:{ all -> 0x0023 }
            r5.runOnUiThread(r0)     // Catch:{ all -> 0x0023 }
        L_0x0021:
            monitor-exit(r7)     // Catch:{ all -> 0x0023 }
            return r6
        L_0x0023:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0023 }
            throw r0
        L_0x0026:
            r7 = r24
            java.io.File r8 = getDestinationFile(r5, r3)
            java.lang.String r9 = "/classes.jar"
            boolean r9 = r3.endsWith(r9)
            r10 = 0
            if (r9 == 0) goto L_0x0055
            java.lang.String r9 = "/"
            int r9 = r3.lastIndexOf(r9)
            int r9 = r9 + r0
            java.lang.String r9 = r3.substring(r10, r9)
            java.lang.String r11 = r8.getName()
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.StringBuilder r9 = r12.append(r9)
            java.lang.StringBuilder r9 = r9.append(r11)
            java.lang.String r7 = r9.toString()
        L_0x0055:
            java.lang.String r9 = LOG_TAG
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "target file = "
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.StringBuilder r11 = r11.append(r8)
            java.lang.String r11 = r11.toString()
            android.util.Log.d(r9, r11)
            int r9 = android.os.Build.VERSION.SDK_INT
            r11 = 34
            if (r9 < r11) goto L_0x0085
            java.lang.String r9 = "/external_comps/"
            boolean r9 = r3.contains(r9)
            if (r9 == 0) goto L_0x0085
            java.lang.String r9 = "/classes.jar"
            boolean r9 = r3.endsWith(r9)
            if (r9 == 0) goto L_0x0085
            r10 = 1
            goto L_0x0086
        L_0x0085:
        L_0x0086:
            r9 = r10
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            java.net.URL r14 = new java.net.URL     // Catch:{ Exception -> 0x0354, all -> 0x034e }
            r14.<init>(r1)     // Catch:{ Exception -> 0x0354, all -> 0x034e }
            java.net.URLConnection r15 = r14.openConnection()     // Catch:{ Exception -> 0x0354, all -> 0x034e }
            java.net.HttpURLConnection r15 = (java.net.HttpURLConnection) r15     // Catch:{ Exception -> 0x0354, all -> 0x034e }
            r10 = r15
            if (r10 == 0) goto L_0x0305
            java.lang.String r15 = "Cookie"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0354, all -> 0x034e }
            r6.<init>()     // Catch:{ Exception -> 0x0354, all -> 0x034e }
            java.lang.String r0 = "AppInventor = "
            java.lang.StringBuilder r0 = r6.append(r0)     // Catch:{ Exception -> 0x0354, all -> 0x034e }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Exception -> 0x0354, all -> 0x034e }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0354, all -> 0x034e }
            r10.addRequestProperty(r15, r0)     // Catch:{ Exception -> 0x0354, all -> 0x034e }
            com.google.appinventor.components.runtime.util.HashDatabase r0 = db     // Catch:{ Exception -> 0x0354, all -> 0x034e }
            com.google.appinventor.components.runtime.util.HashFile r0 = r0.getHashFile(r7)     // Catch:{ Exception -> 0x0354, all -> 0x034e }
            r6 = r0
            if (r6 == 0) goto L_0x00d4
            boolean r0 = r8.exists()     // Catch:{ Exception -> 0x00cf, all -> 0x00ca }
            if (r0 == 0) goto L_0x00d4
            java.lang.String r0 = "If-None-Match"
            java.lang.String r15 = r6.getHash()     // Catch:{ Exception -> 0x00cf, all -> 0x00ca }
            r10.addRequestProperty(r0, r15)     // Catch:{ Exception -> 0x00cf, all -> 0x00ca }
            goto L_0x00d4
        L_0x00ca:
            r0 = move-exception
            r17 = r5
            goto L_0x0383
        L_0x00cf:
            r0 = move-exception
            r17 = r5
            goto L_0x0359
        L_0x00d4:
            java.lang.String r0 = "GET"
            r10.setRequestMethod(r0)     // Catch:{ Exception -> 0x0354, all -> 0x034e }
            int r0 = r10.getResponseCode()     // Catch:{ Exception -> 0x0354, all -> 0x034e }
            r11 = r0
            java.lang.String r0 = LOG_TAG     // Catch:{ Exception -> 0x02fd, all -> 0x02f4 }
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02fd, all -> 0x02f4 }
            r15.<init>()     // Catch:{ Exception -> 0x02fd, all -> 0x02f4 }
            r17 = r5
            java.lang.String r5 = "asset = "
            java.lang.StringBuilder r5 = r15.append(r5)     // Catch:{ Exception -> 0x02ed, all -> 0x02e6 }
            java.lang.StringBuilder r5 = r5.append(r3)     // Catch:{ Exception -> 0x02ed, all -> 0x02e6 }
            java.lang.String r15 = " responseCode = "
            java.lang.StringBuilder r5 = r5.append(r15)     // Catch:{ Exception -> 0x02ed, all -> 0x02e6 }
            java.lang.StringBuilder r5 = r5.append(r11)     // Catch:{ Exception -> 0x02ed, all -> 0x02e6 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x02ed, all -> 0x02e6 }
            android.util.Log.d(r0, r5)     // Catch:{ Exception -> 0x02ed, all -> 0x02e6 }
            java.io.File r0 = r8.getParentFile()     // Catch:{ Exception -> 0x02ed, all -> 0x02e6 }
            r5 = r0
            java.lang.String r0 = "ETag"
            java.lang.String r0 = r10.getHeaderField(r0)     // Catch:{ Exception -> 0x02ed, all -> 0x02e6 }
            r12 = r0
            r0 = 304(0x130, float:4.26E-43)
            if (r11 != r0) goto L_0x011e
            if (r9 == 0) goto L_0x0118
            r8.setReadOnly()
        L_0x0118:
            if (r10 == 0) goto L_0x011d
            r10.disconnect()
        L_0x011d:
            return r8
        L_0x011e:
            if (r5 == 0) goto L_0x02b3
            boolean r0 = r5.exists()     // Catch:{ Exception -> 0x02aa, all -> 0x02a1 }
            if (r0 != 0) goto L_0x0137
            boolean r0 = r5.mkdirs()     // Catch:{ Exception -> 0x031e }
            if (r0 == 0) goto L_0x012d
            goto L_0x0137
        L_0x012d:
            r16 = r6
            r19 = r11
            r18 = r12
            r20 = r13
            goto L_0x02bb
        L_0x0137:
            boolean r0 = r8.canWrite()     // Catch:{ Exception -> 0x02aa, all -> 0x02a1 }
            if (r0 != 0) goto L_0x0141
            r0 = 1
            r8.setWritable(r0)     // Catch:{ Exception -> 0x031e }
        L_0x0141:
            java.io.BufferedInputStream r0 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x02aa, all -> 0x02a1 }
            java.io.InputStream r15 = r10.getInputStream()     // Catch:{ Exception -> 0x02aa, all -> 0x02a1 }
            r16 = r6
            r6 = 4096(0x1000, float:5.74E-42)
            r0.<init>(r15, r6)     // Catch:{ Exception -> 0x02aa, all -> 0x02a1 }
            r15 = r0
            java.io.BufferedOutputStream r0 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x02aa, all -> 0x02a1 }
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x02aa, all -> 0x02a1 }
            r6.<init>(r8)     // Catch:{ Exception -> 0x02aa, all -> 0x02a1 }
            r19 = r11
            r11 = 4096(0x1000, float:5.74E-42)
            r0.<init>(r6, r11)     // Catch:{ Exception -> 0x0298, all -> 0x028f }
            r6 = r0
        L_0x015e:
            int r0 = r15.read()     // Catch:{ IOException -> 0x01d1, all -> 0x01ca }
            r11 = -1
            if (r0 != r11) goto L_0x01bc
            r6.flush()     // Catch:{ IOException -> 0x01d1, all -> 0x01ca }
            r6.close()     // Catch:{ Exception -> 0x0298, all -> 0x028f }
            if (r9 == 0) goto L_0x01b4
            java.lang.String r0 = LOG_TAG     // Catch:{ Exception -> 0x0298, all -> 0x028f }
            java.lang.String r11 = r8.getAbsolutePath()     // Catch:{ Exception -> 0x0298, all -> 0x028f }
            r18 = r12
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0239, all -> 0x0230 }
            r12.<init>()     // Catch:{ Exception -> 0x0239, all -> 0x0230 }
            r20 = r13
            java.lang.String r13 = "Making file read-only: "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.StringBuilder r11 = r12.append(r11)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            android.util.Log.i(r0, r11)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            boolean r0 = r8.setReadOnly()     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            if (r0 == 0) goto L_0x0195
        L_0x0194:
            goto L_0x01b8
        L_0x0195:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            r11.<init>()     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.String r12 = "Unable to make "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.StringBuilder r11 = r11.append(r8)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.String r12 = " read-only."
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            r0.<init>(r11)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
        L_0x01b3:
            throw r0     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
        L_0x01b4:
            r18 = r12
            r20 = r13
        L_0x01b8:
            r13 = r20
            goto L_0x022a
        L_0x01bc:
            r18 = r12
            r20 = r13
            r6.write(r0)     // Catch:{ IOException -> 0x01c8 }
            r12 = r18
            r13 = r20
            goto L_0x015e
        L_0x01c8:
            r0 = move-exception
            goto L_0x01d6
        L_0x01ca:
            r0 = move-exception
            r18 = r12
            r20 = r13
            goto L_0x0243
        L_0x01d1:
            r0 = move-exception
            r18 = r12
            r20 = r13
        L_0x01d6:
            java.lang.String r11 = LOG_TAG     // Catch:{ all -> 0x0242 }
            java.lang.String r12 = "copying assets"
            android.util.Log.e(r11, r12, r0)     // Catch:{ all -> 0x0242 }
            r13 = 1
            r6.close()     // Catch:{ Exception -> 0x0239, all -> 0x0230 }
            if (r9 == 0) goto L_0x0227
            java.lang.String r0 = LOG_TAG     // Catch:{ Exception -> 0x0239, all -> 0x0230 }
            java.lang.String r11 = r8.getAbsolutePath()     // Catch:{ Exception -> 0x0239, all -> 0x0230 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0239, all -> 0x0230 }
            r12.<init>()     // Catch:{ Exception -> 0x0239, all -> 0x0230 }
            r20 = r13
            java.lang.String r13 = "Making file read-only: "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.StringBuilder r11 = r12.append(r11)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            android.util.Log.i(r0, r11)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            boolean r0 = r8.setReadOnly()     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            if (r0 == 0) goto L_0x0208
            goto L_0x0194
        L_0x0208:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            r11.<init>()     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.String r12 = "Unable to make "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.StringBuilder r11 = r11.append(r8)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.String r12 = " read-only."
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            r0.<init>(r11)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            goto L_0x01b3
        L_0x0227:
            r20 = r13
            goto L_0x01b8
        L_0x022a:
            r12 = r18
            r11 = r19
            goto L_0x030b
        L_0x0230:
            r0 = move-exception
            r20 = r13
            r12 = r18
            r11 = r19
            goto L_0x0383
        L_0x0239:
            r0 = move-exception
            r20 = r13
            r12 = r18
            r11 = r19
            goto L_0x0359
        L_0x0242:
            r0 = move-exception
        L_0x0243:
            r6.close()     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            if (r9 == 0) goto L_0x028b
            java.lang.String r11 = LOG_TAG     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.String r12 = r8.getAbsolutePath()     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            r13.<init>()     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            r21 = r6
            java.lang.String r6 = "Making file read-only: "
            java.lang.StringBuilder r6 = r13.append(r6)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.StringBuilder r6 = r6.append(r12)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            android.util.Log.i(r11, r6)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            boolean r6 = r8.setReadOnly()     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            if (r6 != 0) goto L_0x028d
            java.io.IOException r0 = new java.io.IOException     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            r6.<init>()     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.String r11 = "Unable to make "
            java.lang.StringBuilder r6 = r6.append(r11)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.StringBuilder r6 = r6.append(r8)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.String r11 = " read-only."
            java.lang.StringBuilder r6 = r6.append(r11)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            r0.<init>(r6)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            throw r0     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
        L_0x028b:
            r21 = r6
        L_0x028d:
            throw r0     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
        L_0x028f:
            r0 = move-exception
            r18 = r12
            r20 = r13
            r11 = r19
            goto L_0x0383
        L_0x0298:
            r0 = move-exception
            r18 = r12
            r20 = r13
            r11 = r19
            goto L_0x0359
        L_0x02a1:
            r0 = move-exception
            r19 = r11
            r18 = r12
            r20 = r13
            goto L_0x0383
        L_0x02aa:
            r0 = move-exception
            r19 = r11
            r18 = r12
            r20 = r13
            goto L_0x0359
        L_0x02b3:
            r16 = r6
            r19 = r11
            r18 = r12
            r20 = r13
        L_0x02bb:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            r6.<init>()     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.String r11 = "Unable to create assets directory "
            java.lang.StringBuilder r6 = r6.append(r11)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.StringBuilder r6 = r6.append(r5)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            r0.<init>(r6)     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
            throw r0     // Catch:{ Exception -> 0x02dd, all -> 0x02d4 }
        L_0x02d4:
            r0 = move-exception
            r12 = r18
            r11 = r19
            r13 = r20
            goto L_0x0383
        L_0x02dd:
            r0 = move-exception
            r12 = r18
            r11 = r19
            r13 = r20
            goto L_0x0359
        L_0x02e6:
            r0 = move-exception
            r19 = r11
            r20 = r13
            goto L_0x0383
        L_0x02ed:
            r0 = move-exception
            r19 = r11
            r20 = r13
            goto L_0x0359
        L_0x02f4:
            r0 = move-exception
            r17 = r5
            r19 = r11
            r20 = r13
            goto L_0x0383
        L_0x02fd:
            r0 = move-exception
            r17 = r5
            r19 = r11
            r20 = r13
            goto L_0x0359
        L_0x0305:
            r17 = r5
            r20 = r13
            r0 = 1
            r13 = r0
        L_0x030b:
            if (r13 == 0) goto L_0x0320
            int r0 = r4 + 1
            java.io.File r0 = getFile(r1, r2, r3, r0)     // Catch:{ Exception -> 0x031e }
            if (r9 == 0) goto L_0x0318
            r8.setReadOnly()
        L_0x0318:
            if (r10 == 0) goto L_0x031d
            r10.disconnect()
        L_0x031d:
            return r0
        L_0x031e:
            r0 = move-exception
            goto L_0x0359
        L_0x0320:
            if (r9 == 0) goto L_0x0325
            r8.setReadOnly()
        L_0x0325:
            if (r10 == 0) goto L_0x032a
            r10.disconnect()
        L_0x032a:
            r0 = 200(0xc8, float:2.8E-43)
            if (r11 != r0) goto L_0x034c
            java.util.Date r0 = new java.util.Date
            r0.<init>()
            com.google.appinventor.components.runtime.util.HashFile r5 = new com.google.appinventor.components.runtime.util.HashFile
            r5.<init>((java.lang.String) r7, (java.lang.String) r12, (java.util.Date) r0)
            com.google.appinventor.components.runtime.util.HashDatabase r6 = db
            com.google.appinventor.components.runtime.util.HashFile r6 = r6.getHashFile(r7)
            if (r6 != 0) goto L_0x0346
            com.google.appinventor.components.runtime.util.HashDatabase r6 = db
            r6.insertHashFile(r5)
            goto L_0x034b
        L_0x0346:
            com.google.appinventor.components.runtime.util.HashDatabase r6 = db
            r6.updateHashFile(r5)
        L_0x034b:
            return r8
        L_0x034c:
            r5 = 0
            return r5
        L_0x034e:
            r0 = move-exception
            r17 = r5
            r20 = r13
            goto L_0x0383
        L_0x0354:
            r0 = move-exception
            r17 = r5
            r20 = r13
        L_0x0359:
            java.lang.String r5 = LOG_TAG     // Catch:{ all -> 0x0382 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0382 }
            r6.<init>()     // Catch:{ all -> 0x0382 }
            java.lang.String r14 = "Exception while fetching "
            java.lang.StringBuilder r6 = r6.append(r14)     // Catch:{ all -> 0x0382 }
            java.lang.StringBuilder r6 = r6.append(r1)     // Catch:{ all -> 0x0382 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0382 }
            android.util.Log.e(r5, r6, r0)     // Catch:{ all -> 0x0382 }
            int r5 = r4 + 1
            java.io.File r5 = getFile(r1, r2, r3, r5)     // Catch:{ all -> 0x0382 }
            if (r9 == 0) goto L_0x037c
            r8.setReadOnly()
        L_0x037c:
            if (r10 == 0) goto L_0x0381
            r10.disconnect()
        L_0x0381:
            return r5
        L_0x0382:
            r0 = move-exception
        L_0x0383:
            if (r9 == 0) goto L_0x0388
            r8.setReadOnly()
        L_0x0388:
            if (r10 == 0) goto L_0x038d
            r10.disconnect()
        L_0x038d:
            goto L_0x038f
        L_0x038e:
            throw r0
        L_0x038f:
            goto L_0x038e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.AssetFetcher.getFile(java.lang.String, java.lang.String, java.lang.String, int):java.io.File");
    }

    private static File getDestinationFile(Form form, String asset) {
        String filename;
        if (!asset.contains("/external_comps/") || Build.VERSION.SDK_INT < 34) {
            return new File(QUtil.getReplAssetPath(form, true), asset.substring("assets/".length()));
        }
        File dest = new File(form.getCacheDir(), asset.substring("assets/".length()));
        File parent = dest.getParentFile();
        if (parent == null) {
            throw new IllegalStateException("Unable to determine parent directory for " + dest);
        } else if (parent.exists() || parent.mkdirs()) {
            if (asset.endsWith("/classes.jar")) {
                filename = parent.getName() + ".jar";
            } else {
                String[] parts = asset.split("/");
                filename = parts[parts.length - 1];
            }
            return new File(parent, filename);
        } else {
            throw new YailRuntimeError("Unable to create directory " + parent, "Extensions");
        }
    }

    private static String byteArray2Hex(byte[] hash) {
        Formatter formatter = new Formatter();
        int length = hash.length;
        for (int i = 0; i < length; i++) {
            formatter.format("%02x", new Object[]{Byte.valueOf(hash[i])});
        }
        return formatter.toString();
    }
}
