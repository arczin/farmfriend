package gnu.commonlisp.lang;

import gnu.expr.BeginExp;
import gnu.expr.Expression;
import gnu.expr.SetExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class setq extends Syntax {
    public Expression rewriteForm(Pair form, Translator tr) {
        Object name;
        Object obj = form.getCdr();
        Vector results = null;
        while (obj != LList.Empty) {
            if (!(obj instanceof Pair)) {
                return tr.syntaxError("invalid syntax for setq");
            }
            Pair pair = (Pair) obj;
            Object sym = pair.getCar();
            if ((sym instanceof Symbol) || (sym instanceof String)) {
                name = sym;
            } else if (sym != CommonLisp.FALSE) {
                return tr.syntaxError("invalid variable name in setq");
            } else {
                name = "nil";
            }
            Object obj2 = pair.getCdr();
            if (!(obj2 instanceof Pair)) {
                return tr.syntaxError("wrong number of arguments for setq");
            }
            Pair pair2 = (Pair) obj2;
            Expression value = tr.rewrite(pair2.getCar());
            obj = pair2.getCdr();
            SetExp sexp = new SetExp(name, value);
            sexp.setFlag(8);
            if (obj == LList.Empty) {
                sexp.setHasValue(true);
                if (results == null) {
                    return sexp;
                }
            }
            if (results == null) {
                results = new Vector(10);
            }
            results.addElement(sexp);
        }
        if (results == null) {
            return CommonLisp.nilExpr;
        }
        Expression[] stmts = new Expression[results.size()];
        results.copyInto(stmts);
        return new BeginExp(stmts);
    }
}
