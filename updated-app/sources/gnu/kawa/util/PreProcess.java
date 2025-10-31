package gnu.kawa.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class PreProcess {
    static final String JAVA4_FEATURES = "+JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio";
    static final String JAVA5_FEATURES = "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName";
    static final String NO_JAVA4_FEATURES = "-JAVA5 -use:java.util.IdentityHashMap -use:java.lang.CharSequence -use:java.lang.Throwable.getCause -use:java.net.URI -use:java.util.regex -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAVA5 -JAVA6 -JAVA6COMPAT5 -JAXP-QName -use:java.text.Normalizer -SAX2 -use:java.nio -Android";
    static final String NO_JAVA6_FEATURES = "-JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer";
    static String[] version_features = {"java1", "-JAVA2 -JAVA5 -use:java.util.IdentityHashMap -use:java.lang.CharSequence -use:java.lang.Throwable.getCause -use:java.net.URI -use:java.util.regex -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAVA5 -JAVA6 -JAVA6COMPAT5 -JAXP-QName -use:java.text.Normalizer -SAX2 -use:java.nio -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java2", "+JAVA2 -JAVA5 -use:java.util.IdentityHashMap -use:java.lang.CharSequence -use:java.lang.Throwable.getCause -use:java.net.URI -use:java.util.regex -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAVA5 -JAVA6 -JAVA6COMPAT5 -JAXP-QName -use:java.text.Normalizer -SAX2 -use:java.nio -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java4", "-JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAXP-QName -JAVA6COMPAT5 -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java4x", "-JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +JAXP-1.3 +use:javax.xml.transform -JAXP-QName -JAVA6COMPAT5 -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java5", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName -JAVA6COMPAT5 -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java6compat5", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName -JAVA6 -JAVA7 +JAVA6COMPAT5 +use:java.text.Normalizer -use:java.dyn -Android", "java6", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName +JAVA6 -JAVA7 -JAVA6COMPAT5 +use:java.text.Normalizer -use:java.dyn -Android", "java7", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName +JAVA6 +JAVA7 -JAVA6COMPAT5 +use:java.text.Normalizer +use:java.dyn -Android", "android", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +JAXP-1.3 -JAXP-QName -use:javax.xml.transform -JAVA6 -JAVA6COMPAT5 +Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer"};
    String filename;
    Hashtable keywords = new Hashtable();
    int lineno;
    byte[] resultBuffer;
    int resultLength;

    /* access modifiers changed from: package-private */
    public void error(String msg) {
        System.err.println(this.filename + ':' + this.lineno + ": " + msg);
        System.exit(-1);
    }

    public void filter(String filename2) throws Throwable {
        if (filter(filename2, new BufferedInputStream(new FileInputStream(filename2)))) {
            FileOutputStream out = new FileOutputStream(filename2);
            out.write(this.resultBuffer, 0, this.resultLength);
            out.close();
            System.err.println("Pre-processed " + filename2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:172:0x00e0 A[EDGE_INSN: B:172:0x00e0->B:62:0x00e0 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00d0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean filter(java.lang.String r27, java.io.BufferedInputStream r28) throws java.lang.Throwable {
        /*
            r26 = this;
            r0 = r26
            r1 = r27
            r0.filename = r1
            r2 = 0
            r3 = 2000(0x7d0, float:2.803E-42)
            byte[] r3 = new byte[r3]
            r4 = 0
            r5 = 0
            r6 = -1
            r7 = 0
            r8 = 1
            r0.lineno = r8
            r9 = -1
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
        L_0x0018:
            int r15 = r28.read()
            if (r15 >= 0) goto L_0x0022
            r1 = r28
            goto L_0x00e0
        L_0x0022:
            int r8 = r4 + 10
            r16 = r2
            int r2 = r3.length
            r1 = 0
            if (r8 < r2) goto L_0x0032
            int r2 = r4 * 2
            byte[] r2 = new byte[r2]
            java.lang.System.arraycopy(r3, r1, r2, r1, r4)
            r3 = r2
        L_0x0032:
            r2 = 13
            r8 = 10
            if (r15 != r8) goto L_0x004c
            if (r4 <= 0) goto L_0x004c
            int r17 = r4 + -1
            byte r1 = r3[r17]
            if (r1 != r2) goto L_0x004c
            int r1 = r4 + 1
            byte r2 = (byte) r15
            r3[r4] = r2
            r4 = r1
            r2 = r16
            r8 = 1
            r1 = r27
            goto L_0x0018
        L_0x004c:
            r1 = 32
            if (r9 < 0) goto L_0x00b2
            if (r6 >= 0) goto L_0x00b2
            if (r14 > 0) goto L_0x00b2
            if (r15 == r2) goto L_0x00b2
            if (r15 == r8) goto L_0x00b2
            if (r9 == r10) goto L_0x0064
            if (r15 == r1) goto L_0x0061
            r8 = 9
            if (r15 == r8) goto L_0x0061
            goto L_0x0064
        L_0x0061:
            r1 = r28
            goto L_0x00b4
        L_0x0064:
            r8 = 47
            if (r15 != r8) goto L_0x0094
            r2 = 100
            r1 = r28
            r1.mark(r2)
            int r2 = r28.read()
            if (r2 != r8) goto L_0x0077
            r8 = 0
            goto L_0x0090
        L_0x0077:
            r8 = 42
            if (r2 != r8) goto L_0x008f
        L_0x007b:
            int r2 = r28.read()
            r8 = 32
            if (r2 == r8) goto L_0x007b
            r8 = 9
            if (r2 == r8) goto L_0x007b
            r8 = 35
            if (r2 == r8) goto L_0x008d
            r8 = 1
            goto L_0x008e
        L_0x008d:
            r8 = 0
        L_0x008e:
            goto L_0x0090
        L_0x008f:
            r8 = 1
        L_0x0090:
            r28.reset()
            goto L_0x0097
        L_0x0094:
            r1 = r28
            r8 = 1
        L_0x0097:
            if (r8 == 0) goto L_0x00b4
            int r2 = r4 + 1
            r21 = 47
            r3[r4] = r21
            int r4 = r2 + 1
            r3[r2] = r21
            int r2 = r4 + 1
            r24 = 32
            r3[r4] = r24
            r4 = 1
            r14 = 1
            r25 = r4
            r4 = r2
            r2 = r14
            r14 = r25
            goto L_0x00b6
        L_0x00b2:
            r1 = r28
        L_0x00b4:
            r2 = r16
        L_0x00b6:
            r8 = 32
            if (r15 == r8) goto L_0x0110
            r8 = 9
            if (r15 == r8) goto L_0x0110
            if (r6 >= 0) goto L_0x0110
            r6 = r4
            if (r11 <= 0) goto L_0x0110
            if (r9 == r10) goto L_0x0110
            r8 = 47
            if (r15 != r8) goto L_0x0110
            int r15 = r28.read()
            if (r15 >= 0) goto L_0x00d0
            goto L_0x00e0
        L_0x00d0:
            if (r15 == r8) goto L_0x00d9
            int r16 = r4 + 1
            r3[r4] = r8
            r4 = r16
            goto L_0x0110
        L_0x00d9:
            int r15 = r28.read()
            if (r15 >= 0) goto L_0x00ff
        L_0x00e0:
            if (r11 == 0) goto L_0x00fa
            r0.lineno = r7
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r15 = "unterminated "
            java.lang.StringBuilder r8 = r8.append(r15)
            java.lang.StringBuilder r8 = r8.append(r13)
            java.lang.String r8 = r8.toString()
            r0.error(r8)
        L_0x00fa:
            r0.resultBuffer = r3
            r0.resultLength = r4
            return r2
        L_0x00ff:
            r14 = -1
            r2 = 1
            r8 = 32
            if (r15 != r8) goto L_0x0110
            int r15 = r28.read()
            if (r15 == r8) goto L_0x010f
            r8 = 9
            if (r15 != r8) goto L_0x0110
        L_0x010f:
            r6 = -1
        L_0x0110:
            byte r8 = (byte) r15
            r3[r4] = r8
            r8 = 1
            int r4 = r4 + r8
            r8 = 13
            if (r15 == r8) goto L_0x013a
            r8 = 10
            if (r15 != r8) goto L_0x011e
            goto L_0x013a
        L_0x011e:
            if (r6 >= 0) goto L_0x0132
            r8 = 9
            if (r15 != r8) goto L_0x0129
            int r8 = r10 + 8
            r8 = r8 & -8
            goto L_0x012b
        L_0x0129:
            int r8 = r10 + 1
        L_0x012b:
            r16 = r2
            r19 = r3
            r10 = 1
            goto L_0x02e5
        L_0x0132:
            r16 = r2
            r19 = r3
            r8 = r10
            r10 = 1
            goto L_0x02e5
        L_0x013a:
            r8 = -1
            r16 = 0
            r22 = r5
            r1 = r16
            r16 = r2
            r2 = r22
        L_0x0145:
            r22 = r5
            int r5 = r4 + -1
            if (r2 >= r5) goto L_0x0168
            byte r5 = r3[r2]
            r23 = r6
            r6 = 32
            if (r5 == r6) goto L_0x015f
            byte r5 = r3[r2]
            r6 = 9
            if (r5 == r6) goto L_0x0161
            r1 = r2
            if (r8 >= 0) goto L_0x0161
            r5 = r2
            r8 = r5
            goto L_0x0161
        L_0x015f:
            r6 = 9
        L_0x0161:
            int r2 = r2 + 1
            r5 = r22
            r6 = r23
            goto L_0x0145
        L_0x0168:
            r23 = r6
            int r2 = r1 - r8
            r5 = 4
            if (r2 < r5) goto L_0x02d5
            byte r2 = r3[r8]
            r5 = 47
            if (r2 != r5) goto L_0x02d5
            int r2 = r8 + 1
            byte r2 = r3[r2]
            r5 = 42
            if (r2 != r5) goto L_0x02d5
            int r2 = r1 + -1
            byte r2 = r3[r2]
            if (r2 != r5) goto L_0x02d5
            byte r2 = r3[r1]
            r5 = 47
            if (r2 != r5) goto L_0x02d5
            int r8 = r8 + 2
        L_0x018b:
            if (r8 >= r1) goto L_0x0196
            byte r2 = r3[r8]
            r5 = 32
            if (r2 != r5) goto L_0x0196
            int r8 = r8 + 1
            goto L_0x018b
        L_0x0196:
            int r1 = r1 + -2
        L_0x0198:
            if (r1 <= r8) goto L_0x01a3
            byte r2 = r3[r1]
            r5 = 32
            if (r2 != r5) goto L_0x01a3
            int r1 = r1 + -1
            goto L_0x0198
        L_0x01a3:
            byte r2 = r3[r8]
            r5 = 35
            if (r2 != r5) goto L_0x02d0
            java.lang.String r2 = new java.lang.String
            int r5 = r1 - r8
            r6 = 1
            int r5 = r5 + r6
            java.lang.String r6 = "ISO-8859-1"
            r2.<init>(r3, r8, r5, r6)
            r5 = 32
            int r5 = r2.indexOf(r5)
            int r7 = r0.lineno
            if (r5 <= 0) goto L_0x01d4
            r6 = 0
            java.lang.String r13 = r2.substring(r6, r5)
            java.lang.String r17 = r2.substring(r5)
            java.lang.String r6 = r17.trim()
            r17 = r1
            java.util.Hashtable r1 = r0.keywords
            java.lang.Object r1 = r1.get(r6)
            goto L_0x01df
        L_0x01d4:
            r17 = r1
            r1 = r2
            java.lang.String r6 = ""
            r13 = 0
            r25 = r13
            r13 = r1
            r1 = r25
        L_0x01df:
            r19 = r3
            java.lang.String r3 = "#ifdef"
            boolean r3 = r3.equals(r13)
            if (r3 != 0) goto L_0x0272
            java.lang.String r3 = "#ifndef"
            boolean r3 = r3.equals(r13)
            if (r3 == 0) goto L_0x01f5
            r20 = r5
            goto L_0x0274
        L_0x01f5:
            java.lang.String r3 = "#else"
            boolean r3 = r3.equals(r13)
            r20 = r5
            java.lang.String r5 = "unexpected "
            if (r3 == 0) goto L_0x022c
            if (r11 != 0) goto L_0x0218
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.StringBuilder r3 = r3.append(r13)
            java.lang.String r3 = r3.toString()
            r0.error(r3)
            goto L_0x026e
        L_0x0218:
            if (r11 != r12) goto L_0x0220
            r9 = -1
            r12 = 0
            r1 = r17
            goto L_0x02d7
        L_0x0220:
            r9 = r10
            if (r12 != 0) goto L_0x0228
            r12 = r11
            r1 = r17
            goto L_0x02d7
        L_0x0228:
            r1 = r17
            goto L_0x02d7
        L_0x022c:
            java.lang.String r3 = "#endif"
            boolean r3 = r3.equals(r13)
            if (r3 == 0) goto L_0x0258
            if (r11 != 0) goto L_0x024a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.StringBuilder r3 = r3.append(r13)
            java.lang.String r3 = r3.toString()
            r0.error(r3)
        L_0x024a:
            if (r11 != r12) goto L_0x024f
            r12 = 0
            r9 = -1
            goto L_0x0252
        L_0x024f:
            if (r12 <= 0) goto L_0x0252
            r9 = r10
        L_0x0252:
            int r11 = r11 + -1
            r1 = r17
            goto L_0x02d7
        L_0x0258:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "unknown command: "
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.StringBuilder r3 = r3.append(r2)
            java.lang.String r3 = r3.toString()
            r0.error(r3)
        L_0x026e:
            r1 = r17
            goto L_0x02d7
        L_0x0272:
            r20 = r5
        L_0x0274:
            if (r1 != 0) goto L_0x02a7
            java.io.PrintStream r3 = java.lang.System.err
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r21 = r1
            r18 = 0
            r1 = r27
            java.lang.StringBuilder r5 = r5.append(r1)
            java.lang.String r1 = ":"
            java.lang.StringBuilder r1 = r5.append(r1)
            int r5 = r0.lineno
            java.lang.StringBuilder r1 = r1.append(r5)
            java.lang.String r5 = ": warning - undefined keyword: "
            java.lang.StringBuilder r1 = r1.append(r5)
            java.lang.StringBuilder r1 = r1.append(r6)
            java.lang.String r1 = r1.toString()
            r3.println(r1)
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            goto L_0x02ab
        L_0x02a7:
            r21 = r1
            r18 = 0
        L_0x02ab:
            int r11 = r11 + 1
            if (r12 <= 0) goto L_0x02b3
            r9 = r10
            r1 = r17
            goto L_0x02d7
        L_0x02b3:
            r3 = 3
            char r3 = r13.charAt(r3)
            r5 = 110(0x6e, float:1.54E-43)
            if (r3 != r5) goto L_0x02be
            r3 = 1
            goto L_0x02bf
        L_0x02be:
            r3 = 0
        L_0x02bf:
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            if (r1 != r5) goto L_0x02c5
            r5 = 1
            goto L_0x02c6
        L_0x02c5:
            r5 = 0
        L_0x02c6:
            if (r3 == r5) goto L_0x02cd
            r9 = r10
            r12 = r11
            r1 = r17
            goto L_0x02d7
        L_0x02cd:
            r1 = r17
            goto L_0x02d7
        L_0x02d0:
            r17 = r1
            r19 = r3
            goto L_0x02d7
        L_0x02d5:
            r19 = r3
        L_0x02d7:
            r2 = r4
            r3 = -1
            r5 = 0
            int r6 = r0.lineno
            r10 = 1
            int r6 = r6 + r10
            r0.lineno = r6
            r1 = 0
            r14 = r1
            r6 = r3
            r8 = r5
            r5 = r2
        L_0x02e5:
            r1 = r27
            r10 = r8
            r2 = r16
            r3 = r19
            r8 = 1
            goto L_0x0018
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.util.PreProcess.filter(java.lang.String, java.io.BufferedInputStream):boolean");
    }

    /* access modifiers changed from: package-private */
    public void handleArg(String arg) {
        int i = 1;
        if (arg.charAt(0) == '%') {
            String arg2 = arg.substring(1);
            int i2 = 0;
            while (true) {
                if (i2 >= version_features.length) {
                    System.err.println("Unknown version: " + arg2);
                    System.exit(-1);
                }
                if (arg2.equals(version_features[i2])) {
                    break;
                }
                i2 += 2;
            }
            String features = version_features[i2 + 1];
            System.err.println("(variant " + arg2 + " maps to: " + features + ")");
            StringTokenizer tokenizer = new StringTokenizer(features);
            while (tokenizer.hasMoreTokens()) {
                handleArg(tokenizer.nextToken());
            }
            String str = arg2;
        } else if (arg.charAt(0) == '+') {
            this.keywords.put(arg.substring(1), Boolean.TRUE);
        } else if (arg.charAt(0) == '-') {
            int eq = arg.indexOf(61);
            if (eq > 1) {
                if (arg.charAt(1) == '-') {
                    i = 2;
                }
                String keyword = arg.substring(i, eq);
                String value = arg.substring(eq + 1);
                Boolean b = Boolean.FALSE;
                if (value.equalsIgnoreCase("true")) {
                    b = Boolean.TRUE;
                } else if (!value.equalsIgnoreCase("false")) {
                    System.err.println("invalid value " + value + " for " + keyword);
                    System.exit(-1);
                }
                this.keywords.put(keyword, b);
                return;
            }
            this.keywords.put(arg.substring(1), Boolean.FALSE);
        } else {
            try {
                filter(arg);
            } catch (Throwable ex) {
                System.err.println("caught " + ex);
                ex.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public static void main(String[] args) {
        PreProcess pp = new PreProcess();
        pp.keywords.put("true", Boolean.TRUE);
        pp.keywords.put("false", Boolean.FALSE);
        for (String handleArg : args) {
            pp.handleArg(handleArg);
        }
    }
}
