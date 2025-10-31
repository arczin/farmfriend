package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.runtime.util.Ev3Constants;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;

public class Ev3BinaryParser {
    private static byte PRIMPAR_1_BYTE = 1;
    private static byte PRIMPAR_2_BYTES = 2;
    private static byte PRIMPAR_4_BYTES = 3;
    private static byte PRIMPAR_ADDR = 8;
    private static byte PRIMPAR_BYTES = 7;
    private static byte PRIMPAR_CONST = 0;
    private static byte PRIMPAR_CONST_SIGN = 32;
    private static byte PRIMPAR_GLOBAL = 32;
    private static byte PRIMPAR_HANDLE = 16;
    private static byte PRIMPAR_INDEX = 31;
    private static byte PRIMPAR_LOCAL = 0;
    private static byte PRIMPAR_LONG = Byte.MIN_VALUE;
    private static byte PRIMPAR_SHORT = 0;
    private static byte PRIMPAR_STRING = 4;
    private static byte PRIMPAR_STRING_OLD = 0;
    private static byte PRIMPAR_VALUE = Ev3Constants.Opcode.MOVEF_F;
    private static byte PRIMPAR_VARIABEL = Ev3Constants.Opcode.JR;

    private static class FormatLiteral {
        public int size;
        public char symbol;

        public FormatLiteral(char symbol2, int size2) {
            this.symbol = symbol2;
            this.size = size2;
        }
    }

