package androidx.core.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class FontProvider {
    private static final Comparator<byte[]> sByteArrayComparator = new FontProvider$$ExternalSyntheticLambda0();

    private FontProvider() {
    }

    static FontsContractCompat.FontFamilyResult getFontFamilyResult(Context context, FontRequest request, CancellationSignal cancellationSignal) throws PackageManager.NameNotFoundException {
        ProviderInfo providerInfo = getProvider(context.getPackageManager(), request, context.getResources());
        if (providerInfo == null) {
            return FontsContractCompat.FontFamilyResult.create(1, (FontsContractCompat.FontInfo[]) null);
        }
        return FontsContractCompat.FontFamilyResult.create(0, query(context, request, providerInfo.authority, cancellationSignal));
    }

    static ProviderInfo getProvider(PackageManager packageManager, FontRequest request, Resources resources) throws PackageManager.NameNotFoundException {
        String providerAuthority = request.getProviderAuthority();
        ProviderInfo info = packageManager.resolveContentProvider(providerAuthority, 0);
        if (info == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + providerAuthority);
        } else if (info.packageName.equals(request.getProviderPackage())) {
            List<byte[]> signatures = convertToByteArrayList(packageManager.getPackageInfo(info.packageName, 64).signatures);
            Collections.sort(signatures, sByteArrayComparator);
            List<List<byte[]>> requestCertificatesList = getCertificates(request, resources);
            for (int i = 0; i < requestCertificatesList.size(); i++) {
                List<byte[]> requestSignatures = new ArrayList<>(requestCertificatesList.get(i));
                Collections.sort(requestSignatures, sByteArrayComparator);
                if (equalsByteArrayList(signatures, requestSignatures)) {
                    return info;
                }
            }
            return null;
        } else {
            throw new PackageManager.NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + request.getProviderPackage());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:57:0x013c  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x015a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static androidx.core.provider.FontsContractCompat.FontInfo[] query(android.content.Context r22, androidx.core.provider.FontRequest r23, java.lang.String r24, android.os.CancellationSignal r25) {
        /*
            r1 = r24
            java.lang.String r0 = "result_code"
            java.lang.String r2 = "font_italic"
            java.lang.String r3 = "font_weight"
            java.lang.String r4 = "font_ttc_index"
            java.lang.String r5 = "file_id"
            java.lang.String r6 = "_id"
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            android.net.Uri$Builder r8 = new android.net.Uri$Builder
            r8.<init>()
            java.lang.String r9 = "content"
            android.net.Uri$Builder r8 = r8.scheme(r9)
            android.net.Uri$Builder r8 = r8.authority(r1)
            android.net.Uri r8 = r8.build()
            android.net.Uri$Builder r10 = new android.net.Uri$Builder
            r10.<init>()
            android.net.Uri$Builder r9 = r10.scheme(r9)
            android.net.Uri$Builder r9 = r9.authority(r1)
            java.lang.String r10 = "file"
            android.net.Uri$Builder r9 = r9.appendPath(r10)
            android.net.Uri r9 = r9.build()
            r19 = 0
            r10 = 7
            java.lang.String[] r12 = new java.lang.String[r10]     // Catch:{ all -> 0x0153 }
            r15 = 0
            r12[r15] = r6     // Catch:{ all -> 0x0153 }
            r14 = 1
            r12[r14] = r5     // Catch:{ all -> 0x0153 }
            r10 = 2
            r12[r10] = r4     // Catch:{ all -> 0x0153 }
            java.lang.String r10 = "font_variation_settings"
            r11 = 3
            r12[r11] = r10     // Catch:{ all -> 0x0153 }
            r10 = 4
            r12[r10] = r3     // Catch:{ all -> 0x0153 }
            r10 = 5
            r12[r10] = r2     // Catch:{ all -> 0x0153 }
            r10 = 6
            r12[r10] = r0     // Catch:{ all -> 0x0153 }
            android.content.ContentResolver r10 = r22.getContentResolver()     // Catch:{ all -> 0x014f }
            int r11 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x014f }
            r13 = 16
            if (r11 <= r13) goto L_0x007e
            java.lang.String r13 = "query = ?"
            java.lang.String r11 = r23.getQuery()     // Catch:{ all -> 0x014f }
            java.lang.String[] r1 = new java.lang.String[r14]     // Catch:{ all -> 0x0153 }
            r1[r15] = r11     // Catch:{ all -> 0x0153 }
            r16 = 0
            r11 = r8
            r20 = r7
            r7 = 1
            r14 = r1
            r1 = 0
            r15 = r16
            r16 = r25
            android.database.Cursor r11 = androidx.core.provider.FontProvider.Api16Impl.query(r10, r11, r12, r13, r14, r15, r16)     // Catch:{ all -> 0x014b }
            goto L_0x0098
        L_0x007e:
            r20 = r7
            r1 = 0
            r7 = 1
            java.lang.String r16 = "query = ?"
            java.lang.String r11 = r23.getQuery()     // Catch:{ all -> 0x014b }
            java.lang.String[] r15 = new java.lang.String[r7]     // Catch:{ all -> 0x0149 }
            r15[r1] = r11     // Catch:{ all -> 0x0149 }
            r18 = 0
            r13 = r10
            r14 = r8
            r11 = r15
            r15 = r12
            r17 = r11
            android.database.Cursor r11 = r13.query(r14, r15, r16, r17, r18)     // Catch:{ all -> 0x014b }
        L_0x0098:
            if (r11 == 0) goto L_0x0138
            int r13 = r11.getCount()     // Catch:{ all -> 0x0132 }
            if (r13 <= 0) goto L_0x0138
            int r0 = r11.getColumnIndex(r0)     // Catch:{ all -> 0x0132 }
            java.util.ArrayList r13 = new java.util.ArrayList     // Catch:{ all -> 0x0132 }
            r13.<init>()     // Catch:{ all -> 0x0132 }
            int r6 = r11.getColumnIndex(r6)     // Catch:{ all -> 0x012d }
            int r5 = r11.getColumnIndex(r5)     // Catch:{ all -> 0x012d }
            int r4 = r11.getColumnIndex(r4)     // Catch:{ all -> 0x012d }
            int r3 = r11.getColumnIndex(r3)     // Catch:{ all -> 0x012d }
            int r2 = r11.getColumnIndex(r2)     // Catch:{ all -> 0x012d }
        L_0x00bd:
            boolean r14 = r11.moveToNext()     // Catch:{ all -> 0x012d }
            if (r14 == 0) goto L_0x0126
            r14 = -1
            if (r0 == r14) goto L_0x00cb
            int r15 = r11.getInt(r0)     // Catch:{ all -> 0x012d }
            goto L_0x00cc
        L_0x00cb:
            r15 = 0
        L_0x00cc:
            if (r4 == r14) goto L_0x00d4
            int r16 = r11.getInt(r4)     // Catch:{ all -> 0x012d }
            goto L_0x00d6
        L_0x00d4:
            r16 = 0
        L_0x00d6:
            r17 = r16
            if (r5 != r14) goto L_0x00eb
            long r18 = r11.getLong(r6)     // Catch:{ all -> 0x012d }
            r20 = r18
            r18 = r2
            r1 = r20
            android.net.Uri r19 = android.content.ContentUris.withAppendedId(r8, r1)     // Catch:{ all -> 0x012d }
            r1 = r19
            goto L_0x00f7
        L_0x00eb:
            r18 = r2
            long r1 = r11.getLong(r5)     // Catch:{ all -> 0x012d }
            android.net.Uri r19 = android.content.ContentUris.withAppendedId(r9, r1)     // Catch:{ all -> 0x012d }
            r1 = r19
        L_0x00f7:
            if (r3 == r14) goto L_0x00fe
            int r2 = r11.getInt(r3)     // Catch:{ all -> 0x012d }
            goto L_0x0100
        L_0x00fe:
            r2 = 400(0x190, float:5.6E-43)
        L_0x0100:
            r7 = r18
            if (r7 == r14) goto L_0x010f
            int r14 = r11.getInt(r7)     // Catch:{ all -> 0x012d }
            r18 = r0
            r0 = 1
            if (r14 != r0) goto L_0x0112
            r14 = 1
            goto L_0x0113
        L_0x010f:
            r18 = r0
            r0 = 1
        L_0x0112:
            r14 = 0
        L_0x0113:
            r0 = r17
            r17 = r3
            androidx.core.provider.FontsContractCompat$FontInfo r3 = androidx.core.provider.FontsContractCompat.FontInfo.create(r1, r0, r2, r14, r15)     // Catch:{ all -> 0x012d }
            r13.add(r3)     // Catch:{ all -> 0x012d }
            r2 = r7
            r3 = r17
            r0 = r18
            r1 = 0
            r7 = 1
            goto L_0x00bd
        L_0x0126:
            r18 = r0
            r7 = r2
            r17 = r3
            r7 = r13
            goto L_0x013a
        L_0x012d:
            r0 = move-exception
            r19 = r11
            r7 = r13
            goto L_0x0158
        L_0x0132:
            r0 = move-exception
            r19 = r11
            r7 = r20
            goto L_0x0158
        L_0x0138:
            r7 = r20
        L_0x013a:
            if (r11 == 0) goto L_0x013f
            r11.close()
        L_0x013f:
            r0 = 0
            androidx.core.provider.FontsContractCompat$FontInfo[] r0 = new androidx.core.provider.FontsContractCompat.FontInfo[r0]
            java.lang.Object[] r0 = r7.toArray(r0)
            androidx.core.provider.FontsContractCompat$FontInfo[] r0 = (androidx.core.provider.FontsContractCompat.FontInfo[]) r0
            return r0
        L_0x0149:
            r0 = move-exception
            goto L_0x0156
        L_0x014b:
            r0 = move-exception
            r7 = r20
            goto L_0x0158
        L_0x014f:
            r0 = move-exception
            r20 = r7
            goto L_0x0158
        L_0x0153:
            r0 = move-exception
            r20 = r7
        L_0x0156:
            r7 = r20
        L_0x0158:
            if (r19 == 0) goto L_0x015d
            r19.close()
        L_0x015d:
            goto L_0x015f
        L_0x015e:
            throw r0
        L_0x015f:
            goto L_0x015e
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.provider.FontProvider.query(android.content.Context, androidx.core.provider.FontRequest, java.lang.String, android.os.CancellationSignal):androidx.core.provider.FontsContractCompat$FontInfo[]");
    }

    private static List<List<byte[]>> getCertificates(FontRequest request, Resources resources) {
        if (request.getCertificates() != null) {
            return request.getCertificates();
        }
        return FontResourcesParserCompat.readCerts(resources, request.getCertificatesArrayResId());
    }

    static /* synthetic */ int lambda$static$0(byte[] l, byte[] r) {
        if (l.length != r.length) {
            return l.length - r.length;
        }
        for (int i = 0; i < l.length; i++) {
            if (l[i] != r[i]) {
                return l[i] - r[i];
            }
        }
        return 0;
    }

    private static boolean equalsByteArrayList(List<byte[]> signatures, List<byte[]> requestSignatures) {
        if (signatures.size() != requestSignatures.size()) {
            return false;
        }
        for (int i = 0; i < signatures.size(); i++) {
            if (!Arrays.equals(signatures.get(i), requestSignatures.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<byte[]> convertToByteArrayList(Signature[] signatures) {
        List<byte[]> shaList = new ArrayList<>();
        for (Signature signature : signatures) {
            shaList.add(signature.toByteArray());
        }
        return shaList;
    }

    static class Api16Impl {
        private Api16Impl() {
        }

        static Cursor query(ContentResolver contentResolver, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, Object cancellationSignal) {
            return contentResolver.query(uri, projection, selection, selectionArgs, sortOrder, (CancellationSignal) cancellationSignal);
        }
    }
}
