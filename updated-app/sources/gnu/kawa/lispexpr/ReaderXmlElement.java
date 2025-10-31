package gnu.kawa.lispexpr;

import gnu.expr.Compilation;
import gnu.expr.PrimProcedure;
import gnu.expr.Special;
import gnu.kawa.xml.CommentConstructor;
import gnu.kawa.xml.MakeCDATA;
import gnu.kawa.xml.MakeProcInst;
import gnu.kawa.xml.MakeText;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Namespace;
import gnu.mapping.Values;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SyntaxException;
import gnu.xml.XName;
import java.io.IOException;

public class ReaderXmlElement extends ReadTableEntry {
    static final String DEFAULT_ELEMENT_NAMESPACE = "[default-element-namespace]";

    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        LispReader reader = (LispReader) in;
        return readXMLConstructor(reader, reader.readUnicodeChar(), false);
    }

    public static Pair quote(Object obj) {
        return LList.list2(Namespace.EmptyNamespace.getSymbol(LispLanguage.quote_sym), obj);
    }

    public static Object readQNameExpression(LispReader reader, int ch, boolean forElement) throws IOException, SyntaxException {
        String name = reader.getName();
        int line = reader.getLineNumber() + 1;
        int column = reader.getColumnNumber();
        reader.tokenBufferLength = 0;
        if (XName.isNameStart(ch)) {
            int colon = -1;
            while (true) {
                reader.tokenBufferAppend(ch);
                ch = reader.readUnicodeChar();
                if (ch == 58 && colon < 0) {
                    colon = reader.tokenBufferLength;
                } else if (!XName.isNamePart(ch)) {
                    break;
                }
            }
            reader.unread(ch);
            if (colon < 0 && !forElement) {
                return quote(Namespace.getDefaultSymbol(reader.tokenBufferString().intern()));
            }
            return new Pair(ResolveNamespace.resolveQName, PairWithPosition.make(Namespace.EmptyNamespace.getSymbol(colon < 0 ? DEFAULT_ELEMENT_NAMESPACE : new String(reader.tokenBuffer, 0, colon).intern()), new Pair(new String(reader.tokenBuffer, colon + 1, (reader.tokenBufferLength - colon) - 1).intern(), LList.Empty), reader.getName(), line, column));
        } else if (ch == 123 || ch == 40) {
            return readEscapedExpression(reader, ch);
        } else {
            reader.error("missing element name");
            return null;
        }
    }

    static Object readEscapedExpression(LispReader reader, int ch) throws IOException, SyntaxException {
        if (ch == 40) {
            reader.unread(ch);
            return reader.readObject();
        }
        LineBufferedReader port = reader.getPort();
        char saveReadState = reader.pushNesting('{');
        int startLine = port.getLineNumber();
        int startColumn = port.getColumnNumber();
        try {
            Pair list = reader.makePair(new PrimProcedure(Compilation.typeValues.getDeclaredMethod("values", 1)), startLine, startColumn);
            Pair last = list;
            ReadTable readTable = ReadTable.getCurrent();
            while (true) {
                int line = port.getLineNumber();
                int column = port.getColumnNumber();
                int ch2 = port.read();
                if (ch2 == 125) {
                    break;
                }
                if (ch2 < 0) {
                    reader.eofError("unexpected EOF in list starting here", startLine + 1, startColumn);
                }
                Object value = reader.readValues(ch2, readTable.lookup(ch2), readTable);
                if (value != Values.empty) {
                    Pair pair = reader.makePair(reader.handlePostfix(value, readTable, line, column), line, column);
                    reader.setCdr(last, pair);
                    last = pair;
                }
            }
            reader.tokenBufferLength = 0;
            if (last == list.getCdr()) {
                return last.getCar();
            }
            reader.popNesting(saveReadState);
            return list;
        } finally {
            reader.popNesting(saveReadState);
        }
    }

    static Object readXMLConstructor(LispReader reader, int next, boolean inElementContent) throws IOException, SyntaxException {
        int startLine = reader.getLineNumber() + 1;
        int startColumn = reader.getColumnNumber() - 2;
        if (next == 33) {
            int next2 = reader.read();
            if (next2 == 45) {
                int peek = reader.peek();
                next2 = peek;
                if (peek == 45) {
                    reader.skip();
                    if (!reader.readDelimited("-->")) {
                        reader.error('f', reader.getName(), startLine, startColumn, "unexpected end-of-file in XML comment starting here - expected \"-->\"");
                    }
                    return LList.list2(CommentConstructor.commentConstructor, reader.tokenBufferString());
                }
            }
            if (next2 == 91) {
                int read = reader.read();
                next2 = read;
                if (read == 67) {
                    int read2 = reader.read();
                    next2 = read2;
                    if (read2 == 68) {
                        int read3 = reader.read();
                        next2 = read3;
                        if (read3 == 65) {
                            int read4 = reader.read();
                            next2 = read4;
                            if (read4 == 84) {
                                int read5 = reader.read();
                                next2 = read5;
                                if (read5 == 65) {
                                    int read6 = reader.read();
                                    next2 = read6;
                                    if (read6 == 91) {
                                        if (!reader.readDelimited("]]>")) {
                                            reader.error('f', reader.getName(), startLine, startColumn, "unexpected end-of-file in CDATA starting here - expected \"]]>\"");
                                        }
                                        return LList.list2(MakeCDATA.makeCDATA, reader.tokenBufferString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            reader.error('f', reader.getName(), startLine, startColumn, "'<!' must be followed by '--' or '[CDATA['");
            while (next2 >= 0 && next2 != 62 && next2 != 10 && next2 != 13) {
                next2 = reader.read();
            }
            return null;
        } else if (next != 63) {
            return readElementConstructor(reader, next);
        } else {
            int next3 = reader.readUnicodeChar();
            if (next3 < 0 || !XName.isNameStart(next3)) {
                reader.error("missing target after '<?'");
            }
            do {
                reader.tokenBufferAppend(next3);
                next3 = reader.readUnicodeChar();
            } while (XName.isNamePart(next3));
            String target = reader.tokenBufferString();
            int nspaces = 0;
            while (next3 >= 0 && Character.isWhitespace(next3)) {
                nspaces++;
                next3 = reader.read();
            }
            reader.unread(next3);
            char saveReadState = reader.pushNesting('?');
            try {
                if (!reader.readDelimited("?>")) {
                    reader.error('f', reader.getName(), startLine, startColumn, "unexpected end-of-file looking for \"?>\"");
                }
                if (nspaces == 0 && reader.tokenBufferLength > 0) {
                    reader.error("target must be followed by space or '?>'");
                }
                return LList.list3(MakeProcInst.makeProcInst, target, reader.tokenBufferString());
            } finally {
                reader.popNesting(saveReadState);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x00da  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x011a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object readElementConstructor(gnu.kawa.lispexpr.LispReader r23, int r24) throws java.io.IOException, gnu.text.SyntaxException {
        /*
            r6 = r23
            int r0 = r23.getLineNumber()
            r1 = 1
            int r7 = r0 + 1
            int r0 = r23.getColumnNumber()
            r2 = 2
            int r8 = r0 + -2
            r0 = r24
            java.lang.Object r9 = readQNameExpression(r6, r0, r1)
            int r3 = r6.tokenBufferLength
            if (r3 != 0) goto L_0x001c
            r3 = 0
            goto L_0x0020
        L_0x001c:
            java.lang.String r3 = r23.tokenBufferString()
        L_0x0020:
            r10 = r3
            gnu.lists.LList r3 = gnu.lists.LList.Empty
            java.lang.String r5 = r23.getName()
            gnu.lists.PairWithPosition r11 = gnu.lists.PairWithPosition.make(r9, r3, r5, r7, r8)
            r3 = r11
            gnu.lists.LList r5 = gnu.lists.LList.Empty
            r12 = 0
            r13 = r5
        L_0x0030:
            r5 = 0
            int r0 = r23.readUnicodeChar()
        L_0x0035:
            if (r0 < 0) goto L_0x0043
            boolean r14 = java.lang.Character.isWhitespace(r0)
            if (r14 == 0) goto L_0x0043
            int r0 = r23.read()
            r5 = 1
            goto L_0x0035
        L_0x0043:
            r15 = 47
            r4 = 0
            r14 = 62
            if (r0 < 0) goto L_0x0134
            if (r0 == r14) goto L_0x0134
            if (r0 != r15) goto L_0x0054
            r22 = r9
            r9 = 62
            goto L_0x013a
        L_0x0054:
            if (r5 != 0) goto L_0x005b
            java.lang.String r14 = "missing space before attribute"
            r6.error(r14)
        L_0x005b:
            java.lang.Object r14 = readQNameExpression(r6, r0, r4)
            int r15 = r23.getLineNumber()
            int r15 = r15 + r1
            int r17 = r23.getColumnNumber()
            int r17 = r17 + 1
            int r2 = r6.tokenBufferLength
            int r17 = r17 - r2
            r2 = 0
            int r1 = r6.tokenBufferLength
            r4 = 5
            if (r1 < r4) goto L_0x00ca
            char[] r1 = r6.tokenBuffer
            r20 = 0
            char r1 = r1[r20]
            r4 = 120(0x78, float:1.68E-43)
            if (r1 != r4) goto L_0x00ca
            char[] r1 = r6.tokenBuffer
            r4 = 1
            char r1 = r1[r4]
            r4 = 109(0x6d, float:1.53E-43)
            if (r1 != r4) goto L_0x00ca
            char[] r1 = r6.tokenBuffer
            r4 = 2
            char r1 = r1[r4]
            r4 = 108(0x6c, float:1.51E-43)
            if (r1 != r4) goto L_0x00ca
            char[] r1 = r6.tokenBuffer
            r4 = 3
            char r1 = r1[r4]
            r4 = 110(0x6e, float:1.54E-43)
            if (r1 != r4) goto L_0x00ca
            char[] r1 = r6.tokenBuffer
            r4 = 4
            char r1 = r1[r4]
            r4 = 115(0x73, float:1.61E-43)
            if (r1 != r4) goto L_0x00ca
            int r1 = r6.tokenBufferLength
            r4 = 5
            if (r1 != r4) goto L_0x00ac
            java.lang.String r2 = ""
            r21 = r5
            goto L_0x00d0
        L_0x00ac:
            char[] r1 = r6.tokenBuffer
            char r1 = r1[r4]
            r4 = 58
            if (r1 != r4) goto L_0x00c5
            java.lang.String r1 = new java.lang.String
            char[] r4 = r6.tokenBuffer
            r24 = r2
            int r2 = r6.tokenBufferLength
            r21 = r5
            r5 = 6
            int r2 = r2 - r5
            r1.<init>(r4, r5, r2)
            r2 = r1
            goto L_0x00d0
        L_0x00c5:
            r24 = r2
            r21 = r5
            goto L_0x00ce
        L_0x00ca:
            r24 = r2
            r21 = r5
        L_0x00ce:
            r2 = r24
        L_0x00d0:
            r1 = 32
            int r0 = skipSpace(r6, r1)
            r4 = 61
            if (r0 == r4) goto L_0x00df
            java.lang.String r4 = "missing '=' after attribute"
            r6.error(r4)
        L_0x00df:
            int r0 = skipSpace(r6, r1)
            gnu.kawa.xml.MakeAttribute r1 = gnu.kawa.xml.MakeAttribute.makeAttribute
            gnu.lists.LList r4 = gnu.lists.LList.Empty
            java.lang.String r5 = r23.getName()
            gnu.lists.PairWithPosition r1 = gnu.lists.PairWithPosition.make(r1, r4, r5, r7, r8)
            r4 = r1
            gnu.lists.LList r5 = gnu.lists.LList.Empty
            r22 = r9
            java.lang.String r9 = r23.getName()
            gnu.lists.PairWithPosition r5 = gnu.lists.PairWithPosition.make(r14, r5, r9, r7, r8)
            r6.setCdr(r4, r5)
            r4 = r5
            char r9 = (char) r0
            gnu.lists.Pair r4 = readContent(r6, r9, r4)
            if (r2 == 0) goto L_0x011a
            gnu.lists.PairWithPosition r9 = new gnu.lists.PairWithPosition
            r24 = r0
            java.lang.Object r0 = r5.getCdr()
            gnu.lists.Pair r0 = gnu.lists.Pair.make(r2, r0)
            r9.<init>(r5, r0, r13)
            r0 = r9
            r13 = r0
            r14 = 0
            goto L_0x012c
        L_0x011a:
            r24 = r0
            java.lang.Object r0 = r23.makeNil()
            r9 = -1
            r20 = r14
            r14 = 0
            gnu.lists.PairWithPosition r0 = gnu.lists.PairWithPosition.make(r1, r0, r14, r9, r9)
            r3.setCdrBackdoor(r0)
            r3 = r0
        L_0x012c:
            r0 = r24
            r9 = r22
            r1 = 1
            r2 = 2
            goto L_0x0030
        L_0x0134:
            r21 = r5
            r22 = r9
            r9 = 62
        L_0x013a:
            r1 = 0
            if (r0 != r15) goto L_0x014b
            int r0 = r23.read()
            if (r0 != r9) goto L_0x0146
            r1 = 1
            r14 = r1
            goto L_0x014c
        L_0x0146:
            r6.unread(r0)
            r14 = r1
            goto L_0x014c
        L_0x014b:
            r14 = r1
        L_0x014c:
            if (r14 != 0) goto L_0x0207
            if (r0 == r9) goto L_0x0158
            java.lang.String r1 = "missing '>' after start element"
            r6.error(r1)
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            return r1
        L_0x0158:
            r1 = 60
            gnu.lists.Pair r15 = readContent(r6, r1, r3)
            int r0 = r23.readUnicodeChar()
            boolean r1 = gnu.xml.XName.isNameStart(r0)
            if (r1 == 0) goto L_0x01f9
            r4 = 0
            r6.tokenBufferLength = r4
        L_0x016b:
            r6.tokenBufferAppend(r0)
            int r5 = r23.readUnicodeChar()
            boolean r0 = gnu.xml.XName.isNamePart(r5)
            if (r0 != 0) goto L_0x01eb
            r1 = 58
            if (r5 == r1) goto L_0x01e8
            java.lang.String r3 = r23.tokenBufferString()
            if (r10 == 0) goto L_0x0190
            boolean r0 = r3.equals(r10)
            if (r0 != 0) goto L_0x018a
            goto L_0x0190
        L_0x018a:
            r19 = r3
            r17 = r5
            r9 = 0
            goto L_0x01e3
        L_0x0190:
            java.lang.String r2 = r23.getName()
            int r0 = r23.getLineNumber()
            r16 = 1
            int r16 = r0 + 1
            int r0 = r23.getColumnNumber()
            int r1 = r6.tokenBufferLength
            int r17 = r0 - r1
            java.lang.String r0 = ">'"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            if (r10 != 0) goto L_0x01b0
            r1.<init>()
            java.lang.String r4 = "computed start tag closed by '</"
            goto L_0x01bf
        L_0x01b0:
            r1.<init>()
            java.lang.String r4 = "'<"
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.StringBuilder r1 = r1.append(r10)
            java.lang.String r4 = ">' closed by '</"
        L_0x01bf:
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r0 = r0.toString()
            r18 = r0
            r1 = 101(0x65, float:1.42E-43)
            r0 = r23
            r19 = r3
            r3 = r16
            r9 = 0
            r4 = r17
            r17 = r5
            r5 = r18
            r0.error(r1, r2, r3, r4, r5)
        L_0x01e3:
            r6.tokenBufferLength = r9
            r0 = r17
            goto L_0x01f9
        L_0x01e8:
            r17 = r5
            goto L_0x01ef
        L_0x01eb:
            r17 = r5
            r1 = 58
        L_0x01ef:
            r9 = 0
            r16 = 1
            r0 = r17
            r4 = 0
            r9 = 62
            goto L_0x016b
        L_0x01f9:
            int r0 = skipSpace(r6, r0)
            r1 = 62
            if (r0 == r1) goto L_0x0206
            java.lang.String r1 = "missing '>' after end element"
            r6.error(r1)
        L_0x0206:
            r3 = r15
        L_0x0207:
            gnu.lists.LList r1 = gnu.lists.LList.reverseInPlace(r13)
            gnu.kawa.lispexpr.MakeXmlElement r2 = gnu.kawa.lispexpr.MakeXmlElement.makeXml
            gnu.lists.Pair r4 = gnu.lists.Pair.make(r1, r11)
            java.lang.String r5 = r23.getName()
            gnu.lists.PairWithPosition r2 = gnu.lists.PairWithPosition.make(r2, r4, r5, r7, r8)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.ReaderXmlElement.readElementConstructor(gnu.kawa.lispexpr.LispReader, int):java.lang.Object");
    }

    public static Pair readContent(LispReader reader, char delimiter, Pair resultTail) throws IOException, SyntaxException {
        reader.tokenBufferLength = 0;
        boolean prevWasEnclosed = false;
        while (true) {
            Object item = null;
            String text = null;
            int line = reader.getLineNumber() + 1;
            int column = reader.getColumnNumber();
            int next = reader.read();
            if (next < 0) {
                reader.error("unexpected end-of-file");
                item = Special.eof;
            } else if (next == delimiter) {
                if (delimiter == '<') {
                    if (reader.tokenBufferLength > 0) {
                        text = reader.tokenBufferString();
                        reader.tokenBufferLength = 0;
                    }
                    int next2 = reader.read();
                    if (next2 == 47) {
                        item = Special.eof;
                    } else {
                        item = readXMLConstructor(reader, next2, true);
                    }
                } else if (reader.checkNext(delimiter)) {
                    reader.tokenBufferAppend(delimiter);
                } else {
                    item = Special.eof;
                }
                prevWasEnclosed = false;
            } else if (next == 38) {
                int next3 = reader.read();
                if (next3 == 35) {
                    readCharRef(reader);
                } else if (next3 == 40 || next3 == 123) {
                    if (reader.tokenBufferLength > 0 || prevWasEnclosed) {
                        text = reader.tokenBufferString();
                    }
                    reader.tokenBufferLength = 0;
                    item = readEscapedExpression(reader, next3);
                } else {
                    item = readEntity(reader, next3);
                    if (prevWasEnclosed && reader.tokenBufferLength == 0) {
                        text = "";
                    }
                }
                prevWasEnclosed = true;
            } else {
                if (delimiter != '<' && (next == 9 || next == 10 || next == 13)) {
                    next = 32;
                }
                if (next == 60) {
                    reader.error('e', "'<' must be quoted in a direct attribute value");
                }
                reader.tokenBufferAppend((char) next);
            }
            if (item != null && reader.tokenBufferLength > 0) {
                text = reader.tokenBufferString();
                reader.tokenBufferLength = 0;
            }
            if (text != null) {
                Pair make = PairWithPosition.make(Pair.list2(MakeText.makeText, text), reader.makeNil(), (String) null, -1, -1);
                resultTail.setCdrBackdoor(make);
                resultTail = make;
            }
            if (item == Special.eof) {
                return resultTail;
            }
            if (item != null) {
                Pair pair = PairWithPosition.make(item, reader.makeNil(), (String) null, line, column);
                resultTail.setCdrBackdoor(pair);
                resultTail = pair;
            }
        }
    }

    static void readCharRef(LispReader reader) throws IOException, SyntaxException {
        int base;
        int next = reader.read();
        if (next == 120) {
            base = 16;
            next = reader.read();
        } else {
            base = 10;
        }
        int value = 0;
        while (next >= 0) {
            int digit = Character.digit((char) next, base);
            if (digit < 0 || value >= 134217728) {
                break;
            }
            value = (value * base) + digit;
            next = reader.read();
        }
        if (next != 59) {
            reader.unread(next);
            reader.error("invalid character reference");
        } else if ((value <= 0 || value > 55295) && ((value < 57344 || value > 65533) && (value < 65536 || value > 1114111))) {
            reader.error("invalid character value " + value);
        } else {
            reader.tokenBufferAppend(value);
        }
    }

    static Object readEntity(LispReader reader, int next) throws IOException, SyntaxException {
        int saveLength = reader.tokenBufferLength;
        while (next >= 0) {
            char ch = (char) next;
            if (!XName.isNamePart(ch)) {
                break;
            }
            reader.tokenBufferAppend(ch);
            next = reader.read();
        }
        if (next != 59) {
            reader.unread(next);
            reader.error("invalid entity reference");
            return "?";
        }
        String ref = new String(reader.tokenBuffer, saveLength, reader.tokenBufferLength - saveLength);
        reader.tokenBufferLength = saveLength;
        namedEntity(reader, ref);
        return null;
    }

    public static void namedEntity(LispReader reader, String name) {
        String name2 = name.intern();
        char ch = '?';
        if (name2 == "lt") {
            ch = '<';
        } else if (name2 == "gt") {
            ch = '>';
        } else if (name2 == "amp") {
            ch = '&';
        } else if (name2 == "quot") {
            ch = '\"';
        } else if (name2 == "apos") {
            ch = '\'';
        } else {
            reader.error("unknown enity reference: '" + name2 + "'");
        }
        reader.tokenBufferAppend(ch);
    }

    static int skipSpace(LispReader reader, int ch) throws IOException, SyntaxException {
        while (ch >= 0 && Character.isWhitespace(ch)) {
            ch = reader.readUnicodeChar();
        }
        return ch;
    }
}
