package kawa.standard;

public class read_line {
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c6 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object apply(gnu.text.LineBufferedReader r16, java.lang.String r17) throws java.io.IOException {
        /*
            r0 = r16
            r1 = r17
            int r2 = r16.read()
            if (r2 >= 0) goto L_0x000d
            java.lang.Object r3 = gnu.expr.Special.eof
            return r3
        L_0x000d:
            int r3 = r0.pos
            r4 = 1
            int r3 = r3 - r4
            r5 = r3
            int r6 = r0.limit
            char[] r7 = r0.buffer
            r8 = -1
        L_0x0017:
            java.lang.String r9 = "concat"
            java.lang.String r10 = "peek"
            r11 = 13
            r12 = 2
            r13 = 10
            if (r5 >= r6) goto L_0x005f
            int r14 = r5 + 1
            char r2 = r7[r5]
            if (r2 == r11) goto L_0x002d
            if (r2 != r13) goto L_0x002b
            goto L_0x002d
        L_0x002b:
            r5 = r14
            goto L_0x0017
        L_0x002d:
            int r5 = r14 + -1
            java.lang.String r14 = "trim"
            if (r1 == r14) goto L_0x003f
            if (r1 != r10) goto L_0x0036
            goto L_0x003f
        L_0x0036:
            if (r1 != r9) goto L_0x005f
            if (r2 != r13) goto L_0x005f
            int r5 = r5 + 1
            r0.pos = r5
            goto L_0x0057
        L_0x003f:
            if (r1 != r10) goto L_0x0042
            r8 = 0
        L_0x0042:
            if (r2 != r13) goto L_0x0047
            r4 = 1
            r8 = r4
            goto L_0x0053
        L_0x0047:
            int r14 = r5 + 1
            if (r14 >= r6) goto L_0x005f
            int r9 = r5 + 1
            char r9 = r7[r9]
            if (r9 != r13) goto L_0x0052
            r4 = 2
        L_0x0052:
            r8 = r4
        L_0x0053:
            int r4 = r5 + r8
            r0.pos = r4
        L_0x0057:
            gnu.lists.FString r4 = new gnu.lists.FString
            int r9 = r5 - r3
            r4.<init>((char[]) r7, (int) r3, (int) r9)
            return r4
        L_0x005f:
            java.lang.StringBuffer r14 = new java.lang.StringBuffer
            r15 = 100
            r14.<init>(r15)
            if (r5 <= r3) goto L_0x006d
            int r15 = r5 - r3
            r14.append(r7, r3, r15)
        L_0x006d:
            r0.pos = r5
            java.lang.String r15 = "split"
            if (r1 != r10) goto L_0x0076
            r9 = 80
            goto L_0x0080
        L_0x0076:
            if (r1 == r9) goto L_0x007e
            if (r1 != r15) goto L_0x007b
            goto L_0x007e
        L_0x007b:
            r9 = 73
            goto L_0x0080
        L_0x007e:
            r9 = 65
        L_0x0080:
            r0.readLine(r14, r9)
            int r10 = r14.length()
            if (r1 != r15) goto L_0x00a9
            if (r10 != 0) goto L_0x008d
            r8 = 0
            goto L_0x00a9
        L_0x008d:
            int r4 = r10 + -1
            char r4 = r14.charAt(r4)
            if (r4 != r11) goto L_0x0097
            r8 = 1
            goto L_0x00a8
        L_0x0097:
            if (r4 == r13) goto L_0x009b
            r8 = 0
            goto L_0x00a8
        L_0x009b:
            if (r4 <= r12) goto L_0x00a7
            int r13 = r10 + -2
            char r13 = r14.charAt(r13)
            if (r13 != r11) goto L_0x00a7
            r8 = 2
            goto L_0x00a8
        L_0x00a7:
            r8 = 1
        L_0x00a8:
            int r10 = r10 - r8
        L_0x00a9:
            gnu.lists.FString r4 = new gnu.lists.FString
            r11 = 0
            r4.<init>((java.lang.StringBuffer) r14, (int) r11, (int) r10)
            if (r1 != r15) goto L_0x00c6
            gnu.lists.FString r13 = new gnu.lists.FString
            int r15 = r10 - r8
            r13.<init>((java.lang.StringBuffer) r14, (int) r15, (int) r8)
            java.lang.Object[] r12 = new java.lang.Object[r12]
            r12[r11] = r4
            r11 = 1
            r12[r11] = r13
            r11 = r12
            gnu.mapping.Values r12 = new gnu.mapping.Values
            r12.<init>(r11)
            return r12
        L_0x00c6:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.read_line.apply(gnu.text.LineBufferedReader, java.lang.String):java.lang.Object");
    }
}
