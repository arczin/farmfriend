package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.ApplyExp;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.ModuleExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.expr.ThisExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import kawa.lang.Macro;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class define_syntax extends Syntax {
    public static final define_syntax define_macro = new define_syntax("%define-macro", false);
    public static final define_syntax define_syntax = new define_syntax("%define-syntax", true);
    static PrimProcedure makeHygienic = new PrimProcedure(typeMacro.getDeclaredMethod("make", 3));
    static PrimProcedure makeNonHygienic = new PrimProcedure(typeMacro.getDeclaredMethod("makeNonHygienic", 3));
    static PrimProcedure setCapturedScope = new PrimProcedure(typeMacro.getDeclaredMethod("setCapturedScope", 1));
    static ClassType typeMacro = ClassType.make("kawa.lang.Macro");
    boolean hygienic;

    static {
        makeHygienic.setSideEffectFree();
        makeNonHygienic.setSideEffectFree();
    }

    public define_syntax() {
        this.hygienic = true;
    }

    public define_syntax(Object name, boolean hygienic2) {
        super(name);
        this.hygienic = hygienic2;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        return tr.syntaxError("define-syntax not in a body");
    }

    public void scanForm(Pair st, ScopeExp defs, Translator tr) {
        Object name;
        ScopeExp scopeExp = defs;
        Translator translator = tr;
        SyntaxForm syntax = null;
        Object st_cdr = st.getCdr();
        while (st_cdr instanceof SyntaxForm) {
            syntax = st_cdr;
            st_cdr = syntax.getDatum();
        }
        Object p = st_cdr;
        if (p instanceof Pair) {
            Pair pp = (Pair) p;
            Object car = pp.getCar();
            p = pp.getCdr();
            name = car;
        } else {
            name = null;
        }
        SyntaxForm nameSyntax = syntax;
        Object name2 = name;
        while (name2 instanceof SyntaxForm) {
            nameSyntax = (SyntaxForm) name2;
            name2 = nameSyntax.getDatum();
        }
        Object name3 = translator.namespaceResolve(name2);
        if (!(name3 instanceof Symbol)) {
            translator.formStack.addElement(translator.syntaxError("missing macro name for " + Translator.safeCar(st)));
        } else if (p == null || Translator.safeCdr(p) != LList.Empty) {
            translator.formStack.addElement(translator.syntaxError("invalid syntax for " + getName()));
        } else {
            Declaration decl = translator.define(name3, nameSyntax, scopeExp);
            decl.setType(typeMacro);
            translator.push(decl);
            Macro savedMacro = translator.currentMacroDefinition;
            Macro macro = Macro.make(decl);
            macro.setHygienic(this.hygienic);
            translator.currentMacroDefinition = macro;
            Expression rule = translator.rewrite_car((Pair) p, syntax);
            translator.currentMacroDefinition = savedMacro;
            macro.expander = rule;
            if (rule instanceof LambdaExp) {
                ((LambdaExp) rule).setFlag(256);
            }
            Expression rule2 = new ApplyExp((Procedure) this.hygienic ? makeHygienic : makeNonHygienic, new QuoteExp(name3), rule, ThisExp.makeGivingContext(defs));
            decl.noteValue(rule2);
            decl.setProcedureDecl(true);
            if (decl.context instanceof ModuleExp) {
                SetExp result = new SetExp(decl, rule2);
                result.setDefining(true);
                if (tr.getLanguage().hasSeparateFunctionNamespace()) {
                    result.setFuncDef(true);
                }
                translator.formStack.addElement(result);
                if (translator.immediate) {
                    translator.formStack.addElement(new ApplyExp((Procedure) setCapturedScope, new ReferenceExp(decl), new QuoteExp(scopeExp)));
                }
            }
        }
    }
}
