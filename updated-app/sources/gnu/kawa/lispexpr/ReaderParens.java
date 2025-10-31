package gnu.kawa.lispexpr;

import gnu.mapping.Values;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderParens extends ReadTableEntry {
    private static ReaderParens instance;
    char close;
    Object command;
    int kind;
    char open;

    public int getKind() {
        return this.kind;
    }

    public static ReaderParens getInstance(char open2, char close2) {
        return getInstance(open2, close2, 5);
    }

    public static ReaderParens getInstance(char open2, char close2, int kind2) {
        if (open2 != '(' || close2 != ')' || kind2 != 5) {
            return new ReaderParens(open2, close2, kind2, (Object) null);
        }
        if (instance == null) {
            instance = new ReaderParens(open2, close2, kind2, (Object) null);
        }
        return instance;
    }

    public static ReaderParens getInstance(char open2, char close2, int kind2, Object command2) {
        if (command2 == null) {
            return getInstance(open2, close2, kind2);
        }
        return new ReaderParens(open2, close2, kind2, command2);
    }

    public ReaderParens(char open2, char close2, int kind2, Object command2) {
        this.open = open2;
        this.close = close2;
        this.kind = kind2;
        this.command = command2;
    }

    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        Object r = readList((LispReader) in, ch, count, this.close);
        if (this.command == null) {
            return r;
        }
        LineBufferedReader port = in.getPort();
        Object p = ((LispReader) in).makePair(this.command, port.getLineNumber(), port.getColumnNumber());
        ((LispReader) in).setCdr(p, r);
        return p;
    }

    public static Object readList(LispReader lexer, int ch, int count, int close2) throws IOException, SyntaxException {
        ReadTableEntry entry;
        LispReader lispReader = lexer;
        int i = close2;
        LineBufferedReader port = lexer.getPort();
        char saveReadState = lispReader.pushNesting(i == 93 ? '[' : '(');
        int startLine = port.getLineNumber();
        int startColumn = port.getColumnNumber();
        Object last = null;
        try {
            Object list = lexer.makeNil();
            ReadTable readTable = ReadTable.getCurrent();
            int kind2 = false;
            boolean sawDot = false;
            Object list2 = list;
            int i2 = ch;
            while (true) {
                try {
                    int line = port.getLineNumber();
                    int column = port.getColumnNumber();
                    int ch2 = port.read();
                    if (ch2 == i) {
                        Object obj = last;
                        int i3 = kind2;
                        break;
                    }
                    if (ch2 < 0) {
                        lispReader.eofError("unexpected EOF in list starting here", startLine + 1, startColumn);
                    }
                    if (ch2 == 46) {
                        ch2 = port.peek();
                        entry = readTable.lookup(ch2);
                        Object last2 = last;
                        int sawDotCdr = kind2;
                        int kind3 = entry.getKind();
                        if (!(kind3 == 1 || kind3 == 5)) {
                            if (kind3 != 0) {
                                ch2 = 46;
                                entry = ReadTableEntry.getConstituentInstance();
                                last = last2;
                                kind2 = sawDotCdr;
                            }
                        }
                        port.skip();
                        column++;
                        if (ch2 == i) {
                            lispReader.error("unexpected '" + ((char) i) + "' after '.'");
                            break;
                        }
                        if (ch2 < 0) {
                            lispReader.eofError("unexpected EOF in list starting here", startLine + 1, startColumn);
                        }
                        if (sawDot) {
                            lispReader.error("multiple '.' in list");
                            list2 = lexer.makeNil();
                            sawDotCdr = 0;
                            last = null;
                        } else {
                            last = last2;
                        }
                        sawDot = true;
                        kind2 = sawDotCdr;
                    } else {
                        entry = readTable.lookup(ch2);
                        last = last;
                        kind2 = kind2;
                    }
                    Object value = lispReader.readValues(ch2, entry, readTable);
                    if (value == Values.empty) {
                        i = close2;
                    } else {
                        Object value2 = lispReader.handlePostfix(value, readTable, line, column);
                        if (kind2 != 0) {
                            lispReader.error("multiple values after '.'");
                            last = null;
                            list2 = lexer.makeNil();
                            kind2 = 0;
                            i = close2;
                        } else {
                            if (sawDot) {
                                kind2 = 1;
                            } else {
                                if (last == null) {
                                    line = startLine;
                                    column = startColumn - 1;
                                }
                                value2 = lispReader.makePair(value2, line, column);
                            }
                            if (last == null) {
                                list2 = value2;
                            } else {
                                lispReader.setCdr(last, value2);
                            }
                            last = value2;
                            i = close2;
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    lispReader.popNesting(saveReadState);
                    throw th;
                }
            }
            lispReader.popNesting(saveReadState);
            return list2;
        } catch (Throwable th2) {
            th = th2;
            int i4 = ch;
            lispReader.popNesting(saveReadState);
            throw th;
        }
    }
}
