package gnu.kawa.lispexpr;

import gnu.bytecode.Type;
import gnu.expr.Language;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.util.RangeTable;
import gnu.mapping.Environment;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.ThreadLocation;

public class ReadTable extends RangeTable {
    public static final int CONSTITUENT = 2;
    public static final int ILLEGAL = 0;
    public static final int MULTIPLE_ESCAPE = 4;
    public static final int NON_TERMINATING_MACRO = 6;
    public static final int SINGLE_ESCAPE = 3;
    public static final int TERMINATING_MACRO = 5;
    public static final int WHITESPACE = 1;
    static final ThreadLocation current = new ThreadLocation("read-table");
    public static int defaultBracketMode = -1;
    Environment ctorTable = null;
    protected boolean finalColonIsKeyword;
    protected boolean hexEscapeAfterBackslash = true;
    protected boolean initialColonIsKeyword;
    public char postfixLookupOperator = LispReader.TOKEN_ESCAPE_CHAR;

    public void setInitialColonIsKeyword(boolean whenInitial) {
        this.initialColonIsKeyword = whenInitial;
    }

    public void setFinalColonIsKeyword(boolean whenFinal) {
        this.finalColonIsKeyword = whenFinal;
    }

    public void initialize() {
        ReadTableEntry entry = ReadTableEntry.getWhitespaceInstance();
        set(32, entry);
        set(9, entry);
        set(10, entry);
        set(13, entry);
        set(12, entry);
        set(124, ReadTableEntry.getMultipleEscapeInstance());
        set(92, ReadTableEntry.getSingleEscapeInstance());
        set(48, 57, ReadTableEntry.getDigitInstance());
        ReadTableEntry entry2 = ReadTableEntry.getConstituentInstance();
        set(97, 122, entry2);
        set(65, 90, entry2);
        set(33, entry2);
        set(36, entry2);
        set(37, entry2);
        set(38, entry2);
        set(42, entry2);
        set(43, entry2);
        set(45, entry2);
        set(46, entry2);
        set(47, entry2);
        set(61, entry2);
        set(62, entry2);
        set(63, entry2);
        set(64, entry2);
        set(94, entry2);
        set(95, entry2);
        set(123, ReadTableEntry.brace);
        set(126, entry2);
        set(127, entry2);
        set(8, entry2);
        set(58, new ReaderColon());
        set(34, new ReaderString());
        set(35, ReaderDispatch.create(this));
        set(59, ReaderIgnoreRestOfLine.getInstance());
        set(40, ReaderParens.getInstance('(', ')'));
        set(39, new ReaderQuote(makeSymbol(LispLanguage.quote_sym)));
        set(96, new ReaderQuote(makeSymbol(LispLanguage.quasiquote_sym)));
        set(44, new ReaderQuote(makeSymbol(LispLanguage.unquote_sym), '@', makeSymbol(LispLanguage.unquotesplicing_sym)));
        setBracketMode();
    }

    public static ReadTable createInitial() {
        ReadTable tab = new ReadTable();
        tab.initialize();
        return tab;
    }

    public void setBracketMode(int mode) {
        if (mode <= 0) {
            ReadTableEntry token = ReadTableEntry.getConstituentInstance();
            set(60, token);
            if (mode < 0) {
                set(91, token);
                set(93, token);
            }
        } else {
            set(60, new ReaderTypespec());
        }
        if (mode >= 0) {
            set(91, ReaderParens.getInstance('[', ']'));
            remove(93);
        }
    }

    public void setBracketMode() {
        setBracketMode(defaultBracketMode);
    }

    /* access modifiers changed from: package-private */
    public void initCtorTable() {
        if (this.ctorTable == null) {
            this.ctorTable = Environment.make();
        }
    }

    public synchronized void putReaderCtor(String key, Procedure proc) {
        initCtorTable();
        this.ctorTable.put(key, (Object) proc);
    }

    public synchronized void putReaderCtor(String key, Type type) {
        initCtorTable();
        this.ctorTable.put(key, (Object) type);
    }

