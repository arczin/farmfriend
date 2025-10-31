package gnu.kawa.functions;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConditionalTarget;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.math.IntNum;
import gnu.math.RatNum;

public class NumberCompare extends ProcedureN implements Inlineable {
    static final int RESULT_EQU = 0;
    static final int RESULT_GRT = 1;
    static final int RESULT_LSS = -1;
    static final int RESULT_NAN = -2;
    static final int RESULT_NEQ = -3;
    public static final int TRUE_IF_EQU = 8;
    public static final int TRUE_IF_GRT = 16;
    public static final int TRUE_IF_LSS = 4;
    public static final int TRUE_IF_NAN = 2;
    public static final int TRUE_IF_NEQ = 1;
    int flags;
    Language language;

    public int numArgs() {
        return -4094;
    }

    public static boolean $Eq(Object arg1, Object arg2) {
        return apply2(8, arg1, arg2);
    }

    public static boolean $Gr(Object arg1, Object arg2) {
        return apply2(16, arg1, arg2);
    }

    public static boolean $Gr$Eq(Object arg1, Object arg2) {
        return apply2(24, arg1, arg2);
    }

    public static boolean $Ls(Object arg1, Object arg2) {
        return apply2(4, arg1, arg2);
    }

    public static boolean $Ls$Eq(Object arg1, Object arg2) {
        return apply2(12, arg1, arg2);
    }

    public static boolean $Eq$V(Object arg1, Object arg2, Object arg3, Object[] rest) {
        if (!$Eq(arg1, arg2) || !$Eq(arg2, arg3)) {
            return false;
        }
        return rest.length == 0 || ($Eq(arg3, rest[0]) && applyN(8, rest));
    }

    public static boolean $Gr$V(Object arg1, Object arg2, Object arg3, Object[] rest) {
        if (!$Gr(arg1, arg2) || !$Gr(arg2, arg3)) {
            return false;
        }
        return rest.length == 0 || ($Gr(arg3, rest[0]) && applyN(16, rest));
    }

    public static boolean $Gr$Eq$V(Object arg1, Object arg2, Object arg3, Object[] rest) {
        if (!$Gr$Eq(arg1, arg2) || !$Gr$Eq(arg2, arg3)) {
            return false;
        }
        return rest.length == 0 || ($Gr$Eq(arg3, rest[0]) && applyN(24, rest));
    }

    public static boolean $Ls$V(Object arg1, Object arg2, Object arg3, Object[] rest) {
        if (!$Ls(arg1, arg2) || !$Ls(arg2, arg3)) {
            return false;
        }
        return rest.length == 0 || ($Ls(arg3, rest[0]) && applyN(4, rest));
    }

    public static boolean $Ls$Eq$V(Object arg1, Object arg2, Object arg3, Object[] rest) {
        if (!$Ls$Eq(arg1, arg2) || !$Ls$Eq(arg2, arg3)) {
            return false;
        }
        return rest.length == 0 || ($Ls$Eq(arg3, rest[0]) && applyN(12, rest));
    }

    public static NumberCompare make(Language language2, String name, int flags2) {
        NumberCompare proc = new NumberCompare();
        proc.language = language2;
        proc.setName(name);
        proc.flags = flags2;
        proc.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyNumberCompare");
        return proc;
    }

    /* access modifiers changed from: protected */
    public final Language getLanguage() {
        return this.language;
    }

    public Object apply2(Object arg1, Object arg2) {
        return getLanguage().booleanObject(apply2(this.flags, arg1, arg2));
    }

    public static boolean apply2(int flags2, Object arg1, Object arg2) {
        return ((1 << (compare(arg1, arg2, true) + 3)) & flags2) != 0;
    }

    public static boolean checkCompareCode(int code, int flags2) {
        return ((1 << (code + 3)) & flags2) != 0;
    }

    public static boolean applyWithPromotion(int flags2, Object arg1, Object arg2) {
        return checkCompareCode(compare(arg1, arg2, false), flags2);
    }

    public static int compare(Object arg1, Object arg2, boolean exact) {
        return compare(arg1, Arithmetic.classifyValue(arg1), arg2, Arithmetic.classifyValue(arg2), exact);
    }

    public static int compare(Object obj, int i, Object obj2, int i2, boolean z) {
        if (i < 0 || i2 < 0) {
            return -3;
        }
        int i3 = 0;
        switch (i < i2 ? i2 : i) {
            case 1:
                int asInt = Arithmetic.asInt(obj);
                int asInt2 = Arithmetic.asInt(obj2);
                if (asInt < asInt2) {
                    i3 = -1;
                } else if (asInt > asInt2) {
                    i3 = 1;
                }
                return i3;
            case 2:
                long asLong = Arithmetic.asLong(obj);
                long asLong2 = Arithmetic.asLong(obj2);
                if (asLong < asLong2) {
                    i3 = -1;
                } else if (asLong > asLong2) {
                    i3 = 1;
                }
                return i3;
            case 3:
                return Arithmetic.asBigInteger(obj).compareTo(Arithmetic.asBigInteger(obj2));
            case 4:
                return IntNum.compare(Arithmetic.asIntNum(obj), Arithmetic.asIntNum(obj2));
            case 5:
                return Arithmetic.asBigDecimal(obj).compareTo(Arithmetic.asBigDecimal(obj2));
            case 6:
                return RatNum.compare(Arithmetic.asRatNum(obj), Arithmetic.asRatNum(obj2));
            case 7:
                if (!z || (i > 6 && i2 > 6)) {
                    float asFloat = Arithmetic.asFloat(obj);
                    float asFloat2 = Arithmetic.asFloat(obj2);
                    if (asFloat > asFloat2) {
                        return 1;
                    }
                    if (asFloat < asFloat2) {
                        return -1;
                    }
                    if (asFloat == asFloat2) {
                        return 0;
                    }
                    return -2;
                }
            case 8:
            case 9:
                break;
        }
        if (!z || (i > 6 && i2 > 6)) {
            double asDouble = Arithmetic.asDouble(obj);
            double asDouble2 = Arithmetic.asDouble(obj2);
            if (asDouble > asDouble2) {
                return 1;
            }
            if (asDouble < asDouble2) {
                return -1;
            }
            if (asDouble == asDouble2) {
                return 0;
            }
            return -2;
        }
        return Arithmetic.asNumeric(obj).compare(Arithmetic.asNumeric(obj2));
    }

