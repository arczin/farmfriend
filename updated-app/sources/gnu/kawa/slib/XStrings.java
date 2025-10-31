package gnu.kawa.slib;

import androidx.appcompat.widget.ActivityChooserView;
import androidx.fragment.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.math.IntNum;

/* compiled from: XStrings.scm */
public class XStrings extends ModuleBody {
    public static final XStrings $instance = new XStrings();
    static final IntNum Lit0 = IntNum.make((int) ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("substring").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("string-length").readResolve());
    public static final ModuleMethod string$Mnlength;
    public static final ModuleMethod substring;

    static {
        XStrings xStrings = $instance;
        substring = new ModuleMethod(xStrings, 1, Lit1, 12290);
        string$Mnlength = new ModuleMethod(xStrings, 3, Lit2, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $instance.run();
    }

    public XStrings() {
        ModuleInfo.register(this);
    }

    public static Object substring(Object obj, Object obj2) {
        return substring(obj, obj2, Lit0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000c, code lost:
        if (r0 != false) goto L_0x001e;
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x003f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object substring(java.lang.Object r4, java.lang.Object r5, java.lang.Object r6) {
        /*
            gnu.mapping.Values r0 = gnu.mapping.Values.empty
            r1 = 0
            r2 = 1
            if (r4 != r0) goto L_0x0009
            r0 = 1
            goto L_0x000a
        L_0x0009:
            r0 = 0
        L_0x000a:
            if (r0 == 0) goto L_0x000f
            if (r0 == 0) goto L_0x0021
        L_0x000e:
            goto L_0x001e
        L_0x000f:
            gnu.mapping.Values r0 = gnu.mapping.Values.empty
            if (r5 != r0) goto L_0x0014
            r1 = 1
        L_0x0014:
            if (r1 == 0) goto L_0x0019
            if (r1 == 0) goto L_0x0021
            goto L_0x000e
        L_0x0019:
            gnu.mapping.Values r0 = gnu.mapping.Values.empty
            if (r6 != r0) goto L_0x0021
            goto L_0x000e
        L_0x001e:
            gnu.mapping.Values r4 = gnu.mapping.Values.empty
            goto L_0x0045
        L_0x0021:
            r0 = -2
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ ClassCastException -> 0x0058 }
            int r1 = r4.length()
            r3 = r5
            java.lang.Number r3 = (java.lang.Number) r3     // Catch:{ ClassCastException -> 0x004f }
            int r5 = r3.intValue()     // Catch:{ ClassCastException -> 0x004f }
            int r5 = r5 - r2
            r2 = r6
            java.lang.Number r2 = (java.lang.Number) r2     // Catch:{ ClassCastException -> 0x0046 }
            int r6 = r2.intValue()     // Catch:{ ClassCastException -> 0x0046 }
            int r1 = r1 - r5
            if (r6 <= r1) goto L_0x0040
            r6 = r1
        L_0x0040:
            int r6 = r6 + r5
            java.lang.String r4 = r4.substring(r5, r6)
        L_0x0045:
            return r4
        L_0x0046:
            r4 = move-exception
            gnu.mapping.WrongType r5 = new gnu.mapping.WrongType
            java.lang.String r1 = "len"
            r5.<init>((java.lang.ClassCastException) r4, (java.lang.String) r1, (int) r0, (java.lang.Object) r6)
            throw r5
        L_0x004f:
            r4 = move-exception
            gnu.mapping.WrongType r6 = new gnu.mapping.WrongType
            java.lang.String r1 = "sindex"
            r6.<init>((java.lang.ClassCastException) r4, (java.lang.String) r1, (int) r0, (java.lang.Object) r5)
            throw r6
        L_0x0058:
            r5 = move-exception
            gnu.mapping.WrongType r6 = new gnu.mapping.WrongType
            java.lang.String r1 = "s"
            r6.<init>((java.lang.ClassCastException) r5, (java.lang.String) r1, (int) r0, (java.lang.Object) r4)
            goto L_0x0062
        L_0x0061:
            throw r6
        L_0x0062:
            goto L_0x0061
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.XStrings.substring(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        return moduleMethod.selector == 1 ? substring(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        return moduleMethod.selector == 1 ? substring(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        if (moduleMethod.selector != 1) {
            return super.match2(moduleMethod, obj, obj2, callContext);
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        callContext.proc = moduleMethod;
        callContext.pc = 2;
        return 0;
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        if (moduleMethod.selector != 1) {
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        callContext.value3 = obj3;
        callContext.proc = moduleMethod;
        callContext.pc = 3;
        return 0;
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static Object stringLength(Object string) {
        if (string == Values.empty) {
            return Values.empty;
        }
        return Integer.valueOf(((String) string).length());
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        return moduleMethod.selector == 3 ? stringLength(obj) : super.apply1(moduleMethod, obj);
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
}
