package gnu.xquery.lang;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.LambdaExp;
import gnu.expr.LetExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.kawa.functions.Convert;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.kawa.reflect.InstanceOf;
import gnu.kawa.reflect.OccurrenceType;
import gnu.kawa.reflect.SingletonType;
import gnu.kawa.xml.DescendantOrSelfAxis;
import gnu.kawa.xml.ElementType;
import gnu.kawa.xml.MakeAttribute;
import gnu.kawa.xml.MakeElement;
import gnu.kawa.xml.MakeWithBaseUri;
import gnu.kawa.xml.NodeType;
import gnu.kawa.xml.ParentAxis;
import gnu.kawa.xml.ProcessingInstructionType;
import gnu.kawa.xml.XDataType;
import gnu.mapping.CharArrayInPort;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.mapping.TtyInPort;
import gnu.mapping.WrappedException;
import gnu.math.IntNum;
import gnu.text.FilePath;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.Path;
import gnu.text.SourceError;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import gnu.text.URIPath;
import gnu.xml.NamespaceBinding;
import gnu.xml.TextUtils;
import gnu.xml.XName;
import gnu.xquery.util.CastableAs;
import gnu.xquery.util.NamedCollator;
import gnu.xquery.util.QNameUtils;
import gnu.xquery.util.RelativeStep;
import gnu.xquery.util.ValuesFilter;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;

public class XQParser extends Lexer {
    static final int ARROW_TOKEN = 82;
    static final int ATTRIBUTE_TOKEN = 252;
    static final int AXIS_ANCESTOR = 0;
    static final int AXIS_ANCESTOR_OR_SELF = 1;
    static final int AXIS_ATTRIBUTE = 2;
    static final int AXIS_CHILD = 3;
    static final int AXIS_DESCENDANT = 4;
    static final int AXIS_DESCENDANT_OR_SELF = 5;
    static final int AXIS_FOLLOWING = 6;
    static final int AXIS_FOLLOWING_SIBLING = 7;
    static final int AXIS_NAMESPACE = 8;
    static final int AXIS_PARENT = 9;
    static final int AXIS_PRECEDING = 10;
    static final int AXIS_PRECEDING_SIBLING = 11;
    static final int AXIS_SELF = 12;
    static final int CASE_DOLLAR_TOKEN = 247;
    static final int COLON_COLON_TOKEN = 88;
    static final int COLON_EQUAL_TOKEN = 76;
    static final int COMMENT_TOKEN = 254;
    static final int COUNT_OP_AXIS = 13;
    static final char DECIMAL_TOKEN = '1';
    static final int DECLARE_BASE_URI_TOKEN = 66;
    static final int DECLARE_BOUNDARY_SPACE_TOKEN = 83;
    static final int DECLARE_CONSTRUCTION_TOKEN = 75;
    static final int DECLARE_COPY_NAMESPACES_TOKEN = 76;
    static final int DECLARE_FUNCTION_TOKEN = 80;
    static final int DECLARE_NAMESPACE_TOKEN = 78;
    static final int DECLARE_OPTION_TOKEN = 111;
    static final int DECLARE_ORDERING_TOKEN = 85;
    static final int DECLARE_VARIABLE_TOKEN = 86;
    static final int DEFAULT_COLLATION_TOKEN = 71;
    static final int DEFAULT_ELEMENT_TOKEN = 69;
    static final int DEFAULT_FUNCTION_TOKEN = 79;
    static final int DEFAULT_ORDER_TOKEN = 72;
    static final int DEFINE_QNAME_TOKEN = 87;
    static final int DOCUMENT_TOKEN = 256;
    static final int DOTDOT_TOKEN = 51;
    static final Symbol DOT_VARNAME = Symbol.makeUninterned("$dot$");
    static final char DOUBLE_TOKEN = '2';
    static final int ELEMENT_TOKEN = 251;
    static final int EOF_TOKEN = -1;
    static final int EOL_TOKEN = 10;
    static final int EVERY_DOLLAR_TOKEN = 246;
    static final int FNAME_TOKEN = 70;
    static final int FOR_DOLLAR_TOKEN = 243;
    static final int IF_LPAREN_TOKEN = 241;
    static final int IMPORT_MODULE_TOKEN = 73;
    static final int IMPORT_SCHEMA_TOKEN = 84;
    static final char INTEGER_TOKEN = '0';
    static final Symbol LAST_VARNAME = Symbol.makeUninterned("$last$");
    static final int LET_DOLLAR_TOKEN = 244;
    static final int MODULE_NAMESPACE_TOKEN = 77;
    static final int NCNAME_COLON_TOKEN = 67;
    static final int NCNAME_TOKEN = 65;
    static final int OP_ADD = 413;
    static final int OP_AND = 401;
    static final int OP_ATTRIBUTE = 236;
    static final int OP_AXIS_FIRST = 100;
    static final int OP_BASE = 400;
    static final int OP_CASTABLE_AS = 424;
    static final int OP_CAST_AS = 425;
    static final int OP_COMMENT = 232;
    static final int OP_DIV = 416;
    static final int OP_DOCUMENT = 234;
    static final int OP_ELEMENT = 235;
    static final int OP_EMPTY_SEQUENCE = 238;
    static final int OP_EQ = 426;
    static final int OP_EQU = 402;
    static final int OP_EXCEPT = 421;
    static final int OP_GE = 431;
    static final int OP_GEQ = 407;
    static final int OP_GRT = 405;
    static final int OP_GRTGRT = 410;
    static final int OP_GT = 430;
    static final int OP_IDIV = 417;
    static final int OP_INSTANCEOF = 422;
    static final int OP_INTERSECT = 420;
    static final int OP_IS = 408;
    static final int OP_ISNOT = 409;
    static final int OP_ITEM = 237;
    static final int OP_LE = 429;
    static final int OP_LEQ = 406;
    static final int OP_LSS = 404;
    static final int OP_LSSLSS = 411;
    static final int OP_LT = 428;
    static final int OP_MOD = 418;
    static final int OP_MUL = 415;
    static final int OP_NE = 427;
    static final int OP_NEQ = 403;
    static final int OP_NODE = 230;
    static final int OP_OR = 400;
    static final int OP_PI = 233;
    static final int OP_RANGE_TO = 412;
    static final int OP_SCHEMA_ATTRIBUTE = 239;
    static final int OP_SCHEMA_ELEMENT = 240;
    static final int OP_SUB = 414;
    static final int OP_TEXT = 231;
    static final int OP_TREAT_AS = 423;
    static final int OP_UNION = 419;
    static final int OP_WHERE = 196;
    static final int ORDERED_LBRACE_TOKEN = 249;
    static final int PI_TOKEN = 255;
    static final Symbol POSITION_VARNAME = Symbol.makeUninterned("$position$");
    static final int PRAGMA_START_TOKEN = 197;
    static final int QNAME_TOKEN = 81;
    static final int SLASHSLASH_TOKEN = 68;
    static final int SOME_DOLLAR_TOKEN = 245;
    static final int STRING_TOKEN = 34;
    static final int TEXT_TOKEN = 253;
    static final int TYPESWITCH_LPAREN_TOKEN = 242;
    static final int UNORDERED_LBRACE_TOKEN = 250;
    static final int VALIDATE_LBRACE_TOKEN = 248;
    static final int XQUERY_VERSION_TOKEN = 89;
    public static final String[] axisNames = new String[13];
    static NamespaceBinding builtinNamespaces = new NamespaceBinding("local", XQuery.LOCAL_NAMESPACE, new NamespaceBinding("qexo", XQuery.QEXO_FUNCTION_NAMESPACE, new NamespaceBinding("kawa", XQuery.KAWA_FUNCTION_NAMESPACE, new NamespaceBinding("html", "http://www.w3.org/1999/xhtml", new NamespaceBinding("fn", XQuery.XQUERY_FUNCTION_NAMESPACE, new NamespaceBinding("xsi", XQuery.SCHEMA_INSTANCE_NAMESPACE, new NamespaceBinding("xs", XQuery.SCHEMA_NAMESPACE, new NamespaceBinding("xml", NamespaceBinding.XML_NAMESPACE, NamespaceBinding.predefinedXML))))))));
    public static final CastableAs castableAs = CastableAs.castableAs;
    public static final QuoteExp getExternalFunction = QuoteExp.getInstance(new PrimProcedure("gnu.xquery.lang.XQuery", "getExternal", 2));
    public static final InstanceOf instanceOf = new InstanceOf(XQuery.getInstance(), "instance");
    static final Expression makeCDATA = makeFunctionExp("gnu.kawa.xml.MakeCDATA", "makeCDATA");
    public static QuoteExp makeChildAxisStep = QuoteExp.getInstance(new PrimProcedure("gnu.kawa.xml.ChildAxis", "make", 1));
    public static QuoteExp makeDescendantAxisStep = QuoteExp.getInstance(new PrimProcedure("gnu.kawa.xml.DescendantAxis", "make", 1));
    public static Expression makeText = makeFunctionExp("gnu.kawa.xml.MakeText", "makeText");
    static PrimProcedure proc_OccurrenceType_getInstance = new PrimProcedure(ClassType.make("gnu.kawa.reflect.OccurrenceType").getDeclaredMethod("getInstance", 3));
    public static final Convert treatAs = Convert.as;
    public static boolean warnHidePreviousDeclaration = false;
    public static boolean warnOldVersion = true;
    Path baseURI = null;
    boolean baseURIDeclarationSeen;
    boolean boundarySpaceDeclarationSeen;
    boolean boundarySpacePreserve;
    int commentCount;
    Compilation comp;
    boolean constructionModeDeclarationSeen;
    boolean constructionModeStrip;
    NamespaceBinding constructorNamespaces = NamespaceBinding.predefinedXML;
    boolean copyNamespacesDeclarationSeen;
    int copyNamespacesMode = 3;
    int curColumn;
    int curLine;
    int curToken;
    Object curValue;
    NamedCollator defaultCollator = null;
    String defaultElementNamespace = "";
    char defaultEmptyOrder = 'L';
    boolean emptyOrderDeclarationSeen;
    int enclosedExpressionsSeen;
    String errorIfComment;
    Declaration[] flworDecls;
    int flworDeclsCount;
    int flworDeclsFirst;
    public Namespace[] functionNamespacePath = XQuery.defaultFunctionNamespacePath;
    XQuery interpreter;
    String libraryModuleNamespace;
    boolean orderingModeSeen;
    boolean orderingModeUnordered;
    int parseContext;
    int parseCount;
    NamespaceBinding prologNamespaces;
    private int saveToken;
    private Object saveValue;
    boolean seenDeclaration;
    int seenLast;
    int seenPosition;
    private boolean warnedOldStyleKindTest;

    static {
        axisNames[0] = "ancestor";
        axisNames[1] = "ancestor-or-self";
        axisNames[2] = "attribute";
        axisNames[3] = "child";
        axisNames[4] = "descendant";
        axisNames[5] = "descendant-or-self";
        axisNames[6] = "following";
        axisNames[7] = "following-sibling";
        axisNames[8] = "namespace";
        axisNames[9] = "parent";
        axisNames[10] = "preceding";
        axisNames[11] = "preceding-sibling";
        axisNames[12] = "self";
    }

    public void setStaticBaseUri(String uri) {
        try {
            this.baseURI = fixupStaticBaseUri(URIPath.valueOf(uri));
        } catch (Throwable th) {
            ex = th;
            if (ex instanceof WrappedException) {
                ex = ((WrappedException) ex).getCause();
            }
            error('e', "invalid URI: " + ex.getMessage());
        }
    }

    static Path fixupStaticBaseUri(Path path) {
        Path path2 = path.getAbsolute();
        if (path2 instanceof FilePath) {
            return URIPath.valueOf(path2.toURI());
        }
        return path2;
    }

    public String getStaticBaseUri() {
        Path path = this.baseURI;
        if (path == null) {
            Object value = Environment.getCurrent().get(Symbol.make("", "base-uri"), (Object) null, (Object) null);
            if (value != null && !(value instanceof Path)) {
                path = URIPath.valueOf(value.toString());
            }
            if (path == null) {
                LineBufferedReader port = getPort();
                LineBufferedReader port2 = port;
                if (port != null) {
                    path = port2.getPath();
                    if ((path instanceof FilePath) && (!path.exists() || (port2 instanceof TtyInPort) || (port2 instanceof CharArrayInPort))) {
                        path = null;
                    }
                }
            }
            if (path == null) {
                path = Path.currentPath();
            }
            path = fixupStaticBaseUri(path);
            this.baseURI = path;
        }
        return path.toString();
    }

    public String resolveAgainstBaseUri(String uri) {
        if (Path.uriSchemeSpecified(uri)) {
            return uri;
        }
        return Path.valueOf(getStaticBaseUri()).resolve(uri).toString();
    }

    /* access modifiers changed from: package-private */
    public final int skipSpace() throws IOException, SyntaxException {
        return skipSpace(true);
    }

    /* access modifiers changed from: package-private */
    public final int skipSpace(boolean verticalToo) throws IOException, SyntaxException {
        int ch;
        while (true) {
            ch = read();
            if (ch != 40) {
                if (ch != 123) {
                    if (!verticalToo) {
                        if (!(ch == 32 || ch == 9)) {
                            break;
                        }
                    } else if (ch < 0 || !Character.isWhitespace((char) ch)) {
                        break;
                    }
                } else {
                    int ch2 = read();
                    if (ch2 != 45) {
                        unread(ch2);
                        return 123;
                    }
                    int ch3 = read();
                    if (ch3 != 45) {
                        unread(ch3);
                        unread(45);
                        return 123;
                    }
                    skipOldComment();
                }
            } else if (!checkNext(':')) {
                return 40;
            } else {
                skipComment();
            }
        }
        return ch;
    }

    /* access modifiers changed from: package-private */
    public final void skipToSemicolon() throws IOException {
        int next;
        do {
            next = read();
            if (next < 0 || next == 59) {
            }
            next = read();
            return;
        } while (next == 59);
    }

