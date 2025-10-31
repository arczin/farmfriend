package gnu.xml;

import gnu.lists.Consumer;
import gnu.text.LineBufferedReader;
import gnu.text.LineInputStreamReader;
import gnu.text.Path;
import gnu.text.SourceMessages;
import java.io.IOException;
import java.io.InputStream;

public class XMLParser {
    private static final int ATTRIBUTE_SEEN_EQ_STATE = 11;
    private static final int ATTRIBUTE_SEEN_NAME_STATE = 8;
    static final String BAD_ENCODING_SYNTAX = "bad 'encoding' declaration";
    static final String BAD_STANDALONE_SYNTAX = "bad 'standalone' declaration";
    private static final int BEGIN_ELEMENT_STATE = 2;
    private static final int DOCTYPE_NAME_SEEN_STATE = 16;
    private static final int DOCTYPE_SEEN_STATE = 13;
    private static final int END_ELEMENT_STATE = 4;
    private static final int EXPECT_NAME_MODIFIER = 1;
    private static final int EXPECT_RIGHT_STATE = 27;
    private static final int INIT_LEFT_QUEST_STATE = 30;
    private static final int INIT_LEFT_STATE = 34;
    private static final int INIT_STATE = 0;
    private static final int INIT_TEXT_STATE = 31;
    private static final int INVALID_VERSION_DECL = 35;
    private static final int MAYBE_ATTRIBUTE_STATE = 10;
    private static final int PREV_WAS_CR_STATE = 28;
    private static final int SAW_AMP_SHARP_STATE = 26;
    private static final int SAW_AMP_STATE = 25;
    private static final int SAW_ENTITY_REF = 6;
    private static final int SAW_EOF_ERROR = 37;
    private static final int SAW_ERROR = 36;
    private static final int SAW_LEFT_EXCL_MINUS_STATE = 22;
    private static final int SAW_LEFT_EXCL_STATE = 20;
    private static final int SAW_LEFT_QUEST_STATE = 21;
    private static final int SAW_LEFT_SLASH_STATE = 19;
    private static final int SAW_LEFT_STATE = 14;
    private static final int SKIP_SPACES_MODIFIER = 2;
    private static final int TEXT_STATE = 1;

    public static void parse(Object uri, SourceMessages messages, Consumer out) throws IOException {
        parse(Path.openInputStream(uri), uri, messages, out);
    }

    public static LineInputStreamReader XMLStreamReader(InputStream strm) throws IOException {
        LineInputStreamReader in = new LineInputStreamReader(strm);
        int b1 = in.getByte();
        int b4 = -1;
        int b2 = b1 < 0 ? -1 : in.getByte();
        int b3 = b2 < 0 ? -1 : in.getByte();
        if (b1 == 239 && b2 == 187 && b3 == 191) {
            in.resetStart(3);
            in.setCharset("UTF-8");
        } else if (b1 == 255 && b2 == 254 && b3 != 0) {
            in.resetStart(2);
            in.setCharset("UTF-16LE");
        } else if (b1 == 254 && b2 == 255 && b3 != 0) {
            in.resetStart(2);
            in.setCharset("UTF-16BE");
        } else {
            if (b3 >= 0) {
                b4 = in.getByte();
            }
            if (b1 == 76 && b2 == 111 && b3 == 167 && b4 == 148) {
                throw new RuntimeException("XMLParser: EBCDIC encodings not supported");
            }
            in.resetStart(0);
            if ((b1 == 60 && ((b2 == 63 && b3 == 120 && b4 == 109) || (b2 == 0 && b3 == 63 && b4 == 0))) || (b1 == 0 && b2 == 60 && b3 == 0 && b4 == 63)) {
                char[] buffer = in.buffer;
                if (buffer == null) {
                    char[] cArr = new char[8192];
                    buffer = cArr;
                    in.buffer = cArr;
                }
                int pos = 0;
                int quote = 0;
                while (true) {
                    int b = in.getByte();
                    if (b != 0) {
                        if (b < 0) {
                            break;
                        }
                        int pos2 = pos + 1;
                        buffer[pos] = (char) (b & 255);
                        if (quote == 0) {
                            if (b == 62) {
                                pos = pos2;
                                break;
                            } else if (b == 39 || b == 34) {
                                quote = b;
                            }
                        } else if (b == quote) {
                            quote = 0;
                        }
                        pos = pos2;
                    }
                }
                in.pos = 0;
                in.limit = pos;
            } else {
                in.setCharset("UTF-8");
            }
        }
        in.setKeepFullLines(false);
        return in;
    }

    public static void parse(InputStream strm, Object uri, SourceMessages messages, Consumer out) throws IOException {
        LineInputStreamReader in = XMLStreamReader(strm);
        if (uri != null) {
            in.setName(uri);
        }
        parse((LineBufferedReader) in, messages, out);
        in.close();
    }

    public static void parse(LineBufferedReader in, SourceMessages messages, Consumer out) throws IOException {
        XMLFilter filter = new XMLFilter(out);
        filter.setMessages(messages);
        filter.setSourceLocator(in);
        filter.startDocument();
        Object uri = in.getPath();
        if (uri != null) {
            filter.writeDocumentUri(uri);
        }
        parse(in, filter);
        filter.endDocument();
    }

