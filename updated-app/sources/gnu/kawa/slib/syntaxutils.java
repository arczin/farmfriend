package gnu.kawa.slib;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.NameLookup;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.SetExp;
import gnu.expr.Special;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Convert;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.reflect.SlotGet;
import gnu.lists.Consumer;
import gnu.lists.EofClass;
import gnu.lists.LList;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.text.Char;
import gnu.text.SourceMessages;
import kawa.lang.Macro;
import kawa.lang.Quote;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lang.Translator;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.standard.Scheme;

/* compiled from: syntaxutils.scm */
public class syntaxutils extends ModuleBody {
    public static final Macro $Prvt$$Ex = Macro.make(Lit15, Lit16, $instance);
    public static final Macro $Prvt$typecase$Pc = Macro.make(Lit13, Lit14, $instance);
    public static final syntaxutils $instance = new syntaxutils();
    static final Keyword Lit0 = Keyword.make("env");
    static final PairWithPosition Lit1 = PairWithPosition.make(Lit21, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 278557);
    static final PairWithPosition Lit10 = PairWithPosition.make(Lit19, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 552972);
    static final PairWithPosition Lit11 = PairWithPosition.make(Lit25, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 626704);
    static final PairWithPosition Lit12 = PairWithPosition.make((SimpleSymbol) new SimpleSymbol(":").readResolve(), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 634896);
    static final SimpleSymbol Lit13;
    static final SyntaxRules Lit14;
    static final SimpleSymbol Lit15;
    static final SyntaxRules Lit16;
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("expand").readResolve());
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("eql").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve());
    static final Keyword Lit2 = Keyword.make("lang");
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("or").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("begin").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("cond").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("let").readResolve());
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("else").readResolve());
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("as").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("lambda").readResolve());
    static final PairWithPosition Lit3 = PairWithPosition.make((SimpleSymbol) new SimpleSymbol("set").readResolve(), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 368647);
    static final PairWithPosition Lit4 = PairWithPosition.make(Lit26, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 376839);
    static final PairWithPosition Lit5 = PairWithPosition.make(Lit21, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 409627);
    static final PairWithPosition Lit6 = PairWithPosition.make((SimpleSymbol) new SimpleSymbol("if").readResolve(), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 417799);
    static final IntNum Lit7 = IntNum.make(0);
    static final IntNum Lit8 = IntNum.make(1);
    static final PairWithPosition Lit9 = PairWithPosition.make(Lit23, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 479236);
    public static final ModuleMethod expand = new ModuleMethod($instance, 1, Lit17, -4095);

    /* compiled from: syntaxutils.scm */
    public class frame extends ModuleBody {
        LList pack;
    }

    /* compiled from: syntaxutils.scm */
    public class frame0 extends ModuleBody {
        LList pack;
    }

    /* compiled from: syntaxutils.scm */
    public class frame1 extends ModuleBody {
        LList pack;
    }

    static {
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("!").readResolve();
        Lit15 = simpleSymbol;
        Lit16 = new SyntaxRules(new Object[]{simpleSymbol}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\r\u0017\u0010\b\b", new Object[0], 3), "\u0001\u0001\u0003", "\u0011\u0018\u0004\t\u000b)\u0011\u0018\f\b\u0003\b\u0015\u0013", new Object[]{(SimpleSymbol) new SimpleSymbol("invoke").readResolve(), Lit19}, 1)}, 3);
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("typecase%").readResolve();
        Lit13 = simpleSymbol2;
        Lit14 = new SyntaxRules(new Object[]{simpleSymbol2, Lit18, Lit20}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007<\f\u0002\r\u000f\b\b\b\r\u0017\u0010\b\b", new Object[]{Boolean.TRUE}, 3), "\u0001\u0003\u0003", "\u0011\u0018\u0004\b\r\u000b", new Object[]{Lit21}, 1), new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\\,\f\u0002\f\u000f\b\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{Lit18}, 4), "\u0001\u0001\u0003\u0003", "\u0011\u0018\u0004yY\u0011\u0018\f\t\u0003\b\u0011\u0018\u0014\b\u000b\b\u0015\u0013\b\u0011\u0018\u001c\b\u0011\u0018$\t\u0003\b\u001d\u001b", new Object[]{Lit22, (SimpleSymbol) new SimpleSymbol("eqv?").readResolve(), Lit19, Lit24, Lit13}, 1), new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\\,\f\u0002\f\u000f\b\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{Lit20}, 4), "\u0001\u0001\u0003\u0003", "\u0011\u0018\u0004\t\u0003)\t\u000b\b\u0015\u0013\b\u001d\u001b", new Object[]{Lit13}, 1), new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007l<\f\u0002\r\u000f\b\b\b\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{Lit20}, 4), "\u0001\u0003\u0003\u0003", "\u0011\u0018\u0004\b\u0011\u0018\f\b\u0011\u0018\u0014\u0011\b\u0003\b\u0011\u0018\u001c\b\u0015\u0013\b\u0011\u0018$\t\u0003I\r\t\u000b\b\u0011\u0018\f\b\u0003\b\u0011\u0018,\b\u0011\u0018$\t\u0003\b\u001d\u001b", new Object[]{Lit23, (SimpleSymbol) new SimpleSymbol("f").readResolve(), Lit26, Lit21, Lit13, Boolean.TRUE}, 1), new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007<\f\u000f\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[0], 4), "\u0001\u0001\u0003\u0003", "\u0011\u0018\u0004ñ9\u0011\u0018\f\t\u0003\b\u000b\b\u0011\u0018\u0014Q\b\t\u0003\u0011\u0018\u001c\t\u000b\b\u0003\b\u0011\u0018$\b\u0015\u0013\b\u0011\u0018,\b\u0011\u00184\t\u0003\b\u001d\u001b", new Object[]{Lit22, (SimpleSymbol) new SimpleSymbol(GetNamedPart.INSTANCEOF_METHOD_NAME).readResolve(), Lit23, (SimpleSymbol) new SimpleSymbol("::").readResolve(), Lit21, Lit24, Lit13}, 1), new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\u0011\u0018\f\t\u0003\b\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$\u0011\u0018,\b\u0003", new Object[]{(SimpleSymbol) new SimpleSymbol("error").readResolve(), "typecase% failed", Lit15, (SimpleSymbol) new SimpleSymbol("getClass").readResolve(), Lit25, (SimpleSymbol) new SimpleSymbol("<object>").readResolve()}, 0)}, 4);
        $instance.run();
    }

    public syntaxutils() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static Object expand$V(Object sexp, Object[] argsArray) {
        Object env = Keyword.searchForKeyword(argsArray, 0, Lit0);
        if (env == Special.dfault) {
            env = misc.interactionEnvironment();
        }
        return unrewrite(rewriteForm$V(Quote.append$V(new Object[]{Lit1, Quote.consX$V(new Object[]{sexp, LList.Empty})}), new Object[]{Lit0, env}));
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        if (moduleMethod.selector != 1) {
            return super.applyN(moduleMethod, objArr);
        }
        Object obj = objArr[0];
        int length = objArr.length - 1;
        Object[] objArr2 = new Object[length];
        while (true) {
            length--;
            if (length < 0) {
                return expand$V(obj, objArr2);
            }
            objArr2[length] = objArr[length + 1];
        }
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        if (moduleMethod.selector != 1) {
            return super.matchN(moduleMethod, objArr, callContext);
        }
        callContext.values = objArr;
        callContext.proc = moduleMethod;
        callContext.pc = 5;
        return 0;
    }

    static Expression rewriteForm$V(Object exp, Object[] argsArray) {
        Object searchForKeyword = Keyword.searchForKeyword(argsArray, 0, Lit2);
        if (searchForKeyword == Special.dfault) {
            searchForKeyword = Language.getDefaultLanguage();
        }
        Object lang = searchForKeyword;
        Object searchForKeyword2 = Keyword.searchForKeyword(argsArray, 0, Lit0);
        if (searchForKeyword2 == Special.dfault) {
            searchForKeyword2 = misc.interactionEnvironment();
        }
        Object env = searchForKeyword2;
        try {
            try {
                Object obj = lang;
                try {
                    Translator translator = new Translator((Language) lang, new SourceMessages(), NameLookup.getInstance((Environment) env, (Language) lang));
                    translator.pushNewModule((String) null);
                    Compilation saved$Mncomp = Compilation.setSaveCurrent(translator);
                    Object obj2 = lang;
                    try {
                        return translator.rewrite(exp);
                    } finally {
                        Compilation.restoreCurrent(saved$Mncomp);
                    }
                } catch (ClassCastException e) {
                    throw new WrongType(e, "kawa.lang.Translator.<init>(gnu.expr.Language,gnu.text.SourceMessages,gnu.expr.NameLookup)", 1, lang);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "gnu.expr.NameLookup.getInstance(gnu.mapping.Environment,gnu.expr.Language)", 2, lang);
            }
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "gnu.expr.NameLookup.getInstance(gnu.mapping.Environment,gnu.expr.Language)", 1, env);
        }
    }

    static Object unrewrite(Expression exp) {
        frame frame2 = new frame();
        if (exp instanceof LetExp) {
            try {
                return unrewriteLet((LetExp) exp);
            } catch (ClassCastException e) {
                throw new WrongType(e, "exp", -2, (Object) exp);
            }
        } else if (exp instanceof QuoteExp) {
            try {
                return unrewriteQuote((QuoteExp) exp);
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "exp", -2, (Object) exp);
            }
        } else if (exp instanceof SetExp) {
            try {
                SetExp exp2 = (SetExp) exp;
                return Quote.append$V(new Object[]{Lit3, Quote.consX$V(new Object[]{exp2.getSymbol(), Quote.consX$V(new Object[]{unrewrite(exp2.getNewValue()), LList.Empty})})});
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "exp", -2, (Object) exp);
            }
        } else if (exp instanceof LambdaExp) {
            try {
                LambdaExp exp3 = (LambdaExp) exp;
                frame2.pack = LList.Empty;
                Declaration decl = exp3.firstDecl();
                while (true) {
                    frame closureEnv = frame2;
                    if (decl != null) {
                        frame2.pack = lists.cons(decl.getSymbol(), frame2.pack);
                        decl = decl.nextDecl();
                    } else {
                        return Quote.append$V(new Object[]{Lit4, Quote.consX$V(new Object[]{lists.reverse$Ex(frame2.pack), Quote.consX$V(new Object[]{unrewrite(exp3.body), LList.Empty})})});
                    }
                }
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "exp", -2, (Object) exp);
            }
        } else if (exp instanceof ReferenceExp) {
            try {
                return ((ReferenceExp) exp).getSymbol();
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "exp", -2, (Object) exp);
            }
        } else if (exp instanceof ApplyExp) {
            try {
                return unrewriteApply((ApplyExp) exp);
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "exp", -2, (Object) exp);
            }
        } else if (exp instanceof BeginExp) {
            try {
                return Quote.append$V(new Object[]{Lit5, unrewrite$St(((BeginExp) exp).getExpressions())});
            } catch (ClassCastException e7) {
                throw new WrongType(e7, "exp", -2, (Object) exp);
            }
        } else if (!(exp instanceof IfExp)) {
            return exp;
        } else {
            try {
                IfExp exp4 = (IfExp) exp;
                Object unrewrite = unrewrite(exp4.getTest());
                Object unrewrite2 = unrewrite(exp4.getThenClause());
                Expression eclause = exp4.getElseClause();
                Expression expression = exp;
                return Quote.append$V(new Object[]{Lit6, Quote.consX$V(new Object[]{unrewrite, Quote.consX$V(new Object[]{unrewrite2, Quote.append$V(new Object[]{eclause == null ? LList.Empty : LList.list1(unrewrite(eclause)), LList.Empty})})})});
            } catch (ClassCastException e8) {
                throw new WrongType(e8, "exp", -2, (Object) exp);
            }
        }
    }

    static Object unrewrite$St(Expression[] exps) {
        frame0 frame02 = new frame0();
        frame02.pack = LList.Empty;
        Object len = Integer.valueOf(exps.length);
        Object i = Lit7;
        while (true) {
            frame0 closureEnv = frame02;
            if (Scheme.numEqu.apply2(i, len) != Boolean.FALSE) {
                return lists.reverse$Ex(frame02.pack);
            }
            frame02.pack = lists.cons(unrewrite(exps[((Number) i).intValue()]), frame02.pack);
            i = AddOp.$Pl.apply2(i, Lit8);
        }
    }

    static Object unrewriteLet(LetExp exp) {
        frame1 frame12 = new frame1();
        frame12.pack = LList.Empty;
        Declaration decl = exp.firstDecl();
        Object i = Lit7;
        while (true) {
            frame1 closureEnv = frame12;
            if (decl != null) {
                frame12.pack = lists.cons(LList.list2(decl.getSymbol(), unrewrite(exp.inits[((Number) i).intValue()])), frame12.pack);
                Declaration nextDecl = decl.nextDecl();
                i = AddOp.$Pl.apply2(i, Lit8);
                decl = nextDecl;
            } else {
                return Quote.append$V(new Object[]{Lit9, Quote.consX$V(new Object[]{lists.reverse$Ex(frame12.pack), Quote.consX$V(new Object[]{unrewrite(exp.body), LList.Empty})})});
            }
        }
    }

    static Object unrewriteQuote(QuoteExp quoteExp) {
        String str;
        Object value = quoteExp.getValue();
        if (Numeric.asNumericOrNull(value) != null) {
            try {
                return LangObjType.coerceNumeric(value);
            } catch (ClassCastException e) {
                throw new WrongType(e, "val", -2, value);
            }
        } else {
            boolean z = true;
            if (value instanceof Boolean) {
                try {
                    if (value == Boolean.FALSE) {
                        z = false;
                    }
                    return z ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "val", -2, value);
                }
            } else if (value instanceof Char) {
                try {
                    return (Char) value;
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "val", -2, value);
                }
            } else if (value instanceof Keyword) {
                try {
                    return (Keyword) value;
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "val", -2, value);
                }
            } else if (value instanceof CharSequence) {
                try {
                    return (CharSequence) value;
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "val", -2, value);
                }
            } else if (value == Special.undefined || value == EofClass.eofValue) {
                return value;
            } else {
                if (value instanceof Type) {
                    try {
                        str = ((Type) value).getName();
                    } catch (ClassCastException e6) {
                        throw new WrongType(e6, "val", -2, value);
                    }
                } else if (value instanceof Class) {
                    try {
                        str = ((Class) value).getName();
                    } catch (ClassCastException e7) {
                        throw new WrongType(e7, "val", -2, value);
                    }
                } else {
                    return Quote.append$V(new Object[]{Lit10, Quote.consX$V(new Object[]{value, LList.Empty})});
                }
                return misc.string$To$Symbol(Format.formatToString(0, "<~a>", str));
            }
        }
    }

    static Object unrewriteApply(ApplyExp applyExp) {
        Declaration declaration;
        Expression function = applyExp.getFunction();
        Object unrewrite$St = unrewrite$St(applyExp.getArgs());
        if (function instanceof ReferenceExp) {
            try {
                declaration = ((ReferenceExp) function).getBinding();
            } catch (ClassCastException e) {
                throw new WrongType(e, "fun", -2, (Object) function);
            }
        } else {
            declaration = null;
        }
        Declaration declaration2 = declaration;
        Declaration declarationFromStatic = Declaration.getDeclarationFromStatic("kawa.standard.Scheme", "applyToArgs");
        Object functionValue = applyExp.getFunctionValue();
        int i = ((declaration2 == null ? 1 : 0) + 1) & 1;
        if (i != 0) {
            int i2 = ((declarationFromStatic == null ? 1 : 0) + 1) & 1;
            if (i2 != 0) {
                if (SlotGet.getSlotValue(false, declaration2, "field", "field", "getField", "isField", Scheme.instance) == declarationFromStatic.field) {
                    return unrewrite$St;
                }
            } else if (i2 != 0) {
                return unrewrite$St;
            }
        } else if (i != 0) {
            return unrewrite$St;
        }
        Object append$V = functionValue instanceof Convert ? Quote.append$V(new Object[]{Lit11, unrewrite$St}) : functionValue instanceof GetNamedPart ? Quote.append$V(new Object[]{Lit12, unrewrite$St}) : Boolean.FALSE;
        if (append$V != Boolean.FALSE) {
            return append$V;
        }
        return Quote.consX$V(new Object[]{unrewrite(function), unrewrite$St});
    }
}
