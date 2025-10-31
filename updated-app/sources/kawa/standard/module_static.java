package kawa.standard;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class module_static extends Syntax {
    public static final module_static module_static = new module_static();

    static {
        module_static.setName("module-static");
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        ScopeExp scopeExp = defs;
        Translator translator = tr;
        Object list = st.getCdr();
        if (!(scopeExp instanceof ModuleExp)) {
            translator.error('e', "'" + getName() + "' not at module level");
            return true;
        }
        ModuleExp mexp = (ModuleExp) scopeExp;
        if (list instanceof Pair) {
            Pair pair = (Pair) list;
            Pair st2 = pair;
            if (pair.getCdr() == LList.Empty && (st2.getCar() instanceof Boolean)) {
                if (st2.getCar() == Boolean.FALSE) {
                    mexp.setFlag(65536);
                } else {
                    mexp.setFlag(32768);
                }
                if (mexp.getFlag(65536) && mexp.getFlag(32768)) {
                    translator.error('e', "inconsistent module-static specifiers");
                }
                return true;
            }
        }
        if (list instanceof Pair) {
            Pair pair2 = (Pair) list;
            Pair st3 = pair2;
            if (pair2.getCdr() == LList.Empty && (st3.getCar() instanceof Pair)) {
                Pair pair3 = (Pair) st3.getCar();
                Pair st4 = pair3;
                if (translator.matches(pair3.getCar(), LispLanguage.quote_sym)) {
                    Pair pair4 = (Pair) st4.getCdr();
                    Pair st5 = pair4;
                    if (pair4 == LList.Empty || !(st5.getCar() instanceof SimpleSymbol) || st5.getCar().toString() != "init-run") {
                        translator.error('e', "invalid quoted symbol for '" + getName() + '\'');
                        return false;
                    }
                    mexp.setFlag(32768);
                    mexp.setFlag(262144);
                    translator.error('e', "inconsistent module-static specifiers");
                    return true;
                }
            }
        }
        mexp.setFlag(65536);
        while (list != LList.Empty) {
            if (list instanceof Pair) {
                Pair pair5 = (Pair) list;
                Pair st6 = pair5;
                if (pair5.getCar() instanceof Symbol) {
                    Declaration decl = scopeExp.getNoDefine((Symbol) st6.getCar());
                    if (decl.getFlag(512)) {
                        Translator.setLine(decl, (Object) st6);
                    }
                    decl.setFlag(2048);
                    list = st6.getCdr();
                }
            }
            translator.error('e', "invalid syntax in '" + getName() + '\'');
            return false;
        }
        translator.error('e', "inconsistent module-static specifiers");
        return true;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        return null;
    }
}
