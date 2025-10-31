package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.kawa.util.IdentityHashTable;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.text.SourceMessages;

public class ApplyExp extends Expression {
    public static final int INLINE_IF_CONSTANT = 4;
    public static final int MAY_CONTAIN_BACK_JUMP = 8;
    public static final int TAILCALL = 2;
    Expression[] args;
    LambdaExp context;
    Expression func;
    public ApplyExp nextCall;
    protected Type type;

    public final Expression getFunction() {
        return this.func;
    }

    public final Expression[] getArgs() {
        return this.args;
    }

    public final int getArgCount() {
        return this.args.length;
    }

    public void setFunction(Expression func2) {
        this.func = func2;
    }

    public void setArgs(Expression[] args2) {
        this.args = args2;
    }

    public Expression getArg(int i) {
        return this.args[i];
    }

    public void setArg(int i, Expression arg) {
        this.args[i] = arg;
    }

    public final boolean isTailCall() {
        return getFlag(2);
    }

    public final void setTailCall(boolean tailCall) {
        setFlag(tailCall, 2);
    }

    public final Object getFunctionValue() {
        if (this.func instanceof QuoteExp) {
            return ((QuoteExp) this.func).getValue();
        }
        return null;
    }

    public ApplyExp(Expression f, Expression... a) {
        this.func = f;
        this.args = a;
    }

    public ApplyExp(Procedure p, Expression... a) {
        this.func = new QuoteExp(p);
        this.args = a;
    }

    public ApplyExp(Method m, Expression... a) {
        this((Expression) new QuoteExp(new PrimProcedure(m)), a);
    }

    /* access modifiers changed from: protected */
    public boolean mustCompile() {
        return false;
    }

    public void apply(CallContext ctx) throws Throwable {
        Object proc = this.func.eval(ctx);
        int n = this.args.length;
        Object[] vals = new Object[n];
        for (int i = 0; i < n; i++) {
            vals[i] = this.args[i].eval(ctx);
        }
        ((Procedure) proc).checkN(vals, ctx);
    }

    public static void compileToArray(Expression[] args2, Compilation comp) {
        CodeAttr code = comp.getCode();
        if (args2.length == 0) {
            code.emitGetStatic(Compilation.noArgsField);
            return;
        }
        code.emitPushInt(args2.length);
        code.emitNewArray((Type) Type.pointer_type);
        for (int i = 0; i < args2.length; i++) {
            Expression arg = args2[i];
            if (!comp.usingCPStyle() || (arg instanceof QuoteExp) || (arg instanceof ReferenceExp)) {
                code.emitDup((Type) Compilation.objArrayType);
                code.emitPushInt(i);
                arg.compileWithPosition(comp, Target.pushObject);
            } else {
                arg.compileWithPosition(comp, Target.pushObject);
                code.emitSwap();
                code.emitDup(1, 1);
                code.emitSwap();
                code.emitPushInt(i);
                code.emitSwap();
            }
            code.emitArrayStore(Type.pointer_type);
        }
    }

    public void compile(Compilation comp, Target target) {
        compile(this, comp, target, true);
    }

