package gnu.expr;

import gnu.bytecode.ArrayClassLoader;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Label;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.SwitchState;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.kawa.functions.Convert;
import gnu.kawa.lispexpr.LispReader;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.mapping.WrappedException;
import gnu.text.Lexer;
import gnu.text.Options;
import gnu.text.Path;
import gnu.text.SourceLocator;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;
import java.util.jar.JarOutputStream;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kawa.Shell;

public class Compilation implements SourceLocator {
    public static final int BODY_PARSED = 4;
    public static final int CALL_WITH_CONSUMER = 2;
    public static final int CALL_WITH_CONTINUATIONS = 4;
    public static final int CALL_WITH_RETURN = 1;
    public static final int CALL_WITH_TAILCALLS = 3;
    public static final int CALL_WITH_UNSPECIFIED = 0;
    public static final int CLASS_WRITTEN = 14;
    public static final int COMPILED = 12;
    public static final int COMPILE_SETUP = 10;
    public static final int ERROR_SEEN = 100;
    public static final int MODULE_NONSTATIC = -1;
    public static final int MODULE_STATIC = 1;
    public static final int MODULE_STATIC_DEFAULT = 0;
    public static final int MODULE_STATIC_RUN = 2;
    public static final int PROLOG_PARSED = 2;
    public static final int PROLOG_PARSING = 1;
    public static final int RESOLVED = 6;
    public static final int WALKED = 8;
    public static Type[] apply0args = Type.typeArray0;
    public static Method apply0method = typeProcedure.addMethod("apply0", apply0args, (Type) typeObject, 17);
    public static Type[] apply1args = {typeObject};
    public static Method apply1method = typeProcedure.addMethod("apply1", apply1args, (Type) typeObject, 1);
    public static Type[] apply2args = {typeObject, typeObject};
    public static Method apply2method = typeProcedure.addMethod("apply2", apply2args, (Type) typeObject, 1);
    public static Method apply3method = typeProcedure.addMethod("apply3", new Type[]{typeObject, typeObject, typeObject}, (Type) typeObject, 1);
    public static Method apply4method = typeProcedure.addMethod("apply4", new Type[]{typeObject, typeObject, typeObject, typeObject}, (Type) typeObject, 1);
    private static Type[] applyCpsArgs = {typeCallContext};
    public static Method applyCpsMethod = typeProcedure.addMethod("apply", applyCpsArgs, (Type) Type.voidType, 1);
    public static Type[] applyNargs = {objArrayType};
    public static Method applyNmethod = typeProcedure.addMethod("applyN", applyNargs, (Type) typeObject, 1);
    public static Method[] applymethods = {apply0method, apply1method, apply2method, apply3method, apply4method, applyNmethod};
    public static Field argsCallContextField = typeCallContext.getDeclaredField("values");
    private static Compilation chainUninitialized;
    static Method checkArgCountMethod = typeProcedure.addMethod("checkArgCount", new Type[]{typeProcedure, Type.intType}, (Type) Type.voidType, 9);
    public static String classPrefixDefault = "";
    private static final ThreadLocal<Compilation> current = new InheritableThreadLocal();
    public static boolean debugPrintExpr = false;
    public static boolean debugPrintFinalExpr;
    public static int defaultCallConvention;
    public static int defaultClassFileVersion = ClassType.JDK_1_5_VERSION;
    public static boolean emitSourceDebugExtAttr = true;
    public static final Field falseConstant = scmBooleanType.getDeclaredField("FALSE");
    public static boolean generateMainDefault = false;
    public static Method getCallContextInstanceMethod = typeCallContext.getDeclaredMethod("getInstance", 0);
    public static Method getCurrentEnvironmentMethod = typeEnvironment.addMethod("getCurrent", Type.typeArray0, (Type) typeEnvironment, 9);
    public static final Method getLocation1EnvironmentMethod = typeEnvironment.getDeclaredMethod("getLocation", 1);
    public static final Method getLocation2EnvironmentMethod = typeEnvironment.addMethod("getLocation", new Type[]{typeSymbol, Type.objectType}, (Type) typeLocation, 17);
    public static final Method getLocationMethod = typeLocation.addMethod("get", Type.typeArray0, (Type) Type.objectType, 1);
    public static final Method getProcedureBindingMethod = typeSymbol.addMethod("getProcedure", Type.typeArray0, (Type) typeProcedure, 1);
    public static final Method getSymbolProcedureMethod = typeLanguage.getDeclaredMethod("getSymbolProcedure", 1);
    public static final Method getSymbolValueMethod = typeLanguage.getDeclaredMethod("getSymbolValue", 1);
    public static boolean inlineOk = true;
    public static final Type[] int1Args = {Type.intType};
    public static ClassType javaStringType = typeString;
    static Method makeListMethod = scmListType.addMethod("makeList", new Type[]{objArrayType, Type.intType}, (Type) scmListType, 9);
    public static int moduleStatic = 0;
    public static Field noArgsField = typeValues.getDeclaredField("noArgs");
    public static final ArrayType objArrayType = ArrayType.make((Type) typeObject);
    public static Options options = new Options();
    public static Field pcCallContextField = typeCallContext.getDeclaredField("pc");
    public static Field procCallContextField = typeCallContext.getDeclaredField("proc");
    public static ClassType scmBooleanType = ClassType.make("java.lang.Boolean");
    public static ClassType scmKeywordType = ClassType.make("gnu.expr.Keyword");
    public static ClassType scmListType = ClassType.make("gnu.lists.LList");
    public static ClassType scmSequenceType = ClassType.make("gnu.lists.Sequence");
    static final Method setNameMethod = typeProcedure.getDeclaredMethod("setName", 1);
    public static final Type[] string1Arg = {javaStringType};
    public static final Type[] sym1Arg = string1Arg;
    public static final Field trueConstant = scmBooleanType.getDeclaredField("TRUE");
    public static ClassType typeApplet = ClassType.make("java.applet.Applet");
    public static ClassType typeCallContext = ClassType.make("gnu.mapping.CallContext");
    public static ClassType typeClass = Type.javalangClassType;
    public static ClassType typeClassType = ClassType.make("gnu.bytecode.ClassType");
    public static final ClassType typeConsumer = ClassType.make("gnu.lists.Consumer");
    public static ClassType typeEnvironment = ClassType.make("gnu.mapping.Environment");
    public static ClassType typeLanguage = ClassType.make("gnu.expr.Language");
    public static ClassType typeLocation = ClassType.make("gnu.mapping.Location");
    public static ClassType typeMethodProc = ClassType.make("gnu.mapping.MethodProc");
    public static ClassType typeModuleBody = ClassType.make("gnu.expr.ModuleBody");
    public static ClassType typeModuleMethod = ClassType.make("gnu.expr.ModuleMethod");
    public static ClassType typeModuleWithContext = ClassType.make("gnu.expr.ModuleWithContext");
    public static ClassType typeObject = Type.objectType;
    public static ClassType typeObjectType = ClassType.make("gnu.bytecode.ObjectType");
    public static ClassType typePair = ClassType.make("gnu.lists.Pair");
    public static ClassType typeProcedure = ClassType.make("gnu.mapping.Procedure");
    public static ClassType typeProcedure0 = ClassType.make("gnu.mapping.Procedure0");
    public static ClassType typeProcedure1 = ClassType.make("gnu.mapping.Procedure1");
    public static ClassType typeProcedure2 = ClassType.make("gnu.mapping.Procedure2");
    public static ClassType typeProcedure3 = ClassType.make("gnu.mapping.Procedure3");
    public static ClassType typeProcedure4 = ClassType.make("gnu.mapping.Procedure4");
    public static ClassType[] typeProcedureArray = {typeProcedure0, typeProcedure1, typeProcedure2, typeProcedure3, typeProcedure4};
    public static ClassType typeProcedureN = ClassType.make("gnu.mapping.ProcedureN");
    public static ClassType typeRunnable = ClassType.make("java.lang.Runnable");
    public static ClassType typeServlet = ClassType.make("gnu.kawa.servlet.KawaServlet");
    public static ClassType typeString = ClassType.make("java.lang.String");
    public static ClassType typeSymbol = ClassType.make("gnu.mapping.Symbol");
    public static ClassType typeType = ClassType.make("gnu.bytecode.Type");
    public static ClassType typeValues = ClassType.make("gnu.mapping.Values");
    public static Options.OptionInfo warnAsError = options.add("warn-as-error", 1, Boolean.FALSE, "Make all warnings into errors");
    public static Options.OptionInfo warnInvokeUnknownMethod = options.add("warn-invoke-unknown-method", 1, warnUnknownMember, "warn if invoke calls an unknown method (subsumed by warn-unknown-member)");
    public static Options.OptionInfo warnUndefinedVariable = options.add("warn-undefined-variable", 1, Boolean.TRUE, "warn if no compiler-visible binding for a variable");
    public static Options.OptionInfo warnUnknownMember = options.add("warn-unknown-member", 1, Boolean.TRUE, "warn if referencing an unknown method or field");
    Variable callContextVar;
    Variable callContextVarForInit;
    public String classPrefix = classPrefixDefault;
    ClassType[] classes;
    Initializer clinitChain;
    Method clinitMethod;
    public ClassType curClass;
    public LambdaExp curLambda;
    public Options currentOptions = new Options(options);
    protected ScopeExp current_scope;
    public boolean explicit;
    public Stack<Expression> exprStack;
    Method forNameHelper;
    SwitchState fswitch;
    Field fswitchIndex;
    public boolean generateMain = generateMainDefault;
    public boolean immediate;
    private int keyUninitialized;
    int langOptions;
    protected Language language;
    public Lexer lexer;
    public NameLookup lexical;
    LitTable litTable;
    ArrayClassLoader loader;
    int localFieldIndex;
    public ClassType mainClass;
    public ModuleExp mainLambda;
    int maxSelectorValue;
    protected SourceMessages messages;
    public Method method;
    int method_counter;
    public ModuleInfo minfo;
    public ClassType moduleClass;
    Field moduleInstanceMainField;
    Variable moduleInstanceVar;
    public boolean mustCompile = ModuleExp.alwaysCompile;
    private Compilation nextUninitialized;
    int numClasses;
    boolean pedantic;
    public Stack<Object> pendingImports;
    private int state;
    public Variable thisDecl;

    public int getState() {
        return this.state;
    }

    public void setState(int state2) {
        this.state = state2;
    }

    public boolean isPedantic() {
        return this.pedantic;
    }

    public void pushPendingImport(ModuleInfo info, ScopeExp defs, int formSize) {
        if (this.pendingImports == null) {
            this.pendingImports = new Stack<>();
        }
        this.pendingImports.push(info);
        this.pendingImports.push(defs);
        Object obj = null;
        Expression posExp = new ReferenceExp((Object) null);
        posExp.setLine(this);
        this.pendingImports.push(posExp);
        this.pendingImports.push(Integer.valueOf(formSize));
    }

    public boolean warnUndefinedVariable() {
        return this.currentOptions.getBoolean(warnUndefinedVariable);
    }

    public boolean warnUnknownMember() {
        return this.currentOptions.getBoolean(warnUnknownMember);
    }

    public boolean warnInvokeUnknownMethod() {
        return this.currentOptions.getBoolean(warnInvokeUnknownMethod);
    }

    public boolean warnAsError() {
        return this.currentOptions.getBoolean(warnAsError);
    }

    public final boolean getBooleanOption(String key, boolean defaultValue) {
        return this.currentOptions.getBoolean(key, defaultValue);
    }

    public final boolean getBooleanOption(String key) {
        return this.currentOptions.getBoolean(key);
    }

    public boolean usingCPStyle() {
        return defaultCallConvention == 4;
    }

    public boolean usingTailCalls() {
        return defaultCallConvention >= 3;
    }

    public final CodeAttr getCode() {
        return this.method.getCode();
    }

    public boolean generatingApplet() {
        return (this.langOptions & 16) != 0;
    }

    public boolean generatingServlet() {
        return (this.langOptions & 32) != 0;
    }

    public boolean sharedModuleDefs() {
        return (this.langOptions & 2) != 0;
    }

    public void setSharedModuleDefs(boolean shared) {
        if (shared) {
            this.langOptions |= 2;
        } else {
            this.langOptions &= -3;
        }
    }

    public final ClassType getModuleType() {
        return defaultCallConvention >= 2 ? typeModuleWithContext : typeModuleBody;
    }

    public void compileConstant(Object value) {
        CodeAttr code = getCode();
        if (value == null) {
            code.emitPushNull();
        } else if (!(value instanceof String) || this.immediate) {
            code.emitGetStatic(compileConstantToField(value));
        } else {
            code.emitPushString((String) value);
        }
    }

    public Field compileConstantToField(Object value) {
        Literal literal = this.litTable.findLiteral(value);
        if (literal.field == null) {
            literal.assign(this.litTable);
        }
        return literal.field;
    }

    public boolean inlineOk(Expression proc) {
        if (proc instanceof LambdaExp) {
            LambdaExp lproc = (LambdaExp) proc;
            Declaration nameDecl = lproc.nameDecl;
            if (nameDecl == null || nameDecl.getSymbol() == null || !(nameDecl.context instanceof ModuleExp)) {
                return true;
            }
            if (this.immediate && nameDecl.isPublic() && !lproc.getFlag(2048) && (this.curLambda == null || lproc.topLevel() != this.curLambda.topLevel())) {
                return false;
            }
        }
        return inlineOk;
    }

