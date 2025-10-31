package com.google.appinventor.components.runtime.util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.appinventor.components.runtime.ReplForm;
import com.google.appinventor.components.runtime.util.NanoHTTPD;
import gnu.expr.Language;
import gnu.expr.ModuleExp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import kawa.standard.Scheme;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppInvHTTPD extends NanoHTTPD {
    private static final String LOG_TAG = "AppInvHTTPD";
    private static final String MIME_JSON = "application/json";
    private static final int YAV_SKEW_BACKWARD = 4;
    private static final int YAV_SKEW_FORWARD = 1;
    private static byte[] hmacKey;
    private static int seq;
    private final Handler androidUIHandler = new Handler();
    /* access modifiers changed from: private */
    public ReplForm form;
    private File rootDir;
    private Language scheme;
    private boolean secure;

    public AppInvHTTPD(int port, File wwwroot, boolean secure2, ReplForm form2) throws IOException {
        super(port, wwwroot);
        this.rootDir = wwwroot;
        this.scheme = Scheme.getInstance("scheme");
        this.form = form2;
        this.secure = secure2;
        ModuleExp.mustNeverCompile();
    }

    /* JADX WARNING: Removed duplicated region for block: B:89:0x040f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.appinventor.components.runtime.util.NanoHTTPD.Response serve(java.lang.String r33, java.lang.String r34, java.util.Properties r35, java.util.Properties r36, java.util.Properties r37, java.net.Socket r38) {
        /*
            r32 = this;
            r1 = r32
            r2 = r33
            r3 = r34
            r4 = r35
            r5 = r36
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.String r6 = " '"
            java.lang.StringBuilder r0 = r0.append(r6)
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r6 = "' "
            java.lang.StringBuilder r0 = r0.append(r6)
            java.lang.String r0 = r0.toString()
            java.lang.String r6 = "AppInvHTTPD"
            android.util.Log.d(r6, r0)
            boolean r0 = r1.secure
            java.lang.String r7 = "Allow"
            java.lang.String r8 = "Access-Control-Allow-Methods"
            java.lang.String r9 = "origin, content-type"
            java.lang.String r10 = "Access-Control-Allow-Headers"
            java.lang.String r11 = "*"
            java.lang.String r12 = "Access-Control-Allow-Origin"
            java.lang.String r13 = "application/json"
            java.lang.String r14 = "200 OK"
            java.lang.String r15 = "POST,OPTIONS,GET,HEAD,PUT"
            if (r0 == 0) goto L_0x009b
            java.net.InetAddress r0 = r38.getInetAddress()
            java.lang.String r5 = r0.getHostAddress()
            r16 = r0
            java.lang.String r0 = "127.0.0.1"
            boolean r0 = r5.equals(r0)
            if (r0 != 0) goto L_0x009b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "Debug: hostAddress = "
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.StringBuilder r0 = r0.append(r5)
            java.lang.String r2 = " while in secure mode, closing connection."
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r6, r0)
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r0 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r6 = "{\"status\" : \"BAD\", \"message\" : \"Security Error: Invalid Source Location "
            java.lang.StringBuilder r2 = r2.append(r6)
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.String r6 = "\"}"
            java.lang.StringBuilder r2 = r2.append(r6)
            java.lang.String r2 = r2.toString()
            r0.<init>((java.lang.String) r14, (java.lang.String) r13, (java.lang.String) r2)
            r0.addHeader(r12, r11)
            r0.addHeader(r10, r9)
            r0.addHeader(r8, r15)
            r0.addHeader(r7, r15)
            return r0
        L_0x009b:
            java.lang.String r0 = "OPTIONS"
            boolean r0 = r3.equals(r0)
            java.lang.String r2 = "OK"
            java.lang.String r5 = "text/plain"
            if (r0 == 0) goto L_0x00ff
            java.util.Enumeration r0 = r35.propertyNames()
        L_0x00ab:
            boolean r13 = r0.hasMoreElements()
            if (r13 == 0) goto L_0x00eb
            java.lang.Object r13 = r0.nextElement()
            java.lang.String r13 = (java.lang.String) r13
            r16 = r0
            java.lang.String r0 = r4.getProperty(r13)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r3 = "  HDR: '"
            java.lang.StringBuilder r3 = r4.append(r3)
            java.lang.StringBuilder r3 = r3.append(r13)
            java.lang.String r4 = "' = '"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r0 = r3.append(r0)
            java.lang.String r3 = "'"
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r6, r0)
            r3 = r34
            r4 = r35
            r0 = r16
            goto L_0x00ab
        L_0x00eb:
            r16 = r0
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r0 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            r0.<init>((java.lang.String) r14, (java.lang.String) r5, (java.lang.String) r2)
            r0.addHeader(r12, r11)
            r0.addHeader(r10, r9)
            r0.addHeader(r8, r15)
            r0.addHeader(r7, r15)
            return r0
        L_0x00ff:
            java.lang.String r0 = "/_newblocks"
            r3 = r33
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0350
            r32.adoptMainThreadClassLoader()
            java.lang.String r0 = "seq"
            java.lang.String r2 = "0"
            r4 = r36
            java.lang.String r2 = r4.getProperty(r0, r2)
            int r3 = java.lang.Integer.parseInt(r2)
            java.lang.String r0 = "blockid"
            r17 = r5
            java.lang.String r5 = r4.getProperty(r0)
            java.lang.String r0 = "code"
            r18 = r7
            java.lang.String r7 = r4.getProperty(r0)
            java.lang.String r0 = "mac"
            r19 = r8
            java.lang.String r8 = "no key provided"
            java.lang.String r8 = r4.getProperty(r0, r8)
            java.lang.String r20 = ""
            r21 = r7
            byte[] r0 = hmacKey
            if (r0 == 0) goto L_0x0334
            java.lang.String r0 = "HmacSHA1"
            javax.crypto.Mac r0 = javax.crypto.Mac.getInstance(r0)     // Catch:{ Exception -> 0x0311 }
            javax.crypto.spec.SecretKeySpec r4 = new javax.crypto.spec.SecretKeySpec     // Catch:{ Exception -> 0x0311 }
            r22 = r15
            byte[] r15 = hmacKey     // Catch:{ Exception -> 0x0311 }
            r23 = r9
            java.lang.String r9 = "RAW"
            r4.<init>(r15, r9)     // Catch:{ Exception -> 0x0311 }
            r0.init(r4)     // Catch:{ Exception -> 0x0311 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0311 }
            r9.<init>()     // Catch:{ Exception -> 0x0311 }
            java.lang.StringBuilder r9 = r9.append(r7)     // Catch:{ Exception -> 0x0311 }
            java.lang.StringBuilder r9 = r9.append(r2)     // Catch:{ Exception -> 0x0311 }
            java.lang.StringBuilder r9 = r9.append(r5)     // Catch:{ Exception -> 0x0311 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0311 }
            byte[] r9 = r9.getBytes()     // Catch:{ Exception -> 0x0311 }
            byte[] r9 = r0.doFinal(r9)     // Catch:{ Exception -> 0x0311 }
            java.lang.StringBuffer r15 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x0311 }
            r24 = r0
            int r0 = r9.length     // Catch:{ Exception -> 0x0311 }
            int r0 = r0 * 2
            r15.<init>(r0)     // Catch:{ Exception -> 0x0311 }
            r0 = r15
            java.util.Formatter r15 = new java.util.Formatter     // Catch:{ Exception -> 0x0311 }
            r15.<init>(r0)     // Catch:{ Exception -> 0x0311 }
            r25 = r4
            int r4 = r9.length     // Catch:{ Exception -> 0x0311 }
            r26 = r10
            r10 = 0
        L_0x0185:
            if (r10 >= r4) goto L_0x01ad
            byte r27 = r9[r10]     // Catch:{ Exception -> 0x01a8 }
            r28 = r4
            java.lang.String r4 = "%02x"
            java.lang.Byte r29 = java.lang.Byte.valueOf(r27)     // Catch:{ Exception -> 0x01a8 }
            r30 = r9
            r31 = r11
            r9 = 1
            java.lang.Object[] r11 = new java.lang.Object[r9]     // Catch:{ Exception -> 0x01a8 }
            r9 = 0
            r11[r9] = r29     // Catch:{ Exception -> 0x01a8 }
            r15.format(r4, r11)     // Catch:{ Exception -> 0x01a8 }
            int r10 = r10 + 1
            r4 = r28
            r9 = r30
            r11 = r31
            goto L_0x0185
        L_0x01a8:
            r0 = move-exception
            r9 = r21
            goto L_0x0314
        L_0x01ad:
            r30 = r9
            r31 = r11
            java.lang.String r4 = r0.toString()     // Catch:{ Exception -> 0x0311 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r9 = "Incoming Mac = "
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.StringBuilder r0 = r0.append(r8)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r6, r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r9 = "Computed Mac = "
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.StringBuilder r0 = r0.append(r4)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r6, r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r9 = "Incoming seq = "
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r6, r0)
            int r0 = seq
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Computed seq = "
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.StringBuilder r0 = r9.append(r0)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r6, r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r9 = "blockid = "
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.StringBuilder r0 = r0.append(r5)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r6, r0)
            boolean r0 = r8.equals(r4)
            if (r0 != 0) goto L_0x024a
            java.lang.String r0 = "Hmac does not match"
            android.util.Log.e(r6, r0)
            com.google.appinventor.components.runtime.ReplForm r0 = r1.form
            com.google.appinventor.components.runtime.ReplForm r9 = r1.form
            r10 = 1
            java.lang.Object[] r10 = new java.lang.Object[r10]
            java.lang.String r11 = "Invalid HMAC"
            r12 = 0
            r10[r12] = r11
            r11 = 1801(0x709, float:2.524E-42)
            r0.dispatchErrorOccurredEvent(r9, r6, r11, r10)
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r0 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            java.lang.String r6 = "{\"status\" : \"BAD\", \"message\" : \"Security Error: Invalid MAC\"}"
            r0.<init>((java.lang.String) r14, (java.lang.String) r13, (java.lang.String) r6)
            return r0
        L_0x024a:
            int r0 = seq
            if (r0 == r3) goto L_0x0272
            int r0 = seq
            int r9 = r3 + 1
            if (r0 == r9) goto L_0x0272
            java.lang.String r0 = "Seq does not match"
            android.util.Log.e(r6, r0)
            com.google.appinventor.components.runtime.ReplForm r0 = r1.form
            com.google.appinventor.components.runtime.ReplForm r9 = r1.form
            r10 = 1
            java.lang.Object[] r10 = new java.lang.Object[r10]
            java.lang.String r11 = "Invalid Seq"
            r12 = 0
            r10[r12] = r11
            r11 = 1801(0x709, float:2.524E-42)
            r0.dispatchErrorOccurredEvent(r9, r6, r11, r10)
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r0 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            java.lang.String r6 = "{\"status\" : \"BAD\", \"message\" : \"Security Error: Invalid Seq\"}"
            r0.<init>((java.lang.String) r14, (java.lang.String) r13, (java.lang.String) r6)
            return r0
        L_0x0272:
            int r0 = seq
            int r9 = r3 + 1
            if (r0 != r9) goto L_0x027d
            java.lang.String r0 = "Seq Fixup Invoked"
            android.util.Log.e(r6, r0)
        L_0x027d:
            int r0 = r3 + 1
            seq = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r9 = "(begin (require <com.google.youngandroid.runtime>) (process-repl-input "
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.StringBuilder r0 = r0.append(r5)
            java.lang.String r9 = " (begin "
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.StringBuilder r0 = r0.append(r7)
            java.lang.String r9 = " )))"
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.String r7 = r0.toString()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r9 = "To Eval: "
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.StringBuilder r0 = r0.append(r7)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r6, r0)
            java.lang.String r0 = "#f"
            r9 = r21
            boolean r0 = r9.equals(r0)     // Catch:{ all -> 0x02da }
            if (r0 == 0) goto L_0x02ca
            java.lang.String r0 = "Skipping evaluation of #f"
            android.util.Log.e(r6, r0)     // Catch:{ all -> 0x02da }
            goto L_0x02cf
        L_0x02ca:
            gnu.expr.Language r0 = r1.scheme     // Catch:{ all -> 0x02da }
            r0.eval((java.lang.String) r7)     // Catch:{ all -> 0x02da }
        L_0x02cf:
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r0 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response     // Catch:{ all -> 0x02da }
            r10 = 0
            java.lang.String r11 = com.google.appinventor.components.runtime.util.RetValManager.fetch(r10)     // Catch:{ all -> 0x02da }
            r0.<init>((java.lang.String) r14, (java.lang.String) r13, (java.lang.String) r11)     // Catch:{ all -> 0x02da }
            goto L_0x02f8
        L_0x02da:
            r0 = move-exception
            goto L_0x02df
        L_0x02dc:
            r0 = move-exception
            r9 = r21
        L_0x02df:
            java.lang.String r10 = "newblocks: Scheme Failure"
            android.util.Log.e(r6, r10, r0)
            java.lang.String r6 = "BAD"
            java.lang.String r10 = r0.toString()
            com.google.appinventor.components.runtime.util.RetValManager.appendReturnValue(r5, r6, r10)
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r6 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            r10 = 0
            java.lang.String r10 = com.google.appinventor.components.runtime.util.RetValManager.fetch(r10)
            r6.<init>((java.lang.String) r14, (java.lang.String) r13, (java.lang.String) r10)
            r0 = r6
        L_0x02f8:
            r10 = r31
            r0.addHeader(r12, r10)
            r11 = r23
            r15 = r26
            r0.addHeader(r15, r11)
            r6 = r19
            r10 = r22
            r0.addHeader(r6, r10)
            r6 = r18
            r0.addHeader(r6, r10)
            return r0
        L_0x0311:
            r0 = move-exception
            r9 = r21
        L_0x0314:
            java.lang.String r4 = "Error working with hmac"
            android.util.Log.e(r6, r4, r0)
            com.google.appinventor.components.runtime.ReplForm r4 = r1.form
            com.google.appinventor.components.runtime.ReplForm r10 = r1.form
            r11 = 1
            java.lang.Object[] r11 = new java.lang.Object[r11]
            java.lang.String r12 = "Exception working on HMAC"
            r13 = 0
            r11[r13] = r12
            r12 = 1801(0x709, float:2.524E-42)
            r4.dispatchErrorOccurredEvent(r10, r6, r12, r11)
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r4 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            java.lang.String r6 = "NOT"
            r10 = r17
            r4.<init>((java.lang.String) r14, (java.lang.String) r10, (java.lang.String) r6)
            return r4
        L_0x0334:
            java.lang.String r0 = "No HMAC Key"
            android.util.Log.e(r6, r0)
            com.google.appinventor.components.runtime.ReplForm r4 = r1.form
            com.google.appinventor.components.runtime.ReplForm r10 = r1.form
            r11 = 1
            java.lang.Object[] r11 = new java.lang.Object[r11]
            r12 = 0
            r11[r12] = r0
            r12 = 1801(0x709, float:2.524E-42)
            r4.dispatchErrorOccurredEvent(r10, r6, r12, r11)
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r0 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            java.lang.String r4 = "{\"status\" : \"BAD\", \"message\" : \"Security Error: No HMAC Key\"}"
            r0.<init>((java.lang.String) r14, (java.lang.String) r13, (java.lang.String) r4)
            return r0
        L_0x0350:
            r3 = r5
            r4 = r7
            r5 = r8
            r7 = r15
            r15 = r10
            r10 = r11
            r11 = r9
            java.lang.String r0 = "/_values"
            r8 = r33
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x0378
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r0 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            r2 = 1
            java.lang.String r2 = com.google.appinventor.components.runtime.util.RetValManager.fetch(r2)
            r0.<init>((java.lang.String) r14, (java.lang.String) r13, (java.lang.String) r2)
            r0.addHeader(r12, r10)
            r0.addHeader(r15, r11)
            r0.addHeader(r5, r7)
            r0.addHeader(r4, r7)
            return r0
        L_0x0378:
            java.lang.String r0 = "/_getversion"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x041d
            com.google.appinventor.components.runtime.ReplForm r0 = r1.form     // Catch:{ NameNotFoundException -> 0x03ef }
            java.lang.String r0 = r0.getPackageName()     // Catch:{ NameNotFoundException -> 0x03ef }
            com.google.appinventor.components.runtime.ReplForm r2 = r1.form     // Catch:{ NameNotFoundException -> 0x03ef }
            android.content.pm.PackageManager r2 = r2.getPackageManager()     // Catch:{ NameNotFoundException -> 0x03ef }
            r3 = 0
            android.content.pm.PackageInfo r2 = r2.getPackageInfo(r0, r3)     // Catch:{ NameNotFoundException -> 0x03ef }
            int r3 = com.google.appinventor.components.runtime.util.SdkLevel.getLevel()     // Catch:{ NameNotFoundException -> 0x03ef }
            r6 = 5
            java.lang.String r9 = "Not Known"
            if (r3 < r6) goto L_0x03a3
            java.lang.String r3 = "edu.mit.appinventor.aicompanion3"
            com.google.appinventor.components.runtime.ReplForm r6 = r1.form     // Catch:{ NameNotFoundException -> 0x03ef }
            java.lang.String r3 = com.google.appinventor.components.runtime.util.EclairUtil.getInstallerPackageName(r3, r6)     // Catch:{ NameNotFoundException -> 0x03ef }
            goto L_0x03a4
        L_0x03a3:
            r3 = r9
        L_0x03a4:
            java.lang.String r6 = r2.versionName     // Catch:{ NameNotFoundException -> 0x03ef }
            if (r3 != 0) goto L_0x03a9
            r3 = r9
        L_0x03a9:
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r9 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response     // Catch:{ NameNotFoundException -> 0x03ef }
            r16 = r2
            java.lang.String r2 = android.os.Build.FINGERPRINT     // Catch:{ NameNotFoundException -> 0x03ef }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ NameNotFoundException -> 0x03ef }
            r8.<init>()     // Catch:{ NameNotFoundException -> 0x03ef }
            r18 = r4
            java.lang.String r4 = "{\"version\" : \""
            java.lang.StringBuilder r4 = r8.append(r4)     // Catch:{ NameNotFoundException -> 0x03ed }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ NameNotFoundException -> 0x03ed }
            java.lang.String r8 = "\", \"fingerprint\" : \""
            java.lang.StringBuilder r4 = r4.append(r8)     // Catch:{ NameNotFoundException -> 0x03ed }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ NameNotFoundException -> 0x03ed }
            java.lang.String r4 = "\", \"installer\" : \""
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ NameNotFoundException -> 0x03ed }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ NameNotFoundException -> 0x03ed }
            java.lang.String r4 = "\", \"package\" : \""
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ NameNotFoundException -> 0x03ed }
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ NameNotFoundException -> 0x03ed }
            java.lang.String r4 = "\", \"fqcn\" : true }"
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ NameNotFoundException -> 0x03ed }
            java.lang.String r2 = r2.toString()     // Catch:{ NameNotFoundException -> 0x03ed }
            r9.<init>((java.lang.String) r14, (java.lang.String) r13, (java.lang.String) r2)     // Catch:{ NameNotFoundException -> 0x03ed }
            r0 = r9
            goto L_0x03fd
        L_0x03ed:
            r0 = move-exception
            goto L_0x03f2
        L_0x03ef:
            r0 = move-exception
            r18 = r4
        L_0x03f2:
            r0.printStackTrace()
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r2 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            java.lang.String r3 = "{\"verison\" : \"Unknown\""
            r2.<init>((java.lang.String) r14, (java.lang.String) r13, (java.lang.String) r3)
            r0 = r2
        L_0x03fd:
            r0.addHeader(r12, r10)
            r0.addHeader(r15, r11)
            r0.addHeader(r5, r7)
            r4 = r18
            r0.addHeader(r4, r7)
            boolean r2 = r1.secure
            if (r2 == 0) goto L_0x041c
            r2 = 1
            seq = r2
            android.os.Handler r2 = r1.androidUIHandler
            com.google.appinventor.components.runtime.util.AppInvHTTPD$1 r3 = new com.google.appinventor.components.runtime.util.AppInvHTTPD$1
            r3.<init>()
            r2.post(r3)
        L_0x041c:
            return r0
        L_0x041d:
            java.lang.String r0 = "/_extensions"
            r8 = r33
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x042e
            r9 = r36
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r0 = r1.processLoadExtensionsRequest(r9)
            return r0
        L_0x042e:
            r9 = r36
            java.lang.String r0 = "/_proxy"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x0450
            java.lang.String r0 = com.google.appinventor.components.runtime.PhoneStatus.getPopup()
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r2 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            java.lang.String r3 = "text/html"
            r2.<init>((java.lang.String) r14, (java.lang.String) r3, (java.lang.String) r0)
            r2.addHeader(r12, r10)
            r2.addHeader(r15, r11)
            r2.addHeader(r5, r7)
            r2.addHeader(r4, r7)
            return r2
        L_0x0450:
            java.lang.String r0 = "PUT"
            r13 = r34
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x0534
            r16 = 0
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r16)
            r16 = r0
            java.lang.String r0 = "content"
            r13 = 0
            r8 = r37
            java.lang.String r0 = r8.getProperty(r0, r13)
            if (r0 == 0) goto L_0x04fa
            java.io.File r13 = new java.io.File
            r13.<init>(r0)
            r18 = r0
            java.lang.String r0 = "filename"
            r8 = 0
            java.lang.String r0 = r9.getProperty(r0, r8)
            if (r0 == 0) goto L_0x04aa
            java.lang.String r8 = ".."
            boolean r17 = r0.startsWith(r8)
            if (r17 != 0) goto L_0x0493
            boolean r8 = r0.endsWith(r8)
            if (r8 != 0) goto L_0x0493
            java.lang.String r8 = "../"
            int r8 = r0.indexOf(r8)
            if (r8 < 0) goto L_0x04aa
        L_0x0493:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = " Ignoring invalid filename: "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r8 = r8.append(r0)
            java.lang.String r8 = r8.toString()
            android.util.Log.d(r6, r8)
            r0 = 0
        L_0x04aa:
            if (r0 == 0) goto L_0x04ec
            java.io.File r6 = new java.io.File
            java.io.File r8 = r1.rootDir
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.StringBuilder r8 = r9.append(r8)
            java.lang.String r9 = "/"
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r8 = r8.append(r0)
            java.lang.String r8 = r8.toString()
            r6.<init>(r8)
            java.io.File r8 = r6.getParentFile()
            boolean r9 = r8.exists()
            if (r9 != 0) goto L_0x04d7
            r8.mkdirs()
        L_0x04d7:
            boolean r9 = r13.renameTo(r6)
            if (r9 != 0) goto L_0x04e9
            boolean r9 = r1.copyFile(r13, r6)
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r9)
            r13.delete()
            goto L_0x04eb
        L_0x04e9:
            r9 = r16
        L_0x04eb:
            goto L_0x04f9
        L_0x04ec:
            r13.delete()
            java.lang.String r8 = "Received content without a file name!"
            android.util.Log.e(r6, r8)
            r8 = 1
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r8)
        L_0x04f9:
            goto L_0x0506
        L_0x04fa:
            r18 = r0
            r8 = 1
            java.lang.String r0 = "Received PUT without content."
            android.util.Log.e(r6, r0)
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r8)
        L_0x0506:
            boolean r0 = r9.booleanValue()
            if (r0 == 0) goto L_0x0522
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r0 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            java.lang.String r2 = "500 Internal Server Error"
            java.lang.String r6 = "NOTOK"
            r0.<init>((java.lang.String) r2, (java.lang.String) r3, (java.lang.String) r6)
            r0.addHeader(r12, r10)
            r0.addHeader(r15, r11)
            r0.addHeader(r5, r7)
            r0.addHeader(r4, r7)
            return r0
        L_0x0522:
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r0 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            r0.<init>((java.lang.String) r14, (java.lang.String) r3, (java.lang.String) r2)
            r0.addHeader(r12, r10)
            r0.addHeader(r15, r11)
            r0.addHeader(r5, r7)
            r0.addHeader(r4, r7)
            return r0
        L_0x0534:
            java.io.File r0 = r1.rootDir
            r2 = r33
            r3 = r35
            r4 = 1
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r0 = r1.serveFile(r2, r3, r0, r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.AppInvHTTPD.serve(java.lang.String, java.lang.String, java.util.Properties, java.util.Properties, java.util.Properties, java.net.Socket):com.google.appinventor.components.runtime.util.NanoHTTPD$Response");
    }

    private boolean copyFile(File infile, File outfile) {
        try {
            FileInputStream in = new FileInputStream(infile);
            FileOutputStream out = new FileOutputStream(outfile);
            byte[] buffer = new byte[32768];
            while (true) {
                int read = in.read(buffer);
                int len = read;
                if (read > 0) {
                    out.write(buffer, 0, len);
                } else {
                    in.close();
                    out.close();
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }

    private NanoHTTPD.Response processLoadExtensionsRequest(Properties parms) {
        try {
            JSONArray array = new JSONArray(parms.getProperty("extensions", "[]"));
            List<String> extensionsToLoad = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                String extensionName = array.optString(i);
                if (extensionName == null) {
                    return error("Invalid JSON content at index " + i);
                }
                extensionsToLoad.add(extensionName);
            }
            try {
                this.form.loadComponents(extensionsToLoad);
                return message("OK");
            } catch (Exception e) {
                return error((Throwable) e);
            }
        } catch (JSONException e2) {
            return error((Throwable) e2);
        }
    }

    private void adoptMainThreadClassLoader() {
        ClassLoader mainClassLoader = Looper.getMainLooper().getThread().getContextClassLoader();
        Thread myThread = Thread.currentThread();
        if (myThread.getContextClassLoader() != mainClassLoader) {
            myThread.setContextClassLoader(mainClassLoader);
        }
    }

    private NanoHTTPD.Response message(String txt) {
        return addHeaders(new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, NanoHTTPD.MIME_PLAINTEXT, txt));
    }

    private NanoHTTPD.Response json(String json) {
        return addHeaders(new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, MIME_JSON, json));
    }

    private NanoHTTPD.Response error(String msg) {
        JSONObject result = new JSONObject();
        try {
            result.put(NotificationCompat.CATEGORY_STATUS, "BAD");
            result.put("message", msg);
        } catch (JSONException e) {
            Log.wtf(LOG_TAG, "Unable to write basic JSON content", e);
        }
        return addHeaders(new NanoHTTPD.Response(NanoHTTPD.HTTP_OK, MIME_JSON, result.toString()));
    }

    private NanoHTTPD.Response error(Throwable t) {
        return error(t.toString());
    }

    private NanoHTTPD.Response addHeaders(NanoHTTPD.Response res) {
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Headers", "origin, content-type");
        res.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
        res.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
        return res;
    }

    public static void setHmacKey(String inputKey) {
        hmacKey = inputKey.getBytes();
        seq = 1;
    }

    public void resetSeq() {
        seq = 1;
    }
}
