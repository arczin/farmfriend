package kawa.standard;

import kawa.lang.Syntax;

public class module_name extends Syntax {
    public static final module_name module_name = new module_name();

    static {
        module_name.setName("module-name");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v17, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v9, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00be  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void scanForm(gnu.lists.Pair r16, gnu.expr.ScopeExp r17, kawa.lang.Translator r18) {
        /*
            r15 = this;
            r0 = r18
            java.lang.Object r1 = r16.getCdr()
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
            if (r3 == 0) goto L_0x001f
            r3 = r1
            gnu.lists.Pair r3 = (gnu.lists.Pair) r3
            java.lang.Object r3 = r3.getCar()
            goto L_0x0020
        L_0x001f:
            r3 = 0
        L_0x0020:
            boolean r4 = r3 instanceof kawa.lang.SyntaxForm
            if (r4 == 0) goto L_0x002c
            r2 = r3
            kawa.lang.SyntaxForm r2 = (kawa.lang.SyntaxForm) r2
            java.lang.Object r3 = r2.getDatum()
            goto L_0x0020
        L_0x002c:
            r4 = 0
            r5 = 0
            r6 = 0
            boolean r7 = r3 instanceof gnu.lists.Pair
            r8 = 0
            r9 = 1
            if (r7 == 0) goto L_0x006d
            r7 = r3
            gnu.lists.Pair r7 = (gnu.lists.Pair) r7
            r10 = r7
            java.lang.Object r7 = r7.getCar()
            java.lang.String r11 = "quote"
            if (r7 != r11) goto L_0x006d
            java.lang.Object r3 = r10.getCdr()
            boolean r7 = r3 instanceof gnu.lists.Pair
            if (r7 == 0) goto L_0x0068
            r7 = r3
            gnu.lists.Pair r7 = (gnu.lists.Pair) r7
            r10 = r7
            java.lang.Object r7 = r7.getCdr()
            gnu.lists.LList r11 = gnu.lists.LList.Empty
            if (r7 != r11) goto L_0x0068
            java.lang.Object r7 = r10.getCar()
            boolean r7 = r7 instanceof java.lang.String
            if (r7 != 0) goto L_0x005e
            goto L_0x0068
        L_0x005e:
            java.lang.Object r7 = r10.getCar()
            r4 = r7
            java.lang.String r4 = (java.lang.String) r4
            r10 = r17
            goto L_0x00b1
        L_0x0068:
            java.lang.String r5 = "invalid quoted symbol for 'module-name'"
            r10 = r17
            goto L_0x00b1
        L_0x006d:
            boolean r7 = r3 instanceof gnu.lists.FString
            if (r7 != 0) goto L_0x00ab
            boolean r7 = r3 instanceof java.lang.String
            if (r7 == 0) goto L_0x0078
            r10 = r17
            goto L_0x00ad
        L_0x0078:
            boolean r7 = r3 instanceof gnu.mapping.Symbol
            if (r7 == 0) goto L_0x00a6
            java.lang.String r4 = r3.toString()
            int r7 = r4.length()
            r10 = 2
            if (r7 <= r10) goto L_0x009f
            char r10 = r4.charAt(r8)
            r11 = 60
            if (r10 != r11) goto L_0x009f
            int r10 = r7 + -1
            char r10 = r4.charAt(r10)
            r11 = 62
            if (r10 != r11) goto L_0x009f
            int r10 = r7 + -1
            java.lang.String r4 = r4.substring(r9, r10)
        L_0x009f:
            r10 = r17
            gnu.expr.Declaration r6 = r0.define(r3, r2, r10)
            goto L_0x00b1
        L_0x00a6:
            r10 = r17
            java.lang.String r5 = "un-implemented expression in module-name"
            goto L_0x00b1
        L_0x00ab:
            r10 = r17
        L_0x00ad:
            java.lang.String r4 = r3.toString()
        L_0x00b1:
            if (r5 == 0) goto L_0x00be
            java.util.Stack r7 = r0.formStack
            gnu.expr.Expression r8 = r0.syntaxError(r5)
            r7.add(r8)
            goto L_0x0164
        L_0x00be:
            r7 = 46
            int r7 = r4.lastIndexOf(r7)
            r11 = r4
            if (r7 < 0) goto L_0x00d0
            int r12 = r7 + 1
            java.lang.String r8 = r4.substring(r8, r12)
            r0.classPrefix = r8
            goto L_0x00fa
        L_0x00d0:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r12 = r0.classPrefix
            java.lang.StringBuilder r8 = r8.append(r12)
            java.lang.StringBuilder r8 = r8.append(r4)
            java.lang.String r4 = r8.toString()
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r12 = r0.classPrefix
            java.lang.StringBuilder r8 = r8.append(r12)
            java.lang.String r12 = gnu.expr.Compilation.mangleName(r4)
            java.lang.StringBuilder r8 = r8.append(r12)
            java.lang.String r11 = r8.toString()
        L_0x00fa:
            gnu.expr.ModuleExp r8 = r18.getModule()
            gnu.bytecode.ClassType r12 = r0.mainClass
            if (r12 != 0) goto L_0x010a
            gnu.bytecode.ClassType r12 = new gnu.bytecode.ClassType
            r12.<init>(r11)
            r0.mainClass = r12
            goto L_0x0134
        L_0x010a:
            gnu.bytecode.ClassType r12 = r0.mainClass
            java.lang.String r12 = r12.getName()
            if (r12 != 0) goto L_0x0118
            gnu.bytecode.ClassType r13 = r0.mainClass
            r13.setName(r11)
            goto L_0x0134
        L_0x0118:
            boolean r13 = r12.equals(r11)
            if (r13 != 0) goto L_0x0134
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = "duplicate module-name: old name: "
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.StringBuilder r13 = r13.append(r12)
            java.lang.String r13 = r13.toString()
            r0.syntaxError(r13)
        L_0x0134:
            gnu.bytecode.ClassType r12 = r0.mainClass
            r8.setType(r12)
            r8.setName(r4)
            if (r6 == 0) goto L_0x0161
            gnu.expr.QuoteExp r12 = new gnu.expr.QuoteExp
            gnu.bytecode.ClassType r13 = r0.mainClass
            gnu.bytecode.ClassType r14 = gnu.expr.Compilation.typeClass
            r12.<init>(r13, r14)
            r6.noteValue(r12)
            r12 = 16793600(0x1004000, double:8.297141E-317)
            r6.setFlag(r12)
            gnu.expr.ScopeExp r12 = r8.outer
            if (r12 != 0) goto L_0x0159
            r12 = 2048(0x800, double:1.0118E-320)
            r6.setFlag(r12)
        L_0x0159:
            r6.setPrivate(r9)
            gnu.bytecode.ClassType r9 = gnu.expr.Compilation.typeClass
            r6.setType(r9)
        L_0x0161:
            r18.mustCompileHere()
        L_0x0164:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.module_name.scanForm(gnu.lists.Pair, gnu.expr.ScopeExp, kawa.lang.Translator):void");
    }
}
