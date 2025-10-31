package kawa.lib;

import androidx.fragment.app.FragmentTransaction;
import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.Special;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.DivideOp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.standard.Scheme;
import kawa.standard.append;

/* compiled from: srfi95.scm */
public class srfi95 extends ModuleBody {
    public static final ModuleMethod $Pcsort$Mnlist;
    public static final ModuleMethod $Pcsort$Mnvector;
    public static final ModuleMethod $Pcvector$Mnsort$Ex;
    public static final srfi95 $instance = new srfi95();
    static final IntNum Lit0 = IntNum.make(-1);
    static final IntNum Lit1 = IntNum.make(2);
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("%vector-sort!").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("%sort-vector").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("sort").readResolve());
    static final IntNum Lit2 = IntNum.make(1);
    static final IntNum Lit3 = IntNum.make(0);
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("identity").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("sorted?").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("merge").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("merge!").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("%sort-list").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("sort!").readResolve());
    static final ModuleMethod identity;
    public static final ModuleMethod merge;
    public static final ModuleMethod merge$Ex;
    public static final ModuleMethod sort;
    public static final ModuleMethod sort$Ex;
    public static final ModuleMethod sorted$Qu;

    public static void $PcSortVector(Sequence sequence, Object obj) {
        $PcSortVector(sequence, obj, Boolean.FALSE);
    }

    static {
        srfi95 srfi95 = $instance;
        identity = new ModuleMethod(srfi95, 1, Lit4, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        sorted$Qu = new ModuleMethod(srfi95, 2, Lit5, 12290);
        merge = new ModuleMethod(srfi95, 4, Lit6, 16387);
        merge$Ex = new ModuleMethod(srfi95, 6, Lit7, 16387);
        $Pcsort$Mnlist = new ModuleMethod(srfi95, 8, Lit8, 12291);
        sort$Ex = new ModuleMethod(srfi95, 9, Lit9, 12290);
        $Pcvector$Mnsort$Ex = new ModuleMethod(srfi95, 11, Lit10, 12291);
        $Pcsort$Mnvector = new ModuleMethod(srfi95, 12, Lit11, 12290);
        sort = new ModuleMethod(srfi95, 14, Lit12, 12290);
        $instance.run();
    }

    public srfi95() {
        ModuleInfo.register(this);
    }

    public static Object isSorted(Object obj, Object obj2) {
        return isSorted(obj, obj2, identity);
    }

    public static Object merge(Object obj, Object obj2, Object obj3) {
        return merge(obj, obj2, obj3, identity);
    }

    public static Object merge$Ex(Object obj, Object obj2, Object obj3) {
        return merge$Ex(obj, obj2, obj3, identity);
    }

    public static Object sort(Sequence sequence, Object obj) {
        return sort(sequence, obj, Boolean.FALSE);
    }

    public static Object sort$Ex(Sequence sequence, Object obj) {
        return sort$Ex(sequence, obj, Boolean.FALSE);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    static Object identity(Object x) {
        return x;
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        return moduleMethod.selector == 1 ? identity(obj) : super.apply1(moduleMethod, obj);
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

    public static Object isSorted(Object obj, Object obj2, Object obj3) {
        if (lists.isNull(obj)) {
            return Boolean.TRUE;
        }
        boolean z = false;
        if (obj instanceof Sequence) {
            try {
                Sequence sequence = (Sequence) obj;
                int size = sequence.size() - 1;
                if (size <= 1) {
                    z = true;
                }
                if (z) {
                    return z ? Boolean.TRUE : Boolean.FALSE;
                }
                Object valueOf = Integer.valueOf(size - 1);
                Object apply2 = Scheme.applyToArgs.apply2(obj3, sequence.get(size));
                while (true) {
                    try {
                        boolean isNegative = numbers.isNegative(LangObjType.coerceRealNum(valueOf));
                        if (isNegative) {
                            return isNegative ? Boolean.TRUE : Boolean.FALSE;
                        }
                        try {
                            Object apply22 = Scheme.applyToArgs.apply2(obj3, sequence.get(((Number) valueOf).intValue()));
                            Object apply3 = Scheme.applyToArgs.apply3(obj2, apply22, apply2);
                            if (apply3 == Boolean.FALSE) {
                                return apply3;
                            }
                            valueOf = AddOp.$Pl.apply2(Lit0, valueOf);
                            apply2 = apply22;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "gnu.lists.Sequence.get(int)", 2, valueOf);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "negative?", 1, valueOf);
                    }
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "arr", -2, obj);
            }
        } else if (lists.isNull(lists.cdr.apply1(obj))) {
            return Boolean.TRUE;
        } else {
            Object apply23 = Scheme.applyToArgs.apply2(obj3, lists.car.apply1(obj));
            Object apply1 = lists.cdr.apply1(obj);
            while (true) {
                boolean isNull = lists.isNull(apply1);
                if (isNull) {
                    return isNull ? Boolean.TRUE : Boolean.FALSE;
                }
                Object apply24 = Scheme.applyToArgs.apply2(obj3, lists.car.apply1(apply1));
                Object apply32 = Scheme.applyToArgs.apply3(obj2, apply24, apply23);
                try {
                    int i = ((apply32 != Boolean.FALSE ? 1 : 0) + 1) & 1;
                    if (i == 0) {
                        return i != 0 ? Boolean.TRUE : Boolean.FALSE;
                    }
                    apply1 = lists.cdr.apply1(apply1);
                    apply23 = apply24;
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "x", -2, apply32);
                }
            }
        }
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 2:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 9:
                if (!(obj instanceof Sequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 12:
                if (!(obj instanceof Sequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 14:
                if (!(obj instanceof Sequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
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
            case 4:
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
            case 8:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 9:
                if (!(obj instanceof Sequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 11:
                if (!(obj instanceof Sequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 12:
                if (!(obj instanceof Sequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 14:
                if (!(obj instanceof Sequence)) {
                    return -786431;
                }
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

    public static Object merge(Object a, Object b, Object isLess, Object key) {
        frame frame2 = new frame();
        frame2.less$Qu = isLess;
        frame2.key = key;
        if (lists.isNull(a)) {
            return b;
        }
        if (lists.isNull(b)) {
            return a;
        }
        return frame2.lambda1loop(lists.car.apply1(a), Scheme.applyToArgs.apply2(frame2.key, lists.car.apply1(a)), lists.cdr.apply1(a), lists.car.apply1(b), Scheme.applyToArgs.apply2(frame2.key, lists.car.apply1(b)), lists.cdr.apply1(b));
    }

    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 4:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            case 6:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            default:
                return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
        }
    }

    /* compiled from: srfi95.scm */
    public class frame extends ModuleBody {
        Object key;
        Object less$Qu;

        public Object lambda1loop(Object x, Object kx, Object a, Object y, Object ky, Object b) {
            if (Scheme.applyToArgs.apply3(this.less$Qu, ky, kx) != Boolean.FALSE) {
                if (lists.isNull(b)) {
                    return lists.cons(y, lists.cons(x, a));
                }
                return lists.cons(y, lambda1loop(x, kx, a, lists.car.apply1(b), Scheme.applyToArgs.apply2(this.key, lists.car.apply1(b)), lists.cdr.apply1(b)));
            } else if (lists.isNull(a)) {
                return lists.cons(x, lists.cons(y, b));
            } else {
                return lists.cons(x, lambda1loop(lists.car.apply1(a), Scheme.applyToArgs.apply2(this.key, lists.car.apply1(a)), lists.cdr.apply1(a), y, ky, b));
            }
        }
    }

    /* compiled from: srfi95.scm */
    public class frame1 extends ModuleBody {
        Object key;
        Object less$Qu;

        public Object lambda3loop(Object r, Object a, Object kcara, Object b, Object kcarb) {
            if (Scheme.applyToArgs.apply3(this.less$Qu, kcarb, kcara) != Boolean.FALSE) {
                try {
                    lists.setCdr$Ex((Pair) r, b);
                    if (lists.isNull(lists.cdr.apply1(b))) {
                        try {
                            lists.setCdr$Ex((Pair) b, a);
                            return Values.empty;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "set-cdr!", 1, b);
                        }
                    } else {
                        return lambda3loop(b, a, kcara, lists.cdr.apply1(b), Scheme.applyToArgs.apply2(this.key, lists.cadr.apply1(b)));
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "set-cdr!", 1, r);
                }
            } else {
                try {
                    lists.setCdr$Ex((Pair) r, a);
                    if (lists.isNull(lists.cdr.apply1(a))) {
                        try {
                            lists.setCdr$Ex((Pair) a, b);
                            return Values.empty;
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "set-cdr!", 1, a);
                        }
                    } else {
                        return lambda3loop(a, lists.cdr.apply1(a), Scheme.applyToArgs.apply2(this.key, lists.cadr.apply1(a)), b, kcarb);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "set-cdr!", 1, r);
                }
            }
        }
    }

    static Object sort$ClMerge$Ex(Object a, Object b, Object isLess, Object key) {
        frame1 frame12 = new frame1();
        frame12.less$Qu = isLess;
        frame12.key = key;
        if (!lists.isNull(a)) {
            if (!lists.isNull(b)) {
                Object apply2 = Scheme.applyToArgs.apply2(frame12.key, lists.car.apply1(a));
                Object kcarb = Scheme.applyToArgs.apply2(frame12.key, lists.car.apply1(b));
                Object kcara = apply2;
                if (Scheme.applyToArgs.apply3(frame12.less$Qu, kcarb, kcara) != Boolean.FALSE) {
                    if (lists.isNull(lists.cdr.apply1(b))) {
                        try {
                            lists.setCdr$Ex((Pair) b, a);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "set-cdr!", 1, b);
                        }
                    } else {
                        frame12.lambda3loop(b, a, kcara, lists.cdr.apply1(b), Scheme.applyToArgs.apply2(frame12.key, lists.cadr.apply1(b)));
                    }
                } else if (lists.isNull(lists.cdr.apply1(a))) {
                    try {
                        lists.setCdr$Ex((Pair) a, b);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "set-cdr!", 1, a);
                    }
                } else {
                    frame12.lambda3loop(a, lists.cdr.apply1(a), Scheme.applyToArgs.apply2(frame12.key, lists.cadr.apply1(a)), b, kcarb);
                }
            }
            return a;
        }
        return b;
    }

    public static Object merge$Ex(Object a, Object b, Object less$Qu, Object key) {
        return sort$ClMerge$Ex(a, b, less$Qu, key);
    }

    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        switch (moduleMethod.selector) {
            case 4:
                return merge(obj, obj2, obj3, obj4);
            case 6:
                return merge$Ex(obj, obj2, obj3, obj4);
            default:
                return super.apply4(moduleMethod, obj, obj2, obj3, obj4);
        }
    }

    public static Object $PcSortList(Object seq, Object isLess, Object key) {
        frame0 frame02 = new frame0();
        frame02.seq = seq;
        frame02.less$Qu = isLess;
        frame02.keyer = Special.undefined;
        frame02.keyer = key != Boolean.FALSE ? lists.car : identity;
        if (key != Boolean.FALSE) {
            Object lst = frame02.seq;
            while (!lists.isNull(lst)) {
                try {
                    lists.setCar$Ex((Pair) lst, lists.cons(Scheme.applyToArgs.apply2(key, lists.car.apply1(lst)), lists.car.apply1(lst)));
                    lst = lists.cdr.apply1(lst);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "set-car!", 1, lst);
                }
            }
            Object lst2 = frame02.seq;
            try {
                frame02.seq = frame02.lambda2step(Integer.valueOf(lists.length((LList) lst2)));
                Object lst3 = frame02.seq;
                while (!lists.isNull(lst3)) {
                    try {
                        lists.setCar$Ex((Pair) lst3, lists.cdar.apply1(lst3));
                        lst3 = lists.cdr.apply1(lst3);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "set-car!", 1, lst3);
                    }
                }
                return frame02.seq;
            } catch (ClassCastException e3) {
                throw new WrongType(e3, PropertyTypeConstants.PROPERTY_TYPE_LENGTH, 1, lst2);
            }
        } else {
            Object obj = frame02.seq;
            try {
                return frame02.lambda2step(Integer.valueOf(lists.length((LList) obj)));
            } catch (ClassCastException e4) {
                throw new WrongType(e4, PropertyTypeConstants.PROPERTY_TYPE_LENGTH, 1, obj);
            }
        }
    }

    /* compiled from: srfi95.scm */
    public class frame0 extends ModuleBody {
        Object keyer;
        Object less$Qu;
        Object seq;

        public Object lambda2step(Object n) {
            if (Scheme.numGrt.apply2(n, srfi95.Lit1) != Boolean.FALSE) {
                Object j = DivideOp.quotient.apply2(n, srfi95.Lit1);
                return srfi95.sort$ClMerge$Ex(lambda2step(j), lambda2step(AddOp.$Mn.apply2(n, j)), this.less$Qu, this.keyer);
            } else if (Scheme.numEqu.apply2(n, srfi95.Lit1) != Boolean.FALSE) {
                Object x = lists.car.apply1(this.seq);
                Object y = lists.cadr.apply1(this.seq);
                Object p = this.seq;
                this.seq = lists.cddr.apply1(this.seq);
                if (Scheme.applyToArgs.apply3(this.less$Qu, Scheme.applyToArgs.apply2(this.keyer, y), Scheme.applyToArgs.apply2(this.keyer, x)) != Boolean.FALSE) {
                    try {
                        lists.setCar$Ex((Pair) p, y);
                        Object apply1 = lists.cdr.apply1(p);
                        try {
                            lists.setCar$Ex((Pair) apply1, x);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "set-car!", 1, apply1);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "set-car!", 1, p);
                    }
                }
                Object apply12 = lists.cdr.apply1(p);
                try {
                    lists.setCdr$Ex((Pair) apply12, LList.Empty);
                    return p;
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "set-cdr!", 1, apply12);
                }
            } else if (Scheme.numEqu.apply2(n, srfi95.Lit2) == Boolean.FALSE) {
                return LList.Empty;
            } else {
                Object p2 = this.seq;
                this.seq = lists.cdr.apply1(this.seq);
                try {
                    lists.setCdr$Ex((Pair) p2, LList.Empty);
                    return p2;
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "set-cdr!", 1, p2);
                }
            }
        }
    }

    static Object rank$Mn1Array$To$List(Sequence seq) {
        Object lst = LList.Empty;
        for (int idx = seq.size() - 1; idx >= 0; idx--) {
            lst = lists.cons(seq.get(idx), lst);
        }
        return lst;
    }

    public static Object sort$Ex(Sequence seq, Object less$Qu, Object key) {
        if (!lists.isList(seq)) {
            return $PcVectorSort$Ex(seq, less$Qu, key);
        }
        Object ret = $PcSortList(seq, less$Qu, key);
        if (ret != seq) {
            Object crt = ret;
            while (lists.cdr.apply1(crt) != seq) {
                crt = lists.cdr.apply1(crt);
            }
            try {
                lists.setCdr$Ex((Pair) crt, ret);
                Object scar = lists.car.apply1(seq);
                Object obj = crt;
                Object scdr = lists.cdr.apply1(seq);
                try {
                    lists.setCar$Ex((Pair) seq, lists.car.apply1(ret));
                    try {
                        lists.setCdr$Ex((Pair) seq, lists.cdr.apply1(ret));
                        try {
                            lists.setCar$Ex((Pair) ret, scar);
                            try {
                                lists.setCdr$Ex((Pair) ret, scdr);
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "set-cdr!", 1, ret);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "set-car!", 1, ret);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "set-cdr!", 1, (Object) seq);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "set-car!", 1, (Object) seq);
                }
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "set-cdr!", 1, crt);
            }
        }
        return seq;
    }

    public static Object $PcVectorSort$Ex(Sequence seq, Object less$Qu, Object key) {
        Object sorted = $PcSortList(rank$Mn1Array$To$List(seq), less$Qu, key);
        Object i = Lit3;
        while (!lists.isNull(sorted)) {
            seq.set(((Number) i).intValue(), lists.car.apply1(sorted));
            Object apply1 = lists.cdr.apply1(sorted);
            i = AddOp.$Pl.apply2(i, Lit2);
            sorted = apply1;
        }
        return seq;
    }

    public static void $PcSortVector(Sequence seq, Object less$Qu, Object key) {
        FVector newra = vectors.makeVector(seq.size());
        Object sorted = $PcSortList(rank$Mn1Array$To$List(seq), less$Qu, key);
        Object i = Lit3;
        while (!lists.isNull(sorted)) {
            try {
                vectors.vectorSet$Ex(newra, ((Number) i).intValue(), lists.car.apply1(sorted));
                Object apply1 = lists.cdr.apply1(sorted);
                i = AddOp.$Pl.apply2(i, Lit2);
                sorted = apply1;
            } catch (ClassCastException e) {
                throw new WrongType(e, "vector-set!", 1, i);
            }
        }
    }

    public static Object sort(Sequence seq, Object less$Qu, Object key) {
        if (lists.isList(seq)) {
            return $PcSortList(append.append$V(new Object[]{seq, LList.Empty}), less$Qu, key);
        }
        $PcSortVector(seq, less$Qu, key);
        return Values.empty;
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 2:
                return isSorted(obj, obj2);
            case 9:
                try {
                    return sort$Ex((Sequence) obj, obj2);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "sort!", 1, obj);
                }
            case 12:
                try {
                    $PcSortVector((Sequence) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "%sort-vector", 1, obj);
                }
            case 14:
                try {
                    return sort((Sequence) obj, obj2);
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "sort", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 2:
                return isSorted(obj, obj2, obj3);
            case 4:
                return merge(obj, obj2, obj3);
            case 6:
                return merge$Ex(obj, obj2, obj3);
            case 8:
                return $PcSortList(obj, obj2, obj3);
            case 9:
                try {
                    return sort$Ex((Sequence) obj, obj2, obj3);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "sort!", 1, obj);
                }
            case 11:
                try {
                    return $PcVectorSort$Ex((Sequence) obj, obj2, obj3);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "%vector-sort!", 1, obj);
                }
            case 12:
                try {
                    $PcSortVector((Sequence) obj, obj2, obj3);
                    return Values.empty;
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "%sort-vector", 1, obj);
                }
            case 14:
                try {
                    return sort((Sequence) obj, obj2, obj3);
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "sort", 1, obj);
                }
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }
}
