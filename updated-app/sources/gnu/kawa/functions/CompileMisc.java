package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.ConditionalTarget;
import gnu.expr.ConsumerTarget;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.IgnoreTarget;
import gnu.expr.InlineCalls;
import gnu.expr.Inlineable;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.expr.TryExp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.lists.LList;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.WrongArguments;
import kawa.standard.Scheme;

public class CompileMisc implements Inlineable {
    static final int CONVERT = 2;
    static final int NOT = 3;
    static Method coerceMethod;
    public static final ClassType typeContinuation = ClassType.make("kawa.lang.Continuation");
    static ClassType typeType;
    int code;
    Procedure proc;

    public CompileMisc(Procedure proc2, int code2) {
        this.proc = proc2;
        this.code = code2;
    }

    public static CompileMisc forConvert(Object proc2) {
        return new CompileMisc((Procedure) proc2, 2);
    }

    public static CompileMisc forNot(Object proc2) {
        return new CompileMisc((Procedure) proc2, 3);
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        switch (this.code) {
            case 2:
                compileConvert((Convert) this.proc, exp, comp, target);
                return;
            case 3:
                compileNot((Not) this.proc, exp, comp, target);
                return;
            default:
                throw new Error();
        }
    }

    public static Expression validateApplyConstantFunction0(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        exp.visitArgs(visitor);
        int nargs = exp.getArgCount();
        if (nargs == 0 || visitor == null) {
            return ((ConstantFunction0) proc2).constant;
        }
        return visitor.noteError(WrongArguments.checkArgCount(proc2, nargs));
    }

    public static Expression validateApplyConvert(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        Compilation comp = visitor.getCompilation();
        Language language = comp.getLanguage();
        Expression[] args = exp.getArgs();
        if (args.length == 2) {
            args[0] = visitor.visit(args[0], (Type) null);
            Type type = language.getTypeFor(args[0]);
            if (type instanceof Type) {
                args[0] = new QuoteExp(type);
                args[1] = visitor.visit(args[1], type);
                CompileReflect.checkKnownClass(type, comp);
                exp.setType(type);
                return exp;
            }
        }
        exp.visitArgs(visitor);
        return exp;
    }

    public static Expression validateApplyNot(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        exp.visitArgs(visitor);
        exp.setType(visitor.getCompilation().getLanguage().getTypeFor(Boolean.TYPE));
        return exp.inlineIfConstant(proc2, visitor);
    }

    public static Expression validateApplyFormat(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        exp.visitArgs(visitor);
        Type retType = Type.objectType;
        Expression[] args = exp.getArgs();
        if (args.length > 0) {
            ClassType typeFormat = ClassType.make("gnu.kawa.functions.Format");
            Object f = args[0].valueIfConstant();
            Type ftype = args[0].getType();
            if (f == Boolean.FALSE || ftype.isSubtype(LangObjType.stringType)) {
                int skip = f == Boolean.FALSE ? 1 : 0;
                Expression[] xargs = new Expression[((args.length + 1) - skip)];
                xargs[0] = new QuoteExp(0, Type.intType);
                System.arraycopy(args, skip, xargs, 1, xargs.length - 1);
                ApplyExp ae = new ApplyExp(typeFormat.getDeclaredMethod("formatToString", 2), xargs);
                ae.setType(Type.javalangStringType);
                return ae;
            } else if (f == Boolean.TRUE || ftype.isSubtype(ClassType.make("java.io.Writer"))) {
                if (f == Boolean.TRUE) {
                    Expression[] xargs2 = new Expression[args.length];
                    xargs2[0] = QuoteExp.nullExp;
                    System.arraycopy(args, 1, xargs2, 1, args.length - 1);
                    args = xargs2;
                }
                ApplyExp ae2 = new ApplyExp(typeFormat.getDeclaredMethod("formatToWriter", 3), args);
                ae2.setType(Type.voidType);
                return ae2;
            } else if (ftype.isSubtype(ClassType.make("java.io.OutputStream"))) {
                retType = Type.voidType;
            }
        }
        exp.setType(retType);
        return null;
    }

