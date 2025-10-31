package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BlockExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExitExp;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Procedure;
import gnu.math.IntNum;
import kawa.lang.Pattern;
import kawa.lang.PatternScope;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.SyntaxPattern;
import kawa.lang.Translator;

public class syntax_case extends Syntax {
    public static final syntax_case syntax_case = new syntax_case();
    PrimProcedure call_error;

    static {
        syntax_case.setName("syntax-case");
    }

    /* access modifiers changed from: package-private */
    public Expression rewriteClauses(Object obj, syntax_case_work syntax_case_work, Translator translator) {
        Expression syntaxError;
        Expression expression;
        Object obj2 = obj;
        syntax_case_work syntax_case_work2 = syntax_case_work;
        Translator translator2 = translator;
        Language language = translator.getLanguage();
        if (obj2 == LList.Empty) {
            Expression[] expressionArr = {new QuoteExp("syntax-case"), new ReferenceExp(syntax_case_work2.inputExpression)};
            if (this.call_error == null) {
                this.call_error = new PrimProcedure(ClassType.make("kawa.standard.syntax_case").addMethod("error", new Type[]{Compilation.javaStringType, Type.objectType}, (Type) Type.objectType, 9), language);
            }
            return new ApplyExp((Procedure) this.call_error, expressionArr);
        }
        Object pushPositionOf = translator2.pushPositionOf(obj2);
        try {
            if (obj2 instanceof Pair) {
                Object car = ((Pair) obj2).getCar();
                if (car instanceof Pair) {
                    Pair pair = (Pair) car;
                    PatternScope push = PatternScope.push(translator);
                    push.matchArray = translator2.matchArray;
                    translator2.push((ScopeExp) push);
                    Object cdr = pair.getCdr();
                    SyntaxForm syntaxForm = null;
                    while (cdr instanceof SyntaxForm) {
                        syntaxForm = (SyntaxForm) cdr;
                        cdr = syntaxForm.getDatum();
                    }
                    if (!(cdr instanceof Pair)) {
                        syntaxError = translator2.syntaxError("missing syntax-case output expression");
                    } else {
                        int size = push.pattern_names.size();
                        SyntaxPattern syntaxPattern = new SyntaxPattern(pair.getCar(), syntax_case_work2.literal_identifiers, translator2);
                        int varCount = syntaxPattern.varCount();
                        if (varCount > syntax_case_work2.maxVars) {
                            syntax_case_work2.maxVars = varCount;
                        }
                        BlockExp blockExp = new BlockExp();
                        ApplyExp applyExp = new ApplyExp((Procedure) new PrimProcedure(Pattern.matchPatternMethod, language), new QuoteExp(syntaxPattern), new ReferenceExp(syntax_case_work2.inputExpression), new ReferenceExp(translator2.matchArray), new QuoteExp(IntNum.zero()));
                        int i = varCount - size;
                        Expression[] expressionArr2 = new Expression[i];
                        while (true) {
                            i--;
                            if (i < 0) {
                                break;
                            }
                            expressionArr2[i] = QuoteExp.undefined_exp;
                        }
                        push.inits = expressionArr2;
                        Pair pair2 = (Pair) cdr;
                        if (pair2.getCdr() == LList.Empty) {
                            expression = translator2.rewrite_car(pair2, syntaxForm);
                        } else {
                            Expression rewrite_car = translator2.rewrite_car(pair2, syntaxForm);
                            if (pair2.getCdr() instanceof Pair) {
                                Pair pair3 = (Pair) pair2.getCdr();
                                if (pair3.getCdr() == LList.Empty) {
                                    expression = new IfExp(rewrite_car, translator2.rewrite_car(pair3, syntaxForm), new ExitExp(blockExp));
                                }
                            }
                            syntaxError = translator2.syntaxError("syntax-case:  bad clause");
                        }
                        push.setBody(expression);
                        translator2.pop(push);
                        PatternScope.pop(translator);
                        blockExp.setBody(new IfExp(applyExp, push, new ExitExp(blockExp)), rewriteClauses(((Pair) obj2).getCdr(), syntax_case_work2, translator2));
                        translator2.popPositionOf(pushPositionOf);
                        return blockExp;
                    }
                    return syntaxError;
                }
            }
            syntaxError = translator2.syntaxError("syntax-case:  bad clause list");
            return syntaxError;
        } finally {
            translator2.popPositionOf(pushPositionOf);
        }
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        syntax_case_work work = new syntax_case_work();
        Object obj = form.getCdr();
        if (!(obj instanceof Pair) || !(((Pair) obj).getCdr() instanceof Pair)) {
            return tr.syntaxError("insufficiant arguments to syntax-case");
        }
        Expression[] linits = new Expression[2];
        LetExp let = new LetExp(linits);
        String str = null;
        work.inputExpression = let.addDeclaration((Object) null);
        Declaration matchArrayOuter = tr.matchArray;
        Declaration matchArray = let.addDeclaration((Object) null);
        matchArray.setType(Compilation.objArrayType);
        matchArray.setCanRead(true);
        tr.matchArray = matchArray;
        work.inputExpression.setCanRead(true);
        tr.push((ScopeExp) let);
        Pair form2 = (Pair) obj;
        linits[0] = tr.rewrite(form2.getCar());
        work.inputExpression.noteValue(linits[0]);
        Pair form3 = (Pair) form2.getCdr();
        work.literal_identifiers = SyntaxPattern.getLiteralsList(form3.getCar(), (SyntaxForm) null, tr);
        let.body = rewriteClauses(form3.getCdr(), work, tr);
        tr.pop(let);
        Method allocVars = ClassType.make("kawa.lang.SyntaxPattern").getDeclaredMethod("allocVars", 2);
        Expression[] args = new Expression[2];
        args[0] = new QuoteExp(IntNum.make(work.maxVars));
        if (matchArrayOuter == null) {
            args[1] = QuoteExp.nullExp;
        } else {
            args[1] = new ReferenceExp(matchArrayOuter);
        }
        linits[1] = new ApplyExp(allocVars, args);
        matchArray.noteValue(linits[1]);
        tr.matchArray = matchArrayOuter;
        return let;
    }

    public static Object error(String kind, Object arg) {
        Translator tr = (Translator) Compilation.getCurrent();
        if (tr != null) {
            Syntax syntax = tr.getCurrentSyntax();
            return tr.syntaxError("no matching case while expanding " + (syntax == null ? "some syntax" : syntax.getName()));
        }
        throw new RuntimeException("no match in syntax-case");
    }
}
