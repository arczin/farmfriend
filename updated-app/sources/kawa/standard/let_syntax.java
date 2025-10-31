package kawa.standard;

import gnu.expr.Declaration;
import gnu.expr.LetExp;
import gnu.expr.ScopeExp;
import java.util.Stack;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class let_syntax extends Syntax {
    public static final let_syntax let_syntax = new let_syntax(false, "let-syntax");
    public static final let_syntax letrec_syntax = new let_syntax(true, "letrec-syntax");
    boolean recursive;

    public let_syntax(boolean recursive2, String name) {
        super(name);
        this.recursive = recursive2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v16, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v17, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v18, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v25, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v5, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v29, resolved type: kawa.lang.SyntaxForm} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression rewrite(java.lang.Object r29, kawa.lang.Translator r30) {
        /*
            r28 = this;
            r0 = r28
            r1 = r29
            r2 = r30
            boolean r3 = r1 instanceof gnu.lists.Pair
            if (r3 != 0) goto L_0x0011
            java.lang.String r3 = "missing let-syntax arguments"
            gnu.expr.Expression r3 = r2.syntaxError(r3)
            return r3
        L_0x0011:
            r3 = r1
            gnu.lists.Pair r3 = (gnu.lists.Pair) r3
            java.lang.Object r4 = r3.getCar()
            java.lang.Object r5 = r3.getCdr()
            int r6 = kawa.lang.Translator.listLength(r4)
            if (r6 >= 0) goto L_0x0029
            java.lang.String r7 = "bindings not a proper list"
            gnu.expr.Expression r7 = r2.syntaxError(r7)
            return r7
        L_0x0029:
            r7 = 0
            r8 = 0
            gnu.expr.Expression[] r9 = new gnu.expr.Expression[r6]
            gnu.expr.Declaration[] r10 = new gnu.expr.Declaration[r6]
            kawa.lang.Macro[] r11 = new kawa.lang.Macro[r6]
            gnu.lists.Pair[] r12 = new gnu.lists.Pair[r6]
            kawa.lang.SyntaxForm[] r13 = new kawa.lang.SyntaxForm[r6]
            gnu.expr.LetExp r14 = new gnu.expr.LetExp
            r14.<init>(r9)
            r15 = 0
            r16 = 0
            r1 = r16
        L_0x003f:
            if (r1 >= r6) goto L_0x01a3
        L_0x0041:
            r16 = r3
            boolean r3 = r4 instanceof kawa.lang.SyntaxForm
            if (r3 == 0) goto L_0x0051
            r15 = r4
            kawa.lang.SyntaxForm r15 = (kawa.lang.SyntaxForm) r15
            java.lang.Object r4 = r15.getDatum()
            r3 = r16
            goto L_0x0041
        L_0x0051:
            r3 = r15
            r17 = r4
            gnu.lists.Pair r17 = (gnu.lists.Pair) r17
            r18 = r3
            java.lang.Object r3 = r17.getCar()
            r19 = r4
            boolean r4 = r3 instanceof kawa.lang.SyntaxForm
            if (r4 == 0) goto L_0x006b
            r4 = r3
            kawa.lang.SyntaxForm r4 = (kawa.lang.SyntaxForm) r4
            java.lang.Object r3 = r4.getDatum()
            r18 = r4
        L_0x006b:
            boolean r4 = r3 instanceof gnu.lists.Pair
            if (r4 != 0) goto L_0x008d
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r20 = r15
            java.lang.String r15 = r28.getName()
            java.lang.StringBuilder r4 = r4.append(r15)
            java.lang.String r15 = " binding is not a pair"
            java.lang.StringBuilder r4 = r4.append(r15)
            java.lang.String r4 = r4.toString()
            gnu.expr.Expression r4 = r2.syntaxError(r4)
            return r4
        L_0x008d:
            r20 = r15
            r4 = r3
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4
            java.lang.Object r15 = r4.getCar()
            r21 = r18
        L_0x0098:
            r22 = r3
            boolean r3 = r15 instanceof kawa.lang.SyntaxForm
            if (r3 == 0) goto L_0x00a9
            r21 = r15
            kawa.lang.SyntaxForm r21 = (kawa.lang.SyntaxForm) r21
            java.lang.Object r15 = r21.getDatum()
            r3 = r22
            goto L_0x0098
        L_0x00a9:
            boolean r3 = r15 instanceof java.lang.String
            if (r3 != 0) goto L_0x00d5
            boolean r3 = r15 instanceof gnu.mapping.Symbol
            if (r3 != 0) goto L_0x00d5
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r23 = r5
            java.lang.String r5 = "variable in "
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.String r5 = r28.getName()
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.String r5 = " binding is not a symbol"
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.String r3 = r3.toString()
            gnu.expr.Expression r3 = r2.syntaxError(r3)
            return r3
        L_0x00d5:
            r23 = r5
            java.lang.Object r3 = r4.getCdr()
        L_0x00db:
            boolean r5 = r3 instanceof kawa.lang.SyntaxForm
            if (r5 == 0) goto L_0x00e8
            r18 = r3
            kawa.lang.SyntaxForm r18 = (kawa.lang.SyntaxForm) r18
            java.lang.Object r3 = r18.getDatum()
            goto L_0x00db
        L_0x00e8:
            boolean r5 = r3 instanceof gnu.lists.Pair
            if (r5 != 0) goto L_0x0114
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r24 = r4
            java.lang.String r4 = r28.getName()
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.String r5 = " has no value for '"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r15)
            java.lang.String r5 = "'"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            gnu.expr.Expression r4 = r2.syntaxError(r4)
            return r4
        L_0x0114:
            r24 = r4
            r4 = r3
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4
            java.lang.Object r5 = r4.getCdr()
            r24 = r3
            gnu.lists.LList r3 = gnu.lists.LList.Empty
            if (r5 == r3) goto L_0x0141
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "let binding for '"
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.StringBuilder r3 = r3.append(r15)
            java.lang.String r5 = "' is improper list"
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.String r3 = r3.toString()
            gnu.expr.Expression r3 = r2.syntaxError(r3)
            return r3
        L_0x0141:
            gnu.expr.Declaration r3 = new gnu.expr.Declaration
            r3.<init>((java.lang.Object) r15)
            kawa.lang.Macro r5 = kawa.lang.Macro.make(r3)
            r11[r1] = r5
            r12[r1] = r4
            r13[r1] = r18
            r14.addDeclaration((gnu.expr.Declaration) r3)
            if (r21 != 0) goto L_0x0158
            r25 = 0
            goto L_0x015c
        L_0x0158:
            kawa.lang.TemplateScope r25 = r21.getScope()
        L_0x015c:
            r26 = r25
            r25 = r4
            r4 = r26
            if (r4 == 0) goto L_0x0179
            r26 = r15
            gnu.expr.Declaration r15 = r2.makeRenamedAlias(r3, r4)
            if (r7 != 0) goto L_0x0173
            java.util.Stack r27 = new java.util.Stack
            r27.<init>()
            r7 = r27
        L_0x0173:
            r7.push(r15)
            int r8 = r8 + 1
            goto L_0x017b
        L_0x0179:
            r26 = r15
        L_0x017b:
            if (r18 == 0) goto L_0x0182
            kawa.lang.TemplateScope r15 = r18.getScope()
            goto L_0x018c
        L_0x0182:
            boolean r15 = r0.recursive
            if (r15 == 0) goto L_0x0188
            r15 = r14
            goto L_0x018c
        L_0x0188:
            gnu.expr.ScopeExp r15 = r30.currentScope()
        L_0x018c:
            r5.setCapturedScope(r15)
            r10[r1] = r3
            gnu.expr.QuoteExp r15 = gnu.expr.QuoteExp.nullExp
            r9[r1] = r15
            java.lang.Object r4 = r17.getCdr()
            int r1 = r1 + 1
            r3 = r16
            r15 = r20
            r5 = r23
            goto L_0x003f
        L_0x01a3:
            r16 = r3
            r23 = r5
            boolean r1 = r0.recursive
            if (r1 == 0) goto L_0x01ae
            r0.push(r14, r2, r7)
        L_0x01ae:
            kawa.lang.Macro r1 = r2.currentMacroDefinition
            r3 = 0
        L_0x01b1:
            if (r3 >= r6) goto L_0x01f1
            r5 = r11[r3]
            r2.currentMacroDefinition = r5
            r17 = r4
            r4 = r12[r3]
            r18 = r6
            r6 = r13[r3]
            gnu.expr.Expression r4 = r2.rewrite_car((gnu.lists.Pair) r4, (kawa.lang.SyntaxForm) r6)
            r9[r3] = r4
            r6 = r10[r3]
            r5.expander = r4
            r19 = r9
            gnu.expr.QuoteExp r9 = new gnu.expr.QuoteExp
            r9.<init>(r5)
            r6.noteValue(r9)
            boolean r9 = r4 instanceof gnu.expr.LambdaExp
            if (r9 == 0) goto L_0x01e6
            r9 = r4
            gnu.expr.LambdaExp r9 = (gnu.expr.LambdaExp) r9
            r9.nameDecl = r6
            r20 = r4
            java.lang.Object r4 = r6.getSymbol()
            r9.setSymbol(r4)
            goto L_0x01e8
        L_0x01e6:
            r20 = r4
        L_0x01e8:
            int r3 = r3 + 1
            r4 = r17
            r6 = r18
            r9 = r19
            goto L_0x01b1
        L_0x01f1:
            r17 = r4
            r18 = r6
            r19 = r9
            r2.currentMacroDefinition = r1
            boolean r3 = r0.recursive
            if (r3 != 0) goto L_0x0200
            r0.push(r14, r2, r7)
        L_0x0200:
            r3 = r23
            gnu.expr.Expression r4 = r2.rewrite_body(r3)
            r2.pop(r14)
            r2.popRenamedAlias(r8)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.let_syntax.rewrite(java.lang.Object, kawa.lang.Translator):gnu.expr.Expression");
    }

    private void push(LetExp let, Translator tr, Stack renamedAliases) {
        tr.push((ScopeExp) let);
        if (renamedAliases != null) {
            int i = renamedAliases.size();
            while (true) {
                i--;
                if (i >= 0) {
                    tr.pushRenamedAlias((Declaration) renamedAliases.pop());
                } else {
                    return;
                }
            }
        }
    }
}
