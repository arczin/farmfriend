package kawa.standard;

import kawa.lang.ListPat;
import kawa.lang.Pattern;
import kawa.lang.Syntax;

public class prim_method extends Syntax {
    public static final prim_method interface_method = new prim_method(185);
    public static final prim_method op1 = new prim_method();
    private static Pattern pattern3 = new ListPat(3);
    private static Pattern pattern4 = new ListPat(4);
    public static final prim_method static_method = new prim_method(184);
    public static final prim_method virtual_method = new prim_method(182);
    int op_code;

    static {
        virtual_method.setName("primitive-virtual-method");
        static_method.setName("primitive-static-method");
        interface_method.setName("primitive-interface-method");
        op1.setName("primitive-op1");
    }

    /* access modifiers changed from: package-private */
    public int opcode() {
        return this.op_code;
    }

    public prim_method(int opcode) {
        this.op_code = opcode;
    }

    public prim_method() {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v2, resolved type: gnu.lists.LList} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression rewrite(java.lang.Object r18, kawa.lang.Translator r19) {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            r3 = r19
            r0 = 4
            java.lang.Object[] r4 = new java.lang.Object[r0]
            int r0 = r1.op_code
            r5 = 0
            r6 = 1
            if (r0 != 0) goto L_0x0018
            kawa.lang.Pattern r0 = pattern3
            boolean r0 = r0.match(r2, r4, r6)
            if (r0 == 0) goto L_0x0020
            goto L_0x004e
        L_0x0018:
            kawa.lang.Pattern r0 = pattern4
            boolean r0 = r0.match(r2, r4, r5)
            if (r0 != 0) goto L_0x004e
        L_0x0020:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r5 = "wrong number of arguments to "
            java.lang.StringBuilder r0 = r0.append(r5)
            java.lang.String r5 = r17.getName()
            java.lang.StringBuilder r0 = r0.append(r5)
            java.lang.String r5 = "(opcode:"
            java.lang.StringBuilder r0 = r0.append(r5)
            int r5 = r1.op_code
            java.lang.StringBuilder r0 = r0.append(r5)
            java.lang.String r5 = ")"
            java.lang.StringBuilder r0 = r0.append(r5)
            java.lang.String r0 = r0.toString()
            gnu.expr.Expression r0 = r3.syntaxError(r0)
            return r0
        L_0x004e:
            r0 = 3
            r7 = r4[r0]
            boolean r7 = r7 instanceof gnu.lists.LList
            if (r7 != 0) goto L_0x0071
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r5 = "missing/invalid parameter list in "
            java.lang.StringBuilder r0 = r0.append(r5)
            java.lang.String r5 = r17.getName()
            java.lang.StringBuilder r0 = r0.append(r5)
            java.lang.String r0 = r0.toString()
            gnu.expr.Expression r0 = r3.syntaxError(r0)
            return r0
        L_0x0071:
            r0 = r4[r0]
            gnu.lists.LList r0 = (gnu.lists.LList) r0
            int r7 = r0.size()
            gnu.bytecode.Type[] r14 = new gnu.bytecode.Type[r7]
            r8 = 0
            r15 = r0
        L_0x007d:
            if (r8 >= r7) goto L_0x0092
            r0 = r15
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
            gnu.bytecode.Type r9 = r3.exp2Type(r0)
            r14[r8] = r9
            java.lang.Object r9 = r0.getCdr()
            r15 = r9
            gnu.lists.LList r15 = (gnu.lists.LList) r15
            int r8 = r8 + 1
            goto L_0x007d
        L_0x0092:
            gnu.lists.Pair r0 = new gnu.lists.Pair
            r8 = 2
            r8 = r4[r8]
            r9 = 0
            r0.<init>(r8, r9)
            gnu.bytecode.Type r13 = r3.exp2Type(r0)
            int r0 = r1.op_code
            if (r0 != 0) goto L_0x00b7
            r0 = r4[r6]
            java.lang.Number r0 = (java.lang.Number) r0
            r5 = r0
            java.lang.Number r5 = (java.lang.Number) r5
            int r0 = r0.intValue()
            gnu.expr.PrimProcedure r5 = new gnu.expr.PrimProcedure
            r5.<init>((int) r0, (gnu.bytecode.Type) r13, (gnu.bytecode.Type[]) r14)
            r0 = r5
            r6 = r13
            goto L_0x012a
        L_0x00b7:
            r8 = 0
            r0 = r2
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
            gnu.bytecode.Type r0 = r3.exp2Type(r0)
            if (r0 == 0) goto L_0x00c8
            gnu.bytecode.Type r0 = r0.getImplementationType()
            r16 = r0
            goto L_0x00ca
        L_0x00c8:
            r16 = r0
        L_0x00ca:
            r0 = r16
            gnu.bytecode.ClassType r0 = (gnu.bytecode.ClassType) r0     // Catch:{ Exception -> 0x00d4 }
            r8 = r0
            r8.getReflectClass()     // Catch:{ Exception -> 0x00d4 }
            r0 = r8
            goto L_0x00f8
        L_0x00d4:
            r0 = move-exception
            if (r8 != 0) goto L_0x00da
            r9 = 101(0x65, float:1.42E-43)
            goto L_0x00df
        L_0x00da:
            r9 = 119(0x77, float:1.67E-43)
            r8.setExisting(r5)
        L_0x00df:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "unknown class: "
            java.lang.StringBuilder r10 = r10.append(r11)
            r5 = r4[r5]
            java.lang.StringBuilder r5 = r10.append(r5)
            java.lang.String r5 = r5.toString()
            r3.error(r9, r5)
            r0 = r8
        L_0x00f8:
            r5 = r4[r6]
            boolean r5 = r5 instanceof gnu.lists.Pair
            if (r5 == 0) goto L_0x0117
            r5 = r4[r6]
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            r8 = r5
            java.lang.Object r5 = r5.getCar()
            java.lang.String r9 = "quote"
            if (r5 != r9) goto L_0x0117
            java.lang.Object r5 = r8.getCdr()
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            java.lang.Object r5 = r5.getCar()
            r4[r6] = r5
        L_0x0117:
            gnu.expr.PrimProcedure r5 = new gnu.expr.PrimProcedure
            int r9 = r1.op_code
            r6 = r4[r6]
            java.lang.String r11 = r6.toString()
            r8 = r5
            r10 = r0
            r12 = r13
            r6 = r13
            r13 = r14
            r8.<init>(r9, r10, r11, r12, r13)
            r0 = r5
        L_0x012a:
            gnu.expr.QuoteExp r5 = new gnu.expr.QuoteExp
            r5.<init>(r0)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.prim_method.rewrite(java.lang.Object, kawa.lang.Translator):gnu.expr.Expression");
    }
}
