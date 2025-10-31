package gnu.kawa.functions;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.Method;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.IgnoreTarget;
import gnu.expr.Inlineable;
import gnu.expr.LambdaExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.math.IntNum;

public class ValuesMap extends MethodProc implements Inlineable {
    public static final ValuesMap valuesMap = new ValuesMap(-1);
    public static final ValuesMap valuesMapWithPos = new ValuesMap(1);
    private final int startCounter;

    private ValuesMap(int startCounter2) {
        this.startCounter = startCounter2;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyValuesMap");
    }

    public int numArgs() {
        return 8194;
    }

    public void apply(CallContext ctx) throws Throwable {
        Procedure proc = (Procedure) ctx.getNextArg();
        Consumer consumer = ctx.consumer;
        Object val = ctx.getNextArg();
        Procedure.checkArgCount(proc, 1);
        if (val instanceof Values) {
            int ipos = 0;
            int count = this.startCounter;
            Values values = (Values) val;
            while (true) {
                int nextPos = values.nextPos(ipos);
                ipos = nextPos;
                if (nextPos != 0) {
                    Object v = values.getPosPrevious(ipos);
                    if (this.startCounter >= 0) {
                        proc.check2(v, IntNum.make(count), ctx);
                        count++;
                    } else {
                        proc.check1(v, ctx);
                    }
                    ctx.runUntilDone();
                } else {
                    return;
                }
            }
        } else {
            if (this.startCounter >= 0) {
                proc.check2(val, IntNum.make(this.startCounter), ctx);
            } else {
                proc.check1(val, ctx);
            }
            ctx.runUntilDone();
        }
    }

    static LambdaExp canInline(ApplyExp exp, ValuesMap proc) {
        Expression[] args = exp.getArgs();
        int i = 2;
        if (args.length != 2) {
            return null;
        }
        Expression expression = args[0];
        Expression arg0 = expression;
        if (!(expression instanceof LambdaExp)) {
            return null;
        }
        LambdaExp lexp = (LambdaExp) arg0;
        if (lexp.min_args != lexp.max_args) {
            return null;
        }
        int i2 = lexp.min_args;
        if (proc.startCounter < 0) {
            i = 1;
        }
        if (i2 == i) {
            return lexp;
        }
        return null;
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        LambdaExp lambda = canInline(exp, this);
        if (lambda == null) {
            ApplyExp.compile(exp, comp, target);
            return;
        }
        Expression[] args = exp.getArgs();
        if ((target instanceof IgnoreTarget) || (target instanceof ConsumerTarget)) {
            compileInlined(lambda, args[1], this.startCounter, (Method) null, comp, target);
            return;
        }
        ConsumerTarget.compileUsingConsumer(exp, comp, target);
    }

    public static void compileInlined(LambdaExp lambda, Expression vals, int startCounter2, Method matchesMethod, Compilation comp, Target target) {
        Declaration counterDecl;
        Variable counter;
        int i = startCounter2;
        Method method = matchesMethod;
        Compilation compilation = comp;
        Declaration param = lambda.firstDecl();
        CodeAttr code = comp.getCode();
        Scope scope = code.pushScope();
        Type paramType = param.getType();
        if (i >= 0) {
            counter = scope.addVariable(code, Type.intType, "position");
            code.emitPushInt(i);
            code.emitStore(counter);
            counterDecl = new Declaration(counter);
        } else {
            counter = null;
            counterDecl = null;
        }
        if (!param.isSimple() || method != null) {
            param = new Declaration(code.addLocal(paramType.getImplementationType(), Compilation.mangleNameIfNeeded(param.getName())));
        } else {
            param.allocateVariable(code);
        }
        Expression[] args = i >= 0 ? new Expression[]{new ReferenceExp(param), new ReferenceExp(counterDecl)} : new Expression[]{new ReferenceExp(param)};
        Expression app = new ApplyExp((Expression) lambda, args);
        if (method != null) {
            if (app.getType().getImplementationType() != Type.booleanType) {
                app = new ApplyExp(method, app, new ReferenceExp(counterDecl));
            }
            app = new IfExp(app, new ReferenceExp(param), QuoteExp.voidExp);
        }
        Variable indexVar = code.addLocal(Type.intType);
        Variable valuesVar = code.addLocal(Type.pointer_type);
        Variable nextVar = code.addLocal(Type.intType);
        vals.compileWithPosition(compilation, Target.pushObject);
        code.emitStore(valuesVar);
        code.emitPushInt(0);
        code.emitStore(indexVar);
        Label top = new Label(code);
        Label doneLabel = new Label(code);
        top.define(code);
        code.emitLoad(valuesVar);
        code.emitLoad(indexVar);
        Scope scope2 = scope;
        Declaration declaration = counterDecl;
        Expression[] expressionArr = args;
        code.emitInvokeStatic(Compilation.typeValues.getDeclaredMethod("nextIndex", 2));
        code.emitDup((Type) Type.intType);
        code.emitStore(nextVar);
        code.emitGotoIfIntLtZero(doneLabel);
        code.emitLoad(valuesVar);
        code.emitLoad(indexVar);
        code.emitInvokeStatic(Compilation.typeValues.getDeclaredMethod("nextValue", 2));
        StackTarget.convert(compilation, Type.objectType, paramType);
        param.compileStore(compilation);
        app.compile(compilation, target);
        if (i >= 0) {
            code.emitInc(counter, 1);
        }
        code.emitLoad(nextVar);
        code.emitStore(indexVar);
        code.emitGoto(top);
        doneLabel.define(code);
        code.popScope();
    }

    public Type getReturnType(Expression[] args) {
        return Type.pointer_type;
    }
}
