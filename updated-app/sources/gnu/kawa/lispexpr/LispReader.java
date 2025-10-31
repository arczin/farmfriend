package gnu.kawa.lispexpr;

import androidx.core.internal.view.SupportMenu;
import gnu.bytecode.Access;
import gnu.expr.Keyword;
import gnu.expr.QuoteExp;
import gnu.expr.Special;
import gnu.kawa.util.GeneralHashTable;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.lists.Sequence;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.math.IntNum;
import gnu.text.Char;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.IOException;

public class LispReader extends Lexer {
    static final int SCM_COMPLEX = 1;
    public static final int SCM_NUMBERS = 1;
    public static final char TOKEN_ESCAPE_CHAR = '￿';
    protected boolean seenEscapes;
    GeneralHashTable<Integer, Object> sharedStructureTable;

    public LispReader(LineBufferedReader port) {
        super(port);
    }

    public LispReader(LineBufferedReader port, SourceMessages messages) {
        super(port, messages);
    }

    public final void readNestedComment(char c1, char c2) throws IOException, SyntaxException {
        int commentNesting = 1;
        int startLine = this.port.getLineNumber();
        int startColumn = this.port.getColumnNumber();
        do {
            int c = read();
            if (c == 124) {
                c = read();
                if (c == c1) {
                    commentNesting--;
                }
            } else if (c == c1 && (c = read()) == c2) {
                commentNesting++;
            }
            if (c < 0) {
                eofError("unexpected end-of-file in " + c1 + c2 + " comment starting here", startLine + 1, startColumn - 1);
                return;
            }
        } while (commentNesting > 0);
    }

    static char getReadCase() {
        try {
            char read_case = Environment.getCurrent().get("symbol-read-case", (Object) "P").toString().charAt(0);
            if (read_case == 'P') {
                return read_case;
            }
            if (read_case == 'u') {
                return 'U';
            }
            if (read_case == 'd' || read_case == 'l' || read_case == 'L') {
                return 'D';
            }
            if (read_case == 'i') {
                return Access.INNERCLASS_CONTEXT;
            }
            return read_case;
        } catch (Exception e) {
            return 'P';
        }
    }

    public Object readValues(int ch, ReadTable rtable) throws IOException, SyntaxException {
        return readValues(ch, rtable.lookup(ch), rtable);
    }

    public Object readValues(int ch, ReadTableEntry entry, ReadTable rtable) throws IOException, SyntaxException {
        int startPos = this.tokenBufferLength;
        this.seenEscapes = false;
        switch (entry.getKind()) {
            case 0:
                String err = "invalid character #\\" + ((char) ch);
                if (this.interactive) {
                    fatal(err);
                } else {
                    error(err);
                }
                return Values.empty;
            case 1:
                return Values.empty;
            case 5:
            case 6:
                return entry.read(this, ch, -1);
            default:
                return readAndHandleToken(ch, startPos, rtable);
        }
    }

    /* access modifiers changed from: protected */
    public Object readAndHandleToken(int ch, int startPos, ReadTable rtable) throws IOException, SyntaxException {
        Object value;
        int i = startPos;
        ReadTable readTable = rtable;
        readToken(ch, getReadCase(), readTable);
        int endPos = this.tokenBufferLength;
        if (!this.seenEscapes && (value = parseNumber(this.tokenBuffer, startPos, endPos - i, 0, 0, 1)) != null && !(value instanceof String)) {
            return value;
        }
        char readCase = getReadCase();
        char c = TOKEN_ESCAPE_CHAR;
        if (readCase == 'I') {
            int upperCount = 0;
            int lowerCount = 0;
            int i2 = startPos;
            while (i2 < endPos) {
                char ci = this.tokenBuffer[i2];
                if (ci == 65535) {
                    i2++;
                } else if (Character.isLowerCase(ci)) {
                    lowerCount++;
                } else if (Character.isUpperCase(ci)) {
                    upperCount++;
                }
                i2++;
            }
            if (lowerCount == 0) {
                readCase = 'D';
            } else if (upperCount == 0) {
                readCase = 'U';
            } else {
                readCase = 'P';
            }
        }
        boolean handleUri = endPos >= i + 2 && this.tokenBuffer[endPos + -1] == '}' && this.tokenBuffer[endPos + -2] != 65535 && peek() == 58;
        int packageMarker = -1;
        int lbrace = -1;
        int rbrace = -1;
        int braceNesting = 0;
        int j = startPos;
        int i3 = startPos;
        while (i3 < endPos) {
            char ci2 = this.tokenBuffer[i3];
            if (ci2 == c) {
                i3++;
                if (i3 < endPos) {
                    this.tokenBuffer[j] = this.tokenBuffer[i3];
                    j++;
                }
            } else {
                if (handleUri) {
                    if (ci2 == '{') {
                        if (lbrace < 0) {
                            lbrace = j;
                        } else if (braceNesting == 0) {
                        }
                        braceNesting++;
                    } else if (ci2 == '}') {
                        braceNesting--;
                        if (braceNesting >= 0) {
                            if (braceNesting == 0) {
                                if (rbrace < 0) {
                                    rbrace = j;
                                }
                            }
                        }
                    }
                }
                if (braceNesting <= 0) {
                    if (ci2 == ':') {
                        packageMarker = packageMarker >= 0 ? -1 : j;
                    } else if (readCase == 'U') {
                        ci2 = Character.toUpperCase(ci2);
                    } else if (readCase == 'D') {
                        ci2 = Character.toLowerCase(ci2);
                    }
                }
                this.tokenBuffer[j] = ci2;
                j++;
            }
            i3++;
            c = TOKEN_ESCAPE_CHAR;
        }
        int endPos2 = j;
        int len = endPos2 - i;
        if (lbrace < 0 || rbrace <= lbrace) {
            if (readTable.initialColonIsKeyword != 0 && packageMarker == i && len > 1) {
                int startPos2 = 1 + i;
                return Keyword.make(new String(this.tokenBuffer, startPos2, endPos2 - startPos2).intern());
            } else if (readTable.finalColonIsKeyword == 0 || packageMarker != endPos2 - 1 || (len <= 1 && !this.seenEscapes)) {
                return readTable.makeSymbol(new String(this.tokenBuffer, i, len));
            } else {
                return Keyword.make(new String(this.tokenBuffer, i, len - 1).intern());
            }
        } else {
            String prefix = lbrace > 0 ? new String(this.tokenBuffer, i, lbrace - i) : null;
            int lbrace2 = lbrace + 1;
            char c2 = readCase;
            String uri = new String(this.tokenBuffer, lbrace2, rbrace - lbrace2);
            int read = read();
            int ch2 = read();
            Object rightOperand = readValues(ch2, readTable.lookup(ch2), readTable);
            if (!(rightOperand instanceof SimpleSymbol)) {
                error("expected identifier in symbol after '{URI}:'");
            }
            return Symbol.valueOf(rightOperand.toString(), uri, prefix);
        }
    }

