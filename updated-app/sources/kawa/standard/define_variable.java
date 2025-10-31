package kawa.standard;

import gnu.expr.Declaration;
import gnu.expr.ScopeExp;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class define_variable extends Syntax {
    public static final define_variable define_variable = new define_variable();

    static {
        define_variable.setName("define-variable");
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        if (!(st.getCdr() instanceof Pair)) {
            return super.scanForDefinitions(st, forms, defs, tr);
        }
        Pair p = (Pair) st.getCdr();
        Object sym = p.getCar();
        if ((sym instanceof String) || (sym instanceof Symbol)) {
            if (defs.lookup(sym) != null) {
                tr.error('e', "duplicate declaration for '" + sym + "'");
            }
            Declaration decl = defs.addDeclaration(sym);
            tr.push(decl);
            decl.setSimple(false);
            decl.setPrivate(true);
            decl.setFlag(268697600);
            decl.setCanRead(true);
            decl.setCanWrite(true);
            decl.setIndirectBinding(true);
            st = Translator.makePair(st, this, Translator.makePair(p, decl, p.getCdr()));
        }
        forms.addElement(st);
        return true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: gnu.expr.Declaration} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression rewriteForm(gnu.lists.Pair r7, kawa.lang.Translator r8) {
        /*
            r6 = this;
            java.lang.Object r0 = r7.getCdr()
            r1 = 0
            r2 = 0
            boolean r3 = r0 instanceof gnu.lists.Pair
            if (r3 == 0) goto L_0x0064
            r3 = r0
            gnu.lists.Pair r3 = (gnu.lists.Pair) r3
            java.lang.Object r0 = r3.getCar()
            boolean r4 = r0 instanceof java.lang.String
            if (r4 != 0) goto L_0x0048
            boolean r4 = r0 instanceof gnu.mapping.Symbol
            if (r4 == 0) goto L_0x001a
            goto L_0x0048
        L_0x001a:
            boolean r4 = r0 instanceof gnu.expr.Declaration
            if (r4 == 0) goto L_0x0064
            java.lang.Object r4 = r3.getCar()
            r2 = r4
            gnu.expr.Declaration r2 = (gnu.expr.Declaration) r2
            java.lang.Object r0 = r3.getCdr()
            boolean r4 = r0 instanceof gnu.lists.Pair
            if (r4 == 0) goto L_0x0042
            r4 = r0
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4
            r3 = r4
            java.lang.Object r4 = r4.getCdr()
            gnu.lists.LList r5 = gnu.lists.LList.Empty
            if (r4 != r5) goto L_0x0042
            java.lang.Object r4 = r3.getCar()
            gnu.expr.Expression r1 = r8.rewrite(r4)
            goto L_0x0064
        L_0x0042:
            gnu.lists.LList r4 = gnu.lists.LList.Empty
            if (r0 == r4) goto L_0x0064
            r2 = 0
            goto L_0x0064
        L_0x0048:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = r6.getName()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = " is only allowed in a <body>"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            gnu.expr.Expression r4 = r8.syntaxError(r4)
            return r4
        L_0x0064:
            if (r2 != 0) goto L_0x0082
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "invalid syntax for "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r6.getName()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            gnu.expr.Expression r3 = r8.syntaxError(r3)
            return r3
        L_0x0082:
            if (r1 != 0) goto L_0x0087
            gnu.expr.QuoteExp r3 = gnu.expr.QuoteExp.voidExp
            return r3
        L_0x0087:
            gnu.expr.SetExp r3 = new gnu.expr.SetExp
            r3.<init>((gnu.expr.Declaration) r2, (gnu.expr.Expression) r1)
            r4 = 1
            r3.setDefining(r4)
            r3.setSetIfUnbound(r4)
            if (r2 == 0) goto L_0x00a8
            r3.setBinding(r2)
            gnu.expr.ScopeExp r4 = r2.context
            boolean r4 = r4 instanceof gnu.expr.ModuleExp
            if (r4 == 0) goto L_0x00a5
            boolean r4 = r2.getCanWrite()
            if (r4 == 0) goto L_0x00a5
            r1 = 0
        L_0x00a5:
            r2.noteValue(r1)
        L_0x00a8:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.define_variable.rewriteForm(gnu.lists.Pair, kawa.lang.Translator):gnu.expr.Expression");
    }
}
