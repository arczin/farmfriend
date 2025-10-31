package gnu.xquery.util;

import gnu.bytecode.Access;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.kawa.xml.XIntegerType;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Path;
import gnu.text.URIPath;
import gnu.xml.TextUtils;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static String ERROR_VALUE = "<error>";

    static String coerceToString(Object arg, String functionName, int iarg, String onEmpty) {
        if (arg instanceof KNode) {
            arg = KNode.atomicValue(arg);
        }
        if ((arg == Values.empty || arg == null) && onEmpty != ERROR_VALUE) {
            return onEmpty;
        }
        if ((arg instanceof UntypedAtomic) || (arg instanceof CharSequence) || (arg instanceof URI) || (arg instanceof Path)) {
            return arg.toString();
        }
        throw new WrongType(functionName, iarg, arg, onEmpty == ERROR_VALUE ? "xs:string" : "xs:string?");
    }

    public static Object lowerCase(Object node) {
        return coerceToString(node, "lower-case", 1, "").toLowerCase();
    }

    public static Object upperCase(Object node) {
        return coerceToString(node, "upper-case", 1, "").toUpperCase();
    }

    static double asDouble(Object value) {
        if (!(value instanceof Number)) {
            value = NumberValue.numberValue(value);
        }
        return ((Number) value).doubleValue();
    }

    public static Object substring(Object str, Object start) {
        double d1 = asDouble(start);
        if (Double.isNaN(d1)) {
            return "";
        }
        int i = (int) (d1 - 0.5d);
        if (i < 0) {
            i = 0;
        }
        String s = coerceToString(str, "substring", 1, "");
        int len = s.length();
        int offset = 0;
        while (true) {
            i--;
            if (i < 0) {
                return s.substring(offset);
            }
            if (offset >= len) {
                return "";
            }
            int offset2 = offset + 1;
            int offset3 = s.charAt(offset);
            if (offset3 < 55296 || offset3 >= 56320 || offset2 >= len) {
                offset = offset2;
            } else {
                offset = offset2 + 1;
            }
        }
    }

    public static Object substring(Object str, Object start, Object length) {
        int offset;
        String s = coerceToString(str, "substring", 1, "");
        int len = s.length();
        double d1 = Math.floor(asDouble(start) - 0.5d);
        double d2 = Math.floor(asDouble(length) + 0.5d) + d1;
        if (d1 <= 0.0d) {
            d1 = 0.0d;
        }
        if (d2 > ((double) len)) {
            d2 = (double) len;
        }
        if (d2 <= d1) {
            return "";
        }
        int i1 = (int) d1;
        int i2 = ((int) d2) - i1;
        int offset2 = 0;
        while (true) {
            i1--;
            if (i1 < 0) {
                int i12 = offset2;
                while (true) {
                    i2--;
                    if (i2 < 0) {
                        return s.substring(i12, offset2);
                    }
                    if (offset2 >= len) {
                        return "";
                    }
                    int offset3 = offset2 + 1;
                    int offset4 = s.charAt(offset2);
                    if (offset4 < 55296 || offset4 >= 56320 || offset3 >= len) {
                        offset = offset3;
                    } else {
                        offset = offset3 + 1;
                    }
                }
            } else if (offset2 >= len) {
                return "";
            } else {
                int offset5 = offset2 + 1;
                int offset6 = s.charAt(offset2);
                if (offset6 < 55296 || offset6 >= 56320 || offset5 >= len) {
                    offset2 = offset5;
                } else {
                    offset2 = offset5 + 1;
                }
            }
        }
    }

    public static Object stringLength(Object str) {
        String s = coerceToString(str, "string-length", 1, "");
        int slen = s.length();
        int len = 0;
        int i = 0;
        while (i < slen) {
            int i2 = i + 1;
            char ch = s.charAt(i);
            if (ch >= 55296 && ch < 56320 && i2 < slen) {
                i2++;
            }
            len++;
            i = i2;
        }
        return IntNum.make(len);
    }

    public static Object substringBefore(Object str, Object find) {
        int start;
        String s = coerceToString(str, "substring-before", 1, "");
        String f = coerceToString(find, "substring-before", 2, "");
        if (f.length() != 0 && (start = s.indexOf(f)) >= 0) {
            return s.substring(0, start);
        }
        return "";
    }

    public static Object substringAfter(Object str, Object find) {
        String s = coerceToString(str, "substring-after", 1, "");
        String f = coerceToString(find, "substring-after", 2, "");
        int flen = f.length();
        if (flen == 0) {
            return s;
        }
        int start = s.indexOf(f);
        if (start >= 0) {
            return s.substring(start + flen);
        }
        return "";
    }

    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00f7, code lost:
        r8.append(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00fa, code lost:
        if (r12 == 0) goto L_0x0100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00fc, code lost:
        r8.append(r12);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object translate(java.lang.Object r22, java.lang.Object r23, java.lang.Object r24) {
        /*
            r0 = r22
            r1 = 1
            java.lang.String r2 = ""
            java.lang.String r3 = "translate"
            java.lang.String r1 = coerceToString(r0, r3, r1, r2)
            java.lang.Object r2 = gnu.kawa.xml.KNode.atomicValue(r23)
            boolean r4 = r2 instanceof gnu.kawa.xml.UntypedAtomic
            java.lang.String r5 = "xs:string"
            if (r4 != 0) goto L_0x0021
            boolean r4 = r2 instanceof java.lang.String
            if (r4 == 0) goto L_0x001a
            goto L_0x0021
        L_0x001a:
            gnu.mapping.WrongType r4 = new gnu.mapping.WrongType
            r6 = 2
            r4.<init>((java.lang.String) r3, (int) r6, (java.lang.Object) r0, (java.lang.String) r5)
            throw r4
        L_0x0021:
            java.lang.String r4 = r2.toString()
            int r6 = r4.length()
            java.lang.Object r7 = gnu.kawa.xml.KNode.atomicValue(r24)
            boolean r8 = r7 instanceof gnu.kawa.xml.UntypedAtomic
            if (r8 != 0) goto L_0x003d
            boolean r8 = r7 instanceof java.lang.String
            if (r8 == 0) goto L_0x0036
            goto L_0x003d
        L_0x0036:
            gnu.mapping.WrongType r8 = new gnu.mapping.WrongType
            r9 = 3
            r8.<init>((java.lang.String) r3, (int) r9, (java.lang.Object) r0, (java.lang.String) r5)
            throw r8
        L_0x003d:
            java.lang.String r3 = r7.toString()
            if (r6 != 0) goto L_0x0044
            return r1
        L_0x0044:
            int r5 = r1.length()
            java.lang.StringBuffer r8 = new java.lang.StringBuffer
            r8.<init>(r5)
            int r9 = r3.length()
            r10 = 0
        L_0x0052:
            if (r10 >= r5) goto L_0x0109
            int r11 = r10 + 1
            char r10 = r1.charAt(r10)
            r12 = 0
            r13 = 56320(0xdc00, float:7.8921E-41)
            r14 = 55296(0xd800, float:7.7486E-41)
            if (r10 < r14) goto L_0x006e
            if (r10 >= r13) goto L_0x006e
            if (r11 >= r5) goto L_0x006e
            int r15 = r11 + 1
            char r12 = r1.charAt(r11)
            goto L_0x006f
        L_0x006e:
            r15 = r11
        L_0x006f:
            r11 = 0
            r16 = 0
            r13 = r16
        L_0x0074:
            if (r13 >= r6) goto L_0x00f3
            int r14 = r13 + 1
            char r13 = r4.charAt(r13)
            r16 = 0
            r0 = 55296(0xd800, float:7.7486E-41)
            if (r13 < r0) goto L_0x0094
            r0 = 56320(0xdc00, float:7.8921E-41)
            if (r13 >= r0) goto L_0x0094
            if (r14 >= r6) goto L_0x0094
            int r0 = r14 + 1
            char r16 = r4.charAt(r14)
            r14 = r0
            r0 = r16
            goto L_0x0096
        L_0x0094:
            r0 = r16
        L_0x0096:
            if (r13 != r10) goto L_0x00dc
            if (r0 != r12) goto L_0x00dc
            r16 = 0
            r21 = r16
            r16 = r0
            r0 = r21
        L_0x00a2:
            if (r0 < r9) goto L_0x00a9
            r17 = r1
            r19 = r2
            goto L_0x0100
        L_0x00a9:
            r17 = r1
            int r1 = r0 + 1
            char r0 = r3.charAt(r0)
            r18 = 0
            r19 = r2
            r2 = 55296(0xd800, float:7.7486E-41)
            if (r0 < r2) goto L_0x00ca
            r2 = 56320(0xdc00, float:7.8921E-41)
            if (r0 >= r2) goto L_0x00cd
            if (r1 >= r9) goto L_0x00cd
            int r20 = r1 + 1
            char r18 = r3.charAt(r1)
            r1 = r20
            goto L_0x00cd
        L_0x00ca:
            r2 = 56320(0xdc00, float:7.8921E-41)
        L_0x00cd:
            if (r11 != 0) goto L_0x00d4
            r10 = r0
            r12 = r18
            goto L_0x00f7
        L_0x00d4:
            int r11 = r11 + -1
            r0 = r1
            r1 = r17
            r2 = r19
            goto L_0x00a2
        L_0x00dc:
            r16 = r0
            r17 = r1
            r19 = r2
            r2 = 56320(0xdc00, float:7.8921E-41)
            int r11 = r11 + 1
            r0 = r22
            r13 = r14
            r1 = r17
            r2 = r19
            r14 = 55296(0xd800, float:7.7486E-41)
            goto L_0x0074
        L_0x00f3:
            r17 = r1
            r19 = r2
        L_0x00f7:
            r8.append(r10)
            if (r12 == 0) goto L_0x00ff
            r8.append(r12)
        L_0x00ff:
        L_0x0100:
            r0 = r22
            r10 = r15
            r1 = r17
            r2 = r19
            goto L_0x0052
        L_0x0109:
            java.lang.String r0 = r8.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.util.StringUtils.translate(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public static Object stringPad(Object str, Object padcount) {
        int count = ((Number) NumberValue.numberValue(padcount)).intValue();
        if (count > 0) {
            String sv = coerceToString(str, "string-pad", 1, "");
            StringBuffer s = new StringBuffer(count * sv.length());
            for (int i = 0; i < count; i++) {
                s.append(sv);
            }
            return s.toString();
        } else if (count == 0) {
            return "";
        } else {
            throw new IndexOutOfBoundsException("Invalid string-pad count");
        }
    }

    public static Object contains(Object str, Object contain) {
        return coerceToString(str, "contains", 1, "").indexOf(coerceToString(contain, "contains", 2, "")) < 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    public static Object startsWith(Object str, Object with) {
        return coerceToString(str, "starts-with", 1, "").startsWith(coerceToString(with, "starts-with", 2, "")) ? Boolean.TRUE : Boolean.FALSE;
    }

    public static Object endsWith(Object str, Object with) {
        return coerceToString(str, "ends-with", 1, "").endsWith(coerceToString(with, "ends-with", 2, "")) ? Boolean.TRUE : Boolean.FALSE;
    }

    public static Object stringJoin(Object strseq, Object join) {
        StringBuffer s = new StringBuffer();
        String glue = coerceToString(join, "string-join", 2, ERROR_VALUE);
        int glen = glue.length();
        int index = 0;
        boolean started = false;
        while (true) {
            int nextIndex = Values.nextIndex(strseq, index);
            int next = nextIndex;
            if (nextIndex < 0) {
                return s.toString();
            }
            Object obj = Values.nextValue(strseq, index);
            if (obj != Values.empty) {
                if (started && glen > 0) {
                    s.append(glue);
                }
                s.append(TextUtils.stringValue(obj));
                started = true;
                index = next;
            }
        }
    }

    public static String concat$V(Object arg1, Object arg2, Object[] args) {
        String str1 = TextUtils.stringValue(SequenceUtils.coerceToZeroOrOne(arg1, "concat", 1));
        String str2 = TextUtils.stringValue(SequenceUtils.coerceToZeroOrOne(arg2, "concat", 2));
        StringBuilder result = new StringBuilder(str1);
        result.append(str2);
        int count = args.length;
        for (int i = 0; i < count; i++) {
            result.append(TextUtils.stringValue(SequenceUtils.coerceToZeroOrOne(args[i], "concat", i + 2)));
        }
        return result.toString();
    }

    public static Object compare(Object val1, Object val2, NamedCollator coll) {
        if (val1 == Values.empty || val1 == null || val2 == Values.empty || val2 == null) {
            return Values.empty;
        }
        if (coll == null) {
            coll = NamedCollator.codepointCollation;
        }
        int ret = coll.compare(val1.toString(), val2.toString());
        if (ret < 0) {
            return IntNum.minusOne();
        }
        return ret > 0 ? IntNum.one() : IntNum.zero();
    }

    public static void stringToCodepoints$X(Object arg, CallContext ctx) {
        String str = coerceToString(arg, "string-to-codepoints", 1, "");
        int len = str.length();
        Consumer out = ctx.consumer;
        int ch = 0;
        while (ch < len) {
            int i = ch + 1;
            int ch2 = str.charAt(ch);
            if (ch2 >= 55296 && ch2 < 56320 && i < len) {
                ch2 = ((ch2 - 55296) * 1024) + (str.charAt(i) - 56320) + 65536;
                i++;
            }
            out.writeInt(ch2);
            ch = i;
        }
    }

    private static void appendCodepoint(Object code, StringBuffer sbuf) {
        int i = ((IntNum) XIntegerType.integerType.cast(code)).intValue();
        if (i <= 0 || (i > 55295 && (i < 57344 || ((i > 65533 && i < 65536) || i > 1114111)))) {
            throw new IllegalArgumentException("codepoints-to-string: " + i + " is not a valid XML character [FOCH0001]");
        }
        if (i >= 65536) {
            sbuf.append((char) (((i - 65536) >> 10) + 55296));
            i = (i & 1023) + 56320;
        }
        sbuf.append((char) i);
    }

    public static String codepointsToString(Object arg) {
        if (arg == null) {
            return "";
        }
        StringBuffer sbuf = new StringBuffer();
        if (arg instanceof Values) {
            Values vals = (Values) arg;
            int ipos = vals.startPos();
            while (true) {
                int nextPos = vals.nextPos(ipos);
                ipos = nextPos;
                if (nextPos == 0) {
                    break;
                }
                appendCodepoint(vals.getPosPrevious(ipos), sbuf);
            }
        } else {
            appendCodepoint(arg, sbuf);
        }
        return sbuf.toString();
    }

    public static String encodeForUri(Object arg) {
        return URIPath.encodeForUri(coerceToString(arg, "encode-for-uri", 1, ""), 'U');
    }

    public static String iriToUri(Object arg) {
        return URIPath.encodeForUri(coerceToString(arg, "iri-to-uru", 1, ""), Access.INNERCLASS_CONTEXT);
    }

    public static String escapeHtmlUri(Object arg) {
        return URIPath.encodeForUri(coerceToString(arg, "escape-html-uri", 1, ""), 'H');
    }

    public static String normalizeSpace(Object arg) {
        String str = coerceToString(arg, "normalize-space", 1, "");
        int len = str.length();
        StringBuffer sbuf = null;
        int skipped = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (Character.isWhitespace(ch)) {
                if (sbuf == null && skipped == 0 && i > 0) {
                    sbuf = new StringBuffer(str.substring(0, i));
                }
                skipped++;
            } else {
                if (skipped > 0) {
                    if (sbuf != null) {
                        sbuf.append(' ');
                    } else if (skipped > 1 || i == 1 || str.charAt(i - 1) != ' ') {
                        sbuf = new StringBuffer();
                    }
                    skipped = 0;
                }
                if (sbuf != null) {
                    sbuf.append(ch);
                }
            }
        }
        if (sbuf != null) {
            return sbuf.toString();
        }
        if (skipped > 0) {
            return "";
        }
        return str;
    }

    public static Pattern makePattern(String str, String str2) {
        int length = str2.length();
        int i = 0;
        int i2 = 0;
        while (true) {
            length--;
            if (length >= 0) {
                switch (str2.charAt(length)) {
                    case 'i':
                        i2 |= 66;
                        break;
                    case 'm':
                        i2 |= 8;
                        break;
                    case 's':
                        i2 |= 32;
                        break;
                    case 'x':
                        StringBuffer stringBuffer = new StringBuffer();
                        int length2 = str.length();
                        int i3 = 0;
                        int i4 = 0;
                        while (i3 < length2) {
                            int i5 = i3 + 1;
                            char charAt = str.charAt(i3);
                            if (charAt == '\\' && i5 < length2) {
                                stringBuffer.append(charAt);
                                char charAt2 = str.charAt(i5);
                                i5++;
                                charAt = charAt2;
                            } else if (charAt == '[') {
                                i4++;
                            } else if (charAt == ']') {
                                i4--;
                            } else if (i4 == 0 && Character.isWhitespace(charAt)) {
                                i3 = i5;
                            }
                            stringBuffer.append(charAt);
                            i3 = i5;
                        }
                        str = stringBuffer.toString();
                        break;
                    default:
                        throw new IllegalArgumentException("unknown 'replace' flag");
                }
            } else {
                if (str.indexOf("{Is") >= 0) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    int length3 = str.length();
                    while (i < length3) {
                        int i6 = i + 1;
                        char charAt3 = str.charAt(i);
                        if (charAt3 != '\\' || i6 + 4 >= length3) {
                            stringBuffer2.append(charAt3);
                            i = i6;
                        } else {
                            stringBuffer2.append(charAt3);
                            i = i6 + 1;
                            char charAt4 = str.charAt(i6);
                            stringBuffer2.append(charAt4);
                            if ((charAt4 == 'p' || charAt4 == 'P') && str.charAt(i) == '{' && str.charAt(i + 1) == 'I' && str.charAt(i + 2) == 's') {
                                stringBuffer2.append('{');
                                stringBuffer2.append(Access.INNERCLASS_CONTEXT);
                                stringBuffer2.append('n');
                                i += 3;
                            }
                        }
                    }
                    str = stringBuffer2.toString();
                }
                return Pattern.compile(str, i2);
            }
        }
    }

    public static boolean matches(Object input, String pattern) {
        return matches(input, pattern, "");
    }

    public static boolean matches(Object arg, String pattern, String flags) {
        return makePattern(pattern, flags).matcher(coerceToString(arg, "matches", 1, "")).find();
    }

    public static String replace(Object input, String pattern, String replacement) {
        return replace(input, pattern, replacement, "");
    }

    public static String replace(Object arg, String pattern, String replacement, String flags) {
        String str = coerceToString(arg, "replace", 1, "");
        int rlen = replacement.length();
        int i = 0;
        while (i < rlen) {
            int i2 = i + 1;
            char rch = replacement.charAt(i);
            if (rch == '\\') {
                if (i2 < rch) {
                    int i3 = i2 + 1;
                    char i4 = replacement.charAt(i2);
                    char rch2 = i4;
                    if (i4 == '\\' || rch2 == '$') {
                        i = i3;
                    } else {
                        int i5 = i3;
                    }
                }
                throw new IllegalArgumentException("invalid replacement string [FORX0004]");
            }
            i = i2;
        }
        return makePattern(pattern, flags).matcher(str).replaceAll(replacement);
    }

    public static void tokenize$X(Object arg, String pattern, CallContext ctx) {
        tokenize$X(arg, pattern, "", ctx);
    }

    public static void tokenize$X(Object arg, String pattern, String flags, CallContext ctx) {
        String str = coerceToString(arg, "tokenize", 1, "");
        Consumer out = ctx.consumer;
        Matcher matcher = makePattern(pattern, flags).matcher(str);
        if (str.length() != 0) {
            int start = 0;
            while (matcher.find()) {
                int end = matcher.start();
                out.writeObject(str.substring(start, end));
                start = matcher.end();
                if (start == end) {
                    throw new IllegalArgumentException("pattern matches empty string");
                }
            }
            out.writeObject(str.substring(start));
        }
    }

    public static Object codepointEqual(Object arg1, Object arg2) {
        String str1 = coerceToString(arg1, "codepoint-equal", 1, (String) null);
        String str2 = coerceToString(arg2, "codepoint-equal", 2, (String) null);
        if (str1 == null || str2 == null) {
            return Values.empty;
        }
        return str1.equals(str2) ? Boolean.TRUE : Boolean.FALSE;
    }

    public static Object normalizeUnicode(Object arg) {
        return normalizeUnicode(arg, "NFC");
    }

    public static Object normalizeUnicode(Object arg, String form) {
        String str = coerceToString(arg, "normalize-unicode", 1, "");
        if ("".equals(form.trim().toUpperCase())) {
            return str;
        }
        throw new UnsupportedOperationException("normalize-unicode: unicode string normalization not available");
    }
}