    /* access modifiers changed from: package-private */
    public void readToken(int ch, char readCase, ReadTable rtable) throws IOException, SyntaxException {
        boolean inEscapes = false;
        int braceNesting = 0;
        while (true) {
            if (ch < 0) {
                if (inEscapes) {
                    eofError("unexpected EOF between escapes");
                } else {
                    return;
                }
            }
            ReadTableEntry entry = rtable.lookup(ch);
            int kind = entry.getKind();
            if (kind != 0) {
                if (ch == rtable.postfixLookupOperator && !inEscapes) {
                    int next = this.port.peek();
                    if (next == rtable.postfixLookupOperator) {
                        unread(ch);
                        return;
                    } else if (validPostfixLookupStart(next, rtable)) {
                        kind = 5;
                    }
                }
                if (kind == 3) {
                    int ch2 = read();
                    if (ch2 < 0) {
                        eofError("unexpected EOF after single escape");
                    }
                    if (rtable.hexEscapeAfterBackslash && (ch2 == 120 || ch2 == 88)) {
                        ch2 = readHexEscape();
                    }
                    tokenBufferAppend(SupportMenu.USER_MASK);
                    tokenBufferAppend(ch2);
                    this.seenEscapes = true;
                } else if (kind == 4) {
                    inEscapes = !inEscapes;
                    this.seenEscapes = true;
                } else if (inEscapes) {
                    tokenBufferAppend(SupportMenu.USER_MASK);
                    tokenBufferAppend(ch);
                } else {
                    switch (kind) {
                        case 1:
                            unread(ch);
                            return;
                        case 2:
                            if (ch == 123 && entry == ReadTableEntry.brace) {
                                braceNesting++;
                                break;
                            }
                        case 4:
                            inEscapes = true;
                            this.seenEscapes = true;
                            continue;
                        case 5:
                            unread(ch);
                            return;
                        case 6:
                            break;
                    }
                    tokenBufferAppend(ch);
                }
            } else if (inEscapes) {
                tokenBufferAppend(SupportMenu.USER_MASK);
                tokenBufferAppend(ch);
            } else if (ch != 125 || braceNesting - 1 < 0) {
                unread(ch);
            } else {
                tokenBufferAppend(ch);
            }
            ch = read();
        }
        unread(ch);
    }

    public Object readObject() throws IOException, SyntaxException {
        char saveReadState = ((InPort) this.port).readState;
        int startPos = this.tokenBufferLength;
        ((InPort) this.port).readState = ' ';
        try {
            ReadTable rtable = ReadTable.getCurrent();
            while (true) {
                int line = this.port.getLineNumber();
                int column = this.port.getColumnNumber();
                int ch = this.port.read();
                if (ch < 0) {
                    Object obj = Sequence.eofValue;
                    this.tokenBufferLength = startPos;
                    ((InPort) this.port).readState = saveReadState;
                    return obj;
                }
                Object value = readValues(ch, rtable);
                if (value != Values.empty) {
                    return handlePostfix(value, rtable, line, column);
                }
            }
        } finally {
            this.tokenBufferLength = startPos;
            ((InPort) this.port).readState = saveReadState;
        }
    }