    public static void compile(ApplyExp exp, Compilation comp, Target target) {
        compile(exp, comp, target, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:147:0x02a8  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x02c3  */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x02cb  */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x02fa  */
    /* JADX WARNING: Removed duplicated region for block: B:168:0x0300  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void compile(gnu.expr.ApplyExp r19, gnu.expr.Compilation r20, gnu.expr.Target r21, boolean r22) {
        /*
            r1 = r19
            r2 = r20
            r3 = r21
            gnu.expr.Expression[] r0 = r1.args
            int r4 = r0.length
            gnu.expr.Expression r5 = r1.func
            r0 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            boolean r9 = r5 instanceof gnu.expr.LambdaExp
            if (r9 == 0) goto L_0x002a
            r0 = r5
            gnu.expr.LambdaExp r0 = (gnu.expr.LambdaExp) r0
            java.lang.String r6 = r0.getName()
            if (r6 != 0) goto L_0x0024
            java.lang.String r6 = "<lambda>"
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r0
            goto L_0x009e
        L_0x0024:
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r0
            goto L_0x009e
        L_0x002a:
            boolean r9 = r5 instanceof gnu.expr.ReferenceExp
            if (r9 == 0) goto L_0x008a
            r9 = r5
            gnu.expr.ReferenceExp r9 = (gnu.expr.ReferenceExp) r9
            gnu.expr.Declaration r7 = r9.contextDecl()
            gnu.expr.Declaration r10 = r9.binding
        L_0x0037:
            if (r10 == 0) goto L_0x005e
            boolean r11 = r10.isAlias()
            if (r11 == 0) goto L_0x005e
            gnu.expr.Expression r11 = r10.value
            boolean r11 = r11 instanceof gnu.expr.ReferenceExp
            if (r11 == 0) goto L_0x005e
            gnu.expr.Expression r11 = r10.value
            r9 = r11
            gnu.expr.ReferenceExp r9 = (gnu.expr.ReferenceExp) r9
            if (r7 != 0) goto L_0x005e
            boolean r11 = r10.needsContext()
            if (r11 != 0) goto L_0x005e
            gnu.expr.Declaration r11 = r9.binding
            if (r11 != 0) goto L_0x0057
            goto L_0x005e
        L_0x0057:
            gnu.expr.Declaration r10 = r9.binding
            gnu.expr.Declaration r7 = r9.contextDecl()
            goto L_0x0037
        L_0x005e:
            r11 = 65536(0x10000, double:3.2379E-319)
            boolean r11 = r10.getFlag(r11)
            if (r11 != 0) goto L_0x0085
            gnu.expr.Expression r11 = r10.getValue()
            java.lang.String r6 = r10.getName()
            if (r11 == 0) goto L_0x0078
            boolean r12 = r11 instanceof gnu.expr.LambdaExp
            if (r12 == 0) goto L_0x0078
            r0 = r11
            gnu.expr.LambdaExp r0 = (gnu.expr.LambdaExp) r0
        L_0x0078:
            if (r11 == 0) goto L_0x0085
            boolean r12 = r11 instanceof gnu.expr.QuoteExp
            if (r12 == 0) goto L_0x0085
            r12 = r11
            gnu.expr.QuoteExp r12 = (gnu.expr.QuoteExp) r12
            java.lang.Object r8 = r12.getValue()
        L_0x0085:
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r0
            goto L_0x009e
        L_0x008a:
            boolean r9 = r5 instanceof gnu.expr.QuoteExp
            if (r9 == 0) goto L_0x009a
            r9 = r5
            gnu.expr.QuoteExp r9 = (gnu.expr.QuoteExp) r9
            java.lang.Object r8 = r9.getValue()
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r0
            goto L_0x009e
        L_0x009a:
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r0
        L_0x009e:
            r10 = 101(0x65, float:1.42E-43)
            if (r22 == 0) goto L_0x00f1
            boolean r0 = r9 instanceof gnu.mapping.Procedure
            if (r0 == 0) goto L_0x00f1
            r11 = r9
            gnu.mapping.Procedure r11 = (gnu.mapping.Procedure) r11
            boolean r0 = r3 instanceof gnu.expr.IgnoreTarget
            if (r0 == 0) goto L_0x00c1
            boolean r0 = r11.isSideEffectFree()
            if (r0 == 0) goto L_0x00c1
            r0 = 0
        L_0x00b4:
            if (r0 >= r4) goto L_0x00c0
            gnu.expr.Expression[] r10 = r1.args
            r10 = r10[r0]
            r10.compile((gnu.expr.Compilation) r2, (gnu.expr.Target) r3)
            int r0 = r0 + 1
            goto L_0x00b4
        L_0x00c0:
            return
        L_0x00c1:
            boolean r0 = inlineCompile(r11, r1, r2, r3)     // Catch:{ all -> 0x00c9 }
            if (r0 == 0) goto L_0x00c8
            return
        L_0x00c8:
            goto L_0x00f1
        L_0x00c9:
            r0 = move-exception
            r12 = r0
            r0 = r12
            gnu.text.SourceMessages r12 = r20.getMessages()
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = "caught exception in inline-compiler for "
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.StringBuilder r13 = r13.append(r9)
            java.lang.String r14 = " - "
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.StringBuilder r13 = r13.append(r0)
            java.lang.String r13 = r13.toString()
            r12.error((char) r10, (java.lang.String) r13, (java.lang.Throwable) r0)
            return
        L_0x00f1:
            gnu.bytecode.CodeAttr r0 = r20.getCode()
            r11 = 2
            r13 = 0
            if (r6 == 0) goto L_0x019d
            int r14 = r6.max_args
            if (r14 < 0) goto L_0x0106
            int r14 = r6.max_args
            if (r4 > r14) goto L_0x0102
            goto L_0x0106
        L_0x0102:
            r18 = r7
            goto L_0x0184
        L_0x0106:
            int r14 = r6.min_args
            if (r4 < r14) goto L_0x0182
            int r14 = r6.getCallConvention()
            boolean r15 = r2.inlineOk((gnu.expr.Expression) r6)
            if (r15 == 0) goto L_0x017f
            if (r14 <= r11) goto L_0x0124
            r15 = 3
            if (r14 != r15) goto L_0x0120
            boolean r15 = r19.isTailCall()
            if (r15 != 0) goto L_0x0120
            goto L_0x0124
        L_0x0120:
            r18 = r7
            goto L_0x019f
        L_0x0124:
            gnu.bytecode.Method r15 = r6.getMethod(r4)
            r16 = r15
            if (r15 == 0) goto L_0x017a
            gnu.expr.PrimProcedure r10 = new gnu.expr.PrimProcedure
            r15 = r16
            r10.<init>((gnu.bytecode.Method) r15, (gnu.expr.LambdaExp) r6)
            boolean r11 = r15.getStaticFlag()
            r16 = 0
            if (r11 == 0) goto L_0x0145
            gnu.bytecode.Variable r17 = r6.declareClosureEnv()
            if (r17 == 0) goto L_0x0142
            goto L_0x0145
        L_0x0142:
            r18 = r7
            goto L_0x0170
        L_0x0145:
            if (r11 == 0) goto L_0x0149
            r16 = 1
        L_0x0149:
            gnu.expr.LambdaExp r12 = r2.curLambda
            if (r12 != r6) goto L_0x015c
            gnu.bytecode.Variable r12 = r6.closureEnv
            if (r12 == 0) goto L_0x0154
            gnu.bytecode.Variable r12 = r6.closureEnv
            goto L_0x0156
        L_0x0154:
            gnu.bytecode.Variable r12 = r6.thisVariable
        L_0x0156:
            r0.emitLoad(r12)
            r18 = r7
            goto L_0x0170
        L_0x015c:
            if (r8 == 0) goto L_0x0167
            gnu.expr.Target r12 = gnu.expr.Target.pushObject
            r18 = r7
            r7 = 0
            r8.load(r7, r13, r2, r12)
            goto L_0x0170
        L_0x0167:
            r18 = r7
            gnu.expr.LambdaExp r7 = r6.getOwningLambda()
            r7.loadHeapFrame(r2)
        L_0x0170:
            if (r16 == 0) goto L_0x0175
            gnu.bytecode.PrimType r12 = gnu.bytecode.Type.voidType
            goto L_0x0176
        L_0x0175:
            r12 = 0
        L_0x0176:
            r10.compile(r12, r1, r2, r3)
            return
        L_0x017a:
            r18 = r7
            r15 = r16
            goto L_0x019f
        L_0x017f:
            r18 = r7
            goto L_0x019f
        L_0x0182:
            r18 = r7
        L_0x0184:
            java.lang.Error r7 = new java.lang.Error
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "internal error - wrong number of parameters for "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r6)
            java.lang.String r10 = r10.toString()
            r7.<init>(r10)
            throw r7
        L_0x019d:
            r18 = r7
        L_0x019f:
            boolean r7 = r19.isTailCall()
            if (r7 == 0) goto L_0x01ad
            if (r6 == 0) goto L_0x01ad
            gnu.expr.LambdaExp r7 = r2.curLambda
            if (r6 != r7) goto L_0x01ad
            r7 = 1
            goto L_0x01ae
        L_0x01ad:
            r7 = 0
        L_0x01ae:
            if (r6 == 0) goto L_0x01fc
            boolean r14 = r6.getInlineOnly()
            if (r14 == 0) goto L_0x01fc
            if (r7 != 0) goto L_0x01fc
            int r14 = r6.min_args
            if (r14 != r4) goto L_0x01fc
            gnu.expr.Expression[] r10 = r1.args
            r11 = 0
            pushArgs(r6, r10, r11, r2)
            r10 = 128(0x80, float:1.794E-43)
            boolean r12 = r6.getFlag(r10)
            if (r12 == 0) goto L_0x01d5
            popParams(r0, r6, r11, r13)
            gnu.bytecode.Scope r10 = r6.getVarScope()
            r0.emitTailCall(r13, r10)
            return
        L_0x01d5:
            int r11 = r6.flags
            r10 = r10 | r11
            r6.flags = r10
            gnu.expr.LambdaExp r10 = r2.curLambda
            r2.curLambda = r6
            r6.allocChildClasses(r2)
            r6.allocParameters(r2)
            r11 = 0
            popParams(r0, r6, r11, r13)
            r6.enterFunction(r2)
            gnu.expr.Expression r11 = r6.body
            r11.compileWithPosition(r2, r3)
            r6.compileEnd(r2)
            r6.generateApplyMethods(r2)
            r0.popScope()
            r2.curLambda = r10
            return
        L_0x01fc:
            gnu.expr.LambdaExp r14 = r2.curLambda
            boolean r14 = r14.isHandlingTailCalls()
            r15 = 4
            if (r14 == 0) goto L_0x02a5
            boolean r14 = r19.isTailCall()
            if (r14 != 0) goto L_0x0213
            boolean r14 = r3 instanceof gnu.expr.ConsumerTarget
            if (r14 == 0) goto L_0x0210
            goto L_0x0213
        L_0x0210:
            r12 = 1
            goto L_0x02a6
        L_0x0213:
            gnu.expr.LambdaExp r14 = r2.curLambda
            boolean r14 = r14.getInlineOnly()
            if (r14 != 0) goto L_0x02a5
            gnu.bytecode.ClassType r10 = gnu.expr.Compilation.typeCallContext
            gnu.expr.StackTarget r14 = new gnu.expr.StackTarget
            gnu.bytecode.ClassType r12 = gnu.expr.Compilation.typeProcedure
            r14.<init>(r12)
            r5.compile((gnu.expr.Compilation) r2, (gnu.expr.Target) r14)
            if (r4 > r15) goto L_0x025a
            r11 = 0
        L_0x022a:
            if (r11 >= r4) goto L_0x0238
            gnu.expr.Expression[] r12 = r1.args
            r12 = r12[r11]
            gnu.expr.Target r14 = gnu.expr.Target.pushObject
            r12.compileWithPosition(r2, r14)
            int r11 = r11 + 1
            goto L_0x022a
        L_0x0238:
            r20.loadCallContext()
            gnu.bytecode.ClassType r11 = gnu.expr.Compilation.typeProcedure
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r14 = "check"
            java.lang.StringBuilder r12 = r12.append(r14)
            java.lang.StringBuilder r12 = r12.append(r4)
            java.lang.String r12 = r12.toString()
            int r14 = r4 + 1
            gnu.bytecode.Method r11 = r11.getDeclaredMethod((java.lang.String) r12, (int) r14)
            r0.emitInvoke(r11)
            goto L_0x026d
        L_0x025a:
            gnu.expr.Expression[] r12 = r1.args
            compileToArray(r12, r2)
            r20.loadCallContext()
            gnu.bytecode.ClassType r12 = gnu.expr.Compilation.typeProcedure
            java.lang.String r14 = "checkN"
            gnu.bytecode.Method r11 = r12.getDeclaredMethod((java.lang.String) r14, (int) r11)
            r0.emitInvoke(r11)
        L_0x026d:
            boolean r11 = r19.isTailCall()
            if (r11 == 0) goto L_0x0277
            r0.emitReturn()
            goto L_0x02a4
        L_0x0277:
            r11 = r3
            gnu.expr.ConsumerTarget r11 = (gnu.expr.ConsumerTarget) r11
            boolean r11 = r11.isContextTarget()
            if (r11 == 0) goto L_0x028d
            r20.loadCallContext()
            java.lang.String r11 = "runUntilDone"
            gnu.bytecode.Method r11 = r10.getDeclaredMethod((java.lang.String) r11, (int) r13)
            r0.emitInvoke(r11)
            goto L_0x02a4
        L_0x028d:
            r20.loadCallContext()
            r11 = r3
            gnu.expr.ConsumerTarget r11 = (gnu.expr.ConsumerTarget) r11
            gnu.bytecode.Variable r11 = r11.getConsumerVariable()
            r0.emitLoad(r11)
            java.lang.String r11 = "runUntilValue"
            r12 = 1
            gnu.bytecode.Method r11 = r10.getDeclaredMethod((java.lang.String) r11, (int) r12)
            r0.emitInvoke(r11)
        L_0x02a4:
            return
        L_0x02a5:
            r12 = 1
        L_0x02a6:
            if (r7 != 0) goto L_0x02b2
            gnu.expr.StackTarget r11 = new gnu.expr.StackTarget
            gnu.bytecode.ClassType r14 = gnu.expr.Compilation.typeProcedure
            r11.<init>(r14)
            r5.compile((gnu.expr.Compilation) r2, (gnu.expr.Target) r11)
        L_0x02b2:
            if (r7 == 0) goto L_0x02bb
            int r11 = r6.min_args
            int r14 = r6.max_args
            if (r11 == r14) goto L_0x02be
            goto L_0x02bd
        L_0x02bb:
            if (r4 <= r15) goto L_0x02be
        L_0x02bd:
            goto L_0x02bf
        L_0x02be:
            r12 = 0
        L_0x02bf:
            r11 = r12
            r12 = 0
            if (r11 == 0) goto L_0x02cb
            gnu.expr.Expression[] r14 = r1.args
            compileToArray(r14, r2)
            gnu.bytecode.Method r14 = gnu.expr.Compilation.applyNmethod
            goto L_0x02f4
        L_0x02cb:
            if (r7 == 0) goto L_0x02d9
            gnu.expr.Expression[] r14 = r1.args
            int r14 = r14.length
            int[] r12 = new int[r14]
            gnu.expr.Expression[] r14 = r1.args
            pushArgs(r6, r14, r12, r2)
            r14 = 0
            goto L_0x02f4
        L_0x02d9:
            r14 = 0
        L_0x02da:
            if (r14 >= r4) goto L_0x02f0
            gnu.expr.Expression[] r15 = r1.args
            r15 = r15[r14]
            gnu.expr.Target r13 = gnu.expr.Target.pushObject
            r15.compileWithPosition(r2, r13)
            boolean r13 = r0.reachableHere()
            if (r13 != 0) goto L_0x02ec
            goto L_0x02f0
        L_0x02ec:
            int r14 = r14 + 1
            r13 = 0
            goto L_0x02da
        L_0x02f0:
            gnu.bytecode.Method[] r13 = gnu.expr.Compilation.applymethods
            r14 = r13[r4]
        L_0x02f4:
            boolean r13 = r0.reachableHere()
            if (r13 != 0) goto L_0x0300
            java.lang.String r13 = "unreachable code"
            r2.error(r10, r13)
            return
        L_0x0300:
            if (r7 == 0) goto L_0x030e
            popParams(r0, r6, r12, r11)
            gnu.bytecode.Scope r10 = r6.getVarScope()
            r13 = 0
            r0.emitTailCall(r13, r10)
            return
        L_0x030e:
            r0.emitInvokeVirtual(r14)
            gnu.bytecode.ClassType r10 = gnu.bytecode.Type.pointer_type
            r3.compileFromStack(r2, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.ApplyExp.compile(gnu.expr.ApplyExp, gnu.expr.Compilation, gnu.expr.Target, boolean):void");
    }

    public Expression deepCopy(IdentityHashTable mapper) {
        Expression f = deepCopy(this.func, mapper);
        Expression[] a = deepCopy(this.args, mapper);
        if (f == null && this.func != null) {
            return null;
        }
        if (a == null && this.args != null) {
            return null;
        }
        ApplyExp copy = new ApplyExp(f, a);
        copy.flags = getFlags();
        return copy;
    }

    /* access modifiers changed from: protected */
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitApplyExp(this, d);
    }

    public void visitArgs(InlineCalls visitor) {
        this.args = visitor.visitExps(this.args, this.args.length, null);
    }

    /* access modifiers changed from: protected */
    public <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        this.func = visitor.visitAndUpdate(this.func, d);
        if (visitor.exitValue == null) {
            this.args = visitor.visitExps(this.args, this.args.length, d);
        }
    }

