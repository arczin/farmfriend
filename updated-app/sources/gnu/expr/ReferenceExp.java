package gnu.expr;

import gnu.bytecode.Type;
import gnu.kawa.util.IdentityHashTable;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Location;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.mapping.UnboundLocationException;
import gnu.text.SourceLocator;

public class ReferenceExp extends AccessExp {
    public static final int DONT_DEREFERENCE = 2;
    public static final int PREFER_BINDING2 = 8;
    public static final int PROCEDURE_NAME = 4;
    public static final int TYPE_NAME = 16;
    static int counter;
    int id;

    public final boolean getDontDereference() {
        return (this.flags & 2) != 0;
    }

    public final void setDontDereference(boolean setting) {
        setFlag(setting, 2);
    }

    public final boolean isUnknown() {
        return Declaration.isUnknown(this.binding);
    }

    public final boolean isProcedureName() {
        return (this.flags & 4) != 0;
    }

    public final void setProcedureName(boolean setting) {
        setFlag(setting, 4);
    }

    public ReferenceExp(Object symbol) {
        int i = counter + 1;
        counter = i;
        this.id = i;
        this.symbol = symbol;
    }

    public ReferenceExp(Object symbol, Declaration binding) {
        int i = counter + 1;
        counter = i;
        this.id = i;
        this.symbol = symbol;
        this.binding = binding;
    }

    public ReferenceExp(Declaration binding) {
        this(binding.getSymbol(), binding);
    }

    /* access modifiers changed from: protected */
    public boolean mustCompile() {
        return false;
    }

    public final Object valueIfConstant() {
        Expression dvalue;
        if (this.binding == null || (dvalue = this.binding.getValue()) == null) {
            return null;
        }
        return dvalue.valueIfConstant();
    }

    public void apply(CallContext callContext) throws Throwable {
        Object obj;
        Object obj2;
        if (this.binding == null || !this.binding.isAlias() || getDontDereference() || !(this.binding.value instanceof ReferenceExp)) {
            Object obj3 = null;
            if (this.binding != null && this.binding.field != null && this.binding.field.getDeclaringClass().isExisting() && (!getDontDereference() || this.binding.isIndirectBinding())) {
                try {
                    if (!this.binding.field.getStaticFlag()) {
                        obj3 = contextDecl().getValue().eval(callContext);
                    }
                    obj = this.binding.field.getReflectField().get(obj3);
                } catch (Exception e) {
                    throw new UnboundLocationException((Object) "exception evaluating " + this.symbol + " from " + this.binding.field + " - " + e, (SourceLocator) this);
                }
            } else if (this.binding != null && (((this.binding.value instanceof QuoteExp) || (this.binding.value instanceof LambdaExp)) && this.binding.value != QuoteExp.undefined_exp && (!getDontDereference() || this.binding.isIndirectBinding()))) {
                obj = this.binding.value.eval(callContext);
            } else if (this.binding == null || ((this.binding.context instanceof ModuleExp) && !this.binding.isPrivate())) {
                Environment current = Environment.getCurrent();
                Symbol symbol = this.symbol instanceof Symbol ? (Symbol) this.symbol : current.getSymbol(this.symbol.toString());
                if (getFlag(8) && isProcedureName()) {
                    obj3 = EnvironmentKey.FUNCTION;
                }
                if (getDontDereference()) {
                    obj2 = current.getLocation(symbol, obj3);
                } else {
                    String str = Location.UNBOUND;
                    obj2 = current.get(symbol, obj3, str);
                    if (obj2 == str) {
                        throw new UnboundLocationException((Object) symbol, (SourceLocator) this);
                    }
                }
                callContext.writeValue(obj2);
                return;
            } else {
                obj = callContext.evalFrames[ScopeExp.nesting(this.binding.context)][this.binding.evalIndex];
            }
        } else {
            ReferenceExp referenceExp = (ReferenceExp) this.binding.value;
            if (referenceExp.getDontDereference() && referenceExp.binding != null) {
                Expression value = referenceExp.binding.getValue();
                if ((value instanceof QuoteExp) || (value instanceof ReferenceExp) || (value instanceof LambdaExp)) {
                    value.apply(callContext);
                    return;
                }
            }
            obj = this.binding.value.eval(callContext);
        }
        if (!getDontDereference() && this.binding.isIndirectBinding()) {
            obj = ((Location) obj).get();
        }
        callContext.writeValue(obj);
    }

    public void compile(Compilation comp, Target target) {
        if (!(target instanceof ConsumerTarget) || !((ConsumerTarget) target).compileWrite(this, comp)) {
            this.binding.load(this, this.flags, comp, target);
        }
    }

    /* access modifiers changed from: protected */
    public Expression deepCopy(IdentityHashTable mapper) {
        ReferenceExp copy = new ReferenceExp(mapper.get(this.symbol, this.symbol), (Declaration) mapper.get(this.binding, this.binding));
        copy.flags = getFlags();
        return copy;
    }

    /* access modifiers changed from: protected */
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitReferenceExp(this, d);
    }

    public Expression validateApply(ApplyExp exp, InlineCalls visitor, Type required, Declaration decl) {
        Expression dval;
        Declaration decl2 = this.binding;
        if (decl2 != null && !decl2.getFlag(65536)) {
            Declaration decl3 = Declaration.followAliases(decl2);
            if (!decl3.isIndirectBinding() && (dval = decl3.getValue()) != null) {
                return dval.validateApply(exp, visitor, required, decl3);
            }
        } else if (getSymbol() instanceof Symbol) {
            Object fval = Environment.getCurrent().getFunction((Symbol) getSymbol(), (Object) null);
            if (fval instanceof Procedure) {
                return new QuoteExp(fval).validateApply(exp, visitor, required, (Declaration) null);
            }
        }
        exp.visitArgs(visitor);
        return exp;
    }

    public void print(OutPort ps) {
        ps.print("(Ref/");
        ps.print(this.id);
        if (this.symbol != null && (this.binding == null || this.symbol.toString() != this.binding.getName())) {
            ps.print('/');
            ps.print(this.symbol);
        }
        if (this.binding != null) {
            ps.print('/');
            ps.print((Object) this.binding);
        }
        ps.print(")");
    }

    public Type getType() {
        Expression value;
        Declaration decl = this.binding;
        if (decl == null || decl.isFluid()) {
            return Type.pointer_type;
        }
        if (getDontDereference()) {
            return Compilation.typeLocation;
        }
        Declaration decl2 = Declaration.followAliases(decl);
        Type type = decl2.getType();
        if (!((type != null && type != Type.pointer_type) || (value = decl2.getValue()) == null || value == QuoteExp.undefined_exp)) {
            Expression save = decl2.value;
            decl2.value = null;
            type = value.getType();
            decl2.value = save;
        }
        if (type == Type.toStringType) {
            return Type.javalangStringType;
        }
        return type;
    }

    public boolean isSingleValue() {
        if (this.binding == null || !this.binding.getFlag(262144)) {
            return super.isSingleValue();
        }
        return true;
    }

    public boolean side_effects() {
        return this.binding == null || !this.binding.isLexical();
    }

    public String toString() {
        return "RefExp/" + this.symbol + '/' + this.id + '/';
    }
}
