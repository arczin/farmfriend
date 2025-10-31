package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Member;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ClassExp;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.Keyword;
import gnu.expr.PrimProcedure;
import gnu.mapping.MethodProc;

public class CompileInvoke {
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v1, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v0, resolved type: gnu.bytecode.ArrayType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r29v0, resolved type: gnu.bytecode.ArrayType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: gnu.expr.ApplyExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: gnu.expr.ApplyExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: gnu.expr.ApplyExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v20, resolved type: gnu.expr.LetExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v9, resolved type: gnu.expr.ApplyExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v12, resolved type: gnu.expr.ApplyExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v13, resolved type: gnu.expr.ApplyExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v16, resolved type: gnu.expr.ApplyExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v44, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v16, resolved type: gnu.bytecode.ArrayType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v18, resolved type: gnu.bytecode.ClassType} */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x0214, code lost:
        if ((gnu.kawa.reflect.ClassMethods.selectApplicable(r7, new gnu.bytecode.Type[]{gnu.expr.Compilation.typeClassType}) >> 32) == 1) goto L_0x0224;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x0246, code lost:
        if (gnu.kawa.reflect.ClassMethods.selectApplicable(gnu.kawa.reflect.ClassMethods.getMethods(r11, "add", 'V', (gnu.bytecode.ClassType) null, r3.language), 2) > 0) goto L_0x0248;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x039a, code lost:
        if (r3 != 2) goto L_0x039e;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:214:0x03fd A[LOOP:5: B:191:0x03bf->B:214:0x03fd, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:324:0x0407 A[EDGE_INSN: B:324:0x0407->B:216:0x0407 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0107 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0108  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static gnu.expr.Expression validateApplyInvoke(gnu.expr.ApplyExp r30, gnu.expr.InlineCalls r31, gnu.bytecode.Type r32, gnu.mapping.Procedure r33) {
        /*
            r1 = r30
            r0 = r31
            r2 = r32
            r3 = r33
            gnu.kawa.reflect.Invoke r3 = (gnu.kawa.reflect.Invoke) r3
            char r4 = r3.kind
            gnu.expr.Compilation r5 = r31.getCompilation()
            gnu.expr.Expression[] r12 = r30.getArgs()
            int r13 = r12.length
            boolean r6 = r5.mustCompile
            if (r6 == 0) goto L_0x05e6
            if (r13 == 0) goto L_0x05e6
            r6 = 42
            r7 = 86
            r14 = 1
            if (r4 == r7) goto L_0x0024
            if (r4 != r6) goto L_0x0028
        L_0x0024:
            if (r13 != r14) goto L_0x0028
            goto L_0x05e6
        L_0x0028:
            r15 = 0
            r8 = r12[r15]
            r9 = 0
            gnu.expr.Expression r8 = r0.visit((gnu.expr.Expression) r8, (gnu.bytecode.Type) r9)
            r12[r15] = r8
            if (r4 == r7) goto L_0x003e
            if (r4 != r6) goto L_0x0037
            goto L_0x003e
        L_0x0037:
            gnu.expr.Language r10 = r3.language
            gnu.bytecode.Type r10 = r10.getTypeFor((gnu.expr.Expression) r8)
            goto L_0x0042
        L_0x003e:
            gnu.bytecode.Type r10 = r8.getType()
        L_0x0042:
            boolean r11 = r10 instanceof gnu.expr.PairClassType
            r9 = 78
            if (r11 == 0) goto L_0x0050
            if (r4 != r9) goto L_0x0050
            gnu.expr.PairClassType r10 = (gnu.expr.PairClassType) r10
            gnu.bytecode.ClassType r10 = r10.instanceType
            r11 = r10
            goto L_0x0059
        L_0x0050:
            boolean r11 = r10 instanceof gnu.bytecode.ObjectType
            if (r11 == 0) goto L_0x0058
            gnu.bytecode.ObjectType r10 = (gnu.bytecode.ObjectType) r10
            r11 = r10
            goto L_0x0059
        L_0x0058:
            r11 = 0
        L_0x0059:
            java.lang.String r10 = getMethodName(r12, r4)
            r15 = 83
            r17 = -1
            r14 = 2
            if (r4 == r7) goto L_0x0090
            if (r4 != r6) goto L_0x0067
            goto L_0x0090
        L_0x0067:
            if (r4 != r9) goto L_0x0071
            r6 = r13
            r19 = 0
            r20 = -1
            goto L_0x0097
        L_0x0071:
            if (r4 == r15) goto L_0x0088
            r6 = 115(0x73, float:1.61E-43)
            if (r4 != r6) goto L_0x0078
            goto L_0x0088
        L_0x0078:
            r6 = 80
            if (r4 != r6) goto L_0x0084
            int r6 = r13 + -2
            r19 = 3
            r20 = 1
            goto L_0x0097
        L_0x0084:
            r30.visitArgs(r31)
            return r1
        L_0x0088:
            int r6 = r13 + -2
            r19 = 2
            r20 = -1
            goto L_0x0097
        L_0x0090:
            int r6 = r13 + -1
            r19 = 2
            r20 = 0
        L_0x0097:
            r15 = 101(0x65, float:1.42E-43)
            if (r4 != r9) goto L_0x01b1
            boolean r7 = r11 instanceof gnu.bytecode.ArrayType
            if (r7 == 0) goto L_0x01b1
            gnu.bytecode.ArrayType r11 = (gnu.bytecode.ArrayType) r11
            gnu.bytecode.Type r2 = r11.getComponentType()
            int r3 = r12.length
            r4 = 3
            if (r3 < r4) goto L_0x00d8
            r3 = 1
            r4 = r12[r3]
            boolean r4 = r4 instanceof gnu.expr.QuoteExp
            if (r4 == 0) goto L_0x00d8
            r4 = r12[r3]
            gnu.expr.QuoteExp r4 = (gnu.expr.QuoteExp) r4
            java.lang.Object r3 = r4.getValue()
            boolean r4 = r3 instanceof gnu.expr.Keyword
            if (r4 == 0) goto L_0x00d8
            gnu.expr.Keyword r3 = (gnu.expr.Keyword) r3
            java.lang.String r3 = r3.getName()
            java.lang.String r4 = "length"
            boolean r4 = r4.equals(r3)
            if (r4 != 0) goto L_0x00d4
            java.lang.String r4 = "size"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x00d8
        L_0x00d4:
            r3 = r12[r14]
            r4 = 1
            goto L_0x00da
        L_0x00d8:
            r3 = 0
            r4 = 0
        L_0x00da:
            if (r3 != 0) goto L_0x00e8
            java.lang.Integer r3 = new java.lang.Integer
            int r6 = r12.length
            r7 = 1
            int r6 = r6 - r7
            r3.<init>(r6)
            gnu.expr.QuoteExp r3 = gnu.expr.QuoteExp.getInstance(r3)
        L_0x00e8:
            gnu.bytecode.PrimType r6 = gnu.bytecode.Type.intType
            gnu.expr.Expression r3 = r0.visit((gnu.expr.Expression) r3, (gnu.bytecode.Type) r6)
            gnu.expr.ApplyExp r6 = new gnu.expr.ApplyExp
            gnu.kawa.reflect.ArrayNew r7 = new gnu.kawa.reflect.ArrayNew
            r7.<init>(r2)
            r8 = 1
            gnu.expr.Expression[] r9 = new gnu.expr.Expression[r8]
            r8 = 0
            r9[r8] = r3
            r6.<init>((gnu.mapping.Procedure) r7, (gnu.expr.Expression[]) r9)
            r6.setType(r11)
            if (r4 == 0) goto L_0x0108
            int r3 = r12.length
            r7 = 3
            if (r3 != r7) goto L_0x0108
            return r6
        L_0x0108:
            gnu.expr.LetExp r3 = new gnu.expr.LetExp
            r7 = 1
            gnu.expr.Expression[] r8 = new gnu.expr.Expression[r7]
            r7 = 0
            r8[r7] = r6
            r3.<init>(r8)
            r7 = 0
            r9 = r7
            java.lang.String r9 = (java.lang.String) r9
            gnu.expr.Declaration r7 = r3.addDeclaration(r7, r11)
            r7.noteValue(r6)
            gnu.expr.BeginExp r6 = new gnu.expr.BeginExp
            r6.<init>()
            if (r4 == 0) goto L_0x0128
            r8 = 3
            goto L_0x0129
        L_0x0128:
            r8 = 1
        L_0x0129:
            r9 = 0
        L_0x012a:
            int r10 = r12.length
            if (r8 >= r10) goto L_0x01a6
            r10 = r12[r8]
            if (r4 == 0) goto L_0x0171
            int r11 = r8 + 1
            int r13 = r12.length
            if (r11 >= r13) goto L_0x0171
            boolean r13 = r10 instanceof gnu.expr.QuoteExp
            if (r13 == 0) goto L_0x0171
            r13 = r10
            gnu.expr.QuoteExp r13 = (gnu.expr.QuoteExp) r13
            java.lang.Object r13 = r13.getValue()
            boolean r14 = r13 instanceof gnu.expr.Keyword
            if (r14 == 0) goto L_0x0171
            gnu.expr.Keyword r13 = (gnu.expr.Keyword) r13
            java.lang.String r8 = r13.getName()
            int r9 = java.lang.Integer.parseInt(r8)     // Catch:{ all -> 0x0153 }
            r10 = r12[r11]     // Catch:{ all -> 0x0153 }
            r8 = r11
            goto L_0x0171
        L_0x0153:
            r0 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "non-integer keyword '"
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.StringBuilder r0 = r0.append(r8)
            java.lang.String r2 = "' in array constructor"
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            r5.error(r15, r0)
            return r1
        L_0x0171:
            gnu.expr.Expression r10 = r0.visit((gnu.expr.Expression) r10, (gnu.bytecode.Type) r2)
            gnu.expr.ApplyExp r11 = new gnu.expr.ApplyExp
            gnu.kawa.reflect.ArraySet r13 = new gnu.kawa.reflect.ArraySet
            r13.<init>(r2)
            r14 = 3
            gnu.expr.Expression[] r15 = new gnu.expr.Expression[r14]
            gnu.expr.ReferenceExp r14 = new gnu.expr.ReferenceExp
            r14.<init>((gnu.expr.Declaration) r7)
            r16 = 0
            r15[r16] = r14
            java.lang.Integer r14 = new java.lang.Integer
            r14.<init>(r9)
            gnu.expr.QuoteExp r14 = gnu.expr.QuoteExp.getInstance(r14)
            r17 = 1
            r15[r17] = r14
            r14 = 2
            r15[r14] = r10
            r11.<init>((gnu.mapping.Procedure) r13, (gnu.expr.Expression[]) r15)
            r6.add(r11)
            int r9 = r9 + 1
            int r8 = r8 + 1
            r14 = 2
            r15 = 101(0x65, float:1.42E-43)
            goto L_0x012a
        L_0x01a6:
            gnu.expr.ReferenceExp r0 = new gnu.expr.ReferenceExp
            r0.<init>((gnu.expr.Declaration) r7)
            r6.add(r0)
            r3.body = r6
            return r3
        L_0x01b1:
            if (r11 == 0) goto L_0x05e2
            if (r10 == 0) goto L_0x05e2
            boolean r7 = r11 instanceof gnu.expr.TypeValue
            if (r7 == 0) goto L_0x01d6
            if (r4 != r9) goto L_0x01d6
            r7 = r11
            gnu.expr.TypeValue r7 = (gnu.expr.TypeValue) r7
            gnu.mapping.Procedure r7 = r7.getConstructor()
            if (r7 == 0) goto L_0x01d6
            r1 = 1
            int r13 = r13 - r1
            gnu.expr.Expression[] r3 = new gnu.expr.Expression[r13]
            r4 = 0
            java.lang.System.arraycopy(r12, r1, r3, r4, r13)
            gnu.expr.ApplyExp r1 = new gnu.expr.ApplyExp
            r1.<init>((gnu.mapping.Procedure) r7, (gnu.expr.Expression[]) r3)
            gnu.expr.Expression r0 = r0.visit((gnu.expr.Expression) r1, (gnu.bytecode.Type) r2)
            return r0
        L_0x01d6:
            if (r5 != 0) goto L_0x01da
            r14 = 0
            goto L_0x01e4
        L_0x01da:
            gnu.bytecode.ClassType r7 = r5.curClass
            if (r7 == 0) goto L_0x01e1
            gnu.bytecode.ClassType r7 = r5.curClass
            goto L_0x01e3
        L_0x01e1:
            gnu.bytecode.ClassType r7 = r5.mainClass
        L_0x01e3:
            r14 = r7
        L_0x01e4:
            gnu.expr.PrimProcedure[] r7 = getMethods(r11, r10, r14, r3)     // Catch:{ Exception -> 0x05c2 }
            int r15 = gnu.kawa.reflect.ClassMethods.selectApplicable((gnu.expr.PrimProcedure[]) r7, (int) r6)     // Catch:{ Exception -> 0x05c2 }
            r22 = 32
            if (r4 != r9) goto L_0x0379
            r23 = r6
            r9 = 1
            int r6 = hasKeywordArgument(r9, r12)
            int r9 = r12.length
            if (r6 < r9) goto L_0x0222
            if (r15 > 0) goto L_0x0217
            r24 = r10
            r9 = 1
            gnu.bytecode.Type[] r10 = new gnu.bytecode.Type[r9]
            gnu.bytecode.ClassType r9 = gnu.expr.Compilation.typeClassType
            r16 = 0
            r10[r16] = r9
            long r9 = gnu.kawa.reflect.ClassMethods.selectApplicable((gnu.expr.PrimProcedure[]) r7, (gnu.bytecode.Type[]) r10)
            long r9 = r9 >> r22
            r25 = 1
            int r27 = (r9 > r25 ? 1 : (r9 == r25 ? 0 : -1))
            if (r27 != 0) goto L_0x0219
            goto L_0x0224
        L_0x0217:
            r24 = r10
        L_0x0219:
            r26 = r4
            r21 = r13
            r25 = r14
        L_0x021f:
            r9 = 0
            goto L_0x0384
        L_0x0222:
            r24 = r10
        L_0x0224:
            java.lang.Object[] r9 = checkKeywords(r11, r12, r6, r14)
            int r10 = r9.length
            r25 = r14
            r14 = 2
            int r10 = r10 * 2
            int r14 = r12.length
            int r14 = r14 - r6
            r26 = r4
            java.lang.String r4 = "add"
            if (r10 == r14) goto L_0x0248
            gnu.expr.Language r10 = r3.language
            r21 = r13
            r13 = 0
            r14 = 86
            gnu.expr.PrimProcedure[] r10 = gnu.kawa.reflect.ClassMethods.getMethods(r11, r4, r14, r13, r10)
            r13 = 2
            int r10 = gnu.kawa.reflect.ClassMethods.selectApplicable((gnu.expr.PrimProcedure[]) r10, (int) r13)
            if (r10 <= 0) goto L_0x021f
        L_0x0248:
            r10 = 0
            r13 = 0
        L_0x024b:
            int r14 = r9.length
            if (r13 >= r14) goto L_0x0278
            r14 = r9[r13]
            boolean r14 = r14 instanceof java.lang.String
            if (r14 == 0) goto L_0x0275
            if (r10 != 0) goto L_0x0261
            java.lang.StringBuffer r10 = new java.lang.StringBuffer
            r10.<init>()
            java.lang.String r14 = "no field or setter "
            r10.append(r14)
            goto L_0x0266
        L_0x0261:
            java.lang.String r14 = ", "
            r10.append(r14)
        L_0x0266:
            r14 = 96
            r10.append(r14)
            r14 = r9[r13]
            r10.append(r14)
            r14 = 39
            r10.append(r14)
        L_0x0275:
            int r13 = r13 + 1
            goto L_0x024b
        L_0x0278:
            if (r10 == 0) goto L_0x0290
            java.lang.String r0 = " in class "
            r10.append(r0)
            java.lang.String r0 = r11.getName()
            r10.append(r0)
            java.lang.String r0 = r10.toString()
            r2 = 119(0x77, float:1.67E-43)
            r5.error(r2, r0)
            return r1
        L_0x0290:
            int r5 = r12.length
            if (r6 >= r5) goto L_0x02a9
            gnu.expr.Expression[] r5 = new gnu.expr.Expression[r6]
            r10 = 0
            java.lang.System.arraycopy(r12, r10, r5, r10, r6)
            gnu.expr.ApplyExp r7 = new gnu.expr.ApplyExp
            gnu.expr.Expression r8 = r30.getFunction()
            r7.<init>((gnu.expr.Expression) r8, (gnu.expr.Expression[]) r5)
            gnu.expr.Expression r5 = r0.visit((gnu.expr.Expression) r7, (gnu.bytecode.Type) r11)
            gnu.expr.ApplyExp r5 = (gnu.expr.ApplyExp) r5
            goto L_0x02b6
        L_0x02a9:
            r10 = 0
            gnu.expr.ApplyExp r5 = new gnu.expr.ApplyExp
            r7 = r7[r10]
            r13 = 1
            gnu.expr.Expression[] r14 = new gnu.expr.Expression[r13]
            r14[r10] = r8
            r5.<init>((gnu.mapping.Procedure) r7, (gnu.expr.Expression[]) r14)
        L_0x02b6:
            r5.setType(r11)
            int r7 = r12.length
            if (r7 <= 0) goto L_0x0370
            r7 = 0
        L_0x02be:
            int r8 = r9.length
            if (r7 >= r8) goto L_0x030f
            r8 = r9[r7]
            boolean r10 = r8 instanceof gnu.bytecode.Method
            if (r10 == 0) goto L_0x02d2
            r10 = r8
            gnu.bytecode.Method r10 = (gnu.bytecode.Method) r10
            gnu.bytecode.Type[] r10 = r10.getParameterTypes()
            r13 = 0
            r10 = r10[r13]
            goto L_0x02df
        L_0x02d2:
            boolean r10 = r8 instanceof gnu.bytecode.Field
            if (r10 == 0) goto L_0x02de
            r10 = r8
            gnu.bytecode.Field r10 = (gnu.bytecode.Field) r10
            gnu.bytecode.Type r10 = r10.getType()
            goto L_0x02df
        L_0x02de:
            r10 = 0
        L_0x02df:
            if (r10 == 0) goto L_0x02e7
            gnu.expr.Language r13 = r3.language
            gnu.bytecode.Type r10 = r13.getLangTypeFor(r10)
        L_0x02e7:
            int r13 = r7 * 2
            int r13 = r13 + r6
            r14 = 1
            int r13 = r13 + r14
            r13 = r12[r13]
            gnu.expr.Expression r10 = r0.visit((gnu.expr.Expression) r13, (gnu.bytecode.Type) r10)
            r13 = 3
            gnu.expr.Expression[] r15 = new gnu.expr.Expression[r13]
            r13 = 0
            r15[r13] = r5
            gnu.expr.QuoteExp r5 = new gnu.expr.QuoteExp
            r5.<init>(r8)
            r15[r14] = r5
            r5 = 2
            r15[r5] = r10
            gnu.expr.ApplyExp r5 = new gnu.expr.ApplyExp
            gnu.kawa.reflect.SlotSet r8 = gnu.kawa.reflect.SlotSet.setFieldReturnObject
            r5.<init>((gnu.mapping.Procedure) r8, (gnu.expr.Expression[]) r15)
            r5.setType(r11)
            int r7 = r7 + 1
            goto L_0x02be
        L_0x030f:
            int r3 = r12.length
            if (r6 != r3) goto L_0x0314
            r3 = 1
            goto L_0x0319
        L_0x0314:
            int r3 = r9.length
            r7 = 2
            int r3 = r3 * 2
            int r3 = r3 + r6
        L_0x0319:
            int r6 = r12.length
            if (r3 >= r6) goto L_0x0370
            gnu.expr.LetExp r6 = new gnu.expr.LetExp
            r7 = 1
            gnu.expr.Expression[] r8 = new gnu.expr.Expression[r7]
            r7 = 0
            r8[r7] = r5
            r6.<init>(r8)
            r7 = 0
            r9 = r7
            java.lang.String r9 = (java.lang.String) r9
            gnu.expr.Declaration r8 = r6.addDeclaration(r7, r11)
            r8.noteValue(r5)
            gnu.expr.BeginExp r5 = new gnu.expr.BeginExp
            r5.<init>()
        L_0x0339:
            int r7 = r12.length
            if (r3 >= r7) goto L_0x0365
            r7 = 3
            gnu.expr.Expression[] r9 = new gnu.expr.Expression[r7]
            gnu.expr.ReferenceExp r10 = new gnu.expr.ReferenceExp
            r10.<init>((gnu.expr.Declaration) r8)
            r11 = 0
            r9[r11] = r10
            gnu.expr.QuoteExp r10 = gnu.expr.QuoteExp.getInstance(r4)
            r11 = 1
            r9[r11] = r10
            r10 = r12[r3]
            r11 = 2
            r9[r11] = r10
            gnu.expr.ApplyExp r10 = new gnu.expr.ApplyExp
            gnu.kawa.reflect.Invoke r11 = gnu.kawa.reflect.Invoke.invoke
            r10.<init>((gnu.mapping.Procedure) r11, (gnu.expr.Expression[]) r9)
            r9 = 0
            gnu.expr.Expression r10 = r0.visit((gnu.expr.Expression) r10, (gnu.bytecode.Type) r9)
            r5.add(r10)
            int r3 = r3 + 1
            goto L_0x0339
        L_0x0365:
            gnu.expr.ReferenceExp r3 = new gnu.expr.ReferenceExp
            r3.<init>((gnu.expr.Declaration) r8)
            r5.add(r3)
            r6.body = r5
            r5 = r6
        L_0x0370:
            gnu.expr.Expression r1 = r5.setLine((gnu.expr.Expression) r1)
            gnu.expr.Expression r0 = r0.checkType(r1, r2)
            return r0
        L_0x0379:
            r26 = r4
            r23 = r6
            r24 = r10
            r21 = r13
            r25 = r14
            r9 = 0
        L_0x0384:
            if (r15 < 0) goto L_0x0436
            r3 = 1
        L_0x0387:
            r4 = r21
            if (r3 >= r4) goto L_0x041a
            int r13 = r4 + -1
            if (r3 != r13) goto L_0x0392
            r6 = 1
            goto L_0x0393
        L_0x0392:
            r6 = 0
        L_0x0393:
            r13 = r26
            r8 = 80
            if (r13 != r8) goto L_0x039d
            r8 = 2
            if (r3 == r8) goto L_0x03a5
            goto L_0x039e
        L_0x039d:
            r8 = 2
        L_0x039e:
            r10 = 78
            if (r13 == r10) goto L_0x03a8
            r10 = 1
            if (r3 != r10) goto L_0x03a8
        L_0x03a5:
            r8 = r9
            goto L_0x0407
        L_0x03a8:
            r10 = 80
            if (r13 != r10) goto L_0x03b1
            r10 = 1
            if (r3 != r10) goto L_0x03b1
            r8 = r11
            goto L_0x0407
        L_0x03b1:
            if (r15 <= 0) goto L_0x0406
            r10 = 78
            if (r13 != r10) goto L_0x03b9
            r14 = 1
            goto L_0x03bb
        L_0x03b9:
            r14 = r19
        L_0x03bb:
            int r14 = r3 - r14
            r8 = r9
            r9 = 0
        L_0x03bf:
            if (r9 >= r15) goto L_0x0407
            r10 = r7[r9]
            r2 = 83
            if (r13 == r2) goto L_0x03cf
            boolean r2 = r10.takesTarget()
            if (r2 == 0) goto L_0x03cf
            r2 = 1
            goto L_0x03d0
        L_0x03cf:
            r2 = 0
        L_0x03d0:
            int r2 = r2 + r14
            if (r6 == 0) goto L_0x03e3
            boolean r18 = r10.takesVarArgs()
            if (r18 == 0) goto L_0x03e3
            r18 = r6
            int r6 = r10.minArgs()
            if (r2 != r6) goto L_0x03e5
            r8 = 0
            goto L_0x03fa
        L_0x03e3:
            r18 = r6
        L_0x03e5:
            gnu.bytecode.Type r2 = r10.getParameterType(r2)
            if (r9 != 0) goto L_0x03ed
            r8 = r2
            goto L_0x03fa
        L_0x03ed:
            boolean r6 = r2 instanceof gnu.bytecode.PrimType
            boolean r10 = r8 instanceof gnu.bytecode.PrimType
            if (r6 == r10) goto L_0x03f5
            r8 = 0
            goto L_0x03fa
        L_0x03f5:
            gnu.bytecode.Type r2 = gnu.bytecode.Type.lowestCommonSuperType(r8, r2)
            r8 = r2
        L_0x03fa:
            if (r8 != 0) goto L_0x03fd
            goto L_0x0407
        L_0x03fd:
            int r9 = r9 + 1
            r2 = r32
            r6 = r18
            r10 = 78
            goto L_0x03bf
        L_0x0406:
            r8 = 0
        L_0x0407:
            r2 = r12[r3]
            gnu.expr.Expression r2 = r0.visit((gnu.expr.Expression) r2, (gnu.bytecode.Type) r8)
            r12[r3] = r2
            int r3 = r3 + 1
            r2 = r32
            r21 = r4
            r26 = r13
            r9 = 0
            goto L_0x0387
        L_0x041a:
            r13 = r26
            r2 = r23
            r6 = r7
            r3 = r7
            r7 = r11
            r8 = r12
            r14 = 78
            r9 = r2
            r28 = r24
            r10 = r19
            r29 = r11
            r11 = r20
            long r6 = selectApplicable(r6, r7, r8, r9, r10, r11)
            long r8 = r6 >> r22
            int r8 = (int) r8
            int r7 = (int) r6
            goto L_0x0445
        L_0x0436:
            r3 = r7
            r29 = r11
            r4 = r21
            r2 = r23
            r28 = r24
            r13 = r26
            r14 = 78
            r7 = 0
            r8 = 0
        L_0x0445:
            int r11 = r3.length
            int r6 = r8 + r7
            if (r6 != 0) goto L_0x0473
            if (r13 != r14) goto L_0x0473
            java.lang.String r2 = "valueOf"
            gnu.kawa.reflect.Invoke r3 = gnu.kawa.reflect.Invoke.invokeStatic
            r7 = r25
            r10 = r29
            gnu.expr.PrimProcedure[] r2 = getMethods(r10, r2, r7, r3)
            r3 = 1
            int r4 = r4 - r3
            r3 = -1
            r19 = 1
            r6 = r2
            r7 = r10
            r8 = r12
            r9 = r4
            r18 = r10
            r10 = r19
            r21 = r11
            r11 = r3
            long r6 = selectApplicable(r6, r7, r8, r9, r10, r11)
            long r8 = r6 >> r22
            int r8 = (int) r8
            int r7 = (int) r6
            r6 = r4
            goto L_0x0479
        L_0x0473:
            r21 = r11
            r18 = r29
            r6 = r2
            r2 = r3
        L_0x0479:
            int r3 = r8 + r7
            java.lang.String r4 = "' in "
            if (r3 != 0) goto L_0x04f1
            r3 = 80
            if (r13 == r3) goto L_0x048d
            boolean r3 = r5.warnInvokeUnknownMethod()
            if (r3 == 0) goto L_0x048a
            goto L_0x048d
        L_0x048a:
            r3 = 1
            goto L_0x058e
        L_0x048d:
            if (r13 != r14) goto L_0x04a5
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r9 = r28
            java.lang.StringBuilder r3 = r3.append(r9)
            java.lang.String r7 = "/valueOf"
            java.lang.StringBuilder r3 = r3.append(r7)
            java.lang.String r10 = r3.toString()
            goto L_0x04a8
        L_0x04a5:
            r9 = r28
            r10 = r9
        L_0x04a8:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            int r7 = r2.length
            int r11 = r21 + r7
            if (r11 != 0) goto L_0x04b8
            java.lang.String r7 = "no accessible method '"
            r3.append(r7)
            goto L_0x04d1
        L_0x04b8:
            r7 = -983040(0xfffffffffff10000, float:NaN)
            if (r15 != r7) goto L_0x04c2
            java.lang.String r7 = "too few arguments for method '"
            r3.append(r7)
            goto L_0x04d1
        L_0x04c2:
            r7 = -917504(0xfffffffffff20000, float:NaN)
            if (r15 != r7) goto L_0x04cc
            java.lang.String r7 = "too many arguments for method '"
            r3.append(r7)
            goto L_0x04d1
        L_0x04cc:
            java.lang.String r7 = "no possibly applicable method '"
            r3.append(r7)
        L_0x04d1:
            r3.append(r10)
            r3.append(r4)
            java.lang.String r4 = r18.getName()
            r3.append(r4)
            r4 = 80
            if (r13 != r4) goto L_0x04e5
            r15 = 101(0x65, float:1.42E-43)
            goto L_0x04e7
        L_0x04e5:
            r15 = 119(0x77, float:1.67E-43)
        L_0x04e7:
            java.lang.String r3 = r3.toString()
            r5.error(r15, r3)
            r3 = 1
            goto L_0x058e
        L_0x04f1:
            r9 = r28
            r3 = 1
            if (r8 == r3) goto L_0x058c
            if (r8 != 0) goto L_0x04fc
            if (r7 != r3) goto L_0x04fc
            goto L_0x058c
        L_0x04fc:
            if (r8 <= 0) goto L_0x0557
            int r7 = gnu.mapping.MethodProc.mostSpecific((gnu.mapping.MethodProc[]) r2, (int) r8)
            if (r7 >= 0) goto L_0x051e
            r10 = 83
            if (r13 != r10) goto L_0x051e
            r10 = 0
        L_0x0509:
            if (r10 >= r8) goto L_0x051b
            r11 = r2[r10]
            boolean r11 = r11.getStaticFlag()
            if (r11 == 0) goto L_0x0518
            if (r7 < 0) goto L_0x0517
            goto L_0x0520
        L_0x0517:
            r7 = r10
        L_0x0518:
            int r10 = r10 + 1
            goto L_0x0509
        L_0x051b:
            r17 = r7
            goto L_0x0520
        L_0x051e:
            r17 = r7
        L_0x0520:
            if (r17 >= 0) goto L_0x058e
            r7 = 80
            if (r13 == r7) goto L_0x052c
            boolean r7 = r5.warnInvokeUnknownMethod()
            if (r7 == 0) goto L_0x058e
        L_0x052c:
            java.lang.StringBuffer r7 = new java.lang.StringBuffer
            r7.<init>()
            java.lang.String r10 = "more than one definitely applicable method `"
            r7.append(r10)
            r7.append(r9)
            r7.append(r4)
            java.lang.String r4 = r18.getName()
            r7.append(r4)
            append(r2, r8, r7)
            r4 = 80
            if (r13 != r4) goto L_0x054d
            r15 = 101(0x65, float:1.42E-43)
            goto L_0x054f
        L_0x054d:
            r15 = 119(0x77, float:1.67E-43)
        L_0x054f:
            java.lang.String r4 = r7.toString()
            r5.error(r15, r4)
            goto L_0x058e
        L_0x0557:
            r8 = 80
            if (r13 == r8) goto L_0x0561
            boolean r8 = r5.warnInvokeUnknownMethod()
            if (r8 == 0) goto L_0x058e
        L_0x0561:
            java.lang.StringBuffer r8 = new java.lang.StringBuffer
            r8.<init>()
            java.lang.String r10 = "more than one possibly applicable method '"
            r8.append(r10)
            r8.append(r9)
            r8.append(r4)
            java.lang.String r4 = r18.getName()
            r8.append(r4)
            append(r2, r7, r8)
            r4 = 80
            if (r13 != r4) goto L_0x0582
            r15 = 101(0x65, float:1.42E-43)
            goto L_0x0584
        L_0x0582:
            r15 = 119(0x77, float:1.67E-43)
        L_0x0584:
            java.lang.String r4 = r8.toString()
            r5.error(r15, r4)
            goto L_0x058e
        L_0x058c:
            r17 = 0
        L_0x058e:
            if (r17 < 0) goto L_0x05e2
            gnu.expr.Expression[] r4 = new gnu.expr.Expression[r6]
            r2 = r2[r17]
            r2.takesVarArgs()
            if (r20 < 0) goto L_0x05a1
            r5 = r12[r20]
            r7 = 0
            r4[r7] = r5
            r14 = 1
            goto L_0x05a3
        L_0x05a1:
            r7 = 0
            r14 = 0
        L_0x05a3:
            r3 = r19
        L_0x05a5:
            int r5 = r12.length
            if (r3 >= r5) goto L_0x05b3
            if (r14 >= r6) goto L_0x05b3
            r5 = r12[r3]
            r4[r14] = r5
            int r3 = r3 + 1
            int r14 = r14 + 1
            goto L_0x05a5
        L_0x05b3:
            gnu.expr.ApplyExp r3 = new gnu.expr.ApplyExp
            r3.<init>((gnu.mapping.Procedure) r2, (gnu.expr.Expression[]) r4)
            r3.setLine((gnu.expr.Expression) r1)
            r1 = r32
            gnu.expr.Expression r0 = r0.visitApplyOnly(r3, r1)
            return r0
        L_0x05c2:
            r0 = move-exception
            r18 = r11
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "unknown class: "
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r2 = r18.getName()
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            r2 = 119(0x77, float:1.67E-43)
            r5.error(r2, r0)
            return r1
        L_0x05e2:
            r30.visitArgs(r31)
            return r1
        L_0x05e6:
            r30.visitArgs(r31)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.reflect.CompileInvoke.validateApplyInvoke(gnu.expr.ApplyExp, gnu.expr.InlineCalls, gnu.bytecode.Type, gnu.mapping.Procedure):gnu.expr.Expression");
    }

    static Object[] checkKeywords(ObjectType type, Expression[] args, int start, ClassType caller) {
        int len = args.length;
        int npairs = 0;
        while ((npairs * 2) + start + 1 < len && (args[(npairs * 2) + start].valueIfConstant() instanceof Keyword)) {
            npairs++;
        }
        Object[] fields = new Object[npairs];
        for (int i = 0; i < npairs; i++) {
            String name = ((Keyword) args[(i * 2) + start].valueIfConstant()).getName();
            Member slot = SlotSet.lookupMember(type, name, caller);
            if (slot == null) {
                slot = type.getMethod(ClassExp.slotToMethodName("add", name), SlotSet.type1Array);
            }
            fields[i] = slot != null ? slot : name;
        }
        return fields;
    }

    private static String getMethodName(Expression[] args, char kind) {
        if (kind == 'N') {
            return "<init>";
        }
        int nameIndex = kind == 'P' ? 2 : 1;
        if (args.length >= nameIndex + 1) {
            return ClassMethods.checkName(args[nameIndex], false);
        }
        return null;
    }

    private static void append(PrimProcedure[] methods, int mcount, StringBuffer sbuf) {
        for (int i = 0; i < mcount; i++) {
            sbuf.append("\n  candidate: ");
            sbuf.append(methods[i]);
        }
    }

    protected static PrimProcedure[] getMethods(ObjectType ctype, String mname, ClassType caller, Invoke iproc) {
        int kind = iproc.kind;
        char c = 'P';
        if (kind != 80) {
            c = (kind == 42 || kind == 86) ? 'V' : 0;
        }
        return ClassMethods.getMethods(ctype, mname, c, caller, iproc.language);
    }

    static int hasKeywordArgument(int argsStartIndex, Expression[] args) {
        for (int i = argsStartIndex; i < args.length; i++) {
            if (args[i].valueIfConstant() instanceof Keyword) {
                return i;
            }
        }
        return args.length;
    }

    private static long selectApplicable(PrimProcedure[] methods, ObjectType ctype, Expression[] args, int margsLength, int argsStartIndex, int objIndex) {
        Type[] atypes = new Type[margsLength];
        int dst = 0;
        if (objIndex >= 0) {
            atypes[0] = ctype;
            dst = 0 + 1;
        }
        int src = argsStartIndex;
        while (src < args.length && dst < atypes.length) {
            Expression arg = args[src];
            Type atype = null;
            if (InlineCalls.checkIntValue(arg) != null) {
                atype = Type.intType;
            } else if (InlineCalls.checkLongValue(arg) != null) {
                atype = Type.longType;
            } else if (0 == 0) {
                atype = arg.getType();
            }
            atypes[dst] = atype;
            src++;
            dst++;
        }
        return ClassMethods.selectApplicable(methods, atypes);
    }

    public static synchronized PrimProcedure getStaticMethod(ClassType type, String name, Expression[] args) {
        PrimProcedure primProcedure;
        int index;
        synchronized (CompileInvoke.class) {
            primProcedure = null;
            PrimProcedure[] methods = getMethods(type, name, (ClassType) null, Invoke.invokeStatic);
            long num = selectApplicable(methods, type, args, args.length, 0, -1);
            int okCount = (int) (num >> 32);
            int maybeCount = (int) num;
            if (methods == null) {
                index = -1;
            } else if (okCount > 0) {
                index = MethodProc.mostSpecific((MethodProc[]) methods, okCount);
            } else if (maybeCount == 1) {
                index = 0;
            } else {
                index = -1;
            }
            if (index >= 0) {
                primProcedure = methods[index];
            }
        }
        return primProcedure;
    }
}