    static boolean applyN(int flags2, Object[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if (!apply2(flags2, args[i], args[i + 1])) {
                return false;
            }
        }
        return true;
    }

    public Object applyN(Object[] args) {
        return getLanguage().booleanObject(applyN(this.flags, args));
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        int i;
        Type commonType;
        int opcode;
        Compilation compilation = comp;
        Target target2 = target;
        Expression[] args = exp.getArgs();
        if (args.length == 2) {
            Expression arg0 = args[0];
            Expression arg1 = args[1];
            int kind0 = classify(arg0);
            int kind1 = classify(arg1);
            CodeAttr code = comp.getCode();
            if (kind0 <= 0 || kind1 <= 0 || kind0 > 10 || kind1 > 10) {
                ApplyExp applyExp = exp;
            } else if (kind0 == 6 && kind1 == 6) {
                ApplyExp applyExp2 = exp;
            } else if (!(target2 instanceof ConditionalTarget)) {
                IfExp.compile(exp, QuoteExp.trueExp, QuoteExp.falseExp, compilation, target2);
                return;
            } else {
                ApplyExp applyExp3 = exp;
                int mask = this.flags;
                if (mask == 1) {
                    mask = 20;
                }
                if (kind0 > 4 || kind1 > 4) {
                    i = 1;
                } else if (kind0 > 2 || kind1 > 2) {
                    Type[] ctypes = new Type[2];
                    ctypes[0] = Arithmetic.typeIntNum;
                    if (kind1 <= 2) {
                        ctypes[1] = Type.longType;
                    } else if (kind0 > 2 || (!(arg0 instanceof QuoteExp) && !(arg1 instanceof QuoteExp) && !(arg0 instanceof ReferenceExp) && !(arg1 instanceof ReferenceExp))) {
                        ctypes[1] = Arithmetic.typeIntNum;
                    } else {
                        ctypes[1] = Type.longType;
                        args = new Expression[]{arg1, arg0};
                        if (!(mask == 8 || mask == 20)) {
                            mask ^= 20;
                        }
                    }
                    arg0 = new ApplyExp((Procedure) new PrimProcedure(Arithmetic.typeIntNum.getMethod("compare", ctypes)), args);
                    arg1 = new QuoteExp(IntNum.zero());
                    i = 1;
                    kind1 = 1;
                    kind0 = 1;
                } else {
                    i = 1;
                }
                if (kind0 <= i && kind1 <= i) {
                    commonType = Type.intType;
                } else if (kind0 > 2 || kind1 > 2) {
                    commonType = Type.doubleType;
                } else {
                    commonType = Type.longType;
                }
                StackTarget subTarget = new StackTarget(commonType);
                ConditionalTarget ctarget = (ConditionalTarget) target2;
                if ((arg0 instanceof QuoteExp) && !(arg1 instanceof QuoteExp)) {
                    Expression tmp = arg1;
                    arg1 = arg0;
                    arg0 = tmp;
                    if (!(mask == 8 || mask == 20)) {
                        mask ^= 20;
                    }
                }
                Label label1 = ctarget.trueBranchComesFirst ? ctarget.ifFalse : ctarget.ifTrue;
                if (ctarget.trueBranchComesFirst) {
                    mask ^= 28;
                }
                switch (mask) {
                    case 4:
                        opcode = 155;
                        break;
                    case 8:
                        opcode = 153;
                        break;
                    case 12:
                        opcode = 158;
                        break;
                    case 16:
                        opcode = 157;
                        break;
                    case 20:
                        opcode = 154;
                        break;
                    case 24:
                        opcode = 156;
                        break;
                    default:
                        opcode = 0;
                        break;
                }
                arg0.compile(compilation, (Target) subTarget);
                if (kind0 <= 1 && kind1 <= 1 && (arg1 instanceof QuoteExp)) {
                    Object value = ((QuoteExp) arg1).getValue();
                    Object value2 = value;
                    if ((value instanceof IntNum) && ((IntNum) value2).isZero()) {
                        code.emitGotoIfCompare1(label1, opcode);
                        ctarget.emitGotoFirstBranch(code);
                        return;
                    }
                }
                arg1.compile(compilation, (Target) subTarget);
                code.emitGotoIfCompare2(label1, opcode);
                ctarget.emitGotoFirstBranch(code);
                return;
            }
        } else {
            ApplyExp applyExp4 = exp;
        }
        ApplyExp.compile(exp, comp, target);
    }

    static int classify(Expression exp) {
        int kind = Arithmetic.classifyType(exp.getType());
        if (kind == 4 && (exp instanceof QuoteExp)) {
            Object value = ((QuoteExp) exp).getValue();
            Object value2 = value;
            if (value instanceof IntNum) {
                int ilength = ((IntNum) value2).intLength();
                if (ilength < 32) {
                    return 1;
                }
                if (ilength < 64) {
                    return 2;
                }
            }
        }
        return kind;
    }

    public Type getReturnType(Expression[] args) {
        return Type.booleanType;
    }
}
