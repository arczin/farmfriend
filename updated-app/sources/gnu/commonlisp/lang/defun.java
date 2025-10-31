package gnu.commonlisp.lang;

import gnu.expr.Declaration;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Lambda;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class defun extends Syntax {
    Lambda lambdaSyntax;

    public defun(Lambda lambdaSyntax2) {
        this.lambdaSyntax = lambdaSyntax2;
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        if (st.getCdr() instanceof Pair) {
            Pair pair = (Pair) st.getCdr();
            Pair p = pair;
            if ((pair.getCar() instanceof String) || (p.getCar() instanceof Symbol)) {
                Object sym = p.getCar();
                Declaration decl = defs.lookup(sym);
                if (decl == null) {
                    decl = new Declaration(sym);
                    decl.setProcedureDecl(true);
                    defs.addDeclaration(decl);
                } else {
                    tr.error('w', "duplicate declaration for `" + sym + "'");
                }
                if (defs instanceof ModuleExp) {
                    decl.setCanRead(true);
                }
                forms.addElement(Translator.makePair(st, this, Translator.makePair(p, decl, p.getCdr())));
                return true;
            }
        }
        return super.scanForDefinitions(st, forms, defs, tr);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: gnu.expr.Declaration} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression rewriteForm(gnu.lists.Pair r16, kawa.lang.Translator r17) {
        /*
            r15 = this;
            java.lang.Object r0 = r16.getCdr()
            r1 = 0
            r2 = 0
            r3 = 0
            boolean r4 = r0 instanceof gnu.lists.Pair
            if (r4 == 0) goto L_0x0084
            r4 = r0
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4
            java.lang.Object r5 = r4.getCar()
            boolean r6 = r5 instanceof gnu.mapping.Symbol
            if (r6 != 0) goto L_0x0027
            boolean r6 = r5 instanceof java.lang.String
            if (r6 == 0) goto L_0x001b
            goto L_0x0027
        L_0x001b:
            boolean r6 = r5 instanceof gnu.expr.Declaration
            if (r6 == 0) goto L_0x002b
            r3 = r5
            gnu.expr.Declaration r3 = (gnu.expr.Declaration) r3
            java.lang.Object r1 = r3.getSymbol()
            goto L_0x002b
        L_0x0027:
            java.lang.String r1 = r5.toString()
        L_0x002b:
            if (r1 == 0) goto L_0x0082
            java.lang.Object r6 = r4.getCdr()
            boolean r6 = r6 instanceof gnu.lists.Pair
            if (r6 == 0) goto L_0x0082
            java.lang.Object r6 = r4.getCdr()
            gnu.lists.Pair r6 = (gnu.lists.Pair) r6
            gnu.expr.LambdaExp r7 = new gnu.expr.LambdaExp
            r7.<init>()
            r14 = r15
            kawa.lang.Lambda r8 = r14.lambdaSyntax
            java.lang.Object r10 = r6.getCar()
            java.lang.Object r11 = r6.getCdr()
            r13 = 0
            r9 = r7
            r12 = r17
            r8.rewrite(r9, r10, r11, r12, r13)
            r7.setSymbol(r1)
            boolean r8 = r6 instanceof gnu.lists.PairWithPosition
            if (r8 == 0) goto L_0x005f
            r8 = r6
            gnu.lists.PairWithPosition r8 = (gnu.lists.PairWithPosition) r8
            r7.setLocation(r8)
        L_0x005f:
            r2 = r7
            gnu.expr.SetExp r8 = new gnu.expr.SetExp
            r8.<init>((java.lang.Object) r1, (gnu.expr.Expression) r2)
            r9 = 1
            r8.setDefining(r9)
            r8.setFuncDef(r9)
            if (r3 == 0) goto L_0x0081
            r8.setBinding(r3)
            gnu.expr.ScopeExp r9 = r3.context
            boolean r9 = r9 instanceof gnu.expr.ModuleExp
            if (r9 == 0) goto L_0x007e
            boolean r9 = r3.getCanWrite()
            if (r9 == 0) goto L_0x007e
            r2 = 0
        L_0x007e:
            r3.noteValue(r2)
        L_0x0081:
            return r8
        L_0x0082:
            r14 = r15
            goto L_0x0085
        L_0x0084:
            r14 = r15
        L_0x0085:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "invalid syntax for "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = r15.getName()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r5 = r17
            gnu.expr.Expression r4 = r5.syntaxError(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.commonlisp.lang.defun.rewriteForm(gnu.lists.Pair, kawa.lang.Translator):gnu.expr.Expression");
    }
}