    public static byte[] pack(String format, Object... values) throws IllegalArgumentException {
        String[] formatTokens = format.split("(?<=\\D)");
        FormatLiteral[] literals = new FormatLiteral[formatTokens.length];
        int index = 0;
        int bufferCapacity = 0;
        for (int i = 0; i < formatTokens.length; i++) {
            String token = formatTokens[i];
            char symbol = token.charAt(token.length() - 1);
            int size = 1;
            boolean sizeSpecified = false;
            if (token.length() != 1) {
                size = Integer.parseInt(token.substring(0, token.length() - 1));
                sizeSpecified = true;
                if (size < 1) {
                    throw new IllegalArgumentException("Illegal format string");
                }
            }
            switch (symbol) {
                case 'B':
                    bufferCapacity += size;
                    index++;
                    break;
                case 'F':
                    bufferCapacity += size * 4;
                    index++;
                    break;
                case 'H':
                    bufferCapacity += size * 2;
                    index++;
                    break;
                case 'I':
                    bufferCapacity += size * 4;
                    index++;
                    break;
                case 'L':
                    bufferCapacity += size * 8;
                    index++;
                    break;
                case 'S':
                    if (!sizeSpecified) {
                        bufferCapacity += values[index].length() + 1;
                        index++;
                        break;
                    } else {
                        throw new IllegalArgumentException("Illegal format string");
                    }
                case 'b':
                    bufferCapacity += size;
                    index += size;
                    break;
                case 'f':
                    bufferCapacity += size * 4;
                    index += size;
                    break;
                case 'h':
                    bufferCapacity += size * 2;
                    index += size;
                    break;
                case 'i':
                    bufferCapacity += size * 4;
                    index += size;
                    break;
                case 'l':
                    bufferCapacity += size * 8;
                    index += size;
                    break;
                case 's':
                    if (size == values[index].length()) {
                        bufferCapacity += size;
                        index++;
                        break;
                    } else {
                        throw new IllegalArgumentException("Illegal format string");
                    }
                case 'x':
                    bufferCapacity += size;
                    break;
                default:
                    throw new IllegalArgumentException("Illegal format string");
            }
            literals[i] = new FormatLiteral(symbol, size);
        }
        if (index == values.length) {
            int index2 = 0;
            ByteBuffer buffer = ByteBuffer.allocate(bufferCapacity);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            for (FormatLiteral literal : literals) {
                switch (literal.symbol) {
                    case 'B':
                        buffer.put(values[index2]);
                        index2++;
                        break;
                    case 'F':
                        for (int i2 = 0; i2 < literal.size; i2++) {
                            buffer.putFloat(values[index2][i2]);
                        }
                        index2++;
                        break;
                    case 'H':
                        for (int i3 = 0; i3 < literal.size; i3++) {
                            buffer.putShort(values[index2][i3]);
                        }
                        index2++;
                        break;
                    case 'I':
                        for (int i4 = 0; i4 < literal.size; i4++) {
                            buffer.putInt(values[index2][i4]);
                        }
                        index2++;
                        break;
                    case 'L':
                        for (int i5 = 0; i5 < literal.size; i5++) {
                            buffer.putLong(values[index2][i5]);
                        }
                        index2++;
                        break;
                    case 'S':
                        try {
                            buffer.put(values[index2].getBytes("US-ASCII"));
                            buffer.put((byte) 0);
                            index2++;
                            break;
                        } catch (UnsupportedEncodingException e) {
                            throw new IllegalArgumentException();
                        }
                    case 'b':
                        for (int i6 = 0; i6 < literal.size; i6++) {
                            buffer.put(values[index2].byteValue());
                            index2++;
                        }
                        break;
                    case 'f':
                        for (int i7 = 0; i7 < literal.size; i7++) {
                            buffer.putFloat(values[index2].floatValue());
                            index2++;
                        }
                        break;
                    case 'h':
                        for (int i8 = 0; i8 < literal.size; i8++) {
                            buffer.putShort(values[index2].shortValue());
                            index2++;
                        }
                        break;
                    case 'i':
                        for (int i9 = 0; i9 < literal.size; i9++) {
                            buffer.putInt(values[index2].intValue());
                            index2++;
                        }
                        break;
                    case 'l':
                        for (int i10 = 0; i10 < literal.size; i10++) {
                            buffer.putLong(values[index2].longValue());
                            index2++;
                        }
                        break;
                    case 's':
                        try {
                            buffer.put(values[index2].getBytes("US-ASCII"));
                            index2++;
                            break;
                        } catch (UnsupportedEncodingException e2) {
                            throw new IllegalArgumentException();
                        }
                    case 'x':
                        for (int i11 = 0; i11 < literal.size; i11++) {
                            buffer.put((byte) 0);
                        }
                        break;
                }
            }
            return buffer.array();
        }
        throw new IllegalArgumentException("Illegal format string");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:58:0x013f, code lost:
        r5 = r5 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object[] unpack(java.lang.String r14, byte[] r15) throws java.lang.IllegalArgumentException {
        /*
            java.lang.String r0 = "(?<=\\D)"
            java.lang.String[] r0 = r14.split(r0)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.nio.ByteBuffer r2 = java.nio.ByteBuffer.wrap(r15)
            java.nio.ByteOrder r3 = java.nio.ByteOrder.LITTLE_ENDIAN
            r2.order(r3)
            int r3 = r0.length
            r4 = 0
            r5 = 0
        L_0x0017:
            if (r5 >= r3) goto L_0x015d
            r6 = r0[r5]
            r7 = 0
            r8 = 1
            int r9 = r6.length()
            r10 = 1
            int r9 = r9 - r10
            char r9 = r6.charAt(r9)
            int r11 = r6.length()
            java.lang.String r12 = "Illegal format string"
            if (r11 <= r10) goto L_0x0046
            r7 = 1
            int r11 = r6.length()
            int r11 = r11 - r10
            java.lang.String r11 = r6.substring(r4, r11)
            int r8 = java.lang.Integer.parseInt(r11)
            if (r8 < r10) goto L_0x0040
            goto L_0x0046
        L_0x0040:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            r3.<init>(r12)
            throw r3
        L_0x0046:
            switch(r9) {
                case 36: goto L_0x0143;
                case 66: goto L_0x0136;
                case 70: goto L_0x0124;
                case 72: goto L_0x0111;
                case 73: goto L_0x00ff;
                case 76: goto L_0x00ed;
                case 83: goto L_0x00cd;
                case 98: goto L_0x00ba;
                case 102: goto L_0x00a7;
                case 104: goto L_0x0094;
                case 105: goto L_0x0081;
                case 108: goto L_0x006e;
                case 115: goto L_0x0056;
                case 120: goto L_0x004b;
                default: goto L_0x0049;
            }
        L_0x0049:
            goto L_0x0157
        L_0x004b:
            r10 = 0
        L_0x004c:
            if (r10 >= r8) goto L_0x0054
            r2.get()
            int r10 = r10 + 1
            goto L_0x004c
        L_0x0054:
            goto L_0x013f
        L_0x0056:
            byte[] r10 = new byte[r8]
            r2.get(r10, r4, r8)
            java.lang.String r11 = new java.lang.String     // Catch:{ UnsupportedEncodingException -> 0x0067 }
            java.lang.String r12 = "US-ASCII"
            r11.<init>(r10, r12)     // Catch:{ UnsupportedEncodingException -> 0x0067 }
            r1.add(r11)     // Catch:{ UnsupportedEncodingException -> 0x0067 }
            goto L_0x013f
        L_0x0067:
            r3 = move-exception
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            r4.<init>()
            throw r4
        L_0x006e:
            r10 = 0
        L_0x006f:
            if (r10 >= r8) goto L_0x007f
            long r11 = r2.getLong()
            java.lang.Long r11 = java.lang.Long.valueOf(r11)
            r1.add(r11)
            int r10 = r10 + 1
            goto L_0x006f
        L_0x007f:
            goto L_0x013f
        L_0x0081:
            r10 = 0
        L_0x0082:
            if (r10 >= r8) goto L_0x0092
            int r11 = r2.getInt()
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
            r1.add(r11)
            int r10 = r10 + 1
            goto L_0x0082
        L_0x0092:
            goto L_0x013f
        L_0x0094:
            r10 = 0
        L_0x0095:
            if (r10 >= r8) goto L_0x00a5
            short r11 = r2.getShort()
            java.lang.Short r11 = java.lang.Short.valueOf(r11)
            r1.add(r11)
            int r10 = r10 + 1
            goto L_0x0095
        L_0x00a5:
            goto L_0x013f
        L_0x00a7:
            r10 = 0
        L_0x00a8:
            if (r10 >= r8) goto L_0x00b8
            float r11 = r2.getFloat()
            java.lang.Float r11 = java.lang.Float.valueOf(r11)
            r1.add(r11)
            int r10 = r10 + 1
            goto L_0x00a8
        L_0x00b8:
            goto L_0x013f
        L_0x00ba:
            r10 = 0
        L_0x00bb:
            if (r10 >= r8) goto L_0x00cb
            byte r11 = r2.get()
            java.lang.Byte r11 = java.lang.Byte.valueOf(r11)
            r1.add(r11)
            int r10 = r10 + 1
            goto L_0x00bb
        L_0x00cb:
            goto L_0x013f
        L_0x00cd:
            if (r7 != 0) goto L_0x00e7
            java.lang.StringBuffer r10 = new java.lang.StringBuffer
            r10.<init>()
        L_0x00d4:
            byte r11 = r2.get()
            if (r11 == 0) goto L_0x00df
            char r12 = (char) r11
            r10.append(r12)
            goto L_0x00d4
        L_0x00df:
            java.lang.String r11 = r10.toString()
            r1.add(r11)
            goto L_0x013f
        L_0x00e7:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            r3.<init>(r12)
            throw r3
        L_0x00ed:
            long[] r10 = new long[r8]
            r11 = 0
        L_0x00f0:
            if (r11 >= r8) goto L_0x00fb
            long r12 = r2.getLong()
            r10[r11] = r12
            int r11 = r11 + 1
            goto L_0x00f0
        L_0x00fb:
            r1.add(r10)
            goto L_0x013f
        L_0x00ff:
            int[] r10 = new int[r8]
            r11 = 0
        L_0x0102:
            if (r11 >= r8) goto L_0x010d
            int r12 = r2.getInt()
            r10[r11] = r12
            int r11 = r11 + 1
            goto L_0x0102
        L_0x010d:
            r1.add(r10)
            goto L_0x013f
        L_0x0111:
            short[] r10 = new short[r8]
            r11 = 0
        L_0x0114:
            if (r11 >= r8) goto L_0x0120
            short r12 = r2.getShort()
            r10[r11] = r12
            int r12 = r11 + 1
            short r11 = (short) r12
            goto L_0x0114
        L_0x0120:
            r1.add(r10)
            goto L_0x013f
        L_0x0124:
            float[] r10 = new float[r8]
            r11 = 0
        L_0x0127:
            if (r11 >= r8) goto L_0x0132
            float r12 = r2.getFloat()
            r10[r11] = r12
            int r11 = r11 + 1
            goto L_0x0127
        L_0x0132:
            r1.add(r10)
            goto L_0x013f
        L_0x0136:
            byte[] r10 = new byte[r8]
            r2.get(r10, r4, r8)
            r1.add(r10)
        L_0x013f:
            int r5 = r5 + 1
            goto L_0x0017
        L_0x0143:
            if (r7 != 0) goto L_0x0151
            boolean r3 = r2.hasRemaining()
            if (r3 == 0) goto L_0x0157
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            r3.<init>(r12)
            throw r3
        L_0x0151:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            r3.<init>(r12)
            throw r3
        L_0x0157:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            r3.<init>(r12)
            throw r3
        L_0x015d:
            java.lang.Object[] r3 = r1.toArray()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.Ev3BinaryParser.unpack(java.lang.String, byte[]):java.lang.Object[]");
    }

    public static byte[] encodeLC0(byte v) {
        if (v < -31 || v > 31) {
            throw new IllegalArgumentException("Encoded value must be in range [0, 127]");
        }
        return new byte[]{(byte) (PRIMPAR_VALUE & v)};
    }

    public static byte[] encodeLC1(byte v) {
        return new byte[]{(byte) (((byte) (PRIMPAR_LONG | PRIMPAR_CONST)) | PRIMPAR_1_BYTE), (byte) (v & Ev3Constants.Opcode.TST)};
    }

    public static byte[] encodeLC2(short v) {
        return new byte[]{(byte) (((byte) (PRIMPAR_LONG | PRIMPAR_CONST)) | PRIMPAR_2_BYTES), (byte) (v & 255), (byte) ((v >>> 8) & 255)};
    }

    public static byte[] encodeLC4(int v) {
        return new byte[]{(byte) (((byte) (PRIMPAR_LONG | PRIMPAR_CONST)) | PRIMPAR_4_BYTES), (byte) (v & 255), (byte) ((v >>> 8) & 255), (byte) ((v >>> 16) & 255), (byte) ((v >>> 24) & 255)};
    }

    public static byte[] encodeLV0(int i) {
        return new byte[]{(byte) ((PRIMPAR_INDEX & i) | PRIMPAR_SHORT | PRIMPAR_VARIABEL | PRIMPAR_LOCAL)};
    }

    public static byte[] encodeLV1(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_LOCAL | PRIMPAR_1_BYTE), (byte) (i & 255)};
    }

    public static byte[] encodeLV2(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_LOCAL | PRIMPAR_2_BYTES), (byte) (i & 255), (byte) ((i >>> 8) & 255)};
    }

    public static byte[] encodeLV4(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_LOCAL | PRIMPAR_4_BYTES), (byte) (i & 255), (byte) ((i >>> 8) & 255), (byte) ((i >>> 16) & 255), (byte) ((i >>> 24) & 255)};
    }

    public static byte[] encodeGV0(int i) {
        return new byte[]{(byte) ((PRIMPAR_INDEX & i) | PRIMPAR_SHORT | PRIMPAR_VARIABEL | PRIMPAR_GLOBAL)};
    }

    public static byte[] encodeGV1(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_GLOBAL | PRIMPAR_1_BYTE), (byte) (i & 255)};
    }

    public static byte[] encodeGV2(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_GLOBAL | PRIMPAR_2_BYTES), (byte) (i & 255), (byte) ((i >>> 8) & 255)};
    }

    public static byte[] encodeGV4(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_GLOBAL | PRIMPAR_4_BYTES), (byte) (i & 255), (byte) ((i >>> 8) & 255), (byte) ((i >>> 16) & 255), (byte) ((i >>> 24) & 255)};
    }

    public static byte[] encodeSystemCommand(byte command, boolean needReply, Object... parameters) {
        int bufferCapacity = 2;
        int length = parameters.length;
        int i = 0;
        while (true) {
            byte b = 1;
            if (i < length) {
                String str = parameters[i];
                if (str instanceof Byte) {
                    bufferCapacity++;
                } else if (str instanceof Short) {
                    bufferCapacity += 2;
                } else if (str instanceof Integer) {
                    bufferCapacity += 4;
                } else if (str instanceof String) {
                    bufferCapacity += str.length() + 1;
                } else {
                    throw new IllegalArgumentException("Parameters should be one of the class types: Byte, Short, Integer, String");
                }
                i++;
            } else {
                ByteBuffer buffer = ByteBuffer.allocate(bufferCapacity);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                if (!needReply) {
                    b = -127;
                }
                buffer.put(b);
                buffer.put(command);
                for (Byte b2 : parameters) {
                    if (b2 instanceof Byte) {
                        buffer.put(b2.byteValue());
                    } else if (b2 instanceof Short) {
                        buffer.putShort(b2.shortValue());
                    } else if (b2 instanceof Integer) {
                        buffer.putInt(b2.intValue());
                    } else if (b2 instanceof String) {
                        try {
                            buffer.put(b2.getBytes("US-ASCII"));
                            buffer.put((byte) 0);
                        } catch (UnsupportedEncodingException e) {
                            throw new IllegalArgumentException("Non-ASCII string encoding is not supported");
                        }
                    } else {
                        throw new IllegalArgumentException("Parameters should be one of the class types: Byte, Short, Integer, String");
                    }
                }
                return buffer.array();
            }
        }
    }

    public static byte[] encodeDirectCommand(byte opcode, boolean needReply, int globalAllocation, int localAllocation, String paramFormat, Object... parameters) {
        byte b;
        if (globalAllocation < 0 || globalAllocation > 1023 || localAllocation < 0 || localAllocation > 63 || paramFormat.length() != parameters.length) {
            throw new IllegalArgumentException();
        }
        ArrayList<byte[]> payloads = new ArrayList<>();
        for (int i = 0; i < paramFormat.length(); i++) {
            char letter = paramFormat.charAt(i);
            String str = parameters[i];
            switch (letter) {
                case 'c':
                    if (str instanceof Byte) {
                        if (str.byteValue() <= 31 && str.byteValue() >= -31) {
                            payloads.add(encodeLC0(str.byteValue()));
                            break;
                        } else {
                            payloads.add(encodeLC1(str.byteValue()));
                            break;
                        }
                    } else if (str instanceof Short) {
                        payloads.add(encodeLC2(str.shortValue()));
                        break;
                    } else if (str instanceof Integer) {
                        payloads.add(encodeLC4(str.intValue()));
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                case 'g':
                    if (str instanceof Byte) {
                        if (str.byteValue() <= 31 && str.byteValue() >= -31) {
                            payloads.add(encodeGV0(str.byteValue()));
                            break;
                        } else {
                            payloads.add(encodeGV1(str.byteValue()));
                            break;
                        }
                    } else if (str instanceof Short) {
                        payloads.add(encodeGV2(str.shortValue()));
                        break;
                    } else if (str instanceof Integer) {
                        payloads.add(encodeGV4(str.intValue()));
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                case 'l':
                    if (str instanceof Byte) {
                        if (str.byteValue() <= 31 && str.byteValue() >= -31) {
                            payloads.add(encodeLV0(str.byteValue()));
                            break;
                        } else {
                            payloads.add(encodeLV1(str.byteValue()));
                            break;
                        }
                    } else if (str instanceof Short) {
                        payloads.add(encodeLV2(str.shortValue()));
                        break;
                    } else if (str instanceof Integer) {
                        payloads.add(encodeLV4(str.intValue()));
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                case 's':
                    if (str instanceof String) {
                        try {
                            payloads.add((str + "\u0000").getBytes("US-ASCII"));
                            break;
                        } catch (UnsupportedEncodingException e) {
                            throw new IllegalArgumentException();
                        }
                    } else {
                        throw new IllegalArgumentException();
                    }
                default:
                    throw new IllegalArgumentException("Illegal format string");
            }
        }
        int bufferCapacity = 4;
        Iterator<byte[]> it = payloads.iterator();
        while (it.hasNext()) {
            bufferCapacity += it.next().length;
        }
        ByteBuffer buffer = ByteBuffer.allocate(bufferCapacity);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        if (needReply) {
            b = 0;
        } else {
            b = Byte.MIN_VALUE;
        }
        buffer.put(b);
        buffer.put(new byte[]{(byte) (globalAllocation & 255), (byte) (((globalAllocation >>> 8) & 3) | (localAllocation << 2))});
        buffer.put(opcode);
        Iterator<byte[]> it2 = payloads.iterator();
        while (it2.hasNext()) {
            buffer.put(it2.next());
        }
        return buffer.array();
    }
}
