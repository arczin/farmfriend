package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.kawa.reflect.Invoke;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.math.BaseUnit;
import gnu.math.Quantity;
import gnu.math.Unit;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class define_unit extends Syntax {
    public static final define_unit define_base_unit = new define_unit(true);
    public static final define_unit define_unit = new define_unit(false);
    boolean base;

    static {
        define_unit.setName("define-unit");
        define_base_unit.setName("define-base-unit");
    }

    public define_unit(boolean base2) {
        this.base = base2;
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        if (st.getCdr() instanceof Pair) {
            Pair p = (Pair) st.getCdr();
            Object q = p.getCar();
            if (q instanceof SimpleSymbol) {
                String name = q.toString();
                Declaration decl = defs.getDefine(Scheme.unitNamespace.getSymbol(name), 'w', tr);
                tr.push(decl);
                Translator.setLine(decl, (Object) p);
                decl.setFlag(16384);
                if (defs instanceof ModuleExp) {
                    decl.setCanRead(true);
                }
                Unit unit = null;
                if (this.base && p.getCdr() == LList.Empty) {
                    String str = null;
                    unit = BaseUnit.make(name, (String) null);
                } else if (p.getCdr() instanceof Pair) {
                    Object v = ((Pair) p.getCdr()).getCar();
                    if (this.base && (v instanceof CharSequence)) {
                        unit = BaseUnit.make(name, v.toString());
                    } else if (!this.base && (v instanceof Quantity)) {
                        unit = Unit.make(name, (Quantity) v);
                    }
                }
                if (unit != null) {
                    decl.noteValue(new QuoteExp(unit));
                }
                forms.addElement(Translator.makePair(st, this, Translator.makePair(p, decl, p.getCdr())));
                return true;
            }
        }
        tr.error('e', "missing name in define-unit");
        return false;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        Object obj = form.getCdr();
        if (obj instanceof Pair) {
            Pair pair = (Pair) obj;
            Pair p1 = pair;
            if (pair.getCar() instanceof Declaration) {
                Declaration decl = (Declaration) p1.getCar();
                String unit = ((Symbol) decl.getSymbol()).getLocalPart();
                ClassType unitType = ClassType.make("gnu.math.Unit");
                decl.setType(unitType);
                Expression value = decl.getValue();
                Expression value2 = value;
                if (!(value instanceof QuoteExp) || !(((QuoteExp) value2).getValue() instanceof Unit)) {
                    if (this.base) {
                        String dimension = null;
                        if (p1.getCdr() != LList.Empty) {
                            dimension = ((Pair) p1.getCdr()).getCar().toString();
                        }
                        value2 = new QuoteExp(BaseUnit.make(unit, dimension));
                    } else if (!(p1.getCdr() instanceof Pair)) {
                        return tr.syntaxError("missing value for define-unit");
                    } else {
                        Expression value3 = tr.rewrite(((Pair) p1.getCdr()).getCar());
                        if (value3 instanceof QuoteExp) {
                            Object value4 = ((QuoteExp) value3).getValue();
                            Object quantity = value4;
                            if (value4 instanceof Quantity) {
                                value2 = new QuoteExp(Unit.make(unit, (Quantity) quantity));
                            }
                        }
                        value2 = Invoke.makeInvokeStatic(unitType, "make", new Expression[]{new QuoteExp(unit), value3});
                    }
                }
                SetExp sexp = new SetExp(decl, value2);
                sexp.setDefining(true);
                decl.noteValue(value2);
                return sexp;
            }
        }
        return tr.syntaxError("invalid syntax for " + getName());
    }
}
