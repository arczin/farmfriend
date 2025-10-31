package kawa.standard;

import gnu.expr.Expression;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class fluid_let extends Syntax {
    public static final fluid_let fluid_let = new fluid_let();
    Expression defaultInit;
    boolean star;

    static {
        fluid_let.setName("fluid-set");
    }

    public fluid_let(boolean star2, Expression defaultInit2) {
        this.star = star2;
        this.defaultInit = defaultInit2;
    }

    public fluid_let() {
        this.star = false;
    }

    public Expression rewrite(Object obj, Translator tr) {
        if (!(obj instanceof Pair)) {
            return tr.syntaxError("missing let arguments");
        }
        Pair pair = (Pair) obj;
        return rewrite(pair.getCar(), pair.getCdr(), tr);
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x00e5 A[Catch:{ all -> 0x0114 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00fb A[Catch:{ all -> 0x0114 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression rewrite(java.lang.Object r13, java.lang.Object r14, kawa.lang.Translator r15) {
        /*
            r12 = this;
            boolean r0 = r12.star
            r1 = 1
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x000b
        L_0x0007:
            int r0 = gnu.lists.LList.length(r13)
        L_0x000b:
            gnu.expr.Expression[] r2 = new gnu.expr.Expression[r0]
            gnu.expr.FluidLetExp r3 = new gnu.expr.FluidLetExp
            r3.<init>(r2)
            r4 = 0
        L_0x0013:
            if (r4 >= r0) goto L_0x0119
            r5 = r13
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            java.lang.Object r6 = r15.pushPositionOf(r5)
            java.lang.Object r7 = r5.getCar()     // Catch:{ all -> 0x0114 }
            boolean r8 = r7 instanceof java.lang.String     // Catch:{ all -> 0x0114 }
            if (r8 != 0) goto L_0x00d6
            boolean r8 = r7 instanceof gnu.mapping.Symbol     // Catch:{ all -> 0x0114 }
            if (r8 == 0) goto L_0x002a
            goto L_0x00d6
        L_0x002a:
            boolean r8 = r7 instanceof gnu.lists.Pair     // Catch:{ all -> 0x0114 }
            if (r8 == 0) goto L_0x00b1
            r8 = r7
            gnu.lists.Pair r8 = (gnu.lists.Pair) r8     // Catch:{ all -> 0x0114 }
            r9 = r8
            java.lang.Object r8 = r8.getCar()     // Catch:{ all -> 0x0114 }
            boolean r8 = r8 instanceof java.lang.String     // Catch:{ all -> 0x0114 }
            if (r8 != 0) goto L_0x004a
            java.lang.Object r8 = r9.getCar()     // Catch:{ all -> 0x0114 }
            boolean r8 = r8 instanceof gnu.mapping.Symbol     // Catch:{ all -> 0x0114 }
            if (r8 != 0) goto L_0x004a
            java.lang.Object r8 = r9.getCar()     // Catch:{ all -> 0x0114 }
            boolean r8 = r8 instanceof kawa.lang.SyntaxForm     // Catch:{ all -> 0x0114 }
            if (r8 == 0) goto L_0x00b1
        L_0x004a:
            java.lang.Object r8 = r9.getCar()     // Catch:{ all -> 0x0114 }
            r7 = r8
            boolean r8 = r7 instanceof kawa.lang.SyntaxForm     // Catch:{ all -> 0x0114 }
            if (r8 == 0) goto L_0x005c
            r8 = r7
            kawa.lang.SyntaxForm r8 = (kawa.lang.SyntaxForm) r8     // Catch:{ all -> 0x0114 }
            java.lang.Object r8 = r8.getDatum()     // Catch:{ all -> 0x0114 }
            r7 = r8
        L_0x005c:
            java.lang.Object r8 = r9.getCdr()     // Catch:{ all -> 0x0114 }
            gnu.lists.LList r10 = gnu.lists.LList.Empty     // Catch:{ all -> 0x0114 }
            if (r8 != r10) goto L_0x0067
            gnu.expr.Expression r8 = r12.defaultInit     // Catch:{ all -> 0x0114 }
            goto L_0x00d8
        L_0x0067:
            java.lang.Object r8 = r9.getCdr()     // Catch:{ all -> 0x0114 }
            boolean r8 = r8 instanceof gnu.lists.Pair     // Catch:{ all -> 0x0114 }
            if (r8 == 0) goto L_0x0088
            java.lang.Object r8 = r9.getCdr()     // Catch:{ all -> 0x0114 }
            gnu.lists.Pair r8 = (gnu.lists.Pair) r8     // Catch:{ all -> 0x0114 }
            r9 = r8
            java.lang.Object r8 = r8.getCdr()     // Catch:{ all -> 0x0114 }
            gnu.lists.LList r10 = gnu.lists.LList.Empty     // Catch:{ all -> 0x0114 }
            if (r8 == r10) goto L_0x007f
            goto L_0x0088
        L_0x007f:
            java.lang.Object r8 = r9.getCar()     // Catch:{ all -> 0x0114 }
            gnu.expr.Expression r8 = r15.rewrite(r8)     // Catch:{ all -> 0x0114 }
            goto L_0x00d8
        L_0x0088:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0114 }
            r1.<init>()     // Catch:{ all -> 0x0114 }
            java.lang.String r8 = "bad syntax for value of "
            java.lang.StringBuilder r1 = r1.append(r8)     // Catch:{ all -> 0x0114 }
            java.lang.StringBuilder r1 = r1.append(r7)     // Catch:{ all -> 0x0114 }
            java.lang.String r8 = " in "
            java.lang.StringBuilder r1 = r1.append(r8)     // Catch:{ all -> 0x0114 }
            java.lang.String r8 = r12.getName()     // Catch:{ all -> 0x0114 }
            java.lang.StringBuilder r1 = r1.append(r8)     // Catch:{ all -> 0x0114 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0114 }
            gnu.expr.Expression r1 = r15.syntaxError(r1)     // Catch:{ all -> 0x0114 }
            r15.popPositionOf(r6)
            return r1
        L_0x00b1:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0114 }
            r1.<init>()     // Catch:{ all -> 0x0114 }
            java.lang.String r8 = "invalid "
            java.lang.StringBuilder r1 = r1.append(r8)     // Catch:{ all -> 0x0114 }
            java.lang.String r8 = r12.getName()     // Catch:{ all -> 0x0114 }
            java.lang.StringBuilder r1 = r1.append(r8)     // Catch:{ all -> 0x0114 }
            java.lang.String r8 = " syntax"
            java.lang.StringBuilder r1 = r1.append(r8)     // Catch:{ all -> 0x0114 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0114 }
            gnu.expr.Expression r1 = r15.syntaxError(r1)     // Catch:{ all -> 0x0114 }
            r15.popPositionOf(r6)
            return r1
        L_0x00d6:
            gnu.expr.Expression r8 = r12.defaultInit     // Catch:{ all -> 0x0114 }
        L_0x00d8:
            gnu.expr.Declaration r9 = r3.addDeclaration((java.lang.Object) r7)     // Catch:{ all -> 0x0114 }
            gnu.expr.NameLookup r10 = r15.lexical     // Catch:{ all -> 0x0114 }
            r11 = 0
            gnu.expr.Declaration r10 = r10.lookup((java.lang.Object) r7, (boolean) r11)     // Catch:{ all -> 0x0114 }
            if (r10 == 0) goto L_0x00f0
            r10.maybeIndirectBinding(r15)     // Catch:{ all -> 0x0114 }
            r9.base = r10     // Catch:{ all -> 0x0114 }
            r10.setFluid(r1)     // Catch:{ all -> 0x0114 }
            r10.setCanWrite(r1)     // Catch:{ all -> 0x0114 }
        L_0x00f0:
            r9.setCanWrite(r1)     // Catch:{ all -> 0x0114 }
            r9.setFluid(r1)     // Catch:{ all -> 0x0114 }
            r9.setIndirectBinding(r1)     // Catch:{ all -> 0x0114 }
            if (r8 != 0) goto L_0x0101
            gnu.expr.ReferenceExp r11 = new gnu.expr.ReferenceExp     // Catch:{ all -> 0x0114 }
            r11.<init>((java.lang.Object) r7)     // Catch:{ all -> 0x0114 }
            r8 = r11
        L_0x0101:
            r2[r4] = r8     // Catch:{ all -> 0x0114 }
            r11 = 0
            r9.noteValue(r11)     // Catch:{ all -> 0x0114 }
            java.lang.Object r11 = r5.getCdr()     // Catch:{ all -> 0x0114 }
            r13 = r11
            r15.popPositionOf(r6)
            int r4 = r4 + 1
            goto L_0x0013
        L_0x0114:
            r1 = move-exception
            r15.popPositionOf(r6)
            throw r1
        L_0x0119:
            r15.push((gnu.expr.ScopeExp) r3)
            boolean r1 = r12.star
            if (r1 == 0) goto L_0x012b
            gnu.lists.LList r1 = gnu.lists.LList.Empty
            if (r13 == r1) goto L_0x012b
            gnu.expr.Expression r1 = r12.rewrite(r13, r14, r15)
            r3.body = r1
            goto L_0x0131
        L_0x012b:
            gnu.expr.Expression r1 = r15.rewrite_body(r14)
            r3.body = r1
        L_0x0131:
            r15.pop(r3)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.fluid_let.rewrite(java.lang.Object, java.lang.Object, kawa.lang.Translator):gnu.expr.Expression");
    }
}
