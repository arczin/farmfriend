package gnu.kawa.slib;

import androidx.fragment.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.lib.characters;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.ports;
import kawa.lib.rnrs.unicode;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.append;

/* compiled from: pregexp.scm */
public class pregexp extends ModuleBody {
    public static Char $Stpregexp$Mncomment$Mnchar$St;
    public static Object $Stpregexp$Mnnul$Mnchar$Mnint$St;
    public static Object $Stpregexp$Mnreturn$Mnchar$St;
    public static Object $Stpregexp$Mnspace$Mnsensitive$Qu$St;
    public static Object $Stpregexp$Mntab$Mnchar$St;
    public static IntNum $Stpregexp$Mnversion$St;
    public static final pregexp $instance = new pregexp();
    static final IntNum Lit0 = IntNum.make(20050502);
    static final Char Lit1 = Char.make(59);
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol(":bos").readResolve());
    static final SimpleSymbol Lit100 = ((SimpleSymbol) new SimpleSymbol(":sub").readResolve());
    static final SimpleSymbol Lit101 = ((SimpleSymbol) new SimpleSymbol("pregexp-match-positions-aux").readResolve());
    static final SimpleSymbol Lit102 = ((SimpleSymbol) new SimpleSymbol("non-existent-backref").readResolve());
    static final SimpleSymbol Lit103 = ((SimpleSymbol) new SimpleSymbol(":lookahead").readResolve());
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol(":neg-lookahead").readResolve());
    static final SimpleSymbol Lit105 = ((SimpleSymbol) new SimpleSymbol(":lookbehind").readResolve());
    static final PairWithPosition Lit106 = PairWithPosition.make(Lit68, PairWithPosition.make(Boolean.FALSE, PairWithPosition.make(Lit73, PairWithPosition.make(Boolean.FALSE, PairWithPosition.make(Lit14, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2302017), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2302014), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2302012), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2302009), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2301999);
    static final SimpleSymbol Lit107 = ((SimpleSymbol) new SimpleSymbol(":neg-lookbehind").readResolve());
    static final PairWithPosition Lit108;
    static final SimpleSymbol Lit109 = ((SimpleSymbol) new SimpleSymbol(":no-backtrack").readResolve());
    static final Char Lit11;
    static final SimpleSymbol Lit110 = ((SimpleSymbol) new SimpleSymbol("greedy-quantifier-operand-could-be-empty").readResolve());
    static final SimpleSymbol Lit111 = ((SimpleSymbol) new SimpleSymbol("fk").readResolve());
    static final SimpleSymbol Lit112 = ((SimpleSymbol) new SimpleSymbol("identity").readResolve());
    static final Char Lit113 = Char.make(38);
    static final SimpleSymbol Lit114 = ((SimpleSymbol) new SimpleSymbol("pregexp-match-positions").readResolve());
    static final SimpleSymbol Lit115 = ((SimpleSymbol) new SimpleSymbol("pattern-must-be-compiled-or-string-regexp").readResolve());
    static final PairWithPosition Lit116;
    static final SimpleSymbol Lit117 = ((SimpleSymbol) new SimpleSymbol("pregexp-reverse!").readResolve());
    static final SimpleSymbol Lit118 = ((SimpleSymbol) new SimpleSymbol("pregexp-error").readResolve());
    static final SimpleSymbol Lit119 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-pattern").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol(":eos").readResolve());
    static final SimpleSymbol Lit120 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-branch").readResolve());
    static final SimpleSymbol Lit121 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-escaped-number").readResolve());
    static final SimpleSymbol Lit122 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-escaped-char").readResolve());
    static final SimpleSymbol Lit123 = ((SimpleSymbol) new SimpleSymbol("pregexp-invert-char-list").readResolve());
    static final SimpleSymbol Lit124 = ((SimpleSymbol) new SimpleSymbol("pregexp-string-match").readResolve());
    static final SimpleSymbol Lit125 = ((SimpleSymbol) new SimpleSymbol("pregexp-char-word?").readResolve());
    static final SimpleSymbol Lit126 = ((SimpleSymbol) new SimpleSymbol("pregexp-at-word-boundary?").readResolve());
    static final SimpleSymbol Lit127 = ((SimpleSymbol) new SimpleSymbol("pregexp-list-ref").readResolve());
    static final SimpleSymbol Lit128 = ((SimpleSymbol) new SimpleSymbol("pregexp-make-backref-list").readResolve());
    static final SimpleSymbol Lit129 = ((SimpleSymbol) new SimpleSymbol("pregexp-replace-aux").readResolve());
    static final Char Lit13;
    static final SimpleSymbol Lit130 = ((SimpleSymbol) new SimpleSymbol("pregexp").readResolve());
    static final SimpleSymbol Lit131 = ((SimpleSymbol) new SimpleSymbol("pregexp-match").readResolve());
    static final SimpleSymbol Lit132 = ((SimpleSymbol) new SimpleSymbol("pregexp-split").readResolve());
    static final SimpleSymbol Lit133 = ((SimpleSymbol) new SimpleSymbol("pregexp-replace").readResolve());
    static final SimpleSymbol Lit134 = ((SimpleSymbol) new SimpleSymbol("pregexp-replace*").readResolve());
    static final SimpleSymbol Lit135 = ((SimpleSymbol) new SimpleSymbol("pregexp-quote").readResolve());
    static final SimpleSymbol Lit14;
    static final Char Lit15;
    static final IntNum Lit16 = IntNum.make(2);
    static final SimpleSymbol Lit17;
    static final Char Lit18;
    static final Char Lit19;
    static final Char Lit2 = Char.make(97);
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol(":backref").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-piece").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("backslash").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol(":empty").readResolve());
    static final Char Lit24 = Char.make(10);
    static final Char Lit25 = Char.make(98);
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol(":wbdry").readResolve());
    static final Char Lit27 = Char.make(66);
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol(":not-wbdry").readResolve());
    static final Char Lit29 = Char.make(100);
    static final Char Lit3 = Char.make(32);
    static final SimpleSymbol Lit30;
    static final Char Lit31 = Char.make(68);
    static final PairWithPosition Lit32;
    static final Char Lit33 = Char.make(110);
    static final Char Lit34 = Char.make(114);
    static final Char Lit35 = Char.make(115);
    static final SimpleSymbol Lit36;
    static final Char Lit37 = Char.make(83);
    static final PairWithPosition Lit38;
    static final Char Lit39 = Char.make(116);
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol(":or").readResolve());
    static final Char Lit40 = Char.make(119);
    static final SimpleSymbol Lit41;
    static final Char Lit42 = Char.make(87);
    static final PairWithPosition Lit43;
    static final Char Lit44 = Char.make(58);
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-posix-char-class").readResolve());
    static final Char Lit46;
    static final Char Lit47;
    static final Char Lit48 = Char.make(61);
    static final PairWithPosition Lit49 = PairWithPosition.make(Lit103, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 851996);
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol(":seq").readResolve());
    static final Char Lit50 = Char.make(33);
    static final PairWithPosition Lit51 = PairWithPosition.make(Lit104, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 856092);
    static final Char Lit52 = Char.make(62);
    static final PairWithPosition Lit53 = PairWithPosition.make(Lit109, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 860188);
    static final Char Lit54 = Char.make(60);
    static final PairWithPosition Lit55 = PairWithPosition.make(Lit105, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 872479);
    static final PairWithPosition Lit56 = PairWithPosition.make(Lit107, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 876575);
    static final SimpleSymbol Lit57 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-cluster-type").readResolve());
    static final Char Lit58 = Char.make(45);
    static final Char Lit59 = Char.make(105);
    static final Char Lit6;
    static final SimpleSymbol Lit60 = ((SimpleSymbol) new SimpleSymbol(":case-sensitive").readResolve());
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol(":case-insensitive").readResolve());
    static final Char Lit62 = Char.make(120);
    static final PairWithPosition Lit63 = PairWithPosition.make(Lit100, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 942102);
    static final SimpleSymbol Lit64 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-subpattern").readResolve());
    static final Char Lit65;
    static final Char Lit66;
    static final Char Lit67;
    static final SimpleSymbol Lit68;
    static final SimpleSymbol Lit69 = ((SimpleSymbol) new SimpleSymbol("minimal?").readResolve());
    static final Char Lit7;
    static final SimpleSymbol Lit70 = ((SimpleSymbol) new SimpleSymbol("at-least").readResolve());
    static final SimpleSymbol Lit71 = ((SimpleSymbol) new SimpleSymbol("at-most").readResolve());
    static final SimpleSymbol Lit72 = ((SimpleSymbol) new SimpleSymbol("next-i").readResolve());
    static final IntNum Lit73;
    static final SimpleSymbol Lit74 = ((SimpleSymbol) new SimpleSymbol("pregexp-wrap-quantifier-if-any").readResolve());
    static final SimpleSymbol Lit75 = ((SimpleSymbol) new SimpleSymbol("left-brace-must-be-followed-by-number").readResolve());
    static final SimpleSymbol Lit76 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-nums").readResolve());
    static final Char Lit77 = Char.make(44);
    static final Char Lit78;
    static final SimpleSymbol Lit79 = ((SimpleSymbol) new SimpleSymbol(":none-of-chars").readResolve());
    static final IntNum Lit8 = IntNum.make(1);
    static final SimpleSymbol Lit80 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-char-list").readResolve());
    static final SimpleSymbol Lit81 = ((SimpleSymbol) new SimpleSymbol("character-class-ended-too-soon").readResolve());
    static final SimpleSymbol Lit82 = ((SimpleSymbol) new SimpleSymbol(":one-of-chars").readResolve());
    static final SimpleSymbol Lit83 = ((SimpleSymbol) new SimpleSymbol(":char-range").readResolve());
    static final Char Lit84 = Char.make(95);
    static final SimpleSymbol Lit85 = ((SimpleSymbol) new SimpleSymbol(":alnum").readResolve());
    static final SimpleSymbol Lit86 = ((SimpleSymbol) new SimpleSymbol(":alpha").readResolve());
    static final SimpleSymbol Lit87 = ((SimpleSymbol) new SimpleSymbol(":ascii").readResolve());
    static final SimpleSymbol Lit88 = ((SimpleSymbol) new SimpleSymbol(":blank").readResolve());
    static final SimpleSymbol Lit89 = ((SimpleSymbol) new SimpleSymbol(":cntrl").readResolve());
    static final Char Lit9;
    static final SimpleSymbol Lit90 = ((SimpleSymbol) new SimpleSymbol(":graph").readResolve());
    static final SimpleSymbol Lit91 = ((SimpleSymbol) new SimpleSymbol(":lower").readResolve());
    static final SimpleSymbol Lit92 = ((SimpleSymbol) new SimpleSymbol(":print").readResolve());
    static final SimpleSymbol Lit93 = ((SimpleSymbol) new SimpleSymbol(":punct").readResolve());
    static final SimpleSymbol Lit94 = ((SimpleSymbol) new SimpleSymbol(":upper").readResolve());
    static final SimpleSymbol Lit95 = ((SimpleSymbol) new SimpleSymbol(":xdigit").readResolve());
    static final Char Lit96 = Char.make(99);
    static final Char Lit97 = Char.make(101);
    static final Char Lit98 = Char.make(102);
    static final SimpleSymbol Lit99 = ((SimpleSymbol) new SimpleSymbol("pregexp-check-if-in-char-class?").readResolve());
    static final ModuleMethod lambda$Fn1;
    static final ModuleMethod lambda$Fn10;
    static final ModuleMethod lambda$Fn6;
    static final ModuleMethod lambda$Fn7;
    static final ModuleMethod lambda$Fn8;
    static final ModuleMethod lambda$Fn9;
    public static final ModuleMethod pregexp;
    public static final ModuleMethod pregexp$Mnat$Mnword$Mnboundary$Qu;
    public static final ModuleMethod pregexp$Mnchar$Mnword$Qu;
    public static final ModuleMethod pregexp$Mncheck$Mnif$Mnin$Mnchar$Mnclass$Qu;
    public static final ModuleMethod pregexp$Mnerror;
    public static final ModuleMethod pregexp$Mninvert$Mnchar$Mnlist;
    public static final ModuleMethod pregexp$Mnlist$Mnref;
    public static final ModuleMethod pregexp$Mnmake$Mnbackref$Mnlist;
    public static final ModuleMethod pregexp$Mnmatch;
    public static final ModuleMethod pregexp$Mnmatch$Mnpositions;
    public static final ModuleMethod pregexp$Mnmatch$Mnpositions$Mnaux;
    public static final ModuleMethod pregexp$Mnquote;
    public static final ModuleMethod pregexp$Mnread$Mnbranch;
    public static final ModuleMethod pregexp$Mnread$Mnchar$Mnlist;
    public static final ModuleMethod pregexp$Mnread$Mncluster$Mntype;
    public static final ModuleMethod pregexp$Mnread$Mnescaped$Mnchar;
    public static final ModuleMethod pregexp$Mnread$Mnescaped$Mnnumber;
    public static final ModuleMethod pregexp$Mnread$Mnnums;
    public static final ModuleMethod pregexp$Mnread$Mnpattern;
    public static final ModuleMethod pregexp$Mnread$Mnpiece;
    public static final ModuleMethod pregexp$Mnread$Mnposix$Mnchar$Mnclass;
    public static final ModuleMethod pregexp$Mnread$Mnsubpattern;
    public static final ModuleMethod pregexp$Mnreplace;
    public static final ModuleMethod pregexp$Mnreplace$Mnaux;
    public static final ModuleMethod pregexp$Mnreplace$St;
    public static final ModuleMethod pregexp$Mnreverse$Ex;
    public static final ModuleMethod pregexp$Mnsplit;
    public static final ModuleMethod pregexp$Mnstring$Mnmatch;
    public static final ModuleMethod pregexp$Mnwrap$Mnquantifier$Mnif$Mnany;

    static {
        Char make = Char.make(92);
        Lit19 = make;
        Char make2 = Char.make(46);
        Lit13 = make2;
        Char make3 = Char.make(63);
        Lit47 = make3;
        Char make4 = Char.make(42);
        Lit65 = make4;
        Char make5 = Char.make(43);
        Lit66 = make5;
        Char make6 = Char.make(124);
        Lit7 = make6;
        Char make7 = Char.make(94);
        Lit9 = make7;
        Char make8 = Char.make(36);
        Lit11 = make8;
        Char make9 = Char.make(91);
        Lit15 = make9;
        Char make10 = Char.make(93);
        Lit46 = make10;
        Char make11 = Char.make(123);
        Lit67 = make11;
        Char make12 = Char.make(125);
        Lit78 = make12;
        Char make13 = Char.make(40);
        Lit18 = make13;
        Char make14 = Char.make(41);
        Lit6 = make14;
        Lit116 = PairWithPosition.make(make, PairWithPosition.make(make2, PairWithPosition.make(make3, PairWithPosition.make(make4, PairWithPosition.make(make5, PairWithPosition.make(make6, PairWithPosition.make(make7, PairWithPosition.make(make8, PairWithPosition.make(make9, PairWithPosition.make(make10, PairWithPosition.make(make11, PairWithPosition.make(make12, PairWithPosition.make(make13, PairWithPosition.make(make14, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153977), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153973), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153969), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153965), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153961), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153957), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149885), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149881), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149877), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149873), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149869), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149865), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149861), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149856);
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol(":between").readResolve();
        Lit68 = simpleSymbol;
        Boolean bool = Boolean.FALSE;
        IntNum make15 = IntNum.make(0);
        Lit73 = make15;
        Boolean bool2 = Boolean.FALSE;
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol(":any").readResolve();
        Lit14 = simpleSymbol2;
        Lit108 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(bool, PairWithPosition.make(make15, PairWithPosition.make(bool2, PairWithPosition.make(simpleSymbol2, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338881), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338878), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338876), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338873), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338863);
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol(":neg-char").readResolve();
        Lit17 = simpleSymbol3;
        SimpleSymbol simpleSymbol4 = (SimpleSymbol) new SimpleSymbol(":word").readResolve();
        Lit41 = simpleSymbol4;
        Lit43 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol4, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 696359), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 696348);
        SimpleSymbol simpleSymbol5 = Lit17;
        SimpleSymbol simpleSymbol6 = (SimpleSymbol) new SimpleSymbol(":space").readResolve();
        Lit36 = simpleSymbol6;
        Lit38 = PairWithPosition.make(simpleSymbol5, PairWithPosition.make(simpleSymbol6, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 684071), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 684060);
        SimpleSymbol simpleSymbol7 = Lit17;
        SimpleSymbol simpleSymbol8 = (SimpleSymbol) new SimpleSymbol(":digit").readResolve();
        Lit30 = simpleSymbol8;
        Lit32 = PairWithPosition.make(simpleSymbol7, PairWithPosition.make(simpleSymbol8, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 667687), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 667676);
        pregexp pregexp2 = $instance;
        ModuleMethod moduleMethod = new ModuleMethod(pregexp2, 16, Lit117, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:47");
        pregexp$Mnreverse$Ex = moduleMethod;
        ModuleMethod moduleMethod2 = new ModuleMethod(pregexp2, 17, Lit118, -4096);
        moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:57");
        pregexp$Mnerror = moduleMethod2;
        ModuleMethod moduleMethod3 = new ModuleMethod(pregexp2, 18, Lit119, 12291);
        moduleMethod3.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:65");
        pregexp$Mnread$Mnpattern = moduleMethod3;
        ModuleMethod moduleMethod4 = new ModuleMethod(pregexp2, 19, Lit120, 12291);
        moduleMethod4.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:79");
        pregexp$Mnread$Mnbranch = moduleMethod4;
        ModuleMethod moduleMethod5 = new ModuleMethod(pregexp2, 20, Lit21, 12291);
        moduleMethod5.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:91");
        pregexp$Mnread$Mnpiece = moduleMethod5;
        ModuleMethod moduleMethod6 = new ModuleMethod(pregexp2, 21, Lit121, 12291);
        moduleMethod6.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:138");
        pregexp$Mnread$Mnescaped$Mnnumber = moduleMethod6;
        ModuleMethod moduleMethod7 = new ModuleMethod(pregexp2, 22, Lit122, 12291);
        moduleMethod7.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:155");
        pregexp$Mnread$Mnescaped$Mnchar = moduleMethod7;
        ModuleMethod moduleMethod8 = new ModuleMethod(pregexp2, 23, Lit45, 12291);
        moduleMethod8.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:174");
        pregexp$Mnread$Mnposix$Mnchar$Mnclass = moduleMethod8;
        ModuleMethod moduleMethod9 = new ModuleMethod(pregexp2, 24, Lit57, 12291);
        moduleMethod9.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:200");
        pregexp$Mnread$Mncluster$Mntype = moduleMethod9;
        ModuleMethod moduleMethod10 = new ModuleMethod(pregexp2, 25, Lit64, 12291);
        moduleMethod10.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:233");
        pregexp$Mnread$Mnsubpattern = moduleMethod10;
        ModuleMethod moduleMethod11 = new ModuleMethod(pregexp2, 26, Lit74, 12291);
        moduleMethod11.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:254");
        pregexp$Mnwrap$Mnquantifier$Mnif$Mnany = moduleMethod11;
        ModuleMethod moduleMethod12 = new ModuleMethod(pregexp2, 27, Lit76, 12291);
        moduleMethod12.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:300");
        pregexp$Mnread$Mnnums = moduleMethod12;
        ModuleMethod moduleMethod13 = new ModuleMethod(pregexp2, 28, Lit123, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod13.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:323");
        pregexp$Mninvert$Mnchar$Mnlist = moduleMethod13;
        ModuleMethod moduleMethod14 = new ModuleMethod(pregexp2, 29, Lit80, 12291);
        moduleMethod14.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:330");
        pregexp$Mnread$Mnchar$Mnlist = moduleMethod14;
        ModuleMethod moduleMethod15 = new ModuleMethod(pregexp2, 30, Lit124, 24582);
        moduleMethod15.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:368");
        pregexp$Mnstring$Mnmatch = moduleMethod15;
        ModuleMethod moduleMethod16 = new ModuleMethod(pregexp2, 31, Lit125, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod16.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:379");
        pregexp$Mnchar$Mnword$Qu = moduleMethod16;
        ModuleMethod moduleMethod17 = new ModuleMethod(pregexp2, 32, Lit126, 12291);
        moduleMethod17.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:387");
        pregexp$Mnat$Mnword$Mnboundary$Qu = moduleMethod17;
        ModuleMethod moduleMethod18 = new ModuleMethod(pregexp2, 33, Lit99, 8194);
        moduleMethod18.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:399");
        pregexp$Mncheck$Mnif$Mnin$Mnchar$Mnclass$Qu = moduleMethod18;
        ModuleMethod moduleMethod19 = new ModuleMethod(pregexp2, 34, Lit127, 8194);
        moduleMethod19.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:429");
        pregexp$Mnlist$Mnref = moduleMethod19;
        ModuleMethod moduleMethod20 = new ModuleMethod(pregexp2, 35, Lit128, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod20.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:448");
        pregexp$Mnmake$Mnbackref$Mnlist = moduleMethod20;
        ModuleMethod moduleMethod21 = new ModuleMethod(pregexp2, 36, (Object) null, 0);
        moduleMethod21.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:463");
        lambda$Fn1 = moduleMethod21;
        ModuleMethod moduleMethod22 = new ModuleMethod(pregexp2, 37, (Object) null, 0);
        moduleMethod22.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:551");
        lambda$Fn6 = moduleMethod22;
        ModuleMethod moduleMethod23 = new ModuleMethod(pregexp2, 38, (Object) null, 0);
        moduleMethod23.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:556");
        lambda$Fn7 = moduleMethod23;
        ModuleMethod moduleMethod24 = new ModuleMethod(pregexp2, 39, (Object) null, 0);
        moduleMethod24.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:564");
        lambda$Fn8 = moduleMethod24;
        ModuleMethod moduleMethod25 = new ModuleMethod(pregexp2, 40, (Object) null, 0);
        moduleMethod25.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:573");
        lambda$Fn9 = moduleMethod25;
        ModuleMethod moduleMethod26 = new ModuleMethod(pregexp2, 41, (Object) null, 0);
        moduleMethod26.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:578");
        lambda$Fn10 = moduleMethod26;
        ModuleMethod moduleMethod27 = new ModuleMethod(pregexp2, 42, Lit101, 24582);
        moduleMethod27.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:459");
        pregexp$Mnmatch$Mnpositions$Mnaux = moduleMethod27;
        ModuleMethod moduleMethod28 = new ModuleMethod(pregexp2, 43, Lit129, 16388);
        moduleMethod28.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:639");
        pregexp$Mnreplace$Mnaux = moduleMethod28;
        ModuleMethod moduleMethod29 = new ModuleMethod(pregexp2, 44, Lit130, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod29.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:665");
        pregexp = moduleMethod29;
        ModuleMethod moduleMethod30 = new ModuleMethod(pregexp2, 45, Lit114, -4094);
        moduleMethod30.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:670");
        pregexp$Mnmatch$Mnpositions = moduleMethod30;
        ModuleMethod moduleMethod31 = new ModuleMethod(pregexp2, 46, Lit131, -4094);
        moduleMethod31.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:690");
        pregexp$Mnmatch = moduleMethod31;
        ModuleMethod moduleMethod32 = new ModuleMethod(pregexp2, 47, Lit132, 8194);
        moduleMethod32.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:700");
        pregexp$Mnsplit = moduleMethod32;
        ModuleMethod moduleMethod33 = new ModuleMethod(pregexp2, 48, Lit133, 12291);
        moduleMethod33.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:723");
        pregexp$Mnreplace = moduleMethod33;
        ModuleMethod moduleMethod34 = new ModuleMethod(pregexp2, 49, Lit134, 12291);
        moduleMethod34.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:736");
        pregexp$Mnreplace$St = moduleMethod34;
        ModuleMethod moduleMethod35 = new ModuleMethod(pregexp2, 50, Lit135, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod35.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:764");
        pregexp$Mnquote = moduleMethod35;
        $instance.run();
    }

    public pregexp() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        $Stpregexp$Mnversion$St = Lit0;
        $Stpregexp$Mncomment$Mnchar$St = Lit1;
        $Stpregexp$Mnnul$Mnchar$Mnint$St = Integer.valueOf(characters.char$To$Integer(Lit2) - 97);
        $Stpregexp$Mnreturn$Mnchar$St = characters.integer$To$Char(((Number) $Stpregexp$Mnnul$Mnchar$Mnint$St).intValue() + 13);
        $Stpregexp$Mntab$Mnchar$St = characters.integer$To$Char(((Number) $Stpregexp$Mnnul$Mnchar$Mnint$St).intValue() + 9);
        $Stpregexp$Mnspace$Mnsensitive$Qu$St = Boolean.TRUE;
    }

    public static Object pregexpReverse$Ex(Object s) {
        Object r = LList.Empty;
        Object s2 = s;
        while (!lists.isNull(s2)) {
            Object d = lists.cdr.apply1(s2);
            try {
                lists.setCdr$Ex((Pair) s2, r);
                r = s2;
                s2 = d;
            } catch (ClassCastException e) {
                throw new WrongType(e, "set-cdr!", 1, s2);
            }
        }
        return r;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 16:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 28:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 31:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 35:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 44:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 50:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object pregexpError$V(Object[] argsArray) {
        LList whatever = LList.makeList(argsArray, 0);
        ports.display("Error:");
        Object arg0 = whatever;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object x = arg02.getCar();
                ports.display(Lit3);
                ports.write(x);
                arg0 = arg02.getCdr();
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, arg0);
            }
        }
        ports.newline();
        return misc.error$V("pregexp-error", new Object[0]);
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 17:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 30:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 42:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 45:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 46:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0078  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0082 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object pregexpReadPattern(java.lang.Object r6, java.lang.Object r7, java.lang.Object r8) {
        /*
            gnu.kawa.functions.NumberCompare r0 = kawa.standard.Scheme.numGEq
            java.lang.Object r0 = r0.apply2(r7, r8)
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            if (r0 == r1) goto L_0x001c
            gnu.mapping.SimpleSymbol r6 = Lit4
            gnu.mapping.SimpleSymbol r8 = Lit5
            gnu.lists.Pair r8 = gnu.lists.LList.list1(r8)
            gnu.lists.Pair r6 = gnu.lists.LList.list2(r6, r8)
            gnu.lists.Pair r6 = gnu.lists.LList.list2(r6, r7)
            goto L_0x005d
        L_0x001c:
            gnu.lists.LList r0 = gnu.lists.LList.Empty
        L_0x001e:
            gnu.kawa.functions.NumberCompare r1 = kawa.standard.Scheme.numGEq
            java.lang.Object r1 = r1.apply2(r7, r8)
            r2 = r1
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch:{ ClassCastException -> 0x00b3 }
            boolean r1 = r2.booleanValue()     // Catch:{ ClassCastException -> 0x00b3 }
            r2 = 2
            r3 = 1
            java.lang.String r4 = "string-ref"
            if (r1 == 0) goto L_0x0034
            if (r1 == 0) goto L_0x005e
        L_0x0033:
            goto L_0x004f
        L_0x0034:
            r1 = r6
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1     // Catch:{ ClassCastException -> 0x00ac }
            r5 = r7
            java.lang.Number r5 = (java.lang.Number) r5     // Catch:{ ClassCastException -> 0x00a5 }
            int r5 = r5.intValue()     // Catch:{ ClassCastException -> 0x00a5 }
            char r1 = kawa.lib.strings.stringRef(r1, r5)
            gnu.text.Char r1 = gnu.text.Char.make(r1)
            gnu.text.Char r5 = Lit6
            boolean r1 = kawa.lib.characters.isChar$Eq(r1, r5)
            if (r1 == 0) goto L_0x005e
            goto L_0x0033
        L_0x004f:
            gnu.mapping.SimpleSymbol r6 = Lit4
            java.lang.Object r8 = pregexpReverse$Ex(r0)
            gnu.lists.Pair r6 = kawa.lib.lists.cons(r6, r8)
            gnu.lists.Pair r6 = gnu.lists.LList.list2(r6, r7)
        L_0x005d:
            return r6
        L_0x005e:
            r1 = r6
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1     // Catch:{ ClassCastException -> 0x009e }
            r3 = r7
            java.lang.Number r3 = (java.lang.Number) r3     // Catch:{ ClassCastException -> 0x0097 }
            int r2 = r3.intValue()     // Catch:{ ClassCastException -> 0x0097 }
            char r1 = kawa.lib.strings.stringRef(r1, r2)
            gnu.text.Char r1 = gnu.text.Char.make(r1)
            gnu.text.Char r2 = Lit7
            boolean r1 = kawa.lib.characters.isChar$Eq(r1, r2)
            if (r1 == 0) goto L_0x0081
            gnu.kawa.functions.AddOp r1 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r2 = Lit8
            java.lang.Object r7 = r1.apply2(r7, r2)
            goto L_0x0082
        L_0x0081:
        L_0x0082:
            java.lang.Object r7 = pregexpReadBranch(r6, r7, r8)
            gnu.expr.GenericProc r1 = kawa.lib.lists.car
            java.lang.Object r1 = r1.apply1(r7)
            gnu.lists.Pair r0 = kawa.lib.lists.cons(r1, r0)
            gnu.expr.GenericProc r1 = kawa.lib.lists.cadr
            java.lang.Object r7 = r1.apply1(r7)
            goto L_0x001e
        L_0x0097:
            r6 = move-exception
            gnu.mapping.WrongType r8 = new gnu.mapping.WrongType
            r8.<init>((java.lang.ClassCastException) r6, (java.lang.String) r4, (int) r2, (java.lang.Object) r7)
            throw r8
        L_0x009e:
            r7 = move-exception
            gnu.mapping.WrongType r8 = new gnu.mapping.WrongType
            r8.<init>((java.lang.ClassCastException) r7, (java.lang.String) r4, (int) r3, (java.lang.Object) r6)
            throw r8
        L_0x00a5:
            r6 = move-exception
            gnu.mapping.WrongType r8 = new gnu.mapping.WrongType
            r8.<init>((java.lang.ClassCastException) r6, (java.lang.String) r4, (int) r2, (java.lang.Object) r7)
            throw r8
        L_0x00ac:
            r7 = move-exception
            gnu.mapping.WrongType r8 = new gnu.mapping.WrongType
            r8.<init>((java.lang.ClassCastException) r7, (java.lang.String) r4, (int) r3, (java.lang.Object) r6)
            throw r8
        L_0x00b3:
            r6 = move-exception
            gnu.mapping.WrongType r7 = new gnu.mapping.WrongType
            java.lang.String r8 = "x"
            r0 = -2
            r7.<init>((java.lang.ClassCastException) r6, (java.lang.String) r8, (int) r0, (java.lang.Object) r1)
            goto L_0x00be
        L_0x00bd:
            throw r7
        L_0x00be:
            goto L_0x00bd
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpReadPattern(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 18:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 19:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 20:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 21:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 22:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 23:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 24:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 25:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 26:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 27:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 29:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 32:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 48:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 49:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            default:
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
    }

    public static Object pregexpReadBranch(Object obj, Object obj2, Object obj3) {
        Object obj4 = LList.Empty;
        while (Scheme.numGEq.apply2(obj2, obj3) == Boolean.FALSE) {
            try {
                try {
                    char stringRef = strings.stringRef((CharSequence) obj, ((Number) obj2).intValue());
                    boolean isChar$Eq = characters.isChar$Eq(Char.make(stringRef), Lit7);
                    if (isChar$Eq) {
                        if (!isChar$Eq) {
                            Object pregexpReadPiece = pregexpReadPiece(obj, obj2, obj3);
                            obj4 = lists.cons(lists.car.apply1(pregexpReadPiece), obj4);
                            obj2 = lists.cadr.apply1(pregexpReadPiece);
                        }
                    } else if (!characters.isChar$Eq(Char.make(stringRef), Lit6)) {
                        Object pregexpReadPiece2 = pregexpReadPiece(obj, obj2, obj3);
                        obj4 = lists.cons(lists.car.apply1(pregexpReadPiece2), obj4);
                        obj2 = lists.cadr.apply1(pregexpReadPiece2);
                    }
                    return LList.list2(lists.cons(Lit5, pregexpReverse$Ex(obj4)), obj2);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, obj2);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, obj);
            }
        }
        return LList.list2(lists.cons(Lit5, pregexpReverse$Ex(obj4)), obj2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:72:0x01d8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object pregexpReadPiece(java.lang.Object r7, java.lang.Object r8, java.lang.Object r9) {
        /*
            java.lang.String r0 = "string-ref"
            r1 = 1
            r2 = r7
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2     // Catch:{ ClassCastException -> 0x024c }
            r3 = 2
            r4 = r8
            java.lang.Number r4 = (java.lang.Number) r4     // Catch:{ ClassCastException -> 0x0245 }
            int r4 = r4.intValue()     // Catch:{ ClassCastException -> 0x0245 }
            char r2 = kawa.lib.strings.stringRef(r2, r4)
            gnu.kawa.functions.IsEqv r4 = kawa.standard.Scheme.isEqv
            gnu.text.Char r5 = gnu.text.Char.make(r2)
            gnu.text.Char r6 = Lit9
            java.lang.Object r4 = r4.apply2(r5, r6)
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            if (r4 == r5) goto L_0x0032
            gnu.mapping.SimpleSymbol r7 = Lit10
            gnu.kawa.functions.AddOp r9 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r0 = Lit8
            java.lang.Object r8 = r9.apply2(r8, r0)
            gnu.lists.Pair r7 = gnu.lists.LList.list2(r7, r8)
            goto L_0x0236
        L_0x0032:
            gnu.kawa.functions.IsEqv r4 = kawa.standard.Scheme.isEqv
            gnu.text.Char r5 = gnu.text.Char.make(r2)
            gnu.text.Char r6 = Lit11
            java.lang.Object r4 = r4.apply2(r5, r6)
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            if (r4 == r5) goto L_0x0052
            gnu.mapping.SimpleSymbol r7 = Lit12
            gnu.kawa.functions.AddOp r9 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r0 = Lit8
            java.lang.Object r8 = r9.apply2(r8, r0)
            gnu.lists.Pair r7 = gnu.lists.LList.list2(r7, r8)
            goto L_0x0236
        L_0x0052:
            gnu.kawa.functions.IsEqv r4 = kawa.standard.Scheme.isEqv
            gnu.text.Char r5 = gnu.text.Char.make(r2)
            gnu.text.Char r6 = Lit13
            java.lang.Object r4 = r4.apply2(r5, r6)
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            if (r4 == r5) goto L_0x0076
            gnu.mapping.SimpleSymbol r0 = Lit14
            gnu.kawa.functions.AddOp r1 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r2 = Lit8
            java.lang.Object r8 = r1.apply2(r8, r2)
            gnu.lists.Pair r8 = gnu.lists.LList.list2(r0, r8)
            java.lang.Object r7 = pregexpWrapQuantifierIfAny(r8, r7, r9)
            goto L_0x0236
        L_0x0076:
            gnu.kawa.functions.IsEqv r4 = kawa.standard.Scheme.isEqv
            gnu.text.Char r5 = gnu.text.Char.make(r2)
            gnu.text.Char r6 = Lit15
            java.lang.Object r4 = r4.apply2(r5, r6)
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            if (r4 == r5) goto L_0x0109
            gnu.kawa.functions.AddOp r2 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r4 = Lit8
            java.lang.Object r2 = r2.apply2(r8, r4)
            gnu.kawa.functions.NumberCompare r4 = kawa.standard.Scheme.numLss
            java.lang.Object r4 = r4.apply2(r2, r9)
            r5 = r4
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch:{ ClassCastException -> 0x00ff }
            boolean r4 = r5.booleanValue()     // Catch:{ ClassCastException -> 0x00ff }
            if (r4 == 0) goto L_0x00bf
            r4 = r7
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4     // Catch:{ ClassCastException -> 0x00b8 }
            r1 = r2
            java.lang.Number r1 = (java.lang.Number) r1     // Catch:{ ClassCastException -> 0x00b1 }
            int r0 = r1.intValue()     // Catch:{ ClassCastException -> 0x00b1 }
            char r0 = kawa.lib.strings.stringRef(r4, r0)
            gnu.text.Char r0 = gnu.text.Char.make(r0)
            goto L_0x00c6
        L_0x00b1:
            r7 = move-exception
            gnu.mapping.WrongType r8 = new gnu.mapping.WrongType
            r8.<init>((java.lang.ClassCastException) r7, (java.lang.String) r0, (int) r3, (java.lang.Object) r2)
            throw r8
        L_0x00b8:
            r8 = move-exception
            gnu.mapping.WrongType r9 = new gnu.mapping.WrongType
            r9.<init>((java.lang.ClassCastException) r8, (java.lang.String) r0, (int) r1, (java.lang.Object) r7)
            throw r9
        L_0x00bf:
            if (r4 == 0) goto L_0x00c4
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            goto L_0x00c6
        L_0x00c4:
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
        L_0x00c6:
            gnu.kawa.functions.IsEqv r1 = kawa.standard.Scheme.isEqv
            gnu.text.Char r3 = Lit9
            java.lang.Object r0 = r1.apply2(r0, r3)
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            if (r0 == r1) goto L_0x00f5
            gnu.kawa.functions.AddOp r0 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r1 = Lit16
            java.lang.Object r8 = r0.apply2(r8, r1)
            java.lang.Object r8 = pregexpReadCharList(r7, r8, r9)
            gnu.mapping.SimpleSymbol r0 = Lit17
            gnu.expr.GenericProc r1 = kawa.lib.lists.car
            java.lang.Object r1 = r1.apply1(r8)
            gnu.lists.Pair r0 = gnu.lists.LList.list2(r0, r1)
            gnu.expr.GenericProc r1 = kawa.lib.lists.cadr
            java.lang.Object r8 = r1.apply1(r8)
            gnu.lists.Pair r8 = gnu.lists.LList.list2(r0, r8)
            goto L_0x00f9
        L_0x00f5:
            java.lang.Object r8 = pregexpReadCharList(r7, r2, r9)
        L_0x00f9:
            java.lang.Object r7 = pregexpWrapQuantifierIfAny(r8, r7, r9)
            goto L_0x0236
        L_0x00ff:
            r7 = move-exception
            gnu.mapping.WrongType r8 = new gnu.mapping.WrongType
            java.lang.String r9 = "x"
            r0 = -2
            r8.<init>((java.lang.ClassCastException) r7, (java.lang.String) r9, (int) r0, (java.lang.Object) r4)
            throw r8
        L_0x0109:
            gnu.kawa.functions.IsEqv r4 = kawa.standard.Scheme.isEqv
            gnu.text.Char r5 = gnu.text.Char.make(r2)
            gnu.text.Char r6 = Lit18
            java.lang.Object r4 = r4.apply2(r5, r6)
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            if (r4 == r5) goto L_0x012b
            gnu.kawa.functions.AddOp r0 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r1 = Lit8
            java.lang.Object r8 = r0.apply2(r8, r1)
            java.lang.Object r8 = pregexpReadSubpattern(r7, r8, r9)
            java.lang.Object r7 = pregexpWrapQuantifierIfAny(r8, r7, r9)
            goto L_0x0236
        L_0x012b:
            gnu.kawa.functions.IsEqv r4 = kawa.standard.Scheme.isEqv
            gnu.text.Char r5 = gnu.text.Char.make(r2)
            gnu.text.Char r6 = Lit19
            java.lang.Object r4 = r4.apply2(r5, r6)
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            if (r4 == r5) goto L_0x0189
            java.lang.Object r0 = pregexpReadEscapedNumber(r7, r8, r9)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            if (r0 == r2) goto L_0x015b
            gnu.mapping.SimpleSymbol r8 = Lit20
            gnu.expr.GenericProc r1 = kawa.lib.lists.car
            java.lang.Object r1 = r1.apply1(r0)
            gnu.lists.Pair r8 = gnu.lists.LList.list2(r8, r1)
            gnu.expr.GenericProc r1 = kawa.lib.lists.cadr
            java.lang.Object r0 = r1.apply1(r0)
            gnu.lists.Pair r8 = gnu.lists.LList.list2(r8, r0)
            goto L_0x0183
        L_0x015b:
            java.lang.Object r8 = pregexpReadEscapedChar(r7, r8, r9)
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            if (r8 == r0) goto L_0x0174
            gnu.expr.GenericProc r0 = kawa.lib.lists.car
            java.lang.Object r0 = r0.apply1(r8)
            gnu.expr.GenericProc r1 = kawa.lib.lists.cadr
            java.lang.Object r8 = r1.apply1(r8)
            gnu.lists.Pair r8 = gnu.lists.LList.list2(r0, r8)
            goto L_0x0183
        L_0x0174:
            java.lang.Object[] r8 = new java.lang.Object[r3]
            gnu.mapping.SimpleSymbol r0 = Lit21
            r2 = 0
            r8[r2] = r0
            gnu.mapping.SimpleSymbol r0 = Lit22
            r8[r1] = r0
            java.lang.Object r8 = pregexpError$V(r8)
        L_0x0183:
            java.lang.Object r7 = pregexpWrapQuantifierIfAny(r8, r7, r9)
            goto L_0x0236
        L_0x0189:
            java.lang.Object r4 = $Stpregexp$Mnspace$Mnsensitive$Qu$St
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            if (r4 == r5) goto L_0x0194
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            if (r4 == r5) goto L_0x01c5
            goto L_0x01af
        L_0x0194:
            gnu.text.Char r4 = gnu.text.Char.make(r2)
            boolean r4 = kawa.lib.rnrs.unicode.isCharWhitespace(r4)
            int r4 = r4 + r1
            r4 = r4 & r1
            if (r4 == 0) goto L_0x01ad
            gnu.text.Char r4 = gnu.text.Char.make(r2)
            gnu.text.Char r5 = Lit1
            boolean r4 = kawa.lib.characters.isChar$Eq(r4, r5)
            if (r4 != 0) goto L_0x01c5
            goto L_0x01af
        L_0x01ad:
            if (r4 == 0) goto L_0x01c5
        L_0x01af:
            gnu.text.Char r0 = gnu.text.Char.make(r2)
            gnu.kawa.functions.AddOp r1 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r2 = Lit8
            java.lang.Object r8 = r1.apply2(r8, r2)
            gnu.lists.Pair r8 = gnu.lists.LList.list2(r0, r8)
            java.lang.Object r7 = pregexpWrapQuantifierIfAny(r8, r7, r9)
            goto L_0x0236
        L_0x01c5:
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
        L_0x01c7:
            gnu.kawa.functions.NumberCompare r4 = kawa.standard.Scheme.numGEq
            java.lang.Object r4 = r4.apply2(r8, r9)
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            if (r4 == r5) goto L_0x01d8
            gnu.mapping.SimpleSymbol r7 = Lit23
            gnu.lists.Pair r7 = gnu.lists.LList.list2(r7, r8)
            goto L_0x0236
        L_0x01d8:
            r4 = r7
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4     // Catch:{ ClassCastException -> 0x023e }
            r5 = r8
            java.lang.Number r5 = (java.lang.Number) r5     // Catch:{ ClassCastException -> 0x0237 }
            int r5 = r5.intValue()     // Catch:{ ClassCastException -> 0x0237 }
            char r4 = kawa.lib.strings.stringRef(r4, r5)
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            if (r2 == r5) goto L_0x0204
            gnu.kawa.functions.AddOp r2 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r5 = Lit8
            java.lang.Object r8 = r2.apply2(r8, r5)
            gnu.text.Char r2 = gnu.text.Char.make(r4)
            gnu.text.Char r4 = Lit24
            boolean r2 = kawa.lib.characters.isChar$Eq(r2, r4)
            if (r2 == 0) goto L_0x0201
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            goto L_0x01c7
        L_0x0201:
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            goto L_0x01c7
        L_0x0204:
            gnu.text.Char r2 = gnu.text.Char.make(r4)
            boolean r2 = kawa.lib.rnrs.unicode.isCharWhitespace(r2)
            if (r2 == 0) goto L_0x0219
            gnu.kawa.functions.AddOp r2 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r4 = Lit8
            java.lang.Object r8 = r2.apply2(r8, r4)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            goto L_0x01c7
        L_0x0219:
            gnu.text.Char r2 = gnu.text.Char.make(r4)
            gnu.text.Char r4 = Lit1
            boolean r2 = kawa.lib.characters.isChar$Eq(r2, r4)
            if (r2 == 0) goto L_0x0230
            gnu.kawa.functions.AddOp r2 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r4 = Lit8
            java.lang.Object r8 = r2.apply2(r8, r4)
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            goto L_0x01c7
        L_0x0230:
            gnu.mapping.SimpleSymbol r7 = Lit23
            gnu.lists.Pair r7 = gnu.lists.LList.list2(r7, r8)
        L_0x0236:
            return r7
        L_0x0237:
            r7 = move-exception
            gnu.mapping.WrongType r9 = new gnu.mapping.WrongType
            r9.<init>((java.lang.ClassCastException) r7, (java.lang.String) r0, (int) r3, (java.lang.Object) r8)
            throw r9
        L_0x023e:
            r8 = move-exception
            gnu.mapping.WrongType r9 = new gnu.mapping.WrongType
            r9.<init>((java.lang.ClassCastException) r8, (java.lang.String) r0, (int) r1, (java.lang.Object) r7)
            throw r9
        L_0x0245:
            r7 = move-exception
            gnu.mapping.WrongType r9 = new gnu.mapping.WrongType
            r9.<init>((java.lang.ClassCastException) r7, (java.lang.String) r0, (int) r3, (java.lang.Object) r8)
            throw r9
        L_0x024c:
            r8 = move-exception
            gnu.mapping.WrongType r9 = new gnu.mapping.WrongType
            r9.<init>((java.lang.ClassCastException) r8, (java.lang.String) r0, (int) r1, (java.lang.Object) r7)
            goto L_0x0254
        L_0x0253:
            throw r9
        L_0x0254:
            goto L_0x0253
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpReadPiece(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public static Object pregexpReadEscapedNumber(Object obj, Object obj2, Object obj3) {
        Object apply2 = Scheme.numLss.apply2(AddOp.$Pl.apply2(obj2, Lit8), obj3);
        try {
            boolean booleanValue = ((Boolean) apply2).booleanValue();
            if (!booleanValue) {
                return booleanValue ? Boolean.TRUE : Boolean.FALSE;
            }
            try {
                CharSequence charSequence = (CharSequence) obj;
                Object apply22 = AddOp.$Pl.apply2(obj2, Lit8);
                try {
                    char stringRef = strings.stringRef(charSequence, ((Number) apply22).intValue());
                    boolean isCharNumeric = unicode.isCharNumeric(Char.make(stringRef));
                    if (!isCharNumeric) {
                        return isCharNumeric ? Boolean.TRUE : Boolean.FALSE;
                    }
                    Object apply23 = AddOp.$Pl.apply2(obj2, Lit16);
                    Pair list1 = LList.list1(Char.make(stringRef));
                    while (Scheme.numGEq.apply2(apply23, obj3) == Boolean.FALSE) {
                        try {
                            try {
                                char stringRef2 = strings.stringRef((CharSequence) obj, ((Number) apply23).intValue());
                                if (unicode.isCharNumeric(Char.make(stringRef2))) {
                                    apply23 = AddOp.$Pl.apply2(apply23, Lit8);
                                    list1 = lists.cons(Char.make(stringRef2), list1);
                                } else {
                                    Object pregexpReverse$Ex = pregexpReverse$Ex(list1);
                                    try {
                                        return LList.list2(numbers.string$To$Number(strings.list$To$String((LList) pregexpReverse$Ex)), apply23);
                                    } catch (ClassCastException e) {
                                        throw new WrongType(e, "list->string", 1, pregexpReverse$Ex);
                                    }
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 2, apply23);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "string-ref", 1, obj);
                        }
                    }
                    Object pregexpReverse$Ex2 = pregexpReverse$Ex(list1);
                    try {
                        return LList.list2(numbers.string$To$Number(strings.list$To$String((LList) pregexpReverse$Ex2)), apply23);
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "list->string", 1, pregexpReverse$Ex2);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "string-ref", 2, apply22);
                }
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "string-ref", 1, obj);
            }
        } catch (ClassCastException e7) {
            throw new WrongType(e7, "x", -2, apply2);
        }
    }

    public static Object pregexpReadEscapedChar(Object obj, Object obj2, Object obj3) {
        Object apply2 = Scheme.numLss.apply2(AddOp.$Pl.apply2(obj2, Lit8), obj3);
        try {
            boolean booleanValue = ((Boolean) apply2).booleanValue();
            if (!booleanValue) {
                return booleanValue ? Boolean.TRUE : Boolean.FALSE;
            }
            try {
                CharSequence charSequence = (CharSequence) obj;
                Object apply22 = AddOp.$Pl.apply2(obj2, Lit8);
                try {
                    char stringRef = strings.stringRef(charSequence, ((Number) apply22).intValue());
                    if (Scheme.isEqv.apply2(Char.make(stringRef), Lit25) != Boolean.FALSE) {
                        return LList.list2(Lit26, AddOp.$Pl.apply2(obj2, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(stringRef), Lit27) != Boolean.FALSE) {
                        return LList.list2(Lit28, AddOp.$Pl.apply2(obj2, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(stringRef), Lit29) != Boolean.FALSE) {
                        return LList.list2(Lit30, AddOp.$Pl.apply2(obj2, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(stringRef), Lit31) != Boolean.FALSE) {
                        return LList.list2(Lit32, AddOp.$Pl.apply2(obj2, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(stringRef), Lit33) != Boolean.FALSE) {
                        return LList.list2(Lit24, AddOp.$Pl.apply2(obj2, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(stringRef), Lit34) != Boolean.FALSE) {
                        return LList.list2($Stpregexp$Mnreturn$Mnchar$St, AddOp.$Pl.apply2(obj2, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(stringRef), Lit35) != Boolean.FALSE) {
                        return LList.list2(Lit36, AddOp.$Pl.apply2(obj2, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(stringRef), Lit37) != Boolean.FALSE) {
                        return LList.list2(Lit38, AddOp.$Pl.apply2(obj2, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(stringRef), Lit39) != Boolean.FALSE) {
                        return LList.list2($Stpregexp$Mntab$Mnchar$St, AddOp.$Pl.apply2(obj2, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(stringRef), Lit40) != Boolean.FALSE) {
                        return LList.list2(Lit41, AddOp.$Pl.apply2(obj2, Lit16));
                    }
                    if (Scheme.isEqv.apply2(Char.make(stringRef), Lit42) != Boolean.FALSE) {
                        return LList.list2(Lit43, AddOp.$Pl.apply2(obj2, Lit16));
                    }
                    return LList.list2(Char.make(stringRef), AddOp.$Pl.apply2(obj2, Lit16));
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, apply22);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, obj);
            }
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "x", -2, apply2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0086, code lost:
        if (r11 != false) goto L_0x00aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a8, code lost:
        if (kawa.lib.characters.isChar$Eq(gnu.text.Char.make(kawa.lib.strings.stringRef(r9, ((java.lang.Number) r11).intValue())), Lit46) == false) goto L_0x00aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00b5, code lost:
        r9 = pregexpReverse$Ex(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00bc, code lost:
        r9 = kawa.lib.misc.string$To$Symbol(kawa.lib.strings.list$To$String((gnu.lists.LList) r9));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00c6, code lost:
        if (r1 == java.lang.Boolean.FALSE) goto L_0x00d0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00c8, code lost:
        r9 = gnu.lists.LList.list2(Lit17, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00dd, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00e5, code lost:
        throw new gnu.mapping.WrongType(r10, "list->string", 1, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:?, code lost:
        return gnu.lists.LList.list2(r9, gnu.kawa.functions.AddOp.$Pl.apply2(r10, Lit16));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object pregexpReadPosixCharClass(java.lang.Object r9, java.lang.Object r10, java.lang.Object r11) {
        /*
            java.lang.String r0 = "string-ref"
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            gnu.text.Char r2 = Lit44
            gnu.lists.Pair r2 = gnu.lists.LList.list1(r2)
        L_0x000a:
            gnu.kawa.functions.NumberCompare r3 = kawa.standard.Scheme.numGEq
            java.lang.Object r3 = r3.apply2(r10, r11)
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
            r5 = 0
            r6 = 1
            if (r3 == r4) goto L_0x0022
            java.lang.Object[] r9 = new java.lang.Object[r6]
            gnu.mapping.SimpleSymbol r10 = Lit45
            r9[r5] = r10
            java.lang.Object r9 = pregexpError$V(r9)
            goto L_0x0108
        L_0x0022:
            r3 = r9
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3     // Catch:{ ClassCastException -> 0x0110 }
            r4 = 2
            r7 = r10
            java.lang.Number r7 = (java.lang.Number) r7     // Catch:{ ClassCastException -> 0x0109 }
            int r7 = r7.intValue()     // Catch:{ ClassCastException -> 0x0109 }
            char r3 = kawa.lib.strings.stringRef(r3, r7)
            gnu.text.Char r7 = gnu.text.Char.make(r3)
            gnu.text.Char r8 = Lit9
            boolean r7 = kawa.lib.characters.isChar$Eq(r7, r8)
            if (r7 == 0) goto L_0x0048
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            gnu.kawa.functions.AddOp r3 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r4 = Lit8
            java.lang.Object r10 = r3.apply2(r10, r4)
            goto L_0x000a
        L_0x0048:
            gnu.text.Char r7 = gnu.text.Char.make(r3)
            boolean r7 = kawa.lib.rnrs.unicode.isCharAlphabetic(r7)
            if (r7 == 0) goto L_0x0063
            gnu.kawa.functions.AddOp r4 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r5 = Lit8
            java.lang.Object r10 = r4.apply2(r10, r5)
            gnu.text.Char r3 = gnu.text.Char.make(r3)
            gnu.lists.Pair r2 = kawa.lib.lists.cons(r3, r2)
            goto L_0x000a
        L_0x0063:
            gnu.text.Char r3 = gnu.text.Char.make(r3)
            gnu.text.Char r7 = Lit44
            boolean r3 = kawa.lib.characters.isChar$Eq(r3, r7)
            if (r3 == 0) goto L_0x00fe
            gnu.kawa.functions.NumberCompare r3 = kawa.standard.Scheme.numGEq
            gnu.kawa.functions.AddOp r7 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r8 = Lit8
            java.lang.Object r7 = r7.apply2(r10, r8)
            java.lang.Object r11 = r3.apply2(r7, r11)
            r3 = r11
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ ClassCastException -> 0x00f4 }
            boolean r11 = r3.booleanValue()     // Catch:{ ClassCastException -> 0x00f4 }
            if (r11 == 0) goto L_0x0089
            if (r11 == 0) goto L_0x00b5
            goto L_0x00aa
        L_0x0089:
            java.lang.CharSequence r9 = (java.lang.CharSequence) r9     // Catch:{ ClassCastException -> 0x00ed }
            gnu.kawa.functions.AddOp r11 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r3 = Lit8
            java.lang.Object r11 = r11.apply2(r10, r3)
            r3 = r11
            java.lang.Number r3 = (java.lang.Number) r3     // Catch:{ ClassCastException -> 0x00e6 }
            int r11 = r3.intValue()     // Catch:{ ClassCastException -> 0x00e6 }
            char r9 = kawa.lib.strings.stringRef(r9, r11)
            gnu.text.Char r9 = gnu.text.Char.make(r9)
            gnu.text.Char r11 = Lit46
            boolean r9 = kawa.lib.characters.isChar$Eq(r9, r11)
            if (r9 != 0) goto L_0x00b5
        L_0x00aa:
            java.lang.Object[] r9 = new java.lang.Object[r6]
            gnu.mapping.SimpleSymbol r10 = Lit45
            r9[r5] = r10
            java.lang.Object r9 = pregexpError$V(r9)
            goto L_0x00dc
        L_0x00b5:
            java.lang.Object r9 = pregexpReverse$Ex(r2)
            gnu.lists.LList r9 = (gnu.lists.LList) r9     // Catch:{ ClassCastException -> 0x00dd }
            java.lang.CharSequence r9 = kawa.lib.strings.list$To$String(r9)
            gnu.mapping.SimpleSymbol r9 = kawa.lib.misc.string$To$Symbol(r9)
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            if (r1 == r11) goto L_0x00cf
            gnu.mapping.SimpleSymbol r11 = Lit17
            gnu.lists.Pair r9 = gnu.lists.LList.list2(r11, r9)
            goto L_0x00d0
        L_0x00cf:
        L_0x00d0:
            gnu.kawa.functions.AddOp r11 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r0 = Lit16
            java.lang.Object r10 = r11.apply2(r10, r0)
            gnu.lists.Pair r9 = gnu.lists.LList.list2(r9, r10)
        L_0x00dc:
            goto L_0x0108
        L_0x00dd:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            java.lang.String r0 = "list->string"
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r0, (int) r6, (java.lang.Object) r9)
            throw r11
        L_0x00e6:
            r9 = move-exception
            gnu.mapping.WrongType r10 = new gnu.mapping.WrongType
            r10.<init>((java.lang.ClassCastException) r9, (java.lang.String) r0, (int) r4, (java.lang.Object) r11)
            throw r10
        L_0x00ed:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r0, (int) r6, (java.lang.Object) r9)
            throw r11
        L_0x00f4:
            r9 = move-exception
            gnu.mapping.WrongType r10 = new gnu.mapping.WrongType
            java.lang.String r0 = "x"
            r1 = -2
            r10.<init>((java.lang.ClassCastException) r9, (java.lang.String) r0, (int) r1, (java.lang.Object) r11)
            throw r10
        L_0x00fe:
            java.lang.Object[] r9 = new java.lang.Object[r6]
            gnu.mapping.SimpleSymbol r10 = Lit45
            r9[r5] = r10
            java.lang.Object r9 = pregexpError$V(r9)
        L_0x0108:
            return r9
        L_0x0109:
            r9 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r9, (java.lang.String) r0, (int) r4, (java.lang.Object) r10)
            throw r11
        L_0x0110:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r0, (int) r6, (java.lang.Object) r9)
            goto L_0x0118
        L_0x0117:
            throw r11
        L_0x0118:
            goto L_0x0117
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpReadPosixCharClass(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public static Object pregexpReadClusterType(Object obj, Object obj2, Object obj3) {
        char stringRef;
        try {
            try {
                if (Scheme.isEqv.apply2(Char.make(strings.stringRef((CharSequence) obj, ((Number) obj2).intValue())), Lit47) == Boolean.FALSE) {
                    return LList.list2(Lit63, obj2);
                }
                Object apply2 = AddOp.$Pl.apply2(obj2, Lit8);
                try {
                    try {
                        char stringRef2 = strings.stringRef((CharSequence) obj, ((Number) apply2).intValue());
                        if (Scheme.isEqv.apply2(Char.make(stringRef2), Lit44) != Boolean.FALSE) {
                            return LList.list2(LList.Empty, AddOp.$Pl.apply2(apply2, Lit8));
                        }
                        if (Scheme.isEqv.apply2(Char.make(stringRef2), Lit48) != Boolean.FALSE) {
                            return LList.list2(Lit49, AddOp.$Pl.apply2(apply2, Lit8));
                        }
                        if (Scheme.isEqv.apply2(Char.make(stringRef2), Lit50) != Boolean.FALSE) {
                            return LList.list2(Lit51, AddOp.$Pl.apply2(apply2, Lit8));
                        }
                        if (Scheme.isEqv.apply2(Char.make(stringRef2), Lit52) != Boolean.FALSE) {
                            return LList.list2(Lit53, AddOp.$Pl.apply2(apply2, Lit8));
                        }
                        if (Scheme.isEqv.apply2(Char.make(stringRef2), Lit54) != Boolean.FALSE) {
                            try {
                                CharSequence charSequence = (CharSequence) obj;
                                Object apply22 = AddOp.$Pl.apply2(apply2, Lit8);
                                try {
                                    char stringRef3 = strings.stringRef(charSequence, ((Number) apply22).intValue());
                                    return LList.list2(Scheme.isEqv.apply2(Char.make(stringRef3), Lit48) != Boolean.FALSE ? Lit55 : Scheme.isEqv.apply2(Char.make(stringRef3), Lit50) != Boolean.FALSE ? Lit56 : pregexpError$V(new Object[]{Lit57}), AddOp.$Pl.apply2(apply2, Lit16));
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, apply22);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, obj);
                            }
                        } else {
                            LList lList = LList.Empty;
                            Boolean bool = Boolean.FALSE;
                            while (true) {
                                try {
                                    try {
                                        stringRef = strings.stringRef((CharSequence) obj, ((Number) apply2).intValue());
                                        if (Scheme.isEqv.apply2(Char.make(stringRef), Lit58) == Boolean.FALSE) {
                                            if (Scheme.isEqv.apply2(Char.make(stringRef), Lit59) == Boolean.FALSE) {
                                                if (Scheme.isEqv.apply2(Char.make(stringRef), Lit62) == Boolean.FALSE) {
                                                    break;
                                                }
                                                $Stpregexp$Mnspace$Mnsensitive$Qu$St = bool;
                                                apply2 = AddOp.$Pl.apply2(apply2, Lit8);
                                                bool = Boolean.FALSE;
                                            } else {
                                                apply2 = AddOp.$Pl.apply2(apply2, Lit8);
                                                lList = lists.cons(bool != Boolean.FALSE ? Lit60 : Lit61, lList);
                                                bool = Boolean.FALSE;
                                            }
                                        } else {
                                            apply2 = AddOp.$Pl.apply2(apply2, Lit8);
                                            bool = Boolean.TRUE;
                                        }
                                    } catch (ClassCastException e3) {
                                        throw new WrongType(e3, "string-ref", 2, apply2);
                                    }
                                } catch (ClassCastException e4) {
                                    throw new WrongType(e4, "string-ref", 1, obj);
                                }
                            }
                            if (Scheme.isEqv.apply2(Char.make(stringRef), Lit44) != Boolean.FALSE) {
                                return LList.list2(lList, AddOp.$Pl.apply2(apply2, Lit8));
                            }
                            return pregexpError$V(new Object[]{Lit57});
                        }
                    } catch (ClassCastException e5) {
                        throw new WrongType(e5, "string-ref", 2, apply2);
                    }
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "string-ref", 1, obj);
                }
            } catch (ClassCastException e7) {
                throw new WrongType(e7, "string-ref", 2, obj2);
            }
        } catch (ClassCastException e8) {
            throw new WrongType(e8, "string-ref", 1, obj);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x005a, code lost:
        if (kawa.lib.characters.isChar$Eq(gnu.text.Char.make(kawa.lib.strings.stringRef((java.lang.CharSequence) r12, ((java.lang.Number) r2).intValue())), Lit6) != false) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006c, code lost:
        if (r8 != false) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        return pregexpError$V(new java.lang.Object[]{Lit64});
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object pregexpReadSubpattern(java.lang.Object r12, java.lang.Object r13, java.lang.Object r14) {
        /*
            java.lang.String r0 = "string-ref"
            java.lang.Object r1 = $Stpregexp$Mnspace$Mnsensitive$Qu$St
            r2 = 0
            r3 = r2
            java.lang.Object r3 = pregexpReadClusterType(r12, r13, r14)
            r4 = r2
            gnu.expr.GenericProc r4 = kawa.lib.lists.car
            java.lang.Object r4 = r4.apply1(r3)
            r5 = r2
            gnu.expr.GenericProc r5 = kawa.lib.lists.cadr
            java.lang.Object r5 = r5.apply1(r3)
            r6 = r2
            java.lang.Object r6 = pregexpReadPattern(r12, r5, r14)
            r7 = r2
            $Stpregexp$Mnspace$Mnsensitive$Qu$St = r1
            gnu.expr.GenericProc r7 = kawa.lib.lists.car
            java.lang.Object r7 = r7.apply1(r6)
            gnu.expr.GenericProc r8 = kawa.lib.lists.cadr
            java.lang.Object r8 = r8.apply1(r6)
            r9 = r2
            r2 = r8
            gnu.kawa.functions.NumberCompare r8 = kawa.standard.Scheme.numLss
            java.lang.Object r8 = r8.apply2(r2, r14)
            r9 = r8
            java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch:{ ClassCastException -> 0x00a1 }
            boolean r8 = r9.booleanValue()     // Catch:{ ClassCastException -> 0x00a1 }
            r9 = 0
            r9 = 1
            if (r8 == 0) goto L_0x006c
            r10 = r12
            java.lang.CharSequence r10 = (java.lang.CharSequence) r10     // Catch:{ ClassCastException -> 0x0065 }
            r11 = r2
            java.lang.Number r11 = (java.lang.Number) r11     // Catch:{ ClassCastException -> 0x005d }
            int r0 = r11.intValue()     // Catch:{ ClassCastException -> 0x005d }
            char r0 = kawa.lib.strings.stringRef(r10, r0)
            gnu.text.Char r0 = gnu.text.Char.make(r0)
            gnu.text.Char r10 = Lit6
            boolean r0 = kawa.lib.characters.isChar$Eq(r0, r10)
            if (r0 == 0) goto L_0x0095
            goto L_0x006e
        L_0x005d:
            r12 = move-exception
            gnu.mapping.WrongType r13 = new gnu.mapping.WrongType
            r14 = 2
            r13.<init>((java.lang.ClassCastException) r12, (java.lang.String) r0, (int) r14, (java.lang.Object) r2)
            throw r13
        L_0x0065:
            r13 = move-exception
            gnu.mapping.WrongType r14 = new gnu.mapping.WrongType
            r14.<init>((java.lang.ClassCastException) r13, (java.lang.String) r0, (int) r9, (java.lang.Object) r12)
            throw r14
        L_0x006c:
            if (r8 == 0) goto L_0x0095
        L_0x006e:
            r0 = r7
            r8 = r4
        L_0x0070:
            boolean r9 = kawa.lib.lists.isNull(r8)
            if (r9 == 0) goto L_0x0083
            gnu.kawa.functions.AddOp r8 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r9 = Lit8
            java.lang.Object r8 = r8.apply2(r2, r9)
            gnu.lists.Pair r0 = gnu.lists.LList.list2(r0, r8)
            goto L_0x00a0
        L_0x0083:
            gnu.expr.GenericProc r9 = kawa.lib.lists.cdr
            java.lang.Object r9 = r9.apply1(r8)
            gnu.expr.GenericProc r10 = kawa.lib.lists.car
            java.lang.Object r10 = r10.apply1(r8)
            gnu.lists.Pair r0 = gnu.lists.LList.list2(r10, r0)
            r8 = r9
            goto L_0x0070
        L_0x0095:
            java.lang.Object[] r0 = new java.lang.Object[r9]
            gnu.mapping.SimpleSymbol r8 = Lit64
            r9 = 0
            r0[r9] = r8
            java.lang.Object r0 = pregexpError$V(r0)
        L_0x00a0:
            return r0
        L_0x00a1:
            r12 = move-exception
            gnu.mapping.WrongType r13 = new gnu.mapping.WrongType
            java.lang.String r14 = "x"
            r0 = -2
            r13.<init>((java.lang.ClassCastException) r12, (java.lang.String) r14, (int) r0, (java.lang.Object) r8)
            goto L_0x00ac
        L_0x00ab:
            throw r13
        L_0x00ac:
            goto L_0x00ab
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpReadSubpattern(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    /* JADX WARNING: Removed duplicated region for block: B:108:0x01f9  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x022f  */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x025e  */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x01d1 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00e7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object pregexpWrapQuantifierIfAny(java.lang.Object r10, java.lang.Object r11, java.lang.Object r12) {
        /*
            java.lang.String r0 = "string-ref"
            gnu.expr.GenericProc r1 = kawa.lib.lists.car
            java.lang.Object r1 = r1.apply1(r10)
            gnu.expr.GenericProc r2 = kawa.lib.lists.cadr
            java.lang.Object r2 = r2.apply1(r10)
        L_0x000e:
            gnu.kawa.functions.NumberCompare r3 = kawa.standard.Scheme.numGEq
            java.lang.Object r3 = r3.apply2(r2, r12)
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
            if (r3 == r4) goto L_0x001a
            goto L_0x0295
        L_0x001a:
            r3 = 1
            r4 = r11
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4     // Catch:{ ClassCastException -> 0x029d }
            r5 = 2
            r6 = r2
            java.lang.Number r6 = (java.lang.Number) r6     // Catch:{ ClassCastException -> 0x0296 }
            int r6 = r6.intValue()     // Catch:{ ClassCastException -> 0x0296 }
            char r4 = kawa.lib.strings.stringRef(r4, r6)
            gnu.text.Char r6 = gnu.text.Char.make(r4)
            boolean r6 = kawa.lib.rnrs.unicode.isCharWhitespace(r6)
            if (r6 == 0) goto L_0x003b
            java.lang.Object r6 = $Stpregexp$Mnspace$Mnsensitive$Qu$St
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            if (r6 != r7) goto L_0x0046
            goto L_0x003d
        L_0x003b:
            if (r6 == 0) goto L_0x0046
        L_0x003d:
            gnu.kawa.functions.AddOp r3 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r4 = Lit8
            java.lang.Object r2 = r3.apply2(r2, r4)
            goto L_0x000e
        L_0x0046:
            gnu.kawa.functions.IsEqv r6 = kawa.standard.Scheme.isEqv
            gnu.text.Char r7 = gnu.text.Char.make(r4)
            gnu.text.Char r8 = Lit65
            java.lang.Object r6 = r6.apply2(r7, r8)
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            if (r6 == r7) goto L_0x005b
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            if (r6 == r7) goto L_0x0294
        L_0x005a:
            goto L_0x0096
        L_0x005b:
            gnu.kawa.functions.IsEqv r6 = kawa.standard.Scheme.isEqv
            gnu.text.Char r7 = gnu.text.Char.make(r4)
            gnu.text.Char r8 = Lit66
            java.lang.Object r6 = r6.apply2(r7, r8)
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            if (r6 == r7) goto L_0x0070
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            if (r6 == r7) goto L_0x0294
            goto L_0x005a
        L_0x0070:
            gnu.kawa.functions.IsEqv r6 = kawa.standard.Scheme.isEqv
            gnu.text.Char r7 = gnu.text.Char.make(r4)
            gnu.text.Char r8 = Lit47
            java.lang.Object r6 = r6.apply2(r7, r8)
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            if (r6 == r7) goto L_0x0085
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            if (r6 == r7) goto L_0x0294
            goto L_0x005a
        L_0x0085:
            gnu.kawa.functions.IsEqv r6 = kawa.standard.Scheme.isEqv
            gnu.text.Char r7 = gnu.text.Char.make(r4)
            gnu.text.Char r8 = Lit67
            java.lang.Object r6 = r6.apply2(r7, r8)
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            if (r6 == r7) goto L_0x0294
            goto L_0x005a
        L_0x0096:
            gnu.mapping.SimpleSymbol r10 = Lit68
            gnu.lists.Pair r10 = gnu.lists.LList.list1(r10)
            gnu.mapping.SimpleSymbol r6 = Lit69
            gnu.mapping.SimpleSymbol r7 = Lit70
            gnu.mapping.SimpleSymbol r8 = Lit71
            gnu.lists.LList.chain4(r10, r6, r7, r8, r1)
            gnu.mapping.SimpleSymbol r1 = Lit72
            gnu.lists.Pair r1 = gnu.lists.LList.list2(r10, r1)
            gnu.kawa.functions.IsEqv r6 = kawa.standard.Scheme.isEqv
            gnu.text.Char r7 = gnu.text.Char.make(r4)
            gnu.text.Char r8 = Lit65
            java.lang.Object r6 = r6.apply2(r7, r8)
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            java.lang.String r8 = "set-car!"
            if (r6 == r7) goto L_0x00e7
            gnu.expr.GenericProc r4 = kawa.lib.lists.cddr
            java.lang.Object r4 = r4.apply1(r10)
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4     // Catch:{ ClassCastException -> 0x00e0 }
            gnu.math.IntNum r6 = Lit73
            kawa.lib.lists.setCar$Ex(r4, r6)
            gnu.expr.GenericProc r4 = kawa.lib.lists.cdddr
            java.lang.Object r4 = r4.apply1(r10)
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4     // Catch:{ ClassCastException -> 0x00d9 }
            java.lang.Boolean r6 = java.lang.Boolean.FALSE
            kawa.lib.lists.setCar$Ex(r4, r6)
            goto L_0x01bf
        L_0x00d9:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r8, (int) r3, (java.lang.Object) r4)
            throw r11
        L_0x00e0:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r8, (int) r3, (java.lang.Object) r4)
            throw r11
        L_0x00e7:
            gnu.kawa.functions.IsEqv r6 = kawa.standard.Scheme.isEqv
            gnu.text.Char r7 = gnu.text.Char.make(r4)
            gnu.text.Char r9 = Lit66
            java.lang.Object r6 = r6.apply2(r7, r9)
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            if (r6 == r7) goto L_0x0121
            gnu.expr.GenericProc r4 = kawa.lib.lists.cddr
            java.lang.Object r4 = r4.apply1(r10)
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4     // Catch:{ ClassCastException -> 0x011a }
            gnu.math.IntNum r6 = Lit8
            kawa.lib.lists.setCar$Ex(r4, r6)
            gnu.expr.GenericProc r4 = kawa.lib.lists.cdddr
            java.lang.Object r4 = r4.apply1(r10)
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4     // Catch:{ ClassCastException -> 0x0113 }
            java.lang.Boolean r6 = java.lang.Boolean.FALSE
            kawa.lib.lists.setCar$Ex(r4, r6)
            goto L_0x01bf
        L_0x0113:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r8, (int) r3, (java.lang.Object) r4)
            throw r11
        L_0x011a:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r8, (int) r3, (java.lang.Object) r4)
            throw r11
        L_0x0121:
            gnu.kawa.functions.IsEqv r6 = kawa.standard.Scheme.isEqv
            gnu.text.Char r7 = gnu.text.Char.make(r4)
            gnu.text.Char r9 = Lit47
            java.lang.Object r6 = r6.apply2(r7, r9)
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            if (r6 == r7) goto L_0x015a
            gnu.expr.GenericProc r4 = kawa.lib.lists.cddr
            java.lang.Object r4 = r4.apply1(r10)
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4     // Catch:{ ClassCastException -> 0x0153 }
            gnu.math.IntNum r6 = Lit73
            kawa.lib.lists.setCar$Ex(r4, r6)
            gnu.expr.GenericProc r4 = kawa.lib.lists.cdddr
            java.lang.Object r4 = r4.apply1(r10)
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4     // Catch:{ ClassCastException -> 0x014c }
            gnu.math.IntNum r6 = Lit8
            kawa.lib.lists.setCar$Ex(r4, r6)
            goto L_0x01bf
        L_0x014c:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r8, (int) r3, (java.lang.Object) r4)
            throw r11
        L_0x0153:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r8, (int) r3, (java.lang.Object) r4)
            throw r11
        L_0x015a:
            gnu.kawa.functions.IsEqv r6 = kawa.standard.Scheme.isEqv
            gnu.text.Char r4 = gnu.text.Char.make(r4)
            gnu.text.Char r7 = Lit67
            java.lang.Object r4 = r6.apply2(r4, r7)
            java.lang.Boolean r6 = java.lang.Boolean.FALSE
            if (r4 == r6) goto L_0x01bf
            gnu.kawa.functions.AddOp r4 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r6 = Lit8
            java.lang.Object r2 = r4.apply2(r2, r6)
            java.lang.Object r2 = pregexpReadNums(r11, r2, r12)
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
            if (r2 != r4) goto L_0x0188
            java.lang.Object[] r4 = new java.lang.Object[r5]
            gnu.mapping.SimpleSymbol r6 = Lit74
            r7 = 0
            r4[r7] = r6
            gnu.mapping.SimpleSymbol r6 = Lit75
            r4[r3] = r6
            pregexpError$V(r4)
        L_0x0188:
            gnu.expr.GenericProc r4 = kawa.lib.lists.cddr
            java.lang.Object r4 = r4.apply1(r10)
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4     // Catch:{ ClassCastException -> 0x01b8 }
            gnu.expr.GenericProc r6 = kawa.lib.lists.car
            java.lang.Object r6 = r6.apply1(r2)
            kawa.lib.lists.setCar$Ex(r4, r6)
            gnu.expr.GenericProc r4 = kawa.lib.lists.cdddr
            java.lang.Object r4 = r4.apply1(r10)
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4     // Catch:{ ClassCastException -> 0x01b1 }
            gnu.expr.GenericProc r6 = kawa.lib.lists.cadr
            java.lang.Object r6 = r6.apply1(r2)
            kawa.lib.lists.setCar$Ex(r4, r6)
            gnu.expr.GenericProc r4 = kawa.lib.lists.caddr
            java.lang.Object r2 = r4.apply1(r2)
            goto L_0x01bf
        L_0x01b1:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r8, (int) r3, (java.lang.Object) r4)
            throw r11
        L_0x01b8:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r8, (int) r3, (java.lang.Object) r4)
            throw r11
        L_0x01bf:
            gnu.kawa.functions.AddOp r4 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r6 = Lit8
            java.lang.Object r2 = r4.apply2(r2, r6)
        L_0x01c7:
            gnu.kawa.functions.NumberCompare r4 = kawa.standard.Scheme.numGEq
            java.lang.Object r4 = r4.apply2(r2, r12)
            java.lang.Boolean r6 = java.lang.Boolean.FALSE
            if (r4 == r6) goto L_0x01f9
            gnu.expr.GenericProc r11 = kawa.lib.lists.cdr
            java.lang.Object r10 = r11.apply1(r10)
            gnu.lists.Pair r10 = (gnu.lists.Pair) r10     // Catch:{ ClassCastException -> 0x01f2 }
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            kawa.lib.lists.setCar$Ex(r10, r11)
            gnu.expr.GenericProc r10 = kawa.lib.lists.cdr
            java.lang.Object r10 = r10.apply1(r1)
            gnu.lists.Pair r10 = (gnu.lists.Pair) r10     // Catch:{ ClassCastException -> 0x01eb }
            kawa.lib.lists.setCar$Ex(r10, r2)
            goto L_0x0276
        L_0x01eb:
            r11 = move-exception
            gnu.mapping.WrongType r12 = new gnu.mapping.WrongType
            r12.<init>((java.lang.ClassCastException) r11, (java.lang.String) r8, (int) r3, (java.lang.Object) r10)
            throw r12
        L_0x01f2:
            r11 = move-exception
            gnu.mapping.WrongType r12 = new gnu.mapping.WrongType
            r12.<init>((java.lang.ClassCastException) r11, (java.lang.String) r8, (int) r3, (java.lang.Object) r10)
            throw r12
        L_0x01f9:
            r4 = r11
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4     // Catch:{ ClassCastException -> 0x028d }
            r6 = r2
            java.lang.Number r6 = (java.lang.Number) r6     // Catch:{ ClassCastException -> 0x0286 }
            int r6 = r6.intValue()     // Catch:{ ClassCastException -> 0x0286 }
            char r4 = kawa.lib.strings.stringRef(r4, r6)
            gnu.text.Char r6 = gnu.text.Char.make(r4)
            boolean r6 = kawa.lib.rnrs.unicode.isCharWhitespace(r6)
            if (r6 == 0) goto L_0x0218
            java.lang.Object r6 = $Stpregexp$Mnspace$Mnsensitive$Qu$St
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            if (r6 != r7) goto L_0x0223
            goto L_0x021a
        L_0x0218:
            if (r6 == 0) goto L_0x0223
        L_0x021a:
            gnu.kawa.functions.AddOp r4 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r6 = Lit8
            java.lang.Object r2 = r4.apply2(r2, r6)
            goto L_0x01c7
        L_0x0223:
            gnu.text.Char r11 = gnu.text.Char.make(r4)
            gnu.text.Char r12 = Lit47
            boolean r11 = kawa.lib.characters.isChar$Eq(r11, r12)
            if (r11 == 0) goto L_0x025e
            gnu.expr.GenericProc r11 = kawa.lib.lists.cdr
            java.lang.Object r10 = r11.apply1(r10)
            gnu.lists.Pair r10 = (gnu.lists.Pair) r10     // Catch:{ ClassCastException -> 0x0257 }
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            kawa.lib.lists.setCar$Ex(r10, r11)
            gnu.expr.GenericProc r10 = kawa.lib.lists.cdr
            java.lang.Object r10 = r10.apply1(r1)
            gnu.lists.Pair r10 = (gnu.lists.Pair) r10     // Catch:{ ClassCastException -> 0x0250 }
            gnu.kawa.functions.AddOp r11 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r12 = Lit8
            java.lang.Object r11 = r11.apply2(r2, r12)
            kawa.lib.lists.setCar$Ex(r10, r11)
            goto L_0x0276
        L_0x0250:
            r11 = move-exception
            gnu.mapping.WrongType r12 = new gnu.mapping.WrongType
            r12.<init>((java.lang.ClassCastException) r11, (java.lang.String) r8, (int) r3, (java.lang.Object) r10)
            throw r12
        L_0x0257:
            r11 = move-exception
            gnu.mapping.WrongType r12 = new gnu.mapping.WrongType
            r12.<init>((java.lang.ClassCastException) r11, (java.lang.String) r8, (int) r3, (java.lang.Object) r10)
            throw r12
        L_0x025e:
            gnu.expr.GenericProc r11 = kawa.lib.lists.cdr
            java.lang.Object r10 = r11.apply1(r10)
            gnu.lists.Pair r10 = (gnu.lists.Pair) r10     // Catch:{ ClassCastException -> 0x027f }
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            kawa.lib.lists.setCar$Ex(r10, r11)
            gnu.expr.GenericProc r10 = kawa.lib.lists.cdr
            java.lang.Object r10 = r10.apply1(r1)
            gnu.lists.Pair r10 = (gnu.lists.Pair) r10     // Catch:{ ClassCastException -> 0x0278 }
            kawa.lib.lists.setCar$Ex(r10, r2)
        L_0x0276:
            r10 = r1
            goto L_0x0295
        L_0x0278:
            r11 = move-exception
            gnu.mapping.WrongType r12 = new gnu.mapping.WrongType
            r12.<init>((java.lang.ClassCastException) r11, (java.lang.String) r8, (int) r3, (java.lang.Object) r10)
            throw r12
        L_0x027f:
            r11 = move-exception
            gnu.mapping.WrongType r12 = new gnu.mapping.WrongType
            r12.<init>((java.lang.ClassCastException) r11, (java.lang.String) r8, (int) r3, (java.lang.Object) r10)
            throw r12
        L_0x0286:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r0, (int) r5, (java.lang.Object) r2)
            throw r11
        L_0x028d:
            r10 = move-exception
            gnu.mapping.WrongType r12 = new gnu.mapping.WrongType
            r12.<init>((java.lang.ClassCastException) r10, (java.lang.String) r0, (int) r3, (java.lang.Object) r11)
            throw r12
        L_0x0294:
        L_0x0295:
            return r10
        L_0x0296:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r0, (int) r5, (java.lang.Object) r2)
            throw r11
        L_0x029d:
            r10 = move-exception
            gnu.mapping.WrongType r12 = new gnu.mapping.WrongType
            r12.<init>((java.lang.ClassCastException) r10, (java.lang.String) r0, (int) r3, (java.lang.Object) r11)
            goto L_0x02a5
        L_0x02a4:
            throw r12
        L_0x02a5:
            goto L_0x02a4
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpWrapQuantifierIfAny(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0091  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x009e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object pregexpReadNums(java.lang.Object r10, java.lang.Object r11, java.lang.Object r12) {
        /*
            java.lang.String r0 = "list->string"
            java.lang.String r1 = "string-ref"
            gnu.lists.LList r2 = gnu.lists.LList.Empty
            gnu.lists.LList r3 = gnu.lists.LList.Empty
            gnu.math.IntNum r4 = Lit8
        L_0x000a:
            gnu.kawa.functions.NumberCompare r5 = kawa.standard.Scheme.numGEq
            java.lang.Object r5 = r5.apply2(r11, r12)
            java.lang.Boolean r6 = java.lang.Boolean.FALSE
            r7 = 0
            r8 = 1
            if (r5 == r6) goto L_0x001f
            java.lang.Object[] r5 = new java.lang.Object[r8]
            gnu.mapping.SimpleSymbol r6 = Lit76
            r5[r7] = r6
            pregexpError$V(r5)
        L_0x001f:
            r5 = r10
            java.lang.CharSequence r5 = (java.lang.CharSequence) r5     // Catch:{ ClassCastException -> 0x012f }
            r6 = r11
            java.lang.Number r6 = (java.lang.Number) r6     // Catch:{ ClassCastException -> 0x0127 }
            int r6 = r6.intValue()     // Catch:{ ClassCastException -> 0x0127 }
            char r5 = kawa.lib.strings.stringRef(r5, r6)
            gnu.text.Char r6 = gnu.text.Char.make(r5)
            boolean r6 = kawa.lib.rnrs.unicode.isCharNumeric(r6)
            if (r6 == 0) goto L_0x0069
            gnu.kawa.functions.NumberCompare r6 = kawa.standard.Scheme.numEqu
            gnu.math.IntNum r7 = Lit8
            java.lang.Object r4 = r6.apply2(r4, r7)
            java.lang.Boolean r6 = java.lang.Boolean.FALSE
            if (r4 == r6) goto L_0x0056
            gnu.text.Char r4 = gnu.text.Char.make(r5)
            gnu.lists.Pair r2 = kawa.lib.lists.cons(r4, r2)
            gnu.kawa.functions.AddOp r4 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r5 = Lit8
            java.lang.Object r11 = r4.apply2(r11, r5)
            gnu.math.IntNum r4 = Lit8
            goto L_0x000a
        L_0x0056:
            gnu.text.Char r4 = gnu.text.Char.make(r5)
            gnu.lists.Pair r3 = kawa.lib.lists.cons(r4, r3)
            gnu.kawa.functions.AddOp r4 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r5 = Lit8
            java.lang.Object r11 = r4.apply2(r11, r5)
            gnu.math.IntNum r4 = Lit16
            goto L_0x000a
        L_0x0069:
            gnu.text.Char r6 = gnu.text.Char.make(r5)
            boolean r6 = kawa.lib.rnrs.unicode.isCharWhitespace(r6)
            if (r6 == 0) goto L_0x007a
            java.lang.Object r6 = $Stpregexp$Mnspace$Mnsensitive$Qu$St
            java.lang.Boolean r9 = java.lang.Boolean.FALSE
            if (r6 != r9) goto L_0x0085
            goto L_0x007c
        L_0x007a:
            if (r6 == 0) goto L_0x0085
        L_0x007c:
            gnu.kawa.functions.AddOp r5 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r6 = Lit8
            java.lang.Object r11 = r5.apply2(r11, r6)
            goto L_0x000a
        L_0x0085:
            gnu.text.Char r6 = gnu.text.Char.make(r5)
            gnu.text.Char r9 = Lit77
            boolean r6 = kawa.lib.characters.isChar$Eq(r6, r9)
            if (r6 == 0) goto L_0x009e
            gnu.kawa.functions.NumberCompare r6 = kawa.standard.Scheme.numEqu
            gnu.math.IntNum r9 = Lit8
            java.lang.Object r6 = r6.apply2(r4, r9)
            java.lang.Boolean r9 = java.lang.Boolean.FALSE
            if (r6 == r9) goto L_0x00ac
            goto L_0x00a0
        L_0x009e:
            if (r6 == 0) goto L_0x00ac
        L_0x00a0:
            gnu.kawa.functions.AddOp r4 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r5 = Lit8
            java.lang.Object r11 = r4.apply2(r11, r5)
            gnu.math.IntNum r4 = Lit16
            goto L_0x000a
        L_0x00ac:
            gnu.text.Char r10 = gnu.text.Char.make(r5)
            gnu.text.Char r12 = Lit78
            boolean r10 = kawa.lib.characters.isChar$Eq(r10, r12)
            if (r10 == 0) goto L_0x0124
            java.lang.Object r10 = pregexpReverse$Ex(r2)
            gnu.lists.LList r10 = (gnu.lists.LList) r10     // Catch:{ ClassCastException -> 0x011d }
            java.lang.CharSequence r10 = kawa.lib.strings.list$To$String(r10)
            java.lang.Object r10 = kawa.lib.numbers.string$To$Number(r10)
            java.lang.Object r12 = pregexpReverse$Ex(r3)
            gnu.lists.LList r12 = (gnu.lists.LList) r12     // Catch:{ ClassCastException -> 0x0116 }
            java.lang.CharSequence r12 = kawa.lib.strings.list$To$String(r12)
            java.lang.Object r12 = kawa.lib.numbers.string$To$Number(r12)
            java.lang.Boolean r0 = java.lang.Boolean.FALSE     // Catch:{ ClassCastException -> 0x010c }
            if (r10 == r0) goto L_0x00d9
            r7 = 1
        L_0x00d9:
            int r7 = r7 + r8
            r0 = r7 & 1
            if (r0 == 0) goto L_0x00eb
            gnu.kawa.functions.NumberCompare r0 = kawa.standard.Scheme.numEqu
            gnu.math.IntNum r1 = Lit8
            java.lang.Object r0 = r0.apply2(r4, r1)
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            if (r0 == r1) goto L_0x00f6
            goto L_0x00ed
        L_0x00eb:
            if (r0 == 0) goto L_0x00f6
        L_0x00ed:
            gnu.math.IntNum r10 = Lit73
            java.lang.Boolean r12 = java.lang.Boolean.FALSE
            gnu.lists.Pair r10 = gnu.lists.LList.list3(r10, r12, r11)
            goto L_0x010b
        L_0x00f6:
            gnu.kawa.functions.NumberCompare r0 = kawa.standard.Scheme.numEqu
            gnu.math.IntNum r1 = Lit8
            java.lang.Object r0 = r0.apply2(r4, r1)
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            if (r0 == r1) goto L_0x0107
            gnu.lists.Pair r10 = gnu.lists.LList.list3(r10, r10, r11)
            goto L_0x010b
        L_0x0107:
            gnu.lists.Pair r10 = gnu.lists.LList.list3(r10, r12, r11)
        L_0x010b:
            goto L_0x0126
        L_0x010c:
            r11 = move-exception
            gnu.mapping.WrongType r12 = new gnu.mapping.WrongType
            java.lang.String r0 = "x"
            r1 = -2
            r12.<init>((java.lang.ClassCastException) r11, (java.lang.String) r0, (int) r1, (java.lang.Object) r10)
            throw r12
        L_0x0116:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r0, (int) r8, (java.lang.Object) r12)
            throw r11
        L_0x011d:
            r11 = move-exception
            gnu.mapping.WrongType r12 = new gnu.mapping.WrongType
            r12.<init>((java.lang.ClassCastException) r11, (java.lang.String) r0, (int) r8, (java.lang.Object) r10)
            throw r12
        L_0x0124:
            java.lang.Boolean r10 = java.lang.Boolean.FALSE
        L_0x0126:
            return r10
        L_0x0127:
            r10 = move-exception
            gnu.mapping.WrongType r12 = new gnu.mapping.WrongType
            r0 = 2
            r12.<init>((java.lang.ClassCastException) r10, (java.lang.String) r1, (int) r0, (java.lang.Object) r11)
            throw r12
        L_0x012f:
            r11 = move-exception
            gnu.mapping.WrongType r12 = new gnu.mapping.WrongType
            r12.<init>((java.lang.ClassCastException) r11, (java.lang.String) r1, (int) r8, (java.lang.Object) r10)
            goto L_0x0137
        L_0x0136:
            throw r12
        L_0x0137:
            goto L_0x0136
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpReadNums(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public static Object pregexpInvertCharList(Object vv) {
        Object apply1 = lists.car.apply1(vv);
        try {
            lists.setCar$Ex((Pair) apply1, Lit79);
            return vv;
        } catch (ClassCastException e) {
            throw new WrongType(e, "set-car!", 1, apply1);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00bc, code lost:
        if (r3 != false) goto L_0x0101;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ee, code lost:
        if (kawa.lib.characters.isChar$Eq(gnu.text.Char.make(kawa.lib.strings.stringRef((java.lang.CharSequence) r9, ((java.lang.Number) r3).intValue())), Lit46) != false) goto L_0x0101;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00ff, code lost:
        if (r4 != false) goto L_0x0101;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0113, code lost:
        r3 = kawa.lib.lists.car.apply1(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x011d, code lost:
        if (kawa.lib.characters.isChar(r3) == false) goto L_0x0161;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x011f, code lost:
        r2 = Lit83;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        r4 = (java.lang.CharSequence) r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0124, code lost:
        r5 = gnu.kawa.functions.AddOp.$Pl.apply2(r10, Lit8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0133, code lost:
        r1 = kawa.lib.lists.cons(gnu.lists.LList.list3(r2, r3, gnu.text.Char.make(kawa.lib.strings.stringRef(r4, ((java.lang.Number) r5).intValue()))), kawa.lib.lists.cdr.apply1(r1));
        r10 = gnu.kawa.functions.AddOp.$Pl.apply2(r10, Lit16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0153, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0159, code lost:
        throw new gnu.mapping.WrongType(r9, "string-ref", 2, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x015a, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0160, code lost:
        throw new gnu.mapping.WrongType(r10, "string-ref", 1, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0161, code lost:
        r1 = kawa.lib.lists.cons(gnu.text.Char.make(r2), r1);
        r10 = gnu.kawa.functions.AddOp.$Pl.apply2(r10, Lit8);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object pregexpReadCharList(java.lang.Object r9, java.lang.Object r10, java.lang.Object r11) {
        /*
            java.lang.String r0 = "string-ref"
            gnu.lists.LList r1 = gnu.lists.LList.Empty
        L_0x0004:
            gnu.kawa.functions.NumberCompare r2 = kawa.standard.Scheme.numGEq
            java.lang.Object r2 = r2.apply2(r10, r11)
            java.lang.Boolean r3 = java.lang.Boolean.FALSE
            r4 = 0
            r5 = 1
            r6 = 2
            if (r2 == r3) goto L_0x0021
            java.lang.Object[] r9 = new java.lang.Object[r6]
            gnu.mapping.SimpleSymbol r10 = Lit80
            r9[r4] = r10
            gnu.mapping.SimpleSymbol r10 = Lit81
            r9[r5] = r10
            java.lang.Object r9 = pregexpError$V(r9)
            goto L_0x00a5
        L_0x0021:
            r2 = r9
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2     // Catch:{ ClassCastException -> 0x0207 }
            r3 = r10
            java.lang.Number r3 = (java.lang.Number) r3     // Catch:{ ClassCastException -> 0x0200 }
            int r3 = r3.intValue()     // Catch:{ ClassCastException -> 0x0200 }
            char r2 = kawa.lib.strings.stringRef(r2, r3)
            gnu.kawa.functions.IsEqv r3 = kawa.standard.Scheme.isEqv
            gnu.text.Char r7 = gnu.text.Char.make(r2)
            gnu.text.Char r8 = Lit46
            java.lang.Object r3 = r3.apply2(r7, r8)
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            if (r3 == r7) goto L_0x006d
            boolean r3 = kawa.lib.lists.isNull(r1)
            if (r3 == 0) goto L_0x0056
            gnu.text.Char r2 = gnu.text.Char.make(r2)
            gnu.lists.Pair r1 = kawa.lib.lists.cons(r2, r1)
            gnu.kawa.functions.AddOp r2 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r3 = Lit8
            java.lang.Object r10 = r2.apply2(r10, r3)
            goto L_0x0004
        L_0x0056:
            gnu.mapping.SimpleSymbol r9 = Lit82
            java.lang.Object r11 = pregexpReverse$Ex(r1)
            gnu.lists.Pair r9 = kawa.lib.lists.cons(r9, r11)
            gnu.kawa.functions.AddOp r11 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r0 = Lit8
            java.lang.Object r10 = r11.apply2(r10, r0)
            gnu.lists.Pair r9 = gnu.lists.LList.list2(r9, r10)
            goto L_0x00a5
        L_0x006d:
            gnu.kawa.functions.IsEqv r3 = kawa.standard.Scheme.isEqv
            gnu.text.Char r7 = gnu.text.Char.make(r2)
            gnu.text.Char r8 = Lit19
            java.lang.Object r3 = r3.apply2(r7, r8)
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            if (r3 == r7) goto L_0x00a6
            java.lang.Object r10 = pregexpReadEscapedChar(r9, r10, r11)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            if (r10 == r2) goto L_0x0097
            gnu.expr.GenericProc r2 = kawa.lib.lists.car
            java.lang.Object r2 = r2.apply1(r10)
            gnu.lists.Pair r1 = kawa.lib.lists.cons(r2, r1)
            gnu.expr.GenericProc r2 = kawa.lib.lists.cadr
            java.lang.Object r10 = r2.apply1(r10)
            goto L_0x0004
        L_0x0097:
            java.lang.Object[] r9 = new java.lang.Object[r6]
            gnu.mapping.SimpleSymbol r10 = Lit80
            r9[r4] = r10
            gnu.mapping.SimpleSymbol r10 = Lit22
            r9[r5] = r10
            java.lang.Object r9 = pregexpError$V(r9)
        L_0x00a5:
            return r9
        L_0x00a6:
            gnu.kawa.functions.IsEqv r3 = kawa.standard.Scheme.isEqv
            gnu.text.Char r4 = gnu.text.Char.make(r2)
            gnu.text.Char r7 = Lit58
            java.lang.Object r3 = r3.apply2(r4, r7)
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
            if (r3 == r4) goto L_0x017d
            boolean r3 = kawa.lib.lists.isNull(r1)
            if (r3 == 0) goto L_0x00bf
            if (r3 == 0) goto L_0x0113
            goto L_0x0101
        L_0x00bf:
            gnu.kawa.functions.AddOp r3 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r4 = Lit8
            java.lang.Object r3 = r3.apply2(r10, r4)
            gnu.kawa.functions.NumberCompare r4 = kawa.standard.Scheme.numLss
            java.lang.Object r4 = r4.apply2(r3, r11)
            r7 = r4
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch:{ ClassCastException -> 0x0173 }
            boolean r4 = r7.booleanValue()     // Catch:{ ClassCastException -> 0x0173 }
            if (r4 == 0) goto L_0x00ff
            r4 = r9
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4     // Catch:{ ClassCastException -> 0x00f8 }
            r7 = r3
            java.lang.Number r7 = (java.lang.Number) r7     // Catch:{ ClassCastException -> 0x00f1 }
            int r3 = r7.intValue()     // Catch:{ ClassCastException -> 0x00f1 }
            char r3 = kawa.lib.strings.stringRef(r4, r3)
            gnu.text.Char r3 = gnu.text.Char.make(r3)
            gnu.text.Char r4 = Lit46
            boolean r3 = kawa.lib.characters.isChar$Eq(r3, r4)
            if (r3 == 0) goto L_0x0113
            goto L_0x0101
        L_0x00f1:
            r9 = move-exception
            gnu.mapping.WrongType r10 = new gnu.mapping.WrongType
            r10.<init>((java.lang.ClassCastException) r9, (java.lang.String) r0, (int) r6, (java.lang.Object) r3)
            throw r10
        L_0x00f8:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r0, (int) r5, (java.lang.Object) r9)
            throw r11
        L_0x00ff:
            if (r4 == 0) goto L_0x0113
        L_0x0101:
            gnu.text.Char r2 = gnu.text.Char.make(r2)
            gnu.lists.Pair r1 = kawa.lib.lists.cons(r2, r1)
            gnu.kawa.functions.AddOp r2 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r3 = Lit8
            java.lang.Object r10 = r2.apply2(r10, r3)
            goto L_0x0004
        L_0x0113:
            gnu.expr.GenericProc r3 = kawa.lib.lists.car
            java.lang.Object r3 = r3.apply1(r1)
            boolean r4 = kawa.lib.characters.isChar(r3)
            if (r4 == 0) goto L_0x0161
            gnu.mapping.SimpleSymbol r2 = Lit83
            r4 = r9
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4     // Catch:{ ClassCastException -> 0x015a }
            gnu.kawa.functions.AddOp r5 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r7 = Lit8
            java.lang.Object r5 = r5.apply2(r10, r7)
            r7 = r5
            java.lang.Number r7 = (java.lang.Number) r7     // Catch:{ ClassCastException -> 0x0153 }
            int r5 = r7.intValue()     // Catch:{ ClassCastException -> 0x0153 }
            char r4 = kawa.lib.strings.stringRef(r4, r5)
            gnu.text.Char r4 = gnu.text.Char.make(r4)
            gnu.lists.Pair r2 = gnu.lists.LList.list3(r2, r3, r4)
            gnu.expr.GenericProc r3 = kawa.lib.lists.cdr
            java.lang.Object r1 = r3.apply1(r1)
            gnu.lists.Pair r1 = kawa.lib.lists.cons(r2, r1)
            gnu.kawa.functions.AddOp r2 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r3 = Lit16
            java.lang.Object r10 = r2.apply2(r10, r3)
            goto L_0x0004
        L_0x0153:
            r9 = move-exception
            gnu.mapping.WrongType r10 = new gnu.mapping.WrongType
            r10.<init>((java.lang.ClassCastException) r9, (java.lang.String) r0, (int) r6, (java.lang.Object) r5)
            throw r10
        L_0x015a:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r0, (int) r5, (java.lang.Object) r9)
            throw r11
        L_0x0161:
            gnu.text.Char r2 = gnu.text.Char.make(r2)
            gnu.lists.Pair r1 = kawa.lib.lists.cons(r2, r1)
            gnu.kawa.functions.AddOp r2 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r3 = Lit8
            java.lang.Object r10 = r2.apply2(r10, r3)
            goto L_0x0004
        L_0x0173:
            r9 = move-exception
            gnu.mapping.WrongType r10 = new gnu.mapping.WrongType
            java.lang.String r11 = "x"
            r0 = -2
            r10.<init>((java.lang.ClassCastException) r9, (java.lang.String) r11, (int) r0, (java.lang.Object) r4)
            throw r10
        L_0x017d:
            gnu.kawa.functions.IsEqv r3 = kawa.standard.Scheme.isEqv
            gnu.text.Char r4 = gnu.text.Char.make(r2)
            gnu.text.Char r7 = Lit15
            java.lang.Object r3 = r3.apply2(r4, r7)
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
            if (r3 == r4) goto L_0x01ee
            r3 = r9
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3     // Catch:{ ClassCastException -> 0x01e7 }
            gnu.kawa.functions.AddOp r4 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r5 = Lit8
            java.lang.Object r4 = r4.apply2(r10, r5)
            r5 = r4
            java.lang.Number r5 = (java.lang.Number) r5     // Catch:{ ClassCastException -> 0x01e0 }
            int r4 = r5.intValue()     // Catch:{ ClassCastException -> 0x01e0 }
            char r3 = kawa.lib.strings.stringRef(r3, r4)
            gnu.text.Char r3 = gnu.text.Char.make(r3)
            gnu.text.Char r4 = Lit44
            boolean r3 = kawa.lib.characters.isChar$Eq(r3, r4)
            if (r3 == 0) goto L_0x01ce
            gnu.kawa.functions.AddOp r2 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r3 = Lit16
            java.lang.Object r10 = r2.apply2(r10, r3)
            java.lang.Object r10 = pregexpReadPosixCharClass(r9, r10, r11)
            gnu.expr.GenericProc r2 = kawa.lib.lists.car
            java.lang.Object r2 = r2.apply1(r10)
            gnu.lists.Pair r1 = kawa.lib.lists.cons(r2, r1)
            gnu.expr.GenericProc r2 = kawa.lib.lists.cadr
            java.lang.Object r10 = r2.apply1(r10)
            goto L_0x0004
        L_0x01ce:
            gnu.text.Char r2 = gnu.text.Char.make(r2)
            gnu.lists.Pair r1 = kawa.lib.lists.cons(r2, r1)
            gnu.kawa.functions.AddOp r2 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r3 = Lit8
            java.lang.Object r10 = r2.apply2(r10, r3)
            goto L_0x0004
        L_0x01e0:
            r9 = move-exception
            gnu.mapping.WrongType r10 = new gnu.mapping.WrongType
            r10.<init>((java.lang.ClassCastException) r9, (java.lang.String) r0, (int) r6, (java.lang.Object) r4)
            throw r10
        L_0x01e7:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r0, (int) r5, (java.lang.Object) r9)
            throw r11
        L_0x01ee:
            gnu.text.Char r2 = gnu.text.Char.make(r2)
            gnu.lists.Pair r1 = kawa.lib.lists.cons(r2, r1)
            gnu.kawa.functions.AddOp r2 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r3 = Lit8
            java.lang.Object r10 = r2.apply2(r10, r3)
            goto L_0x0004
        L_0x0200:
            r9 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r9, (java.lang.String) r0, (int) r6, (java.lang.Object) r10)
            throw r11
        L_0x0207:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r0, (int) r5, (java.lang.Object) r9)
            goto L_0x020f
        L_0x020e:
            throw r11
        L_0x020f:
            goto L_0x020e
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpReadCharList(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public static Object pregexpStringMatch(Object s1, Object s, Object i, Object n, Object sk, Object fk) {
        try {
            int n1 = strings.stringLength((CharSequence) s1);
            if (Scheme.numGrt.apply2(Integer.valueOf(n1), n) != Boolean.FALSE) {
                return Scheme.applyToArgs.apply1(fk);
            }
            Object j = Lit73;
            Object k = i;
            while (Scheme.numGEq.apply2(j, Integer.valueOf(n1)) == Boolean.FALSE) {
                if (Scheme.numGEq.apply2(k, n) != Boolean.FALSE) {
                    return Scheme.applyToArgs.apply1(fk);
                }
                try {
                    try {
                        try {
                            try {
                                if (!characters.isChar$Eq(Char.make(strings.stringRef((CharSequence) s1, ((Number) j).intValue())), Char.make(strings.stringRef((CharSequence) s, ((Number) k).intValue())))) {
                                    return Scheme.applyToArgs.apply1(fk);
                                }
                                j = AddOp.$Pl.apply2(j, Lit8);
                                k = AddOp.$Pl.apply2(k, Lit8);
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-ref", 2, k);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "string-ref", 1, s);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "string-ref", 2, j);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "string-ref", 1, s1);
                }
            }
            return Scheme.applyToArgs.apply2(sk, k);
        } catch (ClassCastException e5) {
            throw new WrongType(e5, "string-length", 1, s1);
        }
    }

    public static boolean isPregexpCharWord(Object obj) {
        try {
            boolean isCharAlphabetic = unicode.isCharAlphabetic((Char) obj);
            if (isCharAlphabetic) {
                return isCharAlphabetic;
            }
            try {
                boolean isCharNumeric = unicode.isCharNumeric((Char) obj);
                if (isCharNumeric) {
                    return isCharNumeric;
                }
                try {
                    return characters.isChar$Eq((Char) obj, Lit84);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "char=?", 1, obj);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "char-numeric?", 1, obj);
            }
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "char-alphabetic?", 1, obj);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002f, code lost:
        if (r9 != false) goto L_0x0018;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0016, code lost:
        if (r2 != false) goto L_0x0018;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:?, code lost:
        return java.lang.Boolean.FALSE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object isPregexpAtWordBoundary(java.lang.Object r7, java.lang.Object r8, java.lang.Object r9) {
        /*
            java.lang.String r0 = "x"
            java.lang.String r1 = "string-ref"
            gnu.kawa.functions.NumberCompare r2 = kawa.standard.Scheme.numEqu
            gnu.math.IntNum r3 = Lit73
            java.lang.Object r2 = r2.apply2(r8, r3)
            r3 = -2
            r4 = r2
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ ClassCastException -> 0x00be }
            boolean r2 = r4.booleanValue()     // Catch:{ ClassCastException -> 0x00be }
            if (r2 == 0) goto L_0x0020
            if (r2 == 0) goto L_0x001c
        L_0x0018:
            java.lang.Boolean r7 = java.lang.Boolean.TRUE
            goto L_0x0093
        L_0x001c:
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            goto L_0x0093
        L_0x0020:
            gnu.kawa.functions.NumberCompare r2 = kawa.standard.Scheme.numGEq
            java.lang.Object r9 = r2.apply2(r8, r9)
            r2 = r9
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch:{ ClassCastException -> 0x00b7 }
            boolean r9 = r2.booleanValue()     // Catch:{ ClassCastException -> 0x00b7 }
            if (r9 == 0) goto L_0x0032
            if (r9 == 0) goto L_0x001c
            goto L_0x0018
        L_0x0032:
            r9 = 1
            r2 = r7
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2     // Catch:{ ClassCastException -> 0x00b0 }
            r4 = 2
            r5 = r8
            java.lang.Number r5 = (java.lang.Number) r5     // Catch:{ ClassCastException -> 0x00a9 }
            int r5 = r5.intValue()     // Catch:{ ClassCastException -> 0x00a9 }
            char r2 = kawa.lib.strings.stringRef(r2, r5)
            java.lang.CharSequence r7 = (java.lang.CharSequence) r7     // Catch:{ ClassCastException -> 0x00a2 }
            gnu.kawa.functions.AddOp r5 = gnu.kawa.functions.AddOp.$Mn
            gnu.math.IntNum r6 = Lit8
            java.lang.Object r8 = r5.apply2(r8, r6)
            r5 = r8
            java.lang.Number r5 = (java.lang.Number) r5     // Catch:{ ClassCastException -> 0x009b }
            int r8 = r5.intValue()     // Catch:{ ClassCastException -> 0x009b }
            char r7 = kawa.lib.strings.stringRef(r7, r8)
            gnu.text.Char r8 = gnu.text.Char.make(r2)
            gnu.mapping.SimpleSymbol r1 = Lit41
            java.lang.Object r8 = isPregexpCheckIfInCharClass(r8, r1)
            gnu.text.Char r7 = gnu.text.Char.make(r7)
            gnu.mapping.SimpleSymbol r1 = Lit41
            java.lang.Object r7 = isPregexpCheckIfInCharClass(r7, r1)
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            if (r8 == r1) goto L_0x0079
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            if (r7 == r1) goto L_0x0076
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            goto L_0x007a
        L_0x0076:
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            goto L_0x007a
        L_0x0079:
            r1 = r8
        L_0x007a:
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            if (r1 == r2) goto L_0x0080
            r7 = r1
            goto L_0x0093
        L_0x0080:
            java.lang.Boolean r0 = java.lang.Boolean.FALSE     // Catch:{ ClassCastException -> 0x0094 }
            if (r8 == r0) goto L_0x0086
            r8 = 1
            goto L_0x0087
        L_0x0086:
            r8 = 0
        L_0x0087:
            int r8 = r8 + r9
            r8 = r8 & r9
            if (r8 == 0) goto L_0x008c
            goto L_0x0093
        L_0x008c:
            if (r8 == 0) goto L_0x0091
            java.lang.Boolean r7 = java.lang.Boolean.TRUE
            goto L_0x0093
        L_0x0091:
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
        L_0x0093:
            return r7
        L_0x0094:
            r7 = move-exception
            gnu.mapping.WrongType r9 = new gnu.mapping.WrongType
            r9.<init>((java.lang.ClassCastException) r7, (java.lang.String) r0, (int) r3, (java.lang.Object) r8)
            throw r9
        L_0x009b:
            r7 = move-exception
            gnu.mapping.WrongType r9 = new gnu.mapping.WrongType
            r9.<init>((java.lang.ClassCastException) r7, (java.lang.String) r1, (int) r4, (java.lang.Object) r8)
            throw r9
        L_0x00a2:
            r8 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r8, (java.lang.String) r1, (int) r9, (java.lang.Object) r7)
            throw r0
        L_0x00a9:
            r7 = move-exception
            gnu.mapping.WrongType r9 = new gnu.mapping.WrongType
            r9.<init>((java.lang.ClassCastException) r7, (java.lang.String) r1, (int) r4, (java.lang.Object) r8)
            throw r9
        L_0x00b0:
            r8 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r8, (java.lang.String) r1, (int) r9, (java.lang.Object) r7)
            throw r0
        L_0x00b7:
            r7 = move-exception
            gnu.mapping.WrongType r8 = new gnu.mapping.WrongType
            r8.<init>((java.lang.ClassCastException) r7, (java.lang.String) r0, (int) r3, (java.lang.Object) r9)
            throw r8
        L_0x00be:
            r7 = move-exception
            gnu.mapping.WrongType r8 = new gnu.mapping.WrongType
            r8.<init>((java.lang.ClassCastException) r7, (java.lang.String) r0, (int) r3, (java.lang.Object) r2)
            goto L_0x00c6
        L_0x00c5:
            throw r8
        L_0x00c6:
            goto L_0x00c5
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.isPregexpAtWordBoundary(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:226:0x02a8, code lost:
        if (r12 != false) goto L_0x02aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:234:0x02bb, code lost:
        if (r12 != false) goto L_0x02aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:257:0x02fa, code lost:
        if (r12 != false) goto L_0x02fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:265:0x030f, code lost:
        if (r12 != false) goto L_0x02fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:271:0x031d, code lost:
        if (r12 != false) goto L_0x02fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:277:0x032b, code lost:
        if (r12 != false) goto L_0x02fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:283:0x0339, code lost:
        if (r12 != false) goto L_0x02fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:289:0x0347, code lost:
        if (r12 != false) goto L_0x02fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:358:?, code lost:
        return java.lang.Boolean.FALSE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:362:?, code lost:
        return java.lang.Boolean.FALSE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object isPregexpCheckIfInCharClass(java.lang.Object r11, java.lang.Object r12) {
        /*
            java.lang.String r0 = "char-ci=?"
            gnu.kawa.functions.IsEqv r1 = kawa.standard.Scheme.isEqv
            gnu.mapping.SimpleSymbol r2 = Lit14
            java.lang.Object r1 = r1.apply2(r12, r2)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            java.lang.String r3 = "char=?"
            r4 = 1
            if (r1 == r2) goto L_0x002a
            gnu.text.Char r11 = (gnu.text.Char) r11     // Catch:{ ClassCastException -> 0x0023 }
            gnu.text.Char r12 = Lit24
            boolean r11 = kawa.lib.characters.isChar$Eq(r11, r12)
            if (r11 == 0) goto L_0x001f
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x001f:
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x0023:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r3, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x002a:
            gnu.kawa.functions.IsEqv r1 = kawa.standard.Scheme.isEqv
            gnu.mapping.SimpleSymbol r2 = Lit85
            java.lang.Object r1 = r1.apply2(r12, r2)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            java.lang.String r5 = "char-alphabetic?"
            java.lang.String r6 = "char-numeric?"
            if (r1 == r2) goto L_0x006b
            r12 = r11
            gnu.text.Char r12 = (gnu.text.Char) r12     // Catch:{ ClassCastException -> 0x0064 }
            boolean r12 = kawa.lib.rnrs.unicode.isCharAlphabetic(r12)
            if (r12 == 0) goto L_0x004d
            if (r12 == 0) goto L_0x0049
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x0049:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x004d:
            gnu.text.Char r11 = (gnu.text.Char) r11     // Catch:{ ClassCastException -> 0x005d }
            boolean r11 = kawa.lib.rnrs.unicode.isCharNumeric(r11)
            if (r11 == 0) goto L_0x0059
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x0059:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x005d:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r6, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x0064:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r5, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x006b:
            gnu.kawa.functions.IsEqv r1 = kawa.standard.Scheme.isEqv
            gnu.mapping.SimpleSymbol r2 = Lit86
            java.lang.Object r1 = r1.apply2(r12, r2)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            if (r1 == r2) goto L_0x008e
            gnu.text.Char r11 = (gnu.text.Char) r11     // Catch:{ ClassCastException -> 0x0087 }
            boolean r11 = kawa.lib.rnrs.unicode.isCharAlphabetic(r11)
            if (r11 == 0) goto L_0x0083
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x0083:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x0087:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r5, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x008e:
            gnu.kawa.functions.IsEqv r1 = kawa.standard.Scheme.isEqv
            gnu.mapping.SimpleSymbol r2 = Lit87
            java.lang.Object r1 = r1.apply2(r12, r2)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            java.lang.String r7 = "char->integer"
            if (r1 == r2) goto L_0x00b5
            gnu.text.Char r11 = (gnu.text.Char) r11     // Catch:{ ClassCastException -> 0x00ae }
            int r11 = kawa.lib.characters.char$To$Integer(r11)
            r12 = 128(0x80, float:1.794E-43)
            if (r11 >= r12) goto L_0x00aa
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x00aa:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x00ae:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r7, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x00b5:
            gnu.kawa.functions.IsEqv r1 = kawa.standard.Scheme.isEqv
            gnu.mapping.SimpleSymbol r2 = Lit88
            java.lang.Object r1 = r1.apply2(r12, r2)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            if (r1 == r2) goto L_0x0100
            r12 = r11
            gnu.text.Char r12 = (gnu.text.Char) r12     // Catch:{ ClassCastException -> 0x00f9 }
            gnu.text.Char r0 = Lit3
            boolean r12 = kawa.lib.characters.isChar$Eq(r12, r0)
            if (r12 == 0) goto L_0x00d6
            if (r12 == 0) goto L_0x00d2
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x00d2:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x00d6:
            gnu.text.Char r11 = (gnu.text.Char) r11     // Catch:{ ClassCastException -> 0x00f2 }
            java.lang.Object r12 = $Stpregexp$Mntab$Mnchar$St
            gnu.text.Char r12 = (gnu.text.Char) r12     // Catch:{ ClassCastException -> 0x00ea }
            boolean r11 = kawa.lib.characters.isChar$Eq(r11, r12)
            if (r11 == 0) goto L_0x00e6
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x00e6:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x00ea:
            r11 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r1 = 2
            r0.<init>((java.lang.ClassCastException) r11, (java.lang.String) r3, (int) r1, (java.lang.Object) r12)
            throw r0
        L_0x00f2:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r3, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x00f9:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r3, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x0100:
            gnu.kawa.functions.IsEqv r1 = kawa.standard.Scheme.isEqv
            gnu.mapping.SimpleSymbol r2 = Lit89
            java.lang.Object r1 = r1.apply2(r12, r2)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            r8 = 32
            if (r1 == r2) goto L_0x0125
            gnu.text.Char r11 = (gnu.text.Char) r11     // Catch:{ ClassCastException -> 0x011e }
            int r11 = kawa.lib.characters.char$To$Integer(r11)
            if (r11 >= r8) goto L_0x011a
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x011a:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x011e:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r7, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x0125:
            gnu.kawa.functions.IsEqv r1 = kawa.standard.Scheme.isEqv
            gnu.mapping.SimpleSymbol r2 = Lit30
            java.lang.Object r1 = r1.apply2(r12, r2)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            if (r1 == r2) goto L_0x0148
            gnu.text.Char r11 = (gnu.text.Char) r11     // Catch:{ ClassCastException -> 0x0141 }
            boolean r11 = kawa.lib.rnrs.unicode.isCharNumeric(r11)
            if (r11 == 0) goto L_0x013d
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x013d:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x0141:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r6, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x0148:
            gnu.kawa.functions.IsEqv r1 = kawa.standard.Scheme.isEqv
            gnu.mapping.SimpleSymbol r2 = Lit90
            java.lang.Object r1 = r1.apply2(r12, r2)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            java.lang.String r9 = "char-whitespace?"
            r10 = 0
            if (r1 == r2) goto L_0x018b
            r12 = r11
            gnu.text.Char r12 = (gnu.text.Char) r12     // Catch:{ ClassCastException -> 0x0184 }
            int r12 = kawa.lib.characters.char$To$Integer(r12)
            if (r12 < r8) goto L_0x0161
            r10 = 1
        L_0x0161:
            if (r10 == 0) goto L_0x017a
            gnu.text.Char r11 = (gnu.text.Char) r11     // Catch:{ ClassCastException -> 0x0173 }
            boolean r11 = kawa.lib.rnrs.unicode.isCharWhitespace(r11)
            if (r11 == 0) goto L_0x016f
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x016f:
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x0173:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r9, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x017a:
            if (r10 == 0) goto L_0x0180
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x0180:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x0184:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r7, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x018b:
            gnu.kawa.functions.IsEqv r1 = kawa.standard.Scheme.isEqv
            gnu.mapping.SimpleSymbol r2 = Lit91
            java.lang.Object r1 = r1.apply2(r12, r2)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            if (r1 == r2) goto L_0x01b0
            gnu.text.Char r11 = (gnu.text.Char) r11     // Catch:{ ClassCastException -> 0x01a7 }
            boolean r11 = kawa.lib.rnrs.unicode.isCharLowerCase(r11)
            if (r11 == 0) goto L_0x01a3
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x01a3:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x01a7:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            java.lang.String r1 = "char-lower-case?"
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r1, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x01b0:
            gnu.kawa.functions.IsEqv r1 = kawa.standard.Scheme.isEqv
            gnu.mapping.SimpleSymbol r2 = Lit92
            java.lang.Object r1 = r1.apply2(r12, r2)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            if (r1 == r2) goto L_0x01d3
            gnu.text.Char r11 = (gnu.text.Char) r11     // Catch:{ ClassCastException -> 0x01cc }
            int r11 = kawa.lib.characters.char$To$Integer(r11)
            if (r11 < r8) goto L_0x01c8
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x01c8:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x01cc:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r7, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x01d3:
            gnu.kawa.functions.IsEqv r1 = kawa.standard.Scheme.isEqv
            gnu.mapping.SimpleSymbol r2 = Lit93
            java.lang.Object r1 = r1.apply2(r12, r2)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            if (r1 == r2) goto L_0x024b
            r12 = r11
            gnu.text.Char r12 = (gnu.text.Char) r12     // Catch:{ ClassCastException -> 0x0244 }
            int r12 = kawa.lib.characters.char$To$Integer(r12)
            if (r12 < r8) goto L_0x01e9
            r10 = 1
        L_0x01e9:
            if (r10 == 0) goto L_0x023a
            r12 = r11
            gnu.text.Char r12 = (gnu.text.Char) r12     // Catch:{ ClassCastException -> 0x0233 }
            boolean r12 = kawa.lib.rnrs.unicode.isCharWhitespace(r12)
            int r12 = r12 + r4
            r12 = r12 & r4
            if (r12 == 0) goto L_0x0229
            r12 = r11
            gnu.text.Char r12 = (gnu.text.Char) r12     // Catch:{ ClassCastException -> 0x0222 }
            boolean r12 = kawa.lib.rnrs.unicode.isCharAlphabetic(r12)
            int r12 = r12 + r4
            r12 = r12 & r4
            if (r12 == 0) goto L_0x0218
            gnu.text.Char r11 = (gnu.text.Char) r11     // Catch:{ ClassCastException -> 0x0211 }
            boolean r11 = kawa.lib.rnrs.unicode.isCharNumeric(r11)
            if (r11 == 0) goto L_0x020d
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x020d:
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x0211:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r6, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x0218:
            if (r12 == 0) goto L_0x021e
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x021e:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x0222:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r5, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x0229:
            if (r12 == 0) goto L_0x022f
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x022f:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x0233:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r9, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x023a:
            if (r10 == 0) goto L_0x0240
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x0240:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x0244:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r7, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x024b:
            gnu.kawa.functions.IsEqv r1 = kawa.standard.Scheme.isEqv
            gnu.mapping.SimpleSymbol r2 = Lit36
            java.lang.Object r1 = r1.apply2(r12, r2)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            if (r1 == r2) goto L_0x026e
            gnu.text.Char r11 = (gnu.text.Char) r11     // Catch:{ ClassCastException -> 0x0267 }
            boolean r11 = kawa.lib.rnrs.unicode.isCharWhitespace(r11)
            if (r11 == 0) goto L_0x0263
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x0263:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x0267:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r9, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x026e:
            gnu.kawa.functions.IsEqv r1 = kawa.standard.Scheme.isEqv
            gnu.mapping.SimpleSymbol r2 = Lit94
            java.lang.Object r1 = r1.apply2(r12, r2)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            if (r1 == r2) goto L_0x0293
            gnu.text.Char r11 = (gnu.text.Char) r11     // Catch:{ ClassCastException -> 0x028a }
            boolean r11 = kawa.lib.rnrs.unicode.isCharUpperCase(r11)
            if (r11 == 0) goto L_0x0286
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x0286:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x028a:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            java.lang.String r1 = "char-upper-case?"
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r1, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x0293:
            gnu.kawa.functions.IsEqv r1 = kawa.standard.Scheme.isEqv
            gnu.mapping.SimpleSymbol r2 = Lit41
            java.lang.Object r1 = r1.apply2(r12, r2)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            if (r1 == r2) goto L_0x02e5
            r12 = r11
            gnu.text.Char r12 = (gnu.text.Char) r12     // Catch:{ ClassCastException -> 0x02de }
            boolean r12 = kawa.lib.rnrs.unicode.isCharAlphabetic(r12)
            if (r12 == 0) goto L_0x02b2
            if (r12 == 0) goto L_0x02ae
        L_0x02aa:
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x02ae:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x02b2:
            r12 = r11
            gnu.text.Char r12 = (gnu.text.Char) r12     // Catch:{ ClassCastException -> 0x02d7 }
            boolean r12 = kawa.lib.rnrs.unicode.isCharNumeric(r12)
            if (r12 == 0) goto L_0x02be
            if (r12 == 0) goto L_0x02ae
            goto L_0x02aa
        L_0x02be:
            gnu.text.Char r11 = (gnu.text.Char) r11     // Catch:{ ClassCastException -> 0x02d0 }
            gnu.text.Char r12 = Lit84
            boolean r11 = kawa.lib.characters.isChar$Eq(r11, r12)
            if (r11 == 0) goto L_0x02cc
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x02cc:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x02d0:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r3, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x02d7:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r6, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x02de:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r5, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x02e5:
            gnu.kawa.functions.IsEqv r1 = kawa.standard.Scheme.isEqv
            gnu.mapping.SimpleSymbol r2 = Lit95
            java.lang.Object r12 = r1.apply2(r12, r2)
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            if (r12 == r1) goto L_0x038b
            r12 = r11
            gnu.text.Char r12 = (gnu.text.Char) r12     // Catch:{ ClassCastException -> 0x0384 }
            boolean r12 = kawa.lib.rnrs.unicode.isCharNumeric(r12)
            if (r12 == 0) goto L_0x0304
            if (r12 == 0) goto L_0x0300
        L_0x02fc:
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x0300:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x0304:
            r12 = r11
            gnu.text.Char r12 = (gnu.text.Char) r12     // Catch:{ ClassCastException -> 0x037d }
            gnu.text.Char r1 = Lit2
            boolean r12 = kawa.lib.rnrs.unicode.isCharCi$Eq(r12, r1)
            if (r12 == 0) goto L_0x0312
            if (r12 == 0) goto L_0x0300
            goto L_0x02fc
        L_0x0312:
            r12 = r11
            gnu.text.Char r12 = (gnu.text.Char) r12     // Catch:{ ClassCastException -> 0x0376 }
            gnu.text.Char r1 = Lit25
            boolean r12 = kawa.lib.rnrs.unicode.isCharCi$Eq(r12, r1)
            if (r12 == 0) goto L_0x0320
            if (r12 == 0) goto L_0x0300
            goto L_0x02fc
        L_0x0320:
            r12 = r11
            gnu.text.Char r12 = (gnu.text.Char) r12     // Catch:{ ClassCastException -> 0x036f }
            gnu.text.Char r1 = Lit96
            boolean r12 = kawa.lib.rnrs.unicode.isCharCi$Eq(r12, r1)
            if (r12 == 0) goto L_0x032e
            if (r12 == 0) goto L_0x0300
            goto L_0x02fc
        L_0x032e:
            r12 = r11
            gnu.text.Char r12 = (gnu.text.Char) r12     // Catch:{ ClassCastException -> 0x0368 }
            gnu.text.Char r1 = Lit29
            boolean r12 = kawa.lib.rnrs.unicode.isCharCi$Eq(r12, r1)
            if (r12 == 0) goto L_0x033c
            if (r12 == 0) goto L_0x0300
            goto L_0x02fc
        L_0x033c:
            r12 = r11
            gnu.text.Char r12 = (gnu.text.Char) r12     // Catch:{ ClassCastException -> 0x0361 }
            gnu.text.Char r1 = Lit97
            boolean r12 = kawa.lib.rnrs.unicode.isCharCi$Eq(r12, r1)
            if (r12 == 0) goto L_0x034a
            if (r12 == 0) goto L_0x0300
            goto L_0x02fc
        L_0x034a:
            gnu.text.Char r11 = (gnu.text.Char) r11     // Catch:{ ClassCastException -> 0x035a }
            gnu.text.Char r12 = Lit98
            boolean r11 = kawa.lib.rnrs.unicode.isCharCi$Eq(r11, r12)
            if (r11 == 0) goto L_0x0357
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            goto L_0x0395
        L_0x0357:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            goto L_0x0395
        L_0x035a:
            r12 = move-exception
            gnu.mapping.WrongType r1 = new gnu.mapping.WrongType
            r1.<init>((java.lang.ClassCastException) r12, (java.lang.String) r0, (int) r4, (java.lang.Object) r11)
            throw r1
        L_0x0361:
            r12 = move-exception
            gnu.mapping.WrongType r1 = new gnu.mapping.WrongType
            r1.<init>((java.lang.ClassCastException) r12, (java.lang.String) r0, (int) r4, (java.lang.Object) r11)
            throw r1
        L_0x0368:
            r12 = move-exception
            gnu.mapping.WrongType r1 = new gnu.mapping.WrongType
            r1.<init>((java.lang.ClassCastException) r12, (java.lang.String) r0, (int) r4, (java.lang.Object) r11)
            throw r1
        L_0x036f:
            r12 = move-exception
            gnu.mapping.WrongType r1 = new gnu.mapping.WrongType
            r1.<init>((java.lang.ClassCastException) r12, (java.lang.String) r0, (int) r4, (java.lang.Object) r11)
            throw r1
        L_0x0376:
            r12 = move-exception
            gnu.mapping.WrongType r1 = new gnu.mapping.WrongType
            r1.<init>((java.lang.ClassCastException) r12, (java.lang.String) r0, (int) r4, (java.lang.Object) r11)
            throw r1
        L_0x037d:
            r12 = move-exception
            gnu.mapping.WrongType r1 = new gnu.mapping.WrongType
            r1.<init>((java.lang.ClassCastException) r12, (java.lang.String) r0, (int) r4, (java.lang.Object) r11)
            throw r1
        L_0x0384:
            r12 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r12, (java.lang.String) r6, (int) r4, (java.lang.Object) r11)
            throw r0
        L_0x038b:
            java.lang.Object[] r11 = new java.lang.Object[r4]
            gnu.mapping.SimpleSymbol r12 = Lit99
            r11[r10] = r12
            java.lang.Object r11 = pregexpError$V(r11)
        L_0x0395:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.isPregexpCheckIfInCharClass(java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 33:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 34:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 47:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static Object pregexpListRef(Object s, Object i) {
        Object k = Lit73;
        Object s2 = s;
        while (!lists.isNull(s2)) {
            if (Scheme.numEqu.apply2(k, i) != Boolean.FALSE) {
                return lists.car.apply1(s2);
            }
            s2 = lists.cdr.apply1(s2);
            k = AddOp.$Pl.apply2(k, Lit8);
        }
        return Boolean.FALSE;
    }

    public static Object pregexpMakeBackrefList(Object re) {
        return lambda1sub(re);
    }

    public static Object lambda1sub(Object re) {
        if (!lists.isPair(re)) {
            return LList.Empty;
        }
        Object car$Mnre = lists.car.apply1(re);
        Object sub$Mncdr$Mnre = lambda1sub(lists.cdr.apply1(re));
        if (Scheme.isEqv.apply2(car$Mnre, Lit100) != Boolean.FALSE) {
            return lists.cons(lists.cons(re, Boolean.FALSE), sub$Mncdr$Mnre);
        }
        return append.append$V(new Object[]{lambda1sub(car$Mnre), sub$Mncdr$Mnre});
    }

    /* compiled from: pregexp.scm */
    public class frame extends ModuleBody {
        Object backrefs;
        Object case$Mnsensitive$Qu;
        Procedure identity;
        Object n;
        Object s;
        Object sn;
        Object start;

        public frame() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 15, pregexp.Lit112, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:460");
            this.identity = moduleMethod;
        }

        public static Object lambda2identity(Object x) {
            return x;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 15 ? lambda2identity(obj) : super.apply1(moduleMethod, obj);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 15) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }

        static Boolean lambda4() {
            return Boolean.FALSE;
        }

        /* JADX WARNING: Removed duplicated region for block: B:108:0x027a  */
        /* JADX WARNING: Removed duplicated region for block: B:225:0x0612  */
        /* JADX WARNING: Removed duplicated region for block: B:65:0x019e  */
        /* JADX WARNING: Removed duplicated region for block: B:66:0x01b2  */
        /* JADX WARNING: Removed duplicated region for block: B:75:0x01d2  */
        /* JADX WARNING: Removed duplicated region for block: B:81:0x01fa A[ADDED_TO_REGION] */
        /* JADX WARNING: Removed duplicated region for block: B:90:0x0213  */
        /* JADX WARNING: Removed duplicated region for block: B:91:0x0216  */
        /* JADX WARNING: Removed duplicated region for block: B:98:0x0245  */
        /* JADX WARNING: Removed duplicated region for block: B:99:0x0259  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object lambda3sub(java.lang.Object r9, java.lang.Object r10, java.lang.Object r11, java.lang.Object r12) {
            /*
                r8 = this;
                java.lang.String r0 = "substring"
                gnu.kawa.slib.pregexp$frame0 r1 = new gnu.kawa.slib.pregexp$frame0
                r1.<init>()
                r1.staticLink = r8
                r1.re$1 = r9
                r1.i = r10
                r1.sk = r11
                r1.fk = r12
                gnu.kawa.functions.IsEqv r9 = kawa.standard.Scheme.isEqv
                java.lang.Object r10 = r1.re$1
                gnu.mapping.SimpleSymbol r11 = gnu.kawa.slib.pregexp.Lit10
                java.lang.Object r9 = r9.apply2(r10, r11)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x0043
                gnu.kawa.functions.NumberCompare r9 = kawa.standard.Scheme.numEqu
                java.lang.Object r10 = r1.i
                java.lang.Object r11 = r8.start
                java.lang.Object r9 = r9.apply2(r10, r11)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x0039
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.sk
                java.lang.Object r11 = r1.i
                java.lang.Object r9 = r9.apply2(r10, r11)
                goto L_0x0633
            L_0x0039:
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.fk
                java.lang.Object r9 = r9.apply1(r10)
                goto L_0x0633
            L_0x0043:
                gnu.kawa.functions.IsEqv r9 = kawa.standard.Scheme.isEqv
                java.lang.Object r10 = r1.re$1
                gnu.mapping.SimpleSymbol r11 = gnu.kawa.slib.pregexp.Lit12
                java.lang.Object r9 = r9.apply2(r10, r11)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x0075
                gnu.kawa.functions.NumberCompare r9 = kawa.standard.Scheme.numGEq
                java.lang.Object r10 = r1.i
                java.lang.Object r11 = r8.n
                java.lang.Object r9 = r9.apply2(r10, r11)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x006b
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.sk
                java.lang.Object r11 = r1.i
                java.lang.Object r9 = r9.apply2(r10, r11)
                goto L_0x0633
            L_0x006b:
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.fk
                java.lang.Object r9 = r9.apply1(r10)
                goto L_0x0633
            L_0x0075:
                gnu.kawa.functions.IsEqv r9 = kawa.standard.Scheme.isEqv
                java.lang.Object r10 = r1.re$1
                gnu.mapping.SimpleSymbol r11 = gnu.kawa.slib.pregexp.Lit23
                java.lang.Object r9 = r9.apply2(r10, r11)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x008f
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.sk
                java.lang.Object r11 = r1.i
                java.lang.Object r9 = r9.apply2(r10, r11)
                goto L_0x0633
            L_0x008f:
                gnu.kawa.functions.IsEqv r9 = kawa.standard.Scheme.isEqv
                java.lang.Object r10 = r1.re$1
                gnu.mapping.SimpleSymbol r11 = gnu.kawa.slib.pregexp.Lit26
                java.lang.Object r9 = r9.apply2(r10, r11)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x00c0
                java.lang.Object r9 = r8.s
                java.lang.Object r10 = r1.i
                java.lang.Object r11 = r8.n
                java.lang.Object r9 = gnu.kawa.slib.pregexp.isPregexpAtWordBoundary(r9, r10, r11)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x00b6
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.sk
                java.lang.Object r11 = r1.i
                java.lang.Object r9 = r9.apply2(r10, r11)
                goto L_0x00be
            L_0x00b6:
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.fk
                java.lang.Object r9 = r9.apply1(r10)
            L_0x00be:
                goto L_0x0633
            L_0x00c0:
                gnu.kawa.functions.IsEqv r9 = kawa.standard.Scheme.isEqv
                java.lang.Object r10 = r1.re$1
                gnu.mapping.SimpleSymbol r11 = gnu.kawa.slib.pregexp.Lit28
                java.lang.Object r9 = r9.apply2(r10, r11)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x00f1
                java.lang.Object r9 = r8.s
                java.lang.Object r10 = r1.i
                java.lang.Object r11 = r8.n
                java.lang.Object r9 = gnu.kawa.slib.pregexp.isPregexpAtWordBoundary(r9, r10, r11)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x00e5
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.fk
                java.lang.Object r9 = r9.apply1(r10)
                goto L_0x00ef
            L_0x00e5:
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.sk
                java.lang.Object r11 = r1.i
                java.lang.Object r9 = r9.apply2(r10, r11)
            L_0x00ef:
                goto L_0x0633
            L_0x00f1:
                java.lang.Object r9 = r1.re$1
                boolean r9 = kawa.lib.characters.isChar(r9)
                r10 = 2
                java.lang.String r11 = "string-ref"
                r12 = 1
                if (r9 == 0) goto L_0x010c
                gnu.kawa.functions.NumberCompare r9 = kawa.standard.Scheme.numLss
                java.lang.Object r2 = r1.i
                java.lang.Object r3 = r8.n
                java.lang.Object r9 = r9.apply2(r2, r3)
                java.lang.Boolean r2 = java.lang.Boolean.FALSE
                if (r9 == r2) goto L_0x0164
                goto L_0x010e
            L_0x010c:
                if (r9 == 0) goto L_0x0164
            L_0x010e:
                java.lang.Object r9 = r8.case$Mnsensitive$Qu
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r9 == r0) goto L_0x0117
                gnu.expr.ModuleMethod r9 = kawa.lib.characters.char$Eq$Qu
                goto L_0x0119
            L_0x0117:
                gnu.expr.ModuleMethod r9 = kawa.lib.rnrs.unicode.char$Mnci$Eq$Qu
            L_0x0119:
                java.lang.Object r0 = r8.s
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0     // Catch:{ ClassCastException -> 0x015d }
                java.lang.Object r12 = r1.i
                r2 = r12
                java.lang.Number r2 = (java.lang.Number) r2     // Catch:{ ClassCastException -> 0x0156 }
                int r10 = r2.intValue()     // Catch:{ ClassCastException -> 0x0156 }
                char r10 = kawa.lib.strings.stringRef(r0, r10)
                gnu.text.Char r10 = gnu.text.Char.make(r10)
                java.lang.Object r11 = r1.re$1
                java.lang.Object r9 = r9.apply2(r10, r11)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x014c
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.sk
                gnu.kawa.functions.AddOp r11 = gnu.kawa.functions.AddOp.$Pl
                java.lang.Object r12 = r1.i
                gnu.math.IntNum r0 = gnu.kawa.slib.pregexp.Lit8
                java.lang.Object r11 = r11.apply2(r12, r0)
                java.lang.Object r9 = r9.apply2(r10, r11)
                goto L_0x0633
            L_0x014c:
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.fk
                java.lang.Object r9 = r9.apply1(r10)
                goto L_0x0633
            L_0x0156:
                r9 = move-exception
                gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
                r0.<init>((java.lang.ClassCastException) r9, (java.lang.String) r11, (int) r10, (java.lang.Object) r12)
                throw r0
            L_0x015d:
                r9 = move-exception
                gnu.mapping.WrongType r10 = new gnu.mapping.WrongType
                r10.<init>((java.lang.ClassCastException) r9, (java.lang.String) r11, (int) r12, (java.lang.Object) r0)
                throw r10
            L_0x0164:
                java.lang.Object r9 = r1.re$1
                boolean r9 = kawa.lib.lists.isPair(r9)
                int r9 = r9 + r12
                r9 = r9 & r12
                if (r9 == 0) goto L_0x017d
                gnu.kawa.functions.NumberCompare r9 = kawa.standard.Scheme.numLss
                java.lang.Object r2 = r1.i
                java.lang.Object r3 = r8.n
                java.lang.Object r9 = r9.apply2(r2, r3)
                java.lang.Boolean r2 = java.lang.Boolean.FALSE
                if (r9 == r2) goto L_0x01ca
                goto L_0x017f
            L_0x017d:
                if (r9 == 0) goto L_0x01ca
            L_0x017f:
                java.lang.Object r9 = r8.s
                java.lang.CharSequence r9 = (java.lang.CharSequence) r9     // Catch:{ ClassCastException -> 0x01c3 }
                java.lang.Object r12 = r1.i
                r0 = r12
                java.lang.Number r0 = (java.lang.Number) r0     // Catch:{ ClassCastException -> 0x01bc }
                int r10 = r0.intValue()     // Catch:{ ClassCastException -> 0x01bc }
                char r9 = kawa.lib.strings.stringRef(r9, r10)
                gnu.text.Char r9 = gnu.text.Char.make(r9)
                java.lang.Object r10 = r1.re$1
                java.lang.Object r9 = gnu.kawa.slib.pregexp.isPregexpCheckIfInCharClass(r9, r10)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x01b2
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.sk
                gnu.kawa.functions.AddOp r11 = gnu.kawa.functions.AddOp.$Pl
                java.lang.Object r12 = r1.i
                gnu.math.IntNum r0 = gnu.kawa.slib.pregexp.Lit8
                java.lang.Object r11 = r11.apply2(r12, r0)
                java.lang.Object r9 = r9.apply2(r10, r11)
                goto L_0x0633
            L_0x01b2:
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.fk
                java.lang.Object r9 = r9.apply1(r10)
                goto L_0x0633
            L_0x01bc:
                r9 = move-exception
                gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
                r0.<init>((java.lang.ClassCastException) r9, (java.lang.String) r11, (int) r10, (java.lang.Object) r12)
                throw r0
            L_0x01c3:
                r10 = move-exception
                gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
                r0.<init>((java.lang.ClassCastException) r10, (java.lang.String) r11, (int) r12, (java.lang.Object) r9)
                throw r0
            L_0x01ca:
                java.lang.Object r9 = r1.re$1
                boolean r9 = kawa.lib.lists.isPair(r9)
                if (r9 == 0) goto L_0x01fa
                gnu.kawa.functions.IsEqv r9 = kawa.standard.Scheme.isEqv
                gnu.expr.GenericProc r2 = kawa.lib.lists.car
                java.lang.Object r3 = r1.re$1
                java.lang.Object r2 = r2.apply1(r3)
                gnu.mapping.SimpleSymbol r3 = gnu.kawa.slib.pregexp.Lit83
                java.lang.Object r9 = r9.apply2(r2, r3)
                java.lang.Boolean r2 = java.lang.Boolean.FALSE
                if (r9 == r2) goto L_0x01f5
                gnu.kawa.functions.NumberCompare r9 = kawa.standard.Scheme.numLss
                java.lang.Object r2 = r1.i
                java.lang.Object r3 = r8.n
                java.lang.Object r9 = r9.apply2(r2, r3)
                java.lang.Boolean r2 = java.lang.Boolean.FALSE
                if (r9 == r2) goto L_0x0271
                goto L_0x01fc
            L_0x01f5:
                java.lang.Boolean r2 = java.lang.Boolean.FALSE
                if (r9 == r2) goto L_0x0271
                goto L_0x01fc
            L_0x01fa:
                if (r9 == 0) goto L_0x0271
            L_0x01fc:
                java.lang.Object r9 = r8.s
                java.lang.CharSequence r9 = (java.lang.CharSequence) r9     // Catch:{ ClassCastException -> 0x026a }
                java.lang.Object r12 = r1.i
                r0 = r12
                java.lang.Number r0 = (java.lang.Number) r0     // Catch:{ ClassCastException -> 0x0263 }
                int r10 = r0.intValue()     // Catch:{ ClassCastException -> 0x0263 }
                char r9 = kawa.lib.strings.stringRef(r9, r10)
                java.lang.Object r10 = r8.case$Mnsensitive$Qu
                java.lang.Boolean r11 = java.lang.Boolean.FALSE
                if (r10 == r11) goto L_0x0216
                gnu.expr.ModuleMethod r10 = kawa.lib.characters.char$Ls$Eq$Qu
                goto L_0x0218
            L_0x0216:
                gnu.expr.ModuleMethod r10 = kawa.lib.rnrs.unicode.char$Mnci$Ls$Eq$Qu
            L_0x0218:
                gnu.expr.GenericProc r11 = kawa.lib.lists.cadr
                java.lang.Object r12 = r1.re$1
                java.lang.Object r11 = r11.apply1(r12)
                gnu.text.Char r12 = gnu.text.Char.make(r9)
                java.lang.Object r11 = r10.apply2(r11, r12)
                java.lang.Boolean r12 = java.lang.Boolean.FALSE
                if (r11 == r12) goto L_0x0241
                gnu.text.Char r9 = gnu.text.Char.make(r9)
                gnu.expr.GenericProc r11 = kawa.lib.lists.caddr
                java.lang.Object r12 = r1.re$1
                java.lang.Object r11 = r11.apply1(r12)
                java.lang.Object r9 = r10.apply2(r9, r11)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x0259
                goto L_0x0245
            L_0x0241:
                java.lang.Boolean r9 = java.lang.Boolean.FALSE
                if (r11 == r9) goto L_0x0259
            L_0x0245:
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.sk
                gnu.kawa.functions.AddOp r11 = gnu.kawa.functions.AddOp.$Pl
                java.lang.Object r12 = r1.i
                gnu.math.IntNum r0 = gnu.kawa.slib.pregexp.Lit8
                java.lang.Object r11 = r11.apply2(r12, r0)
                java.lang.Object r9 = r9.apply2(r10, r11)
                goto L_0x0633
            L_0x0259:
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.fk
                java.lang.Object r9 = r9.apply1(r10)
                goto L_0x0633
            L_0x0263:
                r9 = move-exception
                gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
                r0.<init>((java.lang.ClassCastException) r9, (java.lang.String) r11, (int) r10, (java.lang.Object) r12)
                throw r0
            L_0x026a:
                r10 = move-exception
                gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
                r0.<init>((java.lang.ClassCastException) r10, (java.lang.String) r11, (int) r12, (java.lang.Object) r9)
                throw r0
            L_0x0271:
                java.lang.Object r9 = r1.re$1
                boolean r9 = kawa.lib.lists.isPair(r9)
                r11 = 0
                if (r9 == 0) goto L_0x0612
                gnu.expr.GenericProc r9 = kawa.lib.lists.car
                java.lang.Object r2 = r1.re$1
                java.lang.Object r9 = r9.apply1(r2)
                gnu.kawa.functions.IsEqv r2 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r3 = gnu.kawa.slib.pregexp.Lit83
                java.lang.Object r2 = r2.apply2(r9, r3)
                java.lang.Boolean r3 = java.lang.Boolean.FALSE
                if (r2 == r3) goto L_0x02b1
                gnu.kawa.functions.NumberCompare r9 = kawa.standard.Scheme.numGEq
                java.lang.Object r10 = r1.i
                java.lang.Object r0 = r8.n
                java.lang.Object r9 = r9.apply2(r10, r0)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x02a5
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.fk
                java.lang.Object r9 = r9.apply1(r10)
                goto L_0x02af
            L_0x02a5:
                java.lang.Object[] r9 = new java.lang.Object[r12]
                gnu.mapping.SimpleSymbol r10 = gnu.kawa.slib.pregexp.Lit101
                r9[r11] = r10
                java.lang.Object r9 = gnu.kawa.slib.pregexp.pregexpError$V(r9)
            L_0x02af:
                goto L_0x0611
            L_0x02b1:
                gnu.kawa.functions.IsEqv r2 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r3 = gnu.kawa.slib.pregexp.Lit82
                java.lang.Object r2 = r2.apply2(r9, r3)
                java.lang.Boolean r3 = java.lang.Boolean.FALSE
                if (r2 == r3) goto L_0x02e2
                gnu.kawa.functions.NumberCompare r9 = kawa.standard.Scheme.numGEq
                java.lang.Object r10 = r1.i
                java.lang.Object r11 = r8.n
                java.lang.Object r9 = r9.apply2(r10, r11)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x02d4
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.fk
                java.lang.Object r9 = r9.apply1(r10)
                goto L_0x02e0
            L_0x02d4:
                gnu.expr.GenericProc r9 = kawa.lib.lists.cdr
                java.lang.Object r10 = r1.re$1
                java.lang.Object r9 = r9.apply1(r10)
                java.lang.Object r9 = r1.lambda5loupOneOfChars(r9)
            L_0x02e0:
                goto L_0x0611
            L_0x02e2:
                gnu.kawa.functions.IsEqv r2 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r3 = gnu.kawa.slib.pregexp.Lit17
                java.lang.Object r2 = r2.apply2(r9, r3)
                java.lang.Boolean r3 = java.lang.Boolean.FALSE
                if (r2 == r3) goto L_0x0319
                gnu.kawa.functions.NumberCompare r9 = kawa.standard.Scheme.numGEq
                java.lang.Object r10 = r1.i
                java.lang.Object r11 = r8.n
                java.lang.Object r9 = r9.apply2(r10, r11)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x0305
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.fk
                java.lang.Object r9 = r9.apply1(r10)
                goto L_0x0317
            L_0x0305:
                gnu.expr.GenericProc r9 = kawa.lib.lists.cadr
                java.lang.Object r10 = r1.re$1
                java.lang.Object r9 = r9.apply1(r10)
                java.lang.Object r10 = r1.i
                gnu.expr.ModuleMethod r11 = r1.lambda$Fn2
                gnu.expr.ModuleMethod r12 = r1.lambda$Fn3
                java.lang.Object r9 = r8.lambda3sub(r9, r10, r11, r12)
            L_0x0317:
                goto L_0x0611
            L_0x0319:
                gnu.kawa.functions.IsEqv r2 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r3 = gnu.kawa.slib.pregexp.Lit5
                java.lang.Object r2 = r2.apply2(r9, r3)
                java.lang.Boolean r3 = java.lang.Boolean.FALSE
                if (r2 == r3) goto L_0x0335
                gnu.expr.GenericProc r9 = kawa.lib.lists.cdr
                java.lang.Object r10 = r1.re$1
                java.lang.Object r9 = r9.apply1(r10)
                java.lang.Object r10 = r1.i
                java.lang.Object r9 = r1.lambda6loupSeq(r9, r10)
                goto L_0x0611
            L_0x0335:
                gnu.kawa.functions.IsEqv r2 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r3 = gnu.kawa.slib.pregexp.Lit4
                java.lang.Object r2 = r2.apply2(r9, r3)
                java.lang.Boolean r3 = java.lang.Boolean.FALSE
                if (r2 == r3) goto L_0x034f
                gnu.expr.GenericProc r9 = kawa.lib.lists.cdr
                java.lang.Object r10 = r1.re$1
                java.lang.Object r9 = r9.apply1(r10)
                java.lang.Object r9 = r1.lambda7loupOr(r9)
                goto L_0x0611
            L_0x034f:
                gnu.kawa.functions.IsEqv r2 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r3 = gnu.kawa.slib.pregexp.Lit20
                java.lang.Object r2 = r2.apply2(r9, r3)
                java.lang.Boolean r3 = java.lang.Boolean.FALSE
                if (r2 == r3) goto L_0x03de
                java.lang.Object r9 = r8.backrefs
                gnu.expr.GenericProc r2 = kawa.lib.lists.cadr
                java.lang.Object r3 = r1.re$1
                java.lang.Object r2 = r2.apply1(r3)
                java.lang.Object r9 = gnu.kawa.slib.pregexp.pregexpListRef(r9, r2)
                java.lang.Boolean r2 = java.lang.Boolean.FALSE
                r3 = 3
                if (r9 == r2) goto L_0x0375
                gnu.expr.GenericProc r11 = kawa.lib.lists.cdr
                java.lang.Object r9 = r11.apply1(r9)
                goto L_0x0388
            L_0x0375:
                java.lang.Object r9 = r1.re$1
                java.lang.Object[] r2 = new java.lang.Object[r3]
                gnu.mapping.SimpleSymbol r4 = gnu.kawa.slib.pregexp.Lit101
                r2[r11] = r4
                gnu.mapping.SimpleSymbol r11 = gnu.kawa.slib.pregexp.Lit102
                r2[r12] = r11
                r2[r10] = r9
                gnu.kawa.slib.pregexp.pregexpError$V(r2)
                java.lang.Boolean r9 = java.lang.Boolean.FALSE
            L_0x0388:
                java.lang.Boolean r11 = java.lang.Boolean.FALSE
                if (r9 == r11) goto L_0x03d2
                java.lang.Object r11 = r8.s
                java.lang.CharSequence r11 = (java.lang.CharSequence) r11     // Catch:{ ClassCastException -> 0x03cb }
                gnu.expr.GenericProc r12 = kawa.lib.lists.car
                java.lang.Object r12 = r12.apply1(r9)
                r2 = r12
                java.lang.Number r2 = (java.lang.Number) r2     // Catch:{ ClassCastException -> 0x03c4 }
                int r10 = r2.intValue()     // Catch:{ ClassCastException -> 0x03c4 }
                gnu.expr.GenericProc r12 = kawa.lib.lists.cdr
                java.lang.Object r9 = r12.apply1(r9)
                r12 = r9
                java.lang.Number r12 = (java.lang.Number) r12     // Catch:{ ClassCastException -> 0x03bd }
                int r9 = r12.intValue()     // Catch:{ ClassCastException -> 0x03bd }
                java.lang.CharSequence r2 = kawa.lib.strings.substring(r11, r10, r9)
                java.lang.Object r3 = r8.s
                java.lang.Object r4 = r1.i
                java.lang.Object r5 = r8.n
                gnu.expr.ModuleMethod r6 = r1.lambda$Fn4
                java.lang.Object r7 = r1.fk
                java.lang.Object r9 = gnu.kawa.slib.pregexp.pregexpStringMatch(r2, r3, r4, r5, r6, r7)
                goto L_0x03dc
            L_0x03bd:
                r10 = move-exception
                gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
                r11.<init>((java.lang.ClassCastException) r10, (java.lang.String) r0, (int) r3, (java.lang.Object) r9)
                throw r11
            L_0x03c4:
                r9 = move-exception
                gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
                r11.<init>((java.lang.ClassCastException) r9, (java.lang.String) r0, (int) r10, (java.lang.Object) r12)
                throw r11
            L_0x03cb:
                r9 = move-exception
                gnu.mapping.WrongType r10 = new gnu.mapping.WrongType
                r10.<init>((java.lang.ClassCastException) r9, (java.lang.String) r0, (int) r12, (java.lang.Object) r11)
                throw r10
            L_0x03d2:
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.sk
                java.lang.Object r11 = r1.i
                java.lang.Object r9 = r9.apply2(r10, r11)
            L_0x03dc:
                goto L_0x0611
            L_0x03de:
                gnu.kawa.functions.IsEqv r10 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r0 = gnu.kawa.slib.pregexp.Lit100
                java.lang.Object r10 = r10.apply2(r9, r0)
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x03fe
                gnu.expr.GenericProc r9 = kawa.lib.lists.cadr
                java.lang.Object r10 = r1.re$1
                java.lang.Object r9 = r9.apply1(r10)
                java.lang.Object r10 = r1.i
                gnu.expr.ModuleMethod r11 = r1.lambda$Fn5
                java.lang.Object r12 = r1.fk
                java.lang.Object r9 = r8.lambda3sub(r9, r10, r11, r12)
                goto L_0x0611
            L_0x03fe:
                gnu.kawa.functions.IsEqv r10 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r0 = gnu.kawa.slib.pregexp.Lit103
                java.lang.Object r10 = r10.apply2(r9, r0)
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x0437
                gnu.expr.GenericProc r9 = kawa.lib.lists.cadr
                java.lang.Object r10 = r1.re$1
                java.lang.Object r9 = r9.apply1(r10)
                java.lang.Object r10 = r1.i
                gnu.mapping.Procedure r11 = r8.identity
                gnu.expr.ModuleMethod r12 = gnu.kawa.slib.pregexp.lambda$Fn6
                java.lang.Object r9 = r8.lambda3sub(r9, r10, r11, r12)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x042d
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.sk
                java.lang.Object r11 = r1.i
                java.lang.Object r9 = r9.apply2(r10, r11)
                goto L_0x0611
            L_0x042d:
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.fk
                java.lang.Object r9 = r9.apply1(r10)
                goto L_0x0611
            L_0x0437:
                gnu.kawa.functions.IsEqv r10 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r0 = gnu.kawa.slib.pregexp.Lit104
                java.lang.Object r10 = r10.apply2(r9, r0)
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x0470
                gnu.expr.GenericProc r9 = kawa.lib.lists.cadr
                java.lang.Object r10 = r1.re$1
                java.lang.Object r9 = r9.apply1(r10)
                java.lang.Object r10 = r1.i
                gnu.mapping.Procedure r11 = r8.identity
                gnu.expr.ModuleMethod r12 = gnu.kawa.slib.pregexp.lambda$Fn7
                java.lang.Object r9 = r8.lambda3sub(r9, r10, r11, r12)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x0464
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.fk
                java.lang.Object r9 = r9.apply1(r10)
                goto L_0x0611
            L_0x0464:
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.sk
                java.lang.Object r11 = r1.i
                java.lang.Object r9 = r9.apply2(r10, r11)
                goto L_0x0611
            L_0x0470:
                gnu.kawa.functions.IsEqv r10 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r0 = gnu.kawa.slib.pregexp.Lit105
                java.lang.Object r10 = r10.apply2(r9, r0)
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x04c3
                java.lang.Object r9 = r8.n
                java.lang.Object r10 = r8.sn
                java.lang.Object r11 = r1.i
                r8.n = r11
                java.lang.Object r11 = r1.i
                r8.sn = r11
                gnu.mapping.SimpleSymbol r11 = gnu.kawa.slib.pregexp.Lit5
                gnu.lists.PairWithPosition r12 = gnu.kawa.slib.pregexp.Lit106
                gnu.expr.GenericProc r0 = kawa.lib.lists.cadr
                java.lang.Object r2 = r1.re$1
                java.lang.Object r0 = r0.apply1(r2)
                gnu.mapping.SimpleSymbol r2 = gnu.kawa.slib.pregexp.Lit12
                gnu.lists.Pair r11 = gnu.lists.LList.list4(r11, r12, r0, r2)
                gnu.math.IntNum r12 = gnu.kawa.slib.pregexp.Lit73
                gnu.mapping.Procedure r0 = r8.identity
                gnu.expr.ModuleMethod r2 = gnu.kawa.slib.pregexp.lambda$Fn8
                java.lang.Object r11 = r8.lambda3sub(r11, r12, r0, r2)
                r8.n = r9
                r8.sn = r10
                java.lang.Boolean r9 = java.lang.Boolean.FALSE
                if (r11 == r9) goto L_0x04b9
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.sk
                java.lang.Object r11 = r1.i
                java.lang.Object r9 = r9.apply2(r10, r11)
                goto L_0x0611
            L_0x04b9:
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.fk
                java.lang.Object r9 = r9.apply1(r10)
                goto L_0x0611
            L_0x04c3:
                gnu.kawa.functions.IsEqv r10 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r0 = gnu.kawa.slib.pregexp.Lit107
                java.lang.Object r10 = r10.apply2(r9, r0)
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x0516
                java.lang.Object r9 = r8.n
                java.lang.Object r10 = r8.sn
                java.lang.Object r11 = r1.i
                r8.n = r11
                java.lang.Object r11 = r1.i
                r8.sn = r11
                gnu.mapping.SimpleSymbol r11 = gnu.kawa.slib.pregexp.Lit5
                gnu.lists.PairWithPosition r12 = gnu.kawa.slib.pregexp.Lit108
                gnu.expr.GenericProc r0 = kawa.lib.lists.cadr
                java.lang.Object r2 = r1.re$1
                java.lang.Object r0 = r0.apply1(r2)
                gnu.mapping.SimpleSymbol r2 = gnu.kawa.slib.pregexp.Lit12
                gnu.lists.Pair r11 = gnu.lists.LList.list4(r11, r12, r0, r2)
                gnu.math.IntNum r12 = gnu.kawa.slib.pregexp.Lit73
                gnu.mapping.Procedure r0 = r8.identity
                gnu.expr.ModuleMethod r2 = gnu.kawa.slib.pregexp.lambda$Fn9
                java.lang.Object r11 = r8.lambda3sub(r11, r12, r0, r2)
                r8.n = r9
                r8.sn = r10
                java.lang.Boolean r9 = java.lang.Boolean.FALSE
                if (r11 == r9) goto L_0x050a
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.fk
                java.lang.Object r9 = r9.apply1(r10)
                goto L_0x0611
            L_0x050a:
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.sk
                java.lang.Object r11 = r1.i
                java.lang.Object r9 = r9.apply2(r10, r11)
                goto L_0x0611
            L_0x0516:
                gnu.kawa.functions.IsEqv r10 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r0 = gnu.kawa.slib.pregexp.Lit109
                java.lang.Object r10 = r10.apply2(r9, r0)
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x054b
                gnu.expr.GenericProc r9 = kawa.lib.lists.cadr
                java.lang.Object r10 = r1.re$1
                java.lang.Object r9 = r9.apply1(r10)
                java.lang.Object r10 = r1.i
                gnu.mapping.Procedure r11 = r8.identity
                gnu.expr.ModuleMethod r12 = gnu.kawa.slib.pregexp.lambda$Fn10
                java.lang.Object r9 = r8.lambda3sub(r9, r10, r11, r12)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x0541
                gnu.kawa.functions.ApplyToArgs r10 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r11 = r1.sk
                java.lang.Object r9 = r10.apply2(r11, r9)
                goto L_0x0549
            L_0x0541:
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.fk
                java.lang.Object r9 = r9.apply1(r10)
            L_0x0549:
                goto L_0x0611
            L_0x054b:
                gnu.kawa.functions.IsEqv r10 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r0 = gnu.kawa.slib.pregexp.Lit60
                java.lang.Object r10 = r10.apply2(r9, r0)
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x055c
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x0592
                goto L_0x0568
            L_0x055c:
                gnu.kawa.functions.IsEqv r10 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r0 = gnu.kawa.slib.pregexp.Lit61
                java.lang.Object r10 = r10.apply2(r9, r0)
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x0592
            L_0x0568:
                java.lang.Object r9 = r8.case$Mnsensitive$Qu
                r1.old = r9
                gnu.kawa.functions.IsEqv r9 = kawa.standard.Scheme.isEqv
                gnu.expr.GenericProc r10 = kawa.lib.lists.car
                java.lang.Object r11 = r1.re$1
                java.lang.Object r10 = r10.apply1(r11)
                gnu.mapping.SimpleSymbol r11 = gnu.kawa.slib.pregexp.Lit60
                java.lang.Object r9 = r9.apply2(r10, r11)
                r8.case$Mnsensitive$Qu = r9
                gnu.expr.GenericProc r9 = kawa.lib.lists.cadr
                java.lang.Object r10 = r1.re$1
                java.lang.Object r9 = r9.apply1(r10)
                java.lang.Object r10 = r1.i
                gnu.expr.ModuleMethod r11 = r1.lambda$Fn11
                gnu.expr.ModuleMethod r12 = r1.lambda$Fn12
                java.lang.Object r9 = r8.lambda3sub(r9, r10, r11, r12)
                goto L_0x0611
            L_0x0592:
                gnu.kawa.functions.IsEqv r10 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r0 = gnu.kawa.slib.pregexp.Lit68
                java.lang.Object r9 = r10.apply2(r9, r0)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x0607
                gnu.expr.GenericProc r9 = kawa.lib.lists.cadr
                java.lang.Object r10 = r1.re$1
                java.lang.Object r9 = r9.apply1(r10)
                r10 = -2
                java.lang.Boolean r0 = java.lang.Boolean.FALSE     // Catch:{ ClassCastException -> 0x05fe }
                if (r9 == r0) goto L_0x05ad
                r9 = 1
                goto L_0x05ae
            L_0x05ad:
                r9 = 0
            L_0x05ae:
                int r9 = r9 + r12
                r9 = r9 & r12
                r1.maximal$Qu = r9
                gnu.expr.GenericProc r9 = kawa.lib.lists.caddr
                java.lang.Object r0 = r1.re$1
                java.lang.Object r9 = r9.apply1(r0)
                r1.p = r9
                gnu.expr.GenericProc r9 = kawa.lib.lists.cadddr
                java.lang.Object r0 = r1.re$1
                java.lang.Object r9 = r9.apply1(r0)
                r1.q = r9
                boolean r9 = r1.maximal$Qu
                if (r9 == 0) goto L_0x05e0
                java.lang.Object r9 = r1.q
                java.lang.Boolean r10 = java.lang.Boolean.FALSE     // Catch:{ ClassCastException -> 0x05d7 }
                if (r9 == r10) goto L_0x05d3
                r11 = 1
            L_0x05d3:
                int r11 = r11 + r12
                r9 = r11 & 1
                goto L_0x05e2
            L_0x05d7:
                r11 = move-exception
                gnu.mapping.WrongType r12 = new gnu.mapping.WrongType
                java.lang.String r0 = "could-loop-infinitely?"
                r12.<init>((java.lang.ClassCastException) r11, (java.lang.String) r0, (int) r10, (java.lang.Object) r9)
                throw r12
            L_0x05e0:
                boolean r9 = r1.maximal$Qu
            L_0x05e2:
                r1.could$Mnloop$Mninfinitely$Qu = r9
                gnu.expr.GenericProc r9 = kawa.lib.lists.car
                gnu.expr.GenericProc r10 = kawa.lib.lists.cddddr
                java.lang.Object r11 = r1.re$1
                java.lang.Object r10 = r10.apply1(r11)
                java.lang.Object r9 = r9.apply1(r10)
                r1.re = r9
                gnu.math.IntNum r9 = gnu.kawa.slib.pregexp.Lit73
                java.lang.Object r10 = r1.i
                java.lang.Object r9 = r1.lambda8loupP(r9, r10)
                goto L_0x0611
            L_0x05fe:
                r11 = move-exception
                gnu.mapping.WrongType r12 = new gnu.mapping.WrongType
                java.lang.String r0 = "maximal?"
                r12.<init>((java.lang.ClassCastException) r11, (java.lang.String) r0, (int) r10, (java.lang.Object) r9)
                throw r12
            L_0x0607:
                java.lang.Object[] r9 = new java.lang.Object[r12]
                gnu.mapping.SimpleSymbol r10 = gnu.kawa.slib.pregexp.Lit101
                r9[r11] = r10
                java.lang.Object r9 = gnu.kawa.slib.pregexp.pregexpError$V(r9)
            L_0x0611:
                goto L_0x0633
            L_0x0612:
                gnu.kawa.functions.NumberCompare r9 = kawa.standard.Scheme.numGEq
                java.lang.Object r10 = r1.i
                java.lang.Object r0 = r8.n
                java.lang.Object r9 = r9.apply2(r10, r0)
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                if (r9 == r10) goto L_0x0629
                gnu.kawa.functions.ApplyToArgs r9 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r10 = r1.fk
                java.lang.Object r9 = r9.apply1(r10)
                goto L_0x0633
            L_0x0629:
                java.lang.Object[] r9 = new java.lang.Object[r12]
                gnu.mapping.SimpleSymbol r10 = gnu.kawa.slib.pregexp.Lit101
                r9[r11] = r10
                java.lang.Object r9 = gnu.kawa.slib.pregexp.pregexpError$V(r9)
            L_0x0633:
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.frame.lambda3sub(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
        }
    }

    public static Object pregexpMatchPositionsAux(Object re, Object s, Object sn, Object start, Object n, Object i) {
        frame frame6 = new frame();
        frame6.s = s;
        frame6.sn = sn;
        frame6.start = start;
        frame6.n = n;
        Procedure procedure = frame6.identity;
        Object pregexpMakeBackrefList = pregexpMakeBackrefList(re);
        frame6.case$Mnsensitive$Qu = Boolean.TRUE;
        frame6.backrefs = pregexpMakeBackrefList;
        frame6.identity = procedure;
        frame6.lambda3sub(re, i, frame6.identity, lambda$Fn1);
        Object arg0 = frame6.backrefs;
        Object result = LList.Empty;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                arg0 = arg02.getCdr();
                result = Pair.make(lists.cdr.apply1(arg02.getCar()), result);
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, arg0);
            }
        }
        LList backrefs = LList.reverseInPlace(result);
        Object x = lists.car.apply1(backrefs);
        return x != Boolean.FALSE ? backrefs : x;
    }

    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 36:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 37:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 38:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 39:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 40:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 41:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            default:
                return super.match0(moduleMethod, callContext);
        }
    }

    /* compiled from: pregexp.scm */
    public class frame0 extends ModuleBody {
        boolean could$Mnloop$Mninfinitely$Qu;
        Object fk;
        Object i;
        final ModuleMethod lambda$Fn11;
        final ModuleMethod lambda$Fn12;
        final ModuleMethod lambda$Fn2;
        final ModuleMethod lambda$Fn3;
        final ModuleMethod lambda$Fn4;
        final ModuleMethod lambda$Fn5;
        boolean maximal$Qu;
        Object old;
        Object p;
        Object q;
        Object re;
        Object re$1;
        Object sk;
        frame staticLink;

        public frame0() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 9, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:513");
            this.lambda$Fn2 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 10, (Object) null, 0);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:514");
            this.lambda$Fn3 = moduleMethod2;
            ModuleMethod moduleMethod3 = new ModuleMethod(this, 11, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod3.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:541");
            this.lambda$Fn4 = moduleMethod3;
            ModuleMethod moduleMethod4 = new ModuleMethod(this, 12, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod4.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:545");
            this.lambda$Fn5 = moduleMethod4;
            ModuleMethod moduleMethod5 = new ModuleMethod(this, 13, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod5.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:587");
            this.lambda$Fn11 = moduleMethod5;
            ModuleMethod moduleMethod6 = new ModuleMethod(this, 14, (Object) null, 0);
            moduleMethod6.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:590");
            this.lambda$Fn12 = moduleMethod6;
        }

        public Object lambda5loupOneOfChars(Object chars) {
            frame1 frame1 = new frame1();
            frame1.staticLink = this;
            frame1.chars = chars;
            if (lists.isNull(frame1.chars)) {
                return Scheme.applyToArgs.apply1(this.fk);
            }
            return this.staticLink.lambda3sub(lists.car.apply1(frame1.chars), this.i, this.sk, frame1.lambda$Fn13);
        }

        /* access modifiers changed from: package-private */
        public Object lambda9(Object i1) {
            return Scheme.applyToArgs.apply1(this.fk);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 9:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 11:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 12:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 13:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                default:
                    return super.match1(moduleMethod, obj, callContext);
            }
        }

        /* access modifiers changed from: package-private */
        public Object lambda10() {
            return Scheme.applyToArgs.apply2(this.sk, AddOp.$Pl.apply2(this.i, pregexp.Lit8));
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 10:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 14:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }

        public Object lambda6loupSeq(Object res, Object i2) {
            frame2 frame2 = new frame2();
            frame2.staticLink = this;
            frame2.res = res;
            if (lists.isNull(frame2.res)) {
                return Scheme.applyToArgs.apply2(this.sk, i2);
            }
            return this.staticLink.lambda3sub(lists.car.apply1(frame2.res), i2, frame2.lambda$Fn14, this.fk);
        }

        public Object lambda7loupOr(Object res) {
            frame3 frame3 = new frame3();
            frame3.staticLink = this;
            frame3.res = res;
            if (lists.isNull(frame3.res)) {
                return Scheme.applyToArgs.apply1(this.fk);
            }
            return this.staticLink.lambda3sub(lists.car.apply1(frame3.res), this.i, frame3.lambda$Fn15, frame3.lambda$Fn16);
        }

        /* access modifiers changed from: package-private */
        public Object lambda11(Object i2) {
            return Scheme.applyToArgs.apply2(this.sk, i2);
        }

        /* access modifiers changed from: package-private */
        public Object lambda12(Object i1) {
            Object assv = lists.assv(this.re$1, this.staticLink.backrefs);
            try {
                lists.setCdr$Ex((Pair) assv, lists.cons(this.i, i1));
                return Scheme.applyToArgs.apply2(this.sk, i1);
            } catch (ClassCastException e) {
                throw new WrongType(e, "set-cdr!", 1, assv);
            }
        }

        static Boolean lambda13() {
            return Boolean.FALSE;
        }

        static Boolean lambda14() {
            return Boolean.FALSE;
        }

        static Boolean lambda15() {
            return Boolean.FALSE;
        }

        static Boolean lambda16() {
            return Boolean.FALSE;
        }

        static Boolean lambda17() {
            return Boolean.FALSE;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 9:
                    return lambda9(obj);
                case 11:
                    return lambda11(obj);
                case 12:
                    return lambda12(obj);
                case 13:
                    return lambda18(obj);
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        /* access modifiers changed from: package-private */
        public Object lambda18(Object i1) {
            this.staticLink.case$Mnsensitive$Qu = this.old;
            return Scheme.applyToArgs.apply2(this.sk, i1);
        }

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 10:
                    return lambda10();
                case 14:
                    return lambda19();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        /* access modifiers changed from: package-private */
        public Object lambda19() {
            this.staticLink.case$Mnsensitive$Qu = this.old;
            return Scheme.applyToArgs.apply1(this.fk);
        }

        public Object lambda8loupP(Object k, Object i2) {
            frame4 frame4 = new frame4();
            frame4.staticLink = this;
            frame4.k = k;
            frame4.i = i2;
            if (Scheme.numLss.apply2(frame4.k, this.p) != Boolean.FALSE) {
                return this.staticLink.lambda3sub(this.re, frame4.i, frame4.lambda$Fn17, this.fk);
            }
            frame4.q = this.q != Boolean.FALSE ? AddOp.$Mn.apply2(this.q, this.p) : this.q;
            return frame4.lambda24loupQ(pregexp.Lit73, frame4.i);
        }
    }

    /* compiled from: pregexp.scm */
    public class frame1 extends ModuleBody {
        Object chars;
        final ModuleMethod lambda$Fn13;
        frame0 staticLink;

        public frame1() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 1, (Object) null, 0);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:508");
            this.lambda$Fn13 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 1 ? lambda20() : super.apply0(moduleMethod);
        }

        /* access modifiers changed from: package-private */
        public Object lambda20() {
            return this.staticLink.lambda5loupOneOfChars(lists.cdr.apply1(this.chars));
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 1) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }
    }

    /* compiled from: pregexp.scm */
    public class frame2 extends ModuleBody {
        final ModuleMethod lambda$Fn14;
        Object res;
        frame0 staticLink;

        public frame2() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 2, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:519");
            this.lambda$Fn14 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 2 ? lambda21(obj) : super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: package-private */
        public Object lambda21(Object i1) {
            return this.staticLink.lambda6loupSeq(lists.cdr.apply1(this.res), i1);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 2) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: pregexp.scm */
    public class frame3 extends ModuleBody {
        final ModuleMethod lambda$Fn15;
        final ModuleMethod lambda$Fn16;
        Object res;
        frame0 staticLink;

        public frame3() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 3, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:526");
            this.lambda$Fn15 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 4, (Object) null, 0);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:529");
            this.lambda$Fn16 = moduleMethod2;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 3 ? lambda22(obj) : super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: package-private */
        public Object lambda22(Object i1) {
            Object x = Scheme.applyToArgs.apply2(this.staticLink.sk, i1);
            return x != Boolean.FALSE ? x : this.staticLink.lambda7loupOr(lists.cdr.apply1(this.res));
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 3) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 4 ? lambda23() : super.apply0(moduleMethod);
        }

        /* access modifiers changed from: package-private */
        public Object lambda23() {
            return this.staticLink.lambda7loupOr(lists.cdr.apply1(this.res));
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 4) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }
    }

    public Object apply0(ModuleMethod moduleMethod) {
        switch (moduleMethod.selector) {
            case 36:
                return frame.lambda4();
            case 37:
                return frame0.lambda13();
            case 38:
                return frame0.lambda14();
            case 39:
                return frame0.lambda15();
            case 40:
                return frame0.lambda16();
            case 41:
                return frame0.lambda17();
            default:
                return super.apply0(moduleMethod);
        }
    }

    /* compiled from: pregexp.scm */
    public class frame4 extends ModuleBody {
        Object i;
        Object k;
        final ModuleMethod lambda$Fn17;
        Object q;
        frame0 staticLink;

        public frame4() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 8, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:602");
            this.lambda$Fn17 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 8 ? lambda25(obj) : super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: package-private */
        public Object lambda25(Object i1) {
            if (!this.staticLink.could$Mnloop$Mninfinitely$Qu ? this.staticLink.could$Mnloop$Mninfinitely$Qu : Scheme.numEqu.apply2(i1, this.i) != Boolean.FALSE) {
                pregexp.pregexpError$V(new Object[]{pregexp.Lit101, pregexp.Lit110});
            }
            return this.staticLink.lambda8loupP(AddOp.$Pl.apply2(this.k, pregexp.Lit8), i1);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 8) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }

        public Object lambda24loupQ(Object k2, Object i2) {
            frame5 frame5 = new frame5();
            frame5.staticLink = this;
            frame5.k = k2;
            frame5.i = i2;
            frame5.fk = frame5.fk;
            if (this.q == Boolean.FALSE ? this.q != Boolean.FALSE : Scheme.numGEq.apply2(frame5.k, this.q) != Boolean.FALSE) {
                return frame5.lambda26fk();
            }
            if (this.staticLink.maximal$Qu) {
                return this.staticLink.staticLink.lambda3sub(this.staticLink.re, frame5.i, frame5.lambda$Fn18, frame5.fk);
            }
            Object x = frame5.lambda26fk();
            return x != Boolean.FALSE ? x : this.staticLink.staticLink.lambda3sub(this.staticLink.re, frame5.i, frame5.lambda$Fn19, frame5.fk);
        }
    }

    /* compiled from: pregexp.scm */
    public class frame5 extends ModuleBody {
        Procedure fk;
        Object i;
        Object k;
        final ModuleMethod lambda$Fn18;
        final ModuleMethod lambda$Fn19;
        frame4 staticLink;

        public frame5() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 5, pregexp.Lit111, 0);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:612");
            this.fk = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 6, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:617");
            this.lambda$Fn18 = moduleMethod2;
            ModuleMethod moduleMethod3 = new ModuleMethod(this, 7, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod3.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:628");
            this.lambda$Fn19 = moduleMethod3;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 5 ? lambda26fk() : super.apply0(moduleMethod);
        }

        public Object lambda26fk() {
            return Scheme.applyToArgs.apply2(this.staticLink.staticLink.sk, this.i);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 5) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        /* access modifiers changed from: package-private */
        public Object lambda27(Object i1) {
            if (!this.staticLink.staticLink.could$Mnloop$Mninfinitely$Qu ? this.staticLink.staticLink.could$Mnloop$Mninfinitely$Qu : Scheme.numEqu.apply2(i1, this.i) != Boolean.FALSE) {
                pregexp.pregexpError$V(new Object[]{pregexp.Lit101, pregexp.Lit110});
            }
            Object x = this.staticLink.lambda24loupQ(AddOp.$Pl.apply2(this.k, pregexp.Lit8), i1);
            return x != Boolean.FALSE ? x : lambda26fk();
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 6:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 7:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                default:
                    return super.match1(moduleMethod, obj, callContext);
            }
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 6:
                    return lambda27(obj);
                case 7:
                    return lambda28(obj);
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        /* access modifiers changed from: package-private */
        public Object lambda28(Object i1) {
            return this.staticLink.lambda24loupQ(AddOp.$Pl.apply2(this.k, pregexp.Lit8), i1);
        }
    }

    public static Object pregexpReplaceAux(Object obj, Object obj2, Object obj3, Object obj4) {
        Object obj5;
        Number number = Lit73;
        Object obj6 = "";
        while (Scheme.numGEq.apply2(number, obj3) == Boolean.FALSE) {
            try {
                try {
                    char stringRef = strings.stringRef((CharSequence) obj2, number.intValue());
                    if (characters.isChar$Eq(Char.make(stringRef), Lit19)) {
                        Object pregexpReadEscapedNumber = pregexpReadEscapedNumber(obj2, number, obj3);
                        if (pregexpReadEscapedNumber != Boolean.FALSE) {
                            obj5 = lists.car.apply1(pregexpReadEscapedNumber);
                        } else {
                            try {
                                CharSequence charSequence = (CharSequence) obj2;
                                Object apply2 = AddOp.$Pl.apply2(number, Lit8);
                                try {
                                    obj5 = characters.isChar$Eq(Char.make(strings.stringRef(charSequence, ((Number) apply2).intValue())), Lit113) ? Lit73 : Boolean.FALSE;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, apply2);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, obj2);
                            }
                        }
                        if (pregexpReadEscapedNumber != Boolean.FALSE) {
                            number = lists.cadr.apply1(pregexpReadEscapedNumber);
                        } else if (obj5 != Boolean.FALSE) {
                            number = AddOp.$Pl.apply2(number, Lit16);
                        } else {
                            number = AddOp.$Pl.apply2(number, Lit8);
                        }
                        if (obj5 == Boolean.FALSE) {
                            try {
                                try {
                                    char stringRef2 = strings.stringRef((CharSequence) obj2, ((Number) number).intValue());
                                    number = AddOp.$Pl.apply2(number, Lit8);
                                    if (!characters.isChar$Eq(Char.make(stringRef2), Lit11)) {
                                        obj6 = strings.stringAppend(obj6, strings.$make$string$(Char.make(stringRef2)));
                                    }
                                } catch (ClassCastException e3) {
                                    throw new WrongType(e3, "string-ref", 2, number);
                                }
                            } catch (ClassCastException e4) {
                                throw new WrongType(e4, "string-ref", 1, obj2);
                            }
                        } else {
                            Object pregexpListRef = pregexpListRef(obj4, obj5);
                            if (pregexpListRef != Boolean.FALSE) {
                                try {
                                    CharSequence charSequence2 = (CharSequence) obj;
                                    Object apply1 = lists.car.apply1(pregexpListRef);
                                    try {
                                        int intValue = ((Number) apply1).intValue();
                                        Object apply12 = lists.cdr.apply1(pregexpListRef);
                                        try {
                                            obj6 = strings.stringAppend(obj6, strings.substring(charSequence2, intValue, ((Number) apply12).intValue()));
                                        } catch (ClassCastException e5) {
                                            throw new WrongType(e5, "substring", 3, apply12);
                                        }
                                    } catch (ClassCastException e6) {
                                        throw new WrongType(e6, "substring", 2, apply1);
                                    }
                                } catch (ClassCastException e7) {
                                    throw new WrongType(e7, "substring", 1, obj);
                                }
                            }
                        }
                    } else {
                        number = AddOp.$Pl.apply2(number, Lit8);
                        obj6 = strings.stringAppend(obj6, strings.$make$string$(Char.make(stringRef)));
                    }
                } catch (ClassCastException e8) {
                    throw new WrongType(e8, "string-ref", 2, (Object) number);
                }
            } catch (ClassCastException e9) {
                throw new WrongType(e9, "string-ref", 1, obj2);
            }
        }
        return obj6;
    }

    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        return moduleMethod.selector == 43 ? pregexpReplaceAux(obj, obj2, obj3, obj4) : super.apply4(moduleMethod, obj, obj2, obj3, obj4);
    }

    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        if (moduleMethod.selector != 43) {
            return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        callContext.value3 = obj3;
        callContext.value4 = obj4;
        callContext.proc = moduleMethod;
        callContext.pc = 4;
        return 0;
    }

    public static Pair pregexp(Object s) {
        $Stpregexp$Mnspace$Mnsensitive$Qu$St = Boolean.TRUE;
        try {
            return LList.list2(Lit100, lists.car.apply1(pregexpReadPattern(s, Lit73, Integer.valueOf(strings.stringLength((CharSequence) s)))));
        } catch (ClassCastException e) {
            throw new WrongType(e, "string-length", 1, s);
        }
    }

    public static Object pregexpMatchPositions$V(Object obj, Object obj2, Object[] objArr) {
        Object obj3;
        Object obj4;
        LList makeList = LList.makeList(objArr, 0);
        if (strings.isString(obj)) {
            obj = pregexp(obj);
        } else if (!lists.isPair(obj)) {
            pregexpError$V(new Object[]{Lit114, Lit115, obj});
        }
        try {
            int stringLength = strings.stringLength((CharSequence) obj2);
            if (lists.isNull(makeList)) {
                obj3 = Lit73;
            } else {
                obj3 = lists.car.apply1(makeList);
                Object apply1 = lists.cdr.apply1(makeList);
                try {
                    makeList = (LList) apply1;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "opt-args", -2, apply1);
                }
            }
            if (lists.isNull(makeList)) {
                obj4 = Integer.valueOf(stringLength);
            } else {
                obj4 = lists.car.apply1(makeList);
            }
            Object obj5 = obj3;
            while (true) {
                Object apply2 = Scheme.numLEq.apply2(obj5, obj4);
                try {
                    boolean booleanValue = ((Boolean) apply2).booleanValue();
                    if (!booleanValue) {
                        return booleanValue ? Boolean.TRUE : Boolean.FALSE;
                    }
                    Object pregexpMatchPositionsAux = pregexpMatchPositionsAux(obj, obj2, Integer.valueOf(stringLength), obj3, obj4, obj5);
                    if (pregexpMatchPositionsAux != Boolean.FALSE) {
                        return pregexpMatchPositionsAux;
                    }
                    obj5 = AddOp.$Pl.apply2(obj5, Lit8);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "x", -2, apply2);
                }
            }
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "string-length", 1, obj2);
        }
    }

    public static Object pregexpMatch$V(Object pat, Object str, Object[] argsArray) {
        Object ix$Mnprs = Scheme.apply.apply4(pregexp$Mnmatch$Mnpositions, pat, str, LList.makeList(argsArray, 0));
        if (ix$Mnprs == Boolean.FALSE) {
            return ix$Mnprs;
        }
        Object result = LList.Empty;
        Object arg0 = ix$Mnprs;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object cdr = arg02.getCdr();
                Object ix$Mnpr = arg02.getCar();
                if (ix$Mnpr != Boolean.FALSE) {
                    try {
                        CharSequence charSequence = (CharSequence) str;
                        Object apply1 = lists.car.apply1(ix$Mnpr);
                        try {
                            int intValue = ((Number) apply1).intValue();
                            Object apply12 = lists.cdr.apply1(ix$Mnpr);
                            try {
                                ix$Mnpr = strings.substring(charSequence, intValue, ((Number) apply12).intValue());
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "substring", 3, apply12);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "substring", 2, apply1);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "substring", 1, str);
                    }
                }
                result = Pair.make(ix$Mnpr, result);
                arg0 = cdr;
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "arg0", -2, arg0);
            }
        }
        return LList.reverseInPlace(result);
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        Object[] objArr2 = objArr;
        switch (moduleMethod.selector) {
            case 17:
                return pregexpError$V(objArr);
            case 30:
                return pregexpStringMatch(objArr2[0], objArr2[1], objArr2[2], objArr2[3], objArr2[4], objArr2[5]);
            case 42:
                return pregexpMatchPositionsAux(objArr2[0], objArr2[1], objArr2[2], objArr2[3], objArr2[4], objArr2[5]);
            case 45:
                Object obj = objArr2[0];
                Object obj2 = objArr2[1];
                int length = objArr2.length - 2;
                Object[] objArr3 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return pregexpMatchPositions$V(obj, obj2, objArr3);
                    }
                    objArr3[length] = objArr2[length + 2];
                }
            case 46:
                Object obj3 = objArr2[0];
                Object obj4 = objArr2[1];
                int length2 = objArr2.length - 2;
                Object[] objArr4 = new Object[length2];
                while (true) {
                    length2--;
                    if (length2 < 0) {
                        return pregexpMatch$V(obj3, obj4, objArr4);
                    }
                    objArr4[length2] = objArr2[length2 + 2];
                }
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }

    public static Object pregexpSplit(Object obj, Object obj2) {
        try {
            int stringLength = strings.stringLength((CharSequence) obj2);
            Object obj3 = Lit73;
            LList lList = LList.Empty;
            Boolean bool = Boolean.FALSE;
            while (Scheme.numGEq.apply2(obj3, Integer.valueOf(stringLength)) == Boolean.FALSE) {
                Object pregexpMatchPositions$V = pregexpMatchPositions$V(obj, obj2, new Object[]{obj3, Integer.valueOf(stringLength)});
                if (pregexpMatchPositions$V != Boolean.FALSE) {
                    Object apply1 = lists.car.apply1(pregexpMatchPositions$V);
                    Object apply12 = lists.car.apply1(apply1);
                    Object apply13 = lists.cdr.apply1(apply1);
                    if (Scheme.numEqu.apply2(apply12, apply13) != Boolean.FALSE) {
                        Object apply2 = AddOp.$Pl.apply2(apply13, Lit8);
                        try {
                            CharSequence charSequence = (CharSequence) obj2;
                            try {
                                int intValue = ((Number) obj3).intValue();
                                Object apply22 = AddOp.$Pl.apply2(apply12, Lit8);
                                try {
                                    lList = lists.cons(strings.substring(charSequence, intValue, ((Number) apply22).intValue()), lList);
                                    Object obj4 = apply2;
                                    bool = Boolean.TRUE;
                                    obj3 = obj4;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "substring", 3, apply22);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "substring", 2, obj3);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "substring", 1, obj2);
                        }
                    } else {
                        Object apply23 = Scheme.numEqu.apply2(apply12, obj3);
                        try {
                            boolean booleanValue = ((Boolean) apply23).booleanValue();
                            if (!booleanValue ? !booleanValue : bool == Boolean.FALSE) {
                                try {
                                    try {
                                        try {
                                            lList = lists.cons(strings.substring((CharSequence) obj2, ((Number) obj3).intValue(), ((Number) apply12).intValue()), lList);
                                            bool = Boolean.FALSE;
                                            obj3 = apply13;
                                        } catch (ClassCastException e4) {
                                            throw new WrongType(e4, "substring", 3, apply12);
                                        }
                                    } catch (ClassCastException e5) {
                                        throw new WrongType(e5, "substring", 2, obj3);
                                    }
                                } catch (ClassCastException e6) {
                                    throw new WrongType(e6, "substring", 1, obj2);
                                }
                            } else {
                                bool = Boolean.FALSE;
                                obj3 = apply13;
                            }
                        } catch (ClassCastException e7) {
                            throw new WrongType(e7, "x", -2, apply23);
                        }
                    }
                } else {
                    Object valueOf = Integer.valueOf(stringLength);
                    try {
                        try {
                            lList = lists.cons(strings.substring((CharSequence) obj2, ((Number) obj3).intValue(), stringLength), lList);
                            Object obj5 = valueOf;
                            bool = Boolean.FALSE;
                            obj3 = obj5;
                        } catch (ClassCastException e8) {
                            throw new WrongType(e8, "substring", 2, obj3);
                        }
                    } catch (ClassCastException e9) {
                        throw new WrongType(e9, "substring", 1, obj2);
                    }
                }
            }
            return pregexpReverse$Ex(lList);
        } catch (ClassCastException e10) {
            throw new WrongType(e10, "string-length", 1, obj2);
        }
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 33:
                return isPregexpCheckIfInCharClass(obj, obj2);
            case 34:
                return pregexpListRef(obj, obj2);
            case 47:
                return pregexpSplit(obj, obj2);
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static Object pregexpReplace(Object pat, Object str, Object ins) {
        Object obj = str;
        Object obj2 = ins;
        try {
            int n = strings.stringLength((CharSequence) obj);
            Object pp = pregexpMatchPositions$V(pat, obj, new Object[]{Lit73, Integer.valueOf(n)});
            Object obj3 = str;
            if (pp == Boolean.FALSE) {
                return obj;
            }
            try {
                int ins$Mnlen = strings.stringLength((CharSequence) obj2);
                Object m$Mni = lists.caar.apply1(pp);
                Object m$Mnn = lists.cdar.apply1(pp);
                Object obj4 = ins;
                try {
                    try {
                        CharSequence substring = strings.substring((CharSequence) obj, 0, ((Number) m$Mni).intValue());
                        Object pregexpReplaceAux = pregexpReplaceAux(obj, obj2, Integer.valueOf(ins$Mnlen), pp);
                        try {
                            try {
                                return strings.stringAppend(substring, pregexpReplaceAux, strings.substring((CharSequence) obj, ((Number) m$Mnn).intValue(), n));
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "substring", 2, m$Mnn);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "substring", 1, obj);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "substring", 3, m$Mni);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "substring", 1, obj);
                }
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "string-length", 1, obj2);
            }
        } catch (ClassCastException e6) {
            throw new WrongType(e6, "string-length", 1, obj);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: gnu.math.IntNum} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: gnu.math.IntNum} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: gnu.math.IntNum} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v0, resolved type: gnu.math.IntNum} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: gnu.math.IntNum} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object pregexpReplace$St(java.lang.Object r17, java.lang.Object r18, java.lang.Object r19) {
        /*
            r1 = r18
            r2 = r19
            java.lang.String r3 = "string-length"
            boolean r0 = kawa.lib.strings.isString(r17)
            if (r0 == 0) goto L_0x0011
            gnu.lists.Pair r0 = pregexp(r17)
            goto L_0x0013
        L_0x0011:
            r0 = r17
        L_0x0013:
            r4 = 1
            r5 = r1
            java.lang.CharSequence r5 = (java.lang.CharSequence) r5     // Catch:{ ClassCastException -> 0x00da }
            int r5 = kawa.lib.strings.stringLength(r5)
            r6 = r2
            java.lang.CharSequence r6 = (java.lang.CharSequence) r6     // Catch:{ ClassCastException -> 0x00d2 }
            int r3 = kawa.lib.strings.stringLength(r6)
            gnu.math.IntNum r6 = Lit73
            java.lang.String r7 = ""
        L_0x0026:
            gnu.kawa.functions.NumberCompare r8 = kawa.standard.Scheme.numGEq
            java.lang.Integer r9 = java.lang.Integer.valueOf(r5)
            java.lang.Object r8 = r8.apply2(r6, r9)
            java.lang.Boolean r9 = java.lang.Boolean.FALSE
            if (r8 == r9) goto L_0x0035
            goto L_0x0072
        L_0x0035:
            java.lang.Integer r8 = java.lang.Integer.valueOf(r5)
            r9 = 2
            java.lang.Object[] r10 = new java.lang.Object[r9]
            r11 = 0
            r10[r11] = r6
            r10[r4] = r8
            java.lang.Object r8 = pregexpMatchPositions$V(r0, r1, r10)
            java.lang.Boolean r10 = java.lang.Boolean.FALSE
            java.lang.String r12 = "substring"
            if (r8 != r10) goto L_0x0082
            gnu.kawa.functions.NumberCompare r0 = kawa.standard.Scheme.numEqu
            gnu.math.IntNum r2 = Lit73
            java.lang.Object r0 = r0.apply2(r6, r2)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            if (r0 == r2) goto L_0x0059
            r0 = r1
            goto L_0x0071
        L_0x0059:
            r0 = r1
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0     // Catch:{ ClassCastException -> 0x007a }
            r1 = r6
            java.lang.Number r1 = (java.lang.Number) r1     // Catch:{ ClassCastException -> 0x0073 }
            int r1 = r1.intValue()     // Catch:{ ClassCastException -> 0x0073 }
            java.lang.CharSequence r0 = kawa.lib.strings.substring(r0, r1, r5)
            java.lang.Object[] r1 = new java.lang.Object[r9]
            r1[r11] = r7
            r1[r4] = r0
            gnu.lists.FString r0 = kawa.lib.strings.stringAppend(r1)
        L_0x0071:
            r7 = r0
        L_0x0072:
            return r7
        L_0x0073:
            r0 = move-exception
            gnu.mapping.WrongType r1 = new gnu.mapping.WrongType
            r1.<init>((java.lang.ClassCastException) r0, (java.lang.String) r12, (int) r9, (java.lang.Object) r6)
            throw r1
        L_0x007a:
            r0 = move-exception
            r2 = r0
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r2, (java.lang.String) r12, (int) r4, (java.lang.Object) r1)
            throw r0
        L_0x0082:
            gnu.expr.GenericProc r10 = kawa.lib.lists.cdar
            java.lang.Object r10 = r10.apply1(r8)
            r13 = r1
            java.lang.CharSequence r13 = (java.lang.CharSequence) r13     // Catch:{ ClassCastException -> 0x00ca }
            r14 = r6
            java.lang.Number r14 = (java.lang.Number) r14     // Catch:{ ClassCastException -> 0x00c3 }
            int r6 = r14.intValue()     // Catch:{ ClassCastException -> 0x00c3 }
            gnu.expr.GenericProc r14 = kawa.lib.lists.caar
            java.lang.Object r14 = r14.apply1(r8)
            r15 = 3
            r16 = r14
            java.lang.Number r16 = (java.lang.Number) r16     // Catch:{ ClassCastException -> 0x00bc }
            int r12 = r16.intValue()     // Catch:{ ClassCastException -> 0x00bc }
            java.lang.CharSequence r6 = kawa.lib.strings.substring(r13, r6, r12)
            java.lang.Integer r12 = java.lang.Integer.valueOf(r3)
            java.lang.Object r8 = pregexpReplaceAux(r1, r2, r12, r8)
            java.lang.Object[] r12 = new java.lang.Object[r15]
            r12[r11] = r7
            r12[r4] = r6
            r12[r9] = r8
            gnu.lists.FString r7 = kawa.lib.strings.stringAppend(r12)
            r6 = r10
            goto L_0x0026
        L_0x00bc:
            r0 = move-exception
            gnu.mapping.WrongType r1 = new gnu.mapping.WrongType
            r1.<init>((java.lang.ClassCastException) r0, (java.lang.String) r12, (int) r15, (java.lang.Object) r14)
            throw r1
        L_0x00c3:
            r0 = move-exception
            gnu.mapping.WrongType r1 = new gnu.mapping.WrongType
            r1.<init>((java.lang.ClassCastException) r0, (java.lang.String) r12, (int) r9, (java.lang.Object) r6)
            throw r1
        L_0x00ca:
            r0 = move-exception
            r2 = r0
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r2, (java.lang.String) r12, (int) r4, (java.lang.Object) r1)
            throw r0
        L_0x00d2:
            r0 = move-exception
            r1 = r0
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r1, (java.lang.String) r3, (int) r4, (java.lang.Object) r2)
            throw r0
        L_0x00da:
            r0 = move-exception
            r2 = r0
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r0.<init>((java.lang.ClassCastException) r2, (java.lang.String) r3, (int) r4, (java.lang.Object) r1)
            goto L_0x00e3
        L_0x00e2:
            throw r0
        L_0x00e3:
            goto L_0x00e2
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpReplace$St(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 18:
                return pregexpReadPattern(obj, obj2, obj3);
            case 19:
                return pregexpReadBranch(obj, obj2, obj3);
            case 20:
                return pregexpReadPiece(obj, obj2, obj3);
            case 21:
                return pregexpReadEscapedNumber(obj, obj2, obj3);
            case 22:
                return pregexpReadEscapedChar(obj, obj2, obj3);
            case 23:
                return pregexpReadPosixCharClass(obj, obj2, obj3);
            case 24:
                return pregexpReadClusterType(obj, obj2, obj3);
            case 25:
                return pregexpReadSubpattern(obj, obj2, obj3);
            case 26:
                return pregexpWrapQuantifierIfAny(obj, obj2, obj3);
            case 27:
                return pregexpReadNums(obj, obj2, obj3);
            case 29:
                return pregexpReadCharList(obj, obj2, obj3);
            case 32:
                return isPregexpAtWordBoundary(obj, obj2, obj3);
            case 48:
                return pregexpReplace(obj, obj2, obj3);
            case 49:
                return pregexpReplace$St(obj, obj2, obj3);
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.lang.Integer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: java.lang.Integer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v9, resolved type: java.lang.Integer} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object pregexpQuote(java.lang.Object r8) {
        /*
            java.lang.String r0 = "string-ref"
            r1 = 1
            r2 = r8
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2     // Catch:{ ClassCastException -> 0x007d }
            int r2 = kawa.lib.strings.stringLength(r2)
            int r2 = r2 - r1
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            gnu.lists.LList r3 = gnu.lists.LList.Empty
            r4 = 0
        L_0x0012:
            gnu.kawa.functions.NumberCompare r5 = kawa.standard.Scheme.numLss
            gnu.math.IntNum r6 = Lit73
            java.lang.Object r5 = r5.apply2(r2, r6)
            java.lang.Boolean r6 = java.lang.Boolean.FALSE
            if (r5 == r6) goto L_0x002f
            r0 = r3
            gnu.lists.LList r0 = (gnu.lists.LList) r0     // Catch:{ ClassCastException -> 0x0026 }
            java.lang.CharSequence r0 = kawa.lib.strings.list$To$String(r0)
            return r0
        L_0x0026:
            r8 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            java.lang.String r2 = "list->string"
            r0.<init>((java.lang.ClassCastException) r8, (java.lang.String) r2, (int) r1, (java.lang.Object) r3)
            throw r0
        L_0x002f:
            gnu.kawa.functions.AddOp r5 = gnu.kawa.functions.AddOp.$Mn
            gnu.math.IntNum r6 = Lit8
            java.lang.Object r5 = r5.apply2(r2, r6)
            r6 = r8
            java.lang.CharSequence r6 = (java.lang.CharSequence) r6     // Catch:{ ClassCastException -> 0x0076 }
            r7 = r2
            java.lang.Number r7 = (java.lang.Number) r7     // Catch:{ ClassCastException -> 0x006e }
            int r7 = r7.intValue()     // Catch:{ ClassCastException -> 0x006e }
            char r6 = kawa.lib.strings.stringRef(r6, r7)
            r4 = r6
            gnu.text.Char r6 = gnu.text.Char.make(r4)
            gnu.lists.PairWithPosition r7 = Lit116
            java.lang.Object r6 = kawa.lib.lists.memv(r6, r7)
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            if (r6 == r7) goto L_0x0063
            gnu.text.Char r6 = Lit19
            gnu.text.Char r7 = gnu.text.Char.make(r4)
            gnu.lists.Pair r7 = kawa.lib.lists.cons(r7, r3)
            gnu.lists.Pair r6 = kawa.lib.lists.cons(r6, r7)
            goto L_0x006b
        L_0x0063:
            gnu.text.Char r6 = gnu.text.Char.make(r4)
            gnu.lists.Pair r6 = kawa.lib.lists.cons(r6, r3)
        L_0x006b:
            r3 = r6
            r2 = r5
            goto L_0x0012
        L_0x006e:
            r8 = move-exception
            gnu.mapping.WrongType r1 = new gnu.mapping.WrongType
            r3 = 2
            r1.<init>((java.lang.ClassCastException) r8, (java.lang.String) r0, (int) r3, (java.lang.Object) r2)
            throw r1
        L_0x0076:
            r2 = move-exception
            gnu.mapping.WrongType r3 = new gnu.mapping.WrongType
            r3.<init>((java.lang.ClassCastException) r2, (java.lang.String) r0, (int) r1, (java.lang.Object) r8)
            throw r3
        L_0x007d:
            r0 = move-exception
            gnu.mapping.WrongType r2 = new gnu.mapping.WrongType
            java.lang.String r3 = "string-length"
            r2.<init>((java.lang.ClassCastException) r0, (java.lang.String) r3, (int) r1, (java.lang.Object) r8)
            goto L_0x0087
        L_0x0086:
            throw r2
        L_0x0087:
            goto L_0x0086
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpQuote(java.lang.Object):java.lang.Object");
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 16:
                return pregexpReverse$Ex(obj);
            case 28:
                return pregexpInvertCharList(obj);
            case 31:
                return isPregexpCharWord(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 35:
                return pregexpMakeBackrefList(obj);
            case 44:
                return pregexp(obj);
            case 50:
                return pregexpQuote(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }
}
