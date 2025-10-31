package gnu.expr;

import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.kawa.functions.AddOp;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Location;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.math.IntNum;

public class SetExp extends AccessExp {
    public static final int BAD_SHORT = 65536;
    public static final int DEFINING_FLAG = 2;
    public static final int GLOBAL_FLAG = 4;
    public static final int HAS_VALUE = 64;
    public static final int PREFER_BINDING2 = 8;
    public static final int PROCEDURE = 16;
    public static final int SET_IF_UNBOUND = 32;
    Expression new_value;

    public SetExp(Object symbol, Expression val) {
        this.symbol = symbol;
        this.new_value = val;
    }

    public SetExp(Declaration decl, Expression val) {
        this.binding = decl;
        this.symbol = decl.getSymbol();
        this.new_value = val;
    }

    public static SetExp makeDefinition(Object symbol, Expression val) {
        SetExp sexp = new SetExp(symbol, val);
        sexp.setDefining(true);
        return sexp;
    }

    public static SetExp makeDefinition(Declaration decl, Expression val) {
        SetExp sexp = new SetExp(decl, val);
        sexp.setDefining(true);
        return sexp;
    }

    public final Expression getNewValue() {
        return this.new_value;
    }

    public final boolean isDefining() {
        return (this.flags & 2) != 0;
    }

    public final void setDefining(boolean value) {
        int i = this.flags;
        this.flags = value ? i | 2 : i & -3;
    }

    public final boolean getHasValue() {
        return (this.flags & 64) != 0;
    }

    public final void setHasValue(boolean value) {
        int i = this.flags;
        this.flags = value ? i | 64 : i & -65;
    }

    public final boolean isFuncDef() {
        return (this.flags & 16) != 0;
    }

    public final void setFuncDef(boolean value) {
        int i = this.flags;
        this.flags = value ? i | 16 : i & -17;
    }

    public final boolean isSetIfUnbound() {
        return (this.flags & 32) != 0;
    }

    public final void setSetIfUnbound(boolean value) {
        int i = this.flags;
        this.flags = value ? i | 32 : i & -33;
    }

    /* access modifiers changed from: protected */
    public boolean mustCompile() {
        return false;
    }

