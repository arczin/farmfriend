package gnu.math;

public class BitOps {
    static final byte[] bit4_count = {0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4};

    private BitOps() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0029 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0014 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean bitValue(gnu.math.IntNum r6, int r7) {
        /*
            int r0 = r6.ival
            int[] r1 = r6.words
            r2 = 0
            r3 = 1
            if (r1 != 0) goto L_0x0016
            r1 = 32
            if (r7 < r1) goto L_0x000f
            if (r0 >= 0) goto L_0x0015
            goto L_0x0014
        L_0x000f:
            int r1 = r0 >> r7
            r1 = r1 & r3
            if (r1 == 0) goto L_0x0015
        L_0x0014:
            r2 = 1
        L_0x0015:
            return r2
        L_0x0016:
            int r1 = r7 >> 5
            int[] r4 = r6.words
            if (r1 < r0) goto L_0x0023
            int r5 = r0 + -1
            r4 = r4[r5]
            if (r4 >= 0) goto L_0x002a
            goto L_0x0029
        L_0x0023:
            r4 = r4[r1]
            int r4 = r4 >> r7
            r4 = r4 & r3
            if (r4 == 0) goto L_0x002a
        L_0x0029:
            r2 = 1
        L_0x002a:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.math.BitOps.bitValue(gnu.math.IntNum, int):boolean");
    }

    static int[] dataBufferFor(IntNum x, int bitno) {
        int[] data;
        int i = x.ival;
        int nwords = (bitno + 1) >> 5;
        if (x.words == null) {
            if (nwords == 0) {
                nwords = 1;
            }
            data = new int[nwords];
            data[0] = i;
            if (i < 0) {
                for (int j = 1; j < nwords; j++) {
                    data[j] = -1;
                }
            }
        } else {
            int nwords2 = (bitno + 1) >> 5;
            data = new int[(nwords2 > i ? nwords2 : i)];
            int j2 = i;
            while (true) {
                j2--;
                if (j2 < 0) {
                    break;
                }
                data[j2] = x.words[j2];
            }
            if (data[i - 1] < 0) {
                for (int j3 = i; j3 < nwords2; j3++) {
                    data[j3] = -1;
                }
            }
        }
        return data;
    }

    public static IntNum setBitValue(IntNum x, int bitno, int newValue) {
        int oldValue;
        int newValue2 = newValue & 1;
        int i = x.ival;
        if (x.words == null) {
            int i2 = 31;
            if (bitno < 31) {
                i2 = bitno;
            }
            if (((i >> i2) & 1) == newValue2) {
                return x;
            }
            if (bitno < 63) {
                return IntNum.make(((long) i) ^ ((long) (1 << bitno)));
            }
        } else {
            int wordno = bitno >> 5;
            if (wordno >= i) {
                oldValue = x.words[i + -1] < 0 ? 1 : 0;
            } else {
                oldValue = (x.words[wordno] >> bitno) & 1;
            }
            if (oldValue == newValue2) {
                return x;
            }
        }
        int[] data = dataBufferFor(x, bitno);
        int i3 = bitno >> 5;
        data[i3] = (1 << (bitno & 31)) ^ data[i3];
        return IntNum.make(data, data.length);
    }

    public static boolean test(IntNum x, int y) {
        if (x.words == null) {
            if ((x.ival & y) != 0) {
                return true;
            }
            return false;
        } else if (y < 0 || (x.words[0] & y) != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean test(IntNum x, IntNum y) {
        if (y.words == null) {
            return test(x, y.ival);
        }
        if (x.words == null) {
            return test(y, x.ival);
        }
        if (x.ival < y.ival) {
            IntNum temp = x;
            x = y;
            y = temp;
        }
        for (int i = 0; i < y.ival; i++) {
            if ((x.words[i] & y.words[i]) != 0) {
                return true;
            }
        }
        return y.isNegative();
    }

    public static IntNum and(IntNum x, int y) {
        if (x.words == null) {
            return IntNum.make(x.ival & y);
        }
        if (y >= 0) {
            return IntNum.make(x.words[0] & y);
        }
        int len = x.ival;
        int[] words = new int[len];
        words[0] = x.words[0] & y;
        while (true) {
            len--;
            if (len <= 0) {
                return IntNum.make(words, x.ival);
            }
            words[len] = x.words[len];
        }
    }

    public static IntNum and(IntNum x, IntNum y) {
        if (y.words == null) {
            return and(x, y.ival);
        }
        if (x.words == null) {
            return and(y, x.ival);
        }
        if (x.ival < y.ival) {
            IntNum temp = x;
            x = y;
            y = temp;
        }
        int len = y.isNegative() ? x.ival : y.ival;
        int[] words = new int[len];
        int i = 0;
        while (i < y.ival) {
            words[i] = x.words[i] & y.words[i];
            i++;
        }
        while (i < len) {
            words[i] = x.words[i];
            i++;
        }
        return IntNum.make(words, len);
    }

    public static IntNum ior(IntNum x, IntNum y) {
        return bitOp(7, x, y);
    }

    public static IntNum xor(IntNum x, IntNum y) {
        return bitOp(6, x, y);
    }

    public static IntNum not(IntNum x) {
        return bitOp(12, x, IntNum.zero());
    }

    public static int swappedOp(int op) {
        return "\u0000\u0001\u0004\u0005\u0002\u0003\u0006\u0007\b\t\f\r\n\u000b\u000e\u000f".charAt(op);
    }

    public static IntNum bitOp(int op, IntNum x, IntNum y) {
        switch (op) {
            case 0:
                return IntNum.zero();
            case 1:
                return and(x, y);
            case 3:
                return x;
            case 5:
                return y;
            case 15:
                return IntNum.minusOne();
            default:
                IntNum result = new IntNum();
                setBitOp(result, op, x, y);
                return result.canonicalize();
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00bd, code lost:
        if ((r6 + 1) < r2) goto L_0x00c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00bf, code lost:
        if (r0 < 0) goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00c2, code lost:
        r8 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00c3, code lost:
        r7 = r8;
        r8 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x010f, code lost:
        if ((r6 + 1) < r2) goto L_0x0119;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0111, code lost:
        if (r0 >= 0) goto L_0x0114;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0114, code lost:
        r8 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0115, code lost:
        r7 = r8;
        r8 = r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void setBitOp(gnu.math.IntNum r11, int r12, gnu.math.IntNum r13, gnu.math.IntNum r14) {
        /*
            int[] r0 = r14.words
            if (r0 != 0) goto L_0x0005
            goto L_0x0016
        L_0x0005:
            int[] r0 = r13.words
            if (r0 == 0) goto L_0x000f
            int r0 = r13.ival
            int r1 = r14.ival
            if (r0 >= r1) goto L_0x0016
        L_0x000f:
            r0 = r13
            r13 = r14
            r14 = r0
            int r12 = swappedOp(r12)
        L_0x0016:
            int[] r0 = r14.words
            r1 = 0
            if (r0 != 0) goto L_0x001f
            int r0 = r14.ival
            r2 = 1
            goto L_0x0025
        L_0x001f:
            int[] r0 = r14.words
            r0 = r0[r1]
            int r2 = r14.ival
        L_0x0025:
            int[] r3 = r13.words
            if (r3 != 0) goto L_0x002d
            int r1 = r13.ival
            r3 = 1
            goto L_0x0033
        L_0x002d:
            int[] r3 = r13.words
            r1 = r3[r1]
            int r3 = r13.ival
        L_0x0033:
            r4 = 1
            if (r3 <= r4) goto L_0x0039
            r11.realloc(r3)
        L_0x0039:
            int[] r5 = r11.words
            r6 = 0
            r7 = 0
            r8 = 2
            r9 = 0
            switch(r12) {
                case 0: goto L_0x0190;
                case 1: goto L_0x0177;
                case 2: goto L_0x015c;
                case 3: goto L_0x0158;
                case 4: goto L_0x013d;
                case 5: goto L_0x0127;
                case 6: goto L_0x010b;
                case 7: goto L_0x00f1;
                case 8: goto L_0x00d5;
                case 9: goto L_0x00b7;
                case 10: goto L_0x00a0;
                case 11: goto L_0x0084;
                case 12: goto L_0x007e;
                case 13: goto L_0x0062;
                case 14: goto L_0x0046;
                default: goto L_0x0042;
            }
        L_0x0042:
            r8 = r9
            r8 = -1
            goto L_0x0192
        L_0x0046:
            r8 = r9
        L_0x0047:
            r9 = r1 & r0
            r8 = r9 ^ -1
            int r9 = r6 + 1
            if (r9 < r2) goto L_0x0054
            if (r0 >= 0) goto L_0x0192
            r7 = 2
            goto L_0x0192
        L_0x0054:
            int r9 = r6 + 1
            r5[r6] = r8
            int[] r6 = r13.words
            r1 = r6[r9]
            int[] r6 = r14.words
            r0 = r6[r9]
            r6 = r9
            goto L_0x0047
        L_0x0062:
            r8 = r9
        L_0x0063:
            r9 = r1 ^ -1
            r8 = r9 | r0
            int r9 = r6 + 1
            if (r9 < r2) goto L_0x0070
            if (r0 < 0) goto L_0x0192
            r7 = 2
            goto L_0x0192
        L_0x0070:
            int r9 = r6 + 1
            r5[r6] = r8
            int[] r6 = r13.words
            r1 = r6[r9]
            int[] r6 = r14.words
            r0 = r6[r9]
            r6 = r9
            goto L_0x0063
        L_0x007e:
            r8 = r9
            r8 = r1 ^ -1
            r7 = 2
            goto L_0x0192
        L_0x0084:
            r8 = r9
        L_0x0085:
            r9 = r0 ^ -1
            r8 = r1 | r9
            int r9 = r6 + 1
            if (r9 < r2) goto L_0x0092
            if (r0 >= 0) goto L_0x0192
            r7 = 1
            goto L_0x0192
        L_0x0092:
            int r9 = r6 + 1
            r5[r6] = r8
            int[] r6 = r13.words
            r1 = r6[r9]
            int[] r6 = r14.words
            r0 = r6[r9]
            r6 = r9
            goto L_0x0085
        L_0x00a0:
            r8 = r9
        L_0x00a1:
            r8 = r0 ^ -1
            int r9 = r6 + 1
            if (r9 < r2) goto L_0x00a9
            goto L_0x0192
        L_0x00a9:
            int r9 = r6 + 1
            r5[r6] = r8
            int[] r6 = r13.words
            r1 = r6[r9]
            int[] r6 = r14.words
            r0 = r6[r9]
            r6 = r9
            goto L_0x00a1
        L_0x00b7:
            r10 = r1 ^ r0
            r9 = r10 ^ -1
            int r10 = r6 + 1
            if (r10 < r2) goto L_0x00c7
            if (r0 < 0) goto L_0x00c2
            goto L_0x00c3
        L_0x00c2:
            r8 = 1
        L_0x00c3:
            r7 = r8
            r8 = r9
            goto L_0x0192
        L_0x00c7:
            int r10 = r6 + 1
            r5[r6] = r9
            int[] r6 = r13.words
            r1 = r6[r10]
            int[] r6 = r14.words
            r0 = r6[r10]
            r6 = r10
            goto L_0x00b7
        L_0x00d5:
            r8 = r9
        L_0x00d6:
            r9 = r1 | r0
            r8 = r9 ^ -1
            int r9 = r6 + 1
            if (r9 < r2) goto L_0x00e3
            if (r0 < 0) goto L_0x0192
            r7 = 2
            goto L_0x0192
        L_0x00e3:
            int r9 = r6 + 1
            r5[r6] = r8
            int[] r6 = r13.words
            r1 = r6[r9]
            int[] r6 = r14.words
            r0 = r6[r9]
            r6 = r9
            goto L_0x00d6
        L_0x00f1:
            r8 = r9
        L_0x00f2:
            r8 = r1 | r0
            int r9 = r6 + 1
            if (r9 < r2) goto L_0x00fd
            if (r0 < 0) goto L_0x0192
            r7 = 1
            goto L_0x0192
        L_0x00fd:
            int r9 = r6 + 1
            r5[r6] = r8
            int[] r6 = r13.words
            r1 = r6[r9]
            int[] r6 = r14.words
            r0 = r6[r9]
            r6 = r9
            goto L_0x00f2
        L_0x010b:
            r9 = r1 ^ r0
            int r10 = r6 + 1
            if (r10 < r2) goto L_0x0119
            if (r0 >= 0) goto L_0x0114
            goto L_0x0115
        L_0x0114:
            r8 = 1
        L_0x0115:
            r7 = r8
            r8 = r9
            goto L_0x0192
        L_0x0119:
            int r10 = r6 + 1
            r5[r6] = r9
            int[] r6 = r13.words
            r1 = r6[r10]
            int[] r6 = r14.words
            r0 = r6[r10]
            r6 = r10
            goto L_0x010b
        L_0x0127:
            r8 = r9
        L_0x0128:
            r8 = r0
            int r9 = r6 + 1
            if (r9 < r2) goto L_0x012f
            goto L_0x0192
        L_0x012f:
            int r9 = r6 + 1
            r5[r6] = r8
            int[] r6 = r13.words
            r1 = r6[r9]
            int[] r6 = r14.words
            r0 = r6[r9]
            r6 = r9
            goto L_0x0128
        L_0x013d:
            r8 = r9
        L_0x013e:
            r9 = r1 ^ -1
            r8 = r9 & r0
            int r9 = r6 + 1
            if (r9 < r2) goto L_0x014a
            if (r0 >= 0) goto L_0x0192
            r7 = 2
            goto L_0x0192
        L_0x014a:
            int r9 = r6 + 1
            r5[r6] = r8
            int[] r6 = r13.words
            r1 = r6[r9]
            int[] r6 = r14.words
            r0 = r6[r9]
            r6 = r9
            goto L_0x013e
        L_0x0158:
            r8 = r9
            r8 = r1
            r7 = 1
            goto L_0x0192
        L_0x015c:
            r8 = r9
        L_0x015d:
            r9 = r0 ^ -1
            r8 = r1 & r9
            int r9 = r6 + 1
            if (r9 < r2) goto L_0x0169
            if (r0 < 0) goto L_0x0192
            r7 = 1
            goto L_0x0192
        L_0x0169:
            int r9 = r6 + 1
            r5[r6] = r8
            int[] r6 = r13.words
            r1 = r6[r9]
            int[] r6 = r14.words
            r0 = r6[r9]
            r6 = r9
            goto L_0x015d
        L_0x0177:
            r8 = r9
        L_0x0178:
            r8 = r1 & r0
            int r9 = r6 + 1
            if (r9 < r2) goto L_0x0182
            if (r0 >= 0) goto L_0x0192
            r7 = 1
            goto L_0x0192
        L_0x0182:
            int r9 = r6 + 1
            r5[r6] = r8
            int[] r6 = r13.words
            r1 = r6[r9]
            int[] r6 = r14.words
            r0 = r6[r9]
            r6 = r9
            goto L_0x0178
        L_0x0190:
            r8 = 0
        L_0x0192:
            int r9 = r6 + 1
            if (r9 != r3) goto L_0x0197
            r7 = 0
        L_0x0197:
            switch(r7) {
                case 0: goto L_0x01b5;
                case 1: goto L_0x01a9;
                case 2: goto L_0x019b;
                default: goto L_0x019a;
            }
        L_0x019a:
            goto L_0x01c1
        L_0x019b:
            r5[r6] = r8
        L_0x019d:
            int r6 = r6 + r4
            if (r6 >= r3) goto L_0x01c1
            int[] r9 = r13.words
            r9 = r9[r6]
            r9 = r9 ^ -1
            r5[r6] = r9
            goto L_0x019d
        L_0x01a9:
            r5[r6] = r8
        L_0x01ab:
            int r6 = r6 + r4
            if (r6 >= r3) goto L_0x01c1
            int[] r9 = r13.words
            r9 = r9[r6]
            r5[r6] = r9
            goto L_0x01ab
        L_0x01b5:
            if (r6 != 0) goto L_0x01bc
            if (r5 != 0) goto L_0x01bc
            r11.ival = r8
            return
        L_0x01bc:
            int r4 = r6 + 1
            r5[r6] = r8
            r6 = r4
        L_0x01c1:
            r11.ival = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.math.BitOps.setBitOp(gnu.math.IntNum, int, gnu.math.IntNum, gnu.math.IntNum):void");
    }

    public static IntNum extract(IntNum x, int startBit, int endBit) {
        int x_len;
        long l;
        int i = 0;
        if (endBit < 32) {
            return IntNum.make(((-1 ^ (-1 << endBit)) & (x.words == null ? x.ival : x.words[0])) >> startBit);
        }
        int i2 = 31;
        if (x.words != null) {
            x_len = x.ival;
        } else if (x.ival >= 0) {
            if (startBit < 31) {
                i = x.ival >> startBit;
            }
            return IntNum.make(i);
        } else {
            x_len = 1;
        }
        boolean neg = x.isNegative();
        if (endBit > x_len * 32) {
            endBit = x_len * 32;
            if (!neg && startBit == 0) {
                return x;
            }
        } else {
            x_len = (endBit + 31) >> 5;
        }
        int length = endBit - startBit;
        if (length < 64) {
            if (x.words == null) {
                int i3 = x.ival;
                if (startBit < 32) {
                    i2 = startBit;
                }
                l = (long) (i3 >> i2);
            } else {
                l = MPN.rshift_long(x.words, x_len, startBit);
            }
            return IntNum.make((-1 ^ (-1 << length)) & l);
        }
        int startWord = startBit >> 5;
        int[] buf = new int[(((endBit >> 5) + 1) - startWord)];
        if (x.words == null) {
            buf[0] = startBit >= 32 ? -1 : x.ival >> startBit;
        } else {
            MPN.rshift0(buf, x.words, startWord, x_len - startWord, startBit & 31);
        }
        int x_len2 = length >> 5;
        buf[x_len2] = (-1 ^ (-1 << length)) & buf[x_len2];
        return IntNum.make(buf, x_len2 + 1);
    }

    public static int lowestBitSet(int i) {
        if (i == 0) {
            return -1;
        }
        int index = 0;
        while ((i & 255) == 0) {
            i >>>= 8;
            index += 8;
        }
        while ((i & 3) == 0) {
            i >>>= 2;
            index += 2;
        }
        if ((i & 1) == 0) {
            return index + 1;
        }
        return index;
    }

    public static int lowestBitSet(IntNum x) {
        int[] x_words = x.words;
        if (x_words == null) {
            return lowestBitSet(x.ival);
        }
        int x_len = x.ival;
        while (0 < x_len) {
            int b = lowestBitSet(x_words[0]);
            if (b >= 0) {
                return (0 * 32) + b;
            }
        }
        return -1;
    }

    public static int bitCount(int i) {
        int count = 0;
        while (i != 0) {
            count += bit4_count[i & 15];
            i >>>= 4;
        }
        return count;
    }

    public static int bitCount(int[] x, int len) {
        int count = 0;
        while (true) {
            len--;
            if (len < 0) {
                return count;
            }
            count += bitCount(x[len]);
        }
    }

    public static int bitCount(IntNum x) {
        int i;
        int x_len;
        int[] x_words = x.words;
        if (x_words == null) {
            x_len = 1;
            i = bitCount(x.ival);
        } else {
            x_len = x.ival;
            i = bitCount(x_words, x_len);
        }
        return x.isNegative() ? (x_len * 32) - i : i;
    }

    public static IntNum reverseBits(IntNum x, int start, int end) {
        long j;
        int wj;
        IntNum intNum = x;
        int i = end;
        int ival = intNum.ival;
        long j2 = -1;
        if (intNum.words != null || i >= 63) {
            int[] data = dataBufferFor(intNum, i - 1);
            int i2 = start;
            int j3 = i - 1;
            while (i2 < j3) {
                int ii = i2 >> 5;
                int jj = j3 >> 5;
                int wi = data[ii];
                int biti = (wi >> i2) & 1;
                if (ii == jj) {
                    j = -1;
                    wj = (biti << j3) | ((int) (((long) wi) & (((1 << i2) | (1 << j3)) ^ -1))) | (((wi >> j3) & 1) << i2);
                } else {
                    j = j2;
                    int wj2 = data[jj];
                    data[jj] = (wj2 & ((1 << (j3 & 31)) ^ -1)) | (biti << (j3 & 31));
                    wj = (wi & ((1 << (i2 & 31)) ^ -1)) | (((wj2 >> (j3 & 31)) & 1) << (i2 & 31));
                }
                data[ii] = wj;
                i2++;
                j3--;
                j2 = j;
            }
            return IntNum.make(data, data.length);
        }
        long w = (long) ival;
        int i3 = start;
        for (int j4 = i - 1; i3 < j4; j4--) {
            w = (w & (((1 << i3) | (1 << j4)) ^ -1)) | (((w >> i3) & 1) << j4) | (((w >> j4) & 1) << i3);
            i3++;
        }
        return IntNum.make(w);
    }
}