    public static Expression validateApplyAppendValues(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length == 1) {
            return args[0];
        }
        if (args.length == 0) {
            return QuoteExp.voidExp;
        }
        Expression folded = exp.inlineIfConstant(proc2, visitor);
        if (folded != exp) {
            return folded;
        }
        return exp;
    }

    public static Expression validateApplyMakeProcedure(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        int alen = args.length;
        LambdaExp method = null;
        int countMethods = 0;
        String name = null;
        int i = 0;
        while (i < alen) {
            Expression arg = args[i];
            if (arg instanceof QuoteExp) {
                Object value = ((QuoteExp) arg).getValue();
                Object key = value;
                if (value instanceof Keyword) {
                    String keyword = ((Keyword) key).getName();
                    i++;
                    Expression next = args[i];
                    if (keyword == "name") {
                        if (next instanceof QuoteExp) {
                            name = ((QuoteExp) next).getValue().toString();
                        }
                    } else if (keyword == "method") {
                        countMethods++;
                        method = next;
                    }
                    i++;
                }
            }
            countMethods++;
            method = arg;
            i++;
        }
        if (countMethods != 1 || !(method instanceof LambdaExp)) {
            return exp;
        }
        LambdaExp lexp = method;
        int i2 = 0;
        while (i2 < alen) {
            Expression arg2 = args[i2];
            if (arg2 instanceof QuoteExp) {
                Object value2 = ((QuoteExp) arg2).getValue();
                Object key2 = value2;
                if (value2 instanceof Keyword) {
                    String keyword2 = ((Keyword) key2).getName();
                    i2++;
                    Expression next2 = args[i2];
                    if (keyword2 == "name") {
                        lexp.setName(name);
                    } else if (keyword2 != "method") {
                        lexp.setProperty(Namespace.EmptyNamespace.getSymbol(keyword2), next2);
                    }
                }
            }
            i2++;
        }
        return method;
    }

    public static Expression validateApplyValuesMap(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        exp.visitArgs(visitor);
        LambdaExp lexp = ValuesMap.canInline(exp, (ValuesMap) proc2);
        if (lexp != null) {
            lexp.setInlineOnly(true);
            lexp.returnContinuation = exp;
            lexp.inlineHome = visitor.getCurrentLambda();
        }
        return exp;
    }

    public static void compileConvert(Convert proc2, ApplyExp exp, Compilation comp, Target target) {
        Expression[] args = exp.getArgs();
        if (args.length == 2) {
            CodeAttr code2 = comp.getCode();
            Type type = Scheme.getTypeValue(args[0]);
            if (type != null) {
                args[1].compile(comp, Target.pushValue(type));
                if (code2.reachableHere()) {
                    target.compileFromStack(comp, type);
                    return;
                }
                return;
            }
            if (typeType == null) {
                typeType = ClassType.make("gnu.bytecode.Type");
            }
            if (coerceMethod == null) {
                coerceMethod = typeType.addMethod("coerceFromObject", Compilation.apply1args, (Type) Type.pointer_type, 1);
            }
            args[0].compile(comp, (Type) LangObjType.typeClassType);
            args[1].compile(comp, Target.pushObject);
            code2.emitInvokeVirtual(coerceMethod);
            target.compileFromStack(comp, Type.pointer_type);
            return;
        }
        throw new Error("wrong number of arguments to " + proc2.getName());
    }

    public void compileNot(Not proc2, ApplyExp exp, Compilation comp, Target target) {
        Expression arg = exp.getArgs()[0];
        Language language = proc2.language;
        if (target instanceof ConditionalTarget) {
            ConditionalTarget ctarget = (ConditionalTarget) target;
            ConditionalTarget sub_target = new ConditionalTarget(ctarget.ifFalse, ctarget.ifTrue, language);
            sub_target.trueBranchComesFirst = true ^ ctarget.trueBranchComesFirst;
            arg.compile(comp, (Target) sub_target);
            return;
        }
        CodeAttr code2 = comp.getCode();
        Type type = target.getType();
        if (!(target instanceof StackTarget) || type.getSignature().charAt(0) != 'Z') {
            IfExp.compile(arg, QuoteExp.getInstance(language.booleanObject(false)), QuoteExp.getInstance(language.booleanObject(true)), comp, target);
            return;
        }
        arg.compile(comp, target);
        code2.emitNot(target.getType());
    }

    public static Expression validateApplyCallCC(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        LambdaExp lexp = canInlineCallCC(exp);
        if (lexp != null) {
            lexp.setInlineOnly(true);
            lexp.returnContinuation = exp;
            lexp.inlineHome = visitor.getCurrentLambda();
            Declaration contDecl = lexp.firstDecl();
            if (!contDecl.getFlag(8192)) {
                contDecl.setType(typeContinuation);
            }
        }
        exp.visitArgs(visitor);
        return exp;
    }

    public static void compileCallCC(ApplyExp exp, Compilation comp, Target target, Procedure proc2) {
        LambdaExp lambda = canInlineCallCC(exp);
        if (lambda == null) {
            ApplyExp.compile(exp, comp, target);
            return;
        }
        CodeAttr code2 = comp.getCode();
        Declaration param = lambda.firstDecl();
        Type rtype = null;
        if (!param.isSimple() || param.getCanRead() || param.getCanWrite()) {
            Variable contVar = code2.pushScope().addVariable(code2, typeContinuation, (String) null);
            Declaration contDecl = new Declaration(contVar);
            code2.emitNew(typeContinuation);
            code2.emitDup((Type) typeContinuation);
            comp.loadCallContext();
            code2.emitInvokeSpecial(typeContinuation.getDeclaredMethod("<init>", 1));
            code2.emitStore(contVar);
            code2.emitTryStart(false, ((target instanceof IgnoreTarget) || (target instanceof ConsumerTarget)) ? null : Type.objectType);
            new ApplyExp((Expression) lambda, new ReferenceExp(contDecl)).compile(comp, target);
            if (code2.reachableHere()) {
                code2.emitLoad(contVar);
                code2.emitPushInt(1);
                code2.emitPutField(typeContinuation.getField("invoked"));
            }
            code2.emitTryEnd();
            code2.emitCatchStart((Variable) null);
            code2.emitLoad(contVar);
            if (target instanceof ConsumerTarget) {
                comp.loadCallContext();
                code2.emitInvokeStatic(typeContinuation.getDeclaredMethod("handleException$X", 3));
            } else {
                code2.emitInvokeStatic(typeContinuation.getDeclaredMethod("handleException", 2));
                target.compileFromStack(comp, Type.objectType);
            }
            code2.emitCatchEnd();
            code2.emitTryCatchEnd();
            code2.popScope();
            return;
        }
        param.setCanCall(false);
        CompileTimeContinuation contProxy = new CompileTimeContinuation();
        if (target instanceof StackTarget) {
            rtype = target.getType();
        }
        contProxy.exitableBlock = code2.startExitableBlock(rtype, ExitThroughFinallyChecker.check(param, lambda.body));
        contProxy.blockTarget = target;
        param.setValue(new QuoteExp(contProxy));
        new ApplyExp((Expression) lambda, QuoteExp.nullExp).compile(comp, target);
        code2.endExitableBlock();
    }

    private static LambdaExp canInlineCallCC(ApplyExp exp) {
        Expression[] args = exp.getArgs();
        if (args.length != 1) {
            return null;
        }
        Expression expression = args[0];
        Expression arg0 = expression;
        if (!(expression instanceof LambdaExp)) {
            return null;
        }
        LambdaExp lexp = (LambdaExp) arg0;
        if (lexp.min_args == 1 && lexp.max_args == 1 && !lexp.firstDecl().getCanWrite()) {
            return lexp;
        }
        return null;
    }

    static class ExitThroughFinallyChecker extends ExpVisitor<Expression, TryExp> {
        Declaration decl;

        ExitThroughFinallyChecker() {
        }

        public static boolean check(Declaration decl2, Expression body) {
            ExitThroughFinallyChecker visitor = new ExitThroughFinallyChecker();
            visitor.decl = decl2;
            visitor.visit(body, null);
            return visitor.exitValue != null;
        }

        /* access modifiers changed from: protected */
        public Expression defaultValue(Expression r, TryExp d) {
            return r;
        }

        /* access modifiers changed from: protected */
        public Expression visitReferenceExp(ReferenceExp exp, TryExp currentTry) {
            if (this.decl == exp.getBinding() && currentTry != null) {
                this.exitValue = Boolean.TRUE;
            }
            return exp;
        }

        /* access modifiers changed from: protected */
        public Expression visitTryExp(TryExp exp, TryExp currentTry) {
            visitExpression(exp, exp.getFinallyClause() != null ? exp : currentTry);
            return exp;
        }
    }

    public static Expression validateApplyMap(ApplyExp exp, InlineCalls visitor, Type required, Procedure xproc) {
        Expression proc2;
        InlineCalls inlineCalls = visitor;
        Map mproc = (Map) xproc;
        boolean collect = mproc.collect;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        int nargs = args.length;
        if (nargs < 2) {
            return exp;
        }
        int nargs2 = nargs - 1;
        Expression proc3 = args[0];
        boolean procSafeForMultipleEvaluation = !proc3.side_effects();
        Expression[] inits1 = {proc3};
        LetExp let1 = new LetExp(inits1);
        Declaration procDecl = let1.addDeclaration("%proc", Compilation.typeProcedure);
        procDecl.noteValue(args[0]);
        Expression[] inits2 = new Expression[1];
        LetExp let2 = new LetExp(inits2);
        let1.setBody(let2);
        LambdaExp lexp = new LambdaExp(collect ? nargs2 + 1 : nargs2);
        inits2[0] = lexp;
        Declaration loopDecl = let2.addDeclaration((Object) "%loop");
        loopDecl.noteValue(lexp);
        Expression[] inits3 = new Expression[nargs2];
        LetExp let3 = new LetExp(inits3);
        Expression proc4 = proc3;
        Declaration[] largs = new Declaration[nargs2];
        Expression[] expressionArr = inits1;
        Declaration[] pargs = new Declaration[nargs2];
        Expression[] expressionArr2 = inits2;
        int i = 0;
        while (i < nargs2) {
            LetExp let12 = let1;
            String argName = "arg" + i;
            largs[i] = lexp.addDeclaration((Object) argName);
            pargs[i] = let3.addDeclaration(argName, Compilation.typePair);
            String str = argName;
            inits3[i] = new ReferenceExp(largs[i]);
            pargs[i].noteValue(inits3[i]);
            i++;
            let1 = let12;
            let2 = let2;
        }
        LetExp let13 = let1;
        LetExp let22 = let2;
        Declaration resultDecl = collect ? lexp.addDeclaration((Object) "result") : null;
        Expression[] doArgs = new Expression[(nargs2 + 1)];
        Expression[] recArgs = new Expression[(collect ? nargs2 + 1 : nargs2)];
        Expression[] expressionArr3 = inits3;
        int i2 = 0;
        while (i2 < nargs2) {
            doArgs[i2 + 1] = inlineCalls.visitApplyOnly(SlotGet.makeGetField(new ReferenceExp(pargs[i2]), "car"), (Type) null);
            recArgs[i2] = inlineCalls.visitApplyOnly(SlotGet.makeGetField(new ReferenceExp(pargs[i2]), "cdr"), (Type) null);
            i2++;
            args = args;
            largs = largs;
        }
        Expression[] args2 = args;
        Declaration[] largs2 = largs;
        if (!procSafeForMultipleEvaluation) {
            proc2 = new ReferenceExp(procDecl);
        } else {
            proc2 = proc4;
        }
        doArgs[0] = proc2;
        Expression expression = proc2;
        Expression doit = inlineCalls.visitApplyOnly(new ApplyExp((Expression) new ReferenceExp(mproc.applyFieldDecl), doArgs), (Type) null);
        if (collect) {
            Declaration[] declarationArr = pargs;
            recArgs[nargs2] = Invoke.makeInvokeStatic(Compilation.typePair, "make", new Expression[]{doit, new ReferenceExp(resultDecl)});
        }
        Expression rec = inlineCalls.visitApplyOnly(new ApplyExp((Expression) new ReferenceExp(loopDecl), recArgs), (Type) null);
        lexp.body = collect ? rec : new BeginExp(doit, rec);
        let3.setBody(lexp.body);
        lexp.body = let3;
        Expression[] initArgs = new Expression[(collect ? nargs2 + 1 : nargs2)];
        Expression expression2 = doit;
        QuoteExp empty = new QuoteExp(LList.Empty);
        int i3 = nargs2;
        while (true) {
            i3--;
            if (i3 < 0) {
                break;
            }
            Expression rec2 = rec;
            LetExp let32 = let3;
            Expression[] recArgs2 = recArgs;
            lexp.body = new IfExp(inlineCalls.visitApplyOnly(new ApplyExp((Procedure) mproc.isEq, new ReferenceExp(largs2[i3]), empty), (Type) null), collect ? new ReferenceExp(resultDecl) : QuoteExp.voidExp, lexp.body);
            initArgs[i3] = args2[i3 + 1];
            let3 = let32;
            rec = rec2;
            recArgs = recArgs2;
            procDecl = procDecl;
            resultDecl = resultDecl;
        }
        LetExp letExp = let3;
        Expression[] expressionArr4 = recArgs;
        Declaration declaration = procDecl;
        Declaration declaration2 = resultDecl;
        if (collect) {
            initArgs[nargs2] = empty;
        }
        Expression body = inlineCalls.visitApplyOnly(new ApplyExp((Expression) new ReferenceExp(loopDecl), initArgs), (Type) null);
        if (collect) {
            body = Invoke.makeInvokeStatic(Compilation.scmListType, "reverseInPlace", new Expression[]{body});
        }
        LetExp let23 = let22;
        let23.setBody(body);
        if (procSafeForMultipleEvaluation) {
            return let23;
        }
        return let13;
    }
}