    public void apply(CallContext ctx) throws Throwable {
        Environment env = Environment.getCurrent();
        Symbol sym = this.symbol instanceof Symbol ? (Symbol) this.symbol : env.getSymbol(this.symbol.toString());
        Object property = null;
        Language language = Language.getDefaultLanguage();
        if (isFuncDef() && language.hasSeparateFunctionNamespace()) {
            property = EnvironmentKey.FUNCTION;
        }
        if (isSetIfUnbound()) {
            Location loc = env.getLocation(sym, property);
            if (!loc.isBound()) {
                loc.set(this.new_value.eval(env));
            }
            if (getHasValue()) {
                ctx.writeValue(loc);
                return;
            }
            return;
        }
        Object new_val = this.new_value.eval(env);
        if (this.binding != null && !(this.binding.context instanceof ModuleExp)) {
            Object[] evalFrame = ctx.evalFrames[ScopeExp.nesting(this.binding.context)];
            if (this.binding.isIndirectBinding()) {
                if (isDefining()) {
                    evalFrame[this.binding.evalIndex] = Location.make(sym);
                }
                ((Location) evalFrame[this.binding.evalIndex]).set(this.new_value);
            } else {
                evalFrame[this.binding.evalIndex] = new_val;
            }
        } else if (isDefining()) {
            env.define(sym, property, new_val);
        } else {
            env.put(sym, property, new_val);
        }
        if (getHasValue()) {
            ctx.writeValue(new_val);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: gnu.expr.SetExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v3, resolved type: gnu.expr.SetExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: gnu.expr.SetExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v24, resolved type: gnu.expr.ReferenceExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v5, resolved type: gnu.expr.SetExp} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void compile(gnu.expr.Compilation r13, gnu.expr.Target r14) {
        /*
            r12 = this;
            gnu.expr.Expression r0 = r12.new_value
            boolean r0 = r0 instanceof gnu.expr.LambdaExp
            if (r0 == 0) goto L_0x0015
            boolean r0 = r14 instanceof gnu.expr.IgnoreTarget
            if (r0 == 0) goto L_0x0015
            gnu.expr.Expression r0 = r12.new_value
            gnu.expr.LambdaExp r0 = (gnu.expr.LambdaExp) r0
            boolean r0 = r0.getInlineOnly()
            if (r0 == 0) goto L_0x0015
            return
        L_0x0015:
            gnu.bytecode.CodeAttr r0 = r13.getCode()
            boolean r1 = r12.getHasValue()
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L_0x0027
            boolean r1 = r14 instanceof gnu.expr.IgnoreTarget
            if (r1 != 0) goto L_0x0027
            r1 = 1
            goto L_0x0028
        L_0x0027:
            r1 = 0
        L_0x0028:
            r4 = 0
            gnu.expr.Declaration r5 = r12.binding
            gnu.expr.Expression r6 = r5.getValue()
            boolean r7 = r6 instanceof gnu.expr.LambdaExp
            if (r7 == 0) goto L_0x0055
            gnu.expr.ScopeExp r7 = r5.context
            boolean r7 = r7 instanceof gnu.expr.ModuleExp
            if (r7 == 0) goto L_0x0055
            boolean r7 = r5.ignorable()
            if (r7 != 0) goto L_0x0055
            r7 = r6
            gnu.expr.LambdaExp r7 = (gnu.expr.LambdaExp) r7
            java.lang.String r7 = r7.getName()
            if (r7 == 0) goto L_0x0055
            gnu.expr.Expression r7 = r12.new_value
            if (r6 != r7) goto L_0x0055
            gnu.expr.Expression r2 = r12.new_value
            gnu.expr.LambdaExp r2 = (gnu.expr.LambdaExp) r2
            r2.compileSetField(r13)
            goto L_0x020f
        L_0x0055:
            boolean r7 = r5.shouldEarlyInit()
            if (r7 != 0) goto L_0x0061
            boolean r7 = r5.isAlias()
            if (r7 == 0) goto L_0x0088
        L_0x0061:
            gnu.expr.ScopeExp r7 = r5.context
            boolean r7 = r7 instanceof gnu.expr.ModuleExp
            if (r7 == 0) goto L_0x0088
            boolean r7 = r12.isDefining()
            if (r7 == 0) goto L_0x0088
            boolean r7 = r5.ignorable()
            if (r7 != 0) goto L_0x0088
            boolean r3 = r5.shouldEarlyInit()
            if (r3 == 0) goto L_0x007e
            gnu.expr.Expression r3 = r12.new_value
            gnu.expr.BindingInitializer.create(r5, r3, r13)
        L_0x007e:
            if (r1 == 0) goto L_0x020f
            gnu.expr.Target r3 = gnu.expr.Target.pushObject
            r5.load(r12, r2, r13, r3)
            r4 = 1
            goto L_0x020f
        L_0x0088:
            r7 = r12
            gnu.expr.Declaration r8 = r12.contextDecl()
            boolean r9 = r12.isDefining()
            if (r9 != 0) goto L_0x00bc
        L_0x0093:
            if (r5 == 0) goto L_0x00bc
            boolean r9 = r5.isAlias()
            if (r9 == 0) goto L_0x00bc
            gnu.expr.Expression r6 = r5.getValue()
            boolean r9 = r6 instanceof gnu.expr.ReferenceExp
            if (r9 != 0) goto L_0x00a4
            goto L_0x00bc
        L_0x00a4:
            r9 = r6
            gnu.expr.ReferenceExp r9 = (gnu.expr.ReferenceExp) r9
            gnu.expr.Declaration r10 = r9.binding
            if (r10 != 0) goto L_0x00ac
            goto L_0x00bc
        L_0x00ac:
            if (r8 == 0) goto L_0x00b5
            boolean r11 = r10.needsContext()
            if (r11 == 0) goto L_0x00b5
            goto L_0x00bc
        L_0x00b5:
            gnu.expr.Declaration r8 = r9.contextDecl()
            r7 = r9
            r5 = r10
            goto L_0x0093
        L_0x00bc:
            boolean r9 = r5.ignorable()
            if (r9 == 0) goto L_0x00cb
            gnu.expr.Expression r2 = r12.new_value
            gnu.expr.Target r3 = gnu.expr.Target.Ignore
            r2.compile((gnu.expr.Compilation) r13, (gnu.expr.Target) r3)
            goto L_0x020f
        L_0x00cb:
            boolean r9 = r5.isAlias()
            r10 = 2
            if (r9 == 0) goto L_0x00f8
            boolean r9 = r12.isDefining()
            if (r9 == 0) goto L_0x00f8
            gnu.expr.Target r2 = gnu.expr.Target.pushObject
            r5.load(r12, r10, r13, r2)
            java.lang.String r2 = "gnu.mapping.IndirectableLocation"
            gnu.bytecode.ClassType r2 = gnu.bytecode.ClassType.make(r2)
            r0.emitCheckcast(r2)
            gnu.expr.Expression r9 = r12.new_value
            gnu.expr.Target r10 = gnu.expr.Target.pushObject
            r9.compile((gnu.expr.Compilation) r13, (gnu.expr.Target) r10)
            java.lang.String r9 = "setAlias"
            gnu.bytecode.Method r3 = r2.getDeclaredMethod((java.lang.String) r9, (int) r3)
            r0.emitInvokeVirtual(r3)
            goto L_0x020f
        L_0x00f8:
            boolean r9 = r5.isIndirectBinding()
            if (r9 == 0) goto L_0x015c
            gnu.expr.Target r9 = gnu.expr.Target.pushObject
            r5.load(r7, r10, r13, r9)
            boolean r9 = r12.isSetIfUnbound()
            if (r9 == 0) goto L_0x012f
            if (r1 == 0) goto L_0x010f
            r0.emitDup()
            r4 = 1
        L_0x010f:
            r0.pushScope()
            r0.emitDup()
            gnu.bytecode.ClassType r9 = gnu.expr.Compilation.typeLocation
            gnu.bytecode.Variable r9 = r0.addLocal(r9)
            r0.emitStore(r9)
            gnu.bytecode.ClassType r10 = gnu.expr.Compilation.typeLocation
            java.lang.String r11 = "isBound"
            gnu.bytecode.Method r2 = r10.getDeclaredMethod((java.lang.String) r11, (int) r2)
            r0.emitInvokeVirtual(r2)
            r0.emitIfIntEqZero()
            r0.emitLoad(r9)
        L_0x012f:
            gnu.expr.Expression r2 = r12.new_value
            gnu.expr.Target r9 = gnu.expr.Target.pushObject
            r2.compile((gnu.expr.Compilation) r13, (gnu.expr.Target) r9)
            if (r1 == 0) goto L_0x0143
            boolean r2 = r12.isSetIfUnbound()
            if (r2 != 0) goto L_0x0143
            r0.emitDupX()
            r2 = 1
            r4 = r2
        L_0x0143:
            java.lang.String r2 = "set"
            gnu.bytecode.ClassType r9 = gnu.expr.Compilation.typeLocation
            gnu.bytecode.Method r3 = r9.getDeclaredMethod((java.lang.String) r2, (int) r3)
            r0.emitInvokeVirtual(r3)
            boolean r3 = r12.isSetIfUnbound()
            if (r3 == 0) goto L_0x015a
            r0.emitFi()
            r0.popScope()
        L_0x015a:
            goto L_0x020f
        L_0x015c:
            boolean r2 = r5.isSimple()
            if (r2 == 0) goto L_0x0199
            gnu.bytecode.Type r2 = r5.getType()
            gnu.bytecode.Variable r3 = r5.getVariable()
            if (r3 != 0) goto L_0x0170
            gnu.bytecode.Variable r3 = r5.allocateVariable(r0)
        L_0x0170:
            gnu.expr.Expression r9 = r12.new_value
            int r9 = canUseInc(r9, r5)
            r10 = 65536(0x10000, float:9.18355E-41)
            if (r9 == r10) goto L_0x0189
            gnu.bytecode.CodeAttr r10 = r13.getCode()
            short r11 = (short) r9
            r10.emitInc(r3, r11)
            if (r1 == 0) goto L_0x0197
            r0.emitLoad(r3)
            r4 = 1
            goto L_0x0197
        L_0x0189:
            gnu.expr.Expression r10 = r12.new_value
            r10.compile((gnu.expr.Compilation) r13, (gnu.expr.Declaration) r5)
            if (r1 == 0) goto L_0x0194
            r0.emitDup((gnu.bytecode.Type) r2)
            r4 = 1
        L_0x0194:
            r0.emitStore(r3)
        L_0x0197:
            goto L_0x020f
        L_0x0199:
            gnu.expr.ScopeExp r2 = r5.context
            boolean r2 = r2 instanceof gnu.expr.ClassExp
            if (r2 == 0) goto L_0x01db
            gnu.bytecode.Field r2 = r5.field
            if (r2 != 0) goto L_0x01db
            r2 = 16
            boolean r2 = r12.getFlag(r2)
            if (r2 != 0) goto L_0x01db
            gnu.expr.ScopeExp r2 = r5.context
            gnu.expr.ClassExp r2 = (gnu.expr.ClassExp) r2
            boolean r2 = r2.isMakingClassPair()
            if (r2 == 0) goto L_0x01db
            java.lang.String r2 = "set"
            java.lang.String r9 = r5.getName()
            java.lang.String r2 = gnu.expr.ClassExp.slotToMethodName(r2, r9)
            gnu.expr.ScopeExp r9 = r5.context
            gnu.expr.ClassExp r9 = (gnu.expr.ClassExp) r9
            gnu.bytecode.ClassType r10 = r9.type
            gnu.bytecode.Method r3 = r10.getDeclaredMethod((java.lang.String) r2, (int) r3)
            r9.loadHeapFrame(r13)
            gnu.expr.Expression r10 = r12.new_value
            r10.compile((gnu.expr.Compilation) r13, (gnu.expr.Declaration) r5)
            if (r1 == 0) goto L_0x01d7
            r0.emitDupX()
            r4 = 1
        L_0x01d7:
            r0.emitInvoke(r3)
            goto L_0x020f
        L_0x01db:
            gnu.bytecode.Field r2 = r5.field
            boolean r3 = r2.getStaticFlag()
            if (r3 != 0) goto L_0x01e6
            r5.loadOwningObject(r8, r13)
        L_0x01e6:
            gnu.bytecode.Type r3 = r2.getType()
            gnu.expr.Expression r9 = r12.new_value
            r9.compile((gnu.expr.Compilation) r13, (gnu.expr.Declaration) r5)
            gnu.bytecode.ClassType r9 = r2.getDeclaringClass()
            r13.usedClass(r9)
            boolean r9 = r2.getStaticFlag()
            if (r9 == 0) goto L_0x0206
            if (r1 == 0) goto L_0x0202
            r0.emitDup((gnu.bytecode.Type) r3)
            r4 = 1
        L_0x0202:
            r0.emitPutStatic(r2)
            goto L_0x020f
        L_0x0206:
            if (r1 == 0) goto L_0x020c
            r0.emitDupX()
            r4 = 1
        L_0x020c:
            r0.emitPutField(r2)
        L_0x020f:
            if (r1 == 0) goto L_0x021c
            if (r4 == 0) goto L_0x0214
            goto L_0x021c
        L_0x0214:
            java.lang.Error r2 = new java.lang.Error
            java.lang.String r3 = "SetExp.compile: not implemented - return value"
            r2.<init>(r3)
            throw r2
        L_0x021c:
            if (r1 == 0) goto L_0x0226
            gnu.bytecode.Type r2 = r12.getType()
            r14.compileFromStack(r13, r2)
            goto L_0x022b
        L_0x0226:
            gnu.mapping.Values r2 = gnu.mapping.Values.empty
            r13.compileConstant(r2, r14)
        L_0x022b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.SetExp.compile(gnu.expr.Compilation, gnu.expr.Target):void");
    }

    public static int canUseInc(Expression rhs, Declaration target) {
        int sign;
        Variable var = target.getVariable();
        if (!target.isSimple() || var.getType().getImplementationType().promote() != Type.intType || !(rhs instanceof ApplyExp)) {
            return 65536;
        }
        ApplyExp applyExp = (ApplyExp) rhs;
        ApplyExp aexp = applyExp;
        if (applyExp.getArgCount() != 2) {
            return 65536;
        }
        Object func = aexp.getFunction().valueIfConstant();
        if (func == AddOp.$Pl) {
            sign = 1;
        } else if (func != AddOp.$Mn) {
            return 65536;
        } else {
            sign = -1;
        }
        Expression arg0 = aexp.getArg(0);
        Expression arg1 = aexp.getArg(1);
        if ((arg0 instanceof QuoteExp) && sign > 0) {
            Expression tmp = arg1;
            arg1 = arg0;
            arg0 = tmp;
        }
        if (!(arg0 instanceof ReferenceExp)) {
            return 65536;
        }
        ReferenceExp ref0 = (ReferenceExp) arg0;
        if (ref0.getBinding() != target || ref0.getDontDereference()) {
            return 65536;
        }
        Object value1 = arg1.valueIfConstant();
        if (value1 instanceof Integer) {
            int val1 = ((Integer) value1).intValue();
            if (sign < 0) {
                val1 = -val1;
            }
            if (((short) val1) == val1) {
                return val1;
            }
            return 65536;
        } else if ((value1 instanceof IntNum) == 0) {
            return 65536;
        } else {
            IntNum int1 = (IntNum) value1;
            int hi = 32767;
            int lo = -32767;
            if (sign > 0) {
                lo--;
            } else {
                hi = 32767 + 1;
            }
            if (IntNum.compare(int1, (long) lo) < 0 || IntNum.compare(int1, (long) hi) > 0) {
                return 65536;
            }
            return int1.intValue() * sign;
        }
    }

    public final Type getType() {
        if (!getHasValue()) {
            return Type.voidType;
        }
        return this.binding == null ? Type.pointer_type : this.binding.getType();
    }

    /* access modifiers changed from: protected */
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitSetExp(this, d);
    }

    /* access modifiers changed from: protected */
    public <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        this.new_value = visitor.visitAndUpdate(this.new_value, d);
    }

    public void print(OutPort out) {
        out.startLogicalBlock(isDefining() ? "(Define" : "(Set", ")", 2);
        out.writeSpaceFill();
        printLineColumn(out);
        if (this.binding == null || this.symbol.toString() != this.binding.getName()) {
            out.print('/');
            out.print(this.symbol);
        }
        if (this.binding != null) {
            out.print('/');
            out.print((Object) this.binding);
        }
        out.writeSpaceLinear();
        this.new_value.print(out);
        out.endLogicalBlock(")");
    }

    public String toString() {
        return "SetExp[" + this.symbol + ":=" + this.new_value + ']';
    }
}
