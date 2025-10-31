package kawa.standard;

import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ScopeExp;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class define_class extends Syntax {
    public static final define_class define_class = new define_class("define-class", false);
    public static final define_class define_simple_class = new define_class("define-simple-class", true);
    boolean isSimple;
    object objectSyntax;

    define_class(object objectSyntax2, boolean isSimple2) {
        this.objectSyntax = objectSyntax2;
        this.isSimple = isSimple2;
    }

    define_class(String name, boolean isSimple2) {
        super(name);
        this.objectSyntax = object.objectSyntax;
        this.isSimple = isSimple2;
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        Translator translator = tr;
        Object st_cdr = st.getCdr();
        SyntaxForm nameSyntax = null;
        while (st_cdr instanceof SyntaxForm) {
            nameSyntax = (SyntaxForm) st_cdr;
            st_cdr = nameSyntax.getDatum();
        }
        if (!(st_cdr instanceof Pair)) {
            return super.scanForDefinitions(st, forms, defs, tr);
        }
        Pair p = (Pair) st_cdr;
        Object name = p.getCar();
        while (name instanceof SyntaxForm) {
            nameSyntax = (SyntaxForm) name;
            name = nameSyntax.getDatum();
        }
        Object name2 = translator.namespaceResolve(name);
        if ((name2 instanceof String) || (name2 instanceof Symbol)) {
            Declaration decl = translator.define(name2, nameSyntax, defs);
            if (p instanceof PairWithPosition) {
                decl.setLocation((PairWithPosition) p);
            }
            ClassExp oexp = new ClassExp(this.isSimple);
            decl.noteValue(oexp);
            decl.setFlag(536887296);
            decl.setType(this.isSimple ? Compilation.typeClass : Compilation.typeClassType);
            tr.mustCompileHere();
            String cname = name2 instanceof Symbol ? ((Symbol) name2).getName() : name2.toString();
            int nlen = cname.length();
            if (nlen > 2 && cname.charAt(0) == '<' && cname.charAt(nlen - 1) == '>') {
                cname = cname.substring(1, nlen - 1);
            }
            oexp.setName(cname);
            Object members = p.getCdr();
            while (members instanceof SyntaxForm) {
                nameSyntax = (SyntaxForm) members;
                members = nameSyntax.getDatum();
            }
            if (!(members instanceof Pair)) {
                translator.error('e', "missing class members");
                return false;
            }
            Pair p2 = (Pair) members;
            ScopeExp save_scope = tr.currentScope();
            if (nameSyntax != null) {
                translator.setCurrentScope(nameSyntax.getScope());
            }
            Object[] saved = this.objectSyntax.scanClassDef(p2, oexp, translator);
            if (nameSyntax != null) {
                translator.setCurrentScope(save_scope);
            }
            if (saved == null) {
                return false;
            }
            forms.addElement(Translator.makePair(st, this, Translator.makePair(p2, decl, saved)));
            return true;
        }
        translator.error('e', "missing class name");
        return false;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: gnu.expr.Declaration} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression rewriteForm(gnu.lists.Pair r6, kawa.lang.Translator r7) {
        /*
            r5 = this;
            r0 = 0
            java.lang.Object r1 = r6.getCdr()
            boolean r2 = r1 instanceof gnu.lists.Pair
            if (r2 == 0) goto L_0x0033
            r6 = r1
            gnu.lists.Pair r6 = (gnu.lists.Pair) r6
            java.lang.Object r2 = r6.getCar()
            boolean r3 = r2 instanceof gnu.expr.Declaration
            if (r3 != 0) goto L_0x0030
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = r5.getName()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = " can only be used in <body>"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            gnu.expr.Expression r3 = r7.syntaxError(r3)
            return r3
        L_0x0030:
            r0 = r2
            gnu.expr.Declaration r0 = (gnu.expr.Declaration) r0
        L_0x0033:
            gnu.expr.Expression r2 = r0.getValue()
            gnu.expr.ClassExp r2 = (gnu.expr.ClassExp) r2
            kawa.standard.object r3 = r5.objectSyntax
            java.lang.Object r4 = r6.getCdr()
            java.lang.Object[] r4 = (java.lang.Object[]) r4
            java.lang.Object[] r4 = (java.lang.Object[]) r4
            r3.rewriteClassDef(r4, r7)
            gnu.expr.SetExp r3 = new gnu.expr.SetExp
            r3.<init>((gnu.expr.Declaration) r0, (gnu.expr.Expression) r2)
            r4 = 1
            r3.setDefining(r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.define_class.rewriteForm(gnu.lists.Pair, kawa.lang.Translator):gnu.expr.Expression");
    }
}
