package kawa.standard;

import kawa.lang.Syntax;

public class let extends Syntax {
    public static final let let = new let();

    static {
        let.setName("let");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v0, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v11, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v8, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v36, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v7, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v9, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v14, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v18, resolved type: java.lang.Object} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression rewrite(java.lang.Object r24, kawa.lang.Translator r25) {
        /*
            r23 = this;
            r0 = r24
            r1 = r25
            boolean r2 = r0 instanceof gnu.lists.Pair
            if (r2 != 0) goto L_0x000f
            java.lang.String r2 = "missing let arguments"
            gnu.expr.Expression r2 = r1.syntaxError(r2)
            return r2
        L_0x000f:
            r2 = r0
            gnu.lists.Pair r2 = (gnu.lists.Pair) r2
            java.lang.Object r3 = r2.getCar()
            java.lang.Object r4 = r2.getCdr()
            int r5 = kawa.lang.Translator.listLength(r3)
            if (r5 >= 0) goto L_0x0027
            java.lang.String r6 = "bindings not a proper list"
            gnu.expr.Expression r6 = r1.syntaxError(r6)
            return r6
        L_0x0027:
            gnu.expr.Expression[] r6 = new gnu.expr.Expression[r5]
            gnu.expr.LetExp r7 = new gnu.expr.LetExp
            r7.<init>(r6)
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
        L_0x0032:
            if (r11 >= r5) goto L_0x01fa
        L_0x0034:
            boolean r12 = r3 instanceof kawa.lang.SyntaxForm
            if (r12 == 0) goto L_0x0040
            r10 = r3
            kawa.lang.SyntaxForm r10 = (kawa.lang.SyntaxForm) r10
            java.lang.Object r3 = r10.getDatum()
            goto L_0x0034
        L_0x0040:
            r12 = r3
            gnu.lists.Pair r12 = (gnu.lists.Pair) r12
            java.lang.Object r13 = r12.getCar()
            r14 = r10
            boolean r15 = r13 instanceof kawa.lang.SyntaxForm
            if (r15 == 0) goto L_0x0053
            r14 = r13
            kawa.lang.SyntaxForm r14 = (kawa.lang.SyntaxForm) r14
            java.lang.Object r13 = r14.getDatum()
        L_0x0053:
            boolean r15 = r13 instanceof gnu.lists.Pair
            if (r15 != 0) goto L_0x0071
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r16 = r2
            java.lang.String r2 = "let binding is not a pair:"
            java.lang.StringBuilder r2 = r15.append(r2)
            java.lang.StringBuilder r2 = r2.append(r13)
            java.lang.String r2 = r2.toString()
            gnu.expr.Expression r2 = r1.syntaxError(r2)
            return r2
        L_0x0071:
            r16 = r2
            r2 = r13
            gnu.lists.Pair r2 = (gnu.lists.Pair) r2
            java.lang.Object r15 = r2.getCar()
            r17 = r3
            boolean r3 = r15 instanceof kawa.lang.SyntaxForm
            if (r3 == 0) goto L_0x008c
            r3 = r15
            kawa.lang.SyntaxForm r3 = (kawa.lang.SyntaxForm) r3
            java.lang.Object r15 = r3.getDatum()
            kawa.lang.TemplateScope r3 = r3.getScope()
            goto L_0x0094
        L_0x008c:
            if (r14 != 0) goto L_0x0090
            r3 = 0
            goto L_0x0094
        L_0x0090:
            kawa.lang.TemplateScope r3 = r14.getScope()
        L_0x0094:
            java.lang.Object r15 = r1.namespaceResolve(r15)
            r18 = r5
            boolean r5 = r15 instanceof gnu.mapping.Symbol
            if (r5 != 0) goto L_0x00c2
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r19 = r10
            java.lang.String r10 = "variable "
            java.lang.StringBuilder r5 = r5.append(r10)
            java.lang.StringBuilder r5 = r5.append(r15)
            java.lang.String r10 = " in let binding is not a symbol: "
            java.lang.StringBuilder r5 = r5.append(r10)
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            gnu.expr.Expression r5 = r1.syntaxError(r5)
            return r5
        L_0x00c2:
            r19 = r10
            gnu.expr.Declaration r5 = r7.addDeclaration((java.lang.Object) r15)
            r10 = r13
            r20 = r14
            r13 = 262144(0x40000, double:1.295163E-318)
            r5.setFlag(r13)
            if (r3 == 0) goto L_0x00e4
            gnu.expr.Declaration r13 = r1.makeRenamedAlias(r5, r3)
            if (r8 != 0) goto L_0x00df
            java.util.Stack r14 = new java.util.Stack
            r14.<init>()
            r8 = r14
        L_0x00df:
            r8.push(r13)
            int r9 = r9 + 1
        L_0x00e4:
            java.lang.Object r13 = r2.getCdr()
            r14 = r20
        L_0x00ea:
            boolean r0 = r13 instanceof kawa.lang.SyntaxForm
            if (r0 == 0) goto L_0x00f8
            r14 = r13
            kawa.lang.SyntaxForm r14 = (kawa.lang.SyntaxForm) r14
            java.lang.Object r13 = r14.getDatum()
            r0 = r24
            goto L_0x00ea
        L_0x00f8:
            boolean r0 = r13 instanceof gnu.lists.Pair
            if (r0 != 0) goto L_0x011c
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r20 = r2
            java.lang.String r2 = "let has no value for '"
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.StringBuilder r0 = r0.append(r15)
            java.lang.String r2 = "'"
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x011c:
            r20 = r2
            r0 = r13
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
            java.lang.Object r2 = r0.getCdr()
        L_0x0125:
            boolean r13 = r2 instanceof kawa.lang.SyntaxForm
            if (r13 == 0) goto L_0x0131
            r14 = r2
            kawa.lang.SyntaxForm r14 = (kawa.lang.SyntaxForm) r14
            java.lang.Object r2 = r14.getDatum()
            goto L_0x0125
        L_0x0131:
            java.lang.Object r13 = r0.getCar()
            r20 = r0
            java.lang.String r0 = "::"
            boolean r0 = r1.matches(r13, r0)
            if (r0 == 0) goto L_0x0172
            boolean r0 = r2 instanceof gnu.lists.Pair
            if (r0 == 0) goto L_0x0167
            r0 = r2
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
            r13 = r0
            java.lang.Object r0 = r0.getCdr()
            r21 = r2
            gnu.lists.LList r2 = gnu.lists.LList.Empty
            if (r0 != r2) goto L_0x0153
            r0 = r13
            goto L_0x016b
        L_0x0153:
            java.lang.Object r0 = r13.getCdr()
            r2 = r0
        L_0x0158:
            boolean r0 = r2 instanceof kawa.lang.SyntaxForm
            if (r0 == 0) goto L_0x0165
            r14 = r2
            kawa.lang.SyntaxForm r14 = (kawa.lang.SyntaxForm) r14
            java.lang.Object r2 = r14.getDatum()
            goto L_0x0158
        L_0x0165:
            r0 = r13
            goto L_0x0176
        L_0x0167:
            r21 = r2
            r0 = r20
        L_0x016b:
            java.lang.String r2 = "missing type after '::' in let"
            gnu.expr.Expression r2 = r1.syntaxError(r2)
            return r2
        L_0x0172:
            r21 = r2
            r0 = r20
        L_0x0176:
            gnu.lists.LList r13 = gnu.lists.LList.Empty
            if (r2 != r13) goto L_0x0183
            r13 = r0
            r20 = r9
            r22 = r13
            r13 = r8
            r8 = r22
            goto L_0x0199
        L_0x0183:
            boolean r13 = r2 instanceof gnu.lists.Pair
            if (r13 == 0) goto L_0x01d9
            gnu.bytecode.Type r13 = r1.exp2Type(r0)
            r5.setType(r13)
            r13 = r8
            r20 = r9
            r8 = 8192(0x2000, double:4.0474E-320)
            r5.setFlag(r8)
            r8 = r2
            gnu.lists.Pair r8 = (gnu.lists.Pair) r8
        L_0x0199:
            gnu.expr.Expression r9 = r1.rewrite_car((gnu.lists.Pair) r8, (kawa.lang.SyntaxForm) r14)
            r6[r11] = r9
            java.lang.Object r9 = r8.getCdr()
            r21 = r0
            gnu.lists.LList r0 = gnu.lists.LList.Empty
            if (r9 == r0) goto L_0x01c1
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r9 = "junk after declaration of "
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.StringBuilder r0 = r0.append(r15)
            java.lang.String r0 = r0.toString()
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x01c1:
            r0 = r6[r11]
            r5.noteValue(r0)
            java.lang.Object r3 = r12.getCdr()
            int r11 = r11 + 1
            r0 = r24
            r8 = r13
            r2 = r16
            r5 = r18
            r10 = r19
            r9 = r20
            goto L_0x0032
        L_0x01d9:
            r21 = r0
            r13 = r8
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r8 = "let binding for '"
            java.lang.StringBuilder r0 = r0.append(r8)
            java.lang.StringBuilder r0 = r0.append(r15)
            java.lang.String r8 = "' is improper list"
            java.lang.StringBuilder r0 = r0.append(r8)
            java.lang.String r0 = r0.toString()
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x01fa:
            r16 = r2
            r18 = r5
            r0 = r9
        L_0x01ff:
            int r0 = r0 + -1
            if (r0 < 0) goto L_0x020d
            java.lang.Object r2 = r8.pop()
            gnu.expr.Declaration r2 = (gnu.expr.Declaration) r2
            r1.pushRenamedAlias(r2)
            goto L_0x01ff
        L_0x020d:
            r1.push((gnu.expr.ScopeExp) r7)
            gnu.expr.Expression r0 = r1.rewrite_body(r4)
            r7.body = r0
            r1.pop(r7)
            r1.popRenamedAlias(r9)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.let.rewrite(java.lang.Object, kawa.lang.Translator):gnu.expr.Expression");
    }
}
