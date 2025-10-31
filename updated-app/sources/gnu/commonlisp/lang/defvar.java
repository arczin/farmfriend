package gnu.commonlisp.lang;

import gnu.expr.Declaration;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class defvar extends Syntax {
    boolean force;

    public defvar(boolean force2) {
        this.force = force2;
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        if (!(st.getCdr() instanceof Pair)) {
            return super.scanForDefinitions(st, forms, defs, tr);
        }
        Pair p = (Pair) st.getCdr();
        Object name = p.getCar();
        if ((name instanceof String) || (name instanceof Symbol)) {
            Declaration decl = defs.lookup(name);
            if (decl == null) {
                decl = new Declaration(name);
                decl.setFlag(268435456);
                defs.addDeclaration(decl);
            } else {
                tr.error('w', "duplicate declaration for `" + name + "'");
            }
            st = Translator.makePair(st, this, Translator.makePair(p, decl, p.getCdr()));
            if (defs instanceof ModuleExp) {
                decl.setCanRead(true);
                decl.setCanWrite(true);
            }
        }
        forms.addElement(st);
        return true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: gnu.expr.Declaration} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression rewriteForm(gnu.lists.Pair r8, kawa.lang.Translator r9) {
        /*
            r7 = this;
            java.lang.Object r0 = r8.getCdr()
            r1 = 0
            r2 = 0
            r3 = 0
            boolean r4 = r0 instanceof gnu.lists.Pair
            if (r4 == 0) goto L_0x0046
            r4 = r0
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4
            java.lang.Object r5 = r4.getCar()
            boolean r5 = r5 instanceof gnu.expr.Declaration
            if (r5 == 0) goto L_0x0046
            java.lang.Object r5 = r4.getCar()
            r3 = r5
            gnu.expr.Declaration r3 = (gnu.expr.Declaration) r3
            java.lang.Object r1 = r3.getSymbol()
            java.lang.Object r5 = r4.getCdr()
            boolean r5 = r5 instanceof gnu.lists.Pair
            if (r5 == 0) goto L_0x003d
            java.lang.Object r5 = r4.getCdr()
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            java.lang.Object r6 = r5.getCar()
            gnu.expr.Expression r2 = r9.rewrite(r6)
            r5.getCdr()
            gnu.lists.LList r6 = gnu.lists.LList.Empty
            goto L_0x0046
        L_0x003d:
            java.lang.Object r5 = r4.getCdr()
            gnu.lists.LList r6 = gnu.lists.LList.Empty
            if (r5 == r6) goto L_0x0046
            r1 = 0
        L_0x0046:
            if (r1 != 0) goto L_0x0064
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "invalid syntax for "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = r7.getName()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            gnu.expr.Expression r4 = r9.syntaxError(r4)
            return r4
        L_0x0064:
            if (r2 != 0) goto L_0x0073
            boolean r4 = r7.force
            if (r4 == 0) goto L_0x006d
            gnu.expr.Expression r2 = gnu.commonlisp.lang.CommonLisp.nilExpr
            goto L_0x0073
        L_0x006d:
            gnu.expr.QuoteExp r4 = new gnu.expr.QuoteExp
            r4.<init>(r1)
            return r4
        L_0x0073:
            gnu.expr.SetExp r4 = new gnu.expr.SetExp
            r4.<init>((java.lang.Object) r1, (gnu.expr.Expression) r2)
            boolean r5 = r7.force
            r6 = 1
            if (r5 != 0) goto L_0x0080
            r4.setSetIfUnbound(r6)
        L_0x0080:
            r4.setDefining(r6)
            if (r3 == 0) goto L_0x0098
            r4.setBinding(r3)
            gnu.expr.ScopeExp r5 = r3.context
            boolean r5 = r5 instanceof gnu.expr.ModuleExp
            if (r5 == 0) goto L_0x0095
            boolean r5 = r3.getCanWrite()
            if (r5 == 0) goto L_0x0095
            r2 = 0
        L_0x0095:
            r3.noteValue(r2)
        L_0x0098:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.commonlisp.lang.defvar.rewriteForm(gnu.lists.Pair, kawa.lang.Translator):gnu.expr.Expression");
    }
}