    public synchronized void putReaderCtorFld(String key, String cname, String fname) {
        initCtorTable();
        StaticFieldLocation.define(this.ctorTable, this.ctorTable.getSymbol(key), (Object) null, cname, fname);
    }

    public synchronized Object getReaderCtor(String key) {
        initCtorTable();
        return this.ctorTable.get(key, (Object) null);
    }

    public static ReadTable getCurrent() {
        ReadTable table = (ReadTable) current.get((Object) null);
        if (table == null) {
            Language language = Language.getDefaultLanguage();
            if (language instanceof LispLanguage) {
                table = ((LispLanguage) language).defaultReadTable;
            } else {
                table = createInitial();
            }
            current.set(table);
        }
        return table;
    }

    public static void setCurrent(ReadTable rt) {
        current.set(rt);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: gnu.kawa.lispexpr.ReadTableEntry} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: gnu.kawa.lispexpr.ReadTableEntry} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: gnu.kawa.lispexpr.ReadTableEntry} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: gnu.kawa.lispexpr.ReadTableEntry} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.kawa.lispexpr.ReadTableEntry lookup(int r4) {
        /*
            r3 = this;
            r0 = 0
            java.lang.Object r1 = r3.lookup(r4, r0)
            gnu.kawa.lispexpr.ReadTableEntry r1 = (gnu.kawa.lispexpr.ReadTableEntry) r1
            if (r1 != 0) goto L_0x0063
            if (r4 < 0) goto L_0x0063
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r4 >= r2) goto L_0x0063
            char r2 = (char) r4
            boolean r2 = java.lang.Character.isDigit(r2)
            if (r2 == 0) goto L_0x0020
            r2 = 48
            java.lang.Object r0 = r3.lookup(r2, r0)
            r1 = r0
            gnu.kawa.lispexpr.ReadTableEntry r1 = (gnu.kawa.lispexpr.ReadTableEntry) r1
            goto L_0x0052
        L_0x0020:
            char r2 = (char) r4
            boolean r2 = java.lang.Character.isLowerCase(r2)
            if (r2 == 0) goto L_0x0031
            r2 = 97
            java.lang.Object r0 = r3.lookup(r2, r0)
            r1 = r0
            gnu.kawa.lispexpr.ReadTableEntry r1 = (gnu.kawa.lispexpr.ReadTableEntry) r1
            goto L_0x0052
        L_0x0031:
            char r2 = (char) r4
            boolean r2 = java.lang.Character.isLetter(r2)
            if (r2 == 0) goto L_0x0042
            r2 = 65
            java.lang.Object r0 = r3.lookup(r2, r0)
            r1 = r0
            gnu.kawa.lispexpr.ReadTableEntry r1 = (gnu.kawa.lispexpr.ReadTableEntry) r1
            goto L_0x0052
        L_0x0042:
            char r2 = (char) r4
            boolean r2 = java.lang.Character.isWhitespace(r2)
            if (r2 == 0) goto L_0x0052
            r2 = 32
            java.lang.Object r0 = r3.lookup(r2, r0)
            r1 = r0
            gnu.kawa.lispexpr.ReadTableEntry r1 = (gnu.kawa.lispexpr.ReadTableEntry) r1
        L_0x0052:
            if (r1 != 0) goto L_0x005d
            r0 = 128(0x80, float:1.794E-43)
            if (r4 < r0) goto L_0x005d
            gnu.kawa.lispexpr.ReadTableEntry r0 = gnu.kawa.lispexpr.ReadTableEntry.getConstituentInstance()
            r1 = r0
        L_0x005d:
            if (r1 != 0) goto L_0x0063
            gnu.kawa.lispexpr.ReadTableEntry r1 = gnu.kawa.lispexpr.ReadTableEntry.getIllegalInstance()
        L_0x0063:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.ReadTable.lookup(int):gnu.kawa.lispexpr.ReadTableEntry");
    }

    /* access modifiers changed from: protected */
    public Object makeSymbol(String name) {
        return Namespace.EmptyNamespace.getSymbol(name.intern());
    }
}
