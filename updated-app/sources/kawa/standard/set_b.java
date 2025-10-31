package kawa.standard;

import gnu.expr.ApplyExp;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.ReferenceExp;
import gnu.expr.SetExp;
import gnu.kawa.functions.CompilationHelpers;
import gnu.lists.LList;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class set_b extends Syntax {
    public static final set_b set = new set_b();

    static {
        set.setName("set!");
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        Translator translator = tr;
        Object o1 = form.getCdr();
        SyntaxForm syntax = null;
        while (o1 instanceof SyntaxForm) {
            syntax = (SyntaxForm) o1;
            o1 = syntax.getDatum();
        }
        if (!(o1 instanceof Pair)) {
            return translator.syntaxError("missing name");
        }
        Pair p1 = (Pair) o1;
        Expression name = translator.rewrite_car(p1, syntax);
        Object o2 = p1.getCdr();
        while (o2 instanceof SyntaxForm) {
            syntax = (SyntaxForm) o2;
            o2 = syntax.getDatum();
        }
        if (o2 instanceof Pair) {
            Pair pair = (Pair) o2;
            Pair p2 = pair;
            if (pair.getCdr() != LList.Empty) {
                Object obj = o1;
                SyntaxForm syntaxForm = syntax;
                Pair pair2 = p1;
            } else {
                Expression value = translator.rewrite_car(p2, syntax);
                if (name instanceof ApplyExp) {
                    ApplyExp aexp = (ApplyExp) name;
                    Expression[] args = aexp.getArgs();
                    int nargs = args.length;
                    int skip = 0;
                    Expression func = aexp.getFunction();
                    if (args.length > 0 && (func instanceof ReferenceExp) && ((ReferenceExp) func).getBinding() == Scheme.applyFieldDecl) {
                        skip = 1;
                        nargs--;
                        func = args[0];
                    }
                    Expression[] setterArgs = {func};
                    Expression[] xargs = new Expression[(nargs + 1)];
                    System.arraycopy(args, skip, xargs, 0, nargs);
                    xargs[nargs] = value;
                    Object obj2 = o1;
                    SyntaxForm syntaxForm2 = syntax;
                    Pair pair3 = p1;
                    return new ApplyExp((Expression) new ApplyExp((Expression) new ReferenceExp(CompilationHelpers.setterDecl), setterArgs), xargs);
                }
                SyntaxForm syntaxForm3 = syntax;
                Pair pair4 = p1;
                if (!(name instanceof ReferenceExp)) {
                    return translator.syntaxError("first set! argument is not a variable name");
                }
                ReferenceExp ref = (ReferenceExp) name;
                Declaration decl = ref.getBinding();
                SetExp sexp = new SetExp(ref.getSymbol(), value);
                sexp.setContextDecl(ref.contextDecl());
                if (decl != null) {
                    decl.setCanWrite(true);
                    sexp.setBinding(decl);
                    Declaration decl2 = Declaration.followAliases(decl);
                    if (decl2 != null) {
                        decl2.noteValue(value);
                    }
                    if (decl2.getFlag(16384)) {
                        return translator.syntaxError("constant variable " + decl2.getName() + " is set!");
                    }
                    if (decl2.context != translator.mainLambda && (decl2.context instanceof ModuleExp) && !decl2.getFlag(268435456) && !decl2.context.getFlag(1048576)) {
                        translator.error('w', decl2, "imported variable ", " is set!");
                    }
                }
                return sexp;
            }
        } else {
            SyntaxForm syntaxForm4 = syntax;
            Pair pair5 = p1;
        }
        return translator.syntaxError("missing or extra arguments to set!");
    }
}
