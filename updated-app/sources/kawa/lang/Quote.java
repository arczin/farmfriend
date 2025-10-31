package kawa.lang;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import java.util.IdentityHashMap;

public class Quote extends Syntax {
    private static final Object CYCLE = new String("(cycle)");
    protected static final int QUOTE_DEPTH = -1;
    private static final Object WORKING = new String("(working)");
    static final Method appendMethod = quoteType.getDeclaredMethod("append$V", 1);
    static final Method consXMethod = quoteType.getDeclaredMethod("consX$V", 1);
    static final Method makePairMethod = Compilation.typePair.getDeclaredMethod("make", 2);
    static final Method makeVectorMethod = ClassType.make("gnu.lists.FVector").getDeclaredMethod("make", 1);
    public static final Quote plainQuote = new Quote(LispLanguage.quote_sym, false);
    public static final Quote quasiQuote = new Quote(LispLanguage.quasiquote_sym, true);
    static final ClassType quoteType = ClassType.make("kawa.lang.Quote");
    static final Method vectorAppendMethod = ClassType.make("kawa.standard.vector_append").getDeclaredMethod("apply$V", 1);
    protected boolean isQuasi;

    public Quote(String name, boolean isQuasi2) {
        super(name);
        this.isQuasi = isQuasi2;
    }

    /* access modifiers changed from: protected */
    public Object expand(Object template, int depth, Translator tr) {
        return expand(template, depth, (SyntaxForm) null, new IdentityHashMap(), tr);
    }

    public static Object quote(Object obj, Translator tr) {
        return plainQuote.expand(obj, -1, tr);
    }

    public static Object quote(Object obj) {
        return plainQuote.expand(obj, -1, (Translator) Compilation.getCurrent());
    }

    /* access modifiers changed from: protected */
    public Expression coerceExpression(Object val, Translator tr) {
        return val instanceof Expression ? (Expression) val : leaf(val, tr);
    }

    /* access modifiers changed from: protected */
    public Expression leaf(Object val, Translator tr) {
        return new QuoteExp(val);
    }

    /* access modifiers changed from: protected */
    public boolean expandColonForms() {
        return true;
    }

