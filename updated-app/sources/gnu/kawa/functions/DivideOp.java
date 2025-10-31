package gnu.kawa.functions;

import gnu.mapping.Procedure;

public class DivideOp extends ArithOp {
    public static final DivideOp $Sl = new DivideOp("/", 4);
    public static final DivideOp div = new DivideOp("div", 6);
    public static final DivideOp div0 = new DivideOp("div0", 6);
    public static final DivideOp idiv = new DivideOp("idiv", 7);
    public static final DivideOp mod = new DivideOp("mod", 8);
    public static final DivideOp mod0 = new DivideOp("mod0", 8);
    public static final DivideOp modulo = new DivideOp("modulo", 8);
    public static final DivideOp quotient = new DivideOp("quotient", 6);
    public static final DivideOp remainder = new DivideOp("remainder", 8);
    int rounding_mode;

    public int getRoundingMode() {
        return this.rounding_mode;
    }

    static {
        idiv.rounding_mode = 3;
        quotient.rounding_mode = 3;
        remainder.rounding_mode = 3;
        modulo.rounding_mode = 1;
        div.rounding_mode = 5;
        mod.rounding_mode = 5;
        div0.rounding_mode = 4;
        mod0.rounding_mode = 4;
    }

    public DivideOp(String name, int op) {
        super(name, op);
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyArithOp");
        Procedure.compilerKey.set(this, "*gnu.kawa.functions.CompileArith:forDiv");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005b, code lost:
        if (r5 != 2) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x008f, code lost:
        if (r2 == 7) goto L_0x0093;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x017d, code lost:
        r9 = java.math.RoundingMode.HALF_EVEN;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x017f, code lost:
        r13 = new java.math.MathContext(0, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0186, code lost:
        switch(r0.op) {
            case 4: goto L_0x01a2;
            case 5: goto L_0x0189;
            case 6: goto L_0x019d;
            case 7: goto L_0x018f;
            case 8: goto L_0x018a;
            default: goto L_0x0189;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x018a, code lost:
        r3 = r10.remainder(r11, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x018f, code lost:
        r3 = r10.divideToIntegralValue(r11, r13).toBigInteger();
        r5 = 3;
        r28 = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x019d, code lost:
        r3 = r10.divideToIntegralValue(r11, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01a2, code lost:
        r3 = r10.divide(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01a7, code lost:
        r15 = r4;
        r16 = r8;
        r8 = r11;
        r17 = 0;
        r11 = r6;
        r7 = r10;
        r10 = r13;
        r13 = r1;
        r2 = r28;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object applyN(java.lang.Object[] r33) throws java.lang.Throwable {
        /*
            r32 = this;
            r0 = r32
            r1 = r33
            int r2 = r1.length
            if (r2 != 0) goto L_0x000c
            gnu.math.IntNum r3 = gnu.math.IntNum.one()
            return r3
        L_0x000c:
            r3 = 0
            r3 = r1[r3]
            java.lang.Number r3 = (java.lang.Number) r3
            r4 = 1
            if (r2 != r4) goto L_0x001d
            gnu.math.IntNum r4 = gnu.math.IntNum.one()
            java.lang.Object r4 = r0.apply2(r4, r3)
            return r4
        L_0x001d:
            int r5 = gnu.kawa.functions.Arithmetic.classifyValue(r3)
            r6 = 1
            r7 = 0
            r8 = 0
            r10 = 0
            r11 = 0
            r10 = r7
            r13 = r8
            r18 = r11
            r20 = r18
            r15 = 0
            r16 = 0
            r17 = 0
            r11 = r13
            r8 = r10
            r9 = r8
        L_0x0036:
            if (r6 >= r2) goto L_0x030f
            r22 = r1[r6]
            int r4 = gnu.kawa.functions.Arithmetic.classifyValue(r22)
            if (r5 >= r4) goto L_0x0043
            r23 = r4
            goto L_0x0045
        L_0x0043:
            r23 = r5
        L_0x0045:
            r5 = r23
            r1 = 4
            if (r5 >= r1) goto L_0x006e
            int r1 = r0.op
            switch(r1) {
                case 4: goto L_0x005e;
                case 5: goto L_0x005e;
                default: goto L_0x0050;
            }
        L_0x0050:
            int r1 = r0.rounding_mode
            r26 = r2
            r2 = 3
            if (r1 != r2) goto L_0x0068
            r1 = 1
            if (r5 == r1) goto L_0x0071
            r2 = 2
            if (r5 != r2) goto L_0x0069
            goto L_0x0071
        L_0x005e:
            r1 = 4
            r5 = r1
            r23 = r1
            r26 = r2
            r2 = r23
            r1 = 1
            goto L_0x0073
        L_0x0068:
            r1 = 1
        L_0x0069:
            r23 = 4
            r2 = r23
            goto L_0x0073
        L_0x006e:
            r26 = r2
            r1 = 1
        L_0x0071:
            r2 = r23
        L_0x0073:
            int r1 = r0.op
            r23 = r4
            r4 = 5
            r27 = r6
            r6 = 8
            if (r1 != r4) goto L_0x008c
            r1 = 10
            if (r5 > r1) goto L_0x008c
            r2 = 10
            if (r5 == r6) goto L_0x009a
            r1 = 7
            if (r5 == r1) goto L_0x009a
            r5 = 9
            goto L_0x009a
        L_0x008c:
            if (r2 == r6) goto L_0x0092
            r1 = 7
            if (r2 != r1) goto L_0x009a
            goto L_0x0093
        L_0x0092:
            r1 = 7
        L_0x0093:
            r2 = 9
            int r4 = r0.op
            if (r4 != r1) goto L_0x009a
            r5 = r2
        L_0x009a:
            switch(r2) {
                case 1: goto L_0x0227;
                case 2: goto L_0x0206;
                case 3: goto L_0x009d;
                case 4: goto L_0x01b5;
                case 5: goto L_0x014f;
                case 6: goto L_0x009d;
                case 7: goto L_0x009d;
                case 8: goto L_0x009d;
                case 9: goto L_0x00cd;
                default: goto L_0x009d;
            }
        L_0x009d:
            r28 = r2
            r1 = r7
            r2 = r15
            r4 = r16
            r7 = r8
            r8 = r17
            r15 = r20
            r17 = r18
            gnu.math.Numeric r6 = gnu.kawa.functions.Arithmetic.asNumeric(r3)
            r20 = r1
            gnu.math.Numeric r1 = gnu.kawa.functions.Arithmetic.asNumeric(r22)
            r21 = r2
            int r2 = r0.op
            r24 = r4
            r4 = 8
            if (r2 != r4) goto L_0x024a
            boolean r2 = r1.isZero()
            if (r2 == 0) goto L_0x024a
            boolean r2 = r1.isExact()
            if (r2 == 0) goto L_0x0245
            r2 = r6
            goto L_0x0249
        L_0x00cd:
            r1 = r7
            r6 = r13
            r4 = r15
            r13 = r16
            r14 = r17
            double r15 = gnu.kawa.functions.Arithmetic.asDouble(r3)
            double r17 = gnu.kawa.functions.Arithmetic.asDouble(r22)
            r19 = r1
            int r1 = r0.op
            switch(r1) {
                case 4: goto L_0x0132;
                case 5: goto L_0x0132;
                case 6: goto L_0x011d;
                case 7: goto L_0x010b;
                case 8: goto L_0x00e8;
                default: goto L_0x00e3;
            }
        L_0x00e3:
            r28 = r2
            r20 = r4
            goto L_0x013e
        L_0x00e8:
            r20 = 0
            int r1 = (r17 > r20 ? 1 : (r17 == r20 ? 0 : -1))
            if (r1 == 0) goto L_0x0100
            r28 = r2
            double r1 = r15 / r17
            r20 = r4
            int r4 = r32.getRoundingMode()
            double r1 = gnu.math.RealNum.toInt(r1, r4)
            double r1 = r1 * r17
            double r15 = r15 - r1
            goto L_0x0104
        L_0x0100:
            r28 = r2
            r20 = r4
        L_0x0104:
            gnu.math.DFloNum r3 = gnu.math.DFloNum.make(r15)
            r2 = r28
            goto L_0x013e
        L_0x010b:
            r28 = r2
            r20 = r4
            double r1 = r15 / r17
            int r4 = r32.getRoundingMode()
            gnu.math.IntNum r3 = gnu.math.RealNum.toExactInt(r1, r4)
            r1 = 4
            r2 = r1
            r5 = r1
            goto L_0x013e
        L_0x011d:
            r28 = r2
            r20 = r4
            double r1 = r15 / r17
            int r4 = r32.getRoundingMode()
            double r1 = gnu.math.RealNum.toInt(r1, r4)
            java.lang.Double r3 = java.lang.Double.valueOf(r1)
            r2 = r28
            goto L_0x013e
        L_0x0132:
            r28 = r2
            r20 = r4
            double r1 = r15 / r17
            gnu.math.DFloNum r3 = gnu.math.DFloNum.make(r1)
            r2 = r28
        L_0x013e:
            r30 = r15
            r16 = r13
            r15 = r20
            r20 = r17
            r17 = r14
            r13 = r6
            r7 = r19
            r18 = r30
            goto L_0x02d3
        L_0x014f:
            r28 = r2
            r1 = r13
            r4 = r15
            r6 = r11
            r8 = r16
            java.math.BigDecimal r10 = gnu.kawa.functions.Arithmetic.asBigDecimal(r3)
            java.math.BigDecimal r11 = gnu.kawa.functions.Arithmetic.asBigDecimal(r22)
            r12 = 0
            int r13 = r32.getRoundingMode()
            switch(r13) {
                case 1: goto L_0x017a;
                case 2: goto L_0x0177;
                case 3: goto L_0x0174;
                case 4: goto L_0x0166;
                case 5: goto L_0x0167;
                default: goto L_0x0166;
            }
        L_0x0166:
            goto L_0x017d
        L_0x0167:
            int r13 = r11.signum()
            if (r13 >= 0) goto L_0x0170
            java.math.RoundingMode r13 = java.math.RoundingMode.CEILING
            goto L_0x0172
        L_0x0170:
            java.math.RoundingMode r13 = java.math.RoundingMode.FLOOR
        L_0x0172:
            r9 = r13
            goto L_0x017d
        L_0x0174:
            java.math.RoundingMode r9 = java.math.RoundingMode.DOWN
            goto L_0x017f
        L_0x0177:
            java.math.RoundingMode r9 = java.math.RoundingMode.CEILING
            goto L_0x017f
        L_0x017a:
            java.math.RoundingMode r9 = java.math.RoundingMode.FLOOR
            goto L_0x017f
        L_0x017d:
            java.math.RoundingMode r9 = java.math.RoundingMode.HALF_EVEN
        L_0x017f:
            java.math.MathContext r13 = new java.math.MathContext
            r13.<init>(r12, r9)
            int r14 = r0.op
            switch(r14) {
                case 4: goto L_0x01a2;
                case 5: goto L_0x0189;
                case 6: goto L_0x019d;
                case 7: goto L_0x018f;
                case 8: goto L_0x018a;
                default: goto L_0x0189;
            }
        L_0x0189:
            goto L_0x01a7
        L_0x018a:
            java.math.BigDecimal r3 = r10.remainder(r11, r13)
            goto L_0x01a7
        L_0x018f:
            java.math.BigDecimal r14 = r10.divideToIntegralValue(r11, r13)
            java.math.BigInteger r3 = r14.toBigInteger()
            r14 = 3
            r15 = r14
            r5 = r14
            r28 = r15
            goto L_0x01a7
        L_0x019d:
            java.math.BigDecimal r3 = r10.divideToIntegralValue(r11, r13)
            goto L_0x01a7
        L_0x01a2:
            java.math.BigDecimal r3 = r10.divide(r11)
        L_0x01a7:
            r15 = r4
            r16 = r8
            r8 = r11
            r17 = r12
            r11 = r6
            r7 = r10
            r10 = r13
            r13 = r1
            r2 = r28
            goto L_0x02d3
        L_0x01b5:
            r28 = r2
            r1 = r13
            r4 = r15
            r6 = r16
            int r13 = r0.op
            switch(r13) {
                case 4: goto L_0x01e3;
                case 5: goto L_0x01c0;
                case 6: goto L_0x01d2;
                case 7: goto L_0x01d2;
                case 8: goto L_0x01c1;
                default: goto L_0x01c0;
            }
        L_0x01c0:
            goto L_0x01fe
        L_0x01c1:
            gnu.math.IntNum r13 = gnu.kawa.functions.Arithmetic.asIntNum((java.lang.Object) r3)
            gnu.math.IntNum r14 = gnu.kawa.functions.Arithmetic.asIntNum((java.lang.Object) r22)
            int r15 = r32.getRoundingMode()
            gnu.math.IntNum r3 = gnu.math.IntNum.remainder(r13, r14, r15)
            goto L_0x01fe
        L_0x01d2:
            gnu.math.IntNum r13 = gnu.kawa.functions.Arithmetic.asIntNum((java.lang.Object) r3)
            gnu.math.IntNum r14 = gnu.kawa.functions.Arithmetic.asIntNum((java.lang.Object) r22)
            int r15 = r32.getRoundingMode()
            gnu.math.IntNum r3 = gnu.math.IntNum.quotient(r13, r14, r15)
            goto L_0x01fe
        L_0x01e3:
            gnu.math.IntNum r13 = gnu.kawa.functions.Arithmetic.asIntNum((java.lang.Object) r3)
            gnu.math.IntNum r14 = gnu.kawa.functions.Arithmetic.asIntNum((java.lang.Object) r22)
            gnu.math.RatNum r3 = gnu.math.RatNum.make(r13, r14)
            boolean r13 = r3 instanceof gnu.math.IntNum
            if (r13 == 0) goto L_0x01f6
            r25 = 4
            goto L_0x01f9
        L_0x01f6:
            r13 = 6
            r25 = 6
        L_0x01f9:
            r5 = r25
            r13 = r5
            r28 = r13
        L_0x01fe:
            r13 = r1
            r15 = r4
            r16 = r6
            r2 = r28
            goto L_0x02d3
        L_0x0206:
            r28 = r2
            r1 = r15
            r2 = r16
            long r11 = gnu.kawa.functions.Arithmetic.asLong(r3)
            long r13 = gnu.kawa.functions.Arithmetic.asLong(r22)
            int r4 = r0.op
            switch(r4) {
                case 8: goto L_0x021a;
                default: goto L_0x0218;
            }
        L_0x0218:
            long r11 = r11 / r13
            goto L_0x021c
        L_0x021a:
            long r11 = r11 % r13
        L_0x021c:
            java.lang.Long r3 = java.lang.Long.valueOf(r11)
            r15 = r1
            r16 = r2
            r2 = r28
            goto L_0x02d3
        L_0x0227:
            r28 = r2
            int r1 = gnu.kawa.functions.Arithmetic.asInt(r3)
            int r2 = gnu.kawa.functions.Arithmetic.asInt(r22)
            int r4 = r0.op
            switch(r4) {
                case 8: goto L_0x0238;
                default: goto L_0x0236;
            }
        L_0x0236:
            int r1 = r1 / r2
            goto L_0x023a
        L_0x0238:
            int r1 = r1 % r2
        L_0x023a:
            java.lang.Integer r3 = java.lang.Integer.valueOf(r1)
            r15 = r1
            r16 = r2
            r2 = r28
            goto L_0x02d3
        L_0x0245:
            gnu.math.Numeric r2 = r6.toInexact()
        L_0x0249:
            return r2
        L_0x024a:
            gnu.math.Numeric r2 = r6.div(r1)
            int r4 = r0.op
            r29 = r5
            r5 = 8
            if (r4 != r5) goto L_0x0269
            r4 = r2
            gnu.math.RealNum r4 = (gnu.math.RealNum) r4
            int r5 = r32.getRoundingMode()
            gnu.math.RealNum r4 = r4.toInt(r5)
            gnu.math.Numeric r4 = r4.mul(r1)
            gnu.math.Numeric r2 = r6.sub(r4)
        L_0x0269:
            int r4 = r0.op
            switch(r4) {
                case 5: goto L_0x02bc;
                case 6: goto L_0x029f;
                case 7: goto L_0x0283;
                default: goto L_0x026e;
            }
        L_0x026e:
            r3 = r2
            r18 = r17
            r2 = r28
            r5 = r29
            r17 = r8
            r8 = r7
            r7 = r20
            r30 = r15
            r15 = r21
            r20 = r30
            r16 = r24
            goto L_0x02d3
        L_0x0283:
            r4 = r2
            gnu.math.RealNum r4 = (gnu.math.RealNum) r4
            int r5 = r0.rounding_mode
            gnu.math.IntNum r3 = r4.toExactInt((int) r5)
            r4 = 4
            r5 = r4
            r2 = r4
            r18 = r17
            r17 = r8
            r8 = r7
            r7 = r20
            r30 = r15
            r15 = r21
            r20 = r30
            r16 = r24
            goto L_0x02d3
        L_0x029f:
            r4 = r2
            gnu.math.RealNum r4 = (gnu.math.RealNum) r4
            int r5 = r0.rounding_mode
            gnu.math.RealNum r3 = r4.toInt(r5)
            r18 = r17
            r2 = r28
            r5 = r29
            r17 = r8
            r8 = r7
            r7 = r20
            r30 = r15
            r15 = r21
            r20 = r30
            r16 = r24
            goto L_0x02d3
        L_0x02bc:
            gnu.math.Numeric r3 = r2.toInexact()
            r18 = r17
            r2 = r28
            r5 = r29
            r17 = r8
            r8 = r7
            r7 = r20
            r30 = r15
            r15 = r21
            r20 = r30
            r16 = r24
        L_0x02d3:
            if (r5 == r2) goto L_0x0306
            switch(r5) {
                case 1: goto L_0x02fd;
                case 2: goto L_0x02f3;
                case 3: goto L_0x02ed;
                case 4: goto L_0x02d8;
                case 5: goto L_0x02d8;
                case 6: goto L_0x02d8;
                case 7: goto L_0x02e3;
                case 8: goto L_0x02d9;
                default: goto L_0x02d8;
            }
        L_0x02d8:
            goto L_0x0306
        L_0x02d9:
            double r24 = r3.doubleValue()
            java.lang.Double r1 = java.lang.Double.valueOf(r24)
            r3 = r1
            goto L_0x0306
        L_0x02e3:
            float r1 = r3.floatValue()
            java.lang.Float r1 = java.lang.Float.valueOf(r1)
            r3 = r1
            goto L_0x0306
        L_0x02ed:
            java.math.BigInteger r1 = gnu.kawa.functions.Arithmetic.asBigInteger(r3)
            r3 = r1
            goto L_0x0306
        L_0x02f3:
            long r24 = r3.longValue()
            java.lang.Long r1 = java.lang.Long.valueOf(r24)
            r3 = r1
            goto L_0x0306
        L_0x02fd:
            int r1 = r3.intValue()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r3 = r1
        L_0x0306:
            int r6 = r27 + 1
            r1 = r33
            r2 = r26
            r4 = 1
            goto L_0x0036
        L_0x030f:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.DivideOp.applyN(java.lang.Object[]):java.lang.Object");
    }

    public int numArgs() {
        return this.op == 4 ? -4095 : 8194;
    }
}