    /* access modifiers changed from: package-private */
    public final void skipOldComment() throws IOException, SyntaxException {
        int seenDashes = 0;
        int startLine = getLineNumber() + 1;
        int startColumn = getColumnNumber() - 2;
        warnOldVersion("use (: :) instead of old-style comment {-- --}");
        while (true) {
            int ch = read();
            if (ch == 45) {
                seenDashes++;
            } else if (ch == 125 && seenDashes >= 2) {
                return;
            } else {
                if (ch < 0) {
                    this.curLine = startLine;
                    this.curColumn = startColumn;
                    eofError("non-terminated comment starting here");
                } else {
                    seenDashes = 0;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void skipComment() throws IOException, SyntaxException {
        this.commentCount++;
        int startLine = getLineNumber() + 1;
        int startColumn = getColumnNumber() - 1;
        if (this.errorIfComment != null) {
            this.curLine = startLine;
            this.curColumn = startColumn;
            error('e', this.errorIfComment);
        }
        int prev = 0;
        int commentNesting = 0;
        char saveReadState = pushNesting(':');
        while (true) {
            int ch = read();
            if (ch == 58) {
                if (prev == 40) {
                    commentNesting++;
                    ch = 0;
                }
            } else if (ch == 41 && prev == 58) {
                if (commentNesting == 0) {
                    popNesting(saveReadState);
                    return;
                }
                commentNesting--;
            } else if (ch < 0) {
                this.curLine = startLine;
                this.curColumn = startColumn;
                eofError("non-terminated comment starting here");
            }
            prev = ch;
        }
    }

    /* access modifiers changed from: package-private */
    public final int peekNonSpace(String message) throws IOException, SyntaxException {
        int ch = skipSpace();
        if (ch < 0) {
            eofError(message);
        }
        unread(ch);
        return ch;
    }

    public void mark() throws IOException {
        super.mark();
        this.saveToken = this.curToken;
        this.saveValue = this.curValue;
    }

    public void reset() throws IOException {
        this.curToken = this.saveToken;
        this.curValue = this.saveValue;
        super.reset();
    }

    private int setToken(int token, int width) {
        this.curToken = token;
        this.curLine = this.port.getLineNumber() + 1;
        this.curColumn = (this.port.getColumnNumber() + 1) - width;
        return token;
    }

    /* access modifiers changed from: package-private */
    public void checkSeparator(char ch) {
        if (XName.isNameStart(ch)) {
            error('e', "missing separator", "XPST0003");
        }
    }

    /* access modifiers changed from: package-private */
    public int getRawToken() throws IOException, SyntaxException {
        int next;
        int next2;
        while (true) {
            int next3 = readUnicodeChar();
            boolean seenDot = false;
            if (next3 < 0) {
                return setToken(-1, 0);
            }
            if (next3 == 10 || next3 == 13) {
                if (this.nesting <= 0) {
                    return setToken(10, 0);
                }
            } else if (next3 == 40) {
                if (checkNext(':')) {
                    skipComment();
                } else if (checkNext('#')) {
                    return setToken(PRAGMA_START_TOKEN, 2);
                } else {
                    return setToken(40, 1);
                }
            } else if (next3 == 123) {
                if (!checkNext('-')) {
                    return setToken(123, 1);
                }
                if (read() != 45) {
                    unread();
                    unread();
                    return setToken(123, 1);
                }
                skipOldComment();
            } else if (!(next3 == 32 || next3 == 9)) {
                this.tokenBufferLength = 0;
                this.curLine = this.port.getLineNumber() + 1;
                this.curColumn = this.port.getColumnNumber();
                char ch = (char) next3;
                switch (ch) {
                    case '!':
                        if (checkNext('=') != 0) {
                            ch = 403;
                            break;
                        }
                        break;
                    case '\"':
                    case '\'':
                        char saveReadState = pushNesting((char) next3);
                        while (true) {
                            int next4 = readUnicodeChar();
                            if (next4 < 0) {
                                eofError("unexpected end-of-file in string starting here");
                            }
                            if (next4 == 38) {
                                parseEntityOrCharRef();
                            } else {
                                if (ch == next4) {
                                    if (ch != peek()) {
                                        popNesting(saveReadState);
                                        ch = '\"';
                                        break;
                                    } else {
                                        next4 = read();
                                    }
                                }
                                tokenBufferAppend(next4);
                            }
                        }
                    case '$':
                    case ')':
                    case ',':
                    case ';':
                    case '?':
                    case '@':
                    case '[':
                    case ']':
                    case '}':
                        break;
                    case '*':
                        ch = 415;
                        break;
                    case '+':
                        ch = 413;
                        break;
                    case '-':
                        ch = 414;
                        break;
                    case '/':
                        if (checkNext('/')) {
                            ch = 'D';
                            break;
                        }
                        break;
                    case ':':
                        if (!checkNext('=')) {
                            if (checkNext(':')) {
                                ch = 'X';
                                break;
                            }
                        } else {
                            ch = 'L';
                            break;
                        }
                        break;
                    case '<':
                        ch = checkNext('=') ? 406 : checkNext('<') ? (char) 411 : 404;
                        break;
                    case '=':
                        if (checkNext('>')) {
                        }
                        ch = 402;
                        break;
                    case '>':
                        ch = checkNext('=') ? 407 : checkNext('>') ? (char) 410 : 405;
                        break;
                    case '|':
                        ch = 419;
                        break;
                    default:
                        if (!Character.isDigit(ch) && (ch != '.' || !Character.isDigit((char) peek()))) {
                            if (ch != '.') {
                                if (!XName.isNameStart(ch)) {
                                    if (ch >= ' ' && ch < 127) {
                                        syntaxError("invalid character '" + ch + '\'');
                                        break;
                                    } else {
                                        syntaxError("invalid character '\\u" + Integer.toHexString(ch) + '\'');
                                        break;
                                    }
                                } else {
                                    do {
                                        tokenBufferAppend(ch);
                                        next2 = read();
                                        ch = (char) next2;
                                    } while (XName.isNamePart(ch));
                                    if (next2 >= 0) {
                                        if (next2 != 58) {
                                            ch = 'A';
                                        } else {
                                            next2 = read();
                                            if (next2 < 0) {
                                                eofError("unexpected end-of-file after NAME ':'");
                                            }
                                            char ch2 = (char) next2;
                                            if (XName.isNameStart(ch2)) {
                                                tokenBufferAppend(58);
                                                do {
                                                    tokenBufferAppend(ch2);
                                                    next2 = read();
                                                    ch2 = (char) next2;
                                                } while (XName.isNamePart(ch2));
                                                ch = 'Q';
                                            } else if (ch2 == '=') {
                                                unread(ch2);
                                                ch = 'A';
                                            } else {
                                                ch = 'C';
                                            }
                                        }
                                        unread(next2);
                                        break;
                                    } else {
                                        ch = 'A';
                                        break;
                                    }
                                }
                            } else if (checkNext('.')) {
                                ch = '3';
                                break;
                            }
                        } else {
                            if (ch == '.') {
                                seenDot = true;
                            }
                            while (true) {
                                tokenBufferAppend(ch);
                                next = read();
                                if (next >= 0) {
                                    ch = (char) next;
                                    if (ch == '.') {
                                        if (!seenDot) {
                                            seenDot = true;
                                        }
                                    } else if (!Character.isDigit(ch)) {
                                    }
                                }
                            }
                            if (next != 101 && next != 69) {
                                char ch3 = seenDot ? DECIMAL_TOKEN : INTEGER_TOKEN;
                                if (next >= 0) {
                                    checkSeparator((char) next);
                                    unread(next);
                                }
                                ch = ch3;
                                break;
                            } else {
                                tokenBufferAppend((char) next);
                                int next5 = read();
                                if (next5 == 43 || next5 == 45) {
                                    tokenBufferAppend(next5);
                                    next5 = read();
                                }
                                int expDigits = 0;
                                while (true) {
                                    if (next5 >= 0) {
                                        char ch4 = (char) next5;
                                        if (!Character.isDigit(ch4)) {
                                            checkSeparator(ch4);
                                            unread();
                                        } else {
                                            tokenBufferAppend(ch4);
                                            next5 = read();
                                            expDigits++;
                                        }
                                    }
                                }
                                if (expDigits == 0) {
                                    error('e', "no digits following exponent", "XPST0003");
                                }
                                ch = '2';
                                break;
                            }
                        }
                        break;
                }
                this.curToken = ch;
                return ch;
            }
        }
    }

    public void getDelimited(String delimiter) throws IOException, SyntaxException {
        if (!readDelimited(delimiter)) {
            eofError("unexpected end-of-file looking for '" + delimiter + '\'');
        }
    }

    public void appendNamedEntity(String name) {
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
            error("unknown enity reference: '" + name2 + "'");
        }
        tokenBufferAppend(ch);
    }

    /* access modifiers changed from: package-private */
    public boolean match(String word1, String word2, boolean force) throws IOException, SyntaxException {
        if (!match(word1)) {
            return false;
        }
        mark();
        getRawToken();
        if (match(word2)) {
            reset();
            getRawToken();
            return true;
        }
        reset();
        if (!force) {
            return false;
        }
        error('e', "'" + word1 + "' must be followed by '" + word2 + "'", "XPST0003");
        return true;
    }

    /* access modifiers changed from: package-private */
    public int peekOperator() throws IOException, SyntaxException {
        while (this.curToken == 10) {
            if (this.nesting == 0) {
                return 10;
            }
            getRawToken();
        }
        if (this.curToken == 65) {
            switch (this.tokenBufferLength) {
                case 2:
                    char c1 = this.tokenBuffer[0];
                    char c2 = this.tokenBuffer[1];
                    if (c1 != 'o' || c2 != 'r') {
                        if (c1 != 't' || c2 != 'o') {
                            if (c1 != 'i' || c2 != 's') {
                                if (c1 != 'e' || c2 != 'q') {
                                    if (c1 != 'n' || c2 != 'e') {
                                        if (c1 != 'g') {
                                            if (c1 == 'l') {
                                                if (c2 != 'e') {
                                                    if (c2 == 't') {
                                                        this.curToken = OP_LT;
                                                        break;
                                                    }
                                                } else {
                                                    this.curToken = OP_LE;
                                                    break;
                                                }
                                            }
                                        } else if (c2 != 'e') {
                                            if (c2 == 't') {
                                                this.curToken = OP_GT;
                                                break;
                                            }
                                        } else {
                                            this.curToken = OP_GE;
                                            break;
                                        }
                                    } else {
                                        this.curToken = OP_NE;
                                        break;
                                    }
                                } else {
                                    this.curToken = OP_EQ;
                                    break;
                                }
                            } else {
                                this.curToken = 408;
                                break;
                            }
                        } else {
                            this.curToken = 412;
                            break;
                        }
                    } else {
                        this.curToken = 400;
                        break;
                    }
                    break;
                case 3:
                    char c12 = this.tokenBuffer[0];
                    char c22 = this.tokenBuffer[1];
                    char c3 = this.tokenBuffer[2];
                    if (c12 != 'a') {
                        if (c12 != 'm') {
                            if (c12 == 'd' && c22 == 'i' && c3 == 'v') {
                                this.curToken = 416;
                                break;
                            }
                        } else {
                            if (c22 == 'u' && c3 == 'l') {
                                this.curToken = 415;
                            }
                            if (c22 == 'o' && c3 == 'd') {
                                this.curToken = 418;
                                break;
                            }
                        }
                    } else if (c22 == 'n' && c3 == 'd') {
                        this.curToken = 401;
                        break;
                    }
                case 4:
                    if (!match("idiv")) {
                        if (match("cast", "as", true)) {
                            this.curToken = OP_CAST_AS;
                            break;
                        }
                    } else {
                        this.curToken = 417;
                        break;
                    }
                    break;
                case 5:
                    if (!match("where")) {
                        if (!match("isnot")) {
                            if (!match("union")) {
                                if (match("treat", "as", true)) {
                                    this.curToken = 423;
                                    break;
                                }
                            } else {
                                this.curToken = 419;
                                break;
                            }
                        } else {
                            this.curToken = 409;
                            break;
                        }
                    } else {
                        this.curToken = 196;
                        break;
                    }
                    break;
                case 6:
                    if (match("except")) {
                        this.curToken = 421;
                        break;
                    }
                    break;
                case 8:
                    if (!match("instance", "of", true)) {
                        if (match("castable", "as", true)) {
                            this.curToken = OP_CASTABLE_AS;
                            break;
                        }
                    } else {
                        this.curToken = 422;
                        break;
                    }
                    break;
                case 9:
                    if (match("intersect")) {
                        this.curToken = 420;
                        break;
                    }
                    break;
                case 10:
                    if (match("instanceof")) {
                        warnOldVersion("use 'instanceof of' (two words) instead of 'instanceof'");
                        this.curToken = 422;
                        break;
                    }
                    break;
            }
        }
        return this.curToken;
    }

    private boolean lookingAt(String word0, String word1) throws IOException, SyntaxException {
        if (!word0.equals(this.curValue)) {
            return false;
        }
        int i = 0;
        int len = word1.length();
        while (true) {
            int ch = read();
            if (i != len) {
                if (ch < 0) {
                    break;
                }
                int i2 = i + 1;
                if (ch != word1.charAt(i)) {
                    i = i2;
                    break;
                }
                i = i2;
            } else if (ch < 0) {
                return true;
            } else {
                if (!XName.isNamePart((char) ch)) {
                    unread();
                    return true;
                }
                i++;
            }
        }
        this.port.skip(-i);
        return false;
    }

    /* access modifiers changed from: package-private */
    public int getAxis() {
        String name = new String(this.tokenBuffer, 0, this.tokenBufferLength).intern();
        int i = 13;
        do {
            i--;
            if (i < 0 || axisNames[i] == name) {
                if (i < 0 || i == 8) {
                    error('e', "unknown axis name '" + name + '\'', "XPST0003");
                    i = 3;
                }
            }
            i--;
            break;
        } while (axisNames[i] == name);
        error('e', "unknown axis name '" + name + '\'', "XPST0003");
        i = 3;
        return (char) (i + 100);
    }

    /* access modifiers changed from: package-private */
    public int peekOperand() throws IOException, SyntaxException {
        while (this.curToken == 10) {
            getRawToken();
        }
        if (this.curToken == 65 || this.curToken == 81) {
            int next = skipSpace(this.nesting != 0);
            switch (this.tokenBuffer[0]) {
                case 'a':
                    if (match("attribute")) {
                        if (next == 40) {
                            this.curToken = OP_ATTRIBUTE;
                            return OP_ATTRIBUTE;
                        } else if (next == 123 || XName.isNameStart((char) next)) {
                            unread();
                            this.curToken = 252;
                            return 252;
                        }
                    }
                    break;
                case 'c':
                    if (match("comment")) {
                        if (next == 40) {
                            this.curToken = OP_COMMENT;
                            return OP_COMMENT;
                        } else if (next == 123) {
                            unread();
                            this.curToken = 254;
                            return 254;
                        }
                    }
                    break;
                case 'd':
                    if (next == 123 && match("document")) {
                        unread();
                        this.curToken = 256;
                        return 256;
                    } else if (next == 40 && match("document-node")) {
                        this.curToken = OP_DOCUMENT;
                        return OP_DOCUMENT;
                    }
                    break;
                case 'e':
                    if (match("element")) {
                        if (next == 40) {
                            this.curToken = OP_ELEMENT;
                            return OP_ELEMENT;
                        } else if (next == 123 || XName.isNameStart((char) next)) {
                            unread();
                            this.curToken = 251;
                            return 251;
                        }
                    } else if (next == 40 && match("empty-sequence")) {
                        this.curToken = OP_EMPTY_SEQUENCE;
                        return OP_EMPTY_SEQUENCE;
                    } else if (next == 36 && match("every")) {
                        this.curToken = EVERY_DOLLAR_TOKEN;
                        return EVERY_DOLLAR_TOKEN;
                    }
                    break;
                case 'f':
                    if (next == 36 && match("for")) {
                        this.curToken = FOR_DOLLAR_TOKEN;
                        return FOR_DOLLAR_TOKEN;
                    }
                case 'i':
                    if (next == 40 && match("if")) {
                        this.curToken = 241;
                        return 241;
                    } else if (next == 40 && match("item")) {
                        this.curToken = OP_ITEM;
                        return OP_ITEM;
                    }
                    break;
                case 'l':
                    if (next == 36 && match("let")) {
                        this.curToken = LET_DOLLAR_TOKEN;
                        return LET_DOLLAR_TOKEN;
                    }
                case 'n':
                    if (next == 40 && match("node")) {
                        this.curToken = OP_NODE;
                        return OP_NODE;
                    }
                case 'o':
                    if (next == 123 && match("ordered")) {
                        this.curToken = ORDERED_LBRACE_TOKEN;
                        return ORDERED_LBRACE_TOKEN;
                    }
                case 'p':
                    if (match("processing-instruction")) {
                        if (next == 40) {
                            this.curToken = 233;
                            return 233;
                        } else if (next == 123 || XName.isNameStart((char) next)) {
                            unread();
                            this.curToken = 255;
                            return 255;
                        }
                    }
                    break;
                case 's':
                    if (next == 36 && match("some")) {
                        this.curToken = SOME_DOLLAR_TOKEN;
                        return SOME_DOLLAR_TOKEN;
                    } else if (next == 40 && match("schema-attribute")) {
                        this.curToken = OP_SCHEMA_ATTRIBUTE;
                        return OP_SCHEMA_ATTRIBUTE;
                    } else if (next == 40 && match("schema-element")) {
                        this.curToken = OP_SCHEMA_ELEMENT;
                        return OP_SCHEMA_ELEMENT;
                    }
                    break;
                case 't':
                    if (match(PropertyTypeConstants.PROPERTY_TYPE_TEXT)) {
                        if (next == 40) {
                            this.curToken = OP_TEXT;
                            return OP_TEXT;
                        } else if (next == 123) {
                            unread();
                            this.curToken = 253;
                            return 253;
                        }
                    }
                    if (next == 40 && match("typeswitch")) {
                        this.curToken = 242;
                        return 242;
                    }
                case 'u':
                    if (next == 123 && match("unordered")) {
                        this.curToken = UNORDERED_LBRACE_TOKEN;
                        return UNORDERED_LBRACE_TOKEN;
                    }
                case 'v':
                    if (next == 123 && match("validate")) {
                        this.curToken = VALIDATE_LBRACE_TOKEN;
                        return VALIDATE_LBRACE_TOKEN;
                    }
            }
            if (next == 40 && peek() != 58) {
                this.curToken = 70;
                return 70;
            } else if (next == 58 && peek() == 58) {
                int axis = getAxis();
                this.curToken = axis;
                return axis;
            } else {
                this.curValue = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                switch (next) {
                    case 98:
                        if (lookingAt("declare", "ase-uri")) {
                            this.curToken = 66;
                            return 66;
                        } else if (lookingAt("declare", "oundary-space")) {
                            this.curToken = 83;
                            return 83;
                        }
                        break;
                    case 99:
                        if (lookingAt("declare", "onstruction")) {
                            this.curToken = 75;
                            return 75;
                        } else if (lookingAt("declare", "opy-namespaces")) {
                            this.curToken = 76;
                            return 76;
                        }
                        break;
                    case 100:
                        if (lookingAt("declare", "efault")) {
                            getRawToken();
                            if (match("function")) {
                                this.curToken = 79;
                                return 79;
                            } else if (match("element")) {
                                this.curToken = 69;
                                return 69;
                            } else if (match("collation")) {
                                this.curToken = 71;
                                return 71;
                            } else if (match("order")) {
                                this.curToken = 72;
                                return 72;
                            } else {
                                error("unrecognized/unimplemented 'declare default'");
                                skipToSemicolon();
                                return peekOperand();
                            }
                        }
                        break;
                    case 101:
                        break;
                    case 102:
                        if (lookingAt("declare", "unction")) {
                            this.curToken = 80;
                            return 80;
                        } else if (lookingAt("define", "unction")) {
                            warnOldVersion("replace 'define function' by 'declare function'");
                            this.curToken = 80;
                            return 80;
                        } else if (lookingAt("default", "unction")) {
                            warnOldVersion("replace 'default function' by 'declare default function namespace'");
                            this.curToken = 79;
                            return 79;
                        }
                        break;
                    case 109:
                        if (lookingAt("import", "odule")) {
                            this.curToken = 73;
                            return 73;
                        }
                        break;
                    case 110:
                        if (lookingAt("declare", "amespace")) {
                            this.curToken = 78;
                            return 78;
                        } else if (lookingAt("default", "amespace")) {
                            warnOldVersion("replace 'default namespace' by 'declare default element namespace'");
                            this.curToken = 69;
                            return 69;
                        } else if (lookingAt("module", "amespace")) {
                            this.curToken = 77;
                            return 77;
                        }
                        break;
                    case 111:
                        if (lookingAt("declare", "rdering")) {
                            this.curToken = 85;
                            return 85;
                        } else if (lookingAt("declare", "ption")) {
                            this.curToken = 111;
                            return 111;
                        }
                        break;
                    case 115:
                        if (lookingAt("import", "chema")) {
                            this.curToken = 84;
                            return 84;
                        }
                        break;
                    case 118:
                        if (lookingAt("declare", "ariable")) {
                            this.curToken = 86;
                            return 86;
                        } else if (lookingAt("define", "ariable")) {
                            warnOldVersion("replace 'define variable' by 'declare variable'");
                            this.curToken = 86;
                            return 86;
                        } else if (lookingAt("xquery", "ersion")) {
                            this.curToken = 89;
                            return 89;
                        }
                        break;
                    case 120:
                        if (lookingAt("declare", "mlspace")) {
                            warnOldVersion("replace 'define xmlspace' by 'declare boundary-space'");
                            this.curToken = 83;
                            return 83;
                        }
                        break;
                }
                if (lookingAt("default", "lement")) {
                    warnOldVersion("replace 'default element' by 'declare default element namespace'");
                    this.curToken = 69;
                    return 69;
                }
                if (next >= 0) {
                    unread();
                    if (XName.isNameStart((char) next) && this.curValue.equals("define")) {
                        getRawToken();
                        this.curToken = 87;
                    }
                }
                return this.curToken;
            }
        } else {
            if (this.curToken == 67) {
                int next2 = read();
                if (next2 == 58) {
                    this.curToken = getAxis();
                } else {
                    unread(next2);
                }
            }
            return this.curToken;
        }
    }

    /* access modifiers changed from: package-private */
    public void checkAllowedNamespaceDeclaration(String prefix, String uri, boolean inConstructor) {
        boolean xmlPrefix = "xml".equals(prefix);
        if (NamespaceBinding.XML_NAMESPACE.equals(uri)) {
            if (!xmlPrefix || !inConstructor) {
                error('e', "namespace uri cannot be the same as the prefined xml namespace", "XQST0070");
            }
        } else if (xmlPrefix || "xmlns".equals(prefix)) {
            error('e', "namespace prefix cannot be 'xml' or 'xmlns'", "XQST0070");
        }
    }

    /* access modifiers changed from: package-private */
    public void pushNamespace(String prefix, String uri) {
        if (uri.length() == 0) {
            uri = null;
        }
        this.prologNamespaces = new NamespaceBinding(prefix, uri, this.prologNamespaces);
    }

    public XQParser(InPort port, SourceMessages messages, XQuery interp) {
        super(port, messages);
        this.interpreter = interp;
        this.nesting = 1;
        this.prologNamespaces = builtinNamespaces;
    }

    public void setInteractive(boolean v) {
        if (this.interactive != v) {
            int i = this.nesting;
            this.nesting = v ? i - 1 : i + 1;
        }
        this.interactive = v;
    }

    private static final int priority(int opcode) {
        switch (opcode) {
            case 400:
                return 1;
            case 401:
                return 2;
            case 402:
            case 403:
            case 404:
            case 405:
            case 406:
            case 407:
            case 408:
            case 409:
            case 410:
            case 411:
            case OP_EQ /*426*/:
            case OP_NE /*427*/:
            case OP_LT /*428*/:
            case OP_LE /*429*/:
            case OP_GT /*430*/:
            case OP_GE /*431*/:
                return 3;
            case 412:
                return 4;
            case 413:
            case 414:
                return 5;
            case 415:
            case 416:
            case 417:
            case 418:
                return 6;
            case 419:
                return 7;
            case 420:
            case 421:
                return 8;
            case 422:
                return 9;
            case 423:
                return 10;
            case OP_CASTABLE_AS /*424*/:
                return 11;
            case OP_CAST_AS /*425*/:
                return 12;
            default:
                return 0;
        }
    }

    static Expression makeBinary(Expression func, Expression exp1, Expression exp2) {
        return new ApplyExp(func, exp1, exp2);
    }

    static Expression makeExprSequence(Expression exp1, Expression exp2) {
        return makeBinary(makeFunctionExp("gnu.kawa.functions.AppendValues", "appendValues"), exp1, exp2);
    }

    /* access modifiers changed from: package-private */
    public Expression makeBinary(int op, Expression exp1, Expression exp2) throws IOException, SyntaxException {
        Expression func;
        switch (op) {
            case 402:
                func = makeFunctionExp("gnu.xquery.util.Compare", "=");
                break;
            case 403:
                func = makeFunctionExp("gnu.xquery.util.Compare", "!=");
                break;
            case 404:
                func = makeFunctionExp("gnu.xquery.util.Compare", "<");
                break;
            case 405:
                func = makeFunctionExp("gnu.xquery.util.Compare", ">");
                break;
            case 406:
                func = makeFunctionExp("gnu.xquery.util.Compare", "<=");
                break;
            case 407:
                func = makeFunctionExp("gnu.xquery.util.Compare", ">=");
                break;
            case 408:
                func = makeFunctionExp("gnu.kawa.xml.NodeCompare", "$Eq", "is");
                break;
            case 409:
                func = makeFunctionExp("gnu.kawa.xml.NodeCompare", "$Ne", "isnot");
                break;
            case 410:
                func = makeFunctionExp("gnu.kawa.xml.NodeCompare", "$Gr", ">>");
                break;
            case 411:
                func = makeFunctionExp("gnu.kawa.xml.NodeCompare", "$Ls", "<<");
                break;
            case 412:
                func = makeFunctionExp("gnu.xquery.util.IntegerRange", "integerRange");
                break;
            case 413:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "add", "+");
                break;
            case 414:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "sub", "-");
                break;
            case 415:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "mul", "*");
                break;
            case 416:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "div", "div");
                break;
            case 417:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "idiv", "idiv");
                break;
            case 418:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "mod", "mod");
                break;
            case 419:
                func = makeFunctionExp("gnu.kawa.xml.UnionNodes", "unionNodes");
                break;
            case 420:
                func = makeFunctionExp("gnu.kawa.xml.IntersectNodes", "intersectNodes");
                break;
            case 421:
                func = makeFunctionExp("gnu.kawa.xml.IntersectNodes", "exceptNodes");
                break;
            case OP_EQ /*426*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valEq", "eq");
                break;
            case OP_NE /*427*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valNe", "ne");
                break;
            case OP_LT /*428*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valLt", "lt");
                break;
            case OP_LE /*429*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valLe", "le");
                break;
            case OP_GT /*430*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valGt", "gt");
                break;
            case OP_GE /*431*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valGe", "ge");
                break;
            default:
                return syntaxError("unimplemented binary op: " + op);
        }
        return makeBinary(func, exp1, exp2);
    }

    private void parseSimpleKindType() throws IOException, SyntaxException {
        getRawToken();
        if (this.curToken == 41) {
            getRawToken();
        } else {
            error("expected ')'");
        }
    }

    public Expression parseNamedNodeType(boolean attribute) throws IOException, SyntaxException {
        Expression qname;
        Expression tname = null;
        getRawToken();
        if (this.curToken == 41) {
            qname = QuoteExp.getInstance(ElementType.MATCH_ANY_QNAME);
            getRawToken();
        } else {
            if (this.curToken == 81 || this.curToken == 65) {
                qname = parseNameTest(attribute);
            } else {
                if (this.curToken != 415) {
                    syntaxError("expected QName or *");
                }
                qname = QuoteExp.getInstance(ElementType.MATCH_ANY_QNAME);
            }
            getRawToken();
            if (this.curToken == 44) {
                getRawToken();
                if (this.curToken == 81 || this.curToken == 65) {
                    tname = parseDataType();
                } else {
                    syntaxError("expected QName");
                }
            }
            if (this.curToken == 41) {
                getRawToken();
            } else {
                error("expected ')' after element");
            }
        }
        return makeNamedNodeType(attribute, qname, tname);
    }

    static Expression makeNamedNodeType(boolean attribute, Expression qname, Expression tname) {
        Expression[] expressionArr = new Expression[2];
        ApplyExp elt = new ApplyExp(ClassType.make(attribute ? "gnu.kawa.xml.AttributeType" : "gnu.kawa.xml.ElementType").getDeclaredMethod("make", 1), qname);
        elt.setFlag(4);
        if (tname == null) {
            return elt;
        }
        return new BeginExp(tname, elt);
    }

    private void warnOldStyleKindTest() {
        if (!this.warnedOldStyleKindTest) {
            error('w', "old-style KindTest - first one here");
            this.warnedOldStyleKindTest = true;
        }
    }

    public Expression parseOptionalTypeDeclaration() throws IOException, SyntaxException {
        if (!match("as")) {
            return null;
        }
        getRawToken();
        return parseDataType();
    }

    public Expression parseDataType() throws IOException, SyntaxException {
        int max;
        int min;
        Expression etype = parseItemType();
        if (etype == null) {
            if (this.curToken != OP_EMPTY_SEQUENCE) {
                return syntaxError("bad syntax - expected DataType");
            }
            parseSimpleKindType();
            if (this.curToken == 63 || this.curToken == 413 || this.curToken == 415) {
                getRawToken();
                return syntaxError("occurrence-indicator meaningless after empty-sequence()");
            }
            etype = QuoteExp.getInstance(OccurrenceType.emptySequenceType);
            min = 0;
            max = 0;
        } else if (this.curToken == 63) {
            min = 0;
            max = 1;
        } else if (this.curToken == 413) {
            min = 1;
            max = -1;
        } else if (this.curToken == 415) {
            min = 0;
            max = -1;
        } else {
            min = 1;
            max = 1;
        }
        if (this.parseContext == 67 && max != 1) {
            return syntaxError("type to 'cast as' or 'castable as' must be a 'SingleType'");
        }
        if (min == max) {
            return etype;
        }
        getRawToken();
        ApplyExp otype = new ApplyExp((Procedure) proc_OccurrenceType_getInstance, etype, QuoteExp.getInstance(IntNum.make(min)), QuoteExp.getInstance(IntNum.make(max)));
        otype.setFlag(4);
        return otype;
    }

    public Expression parseMaybeKindTest() throws IOException, SyntaxException {
        Type type;
        boolean z = false;
        switch (this.curToken) {
            case OP_NODE /*230*/:
                parseSimpleKindType();
                type = NodeType.anyNodeTest;
                break;
            case OP_TEXT /*231*/:
                parseSimpleKindType();
                type = NodeType.textNodeTest;
                break;
            case OP_COMMENT /*232*/:
                parseSimpleKindType();
                type = NodeType.commentNodeTest;
                break;
            case 233:
                getRawToken();
                String piTarget = null;
                if (this.curToken == 65 || this.curToken == 34) {
                    piTarget = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                    getRawToken();
                }
                if (this.curToken == 41) {
                    getRawToken();
                } else {
                    error("expected ')'");
                }
                type = ProcessingInstructionType.getInstance(piTarget);
                break;
            case OP_DOCUMENT /*234*/:
                parseSimpleKindType();
                type = NodeType.documentNodeTest;
                break;
            case OP_ELEMENT /*235*/:
            case OP_ATTRIBUTE /*236*/:
                if (this.curToken == OP_ATTRIBUTE) {
                    z = true;
                }
                return parseNamedNodeType(z);
            default:
                return null;
        }
        return QuoteExp.getInstance(type);
    }

    public Expression parseItemType() throws IOException, SyntaxException {
        Type type;
        peekOperand();
        Expression etype = parseMaybeKindTest();
        if (etype != null) {
            if (this.parseContext != 67) {
                return etype;
            }
            type = XDataType.anyAtomicType;
        } else if (this.curToken == OP_ITEM) {
            parseSimpleKindType();
            type = SingletonType.getInstance();
        } else if (this.curToken != 65 && this.curToken != 81) {
            return null;
        } else {
            ReferenceExp rexp = new ReferenceExp((Object) new String(this.tokenBuffer, 0, this.tokenBufferLength));
            rexp.setFlag(16);
            maybeSetLine((Expression) rexp, this.curLine, this.curColumn);
            getRawToken();
            return rexp;
        }
        return QuoteExp.getInstance(type);
    }

    /* access modifiers changed from: package-private */
    public Object parseURILiteral() throws IOException, SyntaxException {
        getRawToken();
        if (this.curToken != 34) {
            return declError("expected a URILiteral");
        }
        return TextUtils.replaceWhitespace(new String(this.tokenBuffer, 0, this.tokenBufferLength), true);
    }

    /* access modifiers changed from: package-private */
    public Expression parseExpr() throws IOException, SyntaxException {
        return parseExprSingle();
    }

    /* access modifiers changed from: package-private */
    public final Expression parseExprSingle() throws IOException, SyntaxException {
        int i = this.curLine;
        int i2 = this.curColumn;
        peekOperand();
        switch (this.curToken) {
            case 241:
                return parseIfExpr();
            case 242:
                return parseTypeSwitch();
            case FOR_DOLLAR_TOKEN /*243*/:
                return parseFLWRExpression(true);
            case LET_DOLLAR_TOKEN /*244*/:
                return parseFLWRExpression(false);
            case SOME_DOLLAR_TOKEN /*245*/:
                return parseQuantifiedExpr(false);
            case EVERY_DOLLAR_TOKEN /*246*/:
                return parseQuantifiedExpr(true);
            default:
                return parseBinaryExpr(priority(400));
        }
    }

    /* access modifiers changed from: package-private */
    public Expression parseBinaryExpr(int prio) throws IOException, SyntaxException {
        int tokPriority;
        Expression func;
        Expression exp = parseUnaryExpr();
        while (true) {
            int token = peekOperator();
            if (token == 10 || ((token == 404 && peek() == 47) || (tokPriority = priority(token)) < prio)) {
                return exp;
            }
            char saveReadState = pushNesting('%');
            getRawToken();
            popNesting(saveReadState);
            if (token >= 422 && token <= OP_CAST_AS) {
                if (token == OP_CAST_AS || token == OP_CASTABLE_AS) {
                    this.parseContext = 67;
                }
                Expression type = parseDataType();
                this.parseContext = 0;
                Expression[] args = new Expression[2];
                switch (token) {
                    case 422:
                        args[0] = exp;
                        args[1] = type;
                        func = makeFunctionExp("gnu.xquery.lang.XQParser", "instanceOf");
                        break;
                    case 423:
                        args[0] = type;
                        args[1] = exp;
                        func = makeFunctionExp("gnu.xquery.lang.XQParser", "treatAs");
                        break;
                    case OP_CASTABLE_AS /*424*/:
                        args[0] = exp;
                        args[1] = type;
                        func = new ReferenceExp(XQResolveNames.castableAsDecl);
                        break;
                    default:
                        args[0] = type;
                        args[1] = exp;
                        func = new ReferenceExp(XQResolveNames.castAsDecl);
                        break;
                }
                exp = new ApplyExp(func, args);
            } else if (token == 422) {
                exp = new ApplyExp(makeFunctionExp("gnu.xquery.lang.XQParser", "instanceOf"), exp, parseDataType());
            } else {
                Expression exp2 = parseBinaryExpr(tokPriority + 1);
                if (token == 401) {
                    exp = new IfExp(booleanValue(exp), booleanValue(exp2), XQuery.falseExp);
                } else if (token == 400) {
                    exp = new IfExp(booleanValue(exp), XQuery.trueExp, booleanValue(exp2));
                } else {
                    exp = makeBinary(token, exp, exp2);
                }
            }
        }
        return exp;
    }

    /* access modifiers changed from: package-private */
    public Expression parseUnaryExpr() throws IOException, SyntaxException {
        if (this.curToken != 414 && this.curToken != 413) {
            return parseUnionExpr();
        }
        int op = this.curToken;
        getRawToken();
        return new ApplyExp(makeFunctionExp("gnu.xquery.util.ArithOp", op == 413 ? "plus" : "minus", op == 413 ? "+" : "-"), parseUnaryExpr());
    }

    /* access modifiers changed from: package-private */
    public Expression parseUnionExpr() throws IOException, SyntaxException {
        Expression exp = parseIntersectExceptExpr();
        while (true) {
            int op = peekOperator();
            if (op != 419) {
                return exp;
            }
            getRawToken();
            exp = makeBinary(op, exp, parseIntersectExceptExpr());
        }
    }

    /* access modifiers changed from: package-private */
    public Expression parseIntersectExceptExpr() throws IOException, SyntaxException {
        Expression exp = parsePathExpr();
        while (true) {
            int op = peekOperator();
            if (op != 420 && op != 421) {
                return exp;
            }
            getRawToken();
            exp = makeBinary(op, exp, parsePathExpr());
        }
    }

    /* access modifiers changed from: package-private */
    public Expression parsePathExpr() throws IOException, SyntaxException {
        Expression step1;
        Expression dot;
        if (this.curToken == 47 || this.curToken == 68) {
            boolean z = false;
            Declaration dotDecl = this.comp.lexical.lookup((Object) DOT_VARNAME, false);
            if (dotDecl == null) {
                dot = syntaxError("context item is undefined", "XPDY0002");
            } else {
                dot = new ReferenceExp(DOT_VARNAME, dotDecl);
            }
            Expression step12 = new ApplyExp(ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("rootDocument", 1), dot);
            if (this.nesting != 0) {
                z = true;
            }
            int next = skipSpace(z);
            unread(next);
            if (next < 0 || next == 41 || next == 125) {
                getRawToken();
                return step12;
            }
            step1 = step12;
        } else {
            step1 = parseStepExpr();
        }
        return parseRelativePathExpr(step1);
    }

    /* access modifiers changed from: package-private */
    public Expression parseNameTest(boolean attribute) throws IOException, SyntaxException {
        String local = null;
        String prefix = null;
        if (this.curToken == 81) {
            int colon = this.tokenBufferLength;
            do {
                colon--;
            } while (this.tokenBuffer[colon] != ':');
            prefix = new String(this.tokenBuffer, 0, colon);
            int colon2 = colon + 1;
            local = new String(this.tokenBuffer, colon2, this.tokenBufferLength - colon2);
        } else if (this.curToken == 415) {
            int next = read();
            String local2 = "";
            if (next != 58) {
                unread(next);
            } else {
                int next2 = read();
                if (next2 < 0) {
                    eofError("unexpected end-of-file after '*:'");
                }
                if (XName.isNameStart((char) next2)) {
                    unread();
                    getRawToken();
                    if (this.curToken != 65) {
                        syntaxError("invalid name test");
                    } else {
                        local2 = new String(this.tokenBuffer, 0, this.tokenBufferLength).intern();
                    }
                } else if (next2 != 42) {
                    syntaxError("missing local-name after '*:'");
                }
            }
            return QuoteExp.getInstance(new Symbol((Namespace) null, local2));
        } else if (this.curToken == 65) {
            local = new String(this.tokenBuffer, 0, this.tokenBufferLength);
            if (attribute) {
                return new QuoteExp(Namespace.EmptyNamespace.getSymbol(local.intern()));
            }
            prefix = null;
        } else if (this.curToken == 67) {
            prefix = new String(this.tokenBuffer, 0, this.tokenBufferLength);
            if (read() != 42) {
                syntaxError("invalid characters after 'NCName:'");
            }
            local = "";
        }
        if (prefix != null) {
            prefix = prefix.intern();
        }
        Expression[] args = new Expression[3];
        args[0] = new ApplyExp((Expression) new ReferenceExp(XQResolveNames.resolvePrefixDecl), QuoteExp.getInstance(prefix));
        args[1] = new QuoteExp(local == null ? "" : local);
        args[2] = new QuoteExp(prefix);
        ApplyExp make = new ApplyExp(Compilation.typeSymbol.getDeclaredMethod("make", 3), args);
        make.setFlag(4);
        return make;
    }

    /* access modifiers changed from: package-private */
    public Expression parseNodeTest(int i) throws IOException, SyntaxException {
        Expression expression;
        QuoteExp quoteExp;
        String str;
        peekOperand();
        Expression[] expressionArr = new Expression[1];
        Expression parseMaybeKindTest = parseMaybeKindTest();
        if (parseMaybeKindTest != null) {
            expressionArr[0] = parseMaybeKindTest;
        } else if (this.curToken == 65 || this.curToken == 81 || this.curToken == 67 || this.curToken == 415) {
            expressionArr[0] = makeNamedNodeType(i == 2, parseNameTest(i == 2), (Expression) null);
        } else if (i >= 0) {
            return syntaxError("unsupported axis '" + axisNames[i] + "::'");
        } else {
            return null;
        }
        Declaration lookup = this.comp.lexical.lookup((Object) DOT_VARNAME, false);
        if (lookup == null) {
            expression = syntaxError("node test when context item is undefined", "XPDY0002");
        } else {
            expression = new ReferenceExp(DOT_VARNAME, lookup);
        }
        if (parseMaybeKindTest == null) {
            getRawToken();
        }
        if (i == 3 || i == -1) {
            quoteExp = makeChildAxisStep;
        } else if (i == 4) {
            quoteExp = makeDescendantAxisStep;
        } else {
            switch (i) {
                case 0:
                    str = "Ancestor";
                    break;
                case 1:
                    str = "AncestorOrSelf";
                    break;
                case 2:
                    str = "Attribute";
                    break;
                case 5:
                    str = "DescendantOrSelf";
                    break;
                case 6:
                    str = "Following";
                    break;
                case 7:
                    str = "FollowingSibling";
                    break;
                case 9:
                    str = "Parent";
                    break;
                case 10:
                    str = "Preceding";
                    break;
                case 11:
                    str = "PrecedingSibling";
                    break;
                case 12:
                    str = "Self";
                    break;
                default:
                    throw new Error();
            }
            quoteExp = QuoteExp.getInstance(new PrimProcedure("gnu.kawa.xml." + str + "Axis", "make", 1));
        }
        ApplyExp applyExp = new ApplyExp((Expression) quoteExp, expressionArr);
        applyExp.setFlag(4);
        return new ApplyExp((Expression) applyExp, expression);
    }

    /* access modifiers changed from: package-private */
    public Expression parseRelativePathExpr(Expression exp) throws IOException, SyntaxException {
        Expression beforeSlashSlash = null;
        while (true) {
            if (this.curToken != 47 && this.curToken != 68) {
                return exp;
            }
            boolean descendants = this.curToken == 68;
            LambdaExp lexp = new LambdaExp(3);
            Declaration dotDecl = lexp.addDeclaration((Object) DOT_VARNAME);
            dotDecl.setFlag(262144);
            dotDecl.setType(NodeType.anyNodeTest);
            dotDecl.noteValue((Expression) null);
            lexp.addDeclaration(POSITION_VARNAME, LangPrimType.intType);
            lexp.addDeclaration(LAST_VARNAME, LangPrimType.intType);
            this.comp.push((ScopeExp) lexp);
            if (descendants) {
                this.curToken = 47;
                lexp.body = new ApplyExp((Procedure) DescendantOrSelfAxis.anyNode, new ReferenceExp(DOT_VARNAME, dotDecl));
                beforeSlashSlash = exp;
            } else {
                getRawToken();
                Expression exp2 = parseStepExpr();
                if (beforeSlashSlash != null && (exp2 instanceof ApplyExp)) {
                    Expression function = ((ApplyExp) exp2).getFunction();
                    Expression func = function;
                    if (function instanceof ApplyExp) {
                        ApplyExp applyExp = (ApplyExp) func;
                        ApplyExp aexp = applyExp;
                        if (applyExp.getFunction() == makeChildAxisStep) {
                            aexp.setFunction(makeDescendantAxisStep);
                            exp = beforeSlashSlash;
                        }
                    }
                }
                lexp.body = exp2;
                beforeSlashSlash = null;
            }
            this.comp.pop(lexp);
            exp = new ApplyExp((Procedure) RelativeStep.relativeStep, exp, lexp);
        }
    }

    /* access modifiers changed from: package-private */
    public Expression parseStepExpr() throws IOException, SyntaxException {
        Expression exp;
        Expression unqualifiedStep;
        int i = -1;
        if (this.curToken == 46 || this.curToken == 51) {
            int axis = this.curToken == 46 ? 12 : 9;
            getRawToken();
            Declaration dotDecl = this.comp.lexical.lookup((Object) DOT_VARNAME, false);
            if (dotDecl == null) {
                exp = syntaxError("context item is undefined", "XPDY0002");
            } else {
                exp = new ReferenceExp(DOT_VARNAME, dotDecl);
            }
            if (axis == 9) {
                exp = new ApplyExp((Procedure) ParentAxis.make(NodeType.anyNodeTest), exp);
            }
            if (axis != 12) {
                i = axis;
            }
            return parseStepQualifiers(exp, i);
        }
        int axis2 = peekOperand() - 100;
        if (axis2 >= 0 && axis2 < 13) {
            getRawToken();
            unqualifiedStep = parseNodeTest(axis2);
        } else if (this.curToken == 64) {
            getRawToken();
            axis2 = 2;
            unqualifiedStep = parseNodeTest(2);
        } else if (this.curToken == OP_ATTRIBUTE) {
            axis2 = 2;
            unqualifiedStep = parseNodeTest(2);
        } else {
            unqualifiedStep = parseNodeTest(-1);
            if (unqualifiedStep != null) {
                axis2 = 3;
            } else {
                axis2 = -1;
                unqualifiedStep = parsePrimaryExpr();
            }
        }
        return parseStepQualifiers(unqualifiedStep, axis2);
    }

    /* access modifiers changed from: package-private */
    public Expression parseStepQualifiers(Expression exp, int axis) throws IOException, SyntaxException {
        Procedure valuesFilter;
        while (this.curToken == 91) {
            int startLine = getLineNumber() + 1;
            int startColumn = getColumnNumber() + 1;
            int i = this.seenPosition;
            int i2 = this.seenLast;
            getRawToken();
            LambdaExp lexp = new LambdaExp(3);
            maybeSetLine((Expression) lexp, startLine, startColumn);
            Declaration dot = lexp.addDeclaration((Object) DOT_VARNAME);
            if (axis >= 0) {
                dot.setType(NodeType.anyNodeTest);
            } else {
                dot.setType(SingletonType.getInstance());
            }
            lexp.addDeclaration(POSITION_VARNAME, Type.intType);
            lexp.addDeclaration(LAST_VARNAME, Type.intType);
            this.comp.push((ScopeExp) lexp);
            dot.noteValue((Expression) null);
            Expression cond = parseExprSequence(93, false);
            if (this.curToken == -1) {
                eofError("missing ']' - unexpected end-of-file");
            }
            if (axis < 0) {
                valuesFilter = ValuesFilter.exprFilter;
            } else if (axis == 0 || axis == 1 || axis == 9 || axis == 10 || axis == 11) {
                valuesFilter = ValuesFilter.reverseFilter;
            } else {
                valuesFilter = ValuesFilter.forwardFilter;
            }
            maybeSetLine(cond, startLine, startColumn);
            this.comp.pop(lexp);
            lexp.body = cond;
            getRawToken();
            exp = new ApplyExp(valuesFilter, exp, lexp);
        }
        return exp;
    }

    /* access modifiers changed from: package-private */
    public Expression parsePrimaryExpr() throws IOException, SyntaxException {
        Expression exp = parseMaybePrimaryExpr();
        if (exp != null) {
            return exp;
        }
        Expression exp2 = syntaxError("missing expression");
        if (this.curToken != -1) {
            getRawToken();
        }
        return exp2;
    }

    /* access modifiers changed from: package-private */
    public void parseEntityOrCharRef() throws IOException, SyntaxException {
        int base;
        int next = read();
        if (next == 35) {
            int next2 = read();
            if (next2 == 120) {
                base = 16;
                next2 = read();
            } else {
                base = 10;
            }
            int value = 0;
            while (next2 >= 0) {
                int digit = Character.digit((char) next2, base);
                if (digit < 0 || value >= 134217728) {
                    break;
                }
                value = (value * base) + digit;
                next2 = read();
            }
            if (next2 != 59) {
                unread();
                error("invalid character reference");
            } else if ((value <= 0 || value > 55295) && ((value < 57344 || value > 65533) && (value < 65536 || value > 1114111))) {
                error('e', "invalid character value " + value, "XQST0090");
            } else {
                tokenBufferAppend(value);
            }
        } else {
            int saveLength = this.tokenBufferLength;
            while (next >= 0) {
                char ch = (char) next;
                if (!XName.isNamePart(ch)) {
                    break;
                }
                tokenBufferAppend(ch);
                next = read();
            }
            if (next != 59) {
                unread();
                error("invalid entity reference");
                return;
            }
            String ref = new String(this.tokenBuffer, saveLength, this.tokenBufferLength - saveLength);
            this.tokenBufferLength = saveLength;
            appendNamedEntity(ref);
        }
    }

    /* JADX WARNING: type inference failed for: r8v0 */
    /* JADX WARNING: type inference failed for: r8v19 */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Incorrect type for immutable var: ssa=int, code=?, for r8v1, types: [boolean, int] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x015b  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x015a A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void parseContent(char r17, java.util.Vector r18) throws java.io.IOException, gnu.text.SyntaxException {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = 0
            r0.tokenBufferLength = r3
            int r4 = r18.size()
            int r5 = r4 + -1
            boolean r6 = r0.boundarySpacePreserve
            r7 = 60
            r8 = 1
            if (r6 != 0) goto L_0x001a
            if (r1 != r7) goto L_0x001a
            r6 = 1
            goto L_0x001b
        L_0x001a:
            r6 = 0
        L_0x001b:
            r9 = r6
        L_0x001c:
            int r10 = r16.read()
            if (r10 != r1) goto L_0x00ac
            if (r1 != r7) goto L_0x009e
            int r10 = r16.read()
            r11 = 0
            int r12 = r0.tokenBufferLength
            if (r12 <= 0) goto L_0x0047
            java.lang.String r12 = new java.lang.String
            char[] r13 = r0.tokenBuffer
            int r14 = r0.tokenBufferLength
            r12.<init>(r13, r3, r14)
            gnu.expr.Expression[] r13 = new gnu.expr.Expression[r8]
            gnu.expr.QuoteExp r14 = new gnu.expr.QuoteExp
            r14.<init>(r12)
            r13[r3] = r14
            gnu.expr.ApplyExp r14 = new gnu.expr.ApplyExp
            gnu.expr.Expression r15 = makeText
            r14.<init>((gnu.expr.Expression) r15, (gnu.expr.Expression[]) r13)
            r11 = r14
        L_0x0047:
            r0.tokenBufferLength = r3
            r12 = 47
            if (r10 != r12) goto L_0x0056
            if (r11 == 0) goto L_0x015a
            if (r9 != 0) goto L_0x015a
            r2.addElement(r11)
            goto L_0x015a
        L_0x0056:
            gnu.expr.Expression r12 = r0.parseXMLConstructor(r10, r8)
            r13 = 0
            r14 = 0
            boolean r15 = r12 instanceof gnu.expr.ApplyExp
            if (r15 == 0) goto L_0x0082
            r15 = r12
            gnu.expr.ApplyExp r15 = (gnu.expr.ApplyExp) r15
            gnu.expr.Expression r8 = r15.getFunction()
            gnu.expr.Expression r7 = makeCDATA
            if (r8 != r7) goto L_0x0082
            r13 = 1
            gnu.expr.Expression r7 = r15.getArg(r3)
            java.lang.Object r7 = r7.valueIfConstant()
            java.lang.String r7 = (java.lang.String) r7
            if (r7 == 0) goto L_0x0080
            int r8 = r7.length()
            if (r8 != 0) goto L_0x0080
            r8 = 1
            goto L_0x0081
        L_0x0080:
            r8 = 0
        L_0x0081:
            r14 = r8
        L_0x0082:
            if (r11 == 0) goto L_0x008b
            if (r9 == 0) goto L_0x0088
            if (r13 == 0) goto L_0x008b
        L_0x0088:
            r2.addElement(r11)
        L_0x008b:
            if (r13 == 0) goto L_0x0090
            r7 = 0
            r9 = r7
            goto L_0x0092
        L_0x0090:
            r7 = r6
            r9 = r7
        L_0x0092:
            if (r14 != 0) goto L_0x0097
            r2.addElement(r12)
        L_0x0097:
            r0.tokenBufferLength = r3
            r7 = 60
            r8 = 1
            goto L_0x001c
        L_0x009e:
            boolean r7 = r16.checkNext(r17)
            if (r7 == 0) goto L_0x00ac
            r16.tokenBufferAppend(r17)
            r7 = 60
            r8 = 1
            goto L_0x001c
        L_0x00ac:
            r7 = 123(0x7b, float:1.72E-43)
            if (r10 == r1) goto L_0x0111
            if (r10 < 0) goto L_0x0111
            if (r10 != r7) goto L_0x00b7
            r8 = 60
            goto L_0x0113
        L_0x00b7:
            r7 = 125(0x7d, float:1.75E-43)
            if (r10 != r7) goto L_0x00d8
            int r8 = r16.read()
            if (r8 != r7) goto L_0x00cb
            r0.tokenBufferAppend(r7)
            r7 = 0
            r9 = r7
            r8 = 60
            r12 = 1
            goto L_0x017c
        L_0x00cb:
            java.lang.String r7 = "unexpected '}' in element content"
            r0.error(r7)
            r0.unread(r8)
            r8 = 60
            r12 = 1
            goto L_0x017c
        L_0x00d8:
            r7 = 38
            if (r10 != r7) goto L_0x00e6
            r16.parseEntityOrCharRef()
            r7 = 0
            r9 = r7
            r8 = 60
            r12 = 1
            goto L_0x017c
        L_0x00e6:
            r7 = 60
            if (r1 == r7) goto L_0x00f8
            r7 = 9
            if (r10 == r7) goto L_0x00f6
            r7 = 10
            if (r10 == r7) goto L_0x00f6
            r7 = 13
            if (r10 != r7) goto L_0x00f8
        L_0x00f6:
            r10 = 32
        L_0x00f8:
            r8 = 60
            if (r10 != r8) goto L_0x0103
            r7 = 101(0x65, float:1.42E-43)
            java.lang.String r11 = "'<' must be quoted in a direct attribute value"
            r0.error(r7, r11)
        L_0x0103:
            if (r9 == 0) goto L_0x010a
            char r7 = (char) r10
            boolean r9 = java.lang.Character.isWhitespace(r7)
        L_0x010a:
            char r7 = (char) r10
            r0.tokenBufferAppend(r7)
            r12 = 1
            goto L_0x017c
        L_0x0111:
            r8 = 60
        L_0x0113:
            if (r10 != r7) goto L_0x011a
            int r11 = r16.read()
            goto L_0x011b
        L_0x011a:
            r11 = -1
        L_0x011b:
            if (r11 != r7) goto L_0x0126
            r0.tokenBufferAppend(r7)
            r9 = 0
            r7 = 60
            r8 = 1
            goto L_0x001c
        L_0x0126:
            int r12 = r0.tokenBufferLength
            if (r12 <= 0) goto L_0x0136
            if (r9 != 0) goto L_0x0136
            java.lang.String r7 = new java.lang.String
            char[] r12 = r0.tokenBuffer
            int r13 = r0.tokenBufferLength
            r7.<init>(r12, r3, r13)
            goto L_0x0140
        L_0x0136:
            if (r10 != r7) goto L_0x0155
            int r7 = r18.size()
            if (r5 != r7) goto L_0x0155
            java.lang.String r7 = ""
        L_0x0140:
            r12 = 1
            gnu.expr.Expression[] r13 = new gnu.expr.Expression[r12]
            gnu.expr.QuoteExp r12 = new gnu.expr.QuoteExp
            r12.<init>(r7)
            r13[r3] = r12
            r12 = r13
            gnu.expr.ApplyExp r13 = new gnu.expr.ApplyExp
            gnu.expr.Expression r14 = makeText
            r13.<init>((gnu.expr.Expression) r14, (gnu.expr.Expression[]) r12)
            r2.addElement(r13)
        L_0x0155:
            r0.tokenBufferLength = r3
            if (r10 != r1) goto L_0x015b
        L_0x015a:
            return
        L_0x015b:
            if (r10 >= 0) goto L_0x0164
            java.lang.String r7 = "unexpected end-of-file"
            r0.eofError(r7)
            r12 = 1
            goto L_0x017b
        L_0x0164:
            r0.unread(r11)
            int r7 = r0.enclosedExpressionsSeen
            r12 = 1
            int r7 = r7 + r12
            r0.enclosedExpressionsSeen = r7
            gnu.expr.Expression r7 = r16.parseEnclosedExpr()
            r2.addElement(r7)
            r0.tokenBufferLength = r3
            int r5 = r18.size()
            r9 = r6
        L_0x017b:
        L_0x017c:
            r7 = 60
            r8 = 1
            goto L_0x001c
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQParser.parseContent(char, java.util.Vector):void");
    }

    /* access modifiers changed from: package-private */
    public Expression parseEnclosedExpr() throws IOException, SyntaxException {
        String saveErrorIfComment = this.errorIfComment;
        this.errorIfComment = null;
        char saveReadState = pushNesting('{');
        peekNonSpace("unexpected end-of-file after '{'");
        int startLine = getLineNumber() + 1;
        int startColumn = getColumnNumber() + 1;
        getRawToken();
        Expression exp = parseExpr();
        while (true) {
            if (this.curToken == 125) {
                break;
            } else if (this.curToken == -1 || this.curToken == 41 || this.curToken == 93) {
                exp = syntaxError("missing '}'");
            } else {
                if (this.curToken != 44) {
                    exp = syntaxError("missing '}' or ','");
                } else {
                    getRawToken();
                }
                exp = makeExprSequence(exp, parseExpr());
            }
        }
        exp = syntaxError("missing '}'");
        maybeSetLine(exp, startLine, startColumn);
        popNesting(saveReadState);
        this.errorIfComment = saveErrorIfComment;
        return exp;
    }

    public static Expression booleanValue(Expression exp) {
        return new ApplyExp(makeFunctionExp("gnu.xquery.util.BooleanValue", "booleanValue"), exp);
    }

    /* access modifiers changed from: package-private */
    public Expression parseXMLConstructor(int next, boolean inElementContent) throws IOException, SyntaxException {
        if (next == 33) {
            int next2 = read();
            if (next2 == 45 && peek() == 45) {
                skip();
                getDelimited("-->");
                boolean bad = false;
                int i = this.tokenBufferLength;
                boolean sawHyphen = true;
                while (true) {
                    i--;
                    if (i >= 0) {
                        boolean curHyphen = this.tokenBuffer[i] == '-';
                        if (sawHyphen && curHyphen) {
                            bad = true;
                            break;
                        }
                        sawHyphen = curHyphen;
                    } else {
                        break;
                    }
                }
                if (bad) {
                    return syntaxError("consecutive or final hyphen in XML comment");
                }
                return new ApplyExp(makeFunctionExp("gnu.kawa.xml.CommentConstructor", "commentConstructor"), new QuoteExp(new String(this.tokenBuffer, 0, this.tokenBufferLength)));
            } else if (next2 != 91 || read() != 67 || read() != 68 || read() != 65 || read() != 84 || read() != 65 || read() != 91) {
                return syntaxError("'<!' must be followed by '--' or '[CDATA['");
            } else {
                if (!inElementContent) {
                    error('e', "CDATA section must be in element content");
                }
                getDelimited("]]>");
                return new ApplyExp(makeCDATA, new QuoteExp(new String(this.tokenBuffer, 0, this.tokenBufferLength)));
            }
        } else if (next == 63) {
            int next3 = peek();
            if (next3 < 0 || !XName.isNameStart((char) next3) || getRawToken() != 65) {
                syntaxError("missing target after '<?'");
            }
            String target = new String(this.tokenBuffer, 0, this.tokenBufferLength);
            int nspaces = 0;
            while (true) {
                int ch = read();
                if (ch < 0) {
                    break;
                } else if (!Character.isWhitespace((char) ch)) {
                    unread();
                    break;
                } else {
                    nspaces++;
                }
            }
            getDelimited("?>");
            if (nspaces == 0 && this.tokenBufferLength > 0) {
                syntaxError("target must be followed by space or '?>'");
            }
            int i2 = next3;
            return new ApplyExp(makeFunctionExp("gnu.kawa.xml.MakeProcInst", "makeProcInst"), new QuoteExp(target), new QuoteExp(new String(this.tokenBuffer, 0, this.tokenBufferLength)));
        } else if (next < 0 || !XName.isNameStart((char) next)) {
            return syntaxError("expected QName after '<'");
        } else {
            unread(next);
            getRawToken();
            char saveReadState = pushNesting('<');
            Expression exp = parseElementConstructor();
            if (!inElementContent) {
                exp = wrapWithBaseUri(exp);
            }
            popNesting(saveReadState);
            return exp;
        }
    }

    static ApplyExp castQName(Expression value, boolean element) {
        return new ApplyExp((Expression) new ReferenceExp(element ? XQResolveNames.xsQNameDecl : XQResolveNames.xsQNameIgnoreDefaultDecl), value);
    }

    /* access modifiers changed from: package-private */
    public Expression parseElementConstructor() throws IOException, SyntaxException {
        int ch;
        int ch2;
        String ns;
        String str;
        Object x;
        Object x2;
        boolean z = false;
        String startTag = new String(this.tokenBuffer, 0, this.tokenBufferLength);
        Vector vec = new Vector();
        int i = 1;
        vec.addElement(castQName(new QuoteExp(startTag), true));
        this.errorIfComment = "comment not allowed in element start tag";
        NamespaceBinding nsBindings = null;
        while (true) {
            boolean sawSpace = false;
            ch = read();
            while (ch >= 0 && Character.isWhitespace((char) ch)) {
                ch = read();
                sawSpace = true;
            }
            if (ch >= 0 && ch != 62) {
                if (ch == 47) {
                    break;
                }
                if (!sawSpace) {
                    syntaxError("missing space before attribute");
                }
                unread(ch);
                getRawToken();
                int vecSize = vec.size();
                if (this.curToken != 65 && this.curToken != 81) {
                    break;
                }
                String attrName = new String(this.tokenBuffer, z ? 1 : 0, this.tokenBufferLength);
                int startLine = getLineNumber() + i;
                int startColumn = (getColumnNumber() + i) - this.tokenBufferLength;
                String definingNamespace = null;
                if (this.curToken == 65) {
                    if (attrName.equals("xmlns")) {
                        definingNamespace = "";
                    }
                } else if (attrName.startsWith("xmlns:")) {
                    definingNamespace = attrName.substring(6).intern();
                }
                Expression makeAttr = definingNamespace != null ? null : MakeAttribute.makeAttributeExp;
                vec.addElement(castQName(new QuoteExp(attrName), z));
                if (skipSpace() != 61) {
                    this.errorIfComment = null;
                    return syntaxError("missing '=' after attribute");
                }
                int ch3 = skipSpace();
                int enclosedExpressionsStart = this.enclosedExpressionsSeen;
                if (ch3 == 123) {
                    warnOldVersion("enclosed attribute value expression should be quoted");
                    vec.addElement(parseEnclosedExpr());
                } else {
                    parseContent((char) ch3, vec);
                }
                int n = vec.size() - vecSize;
                if (definingNamespace != null) {
                    if (n == i) {
                        ns = "";
                    } else if (this.enclosedExpressionsSeen > enclosedExpressionsStart) {
                        syntaxError("enclosed expression not allowed in namespace declaration");
                        ns = "";
                    } else {
                        Object x3 = vec.elementAt(vecSize + 1);
                        if (x3 instanceof ApplyExp) {
                            ApplyExp applyExp = (ApplyExp) x3;
                            ApplyExp ax = applyExp;
                            x2 = x3;
                            if (applyExp.getFunction() == makeText) {
                                x = ax.getArg(0);
                                ns = ((QuoteExp) x).getValue().toString().intern();
                            }
                        } else {
                            x2 = x3;
                        }
                        x = x2;
                        ns = ((QuoteExp) x).getValue().toString().intern();
                    }
                    vec.setSize(vecSize);
                    checkAllowedNamespaceDeclaration(definingNamespace, ns, true);
                    if (definingNamespace == "") {
                        definingNamespace = null;
                    }
                    NamespaceBinding nsb = nsBindings;
                    while (true) {
                        if (nsb == null) {
                            int i2 = ch3;
                            String str2 = attrName;
                            break;
                        }
                        boolean sawSpace2 = sawSpace;
                        if (nsb.getPrefix() == definingNamespace) {
                            if (definingNamespace == null) {
                                str = "duplicate default namespace declaration";
                                int i3 = ch3;
                            } else {
                                int i4 = ch3;
                                str = "duplicate namespace prefix '" + definingNamespace + '\'';
                            }
                            String str3 = attrName;
                            error('e', str, "XQST0071");
                        } else {
                            String str4 = attrName;
                            nsb = nsb.getNext();
                            sawSpace = sawSpace2;
                        }
                    }
                    nsBindings = new NamespaceBinding(definingNamespace, ns == "" ? null : ns, nsBindings);
                } else {
                    int i5 = ch3;
                    String str5 = attrName;
                    Expression[] args = new Expression[n];
                    int i6 = n;
                    while (true) {
                        i6--;
                        if (i6 < 0) {
                            break;
                        }
                        args[i6] = (Expression) vec.elementAt(vecSize + i6);
                    }
                    vec.setSize(vecSize);
                    ApplyExp aexp = new ApplyExp(makeAttr, args);
                    maybeSetLine((Expression) aexp, startLine, startColumn);
                    vec.addElement(aexp);
                }
                z = false;
                i = 1;
            } else {
                boolean z2 = sawSpace;
            }
        }
        this.errorIfComment = null;
        boolean empty = false;
        if (ch == 47) {
            ch = read();
            if (ch == 62) {
                empty = true;
            } else {
                unread(ch);
            }
        }
        if (!empty) {
            if (ch != 62) {
                return syntaxError("missing '>' after start element");
            }
            parseContent('<', vec);
            int ch4 = peek();
            if (ch4 < 0) {
                ch2 = ch4;
            } else if (!XName.isNameStart((char) ch4)) {
                return syntaxError("invalid tag syntax after '</'");
            } else {
                getRawToken();
                String tag = new String(this.tokenBuffer, 0, this.tokenBufferLength);
                if (!tag.equals(startTag)) {
                    return syntaxError("'<" + startTag + ">' closed by '</" + tag + ">'");
                }
                this.errorIfComment = "comment not allowed in element end tag";
                int ch5 = skipSpace();
                this.errorIfComment = null;
                ch2 = ch5;
            }
            if (ch2 != 62) {
                return syntaxError("missing '>' after end element");
            }
        }
        Expression[] args2 = new Expression[vec.size()];
        vec.copyInto(args2);
        MakeElement mkElement = new MakeElement();
        mkElement.copyNamespacesMode = this.copyNamespacesMode;
        mkElement.setNamespaceNodes(nsBindings);
        return new ApplyExp((Expression) new QuoteExp(mkElement), args2);
    }

    /* access modifiers changed from: package-private */
    public Expression wrapWithBaseUri(Expression exp) {
        if (getStaticBaseUri() == null) {
            return exp;
        }
        return new ApplyExp((Procedure) MakeWithBaseUri.makeWithBaseUri, new ApplyExp((Expression) new ReferenceExp(XQResolveNames.staticBaseUriDecl), Expression.noExpressions), exp).setLine(exp);
    }

    /* access modifiers changed from: package-private */
    public Expression parseParenExpr() throws IOException, SyntaxException {
        getRawToken();
        char saveReadState = pushNesting('(');
        Expression exp = parseExprSequence(41, true);
        popNesting(saveReadState);
        if (this.curToken == -1) {
            eofError("missing ')' - unexpected end-of-file");
        }
        return exp;
    }

    /* access modifiers changed from: package-private */
    public Expression parseExprSequence(int rightToken, boolean optional) throws IOException, SyntaxException {
        if (this.curToken == rightToken || this.curToken == -1) {
            if (!optional) {
                syntaxError("missing expression");
            }
            return QuoteExp.voidExp;
        }
        Expression exp = null;
        while (true) {
            Expression exp1 = parseExprSingle();
            exp = exp == null ? exp1 : makeExprSequence(exp, exp1);
            if (this.curToken == rightToken || this.curToken == -1) {
                return exp;
            }
            if (this.nesting == 0 && this.curToken == 10) {
                return exp;
            }
            if (this.curToken != 44) {
                return syntaxError(rightToken == 41 ? "expected ')'" : "confused by syntax error");
            }
            getRawToken();
        }
        return exp;
    }

    /* access modifiers changed from: package-private */
    public Expression parseTypeSwitch() throws IOException, SyntaxException {
        Declaration decl;
        Declaration decl2;
        char save = pushNesting('t');
        Expression selector = parseParenExpr();
        getRawToken();
        Vector vec = new Vector();
        vec.addElement(selector);
        while (true) {
            char c = 'e';
            if (match("case")) {
                pushNesting('c');
                getRawToken();
                if (this.curToken == 36) {
                    decl2 = parseVariableDeclaration();
                    if (decl2 == null) {
                        return syntaxError("missing Variable after '$'");
                    }
                    getRawToken();
                    if (match("as")) {
                        getRawToken();
                    } else {
                        error('e', "missing 'as'");
                    }
                } else {
                    decl2 = new Declaration((Object) "(arg)");
                }
                decl2.setTypeExp(parseDataType());
                popNesting('t');
                LambdaExp lexp = new LambdaExp(1);
                lexp.addDeclaration(decl2);
                if (match("return")) {
                    getRawToken();
                } else {
                    error("missing 'return' after 'case'");
                }
                this.comp.push((ScopeExp) lexp);
                pushNesting('r');
                lexp.body = parseExpr();
                popNesting('t');
                this.comp.pop(lexp);
                vec.addElement(lexp);
            } else {
                if (match("default")) {
                    LambdaExp lexp2 = new LambdaExp(1);
                    getRawToken();
                    if (this.curToken == 36) {
                        decl = parseVariableDeclaration();
                        if (decl == null) {
                            return syntaxError("missing Variable after '$'");
                        }
                        getRawToken();
                    } else {
                        decl = new Declaration((Object) "(arg)");
                    }
                    lexp2.addDeclaration(decl);
                    if (match("return")) {
                        getRawToken();
                    } else {
                        error("missing 'return' after 'default'");
                    }
                    this.comp.push((ScopeExp) lexp2);
                    lexp2.body = parseExpr();
                    this.comp.pop(lexp2);
                    vec.addElement(lexp2);
                } else {
                    if (!this.comp.isPedantic()) {
                        c = 'w';
                    }
                    error(c, "no 'default' clause in 'typeswitch'", "XPST0003");
                }
                popNesting(save);
                Expression[] args = new Expression[vec.size()];
                vec.copyInto(args);
                return new ApplyExp(makeFunctionExp("gnu.kawa.reflect.TypeSwitch", "typeSwitch"), args);
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x025d  */
    /* JADX WARNING: Removed duplicated region for block: B:161:0x0202 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression parseMaybePrimaryExpr() throws java.io.IOException, gnu.text.SyntaxException {
        /*
            r16 = this;
            r1 = r16
            int r0 = r1.curLine
            int r2 = r1.curColumn
            int r3 = r16.peekOperand()
            r4 = 44
            r5 = 0
            r6 = -1
            r7 = 41
            r8 = 10
            r9 = 65
            r10 = 81
            r11 = 123(0x7b, float:1.72E-43)
            r13 = 125(0x7d, float:1.75E-43)
            r14 = 0
            switch(r3) {
                case 34: goto L_0x0340;
                case 36: goto L_0x0326;
                case 40: goto L_0x0321;
                case 48: goto L_0x0313;
                case 49: goto L_0x02d6;
                case 50: goto L_0x02d6;
                case 70: goto L_0x0281;
                case 123: goto L_0x0276;
                case 197: goto L_0x01ba;
                case 249: goto L_0x01b1;
                case 250: goto L_0x01b1;
                case 251: goto L_0x0082;
                case 252: goto L_0x0082;
                case 253: goto L_0x0082;
                case 254: goto L_0x0082;
                case 255: goto L_0x0082;
                case 256: goto L_0x0082;
                case 404: goto L_0x001f;
                default: goto L_0x001e;
            }
        L_0x001e:
            return r5
        L_0x001f:
            int r3 = r16.read()
            r4 = 47
            if (r3 != r4) goto L_0x0079
            r16.getRawToken()
            int r3 = r1.curToken
            if (r3 == r9) goto L_0x003c
            int r3 = r1.curToken
            if (r3 == r10) goto L_0x003c
            int r3 = r1.curToken
            r4 = 67
            if (r3 != r4) goto L_0x0039
            goto L_0x003c
        L_0x0039:
            java.lang.String r3 = "saw end tag '</' not in an element constructor"
            goto L_0x005e
        L_0x003c:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "saw end tag '</"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = new java.lang.String
            char[] r5 = r1.tokenBuffer
            int r7 = r1.tokenBufferLength
            r4.<init>(r5, r14, r7)
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = ">' not in an element constructor"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
        L_0x005e:
            r1.curLine = r0
            r1.curColumn = r2
            gnu.expr.Expression r0 = r1.syntaxError(r3)
        L_0x0066:
            int r2 = r1.curToken
            r3 = 405(0x195, float:5.68E-43)
            if (r2 == r3) goto L_0x0078
            int r2 = r1.curToken
            if (r2 == r6) goto L_0x0078
            int r2 = r1.curToken
            if (r2 == r8) goto L_0x0078
            r16.getRawToken()
            goto L_0x0066
        L_0x0078:
            return r0
        L_0x0079:
            gnu.expr.Expression r3 = r1.parseXMLConstructor(r3, r14)
            r1.maybeSetLine((gnu.expr.Expression) r3, (int) r0, (int) r2)
            goto L_0x0353
        L_0x0082:
            r16.getRawToken()
            java.util.Vector r5 = new java.util.Vector
            r5.<init>()
            r6 = 254(0xfe, float:3.56E-43)
            r7 = 256(0x100, float:3.59E-43)
            r8 = 255(0xff, float:3.57E-43)
            r15 = 251(0xfb, float:3.52E-43)
            if (r3 == r15) goto L_0x00f6
            r12 = 252(0xfc, float:3.53E-43)
            if (r3 != r12) goto L_0x0099
            goto L_0x00f6
        L_0x0099:
            if (r3 != r7) goto L_0x00a5
            java.lang.String r9 = "gnu.kawa.xml.DocumentConstructor"
            java.lang.String r10 = "documentConstructor"
            gnu.expr.Expression r9 = makeFunctionExp(r9, r10)
            goto L_0x013c
        L_0x00a5:
            if (r3 != r6) goto L_0x00b1
            java.lang.String r9 = "gnu.kawa.xml.CommentConstructor"
            java.lang.String r10 = "commentConstructor"
            gnu.expr.Expression r9 = makeFunctionExp(r9, r10)
            goto L_0x013c
        L_0x00b1:
            if (r3 != r8) goto L_0x00ed
            int r12 = r1.curToken
            if (r12 != r9) goto L_0x00ca
            gnu.expr.QuoteExp r9 = new gnu.expr.QuoteExp
            java.lang.String r10 = new java.lang.String
            char[] r12 = r1.tokenBuffer
            int r7 = r1.tokenBufferLength
            r10.<init>(r12, r14, r7)
            java.lang.String r7 = r10.intern()
            r9.<init>(r7)
            goto L_0x00de
        L_0x00ca:
            int r7 = r1.curToken
            if (r7 != r11) goto L_0x00d3
            gnu.expr.Expression r9 = r16.parseEnclosedExpr()
            goto L_0x00de
        L_0x00d3:
            java.lang.String r7 = "expected NCName or '{' after 'processing-instruction'"
            gnu.expr.Expression r9 = r1.syntaxError(r7)
            int r7 = r1.curToken
            if (r7 == r10) goto L_0x00de
            return r9
        L_0x00de:
            r5.addElement(r9)
            java.lang.String r7 = "gnu.kawa.xml.MakeProcInst"
            java.lang.String r9 = "makeProcInst"
            gnu.expr.Expression r9 = makeFunctionExp(r7, r9)
            r16.getRawToken()
            goto L_0x013c
        L_0x00ed:
            java.lang.String r7 = "gnu.kawa.xml.MakeText"
            java.lang.String r9 = "makeText"
            gnu.expr.Expression r9 = makeFunctionExp(r7, r9)
            goto L_0x013c
        L_0x00f6:
            int r7 = r1.curToken
            if (r7 == r9) goto L_0x010f
            int r7 = r1.curToken
            if (r7 != r10) goto L_0x00ff
            goto L_0x010f
        L_0x00ff:
            int r7 = r1.curToken
            if (r7 != r11) goto L_0x0108
            gnu.expr.Expression r7 = r16.parseEnclosedExpr()
            goto L_0x0118
        L_0x0108:
            java.lang.String r0 = "missing element/attribute name"
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x010f:
            if (r3 == r15) goto L_0x0113
            r7 = 1
            goto L_0x0114
        L_0x0113:
            r7 = 0
        L_0x0114:
            gnu.expr.Expression r7 = r1.parseNameTest(r7)
        L_0x0118:
            if (r3 != r15) goto L_0x011c
            r9 = 1
            goto L_0x011d
        L_0x011c:
            r9 = 0
        L_0x011d:
            gnu.expr.ApplyExp r7 = castQName(r7, r9)
            r5.addElement(r7)
            if (r3 != r15) goto L_0x0135
            gnu.kawa.xml.MakeElement r7 = new gnu.kawa.xml.MakeElement
            r7.<init>()
            int r9 = r1.copyNamespacesMode
            r7.copyNamespacesMode = r9
            gnu.expr.QuoteExp r9 = new gnu.expr.QuoteExp
            r9.<init>(r7)
            goto L_0x0138
        L_0x0135:
            gnu.expr.QuoteExp r7 = gnu.kawa.xml.MakeAttribute.makeAttributeExp
            r9 = r7
        L_0x0138:
            r16.getRawToken()
        L_0x013c:
            char r7 = r1.pushNesting(r11)
            java.lang.String r10 = "unexpected end-of-file after '{'"
            r1.peekNonSpace(r10)
            int r10 = r1.curToken
            if (r10 == r11) goto L_0x0150
            java.lang.String r0 = "missing '{'"
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x0150:
            r16.getRawToken()
            r10 = 253(0xfd, float:3.55E-43)
            if (r3 == r10) goto L_0x0176
            if (r3 == r6) goto L_0x0176
            if (r3 != r8) goto L_0x015c
            goto L_0x0176
        L_0x015c:
            int r6 = r1.curToken
            if (r6 == r13) goto L_0x0182
            gnu.expr.Expression r6 = r16.parseExpr()
            r5.addElement(r6)
        L_0x0167:
            int r6 = r1.curToken
            if (r6 != r4) goto L_0x0182
            r16.getRawToken()
            gnu.expr.Expression r6 = r16.parseExpr()
            r5.addElement(r6)
            goto L_0x0167
        L_0x0176:
            if (r3 != r8) goto L_0x017a
            r12 = 1
            goto L_0x017b
        L_0x017a:
            r12 = 0
        L_0x017b:
            gnu.expr.Expression r4 = r1.parseExprSequence(r13, r12)
            r5.addElement(r4)
        L_0x0182:
            r1.popNesting(r7)
            int r4 = r1.curToken
            if (r4 == r13) goto L_0x0190
            java.lang.String r0 = "missing '}'"
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x0190:
            int r4 = r5.size()
            gnu.expr.Expression[] r4 = new gnu.expr.Expression[r4]
            r5.copyInto(r4)
            gnu.expr.ApplyExp r5 = new gnu.expr.ApplyExp
            r5.<init>((gnu.expr.Expression) r9, (gnu.expr.Expression[]) r4)
            r1.maybeSetLine((gnu.expr.Expression) r5, (int) r0, (int) r2)
            r0 = 256(0x100, float:3.59E-43)
            if (r3 == r0) goto L_0x01ab
            if (r3 != r15) goto L_0x01a8
            goto L_0x01ab
        L_0x01a8:
            r3 = r5
            goto L_0x0353
        L_0x01ab:
            gnu.expr.Expression r3 = r1.wrapWithBaseUri(r5)
            goto L_0x0353
        L_0x01b1:
            r16.getRawToken()
            gnu.expr.Expression r3 = r1.parseExprSequence(r13, r14)
            goto L_0x0353
        L_0x01ba:
            java.util.Stack r0 = new java.util.Stack
            r0.<init>()
        L_0x01bf:
            r16.getRawToken()
            int r2 = r1.curToken
            if (r2 == r10) goto L_0x01d1
            int r2 = r1.curToken
            if (r2 == r9) goto L_0x01d1
            java.lang.String r2 = "missing pragma name"
            gnu.expr.Expression r2 = r1.syntaxError(r2)
            goto L_0x01de
        L_0x01d1:
            java.lang.String r2 = new java.lang.String
            char[] r3 = r1.tokenBuffer
            int r4 = r1.tokenBufferLength
            r2.<init>(r3, r14, r4)
            gnu.expr.QuoteExp r2 = gnu.expr.QuoteExp.getInstance(r2)
        L_0x01de:
            r0.push(r2)
            java.lang.StringBuffer r2 = new java.lang.StringBuffer
            r2.<init>()
            r3 = -1
        L_0x01e7:
            int r4 = r16.read()
            r5 = 1
            int r3 = r3 + r5
            if (r4 < 0) goto L_0x01f6
            char r5 = (char) r4
            boolean r5 = java.lang.Character.isWhitespace(r5)
            if (r5 != 0) goto L_0x01e7
        L_0x01f6:
            r5 = r3
        L_0x01f7:
            r3 = 35
            if (r4 != r3) goto L_0x025d
            int r3 = r16.peek()
            if (r3 == r7) goto L_0x0202
            goto L_0x025d
        L_0x0202:
            r16.read()
            java.lang.String r2 = r2.toString()
            gnu.expr.QuoteExp r2 = gnu.expr.QuoteExp.getInstance(r2)
            r0.push(r2)
            r16.getRawToken()
            int r2 = r1.curToken
            r3 = 197(0xc5, float:2.76E-43)
            if (r2 == r3) goto L_0x025b
            int r2 = r1.curToken
            if (r2 != r11) goto L_0x0253
            r16.getRawToken()
            int r2 = r1.curToken
            if (r2 == r13) goto L_0x023c
            char r2 = r1.pushNesting(r11)
            gnu.expr.Expression r3 = r1.parseExprSequence(r13, r14)
            r0.push(r3)
            r1.popNesting(r2)
            int r2 = r1.curToken
            if (r2 != r6) goto L_0x023c
            java.lang.String r2 = "missing '}' - unexpected end-of-file"
            r1.eofError(r2)
        L_0x023c:
            int r2 = r0.size()
            gnu.expr.Expression[] r2 = new gnu.expr.Expression[r2]
            r0.copyInto(r2)
            gnu.expr.ApplyExp r3 = new gnu.expr.ApplyExp
            gnu.expr.ReferenceExp r0 = new gnu.expr.ReferenceExp
            gnu.expr.Declaration r4 = gnu.xquery.lang.XQResolveNames.handleExtensionDecl
            r0.<init>((gnu.expr.Declaration) r4)
            r3.<init>((gnu.expr.Expression) r0, (gnu.expr.Expression[]) r2)
            goto L_0x0353
        L_0x0253:
            java.lang.String r0 = "missing '{' after pragma"
            gnu.expr.Expression r3 = r1.syntaxError(r0)
            goto L_0x0353
        L_0x025b:
            goto L_0x01bf
        L_0x025d:
            if (r4 >= 0) goto L_0x0264
            java.lang.String r3 = "pragma ended by end-of-file"
            r1.eofError(r3)
        L_0x0264:
            if (r5 != 0) goto L_0x026b
            java.lang.String r3 = "missing space between pragma and extension content"
            r1.error(r3)
        L_0x026b:
            char r3 = (char) r4
            r2.append(r3)
            int r4 = r16.read()
            r5 = 1
            goto L_0x01f7
        L_0x0276:
            java.lang.String r0 = "saw unexpected '{' - assume you meant '('"
            gnu.expr.Expression r3 = r1.syntaxError(r0)
            r16.parseEnclosedExpr()
            goto L_0x0353
        L_0x0281:
            java.lang.String r3 = new java.lang.String
            char[] r6 = r1.tokenBuffer
            int r9 = r1.tokenBufferLength
            r3.<init>(r6, r14, r9)
            r6 = 40
            char r6 = r1.pushNesting(r6)
            r16.getRawToken()
            java.util.Vector r9 = new java.util.Vector
            r9.<init>(r8)
            int r8 = r1.curToken
            if (r8 == r7) goto L_0x02b7
        L_0x029c:
            gnu.expr.Expression r8 = r16.parseExpr()
            r9.addElement(r8)
            int r8 = r1.curToken
            if (r8 != r7) goto L_0x02a8
            goto L_0x02b7
        L_0x02a8:
            int r8 = r1.curToken
            if (r8 == r4) goto L_0x02b3
            java.lang.String r0 = "missing ')' after function call"
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x02b3:
            r16.getRawToken()
            goto L_0x029c
        L_0x02b7:
            int r4 = r9.size()
            gnu.expr.Expression[] r4 = new gnu.expr.Expression[r4]
            r9.copyInto(r4)
            gnu.expr.ReferenceExp r7 = new gnu.expr.ReferenceExp
            r7.<init>(r3, r5)
            r3 = 1
            r7.setProcedureName(r3)
            gnu.expr.ApplyExp r3 = new gnu.expr.ApplyExp
            r3.<init>((gnu.expr.Expression) r7, (gnu.expr.Expression[]) r4)
            r1.maybeSetLine((gnu.expr.Expression) r3, (int) r0, (int) r2)
            r1.popNesting(r6)
            goto L_0x0353
        L_0x02d6:
            java.lang.String r2 = new java.lang.String
            char[] r0 = r1.tokenBuffer
            int r4 = r1.tokenBufferLength
            r2.<init>(r0, r14, r4)
            r0 = 49
            if (r3 != r0) goto L_0x02e9
            java.math.BigDecimal r0 = new java.math.BigDecimal     // Catch:{ all -> 0x02f4 }
            r0.<init>(r2)     // Catch:{ all -> 0x02f4 }
            goto L_0x02ee
        L_0x02e9:
            java.lang.Double r0 = new java.lang.Double     // Catch:{ all -> 0x02f4 }
            r0.<init>(r2)     // Catch:{ all -> 0x02f4 }
        L_0x02ee:
            gnu.expr.QuoteExp r3 = new gnu.expr.QuoteExp     // Catch:{ all -> 0x02f4 }
            r3.<init>(r0)     // Catch:{ all -> 0x02f4 }
            goto L_0x0353
        L_0x02f4:
            r0 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = "invalid decimal literal: '"
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r2 = "'"
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            gnu.expr.Expression r3 = r1.syntaxError(r0)
            goto L_0x0353
        L_0x0313:
            gnu.expr.QuoteExp r3 = new gnu.expr.QuoteExp
            char[] r0 = r1.tokenBuffer
            int r2 = r1.tokenBufferLength
            gnu.math.IntNum r0 = gnu.math.IntNum.valueOf(r0, r14, r2, r8, r14)
            r3.<init>(r0)
            goto L_0x0353
        L_0x0321:
            gnu.expr.Expression r3 = r16.parseParenExpr()
            goto L_0x0353
        L_0x0326:
            java.lang.Object r0 = r16.parseVariable()
            if (r0 != 0) goto L_0x0333
            java.lang.String r0 = "missing Variable"
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x0333:
            gnu.expr.ReferenceExp r3 = new gnu.expr.ReferenceExp
            r3.<init>((java.lang.Object) r0)
            int r0 = r1.curLine
            int r2 = r1.curColumn
            r1.maybeSetLine((gnu.expr.Expression) r3, (int) r0, (int) r2)
            goto L_0x0353
        L_0x0340:
            gnu.expr.QuoteExp r3 = new gnu.expr.QuoteExp
            java.lang.String r0 = new java.lang.String
            char[] r2 = r1.tokenBuffer
            int r4 = r1.tokenBufferLength
            r0.<init>(r2, r14, r4)
            java.lang.String r0 = r0.intern()
            r3.<init>(r0)
        L_0x0353:
            r16.getRawToken()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQParser.parseMaybePrimaryExpr():gnu.expr.Expression");
    }

    public Expression parseIfExpr() throws IOException, SyntaxException {
        char saveReadState1 = pushNesting('i');
        getRawToken();
        char saveReadState2 = pushNesting('(');
        Expression cond = parseExprSequence(41, false);
        popNesting(saveReadState2);
        if (this.curToken == -1) {
            eofError("missing ')' - unexpected end-of-file");
        }
        getRawToken();
        if (!match("then")) {
            syntaxError("missing 'then'");
        } else {
            getRawToken();
        }
        Expression thenPart = parseExpr();
        if (!match("else")) {
            syntaxError("missing 'else'");
        } else {
            getRawToken();
        }
        popNesting(saveReadState1);
        return new IfExp(booleanValue(cond), thenPart, parseExpr());
    }

    public boolean match(String word) {
        int len;
        if (this.curToken != 65 || this.tokenBufferLength != (len = word.length())) {
            return false;
        }
        int i = len;
        do {
            i--;
            if (i < 0) {
                return true;
            }
        } while (word.charAt(i) == this.tokenBuffer[i]);
        return false;
    }

    public Object parseVariable() throws IOException, SyntaxException {
        if (this.curToken == 36) {
            getRawToken();
        } else {
            syntaxError("missing '$' before variable name");
        }
        String str = new String(this.tokenBuffer, 0, this.tokenBufferLength);
        if (this.curToken == 81) {
            return str;
        }
        if (this.curToken == 65) {
            return Namespace.EmptyNamespace.getSymbol(str.intern());
        }
        return null;
    }

    public Declaration parseVariableDeclaration() throws IOException, SyntaxException {
        Object name = parseVariable();
        if (name == null) {
            return null;
        }
        Declaration decl = new Declaration(name);
        maybeSetLine(decl, getLineNumber() + 1, (getColumnNumber() + 1) - this.tokenBufferLength);
        return decl;
    }

    public Expression parseFLWRExpression(boolean isFor) throws IOException, SyntaxException {
        int flworDeclsSave = this.flworDeclsFirst;
        this.flworDeclsFirst = this.flworDeclsCount;
        Expression exp = parseFLWRInner(isFor);
        if (match("order")) {
            char saveNesting = pushNesting(isFor ? 'f' : 'l');
            getRawToken();
            if (match("by")) {
                getRawToken();
            } else {
                error("missing 'by' following 'order'");
            }
            Stack specs = new Stack();
            while (true) {
                boolean descending = false;
                char emptyOrder = this.defaultEmptyOrder;
                LambdaExp lexp = new LambdaExp(this.flworDeclsCount - this.flworDeclsFirst);
                for (int i = this.flworDeclsFirst; i < this.flworDeclsCount; i++) {
                    lexp.addDeclaration(this.flworDecls[i].getSymbol());
                }
                this.comp.push((ScopeExp) lexp);
                lexp.body = parseExprSingle();
                this.comp.pop(lexp);
                specs.push(lexp);
                if (match("ascending")) {
                    getRawToken();
                } else if (match("descending")) {
                    getRawToken();
                    descending = true;
                }
                if (match("empty")) {
                    getRawToken();
                    if (match("greatest")) {
                        getRawToken();
                        emptyOrder = 'G';
                    } else if (match("least")) {
                        getRawToken();
                        emptyOrder = 'L';
                    } else {
                        error("'empty' sequence order must be 'greatest' or 'least'");
                    }
                }
                specs.push(new QuoteExp((descending ? "D" : "A") + emptyOrder));
                Object collation = this.defaultCollator;
                if (match("collation")) {
                    Object uri = parseURILiteral();
                    if (uri instanceof String) {
                        try {
                            collation = NamedCollator.make(resolveAgainstBaseUri((String) uri));
                        } catch (Exception e) {
                            error('e', "unknown collation '" + uri + "'", "XQST0076");
                        }
                    }
                    getRawToken();
                }
                specs.push(new QuoteExp(collation));
                if (this.curToken != 44) {
                    break;
                }
                getRawToken();
            }
            if (!match("return")) {
                return syntaxError("expected 'return' clause");
            }
            getRawToken();
            LambdaExp lexp2 = new LambdaExp(this.flworDeclsCount - this.flworDeclsFirst);
            for (int i2 = this.flworDeclsFirst; i2 < this.flworDeclsCount; i2++) {
                lexp2.addDeclaration(this.flworDecls[i2].getSymbol());
            }
            popNesting(saveNesting);
            this.comp.push((ScopeExp) lexp2);
            lexp2.body = parseExprSingle();
            this.comp.pop(lexp2);
            int nspecs = specs.size();
            Expression[] args = new Expression[(nspecs + 2)];
            args[0] = exp;
            args[1] = lexp2;
            for (int i3 = 0; i3 < nspecs; i3++) {
                args[i3 + 2] = (Expression) specs.elementAt(i3);
            }
            return new ApplyExp(makeFunctionExp("gnu.xquery.util.OrderedMap", "orderedMap"), args);
        }
        this.flworDeclsCount = this.flworDeclsFirst;
        this.flworDeclsFirst = flworDeclsSave;
        return exp;
    }

    public Expression parseFLWRInner(boolean isFor) throws IOException, SyntaxException {
        ScopeExp sc;
        Expression body;
        Expression cond;
        Expression body2;
        char saveNesting = pushNesting(isFor ? 'f' : 'l');
        this.curToken = 36;
        Declaration decl = parseVariableDeclaration();
        if (decl == null) {
            return syntaxError("missing Variable - saw " + tokenString());
        }
        if (this.flworDecls == null) {
            this.flworDecls = new Declaration[8];
        } else if (this.flworDeclsCount >= this.flworDecls.length) {
            Declaration[] tmp = new Declaration[(this.flworDeclsCount * 2)];
            System.arraycopy(this.flworDecls, 0, tmp, 0, this.flworDeclsCount);
            this.flworDecls = tmp;
        }
        Declaration[] tmp2 = this.flworDecls;
        int i = this.flworDeclsCount;
        this.flworDeclsCount = i + 1;
        tmp2[i] = decl;
        getRawToken();
        Expression type = parseOptionalTypeDeclaration();
        Expression[] inits = new Expression[1];
        Declaration posDecl = null;
        if (isFor) {
            boolean sawAt = match("at");
            ScopeExp lexp = new LambdaExp(sawAt ? 2 : 1);
            if (sawAt) {
                getRawToken();
                if (this.curToken == 36) {
                    posDecl = parseVariableDeclaration();
                    getRawToken();
                }
                if (posDecl == null) {
                    syntaxError("missing Variable after 'at'");
                }
            }
            sc = lexp;
            if (match("in")) {
                getRawToken();
            } else {
                if (this.curToken == 76) {
                    getRawToken();
                }
                syntaxError("missing 'in' in 'for' clause");
            }
        } else {
            if (this.curToken == 76) {
                getRawToken();
            } else {
                if (match("in")) {
                    getRawToken();
                }
                syntaxError("missing ':=' in 'let' clause");
            }
            sc = new LetExp(inits);
        }
        inits[0] = parseExprSingle();
        if (type != null && !isFor) {
            inits[0] = Compilation.makeCoercion(inits[0], type);
        }
        popNesting(saveNesting);
        this.comp.push(sc);
        sc.addDeclaration(decl);
        if (type != null) {
            decl.setTypeExp(type);
        }
        if (isFor) {
            decl.noteValue((Expression) null);
            decl.setFlag(262144);
        }
        if (posDecl != null) {
            sc.addDeclaration(posDecl);
            posDecl.setType(LangPrimType.intType);
            posDecl.noteValue((Expression) null);
            posDecl.setFlag(262144);
        }
        if (this.curToken == 44) {
            getRawToken();
            if (this.curToken != 36) {
                return syntaxError("missing $NAME after ','");
            }
            body = parseFLWRInner(isFor);
            char c = saveNesting;
            Declaration declaration = decl;
            Expression expression = type;
        } else if (match("for")) {
            getRawToken();
            if (this.curToken != 36) {
                return syntaxError("missing $NAME after 'for'");
            }
            body = parseFLWRInner(true);
            char c2 = saveNesting;
            Declaration declaration2 = decl;
            Expression expression2 = type;
        } else if (match("let")) {
            getRawToken();
            if (this.curToken != 36) {
                return syntaxError("missing $NAME after 'let'");
            }
            body = parseFLWRInner(false);
            char c3 = saveNesting;
            Declaration declaration3 = decl;
            Expression expression3 = type;
        } else {
            char save = pushNesting('w');
            if (this.curToken == 196) {
                getRawToken();
                cond = parseExprSingle();
            } else if (match("where")) {
                cond = parseExprSingle();
            } else {
                cond = null;
            }
            popNesting(save);
            if (match("stable")) {
                getRawToken();
            }
            boolean sawReturn = match("return");
            boolean sawOrder = match("order");
            if (!sawReturn && !sawOrder && !match("let") && !match("for")) {
                return syntaxError("missing 'return' clause");
            }
            if (!sawOrder) {
                peekNonSpace("unexpected eof-of-file after 'return'");
            }
            int bodyLine = getLineNumber() + 1;
            int bodyColumn = getColumnNumber() + 1;
            if (sawReturn) {
                getRawToken();
            }
            if (sawOrder) {
                int ndecls = this.flworDeclsCount - this.flworDeclsFirst;
                Expression[] args = new Expression[ndecls];
                char c4 = saveNesting;
                int i2 = 0;
                while (i2 < ndecls) {
                    args[i2] = new ReferenceExp(this.flworDecls[this.flworDeclsFirst + i2]);
                    i2++;
                    save = save;
                    decl = decl;
                    type = type;
                }
                Declaration declaration4 = decl;
                Expression expression4 = type;
                int i3 = ndecls;
                body2 = new ApplyExp((Procedure) new PrimProcedure("gnu.xquery.util.OrderedMap", "makeTuple$V", 1), args);
            } else {
                char c5 = save;
                Declaration declaration5 = decl;
                Expression expression5 = type;
                body2 = parseExprSingle();
            }
            if (cond != null) {
                body = new IfExp(booleanValue(cond), body2, QuoteExp.voidExp);
            } else {
                body = body2;
            }
            maybeSetLine(body, bodyLine, bodyColumn);
        }
        this.comp.pop(sc);
        if (isFor) {
            LambdaExp lexp2 = (LambdaExp) sc;
            lexp2.body = body;
            return new ApplyExp(makeFunctionExp("gnu.kawa.functions.ValuesMap", lexp2.min_args == 1 ? "valuesMap" : "valuesMapWithPos"), sc, inits[0]);
        }
        ((LetExp) sc).setBody(body);
        return sc;
    }

    public Expression parseQuantifiedExpr(boolean isEvery) throws IOException, SyntaxException {
        Expression body;
        char saveNesting = pushNesting(isEvery ? 'e' : 's');
        this.curToken = 36;
        Declaration decl = parseVariableDeclaration();
        if (decl == null) {
            return syntaxError("missing Variable token:" + this.curToken);
        }
        getRawToken();
        LambdaExp lexp = new LambdaExp(1);
        lexp.addDeclaration(decl);
        decl.noteValue((Expression) null);
        decl.setFlag(262144);
        decl.setTypeExp(parseOptionalTypeDeclaration());
        if (match("in")) {
            getRawToken();
        } else {
            if (this.curToken == 76) {
                getRawToken();
            }
            syntaxError("missing 'in' in QuantifiedExpr");
        }
        Expression[] inits = {parseExprSingle()};
        popNesting(saveNesting);
        this.comp.push((ScopeExp) lexp);
        String str = "some";
        if (this.curToken == 44) {
            getRawToken();
            if (this.curToken != 36) {
                return syntaxError("missing $NAME after ','");
            }
            body = parseQuantifiedExpr(isEvery);
        } else {
            boolean sawSatisfies = match("satisfies");
            if (!sawSatisfies && !match("every") && !match(str)) {
                return syntaxError("missing 'satisfies' clause");
            }
            peekNonSpace("unexpected eof-of-file after 'satisfies'");
            int bodyLine = getLineNumber() + 1;
            int bodyColumn = getColumnNumber() + 1;
            if (sawSatisfies) {
                getRawToken();
            }
            Expression body2 = parseExprSingle();
            maybeSetLine(body2, bodyLine, bodyColumn);
            body = body2;
        }
        this.comp.pop(lexp);
        lexp.body = body;
        Expression[] args = {lexp, inits[0]};
        if (isEvery) {
            str = "every";
        }
        return new ApplyExp(makeFunctionExp("gnu.xquery.util.ValuesEvery", str), args);
    }

    public Expression parseFunctionDefinition(int declLine, int declColumn) throws IOException, SyntaxException {
        Expression err;
        if (this.curToken != 81 && this.curToken != 65) {
            return syntaxError("missing function name");
        }
        String name = new String(this.tokenBuffer, 0, this.tokenBufferLength);
        getMessages().setLine(this.port.getName(), this.curLine, this.curColumn);
        Symbol sym = namespaceResolve(name, true);
        String uri = sym.getNamespaceURI();
        char c = 'e';
        if (uri == NamespaceBinding.XML_NAMESPACE || uri == XQuery.SCHEMA_NAMESPACE || uri == XQuery.SCHEMA_INSTANCE_NAMESPACE || uri == XQuery.XQUERY_FUNCTION_NAMESPACE) {
            error('e', "cannot declare function in standard namespace '" + uri + '\'', "XQST0045");
        } else if (uri == "") {
            if (!this.comp.isPedantic()) {
                c = 'w';
            }
            error(c, "cannot declare function in empty namespace", "XQST0060");
        } else if (!(this.libraryModuleNamespace == null || uri == this.libraryModuleNamespace || (XQuery.LOCAL_NAMESPACE.equals(uri) && !this.comp.isPedantic()))) {
            error('e', "function not in namespace of library module", "XQST0048");
        }
        getRawToken();
        if (this.curToken != 40) {
            return syntaxError("missing parameter list:" + this.curToken);
        }
        getRawToken();
        LambdaExp lexp = new LambdaExp();
        maybeSetLine((Expression) lexp, declLine, declColumn);
        lexp.setName(name);
        Declaration decl = this.comp.currentScope().addDeclaration((Object) sym);
        if (this.comp.isStatic()) {
            decl.setFlag(2048);
        }
        lexp.setFlag(2048);
        decl.setCanRead(true);
        decl.setProcedureDecl(true);
        maybeSetLine(decl, declLine, declColumn);
        this.comp.push((ScopeExp) lexp);
        if (this.curToken != 41) {
            loop0:
            while (true) {
                Declaration param = parseVariableDeclaration();
                if (param == null) {
                    error("missing parameter name");
                } else {
                    lexp.addDeclaration(param);
                    getRawToken();
                    lexp.min_args++;
                    lexp.max_args++;
                    param.setTypeExp(parseOptionalTypeDeclaration());
                }
                if (this.curToken == 41) {
                    break;
                } else if (this.curToken != 44) {
                    err = syntaxError("missing ',' in parameter list");
                    do {
                        getRawToken();
                        if (this.curToken < 0 || this.curToken == 59 || this.curToken == 59) {
                            return err;
                        }
                        if (this.curToken == 41) {
                            break loop0;
                        }
                    } while (this.curToken != 44);
                } else {
                    getRawToken();
                }
            }
            return err;
        }
        getRawToken();
        Expression retType = parseOptionalTypeDeclaration();
        lexp.body = parseEnclosedExpr();
        this.comp.pop(lexp);
        if (retType != null) {
            lexp.setCoercedReturnValue(retType, this.interpreter);
        }
        SetExp sexp = new SetExp(decl, (Expression) lexp);
        sexp.setDefining(true);
        decl.noteValue(lexp);
        return sexp;
    }

    public Object readObject() throws IOException, SyntaxException {
        return parse((Compilation) null);
    }

    /* access modifiers changed from: protected */
    public Symbol namespaceResolve(String name, boolean function) {
        int colon = name.indexOf(58);
        String prefix = colon >= 0 ? name.substring(0, colon).intern() : function ? XQuery.DEFAULT_FUNCTION_PREFIX : XQuery.DEFAULT_ELEMENT_PREFIX;
        String uri = QNameUtils.lookupPrefix(prefix, this.constructorNamespaces, this.prologNamespaces);
        if (uri == null) {
            if (colon < 0) {
                uri = "";
            } else if (!this.comp.isPedantic()) {
                try {
                    Class<?> cls = Class.forName(prefix);
                    uri = "class:" + prefix;
                } catch (Exception e) {
                    uri = null;
                }
            }
            if (uri == null) {
                getMessages().error('e', "unknown namespace prefix '" + prefix + "'", "XPST0081");
                uri = "(unknown namespace)";
            }
        }
        return Symbol.make(uri, colon < 0 ? name : name.substring(colon + 1), prefix);
    }

    /* access modifiers changed from: package-private */
    public void parseSeparator() throws IOException, SyntaxException {
        boolean z = true;
        int startLine = this.port.getLineNumber() + 1;
        int startColumn = this.port.getColumnNumber() + 1;
        if (this.nesting == 0) {
            z = false;
        }
        int next = skipSpace(z);
        if (next != 59) {
            if (warnOldVersion && next != 10) {
                this.curLine = startLine;
                this.curColumn = startColumn;
                error('w', "missing ';' after declaration");
            }
            if (next >= 0) {
                unread(next);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:86:0x01ab  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression parse(gnu.expr.Compilation r18) throws java.io.IOException, gnu.text.SyntaxException {
        /*
            r17 = this;
            r1 = r17
            r8 = r18
            r1.comp = r8
            int r0 = r17.skipSpace()
            r2 = 0
            if (r0 >= 0) goto L_0x000e
            return r2
        L_0x000e:
            int r3 = r1.parseCount
            r9 = 1
            int r3 = r3 + r9
            r1.parseCount = r3
            r1.unread(r0)
            int r3 = r17.getLineNumber()
            int r3 = r3 + r9
            int r4 = r17.getColumnNumber()
            int r4 = r4 + r9
            r5 = 35
            r6 = 10
            if (r0 != r5) goto L_0x0050
            if (r3 != r9) goto L_0x0050
            if (r4 != r9) goto L_0x0050
            r17.read()
            int r0 = r17.read()
            r5 = 33
            if (r0 != r5) goto L_0x003e
            int r0 = r17.read()
            r5 = 47
            if (r0 == r5) goto L_0x0043
        L_0x003e:
            java.lang.String r5 = "'#' is only allowed in initial '#!/PROGRAM'"
            r1.error(r5)
        L_0x0043:
            r5 = 13
            if (r0 == r5) goto L_0x0050
            if (r0 == r6) goto L_0x0050
            if (r0 < 0) goto L_0x0050
            int r0 = r17.read()
            goto L_0x0043
        L_0x0050:
            int r0 = r17.getRawToken()
            r5 = -1
            if (r0 != r5) goto L_0x0058
            return r2
        L_0x0058:
            r17.peekOperand()
            int r0 = r1.curToken
            r7 = 119(0x77, float:1.67E-43)
            java.lang.String r10 = "namespace"
            r11 = 65
            if (r0 != r11) goto L_0x007c
            java.lang.Object r0 = r1.curValue
            java.lang.String r0 = (java.lang.String) r0
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x007c
            boolean r0 = warnOldVersion
            if (r0 == 0) goto L_0x0078
            java.lang.String r0 = "use 'declare namespace' instead of 'namespace'"
            r1.error(r7, r0)
        L_0x0078:
            r0 = 78
            r1.curToken = r0
        L_0x007c:
            int r0 = r1.curToken
            java.lang.String r12 = "missing '=' in namespace declaration"
            java.lang.String r13 = "XQST0088"
            java.lang.String r14 = "missing namespace prefix"
            java.lang.String r15 = "missing uri in namespace declaration"
            java.lang.String r2 = "strip"
            java.lang.String r6 = "preserve"
            r11 = 34
            r5 = 101(0x65, float:1.42E-43)
            r7 = 0
            switch(r0) {
                case 66: goto L_0x082d;
                case 69: goto L_0x0790;
                case 71: goto L_0x073f;
                case 72: goto L_0x06f6;
                case 73: goto L_0x055c;
                case 75: goto L_0x052a;
                case 76: goto L_0x04b8;
                case 77: goto L_0x03a6;
                case 78: goto L_0x03a6;
                case 79: goto L_0x0790;
                case 80: goto L_0x037e;
                case 83: goto L_0x032e;
                case 84: goto L_0x04af;
                case 85: goto L_0x02f8;
                case 86: goto L_0x01f3;
                case 87: goto L_0x01ca;
                case 89: goto L_0x0109;
                case 111: goto L_0x00a4;
                default: goto L_0x0092;
            }
        L_0x0092:
            r15 = 101(0x65, float:1.42E-43)
            r0 = -1
            gnu.expr.Expression r0 = r1.parseExprSequence(r0, r9)
            int r2 = r1.curToken
            r5 = 10
            if (r2 != r5) goto L_0x0854
            r1.unread(r5)
            goto L_0x0854
        L_0x00a4:
            r17.getRawToken()
            int r0 = r1.curToken
            r2 = 81
            if (r0 == r2) goto L_0x00b3
            java.lang.String r0 = "expected QName after 'declare option'"
            r1.syntaxError(r0)
            goto L_0x0101
        L_0x00b3:
            java.lang.String r0 = new java.lang.String
            char[] r2 = r1.tokenBuffer
            int r3 = r1.tokenBufferLength
            r0.<init>(r2, r7, r3)
            gnu.text.SourceMessages r2 = r17.getMessages()
            gnu.text.LineBufferedReader r3 = r1.port
            java.lang.String r3 = r3.getName()
            int r4 = r1.curLine
            int r5 = r1.curColumn
            r2.setLine(r3, r4, r5)
            gnu.mapping.Symbol r2 = r1.namespaceResolve(r0, r7)
            r17.getRawToken()
            int r3 = r1.curToken
            if (r3 == r11) goto L_0x00f5
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "expected string literal after 'declare option "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r0 = r2.append(r0)
            r2 = 39
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            r1.syntaxError(r0)
            goto L_0x0101
        L_0x00f5:
            java.lang.String r0 = new java.lang.String
            char[] r3 = r1.tokenBuffer
            int r4 = r1.tokenBufferLength
            r0.<init>(r3, r7, r4)
            r1.handleOption(r2, r0)
        L_0x0101:
            r17.parseSeparator()
            r1.seenDeclaration = r9
            gnu.expr.QuoteExp r0 = gnu.expr.QuoteExp.voidExp
            return r0
        L_0x0109:
            int r0 = r1.parseCount
            if (r0 == r9) goto L_0x0113
            java.lang.String r0 = "'xquery version' does not start module"
            r1.error(r5, r0)
            goto L_0x011e
        L_0x0113:
            int r0 = r1.commentCount
            if (r0 <= 0) goto L_0x011e
            java.lang.String r0 = "comments should not precede 'xquery version'"
            r2 = 119(0x77, float:1.67E-43)
            r1.error(r2, r0)
        L_0x011e:
            r17.getRawToken()
            int r0 = r1.curToken
            if (r0 != r11) goto L_0x01c3
            java.lang.String r0 = new java.lang.String
            char[] r2 = r1.tokenBuffer
            int r3 = r1.tokenBufferLength
            r0.<init>(r2, r7, r3)
            java.lang.String r2 = "1.0"
            boolean r2 = r0.equals(r2)
            if (r2 != 0) goto L_0x014e
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "unrecognized xquery version "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            java.lang.String r2 = "XQST0031"
            r1.error(r5, r0, r2)
        L_0x014e:
            r17.getRawToken()
            java.lang.String r0 = "encoding"
            boolean r0 = r1.match(r0)
            if (r0 == 0) goto L_0x01b5
            r17.getRawToken()
            int r0 = r1.curToken
            if (r0 == r11) goto L_0x0168
            java.lang.String r0 = "invalid encoding specification"
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x0168:
            java.lang.String r0 = new java.lang.String
            char[] r2 = r1.tokenBuffer
            int r3 = r1.tokenBufferLength
            r0.<init>(r2, r7, r3)
            int r0 = r1.tokenBufferLength
            if (r0 != 0) goto L_0x0176
            r7 = 1
        L_0x0176:
            r2 = -1
            int r0 = r0 + r2
            if (r0 < 0) goto L_0x01a9
            if (r7 != 0) goto L_0x01a9
            char[] r2 = r1.tokenBuffer
            char r2 = r2[r0]
            r3 = 65
            if (r2 < r3) goto L_0x0188
            r3 = 90
            if (r2 <= r3) goto L_0x0176
        L_0x0188:
            r3 = 97
            if (r2 < r3) goto L_0x0191
            r3 = 122(0x7a, float:1.71E-43)
            if (r2 > r3) goto L_0x0191
            goto L_0x0176
        L_0x0191:
            if (r0 == 0) goto L_0x01a7
            r3 = 48
            if (r2 < r3) goto L_0x019b
            r3 = 57
            if (r2 <= r3) goto L_0x0176
        L_0x019b:
            r3 = 46
            if (r2 == r3) goto L_0x0176
            r3 = 95
            if (r2 == r3) goto L_0x0176
            r3 = 45
            if (r2 == r3) goto L_0x0176
        L_0x01a7:
            r7 = 1
            goto L_0x0176
        L_0x01a9:
            if (r7 == 0) goto L_0x01b2
            java.lang.String r0 = "invalid encoding name syntax"
            java.lang.String r2 = "XQST0087"
            r1.error(r5, r0, r2)
        L_0x01b2:
            r17.getRawToken()
        L_0x01b5:
            int r0 = r1.curToken
            r2 = 59
            if (r0 == r2) goto L_0x01c0
            java.lang.String r0 = "missing ';'"
            r1.syntaxError(r0)
        L_0x01c0:
            gnu.expr.QuoteExp r0 = gnu.expr.QuoteExp.voidExp
            return r0
        L_0x01c3:
            java.lang.String r0 = "missing version string after 'xquery version'"
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x01ca:
            int r0 = r17.getLineNumber()
            int r0 = r0 + r9
            int r2 = r17.getColumnNumber()
            int r2 = r2 + r9
            java.lang.String r3 = "unexpected end-of-file after 'define QName'"
            int r3 = r1.peekNonSpace(r3)
            r4 = 40
            if (r3 != r4) goto L_0x01ec
            java.lang.String r3 = "'missing 'function' after 'define'"
            r1.syntaxError(r3)
            r3 = 65
            r1.curToken = r3
            gnu.expr.Expression r0 = r1.parseFunctionDefinition(r0, r2)
            return r0
        L_0x01ec:
            java.lang.String r0 = "missing keyword after 'define'"
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x01f3:
            r17.getRawToken()
            gnu.expr.Declaration r0 = r17.parseVariableDeclaration()
            if (r0 != 0) goto L_0x0203
            java.lang.String r0 = "missing Variable"
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x0203:
            java.lang.Object r2 = r0.getSymbol()
            boolean r6 = r2 instanceof java.lang.String
            if (r6 == 0) goto L_0x0225
            gnu.text.SourceMessages r6 = r17.getMessages()
            gnu.text.LineBufferedReader r10 = r1.port
            java.lang.String r10 = r10.getName()
            int r11 = r1.curLine
            int r12 = r1.curColumn
            r6.setLine(r10, r11, r12)
            java.lang.String r2 = (java.lang.String) r2
            gnu.mapping.Symbol r2 = r1.namespaceResolve(r2, r7)
            r0.setSymbol(r2)
        L_0x0225:
            java.lang.String r2 = r1.libraryModuleNamespace
            if (r2 == 0) goto L_0x024c
            java.lang.Object r2 = r0.getSymbol()
            gnu.mapping.Symbol r2 = (gnu.mapping.Symbol) r2
            java.lang.String r2 = r2.getNamespaceURI()
            java.lang.String r6 = r1.libraryModuleNamespace
            if (r2 == r6) goto L_0x024c
            java.lang.String r6 = "http://www.w3.org/2005/xquery-local-functions"
            boolean r2 = r6.equals(r2)
            if (r2 == 0) goto L_0x0245
            boolean r2 = r18.isPedantic()
            if (r2 == 0) goto L_0x024c
        L_0x0245:
            java.lang.String r2 = "variable not in namespace of library module"
            java.lang.String r6 = "XQST0048"
            r1.error(r5, r2, r6)
        L_0x024c:
            gnu.expr.ScopeExp r2 = r18.currentScope()
            r2.addDeclaration((gnu.expr.Declaration) r0)
            r17.getRawToken()
            gnu.expr.Expression r2 = r17.parseOptionalTypeDeclaration()
            r0.setCanRead(r9)
            r5 = 16384(0x4000, double:8.0948E-320)
            r0.setFlag(r5)
            int r5 = r1.curToken
            r6 = 402(0x192, float:5.63E-43)
            if (r5 == r6) goto L_0x0273
            int r5 = r1.curToken
            r6 = 76
            if (r5 != r6) goto L_0x0271
            goto L_0x0273
        L_0x0271:
            r5 = 0
            goto L_0x0282
        L_0x0273:
            int r5 = r1.curToken
            r6 = 402(0x192, float:5.63E-43)
            if (r5 != r6) goto L_0x027e
            java.lang.String r5 = "declare variable contains '=' instead of ':='"
            r1.error(r5)
        L_0x027e:
            r17.getRawToken()
            r5 = 1
        L_0x0282:
            int r6 = r1.curToken
            r8 = 123(0x7b, float:1.72E-43)
            if (r6 != r8) goto L_0x0295
            java.lang.String r5 = "obsolete '{' in variable declaration"
            r1.warnOldVersion(r5)
            gnu.expr.Expression r5 = r17.parseEnclosedExpr()
            r17.parseSeparator()
            goto L_0x02e5
        L_0x0295:
            java.lang.String r6 = "external"
            boolean r6 = r1.match(r6)
            if (r6 == 0) goto L_0x02ca
            r5 = 2
            gnu.expr.Expression[] r5 = new gnu.expr.Expression[r5]
            gnu.expr.QuoteExp r6 = new gnu.expr.QuoteExp
            java.lang.Object r8 = r0.getSymbol()
            r6.<init>(r8)
            gnu.expr.ApplyExp r6 = castQName(r6, r7)
            r5[r7] = r6
            if (r2 != 0) goto L_0x02b4
            gnu.expr.QuoteExp r6 = gnu.expr.QuoteExp.nullExp
            goto L_0x02b5
        L_0x02b4:
            r6 = r2
        L_0x02b5:
            r5[r9] = r6
            gnu.expr.ApplyExp r6 = new gnu.expr.ApplyExp
            gnu.expr.QuoteExp r7 = getExternalFunction
            r6.<init>((gnu.expr.Expression) r7, (gnu.expr.Expression[]) r5)
            int r5 = r1.curLine
            int r7 = r1.curColumn
            r1.maybeSetLine((gnu.expr.Expression) r6, (int) r5, (int) r7)
            r17.getRawToken()
            r5 = r6
            goto L_0x02e5
        L_0x02ca:
            gnu.expr.Expression r6 = r17.parseExpr()
            if (r5 == 0) goto L_0x02d7
            if (r6 != 0) goto L_0x02d4
            goto L_0x02d7
        L_0x02d4:
            r16 = 0
            goto L_0x02df
        L_0x02d7:
            java.lang.String r5 = "expected ':= init' or 'external'"
            gnu.expr.Expression r5 = r1.syntaxError(r5)
            r16 = r5
        L_0x02df:
            if (r6 != 0) goto L_0x02e4
            r5 = r16
            goto L_0x02e5
        L_0x02e4:
            r5 = r6
        L_0x02e5:
            if (r2 == 0) goto L_0x02eb
            gnu.expr.ApplyExp r5 = gnu.expr.Compilation.makeCoercion((gnu.expr.Expression) r5, (gnu.expr.Expression) r2)
        L_0x02eb:
            r0.noteValue(r5)
            gnu.expr.SetExp r0 = gnu.expr.SetExp.makeDefinition((gnu.expr.Declaration) r0, (gnu.expr.Expression) r5)
            r1.maybeSetLine((gnu.expr.Expression) r0, (int) r3, (int) r4)
            r1.seenDeclaration = r9
            return r0
        L_0x02f8:
            boolean r0 = r1.orderingModeSeen
            if (r0 == 0) goto L_0x0307
            boolean r0 = r1.interactive
            if (r0 != 0) goto L_0x0307
            java.lang.String r0 = "duplicate 'declare ordering' seen"
            java.lang.String r2 = "XQST0065"
            r1.syntaxError(r0, r2)
        L_0x0307:
            r1.orderingModeSeen = r9
            r17.getRawToken()
            java.lang.String r0 = "ordered"
            boolean r0 = r1.match(r0)
            if (r0 == 0) goto L_0x0317
            r1.orderingModeUnordered = r7
            goto L_0x0321
        L_0x0317:
            java.lang.String r0 = "unordered"
            boolean r0 = r1.match(r0)
            if (r0 == 0) goto L_0x0327
            r1.orderingModeUnordered = r9
        L_0x0321:
            r17.parseSeparator()
            gnu.expr.QuoteExp r0 = gnu.expr.QuoteExp.voidExp
            return r0
        L_0x0327:
            java.lang.String r0 = "ordering declaration must be ordered or unordered"
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x032e:
            r17.getRawToken()
            int r0 = r1.curToken
            r3 = 402(0x192, float:5.63E-43)
            if (r0 != r3) goto L_0x033f
            java.lang.String r0 = "obsolate '=' in boundary-space declaration"
            r1.warnOldVersion(r0)
            r17.getRawToken()
        L_0x033f:
            boolean r0 = r1.boundarySpaceDeclarationSeen
            if (r0 == 0) goto L_0x034e
            boolean r0 = r1.interactive
            if (r0 != 0) goto L_0x034e
            java.lang.String r0 = "duplicate 'declare boundary-space' seen"
            java.lang.String r3 = "XQST0068"
            r1.syntaxError(r0, r3)
        L_0x034e:
            r1.boundarySpaceDeclarationSeen = r9
            boolean r0 = r1.match(r6)
            if (r0 == 0) goto L_0x0359
            r1.boundarySpacePreserve = r9
            goto L_0x0371
        L_0x0359:
            boolean r0 = r1.match(r2)
            if (r0 == 0) goto L_0x0362
            r1.boundarySpacePreserve = r7
            goto L_0x0371
        L_0x0362:
            java.lang.String r0 = "skip"
            boolean r0 = r1.match(r0)
            if (r0 == 0) goto L_0x0377
            java.lang.String r0 = "update: declare boundary-space skip -> strip"
            r1.warnOldVersion(r0)
            r1.boundarySpacePreserve = r7
        L_0x0371:
            r17.parseSeparator()
            gnu.expr.QuoteExp r0 = gnu.expr.QuoteExp.voidExp
            return r0
        L_0x0377:
            java.lang.String r0 = "boundary-space declaration must be preserve or strip"
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x037e:
            int r0 = r17.getLineNumber()
            int r0 = r0 + r9
            int r2 = r17.getColumnNumber()
            int r2 = r2 + r9
            r17.getRawToken()
            java.lang.String r5 = "unexpected end-of-file after 'define function'"
            r1.peekNonSpace(r5)
            r5 = 100
            char r5 = r1.pushNesting(r5)
            gnu.expr.Expression r0 = r1.parseFunctionDefinition(r0, r2)
            r1.popNesting(r5)
            r17.parseSeparator()
            r1.maybeSetLine((gnu.expr.Expression) r0, (int) r3, (int) r4)
            r1.seenDeclaration = r9
            return r0
        L_0x03a6:
            int r0 = r1.curToken
            r2 = 77
            if (r0 != r2) goto L_0x03b6
            java.lang.String r6 = r1.libraryModuleNamespace
            if (r6 == 0) goto L_0x03b6
            java.lang.String r6 = "duplicate module declaration"
            r1.error(r5, r6)
            goto L_0x03c3
        L_0x03b6:
            boolean r6 = r1.seenDeclaration
            if (r6 == 0) goto L_0x03c3
            boolean r6 = r1.interactive
            if (r6 != 0) goto L_0x03c3
            java.lang.String r6 = "namespace declared after function/variable/option"
            r1.error(r5, r6)
        L_0x03c3:
            int r6 = r1.nesting
            if (r6 == 0) goto L_0x03c9
            r6 = 1
            goto L_0x03ca
        L_0x03c9:
            r6 = 0
        L_0x03ca:
            int r6 = r1.skipSpace(r6)
            if (r6 < 0) goto L_0x04af
            r17.unread()
            char r6 = (char) r6
            boolean r6 = gnu.xml.XName.isNameStart(r6)
            if (r6 == 0) goto L_0x04af
            r17.getRawToken()
            int r3 = r1.curToken
            r4 = 65
            if (r3 == r4) goto L_0x03e8
            gnu.expr.Expression r0 = r1.syntaxError(r14)
            return r0
        L_0x03e8:
            java.lang.String r3 = new java.lang.String
            char[] r4 = r1.tokenBuffer
            int r6 = r1.tokenBufferLength
            r3.<init>(r4, r7, r6)
            r17.getRawToken()
            int r4 = r1.curToken
            r6 = 402(0x192, float:5.63E-43)
            if (r4 == r6) goto L_0x03ff
            gnu.expr.Expression r0 = r1.syntaxError(r12)
            return r0
        L_0x03ff:
            r17.getRawToken()
            int r4 = r1.curToken
            if (r4 == r11) goto L_0x040b
            gnu.expr.Expression r0 = r1.syntaxError(r15)
            return r0
        L_0x040b:
            java.lang.String r4 = new java.lang.String
            char[] r6 = r1.tokenBuffer
            int r9 = r1.tokenBufferLength
            r4.<init>(r6, r7, r9)
            java.lang.String r4 = r4.intern()
            java.lang.String r3 = r3.intern()
            gnu.xml.NamespaceBinding r6 = r1.prologNamespaces
        L_0x041e:
            gnu.xml.NamespaceBinding r9 = builtinNamespaces
            if (r6 == r9) goto L_0x044c
            java.lang.String r9 = r6.getPrefix()
            if (r9 != r3) goto L_0x0447
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r9 = "duplicate declarations for the same namespace prefix '"
            java.lang.StringBuilder r6 = r6.append(r9)
            java.lang.StringBuilder r6 = r6.append(r3)
            java.lang.String r9 = "'"
            java.lang.StringBuilder r6 = r6.append(r9)
            java.lang.String r6 = r6.toString()
            java.lang.String r9 = "XQST0033"
            r1.error(r5, r6, r9)
            goto L_0x044c
        L_0x0447:
            gnu.xml.NamespaceBinding r6 = r6.getNext()
            goto L_0x041e
        L_0x044c:
            r1.pushNamespace(r3, r4)
            r1.checkAllowedNamespaceDeclaration(r3, r4, r7)
            r17.parseSeparator()
            if (r0 != r2) goto L_0x04ac
            gnu.expr.ModuleExp r0 = r18.getModule()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = gnu.expr.Compilation.mangleURI(r4)
            java.lang.StringBuilder r2 = r2.append(r3)
            r3 = 46
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = r0.getFileName()
            java.lang.String r3 = gnu.xquery.lang.XQuery.makeClassName(r3)
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.setName(r2)
            gnu.bytecode.ClassType r3 = new gnu.bytecode.ClassType
            r3.<init>(r2)
            r8.mainClass = r3
            gnu.bytecode.ClassType r2 = r8.mainClass
            r0.setType(r2)
            gnu.expr.ModuleManager r2 = gnu.expr.ModuleManager.getInstance()
            gnu.expr.ModuleInfo r2 = r2.find(r8)
            r2.setNamespaceUri(r4)
            gnu.bytecode.ClassType r2 = r8.mainClass
            r0.setType(r2)
            int r0 = r4.length()
            if (r0 != 0) goto L_0x04aa
            java.lang.String r0 = "zero-length module namespace"
            gnu.expr.Expression r0 = r1.syntaxError(r0, r13)
            return r0
        L_0x04aa:
            r1.libraryModuleNamespace = r4
        L_0x04ac:
            gnu.expr.QuoteExp r0 = gnu.expr.QuoteExp.voidExp
            return r0
        L_0x04af:
            java.lang.String r0 = "'import schema' not implemented"
            java.lang.String r2 = "XQST0009"
            r1.fatal(r0, r2)
            goto L_0x055c
        L_0x04b8:
            r17.getRawToken()
            boolean r0 = r1.copyNamespacesDeclarationSeen
            if (r0 == 0) goto L_0x04ca
            boolean r0 = r1.interactive
            if (r0 != 0) goto L_0x04ca
            java.lang.String r0 = "duplicate 'declare copy-namespaces' seen"
            java.lang.String r2 = "XQST0055"
            r1.syntaxError(r0, r2)
        L_0x04ca:
            r1.copyNamespacesDeclarationSeen = r9
            boolean r0 = r1.match(r6)
            if (r0 == 0) goto L_0x04d8
            int r0 = r1.copyNamespacesMode
            r0 = r0 | r9
            r1.copyNamespacesMode = r0
            goto L_0x04e6
        L_0x04d8:
            java.lang.String r0 = "no-preserve"
            boolean r0 = r1.match(r0)
            if (r0 == 0) goto L_0x0523
            int r0 = r1.copyNamespacesMode
            r0 = r0 & -2
            r1.copyNamespacesMode = r0
        L_0x04e6:
            r17.getRawToken()
            int r0 = r1.curToken
            r2 = 44
            if (r0 == r2) goto L_0x04f6
            java.lang.String r0 = "missing ',' in copy-namespaces declaration"
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x04f6:
            r17.getRawToken()
            java.lang.String r0 = "inherit"
            boolean r0 = r1.match(r0)
            if (r0 == 0) goto L_0x0508
            int r0 = r1.copyNamespacesMode
            r0 = r0 | 2
            r1.copyNamespacesMode = r0
            goto L_0x0516
        L_0x0508:
            java.lang.String r0 = "no-inherit"
            boolean r0 = r1.match(r0)
            if (r0 == 0) goto L_0x051c
            int r0 = r1.copyNamespacesMode
            r0 = r0 & -3
            r1.copyNamespacesMode = r0
        L_0x0516:
            r17.parseSeparator()
            gnu.expr.QuoteExp r0 = gnu.expr.QuoteExp.voidExp
            return r0
        L_0x051c:
            java.lang.String r0 = "expected 'inherit' or 'no-inherit' in copy-namespaces declaration"
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x0523:
            java.lang.String r0 = "expected 'preserve' or 'no-preserve' after 'declare copy-namespaces'"
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x052a:
            r17.getRawToken()
            boolean r0 = r1.constructionModeDeclarationSeen
            if (r0 == 0) goto L_0x053c
            boolean r0 = r1.interactive
            if (r0 != 0) goto L_0x053c
            java.lang.String r0 = "duplicate 'declare construction' seen"
            java.lang.String r3 = "XQST0067"
            r1.syntaxError(r0, r3)
        L_0x053c:
            r1.constructionModeDeclarationSeen = r9
            boolean r0 = r1.match(r2)
            if (r0 == 0) goto L_0x0547
            r1.constructionModeStrip = r9
            goto L_0x054f
        L_0x0547:
            boolean r0 = r1.match(r6)
            if (r0 == 0) goto L_0x0555
            r1.constructionModeStrip = r7
        L_0x054f:
            r17.parseSeparator()
            gnu.expr.QuoteExp r0 = gnu.expr.QuoteExp.voidExp
            return r0
        L_0x0555:
            java.lang.String r0 = "construction declaration must be strip or preserve"
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x055c:
            r17.getRawToken()
            boolean r0 = r1.match(r10)
            if (r0 == 0) goto L_0x058f
            r17.getRawToken()
            int r0 = r1.curToken
            r2 = 65
            if (r0 == r2) goto L_0x0574
            gnu.expr.Expression r0 = r1.syntaxError(r14)
            return r0
        L_0x0574:
            java.lang.String r2 = new java.lang.String
            char[] r0 = r1.tokenBuffer
            int r6 = r1.tokenBufferLength
            r2.<init>(r0, r7, r6)
            r17.getRawToken()
            int r0 = r1.curToken
            r6 = 402(0x192, float:5.63E-43)
            if (r0 == r6) goto L_0x058b
            gnu.expr.Expression r0 = r1.syntaxError(r12)
            return r0
        L_0x058b:
            r17.getRawToken()
            goto L_0x0590
        L_0x058f:
            r2 = 0
        L_0x0590:
            int r0 = r1.curToken
            if (r0 == r11) goto L_0x0599
            gnu.expr.Expression r0 = r1.syntaxError(r15)
            return r0
        L_0x0599:
            int r0 = r1.tokenBufferLength
            if (r0 != 0) goto L_0x05a4
            java.lang.String r0 = "zero-length target namespace"
            gnu.expr.Expression r0 = r1.syntaxError(r0, r13)
            return r0
        L_0x05a4:
            java.lang.String r0 = new java.lang.String
            char[] r6 = r1.tokenBuffer
            int r10 = r1.tokenBufferLength
            r0.<init>(r6, r7, r10)
            java.lang.String r10 = r0.intern()
            if (r2 == 0) goto L_0x05bd
            r1.checkAllowedNamespaceDeclaration(r2, r10, r7)
            java.lang.String r0 = r2.intern()
            r1.pushNamespace(r0, r10)
        L_0x05bd:
            r17.getRawToken()
            gnu.expr.ModuleManager r0 = gnu.expr.ModuleManager.getInstance()
            r0.find(r8)
            gnu.expr.ModuleExp r12 = r18.getModule()
            java.util.Vector r13 = new java.util.Vector
            r13.<init>()
            java.lang.String r0 = gnu.expr.Compilation.mangleURI(r10)
            gnu.text.LineBufferedReader r2 = r1.port
            java.lang.String r2 = r2.getName()
            r8.setLine(r2, r3, r4)
            java.lang.String r2 = "at"
            boolean r2 = r1.match(r2)
            if (r2 == 0) goto L_0x065d
        L_0x05e5:
            r17.getRawToken()
            int r0 = r1.curToken
            if (r0 == r11) goto L_0x05f3
            java.lang.String r0 = "missing module location"
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x05f3:
            java.lang.String r0 = new java.lang.String
            char[] r2 = r1.tokenBuffer
            int r3 = r1.tokenBufferLength
            r0.<init>(r2, r7, r3)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = gnu.expr.Compilation.mangleURI(r10)
            java.lang.StringBuilder r2 = r2.append(r3)
            r14 = 46
            java.lang.StringBuilder r2 = r2.append(r14)
            java.lang.String r3 = gnu.xquery.lang.XQuery.makeClassName(r0)
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            gnu.expr.ModuleInfo r3 = kawa.standard.require.lookupModuleFromSourcePath(r0, r12)
            if (r3 != 0) goto L_0x0637
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "malformed URL: "
            java.lang.StringBuilder r4 = r4.append(r6)
            java.lang.StringBuilder r0 = r4.append(r0)
            java.lang.String r0 = r0.toString()
            r8.error(r5, r0)
        L_0x0637:
            r4 = 0
            r15 = 101(0x65, float:1.42E-43)
            r5 = r13
            r6 = r12
            r14 = 0
            r7 = r18
            kawa.standard.require.importDefinitions(r2, r3, r4, r5, r6, r7)
            int r0 = r1.nesting
            if (r0 == 0) goto L_0x0648
            r7 = 1
            goto L_0x0649
        L_0x0648:
            r7 = 0
        L_0x0649:
            int r0 = r1.skipSpace(r7)
            r2 = 44
            if (r0 == r2) goto L_0x0659
            r1.unread(r0)
            r17.parseSeparator()
            goto L_0x06b8
        L_0x0659:
            r5 = 101(0x65, float:1.42E-43)
            r7 = 0
            goto L_0x05e5
        L_0x065d:
            r14 = 0
            r15 = 101(0x65, float:1.42E-43)
            gnu.expr.ModuleManager r11 = gnu.expr.ModuleManager.getInstance()
            r11.loadPackageInfo(r0)     // Catch:{ ClassNotFoundException -> 0x068c, all -> 0x0669 }
        L_0x0668:
            goto L_0x068e
        L_0x0669:
            r0 = move-exception
            r2 = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = "error loading map for "
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.StringBuilder r0 = r0.append(r10)
            java.lang.String r3 = " - "
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            r1.error(r15, r0)
            goto L_0x068e
        L_0x068c:
            r0 = move-exception
            goto L_0x0668
        L_0x068e:
            r7 = 0
        L_0x068f:
            gnu.expr.ModuleInfo r3 = r11.getModule(r14)
            if (r3 != 0) goto L_0x06d9
            if (r7 != 0) goto L_0x06ae
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "no module found for "
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.StringBuilder r0 = r0.append(r10)
            java.lang.String r0 = r0.toString()
            r1.error(r15, r0)
        L_0x06ae:
            int r0 = r1.curToken
            r2 = 59
            if (r0 == r2) goto L_0x06b8
            r17.parseSeparator()
        L_0x06b8:
            java.util.Stack<java.lang.Object> r0 = r8.pendingImports
            if (r0 == 0) goto L_0x06cb
            java.util.Stack<java.lang.Object> r0 = r8.pendingImports
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x06cb
            java.lang.String r0 = "module import forms a cycle"
            java.lang.String r2 = "XQST0073"
            r1.error(r15, r0, r2)
        L_0x06cb:
            int r0 = r13.size()
            gnu.expr.Expression[] r0 = new gnu.expr.Expression[r0]
            r13.toArray(r0)
            gnu.expr.Expression r0 = gnu.expr.BeginExp.canonicalize((gnu.expr.Expression[]) r0)
            return r0
        L_0x06d9:
            java.lang.String r0 = r3.getNamespaceUri()
            boolean r0 = r10.equals(r0)
            if (r0 != 0) goto L_0x06e4
            goto L_0x06f3
        L_0x06e4:
            int r0 = r7 + 1
            java.lang.String r2 = r3.getClassName()
            r4 = 0
            r5 = r13
            r6 = r12
            r7 = r18
            kawa.standard.require.importDefinitions(r2, r3, r4, r5, r6, r7)
            r7 = r0
        L_0x06f3:
            int r14 = r14 + 1
            goto L_0x068f
        L_0x06f6:
            r17.getRawToken()
            java.lang.String r0 = "empty"
            boolean r0 = r1.match(r0)
            boolean r2 = r1.emptyOrderDeclarationSeen
            if (r2 == 0) goto L_0x070e
            boolean r2 = r1.interactive
            if (r2 != 0) goto L_0x070e
            java.lang.String r2 = "duplicate 'declare default empty order' seen"
            java.lang.String r3 = "XQST0069"
            r1.syntaxError(r2, r3)
        L_0x070e:
            r1.emptyOrderDeclarationSeen = r9
            java.lang.String r2 = "expected 'empty greatest' or 'empty least'"
            if (r0 == 0) goto L_0x0718
            r17.getRawToken()
            goto L_0x071b
        L_0x0718:
            r1.syntaxError(r2)
        L_0x071b:
            java.lang.String r0 = "greatest"
            boolean r0 = r1.match(r0)
            if (r0 == 0) goto L_0x0728
            r0 = 71
            r1.defaultEmptyOrder = r0
            goto L_0x0734
        L_0x0728:
            java.lang.String r0 = "least"
            boolean r0 = r1.match(r0)
            if (r0 == 0) goto L_0x073a
            r0 = 76
            r1.defaultEmptyOrder = r0
        L_0x0734:
            r17.parseSeparator()
            gnu.expr.QuoteExp r0 = gnu.expr.QuoteExp.voidExp
            return r0
        L_0x073a:
            gnu.expr.Expression r0 = r1.syntaxError(r2)
            return r0
        L_0x073f:
            r15 = 101(0x65, float:1.42E-43)
            gnu.xquery.util.NamedCollator r0 = r1.defaultCollator
            java.lang.String r2 = "XQST0038"
            if (r0 == 0) goto L_0x0750
            boolean r0 = r1.interactive
            if (r0 != 0) goto L_0x0750
            java.lang.String r0 = "duplicate default collation declaration"
            r1.error(r15, r0, r2)
        L_0x0750:
            java.lang.Object r0 = r17.parseURILiteral()
            boolean r3 = r0 instanceof gnu.expr.Expression
            if (r3 == 0) goto L_0x075b
            gnu.expr.Expression r0 = (gnu.expr.Expression) r0
            return r0
        L_0x075b:
            r3 = r0
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r3 = r1.resolveAgainstBaseUri(r3)     // Catch:{ Exception -> 0x0769 }
            gnu.xquery.util.NamedCollator r0 = gnu.xquery.util.NamedCollator.make(r3)     // Catch:{ Exception -> 0x0769 }
            r1.defaultCollator = r0     // Catch:{ Exception -> 0x0769 }
            goto L_0x078a
        L_0x0769:
            r0 = move-exception
            gnu.xquery.util.NamedCollator r0 = gnu.xquery.util.NamedCollator.codepointCollation
            r1.defaultCollator = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r4 = "unknown collation '"
            java.lang.StringBuilder r0 = r0.append(r4)
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.String r3 = "'"
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.String r0 = r0.toString()
            r1.error(r15, r0, r2)
        L_0x078a:
            r17.parseSeparator()
            gnu.expr.QuoteExp r0 = gnu.expr.QuoteExp.voidExp
            return r0
        L_0x0790:
            r14 = 0
            r15 = 101(0x65, float:1.42E-43)
            int r0 = r1.curToken
            r2 = 79
            if (r0 != r2) goto L_0x079b
            r7 = 1
            goto L_0x079c
        L_0x079b:
            r7 = 0
        L_0x079c:
            if (r7 == 0) goto L_0x07a1
            java.lang.String r0 = "(functions)"
            goto L_0x07a3
        L_0x07a1:
            java.lang.String r0 = gnu.xquery.lang.XQuery.DEFAULT_ELEMENT_PREFIX
        L_0x07a3:
            gnu.xml.NamespaceBinding r2 = r1.prologNamespaces
            gnu.xml.NamespaceBinding r3 = builtinNamespaces
            java.lang.String r2 = r2.resolve(r0, r3)
            if (r2 == 0) goto L_0x07b5
            java.lang.String r2 = "duplicate default namespace declaration"
            java.lang.String r3 = "XQST0066"
            r1.error(r15, r2, r3)
            goto L_0x07c2
        L_0x07b5:
            boolean r2 = r1.seenDeclaration
            if (r2 == 0) goto L_0x07c2
            boolean r2 = r1.interactive
            if (r2 != 0) goto L_0x07c2
            java.lang.String r2 = "default namespace declared after function/variable/option"
            r1.error(r15, r2)
        L_0x07c2:
            r17.getRawToken()
            boolean r2 = r1.match(r10)
            if (r2 == 0) goto L_0x07cf
            r17.getRawToken()
            goto L_0x07e4
        L_0x07cf:
            int r2 = r1.curToken
            java.lang.String r3 = "expected 'namespace' keyword"
            if (r2 == r11) goto L_0x07e1
            int r2 = r1.curToken
            r4 = 402(0x192, float:5.63E-43)
            if (r2 == r4) goto L_0x07e1
            gnu.expr.Expression r0 = r1.declError(r3)
            return r0
        L_0x07e1:
            r1.warnOldVersion(r3)
        L_0x07e4:
            int r2 = r1.curToken
            r3 = 402(0x192, float:5.63E-43)
            if (r2 == r3) goto L_0x07f0
            int r2 = r1.curToken
            r3 = 76
            if (r2 != r3) goto L_0x07f8
        L_0x07f0:
            java.lang.String r2 = "extra '=' in default namespace declaration"
            r1.warnOldVersion(r2)
            r17.getRawToken()
        L_0x07f8:
            int r2 = r1.curToken
            if (r2 == r11) goto L_0x0803
            java.lang.String r0 = "missing namespace uri"
            gnu.expr.Expression r0 = r1.declError(r0)
            return r0
        L_0x0803:
            java.lang.String r2 = new java.lang.String
            char[] r3 = r1.tokenBuffer
            int r4 = r1.tokenBufferLength
            r2.<init>(r3, r14, r4)
            java.lang.String r2 = r2.intern()
            if (r7 == 0) goto L_0x081f
            gnu.mapping.Namespace[] r3 = new gnu.mapping.Namespace[r9]
            r1.functionNamespacePath = r3
            gnu.mapping.Namespace[] r3 = r1.functionNamespacePath
            gnu.mapping.Namespace r4 = gnu.mapping.Namespace.valueOf(r2)
            r3[r14] = r4
            goto L_0x0821
        L_0x081f:
            r1.defaultElementNamespace = r2
        L_0x0821:
            r1.pushNamespace(r0, r2)
            r1.checkAllowedNamespaceDeclaration(r0, r2, r14)
            r17.parseSeparator()
            gnu.expr.QuoteExp r0 = gnu.expr.QuoteExp.voidExp
            return r0
        L_0x082d:
            boolean r0 = r1.baseURIDeclarationSeen
            if (r0 == 0) goto L_0x083c
            boolean r0 = r1.interactive
            if (r0 != 0) goto L_0x083c
            java.lang.String r0 = "duplicate 'declare base-uri' seen"
            java.lang.String r2 = "XQST0032"
            r1.syntaxError(r0, r2)
        L_0x083c:
            r1.baseURIDeclarationSeen = r9
            java.lang.Object r0 = r17.parseURILiteral()
            boolean r2 = r0 instanceof gnu.expr.Expression
            if (r2 == 0) goto L_0x0849
            gnu.expr.Expression r0 = (gnu.expr.Expression) r0
            return r0
        L_0x0849:
            r17.parseSeparator()
            java.lang.String r0 = (java.lang.String) r0
            r1.setStaticBaseUri(r0)
            gnu.expr.QuoteExp r0 = gnu.expr.QuoteExp.voidExp
            return r0
        L_0x0854:
            r1.maybeSetLine((gnu.expr.Expression) r0, (int) r3, (int) r4)
            java.lang.String r2 = r1.libraryModuleNamespace
            if (r2 == 0) goto L_0x0862
            java.lang.String r2 = "query body in a library module"
            java.lang.String r3 = "XPST0003"
            r1.error(r15, r2, r3)
        L_0x0862:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQParser.parse(gnu.expr.Compilation):gnu.expr.Expression");
    }

    public void handleOption(Symbol name, String value) {
    }

    public static Expression makeFunctionExp(String className, String name) {
        return makeFunctionExp(className, Compilation.mangleNameIfNeeded(name), name);
    }

    public static Expression makeFunctionExp(String className, String fieldName, String name) {
        return new ReferenceExp(name, Declaration.getDeclarationValueFromStatic(className, fieldName, name));
    }

    /* access modifiers changed from: package-private */
    public String tokenString() {
        switch (this.curToken) {
            case -1:
                return "<EOF>";
            case 34:
                StringBuffer sbuf = new StringBuffer();
                sbuf.append('\"');
                for (int i = 0; i < this.tokenBufferLength; i++) {
                    char ch = this.tokenBuffer[i];
                    if (ch == '\"') {
                        sbuf.append('\"');
                    }
                    sbuf.append(ch);
                }
                sbuf.append('\"');
                return sbuf.toString();
            case 65:
            case 81:
                return new String(this.tokenBuffer, 0, this.tokenBufferLength);
            case 70:
                return new String(this.tokenBuffer, 0, this.tokenBufferLength) + " + '('";
            default:
                if (this.curToken >= 100 && this.curToken - 100 < 13) {
                    return axisNames[this.curToken - 100] + "::-axis(" + this.curToken + ")";
                }
                if (this.curToken < 32 || this.curToken >= 127) {
                    return Integer.toString(this.curToken);
                }
                return Integer.toString(this.curToken) + "='" + ((char) this.curToken) + "'";
        }
    }

    public void error(char severity, String message, String code) {
        SourceMessages messages = getMessages();
        SourceError err = new SourceError(severity, this.port.getName(), this.curLine, this.curColumn, message);
        err.code = code;
        messages.error(err);
    }

    public void error(char severity, String message) {
        error(severity, message, (String) null);
    }

    public Expression declError(String message) throws IOException, SyntaxException {
        if (this.interactive) {
            return syntaxError(message);
        }
        error(message);
        while (this.curToken != 59 && this.curToken != -1) {
            getRawToken();
        }
        return new ErrorExp(message);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0028, code lost:
        unread(r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression syntaxError(java.lang.String r4, java.lang.String r5) throws java.io.IOException, gnu.text.SyntaxException {
        /*
            r3 = this;
            r0 = 101(0x65, float:1.42E-43)
            r3.error(r0, r4, r5)
            boolean r0 = r3.interactive
            if (r0 == 0) goto L_0x0037
            r0 = 0
            r3.curToken = r0
            r1 = 0
            r3.curValue = r1
            r3.nesting = r0
            gnu.text.LineBufferedReader r0 = r3.getPort()
            gnu.mapping.InPort r0 = (gnu.mapping.InPort) r0
            r1 = 10
            r0.readState = r1
        L_0x001b:
            int r0 = r3.read()
            if (r0 < 0) goto L_0x002c
            r2 = 13
            if (r0 == r2) goto L_0x0028
            if (r0 == r1) goto L_0x0028
            goto L_0x001b
        L_0x0028:
            r3.unread(r0)
            goto L_0x002d
        L_0x002c:
        L_0x002d:
            gnu.text.SyntaxException r0 = new gnu.text.SyntaxException
            gnu.text.SourceMessages r1 = r3.getMessages()
            r0.<init>(r1)
            throw r0
        L_0x0037:
            gnu.expr.ErrorExp r0 = new gnu.expr.ErrorExp
            r0.<init>(r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQParser.syntaxError(java.lang.String, java.lang.String):gnu.expr.Expression");
    }

    public Expression syntaxError(String message) throws IOException, SyntaxException {
        return syntaxError(message, "XPST0003");
    }

    public void eofError(String msg) throws SyntaxException {
        fatal(msg, "XPST0003");
    }

    public void fatal(String msg, String code) throws SyntaxException {
        SourceMessages messages = getMessages();
        SourceError err = new SourceError('f', this.port.getName(), this.curLine, this.curColumn, msg);
        err.code = code;
        messages.error(err);
        throw new SyntaxException(messages);
    }

    /* access modifiers changed from: package-private */
    public void warnOldVersion(String message) {
        if (warnOldVersion || this.comp.isPedantic()) {
            error(this.comp.isPedantic() ? 'e' : 'w', message);
        }
    }

    public void maybeSetLine(Expression exp, int line, int column) {
        String file = getName();
        if (file != null && exp.getFileName() == null && !(exp instanceof QuoteExp)) {
            exp.setFile(file);
            exp.setLine(line, column);
        }
    }

    public void maybeSetLine(Declaration decl, int line, int column) {
        String file = getName();
        if (file != null) {
            decl.setFile(file);
            decl.setLine(line, column);
        }
    }
}