    public static void parse(LineBufferedReader in, SourceMessages messages, XMLFilter filter) throws IOException {
        filter.setMessages(messages);
        filter.setSourceLocator(in);
        filter.startDocument();
        Object uri = in.getPath();
        if (uri != null) {
            filter.writeDocumentUri(uri);
        }
        parse(in, filter);
        filter.endDocument();
        in.close();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0045, code lost:
        r6 = r2 + 1;
        r7 = r9[r2];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0049, code lost:
        if (r7 != '>') goto L_0x0052;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x01f6, code lost:
        if (r2 != '0') goto L_0x0202;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x004b, code lost:
        r2 = 1;
        r22 = r14;
        r14 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0052, code lost:
        r2 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:246:0x03b7, code lost:
        if (r9[r20 + 2] == 's') goto L_0x03ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00a4, code lost:
        if (r7 != ';') goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a6, code lost:
        r1.pos = r2;
        r8.emitCharacterReference(r13, r9, r15, (r2 - 1) - r15);
        r22 = r14;
        r14 = r2;
        r2 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b7, code lost:
        if (r7 != 'x') goto L_0x00be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00b9, code lost:
        if (r3 != 0) goto L_0x00be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00bb, code lost:
        r3 = 16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00c0, code lost:
        if (r13 < 134217728) goto L_0x00c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00c3, code lost:
        if (r3 != 0) goto L_0x00c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00c5, code lost:
        r5 = 10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00c8, code lost:
        r5 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00c9, code lost:
        r16 = java.lang.Character.digit(r7, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:505:0x07d5, code lost:
        if (r13 != 0) goto L_0x07f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:507:0x07d9, code lost:
        if (r0 != 8) goto L_0x07df;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:508:0x07db, code lost:
        r4 = "missing or invalid attribute name";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00cd, code lost:
        if (r16 >= 0) goto L_0x00de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:510:0x07e0, code lost:
        if (r0 == 2) goto L_0x07ea;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:512:0x07e3, code lost:
        if (r0 != 4) goto L_0x07e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:513:0x07e6, code lost:
        r4 = "missing or invalid name";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:514:0x07ea, code lost:
        r4 = "missing or invalid element name";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:515:0x07ed, code lost:
        r14 = r4;
        r0 = 36;
        r12 = r23;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:516:0x07f3, code lost:
        r14 = r22;
        r12 = r23;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00d0, code lost:
        r1.pos = r2;
        r8.error('e', "invalid character reference");
        r22 = r14;
        r14 = r2;
        r2 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00de, code lost:
        r13 = (r13 * r5) + r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00e4, code lost:
        if (r2 >= r10) goto L_0x00ec;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:559:0x08ab, code lost:
        r5 = r2 - r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00ec, code lost:
        r22 = r14;
        r14 = r2;
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:560:0x08ad, code lost:
        if (r5 <= 0) goto L_0x08b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:561:0x08af, code lost:
        r1.pos = r2;
        r8.textFromParser(r9, r4, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:562:0x08b4, code lost:
        r14 = r2;
        r15 = r9.length;
        r13 = r5;
        r12 = r23;
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:565:0x08cd, code lost:
        r14 = r2;
        r12 = r23;
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x003c, code lost:
        r1.pos = r2;
        r8.error('e', r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0041, code lost:
        if (r2 < r10) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0044, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:194:0x02e9  */
    /* JADX WARNING: Removed duplicated region for block: B:262:0x03f9  */
    /* JADX WARNING: Removed duplicated region for block: B:265:0x0400  */
    /* JADX WARNING: Removed duplicated region for block: B:266:0x0408  */
    /* JADX WARNING: Removed duplicated region for block: B:367:0x05f5 A[LOOP:17: B:341:0x0588->B:367:0x05f5, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:520:0x07fe A[LOOP:18: B:441:0x074d->B:520:0x07fe, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:567:0x08d3  */
    /* JADX WARNING: Removed duplicated region for block: B:568:0x08e0  */
    /* JADX WARNING: Removed duplicated region for block: B:673:0x05fd A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:679:0x0805 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void parse(gnu.text.LineBufferedReader r25, gnu.xml.XMLFilter r26) {
        /*
            r1 = r25
            r8 = r26
            char[] r0 = r1.buffer
            int r2 = r1.pos
            int r3 = r1.limit
            r4 = 0
            r5 = 60
            r6 = 14
            r7 = 32
            r9 = 0
            r10 = -1
            r11 = 0
            r12 = r3
            r13 = r9
            r14 = r11
            r15 = r12
            r9 = r0
            r0 = r4
            r11 = r5
            r12 = r6
            r24 = r10
            r10 = r3
            r3 = r24
        L_0x0021:
            r6 = 101(0x65, float:1.42E-43)
            r5 = 62
            r4 = 1
            switch(r0) {
                case 0: goto L_0x08c3;
                case 1: goto L_0x081c;
                case 2: goto L_0x080c;
                case 3: goto L_0x0747;
                case 4: goto L_0x0739;
                case 5: goto L_0x0747;
                case 6: goto L_0x071c;
                case 7: goto L_0x0747;
                case 8: goto L_0x06df;
                case 9: goto L_0x0747;
                case 10: goto L_0x06ad;
                case 11: goto L_0x0674;
                case 12: goto L_0x0647;
                case 13: goto L_0x063d;
                case 14: goto L_0x0608;
                case 15: goto L_0x0647;
                case 16: goto L_0x0577;
                case 17: goto L_0x0747;
                case 18: goto L_0x0029;
                case 19: goto L_0x0570;
                case 20: goto L_0x0499;
                case 21: goto L_0x0108;
                case 22: goto L_0x0029;
                case 23: goto L_0x0647;
                case 24: goto L_0x0747;
                case 25: goto L_0x00f2;
                case 26: goto L_0x00a2;
                case 27: goto L_0x0094;
                case 28: goto L_0x0073;
                case 29: goto L_0x0647;
                case 30: goto L_0x0108;
                case 31: goto L_0x0065;
                case 32: goto L_0x0647;
                case 33: goto L_0x0747;
                case 34: goto L_0x0054;
                case 35: goto L_0x0039;
                case 36: goto L_0x003c;
                case 37: goto L_0x002f;
                default: goto L_0x0029;
            }
        L_0x0029:
            r23 = r12
            r22 = r14
            goto L_0x08cd
        L_0x002f:
            r1.pos = r2
            r4 = 102(0x66, float:1.43E-43)
            java.lang.String r5 = "unexpected end-of-file"
            r8.error(r4, r5)
            return
        L_0x0039:
            r2 = r3
            java.lang.String r14 = "invalid xml version specifier"
        L_0x003c:
            r1.pos = r2
            r8.error(r6, r14)
        L_0x0041:
            if (r2 < r10) goto L_0x0045
            return
        L_0x0045:
            int r6 = r2 + 1
            char r7 = r9[r2]
            if (r7 != r5) goto L_0x0052
            r0 = 1
            r2 = r0
            r22 = r14
            r14 = r6
            goto L_0x08d1
        L_0x0052:
            r2 = r6
            goto L_0x0041
        L_0x0054:
            r5 = 63
            if (r7 != r5) goto L_0x0062
            r5 = r2
            r0 = 33
            r15 = r5
            r22 = r14
            r14 = r2
            r2 = r0
            goto L_0x08d1
        L_0x0062:
            r0 = 14
            goto L_0x0021
        L_0x0065:
            r5 = 60
            if (r7 != r5) goto L_0x0071
            r0 = 34
            r22 = r14
            r14 = r2
            r2 = r0
            goto L_0x08d1
        L_0x0071:
            r0 = 1
            goto L_0x0021
        L_0x0073:
            r0 = 1
            r5 = 10
            if (r7 != r5) goto L_0x007a
            r5 = 1
            goto L_0x007b
        L_0x007a:
            r5 = 0
        L_0x007b:
            r6 = 133(0x85, float:1.86E-43)
            if (r7 != r6) goto L_0x0081
            r6 = 1
            goto L_0x0082
        L_0x0081:
            r6 = 0
        L_0x0082:
            r5 = r5 | r6
            if (r5 == 0) goto L_0x008e
            r1.incrLineNumber(r4, r2)
            r22 = r14
            r14 = r2
            r2 = r0
            goto L_0x08d1
        L_0x008e:
            int r5 = r2 + -1
            r1.incrLineNumber(r4, r5)
            goto L_0x0021
        L_0x0094:
            if (r7 == r5) goto L_0x009b
            java.lang.String r14 = "missing '>'"
            r0 = 36
            goto L_0x0021
        L_0x009b:
            r0 = 1
            r22 = r14
            r14 = r2
            r2 = r0
            goto L_0x08d1
        L_0x00a2:
            r5 = 59
            if (r7 != r5) goto L_0x00b5
            r1.pos = r2
            int r5 = r2 + -1
            int r5 = r5 - r15
            r8.emitCharacterReference(r13, r9, r15, r5)
            r0 = 1
            r22 = r14
            r14 = r2
            r2 = r0
            goto L_0x08d1
        L_0x00b5:
            r5 = 120(0x78, float:1.68E-43)
            if (r7 != r5) goto L_0x00be
            if (r3 != 0) goto L_0x00be
            r3 = 16
            goto L_0x00e4
        L_0x00be:
            r5 = 134217728(0x8000000, float:3.85186E-34)
            if (r13 < r5) goto L_0x00c3
            goto L_0x00d0
        L_0x00c3:
            if (r3 != 0) goto L_0x00c8
            r5 = 10
            goto L_0x00c9
        L_0x00c8:
            r5 = r3
        L_0x00c9:
            int r16 = java.lang.Character.digit(r7, r5)
            if (r16 >= 0) goto L_0x00de
        L_0x00d0:
            r1.pos = r2
            java.lang.String r5 = "invalid character reference"
            r8.error(r6, r5)
            r0 = 1
            r22 = r14
            r14 = r2
            r2 = r0
            goto L_0x08d1
        L_0x00de:
            int r17 = r13 * r5
            int r17 = r17 + r16
            r13 = r17
        L_0x00e4:
            if (r2 >= r10) goto L_0x00ec
            int r5 = r2 + 1
            char r7 = r9[r2]
            r2 = r5
            goto L_0x00a2
        L_0x00ec:
            r22 = r14
            r14 = r2
            r2 = r0
            goto L_0x08d1
        L_0x00f2:
            r5 = 35
            if (r7 != r5) goto L_0x0103
            r0 = 26
            r5 = r2
            r6 = 0
            r3 = 0
            r15 = r5
            r13 = r6
            r22 = r14
            r14 = r2
            r2 = r0
            goto L_0x08d1
        L_0x0103:
            int r15 = r2 + -1
            r0 = 7
            goto L_0x0021
        L_0x0108:
            if (r3 >= 0) goto L_0x0112
            int r3 = r2 + -1
            r24 = r7
            r7 = r2
            r2 = r24
            goto L_0x0117
        L_0x0112:
            r24 = r7
            r7 = r2
            r2 = r24
        L_0x0117:
            if (r2 != r5) goto L_0x0478
            int r16 = r7 + -2
            r17 = r16
            char r4 = r9[r16]
            r5 = 63
            if (r4 != r5) goto L_0x046c
            r5 = r17
            if (r5 < r3) goto L_0x0460
            r1.pos = r7
            r4 = 3
            if (r13 != r4) goto L_0x043b
            char r4 = r9[r15]
            r6 = 120(0x78, float:1.68E-43)
            if (r4 != r6) goto L_0x043b
            int r4 = r15 + 1
            char r4 = r9[r4]
            r6 = 109(0x6d, float:1.53E-43)
            if (r4 != r6) goto L_0x043b
            int r4 = r15 + 2
            char r4 = r9[r4]
            r6 = 108(0x6c, float:1.51E-43)
            if (r4 != r6) goto L_0x043b
            r4 = 30
            if (r0 != r4) goto L_0x0430
            int r4 = r3 + 7
            if (r5 <= r4) goto L_0x0427
            char r4 = r9[r3]
            r6 = 118(0x76, float:1.65E-43)
            if (r4 != r6) goto L_0x0427
            int r4 = r3 + 1
            char r4 = r9[r4]
            r6 = 101(0x65, float:1.42E-43)
            if (r4 != r6) goto L_0x0427
            int r4 = r3 + 2
            char r4 = r9[r4]
            r6 = 114(0x72, float:1.6E-43)
            if (r4 != r6) goto L_0x0427
            int r4 = r3 + 3
            char r4 = r9[r4]
            r6 = 115(0x73, float:1.61E-43)
            if (r4 != r6) goto L_0x0427
            int r4 = r3 + 4
            char r4 = r9[r4]
            r6 = 105(0x69, float:1.47E-43)
            if (r4 != r6) goto L_0x0427
            int r4 = r3 + 5
            char r4 = r9[r4]
            r6 = 111(0x6f, float:1.56E-43)
            if (r4 != r6) goto L_0x0427
            int r4 = r3 + 6
            char r4 = r9[r4]
            r6 = 110(0x6e, float:1.54E-43)
            if (r4 == r6) goto L_0x0182
            goto L_0x0427
        L_0x0182:
            int r3 = r3 + 7
            char r2 = r9[r3]
        L_0x0186:
            boolean r4 = java.lang.Character.isWhitespace(r2)
            if (r4 == 0) goto L_0x0193
            int r3 = r3 + 1
            if (r3 >= r5) goto L_0x0193
            char r2 = r9[r3]
            goto L_0x0186
        L_0x0193:
            r4 = 61
            if (r2 == r4) goto L_0x01a0
            r0 = 35
            r24 = r7
            r7 = r2
            r2 = r24
            goto L_0x0021
        L_0x01a0:
            int r3 = r3 + 1
            char r2 = r9[r3]
        L_0x01a4:
            boolean r4 = java.lang.Character.isWhitespace(r2)
            if (r4 == 0) goto L_0x01b1
            int r3 = r3 + 1
            if (r3 >= r5) goto L_0x01b1
            char r2 = r9[r3]
            goto L_0x01a4
        L_0x01b1:
            r4 = 39
            if (r2 == r4) goto L_0x01c2
            r4 = 34
            if (r2 == r4) goto L_0x01c2
            r0 = 35
            r24 = r7
            r7 = r2
            r2 = r24
            goto L_0x0021
        L_0x01c2:
            r4 = r2
            int r17 = r3 + 1
            r3 = r17
        L_0x01c7:
            if (r3 != r5) goto L_0x01d4
            r0 = 35
            r3 = r17
            r24 = r7
            r7 = r2
            r2 = r24
            goto L_0x0021
        L_0x01d4:
            char r2 = r9[r3]
            if (r2 != r4) goto L_0x0417
            int r6 = r17 + 3
            if (r3 != r6) goto L_0x01fc
            char r6 = r9[r17]
            r19 = r2
            r2 = 49
            if (r6 != r2) goto L_0x01f9
            int r2 = r17 + 1
            char r2 = r9[r2]
            r6 = 46
            if (r2 != r6) goto L_0x01f9
            int r2 = r17 + 2
            char r2 = r9[r2]
            r6 = r2
            r20 = r4
            r4 = 48
            if (r2 == r4) goto L_0x0206
            goto L_0x0202
        L_0x01f9:
            r20 = r4
            goto L_0x0200
        L_0x01fc:
            r19 = r2
            r20 = r4
        L_0x0200:
            r6 = r19
        L_0x0202:
            r2 = 49
            if (r6 != r2) goto L_0x040f
        L_0x0206:
            int r2 = r3 + 1
        L_0x0208:
            if (r2 >= r5) goto L_0x0215
            char r4 = r9[r2]
            boolean r4 = java.lang.Character.isWhitespace(r4)
            if (r4 == 0) goto L_0x0215
            int r2 = r2 + 1
            goto L_0x0208
        L_0x0215:
            int r4 = r2 + 7
            if (r5 <= r4) goto L_0x02dd
            char r4 = r9[r2]
            r19 = r6
            r6 = 101(0x65, float:1.42E-43)
            if (r4 != r6) goto L_0x02df
            int r4 = r2 + 1
            char r4 = r9[r4]
            r6 = 110(0x6e, float:1.54E-43)
            if (r4 != r6) goto L_0x02df
            int r4 = r2 + 2
            char r4 = r9[r4]
            r6 = 99
            if (r4 != r6) goto L_0x02df
            int r4 = r2 + 3
            char r4 = r9[r4]
            r6 = 111(0x6f, float:1.56E-43)
            if (r4 != r6) goto L_0x02df
            int r4 = r2 + 4
            char r4 = r9[r4]
            r6 = 100
            if (r4 != r6) goto L_0x02df
            int r4 = r2 + 5
            char r4 = r9[r4]
            r6 = 105(0x69, float:1.47E-43)
            if (r4 != r6) goto L_0x02df
            int r4 = r2 + 6
            char r4 = r9[r4]
            r6 = 110(0x6e, float:1.54E-43)
            if (r4 != r6) goto L_0x02df
            int r4 = r2 + 7
            char r4 = r9[r4]
            r6 = 103(0x67, float:1.44E-43)
            if (r4 != r6) goto L_0x02df
            int r2 = r2 + 8
            char r4 = r9[r2]
        L_0x025d:
            boolean r6 = java.lang.Character.isWhitespace(r4)
            if (r6 == 0) goto L_0x026a
            int r2 = r2 + 1
            if (r2 >= r5) goto L_0x026a
            char r4 = r9[r2]
            goto L_0x025d
        L_0x026a:
            r6 = 61
            if (r4 == r6) goto L_0x0277
            java.lang.String r14 = "bad 'encoding' declaration"
            r0 = 36
            r3 = r2
            r2 = r7
            r7 = r4
            goto L_0x0021
        L_0x0277:
            int r2 = r2 + 1
            char r4 = r9[r2]
        L_0x027b:
            boolean r6 = java.lang.Character.isWhitespace(r4)
            if (r6 == 0) goto L_0x0288
            int r2 = r2 + 1
            if (r2 >= r5) goto L_0x0288
            char r4 = r9[r2]
            goto L_0x027b
        L_0x0288:
            r6 = 39
            if (r4 == r6) goto L_0x0299
            r6 = 34
            if (r4 == r6) goto L_0x0299
            java.lang.String r14 = "bad 'encoding' declaration"
            r0 = 36
            r3 = r2
            r2 = r7
            r7 = r4
            goto L_0x0021
        L_0x0299:
            r6 = r4
            int r2 = r2 + 1
            r3 = r2
        L_0x029d:
            if (r3 != r5) goto L_0x02a8
            java.lang.String r14 = "bad 'encoding' declaration"
            r0 = 36
            r3 = r2
            r2 = r7
            r7 = r4
            goto L_0x0021
        L_0x02a8:
            char r4 = r9[r3]
            if (r4 != r6) goto L_0x02d6
            r17 = r4
            java.lang.String r4 = new java.lang.String
            r21 = r6
            int r6 = r3 - r2
            r4.<init>(r9, r2, r6)
            boolean r6 = r1 instanceof gnu.text.LineInputStreamReader
            if (r6 == 0) goto L_0x02c2
            r6 = r1
            gnu.text.LineInputStreamReader r6 = (gnu.text.LineInputStreamReader) r6
            r6.setCharset((java.lang.String) r4)
        L_0x02c2:
            int r2 = r3 + 1
        L_0x02c4:
            if (r2 >= r5) goto L_0x02d1
            char r6 = r9[r2]
            boolean r6 = java.lang.Character.isWhitespace(r6)
            if (r6 == 0) goto L_0x02d1
            int r2 = r2 + 1
            goto L_0x02c4
        L_0x02d1:
            r6 = r17
            r4 = r21
            goto L_0x02e3
        L_0x02d6:
            r17 = r4
            r21 = r6
            int r3 = r3 + 1
            goto L_0x029d
        L_0x02dd:
            r19 = r6
        L_0x02df:
            r6 = r19
            r4 = r20
        L_0x02e3:
            r17 = r3
            int r3 = r2 + 9
            if (r5 <= r3) goto L_0x03f9
            char r3 = r9[r2]
            r19 = r4
            r4 = 115(0x73, float:1.61E-43)
            if (r3 != r4) goto L_0x03fb
            int r3 = r2 + 1
            char r3 = r9[r3]
            r4 = 116(0x74, float:1.63E-43)
            if (r3 != r4) goto L_0x03fb
            int r3 = r2 + 2
            char r3 = r9[r3]
            r4 = 97
            if (r3 != r4) goto L_0x03fb
            int r3 = r2 + 3
            char r3 = r9[r3]
            r4 = 110(0x6e, float:1.54E-43)
            if (r3 != r4) goto L_0x03fb
            int r3 = r2 + 4
            char r3 = r9[r3]
            r4 = 100
            if (r3 != r4) goto L_0x03fb
            int r3 = r2 + 5
            char r3 = r9[r3]
            r4 = 97
            if (r3 != r4) goto L_0x03fb
            int r3 = r2 + 6
            char r3 = r9[r3]
            r4 = 108(0x6c, float:1.51E-43)
            if (r3 != r4) goto L_0x03fb
            int r3 = r2 + 7
            char r3 = r9[r3]
            r4 = 111(0x6f, float:1.56E-43)
            if (r3 != r4) goto L_0x03fb
            int r3 = r2 + 8
            char r3 = r9[r3]
            r4 = 110(0x6e, float:1.54E-43)
            if (r3 != r4) goto L_0x03fb
            int r3 = r2 + 9
            char r3 = r9[r3]
            r4 = 101(0x65, float:1.42E-43)
            if (r3 != r4) goto L_0x03fb
            int r2 = r2 + 10
            char r3 = r9[r2]
        L_0x033d:
            boolean r4 = java.lang.Character.isWhitespace(r3)
            if (r4 == 0) goto L_0x034a
            int r2 = r2 + 1
            if (r2 >= r5) goto L_0x034a
            char r3 = r9[r2]
            goto L_0x033d
        L_0x034a:
            r4 = 61
            if (r3 == r4) goto L_0x035a
            java.lang.String r14 = "bad 'standalone' declaration"
            r0 = 36
            r24 = r3
            r3 = r2
            r2 = r7
            r7 = r24
            goto L_0x0021
        L_0x035a:
            int r2 = r2 + 1
            char r3 = r9[r2]
        L_0x035e:
            boolean r4 = java.lang.Character.isWhitespace(r3)
            if (r4 == 0) goto L_0x036b
            int r2 = r2 + 1
            if (r2 >= r5) goto L_0x036b
            char r3 = r9[r2]
            goto L_0x035e
        L_0x036b:
            r4 = 39
            if (r3 == r4) goto L_0x037f
            r4 = 34
            if (r3 == r4) goto L_0x037f
            java.lang.String r14 = "bad 'standalone' declaration"
            r0 = 36
            r24 = r3
            r3 = r2
            r2 = r7
            r7 = r24
            goto L_0x0021
        L_0x037f:
            r4 = r3
            int r20 = r2 + 1
            r2 = r20
            r24 = r3
            r3 = r2
            r2 = r24
        L_0x0389:
            if (r3 != r5) goto L_0x0398
            java.lang.String r14 = "bad 'standalone' declaration"
            r0 = 36
            r3 = r20
            r24 = r7
            r7 = r2
            r2 = r24
            goto L_0x0021
        L_0x0398:
            char r2 = r9[r3]
            if (r2 != r4) goto L_0x03ee
            int r6 = r20 + 3
            if (r3 != r6) goto L_0x03ba
            char r6 = r9[r20]
            r17 = r2
            r2 = 121(0x79, float:1.7E-43)
            if (r6 != r2) goto L_0x03bc
            int r2 = r20 + 1
            char r2 = r9[r2]
            r6 = 101(0x65, float:1.42E-43)
            if (r2 != r6) goto L_0x03bc
            int r2 = r20 + 2
            char r2 = r9[r2]
            r6 = 115(0x73, float:1.61E-43)
            if (r2 != r6) goto L_0x03bc
            goto L_0x03ce
        L_0x03ba:
            r17 = r2
        L_0x03bc:
            int r2 = r20 + 2
            if (r3 != r2) goto L_0x03e3
            char r2 = r9[r20]
            r6 = 110(0x6e, float:1.54E-43)
            if (r2 != r6) goto L_0x03e3
            int r2 = r20 + 1
            char r2 = r9[r2]
            r6 = 111(0x6f, float:1.56E-43)
            if (r2 != r6) goto L_0x03e3
        L_0x03ce:
            int r2 = r3 + 1
        L_0x03d0:
            if (r2 >= r5) goto L_0x03dd
            char r6 = r9[r2]
            boolean r6 = java.lang.Character.isWhitespace(r6)
            if (r6 == 0) goto L_0x03dd
            int r2 = r2 + 1
            goto L_0x03d0
        L_0x03dd:
            r6 = r17
            r17 = r3
            r3 = r2
            goto L_0x03fe
        L_0x03e3:
            java.lang.String r14 = "bad 'standalone' declaration"
            r0 = 36
            r2 = r7
            r7 = r17
            r3 = r20
            goto L_0x0021
        L_0x03ee:
            r17 = r2
            r6 = 101(0x65, float:1.42E-43)
            r16 = 111(0x6f, float:1.56E-43)
            r18 = 110(0x6e, float:1.54E-43)
            int r3 = r3 + 1
            goto L_0x0389
        L_0x03f9:
            r19 = r4
        L_0x03fb:
            r3 = r2
            r4 = r19
        L_0x03fe:
            if (r5 == r3) goto L_0x0408
            java.lang.String r14 = "junk at end of xml declaration"
            r2 = r3
            r0 = 36
            r7 = r6
            goto L_0x0021
        L_0x0408:
            r19 = r5
            r2 = r6
            r22 = r14
            r14 = r7
            goto L_0x0458
        L_0x040f:
            r0 = 35
            r2 = r7
            r3 = r17
            r7 = r6
            goto L_0x0021
        L_0x0417:
            r19 = r2
            r20 = r4
            r16 = 111(0x6f, float:1.56E-43)
            r18 = 110(0x6e, float:1.54E-43)
            r21 = 101(0x65, float:1.42E-43)
            int r3 = r3 + 1
            r6 = 110(0x6e, float:1.54E-43)
            goto L_0x01c7
        L_0x0427:
            r4 = r3
            java.lang.String r14 = "xml declaration without version"
            r0 = 36
            r7 = r2
            r2 = r4
            goto L_0x0021
        L_0x0430:
            java.lang.String r14 = "<?xml must be at start of file"
            r0 = 36
            r24 = r7
            r7 = r2
            r2 = r24
            goto L_0x0021
        L_0x043b:
            int r16 = r5 - r3
            r17 = r2
            r2 = r26
            r18 = r3
            r3 = r9
            r6 = 1
            r4 = r15
            r19 = r5
            r5 = r13
            r22 = r14
            r14 = 1
            r6 = r18
            r14 = r7
            r7 = r16
            r2.processingInstructionFromParser(r3, r4, r5, r6, r7)
            r2 = r17
            r3 = r18
        L_0x0458:
            r4 = r10
            r3 = -1
            r0 = 1
            r7 = r2
            r15 = r4
            r2 = r0
            goto L_0x08d1
        L_0x0460:
            r17 = r2
            r18 = r3
            r19 = r5
            r22 = r14
            r21 = 101(0x65, float:1.42E-43)
            r14 = r7
            goto L_0x0481
        L_0x046c:
            r18 = r3
            r22 = r14
            r19 = r17
            r21 = 101(0x65, float:1.42E-43)
            r17 = r2
            r14 = r7
            goto L_0x0481
        L_0x0478:
            r17 = r2
            r18 = r3
            r22 = r14
            r21 = 101(0x65, float:1.42E-43)
            r14 = r7
        L_0x0481:
            if (r14 >= r10) goto L_0x0492
            int r7 = r14 + 1
            char r2 = r9[r14]
            r3 = r18
            r14 = r22
            r4 = 1
            r5 = 62
            r6 = 101(0x65, float:1.42E-43)
            goto L_0x0117
        L_0x0492:
            r2 = r0
            r7 = r17
            r3 = r18
            goto L_0x08d1
        L_0x0499:
            r22 = r14
        L_0x049b:
            r4 = 62
            if (r7 != r4) goto L_0x0525
            int r4 = r2 + -1
            int r4 = r4 - r15
            r5 = 4
            if (r4 < r5) goto L_0x04c7
            char r5 = r9[r15]
            r6 = 45
            if (r5 != r6) goto L_0x04c7
            int r5 = r15 + 1
            char r5 = r9[r5]
            if (r5 != r6) goto L_0x04c7
            int r5 = r2 + -2
            char r5 = r9[r5]
            if (r5 != r6) goto L_0x051b
            int r5 = r2 + -3
            char r5 = r9[r5]
            if (r5 != r6) goto L_0x051b
            r1.pos = r2
            int r5 = r15 + 2
            int r6 = r4 + -4
            r8.commentFromParser(r9, r5, r6)
            goto L_0x051d
        L_0x04c7:
            r5 = 6
            if (r4 < r5) goto L_0x051d
            char r5 = r9[r15]
            r6 = 91
            if (r5 != r6) goto L_0x051d
            int r5 = r15 + 1
            char r5 = r9[r5]
            r6 = 67
            if (r5 != r6) goto L_0x051d
            int r5 = r15 + 2
            char r5 = r9[r5]
            r6 = 68
            if (r5 != r6) goto L_0x051d
            int r5 = r15 + 3
            char r5 = r9[r5]
            r6 = 65
            if (r5 != r6) goto L_0x051d
            int r5 = r15 + 4
            char r5 = r9[r5]
            r6 = 84
            if (r5 != r6) goto L_0x051d
            int r5 = r15 + 5
            char r5 = r9[r5]
            r6 = 65
            if (r5 != r6) goto L_0x051d
            int r5 = r15 + 6
            char r5 = r9[r5]
            r6 = 91
            if (r5 != r6) goto L_0x051d
            int r5 = r2 + -2
            char r5 = r9[r5]
            r6 = 93
            if (r5 != r6) goto L_0x051b
            int r5 = r2 + -3
            char r5 = r9[r5]
            r6 = 93
            if (r5 != r6) goto L_0x051b
            r1.pos = r2
            int r5 = r15 + 7
            int r6 = r2 + -10
            int r6 = r6 - r15
            r8.writeCDATA(r9, r5, r6)
            goto L_0x051d
        L_0x051b:
            r13 = r4
            goto L_0x0563
        L_0x051d:
            r5 = r10
            r0 = 1
            r14 = r2
            r13 = r4
            r15 = r5
            r2 = r0
            goto L_0x08d1
        L_0x0525:
            int r4 = r15 + 7
            if (r2 != r4) goto L_0x0563
            char r4 = r9[r15]
            r5 = 68
            if (r4 != r5) goto L_0x0563
            int r4 = r15 + 1
            char r4 = r9[r4]
            r5 = 79
            if (r4 != r5) goto L_0x0563
            int r4 = r15 + 2
            char r4 = r9[r4]
            r5 = 67
            if (r4 != r5) goto L_0x0563
            int r4 = r15 + 3
            char r4 = r9[r4]
            r5 = 84
            if (r4 != r5) goto L_0x0563
            int r4 = r15 + 4
            char r4 = r9[r4]
            r5 = 89
            if (r4 != r5) goto L_0x0563
            int r4 = r15 + 5
            char r4 = r9[r4]
            r5 = 80
            if (r4 != r5) goto L_0x0563
            r4 = 69
            if (r7 != r4) goto L_0x0563
            r4 = r10
            r0 = 15
            r14 = r2
            r15 = r4
            r2 = r0
            goto L_0x08d1
        L_0x0563:
            if (r2 >= r10) goto L_0x056c
            int r4 = r2 + 1
            char r7 = r9[r2]
            r2 = r4
            goto L_0x049b
        L_0x056c:
            r14 = r2
            r2 = r0
            goto L_0x08d1
        L_0x0570:
            r22 = r14
            int r15 = r2 + -1
            r0 = 5
            goto L_0x0021
        L_0x0577:
            r22 = r14
            if (r3 >= 0) goto L_0x0585
            int r3 = r2 + -1
            int r3 = r3 - r15
            r4 = 1
            int r3 = r3 << r4
            r11 = 0
            r14 = r7
            r7 = r11
            r11 = r2
            goto L_0x0588
        L_0x0585:
            r14 = r7
            r7 = r11
            r11 = r2
        L_0x0588:
            r2 = 39
            if (r14 == r2) goto L_0x05e5
            r2 = 34
            if (r14 != r2) goto L_0x0595
            r23 = r12
            r12 = r7
            goto L_0x05e8
        L_0x0595:
            if (r7 != 0) goto L_0x05e1
            r2 = 91
            if (r14 != r2) goto L_0x05a2
            r2 = r3 | 1
            r3 = r2
            r23 = r12
            goto L_0x05f3
        L_0x05a2:
            r2 = 93
            if (r14 != r2) goto L_0x05ad
            r2 = r3 & -2
            r3 = r2
            r23 = r12
            goto L_0x05f3
        L_0x05ad:
            r2 = 62
            if (r14 != r2) goto L_0x05dd
            r2 = r3 & 1
            if (r2 != 0) goto L_0x05dd
            r1.pos = r11
            int r2 = r3 >> 1
            int r16 = r2 + r15
            int r2 = r11 + -1
            int r17 = r2 - r16
            r2 = r26
            r3 = r9
            r4 = r15
            r5 = r13
            r6 = r16
            r23 = r12
            r12 = r7
            r7 = r17
            r2.emitDoctypeDecl(r3, r4, r5, r6, r7)
            r2 = 60
            r3 = r10
            r4 = -1
            r0 = 1
            r15 = r3
            r3 = r4
            r7 = r14
            r12 = r23
            r14 = r11
            r11 = r2
            r2 = r0
            goto L_0x08d1
        L_0x05dd:
            r23 = r12
            r12 = r7
            goto L_0x05f2
        L_0x05e1:
            r23 = r12
            r12 = r7
            goto L_0x05f2
        L_0x05e5:
            r23 = r12
            r12 = r7
        L_0x05e8:
            if (r12 != 0) goto L_0x05ed
            r2 = r14
            r7 = r2
            goto L_0x05f3
        L_0x05ed:
            if (r12 != r14) goto L_0x05f2
            r2 = 0
            r7 = r2
            goto L_0x05f3
        L_0x05f2:
            r7 = r12
        L_0x05f3:
            if (r11 >= r10) goto L_0x05fd
            int r2 = r11 + 1
            char r14 = r9[r11]
            r11 = r2
            r12 = r23
            goto L_0x0588
        L_0x05fd:
            r2 = r0
            r12 = r23
            r24 = r11
            r11 = r7
            r7 = r14
            r14 = r24
            goto L_0x08d1
        L_0x0608:
            r23 = r12
            r22 = r14
            r4 = 47
            if (r7 != r4) goto L_0x0618
            r0 = 19
            r14 = r2
            r12 = r23
            r2 = r0
            goto L_0x08d1
        L_0x0618:
            r4 = 63
            if (r7 != r4) goto L_0x0626
            r4 = r2
            r0 = 24
            r14 = r2
            r15 = r4
            r12 = r23
            r2 = r0
            goto L_0x08d1
        L_0x0626:
            r4 = 33
            if (r7 != r4) goto L_0x0634
            r0 = 20
            r4 = r2
            r14 = r2
            r15 = r4
            r12 = r23
            r2 = r0
            goto L_0x08d1
        L_0x0634:
            int r15 = r2 + -1
            r0 = 3
            r14 = r22
            r12 = r23
            goto L_0x0021
        L_0x063d:
            r23 = r12
            r22 = r14
            r0 = 17
            int r15 = r2 + -1
            goto L_0x0021
        L_0x0647:
            r23 = r12
            r22 = r14
            r4 = 32
            if (r7 == r4) goto L_0x08cd
            r4 = 9
            if (r7 != r4) goto L_0x0655
            goto L_0x08cd
        L_0x0655:
            r4 = 10
            if (r7 == r4) goto L_0x066e
            r4 = 13
            if (r7 == r4) goto L_0x066e
            r4 = 133(0x85, float:1.86E-43)
            if (r7 == r4) goto L_0x066e
            r4 = 8232(0x2028, float:1.1535E-41)
            if (r7 != r4) goto L_0x0666
            goto L_0x066e
        L_0x0666:
            int r0 = r0 + -2
            r14 = r22
            r12 = r23
            goto L_0x0021
        L_0x066e:
            r4 = 1
            r1.incrLineNumber(r4, r2)
            goto L_0x08cd
        L_0x0674:
            r23 = r12
            r22 = r14
            r4 = 39
            if (r7 == r4) goto L_0x06a3
            r4 = 34
            if (r7 != r4) goto L_0x0681
            goto L_0x06a3
        L_0x0681:
            r4 = 32
            if (r7 == r4) goto L_0x08cd
            r4 = 9
            if (r7 == r4) goto L_0x08cd
            r4 = 13
            if (r7 == r4) goto L_0x08cd
            r4 = 10
            if (r7 == r4) goto L_0x08cd
            r4 = 133(0x85, float:1.86E-43)
            if (r7 == r4) goto L_0x08cd
            r4 = 8232(0x2028, float:1.1535E-41)
            if (r7 != r4) goto L_0x069b
            goto L_0x08cd
        L_0x069b:
            java.lang.String r14 = "missing or unquoted attribute value"
            r0 = 36
            r12 = r23
            goto L_0x0021
        L_0x06a3:
            r4 = r7
            r5 = 12
            r0 = 1
            r14 = r2
            r11 = r4
            r12 = r5
            r2 = r0
            goto L_0x08d1
        L_0x06ad:
            r23 = r12
            r22 = r14
            r11 = 60
            r12 = 14
            r4 = 47
            if (r7 != r4) goto L_0x06c9
            r1.pos = r2
            r26.emitEndAttributes()
            r4 = 0
            r5 = 0
            r8.emitEndElement(r4, r5, r5)
            r0 = 27
            r14 = r2
            r2 = r0
            goto L_0x08d1
        L_0x06c9:
            r4 = 62
            if (r7 != r4) goto L_0x06d7
            r1.pos = r2
            r26.emitEndAttributes()
            r0 = 1
            r14 = r2
            r2 = r0
            goto L_0x08d1
        L_0x06d7:
            int r15 = r2 + -1
            r0 = 9
            r14 = r22
            goto L_0x0021
        L_0x06df:
            r23 = r12
            r22 = r14
            r4 = 32
            if (r7 == r4) goto L_0x08cd
            r4 = 9
            if (r7 == r4) goto L_0x08cd
            r4 = 13
            if (r7 == r4) goto L_0x08cd
            r4 = 10
            if (r7 == r4) goto L_0x08cd
            r4 = 133(0x85, float:1.86E-43)
            if (r7 == r4) goto L_0x08cd
            r4 = 8232(0x2028, float:1.1535E-41)
            if (r7 != r4) goto L_0x06fd
            goto L_0x08cd
        L_0x06fd:
            int r4 = r2 - r13
            r1.pos = r4
            r8.emitStartAttribute(r9, r15, r13)
            r15 = r10
            r4 = 61
            if (r7 != r4) goto L_0x0711
            r0 = 11
            r14 = r2
            r12 = r23
            r2 = r0
            goto L_0x08d1
        L_0x0711:
            r26.emitEndAttributes()
            java.lang.String r14 = "missing or misplaced '=' after attribute name"
            r0 = 36
            r12 = r23
            goto L_0x0021
        L_0x071c:
            r23 = r12
            r22 = r14
            r1.pos = r2
            r4 = 59
            if (r7 == r4) goto L_0x072d
            r4 = 119(0x77, float:1.67E-43)
            java.lang.String r5 = "missing ';'"
            r8.error(r4, r5)
        L_0x072d:
            r8.emitEntityReference(r9, r15, r13)
            r4 = r10
            r0 = 1
            r14 = r2
            r15 = r4
            r12 = r23
            r2 = r0
            goto L_0x08d1
        L_0x0739:
            r23 = r12
            r22 = r14
            r1.pos = r2
            r8.emitEndElement(r9, r15, r13)
            r15 = r10
            r0 = 29
            goto L_0x0021
        L_0x0747:
            r23 = r12
            r22 = r14
            int r4 = r15 + 1
        L_0x074d:
            r5 = 97
            if (r7 < r5) goto L_0x075b
            r5 = 122(0x7a, float:1.71E-43)
            if (r7 <= r5) goto L_0x0756
            goto L_0x075b
        L_0x0756:
            r5 = 45
        L_0x0758:
            r6 = 4
            goto L_0x07fc
        L_0x075b:
            r5 = 65
            if (r7 < r5) goto L_0x0763
            r5 = 90
            if (r7 <= r5) goto L_0x0756
        L_0x0763:
            r5 = 95
            if (r7 == r5) goto L_0x07f9
            r5 = 58
            if (r7 == r5) goto L_0x07f9
            r5 = 192(0xc0, float:2.69E-43)
            if (r7 < r5) goto L_0x07aa
            r5 = 767(0x2ff, float:1.075E-42)
            if (r7 <= r5) goto L_0x0756
            r5 = 880(0x370, float:1.233E-42)
            if (r7 < r5) goto L_0x07aa
            r5 = 8191(0x1fff, float:1.1478E-41)
            if (r7 > r5) goto L_0x077f
            r5 = 894(0x37e, float:1.253E-42)
            if (r7 != r5) goto L_0x0756
        L_0x077f:
            r5 = 8204(0x200c, float:1.1496E-41)
            if (r7 < r5) goto L_0x07aa
            r5 = 8205(0x200d, float:1.1498E-41)
            if (r7 <= r5) goto L_0x0756
            r5 = 8304(0x2070, float:1.1636E-41)
            if (r7 < r5) goto L_0x078f
            r5 = 8591(0x218f, float:1.2039E-41)
            if (r7 <= r5) goto L_0x0756
        L_0x078f:
            r5 = 11264(0x2c00, float:1.5784E-41)
            if (r7 < r5) goto L_0x0797
            r5 = 12271(0x2fef, float:1.7195E-41)
            if (r7 <= r5) goto L_0x0756
        L_0x0797:
            r5 = 12289(0x3001, float:1.722E-41)
            if (r7 < r5) goto L_0x07a0
            r5 = 55295(0xd7ff, float:7.7485E-41)
            if (r7 <= r5) goto L_0x0756
        L_0x07a0:
            r5 = 63744(0xf900, float:8.9324E-41)
            if (r7 < r5) goto L_0x07aa
            r5 = 65533(0xfffd, float:9.1831E-41)
            if (r7 <= r5) goto L_0x0756
        L_0x07aa:
            if (r2 <= r4) goto L_0x07b4
            r5 = 48
            if (r7 < r5) goto L_0x07b4
            r5 = 57
            if (r7 <= r5) goto L_0x0756
        L_0x07b4:
            r5 = 46
            if (r7 == r5) goto L_0x07f9
            r5 = 45
            if (r7 == r5) goto L_0x07fb
            r6 = 183(0xb7, float:2.56E-43)
            if (r7 == r6) goto L_0x07fb
            r6 = 768(0x300, float:1.076E-42)
            if (r7 <= r6) goto L_0x07d1
            r6 = 879(0x36f, float:1.232E-42)
            if (r7 <= r6) goto L_0x07d0
            r6 = 8255(0x203f, float:1.1568E-41)
            if (r7 < r6) goto L_0x07d1
            r6 = 8256(0x2040, float:1.1569E-41)
            if (r7 > r6) goto L_0x07d1
        L_0x07d0:
            goto L_0x0758
        L_0x07d1:
            int r0 = r0 + -1
            int r13 = r2 - r4
            if (r13 != 0) goto L_0x07f3
            r4 = 8
            if (r0 != r4) goto L_0x07df
            java.lang.String r4 = "missing or invalid attribute name"
            r14 = r4
            goto L_0x07ed
        L_0x07df:
            r4 = 2
            if (r0 == r4) goto L_0x07ea
            r6 = 4
            if (r0 != r6) goto L_0x07e6
            goto L_0x07ea
        L_0x07e6:
            java.lang.String r4 = "missing or invalid name"
            r14 = r4
            goto L_0x07ed
        L_0x07ea:
            java.lang.String r4 = "missing or invalid element name"
            r14 = r4
        L_0x07ed:
            r0 = 36
            r12 = r23
            goto L_0x0021
        L_0x07f3:
            r14 = r22
            r12 = r23
            goto L_0x0021
        L_0x07f9:
            r5 = 45
        L_0x07fb:
            r6 = 4
        L_0x07fc:
            if (r2 >= r10) goto L_0x0805
            int r12 = r2 + 1
            char r7 = r9[r2]
            r2 = r12
            goto L_0x074d
        L_0x0805:
            r14 = r2
            r13 = r4
            r12 = r23
            r2 = r0
            goto L_0x08d1
        L_0x080c:
            r23 = r12
            r22 = r14
            int r4 = r2 - r13
            r1.pos = r4
            r8.emitStartElement(r9, r15, r13)
            r0 = 12
            r15 = r10
            goto L_0x0021
        L_0x081c:
            r23 = r12
            r22 = r14
            int r4 = r2 + -1
            r5 = r2
        L_0x0823:
            if (r7 != r11) goto L_0x0829
            r0 = r23
            goto L_0x08ab
        L_0x0829:
            r6 = 38
            if (r7 != r6) goto L_0x0831
            r0 = 25
            goto L_0x08ab
        L_0x0831:
            r6 = 13
            if (r7 != r6) goto L_0x0879
            int r5 = r2 - r5
            r1.pos = r2
            if (r5 <= 0) goto L_0x083e
            r8.textFromParser(r9, r4, r5)
        L_0x083e:
            if (r2 >= r10) goto L_0x086c
            char r7 = r9[r2]
            r12 = 10
            if (r7 != r12) goto L_0x084b
            r4 = r2
            int r2 = r2 + 1
            r5 = r2
            goto L_0x0858
        L_0x084b:
            r26.linefeedFromParser()
            r12 = 133(0x85, float:1.86E-43)
            if (r7 != r12) goto L_0x0863
            int r12 = r2 + 1
            r4 = r2
            int r5 = r12 + 1
            r2 = r12
        L_0x0858:
            r12 = 1
            r1.incrLineNumber(r12, r2)
            r12 = 133(0x85, float:1.86E-43)
            r13 = 10
            r14 = 8232(0x2028, float:1.1535E-41)
            goto L_0x08a6
        L_0x0863:
            r12 = 1
            r1.incrLineNumber(r12, r2)
            r4 = r2
            int r2 = r2 + 1
            r5 = r2
            goto L_0x0823
        L_0x086c:
            r26.linefeedFromParser()
            r0 = 28
            r14 = r2
            r15 = r4
            r13 = r5
            r12 = r23
            r2 = r0
            goto L_0x08d1
        L_0x0879:
            r12 = 133(0x85, float:1.86E-43)
            if (r7 == r12) goto L_0x088d
            r14 = 8232(0x2028, float:1.1535E-41)
            if (r7 != r14) goto L_0x0884
            r13 = 10
            goto L_0x0891
        L_0x0884:
            r13 = 10
            if (r7 != r13) goto L_0x08a6
            r15 = 1
            r1.incrLineNumber(r15, r2)
            goto L_0x08a6
        L_0x088d:
            r13 = 10
            r14 = 8232(0x2028, float:1.1535E-41)
        L_0x0891:
            int r5 = r2 - r5
            int r15 = r2 + -1
            r1.pos = r15
            if (r5 <= 0) goto L_0x089c
            r8.textFromParser(r9, r4, r5)
        L_0x089c:
            r26.linefeedFromParser()
            r15 = 1
            r1.incrLineNumber(r15, r2)
            int r5 = r2 + 1
            r4 = r2
        L_0x08a6:
            if (r2 != r10) goto L_0x08bc
            int r5 = r5 + -1
        L_0x08ab:
            int r5 = r2 - r5
            if (r5 <= 0) goto L_0x08b4
            r1.pos = r2
            r8.textFromParser(r9, r4, r5)
        L_0x08b4:
            int r4 = r9.length
            r14 = r2
            r15 = r4
            r13 = r5
            r12 = r23
            r2 = r0
            goto L_0x08d1
        L_0x08bc:
            int r15 = r2 + 1
            char r7 = r9[r2]
            r2 = r15
            goto L_0x0823
        L_0x08c3:
            r23 = r12
            r22 = r14
            r0 = 1
            r0 = 31
            r14 = r2
            r2 = r0
            goto L_0x08d1
        L_0x08cd:
            r14 = r2
            r12 = r23
            r2 = r0
        L_0x08d1:
            if (r14 >= r10) goto L_0x08e0
            int r0 = r14 + 1
            char r7 = r9[r14]
            r14 = r22
            r24 = r2
            r2 = r0
            r0 = r24
            goto L_0x0021
        L_0x08e0:
            int r4 = r14 - r15
            if (r4 <= 0) goto L_0x08eb
            r1.pos = r15     // Catch:{ IOException -> 0x0928 }
            int r0 = r4 + 1
            r1.mark(r0)     // Catch:{ IOException -> 0x0928 }
        L_0x08eb:
            r1.pos = r14     // Catch:{ IOException -> 0x0928 }
            int r0 = r25.read()     // Catch:{ IOException -> 0x0928 }
            if (r0 >= 0) goto L_0x0904
            r5 = 1
            if (r2 == r5) goto L_0x0903
            r5 = 28
            if (r2 != r5) goto L_0x08fb
            goto L_0x0903
        L_0x08fb:
            r2 = 37
            r0 = r2
            r2 = r14
            r14 = r22
            goto L_0x0021
        L_0x0903:
            return
        L_0x0904:
            if (r4 <= 0) goto L_0x090d
            r25.reset()     // Catch:{ IOException -> 0x0928 }
            r1.skip(r4)     // Catch:{ IOException -> 0x0928 }
            goto L_0x0910
        L_0x090d:
            r25.unread_quick()     // Catch:{ IOException -> 0x0928 }
        L_0x0910:
            int r0 = r1.pos
            char[] r9 = r1.buffer
            int r10 = r1.limit
            if (r4 <= 0) goto L_0x091c
            int r5 = r0 - r4
            goto L_0x091d
        L_0x091c:
            r5 = r10
        L_0x091d:
            r15 = r5
            int r5 = r0 + 1
            char r7 = r9[r0]
            r0 = r2
            r2 = r5
            r14 = r22
            goto L_0x0021
        L_0x0928:
            r0 = move-exception
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.String r6 = r0.getMessage()
            r5.<init>(r6)
            goto L_0x0934
        L_0x0933:
            throw r5
        L_0x0934:
            goto L_0x0933
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xml.XMLParser.parse(gnu.text.LineBufferedReader, gnu.xml.XMLFilter):void");
    }
}
