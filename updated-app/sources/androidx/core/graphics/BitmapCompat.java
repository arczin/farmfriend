package androidx.core.graphics;

import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.os.Build;

public final class BitmapCompat {
    public static boolean hasMipMap(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 17) {
            return Api17Impl.hasMipMap(bitmap);
        }
        return false;
    }

    public static void setHasMipMap(Bitmap bitmap, boolean hasMipMap) {
        if (Build.VERSION.SDK_INT >= 17) {
            Api17Impl.setHasMipMap(bitmap, hasMipMap);
        }
    }

    public static int getAllocationByteCount(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 19) {
            return Api19Impl.getAllocationByteCount(bitmap);
        }
        return bitmap.getByteCount();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:111:0x01e4, code lost:
        if (androidx.core.graphics.BitmapCompat.Api27Impl.isAlreadyF16AndLinear(r8) == false) goto L_0x01fb;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Bitmap createScaledBitmap(android.graphics.Bitmap r30, int r31, int r32, android.graphics.Rect r33, boolean r34) {
        /*
            r0 = r30
            r1 = r31
            r2 = r32
            r3 = r33
            if (r1 <= 0) goto L_0x024f
            if (r2 <= 0) goto L_0x024f
            if (r3 == 0) goto L_0x0035
            boolean r4 = r33.isEmpty()
            if (r4 != 0) goto L_0x002d
            int r4 = r3.left
            if (r4 < 0) goto L_0x002d
            int r4 = r3.right
            int r5 = r30.getWidth()
            if (r4 > r5) goto L_0x002d
            int r4 = r3.top
            if (r4 < 0) goto L_0x002d
            int r4 = r3.bottom
            int r5 = r30.getHeight()
            if (r4 > r5) goto L_0x002d
            goto L_0x0035
        L_0x002d:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r5 = "srcRect must be contained by srcBm!"
            r4.<init>(r5)
            throw r4
        L_0x0035:
            r4 = r30
            int r5 = android.os.Build.VERSION.SDK_INT
            r6 = 27
            if (r5 < r6) goto L_0x0041
            android.graphics.Bitmap r4 = androidx.core.graphics.BitmapCompat.Api27Impl.copyBitmapIfHardware(r30)
        L_0x0041:
            if (r3 == 0) goto L_0x0048
            int r5 = r33.width()
            goto L_0x004c
        L_0x0048:
            int r5 = r30.getWidth()
        L_0x004c:
            if (r3 == 0) goto L_0x0053
            int r7 = r33.height()
            goto L_0x0057
        L_0x0053:
            int r7 = r30.getHeight()
        L_0x0057:
            float r8 = (float) r1
            float r9 = (float) r5
            float r8 = r8 / r9
            float r9 = (float) r2
            float r10 = (float) r7
            float r9 = r9 / r10
            if (r3 == 0) goto L_0x0062
            int r11 = r3.left
            goto L_0x0063
        L_0x0062:
            r11 = 0
        L_0x0063:
            if (r3 == 0) goto L_0x0068
            int r12 = r3.top
            goto L_0x0069
        L_0x0068:
            r12 = 0
        L_0x0069:
            r13 = 1
            if (r11 != 0) goto L_0x008c
            if (r12 != 0) goto L_0x008c
            int r14 = r30.getWidth()
            if (r1 != r14) goto L_0x008c
            int r14 = r30.getHeight()
            if (r2 != r14) goto L_0x008c
            boolean r6 = r30.isMutable()
            if (r6 == 0) goto L_0x008b
            if (r0 != r4) goto L_0x008b
            android.graphics.Bitmap$Config r6 = r30.getConfig()
            android.graphics.Bitmap r6 = r0.copy(r6, r13)
            return r6
        L_0x008b:
            return r4
        L_0x008c:
            android.graphics.Paint r14 = new android.graphics.Paint
            r14.<init>(r13)
            r14.setFilterBitmap(r13)
            int r15 = android.os.Build.VERSION.SDK_INT
            r10 = 29
            if (r15 < r10) goto L_0x009e
            androidx.core.graphics.BitmapCompat.Api29Impl.setPaintBlendMode(r14)
            goto L_0x00a8
        L_0x009e:
            android.graphics.PorterDuffXfermode r10 = new android.graphics.PorterDuffXfermode
            android.graphics.PorterDuff$Mode r15 = android.graphics.PorterDuff.Mode.SRC
            r10.<init>(r15)
            r14.setXfermode(r10)
        L_0x00a8:
            if (r5 != r1) goto L_0x00c1
            if (r7 != r2) goto L_0x00c1
            android.graphics.Bitmap$Config r6 = r4.getConfig()
            android.graphics.Bitmap r6 = android.graphics.Bitmap.createBitmap(r1, r2, r6)
            android.graphics.Canvas r10 = new android.graphics.Canvas
            r10.<init>(r6)
            int r13 = -r11
            float r13 = (float) r13
            int r15 = -r12
            float r15 = (float) r15
            r10.drawBitmap(r4, r13, r15, r14)
            return r6
        L_0x00c1:
            r17 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r17 = java.lang.Math.log(r17)
            r10 = 1065353216(0x3f800000, float:1.0)
            int r15 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r15 <= 0) goto L_0x00db
            r15 = r14
            double r13 = (double) r8
            double r13 = java.lang.Math.log(r13)
            double r13 = r13 / r17
            double r13 = java.lang.Math.ceil(r13)
            int r13 = (int) r13
            goto L_0x00e8
        L_0x00db:
            r15 = r14
            double r13 = (double) r8
            double r13 = java.lang.Math.log(r13)
            double r13 = r13 / r17
            double r13 = java.lang.Math.floor(r13)
            int r13 = (int) r13
        L_0x00e8:
            int r10 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r10 <= 0) goto L_0x00fb
            r10 = r7
            double r6 = (double) r9
            double r6 = java.lang.Math.log(r6)
            double r6 = r6 / r17
            double r6 = java.lang.Math.ceil(r6)
            int r6 = (int) r6
            goto L_0x0108
        L_0x00fb:
            r10 = r7
            double r6 = (double) r9
            double r6 = java.lang.Math.log(r6)
            double r6 = r6 / r17
            double r6 = java.lang.Math.floor(r6)
            int r6 = (int) r6
        L_0x0108:
            r7 = r13
            r20 = r6
            r21 = 0
            r22 = 0
            if (r34 == 0) goto L_0x016a
            int r14 = android.os.Build.VERSION.SDK_INT
            r3 = 27
            if (r14 < r3) goto L_0x0161
            boolean r3 = androidx.core.graphics.BitmapCompat.Api27Impl.isAlreadyF16AndLinear(r30)
            if (r3 != 0) goto L_0x0161
            if (r13 <= 0) goto L_0x0126
            r3 = 1
            int r19 = sizeAtStep(r5, r1, r3, r7)
            goto L_0x0129
        L_0x0126:
            r3 = 1
            r19 = r5
        L_0x0129:
            r23 = r19
            if (r6 <= 0) goto L_0x0134
            r14 = r20
            int r20 = sizeAtStep(r10, r2, r3, r14)
            goto L_0x0138
        L_0x0134:
            r14 = r20
            r20 = r10
        L_0x0138:
            r24 = r20
            r20 = r6
            r6 = r23
            r23 = r8
            r8 = r24
            r24 = r9
            android.graphics.Bitmap r9 = androidx.core.graphics.BitmapCompat.Api27Impl.createBitmapWithSourceColorspace(r6, r8, r0, r3)
            android.graphics.Canvas r3 = new android.graphics.Canvas
            r3.<init>(r9)
            r25 = r6
            int r6 = -r11
            float r6 = (float) r6
            r26 = r8
            int r8 = -r12
            float r8 = (float) r8
            r3.drawBitmap(r4, r6, r8, r15)
            r11 = 0
            r12 = 0
            r6 = r9
            r21 = r4
            r4 = r6
            r22 = 1
            goto L_0x0172
        L_0x0161:
            r23 = r8
            r24 = r9
            r14 = r20
            r20 = r6
            goto L_0x0172
        L_0x016a:
            r23 = r8
            r24 = r9
            r14 = r20
            r20 = r6
        L_0x0172:
            android.graphics.Rect r3 = new android.graphics.Rect
            r3.<init>(r11, r12, r5, r10)
            android.graphics.Rect r6 = new android.graphics.Rect
            r6.<init>()
            r8 = r21
        L_0x017e:
            if (r13 != 0) goto L_0x018b
            if (r20 == 0) goto L_0x0183
            goto L_0x018b
        L_0x0183:
            if (r8 == r0) goto L_0x018a
            if (r8 == 0) goto L_0x018a
            r8.recycle()
        L_0x018a:
            return r4
        L_0x018b:
            if (r13 >= 0) goto L_0x0190
            int r13 = r13 + 1
            goto L_0x0194
        L_0x0190:
            if (r13 <= 0) goto L_0x0194
            int r13 = r13 + -1
        L_0x0194:
            if (r20 >= 0) goto L_0x019b
            int r20 = r20 + 1
            r9 = r20
            goto L_0x01a4
        L_0x019b:
            if (r20 <= 0) goto L_0x01a2
            int r20 = r20 + -1
            r9 = r20
            goto L_0x01a4
        L_0x01a2:
            r9 = r20
        L_0x01a4:
            r20 = r11
            int r11 = sizeAtStep(r5, r1, r13, r7)
            r21 = r12
            int r12 = sizeAtStep(r10, r2, r9, r14)
            r25 = r3
            r3 = 0
            r6.set(r3, r3, r11, r12)
            if (r13 != 0) goto L_0x01bd
            if (r9 != 0) goto L_0x01bd
            r16 = 1
            goto L_0x01bf
        L_0x01bd:
            r16 = 0
        L_0x01bf:
            if (r8 == 0) goto L_0x01cf
            int r3 = r8.getWidth()
            if (r3 != r1) goto L_0x01cf
            int r3 = r8.getHeight()
            if (r3 != r2) goto L_0x01cf
            r3 = 1
            goto L_0x01d0
        L_0x01cf:
            r3 = 0
        L_0x01d0:
            if (r8 == 0) goto L_0x01f7
            if (r8 == r0) goto L_0x01f7
            if (r34 == 0) goto L_0x01e7
            r27 = r11
            int r11 = android.os.Build.VERSION.SDK_INT
            r28 = r12
            r12 = 27
            if (r11 < r12) goto L_0x01eb
            boolean r12 = androidx.core.graphics.BitmapCompat.Api27Impl.isAlreadyF16AndLinear(r8)
            if (r12 == 0) goto L_0x01f1
            goto L_0x01eb
        L_0x01e7:
            r27 = r11
            r28 = r12
        L_0x01eb:
            if (r16 == 0) goto L_0x01f2
            if (r3 == 0) goto L_0x01f1
            if (r22 == 0) goto L_0x01f2
        L_0x01f1:
            goto L_0x01fb
        L_0x01f2:
            r29 = r3
            r3 = 27
            goto L_0x0232
        L_0x01f7:
            r27 = r11
            r28 = r12
        L_0x01fb:
            if (r8 == r0) goto L_0x0202
            if (r8 == 0) goto L_0x0202
            r8.recycle()
        L_0x0202:
            r12 = r22
            if (r13 <= 0) goto L_0x0208
            r11 = r12
            goto L_0x0209
        L_0x0208:
            r11 = r13
        L_0x0209:
            int r11 = sizeAtStep(r5, r1, r11, r7)
            if (r9 <= 0) goto L_0x0211
            r1 = r12
            goto L_0x0212
        L_0x0211:
            r1 = r9
        L_0x0212:
            int r1 = sizeAtStep(r10, r2, r1, r14)
            int r2 = android.os.Build.VERSION.SDK_INT
            r29 = r3
            r3 = 27
            if (r2 < r3) goto L_0x022a
            if (r34 == 0) goto L_0x0224
            if (r16 != 0) goto L_0x0224
            r2 = 1
            goto L_0x0225
        L_0x0224:
            r2 = 0
        L_0x0225:
            android.graphics.Bitmap r8 = androidx.core.graphics.BitmapCompat.Api27Impl.createBitmapWithSourceColorspace(r11, r1, r0, r2)
            goto L_0x0232
        L_0x022a:
            android.graphics.Bitmap$Config r2 = r4.getConfig()
            android.graphics.Bitmap r8 = android.graphics.Bitmap.createBitmap(r11, r1, r2)
        L_0x0232:
            android.graphics.Canvas r1 = new android.graphics.Canvas
            r1.<init>(r8)
            r2 = r25
            r1.drawBitmap(r4, r2, r6, r15)
            r11 = r4
            r4 = r8
            r8 = r11
            r2.set(r6)
            r1 = r31
            r3 = r2
            r11 = r20
            r12 = r21
            r2 = r32
            r20 = r9
            goto L_0x017e
        L_0x024f:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "dstW and dstH must be > 0!"
            r1.<init>(r2)
            goto L_0x0258
        L_0x0257:
            throw r1
        L_0x0258:
            goto L_0x0257
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.BitmapCompat.createScaledBitmap(android.graphics.Bitmap, int, int, android.graphics.Rect, boolean):android.graphics.Bitmap");
    }

    public static int sizeAtStep(int srcSize, int dstSize, int step, int totalSteps) {
        if (step == 0) {
            return dstSize;
        }
        if (step > 0) {
            return (1 << (totalSteps - step)) * srcSize;
        }
        return dstSize << ((-step) - 1);
    }

    private BitmapCompat() {
    }

    static class Api17Impl {
        private Api17Impl() {
        }

        static boolean hasMipMap(Bitmap bitmap) {
            return bitmap.hasMipMap();
        }

        static void setHasMipMap(Bitmap bitmap, boolean hasMipMap) {
            bitmap.setHasMipMap(hasMipMap);
        }
    }

    static class Api19Impl {
        private Api19Impl() {
        }

        static int getAllocationByteCount(Bitmap bitmap) {
            return bitmap.getAllocationByteCount();
        }
    }

    static class Api27Impl {
        private Api27Impl() {
        }

        static Bitmap createBitmapWithSourceColorspace(int w, int h, Bitmap src, boolean linear) {
            Bitmap.Config config = src.getConfig();
            ColorSpace colorSpace = src.getColorSpace();
            ColorSpace linearCs = ColorSpace.get(ColorSpace.Named.LINEAR_EXTENDED_SRGB);
            if (linear && !src.getColorSpace().equals(linearCs)) {
                config = Bitmap.Config.RGBA_F16;
                colorSpace = linearCs;
            } else if (src.getConfig() == Bitmap.Config.HARDWARE) {
                config = Bitmap.Config.ARGB_8888;
                if (Build.VERSION.SDK_INT >= 31) {
                    config = Api31Impl.getHardwareBitmapConfig(src);
                }
            }
            return Bitmap.createBitmap(w, h, config, src.hasAlpha(), colorSpace);
        }

        static boolean isAlreadyF16AndLinear(Bitmap b) {
            return b.getConfig() == Bitmap.Config.RGBA_F16 && b.getColorSpace().equals(ColorSpace.get(ColorSpace.Named.LINEAR_EXTENDED_SRGB));
        }

        static Bitmap copyBitmapIfHardware(Bitmap bm) {
            if (bm.getConfig() != Bitmap.Config.HARDWARE) {
                return bm;
            }
            Bitmap.Config newConfig = Bitmap.Config.ARGB_8888;
            if (Build.VERSION.SDK_INT >= 31) {
                newConfig = Api31Impl.getHardwareBitmapConfig(bm);
            }
            return bm.copy(newConfig, true);
        }
    }

    static class Api29Impl {
        private Api29Impl() {
        }

        static void setPaintBlendMode(Paint paint) {
            paint.setBlendMode(BlendMode.SRC);
        }
    }

    static class Api31Impl {
        private Api31Impl() {
        }

        static Bitmap.Config getHardwareBitmapConfig(Bitmap bm) {
            if (bm.getHardwareBuffer().getFormat() == 22) {
                return Bitmap.Config.RGBA_F16;
            }
            return Bitmap.Config.ARGB_8888;
        }
    }
}