    public boolean inlineOk(Procedure proc) {
        if (!this.immediate || !(proc instanceof ModuleMethod) || !(((ModuleMethod) proc).module.getClass().getClassLoader() instanceof ArrayClassLoader)) {
            return inlineOk;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x006c A[Catch:{ ClassCastException -> 0x00c2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00a7 A[Catch:{ ClassCastException -> 0x00c2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00b2 A[Catch:{ ClassCastException -> 0x00c2 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void compileConstant(java.lang.Object r8, gnu.expr.Target r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof gnu.expr.IgnoreTarget
            if (r0 == 0) goto L_0x0005
            return
        L_0x0005:
            boolean r0 = r8 instanceof gnu.mapping.Values
            if (r0 == 0) goto L_0x0021
            r0 = r8
            gnu.mapping.Values r0 = (gnu.mapping.Values) r0
            java.lang.Object[] r0 = r0.getValues()
            int r1 = r0.length
            boolean r2 = r9 instanceof gnu.expr.ConsumerTarget
            if (r2 == 0) goto L_0x0021
            r2 = 0
        L_0x0016:
            if (r2 >= r1) goto L_0x0020
            r3 = r0[r2]
            r7.compileConstant(r3, r9)
            int r2 = r2 + 1
            goto L_0x0016
        L_0x0020:
            return
        L_0x0021:
            boolean r0 = r9 instanceof gnu.expr.ConditionalTarget
            if (r0 == 0) goto L_0x003f
            r0 = r9
            gnu.expr.ConditionalTarget r0 = (gnu.expr.ConditionalTarget) r0
            gnu.bytecode.CodeAttr r1 = r7.getCode()
            gnu.expr.Language r2 = r7.getLanguage()
            boolean r2 = r2.isTrue(r8)
            if (r2 == 0) goto L_0x0039
            gnu.bytecode.Label r2 = r0.ifTrue
            goto L_0x003b
        L_0x0039:
            gnu.bytecode.Label r2 = r0.ifFalse
        L_0x003b:
            r1.emitGoto(r2)
            return
        L_0x003f:
            boolean r0 = r9 instanceof gnu.expr.StackTarget
            if (r0 == 0) goto L_0x0115
            r0 = r9
            gnu.expr.StackTarget r0 = (gnu.expr.StackTarget) r0
            gnu.bytecode.Type r0 = r0.getType()
            boolean r1 = r0 instanceof gnu.bytecode.PrimType
            if (r1 == 0) goto L_0x00c3
            java.lang.String r1 = r0.getSignature()     // Catch:{ ClassCastException -> 0x00c2 }
            gnu.bytecode.CodeAttr r2 = r7.getCode()     // Catch:{ ClassCastException -> 0x00c2 }
            r3 = 0
            r4 = 1
            if (r1 == 0) goto L_0x0066
            int r5 = r1.length()     // Catch:{ ClassCastException -> 0x00c2 }
            if (r5 == r4) goto L_0x0061
            goto L_0x0066
        L_0x0061:
            char r5 = r1.charAt(r3)     // Catch:{ ClassCastException -> 0x00c2 }
            goto L_0x0068
        L_0x0066:
            r5 = 32
        L_0x0068:
            boolean r6 = r8 instanceof java.lang.Number     // Catch:{ ClassCastException -> 0x00c2 }
            if (r6 == 0) goto L_0x00a3
            r6 = r8
            java.lang.Number r6 = (java.lang.Number) r6     // Catch:{ ClassCastException -> 0x00c2 }
            switch(r5) {
                case 66: goto L_0x009b;
                case 68: goto L_0x0093;
                case 70: goto L_0x008b;
                case 73: goto L_0x0083;
                case 74: goto L_0x007b;
                case 83: goto L_0x0073;
                default: goto L_0x0072;
            }     // Catch:{ ClassCastException -> 0x00c2 }
        L_0x0072:
            goto L_0x00a3
        L_0x0073:
            short r3 = r6.shortValue()     // Catch:{ ClassCastException -> 0x00c2 }
            r2.emitPushInt(r3)     // Catch:{ ClassCastException -> 0x00c2 }
            return
        L_0x007b:
            long r3 = r6.longValue()     // Catch:{ ClassCastException -> 0x00c2 }
            r2.emitPushLong(r3)     // Catch:{ ClassCastException -> 0x00c2 }
            return
        L_0x0083:
            int r3 = r6.intValue()     // Catch:{ ClassCastException -> 0x00c2 }
            r2.emitPushInt(r3)     // Catch:{ ClassCastException -> 0x00c2 }
            return
        L_0x008b:
            float r3 = r6.floatValue()     // Catch:{ ClassCastException -> 0x00c2 }
            r2.emitPushFloat(r3)     // Catch:{ ClassCastException -> 0x00c2 }
            return
        L_0x0093:
            double r3 = r6.doubleValue()     // Catch:{ ClassCastException -> 0x00c2 }
            r2.emitPushDouble(r3)     // Catch:{ ClassCastException -> 0x00c2 }
            return
        L_0x009b:
            byte r3 = r6.byteValue()     // Catch:{ ClassCastException -> 0x00c2 }
            r2.emitPushInt(r3)     // Catch:{ ClassCastException -> 0x00c2 }
            return
        L_0x00a3:
            r6 = 67
            if (r5 != r6) goto L_0x00b2
            r3 = r0
            gnu.bytecode.PrimType r3 = (gnu.bytecode.PrimType) r3     // Catch:{ ClassCastException -> 0x00c2 }
            char r3 = r3.charValue(r8)     // Catch:{ ClassCastException -> 0x00c2 }
            r2.emitPushInt(r3)     // Catch:{ ClassCastException -> 0x00c2 }
            return
        L_0x00b2:
            r6 = 90
            if (r5 != r6) goto L_0x00c1
            boolean r6 = gnu.bytecode.PrimType.booleanValue(r8)     // Catch:{ ClassCastException -> 0x00c2 }
            if (r6 == 0) goto L_0x00bd
            r3 = 1
        L_0x00bd:
            r2.emitPushInt(r3)     // Catch:{ ClassCastException -> 0x00c2 }
            return
        L_0x00c1:
            goto L_0x00c3
        L_0x00c2:
            r1 = move-exception
        L_0x00c3:
            gnu.bytecode.ClassType r1 = typeClass
            if (r0 != r1) goto L_0x00d2
            boolean r1 = r8 instanceof gnu.bytecode.ClassType
            if (r1 == 0) goto L_0x00d2
            r1 = r8
            gnu.bytecode.ClassType r1 = (gnu.bytecode.ClassType) r1
            r7.loadClassRef(r1)
            return
        L_0x00d2:
            java.lang.Object r1 = r0.coerceFromObject(r8)     // Catch:{ Exception -> 0x00d8 }
            r8 = r1
            goto L_0x0115
        L_0x00d8:
            r1 = move-exception
            java.lang.StringBuffer r2 = new java.lang.StringBuffer
            r2.<init>()
            gnu.mapping.Values r3 = gnu.mapping.Values.empty
            if (r8 != r3) goto L_0x00e8
            java.lang.String r3 = "cannot convert void to "
            r2.append(r3)
            goto L_0x0105
        L_0x00e8:
            java.lang.String r3 = "cannot convert literal (of type "
            r2.append(r3)
            if (r8 != 0) goto L_0x00f5
            java.lang.String r3 = "<null>"
            r2.append(r3)
            goto L_0x0100
        L_0x00f5:
            java.lang.Class r3 = r8.getClass()
            java.lang.String r3 = r3.getName()
            r2.append(r3)
        L_0x0100:
            java.lang.String r3 = ") to "
            r2.append(r3)
        L_0x0105:
            java.lang.String r3 = r0.getName()
            r2.append(r3)
            r3 = 119(0x77, float:1.67E-43)
            java.lang.String r4 = r2.toString()
            r7.error(r3, r4)
        L_0x0115:
            r7.compileConstant(r8)
            if (r8 != 0) goto L_0x011f
            gnu.bytecode.Type r0 = r9.getType()
            goto L_0x0127
        L_0x011f:
            java.lang.Class r0 = r8.getClass()
            gnu.bytecode.Type r0 = gnu.bytecode.Type.make(r0)
        L_0x0127:
            r9.compileFromStack(r7, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.Compilation.compileConstant(java.lang.Object, gnu.expr.Target):void");
    }

    private void dumpInitializers(Initializer inits) {
        for (Initializer init = Initializer.reverse(inits); init != null; init = init.next) {
            init.emit(this);
        }
    }

    public ClassType findNamedClass(String name) {
        for (int i = 0; i < this.numClasses; i++) {
            if (name.equals(this.classes[i].getName())) {
                return this.classes[i];
            }
        }
        return null;
    }

    private static void putURLWords(String name, StringBuffer sbuf) {
        int dot = name.indexOf(46);
        if (dot > 0) {
            putURLWords(name.substring(dot + 1), sbuf);
            sbuf.append('.');
            name = name.substring(0, dot);
        }
        sbuf.append(name);
    }

    public static String mangleURI(String name) {
        int dot;
        int extLen;
        boolean hasSlash = name.indexOf(47) >= 0;
        int len = name.length();
        if (len > 6 && name.startsWith("class:")) {
            return name.substring(6);
        }
        if (len > 5 && name.charAt(4) == ':' && name.substring(0, 4).equalsIgnoreCase("http")) {
            name = name.substring(5);
            len -= 5;
            hasSlash = true;
        } else if (len > 4 && name.charAt(3) == ':' && name.substring(0, 3).equalsIgnoreCase("uri")) {
            name = name.substring(4);
            len -= 4;
        }
        int start = 0;
        StringBuffer sbuf = new StringBuffer();
        while (true) {
            int slash = name.indexOf(47, start);
            int end = slash < 0 ? len : slash;
            boolean first = sbuf.length() == 0;
            if (first && hasSlash) {
                String host = name.substring(start, end);
                if (end - start > 4 && host.startsWith("www.")) {
                    host = host.substring(4);
                }
                putURLWords(host, sbuf);
            } else if (start != end) {
                if (!first) {
                    sbuf.append('.');
                }
                if (end == len && (dot = name.lastIndexOf(46, len)) > start + 1 && !first && ((extLen = len - dot) <= 4 || (extLen == 5 && name.endsWith("html")))) {
                    len -= extLen;
                    end = len;
                    name = name.substring(0, len);
                }
                sbuf.append(name.substring(start, end));
            }
            if (slash < 0) {
                return sbuf.toString();
            }
            start = slash + 1;
        }
    }

    public static String mangleName(String name) {
        return mangleName(name, -1);
    }

    public static String mangleNameIfNeeded(String name) {
        if (name == null || isValidJavaName(name)) {
            return name;
        }
        return mangleName(name, 0);
    }

    public static boolean isValidJavaName(String name) {
        int len = name.length();
        if (len == 0 || !Character.isJavaIdentifierStart(name.charAt(0))) {
            return false;
        }
        int i = len;
        do {
            i--;
            if (i <= 0) {
                return true;
            }
        } while (Character.isJavaIdentifierPart(name.charAt(i)));
        return false;
    }

    public static String mangleName(String name, boolean reversible) {
        return mangleName(name, reversible ? 1 : -1);
    }

    public static String mangleName(String str, int i) {
        boolean z = i >= 0;
        int length = str.length();
        if (length == 6 && str.equals("*init*")) {
            return "<init>";
        }
        StringBuffer stringBuffer = new StringBuffer(length);
        int i2 = 0;
        boolean z2 = false;
        while (i2 < length) {
            char charAt = str.charAt(i2);
            if (z2) {
                charAt = Character.toTitleCase(charAt);
                z2 = false;
            }
            if (Character.isDigit(charAt)) {
                if (i2 == 0) {
                    stringBuffer.append("$N");
                }
                stringBuffer.append(charAt);
            } else if (Character.isLetter(charAt) || charAt == '_') {
                stringBuffer.append(charAt);
            } else if (charAt == '$') {
                stringBuffer.append(i > 1 ? "$$" : "$");
            } else {
                switch (charAt) {
                    case '!':
                        stringBuffer.append("$Ex");
                        break;
                    case '\"':
                        stringBuffer.append("$Dq");
                        break;
                    case '#':
                        stringBuffer.append("$Nm");
                        break;
                    case '%':
                        stringBuffer.append("$Pc");
                        break;
                    case '&':
                        stringBuffer.append("$Am");
                        break;
                    case '\'':
                        stringBuffer.append("$Sq");
                        break;
                    case '(':
                        stringBuffer.append("$LP");
                        break;
                    case ')':
                        stringBuffer.append("$RP");
                        break;
                    case '*':
                        stringBuffer.append("$St");
                        break;
                    case '+':
                        stringBuffer.append("$Pl");
                        break;
                    case ',':
                        stringBuffer.append("$Cm");
                        break;
                    case '-':
                        if (!z) {
                            int i3 = i2 + 1;
                            char charAt2 = i3 < length ? str.charAt(i3) : 0;
                            if (charAt2 != '>') {
                                if (!Character.isLowerCase(charAt2)) {
                                    stringBuffer.append("$Mn");
                                    break;
                                }
                            } else {
                                stringBuffer.append("$To$");
                                i2 = i3;
                                break;
                            }
                        } else {
                            stringBuffer.append("$Mn");
                            break;
                        }
                        break;
                    case '.':
                        stringBuffer.append("$Dt");
                        break;
                    case '/':
                        stringBuffer.append("$Sl");
                        break;
                    case ':':
                        stringBuffer.append("$Cl");
                        break;
                    case ';':
                        stringBuffer.append("$SC");
                        break;
                    case '<':
                        stringBuffer.append("$Ls");
                        break;
                    case '=':
                        stringBuffer.append("$Eq");
                        break;
                    case '>':
                        stringBuffer.append("$Gr");
                        break;
                    case '?':
                        char charAt3 = stringBuffer.length() > 0 ? stringBuffer.charAt(0) : 0;
                        if (!z && i2 + 1 == length && Character.isLowerCase(charAt3)) {
                            stringBuffer.setCharAt(0, Character.toTitleCase(charAt3));
                            stringBuffer.insert(0, "is");
                            break;
                        } else {
                            stringBuffer.append("$Qu");
                            break;
                        }
                        break;
                    case '@':
                        stringBuffer.append("$At");
                        break;
                    case '[':
                        stringBuffer.append("$LB");
                        break;
                    case ']':
                        stringBuffer.append("$RB");
                        break;
                    case '^':
                        stringBuffer.append("$Up");
                        break;
                    case '{':
                        stringBuffer.append("$LC");
                        break;
                    case '|':
                        stringBuffer.append("$VB");
                        break;
                    case '}':
                        stringBuffer.append("$RC");
                        break;
                    case '~':
                        stringBuffer.append("$Tl");
                        break;
                    default:
                        stringBuffer.append('$');
                        stringBuffer.append(Character.forDigit((charAt >> 12) & 15, 16));
                        stringBuffer.append(Character.forDigit((charAt >> 8) & 15, 16));
                        stringBuffer.append(Character.forDigit((charAt >> 4) & 15, 16));
                        stringBuffer.append(Character.forDigit(charAt & 15, 16));
                        break;
                }
                if (!z) {
                    z2 = true;
                }
            }
            i2++;
        }
        String stringBuffer2 = stringBuffer.toString();
        return stringBuffer2.equals(str) ? str : stringBuffer2;
    }

    public static char demangle2(char char1, char char2) {
        switch ((char1 << 16) | char2) {
            case 'm':
                return '&';
            case 't':
                return '@';
            case 'l':
                return ':';
            case 'm':
                return ',';
            case 'q':
                return '\"';
            case 't':
                return '.';
            case 'q':
                return '=';
            case 'x':
                return '!';
            case 'r':
                return '>';
            case 'B':
                return '[';
            case 'C':
                return '{';
            case 'P':
                return '(';
            case 's':
                return '<';
            case 'c':
                return '%';
            case 'n':
                return '-';
            case 'm':
                return '#';
            case 'c':
                return '%';
            case 'l':
                return '+';
            case 'u':
                return '?';
            case 'B':
                return ']';
            case 'C':
                return '}';
            case 'P':
                return ')';
            case 'C':
                return ';';
            case 'l':
                return '/';
            case 'q':
                return '\\';
            case 't':
                return '*';
            case 'l':
                return '~';
            case 'p':
                return '^';
            case 'B':
                return '|';
            default:
                return LispReader.TOKEN_ESCAPE_CHAR;
        }
    }

    public static String demangleName(String name) {
        return demangleName(name, false);
    }

    public static String demangleName(String name, boolean reversible) {
        StringBuffer sbuf = new StringBuffer();
        int len = name.length();
        boolean mangled = false;
        boolean predicate = false;
        boolean downCaseNext = false;
        int i = 0;
        while (i < len) {
            char ch = name.charAt(i);
            if (downCaseNext && !reversible) {
                ch = Character.toLowerCase(ch);
                downCaseNext = false;
            }
            if (!reversible && ch == 'i' && i == 0 && len > 2 && name.charAt(i + 1) == 's') {
                char charAt = name.charAt(i + 2);
                char d = charAt;
                if (!Character.isLowerCase(charAt)) {
                    mangled = true;
                    predicate = true;
                    i++;
                    if (Character.isUpperCase(d) || Character.isTitleCase(d)) {
                        sbuf.append(Character.toLowerCase(d));
                        i++;
                        i++;
                    } else {
                        i++;
                    }
                }
            }
            if (ch == '$' && i + 2 < len) {
                char c1 = name.charAt(i + 1);
                char c2 = name.charAt(i + 2);
                char d2 = demangle2(c1, c2);
                if (d2 != 65535) {
                    sbuf.append(d2);
                    i += 2;
                    mangled = true;
                    downCaseNext = true;
                } else if (c1 == 'T' && c2 == 'o' && i + 3 < len && name.charAt(i + 3) == '$') {
                    sbuf.append("->");
                    i += 3;
                    mangled = true;
                    downCaseNext = true;
                }
                i++;
            } else if (!reversible && i > 1 && ((Character.isUpperCase(ch) || Character.isTitleCase(ch)) && Character.isLowerCase(name.charAt(i - 1)))) {
                sbuf.append('-');
                mangled = true;
                ch = Character.toLowerCase(ch);
            }
            sbuf.append(ch);
            i++;
        }
        if (predicate) {
            sbuf.append('?');
        }
        return mangled ? sbuf.toString() : name;
    }

    public String generateClassName(String hint) {
        String hint2 = mangleName(hint, true);
        if (this.mainClass != null) {
            hint2 = this.mainClass.getName() + '$' + hint2;
        } else if (this.classPrefix != null) {
            hint2 = this.classPrefix + hint2;
        }
        if (findNamedClass(hint2) == null) {
            return hint2;
        }
        int i = 0;
        while (true) {
            String new_hint = hint2 + i;
            if (findNamedClass(new_hint) == null) {
                return new_hint;
            }
            i++;
        }
    }

    public Compilation(Language language2, SourceMessages messages2, NameLookup lexical2) {
        this.language = language2;
        this.messages = messages2;
        this.lexical = lexical2;
    }

    public void walkModule(ModuleExp mexp) {
        if (debugPrintExpr) {
            OutPort dout = OutPort.errDefault();
            dout.println("[Module:" + mexp.getName());
            mexp.print(dout);
            dout.println(']');
            dout.flush();
        }
        InlineCalls.inlineCalls(mexp, this);
        PushApply.pushApply(mexp);
        ChainLambdas.chainLambdas(mexp, this);
        FindTailCalls.findTailCalls(mexp, this);
    }

    public void outputClass(String directory) throws IOException {
        char dirSep = File.separatorChar;
        for (int iClass = 0; iClass < this.numClasses; iClass++) {
            ClassType clas = this.classes[iClass];
            String out_name = directory + clas.getName().replace('.', dirSep) + ".class";
            String parent = new File(out_name).getParent();
            if (parent != null) {
                new File(parent).mkdirs();
            }
            clas.writeToFile(out_name);
        }
        this.minfo.cleanupAfterCompilation();
    }

    public void cleanupAfterCompilation() {
        for (int iClass = 0; iClass < this.numClasses; iClass++) {
            this.classes[iClass].cleanupAfterCompilation();
        }
        this.classes = null;
        this.minfo.comp = null;
        if (this.minfo.exp != null) {
            this.minfo.exp.body = null;
        }
        this.mainLambda.body = null;
        this.mainLambda = null;
        if (!this.immediate) {
            this.litTable = null;
        }
    }

    public void compileToArchive(ModuleExp mexp, String fname) throws IOException {
        boolean makeJar;
        ZipOutputStream zout;
        if (fname.endsWith(".zip")) {
            makeJar = false;
        } else if (fname.endsWith(".jar")) {
            makeJar = true;
        } else {
            fname = fname + ".zip";
            makeJar = false;
        }
        process(12);
        File zar_file = new File(fname);
        if (zar_file.exists()) {
            zar_file.delete();
        }
        if (makeJar) {
            zout = new JarOutputStream(new FileOutputStream(zar_file));
        } else {
            zout = new ZipOutputStream(new FileOutputStream(zar_file));
        }
        byte[][] classBytes = new byte[this.numClasses][];
        CRC32 zcrc = new CRC32();
        for (int iClass = 0; iClass < this.numClasses; iClass++) {
            ClassType clas = this.classes[iClass];
            classBytes[iClass] = clas.writeToArray();
            ZipEntry zent = new ZipEntry(clas.getName().replace('.', '/') + ".class");
            zent.setSize((long) classBytes[iClass].length);
            zcrc.reset();
            zcrc.update(classBytes[iClass], 0, classBytes[iClass].length);
            zent.setCrc(zcrc.getValue());
            zout.putNextEntry(zent);
            zout.write(classBytes[iClass]);
        }
        zout.close();
    }

    private void registerClass(ClassType new_class) {
        if (this.classes == null) {
            this.classes = new ClassType[20];
        } else if (this.numClasses >= this.classes.length) {
            ClassType[] new_classes = new ClassType[(this.classes.length * 2)];
            System.arraycopy(this.classes, 0, new_classes, 0, this.numClasses);
            this.classes = new_classes;
        }
        new_class.addModifiers(new_class.isInterface() ? 1 : 33);
        if (new_class == this.mainClass && this.numClasses > 0) {
            new_class = this.classes[0];
            this.classes[0] = this.mainClass;
        }
        ClassType[] classTypeArr = this.classes;
        int i = this.numClasses;
        this.numClasses = i + 1;
        classTypeArr[i] = new_class;
    }

    public void addClass(ClassType new_class) {
        if (this.mainLambda.filename != null) {
            if (emitSourceDebugExtAttr) {
                new_class.setStratum(getLanguage().getName());
            }
            new_class.setSourceFile(this.mainLambda.filename);
        }
        registerClass(new_class);
        new_class.setClassfileVersion(defaultClassFileVersion);
    }

    public boolean makeRunnable() {
        return !generatingServlet() && !generatingApplet() && !getModule().staticInitRun();
    }

    public void addMainClass(ModuleExp module) {
        this.mainClass = module.classFor(this);
        ClassType type = this.mainClass;
        ClassType[] interfaces = module.getInterfaces();
        if (interfaces != null) {
            type.setInterfaces(interfaces);
        }
        ClassType sup = module.getSuperType();
        if (sup == null) {
            if (generatingApplet()) {
                sup = typeApplet;
            } else if (generatingServlet()) {
                sup = typeServlet;
            } else {
                sup = getModuleType();
            }
        }
        if (makeRunnable()) {
            type.addInterface(typeRunnable);
        }
        type.setSuper(sup);
        module.type = type;
        addClass(type);
        getConstructor(this.mainClass, module);
    }

    public final Method getConstructor(LambdaExp lexp) {
        return getConstructor(lexp.getHeapFrameType(), lexp);
    }

    public static final Method getConstructor(ClassType clas, LambdaExp lexp) {
        Method meth = clas.getDeclaredMethod("<init>", 0);
        if (meth != null) {
            return meth;
        }
        return clas.addMethod("<init>", 1, (!(lexp instanceof ClassExp) || lexp.staticLinkField == null) ? apply0args : new Type[]{lexp.staticLinkField.getType()}, (Type) Type.voidType);
    }

    public final void generateConstructor(LambdaExp lexp) {
        generateConstructor(lexp.getHeapFrameType(), lexp);
    }

    public final void generateConstructor(ClassType clas, LambdaExp lexp) {
        Method save_method = this.method;
        Variable callContextSave = this.callContextVar;
        this.callContextVar = null;
        ClassType save_class = this.curClass;
        this.curClass = clas;
        Method constructor_method = getConstructor(clas, lexp);
        clas.constructor = constructor_method;
        this.method = constructor_method;
        CodeAttr code = constructor_method.startCode();
        if ((lexp instanceof ClassExp) && lexp.staticLinkField != null) {
            code.emitPushThis();
            code.emitLoad(code.getCurrentScope().getVariable(1));
            code.emitPutField(lexp.staticLinkField);
        }
        ClassExp.invokeDefaultSuperConstructor(clas.getSuperclass(), this, lexp);
        if (!(this.curClass != this.mainClass || this.minfo == null || this.minfo.sourcePath == null)) {
            code.emitPushThis();
            code.emitInvokeStatic(ClassType.make("gnu.expr.ModuleInfo").getDeclaredMethod("register", 1));
        }
        if (!(lexp == null || lexp.initChain == null)) {
            LambdaExp save = this.curLambda;
            this.curLambda = new LambdaExp();
            this.curLambda.closureEnv = code.getArg(0);
            this.curLambda.outer = save;
            while (true) {
                Initializer initializer = lexp.initChain;
                Initializer init = initializer;
                if (initializer == null) {
                    break;
                }
                lexp.initChain = null;
                dumpInitializers(init);
            }
            this.curLambda = save;
        }
        if (lexp instanceof ClassExp) {
            callInitMethods(((ClassExp) lexp).getCompiledClassType(this), new Vector(10));
        }
        code.emitReturn();
        this.method = save_method;
        this.curClass = save_class;
        this.callContextVar = callContextSave;
    }

    /* access modifiers changed from: package-private */
    public void callInitMethods(ClassType clas, Vector<ClassType> seen) {
        if (clas != null) {
            String name = clas.getName();
            if (!"java.lang.Object".equals(name)) {
                int i = seen.size();
                do {
                    i--;
                    if (i < 0) {
                        seen.addElement(clas);
                        ClassType[] interfaces = clas.getInterfaces();
                        if (interfaces != null) {
                            for (ClassType callInitMethods : interfaces) {
                                callInitMethods(callInitMethods, seen);
                            }
                        }
                        int clEnvArgs = 1;
                        if (!clas.isInterface()) {
                            clEnvArgs = 0;
                        } else if (clas instanceof PairClassType) {
                            clas = ((PairClassType) clas).instanceType;
                        } else {
                            try {
                                clas = (ClassType) Type.make(Class.forName(clas.getName() + "$class"));
                            } catch (Throwable th) {
                                return;
                            }
                        }
                        Method meth = clas.getDeclaredMethod("$finit$", clEnvArgs);
                        if (meth != null) {
                            CodeAttr code = getCode();
                            code.emitPushThis();
                            code.emitInvoke(meth);
                            return;
                        }
                        return;
                    }
                } while (seen.elementAt(i).getName() != name);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0112  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x011c  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x011e  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0126  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01f0  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x021a  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0238  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0241  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void generateMatchMethods(gnu.expr.LambdaExp r31) {
        /*
            r30 = this;
            r0 = r30
            r1 = r31
            java.util.Vector r2 = r1.applyMethods
            if (r2 != 0) goto L_0x000a
            r2 = 0
            goto L_0x0010
        L_0x000a:
            java.util.Vector r2 = r1.applyMethods
            int r2 = r2.size()
        L_0x0010:
            if (r2 != 0) goto L_0x0013
            return
        L_0x0013:
            gnu.bytecode.Method r4 = r0.method
            gnu.bytecode.ClassType r5 = r0.curClass
            gnu.bytecode.ClassType r6 = typeModuleMethod
            gnu.bytecode.ClassType r7 = r31.getHeapFrameType()
            r0.curClass = r7
            gnu.bytecode.ClassType r7 = r0.curClass
            gnu.bytecode.ClassType r7 = r7.getSuperclass()
            gnu.bytecode.ClassType r8 = typeModuleBody
            boolean r7 = r7.isSubtype(r8)
            if (r7 != 0) goto L_0x0031
            gnu.bytecode.ClassType r7 = r0.moduleClass
            r0.curClass = r7
        L_0x0031:
            r7 = 0
            r8 = 0
        L_0x0033:
            r9 = 5
            if (r8 > r9) goto L_0x029d
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = r2
        L_0x003b:
            int r14 = r14 + -1
            if (r14 < 0) goto L_0x0261
            java.util.Vector r15 = r1.applyMethods
            java.lang.Object r15 = r15.elementAt(r14)
            gnu.expr.LambdaExp r15 = (gnu.expr.LambdaExp) r15
            gnu.bytecode.Method[] r3 = r15.primMethods
            int r9 = r3.length
            int r1 = r15.max_args
            if (r1 < 0) goto L_0x005a
            int r1 = r15.max_args
            r18 = r2
            int r2 = r15.min_args
            int r2 = r2 + r9
            if (r1 < r2) goto L_0x0058
            goto L_0x005c
        L_0x0058:
            r1 = 0
            goto L_0x005d
        L_0x005a:
            r18 = r2
        L_0x005c:
            r1 = 1
        L_0x005d:
            r2 = 5
            if (r8 >= r2) goto L_0x0077
            int r2 = r15.min_args
            int r2 = r8 - r2
            if (r2 < 0) goto L_0x0074
            if (r2 >= r9) goto L_0x0074
            r19 = r3
            int r3 = r9 + -1
            if (r2 != r3) goto L_0x0071
            if (r1 == 0) goto L_0x0071
            goto L_0x0085
        L_0x0071:
            r9 = 1
            r1 = 0
            goto L_0x008d
        L_0x0074:
            r19 = r3
            goto L_0x0085
        L_0x0077:
            r19 = r3
            int r2 = r15.min_args
            r3 = 5
            int r2 = 5 - r2
            if (r2 <= 0) goto L_0x008b
            if (r9 > r2) goto L_0x008b
            if (r1 != 0) goto L_0x008b
        L_0x0085:
            r1 = r31
            r2 = r18
            r9 = 5
            goto L_0x003b
        L_0x008b:
            int r2 = r9 + -1
        L_0x008d:
            if (r10 != 0) goto L_0x0101
            r3 = 5
            if (r8 >= r3) goto L_0x00c0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r21 = r1
            java.lang.String r1 = "match"
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.StringBuilder r1 = r1.append(r8)
            java.lang.String r1 = r1.toString()
            int r3 = r8 + 2
            gnu.bytecode.Type[] r3 = new gnu.bytecode.Type[r3]
            r12 = r8
        L_0x00ac:
            if (r12 < 0) goto L_0x00b7
            int r13 = r12 + 1
            gnu.bytecode.ClassType r22 = typeObject
            r3[r13] = r22
            int r12 = r12 + -1
            goto L_0x00ac
        L_0x00b7:
            int r12 = r8 + 1
            gnu.bytecode.ClassType r13 = typeCallContext
            r3[r12] = r13
            r12 = r3
            r3 = 1
            goto L_0x00d2
        L_0x00c0:
            r21 = r1
            java.lang.String r1 = "matchN"
            r3 = 3
            gnu.bytecode.Type[] r12 = new gnu.bytecode.Type[r3]
            gnu.bytecode.ArrayType r13 = objArrayType
            r3 = 1
            r12[r3] = r13
            gnu.bytecode.ClassType r13 = typeCallContext
            r16 = 2
            r12[r16] = r13
        L_0x00d2:
            r13 = 0
            r12[r13] = r6
            gnu.bytecode.ClassType r13 = r0.curClass
            r22 = r9
            gnu.bytecode.PrimType r9 = gnu.bytecode.Type.intType
            gnu.bytecode.Method r9 = r13.addMethod((java.lang.String) r1, (gnu.bytecode.Type[]) r12, (gnu.bytecode.Type) r9, (int) r3)
            r0.method = r9
            gnu.bytecode.Method r9 = r0.method
            gnu.bytecode.CodeAttr r7 = r9.startCode()
            gnu.bytecode.Variable r9 = r7.getArg(r3)
            r7.emitLoad(r9)
            java.lang.String r3 = "selector"
            gnu.bytecode.Field r3 = r6.getField(r3)
            r7.emitGetField(r3)
            gnu.bytecode.SwitchState r3 = r7.startSwitch()
            r9 = 1
            r11 = r3
            r10 = r9
            r13 = r12
            r12 = r1
            goto L_0x0105
        L_0x0101:
            r21 = r1
            r22 = r9
        L_0x0105:
            int r1 = r15.getSelectorValue(r0)
            r11.addCase(r1, r7)
            int r1 = r15.getLineNumber()
            if (r1 <= 0) goto L_0x0119
            java.lang.String r3 = r15.getFileName()
            r7.putLineNumber(r3, r1)
        L_0x0119:
            r3 = 5
            if (r8 != r3) goto L_0x011e
            r9 = 3
            goto L_0x0120
        L_0x011e:
            int r9 = r8 + 2
        L_0x0120:
            gnu.bytecode.Variable r9 = r7.getArg(r9)
            if (r8 >= r3) goto L_0x01f0
            gnu.expr.Declaration r17 = r15.firstDecl()
            r20 = 1
            r3 = r20
        L_0x012e:
            if (r3 > r8) goto L_0x01e3
            r7.emitLoad(r9)
            r23 = r1
            int r1 = r3 + 1
            gnu.bytecode.Variable r1 = r7.getArg(r1)
            r7.emitLoad(r1)
            gnu.bytecode.Type r1 = r17.getType()
            r24 = r6
            gnu.bytecode.ClassType r6 = gnu.bytecode.Type.objectType
            if (r1 == r6) goto L_0x01ab
            boolean r6 = r1 instanceof gnu.expr.TypeValue
            r25 = -786432(0xfffffffffff40000, float:NaN)
            if (r6 == 0) goto L_0x0182
            gnu.bytecode.Label r6 = new gnu.bytecode.Label
            r6.<init>((gnu.bytecode.CodeAttr) r7)
            r26 = r10
            gnu.bytecode.Label r10 = new gnu.bytecode.Label
            r10.<init>((gnu.bytecode.CodeAttr) r7)
            r27 = r11
            gnu.expr.ConditionalTarget r11 = new gnu.expr.ConditionalTarget
            r28 = r12
            gnu.expr.Language r12 = r30.getLanguage()
            r11.<init>(r6, r10, r12)
            r7.emitDup()
            r12 = r1
            gnu.expr.TypeValue r12 = (gnu.expr.TypeValue) r12
            r29 = r13
            r13 = 0
            r12.emitIsInstance(r13, r0, r11)
            r10.define(r7)
            r12 = r3 | r25
            r7.emitPushInt(r12)
            r7.emitReturn()
            r6.define(r7)
            goto L_0x01b3
        L_0x0182:
            r26 = r10
            r27 = r11
            r28 = r12
            r29 = r13
            boolean r6 = r1 instanceof gnu.bytecode.ClassType
            if (r6 == 0) goto L_0x01b3
            gnu.bytecode.ClassType r6 = gnu.bytecode.Type.objectType
            if (r1 == r6) goto L_0x01b3
            gnu.bytecode.ClassType r6 = gnu.bytecode.Type.toStringType
            if (r1 == r6) goto L_0x01b3
            r7.emitDup()
            r1.emitIsInstance(r7)
            r7.emitIfIntEqZero()
            r6 = r3 | r25
            r7.emitPushInt(r6)
            r7.emitReturn()
            r7.emitFi()
            goto L_0x01b3
        L_0x01ab:
            r26 = r10
            r27 = r11
            r28 = r12
            r29 = r13
        L_0x01b3:
            gnu.bytecode.ClassType r6 = typeCallContext
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "value"
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r3)
            java.lang.String r10 = r10.toString()
            gnu.bytecode.Field r6 = r6.getField(r10)
            r7.emitPutField(r6)
            gnu.expr.Declaration r17 = r17.nextDecl()
            int r3 = r3 + 1
            r1 = r23
            r6 = r24
            r10 = r26
            r11 = r27
            r12 = r28
            r13 = r29
            goto L_0x012e
        L_0x01e3:
            r23 = r1
            r24 = r6
            r26 = r10
            r27 = r11
            r28 = r12
            r29 = r13
            goto L_0x0212
        L_0x01f0:
            r23 = r1
            r24 = r6
            r26 = r10
            r27 = r11
            r28 = r12
            r29 = r13
            r7.emitLoad(r9)
            r1 = 2
            gnu.bytecode.Variable r3 = r7.getArg(r1)
            r7.emitLoad(r3)
            gnu.bytecode.ClassType r1 = typeCallContext
            java.lang.String r3 = "values"
            gnu.bytecode.Field r1 = r1.getField(r3)
            r7.emitPutField(r1)
        L_0x0212:
            r7.emitLoad(r9)
            int r1 = defaultCallConvention
            r3 = 2
            if (r1 >= r3) goto L_0x0223
            r1 = 1
            gnu.bytecode.Variable r1 = r7.getArg(r1)
            r7.emitLoad(r1)
            goto L_0x022b
        L_0x0223:
            r1 = 0
            gnu.bytecode.Variable r3 = r7.getArg(r1)
            r7.emitLoad(r3)
        L_0x022b:
            gnu.bytecode.Field r1 = procCallContextField
            r7.emitPutField(r1)
            r7.emitLoad(r9)
            int r1 = defaultCallConvention
            r3 = 2
            if (r1 < r3) goto L_0x0241
            int r1 = r15.getSelectorValue(r0)
            int r1 = r1 + r2
            r7.emitPushInt(r1)
            goto L_0x0244
        L_0x0241:
            r7.emitPushInt(r8)
        L_0x0244:
            gnu.bytecode.Field r1 = pcCallContextField
            r7.emitPutField(r1)
            r1 = 0
            r7.emitPushInt(r1)
            r7.emitReturn()
            r1 = r31
            r2 = r18
            r6 = r24
            r10 = r26
            r11 = r27
            r12 = r28
            r13 = r29
            r9 = 5
            goto L_0x003b
        L_0x0261:
            r18 = r2
            r24 = r6
            r1 = 0
            r3 = 2
            if (r10 == 0) goto L_0x0293
            r11.addDefault(r7)
            r2 = 4
            if (r8 <= r2) goto L_0x0271
            r15 = 2
            goto L_0x0273
        L_0x0271:
            int r15 = r8 + 1
        L_0x0273:
            r2 = r15
            r3 = 1
            int r2 = r2 + r3
            r3 = 0
        L_0x0277:
            if (r3 > r2) goto L_0x0283
            gnu.bytecode.Variable r6 = r7.getArg(r3)
            r7.emitLoad(r6)
            int r3 = r3 + 1
            goto L_0x0277
        L_0x0283:
            gnu.bytecode.ClassType r3 = typeModuleBody
            int r6 = r13.length
            gnu.bytecode.Method r3 = r3.getDeclaredMethod((java.lang.String) r12, (int) r6)
            r7.emitInvokeSpecial(r3)
            r7.emitReturn()
            r11.finish(r7)
        L_0x0293:
            int r8 = r8 + 1
            r1 = r31
            r2 = r18
            r6 = r24
            goto L_0x0033
        L_0x029d:
            r0.method = r4
            r0.curClass = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.Compilation.generateMatchMethods(gnu.expr.LambdaExp):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0083, code lost:
        if (r2.max_args >= (r2.min_args + r0)) goto L_0x0088;
     */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x029b A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x01da A[EDGE_INSN: B:108:0x01da->B:78:0x01da ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x01cc A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x028d A[EDGE_INSN: B:111:0x028d->B:96:0x028d ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00a4  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00ae  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00ca  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00f7  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0108  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x010a  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x010f  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0111  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0117  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x012e  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x014a A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x01bc  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x01e0  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0268  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x0289 A[LOOP:3: B:93:0x0284->B:95:0x0289, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0292  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void generateApplyMethodsWithContext(gnu.expr.LambdaExp r37) {
        /*
            r36 = this;
            r6 = r36
            r7 = r37
            java.util.Vector r0 = r7.applyMethods
            r8 = 0
            if (r0 != 0) goto L_0x000b
            r0 = 0
            goto L_0x0011
        L_0x000b:
            java.util.Vector r0 = r7.applyMethods
            int r0 = r0.size()
        L_0x0011:
            r9 = r0
            if (r9 != 0) goto L_0x0015
            return
        L_0x0015:
            gnu.bytecode.ClassType r10 = r6.curClass
            gnu.bytecode.ClassType r0 = r37.getHeapFrameType()
            r6.curClass = r0
            gnu.bytecode.ClassType r0 = r6.curClass
            gnu.bytecode.ClassType r0 = r0.getSuperclass()
            gnu.bytecode.ClassType r1 = typeModuleWithContext
            boolean r0 = r0.isSubtype(r1)
            if (r0 != 0) goto L_0x002f
            gnu.bytecode.ClassType r0 = r6.moduleClass
            r6.curClass = r0
        L_0x002f:
            gnu.bytecode.ClassType r11 = typeModuleMethod
            gnu.bytecode.Method r12 = r6.method
            r0 = 0
            r13 = 1
            gnu.bytecode.Type[] r1 = new gnu.bytecode.Type[r13]
            gnu.bytecode.ClassType r2 = typeCallContext
            r1[r8] = r2
            r14 = r1
            gnu.bytecode.ClassType r1 = r6.curClass
            java.lang.String r2 = "apply"
            gnu.bytecode.PrimType r3 = gnu.bytecode.Type.voidType
            gnu.bytecode.Method r1 = r1.addMethod((java.lang.String) r2, (gnu.bytecode.Type[]) r14, (gnu.bytecode.Type) r3, (int) r13)
            r6.method = r1
            gnu.bytecode.Method r1 = r6.method
            gnu.bytecode.CodeAttr r15 = r1.startCode()
            gnu.bytecode.Variable r5 = r15.getArg(r13)
            r15.emitLoad(r5)
            gnu.bytecode.Field r0 = pcCallContextField
            r15.emitGetField(r0)
            gnu.bytecode.SwitchState r4 = r15.startSwitch()
            r0 = 0
            r3 = r0
        L_0x0060:
            if (r3 >= r9) goto L_0x02d6
            java.util.Vector r0 = r7.applyMethods
            java.lang.Object r0 = r0.elementAt(r3)
            r2 = r0
            gnu.expr.LambdaExp r2 = (gnu.expr.LambdaExp) r2
            gnu.bytecode.Method[] r1 = r2.primMethods
            int r0 = r1.length
            r16 = 0
            r8 = r16
        L_0x0072:
            if (r8 >= r0) goto L_0x02ba
            int r13 = r0 + -1
            if (r8 != r13) goto L_0x008a
            int r13 = r2.max_args
            if (r13 < 0) goto L_0x0086
            int r13 = r2.max_args
            r17 = r3
            int r3 = r2.min_args
            int r3 = r3 + r0
            if (r13 < r3) goto L_0x008c
            goto L_0x0088
        L_0x0086:
            r17 = r3
        L_0x0088:
            r3 = 1
            goto L_0x008d
        L_0x008a:
            r17 = r3
        L_0x008c:
            r3 = 0
        L_0x008d:
            r13 = r3
            r18 = r8
            int r3 = r2.getSelectorValue(r6)
            int r3 = r3 + r8
            r4.addCase(r3, r15)
            gnu.text.SourceMessages r3 = r6.messages
            gnu.text.SourceLocator r3 = r3.swapSourceLocator(r2)
            int r7 = r2.getLineNumber()
            if (r7 <= 0) goto L_0x00ae
            r19 = r3
            java.lang.String r3 = r2.getFileName()
            r15.putLineNumber(r3, r7)
            goto L_0x00b0
        L_0x00ae:
            r19 = r3
        L_0x00b0:
            r3 = r1[r18]
            r20 = r7
            gnu.bytecode.Type[] r7 = r3.getParameterTypes()
            r21 = r4
            int r4 = r2.min_args
            int r4 = r4 + r18
            r22 = 0
            r23 = 0
            r24 = r9
            r9 = 4
            if (r8 <= r9) goto L_0x00f7
            r9 = 1
            if (r0 <= r9) goto L_0x00f7
            gnu.bytecode.PrimType r9 = gnu.bytecode.Type.intType
            gnu.bytecode.Variable r9 = r15.addLocal(r9)
            r15.emitLoad(r5)
            r26 = r0
            gnu.bytecode.ClassType r0 = typeCallContext
            r27 = r11
            java.lang.String r11 = "getArgCount"
            r28 = r14
            r14 = 0
            gnu.bytecode.Method r0 = r0.getDeclaredMethod((java.lang.String) r11, (int) r14)
            r15.emitInvoke(r0)
            int r0 = r2.min_args
            if (r0 == 0) goto L_0x00f3
            int r0 = r2.min_args
            r15.emitPushInt(r0)
            gnu.bytecode.PrimType r0 = gnu.bytecode.Type.intType
            r15.emitSub((gnu.bytecode.PrimType) r0)
        L_0x00f3:
            r15.emitStore(r9)
            goto L_0x00ff
        L_0x00f7:
            r26 = r0
            r27 = r11
            r28 = r14
            r9 = r22
        L_0x00ff:
            boolean r0 = r3.getStaticFlag()
            r11 = 1
            r0 = r0 ^ r11
            r11 = r0
            if (r13 == 0) goto L_0x010a
            r0 = 2
            goto L_0x010b
        L_0x010a:
            r0 = 1
        L_0x010b:
            int r0 = r0 + r4
            int r14 = r7.length
            if (r0 >= r14) goto L_0x0111
            r0 = 1
            goto L_0x0112
        L_0x0111:
            r0 = 0
        L_0x0112:
            r14 = r0
            int r0 = r11 + r14
            if (r0 <= 0) goto L_0x012e
            r15.emitPushThis()
            gnu.bytecode.ClassType r0 = r6.curClass
            r29 = r3
            gnu.bytecode.ClassType r3 = r6.moduleClass
            if (r0 != r3) goto L_0x0130
            gnu.bytecode.ClassType r0 = r6.mainClass
            gnu.bytecode.ClassType r3 = r6.moduleClass
            if (r0 == r3) goto L_0x0130
            gnu.bytecode.Field r0 = r6.moduleInstanceMainField
            r15.emitGetField(r0)
            goto L_0x0130
        L_0x012e:
            r29 = r3
        L_0x0130:
            gnu.expr.Declaration r0 = r2.firstDecl()
            if (r0 == 0) goto L_0x0140
            boolean r3 = r0.isThisParameter()
            if (r3 == 0) goto L_0x0140
            gnu.expr.Declaration r0 = r0.nextDecl()
        L_0x0140:
            r3 = 0
            r35 = r3
            r3 = r0
            r0 = r35
        L_0x0146:
            r30 = r11
            if (r0 >= r4) goto L_0x01da
            if (r9 == 0) goto L_0x016b
            int r11 = r2.min_args
            if (r0 < r11) goto L_0x016b
            r15.emitLoad(r9)
            r15.emitIfIntLEqZero()
            r15.emitLoad(r5)
            int r11 = r2.min_args
            int r11 = r0 - r11
            r11 = r1[r11]
            r15.emitInvoke(r11)
            r15.emitElse()
            int r23 = r23 + 1
            r11 = -1
            r15.emitInc(r9, r11)
        L_0x016b:
            r15.emitLoad(r5)
            r11 = 4
            if (r0 > r11) goto L_0x019d
            if (r13 != 0) goto L_0x019d
            r25 = r1
            int r1 = r2.max_args
            if (r1 > r11) goto L_0x019a
            gnu.bytecode.ClassType r1 = typeCallContext
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r32 = r5
            java.lang.String r5 = "value"
            java.lang.StringBuilder r5 = r11.append(r5)
            int r11 = r0 + 1
            java.lang.StringBuilder r5 = r5.append(r11)
            java.lang.String r5 = r5.toString()
            gnu.bytecode.Field r1 = r1.getDeclaredField(r5)
            r15.emitGetField(r1)
            goto L_0x01b4
        L_0x019a:
            r32 = r5
            goto L_0x01a1
        L_0x019d:
            r25 = r1
            r32 = r5
        L_0x01a1:
            gnu.bytecode.ClassType r1 = typeCallContext
            java.lang.String r5 = "values"
            gnu.bytecode.Field r1 = r1.getDeclaredField(r5)
            r15.emitGetField(r1)
            r15.emitPushInt(r0)
            gnu.bytecode.ClassType r1 = gnu.bytecode.Type.objectType
            r15.emitArrayLoad(r1)
        L_0x01b4:
            gnu.bytecode.Type r1 = r3.getType()
            gnu.bytecode.ClassType r5 = gnu.bytecode.Type.objectType
            if (r1 == r5) goto L_0x01cc
            gnu.text.SourceMessages r5 = r6.messages
            gnu.text.SourceLocator r5 = r5.swapSourceLocator(r3)
            int r11 = r0 + 1
            gnu.expr.CheckedTarget.emitCheckedCoerce((gnu.expr.Compilation) r6, (gnu.expr.LambdaExp) r2, (int) r11, (gnu.bytecode.Type) r1)
            gnu.text.SourceMessages r11 = r6.messages
            r11.swapSourceLocator(r5)
        L_0x01cc:
            gnu.expr.Declaration r3 = r3.nextDecl()
            int r0 = r0 + 1
            r1 = r25
            r11 = r30
            r5 = r32
            goto L_0x0146
        L_0x01da:
            r25 = r1
            r32 = r5
            if (r13 == 0) goto L_0x0268
            int r0 = r14 + r4
            r11 = r7[r0]
            boolean r0 = r11 instanceof gnu.bytecode.ArrayType
            if (r0 == 0) goto L_0x020e
            r0 = r36
            r1 = r2
            r31 = r2
            r2 = r4
            r5 = r19
            r19 = r7
            r7 = r29
            r29 = r3
            r3 = r9
            r33 = r9
            r9 = r21
            r21 = r13
            r13 = r4
            r4 = r11
            r34 = r32
            r32 = r14
            r14 = r5
            r5 = r34
            r0.varArgsToArray(r1, r2, r3, r4, r5)
            r0 = r34
            r3 = 1
            goto L_0x027e
        L_0x020e:
            r31 = r2
            r33 = r9
            r9 = r21
            r34 = r32
            r21 = r13
            r32 = r14
            r14 = r19
            r13 = r4
            r19 = r7
            r7 = r29
            r29 = r3
            java.lang.String r0 = "gnu.lists.LList"
            java.lang.String r1 = r11.getName()
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0244
            r0 = r34
            r15.emitLoad(r0)
            r15.emitPushInt(r13)
            gnu.bytecode.ClassType r1 = typeCallContext
            java.lang.String r2 = "getRestArgsList"
            r3 = 1
            gnu.bytecode.Method r1 = r1.getDeclaredMethod((java.lang.String) r2, (int) r3)
            r15.emitInvokeVirtual(r1)
            goto L_0x027e
        L_0x0244:
            r0 = r34
            r3 = 1
            gnu.bytecode.ClassType r1 = typeCallContext
            if (r11 != r1) goto L_0x024f
            r15.emitLoad(r0)
            goto L_0x027e
        L_0x024f:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "unsupported #!rest type:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r11)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0268:
            r31 = r2
            r33 = r9
            r9 = r21
            r0 = r32
            r21 = r13
            r32 = r14
            r14 = r19
            r13 = r4
            r19 = r7
            r7 = r29
            r29 = r3
            r3 = 1
        L_0x027e:
            r15.emitLoad(r0)
            r15.emitInvoke(r7)
        L_0x0284:
            r1 = -1
            int r23 = r23 + -1
            if (r23 < 0) goto L_0x028d
            r15.emitFi()
            goto L_0x0284
        L_0x028d:
            int r1 = defaultCallConvention
            r2 = 2
            if (r1 >= r2) goto L_0x029b
            gnu.expr.Target r1 = gnu.expr.Target.pushObject
            gnu.bytecode.Type r2 = r31.getReturnType()
            r1.compileFromStack(r6, r2)
        L_0x029b:
            gnu.text.SourceMessages r1 = r6.messages
            r1.swapSourceLocator(r14)
            r15.emitReturn()
            int r8 = r8 + 1
            r7 = r37
            r5 = r0
            r4 = r9
            r3 = r17
            r9 = r24
            r1 = r25
            r0 = r26
            r11 = r27
            r14 = r28
            r2 = r31
            r13 = 1
            goto L_0x0072
        L_0x02ba:
            r26 = r0
            r25 = r1
            r31 = r2
            r17 = r3
            r0 = r5
            r24 = r9
            r27 = r11
            r28 = r14
            r3 = 1
            r9 = r4
            int r1 = r17 + 1
            r7 = r37
            r3 = r1
            r9 = r24
            r8 = 0
            r13 = 1
            goto L_0x0060
        L_0x02d6:
            r17 = r3
            r24 = r9
            r9 = r4
            r9.addDefault(r15)
            gnu.bytecode.ClassType r1 = typeModuleMethod
            java.lang.String r2 = "applyError"
            r3 = 0
            gnu.bytecode.Method r1 = r1.getDeclaredMethod((java.lang.String) r2, (int) r3)
            r15.emitInvokeStatic(r1)
            r15.emitReturn()
            r9.finish(r15)
            r6.method = r12
            r6.curClass = r10
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.Compilation.generateApplyMethodsWithContext(gnu.expr.LambdaExp):void");
    }

    public void generateApplyMethodsWithoutContext(LambdaExp lexp) {
        boolean skipThisProc;
        int methodIndex;
        ClassType procType;
        Method save_method;
        ClassType save_class;
        Type[] applyArgs;
        String mname;
        SwitchState aswitch;
        boolean needThisApply;
        CodeAttr code;
        SourceLocator saveLoc1;
        Variable counter;
        Method primMethod;
        SourceLocator saveLoc12;
        Method[] primMethods;
        boolean needThisApply2;
        int k;
        Type[] applyArgs2;
        String mname2;
        boolean skipThisProc2;
        LambdaExp lambdaExp = lexp;
        int numApplyMethods = lambdaExp.applyMethods == null ? 0 : lambdaExp.applyMethods.size();
        if (numApplyMethods != 0) {
            ClassType save_class2 = this.curClass;
            this.curClass = lexp.getHeapFrameType();
            ClassType procType2 = typeModuleMethod;
            if (!this.curClass.getSuperclass().isSubtype(typeModuleBody)) {
                this.curClass = this.moduleClass;
            }
            Method save_method2 = this.method;
            CodeAttr code2 = null;
            int i = defaultCallConvention >= 2 ? 5 : 0;
            while (i < 6) {
                boolean needThisApply3 = false;
                SwitchState aswitch2 = null;
                String mname3 = null;
                Type[] applyArgs3 = null;
                int j = 0;
                while (j < numApplyMethods) {
                    LambdaExp source = (LambdaExp) lambdaExp.applyMethods.elementAt(j);
                    int j2 = j;
                    Method[] primMethods2 = source.primMethods;
                    int numMethods = primMethods2.length;
                    boolean varArgs = source.max_args < 0 || source.max_args >= source.min_args + numMethods;
                    int numApplyMethods2 = numApplyMethods;
                    if (i < 5) {
                        methodIndex = i - source.min_args;
                        if (methodIndex >= 0 && methodIndex < numMethods) {
                            if (methodIndex != numMethods - 1 || !varArgs) {
                                skipThisProc = false;
                                numMethods = 1;
                                varArgs = false;
                            }
                        }
                        skipThisProc = true;
                        numMethods = 1;
                        varArgs = false;
                    } else {
                        int methodIndex2 = 5 - source.min_args;
                        if (methodIndex2 <= 0 || numMethods > methodIndex2 || varArgs) {
                            skipThisProc2 = false;
                        } else {
                            skipThisProc2 = true;
                        }
                        methodIndex = numMethods - 1;
                    }
                    if (skipThisProc) {
                        save_class = save_class2;
                        procType = procType2;
                        save_method = save_method2;
                    } else {
                        if (!needThisApply3) {
                            boolean z = skipThisProc;
                            if (i < 5) {
                                save_class = save_class2;
                                mname2 = "apply" + i;
                                applyArgs2 = new Type[(i + 1)];
                                for (int k2 = i; k2 > 0; k2--) {
                                    applyArgs2[k2] = typeObject;
                                }
                                k = 2;
                            } else {
                                save_class = save_class2;
                                mname2 = "applyN";
                                k = 2;
                                applyArgs2 = new Type[2];
                                applyArgs2[1] = objArrayType;
                            }
                            applyArgs2[0] = procType2;
                            save_method = save_method2;
                            this.method = this.curClass.addMethod(mname2, applyArgs2, defaultCallConvention >= k ? Type.voidType : Type.objectType, 1);
                            CodeAttr code3 = this.method.startCode();
                            code3.emitLoad(code3.getArg(1));
                            code3.emitGetField(procType2.getField("selector"));
                            code = code3;
                            needThisApply = true;
                            aswitch = code3.startSwitch();
                            mname = mname2;
                            applyArgs = applyArgs2;
                        } else {
                            save_class = save_class2;
                            save_method = save_method2;
                            boolean z2 = skipThisProc;
                            code = code2;
                            needThisApply = needThisApply3;
                            aswitch = aswitch2;
                            mname = mname3;
                            applyArgs = applyArgs3;
                        }
                        aswitch.addCase(source.getSelectorValue(this), code);
                        SourceLocator saveLoc13 = this.messages.swapSourceLocator(source);
                        int line = source.getLineNumber();
                        if (line > 0) {
                            code.putLineNumber(source.getFileName(), line);
                        }
                        Method primMethod2 = primMethods2[methodIndex];
                        Type[] primArgTypes = primMethod2.getParameterTypes();
                        int singleArgs = source.min_args + methodIndex;
                        int pendingIfEnds = 0;
                        int i2 = line;
                        if (i <= 4 || numMethods <= 1) {
                            saveLoc1 = saveLoc13;
                            int i3 = methodIndex;
                            counter = null;
                        } else {
                            Variable counter2 = code.addLocal(Type.intType);
                            saveLoc1 = saveLoc13;
                            int i4 = methodIndex;
                            code.emitLoad(code.getArg(2));
                            code.emitArrayLength();
                            if (source.min_args != 0) {
                                code.emitPushInt(source.min_args);
                                code.emitSub(Type.intType);
                            }
                            code.emitStore(counter2);
                            counter = counter2;
                        }
                        int needsThis = primMethod2.getStaticFlag() ^ 1;
                        int explicitFrameArg = (varArgs ? 1 : 0) + singleArgs < primArgTypes.length ? 1 : 0;
                        if (((int) needsThis) + ((int) explicitFrameArg) > 0) {
                            code.emitPushThis();
                            if (this.curClass == this.moduleClass && this.mainClass != this.moduleClass) {
                                code.emitGetField(this.moduleInstanceMainField);
                            }
                        }
                        Declaration var = source.firstDecl();
                        if (var != null && var.isThisParameter()) {
                            var = var.nextDecl();
                        }
                        Declaration var2 = var;
                        int k3 = 0;
                        while (true) {
                            procType = procType2;
                            if (k3 >= singleArgs) {
                                break;
                            }
                            if (counter != null && k3 >= source.min_args) {
                                code.emitLoad(counter);
                                code.emitIfIntLEqZero();
                                code.emitInvoke(primMethods2[k3 - source.min_args]);
                                code.emitElse();
                                pendingIfEnds++;
                                code.emitInc(counter, -1);
                            }
                            Variable pvar = null;
                            Method primMethod3 = primMethod2;
                            if (i <= 4) {
                                pvar = code.getArg(k3 + 2);
                                code.emitLoad(pvar);
                                primMethods = primMethods2;
                            } else {
                                primMethods = primMethods2;
                                code.emitLoad(code.getArg(2));
                                code.emitPushInt(k3);
                                code.emitArrayLoad(Type.objectType);
                            }
                            Type ptype = var2.getType();
                            if (ptype != Type.objectType) {
                                SourceLocator saveLoc2 = this.messages.swapSourceLocator(var2);
                                needThisApply2 = needThisApply;
                                CheckedTarget.emitCheckedCoerce(this, source, k3 + 1, ptype, pvar);
                                this.messages.swapSourceLocator(saveLoc2);
                            } else {
                                needThisApply2 = needThisApply;
                            }
                            var2 = var2.nextDecl();
                            k3++;
                            procType2 = procType;
                            primMethod2 = primMethod3;
                            primMethods2 = primMethods;
                            needThisApply = needThisApply2;
                        }
                        Method primMethod4 = primMethod2;
                        Method[] methodArr = primMethods2;
                        boolean needThisApply4 = needThisApply;
                        if (varArgs) {
                            Type lastArgType = primArgTypes[explicitFrameArg + singleArgs];
                            if (lastArgType instanceof ArrayType) {
                                Type[] typeArr = primArgTypes;
                                primMethod = primMethod4;
                                boolean z3 = varArgs;
                                saveLoc12 = saveLoc1;
                                SourceLocator saveLoc14 = var2;
                                varArgsToArray(source, singleArgs, counter, lastArgType, (Variable) null);
                            } else {
                                int singleArgs2 = singleArgs;
                                Type[] typeArr2 = primArgTypes;
                                primMethod = primMethod4;
                                boolean z4 = varArgs;
                                saveLoc12 = saveLoc1;
                                SourceLocator saveLoc15 = var2;
                                if ("gnu.lists.LList".equals(lastArgType.getName())) {
                                    code.emitLoad(code.getArg(2));
                                    code.emitPushInt(singleArgs2);
                                    code.emitInvokeStatic(makeListMethod);
                                } else if (lastArgType == typeCallContext) {
                                    code.emitLoad(code.getArg(2));
                                } else {
                                    throw new RuntimeException("unsupported #!rest type:" + lastArgType);
                                }
                            }
                        } else {
                            Type[] typeArr3 = primArgTypes;
                            primMethod = primMethod4;
                            boolean z5 = varArgs;
                            saveLoc12 = saveLoc1;
                            SourceLocator saveLoc16 = var2;
                        }
                        code.emitInvoke(primMethod);
                        while (true) {
                            pendingIfEnds--;
                            if (pendingIfEnds < 0) {
                                break;
                            }
                            code.emitFi();
                        }
                        if (defaultCallConvention < 2) {
                            Target.pushObject.compileFromStack(this, source.getReturnType());
                        }
                        this.messages.swapSourceLocator(saveLoc12);
                        code.emitReturn();
                        code2 = code;
                        aswitch2 = aswitch;
                        mname3 = mname;
                        applyArgs3 = applyArgs;
                        needThisApply3 = needThisApply4;
                    }
                    j = j2 + 1;
                    lambdaExp = lexp;
                    numApplyMethods = numApplyMethods2;
                    save_class2 = save_class;
                    save_method2 = save_method;
                    procType2 = procType;
                }
                int i5 = j;
                int numApplyMethods3 = numApplyMethods;
                ClassType save_class3 = save_class2;
                ClassType procType3 = procType2;
                Method save_method3 = save_method2;
                if (needThisApply3) {
                    aswitch2.addDefault(code2);
                    if (defaultCallConvention >= 2) {
                        code2.emitInvokeStatic(typeModuleMethod.getDeclaredMethod("applyError", 0));
                    } else {
                        int nargs = (i > 4 ? 2 : i + 1) + 1;
                        for (int k4 = 0; k4 < nargs; k4++) {
                            code2.emitLoad(code2.getArg(k4));
                        }
                        code2.emitInvokeSpecial(typeModuleBody.getDeclaredMethod(mname3, applyArgs3));
                    }
                    code2.emitReturn();
                    aswitch2.finish(code2);
                }
                i++;
                lambdaExp = lexp;
                numApplyMethods = numApplyMethods3;
                save_class2 = save_class3;
                save_method2 = save_method3;
                procType2 = procType3;
            }
            this.method = save_method2;
            this.curClass = save_class2;
        }
    }

    private void varArgsToArray(LambdaExp source, int singleArgs, Variable counter, Type lastArgType, Variable ctxVar) {
        Variable counter2;
        int i = singleArgs;
        Variable variable = ctxVar;
        CodeAttr code = getCode();
        Type elType = ((ArrayType) lastArgType).getComponentType();
        boolean mustConvert = !"java.lang.Object".equals(elType.getName());
        if (variable != null && !mustConvert) {
            code.emitLoad(variable);
            code.emitPushInt(i);
            code.emitInvokeVirtual(typeCallContext.getDeclaredMethod("getRestArgsArray", 1));
        } else if (i != 0 || mustConvert) {
            code.pushScope();
            if (counter == null) {
                Variable counter3 = code.addLocal(Type.intType);
                if (variable != null) {
                    code.emitLoad(variable);
                    code.emitInvoke(typeCallContext.getDeclaredMethod("getArgCount", 0));
                } else {
                    code.emitLoad(code.getArg(2));
                    code.emitArrayLength();
                }
                if (i != 0) {
                    code.emitPushInt(i);
                    code.emitSub(Type.intType);
                }
                code.emitStore(counter3);
                counter2 = counter3;
            } else {
                counter2 = counter;
            }
            code.emitLoad(counter2);
            code.emitNewArray(elType.getImplementationType());
            Label testLabel = new Label(code);
            Label loopTopLabel = new Label(code);
            loopTopLabel.setTypes(code);
            code.emitGoto(testLabel);
            loopTopLabel.define(code);
            code.emitDup(1);
            code.emitLoad(counter2);
            if (variable != null) {
                code.emitLoad(variable);
            } else {
                code.emitLoad(code.getArg(2));
            }
            code.emitLoad(counter2);
            if (i != 0) {
                code.emitPushInt(i);
                code.emitAdd(Type.intType);
            }
            if (variable != null) {
                code.emitInvokeVirtual(typeCallContext.getDeclaredMethod("getArgAsObject", 1));
            } else {
                code.emitArrayLoad(Type.objectType);
            }
            if (mustConvert) {
                CheckedTarget.emitCheckedCoerce(this, source, source.getName(), 0, elType, (Variable) null);
            }
            code.emitArrayStore(elType);
            testLabel.define(code);
            code.emitInc(counter2, -1);
            code.emitLoad(counter2);
            code.emitGotoIfIntGeZero(loopTopLabel);
            code.popScope();
            return;
        } else {
            code.emitLoad(code.getArg(2));
        }
        Variable variable2 = counter;
    }

    private Method startClassInit() {
        Method registerMethod;
        this.method = this.curClass.addMethod("<clinit>", apply0args, (Type) Type.voidType, 9);
        CodeAttr code = this.method.startCode();
        if ((this.generateMain || generatingApplet() || generatingServlet()) && (registerMethod = ((ClassType) Type.make(getLanguage().getClass())).getDeclaredMethod("registerEnvironment", 0)) != null) {
            code.emitInvokeStatic(registerMethod);
        }
        return this.method;
    }

    public void process(int wantedState) {
        Compilation saveCompilation = setSaveCurrent(this);
        try {
            ModuleExp mexp = getModule();
            int i = 4;
            if (wantedState >= 4 && getState() < 3) {
                setState(3);
                this.language.parse(this, 0);
                this.lexer.close();
                this.lexer = null;
                if (this.messages.seenErrors()) {
                    i = 100;
                }
                setState(i);
                if (this.pendingImports != null) {
                    restoreCurrent(saveCompilation);
                    return;
                }
            }
            int i2 = 6;
            if (wantedState >= 6) {
                if (getState() < 6) {
                    addMainClass(mexp);
                    this.language.resolve(this);
                    if (this.messages.seenErrors()) {
                        i2 = 100;
                    }
                    setState(i2);
                }
            }
            if (!this.explicit && !this.immediate && this.minfo.checkCurrent(ModuleManager.getInstance(), System.currentTimeMillis())) {
                this.minfo.cleanupAfterCompilation();
                setState(14);
            }
            int i3 = 8;
            if (wantedState >= 8 && getState() < 8) {
                walkModule(mexp);
                if (this.messages.seenErrors()) {
                    i3 = 100;
                }
                setState(i3);
            }
            int i4 = 10;
            if (wantedState >= 10 && getState() < 10) {
                this.litTable = new LitTable(this);
                mexp.setCanRead(true);
                FindCapturedVars.findCapturedVars(mexp, this);
                mexp.allocFields(this);
                mexp.allocChildMethods(this);
                if (this.messages.seenErrors()) {
                    i4 = 100;
                }
                setState(i4);
            }
            int i5 = 12;
            if (wantedState >= 12 && getState() < 12) {
                if (this.immediate) {
                    this.loader = new ArrayClassLoader(ObjectType.getContextClassLoader());
                }
                generateBytecode();
                if (this.messages.seenErrors()) {
                    i5 = 100;
                }
                setState(i5);
            }
            if (wantedState >= 14 && getState() < 14) {
                outputClass(ModuleManager.getInstance().getCompilationDirectory());
                setState(14);
            }
        } catch (SyntaxException ex) {
            setState(100);
            if (ex.getMessages() != getMessages()) {
                throw new RuntimeException("confussing syntax error: " + ex);
            }
        } catch (IOException ex2) {
            ex2.printStackTrace();
            error('f', "caught " + ex2);
            setState(100);
        } catch (Throwable th) {
            restoreCurrent(saveCompilation);
            throw th;
        }
        restoreCurrent(saveCompilation);
    }

    /* access modifiers changed from: package-private */
    public void generateBytecode() {
        Type[] arg_types;
        int arg_count;
        Method initMethod;
        String mainPrefix;
        Variable heapFrame;
        ClassType typeModuleSet;
        String uri;
        ModuleManager manager;
        Initializer init;
        ModuleExp module = getModule();
        if (debugPrintFinalExpr) {
            OutPort dout = OutPort.errDefault();
            dout.println("[Compiling final " + module.getName() + " to " + this.mainClass.getName() + ":");
            module.print(dout);
            dout.println(']');
            dout.flush();
        }
        ClassType neededSuper = getModuleType();
        if (this.mainClass.getSuperclass().isSubtype(neededSuper)) {
            this.moduleClass = this.mainClass;
        } else {
            this.moduleClass = new ClassType(generateClassName("frame"));
            this.moduleClass.setSuper(neededSuper);
            addClass(this.moduleClass);
            generateConstructor(this.moduleClass, (LambdaExp) null);
        }
        this.curClass = module.type;
        LambdaExp saveLambda = this.curLambda;
        this.curLambda = module;
        if (module.isHandlingTailCalls()) {
            arg_types = new Type[]{typeCallContext};
            arg_count = 1;
        } else if (module.min_args != module.max_args || module.min_args > 4) {
            arg_types = new Type[]{new ArrayType(typeObject)};
            arg_count = 1;
        } else {
            int arg_count2 = module.min_args;
            Type[] arg_types2 = new Type[arg_count2];
            int i = arg_count2;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                arg_types2[i] = typeObject;
            }
            arg_types = arg_types2;
            arg_count = arg_count2;
        }
        Variable heapFrame2 = module.heapFrame;
        boolean staticModule = module.isStatic();
        this.method = this.curClass.addMethod("run", arg_types, (Type) Type.voidType, 17);
        this.method.initCode();
        CodeAttr code = getCode();
        this.thisDecl = this.method.getStaticFlag() ? null : module.declareThis(module.type);
        module.closureEnv = module.thisVariable;
        module.heapFrame = module.isStatic() ? null : module.thisVariable;
        module.allocChildClasses(this);
        if (module.isHandlingTailCalls() || usingCPStyle()) {
            this.callContextVar = new Variable("$ctx", typeCallContext);
            module.getVarScope().addVariableAfter(this.thisDecl, this.callContextVar);
            this.callContextVar.setParameter(true);
        }
        int line = module.getLineNumber();
        if (line > 0) {
            code.putLineNumber(module.getFileName(), line);
        }
        module.allocParameters(this);
        module.enterFunction(this);
        if (usingCPStyle()) {
            loadCallContext();
            code.emitGetField(pcCallContextField);
            this.fswitch = code.startSwitch();
            this.fswitch.addCase(0, code);
        }
        module.compileBody(this);
        module.compileEnd(this);
        Label startLiterals = null;
        Label afterLiterals = null;
        ClassType classType = neededSuper;
        if (this.curClass == this.mainClass) {
            Method save_method = this.method;
            Variable callContextSave = this.callContextVar;
            this.callContextVar = null;
            Method initMethod2 = startClassInit();
            this.clinitMethod = initMethod2;
            code = getCode();
            Method initMethod3 = initMethod2;
            startLiterals = new Label(code);
            afterLiterals = new Label(code);
            code.fixupChain(afterLiterals, startLiterals);
            if (staticModule) {
                generateConstructor(module);
                code.emitNew(this.moduleClass);
                code.emitDup((Type) this.moduleClass);
                code.emitInvokeSpecial(this.moduleClass.constructor);
                int i2 = line;
                int i3 = arg_count;
                Type[] typeArr = arg_types;
                this.moduleInstanceMainField = this.moduleClass.addField("$instance", this.moduleClass, 25);
                code.emitPutStatic(this.moduleInstanceMainField);
            } else {
                int i4 = arg_count;
                Type[] typeArr2 = arg_types;
            }
            while (true) {
                Initializer initializer = this.clinitChain;
                init = initializer;
                if (initializer == null) {
                    break;
                }
                this.clinitChain = null;
                dumpInitializers(init);
            }
            if (module.staticInitRun()) {
                code.emitGetStatic(this.moduleInstanceMainField);
                code.emitInvoke(typeModuleBody.getDeclaredMethod("run", 0));
            }
            code.emitReturn();
            if (this.moduleClass == this.mainClass || staticModule || this.generateMain || this.immediate) {
                boolean z = staticModule;
            } else {
                Initializer initializer2 = init;
                this.method = this.curClass.addMethod("run", 1, Type.typeArray0, (Type) Type.voidType);
                code = this.method.startCode();
                Variable ctxVar = code.addLocal(typeCallContext);
                Variable saveVar = code.addLocal(typeConsumer);
                Variable exceptionVar = code.addLocal(Type.javalangThrowableType);
                code.emitInvokeStatic(getCallContextInstanceMethod);
                code.emitStore(ctxVar);
                Field consumerFld = typeCallContext.getDeclaredField("consumer");
                code.emitLoad(ctxVar);
                code.emitGetField(consumerFld);
                code.emitStore(saveVar);
                code.emitLoad(ctxVar);
                boolean z2 = staticModule;
                code.emitGetStatic(ClassType.make("gnu.lists.VoidConsumer").getDeclaredField("instance"));
                code.emitPutField(consumerFld);
                code.emitTryStart(false, Type.voidType);
                code.emitPushThis();
                code.emitLoad(ctxVar);
                code.emitInvokeVirtual(save_method);
                code.emitPushNull();
                code.emitStore(exceptionVar);
                code.emitTryEnd();
                code.emitCatchStart(exceptionVar);
                code.emitCatchEnd();
                code.emitTryCatchEnd();
                code.emitLoad(ctxVar);
                code.emitLoad(exceptionVar);
                code.emitLoad(saveVar);
                Variable variable = ctxVar;
                code.emitInvokeStatic(typeModuleBody.getDeclaredMethod("runCleanup", 3));
                code.emitReturn();
            }
            this.method = save_method;
            this.callContextVar = callContextSave;
            initMethod = initMethod3;
        } else {
            int i5 = arg_count;
            Type[] typeArr3 = arg_types;
            boolean z3 = staticModule;
            initMethod = null;
        }
        module.generateApplyMethods(this);
        this.curLambda = saveLambda;
        module.heapFrame = heapFrame2;
        if (usingCPStyle()) {
            code = getCode();
            this.fswitch.finish(code);
        }
        if (!(startLiterals == null && this.callContextVar == null)) {
            this.method = initMethod;
            CodeAttr code2 = getCode();
            Label endLiterals = new Label(code2);
            code2.fixupChain(startLiterals, endLiterals);
            if (this.callContextVarForInit != null) {
                code2.emitInvokeStatic(getCallContextInstanceMethod);
                code2.emitStore(this.callContextVarForInit);
            }
            try {
                if (this.immediate) {
                    code2.emitPushInt(registerForImmediateLiterals(this));
                    code2.emitInvokeStatic(ClassType.make("gnu.expr.Compilation").getDeclaredMethod("setupLiterals", 1));
                } else {
                    this.litTable.emit();
                }
            } catch (Throwable ex) {
                error('e', "Literals: Internal error:" + ex);
            }
            code2.fixupChain(endLiterals, afterLiterals);
            code = code2;
        }
        if (!this.generateMain || this.curClass != this.mainClass) {
            CodeAttr codeAttr = code;
        } else {
            this.method = this.curClass.addMethod("main", 9, new Type[]{new ArrayType(javaStringType)}, (Type) Type.voidType);
            CodeAttr code3 = this.method.startCode();
            if (Shell.defaultFormatName != null) {
                code3.emitPushString(Shell.defaultFormatName);
                code3.emitInvokeStatic(ClassType.make("kawa.Shell").getDeclaredMethod("setDefaultFormat", 1));
            }
            code3.emitLoad(code3.getArg(0));
            code3.emitInvokeStatic(ClassType.make("gnu.expr.ApplicationMainSupport").getDeclaredMethod("processArgs", 1));
            if (this.moduleInstanceMainField != null) {
                code3.emitGetStatic(this.moduleInstanceMainField);
            } else {
                code3.emitNew(this.curClass);
                code3.emitDup((Type) this.curClass);
                code3.emitInvokeSpecial(this.curClass.constructor);
            }
            code3.emitInvokeVirtual(typeModuleBody.getDeclaredMethod("runAsMain", 0));
            code3.emitReturn();
            CodeAttr codeAttr2 = code3;
        }
        if (this.minfo != null) {
            String namespaceUri = this.minfo.getNamespaceUri();
            String uri2 = namespaceUri;
            if (namespaceUri != null) {
                ModuleManager manager2 = ModuleManager.getInstance();
                String mainPrefix2 = this.mainClass.getName();
                int dot = mainPrefix2.lastIndexOf(46);
                if (dot < 0) {
                    ModuleExp moduleExp = module;
                    mainPrefix = "";
                } else {
                    String mainPackage = mainPrefix2.substring(0, dot);
                    try {
                        manager2.loadPackageInfo(mainPackage);
                        ModuleExp moduleExp2 = module;
                    } catch (ClassNotFoundException e) {
                        ModuleExp moduleExp3 = module;
                    } catch (Throwable th) {
                        ModuleExp moduleExp4 = module;
                        error('e', "error loading map for " + mainPackage + " - " + th);
                    }
                    mainPrefix = mainPrefix2.substring(0, dot + 1);
                }
                ClassType mapClass = new ClassType(mainPrefix + ModuleSet.MODULES_MAP);
                ClassType typeModuleSet2 = ClassType.make("gnu.expr.ModuleSet");
                mapClass.setSuper(typeModuleSet2);
                registerClass(mapClass);
                Method method2 = initMethod;
                LambdaExp lambdaExp = saveLambda;
                this.method = mapClass.addMethod("<init>", 1, apply0args, (Type) Type.voidType);
                Method superConstructor = typeModuleSet2.addMethod("<init>", 1, apply0args, (Type) Type.voidType);
                CodeAttr code4 = this.method.startCode();
                code4.emitPushThis();
                code4.emitInvokeSpecial(superConstructor);
                code4.emitReturn();
                ClassType typeModuleManager = ClassType.make("gnu.expr.ModuleManager");
                CodeAttr codeAttr3 = code4;
                Method method3 = superConstructor;
                this.method = mapClass.addMethod("register", new Type[]{typeModuleManager}, (Type) Type.voidType, 1);
                CodeAttr code5 = this.method.startCode();
                ClassType classType2 = mapClass;
                Method reg = typeModuleManager.getDeclaredMethod("register", 3);
                int i6 = manager2.numModules;
                while (true) {
                    int i7 = i6 - 1;
                    if (i7 >= 0) {
                        ClassType typeModuleManager2 = typeModuleManager;
                        ModuleInfo mi = manager2.modules[i7];
                        int i8 = i7;
                        String miClassName = mi.getClassName();
                        if (miClassName == null) {
                            String str = miClassName;
                            uri = uri2;
                            manager = manager2;
                            typeModuleSet = typeModuleSet2;
                            heapFrame = heapFrame2;
                        } else if (!miClassName.startsWith(mainPrefix)) {
                            uri = uri2;
                            manager = manager2;
                            typeModuleSet = typeModuleSet2;
                            heapFrame = heapFrame2;
                        } else {
                            uri = uri2;
                            String moduleSource = mi.sourcePath;
                            typeModuleSet = typeModuleSet2;
                            String moduleUri = mi.getNamespaceUri();
                            heapFrame = heapFrame2;
                            code5.emitLoad(code5.getArg(1));
                            compileConstant(miClassName);
                            if (!Path.valueOf(moduleSource).isAbsolute()) {
                                try {
                                    char sep = File.separatorChar;
                                    String str2 = miClassName;
                                    String path = manager2.getCompilationDirectory();
                                    try {
                                        String str3 = path;
                                        manager = manager2;
                                        try {
                                            String path2 = Path.toURL(path + mainPrefix.replace('.', sep)).toString();
                                            int plen = path2.length();
                                            if (plen > 0 && path2.charAt(plen - 1) != sep) {
                                                path2 = path2 + sep;
                                            }
                                            moduleSource = Path.relativize(mi.getSourceAbsPathname(), path2);
                                        } catch (Throwable th2) {
                                            ex = th2;
                                            throw new WrappedException("exception while fixing up '" + moduleSource + '\'', ex);
                                        }
                                    } catch (Throwable th3) {
                                        ex = th3;
                                        ModuleManager moduleManager = manager2;
                                        throw new WrappedException("exception while fixing up '" + moduleSource + '\'', ex);
                                    }
                                } catch (Throwable th4) {
                                    ex = th4;
                                    String str4 = miClassName;
                                    ModuleManager moduleManager2 = manager2;
                                    throw new WrappedException("exception while fixing up '" + moduleSource + '\'', ex);
                                }
                            } else {
                                manager = manager2;
                            }
                            compileConstant(moduleSource);
                            compileConstant(moduleUri);
                            code5.emitInvokeVirtual(reg);
                        }
                        manager2 = manager;
                        typeModuleManager = typeModuleManager2;
                        i6 = i8;
                        uri2 = uri;
                        typeModuleSet2 = typeModuleSet;
                        heapFrame2 = heapFrame;
                    } else {
                        ClassType classType3 = typeModuleManager;
                        String str5 = uri2;
                        ModuleManager moduleManager3 = manager2;
                        ClassType classType4 = typeModuleSet2;
                        Variable variable2 = heapFrame2;
                        code5.emitReturn();
                        return;
                    }
                }
            } else {
                Method method4 = initMethod;
                LambdaExp lambdaExp2 = saveLambda;
                String str6 = uri2;
                Variable variable3 = heapFrame2;
            }
        } else {
            Method method5 = initMethod;
            LambdaExp lambdaExp3 = saveLambda;
            Variable variable4 = heapFrame2;
        }
    }

    public Field allocLocalField(Type type, String name) {
        if (name == null) {
            StringBuilder append = new StringBuilder().append("tmp_");
            int i = this.localFieldIndex + 1;
            this.localFieldIndex = i;
            name = append.append(i).toString();
        }
        return this.curClass.addField(name, type, 0);
    }

    public final void loadCallContext() {
        CodeAttr code = getCode();
        if (this.callContextVar != null && !this.callContextVar.dead()) {
            code.emitLoad(this.callContextVar);
        } else if (this.method == this.clinitMethod) {
            this.callContextVar = new Variable("$ctx", typeCallContext);
            this.callContextVar.reserveLocal(code.getMaxLocals(), code);
            code.emitLoad(this.callContextVar);
            this.callContextVarForInit = this.callContextVar;
        } else {
            code.emitInvokeStatic(getCallContextInstanceMethod);
            code.emitDup();
            this.callContextVar = new Variable("$ctx", typeCallContext);
            code.getCurrentScope().addVariable(code, this.callContextVar);
            code.emitStore(this.callContextVar);
        }
    }

    public void freeLocalField(Field field) {
    }

    public Expression parse(Object input) {
        throw new Error("unimeplemented parse");
    }

    public Language getLanguage() {
        return this.language;
    }

    public LambdaExp currentLambda() {
        return this.current_scope.currentLambda();
    }

    public final ModuleExp getModule() {
        return this.mainLambda;
    }

    public void setModule(ModuleExp mexp) {
        this.mainLambda = mexp;
    }

    public boolean isStatic() {
        return this.mainLambda.isStatic();
    }

    public ModuleExp currentModule() {
        return this.current_scope.currentModule();
    }

    public void mustCompileHere() {
        if (this.mustCompile || ModuleExp.compilerAvailable) {
            this.mustCompile = true;
        } else {
            error('w', "this expression claimed that it must be compiled, but compiler is unavailable");
        }
    }

    public ScopeExp currentScope() {
        return this.current_scope;
    }

    public void setCurrentScope(ScopeExp scope) {
        int scope_nesting = ScopeExp.nesting(scope);
        int current_nesting = ScopeExp.nesting(this.current_scope);
        while (current_nesting > scope_nesting) {
            pop(this.current_scope);
            current_nesting--;
        }
        ScopeExp sc = scope;
        while (scope_nesting > current_nesting) {
            sc = sc.outer;
            scope_nesting--;
        }
        while (sc != this.current_scope) {
            pop(this.current_scope);
            sc = sc.outer;
        }
        pushChain(scope, sc);
    }

    /* access modifiers changed from: package-private */
    public void pushChain(ScopeExp scope, ScopeExp limit) {
        if (scope != limit) {
            pushChain(scope.outer, limit);
            pushScope(scope);
            this.lexical.push(scope);
        }
    }

    public ModuleExp pushNewModule(Lexer lexer2) {
        this.lexer = lexer2;
        return pushNewModule(lexer2.getName());
    }

    public ModuleExp pushNewModule(String filename) {
        ModuleExp module = new ModuleExp();
        if (filename != null) {
            module.setFile(filename);
        }
        if (generatingApplet() || generatingServlet()) {
            module.setFlag(131072);
        }
        if (this.immediate) {
            module.setFlag(1048576);
            new ModuleInfo().setCompilation(this);
        }
        this.mainLambda = module;
        push((ScopeExp) module);
        return module;
    }

    public void push(ScopeExp scope) {
        pushScope(scope);
        this.lexical.push(scope);
    }

    public final void pushScope(ScopeExp scope) {
        if (!this.mustCompile && (scope.mustCompile() || (ModuleExp.compilerAvailable && (scope instanceof LambdaExp) && !(scope instanceof ModuleExp)))) {
            mustCompileHere();
        }
        scope.outer = this.current_scope;
        this.current_scope = scope;
    }

    public void pop(ScopeExp scope) {
        this.lexical.pop(scope);
        this.current_scope = scope.outer;
    }

    public final void pop() {
        pop(this.current_scope);
    }

    public void push(Declaration decl) {
        this.lexical.push(decl);
    }

    public Declaration lookup(Object name, int namespace) {
        return this.lexical.lookup(name, namespace);
    }

    public void usedClass(Type type) {
        while (type instanceof ArrayType) {
            type = ((ArrayType) type).getComponentType();
        }
        if (this.immediate && (type instanceof ClassType)) {
            this.loader.addClass((ClassType) type);
        }
    }

    public SourceMessages getMessages() {
        return this.messages;
    }

    public void setMessages(SourceMessages messages2) {
        this.messages = messages2;
    }

    public void error(char severity, String message, SourceLocator location) {
        String file = location.getFileName();
        int line = location.getLineNumber();
        int column = location.getColumnNumber();
        if (file == null || line <= 0) {
            file = getFileName();
            line = getLineNumber();
            column = getColumnNumber();
        }
        if (severity == 'w' && warnAsError()) {
            severity = 'e';
        }
        this.messages.error(severity, file, line, column, message);
    }

    public void error(char severity, String message) {
        if (severity == 'w' && warnAsError()) {
            severity = 'e';
        }
        this.messages.error(severity, (SourceLocator) this, message);
    }

    public void error(char severity, Declaration decl, String msg1, String msg2) {
        error(severity, msg1 + decl.getName() + msg2, (String) null, decl);
    }

    public void error(char severity, String message, String code, Declaration decl) {
        int column;
        int line;
        String filename;
        if (severity == 'w' && warnAsError()) {
            severity = 'e';
        }
        String filename2 = getFileName();
        int line2 = getLineNumber();
        int column2 = getColumnNumber();
        int decl_line = decl.getLineNumber();
        if (decl_line > 0) {
            filename = decl.getFileName();
            line = decl_line;
            column = decl.getColumnNumber();
        } else {
            filename = filename2;
            line = line2;
            column = column2;
        }
        this.messages.error(severity, filename, line, column, message, code);
    }

    public Expression syntaxError(String message) {
        error('e', message);
        return new ErrorExp(message);
    }

    public final int getLineNumber() {
        return this.messages.getLineNumber();
    }

    public final int getColumnNumber() {
        return this.messages.getColumnNumber();
    }

    public final String getFileName() {
        return this.messages.getFileName();
    }

    public String getPublicId() {
        return this.messages.getPublicId();
    }

    public String getSystemId() {
        return this.messages.getSystemId();
    }

    public boolean isStableSourceLocation() {
        return false;
    }

    public void setFile(String filename) {
        this.messages.setFile(filename);
    }

    public void setLine(int line) {
        this.messages.setLine(line);
    }

    public void setColumn(int column) {
        this.messages.setColumn(column);
    }

    public final void setLine(Expression position) {
        this.messages.setLocation(position);
    }

    public void setLine(Object location) {
        if (location instanceof SourceLocator) {
            this.messages.setLocation((SourceLocator) location);
        }
    }

    public final void setLocation(SourceLocator position) {
        this.messages.setLocation(position);
    }

    public void setLine(String filename, int line, int column) {
        this.messages.setLine(filename, line, column);
    }

    public void letStart() {
        pushScope(new LetExp((Expression[]) null));
    }

    public Declaration letVariable(Object name, Type type, Expression init) {
        Declaration decl = ((LetExp) this.current_scope).addDeclaration(name, type);
        decl.noteValue(init);
        return decl;
    }

    public void letEnter() {
        LetExp let = (LetExp) this.current_scope;
        Expression[] inits = new Expression[let.countDecls()];
        int i = 0;
        Declaration decl = let.firstDecl();
        while (decl != null) {
            inits[i] = decl.getValue();
            decl = decl.nextDecl();
            i++;
        }
        let.inits = inits;
        this.lexical.push((ScopeExp) let);
    }

    public LetExp letDone(Expression body) {
        LetExp let = (LetExp) this.current_scope;
        let.body = body;
        pop(let);
        return let;
    }

    private void checkLoop() {
        if (((LambdaExp) this.current_scope).getName() != "%do%loop") {
            throw new Error("internal error - bad loop state");
        }
    }

    public void loopStart() {
        LambdaExp loopLambda = new LambdaExp();
        LetExp let = new LetExp(new Expression[]{loopLambda});
        let.addDeclaration((Object) "%do%loop").noteValue(loopLambda);
        loopLambda.setName("%do%loop");
        let.outer = this.current_scope;
        loopLambda.outer = let;
        this.current_scope = loopLambda;
    }

    public Declaration loopVariable(Object name, Type type, Expression init) {
        checkLoop();
        LambdaExp loopLambda = (LambdaExp) this.current_scope;
        Declaration decl = loopLambda.addDeclaration(name, type);
        if (this.exprStack == null) {
            this.exprStack = new Stack<>();
        }
        this.exprStack.push(init);
        loopLambda.min_args++;
        return decl;
    }

    public void loopEnter() {
        checkLoop();
        LambdaExp loopLambda = (LambdaExp) this.current_scope;
        int ninits = loopLambda.min_args;
        loopLambda.max_args = ninits;
        Expression[] inits = new Expression[ninits];
        int i = ninits;
        while (true) {
            i--;
            if (i >= 0) {
                inits[i] = this.exprStack.pop();
            } else {
                LetExp let = (LetExp) loopLambda.outer;
                let.setBody(new ApplyExp((Expression) new ReferenceExp(let.firstDecl()), inits));
                this.lexical.push((ScopeExp) loopLambda);
                return;
            }
        }
    }

    public void loopCond(Expression cond) {
        checkLoop();
        this.exprStack.push(cond);
    }

    public void loopBody(Expression body) {
        ((LambdaExp) this.current_scope).body = body;
    }

    public Expression loopRepeat(Expression[] exps) {
        LambdaExp loopLambda = (LambdaExp) this.current_scope;
        ScopeExp let = loopLambda.outer;
        loopLambda.body = new IfExp(this.exprStack.pop(), new BeginExp(loopLambda.body, new ApplyExp((Expression) new ReferenceExp(let.firstDecl()), exps)), QuoteExp.voidExp);
        this.lexical.pop((ScopeExp) loopLambda);
        this.current_scope = let.outer;
        return let;
    }

    public Expression loopRepeat() {
        return loopRepeat(Expression.noExpressions);
    }

    public Expression loopRepeat(Expression exp) {
        return loopRepeat(new Expression[]{exp});
    }

    public static ApplyExp makeCoercion(Expression value, Expression type) {
        return new ApplyExp((Expression) new QuoteExp(Convert.getInstance()), type, value);
    }

    public static Expression makeCoercion(Expression value, Type type) {
        return makeCoercion(value, (Expression) new QuoteExp(type));
    }

    public void loadClassRef(ObjectType clas) {
        CodeAttr code = getCode();
        if (this.curClass.getClassfileVersion() >= 3211264) {
            code.emitPushClass(clas);
        } else if (clas != this.mainClass || !this.mainLambda.isStatic() || this.moduleInstanceMainField == null) {
            code.emitPushString(clas instanceof ClassType ? clas.getName() : clas.getInternalName().replace('/', '.'));
            code.emitInvokeStatic(getForNameHelper());
        } else {
            code.emitGetStatic(this.moduleInstanceMainField);
            code.emitInvokeVirtual(Type.objectType.getDeclaredMethod("getClass", 0));
        }
    }

    public Method getForNameHelper() {
        if (this.forNameHelper == null) {
            Method save_method = this.method;
            this.method = this.curClass.addMethod("class$", 9, string1Arg, (Type) typeClass);
            this.forNameHelper = this.method;
            CodeAttr code = this.method.startCode();
            code.emitLoad(code.getArg(0));
            code.emitPushInt(0);
            code.emitPushString(this.mainClass.getName());
            code.emitInvokeStatic(typeClass.getDeclaredMethod("forName", 1));
            code.emitInvokeVirtual(typeClass.getDeclaredMethod("getClassLoader", 0));
            code.emitInvokeStatic(typeClass.getDeclaredMethod("forName", 3));
            code.emitReturn();
            this.method = save_method;
        }
        return this.forNameHelper;
    }

    public Object resolve(Object name, boolean function) {
        Symbol symbol;
        Environment env = Environment.getCurrent();
        if (name instanceof String) {
            symbol = env.defaultNamespace().lookup((String) name);
        } else {
            symbol = (Symbol) name;
        }
        if (symbol == null) {
            return null;
        }
        if (!function || !getLanguage().hasSeparateFunctionNamespace()) {
            return env.get((EnvironmentKey) symbol, (Object) null);
        }
        return env.getFunction(symbol, (Object) null);
    }

    public static void setupLiterals(int key) {
        Compilation comp = findForImmediateLiterals(key);
        try {
            Class clas = comp.loader.loadClass(comp.mainClass.getName());
            for (Literal init = comp.litTable.literalsChain; init != null; init = init.next) {
                clas.getDeclaredField(init.field.getName()).set((Object) null, init.value);
            }
            comp.litTable = null;
        } catch (Throwable ex) {
            throw new WrappedException("internal error", ex);
        }
    }

    public static synchronized int registerForImmediateLiterals(Compilation comp) {
        int i;
        synchronized (Compilation.class) {
            i = 0;
            for (Compilation c = chainUninitialized; c != null; c = c.nextUninitialized) {
                if (i <= c.keyUninitialized) {
                    i = c.keyUninitialized + 1;
                }
            }
            comp.keyUninitialized = i;
            comp.nextUninitialized = chainUninitialized;
            chainUninitialized = comp;
        }
        return i;
    }

    public static synchronized Compilation findForImmediateLiterals(int key) {
        Compilation comp;
        Compilation next;
        synchronized (Compilation.class) {
            Compilation prev = null;
            comp = chainUninitialized;
            while (true) {
                next = comp.nextUninitialized;
                if (comp.keyUninitialized == key) {
                    break;
                }
                prev = comp;
                comp = next;
            }
            if (prev == null) {
                chainUninitialized = next;
            } else {
                prev.nextUninitialized = next;
            }
            comp.nextUninitialized = null;
        }
        return comp;
    }

    public static Compilation getCurrent() {
        return current.get();
    }

    public static void setCurrent(Compilation comp) {
        current.set(comp);
    }

    public static Compilation setSaveCurrent(Compilation comp) {
        Compilation save = current.get();
        current.set(comp);
        return save;
    }

    public static void restoreCurrent(Compilation saved) {
        current.set(saved);
    }

    public String toString() {
        return "<compilation " + this.mainLambda + ">";
    }
}