    /* access modifiers changed from: protected */
    public boolean validPostfixLookupStart(int ch, ReadTable rtable) throws IOException {
        int kind;
        if (ch < 0 || ch == 58 || ch == rtable.postfixLookupOperator) {
            return false;
        }
        if (ch == 44 || (kind = rtable.lookup(ch).getKind()) == 2 || kind == 6 || kind == 4 || kind == 3) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public Object handlePostfix(Object value, ReadTable rtable, int line, int column) throws IOException, SyntaxException {
        if (value == QuoteExp.voidExp) {
            value = Values.empty;
        }
        while (true) {
            int ch = this.port.peek();
            if (ch < 0 || ch != rtable.postfixLookupOperator) {
                break;
            }
            this.port.read();
            if (!validPostfixLookupStart(this.port.peek(), rtable)) {
                unread();
                break;
            }
            int ch2 = this.port.read();
            value = PairWithPosition.make(LispLanguage.lookup_sym, LList.list2(value, LList.list2(rtable.makeSymbol(LispLanguage.quasiquote_sym), readValues(ch2, rtable.lookup(ch2), rtable))), this.port.getName(), line + 1, column + 1);
        }
        return value;
    }

    private boolean isPotentialNumber(char[] buffer, int start, int end) {
        int sawDigits = 0;
        for (int i = start; i < end; i++) {
            char ch = buffer[i];
            if (Character.isDigit(ch)) {
                sawDigits++;
            } else if (ch == '-' || ch == '+') {
                if (i + 1 == end) {
                    return false;
                }
            } else if (ch == '#') {
                return true;
            } else {
                if (Character.isLetter(ch) || ch == '/' || ch == '_' || ch == '^') {
                    if (i == start) {
                        return false;
                    }
                } else if (ch != '.') {
                    return false;
                }
            }
        }
        if (sawDigits > 0) {
            return true;
        }
        return false;
    }

    public static Object parseNumber(CharSequence str, int radix) {
        char[] buf;
        if (str instanceof FString) {
            buf = ((FString) str).data;
        } else {
            buf = str.toString().toCharArray();
        }
        return parseNumber(buf, 0, str.length(), 0, radix, 1);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:166:0x0275, code lost:
        if (r7[r4 + 2] == 'n') goto L_0x0279;
     */
    /* JADX WARNING: Removed duplicated region for block: B:169:0x027b A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x027c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object parseNumber(char[] r26, int r27, int r28, char r29, int r30, int r31) {
        /*
            r7 = r26
            r0 = r27
            int r8 = r0 + r28
            java.lang.String r9 = "no digits"
            if (r0 < r8) goto L_0x000c
            return r9
        L_0x000c:
            int r1 = r0 + 1
            char r2 = r7[r0]
            r3 = r1
            r4 = r2
            r1 = r29
            r2 = r30
        L_0x0016:
            r5 = 35
            r10 = 32
            r12 = 10
            if (r4 != r5) goto L_0x00a6
            if (r3 < r8) goto L_0x0021
            return r9
        L_0x0021:
            int r4 = r3 + 1
            char r3 = r7[r3]
            java.lang.String r6 = "duplicate radix specifier"
            r13 = 2
            switch(r3) {
                case 66: goto L_0x004f;
                case 68: goto L_0x0048;
                case 69: goto L_0x003b;
                case 73: goto L_0x003b;
                case 79: goto L_0x0034;
                case 88: goto L_0x002d;
                case 98: goto L_0x004f;
                case 100: goto L_0x0048;
                case 101: goto L_0x003b;
                case 105: goto L_0x003b;
                case 111: goto L_0x0034;
                case 120: goto L_0x002d;
                default: goto L_0x002b;
            }
        L_0x002b:
            r11 = 0
            goto L_0x0055
        L_0x002d:
            if (r2 == 0) goto L_0x0030
            return r6
        L_0x0030:
            r2 = 16
            goto L_0x0089
        L_0x0034:
            if (r2 == 0) goto L_0x0037
            return r6
        L_0x0037:
            r2 = 8
            goto L_0x0089
        L_0x003b:
            if (r1 == 0) goto L_0x0045
            if (r1 != r10) goto L_0x0042
            java.lang.String r0 = "non-prefix exactness specifier"
            return r0
        L_0x0042:
            java.lang.String r0 = "duplicate exactness specifier"
            return r0
        L_0x0045:
            r1 = r3
            goto L_0x0089
        L_0x0048:
            if (r2 == 0) goto L_0x004b
            return r6
        L_0x004b:
            r2 = 10
            goto L_0x0089
        L_0x004f:
            if (r2 == 0) goto L_0x0052
            return r6
        L_0x0052:
            r2 = 2
            goto L_0x0089
        L_0x0055:
            int r10 = java.lang.Character.digit(r3, r12)
            if (r10 >= 0) goto L_0x0094
            r10 = 82
            if (r3 == r10) goto L_0x007f
            r10 = 114(0x72, float:1.6E-43)
            if (r3 != r10) goto L_0x0065
            goto L_0x007f
        L_0x0065:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "unknown modifier '#"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r3)
            r1 = 39
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        L_0x007f:
            if (r2 == 0) goto L_0x0082
            return r6
        L_0x0082:
            if (r11 < r13) goto L_0x0091
            if (r11 <= r5) goto L_0x0087
            goto L_0x0091
        L_0x0087:
            r2 = r11
        L_0x0089:
            if (r4 < r8) goto L_0x008c
            return r9
        L_0x008c:
            int r3 = r4 + 1
            char r4 = r7[r4]
            goto L_0x0016
        L_0x0091:
            java.lang.String r0 = "invalid radix specifier"
            return r0
        L_0x0094:
            int r11 = r11 * 10
            int r11 = r11 + r10
            if (r4 < r8) goto L_0x009c
            java.lang.String r0 = "missing letter after '#'"
            return r0
        L_0x009c:
            int r3 = r4 + 1
            char r4 = r7[r4]
            r25 = r4
            r4 = r3
            r3 = r25
            goto L_0x0055
        L_0x00a6:
            if (r1 != 0) goto L_0x00ab
            r13 = 32
            goto L_0x00ac
        L_0x00ab:
            r13 = r1
        L_0x00ac:
            r14 = 46
            r15 = -1
            if (r2 != 0) goto L_0x00c4
            r1 = r28
        L_0x00b3:
            int r1 = r1 + r15
            if (r1 >= 0) goto L_0x00ba
            r5 = 10
            goto L_0x00c5
        L_0x00ba:
            int r2 = r0 + r1
            char r2 = r7[r2]
            if (r2 != r14) goto L_0x00b3
            r5 = 10
            goto L_0x00c5
        L_0x00c4:
            r5 = r2
        L_0x00c5:
            r6 = 45
            if (r4 != r6) goto L_0x00cc
            r16 = 1
            goto L_0x00ce
        L_0x00cc:
            r16 = 0
        L_0x00ce:
            r1 = 43
            if (r4 == r6) goto L_0x00d9
            if (r4 != r1) goto L_0x00d6
            goto L_0x00d9
        L_0x00d6:
            r17 = 0
            goto L_0x00db
        L_0x00d9:
            r17 = 1
        L_0x00db:
            if (r17 == 0) goto L_0x00eb
            if (r3 < r8) goto L_0x00e2
            java.lang.String r0 = "no digits following sign"
            return r0
        L_0x00e2:
            int r4 = r3 + 1
            char r3 = r7[r3]
            r25 = r4
            r4 = r3
            r3 = r25
        L_0x00eb:
            r10 = 0
            r15 = 73
            r14 = 105(0x69, float:1.47E-43)
            if (r4 == r14) goto L_0x00f5
            if (r4 != r15) goto L_0x0124
        L_0x00f5:
            if (r3 != r8) goto L_0x0124
            int r2 = r3 + -2
            if (r0 != r2) goto L_0x0124
            r2 = r31 & 1
            if (r2 == 0) goto L_0x0124
            char r0 = r7[r0]
            if (r0 == r1) goto L_0x0106
            if (r0 == r6) goto L_0x0106
            return r9
        L_0x0106:
            if (r13 == r14) goto L_0x0117
            if (r13 != r15) goto L_0x010b
            goto L_0x0117
        L_0x010b:
            if (r16 == 0) goto L_0x0112
            gnu.math.CComplex r0 = gnu.math.Complex.imMinusOne()
            goto L_0x0116
        L_0x0112:
            gnu.math.CComplex r0 = gnu.math.Complex.imOne()
        L_0x0116:
            return r0
        L_0x0117:
            gnu.math.DComplex r0 = new gnu.math.DComplex
            if (r16 == 0) goto L_0x011e
            r1 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            goto L_0x0120
        L_0x011e:
            r1 = 4607182418800017408(0x3ff0000000000000, double:1.0)
        L_0x0120:
            r0.<init>(r10, r1)
            return r0
        L_0x0124:
            r19 = 0
            r0 = 0
            r21 = r16
            r22 = r19
            r2 = -1
            r24 = -1
            r25 = r4
            r4 = r3
            r3 = r25
        L_0x013b:
            int r10 = java.lang.Character.digit(r3, r5)
            if (r10 < 0) goto L_0x0157
            if (r2 >= 0) goto L_0x0146
            int r2 = r4 + -1
        L_0x0146:
            long r14 = (long) r5
            long r14 = r14 * r22
            r27 = r2
            long r1 = (long) r10
            long r22 = r14 + r1
            r2 = r27
            r15 = r4
            r11 = r5
            r10 = 45
            r14 = 1
            goto L_0x0219
        L_0x0157:
            switch(r3) {
                case 46: goto L_0x0207;
                case 47: goto L_0x01d7;
                case 68: goto L_0x016a;
                case 69: goto L_0x016a;
                case 70: goto L_0x016a;
                case 76: goto L_0x016a;
                case 83: goto L_0x016a;
                case 100: goto L_0x016a;
                case 101: goto L_0x016a;
                case 102: goto L_0x016a;
                case 108: goto L_0x016a;
                case 115: goto L_0x016a;
                default: goto L_0x015a;
            }
        L_0x015a:
            r15 = r4
            r11 = r5
            r10 = 45
            r14 = 1
            int r4 = r15 + -1
            r12 = r0
            r1 = r2
            r5 = r22
            r0 = r24
            r3 = -1
            goto L_0x0235
        L_0x016a:
            if (r4 == r8) goto L_0x01c6
            if (r5 == r12) goto L_0x0172
            r10 = 43
            r14 = 1
            goto L_0x01c9
        L_0x0172:
            char r1 = r7[r4]
            int r3 = r4 + -1
            r10 = 43
            if (r1 == r10) goto L_0x0192
            if (r1 != r6) goto L_0x017d
            goto L_0x0192
        L_0x017d:
            int r1 = java.lang.Character.digit(r1, r12)
            if (r1 >= 0) goto L_0x019f
            int r4 = r4 + -1
            r12 = r0
            r1 = r2
            r11 = r5
            r5 = r22
            r0 = r24
            r3 = -1
            r10 = 45
            r14 = 1
            goto L_0x0235
        L_0x0192:
            int r4 = r4 + 1
            if (r4 >= r8) goto L_0x01c3
            char r1 = r7[r4]
            int r1 = java.lang.Character.digit(r1, r12)
            if (r1 >= 0) goto L_0x019f
            goto L_0x01c3
        L_0x019f:
            if (r5 == r12) goto L_0x01a5
            java.lang.String r0 = "exponent in non-decimal number"
            return r0
        L_0x01a5:
            if (r2 >= 0) goto L_0x01aa
            java.lang.String r0 = "mantissa with no digits"
            return r0
        L_0x01aa:
        L_0x01ab:
            r14 = 1
            int r4 = r4 + r14
            if (r4 >= r8) goto L_0x01b8
            char r1 = r7[r4]
            int r1 = java.lang.Character.digit(r1, r12)
            if (r1 >= 0) goto L_0x01ab
        L_0x01b8:
            r12 = r0
            r1 = r2
            r11 = r5
            r5 = r22
            r0 = r24
            r10 = 45
            goto L_0x0235
        L_0x01c3:
            java.lang.String r0 = "missing exponent digits"
            return r0
        L_0x01c6:
            r10 = 43
            r14 = 1
        L_0x01c9:
            int r4 = r4 + -1
            r12 = r0
            r1 = r2
            r11 = r5
            r5 = r22
            r0 = r24
            r3 = -1
            r10 = 45
            goto L_0x0235
        L_0x01d7:
            r10 = 43
            r14 = 1
            if (r0 == 0) goto L_0x01df
            java.lang.String r0 = "multiple fraction symbol '/'"
            return r0
        L_0x01df:
            if (r2 >= 0) goto L_0x01e4
            java.lang.String r0 = "no digits before fraction symbol '/'"
            return r0
        L_0x01e4:
            if (r24 < 0) goto L_0x01e9
            java.lang.String r0 = "fraction symbol '/' following exponent or '.'"
            return r0
        L_0x01e9:
            int r3 = r4 - r2
            r0 = r26
            r1 = r2
            r2 = r3
            r3 = r5
            r15 = r4
            r4 = r21
            r11 = r5
            r10 = 45
            r5 = r22
            gnu.math.IntNum r0 = valueOf(r0, r1, r2, r3, r4, r5)
            r22 = r19
            r2 = -1
            r21 = 0
            goto L_0x0219
        L_0x0207:
            r15 = r4
            r11 = r5
            r10 = 45
            r14 = 1
            if (r24 < 0) goto L_0x0211
            java.lang.String r0 = "duplicate '.' in number"
            return r0
        L_0x0211:
            if (r11 == r12) goto L_0x0216
            java.lang.String r0 = "'.' in non-decimal number"
            return r0
        L_0x0216:
            int r24 = r15 + -1
        L_0x0219:
            if (r15 != r8) goto L_0x0224
            r12 = r0
            r1 = r2
            r4 = r15
            r5 = r22
            r0 = r24
            r3 = -1
            goto L_0x0235
        L_0x0224:
            int r4 = r15 + 1
            char r3 = r7[r15]
            r5 = r11
            r1 = 43
            r6 = 45
            r10 = 0
            r14 = 105(0x69, float:1.47E-43)
            r15 = 73
            goto L_0x013b
        L_0x0235:
            if (r1 >= 0) goto L_0x0280
            if (r17 == 0) goto L_0x0278
            int r2 = r4 + 4
            if (r2 >= r8) goto L_0x0278
            int r15 = r4 + 3
            char r15 = r7[r15]
            r10 = 46
            if (r15 != r10) goto L_0x0278
            char r2 = r7[r2]
            r10 = 48
            if (r2 != r10) goto L_0x0278
            char r2 = r7[r4]
            r10 = 110(0x6e, float:1.54E-43)
            r15 = 105(0x69, float:1.47E-43)
            if (r2 != r15) goto L_0x0265
            int r2 = r4 + 1
            char r2 = r7[r2]
            if (r2 != r10) goto L_0x0265
            int r2 = r4 + 2
            char r2 = r7[r2]
            r15 = 102(0x66, float:1.43E-43)
            if (r2 != r15) goto L_0x0265
            r10 = 105(0x69, float:1.47E-43)
            goto L_0x0279
        L_0x0265:
            char r2 = r7[r4]
            if (r2 != r10) goto L_0x0278
            int r2 = r4 + 1
            char r2 = r7[r2]
            r15 = 97
            if (r2 != r15) goto L_0x0278
            int r2 = r4 + 2
            char r2 = r7[r2]
            if (r2 != r10) goto L_0x0278
            goto L_0x0279
        L_0x0278:
            r10 = 0
        L_0x0279:
            if (r10 != 0) goto L_0x027c
            return r9
        L_0x027c:
            int r4 = r4 + 5
            r9 = r4
            goto L_0x0282
        L_0x0280:
            r9 = r4
            r10 = 0
        L_0x0282:
            r2 = 105(0x69, float:1.47E-43)
            if (r13 == r2) goto L_0x0290
            r2 = 73
            if (r13 == r2) goto L_0x0290
            r2 = 32
            r18 = 0
            goto L_0x0292
        L_0x0290:
            r18 = 1
        L_0x0292:
            r19 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
            r22 = 9221120237041090560(0x7ff8000000000000, double:NaN)
            r4 = 101(0x65, float:1.42E-43)
            if (r10 == 0) goto L_0x02b4
            r0 = 105(0x69, float:1.47E-43)
            if (r10 != r0) goto L_0x02a4
            r0 = r19
            goto L_0x02a6
        L_0x02a4:
            r0 = r22
        L_0x02a6:
            gnu.math.DFloNum r2 = new gnu.math.DFloNum
            if (r21 == 0) goto L_0x02ab
            double r0 = -r0
        L_0x02ab:
            r2.<init>((double) r0)
            r0 = 0
            r4 = 0
            r10 = 101(0x65, float:1.42E-43)
            goto L_0x036d
        L_0x02b4:
            if (r3 >= 0) goto L_0x031d
            if (r0 < 0) goto L_0x02bc
            r10 = 101(0x65, float:1.42E-43)
            goto L_0x031f
        L_0x02bc:
            int r2 = r9 - r1
            r0 = r26
            r3 = r11
            r10 = 101(0x65, float:1.42E-43)
            r4 = r21
            gnu.math.IntNum r0 = valueOf(r0, r1, r2, r3, r4, r5)
            if (r12 != 0) goto L_0x02cd
            r2 = r0
            goto L_0x02fa
        L_0x02cd:
            boolean r1 = r0.isZero()
            if (r1 == 0) goto L_0x02f5
            boolean r1 = r12.isZero()
            if (r18 == 0) goto L_0x02ea
            gnu.math.DFloNum r0 = new gnu.math.DFloNum
            if (r1 == 0) goto L_0x02e0
            r1 = r22
            goto L_0x02e6
        L_0x02e0:
            if (r16 == 0) goto L_0x02e4
            r19 = -4503599627370496(0xfff0000000000000, double:-Infinity)
        L_0x02e4:
            r1 = r19
        L_0x02e6:
            r0.<init>((double) r1)
            goto L_0x02f3
        L_0x02ea:
            if (r1 == 0) goto L_0x02ef
            java.lang.String r0 = "0/0 is undefined"
            return r0
        L_0x02ef:
            gnu.math.RatNum r0 = gnu.math.RatNum.make(r12, r0)
        L_0x02f3:
            r2 = r0
            goto L_0x02fa
        L_0x02f5:
            gnu.math.RatNum r0 = gnu.math.RatNum.make(r12, r0)
            r2 = r0
        L_0x02fa:
            if (r18 == 0) goto L_0x031a
            boolean r0 = r2.isExact()
            if (r0 == 0) goto L_0x031a
            gnu.math.DFloNum r0 = new gnu.math.DFloNum
            if (r16 == 0) goto L_0x030f
            boolean r1 = r2.isZero()
            if (r1 == 0) goto L_0x030f
            r1 = -9223372036854775808
            goto L_0x0313
        L_0x030f:
            double r1 = r2.doubleValue()
        L_0x0313:
            r0.<init>((double) r1)
            r2 = r0
            r0 = 0
            r4 = 0
            goto L_0x036d
        L_0x031a:
            r0 = 0
            r4 = 0
            goto L_0x036d
        L_0x031d:
            r10 = 101(0x65, float:1.42E-43)
        L_0x031f:
            if (r1 <= r0) goto L_0x0324
            if (r0 < 0) goto L_0x0324
            r1 = r0
        L_0x0324:
            if (r12 == 0) goto L_0x0329
            java.lang.String r0 = "floating-point number after fraction symbol '/'"
            return r0
        L_0x0329:
            java.lang.String r0 = new java.lang.String
            int r2 = r9 - r1
            r0.<init>(r7, r1, r2)
            if (r3 < 0) goto L_0x035d
            char r2 = r7[r3]
            char r2 = java.lang.Character.toLowerCase(r2)
            if (r2 == r10) goto L_0x035b
            int r3 = r3 - r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r4 = 0
            java.lang.String r5 = r0.substring(r4, r3)
            java.lang.StringBuilder r1 = r1.append(r5)
            java.lang.StringBuilder r1 = r1.append(r10)
            int r3 = r3 + r14
            java.lang.String r0 = r0.substring(r3)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r0 = r0.toString()
            goto L_0x035f
        L_0x035b:
            r4 = 0
            goto L_0x035f
        L_0x035d:
            r4 = 0
            r2 = 0
        L_0x035f:
            double r0 = gnu.lists.Convert.parseDouble(r0)
            gnu.math.DFloNum r3 = new gnu.math.DFloNum
            if (r21 == 0) goto L_0x0368
            double r0 = -r0
        L_0x0368:
            r3.<init>((double) r0)
            r0 = r2
            r2 = r3
        L_0x036d:
            if (r13 == r10) goto L_0x0376
            r1 = 69
            if (r13 != r1) goto L_0x0374
            goto L_0x0376
        L_0x0374:
            r6 = r2
            goto L_0x037b
        L_0x0376:
            gnu.math.RatNum r2 = r2.toExact()
            r6 = r2
        L_0x037b:
            if (r9 >= r8) goto L_0x0448
            int r1 = r9 + 1
            char r0 = r7[r9]
            r2 = 64
            if (r0 != r2) goto L_0x03b9
            int r2 = r8 - r1
            r4 = 10
            r0 = r26
            r3 = r13
            r5 = r31
            java.lang.Object r0 = parseNumber(r0, r1, r2, r3, r4, r5)
            boolean r1 = r0 instanceof java.lang.String
            if (r1 == 0) goto L_0x0397
            return r0
        L_0x0397:
            boolean r1 = r0 instanceof gnu.math.RealNum
            if (r1 != 0) goto L_0x039e
            java.lang.String r0 = "invalid complex polar constant"
            return r0
        L_0x039e:
            gnu.math.RealNum r0 = (gnu.math.RealNum) r0
            boolean r1 = r6.isZero()
            if (r1 == 0) goto L_0x03b4
            boolean r1 = r0.isExact()
            if (r1 != 0) goto L_0x03b4
            gnu.math.DFloNum r0 = new gnu.math.DFloNum
            r1 = 0
            r0.<init>((double) r1)
            return r0
        L_0x03b4:
            gnu.math.DComplex r0 = gnu.math.Complex.polar((gnu.math.RealNum) r6, (gnu.math.RealNum) r0)
            return r0
        L_0x03b9:
            r2 = 45
            if (r0 == r2) goto L_0x03fe
            r2 = 43
            if (r0 != r2) goto L_0x03c2
            goto L_0x03fe
        L_0x03c2:
            r11 = 0
        L_0x03c3:
            boolean r0 = java.lang.Character.isLetter(r0)
            if (r0 != 0) goto L_0x03cc
            int r1 = r1 + -1
            goto L_0x03d1
        L_0x03cc:
            int r11 = r11 + 1
            if (r1 != r8) goto L_0x03f0
        L_0x03d1:
            if (r11 != r14) goto L_0x03ed
            int r0 = r1 + -1
            char r0 = r7[r0]
            r2 = 105(0x69, float:1.47E-43)
            if (r0 == r2) goto L_0x03df
            r3 = 73
            if (r0 != r3) goto L_0x03ed
        L_0x03df:
            if (r1 >= r8) goto L_0x03e4
            java.lang.String r0 = "junk after imaginary suffix 'i'"
            return r0
        L_0x03e4:
            gnu.math.IntNum r0 = gnu.math.IntNum.zero()
            gnu.math.Complex r0 = gnu.math.Complex.make((gnu.math.RealNum) r0, (gnu.math.RealNum) r6)
            return r0
        L_0x03ed:
            java.lang.String r0 = "excess junk after number"
            return r0
        L_0x03f0:
            r2 = 105(0x69, float:1.47E-43)
            r3 = 73
            int r0 = r1 + 1
            char r1 = r7[r1]
            r25 = r1
            r1 = r0
            r0 = r25
            goto L_0x03c3
        L_0x03fe:
            r0 = -1
            int r1 = r1 + r0
            int r2 = r8 - r1
            r4 = 10
            r0 = r26
            r3 = r13
            r5 = r31
            java.lang.Object r0 = parseNumber(r0, r1, r2, r3, r4, r5)
            boolean r1 = r0 instanceof java.lang.String
            if (r1 == 0) goto L_0x0412
            return r0
        L_0x0412:
            boolean r1 = r0 instanceof gnu.math.Complex
            if (r1 != 0) goto L_0x0430
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "invalid numeric constant ("
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r1 = ")"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        L_0x0430:
            gnu.math.Complex r0 = (gnu.math.Complex) r0
            gnu.math.RealNum r1 = r0.re()
            boolean r1 = r1.isZero()
            if (r1 != 0) goto L_0x043f
            java.lang.String r0 = "invalid numeric constant"
            return r0
        L_0x043f:
            gnu.math.RealNum r0 = r0.im()
            gnu.math.Complex r0 = gnu.math.Complex.make((gnu.math.RealNum) r6, (gnu.math.RealNum) r0)
            return r0
        L_0x0448:
            boolean r1 = r6 instanceof gnu.math.DFloNum
            if (r1 == 0) goto L_0x0468
            if (r0 <= 0) goto L_0x0468
            if (r0 == r10) goto L_0x0468
            double r1 = r6.doubleValue()
            switch(r0) {
                case 100: goto L_0x0463;
                case 102: goto L_0x045d;
                case 108: goto L_0x0458;
                case 115: goto L_0x045d;
                default: goto L_0x0457;
            }
        L_0x0457:
            goto L_0x0468
        L_0x0458:
            java.math.BigDecimal r0 = java.math.BigDecimal.valueOf(r1)
            return r0
        L_0x045d:
            float r0 = (float) r1
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            return r0
        L_0x0463:
            java.lang.Double r0 = java.lang.Double.valueOf(r1)
            return r0
        L_0x0468:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.LispReader.parseNumber(char[], int, int, char, int, int):java.lang.Object");
    }

    private static IntNum valueOf(char[] buffer, int digits_start, int number_of_digits, int radix, boolean negative, long lvalue) {
        if (number_of_digits + radix > 28) {
            return IntNum.valueOf(buffer, digits_start, number_of_digits, radix, negative);
        }
        return IntNum.make(negative ? -lvalue : lvalue);
    }

    public int readEscape() throws IOException, SyntaxException {
        int c = read();
        if (c >= 0) {
            return readEscape(c);
        }
        eofError("unexpected EOF in character literal");
        return -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00b4, code lost:
        if (r7 >= 0) goto L_0x00ba;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00b6, code lost:
        eofError("unexpected EOF in literal");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00b9, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00c0, code lost:
        if (r7 != 10) goto L_0x00c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00c5, code lost:
        if (r7 != 13) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00cb, code lost:
        if (peek() != 10) goto L_0x00d0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00cd, code lost:
        skip();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00d0, code lost:
        r7 = 10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00d3, code lost:
        if (r7 == 32) goto L_0x00f1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00d5, code lost:
        if (r7 == 9) goto L_0x00f1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00d7, code lost:
        unread(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00db, code lost:
        if (r7 == 10) goto L_0x00de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00de, code lost:
        r7 = read();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00e2, code lost:
        if (r7 >= 0) goto L_0x00e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x00e4, code lost:
        eofError("unexpected EOF in literal");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x00e7, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x00e8, code lost:
        if (r7 == 32) goto L_0x00de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x00ea, code lost:
        if (r7 == 9) goto L_0x00de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x00ec, code lost:
        unread(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x00f0, code lost:
        return -2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x00f1, code lost:
        r7 = read();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:?, code lost:
        return r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int readEscape(int r7) throws java.io.IOException, gnu.text.SyntaxException {
        /*
            r6 = this;
            char r0 = (char) r7
            java.lang.String r1 = "Invalid escape character syntax"
            r2 = 45
            r3 = 92
            r4 = 63
            r5 = -1
            switch(r0) {
                case 9: goto L_0x00b2;
                case 10: goto L_0x00b2;
                case 13: goto L_0x00b2;
                case 32: goto L_0x00b2;
                case 34: goto L_0x00af;
                case 48: goto L_0x008f;
                case 49: goto L_0x008f;
                case 50: goto L_0x008f;
                case 51: goto L_0x008f;
                case 52: goto L_0x008f;
                case 53: goto L_0x008f;
                case 54: goto L_0x008f;
                case 55: goto L_0x008f;
                case 67: goto L_0x0073;
                case 77: goto L_0x005c;
                case 88: goto L_0x0057;
                case 92: goto L_0x0053;
                case 94: goto L_0x007d;
                case 97: goto L_0x0050;
                case 98: goto L_0x004c;
                case 101: goto L_0x0048;
                case 102: goto L_0x0044;
                case 110: goto L_0x0040;
                case 114: goto L_0x003c;
                case 116: goto L_0x0038;
                case 117: goto L_0x0013;
                case 118: goto L_0x000f;
                case 120: goto L_0x0057;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x00f6
        L_0x000f:
            r7 = 11
            goto L_0x00f6
        L_0x0013:
            r7 = 0
            r0 = 4
        L_0x0015:
            int r0 = r0 + r5
            if (r0 < 0) goto L_0x0036
            int r1 = r6.read()
            if (r1 >= 0) goto L_0x0023
            java.lang.String r2 = "premature EOF in \\u escape"
            r6.eofError(r2)
        L_0x0023:
            char r2 = (char) r1
            r3 = 16
            int r2 = java.lang.Character.digit(r2, r3)
            if (r2 >= 0) goto L_0x0031
            java.lang.String r3 = "non-hex character following \\u"
            r6.error(r3)
        L_0x0031:
            int r3 = r7 * 16
            int r7 = r3 + r2
            goto L_0x0015
        L_0x0036:
            goto L_0x00f6
        L_0x0038:
            r7 = 9
            goto L_0x00f6
        L_0x003c:
            r7 = 13
            goto L_0x00f6
        L_0x0040:
            r7 = 10
            goto L_0x00f6
        L_0x0044:
            r7 = 12
            goto L_0x00f6
        L_0x0048:
            r7 = 27
            goto L_0x00f6
        L_0x004c:
            r7 = 8
            goto L_0x00f6
        L_0x0050:
            r7 = 7
            goto L_0x00f6
        L_0x0053:
            r7 = 92
            goto L_0x00f6
        L_0x0057:
            int r0 = r6.readHexEscape()
            return r0
        L_0x005c:
            int r7 = r6.read()
            if (r7 == r2) goto L_0x0066
            r6.error(r1)
            return r4
        L_0x0066:
            int r7 = r6.read()
            if (r7 != r3) goto L_0x0070
            int r7 = r6.readEscape()
        L_0x0070:
            r0 = r7 | 128(0x80, float:1.794E-43)
            return r0
        L_0x0073:
            int r7 = r6.read()
            if (r7 == r2) goto L_0x007d
            r6.error(r1)
            return r4
        L_0x007d:
            int r7 = r6.read()
            if (r7 != r3) goto L_0x0087
            int r7 = r6.readEscape()
        L_0x0087:
            if (r7 != r4) goto L_0x008c
            r0 = 127(0x7f, float:1.78E-43)
            return r0
        L_0x008c:
            r0 = r7 & 159(0x9f, float:2.23E-43)
            return r0
        L_0x008f:
            int r7 = r7 + -48
            r0 = 0
        L_0x0092:
            int r0 = r0 + 1
            r1 = 3
            if (r0 >= r1) goto L_0x00ae
            int r1 = r6.read()
            char r2 = (char) r1
            r3 = 8
            int r2 = java.lang.Character.digit(r2, r3)
            if (r2 < 0) goto L_0x00a9
            int r3 = r7 << 3
            int r7 = r3 + r2
            goto L_0x0092
        L_0x00a9:
            if (r1 < 0) goto L_0x00ae
            r6.unread(r1)
        L_0x00ae:
            goto L_0x00f6
        L_0x00af:
            r7 = 34
            goto L_0x00f6
        L_0x00b2:
            java.lang.String r0 = "unexpected EOF in literal"
            if (r7 >= 0) goto L_0x00ba
            r6.eofError(r0)
            return r5
        L_0x00ba:
            r1 = 9
            r2 = 32
            r3 = 10
            if (r7 != r3) goto L_0x00c3
            goto L_0x00db
        L_0x00c3:
            r4 = 13
            if (r7 != r4) goto L_0x00d3
            int r4 = r6.peek()
            if (r4 != r3) goto L_0x00d0
            r6.skip()
        L_0x00d0:
            r7 = 10
            goto L_0x00db
        L_0x00d3:
            if (r7 == r2) goto L_0x00f1
            if (r7 == r1) goto L_0x00f1
            r6.unread(r7)
        L_0x00db:
            if (r7 == r3) goto L_0x00de
            goto L_0x00f6
        L_0x00de:
            int r7 = r6.read()
            if (r7 >= 0) goto L_0x00e8
            r6.eofError(r0)
            return r5
        L_0x00e8:
            if (r7 == r2) goto L_0x00de
            if (r7 == r1) goto L_0x00de
            r6.unread(r7)
            r0 = -2
            return r0
        L_0x00f1:
            int r7 = r6.read()
            goto L_0x00b2
        L_0x00f6:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.LispReader.readEscape(int):int");
    }

    public int readHexEscape() throws IOException, SyntaxException {
        int d;
        int c = 0;
        while (true) {
            d = read();
            int v = Character.digit((char) d, 16);
            if (v < 0) {
                break;
            }
            c = (c << 4) + v;
        }
        if (d != 59 && d >= 0) {
            unread(d);
        }
        return c;
    }

    public final Object readObject(int c) throws IOException, SyntaxException {
        unread(c);
        return readObject();
    }

    public Object readCommand() throws IOException, SyntaxException {
        return readObject();
    }

    /* access modifiers changed from: protected */
    public Object makeNil() {
        return LList.Empty;
    }

    /* access modifiers changed from: protected */
    public Pair makePair(Object car, int line, int column) {
        return makePair(car, LList.Empty, line, column);
    }

    /* access modifiers changed from: protected */
    public Pair makePair(Object car, Object cdr, int line, int column) {
        String pname = this.port.getName();
        if (pname == null || line < 0) {
            return Pair.make(car, cdr);
        }
        return PairWithPosition.make(car, cdr, pname, line + 1, column + 1);
    }

    /* access modifiers changed from: protected */
    public void setCdr(Object pair, Object cdr) {
        ((Pair) pair).setCdrBackdoor(cdr);
    }

    public static Object readNumberWithRadix(int previous, LispReader reader, int radix) throws IOException, SyntaxException {
        int startPos = reader.tokenBufferLength - previous;
        reader.readToken(reader.read(), 'P', ReadTable.getCurrent());
        int endPos = reader.tokenBufferLength;
        if (startPos == endPos) {
            reader.error("missing numeric token");
            return IntNum.zero();
        }
        Object result = parseNumber(reader.tokenBuffer, startPos, endPos - startPos, 0, radix, 0);
        if (result instanceof String) {
            reader.error((String) result);
            return IntNum.zero();
        } else if (result != null) {
            return result;
        } else {
            reader.error("invalid numeric constant");
            return IntNum.zero();
        }
    }

    public static Object readCharacter(LispReader reader) throws IOException, SyntaxException {
        int ch = reader.read();
        if (ch < 0) {
            reader.eofError("unexpected EOF in character literal");
        }
        int startPos = reader.tokenBufferLength;
        reader.tokenBufferAppend(ch);
        reader.readToken(reader.read(), 'D', ReadTable.getCurrent());
        char[] tokenBuffer = reader.tokenBuffer;
        int length = reader.tokenBufferLength - startPos;
        if (length == 1) {
            return Char.make(tokenBuffer[startPos]);
        }
        String name = new String(tokenBuffer, startPos, length);
        int ch2 = Char.nameToChar(name);
        if (ch2 >= 0) {
            return Char.make(ch2);
        }
        char ch3 = tokenBuffer[startPos];
        if (ch3 == 'x' || ch3 == 'X') {
            int value = 0;
            int i = 1;
            while (i != length) {
                int v = Character.digit(tokenBuffer[startPos + i], 16);
                if (v >= 0 && (value = (value * 16) + v) <= 1114111) {
                    i++;
                }
            }
            return Char.make(value);
        }
        int ch4 = Character.digit(ch3, 8);
        if (ch4 >= 0) {
            int value2 = ch4;
            int i2 = 1;
            while (i2 != length) {
                int ch5 = Character.digit(tokenBuffer[startPos + i2], 8);
                if (ch5 >= 0) {
                    value2 = (value2 * 8) + ch5;
                    i2++;
                }
            }
            return Char.make(value2);
        }
        reader.error("unknown character name: " + name);
        return Char.make(63);
    }

    public static Object readSpecial(LispReader reader) throws IOException, SyntaxException {
        int ch = reader.read();
        if (ch < 0) {
            reader.eofError("unexpected EOF in #! special form");
        }
        if (ch == 47 && reader.getLineNumber() == 0 && reader.getColumnNumber() == 3) {
            ReaderIgnoreRestOfLine.getInstance().read(reader, 35, 1);
            return Values.empty;
        }
        int startPos = reader.tokenBufferLength;
        reader.tokenBufferAppend(ch);
        reader.readToken(reader.read(), 'D', ReadTable.getCurrent());
        String name = new String(reader.tokenBuffer, startPos, reader.tokenBufferLength - startPos);
        if (name.equals("optional")) {
            return Special.optional;
        }
        if (name.equals("rest")) {
            return Special.rest;
        }
        if (name.equals("key")) {
            return Special.key;
        }
        if (name.equals("eof")) {
            return Special.eof;
        }
        if (name.equals("void")) {
            return QuoteExp.voidExp;
        }
        if (name.equals("default")) {
            return Special.dfault;
        }
        if (name.equals("undefined")) {
            return Special.undefined;
        }
        if (name.equals("abstract")) {
            return Special.abstractSpecial;
        }
        if (name.equals("null")) {
            return null;
        }
        reader.error("unknown named constant #!" + name);
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0062, code lost:
        switch(r0) {
            case 8: goto L_0x0078;
            case 16: goto L_0x0072;
            case 32: goto L_0x006c;
            case 64: goto L_0x0066;
            default: goto L_0x0065;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x006b, code lost:
        return new gnu.lists.S64Vector(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0071, code lost:
        return new gnu.lists.S32Vector(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0077, code lost:
        return new gnu.lists.S16Vector(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x007d, code lost:
        return new gnu.lists.S8Vector(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x007e, code lost:
        switch(r0) {
            case 8: goto L_0x0094;
            case 16: goto L_0x008e;
            case 32: goto L_0x0088;
            case 64: goto L_0x0082;
            default: goto L_0x0081;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0087, code lost:
        return new gnu.lists.U64Vector(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x008d, code lost:
        return new gnu.lists.U32Vector(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0093, code lost:
        return new gnu.lists.U16Vector(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0099, code lost:
        return new gnu.lists.U8Vector(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x009a, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static gnu.lists.SimpleVector readSimpleVector(gnu.kawa.lispexpr.LispReader r6, char r7) throws java.io.IOException, gnu.text.SyntaxException {
        /*
            r0 = 0
        L_0x0001:
            int r1 = r6.read()
            if (r1 >= 0) goto L_0x000c
            java.lang.String r2 = "unexpected EOF reading uniform vector"
            r6.eofError(r2)
        L_0x000c:
            char r2 = (char) r1
            r3 = 10
            int r2 = java.lang.Character.digit(r2, r3)
            if (r2 >= 0) goto L_0x009b
            r2 = 8
            r3 = 32
            r4 = 0
            if (r0 == r2) goto L_0x0027
            r2 = 16
            if (r0 == r2) goto L_0x0027
            if (r0 == r3) goto L_0x0027
            r2 = 64
            if (r0 != r2) goto L_0x0031
        L_0x0027:
            r2 = 70
            if (r7 != r2) goto L_0x002d
            if (r0 < r3) goto L_0x0031
        L_0x002d:
            r2 = 40
            if (r1 == r2) goto L_0x0037
        L_0x0031:
            java.lang.String r2 = "invalid uniform vector syntax"
            r6.error(r2)
            return r4
        L_0x0037:
            r3 = -1
            r5 = 41
            java.lang.Object r2 = gnu.kawa.lispexpr.ReaderParens.readList(r6, r2, r3, r5)
            r3 = 0
            int r3 = gnu.lists.LList.listLength(r2, r3)
            if (r3 >= 0) goto L_0x004b
            java.lang.String r5 = "invalid elements in uniform vector syntax"
            r6.error(r5)
            return r4
        L_0x004b:
            r5 = r2
            gnu.lists.Sequence r5 = (gnu.lists.Sequence) r5
            switch(r7) {
                case 70: goto L_0x0052;
                case 83: goto L_0x0062;
                case 85: goto L_0x007e;
                default: goto L_0x0051;
            }
        L_0x0051:
            goto L_0x009a
        L_0x0052:
            switch(r0) {
                case 32: goto L_0x005c;
                case 64: goto L_0x0056;
                default: goto L_0x0055;
            }
        L_0x0055:
            goto L_0x0062
        L_0x0056:
            gnu.lists.F64Vector r4 = new gnu.lists.F64Vector
            r4.<init>((gnu.lists.Sequence) r5)
            return r4
        L_0x005c:
            gnu.lists.F32Vector r4 = new gnu.lists.F32Vector
            r4.<init>((gnu.lists.Sequence) r5)
            return r4
        L_0x0062:
            switch(r0) {
                case 8: goto L_0x0078;
                case 16: goto L_0x0072;
                case 32: goto L_0x006c;
                case 64: goto L_0x0066;
                default: goto L_0x0065;
            }
        L_0x0065:
            goto L_0x007e
        L_0x0066:
            gnu.lists.S64Vector r4 = new gnu.lists.S64Vector
            r4.<init>((gnu.lists.Sequence) r5)
            return r4
        L_0x006c:
            gnu.lists.S32Vector r4 = new gnu.lists.S32Vector
            r4.<init>((gnu.lists.Sequence) r5)
            return r4
        L_0x0072:
            gnu.lists.S16Vector r4 = new gnu.lists.S16Vector
            r4.<init>((gnu.lists.Sequence) r5)
            return r4
        L_0x0078:
            gnu.lists.S8Vector r4 = new gnu.lists.S8Vector
            r4.<init>((gnu.lists.Sequence) r5)
            return r4
        L_0x007e:
            switch(r0) {
                case 8: goto L_0x0094;
                case 16: goto L_0x008e;
                case 32: goto L_0x0088;
                case 64: goto L_0x0082;
                default: goto L_0x0081;
            }
        L_0x0081:
            goto L_0x009a
        L_0x0082:
            gnu.lists.U64Vector r4 = new gnu.lists.U64Vector
            r4.<init>((gnu.lists.Sequence) r5)
            return r4
        L_0x0088:
            gnu.lists.U32Vector r4 = new gnu.lists.U32Vector
            r4.<init>((gnu.lists.Sequence) r5)
            return r4
        L_0x008e:
            gnu.lists.U16Vector r4 = new gnu.lists.U16Vector
            r4.<init>((gnu.lists.Sequence) r5)
            return r4
        L_0x0094:
            gnu.lists.U8Vector r4 = new gnu.lists.U8Vector
            r4.<init>((gnu.lists.Sequence) r5)
            return r4
        L_0x009a:
            return r4
        L_0x009b:
            int r3 = r0 * 10
            int r0 = r3 + r2
            goto L_0x0001
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.LispReader.readSimpleVector(gnu.kawa.lispexpr.LispReader, char):gnu.lists.SimpleVector");
    }
}
