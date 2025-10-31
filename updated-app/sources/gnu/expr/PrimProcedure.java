package gnu.expr;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassFileInput;
import gnu.bytecode.ClassType;
import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.kawa.functions.MakeList;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.Consumer;
import gnu.lists.ConsumerWriter;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrongArguments;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.net.URL;

public class PrimProcedure extends MethodProc implements Inlineable {
    private static ClassLoader systemClassLoader = PrimProcedure.class.getClassLoader();
    Type[] argTypes;
    Member member;
    Method method;
    char mode;
    int op_code;
    Type retType;
    boolean sideEffectFree;
    LambdaExp source;

    public final int opcode() {
        return this.op_code;
    }

    public Type getReturnType() {
        return this.retType;
    }

    public void setReturnType(Type retType2) {
        this.retType = retType2;
    }

    public boolean isSpecial() {
        return this.mode == 'P';
    }

    public Type getReturnType(Expression[] args) {
        return this.retType;
    }

    public Method getMethod() {
        return this.method;
    }

    public boolean isSideEffectFree() {
        return this.sideEffectFree;
    }

    public void setSideEffectFree() {
        this.sideEffectFree = true;
    }

    public boolean takesVarArgs() {
        if (this.method == null) {
            return false;
        }
        if ((this.method.getModifiers() & 128) != 0) {
            return true;
        }
        String name = this.method.getName();
        if (name.endsWith("$V") || name.endsWith("$V$X")) {
            return true;
        }
        return false;
    }

    public boolean takesContext() {
        return this.method != null && takesContext(this.method);
    }

    public static boolean takesContext(Method method2) {
        return method2.getName().endsWith("$X");
    }

    public int isApplicable(Type[] argTypes2) {
        int app = super.isApplicable(argTypes2);
        int nargs = argTypes2.length;
        if (app != -1 || this.method == null || (this.method.getModifiers() & 128) == 0 || nargs <= 0 || !(argTypes2[nargs - 1] instanceof ArrayType)) {
            return app;
        }
        Type[] tmp = new Type[nargs];
        System.arraycopy(argTypes2, 0, tmp, 0, nargs - 1);
        tmp[nargs - 1] = argTypes2[nargs - 1].getComponentType();
        return super.isApplicable(tmp);
    }

    public final boolean isConstructor() {
        return opcode() == 183 && this.mode != 'P';
    }

    public boolean takesTarget() {
        return this.mode != 0;
    }

    public int numArgs() {
        int num = this.argTypes.length;
        if (takesTarget()) {
            num++;
        }
        if (takesContext()) {
            num--;
        }
        return takesVarArgs() ? (num - 1) - 4096 : (num << 12) + num;
    }

    public int match0(CallContext ctx) {
        return matchN(ProcedureN.noArgs, ctx);
    }

    public int match1(Object arg1, CallContext ctx) {
        return matchN(new Object[]{arg1}, ctx);
    }