    public void print(OutPort out) {
        out.startLogicalBlock("(Apply", ")", 2);
        if (isTailCall()) {
            out.print(" [tailcall]");
        }
        if (!(this.type == null || this.type == Type.pointer_type)) {
            out.print(" => ");
            out.print((Object) this.type);
        }
        out.writeSpaceFill();
        printLineColumn(out);
        this.func.print(out);
        for (Expression print : this.args) {
            out.writeSpaceLinear();
            print.print(out);
        }
        out.endLogicalBlock(")");
    }

    private static void pushArgs(LambdaExp lexp, Expression[] args2, int[] incValues, Compilation comp) {
        Declaration param = lexp.firstDecl();
        int args_length = args2.length;
        for (int i = 0; i < args_length; i++) {
            Expression arg = args2[i];
            if (param.ignorable()) {
                arg.compile(comp, Target.Ignore);
            } else {
                if (incValues != null) {
                    int canUseInc = SetExp.canUseInc(arg, param);
                    incValues[i] = canUseInc;
                    if (canUseInc != 65536) {
                    }
                }
                arg.compileWithPosition(comp, StackTarget.getInstance(param.getType()));
            }
            param = param.nextDecl();
        }
    }

    private static void popParams(CodeAttr code, LambdaExp lexp, int[] incValues, boolean toArray) {
        Variable vars = lexp.getVarScope().firstVar();
        Declaration decls = lexp.firstDecl();
        if (vars != null && vars.getName() == "this") {
            vars = vars.nextVar();
        }
        if (vars != null && vars.getName() == "$ctx") {
            vars = vars.nextVar();
        }
        if (vars != null && vars.getName() == "argsArray") {
            if (toArray) {
                popParams(code, 0, 1, (int[]) null, decls, vars);
                return;
            }
            vars = vars.nextVar();
        }
        popParams(code, 0, lexp.min_args, incValues, decls, vars);
    }

