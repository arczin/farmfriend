package kawa.standard;

import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.LangExp;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import kawa.lang.Lambda;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.TemplateScope;
import kawa.lang.Translator;

public class define extends Syntax {
    public static final define defineRaw = new define(SchemeCompilation.lambda);
    Lambda lambda;

    /* access modifiers changed from: package-private */
    public String getName(int options) {
        if ((options & 4) != 0) {
            return "define-private";
        }
        if ((options & 8) != 0) {
            return "define-constant";
        }
        return "define";
    }

    public define(Lambda lambda2) {
        this.lambda = lambda2;
    }

    public void scanForm(Pair st, ScopeExp defs, Translator tr) {
        ScopeExp scopeExp = defs;
        Translator translator = tr;
        Pair p1 = (Pair) st.getCdr();
        Pair p2 = (Pair) p1.getCdr();
        Pair p3 = (Pair) p2.getCdr();
        Pair p4 = (Pair) p3.getCdr();
        SyntaxForm nameSyntax = null;
        Object name = p1.getCar();
        while (name instanceof SyntaxForm) {
            nameSyntax = (SyntaxForm) name;
            name = nameSyntax.getDatum();
        }
        int options = ((Number) Translator.stripSyntax(p2.getCar())).intValue();
        boolean makeConstant = false;
        boolean makePrivate = (options & 4) != 0;
        if ((options & 8) != 0) {
            makeConstant = true;
        }
        ScopeExp scope = tr.currentScope();
        Object name2 = translator.namespaceResolve(name);
        if (!(name2 instanceof Symbol)) {
            translator.error('e', "'" + name2 + "' is not a valid identifier");
            name2 = null;
        }
        Object savePos = translator.pushPositionOf(p1);
        Declaration decl = translator.define(name2, nameSyntax, scopeExp);
        translator.popPositionOf(savePos);
        Object name3 = decl.getSymbol();
        if (makePrivate) {
            ScopeExp scopeExp2 = scope;
            decl.setFlag(16777216);
            decl.setPrivate(true);
        }
        if (makeConstant) {
            decl.setFlag(16384);
        }
        decl.setFlag(262144);
        if ((options & 2) != 0) {
            LambdaExp lexp = new LambdaExp();
            lexp.setSymbol(name3);
            if (Compilation.inlineOk) {
                decl.setProcedureDecl(true);
                decl.setType(Compilation.typeProcedure);
                lexp.nameDecl = decl;
            }
            Object formals = p4.getCar();
            SyntaxForm syntaxForm = nameSyntax;
            Object body = p4.getCdr();
            Translator.setLine((Expression) lexp, (Object) p1);
            Pair pair = p4;
            Object obj = name3;
            this.lambda.rewriteFormals(lexp, formals, translator, (TemplateScope) null);
            Object realBody = this.lambda.rewriteAttrs(lexp, body, translator);
            if (realBody != body) {
                Object obj2 = body;
                Pair pair2 = p2;
                boolean z = makeConstant;
                Object obj3 = savePos;
                p2 = new Pair(p2.getCar(), new Pair(p3.getCar(), new Pair(formals, realBody)));
            } else {
                Pair pair3 = p2;
                Object obj4 = body;
                boolean z2 = makeConstant;
                Object obj5 = savePos;
            }
            decl.noteValue(lexp);
        } else {
            Pair pair4 = p2;
            Pair pair5 = p4;
            SyntaxForm syntaxForm2 = nameSyntax;
            Object obj6 = name3;
            boolean z3 = makeConstant;
            Object obj7 = savePos;
        }
        if ((scopeExp instanceof ModuleExp) && !makePrivate && (!Compilation.inlineOk || tr.sharedModuleDefs())) {
            decl.setCanWrite(true);
        }
        if ((options & 1) != 0) {
            decl.setTypeExp(new LangExp(p3));
            decl.setFlag(8192);
        }
        Pair st2 = Translator.makePair(st, this, Translator.makePair(p1, decl, p2));
        Translator.setLine(decl, (Object) p1);
        translator.formStack.addElement(st2);
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        Expression value;
        Translator translator = tr;
        Pair p1 = (Pair) form.getCdr();
        Pair p2 = (Pair) p1.getCdr();
        Pair p4 = (Pair) ((Pair) p2.getCdr()).getCdr();
        Object name = Translator.stripSyntax(p1.getCar());
        int options = ((Number) Translator.stripSyntax(p2.getCar())).intValue();
        boolean makePrivate = (options & 4) != 0;
        if (!(name instanceof Declaration)) {
            return translator.syntaxError(getName(options) + " is only allowed in a <body>");
        }
        Declaration decl = (Declaration) name;
        if (decl.getFlag(8192)) {
            Expression texp = decl.getTypeExp();
            if (texp instanceof LangExp) {
                decl.setType(translator.exp2Type((Pair) ((LangExp) texp).getLangValue()));
            }
        }
        Expression expression = null;
        if ((options & 2) != 0) {
            LambdaExp lexp = (LambdaExp) decl.getValue();
            this.lambda.rewriteBody(lexp, p4.getCdr(), translator);
            value = lexp;
            if (!Compilation.inlineOk) {
                decl.noteValue((Expression) null);
            }
        } else {
            value = translator.rewrite(p4.getCar());
            if (!(decl.context instanceof ModuleExp) || makePrivate || !decl.getCanWrite()) {
                expression = value;
            }
            decl.noteValue(expression);
        }
        SetExp sexp = new SetExp(decl, value);
        sexp.setDefining(true);
        if (makePrivate && !(tr.currentScope() instanceof ModuleExp)) {
            translator.error('w', "define-private not at top level " + tr.currentScope());
        }
        return sexp;
    }
}
