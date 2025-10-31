package kawa.lib;

import androidx.fragment.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.SynchronizedExp;
import gnu.expr.TryExp;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.math.IntNum;
import kawa.lang.Macro;
import kawa.lang.Quote;
import kawa.lang.SyntaxForms;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.lang.Translator;
import kawa.standard.IfFeature;
import kawa.standard.Scheme;
import kawa.standard.syntax_case;

/* compiled from: syntax.scm */
public class syntax extends ModuleBody {
    public static final Macro $Pccond$Mnexpand;
    public static final Macro $Pcimport;
    public static final Location $Prvt$and = StaticFieldLocation.make("kawa.lib.std_syntax", "and");
    public static final Location $Prvt$define$Mnconstant = StaticFieldLocation.make("kawa.lib.prim_syntax", "define$Mnconstant");
    public static final Location $Prvt$define$Mnsyntax = StaticFieldLocation.make("kawa.lib.prim_syntax", "define$Mnsyntax");
    public static final Location $Prvt$if = StaticFieldLocation.make("kawa.lib.prim_syntax", "if");
    public static final Location $Prvt$let = StaticFieldLocation.make("kawa.lib.std_syntax", "let");
    public static final Location $Prvt$or = StaticFieldLocation.make("kawa.lib.std_syntax", "or");
    public static final Location $Prvt$try$Mncatch = StaticFieldLocation.make("kawa.lib.prim_syntax", "try$Mncatch");
    public static final syntax $instance = new syntax();
    static final SyntaxPattern Lit0 = new SyntaxPattern("\f\u0007\u000b", new Object[0], 2);
    static final SyntaxTemplate Lit1;
    static final SyntaxPattern Lit10 = new SyntaxPattern("\u0003", new Object[0], 1);
    static final SimpleSymbol Lit100;
    static final SyntaxRules Lit101;
    static final SimpleSymbol Lit102 = ((SimpleSymbol) new SimpleSymbol("$lookup$").readResolve());
    static final SimpleSymbol Lit103 = ((SimpleSymbol) new SimpleSymbol("gnu.mapping.LocationProc").readResolve());
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quasiquote_sym).readResolve());
    static final SimpleSymbol Lit105 = ((SimpleSymbol) new SimpleSymbol("arg").readResolve());
    static final SimpleSymbol Lit106 = ((SimpleSymbol) new SimpleSymbol("as").readResolve());
    static final SimpleSymbol Lit107 = ((SimpleSymbol) new SimpleSymbol("wt").readResolve());
    static final SimpleSymbol Lit108 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve());
    static final SimpleSymbol Lit109 = ((SimpleSymbol) new SimpleSymbol("lambda").readResolve());
    static final SimpleSymbol Lit11;
    static final SimpleSymbol Lit110 = ((SimpleSymbol) new SimpleSymbol("begin").readResolve());
    static final SimpleSymbol Lit111 = ((SimpleSymbol) new SimpleSymbol("else").readResolve());
    static final SimpleSymbol Lit112 = ((SimpleSymbol) new SimpleSymbol("and").readResolve());
    static final SimpleSymbol Lit113 = ((SimpleSymbol) new SimpleSymbol("or").readResolve());
    static final SimpleSymbol Lit114 = ((SimpleSymbol) new SimpleSymbol("not").readResolve());
    static final SimpleSymbol Lit115 = ((SimpleSymbol) new SimpleSymbol("let").readResolve());
    static final SimpleSymbol Lit116 = ((SimpleSymbol) new SimpleSymbol("call-with-values").readResolve());
    static final SimpleSymbol Lit117 = ((SimpleSymbol) new SimpleSymbol("x").readResolve());
    static final SimpleSymbol Lit118 = ((SimpleSymbol) new SimpleSymbol("kawa.standard.ImportFromLibrary").readResolve());
    static final SimpleSymbol Lit119 = ((SimpleSymbol) new SimpleSymbol("instance").readResolve());
    static final SyntaxRules Lit12;
    static final SimpleSymbol Lit120 = ((SimpleSymbol) new SimpleSymbol("prefix").readResolve());
    static final SimpleSymbol Lit121 = ((SimpleSymbol) new SimpleSymbol("if").readResolve());
    static final SimpleSymbol Lit122 = ((SimpleSymbol) new SimpleSymbol("form").readResolve());
    static final SimpleSymbol Lit123 = ((SimpleSymbol) new SimpleSymbol("%define-macro").readResolve());
    static final SimpleSymbol Lit13;
    static final SyntaxRules Lit14;
    static final SimpleSymbol Lit15;
    static final SyntaxRules Lit16;
    static final SimpleSymbol Lit17;
    static final SyntaxRules Lit18 = new SyntaxRules(new Object[]{Lit17}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\r\u000f\b\b\b", new Object[0], 2), "\u0001\u0003", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\b\r\u000b", new Object[]{Lit121, Lit110}, 1)}, 2);
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("unless").readResolve());
    static final SyntaxTemplate Lit2;
    static final SyntaxRules Lit20;
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("try-finally").readResolve());
    static final SyntaxPattern Lit22 = new SyntaxPattern("\f\u0007\f\u000f\f\u0017\b", new Object[0], 3);
    static final SyntaxTemplate Lit23 = new SyntaxTemplate("\u0001\u0001\u0001", "\u000b", new Object[0], 0);
    static final SyntaxTemplate Lit24 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0013", new Object[0], 0);
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("synchronized").readResolve());
    static final SyntaxPattern Lit26 = new SyntaxPattern("\f\u0007\f\u000f\u0013", new Object[0], 3);
    static final SyntaxTemplate Lit27 = new SyntaxTemplate("\u0001\u0001\u0000", "\u000b", new Object[0], 0);
    static final SyntaxTemplate Lit28 = new SyntaxTemplate("\u0001\u0001\u0000", "\u0012", new Object[0], 0);
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("identifier-list?").readResolve());
    static final SyntaxPattern Lit3 = new SyntaxPattern("\b", new Object[0], 0);
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("identifier-pair-list?").readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol("import-handle-only").readResolve());
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol("import-handle-except").readResolve());
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol("import-handle-prefix").readResolve());
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("import-handle-rename").readResolve());
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("import-mapper").readResolve());
    static final SimpleSymbol Lit36;
    static final SyntaxRules Lit37;
    static final SimpleSymbol Lit38;
    static final SyntaxPattern Lit39 = new SyntaxPattern("\f\u0007,\f\u0002\f\u000f\u0013\f\u001f\b", new Object[]{(SimpleSymbol) new SimpleSymbol("rename").readResolve()}, 4);
    static final SyntaxPattern Lit4 = new SyntaxPattern("\u0003", new Object[0], 1);
    static final SyntaxTemplate Lit40 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxTemplate Lit41 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0011\u0018\u0004\b\u000b", new Object[]{Lit38}, 0);
    static final SyntaxTemplate Lit42 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxTemplate Lit43 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u001b", new Object[0], 0);
    static final SyntaxTemplate Lit44 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0010", new Object[0], 0);
    static final SyntaxTemplate Lit45 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0018\u0004", new Object[]{(SimpleSymbol) new SimpleSymbol("rest").readResolve()}, 0);
    static final SyntaxPattern Lit46 = new SyntaxPattern("\f\u0007,\f\u0002\f\u000f\u0013\f\u001f\b", new Object[]{(SimpleSymbol) new SimpleSymbol("only").readResolve()}, 4);
    static final SyntaxTemplate Lit47 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxTemplate Lit48 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0011\u0018\u0004\b\u000b", new Object[]{Lit38}, 0);
    static final SyntaxTemplate Lit49 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxPattern Lit5 = new SyntaxPattern(",\f\u0007\f\u000f\b\u0013", new Object[0], 3);
    static final SyntaxTemplate Lit50 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u001b", new Object[0], 0);
    static final SyntaxTemplate Lit51 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0010", new Object[0], 0);
    static final SyntaxTemplate Lit52 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxPattern Lit53 = new SyntaxPattern("\f\u0007,\f\u0002\f\u000f\u0013\f\u001f\b", new Object[]{(SimpleSymbol) new SimpleSymbol("except").readResolve()}, 4);
    static final SyntaxTemplate Lit54 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxTemplate Lit55 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0011\u0018\u0004\b\u000b", new Object[]{Lit38}, 0);
    static final SyntaxTemplate Lit56 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxTemplate Lit57 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u001b", new Object[0], 0);
    static final SyntaxTemplate Lit58 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0010", new Object[0], 0);
    static final SyntaxTemplate Lit59 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxTemplate Lit6 = new SyntaxTemplate("\u0001\u0001\u0000", "\u0003", new Object[0], 0);
    static final SyntaxPattern Lit60 = new SyntaxPattern("\f\u0007<\f\u0002\f\u000f\f\u0017\b\f\u001f\b", new Object[]{Lit120}, 4);
    static final SyntaxTemplate Lit61;
    static final SyntaxTemplate Lit62 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0013", new Object[0], 0);
    static final SyntaxTemplate Lit63 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u001b", new Object[0], 0);
    static final SyntaxTemplate Lit64 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0010", new Object[0], 0);
    static final SyntaxPattern Lit65 = new SyntaxPattern("\f\u0007,\f\u0002\f\u000f\u0013\f\u001f\b", new Object[]{Lit120}, 4);
    static final SyntaxTemplate Lit66 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxPattern Lit67 = new SyntaxPattern("\f\u0007,\f\u0002\f\u000f\b\f\u0017\b", new Object[]{(SimpleSymbol) new SimpleSymbol("library").readResolve()}, 3);
    static final SyntaxTemplate Lit68 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit102, Pair.make(Lit118, Pair.make(Pair.make(Lit104, Pair.make(Lit119, LList.Empty)), LList.Empty)), "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 458759)}, 0);
    static final SyntaxTemplate Lit69 = new SyntaxTemplate("\u0001\u0001\u0001", "\b\u000b", new Object[0], 0);
    static final SyntaxTemplate Lit7 = new SyntaxTemplate("\u0001\u0001\u0000", "\u000b", new Object[0], 0);
    static final SyntaxTemplate Lit70 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0013", new Object[0], 0);
    static final SyntaxTemplate Lit71 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0010", new Object[0], 0);
    static final SyntaxPattern Lit72 = new SyntaxPattern("\f\u0007\f\u000f\f\u0017\b", new Object[0], 3);
    static final SyntaxTemplate Lit73 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit102, Pair.make(Lit118, Pair.make(Pair.make(Lit104, Pair.make(Lit119, LList.Empty)), LList.Empty)), "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 466951)}, 0);
    static final SyntaxTemplate Lit74 = new SyntaxTemplate("\u0001\u0001\u0001", "\b\u000b", new Object[0], 0);
    static final SyntaxTemplate Lit75 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0013", new Object[0], 0);
    static final SyntaxTemplate Lit76 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0010", new Object[0], 0);
    static final SimpleSymbol Lit77;
    static final SyntaxRules Lit78 = new SyntaxRules(new Object[]{Lit77}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018,\r\u0007\u0000\b\b\f\u000f\r\u0017\u0010\b\b", new Object[0], 3), "\u0003\u0001\u0003", "\u0011\u0018\u0004\u0011\u0018\f\u0019\b\u0005\u0003\t\u0010\b\u0011\u0018\u0014\t\u000b\b\u0015\u0013", new Object[]{Lit77, "bind", Lit110}, 1), new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0002\f\b\f\u0007\f\u000f\b", new Object[]{"bind"}, 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u000b", new Object[]{Lit115}, 0), new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0002\\,\f\u0007\f\u000f\b\r\u0017\u0010\b\b\f\u001f\f'\b", new Object[]{"bind"}, 5), "\u0001\u0001\u0003\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f\t\u0003\t\u000b\t\u0010\u0019\b\u0015\u0013\t\u001b\b#", new Object[]{Lit77, "mktmp"}, 1), new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0002\f\b\f\u0007\f\u000f\f\u0017\f\u001f\f'\b", new Object[]{"mktmp"}, 5), "\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u00049\u0011\u0018\f\t\u0010\b\u0003\b\u0011\u0018\f\t\u000b\b\u0011\u0018\u0014\u0011\u0018\u001c\t\u0013\t\u001b\b#", new Object[]{Lit116, Lit109, Lit77, "bind"}, 0), new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0002\u001c\f\u0007\u000b\f\u0017,\r\u001f\u0018\b\b\f',\r/(\b\b\f7\b", new Object[]{"mktmp"}, 7), "\u0001\u0000\u0001\u0003\u0001\u0003\u0001", "\u0011\u0018\u0004\u0011\u0018\f\t\n\t\u0013)\u0011\u001d\u001b\u0018\u0014\t#A\u0011-+\b\t\u0003\u0018\u001c\b3", new Object[]{Lit77, "mktmp", PairWithPosition.make(Lit117, LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 569387), PairWithPosition.make(Lit117, LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 569414)}, 1), new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0002\f\u0007\f\u000f,\r\u0017\u0010\b\b\f\u001f,\r' \b\b\f/\b", new Object[]{"mktmp"}, 6), "\u0001\u0001\u0003\u0001\u0003\u0001", "\u0011\u0018\u00049\u0011\u0018\f\t\u0010\b\u000b\b\u0011\u0018\f)\u0011\u0015\u0013\u0018\u0014\b\u0011\u0018\u001c\u0011\u0018$\t\u001bA\u0011%#\b\t\u0003\u0018,\b+", new Object[]{Lit116, Lit109, Lit117, Lit77, "bind", PairWithPosition.make(Lit117, LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 593973)}, 1)}, 7);
    static final SimpleSymbol Lit79;
    static final SyntaxTemplate Lit8 = new SyntaxTemplate("\u0001\u0001\u0000", "\u0012", new Object[0], 0);
    static final SyntaxRules Lit80;
    static final SimpleSymbol Lit81 = ((SimpleSymbol) new SimpleSymbol("case-lambda").readResolve());
    static final SyntaxPattern Lit82 = new SyntaxPattern("\f\u0007\u000b", new Object[0], 2);
    static final SyntaxTemplate Lit83 = new SyntaxTemplate("\u0001\u0000", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit102, Pair.make((SimpleSymbol) new SimpleSymbol("gnu.expr.GenericProc").readResolve(), Pair.make(Pair.make(Lit104, Pair.make((SimpleSymbol) new SimpleSymbol("makeWithoutSorting").readResolve(), LList.Empty)), LList.Empty)), "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 651273)}, 0);
    static final SyntaxTemplate Lit84 = new SyntaxTemplate("\u0001\u0000", "\n", new Object[0], 0);
    static final SyntaxPattern Lit85 = new SyntaxPattern("\u001c\f\u0017\u001b#", new Object[0], 5);
    static final SyntaxTemplate Lit86 = new SyntaxTemplate("\u0001\u0000\u0001\u0000\u0000", "\u0011\u0018\u0004\t\u0013\u001a", new Object[]{Lit109}, 0);
    static final SyntaxTemplate Lit87 = new SyntaxTemplate("\u0001\u0000\u0001\u0000\u0000", "\"", new Object[0], 0);
    static final SyntaxPattern Lit88 = new SyntaxPattern("\b", new Object[0], 2);
    static final SyntaxPattern Lit89 = new SyntaxPattern("\u0013", new Object[0], 3);
    static final SyntaxPattern Lit9 = new SyntaxPattern("\b", new Object[0], 0);
    static final SyntaxTemplate Lit90 = new SyntaxTemplate("\u0001\u0000\u0000", "\u0012", new Object[0], 0);
    static final SimpleSymbol Lit91;
    static final SyntaxRules Lit92 = new SyntaxRules(new Object[]{Lit91, Lit112, Lit113, Lit114, Lit111}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\b", new Object[0], 0), "", "\u0018\u0004", new Object[]{PairWithPosition.make((SimpleSymbol) new SimpleSymbol("%syntax-error").readResolve(), PairWithPosition.make("Unfulfilled cond-expand", LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 802851), "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 802836)}, 0), new SyntaxRule(new SyntaxPattern("\f\u0018<\f\u0002\r\u0007\u0000\b\b\b", new Object[]{Lit111}, 1), "\u0003", "\u0011\u0018\u0004\b\u0005\u0003", new Object[]{Lit110}, 1), new SyntaxRule(new SyntaxPattern("\f\u0018L\u001c\f\u0002\b\r\u0007\u0000\b\b\r\u000f\b\b\b", new Object[]{Lit112}, 2), "\u0003\u0003", "\u0011\u0018\u0004\b\u0005\u0003", new Object[]{Lit110}, 1), new SyntaxRule(new SyntaxPattern("\f\u0018|L\f\u0002\f\u0007\r\u000f\b\b\b\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{Lit112}, 4), "\u0001\u0003\u0003\u0003", "\u0011\u0018\u0004¡\t\u0003\b\u0011\u0018\u0004Q1\u0011\u0018\f\b\r\u000b\b\u0015\u0013\b\u001d\u001b\b\u001d\u001b", new Object[]{Lit91, Lit112}, 1), new SyntaxRule(new SyntaxPattern("\f\u0018L\u001c\f\u0002\b\r\u0007\u0000\b\b\r\u000f\b\b\b", new Object[]{Lit113}, 2), "\u0003\u0003", "\u0011\u0018\u0004\b\r\u000b", new Object[]{Lit91}, 1), new SyntaxRule(new SyntaxPattern("\f\u0018|L\f\u0002\f\u0007\r\u000f\b\b\b\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{Lit113}, 4), "\u0001\u0003\u0003\u0003", "\u0011\u0018\u0004I\t\u0003\b\u0011\u0018\f\b\u0015\u0013\b\u0011\u0018\u0014\b\u0011\u0018\u0004Q1\u0011\u0018\u001c\b\r\u000b\b\u0015\u0013\b\u001d\u001b", new Object[]{Lit91, Lit110, Lit111, Lit113}, 1), new SyntaxRule(new SyntaxPattern("\f\u0018\\,\f\u0002\f\u0007\b\r\u000f\b\b\b\r\u0017\u0010\b\b", new Object[]{Lit114}, 3), "\u0001\u0003\u0003", "\u0011\u0018\u0004I\t\u0003\b\u0011\u0018\u0004\b\u0015\u0013\b\u0011\u0018\f\b\r\u000b", new Object[]{Lit91, Lit111}, 1), new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\u000b\u0013", new Object[0], 3), "\u0001\u0000\u0000", "\u0011\u0018\u0004\u0019\t\u0003\n\u0012", new Object[]{Lit93}, 0)}, 4);
    static final SimpleSymbol Lit93 = ((SimpleSymbol) new SimpleSymbol("%cond-expand").readResolve());
    static final SyntaxPattern Lit94 = new SyntaxPattern("\f\u0007\u001c\f\u000f\u0013\u001b", new Object[0], 4);
    static final SyntaxTemplate Lit95 = new SyntaxTemplate("\u0001\u0001\u0000\u0000", "\u000b", new Object[0], 0);
    static final SyntaxTemplate Lit96 = new SyntaxTemplate("\u0001\u0001\u0000\u0000", "\u0011\u0018\u0004\u0012", new Object[]{Lit110}, 0);
    static final SyntaxTemplate Lit97;
    static final SimpleSymbol Lit98;
    static final SyntaxRules Lit99;
    public static final Macro case$Mnlambda;
    public static final Macro cond$Mnexpand = Macro.make(Lit91, Lit92, $instance);
    public static final Macro define$Mnalias$Mnparameter = Macro.make(Lit100, Lit101, $instance);
    public static final Macro define$Mnmacro = Macro.make(Lit13, Lit14, $instance);
    public static final Macro define$Mnsyntax$Mncase = Macro.make(Lit15, Lit16, $instance);
    public static final Macro defmacro = Macro.make(Lit11, Lit12, $instance);
    public static final ModuleMethod identifier$Mnlist$Qu;
    public static final ModuleMethod identifier$Mnpair$Mnlist$Qu;

    /* renamed from: import  reason: not valid java name */
    public static final Macro f4import = Macro.make(Lit36, Lit37, $instance);
    public static final ModuleMethod import$Mnhandle$Mnexcept;
    public static final ModuleMethod import$Mnhandle$Mnonly;
    public static final ModuleMethod import$Mnhandle$Mnprefix;
    public static final ModuleMethod import$Mnhandle$Mnrename;
    public static final ModuleMethod import$Mnmapper;
    public static final Macro let$Mnvalues = Macro.make(Lit77, Lit78, $instance);
    public static final Macro let$St$Mnvalues = Macro.make(Lit79, Lit80, $instance);
    public static final Macro receive = Macro.make(Lit98, Lit99, $instance);

    /* renamed from: synchronized  reason: not valid java name */
    public static final Macro f5synchronized;
    public static final Macro try$Mnfinally;
    public static final Macro unless = Macro.make(Lit19, Lit20, $instance);
    public static final Macro when = Macro.make(Lit17, Lit18, $instance);

    static {
        SyntaxRule syntaxRule;
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("define-alias-parameter").readResolve();
        Lit100 = simpleSymbol;
        SyntaxPattern syntaxPattern = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3);
        PairWithPosition make = PairWithPosition.make(Lit102, Pair.make(Lit103, Pair.make(Pair.make(Lit104, Pair.make((SimpleSymbol) new SimpleSymbol("makeNamed").readResolve(), LList.Empty)), LList.Empty)), "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1069060);
        PairWithPosition make2 = PairWithPosition.make(Lit102, Pair.make(Lit103, Pair.make(Pair.make(Lit104, Pair.make((SimpleSymbol) new SimpleSymbol("pushConverter").readResolve(), LList.Empty)), LList.Empty)), "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1073161);
        PairWithPosition make3 = PairWithPosition.make(Lit105, LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1081354);
        PairWithPosition make4 = PairWithPosition.make(Lit105, LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1089550);
        Object[] objArr = {simpleSymbol};
        PairWithPosition make5 = PairWithPosition.make(Lit102, Pair.make((SimpleSymbol) new SimpleSymbol("gnu.mapping.WrongType").readResolve(), Pair.make(Pair.make(Lit104, Pair.make((SimpleSymbol) new SimpleSymbol("make").readResolve(), LList.Empty)), LList.Empty)), "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1097748);
        PairWithPosition make6 = PairWithPosition.make(PairWithPosition.make(Lit106, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("<int>").readResolve(), PairWithPosition.make(IntNum.make(1), LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1101846), "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1101840), "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1101836), PairWithPosition.make(Lit105, LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1101849), "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1101836);
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("set!").readResolve();
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("<java.lang.ClassCastException>").readResolve();
        new SyntaxRule(syntaxPattern, "\u0001\u0001\u0001", "\u0011\u0018\u0004¹\u0011\u0018\f\t\u0003\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$)\u0011\u0018,\b\u0003\b\u0013\b\u0011\u00184\t\u0003\b\u0011\u0018<\u0011\u0018D\b\u0011\u0018L9\u0011\u0018T\t\u000b\u0018\\\b\u0011\u0018d\u0011\u0018l\b\u0011\u0018ty\b\u0011\u0018|\b\u0011\u0018\u0011\u0018d\t\u0003\u0018A\u0011\u0018\u0011\u0018\b\u000b\u0018¤", new Object[]{Lit110, (SimpleSymbol) new SimpleSymbol("define-constant").readResolve(), (SimpleSymbol) new SimpleSymbol("::").readResolve(), (SimpleSymbol) new SimpleSymbol("<gnu.mapping.LocationProc>").readResolve(), make, Lit108, make2, Lit109, make3, (SimpleSymbol) new SimpleSymbol("try-catch").readResolve(), Lit106, make4, (SimpleSymbol) new SimpleSymbol("ex").readResolve(), simpleSymbol3, Lit115, Lit107, make5, make6, simpleSymbol2, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("field").readResolve(), PairWithPosition.make(Lit107, PairWithPosition.make(PairWithPosition.make(Lit108, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("expectedType").readResolve(), LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1105941), "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1105941), LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1105940), "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1105937), "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1105930), PairWithPosition.make(PairWithPosition.make((SimpleSymbol) new SimpleSymbol("primitive-throw").readResolve(), PairWithPosition.make(Lit107, LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1110037), "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1110020), LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 1110020)}, 0);
        Lit101 = new SyntaxRules(objArr, new SyntaxRule[]{syntaxRule}, 3);
        SimpleSymbol simpleSymbol4 = (SimpleSymbol) new SimpleSymbol("receive").readResolve();
        Lit98 = simpleSymbol4;
        Lit99 = new SyntaxRules(new Object[]{simpleSymbol4}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\r\u0017\u0010\b\b", new Object[0], 3), "\u0001\u0001\u0003", "\u0011\u0018\u00049\u0011\u0018\f\t\u0010\b\u000b\b\u0011\u0018\f\t\u0003\b\u0015\u0013", new Object[]{Lit116, Lit109}, 1)}, 3);
        SimpleSymbol simpleSymbol5 = (SimpleSymbol) new SimpleSymbol("cond-expand").readResolve();
        Lit91 = simpleSymbol5;
        Lit97 = new SyntaxTemplate("\u0001\u0001\u0000\u0000", "\u0011\u0018\u0004\u001a", new Object[]{simpleSymbol5}, 0);
        SimpleSymbol simpleSymbol6 = (SimpleSymbol) new SimpleSymbol("let*-values").readResolve();
        Lit79 = simpleSymbol6;
        SyntaxPattern syntaxPattern2 = new SyntaxPattern("\f\u0018\f\b\f\u0007\r\u000f\b\b\b", new Object[0], 2);
        Object[] objArr2 = {Lit110};
        SyntaxPattern syntaxPattern3 = new SyntaxPattern("\f\u0018<\f\u0007\r\u000f\b\b\b\f\u0017\r\u001f\u0018\b\b", new Object[0], 4);
        SimpleSymbol simpleSymbol7 = (SimpleSymbol) new SimpleSymbol("let-values").readResolve();
        Lit77 = simpleSymbol7;
        Lit80 = new SyntaxRules(new Object[]{simpleSymbol6}, new SyntaxRule[]{new SyntaxRule(syntaxPattern2, "\u0001\u0003", "\u0011\u0018\u0004\t\u0003\b\r\u000b", objArr2, 1), new SyntaxRule(syntaxPattern3, "\u0001\u0003\u0001\u0003", "\u0011\u0018\u0004\u0011\b\u0003\b\u0011\u0018\f\u0019\b\r\u000b\t\u0013\b\u001d\u001b", new Object[]{simpleSymbol7, Lit79}, 1)}, 4);
        SimpleSymbol simpleSymbol8 = (SimpleSymbol) new SimpleSymbol("%import").readResolve();
        Lit38 = simpleSymbol8;
        Lit61 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\b\u000b", new Object[]{simpleSymbol8}, 0);
        SimpleSymbol simpleSymbol9 = (SimpleSymbol) new SimpleSymbol("import").readResolve();
        Lit36 = simpleSymbol9;
        Lit37 = new SyntaxRules(new Object[]{simpleSymbol9}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1), "\u0003", "\u0011\u0018\u0004\b\u0005\u0011\u0018\f\t\u0003\u0018\u0014", new Object[]{Lit110, Lit38, PairWithPosition.make(LList.Empty, LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 376866)}, 1)}, 1);
        SimpleSymbol simpleSymbol10 = (SimpleSymbol) new SimpleSymbol("when").readResolve();
        Lit17 = simpleSymbol10;
        Lit20 = new SyntaxRules(new Object[]{simpleSymbol10}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\r\u000f\b\b\b", new Object[0], 2), "\u0001\u0003", "\u0011\u0018\u0004)\u0011\u0018\f\b\u0003\b\u0011\u0018\u0014\b\r\u000b", new Object[]{Lit121, Lit114, Lit110}, 1)}, 2);
        SimpleSymbol simpleSymbol11 = (SimpleSymbol) new SimpleSymbol("define-syntax-case").readResolve();
        Lit15 = simpleSymbol11;
        Lit16 = new SyntaxRules(new Object[]{simpleSymbol11}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\u0013", new Object[0], 3), "\u0001\u0001\u0000", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\u0011\u0018\u0014\b\u0011\u0018\u001c\u0011\u0018$\t\u000b\u0012", new Object[]{(SimpleSymbol) new SimpleSymbol("define-syntax").readResolve(), Lit109, PairWithPosition.make(Lit122, LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm", 90129), (SimpleSymbol) new SimpleSymbol("syntax-case").readResolve(), Lit122}, 0)}, 3);
        SimpleSymbol simpleSymbol12 = (SimpleSymbol) new SimpleSymbol("define-macro").readResolve();
        Lit13 = simpleSymbol12;
        Lit14 = new SyntaxRules(new Object[]{simpleSymbol12}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\u000b\u0013", new Object[0], 3), "\u0001\u0000\u0000", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\t\n\u0012", new Object[]{Lit123, Lit109}, 0), new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u000b", new Object[]{Lit123}, 0)}, 3);
        SimpleSymbol simpleSymbol13 = (SimpleSymbol) new SimpleSymbol("defmacro").readResolve();
        Lit11 = simpleSymbol13;
        Lit12 = new SyntaxRules(new Object[]{simpleSymbol13}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\u0013", new Object[0], 3), "\u0001\u0001\u0000", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\t\u000b\u0012", new Object[]{Lit123, Lit109}, 0)}, 3);
        String str = "\u0001\u0000";
        Lit2 = new SyntaxTemplate(str, "\n", new Object[0], 0);
        Lit1 = new SyntaxTemplate(str, "\u0003", new Object[0], 0);
        SimpleSymbol simpleSymbol14 = Lit21;
        syntax syntax = $instance;
        try$Mnfinally = Macro.make(simpleSymbol14, new ModuleMethod(syntax, 2, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN), $instance);
        f5synchronized = Macro.make(Lit25, new ModuleMethod(syntax, 3, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN), $instance);
        identifier$Mnlist$Qu = new ModuleMethod(syntax, 4, Lit29, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        identifier$Mnpair$Mnlist$Qu = new ModuleMethod(syntax, 5, Lit30, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        import$Mnhandle$Mnonly = new ModuleMethod(syntax, 6, Lit31, 8194);
        import$Mnhandle$Mnexcept = new ModuleMethod(syntax, 7, Lit32, 8194);
        import$Mnhandle$Mnprefix = new ModuleMethod(syntax, 8, Lit33, 8194);
        import$Mnhandle$Mnrename = new ModuleMethod(syntax, 9, Lit34, 8194);
        import$Mnmapper = new ModuleMethod(syntax, 10, Lit35, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Pcimport = Macro.make(Lit38, new ModuleMethod(syntax, 11, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN), $instance);
        case$Mnlambda = Macro.make(Lit81, new ModuleMethod(syntax, 12, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN), $instance);
        SimpleSymbol simpleSymbol15 = Lit93;
        ModuleMethod moduleMethod = new ModuleMethod(syntax, 13, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm:227");
        $Pccond$Mnexpand = Macro.make(simpleSymbol15, moduleMethod, $instance);
        $instance.run();
    }

    public syntax() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    static Object lambda2(Object x) {
        Object[] allocVars = SyntaxPattern.allocVars(3, (Object[]) null);
        if (!Lit22.match(x, allocVars, 0)) {
            return syntax_case.error("syntax-case", x);
        }
        return new TryExp(SyntaxForms.rewrite(Lit23.execute(allocVars, TemplateScope.make())), SyntaxForms.rewrite(Lit24.execute(allocVars, TemplateScope.make())));
    }

    static Object lambda3(Object x) {
        Object[] allocVars = SyntaxPattern.allocVars(3, (Object[]) null);
        if (!Lit26.match(x, allocVars, 0)) {
            return syntax_case.error("syntax-case", x);
        }
        return new SynchronizedExp(SyntaxForms.rewrite(Lit27.execute(allocVars, TemplateScope.make())), SyntaxForms.rewriteBody(Lit28.execute(allocVars, TemplateScope.make())));
    }

    public static boolean isIdentifierList(Object obj) {
        boolean x = true;
        boolean x2 = Translator.listLength(obj) >= 0;
        if (!x2) {
            return x2;
        }
        Object obj2 = obj;
        while (true) {
            Object[] allocVars = SyntaxPattern.allocVars(2, (Object[]) null);
            if (Lit0.match(obj2, allocVars, 0)) {
                boolean x3 = std_syntax.isIdentifier(Lit1.execute(allocVars, TemplateScope.make()));
                if (!x3) {
                    x = x3;
                    break;
                }
                obj2 = Lit2.execute(allocVars, TemplateScope.make());
            } else if (!Lit3.match(obj2, allocVars, 0) && (Lit4.match(obj2, allocVars, 0) || syntax_case.error("syntax-case", obj2) == Boolean.FALSE)) {
                x = false;
            }
        }
        return x;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 2:
            case 3:
            case 11:
            case 12:
            case 13:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 4:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 5:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 10:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x004a, code lost:
        r1 = r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isIdentifierPairList(java.lang.Object r4) {
        /*
            int r0 = kawa.lang.Translator.listLength(r4)
            r1 = 1
            r2 = 0
            if (r0 < 0) goto L_0x000b
            r0 = 1
            goto L_0x000c
        L_0x000b:
            r0 = 0
        L_0x000c:
            if (r0 == 0) goto L_0x006b
        L_0x0011:
            r0 = 3
            r3 = 0
            java.lang.Object[] r0 = kawa.lang.SyntaxPattern.allocVars(r0, r3)
            kawa.lang.SyntaxPattern r3 = Lit5
            boolean r3 = r3.match(r4, r0, r2)
            if (r3 == 0) goto L_0x004c
            kawa.lang.TemplateScope r4 = kawa.lang.TemplateScope.make()
            kawa.lang.SyntaxTemplate r3 = Lit6
            java.lang.Object r4 = r3.execute(r0, r4)
            boolean r4 = kawa.lib.std_syntax.isIdentifier(r4)
            if (r4 == 0) goto L_0x004a
            kawa.lang.TemplateScope r4 = kawa.lang.TemplateScope.make()
            kawa.lang.SyntaxTemplate r3 = Lit7
            java.lang.Object r4 = r3.execute(r0, r4)
            boolean r4 = kawa.lib.std_syntax.isIdentifier(r4)
            if (r4 == 0) goto L_0x004a
            kawa.lang.TemplateScope r4 = kawa.lang.TemplateScope.make()
            kawa.lang.SyntaxTemplate r3 = Lit8
            java.lang.Object r4 = r3.execute(r0, r4)
            goto L_0x0011
        L_0x004a:
            r1 = r4
            goto L_0x0054
        L_0x004c:
            kawa.lang.SyntaxPattern r3 = Lit9
            boolean r3 = r3.match(r4, r0, r2)
            if (r3 == 0) goto L_0x0055
        L_0x0054:
            goto L_0x006a
        L_0x0055:
            kawa.lang.SyntaxPattern r3 = Lit10
            boolean r0 = r3.match(r4, r0, r2)
            if (r0 == 0) goto L_0x005f
        L_0x005d:
            r1 = 0
            goto L_0x006a
        L_0x005f:
            java.lang.String r0 = "syntax-case"
            java.lang.Object r4 = kawa.standard.syntax_case.error(r0, r4)
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            if (r4 == r0) goto L_0x005d
            goto L_0x0054
        L_0x006a:
            r0 = r1
        L_0x006b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lib.syntax.isIdentifierPairList(java.lang.Object):boolean");
    }

    public static Object importHandleOnly(Object name, Object list) {
        if (lists.memq(name, list) != Boolean.FALSE) {
            return name;
        }
        return null;
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 6:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 7:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 8:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 9:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static Object importHandleExcept(Object name, Object list) {
        if (lists.memq(name, list) != Boolean.FALSE) {
            return null;
        }
        return name;
    }

    public static Object importHandlePrefix(Object name, Object prefix) {
        return null;
    }

    public static Object importHandleRename(Object name, Object rename$Mnpairs) {
        if (!lists.isPair(rename$Mnpairs)) {
            return name;
        }
        if (name == lists.caar.apply1(rename$Mnpairs)) {
            return lists.cadar.apply1(rename$Mnpairs);
        }
        return importHandleRename(name, lists.cdr.apply1(rename$Mnpairs));
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 6:
                return importHandleOnly(obj, obj2);
            case 7:
                return importHandleExcept(obj, obj2);
            case 8:
                return importHandlePrefix(obj, obj2);
            case 9:
                return importHandleRename(obj, obj2);
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    /* compiled from: syntax.scm */
    public class frame extends ModuleBody {
        final ModuleMethod lambda$Fn1;
        Object list;

        public frame() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 1, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/kawa/lib/syntax.scm:83");
            this.lambda$Fn1 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 1 ? lambda1(obj) : super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: package-private */
        public Object lambda1(Object name) {
            Object l = this.list;
            Object n = name;
            while (true) {
                boolean x = n == null;
                if (x) {
                    if (x) {
                        break;
                    }
                    Object l2 = lists.cdr.apply1(l);
                    n = Scheme.applyToArgs.apply3(lists.caar.apply1(l), n, lists.cdar.apply1(l));
                    l = l2;
                } else if (lists.isNull(l)) {
                    break;
                } else {
                    Object l22 = lists.cdr.apply1(l);
                    n = Scheme.applyToArgs.apply3(lists.caar.apply1(l), n, lists.cdar.apply1(l));
                    l = l22;
                }
            }
            return n;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 1) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    public static Procedure importMapper(Object list) {
        frame frame2 = new frame();
        frame2.list = list;
        return frame2.lambda$Fn1;
    }

    static Object lambda4(Object form) {
        Object[] objArr;
        Object[] objArr2;
        Object[] objArr3;
        Object[] objArr4;
        Object[] allocVars = SyntaxPattern.allocVars(4, (Object[]) null);
        if (Lit39.match(form, allocVars, 0)) {
            if (isIdentifierPairList(Lit40.execute(allocVars, TemplateScope.make()))) {
                TemplateScope make = TemplateScope.make();
                return Quote.append$V(new Object[]{Lit41.execute(allocVars, make), Quote.consX$V(new Object[]{lists.cons(lists.cons(import$Mnhandle$Mnrename, Lit42.execute(allocVars, make)), Lit43.execute(allocVars, make)), Lit44.execute(allocVars, make)})});
            }
            Object execute = Lit45.execute(allocVars, TemplateScope.make());
            if (!("invalid 'rename' clause in import" instanceof Object[])) {
                objArr4 = new Object[]{"invalid 'rename' clause in import"};
            }
            return prim_syntax.syntaxError(execute, objArr4);
        } else if (Lit46.match(form, allocVars, 0)) {
            if (isIdentifierList(Lit47.execute(allocVars, TemplateScope.make()))) {
                TemplateScope make2 = TemplateScope.make();
                return Quote.append$V(new Object[]{Lit48.execute(allocVars, make2), Quote.consX$V(new Object[]{lists.cons(lists.cons(import$Mnhandle$Mnonly, Lit49.execute(allocVars, make2)), Lit50.execute(allocVars, make2)), Lit51.execute(allocVars, make2)})});
            }
            Object execute2 = Lit52.execute(allocVars, TemplateScope.make());
            if (!("invalid 'only' identifier list" instanceof Object[])) {
                objArr3 = new Object[]{"invalid 'only' identifier list"};
            }
            return prim_syntax.syntaxError(execute2, objArr3);
        } else if (Lit53.match(form, allocVars, 0)) {
            if (isIdentifierList(Lit54.execute(allocVars, TemplateScope.make()))) {
                TemplateScope make3 = TemplateScope.make();
                return Quote.append$V(new Object[]{Lit55.execute(allocVars, make3), Quote.consX$V(new Object[]{lists.cons(lists.cons(import$Mnhandle$Mnexcept, Lit56.execute(allocVars, make3)), Lit57.execute(allocVars, make3)), Lit58.execute(allocVars, make3)})});
            }
            Object execute3 = Lit59.execute(allocVars, TemplateScope.make());
            if (!("invalid 'except' identifier list" instanceof Object[])) {
                objArr2 = new Object[]{"invalid 'except' identifier list"};
            }
            return prim_syntax.syntaxError(execute3, objArr2);
        } else if (Lit60.match(form, allocVars, 0)) {
            TemplateScope make4 = TemplateScope.make();
            return Quote.append$V(new Object[]{Lit61.execute(allocVars, make4), Quote.consX$V(new Object[]{lists.cons(lists.cons(import$Mnhandle$Mnprefix, Lit62.execute(allocVars, make4)), Lit63.execute(allocVars, make4)), Lit64.execute(allocVars, make4)})});
        } else if (Lit65.match(form, allocVars, 0)) {
            Object execute4 = Lit66.execute(allocVars, TemplateScope.make());
            if (!("invalid prefix clause in import" instanceof Object[])) {
                objArr = new Object[]{"invalid prefix clause in import"};
            }
            return prim_syntax.syntaxError(execute4, objArr);
        } else if (Lit67.match(form, allocVars, 0)) {
            TemplateScope make5 = TemplateScope.make();
            return Pair.make(Lit68.execute(allocVars, make5), Quote.append$V(new Object[]{Lit69.execute(allocVars, make5), Quote.consX$V(new Object[]{importMapper(std_syntax.syntaxObject$To$Datum(Lit70.execute(allocVars, make5))), Lit71.execute(allocVars, make5)})}));
        } else if (!Lit72.match(form, allocVars, 0)) {
            return syntax_case.error("syntax-case", form);
        } else {
            TemplateScope make6 = TemplateScope.make();
            return Pair.make(Lit73.execute(allocVars, make6), Quote.append$V(new Object[]{Lit74.execute(allocVars, make6), Quote.consX$V(new Object[]{importMapper(std_syntax.syntaxObject$To$Datum(Lit75.execute(allocVars, make6))), Lit76.execute(allocVars, make6)})}));
        }
    }

    static Object lambda5(Object form) {
        frame0 frame02 = new frame0();
        frame02.$unnamed$1 = SyntaxPattern.allocVars(2, (Object[]) null);
        if (!Lit82.match(form, frame02.$unnamed$1, 0)) {
            return syntax_case.error("syntax-case", form);
        }
        frame02.$unnamed$0 = TemplateScope.make();
        return Pair.make(Lit83.execute(frame02.$unnamed$1, frame02.$unnamed$0), frame02.lambda6loop(Lit84.execute(frame02.$unnamed$1, frame02.$unnamed$0)));
    }

    /* compiled from: syntax.scm */
    public class frame0 extends ModuleBody {
        TemplateScope $unnamed$0;
        Object[] $unnamed$1;

        public Object lambda6loop(Object clauses) {
            Object[] objArr;
            Object[] allocVars = SyntaxPattern.allocVars(5, this.$unnamed$1);
            if (syntax.Lit85.match(clauses, allocVars, 0)) {
                return Pair.make(syntax.Lit86.execute(allocVars, this.$unnamed$0), lambda6loop(syntax.Lit87.execute(allocVars, this.$unnamed$0)));
            }
            if (syntax.Lit88.match(clauses, allocVars, 0)) {
                return LList.Empty;
            }
            if (!syntax.Lit89.match(clauses, allocVars, 0)) {
                return syntax_case.error("syntax-case", clauses);
            }
            Object execute = syntax.Lit90.execute(allocVars, this.$unnamed$0);
            if (!("invalid case-lambda clause" instanceof Object[])) {
                objArr = new Object[]{"invalid case-lambda clause"};
            }
            return LList.list1(prim_syntax.syntaxError(execute, objArr));
        }
    }

    static Object lambda7(Object x) {
        Object[] allocVars = SyntaxPattern.allocVars(4, (Object[]) null);
        if (!Lit94.match(x, allocVars, 0)) {
            return syntax_case.error("syntax-case", x);
        }
        if (IfFeature.testFeature(Lit95.execute(allocVars, TemplateScope.make()))) {
            return Lit96.execute(allocVars, TemplateScope.make());
        }
        return Lit97.execute(allocVars, TemplateScope.make());
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 2:
                return lambda2(obj);
            case 3:
                return lambda3(obj);
            case 4:
                return isIdentifierList(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 5:
                return isIdentifierPairList(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 10:
                return importMapper(obj);
            case 11:
                return lambda4(obj);
            case 12:
                return lambda5(obj);
            case 13:
                return lambda7(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }
}
