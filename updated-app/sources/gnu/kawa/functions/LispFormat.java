package gnu.kawa.functions;

import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.text.CompoundFormat;
import gnu.text.ReportFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.Vector;

public class LispFormat extends CompoundFormat {
    public static final String paramFromCount = "<from count>";
    public static final String paramFromList = "<from list>";
    public static final String paramUnspecified = "<unspecified>";

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x024f, code lost:
        throw new java.text.ParseException("saw ~] without matching ~[", r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:211:0x04b0, code lost:
        throw new java.text.ParseException("saw ~; without matching ~[ or ~<", r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:218:0x04f0, code lost:
        throw new java.text.ParseException("saw ~) without matching ~(", r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:0x0517, code lost:
        r9 = r8;
        r8 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:228:0x051a, code lost:
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:246:0x05a2, code lost:
        r6.setSize(r8);
        r6.push(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:258:0x05cf, code lost:
        r2 = r12;
        r8 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0184, code lost:
        throw new java.text.ParseException("saw ~} without matching ~{", r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0185, code lost:
        r2 = getParam(r6, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0189, code lost:
        if (r2 != -1073741824) goto L_0x018c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x018b, code lost:
        r2 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x018c, code lost:
        r3 = getParam(r6, r8 + 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0192, code lost:
        if (r3 != -1073741824) goto L_0x019e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0196, code lost:
        if (r5 != '|') goto L_0x019b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0198, code lost:
        r11 = 12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x019b, code lost:
        r11 = 126;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x019d, code lost:
        r3 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x019e, code lost:
        r2 = gnu.kawa.functions.LispCharacterFormat.getInstance(r3, r2, false, false);
        r12 = null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public LispFormat(char[] r29, int r30, int r31) throws java.text.ParseException {
        /*
            r28 = this;
            r0 = r28
            r1 = r29
            r2 = 0
            r3 = 0
            r0.<init>(r2, r3)
            java.lang.StringBuffer r4 = new java.lang.StringBuffer
            r5 = 100
            r4.<init>(r5)
            java.util.Stack r6 = new java.util.Stack
            r6.<init>()
            int r7 = r30 + r31
            r8 = -1
            r8 = r30
            r9 = -1
            r10 = 0
        L_0x001e:
            r11 = 126(0x7e, float:1.77E-43)
            if (r8 >= r7) goto L_0x0026
            char r12 = r1[r8]
            if (r12 != r11) goto L_0x0037
        L_0x0026:
            int r12 = r4.length()
            if (r12 <= 0) goto L_0x0037
            gnu.text.LiteralFormat r12 = new gnu.text.LiteralFormat
            r12.<init>((java.lang.StringBuffer) r4)
            r6.push(r12)
            r4.setLength(r3)
        L_0x0037:
            if (r8 < r7) goto L_0x005e
            if (r8 > r7) goto L_0x0058
            if (r9 >= 0) goto L_0x0050
            int r1 = r6.size()
            r0.length = r1
            int r1 = r0.length
            java.text.Format[] r1 = new java.text.Format[r1]
            r0.formats = r1
            java.text.Format[] r1 = r0.formats
            r6.copyInto(r1)
            return
        L_0x0050:
            java.text.ParseException r1 = new java.text.ParseException
            java.lang.String r2 = "missing ~] or ~}"
            r1.<init>(r2, r8)
            throw r1
        L_0x0058:
            java.lang.IndexOutOfBoundsException r1 = new java.lang.IndexOutOfBoundsException
            r1.<init>()
            throw r1
        L_0x005e:
            int r12 = r8 + 1
            char r8 = r1[r8]
            if (r8 == r11) goto L_0x0069
            r4.append(r8)
            r8 = r12
            goto L_0x001e
        L_0x0069:
            int r8 = r6.size()
            int r13 = r12 + 1
            char r12 = r1[r12]
        L_0x0071:
            r14 = 35
            r15 = 8
            r5 = 44
            r11 = 10
            if (r12 != r14) goto L_0x008b
            java.lang.String r12 = "<from count>"
            r6.push(r12)
            int r12 = r13 + 1
            char r13 = r1[r13]
            r27 = r13
            r13 = r12
            r12 = r27
            goto L_0x0106
        L_0x008b:
            r14 = 118(0x76, float:1.65E-43)
            if (r12 == r14) goto L_0x00fb
            r14 = 86
            if (r12 != r14) goto L_0x0095
            goto L_0x00fb
        L_0x0095:
            r14 = 45
            if (r12 == r14) goto L_0x00bc
            int r16 = java.lang.Character.digit(r12, r11)
            if (r16 < 0) goto L_0x00a0
            goto L_0x00bc
        L_0x00a0:
            r14 = 39
            if (r12 != r14) goto L_0x00b4
            int r12 = r13 + 1
            char r13 = r1[r13]
            gnu.text.Char r13 = gnu.text.Char.make(r13)
            r6.push(r13)
            int r13 = r12 + 1
            char r12 = r1[r12]
            goto L_0x0106
        L_0x00b4:
            if (r12 != r5) goto L_0x0109
            java.lang.String r14 = "<unspecified>"
            r6.push(r14)
            goto L_0x0106
        L_0x00bc:
            if (r12 != r14) goto L_0x00c0
            r14 = 1
            goto L_0x00c1
        L_0x00c0:
            r14 = 0
        L_0x00c1:
            if (r14 == 0) goto L_0x00c8
            int r12 = r13 + 1
            char r13 = r1[r13]
            goto L_0x00cd
        L_0x00c8:
            r27 = r13
            r13 = r12
            r12 = r27
        L_0x00cd:
            r16 = r12
            r2 = 0
        L_0x00d1:
            int r17 = java.lang.Character.digit(r13, r11)
            if (r17 >= 0) goto L_0x00ef
            int r3 = r16 - r12
            if (r3 >= r15) goto L_0x00e4
            if (r14 == 0) goto L_0x00df
            int r2 = -r2
        L_0x00df:
            gnu.math.IntNum r2 = gnu.math.IntNum.make((int) r2)
            goto L_0x00e8
        L_0x00e4:
            gnu.math.IntNum r2 = gnu.math.IntNum.valueOf(r1, r12, r3, r11, r14)
        L_0x00e8:
            r6.push(r2)
            r12 = r13
            r13 = r16
            goto L_0x0106
        L_0x00ef:
            int r2 = r2 * 10
            int r2 = r2 + r17
            int r3 = r16 + 1
            char r13 = r1[r16]
            r16 = r3
            r3 = 0
            goto L_0x00d1
        L_0x00fb:
            java.lang.String r2 = "<from list>"
            r6.push(r2)
            int r2 = r13 + 1
            char r3 = r1[r13]
            r13 = r2
            r12 = r3
        L_0x0106:
            if (r12 == r5) goto L_0x05d3
        L_0x0109:
            r2 = 0
            r3 = 0
        L_0x010c:
            r5 = 58
            if (r12 != r5) goto L_0x0112
            r3 = 1
            goto L_0x0117
        L_0x0112:
            r5 = 64
            if (r12 != r5) goto L_0x011d
            r2 = 1
        L_0x0117:
            int r5 = r13 + 1
            char r12 = r1[r13]
            r13 = r5
            goto L_0x010c
        L_0x011d:
            char r5 = java.lang.Character.toUpperCase(r12)
            int r12 = r6.size()
            int r12 = r12 - r8
            r14 = -1610612736(0xffffffffa0000000, float:-1.0842022E-19)
            r11 = -1073741824(0xffffffffc0000000, float:-2.0)
            r15 = 2
            switch(r5) {
                case 10: goto L_0x05a9;
                case 33: goto L_0x059b;
                case 36: goto L_0x053f;
                case 37: goto L_0x052d;
                case 38: goto L_0x051f;
                case 40: goto L_0x04f1;
                case 41: goto L_0x04bf;
                case 42: goto L_0x04b1;
                case 59: goto L_0x045b;
                case 60: goto L_0x0424;
                case 62: goto L_0x0374;
                case 63: goto L_0x0362;
                case 65: goto L_0x0323;
                case 66: goto L_0x02cb;
                case 67: goto L_0x02bc;
                case 68: goto L_0x02cb;
                case 69: goto L_0x053f;
                case 70: goto L_0x053f;
                case 71: goto L_0x053f;
                case 73: goto L_0x02ad;
                case 79: goto L_0x02cb;
                case 80: goto L_0x02a5;
                case 82: goto L_0x02cb;
                case 83: goto L_0x0323;
                case 84: goto L_0x028b;
                case 87: goto L_0x0323;
                case 88: goto L_0x02cb;
                case 89: goto L_0x0323;
                case 91: goto L_0x0250;
                case 93: goto L_0x0206;
                case 94: goto L_0x01ec;
                case 95: goto L_0x01c6;
                case 123: goto L_0x01a7;
                case 124: goto L_0x0185;
                case 125: goto L_0x0150;
                case 126: goto L_0x0147;
                default: goto L_0x012e;
            }
        L_0x012e:
            java.text.ParseException r1 = new java.text.ParseException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "unrecognized format specifier ~"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2, r13)
            throw r1
        L_0x0147:
            if (r12 != 0) goto L_0x0185
            r4.append(r5)
            r12 = 0
            r14 = 0
            goto L_0x05cf
        L_0x0150:
            if (r9 < 0) goto L_0x017d
            java.lang.Object r2 = r6.elementAt(r9)
            boolean r2 = r2 instanceof gnu.kawa.functions.LispIterationFormat
            if (r2 == 0) goto L_0x017d
            java.lang.Object r2 = r6.elementAt(r9)
            gnu.kawa.functions.LispIterationFormat r2 = (gnu.kawa.functions.LispIterationFormat) r2
            r2.atLeastOnce = r3
            int r9 = r9 + 2
            if (r8 <= r9) goto L_0x016c
            java.text.Format r3 = popFormats(r6, r9, r8)
            r2.body = r3
        L_0x016c:
            java.lang.Object r2 = r6.pop()
            gnu.math.IntNum r2 = (gnu.math.IntNum) r2
            int r9 = r2.intValue()
            r8 = r13
            r2 = 0
            r3 = 0
            r5 = 100
            goto L_0x001e
        L_0x017d:
            java.text.ParseException r1 = new java.text.ParseException
            java.lang.String r2 = "saw ~} without matching ~{"
            r1.<init>(r2, r13)
            throw r1
        L_0x0185:
            int r2 = getParam(r6, r8)
            if (r2 != r11) goto L_0x018c
            r2 = 1
        L_0x018c:
            int r3 = r8 + 1
            int r3 = getParam(r6, r3)
            if (r3 != r11) goto L_0x019e
            r3 = 124(0x7c, float:1.74E-43)
            if (r5 != r3) goto L_0x019b
            r11 = 12
            goto L_0x019d
        L_0x019b:
            r11 = 126(0x7e, float:1.77E-43)
        L_0x019d:
            r3 = r11
        L_0x019e:
            r5 = 0
            gnu.kawa.functions.LispCharacterFormat r2 = gnu.kawa.functions.LispCharacterFormat.getInstance(r3, r2, r5, r5)
            r12 = 0
            r14 = 0
            goto L_0x05a2
        L_0x01a7:
            gnu.kawa.functions.LispIterationFormat r5 = new gnu.kawa.functions.LispIterationFormat
            r5.<init>()
            r5.seenAt = r2
            r5.seenColon = r3
            int r2 = getParam(r6, r8)
            r5.maxIterations = r2
            r6.setSize(r8)
            r6.push(r5)
            gnu.math.IntNum r2 = gnu.math.IntNum.make((int) r9)
            r6.push(r2)
            goto L_0x0517
        L_0x01c6:
            int r5 = getParam(r6, r8)
            if (r5 != r11) goto L_0x01cd
            r5 = 1
        L_0x01cd:
            if (r3 == 0) goto L_0x01d1
            if (r2 == 0) goto L_0x01d1
        L_0x01d1:
            if (r2 == 0) goto L_0x01d8
            if (r3 == 0) goto L_0x01d8
            r15 = 82
            goto L_0x01e4
        L_0x01d8:
            if (r2 == 0) goto L_0x01dd
            r15 = 77
            goto L_0x01e4
        L_0x01dd:
            if (r3 == 0) goto L_0x01e2
            r15 = 70
            goto L_0x01e4
        L_0x01e2:
            r15 = 78
        L_0x01e4:
            gnu.kawa.functions.LispNewlineFormat r2 = gnu.kawa.functions.LispNewlineFormat.getInstance(r5, r15)
            r12 = 0
            r14 = 0
            goto L_0x05a2
        L_0x01ec:
            int r2 = getParam(r6, r8)
            int r3 = r8 + 1
            int r3 = getParam(r6, r3)
            int r5 = r8 + 2
            int r5 = getParam(r6, r5)
            gnu.kawa.functions.LispEscapeFormat r11 = new gnu.kawa.functions.LispEscapeFormat
            r11.<init>(r2, r3, r5)
            r2 = r11
            r12 = 0
            r14 = 0
            goto L_0x05a2
        L_0x0206:
            if (r9 < 0) goto L_0x0248
            java.lang.Object r2 = r6.elementAt(r9)
            boolean r2 = r2 instanceof gnu.kawa.functions.LispChoiceFormat
            if (r2 == 0) goto L_0x0248
            int r2 = r9 + 3
            int r10 = r10 + r2
            java.text.Format r3 = popFormats(r6, r10, r8)
            r6.push(r3)
            java.lang.Object r3 = r6.elementAt(r9)
            gnu.kawa.functions.LispChoiceFormat r3 = (gnu.kawa.functions.LispChoiceFormat) r3
            int r5 = r6.size()
            java.text.Format[] r5 = getFormats(r6, r2, r5)
            r3.choices = r5
            r6.setSize(r2)
            java.lang.Object r2 = r6.pop()
            gnu.math.IntNum r2 = (gnu.math.IntNum) r2
            int r10 = r2.intValue()
            java.lang.Object r2 = r6.pop()
            gnu.math.IntNum r2 = (gnu.math.IntNum) r2
            int r9 = r2.intValue()
            r8 = r13
            r2 = 0
            r3 = 0
            r5 = 100
            goto L_0x001e
        L_0x0248:
            java.text.ParseException r1 = new java.text.ParseException
            java.lang.String r2 = "saw ~] without matching ~["
            r1.<init>(r2, r13)
            throw r1
        L_0x0250:
            gnu.kawa.functions.LispChoiceFormat r5 = new gnu.kawa.functions.LispChoiceFormat
            r5.<init>()
            int r12 = getParam(r6, r8)
            r5.param = r12
            int r12 = r5.param
            if (r12 != r11) goto L_0x0261
            r5.param = r14
        L_0x0261:
            if (r3 == 0) goto L_0x0267
            r3 = 1
            r5.testBoolean = r3
            goto L_0x0268
        L_0x0267:
            r3 = 1
        L_0x0268:
            if (r2 == 0) goto L_0x026c
            r5.skipIfFalse = r3
        L_0x026c:
            r6.setSize(r8)
            r6.push(r5)
            gnu.math.IntNum r2 = gnu.math.IntNum.make((int) r9)
            r6.push(r2)
            gnu.math.IntNum r2 = gnu.math.IntNum.make((int) r10)
            r6.push(r2)
            r9 = r8
            r8 = r13
            r2 = 0
            r3 = 0
            r5 = 100
            r10 = 0
            goto L_0x001e
        L_0x028b:
            int r3 = getParam(r6, r8)
            int r5 = r8 + 1
            int r5 = getParam(r6, r5)
            int r11 = r8 + 2
            int r11 = getParam(r6, r11)
            gnu.kawa.functions.LispTabulateFormat r12 = new gnu.kawa.functions.LispTabulateFormat
            r12.<init>(r3, r5, r11, r2)
            r2 = r12
            r12 = 0
            r14 = 0
            goto L_0x05a2
        L_0x02a5:
            gnu.kawa.functions.LispPluralFormat r2 = gnu.kawa.functions.LispPluralFormat.getInstance(r3, r2)
            r12 = 0
            r14 = 0
            goto L_0x05a2
        L_0x02ad:
            int r2 = getParam(r6, r8)
            if (r2 != r11) goto L_0x02b4
            r2 = 0
        L_0x02b4:
            gnu.kawa.functions.LispIndentFormat r2 = gnu.kawa.functions.LispIndentFormat.getInstance(r2, r3)
            r12 = 0
            r14 = 0
            goto L_0x05a2
        L_0x02bc:
            if (r12 <= 0) goto L_0x02c2
            int r14 = getParam(r6, r8)
        L_0x02c2:
            r5 = 1
            gnu.kawa.functions.LispCharacterFormat r2 = gnu.kawa.functions.LispCharacterFormat.getInstance(r14, r5, r2, r3)
            r12 = 0
            r14 = 0
            goto L_0x05a2
        L_0x02cb:
            r11 = 82
            if (r5 != r11) goto L_0x02d9
            int r5 = r8 + 1
            int r15 = getParam(r6, r8)
            r21 = r15
            goto L_0x02f6
        L_0x02d9:
            r11 = 68
            if (r5 != r11) goto L_0x02e1
            r5 = r8
            r21 = 10
            goto L_0x02f6
        L_0x02e1:
            r11 = 79
            if (r5 != r11) goto L_0x02e9
            r5 = r8
            r21 = 8
            goto L_0x02f6
        L_0x02e9:
            r11 = 88
            if (r5 != r11) goto L_0x02f3
            r15 = 16
            r5 = r8
            r21 = 16
            goto L_0x02f6
        L_0x02f3:
            r5 = r8
            r21 = 2
        L_0x02f6:
            int r22 = getParam(r6, r5)
            int r11 = r5 + 1
            int r23 = getParam(r6, r11)
            int r11 = r5 + 2
            int r24 = getParam(r6, r11)
            r11 = 3
            int r5 = r5 + r11
            int r25 = getParam(r6, r5)
            if (r3 == 0) goto L_0x0311
            r3 = 1
            goto L_0x0312
        L_0x0311:
            r3 = 0
        L_0x0312:
            if (r2 == 0) goto L_0x0319
            r2 = r3 | 2
            r26 = r2
            goto L_0x031b
        L_0x0319:
            r26 = r3
        L_0x031b:
            java.text.Format r2 = gnu.kawa.functions.IntegerFormat.getInstance(r21, r22, r23, r24, r25, r26)
            r12 = 0
            r14 = 0
            goto L_0x05a2
        L_0x0323:
            r3 = 65
            if (r5 == r3) goto L_0x0329
            r3 = 1
            goto L_0x032a
        L_0x0329:
            r3 = 0
        L_0x032a:
            gnu.kawa.functions.ObjectFormat r3 = gnu.kawa.functions.ObjectFormat.getInstance(r3)
            if (r12 <= 0) goto L_0x035d
            int r20 = getParam(r6, r8)
            int r5 = r8 + 1
            int r21 = getParam(r6, r5)
            int r5 = r8 + 2
            int r22 = getParam(r6, r5)
            int r5 = r8 + 3
            int r23 = getParam(r6, r5)
            gnu.kawa.functions.LispObjectFormat r5 = new gnu.kawa.functions.LispObjectFormat
            r19 = r3
            gnu.text.ReportFormat r19 = (gnu.text.ReportFormat) r19
            if (r2 == 0) goto L_0x0351
            r24 = 0
            goto L_0x0353
        L_0x0351:
            r24 = 100
        L_0x0353:
            r18 = r5
            r18.<init>(r19, r20, r21, r22, r23, r24)
            r2 = r5
            r12 = 0
            r14 = 0
            goto L_0x05a2
        L_0x035d:
            r2 = r3
            r12 = 0
            r14 = 0
            goto L_0x05a2
        L_0x0362:
            gnu.kawa.functions.LispIterationFormat r3 = new gnu.kawa.functions.LispIterationFormat
            r3.<init>()
            r3.seenAt = r2
            r2 = 1
            r3.maxIterations = r2
            r3.atLeastOnce = r2
            r2 = r3
            r12 = 0
            r14 = 0
            goto L_0x05a2
        L_0x0374:
            if (r9 < 0) goto L_0x041c
            java.lang.Object r2 = r6.elementAt(r9)
            boolean r2 = r2 instanceof gnu.kawa.functions.LispPrettyFormat
            if (r2 == 0) goto L_0x041c
            int r2 = r9 + 3
            int r5 = r2 + r10
            java.text.Format r5 = popFormats(r6, r5, r8)
            r6.push(r5)
            java.lang.Object r5 = r6.elementAt(r9)
            gnu.kawa.functions.LispPrettyFormat r5 = (gnu.kawa.functions.LispPrettyFormat) r5
            int r8 = r6.size()
            java.text.Format[] r8 = getFormats(r6, r2, r8)
            r5.segments = r8
            r6.setSize(r2)
            java.lang.Object r2 = r6.pop()
            gnu.math.IntNum r2 = (gnu.math.IntNum) r2
            r2.intValue()
            java.lang.Object r2 = r6.pop()
            gnu.math.IntNum r2 = (gnu.math.IntNum) r2
            int r9 = r2.intValue()
            if (r3 == 0) goto L_0x0414
            java.text.Format[] r2 = r5.segments
            int r2 = r2.length
            r3 = 3
            if (r2 > r3) goto L_0x040c
            if (r2 < r15) goto L_0x03de
            java.text.Format[] r3 = r5.segments
            r8 = 0
            r3 = r3[r8]
            boolean r3 = r3 instanceof gnu.text.LiteralFormat
            if (r3 == 0) goto L_0x03d6
            java.text.Format[] r3 = r5.segments
            r3 = r3[r8]
            gnu.text.LiteralFormat r3 = (gnu.text.LiteralFormat) r3
            java.lang.String r3 = r3.content()
            r5.prefix = r3
            java.text.Format[] r3 = r5.segments
            r8 = 1
            r3 = r3[r8]
            r5.body = r3
            goto L_0x03e5
        L_0x03d6:
            java.text.ParseException r1 = new java.text.ParseException
            java.lang.String r2 = "prefix segment is not literal"
            r1.<init>(r2, r13)
            throw r1
        L_0x03de:
            java.text.Format[] r3 = r5.segments
            r8 = 0
            r3 = r3[r8]
            r5.body = r3
        L_0x03e5:
            r3 = 3
            if (r2 < r3) goto L_0x0405
            java.text.Format[] r2 = r5.segments
            r2 = r2[r15]
            boolean r2 = r2 instanceof gnu.text.LiteralFormat
            if (r2 == 0) goto L_0x03fd
            java.text.Format[] r2 = r5.segments
            r2 = r2[r15]
            gnu.text.LiteralFormat r2 = (gnu.text.LiteralFormat) r2
            java.lang.String r2 = r2.content()
            r5.suffix = r2
            goto L_0x0405
        L_0x03fd:
            java.text.ParseException r1 = new java.text.ParseException
            java.lang.String r2 = "suffix segment is not literal"
            r1.<init>(r2, r13)
            throw r1
        L_0x0405:
            r8 = r13
            r2 = 0
            r3 = 0
            r5 = 100
            goto L_0x001e
        L_0x040c:
            java.text.ParseException r1 = new java.text.ParseException
            java.lang.String r2 = "too many segments in Logical Block format"
            r1.<init>(r2, r13)
            throw r1
        L_0x0414:
            java.text.ParseException r1 = new java.text.ParseException
            java.lang.String r2 = "not implemented: justfication i.e. ~<...~>"
            r1.<init>(r2, r13)
            throw r1
        L_0x041c:
            java.text.ParseException r1 = new java.text.ParseException
            java.lang.String r2 = "saw ~> without matching ~<"
            r1.<init>(r2, r13)
            throw r1
        L_0x0424:
            gnu.kawa.functions.LispPrettyFormat r5 = new gnu.kawa.functions.LispPrettyFormat
            r5.<init>()
            r5.seenAt = r2
            if (r3 == 0) goto L_0x0436
            java.lang.String r2 = "("
            r5.prefix = r2
            java.lang.String r2 = ")"
            r5.suffix = r2
            goto L_0x043c
        L_0x0436:
            java.lang.String r2 = ""
            r5.prefix = r2
            r5.suffix = r2
        L_0x043c:
            r6.setSize(r8)
            r6.push(r5)
            gnu.math.IntNum r2 = gnu.math.IntNum.make((int) r9)
            r6.push(r2)
            gnu.math.IntNum r2 = gnu.math.IntNum.make((int) r10)
            r6.push(r2)
            r9 = r8
            r8 = r13
            r2 = 0
            r3 = 0
            r5 = 100
            r10 = 0
            goto L_0x001e
        L_0x045b:
            if (r9 < 0) goto L_0x04a9
            java.lang.Object r5 = r6.elementAt(r9)
            boolean r5 = r5 instanceof gnu.kawa.functions.LispChoiceFormat
            if (r5 == 0) goto L_0x0483
            java.lang.Object r2 = r6.elementAt(r9)
            gnu.kawa.functions.LispChoiceFormat r2 = (gnu.kawa.functions.LispChoiceFormat) r2
            if (r3 == 0) goto L_0x0470
            r3 = 1
            r2.lastIsDefault = r3
        L_0x0470:
            int r2 = r9 + 3
            int r2 = r2 + r10
            java.text.Format r2 = popFormats(r6, r2, r8)
            r6.push(r2)
            int r10 = r10 + 1
            r8 = r13
            r2 = 0
            r3 = 0
            r5 = 100
            goto L_0x001e
        L_0x0483:
            java.lang.Object r3 = r6.elementAt(r9)
            boolean r3 = r3 instanceof gnu.kawa.functions.LispPrettyFormat
            if (r3 == 0) goto L_0x04a9
            java.lang.Object r3 = r6.elementAt(r9)
            gnu.kawa.functions.LispPrettyFormat r3 = (gnu.kawa.functions.LispPrettyFormat) r3
            if (r2 == 0) goto L_0x0496
            r2 = 1
            r3.perLine = r2
        L_0x0496:
            int r2 = r9 + 3
            int r2 = r2 + r10
            java.text.Format r2 = popFormats(r6, r2, r8)
            r6.push(r2)
            int r10 = r10 + 1
            r8 = r13
            r2 = 0
            r3 = 0
            r5 = 100
            goto L_0x001e
        L_0x04a9:
            java.text.ParseException r1 = new java.text.ParseException
            java.lang.String r2 = "saw ~; without matching ~[ or ~<"
            r1.<init>(r2, r13)
            throw r1
        L_0x04b1:
            gnu.kawa.functions.LispRepositionFormat r5 = new gnu.kawa.functions.LispRepositionFormat
            int r11 = getParam(r6, r8)
            r5.<init>(r11, r3, r2)
            r2 = r5
            r12 = 0
            r14 = 0
            goto L_0x05a2
        L_0x04bf:
            if (r9 < 0) goto L_0x04e9
            java.lang.Object r2 = r6.elementAt(r9)
            boolean r2 = r2 instanceof gnu.text.CaseConvertFormat
            if (r2 == 0) goto L_0x04e9
            java.lang.Object r2 = r6.elementAt(r9)
            gnu.text.CaseConvertFormat r2 = (gnu.text.CaseConvertFormat) r2
            int r9 = r9 + 2
            java.text.Format r3 = popFormats(r6, r9, r8)
            r2.setBaseFormat(r3)
            java.lang.Object r2 = r6.pop()
            gnu.math.IntNum r2 = (gnu.math.IntNum) r2
            int r9 = r2.intValue()
            r8 = r13
            r2 = 0
            r3 = 0
            r5 = 100
            goto L_0x001e
        L_0x04e9:
            java.text.ParseException r1 = new java.text.ParseException
            java.lang.String r2 = "saw ~) without matching ~("
            r1.<init>(r2, r13)
            throw r1
        L_0x04f1:
            if (r3 == 0) goto L_0x04fb
            if (r2 == 0) goto L_0x04f8
            r11 = 85
            goto L_0x0502
        L_0x04f8:
            r11 = 67
            goto L_0x0502
        L_0x04fb:
            if (r2 == 0) goto L_0x0500
            r11 = 84
            goto L_0x0502
        L_0x0500:
            r11 = 76
        L_0x0502:
            gnu.text.CaseConvertFormat r2 = new gnu.text.CaseConvertFormat
            r3 = 0
            r2.<init>(r3, r11)
            r6.setSize(r8)
            r6.push(r2)
            gnu.math.IntNum r2 = gnu.math.IntNum.make((int) r9)
            r6.push(r2)
        L_0x0517:
            r9 = r8
            r8 = r13
            r2 = 0
        L_0x051a:
            r3 = 0
            r5 = 100
            goto L_0x001e
        L_0x051f:
            int r2 = getParam(r6, r8)
            gnu.kawa.functions.LispFreshlineFormat r3 = new gnu.kawa.functions.LispFreshlineFormat
            r3.<init>(r2)
            r2 = r3
            r12 = 0
            r14 = 0
            goto L_0x05a2
        L_0x052d:
            r2 = 1
            int r3 = getParam(r6, r8)
            if (r3 != r11) goto L_0x0535
            goto L_0x0536
        L_0x0535:
            r2 = r3
        L_0x0536:
            r3 = 76
            gnu.kawa.functions.LispNewlineFormat r2 = gnu.kawa.functions.LispNewlineFormat.getInstance(r2, r3)
            r12 = 0
            r14 = 0
            goto L_0x05a2
        L_0x053f:
            gnu.kawa.functions.LispRealFormat r11 = new gnu.kawa.functions.LispRealFormat
            r11.<init>()
            r11.op = r5
            int r12 = getParam(r6, r8)
            r11.arg1 = r12
            int r12 = r8 + 1
            int r12 = getParam(r6, r12)
            r11.arg2 = r12
            int r12 = r8 + 2
            int r12 = getParam(r6, r12)
            r11.arg3 = r12
            int r12 = r8 + 3
            int r12 = getParam(r6, r12)
            r11.arg4 = r12
            r12 = 36
            if (r5 == r12) goto L_0x0588
            int r12 = r8 + 4
            int r12 = getParam(r6, r12)
            r11.arg5 = r12
            r12 = 69
            if (r5 == r12) goto L_0x0578
            r12 = 71
            if (r5 != r12) goto L_0x0588
        L_0x0578:
            int r5 = r8 + 5
            int r5 = getParam(r6, r5)
            r11.arg6 = r5
            int r5 = r8 + 6
            int r5 = getParam(r6, r5)
            r11.arg7 = r5
        L_0x0588:
            r11.showPlus = r2
            r11.internalPad = r3
            int r2 = r11.argsUsed
            if (r2 != 0) goto L_0x0597
            r12 = 0
            r14 = 0
            java.text.Format r2 = r11.resolve(r12, r14)
            goto L_0x05a2
        L_0x0597:
            r12 = 0
            r14 = 0
            r2 = r11
            goto L_0x05a2
        L_0x059b:
            r12 = 0
            r14 = 0
            gnu.text.FlushFormat r2 = gnu.text.FlushFormat.getInstance()
        L_0x05a2:
            r6.setSize(r8)
            r6.push(r2)
            goto L_0x05cf
        L_0x05a9:
            r12 = 0
            r14 = 0
            if (r2 == 0) goto L_0x05b0
            r4.append(r5)
        L_0x05b0:
            if (r3 != 0) goto L_0x05cf
            r8 = r13
        L_0x05b3:
            if (r8 >= r7) goto L_0x05c9
            int r2 = r8 + 1
            char r3 = r1[r8]
            boolean r3 = java.lang.Character.isWhitespace(r3)
            if (r3 != 0) goto L_0x05c7
            int r8 = r2 + -1
            r2 = r12
            r3 = 0
            r5 = 100
            goto L_0x001e
        L_0x05c7:
            r8 = r2
            goto L_0x05b3
        L_0x05c9:
            r2 = r12
            r3 = 0
            r5 = 100
            goto L_0x001e
        L_0x05cf:
            r2 = r12
            r8 = r13
            goto L_0x051a
        L_0x05d3:
            r12 = 0
            r14 = 0
            int r2 = r13 + 1
            char r3 = r1[r13]
            r13 = r2
            r2 = r12
            r5 = 100
            r11 = 126(0x7e, float:1.77E-43)
            r12 = r3
            r3 = 0
            goto L_0x0071
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.LispFormat.<init>(char[], int, int):void");
    }

    static Format[] getFormats(Vector vector, int start, int end) {
        Format[] f = new Format[(end - start)];
        for (int i = start; i < end; i++) {
            f[i - start] = (Format) vector.elementAt(i);
        }
        return f;
    }

    static Format popFormats(Vector vector, int start, int end) {
        Format f;
        if (end == start + 1) {
            f = (Format) vector.elementAt(start);
        } else {
            f = new CompoundFormat(getFormats(vector, start, end));
        }
        vector.setSize(start);
        return f;
    }

    public LispFormat(String str) throws ParseException {
        this(str.toCharArray());
    }

    public LispFormat(char[] format) throws ParseException {
        this(format, 0, format.length);
    }

    public static int getParam(Vector vec, int index) {
        if (index >= vec.size()) {
            return -1073741824;
        }
        Object arg = vec.elementAt(index);
        if (arg == paramFromList) {
            return -1610612736;
        }
        if (arg == paramFromCount) {
            return ReportFormat.PARAM_FROM_COUNT;
        }
        if (arg == paramUnspecified) {
            return -1073741824;
        }
        return getParam(arg, -1073741824);
    }

    public static Object[] asArray(Object arg) {
        if (arg instanceof Object[]) {
            return (Object[]) arg;
        }
        if (!(arg instanceof Sequence)) {
            return null;
        }
        int count = ((Sequence) arg).size();
        Object[] arr = new Object[count];
        int i = 0;
        while (arg instanceof Pair) {
            Pair pair = (Pair) arg;
            arr[i] = pair.getCar();
            arg = pair.getCdr();
            i++;
        }
        if (i < count) {
            if (!(arg instanceof Sequence)) {
                return null;
            }
            int npairs = i;
            Sequence seq = (Sequence) arg;
            while (i < count) {
                arr[i] = seq.get(npairs + i);
                i++;
            }
        }
        return arr;
    }
}
