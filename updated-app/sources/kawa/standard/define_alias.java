package kawa.standard;

import gnu.expr.Expression;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class define_alias extends Syntax {
    public static final define_alias define_alias = new define_alias();

    static {
        define_alias.setName("define-alias");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x008d, code lost:
        if ((r16 instanceof gnu.expr.ModuleExp) != false) goto L_0x0092;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean scanForDefinitions(gnu.lists.Pair r18, java.util.Vector r19, gnu.expr.ScopeExp r20, kawa.lang.Translator r21) {
        /*
            r17 = this;
            r0 = r21
            java.lang.Object r1 = r18.getCdr()
            r2 = 0
        L_0x0007:
            boolean r3 = r1 instanceof kawa.lang.SyntaxForm
            if (r3 == 0) goto L_0x0013
            r2 = r1
            kawa.lang.SyntaxForm r2 = (kawa.lang.SyntaxForm) r2
            java.lang.Object r1 = r2.getDatum()
            goto L_0x0007
        L_0x0013:
            boolean r3 = r1 instanceof gnu.lists.Pair
            r4 = 0
            if (r3 == 0) goto L_0x00d6
            r3 = r1
            gnu.lists.Pair r3 = (gnu.lists.Pair) r3
            r5 = r2
            java.lang.Object r6 = r3.getCar()
        L_0x0020:
            boolean r7 = r6 instanceof kawa.lang.SyntaxForm
            if (r7 == 0) goto L_0x002c
            r5 = r6
            kawa.lang.SyntaxForm r5 = (kawa.lang.SyntaxForm) r5
            java.lang.Object r6 = r5.getDatum()
            goto L_0x0020
        L_0x002c:
            java.lang.Object r1 = r3.getCdr()
        L_0x0030:
            boolean r7 = r1 instanceof kawa.lang.SyntaxForm
            if (r7 == 0) goto L_0x003c
            r2 = r1
            kawa.lang.SyntaxForm r2 = (kawa.lang.SyntaxForm) r2
            java.lang.Object r1 = r2.getDatum()
            goto L_0x0030
        L_0x003c:
            boolean r7 = r6 instanceof java.lang.String
            if (r7 != 0) goto L_0x004b
            boolean r7 = r6 instanceof gnu.mapping.Symbol
            if (r7 == 0) goto L_0x0045
            goto L_0x004b
        L_0x0045:
            r11 = r19
            r7 = r20
            goto L_0x00da
        L_0x004b:
            boolean r7 = r1 instanceof gnu.lists.Pair
            if (r7 == 0) goto L_0x00d1
            r7 = r1
            gnu.lists.Pair r7 = (gnu.lists.Pair) r7
            r3 = r7
            java.lang.Object r7 = r7.getCdr()
            gnu.lists.LList r8 = gnu.lists.LList.Empty
            if (r7 != r8) goto L_0x00d1
            r7 = r20
            gnu.expr.Declaration r8 = r0.define(r6, r5, r7)
            r9 = 1
            r8.setIndirectBinding(r9)
            r8.setAlias(r9)
            gnu.expr.Expression r10 = r0.rewrite_car((gnu.lists.Pair) r3, (kawa.lang.SyntaxForm) r2)
            boolean r11 = r10 instanceof gnu.expr.ReferenceExp
            r12 = 16384(0x4000, double:8.0948E-320)
            if (r11 == 0) goto L_0x009e
            r11 = r10
            gnu.expr.ReferenceExp r11 = (gnu.expr.ReferenceExp) r11
            gnu.expr.Declaration r14 = r11.getBinding()
            gnu.expr.Declaration r14 = gnu.expr.Declaration.followAliases(r14)
            if (r14 == 0) goto L_0x0099
            gnu.expr.Expression r15 = r14.getValue()
            r16 = r15
            boolean r15 = r15 instanceof gnu.expr.ClassExp
            if (r15 != 0) goto L_0x0090
            r15 = r16
            boolean r9 = r15 instanceof gnu.expr.ModuleExp
            if (r9 == 0) goto L_0x0099
            goto L_0x0092
        L_0x0090:
            r15 = r16
        L_0x0092:
            r8.setIndirectBinding(r4)
            r8.setFlag(r12)
            goto L_0x009d
        L_0x0099:
            r4 = 1
            r11.setDontDereference(r4)
        L_0x009d:
            goto L_0x00b6
        L_0x009e:
            boolean r9 = r10 instanceof gnu.expr.QuoteExp
            if (r9 == 0) goto L_0x00a9
            r8.setIndirectBinding(r4)
            r8.setFlag(r12)
            goto L_0x00b6
        L_0x00a9:
            gnu.expr.Expression r10 = kawa.standard.location.rewrite((gnu.expr.Expression) r10, (kawa.lang.Translator) r0)
            java.lang.String r4 = "gnu.mapping.Location"
            gnu.bytecode.ClassType r4 = gnu.bytecode.ClassType.make(r4)
            r8.setType(r4)
        L_0x00b6:
            r21.mustCompileHere()
            r0.push((gnu.expr.Declaration) r8)
            gnu.expr.SetExp r4 = new gnu.expr.SetExp
            r4.<init>((gnu.expr.Declaration) r8, (gnu.expr.Expression) r10)
            r0.setLineOf(r4)
            r8.noteValue(r10)
            r9 = 1
            r4.setDefining(r9)
            r11 = r19
            r11.addElement(r4)
            return r9
        L_0x00d1:
            r11 = r19
            r7 = r20
            goto L_0x00da
        L_0x00d6:
            r11 = r19
            r7 = r20
        L_0x00da:
            r3 = 101(0x65, float:1.42E-43)
            java.lang.String r5 = "invalid syntax for define-alias"
            r0.error(r3, r5)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.define_alias.scanForDefinitions(gnu.lists.Pair, java.util.Vector, gnu.expr.ScopeExp, kawa.lang.Translator):boolean");
    }

    public Expression rewrite(Object obj, Translator tr) {
        return tr.syntaxError("define-alias is only allowed in a <body>");
    }
}
