package kawa.standard;

import gnu.expr.Expression;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleManager;
import gnu.expr.ScopeExp;
import gnu.lists.Pair;
import gnu.text.Path;
import java.util.Hashtable;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class require extends Syntax {
    private static final String SLIB_PREFIX = "gnu.kawa.slib.";
    static Hashtable featureMap = new Hashtable();
    public static final require require = new require();

    static {
        require.setName("require");
        map("generic-write", "gnu.kawa.slib.genwrite");
        map("pretty-print", "gnu.kawa.slib.pp");
        map("pprint-file", "gnu.kawa.slib.ppfile");
        map("printf", "gnu.kawa.slib.printf");
        map("xml", "gnu.kawa.slib.XML");
        map("readtable", "gnu.kawa.slib.readtable");
        map("srfi-10", "gnu.kawa.slib.readtable");
        map("http", "gnu.kawa.servlet.HTTP");
        map("servlets", "gnu.kawa.servlet.servlets");
        map("srfi-1", "gnu.kawa.slib.srfi1");
        map("list-lib", "gnu.kawa.slib.srfi1");
        map("srfi-2", "gnu.kawa.slib.srfi2");
        map("and-let*", "gnu.kawa.slib.srfi2");
        map("srfi-13", "gnu.kawa.slib.srfi13");
        map("string-lib", "gnu.kawa.slib.srfi13");
        map("srfi-34", "gnu.kawa.slib.srfi34");
        map("srfi-35", "gnu.kawa.slib.conditions");
        map("condition", "gnu.kawa.slib.conditions");
        map("conditions", "gnu.kawa.slib.conditions");
        map("srfi-37", "gnu.kawa.slib.srfi37");
        map("args-fold", "gnu.kawa.slib.srfi37");
        map("srfi-64", "gnu.kawa.slib.testing");
        map("testing", "gnu.kawa.slib.testing");
        map("srfi-69", "gnu.kawa.slib.srfi69");
        map("hash-table", "gnu.kawa.slib.srfi69");
        map("basic-hash-tables", "gnu.kawa.slib.srfi69");
        map("srfi-95", "kawa.lib.srfi95");
        map("sorting-and-merging", "kawa.lib.srfi95");
        map("regex", "kawa.lib.kawa.regex");
        map("pregexp", "gnu.kawa.slib.pregexp");
        map("gui", "gnu.kawa.slib.gui");
        map("swing-gui", "gnu.kawa.slib.swing");
        map("android-defs", "gnu.kawa.android.defs");
        map("syntax-utils", "gnu.kawa.slib.syntaxutils");
    }

    static void map(String featureName, String className) {
        featureMap.put(featureName, className);
    }

    public static String mapFeature(String featureName) {
        return (String) featureMap.get(featureName);
    }

    public static Object find(String typeName) {
        return ModuleManager.getInstance().findWithClassName(typeName).getInstance();
    }

    /* JADX WARNING: Removed duplicated region for block: B:49:0x011c  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0122  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean scanForDefinitions(gnu.lists.Pair r11, java.util.Vector r12, gnu.expr.ScopeExp r13, kawa.lang.Translator r14) {
        /*
            r10 = this;
            int r1 = r14.getState()
            r7 = 1
            if (r1 != r7) goto L_0x000e
            r0 = 2
            r14.setState(r0)
            r14.pendingForm = r11
            return r7
        L_0x000e:
            java.lang.Object r1 = r11.getCdr()
            gnu.lists.Pair r1 = (gnu.lists.Pair) r1
            java.lang.Object r2 = r1.getCar()
            boolean r3 = r2 instanceof gnu.lists.Pair
            r4 = 101(0x65, float:1.42E-43)
            r5 = 0
            if (r3 == 0) goto L_0x0088
            r3 = r2
            gnu.lists.Pair r3 = (gnu.lists.Pair) r3
            java.lang.Object r8 = r3.getCar()
            java.lang.String r9 = "quote"
            boolean r8 = r14.matches(r8, r9)
            if (r8 == 0) goto L_0x0088
            java.lang.Object r1 = r3.getCdr()
            boolean r2 = r1 instanceof gnu.lists.Pair
            if (r2 == 0) goto L_0x0082
            gnu.lists.Pair r1 = (gnu.lists.Pair) r1
            java.lang.Object r2 = r1.getCdr()
            gnu.lists.LList r3 = gnu.lists.LList.Empty
            if (r2 != r3) goto L_0x0082
            java.lang.Object r2 = r1.getCar()
            boolean r2 = r2 instanceof gnu.mapping.Symbol
            if (r2 != 0) goto L_0x004a
            goto L_0x0082
        L_0x004a:
            java.lang.Object r2 = r1.getCar()
            java.lang.String r2 = r2.toString()
            java.lang.String r2 = mapFeature(r2)
            if (r2 != 0) goto L_0x0079
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "unknown feature name '"
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.Object r1 = r1.getCar()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = "' for 'require'"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r14.error(r4, r0)
            return r5
        L_0x0079:
            r1 = r2
            java.lang.String r1 = (java.lang.String) r1
            gnu.bytecode.ClassType r1 = gnu.bytecode.ClassType.make(r2)
            goto L_0x0118
        L_0x0082:
            java.lang.String r0 = "invalid quoted symbol for 'require'"
            r14.error(r4, r0)
            return r5
        L_0x0088:
            boolean r3 = r2 instanceof java.lang.CharSequence
            java.lang.String r8 = "malformed URL: "
            if (r3 == 0) goto L_0x00b7
            java.lang.String r1 = r2.toString()
            gnu.expr.ModuleInfo r2 = lookupModuleFromSourcePath(r1, r13)
            if (r2 != 0) goto L_0x00ad
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r0 = r0.append(r8)
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r14.error(r4, r0)
            return r5
        L_0x00ad:
            r1 = 0
            r3 = 0
            r4 = r12
            r5 = r13
            r6 = r14
            boolean r0 = importDefinitions(r1, r2, r3, r4, r5, r6)
            return r0
        L_0x00b7:
            boolean r3 = r2 instanceof gnu.mapping.Symbol
            if (r3 == 0) goto L_0x0117
            boolean r3 = r14.selfEvaluatingSymbol(r2)
            if (r3 != 0) goto L_0x0117
            gnu.expr.Language r3 = r14.getLanguage()
            gnu.expr.Expression r2 = r14.rewrite(r2, r5)
            gnu.bytecode.Type r2 = r3.getTypeFor((gnu.expr.Expression) r2)
            boolean r3 = r2 instanceof gnu.bytecode.ClassType
            if (r3 == 0) goto L_0x0115
            java.lang.Object r3 = r1.getCdr()
            boolean r3 = r3 instanceof gnu.lists.Pair
            if (r3 == 0) goto L_0x0115
            java.lang.Object r1 = r1.getCdr()
            gnu.lists.Pair r1 = (gnu.lists.Pair) r1
            java.lang.Object r1 = r1.getCar()
            boolean r3 = r1 instanceof java.lang.CharSequence
            if (r3 == 0) goto L_0x0115
            java.lang.String r1 = r1.toString()
            gnu.expr.ModuleInfo r3 = lookupModuleFromSourcePath(r1, r13)
            if (r3 != 0) goto L_0x0106
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r0 = r0.append(r8)
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r14.error(r4, r0)
            return r5
        L_0x0106:
            java.lang.String r1 = r2.getName()
            r4 = 0
            r2 = r3
            r3 = r4
            r4 = r12
            r5 = r13
            r6 = r14
            boolean r0 = importDefinitions(r1, r2, r3, r4, r5, r6)
            return r0
        L_0x0115:
            r1 = r2
            goto L_0x0118
        L_0x0117:
            r1 = 0
        L_0x0118:
            boolean r2 = r1 instanceof gnu.bytecode.ClassType
            if (r2 != 0) goto L_0x0122
            java.lang.String r0 = "invalid specifier for 'require'"
            r14.error(r4, r0)
            return r5
        L_0x0122:
            r2 = r1
            gnu.bytecode.ClassType r2 = (gnu.bytecode.ClassType) r2     // Catch:{ Exception -> 0x0133 }
            gnu.expr.ModuleInfo r2 = gnu.expr.ModuleInfo.find(r2)     // Catch:{ Exception -> 0x0133 }
            r1 = 0
            r3 = 0
            r4 = r12
            r5 = r13
            r6 = r14
            importDefinitions(r1, r2, r3, r4, r5, r6)
            return r7
        L_0x0133:
            r0 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "unknown class "
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r1 = r1.getName()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r14.error(r4, r0)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.require.scanForDefinitions(gnu.lists.Pair, java.util.Vector, gnu.expr.ScopeExp, kawa.lang.Translator):boolean");
    }

    public static ModuleInfo lookupModuleFromSourcePath(String sourceName, ScopeExp defs) {
        ModuleManager manager = ModuleManager.getInstance();
        String baseName = defs.getFileName();
        if (baseName != null) {
            sourceName = Path.valueOf(baseName).resolve(sourceName).toString();
        }
        return manager.findWithSourcePath(sourceName);
    }

    /* JADX WARNING: type inference failed for: r5v5 */
    /* JADX WARNING: type inference failed for: r5v7 */
    /* JADX WARNING: type inference failed for: r5v8 */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0266, code lost:
        if (gnu.expr.Declaration.followAliases(r11) == gnu.expr.Declaration.followAliases(r14)) goto L_0x0269;
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=int, code=?, for r5v4, types: [boolean, int] */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x02b9  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x02c2  */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x02c8  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x02de  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x02e4  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x02a2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean importDefinitions(java.lang.String r22, gnu.expr.ModuleInfo r23, gnu.mapping.Procedure r24, java.util.Vector r25, gnu.expr.ScopeExp r26, gnu.expr.Compilation r27) {
        /*
            r0 = r23
            r1 = r24
            r2 = r25
            r3 = r26
            r4 = r27
            gnu.expr.ModuleManager r5 = gnu.expr.ModuleManager.getInstance()
            int r6 = r23.getState()
            r7 = 1
            r6 = r6 & r7
            r8 = 101(0x65, float:1.42E-43)
            r9 = 0
            if (r6 != 0) goto L_0x00b5
            gnu.expr.Compilation r6 = r23.getCompilation()
            if (r6 != 0) goto L_0x00b5
            long r10 = java.lang.System.currentTimeMillis()
            boolean r5 = r0.checkCurrent(r5, r10)
            if (r5 != 0) goto L_0x00b5
            gnu.text.SourceMessages r5 = r27.getMessages()
            gnu.expr.Language r6 = gnu.expr.Language.getDefaultLanguage()
            gnu.text.Path r10 = r23.getSourceAbsPath()     // Catch:{ FileNotFoundException -> 0x0099, IOException -> 0x0081, SyntaxException -> 0x0060 }
            gnu.mapping.InPort r10 = gnu.mapping.InPort.openFile(r10)     // Catch:{ FileNotFoundException -> 0x0099, IOException -> 0x0081, SyntaxException -> 0x0060 }
            r23.clearClass()     // Catch:{ FileNotFoundException -> 0x0099, IOException -> 0x0081, SyntaxException -> 0x0060 }
            r11 = r22
            r0.setClassName(r11)     // Catch:{ FileNotFoundException -> 0x0099, IOException -> 0x0081, SyntaxException -> 0x0060 }
            boolean r11 = r4.immediate     // Catch:{ FileNotFoundException -> 0x0099, IOException -> 0x0081, SyntaxException -> 0x0060 }
            if (r11 == 0) goto L_0x0049
            r11 = 9
            goto L_0x004b
        L_0x0049:
            r11 = 8
        L_0x004b:
            gnu.expr.Compilation r5 = r6.parse(r10, r5, r11, r0)     // Catch:{ FileNotFoundException -> 0x0099, IOException -> 0x0081, SyntaxException -> 0x0060 }
            gnu.expr.ModuleExp r6 = r5.getModule()
            gnu.bytecode.ClassType r5 = r6.classFor(r5)
            java.lang.String r5 = r5.getName()
            r0.setClassName(r5)
            goto L_0x00b5
        L_0x0060:
            r0 = move-exception
            gnu.text.SourceMessages r1 = r0.getMessages()
            if (r1 != r5) goto L_0x0068
            return r9
        L_0x0068:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "confussing syntax error: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        L_0x0081:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "caught "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r0 = r0.toString()
            r4.error(r8, r0)
            return r9
        L_0x0099:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "not found: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r0 = r0.toString()
            r4.error(r8, r0)
            return r9
        L_0x00b5:
            gnu.expr.ModuleInfo r5 = r4.minfo
            r6 = 6
            if (r5 == 0) goto L_0x00dc
            int r5 = r27.getState()
            r10 = 4
            if (r5 >= r10) goto L_0x00dc
            gnu.expr.ModuleInfo r5 = r4.minfo
            r5.addDependency(r0)
            r5 = 12
            boolean r5 = r0.loadEager(r5)
            if (r5 != 0) goto L_0x00dc
            int r5 = r23.getState()
            if (r5 >= r6) goto L_0x00dc
            int r1 = r25.size()
            r4.pushPendingImport(r0, r3, r1)
            return r7
        L_0x00dc:
            gnu.bytecode.ClassType r5 = r23.getClassType()
            java.lang.String r10 = r5.getName()
            boolean r11 = r27.sharedModuleDefs()
            int r12 = r23.getState()
            if (r12 >= r6) goto L_0x00f7
            gnu.expr.Compilation r6 = r23.getCompilation()
            boolean r6 = r6.makeRunnable()
            goto L_0x00fd
        L_0x00f7:
            gnu.bytecode.ClassType r6 = gnu.expr.Compilation.typeRunnable
            boolean r6 = r5.isSubtype(r6)
        L_0x00fd:
            java.lang.String r12 = "kawa.standard.require"
            gnu.bytecode.ClassType r12 = gnu.bytecode.ClassType.make(r12)
            gnu.expr.Expression[] r13 = new gnu.expr.Expression[r7]
            gnu.expr.QuoteExp r14 = new gnu.expr.QuoteExp
            r14.<init>(r10)
            r13[r9] = r14
            java.lang.String r14 = "find"
            gnu.expr.ApplyExp r12 = gnu.kawa.reflect.Invoke.makeInvokeStatic(r12, r14, r13)
            gnu.expr.Language r13 = r27.getLanguage()
            r12.setLine((gnu.expr.Compilation) r4)
            int r14 = r25.size()
            gnu.expr.ModuleExp r0 = r23.setupModuleExp()
            java.util.Vector r15 = new java.util.Vector
            r15.<init>()
            gnu.expr.Declaration r0 = r0.firstDecl()
            r16 = 0
            r9 = r16
            r17 = r9
            r16 = r14
            r14 = r0
        L_0x0135:
            java.lang.String r7 = "$instance"
            if (r14 == 0) goto L_0x030d
            boolean r0 = r14.isPrivate()
            if (r0 == 0) goto L_0x0140
            goto L_0x0177
        L_0x0140:
            java.lang.Object r0 = r14.getSymbol()
            gnu.mapping.Symbol r0 = (gnu.mapping.Symbol) r0
            if (r1 == 0) goto L_0x0186
            java.lang.Object r0 = r1.apply1(r0)     // Catch:{ all -> 0x014d }
            goto L_0x014f
        L_0x014d:
            r0 = move-exception
        L_0x014f:
            if (r0 != 0) goto L_0x0152
            goto L_0x0177
        L_0x0152:
            boolean r8 = r0 instanceof gnu.mapping.Symbol
            if (r8 != 0) goto L_0x0182
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "internal error - import name mapper returned non-symbol: "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.Class r0 = r0.getClass()
            java.lang.String r0 = r0.getName()
            java.lang.StringBuilder r0 = r7.append(r0)
            java.lang.String r0 = r0.toString()
            r8 = 101(0x65, float:1.42E-43)
            r4.error(r8, r0)
        L_0x0177:
            r21 = r10
            r8 = r11
            r20 = r13
            r10 = r16
            r16 = r12
            goto L_0x02f9
        L_0x0182:
            r8 = 101(0x65, float:1.42E-43)
            gnu.mapping.Symbol r0 = (gnu.mapping.Symbol) r0
        L_0x0186:
            r18 = r9
            r8 = 2048(0x800, double:1.0118E-320)
            boolean r19 = r14.getFlag(r8)
            if (r19 != 0) goto L_0x01ed
            if (r18 != 0) goto L_0x01ed
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r1 = 46
            r9 = 36
            java.lang.String r1 = r10.replace(r1, r9)
            java.lang.StringBuilder r1 = r8.append(r1)
            java.lang.StringBuilder r1 = r1.append(r7)
            java.lang.String r1 = r1.toString()
            gnu.expr.Declaration r9 = new gnu.expr.Declaration
            gnu.mapping.SimpleSymbol r1 = gnu.mapping.SimpleSymbol.valueOf(r1)
            r9.<init>((java.lang.Object) r1, (gnu.bytecode.Type) r5)
            r1 = 1
            r9.setPrivate(r1)
            r1 = r10
            r8 = r11
            r10 = 1073758208(0x40004000, double:5.305070425E-315)
            r9.setFlag(r10)
            r3.addDeclaration((gnu.expr.Declaration) r9)
            r9.noteValue(r12)
            gnu.expr.SetExp r10 = new gnu.expr.SetExp
            r10.<init>((gnu.expr.Declaration) r9, (gnu.expr.Expression) r12)
            r10.setLine((gnu.expr.Compilation) r4)
            r11 = 1
            r10.setDefining(r11)
            r2.addElement(r10)
            int r16 = r25.size()
            r10 = 536870912(0x20000000, double:2.652494739E-315)
            r9.setFlag(r10)
            if (r6 == 0) goto L_0x01e5
            r10 = 0
            r9.setSimple(r10)
        L_0x01e5:
            r10 = 8192(0x2000, double:4.0474E-320)
            r9.setFlag(r10)
            r10 = r16
            goto L_0x01f3
        L_0x01ed:
            r1 = r10
            r8 = r11
            r10 = r16
            r9 = r18
        L_0x01f3:
            gnu.bytecode.Field r11 = r14.field
            if (r11 == 0) goto L_0x020f
            gnu.bytecode.Field r11 = r14.field
            java.lang.String r11 = r11.getName()
            boolean r11 = r11.equals(r7)
            if (r11 == 0) goto L_0x020f
            gnu.bytecode.Field r0 = r14.field
            r17 = r0
            r21 = r1
            r16 = r12
            r20 = r13
            goto L_0x02f9
        L_0x020f:
            gnu.bytecode.Field r11 = r14.field
            if (r11 == 0) goto L_0x0221
            gnu.bytecode.Field r11 = r14.field
            java.lang.String r11 = r11.getName()
            boolean r7 = r11.endsWith(r7)
            if (r7 == 0) goto L_0x0221
            r7 = 1
            goto L_0x0222
        L_0x0221:
            r7 = 0
        L_0x0222:
            int r11 = r13.getNamespaceOf(r14)
            gnu.expr.Declaration r11 = r3.lookup(r0, r13, r11)
            if (r7 == 0) goto L_0x0250
            if (r11 == 0) goto L_0x0233
            r16 = r12
            r20 = r13
            goto L_0x0269
        L_0x0233:
            gnu.expr.Declaration r0 = r3.addDeclaration((java.lang.Object) r0)
            r16 = r12
            r11 = 1073758208(0x40004000, double:5.305070425E-315)
            r0.setFlag(r11)
            gnu.bytecode.Type r11 = r14.getType()
            r0.setType(r11)
            r11 = 8192(0x2000, double:4.0474E-320)
            r0.setFlag(r11)
            r21 = r1
            r20 = r13
            goto L_0x0295
        L_0x0250:
            r16 = r12
            if (r11 == 0) goto L_0x026d
            r20 = r13
            r12 = 512(0x200, double:2.53E-321)
            boolean r12 = r11.getFlag(r12)
            if (r12 != 0) goto L_0x026f
            gnu.expr.Declaration r12 = gnu.expr.Declaration.followAliases(r11)
            gnu.expr.Declaration r13 = gnu.expr.Declaration.followAliases(r14)
            if (r12 != r13) goto L_0x026f
        L_0x0269:
            r21 = r1
            goto L_0x02f9
        L_0x026d:
            r20 = r13
        L_0x026f:
            if (r11 == 0) goto L_0x0281
            r12 = 66048(0x10200, double:3.2632E-319)
            boolean r18 = r11.getFlag(r12)
            if (r18 == 0) goto L_0x0281
            r21 = r1
            r1 = 0
            r11.setFlag(r1, r12)
            goto L_0x028d
        L_0x0281:
            r21 = r1
            gnu.expr.Declaration r0 = r3.addDeclaration((java.lang.Object) r0)
            if (r11 == 0) goto L_0x028c
            gnu.expr.ScopeExp.duplicateDeclarationError(r11, r0, r4)
        L_0x028c:
            r11 = r0
        L_0x028d:
            r1 = 1
            r11.setAlias(r1)
            r11.setIndirectBinding(r1)
            r0 = r11
        L_0x0295:
            r0.setLocation(r4)
            gnu.expr.ReferenceExp r1 = new gnu.expr.ReferenceExp
            r1.<init>((gnu.expr.Declaration) r14)
            r1.setContextDecl(r9)
            if (r7 != 0) goto L_0x02ab
            r11 = 1
            r1.setDontDereference(r11)
            if (r8 != 0) goto L_0x02ab
            r0.setPrivate(r11)
        L_0x02ab:
            r11 = 16384(0x4000, double:8.0948E-320)
            r0.setFlag(r11)
            r11 = 32768(0x8000, double:1.61895E-319)
            boolean r13 = r14.getFlag(r11)
            if (r13 == 0) goto L_0x02bc
            r0.setFlag(r11)
        L_0x02bc:
            boolean r11 = r14.isProcedureDecl()
            if (r11 == 0) goto L_0x02c6
            r11 = 1
            r0.setProcedureDecl(r11)
        L_0x02c6:
            if (r19 == 0) goto L_0x02cd
            r11 = 2048(0x800, double:1.0118E-320)
            r0.setFlag(r11)
        L_0x02cd:
            gnu.expr.SetExp r11 = new gnu.expr.SetExp
            r11.<init>((gnu.expr.Declaration) r0, (gnu.expr.Expression) r1)
            r12 = 536870912(0x20000000, double:2.652494739E-315)
            r0.setFlag(r12)
            r12 = 1
            r11.setDefining(r12)
            if (r7 == 0) goto L_0x02e4
            r2.insertElementAt(r11, r10)
            int r10 = r10 + 1
            goto L_0x02e7
        L_0x02e4:
            r2.addElement(r11)
        L_0x02e7:
            r15.add(r0)
            r15.add(r14)
            r0.noteValue(r1)
            r11 = 131072(0x20000, double:6.47582E-319)
            r0.setFlag(r11)
            r4.push((gnu.expr.Declaration) r0)
        L_0x02f9:
            gnu.expr.Declaration r14 = r14.nextDecl()
            r1 = r24
            r11 = r8
            r12 = r16
            r13 = r20
            r7 = 1
            r8 = 101(0x65, float:1.42E-43)
            r16 = r10
            r10 = r21
            goto L_0x0135
        L_0x030d:
            r18 = r9
            r16 = r12
            int r0 = r15.size()
            r1 = 0
        L_0x0316:
            if (r1 >= r0) goto L_0x0389
            java.lang.Object r8 = r15.elementAt(r1)
            gnu.expr.Declaration r8 = (gnu.expr.Declaration) r8
            int r9 = r1 + 1
            java.lang.Object r9 = r15.elementAt(r9)
            gnu.expr.Declaration r9 = (gnu.expr.Declaration) r9
            gnu.expr.Expression r10 = r9.getValue()
            boolean r9 = r9.isIndirectBinding()
            if (r9 == 0) goto L_0x0382
            boolean r9 = r10 instanceof gnu.expr.ReferenceExp
            if (r9 == 0) goto L_0x0382
            gnu.expr.Expression r8 = r8.getValue()
            gnu.expr.ReferenceExp r8 = (gnu.expr.ReferenceExp) r8
            gnu.expr.ReferenceExp r10 = (gnu.expr.ReferenceExp) r10
            gnu.expr.Declaration r9 = r10.getBinding()
            r8.setBinding(r9)
            boolean r10 = r9.needsContext()
            if (r10 == 0) goto L_0x037d
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            gnu.bytecode.Field r9 = r9.field
            gnu.bytecode.ClassType r9 = r9.getDeclaringClass()
            java.lang.String r9 = r9.getName()
            r11 = 36
            r12 = 46
            java.lang.String r9 = r9.replace(r12, r11)
            java.lang.StringBuilder r9 = r10.append(r9)
            java.lang.StringBuilder r9 = r9.append(r7)
            java.lang.String r9 = r9.toString()
            gnu.mapping.SimpleSymbol r9 = gnu.mapping.SimpleSymbol.valueOf(r9)
            gnu.expr.Declaration r9 = r3.lookup(r9)
            r13 = 1024(0x400, double:5.06E-321)
            r9.setFlag(r13)
            r8.setContextDecl(r9)
            goto L_0x0386
        L_0x037d:
            r11 = 36
            r12 = 46
            goto L_0x0386
        L_0x0382:
            r11 = 36
            r12 = 46
        L_0x0386:
            int r1 = r1 + 2
            goto L_0x0316
        L_0x0389:
            if (r6 == 0) goto L_0x03d0
            gnu.bytecode.ClassType r0 = gnu.expr.Compilation.typeRunnable
            java.lang.String r1 = "run"
            r3 = 0
            gnu.bytecode.Method r0 = r0.getDeclaredMethod((java.lang.String) r1, (int) r3)
            if (r18 == 0) goto L_0x039f
            gnu.expr.ReferenceExp r12 = new gnu.expr.ReferenceExp
            r9 = r18
            r12.<init>((gnu.expr.Declaration) r9)
            r5 = 1
            goto L_0x03bf
        L_0x039f:
            if (r17 == 0) goto L_0x03bc
            r1 = 2
            gnu.expr.Expression[] r1 = new gnu.expr.Expression[r1]
            gnu.expr.QuoteExp r3 = new gnu.expr.QuoteExp
            r3.<init>(r5)
            r5 = 0
            r1[r5] = r3
            gnu.expr.QuoteExp r3 = new gnu.expr.QuoteExp
            r3.<init>(r7)
            r5 = 1
            r1[r5] = r3
            gnu.expr.ApplyExp r12 = new gnu.expr.ApplyExp
            gnu.kawa.reflect.SlotGet r3 = gnu.kawa.reflect.SlotGet.staticField
            r12.<init>((gnu.mapping.Procedure) r3, (gnu.expr.Expression[]) r1)
            goto L_0x03bf
        L_0x03bc:
            r5 = 1
            r12 = r16
        L_0x03bf:
            gnu.expr.ApplyExp r1 = new gnu.expr.ApplyExp
            gnu.expr.Expression[] r3 = new gnu.expr.Expression[r5]
            r6 = 0
            r3[r6] = r12
            r1.<init>((gnu.bytecode.Method) r0, (gnu.expr.Expression[]) r3)
            r1.setLine((gnu.expr.Compilation) r4)
            r2.addElement(r1)
            goto L_0x03d1
        L_0x03d0:
            r5 = 1
        L_0x03d1:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.require.importDefinitions(java.lang.String, gnu.expr.ModuleInfo, gnu.mapping.Procedure, java.util.Vector, gnu.expr.ScopeExp, gnu.expr.Compilation):boolean");
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        return null;
    }
}
