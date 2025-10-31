package gnu.math;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class FixedRealFormat extends Format {
    private int d;
    private int i;
    public boolean internalPad;
    public char overflowChar;
    public char padChar;
    public int scale;
    public boolean showPlus;
    public int width;

    public int getMaximumFractionDigits() {
        return this.d;
    }

    public int getMinimumIntegerDigits() {
        return this.i;
    }

    public void setMaximumFractionDigits(int d2) {
        this.d = d2;
    }

    public void setMinimumIntegerDigits(int i2) {
        this.i = i2;
    }

    public void format(RealNum number, StringBuffer sbuf, FieldPosition fpos) {
        RatNum ratnum;
        int signLen;
        RealNum realNum = number;
        StringBuffer stringBuffer = sbuf;
        if (realNum instanceof RatNum) {
            int maximumFractionDigits = getMaximumFractionDigits();
            int decimals = maximumFractionDigits;
            if (maximumFractionDigits >= 0) {
                RatNum ratnum2 = (RatNum) realNum;
                boolean negative = ratnum2.isNegative();
                if (negative) {
                    ratnum = ratnum2.rneg();
                } else {
                    ratnum = ratnum2;
                }
                int oldSize = sbuf.length();
                if (negative) {
                    stringBuffer.append('-');
                } else if (this.showPlus) {
                    stringBuffer.append('+');
                } else {
                    signLen = 0;
                    String string = RealNum.toScaledInt(ratnum, this.scale + decimals).toString();
                    stringBuffer.append(string);
                    int length = string.length();
                    String str = string;
                    format(sbuf, fpos, length, length - decimals, decimals, signLen, oldSize);
                    return;
                }
                signLen = 1;
                String string2 = RealNum.toScaledInt(ratnum, this.scale + decimals).toString();
                stringBuffer.append(string2);
                int length2 = string2.length();
                String str2 = string2;
                format(sbuf, fpos, length2, length2 - decimals, decimals, signLen, oldSize);
                return;
            }
        }
        format(number.doubleValue(), stringBuffer, fpos);
    }

    public StringBuffer format(long num, StringBuffer sbuf, FieldPosition fpos) {
        format((RealNum) IntNum.make(num), sbuf, fpos);
        return sbuf;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0180, code lost:
        if (((r15 + r16) + 1) < r8.width) goto L_0x0185;
     */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00b7 A[LOOP:0: B:29:0x00a9->B:33:0x00b7, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00bd A[EDGE_INSN: B:88:0x00bd->B:34:0x00bd ?: BREAK  
    EDGE_INSN: B:89:0x00bd->B:34:0x00bd ?: BREAK  ] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00ca  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00d5 A[LOOP:1: B:39:0x00d5->B:40:0x00d7, LOOP_START, PHI: r0 r3 
      PHI: (r0v25 'i' int) = (r0v5 'i' int), (r0v26 'i' int) binds: [B:38:0x00d3, B:40:0x00d7] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r3v12 'digits' int) = (r3v3 'digits' int), (r3v13 'digits' int) binds: [B:38:0x00d3, B:40:0x00d7] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00f3  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00fb A[LOOP:2: B:49:0x00f9->B:50:0x00fb, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x011d  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x012d  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x012f  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0134  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0137  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x014b A[LOOP:3: B:64:0x013d->B:68:0x014b, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0150 A[EDGE_INSN: B:92:0x0150->B:69:0x0150 ?: BREAK  
    EDGE_INSN: B:93:0x0150->B:69:0x0150 ?: BREAK  ] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x015c  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0168  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0170  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0196  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.StringBuffer format(double r30, java.lang.StringBuffer r32, java.text.FieldPosition r33) {
        /*
            r29 = this;
            r8 = r29
            r0 = r30
            r9 = r32
            boolean r2 = java.lang.Double.isNaN(r30)
            if (r2 != 0) goto L_0x01bf
            boolean r2 = java.lang.Double.isInfinite(r30)
            if (r2 == 0) goto L_0x0014
            goto L_0x01bf
        L_0x0014:
            int r2 = r29.getMaximumFractionDigits()
            if (r2 < 0) goto L_0x0026
            gnu.math.RatNum r2 = gnu.math.DFloNum.toExact(r30)
            r10 = r33
            r8.format((gnu.math.RealNum) r2, (java.lang.StringBuffer) r9, (java.text.FieldPosition) r10)
            r11 = r0
            goto L_0x01be
        L_0x0026:
            r10 = r33
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 >= 0) goto L_0x0033
            r2 = 1
            double r0 = -r0
            r11 = r0
            r13 = r2
            goto L_0x0036
        L_0x0033:
            r2 = 0
            r11 = r0
            r13 = r2
        L_0x0036:
            int r14 = r32.length()
            r0 = 1
            r1 = 43
            if (r13 == 0) goto L_0x0045
            r2 = 45
            r9.append(r2)
            goto L_0x004c
        L_0x0045:
            boolean r2 = r8.showPlus
            if (r2 == 0) goto L_0x004e
            r9.append(r1)
        L_0x004c:
            r15 = r0
            goto L_0x0050
        L_0x004e:
            r0 = 0
            r15 = r0
        L_0x0050:
            java.lang.String r0 = java.lang.Double.toString(r11)
            int r2 = r8.scale
            r3 = 69
            int r7 = r0.indexOf(r3)
            r3 = 0
            if (r7 < 0) goto L_0x0076
            int r4 = r7 + 1
            char r5 = r0.charAt(r4)
            if (r5 != r1) goto L_0x0069
            int r4 = r4 + 1
        L_0x0069:
            java.lang.String r1 = r0.substring(r4)
            int r1 = java.lang.Integer.parseInt(r1)
            int r2 = r2 + r1
            java.lang.String r0 = r0.substring(r3, r7)
        L_0x0076:
            r1 = 46
            int r6 = r0.indexOf(r1)
            int r1 = r0.length()
            r4 = 1
            if (r6 < 0) goto L_0x00a4
            int r5 = r1 - r6
            int r5 = r5 - r4
            int r2 = r2 - r5
            int r1 = r1 + -1
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r4 = r0.substring(r3, r6)
            java.lang.StringBuilder r4 = r5.append(r4)
            int r5 = r6 + 1
            java.lang.String r5 = r0.substring(r5)
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r0 = r4.toString()
        L_0x00a4:
            int r4 = r0.length()
            r5 = 0
        L_0x00a9:
            int r3 = r4 + -1
            r16 = r1
            r1 = 48
            if (r5 >= r3) goto L_0x00bd
            char r3 = r0.charAt(r5)
            if (r3 != r1) goto L_0x00bd
            int r5 = r5 + 1
            r1 = r16
            r3 = 0
            goto L_0x00a9
        L_0x00bd:
            if (r5 <= 0) goto L_0x00ca
            java.lang.String r0 = r0.substring(r5)
            int r4 = r4 - r5
            r28 = r4
            r4 = r0
            r0 = r28
            goto L_0x00cf
        L_0x00ca:
            r28 = r4
            r4 = r0
            r0 = r28
        L_0x00cf:
            int r3 = r0 + r2
            int r1 = r8.width
            if (r1 <= 0) goto L_0x00ea
        L_0x00d5:
            if (r3 >= 0) goto L_0x00e1
            r1 = 48
            r9.append(r1)
            int r3 = r3 + 1
            int r0 = r0 + 1
            goto L_0x00d5
        L_0x00e1:
            int r1 = r8.width
            int r1 = r1 - r15
            r18 = 1
            int r1 = r1 + -1
            int r1 = r1 - r3
            goto L_0x00f1
        L_0x00ea:
            r1 = 16
            if (r0 <= r1) goto L_0x00ef
            goto L_0x00f0
        L_0x00ef:
            r1 = r0
        L_0x00f0:
            int r1 = r1 - r3
        L_0x00f1:
            if (r1 >= 0) goto L_0x00f4
            r1 = 0
        L_0x00f4:
            r9.append(r4)
            r18 = r2
        L_0x00f9:
            if (r18 <= 0) goto L_0x0105
            r2 = 48
            r9.append(r2)
            int r18 = r18 + -1
            int r0 = r0 + 1
            goto L_0x00f9
        L_0x0105:
            int r2 = r14 + r15
            int r19 = r2 + r3
            r20 = r0
            int r0 = r19 + r1
            int r10 = r32.length()
            if (r0 < r10) goto L_0x011d
            r0 = r10
            r19 = 48
            r28 = r19
            r19 = r10
            r10 = r28
            goto L_0x0127
        L_0x011d:
            char r19 = r9.charAt(r0)
            r28 = r19
            r19 = r10
            r10 = r28
        L_0x0127:
            r20 = r0
            r0 = 53
            if (r10 < r0) goto L_0x012f
            r0 = 1
            goto L_0x0130
        L_0x012f:
            r0 = 0
        L_0x0130:
            r21 = r0
            if (r21 == 0) goto L_0x0137
            r0 = 57
            goto L_0x0139
        L_0x0137:
            r0 = 48
        L_0x0139:
            r31 = r10
            r10 = r20
        L_0x013d:
            r20 = r1
            int r1 = r2 + r3
            if (r10 <= r1) goto L_0x0150
            int r1 = r10 + -1
            char r1 = r9.charAt(r1)
            if (r1 != r0) goto L_0x0150
            int r10 = r10 + -1
            r1 = r20
            goto L_0x013d
        L_0x0150:
            int r1 = r10 - r2
            int r16 = r1 - r3
            if (r21 == 0) goto L_0x0168
            boolean r20 = gnu.math.ExponentialFormat.addOne(r9, r2, r10)
            if (r20 == 0) goto L_0x0168
            int r3 = r3 + 1
            r16 = 0
            r1 = r3
            r28 = r16
            r16 = r3
            r3 = r28
            goto L_0x016e
        L_0x0168:
            r28 = r16
            r16 = r3
            r3 = r28
        L_0x016e:
            if (r3 != 0) goto L_0x0196
            r20 = r0
            int r0 = r8.width
            if (r0 <= 0) goto L_0x0183
            int r0 = r15 + r16
            r22 = 1
            int r0 = r0 + 1
            r30 = r3
            int r3 = r8.width
            if (r0 >= r3) goto L_0x019a
            goto L_0x0185
        L_0x0183:
            r30 = r3
        L_0x0185:
            r3 = 1
            int r1 = r1 + 1
            int r0 = r2 + r16
            r30 = r1
            r1 = 48
            r9.insert(r0, r1)
            r17 = r30
            r22 = r3
            goto L_0x019e
        L_0x0196:
            r20 = r0
            r30 = r3
        L_0x019a:
            r22 = r30
            r17 = r1
        L_0x019e:
            int r0 = r2 + r17
            r9.setLength(r0)
            r0 = r29
            r1 = r32
            r23 = r2
            r2 = r33
            r3 = r17
            r24 = r4
            r4 = r16
            r25 = r5
            r5 = r22
            r26 = r6
            r6 = r13
            r27 = r7
            r7 = r14
            r0.format(r1, r2, r3, r4, r5, r6, r7)
        L_0x01be:
            return r9
        L_0x01bf:
            java.lang.StringBuffer r2 = r9.append(r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.math.FixedRealFormat.format(double, java.lang.StringBuffer, java.text.FieldPosition):java.lang.StringBuffer");
    }

    public StringBuffer format(Object num, StringBuffer sbuf, FieldPosition fpos) {
        RealNum rnum = RealNum.asRealNumOrNull(num);
        if (rnum == null) {
            if (num instanceof Complex) {
                String str = num.toString();
                int padding = this.width - str.length();
                while (true) {
                    padding--;
                    if (padding >= 0) {
                        sbuf.append(' ');
                    } else {
                        sbuf.append(str);
                        return sbuf;
                    }
                }
            } else {
                rnum = (RealNum) num;
            }
        }
        return format(rnum.doubleValue(), sbuf, fpos);
    }

    private void format(StringBuffer sbuf, FieldPosition fpos, int length, int digits, int decimals, int signLen, int oldSize) {
        int zero_digits;
        int i2 = digits + decimals;
        int zero_digits2 = getMinimumIntegerDigits();
        if (digits < 0 || digits <= zero_digits2) {
            zero_digits = zero_digits2 - digits;
        } else {
            zero_digits = 0;
        }
        if (digits + zero_digits <= 0 && (this.width <= 0 || this.width > decimals + 1 + signLen)) {
            zero_digits++;
        }
        int padding = this.width - (((signLen + length) + zero_digits) + 1);
        int i3 = zero_digits;
        while (true) {
            i3--;
            if (i3 < 0) {
                break;
            }
            sbuf.insert(oldSize + signLen, '0');
        }
        if (padding >= 0) {
            int i4 = oldSize;
            if (this.internalPad && signLen > 0) {
                i4++;
            }
            while (true) {
                padding--;
                if (padding < 0) {
                    break;
                }
                sbuf.insert(i4, this.padChar);
            }
        } else if (this.overflowChar != 0) {
            sbuf.setLength(oldSize);
            this.i = this.width;
            while (true) {
                int i5 = this.i - 1;
                this.i = i5;
                if (i5 >= 0) {
                    sbuf.append(this.overflowChar);
                } else {
                    return;
                }
            }
        }
        sbuf.insert(sbuf.length() - decimals, '.');
    }

    public Number parse(String text, ParsePosition status) {
        throw new Error("RealFixedFormat.parse - not implemented");
    }

    public Object parseObject(String text, ParsePosition status) {
        throw new Error("RealFixedFormat.parseObject - not implemented");
    }
}