    public static Symbol makeSymbol(Namespace ns, Object local) {
        String name;
        if (local instanceof CharSequence) {
            name = ((CharSequence) local).toString();
        } else {
            name = (String) local;
        }
        return ns.getSymbol(name.intern());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v2, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v10, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v11, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v33, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v38, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v12, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v24, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v39, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v13, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v39, resolved type: gnu.mapping.Symbol} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v48, resolved type: gnu.mapping.Symbol} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v44, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v45, resolved type: kawa.lang.SyntaxForm} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x016c, code lost:
        return r9.syntaxError("invalid used of " + r10.getCar() + " in quasiquote template");
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x01d4 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x0295 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01ab A[LOOP:1: B:56:0x01a7->B:58:0x01ab, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x01b8  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0274  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object expand_pair(gnu.lists.Pair r22, int r23, kawa.lang.SyntaxForm r24, java.lang.Object r25, kawa.lang.Translator r26) {
        /*
            r21 = this;
            r6 = r21
            r7 = r22
            r8 = r24
            r9 = r26
            r0 = r22
            r10 = r0
            r0 = r23
        L_0x000d:
            r11 = r10
            boolean r1 = r21.expandColonForms()
            r12 = 2
            r13 = 0
            r14 = 1
            if (r1 == 0) goto L_0x0103
            if (r10 != r7) goto L_0x0103
            java.lang.Object r1 = r10.getCar()
            gnu.mapping.Symbol r2 = gnu.kawa.lispexpr.LispLanguage.lookup_sym
            boolean r1 = r9.matches((java.lang.Object) r1, (kawa.lang.SyntaxForm) r8, (gnu.mapping.Symbol) r2)
            if (r1 == 0) goto L_0x0103
            java.lang.Object r1 = r10.getCdr()
            boolean r1 = r1 instanceof gnu.lists.Pair
            if (r1 == 0) goto L_0x0103
            java.lang.Object r1 = r10.getCdr()
            gnu.lists.Pair r1 = (gnu.lists.Pair) r1
            r2 = r1
            boolean r1 = r1 instanceof gnu.lists.Pair
            if (r1 == 0) goto L_0x0103
            java.lang.Object r1 = r2.getCdr()
            gnu.lists.Pair r1 = (gnu.lists.Pair) r1
            r3 = r1
            boolean r1 = r1 instanceof gnu.lists.Pair
            if (r1 == 0) goto L_0x0101
            java.lang.Object r1 = r3.getCdr()
            gnu.lists.LList r4 = gnu.lists.LList.Empty
            if (r1 != r4) goto L_0x0101
            gnu.expr.Expression r1 = r9.rewrite_car((gnu.lists.Pair) r2, (boolean) r13)
            gnu.expr.Expression r4 = r9.rewrite_car((gnu.lists.Pair) r3, (boolean) r13)
            gnu.mapping.Namespace r5 = r9.namespaceResolvePrefix(r1)
            gnu.mapping.Symbol r15 = r9.namespaceResolve((gnu.mapping.Namespace) r5, (gnu.expr.Expression) r4)
            if (r15 == 0) goto L_0x0063
            r16 = r15
            r3 = r16
            goto L_0x02d1
        L_0x0063:
            if (r5 == 0) goto L_0x0087
            if (r0 != r14) goto L_0x0087
            gnu.expr.ApplyExp r14 = new gnu.expr.ApplyExp
            gnu.bytecode.ClassType r13 = quoteType
            r17 = r3
            java.lang.String r3 = "makeSymbol"
            gnu.bytecode.Method r3 = r13.getDeclaredMethod((java.lang.String) r3, (int) r12)
            gnu.expr.Expression[] r13 = new gnu.expr.Expression[r12]
            gnu.expr.QuoteExp r18 = gnu.expr.QuoteExp.getInstance(r5)
            r16 = 0
            r13[r16] = r18
            r18 = 1
            r13[r18] = r4
            r14.<init>((gnu.bytecode.Method) r3, (gnu.expr.Expression[]) r13)
            r3 = r14
            goto L_0x02d1
        L_0x0087:
            r17 = r3
            boolean r3 = r1 instanceof gnu.expr.ReferenceExp
            if (r3 == 0) goto L_0x00c4
            boolean r3 = r4 instanceof gnu.expr.QuoteExp
            if (r3 == 0) goto L_0x00c4
            gnu.mapping.Environment r3 = r26.getGlobalEnvironment()
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r14 = r1
            gnu.expr.ReferenceExp r14 = (gnu.expr.ReferenceExp) r14
            java.lang.String r14 = r14.getName()
            java.lang.StringBuilder r13 = r13.append(r14)
            r14 = 58
            java.lang.StringBuilder r13 = r13.append(r14)
            r14 = r4
            gnu.expr.QuoteExp r14 = (gnu.expr.QuoteExp) r14
            java.lang.Object r14 = r14.getValue()
            java.lang.String r14 = r14.toString()
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.String r13 = r13.toString()
            gnu.mapping.Symbol r3 = r3.getSymbol(r13)
            goto L_0x02d1
        L_0x00c4:
            java.lang.String r3 = gnu.kawa.functions.CompileNamedPart.combineName(r1, r4)
            r13 = r3
            if (r3 == 0) goto L_0x00d5
            gnu.mapping.Environment r3 = r26.getGlobalEnvironment()
            gnu.mapping.Symbol r3 = r3.getSymbol(r13)
            goto L_0x02d1
        L_0x00d5:
            java.lang.Object r3 = r9.pushPositionOf(r10)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r12 = "'"
            java.lang.StringBuilder r12 = r14.append(r12)
            java.lang.Object r14 = r2.getCar()
            java.lang.StringBuilder r12 = r12.append(r14)
            java.lang.String r14 = "' is not a valid prefix"
            java.lang.StringBuilder r12 = r12.append(r14)
            java.lang.String r12 = r12.toString()
            r14 = 101(0x65, float:1.42E-43)
            r9.error(r14, r12)
            r9.popPositionOf(r3)
            r3 = r15
            goto L_0x02d1
        L_0x0101:
            r17 = r3
        L_0x0103:
            java.lang.String r1 = "unquote-splicing"
            java.lang.String r2 = "unquote"
            if (r0 >= 0) goto L_0x010b
            goto L_0x0195
        L_0x010b:
            java.lang.Object r3 = r10.getCar()
            java.lang.String r4 = "quasiquote"
            boolean r3 = r9.matches((java.lang.Object) r3, (kawa.lang.SyntaxForm) r8, (java.lang.String) r4)
            if (r3 == 0) goto L_0x011c
            int r0 = r0 + 1
            r12 = r0
            goto L_0x0196
        L_0x011c:
            java.lang.Object r3 = r10.getCar()
            boolean r3 = r9.matches((java.lang.Object) r3, (kawa.lang.SyntaxForm) r8, (java.lang.String) r2)
            java.lang.String r4 = " in quasiquote template"
            java.lang.String r5 = "invalid used of "
            if (r3 == 0) goto L_0x016d
            int r0 = r0 + -1
            java.lang.Object r3 = r10.getCdr()
            boolean r3 = r3 instanceof gnu.lists.Pair
            if (r3 == 0) goto L_0x014f
            java.lang.Object r3 = r10.getCdr()
            gnu.lists.Pair r3 = (gnu.lists.Pair) r3
            r12 = r3
            java.lang.Object r3 = r3.getCdr()
            gnu.lists.LList r13 = gnu.lists.LList.Empty
            if (r3 == r13) goto L_0x0144
            goto L_0x014f
        L_0x0144:
            if (r0 != 0) goto L_0x014d
            gnu.expr.Expression r1 = r9.rewrite_car((gnu.lists.Pair) r12, (kawa.lang.SyntaxForm) r8)
            r3 = r1
            goto L_0x02d1
        L_0x014d:
            r12 = r0
            goto L_0x0196
        L_0x014f:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r1 = r1.append(r5)
            java.lang.Object r2 = r10.getCar()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r1 = r1.toString()
            gnu.expr.Expression r1 = r9.syntaxError(r1)
            return r1
        L_0x016d:
            java.lang.Object r3 = r10.getCar()
            boolean r3 = r9.matches((java.lang.Object) r3, (kawa.lang.SyntaxForm) r8, (java.lang.String) r1)
            if (r3 == 0) goto L_0x0195
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r1 = r1.append(r5)
            java.lang.Object r2 = r10.getCar()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r1 = r1.toString()
            gnu.expr.Expression r1 = r9.syntaxError(r1)
            return r1
        L_0x0195:
            r12 = r0
        L_0x0196:
            r0 = 1
            if (r12 != r0) goto L_0x025d
            java.lang.Object r0 = r10.getCar()
            boolean r0 = r0 instanceof gnu.lists.Pair
            if (r0 == 0) goto L_0x025d
            java.lang.Object r0 = r10.getCar()
            r3 = r24
        L_0x01a7:
            boolean r4 = r0 instanceof kawa.lang.SyntaxForm
            if (r4 == 0) goto L_0x01b3
            r3 = r0
            kawa.lang.SyntaxForm r3 = (kawa.lang.SyntaxForm) r3
            java.lang.Object r0 = r3.getDatum()
            goto L_0x01a7
        L_0x01b3:
            r4 = -1
            boolean r5 = r0 instanceof gnu.lists.Pair
            if (r5 == 0) goto L_0x01d1
            r5 = r0
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            java.lang.Object r5 = r5.getCar()
            boolean r2 = r9.matches((java.lang.Object) r5, (kawa.lang.SyntaxForm) r3, (java.lang.String) r2)
            if (r2 == 0) goto L_0x01c8
            r4 = 0
            r13 = r4
            goto L_0x01d2
        L_0x01c8:
            boolean r1 = r9.matches((java.lang.Object) r5, (kawa.lang.SyntaxForm) r3, (java.lang.String) r1)
            if (r1 == 0) goto L_0x01d1
            r4 = 1
            r13 = r4
            goto L_0x01d2
        L_0x01d1:
            r13 = r4
        L_0x01d2:
            if (r13 < 0) goto L_0x025d
            r1 = r0
            gnu.lists.Pair r1 = (gnu.lists.Pair) r1
            java.lang.Object r0 = r1.getCdr()
            java.util.Vector r1 = new java.util.Vector
            r1.<init>()
            r14 = r1
            r15 = 0
        L_0x01e2:
            boolean r1 = r0 instanceof kawa.lang.SyntaxForm
            if (r1 == 0) goto L_0x01f0
            r1 = r0
            kawa.lang.SyntaxForm r1 = (kawa.lang.SyntaxForm) r1
            java.lang.Object r0 = r1.getDatum()
            r5 = r0
            r4 = r1
            goto L_0x01f2
        L_0x01f0:
            r5 = r0
            r4 = r3
        L_0x01f2:
            gnu.lists.LList r0 = gnu.lists.LList.Empty
            if (r5 != r0) goto L_0x0238
            int r0 = r14.size()
            r1 = 1
            int r3 = r0 + 1
            java.lang.Object r1 = r10.getCdr()
            r2 = 1
            r0 = r21
            r19 = r3
            r3 = r24
            r20 = r4
            r4 = r25
            r8 = r5
            r5 = r26
            java.lang.Object r0 = r0.expand(r1, r2, r3, r4, r5)
            r1 = r19
            r2 = 1
            if (r1 <= r2) goto L_0x0233
            gnu.expr.Expression[] r2 = new gnu.expr.Expression[r1]
            r14.copyInto(r2)
            int r3 = r1 + -1
            gnu.expr.Expression r4 = r6.coerceExpression(r0, r9)
            r2[r3] = r4
            if (r13 != 0) goto L_0x022b
            gnu.bytecode.Method r3 = consXMethod
            goto L_0x022d
        L_0x022b:
            gnu.bytecode.Method r3 = appendMethod
        L_0x022d:
            gnu.expr.ApplyExp r4 = new gnu.expr.ApplyExp
            r4.<init>((gnu.bytecode.Method) r3, (gnu.expr.Expression[]) r2)
            r0 = r4
        L_0x0233:
            r11 = r10
            r3 = r0
            r0 = r12
            goto L_0x02d1
        L_0x0238:
            r20 = r4
            r8 = r5
            boolean r0 = r8 instanceof gnu.lists.Pair
            if (r0 == 0) goto L_0x0256
            r0 = r8
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
            r1 = r20
            gnu.expr.Expression r0 = r9.rewrite_car((gnu.lists.Pair) r0, (kawa.lang.SyntaxForm) r1)
            r14.addElement(r0)
            r0 = r8
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
            java.lang.Object r0 = r0.getCdr()
            r8 = r24
            r3 = r1
            goto L_0x01e2
        L_0x0256:
            java.lang.String r0 = "improper list argument to unquote"
            gnu.expr.Expression r0 = r9.syntaxError(r0)
            return r0
        L_0x025d:
            java.lang.Object r1 = r10.getCar()
            r0 = r21
            r2 = r12
            r3 = r24
            r4 = r25
            r5 = r26
            java.lang.Object r8 = r0.expand(r1, r2, r3, r4, r5)
            java.lang.Object r0 = r10.getCar()
            if (r8 != r0) goto L_0x0295
            java.lang.Object r11 = r10.getCdr()
            boolean r0 = r11 instanceof gnu.lists.Pair
            if (r0 == 0) goto L_0x0284
            r10 = r11
            gnu.lists.Pair r10 = (gnu.lists.Pair) r10
            r8 = r24
            r0 = r12
            goto L_0x000d
        L_0x0284:
            r0 = r21
            r1 = r11
            r2 = r12
            r3 = r24
            r4 = r25
            r5 = r26
            java.lang.Object r0 = r0.expand(r1, r2, r3, r4, r5)
            r3 = r0
            r0 = r12
            goto L_0x02d1
        L_0x0295:
            java.lang.Object r1 = r10.getCdr()
            r0 = r21
            r2 = r12
            r3 = r24
            r4 = r25
            r5 = r26
            java.lang.Object r0 = r0.expand(r1, r2, r3, r4, r5)
            boolean r1 = r8 instanceof gnu.expr.Expression
            if (r1 != 0) goto L_0x02b6
            boolean r1 = r0 instanceof gnu.expr.Expression
            if (r1 == 0) goto L_0x02af
            goto L_0x02b6
        L_0x02af:
            gnu.lists.Pair r0 = kawa.lang.Translator.makePair(r10, r8, r0)
            r3 = r0
            r0 = r12
            goto L_0x02d1
        L_0x02b6:
            r1 = 2
            gnu.expr.Expression[] r2 = new gnu.expr.Expression[r1]
            gnu.expr.Expression r1 = r6.coerceExpression(r8, r9)
            r3 = 0
            r2[r3] = r1
            gnu.expr.Expression r1 = r6.coerceExpression(r0, r9)
            r3 = 1
            r2[r3] = r1
            gnu.expr.ApplyExp r1 = new gnu.expr.ApplyExp
            gnu.bytecode.Method r3 = makePairMethod
            r1.<init>((gnu.bytecode.Method) r3, (gnu.expr.Expression[]) r2)
            r0 = r1
            r3 = r0
            r0 = r12
        L_0x02d1:
            if (r7 != r11) goto L_0x02d4
            return r3
        L_0x02d4:
            r1 = r22
            r2 = 20
            gnu.lists.Pair[] r2 = new gnu.lists.Pair[r2]
            r4 = 0
        L_0x02db:
            int r5 = r2.length
            if (r4 < r5) goto L_0x02e7
            int r5 = r4 * 2
            gnu.lists.Pair[] r5 = new gnu.lists.Pair[r5]
            r8 = 0
            java.lang.System.arraycopy(r2, r8, r5, r8, r4)
            r2 = r5
        L_0x02e7:
            int r5 = r4 + 1
            r2[r4] = r1
            java.lang.Object r4 = r1.getCdr()
            if (r4 != r11) goto L_0x033b
            boolean r4 = r3 instanceof gnu.expr.Expression
            if (r4 == 0) goto L_0x02f9
            gnu.lists.LList r4 = gnu.lists.LList.Empty
            goto L_0x02fa
        L_0x02f9:
            r4 = r3
        L_0x02fa:
            int r5 = r5 + -1
            if (r5 < 0) goto L_0x0309
            r1 = r2[r5]
            java.lang.Object r8 = r1.getCar()
            gnu.lists.Pair r4 = kawa.lang.Translator.makePair(r1, r8, r4)
            goto L_0x02fa
        L_0x0309:
            boolean r8 = r3 instanceof gnu.expr.Expression
            if (r8 == 0) goto L_0x033a
            r8 = 2
            gnu.expr.Expression[] r8 = new gnu.expr.Expression[r8]
            r12 = r3
            gnu.expr.Expression r12 = (gnu.expr.Expression) r12
            r13 = 1
            r8[r13] = r12
            if (r5 != r13) goto L_0x032b
            java.lang.Object r12 = r22.getCar()
            gnu.expr.Expression r12 = r6.leaf(r12, r9)
            r14 = 0
            r8[r14] = r12
            gnu.expr.ApplyExp r12 = new gnu.expr.ApplyExp
            gnu.bytecode.Method r13 = makePairMethod
            r12.<init>((gnu.bytecode.Method) r13, (gnu.expr.Expression[]) r8)
            return r12
        L_0x032b:
            r14 = 0
            gnu.expr.Expression r12 = r6.leaf(r4, r9)
            r8[r14] = r12
            gnu.expr.ApplyExp r12 = new gnu.expr.ApplyExp
            gnu.bytecode.Method r13 = appendMethod
            r12.<init>((gnu.bytecode.Method) r13, (gnu.expr.Expression[]) r8)
            return r12
        L_0x033a:
            return r4
        L_0x033b:
            r8 = 2
            r13 = 1
            r14 = 0
            java.lang.Object r4 = r1.getCdr()
            r1 = r4
            gnu.lists.Pair r1 = (gnu.lists.Pair) r1
            r4 = r5
            goto L_0x02db
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Quote.expand_pair(gnu.lists.Pair, int, kawa.lang.SyntaxForm, java.lang.Object, kawa.lang.Translator):java.lang.Object");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0110  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0114  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object expand(java.lang.Object r22, int r23, kawa.lang.SyntaxForm r24, java.lang.Object r25, kawa.lang.Translator r26) {
        /*
            r21 = this;
            r6 = r21
            r7 = r22
            r8 = r24
            r9 = r26
            r10 = r25
            java.util.IdentityHashMap r10 = (java.util.IdentityHashMap) r10
            java.lang.Object r11 = r10.get(r7)
            java.lang.Object r0 = WORKING
            if (r11 != r0) goto L_0x001a
            java.lang.Object r0 = CYCLE
            r10.put(r7, r0)
            return r11
        L_0x001a:
            java.lang.Object r0 = CYCLE
            if (r11 != r0) goto L_0x001f
            return r11
        L_0x001f:
            if (r11 == 0) goto L_0x0022
            return r11
        L_0x0022:
            boolean r0 = r7 instanceof gnu.lists.Pair
            if (r0 == 0) goto L_0x003b
            r1 = r7
            gnu.lists.Pair r1 = (gnu.lists.Pair) r1
            r0 = r21
            r2 = r23
            r3 = r24
            r4 = r25
            r5 = r26
            java.lang.Object r0 = r0.expand_pair(r1, r2, r3, r4, r5)
            r18 = r11
            goto L_0x0192
        L_0x003b:
            boolean r0 = r7 instanceof kawa.lang.SyntaxForm
            if (r0 == 0) goto L_0x0057
            r8 = r7
            kawa.lang.SyntaxForm r8 = (kawa.lang.SyntaxForm) r8
            java.lang.Object r1 = r8.getDatum()
            r0 = r21
            r2 = r23
            r3 = r8
            r4 = r25
            r5 = r26
            java.lang.Object r0 = r0.expand(r1, r2, r3, r4, r5)
            r18 = r11
            goto L_0x0192
        L_0x0057:
            boolean r0 = r7 instanceof gnu.lists.FVector
            if (r0 == 0) goto L_0x018c
            r12 = r7
            gnu.lists.FVector r12 = (gnu.lists.FVector) r12
            int r13 = r12.size()
            java.lang.Object[] r14 = new java.lang.Object[r13]
            byte[] r15 = new byte[r13]
            r0 = 0
            r1 = 0
            r5 = r0
            r4 = r1
        L_0x006a:
            r16 = 0
            if (r4 >= r13) goto L_0x011d
            java.lang.Object r1 = r12.get(r4)
            r17 = r23
            boolean r2 = r1 instanceof gnu.lists.Pair
            if (r2 == 0) goto L_0x00dd
            r2 = -1
            r0 = r23
            if (r0 <= r2) goto L_0x00dd
            r2 = r1
            gnu.lists.Pair r2 = (gnu.lists.Pair) r2
            r18 = r2
            java.lang.Object r2 = r2.getCar()
            java.lang.String r3 = "unquote-splicing"
            boolean r2 = r9.matches((java.lang.Object) r2, (kawa.lang.SyntaxForm) r8, (java.lang.String) r3)
            if (r2 == 0) goto L_0x00dd
            int r17 = r17 + -1
            if (r17 != 0) goto L_0x00dd
            java.lang.Object r2 = r18.getCdr()
            boolean r2 = r2 instanceof gnu.lists.Pair
            if (r2 == 0) goto L_0x00bb
            java.lang.Object r2 = r18.getCdr()
            gnu.lists.Pair r2 = (gnu.lists.Pair) r2
            r3 = r2
            java.lang.Object r2 = r2.getCdr()
            gnu.lists.LList r0 = gnu.lists.LList.Empty
            if (r2 == r0) goto L_0x00aa
            goto L_0x00bb
        L_0x00aa:
            gnu.expr.Expression r0 = r9.rewrite_car((gnu.lists.Pair) r3, (kawa.lang.SyntaxForm) r8)
            r14[r4] = r0
            r0 = 3
            r15[r4] = r0
            r8 = r1
            r19 = r4
            r20 = r5
            r18 = r11
            goto L_0x010a
        L_0x00bb:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "invalid used of "
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.Object r2 = r18.getCar()
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r2 = " in quasiquote template"
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            gnu.expr.Expression r0 = r9.syntaxError(r0)
            return r0
        L_0x00dd:
            r0 = r21
            r3 = r1
            r8 = 1
            r2 = r17
            r8 = r3
            r18 = r11
            r11 = 2
            r3 = r24
            r19 = r4
            r4 = r25
            r20 = r5
            r5 = r26
            java.lang.Object r0 = r0.expand(r1, r2, r3, r4, r5)
            r14[r19] = r0
            r0 = r14[r19]
            if (r0 != r8) goto L_0x00fe
            r15[r19] = r16
            goto L_0x010a
        L_0x00fe:
            r0 = r14[r19]
            boolean r0 = r0 instanceof gnu.expr.Expression
            if (r0 == 0) goto L_0x0107
            r15[r19] = r11
            goto L_0x010a
        L_0x0107:
            r0 = 1
            r15[r19] = r0
        L_0x010a:
            byte r0 = r15[r19]
            r1 = r20
            if (r0 <= r1) goto L_0x0114
            byte r0 = r15[r19]
            r5 = r0
            goto L_0x0115
        L_0x0114:
            r5 = r1
        L_0x0115:
            int r4 = r19 + 1
            r8 = r24
            r11 = r18
            goto L_0x006a
        L_0x011d:
            r19 = r4
            r1 = r5
            r18 = r11
            r11 = 2
            if (r1 != 0) goto L_0x0127
            r0 = r12
            goto L_0x0189
        L_0x0127:
            r0 = 1
            if (r1 != r0) goto L_0x0130
            gnu.lists.FVector r0 = new gnu.lists.FVector
            r0.<init>((java.lang.Object[]) r14)
            goto L_0x0189
        L_0x0130:
            gnu.expr.Expression[] r0 = new gnu.expr.Expression[r13]
            r2 = 0
        L_0x0133:
            if (r2 >= r13) goto L_0x0178
            byte r3 = r15[r2]
            r4 = 3
            if (r3 != r4) goto L_0x0142
            r3 = r14[r2]
            gnu.expr.Expression r3 = (gnu.expr.Expression) r3
            r0[r2] = r3
            r3 = 1
            goto L_0x0175
        L_0x0142:
            if (r1 >= r4) goto L_0x014e
            r3 = r14[r2]
            gnu.expr.Expression r3 = r6.coerceExpression(r3, r9)
            r0[r2] = r3
            r3 = 1
            goto L_0x0175
        L_0x014e:
            byte r3 = r15[r2]
            if (r3 >= r11) goto L_0x0166
            r3 = 1
            java.lang.Object[] r4 = new java.lang.Object[r3]
            r3 = r14[r2]
            r4[r16] = r3
            gnu.lists.FVector r3 = new gnu.lists.FVector
            r3.<init>((java.lang.Object[]) r4)
            gnu.expr.Expression r3 = r6.leaf(r3, r9)
            r0[r2] = r3
            r3 = 1
            goto L_0x0175
        L_0x0166:
            r3 = 1
            gnu.expr.Expression[] r4 = new gnu.expr.Expression[r3]
            r5 = r14[r2]
            gnu.expr.Expression r5 = (gnu.expr.Expression) r5
            r4[r16] = r5
            gnu.expr.ApplyExp r5 = makeInvokeMakeVector(r4)
            r0[r2] = r5
        L_0x0175:
            int r2 = r2 + 1
            goto L_0x0133
        L_0x0178:
            r2 = 3
            if (r1 >= r2) goto L_0x0181
            gnu.expr.ApplyExp r2 = makeInvokeMakeVector(r0)
            r0 = r2
            goto L_0x0189
        L_0x0181:
            gnu.expr.ApplyExp r2 = new gnu.expr.ApplyExp
            gnu.bytecode.Method r3 = vectorAppendMethod
            r2.<init>((gnu.bytecode.Method) r3, (gnu.expr.Expression[]) r0)
            r0 = r2
        L_0x0189:
            r8 = r24
            goto L_0x0192
        L_0x018c:
            r18 = r11
            r0 = r22
            r8 = r24
        L_0x0192:
            if (r7 == r0) goto L_0x01a3
            java.lang.Object r1 = r10.get(r7)
            java.lang.Object r2 = CYCLE
            if (r1 != r2) goto L_0x01a3
            r1 = 101(0x65, float:1.42E-43)
            java.lang.String r2 = "cycle in non-literal data"
            r9.error(r1, r2)
        L_0x01a3:
            r10.put(r7, r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Quote.expand(java.lang.Object, int, kawa.lang.SyntaxForm, java.lang.Object, kawa.lang.Translator):java.lang.Object");
    }

    private static ApplyExp makeInvokeMakeVector(Expression[] args) {
        return new ApplyExp(makeVectorMethod, args);
    }

    public Expression rewrite(Object obj, Translator tr) {
        if (obj instanceof Pair) {
            Pair pair = (Pair) obj;
            Pair pair2 = pair;
            if (pair.getCdr() == LList.Empty) {
                return coerceExpression(expand(pair2.getCar(), this.isQuasi ? 1 : -1, tr), tr);
            }
        }
        return tr.syntaxError("wrong number of arguments to quote");
    }

    public static Object consX$V(Object[] args) {
        return LList.consX(args);
    }

    public static Object append$V(Object[] args) {
        int count = args.length;
        if (count == 0) {
            return LList.Empty;
        }
        Object result = args[count - 1];
        int i = count - 1;
        while (true) {
            i--;
            if (i < 0) {
                return result;
            }
            Object list = args[i];
            Object copy = null;
            Pair last = null;
            SyntaxForm syntax = null;
            while (true) {
                if (list instanceof SyntaxForm) {
                    syntax = (SyntaxForm) list;
                    list = syntax.getDatum();
                } else if (list == LList.Empty) {
                    break;
                } else {
                    Pair list_pair = (Pair) list;
                    Object car = list_pair.getCar();
                    if (syntax != null && !(car instanceof SyntaxForm)) {
                        car = SyntaxForms.makeForm(car, syntax.getScope());
                    }
                    Object new_pair = new Pair(car, (Object) null);
                    if (last == null) {
                        copy = new_pair;
                    } else {
                        last.setCdr(new_pair);
                    }
                    last = new_pair;
                    list = list_pair.getCdr();
                }
            }
            if (last != null) {
                last.setCdr(result);
                result = copy;
            }
        }
    }
}