    private static void popParams(CodeAttr code, int paramNo, int count, int[] incValues, Declaration decl, Variable vars) {
        if (count > 0) {
            popParams(code, paramNo + 1, count - 1, incValues, decl.nextDecl(), decl.getVariable() == null ? vars : vars.nextVar());
            if (decl.ignorable()) {
                return;
            }
            if (incValues == null || incValues[paramNo] == 65536) {
                code.emitStore(vars);
            } else {
                code.emitInc(vars, (short) incValues[paramNo]);
            }
        }
    }

    public final Type getTypeRaw() {
        return this.type;
    }

    public final void setType(Type type2) {
        this.type = type2;
    }

    public boolean side_effects() {
        Object value = derefFunc(this.func).valueIfConstant();
        if (!(value instanceof Procedure) || !((Procedure) value).isSideEffectFree()) {
            return true;
        }
        for (Expression side_effects : this.args) {
            if (side_effects.side_effects()) {
                return true;
            }
        }
        return false;
    }

    static Expression derefFunc(Expression afunc) {
        Declaration func_decl;
        if (!(afunc instanceof ReferenceExp) || (func_decl = Declaration.followAliases(((ReferenceExp) afunc).binding)) == null || func_decl.getFlag(65536)) {
            return afunc;
        }
        return func_decl.getValue();
    }

