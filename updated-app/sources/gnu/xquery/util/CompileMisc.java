package gnu.xquery.util;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.InlineCalls;
import gnu.expr.LambdaExp;
import gnu.expr.LetExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.Target;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.ValuesMap;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.xml.CoerceNodes;
import gnu.kawa.xml.SortNodes;
import gnu.kawa.xml.XDataType;
import gnu.mapping.Procedure;
import gnu.math.IntNum;
import gnu.xquery.lang.XQuery;

public class CompileMisc {
    static final Method castMethod = typeXDataType.getDeclaredMethod("cast", 1);
    static final Method castableMethod = typeXDataType.getDeclaredMethod("castable", 1);
    static final ClassType typeTuples = ClassType.make("gnu.xquery.util.OrderedTuples");
    static final ClassType typeXDataType = ClassType.make("gnu.kawa.xml.XDataType");

    public static Expression validateCompare(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression folded = exp.inlineIfConstant(proc, visitor);
        if (folded != exp) {
            return folded;
        }
        Compare cproc = (Compare) proc;
        if ((cproc.flags & 32) == 0) {
            exp = new ApplyExp(ClassType.make("gnu.xquery.util.Compare").getDeclaredMethod("apply", 4), new QuoteExp(IntNum.make(cproc.flags)), exp.getArg(0), exp.getArg(1), QuoteExp.nullExp);
        }
        if (exp.getTypeRaw() == null) {
            exp.setType(XDataType.booleanType);
        }
        return exp;
    }