    public int match2(Object arg1, Object arg2, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2}, ctx);
    }

    public int match3(Object arg1, Object arg2, Object arg3, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2, arg3}, ctx);
    }

    public int match4(Object arg1, Object arg2, Object arg3, Object arg4, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2, arg3, arg4}, ctx);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v0, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int matchN(java.lang.Object[] r13, gnu.mapping.CallContext r14) {
        /*
            r12 = this;
            int r0 = r13.length
            boolean r1 = r12.takesVarArgs()
            int r2 = r12.minArgs()
            if (r0 >= r2) goto L_0x000f
            r13 = -983040(0xfffffffffff10000, float:NaN)
            r13 = r13 | r2
            return r13
        L_0x000f:
            if (r1 != 0) goto L_0x0017
            if (r0 <= r2) goto L_0x0017
            r13 = -917504(0xfffffffffff20000, float:NaN)
            r13 = r13 | r2
            return r13
        L_0x0017:
            gnu.bytecode.Type[] r3 = r12.argTypes
            int r3 = r3.length
            boolean r4 = r12.takesTarget()
            r5 = 0
            r6 = 1
            if (r4 != 0) goto L_0x002d
            boolean r4 = r12.isConstructor()
            if (r4 == 0) goto L_0x002b
            goto L_0x002d
        L_0x002b:
            r4 = 0
            goto L_0x002e
        L_0x002d:
            r4 = 1
        L_0x002e:
            boolean r7 = r12.takesContext()
            java.lang.Object[] r8 = new java.lang.Object[r3]
            if (r7 == 0) goto L_0x003a
            int r3 = r3 + -1
            r8[r3] = r14
        L_0x003a:
            r7 = 0
            if (r1 == 0) goto L_0x006c
            gnu.bytecode.Type[] r1 = r12.argTypes
            int r3 = r3 - r6
            r1 = r1[r3]
            gnu.bytecode.ClassType r9 = gnu.expr.Compilation.scmListType
            if (r1 == r9) goto L_0x0061
            gnu.kawa.lispexpr.LangObjType r9 = gnu.kawa.lispexpr.LangObjType.listType
            if (r1 != r9) goto L_0x004b
            goto L_0x0061
        L_0x004b:
            gnu.bytecode.ArrayType r1 = (gnu.bytecode.ArrayType) r1
            gnu.bytecode.Type r1 = r1.getComponentType()
            java.lang.Class r9 = r1.getReflectClass()
            int r0 = r0 - r2
            java.lang.Object r0 = java.lang.reflect.Array.newInstance(r9, r0)
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            r8[r3] = r0
            goto L_0x006e
        L_0x0061:
            gnu.lists.LList r0 = gnu.lists.LList.makeList(r13, r2)
            r8[r3] = r0
            gnu.bytecode.ClassType r1 = gnu.bytecode.Type.objectType
            r0 = r7
            goto L_0x006e
        L_0x006c:
            r0 = r7
            r1 = r0
        L_0x006e:
            boolean r3 = r12.isConstructor()
            if (r3 == 0) goto L_0x0077
            r7 = r13[r5]
            goto L_0x008c
        L_0x0077:
            if (r4 == 0) goto L_0x008b
            gnu.bytecode.Method r3 = r12.method     // Catch:{ ClassCastException -> 0x0086 }
            gnu.bytecode.ClassType r3 = r3.getDeclaringClass()     // Catch:{ ClassCastException -> 0x0086 }
            r7 = r13[r5]     // Catch:{ ClassCastException -> 0x0086 }
            java.lang.Object r7 = r3.coerceFromObject(r7)     // Catch:{ ClassCastException -> 0x0086 }
            goto L_0x008c
        L_0x0086:
            r13 = move-exception
            r13 = -786431(0xfffffffffff40001, float:NaN)
            return r13
        L_0x008b:
        L_0x008c:
            r3 = r4
        L_0x008d:
            int r9 = r13.length
            if (r3 >= r9) goto L_0x00bb
            r9 = r13[r3]
            if (r3 >= r2) goto L_0x009b
            gnu.bytecode.Type[] r10 = r12.argTypes
            int r11 = r3 - r4
            r10 = r10[r11]
            goto L_0x009c
        L_0x009b:
            r10 = r1
        L_0x009c:
            gnu.bytecode.ClassType r11 = gnu.bytecode.Type.objectType
            if (r10 == r11) goto L_0x00ab
            java.lang.Object r9 = r10.coerceFromObject(r9)     // Catch:{ ClassCastException -> 0x00a5 }
            goto L_0x00ab
        L_0x00a5:
            r13 = move-exception
            r13 = -786432(0xfffffffffff40000, float:NaN)
            int r3 = r3 + r6
            r13 = r13 | r3
            return r13
        L_0x00ab:
            if (r3 >= r2) goto L_0x00b2
            int r10 = r3 - r4
            r8[r10] = r9
            goto L_0x00b8
        L_0x00b2:
            if (r0 == 0) goto L_0x00b8
            int r10 = r3 - r2
            r0[r10] = r9
        L_0x00b8:
            int r3 = r3 + 1
            goto L_0x008d
        L_0x00bb:
            r14.value1 = r7
            r14.values = r8
            r14.proc = r12
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.PrimProcedure.matchN(java.lang.Object[], gnu.mapping.CallContext):int");
    }

    public void apply(CallContext ctx) throws Throwable {
        Object result;
        int arg_count = this.argTypes.length;
        boolean is_constructor = isConstructor();
        boolean slink = is_constructor && this.method.getDeclaringClass().hasOuterLink();
        try {
            if (this.member == null) {
                Class clas = this.method.getDeclaringClass().getReflectClass();
                Class[] paramTypes = new Class[((slink ? 1 : 0) + arg_count)];
                int i = arg_count;
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    }
                    paramTypes[(slink ? 1 : 0) + i] = this.argTypes[i].getReflectClass();
                }
                if (slink) {
                    paramTypes[0] = this.method.getDeclaringClass().getOuterLinkType().getReflectClass();
                }
                if (is_constructor) {
                    this.member = clas.getConstructor(paramTypes);
                } else if (this.method != Type.clone_method) {
                    this.member = clas.getMethod(this.method.getName(), paramTypes);
                }
            }
            if (is_constructor) {
                Object[] args = ctx.values;
                if (slink) {
                    int nargs = args.length + 1;
                    Object[] xargs = new Object[nargs];
                    System.arraycopy(args, 0, xargs, 1, nargs - 1);
                    xargs[0] = ((PairClassType) ctx.value1).staticLink;
                    args = xargs;
                }
                result = ((Constructor) this.member).newInstance(args);
            } else if (this.method == Type.clone_method) {
                Object arr = ctx.value1;
                Class elClass = arr.getClass().getComponentType();
                int n = Array.getLength(arr);
                Object result2 = Array.newInstance(elClass, n);
                System.arraycopy(arr, 0, result2, 0, n);
                result = result2;
            } else {
                result = this.retType.coerceToObject(((java.lang.reflect.Method) this.member).invoke(ctx.value1, ctx.values));
            }
            if (!takesContext()) {
                ctx.consumer.writeObject(result);
            }
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }

    public PrimProcedure(String className, String methodName, int numArgs) {
        this(ClassType.make(className).getDeclaredMethod(methodName, numArgs));
    }

    public PrimProcedure(java.lang.reflect.Method method2, Language language) {
        this(((ClassType) language.getTypeFor((Class) method2.getDeclaringClass())).getMethod(method2), language);
    }

    public PrimProcedure(Method method2) {
        init(method2);
        this.retType = method2.getName().endsWith("$X") ? Type.objectType : method2.getReturnType();
    }

    public PrimProcedure(Method method2, Language language) {
        this(method2, 0, language);
    }

    public PrimProcedure(Method method2, char mode2, Language language) {
        this.mode = mode2;
        init(method2);
        Type[] pTypes = this.argTypes;
        int nTypes = pTypes.length;
        this.argTypes = null;
        int i = nTypes;
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            Type javaType = pTypes[i];
            Type langType = language.getLangTypeFor(javaType);
            if (javaType != langType) {
                if (this.argTypes == null) {
                    this.argTypes = new Type[nTypes];
                    System.arraycopy(pTypes, 0, this.argTypes, 0, nTypes);
                }
                this.argTypes[i] = langType;
            }
        }
        if (this.argTypes == null) {
            this.argTypes = pTypes;
        }
        if (isConstructor()) {
            this.retType = method2.getDeclaringClass();
        } else if (method2.getName().endsWith("$X")) {
            this.retType = Type.objectType;
        } else {
            this.retType = language.getLangTypeFor(method2.getReturnType());
            if (this.retType == Type.toStringType) {
                this.retType = Type.javalangStringType;
            }
        }
    }

    private void init(Method method2) {
        this.method = method2;
        if ((method2.getModifiers() & 8) != 0) {
            this.op_code = 184;
        } else {
            ClassType mclass = method2.getDeclaringClass();
            if (this.mode == 'P') {
                this.op_code = 183;
            } else {
                this.mode = 'V';
                if ("<init>".equals(method2.getName())) {
                    this.op_code = 183;
                } else if ((mclass.getModifiers() & 512) != 0) {
                    this.op_code = 185;
                } else {
                    this.op_code = 182;
                }
            }
        }
        Type[] mtypes = method2.getParameterTypes();
        if (isConstructor() && method2.getDeclaringClass().hasOuterLink()) {
            int len = mtypes.length - 1;
            Type[] types = new Type[len];
            System.arraycopy(mtypes, 1, types, 0, len);
            mtypes = types;
        }
        this.argTypes = mtypes;
    }

    public PrimProcedure(Method method2, LambdaExp source2) {
        this(method2);
        this.retType = source2.getReturnType();
        this.source = source2;
    }

    public PrimProcedure(int opcode, Type retType2, Type[] argTypes2) {
        this.op_code = opcode;
        this.retType = retType2;
        this.argTypes = argTypes2;
    }

    public static PrimProcedure makeBuiltinUnary(int opcode, Type type) {
        return new PrimProcedure(opcode, type, new Type[]{type});
    }

    public static PrimProcedure makeBuiltinBinary(int opcode, Type type) {
        return new PrimProcedure(opcode, type, new Type[]{type, type});
    }

    public PrimProcedure(int op_code2, ClassType classtype, String name, Type retType2, Type[] argTypes2) {
        this.op_code = op_code2;
        char c = 0;
        this.method = classtype.addMethod(name, op_code2 == 184 ? 8 : 0, argTypes2, retType2);
        this.retType = retType2;
        this.argTypes = argTypes2;
        this.mode = op_code2 != 184 ? 'V' : c;
    }

    public final boolean getStaticFlag() {
        return this.method == null || this.method.getStaticFlag() || isConstructor();
    }

    public final Type[] getParameterTypes() {
        return this.argTypes;
    }

    private void compileArgs(Expression[] args, int startArg, Type thisType, Compilation comp) {
        int fix_arg_count;
        boolean variable;
        PrimProcedure primProcedure = this;
        Expression[] expressionArr = args;
        Type type = thisType;
        Compilation compilation = comp;
        boolean variable2 = takesVarArgs();
        String name = getName();
        Type arg_type = null;
        CodeAttr code = comp.getCode();
        int skipArg = type == Type.voidType ? 1 : 0;
        int arg_count = primProcedure.argTypes.length - skipArg;
        if (takesContext()) {
            arg_count--;
        }
        int nargs = expressionArr.length - startArg;
        boolean is_static = type == null || skipArg != 0;
        boolean createVarargsArrayIfNeeded = false;
        if (variable2 && (primProcedure.method.getModifiers() & 128) != 0 && nargs > 0 && primProcedure.argTypes.length > 0) {
            if (nargs == (is_static ? 0 : 1) + arg_count) {
                Type lastType = expressionArr[expressionArr.length - 1].getType();
                Type lastParam = primProcedure.argTypes[primProcedure.argTypes.length - 1];
                if ((lastType instanceof ObjectType) && (lastParam instanceof ArrayType) && !(((ArrayType) lastParam).getComponentType() instanceof ArrayType)) {
                    if (!(lastType instanceof ArrayType)) {
                        createVarargsArrayIfNeeded = true;
                    }
                    variable2 = false;
                }
            }
        }
        if (variable2) {
            fix_arg_count = arg_count - (is_static ? 1 : 0);
        } else {
            fix_arg_count = expressionArr.length - startArg;
        }
        Declaration argDecl = primProcedure.source == null ? null : primProcedure.source.firstDecl();
        if (argDecl != null && argDecl.isThisParameter()) {
            argDecl = argDecl.nextDecl();
        }
        int i = 0;
        while (true) {
            if (variable2 && i == fix_arg_count) {
                Type arg_type2 = primProcedure.argTypes[(arg_count - 1) + skipArg];
                if (arg_type2 == Compilation.scmListType || arg_type2 == LangObjType.listType) {
                    MakeList.compile(expressionArr, startArg + i, compilation);
                } else {
                    code.emitPushInt((expressionArr.length - startArg) - fix_arg_count);
                    arg_type = ((ArrayType) arg_type2).getComponentType();
                    code.emitNewArray(arg_type);
                }
            }
            if (i < nargs) {
                boolean createVarargsNow = createVarargsArrayIfNeeded && i + 1 == nargs;
                if (i >= fix_arg_count) {
                    variable = variable2;
                    code.emitDup(1);
                    code.emitPushInt(i - fix_arg_count);
                } else {
                    variable = variable2;
                    arg_type = (argDecl == null || (!is_static && i <= 0)) ? is_static ? primProcedure.argTypes[i + skipArg] : i == 0 ? thisType : primProcedure.argTypes[i - 1] : argDecl.getType();
                }
                compilation.usedClass(arg_type);
                Type argTypeForTarget = createVarargsNow ? Type.objectType : arg_type;
                int skipArg2 = skipArg;
                Type type2 = argTypeForTarget;
                expressionArr[startArg + i].compileNotePosition(compilation, primProcedure.source == null ? CheckedTarget.getInstance(argTypeForTarget, name, i + 1) : CheckedTarget.getInstance(argTypeForTarget, primProcedure.source, i), expressionArr[startArg + i]);
                if (createVarargsNow) {
                    Type eltype = ((ArrayType) arg_type).getComponentType();
                    code.emitDup();
                    code.emitInstanceof(arg_type);
                    code.emitIfIntNotZero();
                    code.emitCheckcast(arg_type);
                    code.emitElse();
                    code.emitPushInt(1);
                    code.emitNewArray(eltype);
                    code.emitDupX();
                    code.emitSwap();
                    code.emitPushInt(0);
                    code.emitSwap();
                    eltype.emitCoerceFromObject(code);
                    code.emitArrayStore(arg_type);
                    code.emitFi();
                }
                if (i >= fix_arg_count) {
                    code.emitArrayStore(arg_type);
                }
                if (argDecl != null && (is_static || i > 0)) {
                    argDecl = argDecl.nextDecl();
                }
                i++;
                primProcedure = this;
                variable2 = variable;
                skipArg = skipArg2;
            } else {
                return;
            }
        }
        MakeList.compile(expressionArr, startArg + i, compilation);
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        CodeAttr code = comp.getCode();
        ClassType classType = null;
        ClassType mclass = this.method == null ? null : this.method.getDeclaringClass();
        Expression[] args = exp.getArgs();
        if (isConstructor()) {
            if (exp.getFlag(8)) {
                int nargs = args.length;
                comp.letStart();
                Expression[] xargs = new Expression[nargs];
                xargs[0] = args[0];
                for (int i = 1; i < nargs; i++) {
                    Expression argi = args[i];
                    Declaration d = comp.letVariable((Object) null, argi.getType(), argi);
                    d.setCanRead(true);
                    xargs[i] = new ReferenceExp(d);
                }
                comp.letEnter();
                comp.letDone(new ApplyExp(exp.func, xargs)).compile(comp, target);
                return;
            }
            code.emitNew(mclass);
            code.emitDup((Type) mclass);
        }
        String arg_error = WrongArguments.checkArgCount(this, args.length);
        if (arg_error != null) {
            comp.error('e', arg_error);
        }
        if (!getStaticFlag()) {
            classType = mclass;
        }
        compile(classType, exp, comp, target);
    }

    /* access modifiers changed from: package-private */
    public void compile(Type thisType, ApplyExp exp, Compilation comp, Target target) {
        int startArg;
        Expression[] args = exp.getArgs();
        CodeAttr code = comp.getCode();
        Type stackType = this.retType;
        int startArg2 = 0;
        ClassType mclass = null;
        if (isConstructor()) {
            if (this.method != null) {
                mclass = this.method.getDeclaringClass();
            }
            ClassType mclass2 = mclass;
            if (mclass2.hasOuterLink()) {
                ClassExp.loadSuperStaticLink(args[0], mclass2, comp);
            }
            thisType = null;
            startArg = 1;
        } else if (opcode() == 183 && this.mode == 'P' && "<init>".equals(this.method.getName())) {
            if (this.method != null) {
                mclass = this.method.getDeclaringClass();
            }
            if (mclass.hasOuterLink()) {
                code.emitPushThis();
                code.emitLoad(code.getCurrentScope().getVariable(1));
                thisType = null;
                startArg2 = 1;
            }
            startArg = startArg2;
        } else if (!takesTarget() || !this.method.getStaticFlag()) {
            startArg = 0;
        } else {
            startArg = 1;
        }
        compileArgs(args, startArg, thisType, comp);
        if (this.method == null) {
            code.emitPrimop(opcode(), args.length, this.retType);
            target.compileFromStack(comp, stackType);
            return;
        }
        compileInvoke(comp, this.method, target, exp.isTailCall(), this.op_code, stackType);
    }

    public static void compileInvoke(Compilation comp, Method method2, Target target, boolean isTailCall, int op_code2, Type stackType) {
        CodeAttr code = comp.getCode();
        comp.usedClass(method2.getDeclaringClass());
        comp.usedClass(method2.getReturnType());
        if (!takesContext(method2)) {
            code.emitInvokeMethod(method2, op_code2);
        } else if ((target instanceof IgnoreTarget) || ((target instanceof ConsumerTarget) && ((ConsumerTarget) target).isContextTarget())) {
            Field consumerFld = null;
            Variable saveCallContext = null;
            comp.loadCallContext();
            if (target instanceof IgnoreTarget) {
                ClassType typeCallContext = Compilation.typeCallContext;
                consumerFld = typeCallContext.getDeclaredField("consumer");
                code.pushScope();
                saveCallContext = code.addLocal(typeCallContext);
                code.emitDup();
                code.emitGetField(consumerFld);
                code.emitStore(saveCallContext);
                code.emitDup();
                code.emitGetStatic(ClassType.make("gnu.lists.VoidConsumer").getDeclaredField("instance"));
                code.emitPutField(consumerFld);
            }
            code.emitInvokeMethod(method2, op_code2);
            if (isTailCall) {
                comp.loadCallContext();
                code.emitInvoke(Compilation.typeCallContext.getDeclaredMethod("runUntilDone", 0));
            }
            if (target instanceof IgnoreTarget) {
                comp.loadCallContext();
                code.emitLoad(saveCallContext);
                code.emitPutField(consumerFld);
                code.popScope();
                return;
            }
            return;
        } else {
            comp.loadCallContext();
            stackType = Type.objectType;
            code.pushScope();
            Variable saveIndex = code.addLocal(Type.intType);
            comp.loadCallContext();
            code.emitInvokeVirtual(Compilation.typeCallContext.getDeclaredMethod("startFromContext", 0));
            code.emitStore(saveIndex);
            code.emitWithCleanupStart();
            code.emitInvokeMethod(method2, op_code2);
            code.emitWithCleanupCatch((Variable) null);
            comp.loadCallContext();
            code.emitLoad(saveIndex);
            code.emitInvokeVirtual(Compilation.typeCallContext.getDeclaredMethod("cleanupFromContext", 1));
            code.emitWithCleanupDone();
            comp.loadCallContext();
            code.emitLoad(saveIndex);
            code.emitInvokeVirtual(Compilation.typeCallContext.getDeclaredMethod("getFromContext", 1));
            code.popScope();
        }
        target.compileFromStack(comp, stackType);
    }

    public Type getParameterType(int index) {
        if (takesTarget()) {
            if (index == 0) {
                return isConstructor() ? Type.objectType : this.method.getDeclaringClass();
            }
            index--;
        }
        int lenTypes = this.argTypes.length;
        if (index < lenTypes - 1) {
            return this.argTypes[index];
        }
        boolean varArgs = takesVarArgs();
        if (index < lenTypes && !varArgs) {
            return this.argTypes[index];
        }
        Type restType = this.argTypes[lenTypes - 1];
        if (restType instanceof ArrayType) {
            return ((ArrayType) restType).getComponentType();
        }
        return Type.objectType;
    }

    public static PrimProcedure getMethodFor(Procedure pproc, Expression[] args) {
        return getMethodFor(pproc, (Declaration) null, args, Language.getDefaultLanguage());
    }

    public static PrimProcedure getMethodFor(Procedure pproc, Declaration decl, Expression[] args, Language language) {
        int nargs = args.length;
        Type[] atypes = new Type[nargs];
        int i = nargs;
        while (true) {
            i--;
            if (i < 0) {
                return getMethodFor(pproc, decl, atypes, language);
            }
            atypes[i] = args[i].getType();
        }
    }

    public static PrimProcedure getMethodFor(Procedure pproc, Declaration decl, Type[] atypes, Language language) {
        Procedure pproc2;
        boolean z = pproc instanceof GenericProc;
        Procedure pproc3 = pproc;
        if (z) {
            GenericProc gproc = (GenericProc) pproc;
            Procedure[] methods = gproc.methods;
            Procedure pproc4 = null;
            int i = gproc.count;
            while (true) {
                i--;
                if (i >= 0) {
                    if (methods[i].isApplicable(atypes) < 0) {
                        pproc2 = pproc4;
                    } else if (pproc4 != null) {
                        return null;
                    } else {
                        pproc2 = methods[i];
                    }
                    pproc4 = pproc2;
                } else {
                    pproc3 = pproc4;
                    if (pproc4 == null) {
                        return null;
                    }
                }
            }
        }
        if (pproc3 instanceof PrimProcedure) {
            PrimProcedure prproc = (PrimProcedure) pproc3;
            if (prproc.isApplicable(atypes) >= 0) {
                return prproc;
            }
        }
        Class pclass = getProcedureClass(pproc3);
        if (pclass == null) {
            return null;
        }
        return getMethodFor((ClassType) Type.make(pclass), pproc3.getName(), decl, atypes, language);
    }

    public static void disassemble$X(Procedure pproc, CallContext ctx) throws Exception {
        Consumer cons = ctx.consumer;
        disassemble(pproc, cons instanceof Writer ? (Writer) cons : new ConsumerWriter(cons));
    }

    public static void disassemble(Procedure proc, Writer out) throws Exception {
        disassemble(proc, new ClassTypeWriter((ClassType) null, out, 0));
    }

    public static void disassemble(Procedure proc, ClassTypeWriter cwriter) throws Exception {
        Method pmethod;
        if (proc instanceof GenericProc) {
            GenericProc gproc = (GenericProc) proc;
            int n = gproc.getMethodCount();
            cwriter.print("Generic procedure with ");
            cwriter.print(n);
            cwriter.println(n == 1 ? " method." : "methods.");
            for (int i = 0; i < n; i++) {
                Procedure mproc = gproc.getMethod(i);
                if (mproc != null) {
                    cwriter.println();
                    disassemble(mproc, cwriter);
                }
            }
            return;
        }
        String pname = null;
        Class cl = proc.getClass();
        if (proc instanceof ModuleMethod) {
            cl = ((ModuleMethod) proc).module.getClass();
        } else if ((proc instanceof PrimProcedure) && (pmethod = ((PrimProcedure) proc).method) != null) {
            cl = pmethod.getDeclaringClass().getReflectClass();
            pname = pmethod.getName();
        }
        ClassLoader loader = cl.getClassLoader();
        String cname = cl.getName();
        String rname = cname.replace('.', '/') + ".class";
        ClassType ctype = new ClassType();
        InputStream rin = loader.getResourceAsStream(rname);
        if (rin != null) {
            new ClassFileInput(ctype, rin);
            cwriter.setClass(ctype);
            URL resource = loader.getResource(rname);
            cwriter.print("In class ");
            cwriter.print(cname);
            if (resource != null) {
                cwriter.print(" at ");
                cwriter.print(resource);
            }
            cwriter.println();
            if (pname == null) {
                String pname2 = proc.getName();
                if (pname2 == null) {
                    cwriter.println("Anonymous function - unknown method.");
                    return;
                }
                pname = Compilation.mangleName(pname2);
            }
            for (Method method2 = ctype.getMethods(); method2 != null; method2 = method2.getNext()) {
                if (method2.getName().equals(pname)) {
                    cwriter.printMethod(method2);
                }
            }
            cwriter.flush();
            return;
        }
        throw new RuntimeException("missing resource " + rname);
    }

    public static Class getProcedureClass(Object pproc) {
        Class procClass;
        if (pproc instanceof ModuleMethod) {
            procClass = ((ModuleMethod) pproc).module.getClass();
        } else {
            procClass = pproc.getClass();
        }
        try {
            if (procClass.getClassLoader() == systemClassLoader) {
                return procClass;
            }
            return null;
        } catch (SecurityException e) {
            return null;
        }
    }

    public static PrimProcedure getMethodFor(Class procClass, String name, Declaration decl, Expression[] args, Language language) {
        return getMethodFor((ClassType) Type.make(procClass), name, decl, args, language);
    }

    public static PrimProcedure getMethodFor(ClassType procClass, String name, Declaration decl, Expression[] args, Language language) {
        int nargs = args.length;
        Type[] atypes = new Type[nargs];
        int i = nargs;
        while (true) {
            i--;
            if (i < 0) {
                return getMethodFor(procClass, name, decl, atypes, language);
            }
            atypes[i] = args[i].getType();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x009b A[Catch:{ SecurityException -> 0x00f8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00b7 A[Catch:{ SecurityException -> 0x00ed }] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00de A[Catch:{ SecurityException -> 0x00ed }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static gnu.expr.PrimProcedure getMethodFor(gnu.bytecode.ClassType r18, java.lang.String r19, gnu.expr.Declaration r20, gnu.bytecode.Type[] r21, gnu.expr.Language r22) {
        /*
            r1 = r19
            r2 = r20
            r3 = 0
            r4 = -1
            r5 = 0
            r0 = 0
            if (r1 != 0) goto L_0x000b
            return r0
        L_0x000b:
            java.lang.String r6 = gnu.expr.Compilation.mangleName(r19)     // Catch:{ SecurityException -> 0x00f8 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ SecurityException -> 0x00f8 }
            r7.<init>()     // Catch:{ SecurityException -> 0x00f8 }
            java.lang.StringBuilder r7 = r7.append(r6)     // Catch:{ SecurityException -> 0x00f8 }
            java.lang.String r8 = "$V"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ SecurityException -> 0x00f8 }
            java.lang.String r7 = r7.toString()     // Catch:{ SecurityException -> 0x00f8 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ SecurityException -> 0x00f8 }
            r8.<init>()     // Catch:{ SecurityException -> 0x00f8 }
            java.lang.StringBuilder r8 = r8.append(r6)     // Catch:{ SecurityException -> 0x00f8 }
            java.lang.String r9 = "$V$X"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ SecurityException -> 0x00f8 }
            java.lang.String r8 = r8.toString()     // Catch:{ SecurityException -> 0x00f8 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ SecurityException -> 0x00f8 }
            r9.<init>()     // Catch:{ SecurityException -> 0x00f8 }
            java.lang.StringBuilder r9 = r9.append(r6)     // Catch:{ SecurityException -> 0x00f8 }
            java.lang.String r10 = "$X"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ SecurityException -> 0x00f8 }
            java.lang.String r9 = r9.toString()     // Catch:{ SecurityException -> 0x00f8 }
            r10 = 1
            gnu.bytecode.Method r11 = r18.getDeclaredMethods()     // Catch:{ SecurityException -> 0x00f8 }
        L_0x004d:
            if (r11 == 0) goto L_0x00f3
            int r12 = r11.getModifiers()     // Catch:{ SecurityException -> 0x00f8 }
            r13 = r12 & 9
            r14 = 9
            if (r13 == r14) goto L_0x0060
            if (r2 == 0) goto L_0x0091
            gnu.expr.Declaration r13 = r2.base     // Catch:{ SecurityException -> 0x00f8 }
            if (r13 != 0) goto L_0x0060
            goto L_0x0091
        L_0x0060:
            java.lang.String r13 = r11.getName()     // Catch:{ SecurityException -> 0x00f8 }
            boolean r14 = r13.equals(r6)     // Catch:{ SecurityException -> 0x00f8 }
            if (r14 != 0) goto L_0x0098
            boolean r14 = r13.equals(r7)     // Catch:{ SecurityException -> 0x00f8 }
            if (r14 != 0) goto L_0x0098
            boolean r14 = r13.equals(r9)     // Catch:{ SecurityException -> 0x00f8 }
            if (r14 != 0) goto L_0x0098
            boolean r14 = r13.equals(r8)     // Catch:{ SecurityException -> 0x00f8 }
            if (r14 == 0) goto L_0x007d
            goto L_0x0098
        L_0x007d:
            if (r10 == 0) goto L_0x0091
            java.lang.String r14 = "apply"
            boolean r14 = r13.equals(r14)     // Catch:{ SecurityException -> 0x00f8 }
            if (r14 != 0) goto L_0x008f
            java.lang.String r14 = "apply$V"
            boolean r14 = r13.equals(r14)     // Catch:{ SecurityException -> 0x00f8 }
            if (r14 == 0) goto L_0x0091
        L_0x008f:
            r14 = 1
            goto L_0x0099
        L_0x0091:
            r1 = r21
            r2 = r22
            r16 = r0
            goto L_0x00e0
        L_0x0098:
            r14 = 0
        L_0x0099:
            if (r14 != 0) goto L_0x00a1
            r10 = 0
            if (r5 == 0) goto L_0x00a1
            r3 = 0
            r4 = -1
            r5 = 0
        L_0x00a1:
            gnu.expr.PrimProcedure r15 = new gnu.expr.PrimProcedure     // Catch:{ SecurityException -> 0x00f8 }
            r2 = r22
            r15.<init>((gnu.bytecode.Method) r11, (gnu.expr.Language) r2)     // Catch:{ SecurityException -> 0x00ef }
            r15.setName(r1)     // Catch:{ SecurityException -> 0x00ef }
            r1 = r21
            int r16 = r15.isApplicable(r1)     // Catch:{ SecurityException -> 0x00ed }
            r17 = r16
            r0 = r17
            if (r0 < 0) goto L_0x00de
            if (r0 >= r4) goto L_0x00bc
            r16 = 0
            goto L_0x00e0
        L_0x00bc:
            if (r0 <= r4) goto L_0x00c2
            r3 = r15
            r16 = 0
            goto L_0x00db
        L_0x00c2:
            if (r3 == 0) goto L_0x00d9
            gnu.mapping.MethodProc r17 = gnu.mapping.MethodProc.mostSpecific((gnu.mapping.MethodProc) r3, (gnu.mapping.MethodProc) r15)     // Catch:{ SecurityException -> 0x00ed }
            gnu.expr.PrimProcedure r17 = (gnu.expr.PrimProcedure) r17     // Catch:{ SecurityException -> 0x00ed }
            r3 = r17
            if (r3 != 0) goto L_0x00d6
            if (r4 <= 0) goto L_0x00d3
            r16 = 0
            return r16
        L_0x00d3:
            r16 = 0
            goto L_0x00db
        L_0x00d6:
            r16 = 0
            goto L_0x00db
        L_0x00d9:
            r16 = 0
        L_0x00db:
            r4 = r0
            r5 = r14
            goto L_0x00e0
        L_0x00de:
            r16 = 0
        L_0x00e0:
            gnu.bytecode.Method r0 = r11.getNext()     // Catch:{ SecurityException -> 0x00ed }
            r11 = r0
            r1 = r19
            r2 = r20
            r0 = r16
            goto L_0x004d
        L_0x00ed:
            r0 = move-exception
            goto L_0x00fd
        L_0x00ef:
            r0 = move-exception
            r1 = r21
            goto L_0x00fd
        L_0x00f3:
            r1 = r21
            r2 = r22
            goto L_0x00fd
        L_0x00f8:
            r0 = move-exception
            r1 = r21
            r2 = r22
        L_0x00fd:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.PrimProcedure.getMethodFor(gnu.bytecode.ClassType, java.lang.String, gnu.expr.Declaration, gnu.bytecode.Type[], gnu.expr.Language):gnu.expr.PrimProcedure");
    }

    public String getName() {
        String name = super.getName();
        if (name != null) {
            return name;
        }
        String name2 = getVerboseName();
        setName(name2);
        return name2;
    }

    public String getVerboseName() {
        StringBuffer buf = new StringBuffer(100);
        if (this.method == null) {
            buf.append("<op ");
            buf.append(this.op_code);
            buf.append('>');
        } else {
            buf.append(this.method.getDeclaringClass().getName());
            buf.append('.');
            buf.append(this.method.getName());
        }
        buf.append('(');
        for (int i = 0; i < this.argTypes.length; i++) {
            if (i > 0) {
                buf.append(',');
            }
            buf.append(this.argTypes[i].getName());
        }
        buf.append(')');
        return buf.toString();
    }

    public String toString() {
        StringBuffer buf = new StringBuffer(100);
        buf.append(this.retType == null ? "<unknown>" : this.retType.getName());
        buf.append(' ');
        buf.append(getVerboseName());
        return buf.toString();
    }

    public void print(PrintWriter ps) {
        ps.print("#<primitive procedure ");
        ps.print(toString());
        ps.print('>');
    }
}