    public final Type getType() {
        if (this.type != null) {
            return this.type;
        }
        Expression afunc = derefFunc(this.func);
        this.type = Type.objectType;
        if (afunc instanceof QuoteExp) {
            Object value = ((QuoteExp) afunc).getValue();
            if (value instanceof Procedure) {
                this.type = ((Procedure) value).getReturnType(this.args);
            }
        } else if (afunc instanceof LambdaExp) {
            this.type = ((LambdaExp) afunc).getReturnType();
        }
        return this.type;
    }

    public static Inlineable asInlineable(Procedure proc) {
        if (proc instanceof Inlineable) {
            return (Inlineable) proc;
        }
        return (Inlineable) Procedure.compilerKey.get(proc);
    }

    static boolean inlineCompile(Procedure proc, ApplyExp exp, Compilation comp, Target target) throws Throwable {
        Inlineable compiler = asInlineable(proc);
        if (compiler == null) {
            return false;
        }
        compiler.compile(exp, comp, target);
        return true;
    }

    public final Expression inlineIfConstant(Procedure proc, InlineCalls visitor) {
        return inlineIfConstant(proc, visitor.getMessages());
    }

    public final Expression inlineIfConstant(Procedure proc, SourceMessages messages) {
        Declaration decl;
        int len = this.args.length;
        Object[] vals = new Object[len];
        int i = len;
        while (true) {
            i--;
            if (i >= 0) {
                Expression arg = this.args[i];
                if (((arg instanceof ReferenceExp) && (decl = ((ReferenceExp) arg).getBinding()) != null && (arg = decl.getValue()) == QuoteExp.undefined_exp) || !(arg instanceof QuoteExp)) {
                    return this;
                }
                vals[i] = ((QuoteExp) arg).getValue();
            } else {
                try {
                    return new QuoteExp(proc.applyN(vals), this.type);
                } catch (Throwable ex) {
                    if (messages != null) {
                        messages.error('w', "call to " + proc + " throws " + ex);
                    }
                    return this;
                }
            }
        }
    }

    public String toString() {
        if (this == LambdaExp.unknownContinuation) {
            return "ApplyExp[unknownContinuation]";
        }
        return "ApplyExp/" + (this.args == null ? 0 : this.args.length) + '[' + this.func + ']';
    }
}
