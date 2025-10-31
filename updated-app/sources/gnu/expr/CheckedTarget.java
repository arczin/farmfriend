package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.Method;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;

public class CheckedTarget extends StackTarget {
    static Method initWrongTypeProcMethod;
    static Method initWrongTypeStringMethod;
    static ClassType typeClassCastException;
    static ClassType typeWrongType;
    int argno;
    LambdaExp proc;
    String procname;

    public CheckedTarget(Type type) {
        super(type);
        this.argno = -4;
    }

    public CheckedTarget(Type type, LambdaExp proc2, int argno2) {
        super(type);
        this.proc = proc2;
        this.procname = proc2.getName();
        this.argno = argno2;
    }

    public CheckedTarget(Type type, String procname2, int argno2) {
        super(type);
        this.procname = procname2;
        this.argno = argno2;
    }

    public static Target getInstance(Type type, String procname2, int argno2) {
        return type == Type.objectType ? Target.pushObject : new CheckedTarget(type, procname2, argno2);
    }

    public static Target getInstance(Type type, LambdaExp proc2, int argno2) {
        return type == Type.objectType ? Target.pushObject : new CheckedTarget(type, proc2, argno2);
    }

    public static Target getInstance(Type type) {
        return type == Type.objectType ? Target.pushObject : new CheckedTarget(type);
    }

    public static Target getInstance(Declaration decl) {
        return getInstance(decl.getType(), decl.getName(), -2);
    }

    private static void initWrongType() {
        if (typeClassCastException == null) {
            typeClassCastException = ClassType.make("java.lang.ClassCastException");
        }
        if (typeWrongType == null) {
            typeWrongType = ClassType.make("gnu.mapping.WrongType");
            initWrongTypeStringMethod = typeWrongType.addMethod("<init>", 1, new Type[]{typeClassCastException, Compilation.javaStringType, Type.intType, Type.objectType}, (Type) Type.voidType);
            initWrongTypeProcMethod = typeWrongType.addMethod("<init>", 1, new Type[]{typeClassCastException, Compilation.typeProcedure, Type.intType, Type.objectType}, (Type) Type.voidType);
        }
    }

    public void compileFromStack(Compilation comp, Type stackType) {
        if (!compileFromStack0(comp, stackType)) {
            emitCheckedCoerce(comp, this.proc, this.procname, this.argno, this.type, (Variable) null);
        }
    }

    public static void emitCheckedCoerce(Compilation comp, String procname2, int argno2, Type type) {
        emitCheckedCoerce(comp, (LambdaExp) null, procname2, argno2, type, (Variable) null);
    }

    public static void emitCheckedCoerce(Compilation comp, LambdaExp proc2, int argno2, Type type) {
        emitCheckedCoerce(comp, proc2, proc2.getName(), argno2, type, (Variable) null);
    }

    public static void emitCheckedCoerce(Compilation comp, LambdaExp proc2, int argno2, Type type, Variable argValue) {
        emitCheckedCoerce(comp, proc2, proc2.getName(), argno2, type, argValue);
    }

    static void emitCheckedCoerce(Compilation comp, LambdaExp proc2, String procname2, int argno2, Type type, Variable argValue) {
        Variable argValue2;
        Scope tmpScope;
        Compilation compilation = comp;
        LambdaExp lambdaExp = proc2;
        int i = argno2;
        Type type2 = type;
        CodeAttr code = comp.getCode();
        boolean isInTry = code.isInTry();
        initWrongType();
        Label startTry = new Label(code);
        if (argValue != null || type2 == Type.toStringType) {
            tmpScope = null;
            argValue2 = argValue;
        } else {
            tmpScope = code.pushScope();
            argValue2 = code.addLocal(Type.objectType);
            code.emitDup(1);
            code.emitStore(argValue2);
        }
        int startPC = code.getPC();
        startTry.define(code);
        emitCoerceFromObject(type2, compilation);
        if (code.getPC() != startPC && type2 != Type.toStringType) {
            Label endTry = new Label(code);
            endTry.define(code);
            Label endLabel = new Label(code);
            endLabel.setTypes(code);
            if (isInTry) {
                code.emitGoto(endLabel);
            }
            int fragment_cookie = 0;
            code.setUnreachable();
            if (!isInTry) {
                fragment_cookie = code.beginFragment(endLabel);
            }
            code.addHandler(startTry, endTry, typeClassCastException);
            boolean thisIsProc = false;
            if (lambdaExp != null && proc2.isClassGenerated() && !compilation.method.getStaticFlag() && compilation.method.getDeclaringClass() == lambdaExp.getCompiledClassType(compilation)) {
                thisIsProc = true;
            }
            int line = comp.getLineNumber();
            if (line > 0) {
                code.putLineNumber(line);
            }
            code.emitNew(typeWrongType);
            code.emitDupX();
            code.emitSwap();
            if (thisIsProc) {
                code.emitPushThis();
            } else {
                code.emitPushString((procname2 != null || i == -4) ? procname2 : "lambda");
            }
            code.emitPushInt(i);
            code.emitLoad(argValue2);
            code.emitInvokeSpecial(thisIsProc ? initWrongTypeProcMethod : initWrongTypeStringMethod);
            if (tmpScope != null) {
                code.popScope();
            }
            code.emitThrow();
            if (isInTry) {
                endLabel.define(code);
            } else {
                code.endFragment(fragment_cookie);
            }
        } else if (tmpScope != null) {
            code.popScope();
        }
    }
}