    public static Expression validateBooleanValue(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length == 1) {
            Expression arg = args[0];
            Type type = arg.getType();
            if (type == XDataType.booleanType) {
                return arg;
            }
            if (type == null) {
                exp.setType(XDataType.booleanType);
            }
            if (arg instanceof QuoteExp) {
                try {
                    return BooleanValue.booleanValue(((QuoteExp) arg).getValue()) ? XQuery.trueExp : XQuery.falseExp;
                } catch (Throwable th) {
                    visitor.getMessages().error('e', "cannot convert to a boolean");
                    return new ErrorExp("cannot convert to a boolean");
                }
            }
        }
        return exp;
    }

    public static Expression validateArithOp(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        return exp;
    }

    public static Expression validateApplyValuesFilter(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        Type seqType;
        Method sizeMethod;
        ApplyExp applyExp = exp;
        ValuesFilter vproc = (ValuesFilter) proc;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        Expression exp2 = args[1];
        if (exp2 instanceof LambdaExp) {
            LambdaExp lambdaExp = (LambdaExp) exp2;
            LambdaExp lexp2 = lambdaExp;
            if (lambdaExp.min_args != 3) {
                InlineCalls inlineCalls = visitor;
                Type type = required;
                ValuesFilter valuesFilter = vproc;
                Expression[] expressionArr = args;
                Expression expression = exp2;
            } else if (lexp2.max_args != 3) {
                InlineCalls inlineCalls2 = visitor;
                Type type2 = required;
                ValuesFilter valuesFilter2 = vproc;
                Expression[] expressionArr2 = args;
                Expression expression2 = exp2;
            } else {
                applyExp.setType(args[0].getType());
                Compilation parser = visitor.getCompilation();
                Declaration dotArg = lexp2.firstDecl();
                Declaration posArg = dotArg.nextDecl();
                Declaration lastArg = posArg.nextDecl();
                lexp2.setInlineOnly(true);
                lexp2.returnContinuation = applyExp;
                lexp2.inlineHome = visitor.getCurrentLambda();
                lexp2.remove(posArg, lastArg);
                lexp2.min_args = 2;
                lexp2.max_args = 2;
                if (!lastArg.getCanRead() && vproc.kind != 'R') {
                    return applyExp;
                }
                parser.letStart();
                Expression seq = args[0];
                if (vproc.kind == 'P') {
                    seqType = seq.getType();
                    sizeMethod = Compilation.typeValues.getDeclaredMethod("countValues", 1);
                } else {
                    seqType = SortNodes.typeSortedNodes;
                    seq = new ApplyExp((Procedure) SortNodes.sortNodes, seq);
                    sizeMethod = CoerceNodes.typeNodes.getDeclaredMethod("size", 0);
                }
                Declaration sequence = parser.letVariable("sequence", seqType, seq);
                parser.letEnter();
                Expression pred = lexp2.body;
                Type predType = lexp2.body.getType();
                if (predType != XDataType.booleanType) {
                    Expression[] expressionArr3 = args;
                    Type type3 = predType;
                    Expression expression3 = exp2;
                    pred = new ApplyExp(ValuesFilter.matchesMethod, pred, new ReferenceExp(posArg));
                } else {
                    Type type4 = predType;
                    Expression expression4 = exp2;
                }
                if (vproc.kind == 'R') {
                    Declaration posIncoming = new Declaration((Object) null, (Type) Type.intType);
                    ValuesFilter valuesFilter3 = vproc;
                    Expression expression5 = seq;
                    ApplyExp applyExp2 = new ApplyExp((Procedure) AddOp.$Mn, new ReferenceExp(lastArg), new ReferenceExp(posIncoming));
                    ApplyExp applyExp3 = applyExp2;
                    LetExp let = new LetExp(new Expression[]{new ApplyExp((Procedure) AddOp.$Pl, applyExp2, new QuoteExp(IntNum.one()))});
                    lexp2.replaceFollowing(dotArg, posIncoming);
                    let.add(posArg);
                    let.body = pred;
                    pred = let;
                } else {
                    Expression expression6 = seq;
                }
                lexp2.body = new IfExp(pred, new ReferenceExp(dotArg), QuoteExp.voidExp);
                ApplyExp doMap = new ApplyExp((Procedure) ValuesMap.valuesMapWithPos, lexp2, new ReferenceExp(sequence));
                doMap.setType(dotArg.getType());
                lexp2.returnContinuation = doMap;
                LetExp let2 = new LetExp(new Expression[]{new ApplyExp(sizeMethod, new ReferenceExp(sequence))});
                let2.add(lastArg);
                let2.body = gnu.kawa.functions.CompileMisc.validateApplyValuesMap(doMap, visitor, required, ValuesMap.valuesMapWithPos);
                return parser.letDone(let2);
            }
        } else {
            InlineCalls inlineCalls3 = visitor;
            Type type5 = required;
            ValuesFilter valuesFilter4 = vproc;
            Expression[] expressionArr4 = args;
            Expression expression7 = exp2;
        }
        return applyExp;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v1, resolved type: gnu.expr.Expression} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: gnu.expr.ApplyExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: gnu.expr.ApplyExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v9, resolved type: gnu.expr.Expression} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r20v1, resolved type: gnu.expr.ApplyExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: gnu.expr.ApplyExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v11, resolved type: gnu.expr.ApplyExp} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x01a6  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x021b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static gnu.expr.Expression validateApplyRelativeStep(gnu.expr.ApplyExp r23, gnu.expr.InlineCalls r24, gnu.bytecode.Type r25, gnu.mapping.Procedure r26) {
        /*
            r0 = r23
            r23.visitArgs(r24)
            gnu.expr.Expression[] r1 = r23.getArgs()
            r2 = 0
            r3 = r1[r2]
            r4 = 1
            r5 = r1[r4]
            gnu.expr.Compilation r6 = r24.getCompilation()
            boolean r7 = r5 instanceof gnu.expr.LambdaExp
            if (r7 == 0) goto L_0x0225
            boolean r7 = r6.mustCompile
            if (r7 == 0) goto L_0x0225
            r7 = r5
            gnu.expr.LambdaExp r7 = (gnu.expr.LambdaExp) r7
            r8 = r7
            int r7 = r7.min_args
            r9 = 3
            if (r7 != r9) goto L_0x0220
            int r7 = r8.max_args
            if (r7 == r9) goto L_0x002e
            r19 = r1
            r16 = r3
            goto L_0x0229
        L_0x002e:
            r8.setInlineOnly(r4)
            r8.returnContinuation = r0
            gnu.expr.LambdaExp r7 = r24.getCurrentLambda()
            r8.inlineHome = r7
            gnu.expr.Expression r5 = r8.body
            gnu.expr.Declaration r7 = r8.firstDecl()
            gnu.expr.Declaration r9 = r7.nextDecl()
            gnu.expr.Declaration r10 = r9.nextDecl()
            gnu.expr.Declaration r11 = r10.nextDecl()
            r9.setNext(r11)
            r11 = 0
            r10.setNext(r11)
            r12 = 2
            r8.min_args = r12
            r8.max_args = r12
            gnu.bytecode.Type r13 = r3.getType()
            r14 = -3
            if (r13 == 0) goto L_0x009a
            gnu.kawa.xml.NodeType r15 = gnu.kawa.xml.NodeType.anyNodeTest
            int r15 = r15.compare(r13)
            if (r15 != r14) goto L_0x009a
            gnu.expr.Compilation r2 = r24.getCompilation()
            gnu.expr.Language r2 = r2.getLanguage()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r11 = "step input is "
            java.lang.StringBuilder r4 = r4.append(r11)
            java.lang.String r11 = r2.formatType(r13)
            java.lang.StringBuilder r4 = r4.append(r11)
            java.lang.String r11 = " - not a node sequence"
            java.lang.StringBuilder r4 = r4.append(r11)
            java.lang.String r4 = r4.toString()
            gnu.text.SourceMessages r11 = r24.getMessages()
            r12 = 101(0x65, float:1.42E-43)
            r11.error(r12, r4)
            gnu.expr.ErrorExp r11 = new gnu.expr.ErrorExp
            r11.<init>(r4)
            return r11
        L_0x009a:
            gnu.bytecode.Type r15 = r23.getTypeRaw()
            if (r15 == 0) goto L_0x00a4
            gnu.bytecode.ClassType r14 = gnu.bytecode.Type.pointer_type
            if (r15 != r14) goto L_0x00c2
        L_0x00a4:
            gnu.bytecode.Type r14 = r5.getType()
            gnu.bytecode.Type r12 = gnu.kawa.reflect.OccurrenceType.itemPrimeType(r14)
            gnu.kawa.xml.NodeType r11 = gnu.kawa.xml.NodeType.anyNodeTest
            int r11 = r11.compare(r12)
            if (r11 < 0) goto L_0x00b9
            gnu.bytecode.Type r15 = gnu.kawa.xml.NodeSetType.getInstance(r12)
            goto L_0x00bf
        L_0x00b9:
            r4 = -1
            gnu.bytecode.Type r4 = gnu.kawa.reflect.OccurrenceType.getInstance(r12, r2, r4)
            r15 = r4
        L_0x00bf:
            r0.setType(r15)
        L_0x00c2:
            boolean r4 = r10.getCanRead()
            if (r4 == 0) goto L_0x012b
            gnu.bytecode.ClassType r4 = gnu.kawa.xml.CoerceNodes.typeNodes
            r6.letStart()
            gnu.expr.ApplyExp r11 = new gnu.expr.ApplyExp
            gnu.kawa.xml.CoerceNodes r12 = gnu.kawa.xml.CoerceNodes.coerceNodes
            r19 = r1
            r14 = 1
            gnu.expr.Expression[] r1 = new gnu.expr.Expression[r14]
            r1[r2] = r3
            r11.<init>((gnu.mapping.Procedure) r12, (gnu.expr.Expression[]) r1)
            r1 = 0
            gnu.expr.Declaration r1 = r6.letVariable(r1, r4, r11)
            r6.letEnter()
            java.lang.String r11 = "size"
            gnu.bytecode.Method r11 = r4.getDeclaredMethod((java.lang.String) r11, (int) r2)
            gnu.expr.ApplyExp r12 = new gnu.expr.ApplyExp
            gnu.expr.Expression[] r2 = new gnu.expr.Expression[r14]
            gnu.expr.ReferenceExp r14 = new gnu.expr.ReferenceExp
            r14.<init>((gnu.expr.Declaration) r1)
            r16 = 0
            r2[r16] = r14
            r12.<init>((gnu.bytecode.Method) r11, (gnu.expr.Expression[]) r2)
            r2 = r12
            gnu.expr.LetExp r12 = new gnu.expr.LetExp
            r18 = r4
            r14 = 1
            gnu.expr.Expression[] r4 = new gnu.expr.Expression[r14]
            r4[r16] = r2
            r12.<init>(r4)
            r4 = r12
            r4.addDeclaration((gnu.expr.Declaration) r10)
            gnu.expr.ApplyExp r12 = new gnu.expr.ApplyExp
            gnu.expr.Expression r14 = r23.getFunction()
            r20 = r2
            r2 = 2
            gnu.expr.Expression[] r2 = new gnu.expr.Expression[r2]
            r21 = r7
            gnu.expr.ReferenceExp r7 = new gnu.expr.ReferenceExp
            r7.<init>((gnu.expr.Declaration) r1)
            r2[r16] = r7
            r7 = 1
            r2[r7] = r8
            r12.<init>((gnu.expr.Expression) r14, (gnu.expr.Expression[]) r2)
            r4.body = r12
            gnu.expr.LetExp r2 = r6.letDone(r4)
            return r2
        L_0x012b:
            r19 = r1
            r21 = r7
            r1 = r23
            boolean r2 = r5 instanceof gnu.expr.ApplyExp
            if (r2 == 0) goto L_0x019a
            r2 = r5
            gnu.expr.ApplyExp r2 = (gnu.expr.ApplyExp) r2
            gnu.expr.Expression r4 = r2.getFunction()
            java.lang.Object r4 = r4.valueIfConstant()
            boolean r7 = r4 instanceof gnu.xquery.util.ValuesFilter
            if (r7 == 0) goto L_0x0197
            gnu.expr.Expression[] r7 = r2.getArgs()
            r11 = 1
            r7 = r7[r11]
            r11 = r7
            boolean r7 = r7 instanceof gnu.expr.LambdaExp
            if (r7 == 0) goto L_0x0194
            r7 = r11
            gnu.expr.LambdaExp r7 = (gnu.expr.LambdaExp) r7
            gnu.expr.Declaration r12 = r7.firstDecl()
            if (r12 == 0) goto L_0x0191
            gnu.expr.Declaration r14 = r12.nextDecl()
            r20 = r14
            if (r14 == 0) goto L_0x018e
            gnu.expr.Declaration r14 = r20.nextDecl()
            if (r14 != 0) goto L_0x018e
            boolean r14 = r20.getCanRead()
            if (r14 != 0) goto L_0x018e
            java.lang.String r14 = "java.lang.Number"
            gnu.bytecode.ClassType r14 = gnu.bytecode.ClassType.make(r14)
            r22 = r1
            gnu.expr.Expression r1 = r7.body
            gnu.bytecode.Type r1 = r1.getType()
            int r1 = r14.compare(r1)
            r14 = -3
            if (r1 != r14) goto L_0x019c
            r1 = 0
            gnu.expr.Expression r5 = r2.getArg(r1)
            r8.body = r5
            r2.setArg(r1, r0)
            r1 = r2
            goto L_0x019e
        L_0x018e:
            r22 = r1
            goto L_0x019c
        L_0x0191:
            r22 = r1
            goto L_0x019c
        L_0x0194:
            r22 = r1
            goto L_0x019c
        L_0x0197:
            r22 = r1
            goto L_0x019c
        L_0x019a:
            r22 = r1
        L_0x019c:
            r1 = r22
        L_0x019e:
            boolean r2 = r3 instanceof gnu.expr.ApplyExp
            if (r2 == 0) goto L_0x021b
            boolean r2 = r5 instanceof gnu.expr.ApplyExp
            if (r2 == 0) goto L_0x021b
            r2 = r3
            gnu.expr.ApplyExp r2 = (gnu.expr.ApplyExp) r2
            r4 = r5
            gnu.expr.ApplyExp r4 = (gnu.expr.ApplyExp) r4
            gnu.expr.Expression r7 = r2.getFunction()
            java.lang.Object r7 = r7.valueIfConstant()
            gnu.expr.Expression r11 = r4.getFunction()
            java.lang.Object r11 = r11.valueIfConstant()
            gnu.xquery.util.RelativeStep r12 = gnu.xquery.util.RelativeStep.relativeStep
            if (r7 != r12) goto L_0x0216
            boolean r12 = r11 instanceof gnu.kawa.xml.ChildAxis
            if (r12 == 0) goto L_0x0216
            int r12 = r2.getArgCount()
            r14 = 2
            if (r12 != r14) goto L_0x0216
            r12 = 1
            gnu.expr.Expression r12 = r2.getArg(r12)
            r14 = r12
            boolean r12 = r12 instanceof gnu.expr.LambdaExp
            if (r12 == 0) goto L_0x0211
            r12 = r14
            gnu.expr.LambdaExp r12 = (gnu.expr.LambdaExp) r12
            r16 = r3
            gnu.expr.Expression r3 = r12.body
            boolean r3 = r3 instanceof gnu.expr.ApplyExp
            if (r3 == 0) goto L_0x020e
            gnu.expr.Expression r3 = r12.body
            gnu.expr.ApplyExp r3 = (gnu.expr.ApplyExp) r3
            gnu.expr.Expression r3 = r3.getFunction()
            java.lang.Object r3 = r3.valueIfConstant()
            r17 = r5
            gnu.kawa.xml.DescendantOrSelfAxis r5 = gnu.kawa.xml.DescendantOrSelfAxis.anyNode
            if (r3 != r5) goto L_0x021f
            r3 = 0
            gnu.expr.Expression r5 = r2.getArg(r3)
            r0.setArg(r3, r5)
            gnu.expr.QuoteExp r3 = new gnu.expr.QuoteExp
            r5 = r11
            gnu.kawa.xml.ChildAxis r5 = (gnu.kawa.xml.ChildAxis) r5
            gnu.lists.NodePredicate r5 = r5.getNodePredicate()
            gnu.kawa.xml.DescendantAxis r5 = gnu.kawa.xml.DescendantAxis.make(r5)
            r3.<init>(r5)
            r4.setFunction(r3)
            goto L_0x021f
        L_0x020e:
            r17 = r5
            goto L_0x021f
        L_0x0211:
            r16 = r3
            r17 = r5
            goto L_0x021f
        L_0x0216:
            r16 = r3
            r17 = r5
            goto L_0x021f
        L_0x021b:
            r16 = r3
            r17 = r5
        L_0x021f:
            return r1
        L_0x0220:
            r19 = r1
            r16 = r3
            goto L_0x0229
        L_0x0225:
            r19 = r1
            r16 = r3
        L_0x0229:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.util.CompileMisc.validateApplyRelativeStep(gnu.expr.ApplyExp, gnu.expr.InlineCalls, gnu.bytecode.Type, gnu.mapping.Procedure):gnu.expr.Expression");
    }

    public static Expression validateApplyOrderedMap(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length <= 2) {
            return exp;
        }
        Expression[] rargs = new Expression[(args.length - 1)];
        System.arraycopy(args, 1, rargs, 0, rargs.length);
        return new ApplyExp(proc, args[0], new ApplyExp(typeTuples.getDeclaredMethod("make$V", 2), rargs));
    }

    public static void compileOrderedMap(ApplyExp exp, Compilation comp, Target target, Procedure proc) {
        Compilation compilation = comp;
        Expression[] args = exp.getArgs();
        if (args.length != 2) {
            ApplyExp.compile(exp, comp, target);
            return;
        }
        CodeAttr code = comp.getCode();
        Variable consumer = code.pushScope().addVariable(code, typeTuples, (String) null);
        args[1].compile(comp, Target.pushValue(typeTuples));
        code.emitStore(consumer);
        args[0].compile(comp, (Target) new ConsumerTarget(consumer));
        Method mm = typeTuples.getDeclaredMethod("run$X", 1);
        code.emitLoad(consumer);
        PrimProcedure.compileInvoke(comp, mm, target, exp.isTailCall(), 182, Type.pointer_type);
        code.popScope();
    }

    public static Expression validateApplyCastAs(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        ApplyExp exp2 = CompileReflect.inlineClassName(exp, 0, visitor);
        Expression[] args = exp2.getArgs();
        if (args.length != 2 || !(args[0] instanceof QuoteExp) || !(((QuoteExp) args[0]).getValue() instanceof XDataType)) {
            return exp2;
        }
        return new ApplyExp(castMethod, args);
    }

    public static Expression validateApplyCastableAs(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        ApplyExp exp2 = CompileReflect.inlineClassName(exp, 1, visitor);
        Expression[] args = exp2.getArgs();
        if (args.length != 2 || !(args[1] instanceof QuoteExp) || !(((QuoteExp) args[1]).getValue() instanceof XDataType)) {
            return exp2;
        }
        return new ApplyExp(castableMethod, args[1], args[0]);
    }
}
