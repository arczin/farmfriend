package gnu.kawa.slib;

import androidx.fragment.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Format;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.strings;
import kawa.lib.vectors;
import kawa.standard.Scheme;

/* compiled from: genwrite.scm */
public class genwrite extends ModuleBody {
    public static final genwrite $instance = new genwrite();
    static final Char Lit0 = Char.make(10);
    static final IntNum Lit1 = IntNum.make(0);
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("and").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("or").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("let").readResolve());
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("begin").readResolve());
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("do").readResolve());
    static final IntNum Lit15 = IntNum.make(7);
    static final IntNum Lit16 = IntNum.make(8);
    static final IntNum Lit17 = IntNum.make(1);
    static final IntNum Lit18 = IntNum.make(50);
    static final IntNum Lit19 = IntNum.make(2);
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("lambda").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("pp-expr").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("pp-expr-list").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("pp-LAMBDA").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("pp-IF").readResolve());
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("pp-COND").readResolve());
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("pp-CASE").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("pp-AND").readResolve());
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("pp-LET").readResolve());
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("pp-BEGIN").readResolve());
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("pp-DO").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("let*").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quasiquote_sym).readResolve());
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.unquote_sym).readResolve());
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.unquotesplicing_sym).readResolve());
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("generic-write").readResolve());
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("reverse-string-append").readResolve());
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("letrec").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("define").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("if").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("set!").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("cond").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("case").readResolve());
    public static final ModuleMethod generic$Mnwrite;
    public static final ModuleMethod reverse$Mnstring$Mnappend;

    static {
        genwrite genwrite = $instance;
        generic$Mnwrite = new ModuleMethod(genwrite, 12, Lit34, 16388);
        reverse$Mnstring$Mnappend = new ModuleMethod(genwrite, 13, Lit35, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $instance.run();
    }

    public genwrite() {
        ModuleInfo.register(this);
    }

    public static Object genericWrite(Object obj, Object isDisplay, Object width, Object output) {
        frame closureEnv = new frame();
        closureEnv.display$Qu = isDisplay;
        closureEnv.width = width;
        closureEnv.output = output;
        if (closureEnv.width != Boolean.FALSE) {
            CharSequence makeString = strings.makeString(1, Lit0);
            Object col = Lit1;
            frame0 frame02 = new frame0();
            frame02.staticLink = closureEnv;
            Procedure procedure = frame02.pp$Mnexpr;
            Procedure procedure2 = frame02.pp$Mnexpr$Mnlist;
            Procedure procedure3 = frame02.pp$MnLAMBDA;
            Procedure procedure4 = frame02.pp$MnIF;
            Procedure procedure5 = frame02.pp$MnCOND;
            Procedure procedure6 = frame02.pp$MnCASE;
            Procedure procedure7 = frame02.pp$MnAND;
            Procedure procedure8 = frame02.pp$MnLET;
            Procedure procedure9 = frame02.pp$MnBEGIN;
            frame02.pp$MnDO = frame02.pp$MnDO;
            frame02.pp$MnBEGIN = procedure9;
            frame02.pp$MnLET = procedure8;
            frame02.pp$MnAND = procedure7;
            frame02.pp$MnCASE = procedure6;
            frame02.pp$MnCOND = procedure5;
            frame02.pp$MnIF = procedure4;
            frame02.pp$MnLAMBDA = procedure3;
            frame02.pp$Mnexpr$Mnlist = procedure2;
            frame02.pp$Mnexpr = procedure;
            Object obj2 = obj;
            return closureEnv.lambda4out(makeString, frame02.lambda7pr(obj, col, Lit1, frame02.pp$Mnexpr));
        }
        return closureEnv.lambda5wr(obj, Lit1);
    }

    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        return moduleMethod.selector == 12 ? genericWrite(obj, obj2, obj3, obj4) : super.apply4(moduleMethod, obj, obj2, obj3, obj4);
    }

    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        if (moduleMethod.selector != 12) {
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

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    /* compiled from: genwrite.scm */
    public class frame extends ModuleBody {
        Object display$Qu;
        Object output;
        Object width;

        public static Object lambda1isReadMacro(Object obj) {
            Object apply2;
            Object apply22;
            Object apply1 = lists.car.apply1(obj);
            Object apply12 = lists.cdr.apply1(obj);
            Object apply23 = Scheme.isEqv.apply2(apply1, genwrite.Lit30);
            if (apply23 == Boolean.FALSE ? (apply2 = Scheme.isEqv.apply2(apply1, genwrite.Lit31)) == Boolean.FALSE ? (apply22 = Scheme.isEqv.apply2(apply1, genwrite.Lit32)) == Boolean.FALSE ? Scheme.isEqv.apply2(apply1, genwrite.Lit33) == Boolean.FALSE : apply22 == Boolean.FALSE : apply2 == Boolean.FALSE : apply23 == Boolean.FALSE) {
                return Boolean.FALSE;
            }
            boolean isPair = lists.isPair(apply12);
            return isPair ? lists.isNull(lists.cdr.apply1(apply12)) ? Boolean.TRUE : Boolean.FALSE : isPair ? Boolean.TRUE : Boolean.FALSE;
        }

        public static Object lambda2readMacroBody(Object l) {
            return lists.cadr.apply1(l);
        }

        public static Object lambda3readMacroPrefix(Object l) {
            Object head = lists.car.apply1(l);
            lists.cdr.apply1(l);
            if (Scheme.isEqv.apply2(head, genwrite.Lit30) != Boolean.FALSE) {
                return "'";
            }
            if (Scheme.isEqv.apply2(head, genwrite.Lit31) != Boolean.FALSE) {
                return "`";
            }
            if (Scheme.isEqv.apply2(head, genwrite.Lit32) != Boolean.FALSE) {
                return ",";
            }
            return Scheme.isEqv.apply2(head, genwrite.Lit33) != Boolean.FALSE ? ",@" : Values.empty;
        }

        public Object lambda4out(Object str, Object col) {
            if (col == Boolean.FALSE) {
                return col;
            }
            Object x = Scheme.applyToArgs.apply2(this.output, str);
            if (x == Boolean.FALSE) {
                return x;
            }
            try {
                return AddOp.$Pl.apply2(col, Integer.valueOf(strings.stringLength((CharSequence) str)));
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, str);
            }
        }

        public Object lambda5wr(Object obj, Object col) {
            Object expr;
            Object l;
            Object obj2;
            if (lists.isPair(obj)) {
                Object expr2 = obj;
                Object col2 = col;
                if (lambda1isReadMacro(expr2) != Boolean.FALSE) {
                    return lambda5wr(lambda2readMacroBody(expr2), lambda4out(lambda3readMacroPrefix(expr2), col2));
                }
                l = expr2;
                expr = col2;
            } else if (lists.isNull(obj)) {
                l = obj;
                expr = col;
            } else if (vectors.isVector(obj)) {
                try {
                    l = vectors.vector$To$List((FVector) obj);
                    expr = lambda4out("#", col);
                    Object col3 = obj;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "vector->list", 1, obj);
                }
            } else {
                return lambda4out(Format.formatToString(0, this.display$Qu != Boolean.FALSE ? "~a" : "~s", obj), col);
            }
            if (!lists.isPair(l)) {
                return lambda4out("()", expr);
            }
            Object l2 = lists.cdr.apply1(l);
            Object col4 = expr != Boolean.FALSE ? lambda5wr(lists.car.apply1(l), lambda4out("(", expr)) : expr;
            while (true) {
                if (col4 == Boolean.FALSE) {
                    obj2 = col4;
                    break;
                } else if (lists.isPair(l2)) {
                    Object l3 = lists.cdr.apply1(l2);
                    col4 = lambda5wr(lists.car.apply1(l2), lambda4out(" ", col4));
                    l2 = l3;
                } else if (lists.isNull(l2)) {
                    obj2 = lambda4out(")", col4);
                } else {
                    obj2 = lambda4out(")", lambda5wr(l2, lambda4out(" . ", col4)));
                }
            }
            return obj2;
        }
    }

    /* compiled from: genwrite.scm */
    public class frame0 extends ModuleBody {
        Procedure pp$MnAND = new ModuleMethod(this, 8, genwrite.Lit26, 12291);
        Procedure pp$MnBEGIN = new ModuleMethod(this, 10, genwrite.Lit28, 12291);
        Procedure pp$MnCASE = new ModuleMethod(this, 7, genwrite.Lit25, 12291);
        Procedure pp$MnCOND = new ModuleMethod(this, 6, genwrite.Lit24, 12291);
        Procedure pp$MnDO = new ModuleMethod(this, 11, genwrite.Lit29, 12291);
        Procedure pp$MnIF = new ModuleMethod(this, 5, genwrite.Lit23, 12291);
        Procedure pp$MnLAMBDA = new ModuleMethod(this, 4, genwrite.Lit22, 12291);
        Procedure pp$MnLET = new ModuleMethod(this, 9, genwrite.Lit27, 12291);
        Procedure pp$Mnexpr = new ModuleMethod(this, 2, genwrite.Lit20, 12291);
        Procedure pp$Mnexpr$Mnlist = new ModuleMethod(this, 3, genwrite.Lit21, 12291);
        frame staticLink;

        public Object lambda6indent(Object to, Object col) {
            Object n;
            Object x;
            if (col == Boolean.FALSE) {
                return col;
            }
            if (Scheme.numLss.apply2(to, col) != Boolean.FALSE) {
                Object x2 = this.staticLink.lambda4out(strings.makeString(1, genwrite.Lit0), col);
                if (x2 == Boolean.FALSE) {
                    return x2;
                }
                x = genwrite.Lit1;
                n = to;
            } else {
                n = AddOp.$Mn.apply2(to, col);
                x = col;
            }
            while (true) {
                if (Scheme.numGrt.apply2(n, genwrite.Lit1) == Boolean.FALSE) {
                    break;
                } else if (Scheme.numGrt.apply2(n, genwrite.Lit15) != Boolean.FALSE) {
                    n = AddOp.$Mn.apply2(n, genwrite.Lit16);
                    x = this.staticLink.lambda4out("        ", x);
                } else {
                    try {
                        x = this.staticLink.lambda4out(strings.substring("        ", 0, ((Number) n).intValue()), x);
                        break;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "substring", 3, n);
                    }
                }
            }
            return x;
        }

        public Object lambda7pr(Object obj, Object col, Object extra, Object pp$Mnpair) {
            frame1 frame1 = new frame1();
            frame1.staticLink = this;
            boolean x = lists.isPair(obj);
            if (!x ? !vectors.isVector(obj) : !x) {
                return this.staticLink.lambda5wr(obj, col);
            }
            LList lList = LList.Empty;
            frame1.left = numbers.min(AddOp.$Pl.apply2(AddOp.$Mn.apply2(AddOp.$Mn.apply2(this.staticLink.width, col), extra), genwrite.Lit17), genwrite.Lit18);
            frame1.result = lList;
            genwrite.genericWrite(obj, this.staticLink.display$Qu, Boolean.FALSE, frame1.lambda$Fn1);
            if (Scheme.numGrt.apply2(frame1.left, genwrite.Lit1) != Boolean.FALSE) {
                return this.staticLink.lambda4out(genwrite.reverseStringAppend(frame1.result), col);
            }
            if (lists.isPair(obj)) {
                return Scheme.applyToArgs.apply4(pp$Mnpair, obj, col, extra);
            }
            try {
                return lambda10ppList(vectors.vector$To$List((FVector) obj), this.staticLink.lambda4out("#", col), extra, this.pp$Mnexpr);
            } catch (ClassCastException e) {
                throw new WrongType(e, "vector->list", 1, obj);
            }
        }

        public Object lambda8ppExpr(Object expr, Object col, Object extra) {
            Object x;
            Object head;
            Object x2;
            Object x3;
            if (frame.lambda1isReadMacro(expr) != Boolean.FALSE) {
                return lambda7pr(frame.lambda2readMacroBody(expr), this.staticLink.lambda4out(frame.lambda3readMacroPrefix(expr), col), extra, this.pp$Mnexpr);
            }
            Object head2 = lists.car.apply1(expr);
            if (!misc.isSymbol(head2)) {
                return lambda10ppList(expr, col, extra, this.pp$Mnexpr);
            }
            Object head3 = head2;
            Object x4 = Scheme.isEqv.apply2(head3, genwrite.Lit2);
            if (x4 == Boolean.FALSE ? (x2 = Scheme.isEqv.apply2(head3, genwrite.Lit3)) == Boolean.FALSE ? (x3 = Scheme.isEqv.apply2(head3, genwrite.Lit4)) == Boolean.FALSE ? Scheme.isEqv.apply2(head3, genwrite.Lit5) == Boolean.FALSE : x3 == Boolean.FALSE : x2 == Boolean.FALSE : x4 == Boolean.FALSE) {
                Object x5 = Scheme.isEqv.apply2(head3, genwrite.Lit6);
                if (x5 == Boolean.FALSE ? Scheme.isEqv.apply2(head3, genwrite.Lit7) != Boolean.FALSE : x5 != Boolean.FALSE) {
                    x = this.pp$MnIF;
                } else if (Scheme.isEqv.apply2(head3, genwrite.Lit8) != Boolean.FALSE) {
                    x = this.pp$MnCOND;
                } else if (Scheme.isEqv.apply2(head3, genwrite.Lit9) != Boolean.FALSE) {
                    x = this.pp$MnCASE;
                } else {
                    Object x6 = Scheme.isEqv.apply2(head3, genwrite.Lit10);
                    if (x6 == Boolean.FALSE ? Scheme.isEqv.apply2(head3, genwrite.Lit11) != Boolean.FALSE : x6 != Boolean.FALSE) {
                        x = this.pp$MnAND;
                    } else if (Scheme.isEqv.apply2(head3, genwrite.Lit12) != Boolean.FALSE) {
                        x = this.pp$MnLET;
                    } else if (Scheme.isEqv.apply2(head3, genwrite.Lit13) != Boolean.FALSE) {
                        x = this.pp$MnBEGIN;
                    } else {
                        x = Scheme.isEqv.apply2(head3, genwrite.Lit14) != Boolean.FALSE ? this.pp$MnDO : Boolean.FALSE;
                    }
                }
            } else {
                x = this.pp$MnLAMBDA;
            }
            Object proc = x;
            if (proc != Boolean.FALSE) {
                head = Scheme.applyToArgs.apply4(proc, expr, col, extra);
            } else {
                try {
                    if (strings.stringLength(misc.symbol$To$String((Symbol) head2)) > 5) {
                        head = lambda12ppGeneral(expr, col, extra, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, this.pp$Mnexpr);
                    } else {
                        head = lambda9ppCall(expr, col, extra, this.pp$Mnexpr);
                    }
                } catch (ClassCastException e) {
                    throw new WrongType(e, "symbol->string", 1, head2);
                }
            }
            return head;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 2:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 3:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 4:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 5:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 6:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 7:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 8:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 9:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 10:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 11:
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

        public Object lambda9ppCall(Object expr, Object col, Object extra, Object pp$Mnitem) {
            Object col$St = this.staticLink.lambda5wr(lists.car.apply1(expr), this.staticLink.lambda4out("(", col));
            if (col == Boolean.FALSE) {
                return col;
            }
            return lambda11ppDown(lists.cdr.apply1(expr), col$St, AddOp.$Pl.apply2(col$St, genwrite.Lit17), extra, pp$Mnitem);
        }

        public Object lambda10ppList(Object l, Object col, Object extra, Object pp$Mnitem) {
            Object col2 = this.staticLink.lambda4out("(", col);
            return lambda11ppDown(l, col2, col2, extra, pp$Mnitem);
        }

        public Object lambda11ppDown(Object l, Object col1, Object col2, Object extra, Object pp$Mnitem) {
            Object col;
            Object l2 = l;
            Object col3 = col1;
            while (true) {
                if (col3 == Boolean.FALSE) {
                    return col3;
                }
                if (lists.isPair(l2)) {
                    Object rest = lists.cdr.apply1(l2);
                    col3 = lambda7pr(lists.car.apply1(l2), lambda6indent(col2, col3), lists.isNull(rest) ? AddOp.$Pl.apply2(extra, genwrite.Lit17) : genwrite.Lit1, pp$Mnitem);
                    l2 = rest;
                } else {
                    if (lists.isNull(l2)) {
                        col = this.staticLink.lambda4out(")", col3);
                    } else {
                        col = this.staticLink.lambda4out(")", lambda7pr(l2, lambda6indent(col2, this.staticLink.lambda4out(".", lambda6indent(col2, col3))), AddOp.$Pl.apply2(extra, genwrite.Lit17), pp$Mnitem));
                    }
                    return col;
                }
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:24:0x012c  */
        /* JADX WARNING: Removed duplicated region for block: B:27:0x0141  */
        /* JADX WARNING: Removed duplicated region for block: B:31:0x0164  */
        /* JADX WARNING: Removed duplicated region for block: B:32:0x0171  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object lambda12ppGeneral(java.lang.Object r41, java.lang.Object r42, java.lang.Object r43, java.lang.Object r44, java.lang.Object r45, java.lang.Object r46, java.lang.Object r47) {
            /*
                r40 = this;
                r6 = r40
                r7 = r41
                r8 = r42
                r9 = r43
                r10 = r44
                r11 = r45
                r12 = r46
                gnu.expr.GenericProc r0 = kawa.lib.lists.car
                java.lang.Object r0 = r0.apply1(r7)
                r1 = 0
                r2 = r1
                r13 = r0
                gnu.expr.GenericProc r0 = kawa.lib.lists.cdr
                java.lang.Object r0 = r0.apply1(r7)
                r14 = r0
                gnu.kawa.slib.genwrite$frame r0 = r6.staticLink
                gnu.kawa.slib.genwrite$frame r2 = r6.staticLink
                java.lang.String r3 = "("
                java.lang.Object r2 = r2.lambda4out(r3, r8)
                java.lang.Object r0 = r0.lambda5wr(r13, r2)
                r2 = r1
                r15 = r0
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x003e
                boolean r0 = kawa.lib.lists.isPair(r14)
                if (r0 == 0) goto L_0x0085
                goto L_0x0042
            L_0x003e:
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x0085
            L_0x0042:
                gnu.expr.GenericProc r0 = kawa.lib.lists.car
                java.lang.Object r0 = r0.apply1(r14)
                r2 = r1
                gnu.expr.GenericProc r2 = kawa.lib.lists.cdr
                java.lang.Object r2 = r2.apply1(r14)
                r3 = r1
                gnu.kawa.slib.genwrite$frame r3 = r6.staticLink
                gnu.kawa.slib.genwrite$frame r4 = r6.staticLink
                java.lang.String r5 = " "
                java.lang.Object r4 = r4.lambda4out(r5, r15)
                java.lang.Object r3 = r3.lambda5wr(r0, r4)
                r4 = r1
                gnu.kawa.functions.AddOp r4 = gnu.kawa.functions.AddOp.$Pl
                gnu.math.IntNum r5 = gnu.kawa.slib.genwrite.Lit19
                java.lang.Object r4 = r4.apply2(r8, r5)
                gnu.kawa.functions.AddOp r5 = gnu.kawa.functions.AddOp.$Pl
                gnu.math.IntNum r1 = gnu.kawa.slib.genwrite.Lit17
                java.lang.Object r1 = r5.apply2(r3, r1)
                r5 = r2
                r17 = r3
                r16 = 0
                r18 = r16
                r19 = r3
                r20 = r4
                r4 = r5
                r17 = r0
                r5 = r1
                r0 = r18
                r18 = r2
                goto L_0x00aa
            L_0x0085:
                gnu.kawa.functions.AddOp r0 = gnu.kawa.functions.AddOp.$Pl
                gnu.math.IntNum r1 = gnu.kawa.slib.genwrite.Lit19
                java.lang.Object r0 = r0.apply2(r8, r1)
                gnu.kawa.functions.AddOp r1 = gnu.kawa.functions.AddOp.$Pl
                gnu.math.IntNum r2 = gnu.kawa.slib.genwrite.Lit17
                java.lang.Object r1 = r1.apply2(r15, r2)
                r4 = r0
                r5 = r14
                r0 = 0
                r2 = r0
                r3 = r0
                r17 = r0
                r18 = r15
                r19 = r0
                r20 = r4
                r4 = r5
                r5 = r1
                r19 = r3
                r3 = r18
                r18 = r2
            L_0x00aa:
                r21 = r40
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r11 == r0) goto L_0x00ba
                boolean r0 = kawa.lib.lists.isPair(r4)
                if (r0 == 0) goto L_0x00b7
                goto L_0x00be
            L_0x00b7:
                r16 = 0
                goto L_0x0108
            L_0x00ba:
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r11 == r0) goto L_0x0106
            L_0x00be:
                gnu.expr.GenericProc r0 = kawa.lib.lists.car
                java.lang.Object r0 = r0.apply1(r4)
                r1 = 0
                r2 = r1
                gnu.expr.GenericProc r2 = kawa.lib.lists.cdr
                java.lang.Object r2 = r2.apply1(r4)
                r16 = r1
                boolean r16 = kawa.lib.lists.isNull(r2)
                if (r16 == 0) goto L_0x00df
                gnu.kawa.functions.AddOp r1 = gnu.kawa.functions.AddOp.$Pl
                gnu.math.IntNum r7 = gnu.kawa.slib.genwrite.Lit17
                java.lang.Object r1 = r1.apply2(r9, r7)
                r7 = 0
                goto L_0x00e2
            L_0x00df:
                r7 = r1
                gnu.math.IntNum r1 = gnu.kawa.slib.genwrite.Lit1
            L_0x00e2:
                r16 = r7
                java.lang.Object r7 = r6.lambda6indent(r5, r3)
                java.lang.Object r7 = r6.lambda7pr(r0, r7, r1, r11)
                r22 = r5
                r16 = 0
                r23 = r16
                r24 = r20
                r25 = r2
                r38 = r7
                r7 = r0
                r0 = r23
                r23 = r2
                r2 = r38
                r39 = r22
                r22 = r1
                r1 = r39
                goto L_0x0126
            L_0x0106:
                r16 = 0
            L_0x0108:
                r22 = r5
                r7 = r3
                r23 = r16
                r0 = r16
                r24 = r20
                r2 = r16
                r25 = r4
                r1 = r16
                r38 = r7
                r7 = r0
                r0 = r23
                r23 = r2
                r2 = r38
                r39 = r22
                r22 = r1
                r1 = r39
            L_0x0126:
                r26 = r40
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r12 == r0) goto L_0x0141
                boolean r0 = kawa.lib.lists.isPair(r25)
                if (r0 == 0) goto L_0x0133
                goto L_0x0145
            L_0x0133:
                r28 = r4
                r29 = r5
                r27 = r7
                r7 = r25
                r16 = 0
                r25 = r3
                goto L_0x01a1
            L_0x0141:
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r12 == r0) goto L_0x0195
            L_0x0145:
                gnu.expr.GenericProc r0 = kawa.lib.lists.car
                r27 = r7
                r7 = r25
                java.lang.Object r0 = r0.apply1(r7)
                r16 = 0
                r25 = r16
                r25 = r3
                gnu.expr.GenericProc r3 = kawa.lib.lists.cdr
                java.lang.Object r3 = r3.apply1(r7)
                r28 = r16
                boolean r28 = kawa.lib.lists.isNull(r3)
                if (r28 == 0) goto L_0x0171
                r28 = r4
                gnu.kawa.functions.AddOp r4 = gnu.kawa.functions.AddOp.$Pl
                r29 = r5
                gnu.math.IntNum r5 = gnu.kawa.slib.genwrite.Lit17
                java.lang.Object r4 = r4.apply2(r9, r5)
                goto L_0x0177
            L_0x0171:
                r28 = r4
                r29 = r5
                gnu.math.IntNum r4 = gnu.kawa.slib.genwrite.Lit1
            L_0x0177:
                r5 = r16
                java.lang.Object r5 = r6.lambda6indent(r1, r2)
                java.lang.Object r5 = r6.lambda7pr(r0, r5, r4, r12)
                r30 = r3
                r31 = r24
                r32 = r5
                r33 = r30
                r34 = r31
                r31 = r4
                r38 = r16
                r16 = r0
                r0 = r38
                goto L_0x01bb
            L_0x0195:
                r28 = r4
                r29 = r5
                r27 = r7
                r7 = r25
                r16 = 0
                r25 = r3
            L_0x01a1:
                r0 = r16
                r4 = r16
                r30 = r7
                r3 = r16
                r5 = r16
                r31 = r24
                r16 = r2
                r32 = r16
                r33 = r30
                r34 = r31
                r30 = r3
                r31 = r4
                r16 = r5
            L_0x01bb:
                r35 = r40
                r0 = r40
                r36 = r1
                r1 = r33
                r37 = r2
                r2 = r32
                r3 = r34
                r4 = r43
                r5 = r47
                java.lang.Object r0 = r0.lambda11ppDown(r1, r2, r3, r4, r5)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.genwrite.frame0.lambda12ppGeneral(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
        }

        public Object lambda13ppExprList(Object l, Object col, Object extra) {
            return lambda10ppList(l, col, extra, this.pp$Mnexpr);
        }

        public Object lambda14pp$MnLAMBDA(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, this.pp$Mnexpr$Mnlist, Boolean.FALSE, this.pp$Mnexpr);
        }

        public Object lambda15pp$MnIF(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, this.pp$Mnexpr, Boolean.FALSE, this.pp$Mnexpr);
        }

        public Object lambda16pp$MnCOND(Object expr, Object col, Object extra) {
            return lambda9ppCall(expr, col, extra, this.pp$Mnexpr$Mnlist);
        }

        public Object lambda17pp$MnCASE(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, this.pp$Mnexpr, Boolean.FALSE, this.pp$Mnexpr$Mnlist);
        }

        public Object lambda18pp$MnAND(Object expr, Object col, Object extra) {
            return lambda9ppCall(expr, col, extra, this.pp$Mnexpr);
        }

        public Object lambda19pp$MnLET(Object expr, Object col, Object extra) {
            Object rest = lists.cdr.apply1(expr);
            boolean x = lists.isPair(rest);
            if (x) {
                x = misc.isSymbol(lists.car.apply1(rest));
            }
            return lambda12ppGeneral(expr, col, extra, x ? Boolean.TRUE : Boolean.FALSE, this.pp$Mnexpr$Mnlist, Boolean.FALSE, this.pp$Mnexpr);
        }

        public Object lambda20pp$MnBEGIN(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, this.pp$Mnexpr);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            switch (moduleMethod.selector) {
                case 2:
                    return lambda8ppExpr(obj, obj2, obj3);
                case 3:
                    return lambda13ppExprList(obj, obj2, obj3);
                case 4:
                    return lambda14pp$MnLAMBDA(obj, obj2, obj3);
                case 5:
                    return lambda15pp$MnIF(obj, obj2, obj3);
                case 6:
                    return lambda16pp$MnCOND(obj, obj2, obj3);
                case 7:
                    return lambda17pp$MnCASE(obj, obj2, obj3);
                case 8:
                    return lambda18pp$MnAND(obj, obj2, obj3);
                case 9:
                    return lambda19pp$MnLET(obj, obj2, obj3);
                case 10:
                    return lambda20pp$MnBEGIN(obj, obj2, obj3);
                case 11:
                    return lambda21pp$MnDO(obj, obj2, obj3);
                default:
                    return super.apply3(moduleMethod, obj, obj2, obj3);
            }
        }

        public Object lambda21pp$MnDO(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, this.pp$Mnexpr$Mnlist, this.pp$Mnexpr$Mnlist, this.pp$Mnexpr);
        }
    }

    /* compiled from: genwrite.scm */
    public class frame1 extends ModuleBody {
        final ModuleMethod lambda$Fn1;
        Object left;
        Object result;
        frame0 staticLink;

        public frame1() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 1, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/genwrite.scm:72");
            this.lambda$Fn1 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 1) {
                return lambda22(obj) ? Boolean.TRUE : Boolean.FALSE;
            }
            return super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: package-private */
        public boolean lambda22(Object str) {
            this.result = lists.cons(str, this.result);
            try {
                this.left = AddOp.$Mn.apply2(this.left, Integer.valueOf(strings.stringLength((CharSequence) str)));
                return ((Boolean) Scheme.numGrt.apply2(this.left, genwrite.Lit1)).booleanValue();
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, str);
            }
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

    public static Object reverseStringAppend(Object l) {
        return lambda23revStringAppend(l, Lit1);
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        return moduleMethod.selector == 13 ? reverseStringAppend(obj) : super.apply1(moduleMethod, obj);
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        if (moduleMethod.selector != 13) {
            return super.match1(moduleMethod, obj, callContext);
        }
        callContext.value1 = obj;
        callContext.proc = moduleMethod;
        callContext.pc = 1;
        return 0;
    }

    public static Object lambda23revStringAppend(Object l, Object i) {
        if (lists.isPair(l)) {
            Object str = lists.car.apply1(l);
            try {
                int len = strings.stringLength((CharSequence) str);
                Object result = lambda23revStringAppend(lists.cdr.apply1(l), AddOp.$Pl.apply2(i, Integer.valueOf(len)));
                Object obj = str;
                Object j = Lit1;
                try {
                    Object k = AddOp.$Mn.apply2(AddOp.$Mn.apply2(Integer.valueOf(strings.stringLength((CharSequence) result)), i), Integer.valueOf(len));
                    while (Scheme.numLss.apply2(j, Integer.valueOf(len)) != Boolean.FALSE) {
                        try {
                            try {
                                try {
                                    try {
                                        strings.stringSet$Ex((CharSeq) result, ((Number) k).intValue(), strings.stringRef((CharSequence) str, ((Number) j).intValue()));
                                        j = AddOp.$Pl.apply2(j, Lit17);
                                        k = AddOp.$Pl.apply2(k, Lit17);
                                    } catch (ClassCastException e) {
                                        throw new WrongType(e, "string-ref", 2, j);
                                    }
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "string-ref", 1, str);
                                }
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "string-set!", 2, k);
                            }
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "string-set!", 1, result);
                        }
                    }
                    return result;
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "string-length", 1, result);
                }
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "string-length", 1, str);
            }
        } else {
            try {
                return strings.makeString(((Number) i).intValue());
            } catch (ClassCastException e7) {
                throw new WrongType(e7, "make-string", 1, i);
            }
        }
    }
}
