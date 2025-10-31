package kawa.lang;

import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.lists.Consumer;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;

public class Lambda extends Syntax {
    public static final Keyword nameKeyword = Keyword.make("name");
    public Expression defaultDefault = QuoteExp.falseExp;
    public Object keyKeyword;
    public Object optionalKeyword;
    public Object restKeyword;

    public void setKeywords(Object optional, Object rest, Object key) {
        this.optionalKeyword = optional;
        this.restKeyword = rest;
        this.keyKeyword = key;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        Expression exp = rewrite(form.getCdr(), tr);
        Translator.setLine(exp, (Object) form);
        return exp;
    }

    public Expression rewrite(Object obj, Translator tr) {
        if (!(obj instanceof Pair)) {
            return tr.syntaxError("missing formals in lambda");
        }
        int old_errors = tr.getMessages().getErrorCount();
        LambdaExp lexp = new LambdaExp();
        Pair pair = (Pair) obj;
        Translator.setLine((Expression) lexp, (Object) pair);
        rewrite(lexp, pair.getCar(), pair.getCdr(), tr, (TemplateScope) null);
        if (tr.getMessages().getErrorCount() > old_errors) {
            return new ErrorExp("bad lambda expression");
        }
        return lexp;
    }

    public void rewrite(LambdaExp lexp, Object formals, Object body, Translator tr, TemplateScope templateScopeRest) {
        rewriteFormals(lexp, formals, tr, templateScopeRest);
        if (body instanceof PairWithPosition) {
            lexp.setFile(((PairWithPosition) body).getFileName());
        }
        rewriteBody(lexp, rewriteAttrs(lexp, body, tr), tr);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v13, resolved type: gnu.mapping.Symbol} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v7, resolved type: gnu.mapping.Symbol} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v8, resolved type: gnu.mapping.Symbol} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v15, resolved type: gnu.mapping.Symbol} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v20, resolved type: gnu.mapping.Symbol} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v23, resolved type: gnu.mapping.Symbol} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v24, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v38, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v34, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v40, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v27, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v49, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v31, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v63, resolved type: gnu.lists.Pair} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void rewriteFormals(gnu.expr.LambdaExp r24, java.lang.Object r25, kawa.lang.Translator r26, kawa.lang.TemplateScope r27) {
        /*
            r23 = this;
            r0 = r23
            r1 = r24
            r2 = r26
            java.lang.Object r3 = r24.getSymbol()
            if (r3 != 0) goto L_0x001b
            java.lang.String r3 = r24.getFileName()
            int r4 = r24.getLineNumber()
            if (r3 == 0) goto L_0x001b
            if (r4 <= 0) goto L_0x001b
            r1.setSourceLocation(r3, r4)
        L_0x001b:
            r3 = r25
            r4 = -1
            r5 = -1
            r6 = -1
        L_0x0020:
            boolean r7 = r3 instanceof kawa.lang.SyntaxForm
            if (r7 == 0) goto L_0x002b
            r7 = r3
            kawa.lang.SyntaxForm r7 = (kawa.lang.SyntaxForm) r7
            java.lang.Object r3 = r7.getDatum()
        L_0x002b:
            boolean r7 = r3 instanceof gnu.lists.Pair
            java.lang.String r8 = "::"
            java.lang.String r9 = "multiple "
            r10 = 1
            if (r7 != 0) goto L_0x03c8
            boolean r7 = r3 instanceof gnu.mapping.Symbol
            if (r7 == 0) goto L_0x0073
            if (r4 >= 0) goto L_0x0042
            if (r6 >= 0) goto L_0x0042
            if (r5 < 0) goto L_0x0040
            goto L_0x0042
        L_0x0040:
            r5 = 1
            goto L_0x007d
        L_0x0042:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "dotted rest-arg after "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.Object r8 = r0.optionalKeyword
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = ", "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.Object r8 = r0.restKeyword
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = ", or "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.Object r8 = r0.keyKeyword
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            r2.syntaxError(r7)
            return
        L_0x0073:
            gnu.lists.LList r7 = gnu.lists.LList.Empty
            if (r3 == r7) goto L_0x007d
            java.lang.String r7 = "misformed formals in lambda"
            r2.syntaxError(r7)
            return
        L_0x007d:
            if (r5 <= r10) goto L_0x009c
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.StringBuilder r7 = r7.append(r9)
            java.lang.Object r8 = r0.restKeyword
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = " parameters"
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            r2.syntaxError(r7)
            return
        L_0x009c:
            if (r4 >= 0) goto L_0x009f
            r4 = 0
        L_0x009f:
            if (r5 >= 0) goto L_0x00a4
            r5 = 0
            r7 = r5
            goto L_0x00a5
        L_0x00a4:
            r7 = r5
        L_0x00a5:
            if (r6 >= 0) goto L_0x00a8
            r6 = 0
        L_0x00a8:
            if (r7 <= 0) goto L_0x00ae
            r5 = -1
            r1.max_args = r5
            goto L_0x00b6
        L_0x00ae:
            int r5 = r1.min_args
            int r5 = r5 + r4
            int r9 = r6 * 2
            int r5 = r5 + r9
            r1.max_args = r5
        L_0x00b6:
            int r5 = r4 + r6
            if (r5 <= 0) goto L_0x00c0
            int r5 = r4 + r6
            gnu.expr.Expression[] r5 = new gnu.expr.Expression[r5]
            r1.defaultArgs = r5
        L_0x00c0:
            if (r6 <= 0) goto L_0x00c6
            gnu.expr.Keyword[] r5 = new gnu.expr.Keyword[r6]
            r1.keywords = r5
        L_0x00c6:
            r3 = r25
            r4 = 0
            r5 = 0
            r6 = 0
            r9 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r27
        L_0x00d1:
            boolean r10 = r4 instanceof kawa.lang.SyntaxForm
            if (r10 == 0) goto L_0x00e0
            r10 = r4
            kawa.lang.SyntaxForm r10 = (kawa.lang.SyntaxForm) r10
            java.lang.Object r4 = r10.getDatum()
            kawa.lang.TemplateScope r3 = r10.getScope()
        L_0x00e0:
            r10 = r3
            boolean r11 = r4 instanceof gnu.lists.Pair
            r12 = 0
            r13 = 262144(0x40000, double:1.295163E-318)
            if (r11 != 0) goto L_0x0111
            boolean r8 = r4 instanceof kawa.lang.SyntaxForm
            if (r8 == 0) goto L_0x00f9
            r8 = r4
            kawa.lang.SyntaxForm r8 = (kawa.lang.SyntaxForm) r8
            java.lang.Object r4 = r8.getDatum()
            kawa.lang.TemplateScope r3 = r8.getScope()
        L_0x00f9:
            boolean r8 = r4 instanceof gnu.mapping.Symbol
            if (r8 == 0) goto L_0x0110
            gnu.expr.Declaration r8 = new gnu.expr.Declaration
            r8.<init>((java.lang.Object) r4)
            gnu.kawa.lispexpr.LangObjType r10 = gnu.kawa.lispexpr.LangObjType.listType
            r8.setType(r10)
            r8.setFlag(r13)
            r8.noteValue(r12)
            addParam(r8, r3, r1, r2)
        L_0x0110:
            return
        L_0x0111:
            r11 = r4
            gnu.lists.Pair r11 = (gnu.lists.Pair) r11
            java.lang.Object r15 = r11.getCar()
            boolean r12 = r15 instanceof kawa.lang.SyntaxForm
            if (r12 == 0) goto L_0x0127
            r12 = r15
            kawa.lang.SyntaxForm r12 = (kawa.lang.SyntaxForm) r12
            java.lang.Object r15 = r12.getDatum()
            kawa.lang.TemplateScope r10 = r12.getScope()
        L_0x0127:
            java.lang.Object r12 = r0.optionalKeyword
            if (r15 == r12) goto L_0x03b1
            java.lang.Object r12 = r0.restKeyword
            if (r15 == r12) goto L_0x03b1
            java.lang.Object r12 = r0.keyKeyword
            if (r15 != r12) goto L_0x013e
            r17 = r3
            r18 = r7
            r19 = r10
            r21 = r11
            r7 = r4
            goto L_0x03ba
        L_0x013e:
            java.lang.Object r12 = r2.pushPositionOf(r11)
            r16 = 0
            gnu.expr.Expression r13 = r0.defaultDefault
            r14 = 0
            boolean r17 = r2.matches(r15, r8)
            if (r17 == 0) goto L_0x0153
            java.lang.String r8 = "'::' must follow parameter name"
            r2.syntaxError(r8)
            return
        L_0x0153:
            java.lang.Object r15 = r2.namespaceResolve(r15)
            r17 = r3
            boolean r3 = r15 instanceof gnu.mapping.Symbol
            r18 = r7
            java.lang.String r7 = "'::' not followed by a type specifier (for parameter '"
            r19 = r10
            java.lang.String r10 = "')"
            if (r3 == 0) goto L_0x01b8
            r3 = r15
            r20 = r13
            java.lang.Object r13 = r11.getCdr()
            boolean r13 = r13 instanceof gnu.lists.Pair
            if (r13 == 0) goto L_0x01b2
            java.lang.Object r13 = r11.getCdr()
            gnu.lists.Pair r13 = (gnu.lists.Pair) r13
            r16 = r13
            java.lang.Object r13 = r13.getCar()
            boolean r13 = r2.matches(r13, r8)
            if (r13 == 0) goto L_0x01b2
            java.lang.Object r13 = r11.getCdr()
            boolean r13 = r13 instanceof gnu.lists.Pair
            if (r13 != 0) goto L_0x01a3
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r7 = r8.append(r7)
            java.lang.StringBuilder r7 = r7.append(r3)
            java.lang.StringBuilder r7 = r7.append(r10)
            java.lang.String r7 = r7.toString()
            r2.syntaxError(r7)
            return
        L_0x01a3:
            java.lang.Object r7 = r16.getCdr()
            gnu.lists.Pair r7 = (gnu.lists.Pair) r7
            r14 = r7
            r10 = r7
            r11 = r10
            r10 = r19
            r13 = r20
            goto L_0x031f
        L_0x01b2:
            r10 = r19
            r13 = r20
            goto L_0x031f
        L_0x01b8:
            r20 = r13
            boolean r3 = r15 instanceof gnu.lists.Pair
            if (r3 == 0) goto L_0x0315
            r3 = r15
            gnu.lists.Pair r3 = (gnu.lists.Pair) r3
            java.lang.Object r13 = r3.getCar()
            boolean r15 = r13 instanceof kawa.lang.SyntaxForm
            if (r15 == 0) goto L_0x01d4
            r15 = r13
            kawa.lang.SyntaxForm r15 = (kawa.lang.SyntaxForm) r15
            java.lang.Object r13 = r15.getDatum()
            kawa.lang.TemplateScope r19 = r15.getScope()
        L_0x01d4:
            java.lang.Object r15 = r2.namespaceResolve(r13)
            boolean r13 = r15 instanceof gnu.mapping.Symbol
            if (r13 == 0) goto L_0x0306
            java.lang.Object r13 = r3.getCdr()
            boolean r13 = r13 instanceof gnu.lists.Pair
            if (r13 == 0) goto L_0x0306
            r13 = r15
            java.lang.Object r16 = r3.getCdr()
            r3 = r16
            gnu.lists.Pair r3 = (gnu.lists.Pair) r3
            r21 = r11
            java.lang.Object r11 = r3.getCar()
            boolean r11 = r2.matches(r11, r8)
            r22 = r14
            java.lang.String r14 = "improper list in specifier for parameter '"
            if (r11 == 0) goto L_0x025b
            java.lang.Object r11 = r3.getCdr()
            boolean r11 = r11 instanceof gnu.lists.Pair
            if (r11 != 0) goto L_0x021e
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r7 = r8.append(r7)
            java.lang.StringBuilder r7 = r7.append(r13)
            java.lang.StringBuilder r7 = r7.append(r10)
            java.lang.String r7 = r7.toString()
            r2.syntaxError(r7)
            return
        L_0x021e:
            java.lang.Object r7 = r3.getCdr()
            r3 = r7
            gnu.lists.Pair r3 = (gnu.lists.Pair) r3
            r7 = r3
            java.lang.Object r11 = r3.getCdr()
            boolean r11 = r11 instanceof gnu.lists.Pair
            if (r11 == 0) goto L_0x0236
            java.lang.Object r11 = r3.getCdr()
            r3 = r11
            gnu.lists.Pair r3 = (gnu.lists.Pair) r3
            goto L_0x025d
        L_0x0236:
            java.lang.Object r11 = r3.getCdr()
            r16 = r3
            gnu.lists.LList r3 = gnu.lists.LList.Empty
            if (r11 != r3) goto L_0x0242
            r3 = 0
            goto L_0x025d
        L_0x0242:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r3 = r3.append(r14)
            java.lang.StringBuilder r3 = r3.append(r13)
            java.lang.StringBuilder r3 = r3.append(r10)
            java.lang.String r3 = r3.toString()
            r2.syntaxError(r3)
            return
        L_0x025b:
            r7 = r22
        L_0x025d:
            if (r3 == 0) goto L_0x029c
            if (r9 == 0) goto L_0x029c
            java.lang.Object r11 = r3.getCar()
            r16 = r11
            java.lang.Object r11 = r3.getCdr()
            boolean r11 = r11 instanceof gnu.lists.Pair
            if (r11 == 0) goto L_0x0277
            java.lang.Object r10 = r3.getCdr()
            r3 = r10
            gnu.lists.Pair r3 = (gnu.lists.Pair) r3
            goto L_0x02a2
        L_0x0277:
            java.lang.Object r11 = r3.getCdr()
            r22 = r3
            gnu.lists.LList r3 = gnu.lists.LList.Empty
            if (r11 != r3) goto L_0x0283
            r3 = 0
            goto L_0x02a2
        L_0x0283:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r3 = r3.append(r14)
            java.lang.StringBuilder r3 = r3.append(r13)
            java.lang.StringBuilder r3 = r3.append(r10)
            java.lang.String r3 = r3.toString()
            r2.syntaxError(r3)
            return
        L_0x029c:
            r22 = r3
            r16 = r20
            r3 = r22
        L_0x02a2:
            if (r3 == 0) goto L_0x02fd
            r10 = 39
            if (r7 == 0) goto L_0x02c3
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r11 = "duplicate type specifier for parameter '"
            java.lang.StringBuilder r8 = r8.append(r11)
            java.lang.StringBuilder r8 = r8.append(r13)
            java.lang.StringBuilder r8 = r8.append(r10)
            java.lang.String r8 = r8.toString()
            r2.syntaxError(r8)
            return
        L_0x02c3:
            r14 = r3
            java.lang.Object r7 = r3.getCdr()
            gnu.lists.LList r11 = gnu.lists.LList.Empty
            if (r7 == r11) goto L_0x02f5
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "junk at end of specifier for parameter '"
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r13)
            java.lang.StringBuilder r7 = r7.append(r10)
            java.lang.String r8 = " after type "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.Object r8 = r3.getCar()
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            r2.syntaxError(r7)
            return
        L_0x02f5:
            r3 = r13
            r13 = r16
            r10 = r19
            r11 = r21
            goto L_0x031f
        L_0x02fd:
            r14 = r7
            r3 = r13
            r13 = r16
            r10 = r19
            r11 = r21
            goto L_0x031f
        L_0x0306:
            r21 = r11
            r22 = r14
            r3 = r16
            r10 = r19
            r13 = r20
            r11 = r21
            r14 = r22
            goto L_0x031f
        L_0x0315:
            r21 = r11
            r22 = r14
            r3 = r16
            r10 = r19
            r13 = r20
        L_0x031f:
            if (r3 != 0) goto L_0x0338
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "parameter is neither name nor (name :: type) nor (name default): "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r11)
            java.lang.String r7 = r7.toString()
            r2.syntaxError(r7)
            return
        L_0x0338:
            java.lang.Object r7 = r0.optionalKeyword
            if (r9 == r7) goto L_0x0344
            java.lang.Object r7 = r0.keyKeyword
            if (r9 != r7) goto L_0x0341
            goto L_0x0344
        L_0x0341:
            r19 = r11
            goto L_0x0353
        L_0x0344:
            gnu.expr.Expression[] r7 = r1.defaultArgs
            int r16 = r5 + 1
            r19 = r11
            gnu.expr.LangExp r11 = new gnu.expr.LangExp
            r11.<init>(r13)
            r7[r5] = r11
            r5 = r16
        L_0x0353:
            java.lang.Object r7 = r0.keyKeyword
            if (r9 != r7) goto L_0x0375
            gnu.expr.Keyword[] r7 = r1.keywords
            int r11 = r6 + 1
            r16 = r5
            boolean r5 = r3 instanceof gnu.mapping.Symbol
            if (r5 == 0) goto L_0x0369
            r5 = r3
            gnu.mapping.Symbol r5 = (gnu.mapping.Symbol) r5
            java.lang.String r5 = r5.getName()
            goto L_0x036d
        L_0x0369:
            java.lang.String r5 = r3.toString()
        L_0x036d:
            gnu.expr.Keyword r5 = gnu.expr.Keyword.make(r5)
            r7[r6] = r5
            r6 = r11
            goto L_0x0377
        L_0x0375:
            r16 = r5
        L_0x0377:
            gnu.expr.Declaration r5 = new gnu.expr.Declaration
            r5.<init>((java.lang.Object) r3)
            kawa.lang.Translator.setLine((gnu.expr.Declaration) r5, (java.lang.Object) r4)
            if (r14 == 0) goto L_0x0391
            gnu.expr.LangExp r7 = new gnu.expr.LangExp
            r7.<init>(r14)
            r5.setTypeExp(r7)
            r11 = r3
            r7 = r4
            r3 = 8192(0x2000, double:4.0474E-320)
            r5.setFlag(r3)
            goto L_0x039c
        L_0x0391:
            r11 = r3
            r7 = r4
            java.lang.Object r3 = r0.restKeyword
            if (r9 != r3) goto L_0x039c
            gnu.kawa.lispexpr.LangObjType r3 = gnu.kawa.lispexpr.LangObjType.listType
            r5.setType(r3)
        L_0x039c:
            r3 = 262144(0x40000, double:1.295163E-318)
            r5.setFlag(r3)
            r3 = 0
            r5.noteValue(r3)
            addParam(r5, r10, r1, r2)
            r2.popPositionOf(r12)
            r5 = r16
            r11 = r19
            goto L_0x03be
        L_0x03b1:
            r17 = r3
            r18 = r7
            r19 = r10
            r21 = r11
            r7 = r4
        L_0x03ba:
            r3 = r15
            r9 = r3
            r11 = r21
        L_0x03be:
            java.lang.Object r4 = r11.getCdr()
            r3 = r17
            r7 = r18
            goto L_0x00d1
        L_0x03c8:
            r7 = r3
            gnu.lists.Pair r7 = (gnu.lists.Pair) r7
            java.lang.Object r11 = r7.getCar()
            boolean r12 = r11 instanceof kawa.lang.SyntaxForm
            if (r12 == 0) goto L_0x03da
            r12 = r11
            kawa.lang.SyntaxForm r12 = (kawa.lang.SyntaxForm) r12
            java.lang.Object r11 = r12.getDatum()
        L_0x03da:
            java.lang.Object r12 = r0.optionalKeyword
            java.lang.String r13 = " after "
            java.lang.String r14 = " in parameter list"
            if (r11 != r12) goto L_0x0434
            if (r4 < 0) goto L_0x03ff
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.Object r9 = r0.optionalKeyword
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r8 = r8.append(r14)
            java.lang.String r8 = r8.toString()
            r2.syntaxError(r8)
            return
        L_0x03ff:
            if (r5 >= 0) goto L_0x0407
            if (r6 < 0) goto L_0x0404
            goto L_0x0407
        L_0x0404:
            r4 = 0
            goto L_0x04cb
        L_0x0407:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.Object r9 = r0.optionalKeyword
            java.lang.String r9 = r9.toString()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r8 = r8.append(r13)
            java.lang.Object r9 = r0.restKeyword
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = " or "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.Object r9 = r0.keyKeyword
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r2.syntaxError(r8)
            return
        L_0x0434:
            java.lang.Object r12 = r0.restKeyword
            if (r11 != r12) goto L_0x047a
            if (r5 < 0) goto L_0x0455
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.Object r9 = r0.restKeyword
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r8 = r8.append(r14)
            java.lang.String r8 = r8.toString()
            r2.syntaxError(r8)
            return
        L_0x0455:
            if (r6 < 0) goto L_0x0478
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.Object r9 = r0.restKeyword
            java.lang.String r9 = r9.toString()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r8 = r8.append(r13)
            java.lang.Object r9 = r0.keyKeyword
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r2.syntaxError(r8)
            return
        L_0x0478:
            r5 = 0
            goto L_0x04cb
        L_0x047a:
            java.lang.Object r12 = r0.keyKeyword
            if (r11 != r12) goto L_0x049d
            if (r6 < 0) goto L_0x049b
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.Object r9 = r0.keyKeyword
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r8 = r8.append(r14)
            java.lang.String r8 = r8.toString()
            r2.syntaxError(r8)
            return
        L_0x049b:
            r6 = 0
            goto L_0x04cb
        L_0x049d:
            java.lang.Object r9 = r7.getCar()
            boolean r8 = r2.matches(r9, r8)
            if (r8 == 0) goto L_0x04b7
            java.lang.Object r8 = r7.getCdr()
            boolean r8 = r8 instanceof gnu.lists.Pair
            if (r8 == 0) goto L_0x04b7
            java.lang.Object r8 = r7.getCdr()
            r7 = r8
            gnu.lists.Pair r7 = (gnu.lists.Pair) r7
            goto L_0x04cb
        L_0x04b7:
            if (r6 < 0) goto L_0x04bc
            int r6 = r6 + 1
            goto L_0x04cb
        L_0x04bc:
            if (r5 < 0) goto L_0x04c1
            int r5 = r5 + 1
            goto L_0x04cb
        L_0x04c1:
            if (r4 < 0) goto L_0x04c6
            int r4 = r4 + 1
            goto L_0x04cb
        L_0x04c6:
            int r8 = r1.min_args
            int r8 = r8 + r10
            r1.min_args = r8
        L_0x04cb:
            java.lang.Object r3 = r7.getCdr()
            java.lang.Object r3 = r7.getCdr()
            goto L_0x0020
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Lambda.rewriteFormals(gnu.expr.LambdaExp, java.lang.Object, kawa.lang.Translator, kawa.lang.TemplateScope):void");
    }

    private static void addParam(Declaration decl, ScopeExp templateScope, LambdaExp lexp, Translator tr) {
        if (templateScope != null) {
            decl = tr.makeRenamedAlias(decl, templateScope);
        }
        lexp.addDeclaration(decl);
        if (templateScope != null) {
            decl.context = templateScope;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v2, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v4, resolved type: kawa.lang.SyntaxForm} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object rewriteAttrs(gnu.expr.LambdaExp r21, java.lang.Object r22, kawa.lang.Translator r23) {
        /*
            r20 = this;
            r0 = r21
            r1 = r23
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r22
        L_0x0010:
            boolean r8 = r2 instanceof kawa.lang.SyntaxForm
            if (r8 == 0) goto L_0x001c
            r7 = r2
            kawa.lang.SyntaxForm r7 = (kawa.lang.SyntaxForm) r7
            java.lang.Object r2 = r7.getDatum()
            goto L_0x0010
        L_0x001c:
            boolean r8 = r2 instanceof gnu.lists.Pair
            if (r8 != 0) goto L_0x0021
            goto L_0x0051
        L_0x0021:
            r8 = r2
            gnu.lists.Pair r8 = (gnu.lists.Pair) r8
            java.lang.Object r9 = r8.getCar()
            java.lang.Object r9 = kawa.lang.Translator.stripSyntax(r9)
            java.lang.String r10 = "::"
            boolean r10 = r1.matches(r9, r10)
            if (r10 == 0) goto L_0x0036
            r9 = 0
            goto L_0x003b
        L_0x0036:
            boolean r10 = r9 instanceof gnu.expr.Keyword
            if (r10 != 0) goto L_0x003b
            goto L_0x0051
        L_0x003b:
            r10 = r7
            java.lang.Object r11 = r8.getCdr()
        L_0x0040:
            boolean r12 = r11 instanceof kawa.lang.SyntaxForm
            if (r12 == 0) goto L_0x004c
            r10 = r11
            kawa.lang.SyntaxForm r10 = (kawa.lang.SyntaxForm) r10
            java.lang.Object r11 = r10.getDatum()
            goto L_0x0040
        L_0x004c:
            boolean r12 = r11 instanceof gnu.lists.Pair
            if (r12 != 0) goto L_0x0061
        L_0x0051:
            r5 = r5 | r6
            if (r5 == 0) goto L_0x005a
            gnu.expr.Declaration r8 = r0.nameDecl
            long r9 = (long) r5
            r8.setFlag(r9)
        L_0x005a:
            if (r7 == 0) goto L_0x0060
            java.lang.Object r2 = kawa.lang.SyntaxForms.fromDatumIfNeeded(r2, r7)
        L_0x0060:
            return r2
        L_0x0061:
            r12 = r11
            gnu.lists.Pair r12 = (gnu.lists.Pair) r12
            r13 = 101(0x65, float:1.42E-43)
            if (r9 != 0) goto L_0x009f
            boolean r14 = r21.isClassMethod()
            if (r14 == 0) goto L_0x0087
            java.lang.String r14 = "*init*"
            java.lang.String r15 = r21.getName()
            boolean r14 = r14.equals(r15)
            if (r14 == 0) goto L_0x0087
            java.lang.String r14 = "explicit return type for '*init*' method"
            r1.error(r13, r14)
            r16 = r2
            r17 = r5
            r18 = r6
            goto L_0x0271
        L_0x0087:
            gnu.expr.LangExp r13 = new gnu.expr.LangExp
            r14 = 2
            java.lang.Object[] r14 = new java.lang.Object[r14]
            r15 = 0
            r14[r15] = r12
            r15 = 1
            r14[r15] = r10
            r13.<init>(r14)
            r0.body = r13
            r16 = r2
            r17 = r5
            r18 = r6
            goto L_0x0271
        L_0x009f:
            gnu.expr.Keyword r14 = kawa.standard.object.accessKeyword
            java.lang.String r15 = " and "
            if (r9 != r14) goto L_0x0162
            gnu.expr.Expression r14 = r1.rewrite_car((gnu.lists.Pair) r12, (kawa.lang.SyntaxForm) r10)
            boolean r13 = r14 instanceof gnu.expr.QuoteExp
            if (r13 == 0) goto L_0x014f
            r13 = r14
            gnu.expr.QuoteExp r13 = (gnu.expr.QuoteExp) r13
            java.lang.Object r13 = r13.getValue()
            r16 = r13
            boolean r13 = r13 instanceof gnu.mapping.SimpleSymbol
            if (r13 != 0) goto L_0x00c8
            r13 = r16
            r16 = r2
            boolean r2 = r13 instanceof java.lang.CharSequence
            if (r2 != 0) goto L_0x00cc
            r17 = r5
            r18 = r6
            goto L_0x0155
        L_0x00c8:
            r13 = r16
            r16 = r2
        L_0x00cc:
            gnu.expr.Declaration r2 = r0.nameDecl
            if (r2 != 0) goto L_0x00dd
            java.lang.String r2 = "access: not allowed for anonymous function"
            r15 = 101(0x65, float:1.42E-43)
            r1.error(r15, r2)
            r17 = r5
            r18 = r6
            goto L_0x015c
        L_0x00dd:
            java.lang.String r2 = r13.toString()
            r17 = r5
            java.lang.String r5 = "private"
            boolean r5 = r5.equals(r2)
            if (r5 == 0) goto L_0x00f0
            r5 = 16777216(0x1000000, float:2.3509887E-38)
            r18 = r6
            goto L_0x0122
        L_0x00f0:
            java.lang.String r5 = "protected"
            boolean r5 = r5.equals(r2)
            if (r5 == 0) goto L_0x00fd
            r5 = 33554432(0x2000000, float:9.403955E-38)
            r18 = r6
            goto L_0x0122
        L_0x00fd:
            java.lang.String r5 = "public"
            boolean r5 = r5.equals(r2)
            if (r5 == 0) goto L_0x010a
            r5 = 67108864(0x4000000, float:1.5046328E-36)
            r18 = r6
            goto L_0x0122
        L_0x010a:
            java.lang.String r5 = "package"
            boolean r5 = r5.equals(r2)
            if (r5 == 0) goto L_0x0117
            r5 = 134217728(0x8000000, float:3.85186E-34)
            r18 = r6
            goto L_0x0122
        L_0x0117:
            java.lang.String r5 = "unknown access specifier"
            r18 = r6
            r6 = 101(0x65, float:1.42E-43)
            r1.error(r6, r5)
            r5 = r17
        L_0x0122:
            if (r3 == 0) goto L_0x0149
            if (r2 == 0) goto L_0x0149
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r17 = r5
            java.lang.String r5 = "duplicate access specifiers - "
            java.lang.StringBuilder r5 = r6.append(r5)
            java.lang.StringBuilder r5 = r5.append(r3)
            java.lang.StringBuilder r5 = r5.append(r15)
            java.lang.StringBuilder r5 = r5.append(r2)
            java.lang.String r5 = r5.toString()
            r6 = 101(0x65, float:1.42E-43)
            r1.error(r6, r5)
            goto L_0x014b
        L_0x0149:
            r17 = r5
        L_0x014b:
            r3 = r2
            r5 = r17
            goto L_0x015e
        L_0x014f:
            r16 = r2
            r17 = r5
            r18 = r6
        L_0x0155:
            java.lang.String r2 = "access: value not a constant symbol or string"
            r5 = 101(0x65, float:1.42E-43)
            r1.error(r5, r2)
        L_0x015c:
            r5 = r17
        L_0x015e:
            r6 = r18
            goto L_0x0275
        L_0x0162:
            r16 = r2
            r17 = r5
            r18 = r6
            gnu.expr.Keyword r2 = kawa.standard.object.allocationKeyword
            if (r9 != r2) goto L_0x01fb
            gnu.expr.Expression r2 = r1.rewrite_car((gnu.lists.Pair) r12, (kawa.lang.SyntaxForm) r10)
            boolean r5 = r2 instanceof gnu.expr.QuoteExp
            if (r5 == 0) goto L_0x01ec
            r5 = r2
            gnu.expr.QuoteExp r5 = (gnu.expr.QuoteExp) r5
            java.lang.Object r5 = r5.getValue()
            r6 = r5
            boolean r5 = r5 instanceof gnu.mapping.SimpleSymbol
            if (r5 != 0) goto L_0x0188
            boolean r5 = r6 instanceof java.lang.CharSequence
            if (r5 != 0) goto L_0x0188
            r19 = r2
            goto L_0x01ee
        L_0x0188:
            gnu.expr.Declaration r5 = r0.nameDecl
            if (r5 != 0) goto L_0x0194
            java.lang.String r5 = "allocation: not allowed for anonymous function"
            r13 = 101(0x65, float:1.42E-43)
            r1.error(r13, r5)
            goto L_0x01f5
        L_0x0194:
            java.lang.String r5 = r6.toString()
            java.lang.String r13 = "class"
            boolean r13 = r13.equals(r5)
            if (r13 != 0) goto L_0x01be
            java.lang.String r13 = "static"
            boolean r13 = r13.equals(r5)
            if (r13 == 0) goto L_0x01a9
            goto L_0x01be
        L_0x01a9:
            java.lang.String r13 = "instance"
            boolean r13 = r13.equals(r5)
            if (r13 == 0) goto L_0x01b4
            r13 = 4096(0x1000, float:5.74E-42)
            goto L_0x01c0
        L_0x01b4:
            java.lang.String r13 = "unknown allocation specifier"
            r14 = 101(0x65, float:1.42E-43)
            r1.error(r14, r13)
            r13 = r18
            goto L_0x01c0
        L_0x01be:
            r13 = 2048(0x800, float:2.87E-42)
        L_0x01c0:
            if (r4 == 0) goto L_0x01e7
            if (r5 == 0) goto L_0x01e7
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r19 = r2
            java.lang.String r2 = "duplicate allocation specifiers - "
            java.lang.StringBuilder r2 = r14.append(r2)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r2 = r2.append(r15)
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.String r2 = r2.toString()
            r14 = 101(0x65, float:1.42E-43)
            r1.error(r14, r2)
            goto L_0x01e9
        L_0x01e7:
            r19 = r2
        L_0x01e9:
            r4 = r5
            r6 = r13
            goto L_0x01f7
        L_0x01ec:
            r19 = r2
        L_0x01ee:
            java.lang.String r2 = "allocation: value not a constant symbol or string"
            r5 = 101(0x65, float:1.42E-43)
            r1.error(r5, r2)
        L_0x01f5:
            r6 = r18
        L_0x01f7:
            r5 = r17
            goto L_0x0275
        L_0x01fb:
            gnu.expr.Keyword r2 = kawa.standard.object.throwsKeyword
            if (r9 != r2) goto L_0x023e
            java.lang.Object r2 = r12.getCar()
            int r5 = kawa.lang.Translator.listLength(r2)
            if (r5 >= 0) goto L_0x0211
            java.lang.String r6 = "throws: not followed by a list"
            r13 = 101(0x65, float:1.42E-43)
            r1.error(r13, r6)
            goto L_0x023d
        L_0x0211:
            gnu.expr.Expression[] r6 = new gnu.expr.Expression[r5]
            r13 = r10
            r14 = 0
        L_0x0215:
            if (r14 >= r5) goto L_0x023a
        L_0x0217:
            boolean r15 = r2 instanceof kawa.lang.SyntaxForm
            if (r15 == 0) goto L_0x0223
            r13 = r2
            kawa.lang.SyntaxForm r13 = (kawa.lang.SyntaxForm) r13
            java.lang.Object r2 = r13.getDatum()
            goto L_0x0217
        L_0x0223:
            r15 = r2
            gnu.lists.Pair r15 = (gnu.lists.Pair) r15
            gnu.expr.Expression r19 = r1.rewrite_car((gnu.lists.Pair) r15, (kawa.lang.SyntaxForm) r13)
            r6[r14] = r19
            r19 = r2
            r2 = r6[r14]
            kawa.lang.Translator.setLine((gnu.expr.Expression) r2, (java.lang.Object) r15)
            java.lang.Object r2 = r15.getCdr()
            int r14 = r14 + 1
            goto L_0x0215
        L_0x023a:
            r0.setExceptions(r6)
        L_0x023d:
            goto L_0x0271
        L_0x023e:
            gnu.expr.Keyword r2 = nameKeyword
            if (r9 != r2) goto L_0x0259
            gnu.expr.Expression r2 = r1.rewrite_car((gnu.lists.Pair) r12, (kawa.lang.SyntaxForm) r10)
            boolean r5 = r2 instanceof gnu.expr.QuoteExp
            if (r5 == 0) goto L_0x0258
            r5 = r2
            gnu.expr.QuoteExp r5 = (gnu.expr.QuoteExp) r5
            java.lang.Object r5 = r5.getValue()
            java.lang.String r5 = r5.toString()
            r0.setName(r5)
        L_0x0258:
            goto L_0x0271
        L_0x0259:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r5 = "unknown procedure property "
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.StringBuilder r2 = r2.append(r9)
            java.lang.String r2 = r2.toString()
            r5 = 119(0x77, float:1.67E-43)
            r1.error(r5, r2)
        L_0x0271:
            r5 = r17
            r6 = r18
        L_0x0275:
            java.lang.Object r2 = r12.getCdr()
            goto L_0x0010
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Lambda.rewriteAttrs(gnu.expr.LambdaExp, java.lang.Object, kawa.lang.Translator):java.lang.Object");
    }

    public Object skipAttrs(LambdaExp lexp, Object body, Translator tr) {
        while (body instanceof Pair) {
            Pair pair = (Pair) body;
            if (!(pair.getCdr() instanceof Pair)) {
                break;
            }
            Object attrName = pair.getCar();
            if (!tr.matches(attrName, "::")) {
                if (!(attrName instanceof Keyword)) {
                    break;
                }
            }
            body = ((Pair) pair.getCdr()).getCdr();
        }
        return body;
    }

    /* JADX WARNING: Removed duplicated region for block: B:63:0x0169  */
    /* JADX WARNING: Removed duplicated region for block: B:69:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void rewriteBody(gnu.expr.LambdaExp r20, java.lang.Object r21, kawa.lang.Translator r22) {
        /*
            r19 = this;
            r0 = r20
            r1 = r22
            r2 = 0
            gnu.expr.LambdaExp r3 = r1.curMethodLambda
            if (r3 != 0) goto L_0x001b
            gnu.expr.Declaration r3 = r0.nameDecl
            if (r3 == 0) goto L_0x001b
            gnu.expr.ModuleExp r3 = r22.getModule()
            r4 = 131072(0x20000, float:1.83671E-40)
            boolean r3 = r3.getFlag(r4)
            if (r3 == 0) goto L_0x001b
            r1.curMethodLambda = r0
        L_0x001b:
            gnu.expr.ScopeExp r3 = r22.currentScope()
            r1.pushScope(r0)
            r4 = 0
            gnu.expr.Keyword[] r5 = r0.keywords
            r6 = 0
            if (r5 != 0) goto L_0x002a
            r5 = 0
            goto L_0x002d
        L_0x002a:
            gnu.expr.Keyword[] r5 = r0.keywords
            int r5 = r5.length
        L_0x002d:
            gnu.expr.Expression[] r7 = r0.defaultArgs
            if (r7 != 0) goto L_0x0033
            r7 = 0
            goto L_0x0037
        L_0x0033:
            gnu.expr.Expression[] r7 = r0.defaultArgs
            int r7 = r7.length
            int r7 = r7 - r5
        L_0x0037:
            r8 = 0
            r9 = 0
            gnu.expr.Declaration r10 = r20.firstDecl()
        L_0x003d:
            if (r10 == 0) goto L_0x009d
            boolean r11 = r10.isAlias()
            if (r11 == 0) goto L_0x0058
            gnu.expr.ReferenceExp r11 = kawa.lang.Translator.getOriginalRef(r10)
            gnu.expr.Declaration r11 = r11.getBinding()
            r0.replaceFollowing(r4, r11)
            r11.context = r0
            r1.pushRenamedAlias(r10)
            int r2 = r2 + 1
            r10 = r11
        L_0x0058:
            gnu.expr.Expression r11 = r10.getTypeExp()
            boolean r12 = r11 instanceof gnu.expr.LangExp
            if (r12 == 0) goto L_0x0070
            r12 = r11
            gnu.expr.LangExp r12 = (gnu.expr.LangExp) r12
            java.lang.Object r12 = r12.getLangValue()
            gnu.lists.Pair r12 = (gnu.lists.Pair) r12
            gnu.bytecode.Type r13 = r1.exp2Type(r12)
            r10.setType(r13)
        L_0x0070:
            r4 = r10
            int r12 = r0.min_args
            if (r8 < r12) goto L_0x0091
            int r12 = r0.min_args
            int r12 = r12 + r7
            if (r8 < r12) goto L_0x0083
            int r12 = r0.max_args
            if (r12 >= 0) goto L_0x0083
            int r12 = r0.min_args
            int r12 = r12 + r7
            if (r8 == r12) goto L_0x0091
        L_0x0083:
            gnu.expr.Expression[] r12 = r0.defaultArgs
            gnu.expr.Expression[] r13 = r0.defaultArgs
            r13 = r13[r9]
            gnu.expr.Expression r13 = r1.rewrite(r13)
            r12[r9] = r13
            int r9 = r9 + 1
        L_0x0091:
            int r8 = r8 + 1
            gnu.expr.NameLookup r12 = r1.lexical
            r12.push((gnu.expr.Declaration) r10)
            gnu.expr.Declaration r10 = r10.nextDecl()
            goto L_0x003d
        L_0x009d:
            boolean r10 = r20.isClassMethod()
            r11 = 0
            if (r10 == 0) goto L_0x00b8
            gnu.expr.Declaration r10 = r0.nameDecl
            r12 = 2048(0x800, double:1.0118E-320)
            boolean r10 = r10.getFlag(r12)
            if (r10 != 0) goto L_0x00b8
            gnu.expr.Declaration r10 = new gnu.expr.Declaration
            java.lang.String r12 = gnu.expr.ThisExp.THIS_NAME
            r10.<init>((java.lang.Object) r12)
            r0.add(r11, r10)
        L_0x00b8:
            gnu.expr.LambdaExp r10 = r1.curLambda
            r1.curLambda = r0
            gnu.bytecode.Type r12 = r0.returnType
            gnu.expr.Expression r13 = r0.body
            boolean r13 = r13 instanceof gnu.expr.LangExp
            r14 = 1
            if (r13 == 0) goto L_0x00e7
            gnu.expr.Expression r13 = r0.body
            gnu.expr.LangExp r13 = (gnu.expr.LangExp) r13
            java.lang.Object r13 = r13.getLangValue()
            java.lang.Object[] r13 = (java.lang.Object[]) r13
            java.lang.Object[] r13 = (java.lang.Object[]) r13
            r15 = r13[r6]
            gnu.lists.Pair r15 = (gnu.lists.Pair) r15
            r16 = r13[r14]
            r11 = r16
            kawa.lang.SyntaxForm r11 = (kawa.lang.SyntaxForm) r11
            gnu.expr.Expression r11 = r1.rewrite_car((gnu.lists.Pair) r15, (kawa.lang.SyntaxForm) r11)
            gnu.expr.Language r15 = r22.getLanguage()
            gnu.bytecode.Type r12 = r15.getTypeFor((gnu.expr.Expression) r11)
        L_0x00e7:
            r11 = r21
            gnu.expr.Expression r13 = r1.rewrite_body(r11)
            r0.body = r13
            r1.curLambda = r10
            gnu.expr.Expression r13 = r0.body
            boolean r13 = r13 instanceof gnu.expr.BeginExp
            if (r13 == 0) goto L_0x0152
            gnu.expr.Expression r13 = r0.body
            gnu.expr.BeginExp r13 = (gnu.expr.BeginExp) r13
            gnu.expr.Expression[] r13 = r13.getExpressions()
            r15 = r13
            int r13 = r13.length
            r16 = r13
            if (r13 <= r14) goto L_0x014d
            r13 = r15[r6]
            boolean r13 = r13 instanceof gnu.expr.ReferenceExp
            if (r13 != 0) goto L_0x0125
            r13 = r15[r6]
            java.lang.Object r13 = r13.valueIfConstant()
            r17 = r13
            boolean r13 = r13 instanceof gnu.bytecode.Type
            if (r13 != 0) goto L_0x0123
            r13 = r17
            boolean r14 = r13 instanceof java.lang.Class
            if (r14 == 0) goto L_0x011e
            goto L_0x0125
        L_0x011e:
            r17 = r3
            r18 = r4
            goto L_0x0156
        L_0x0123:
            r13 = r17
        L_0x0125:
            r13 = r15[r6]
            int r14 = r16 + -1
            r6 = 1
            if (r14 != r6) goto L_0x0135
            r6 = r15[r6]
            r0.body = r6
            r17 = r3
            r18 = r4
            goto L_0x0145
        L_0x0135:
            r17 = r3
            gnu.expr.Expression[] r3 = new gnu.expr.Expression[r14]
            r18 = r4
            r4 = 0
            java.lang.System.arraycopy(r15, r6, r3, r4, r14)
            gnu.expr.Expression r4 = gnu.expr.BeginExp.canonicalize((gnu.expr.Expression[]) r3)
            r0.body = r4
        L_0x0145:
            gnu.expr.Language r3 = r22.getLanguage()
            r0.setCoercedReturnValue(r13, r3)
            goto L_0x0159
        L_0x014d:
            r17 = r3
            r18 = r4
            goto L_0x0156
        L_0x0152:
            r17 = r3
            r18 = r4
        L_0x0156:
            r0.setCoercedReturnType(r12)
        L_0x0159:
            r1.pop(r0)
            r20.countDecls()
            r1.popRenamedAlias(r2)
            r20.countDecls()
            gnu.expr.LambdaExp r3 = r1.curMethodLambda
            if (r3 != r0) goto L_0x016c
            r3 = 0
            r1.curMethodLambda = r3
        L_0x016c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Lambda.rewriteBody(gnu.expr.LambdaExp, java.lang.Object, kawa.lang.Translator):void");
    }

    public void print(Consumer out) {
        out.write("#<builtin lambda>");
    }
}
