package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Member;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.CheckedTarget;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure3;
import gnu.mapping.Values;

public class SlotSet extends Procedure3 implements Inlineable {
    public static final SlotSet set$Mnfield$Ex = new SlotSet("set-field!", false);
    public static final SlotSet set$Mnstatic$Mnfield$Ex = new SlotSet("set-static-field!", true);
    public static final SlotSet setFieldReturnObject = new SlotSet("set-field-return-object!", false);
    static final Type[] type1Array = new Type[1];
    boolean isStatic;
    boolean returnSelf;

    static {
        setFieldReturnObject.returnSelf = true;
    }

    public SlotSet(String name, boolean isStatic2) {
        super(name);
        this.isStatic = isStatic2;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileReflect:validateApplySlotSet");
    }

    public static void setField(Object obj, String name, Object value) {
        apply(false, obj, name, value);
    }

    public static void setStaticField(Object obj, String name, Object value) {
        apply(true, obj, name, value);
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0064 A[SYNTHETIC, Splitter:B:28:0x0064] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0079 A[SYNTHETIC, Splitter:B:37:0x0079] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0091 A[Catch:{ Exception -> 0x008f }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00e5  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00fe  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void apply(boolean r16, java.lang.Object r17, java.lang.Object r18, java.lang.Object r19) {
        /*
            r1 = r17
            r2 = r18
            r3 = r19
            gnu.expr.Language r4 = gnu.expr.Language.getDefaultLanguage()
            r5 = 0
            boolean r0 = r2 instanceof java.lang.String
            if (r0 != 0) goto L_0x0024
            boolean r0 = r2 instanceof gnu.lists.FString
            if (r0 != 0) goto L_0x0024
            boolean r0 = r2 instanceof gnu.mapping.Symbol
            if (r0 == 0) goto L_0x0018
            goto L_0x0024
        L_0x0018:
            r0 = r2
            gnu.bytecode.Member r0 = (gnu.bytecode.Member) r0
            java.lang.String r0 = r0.getName()
            r6 = r0
            r7 = 0
            r8 = r7
            r7 = r6
            goto L_0x003a
        L_0x0024:
            java.lang.String r6 = r18.toString()
            java.lang.String r0 = gnu.expr.Compilation.mangleNameIfNeeded(r6)
            if (r16 == 0) goto L_0x0033
            java.lang.Class r7 = gnu.kawa.reflect.SlotGet.coerceToClass(r17)
            goto L_0x0037
        L_0x0033:
            java.lang.Class r7 = r17.getClass()
        L_0x0037:
            r8 = r7
            r7 = r6
            r6 = r0
        L_0x003a:
            boolean r0 = r2 instanceof gnu.bytecode.Field     // Catch:{ NoSuchFieldException -> 0x0059, IllegalAccessException -> 0x0056 }
            if (r0 == 0) goto L_0x0046
            r0 = r2
            gnu.bytecode.Field r0 = (gnu.bytecode.Field) r0     // Catch:{ NoSuchFieldException -> 0x0059, IllegalAccessException -> 0x0056 }
            java.lang.reflect.Field r0 = r0.getReflectField()     // Catch:{ NoSuchFieldException -> 0x0059, IllegalAccessException -> 0x0056 }
            goto L_0x004a
        L_0x0046:
            java.lang.reflect.Field r0 = r8.getField(r6)     // Catch:{ NoSuchFieldException -> 0x0059, IllegalAccessException -> 0x0056 }
        L_0x004a:
            java.lang.Class r9 = r0.getType()     // Catch:{ NoSuchFieldException -> 0x0059, IllegalAccessException -> 0x0056 }
            java.lang.Object r10 = r4.coerceFromObject(r9, r3)     // Catch:{ NoSuchFieldException -> 0x0059, IllegalAccessException -> 0x0056 }
            r0.set(r1, r10)     // Catch:{ NoSuchFieldException -> 0x0059, IllegalAccessException -> 0x0056 }
            return
        L_0x0056:
            r0 = move-exception
            r5 = 1
            goto L_0x005b
        L_0x0059:
            r0 = move-exception
        L_0x005b:
            r9 = 0
            boolean r0 = r2 instanceof gnu.bytecode.Method     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            java.lang.String r10 = "set"
            if (r0 == 0) goto L_0x0064
            r11 = r6
            goto L_0x0068
        L_0x0064:
            java.lang.String r11 = gnu.expr.ClassExp.slotToMethodName(r10, r7)     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
        L_0x0068:
            if (r0 == 0) goto L_0x0073
            boolean r10 = r11.startsWith(r10)     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            if (r10 != 0) goto L_0x0073
            r0 = 0
            r10 = r0
            goto L_0x0074
        L_0x0073:
            r10 = r0
        L_0x0074:
            r12 = 3
            java.lang.String r0 = "get"
            if (r10 == 0) goto L_0x0091
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008f }
            r13.<init>()     // Catch:{ Exception -> 0x008f }
            java.lang.StringBuilder r0 = r13.append(r0)     // Catch:{ Exception -> 0x008f }
            java.lang.String r13 = r11.substring(r12)     // Catch:{ Exception -> 0x008f }
            java.lang.StringBuilder r0 = r0.append(r13)     // Catch:{ Exception -> 0x008f }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x008f }
            goto L_0x0095
        L_0x008f:
            r0 = move-exception
            goto L_0x009d
        L_0x0091:
            java.lang.String r0 = gnu.expr.ClassExp.slotToMethodName(r0, r7)     // Catch:{ Exception -> 0x008f }
        L_0x0095:
            java.lang.Class[] r13 = gnu.kawa.reflect.SlotGet.noClasses     // Catch:{ Exception -> 0x008f }
            java.lang.reflect.Method r12 = r8.getMethod(r0, r13)     // Catch:{ Exception -> 0x008f }
            r9 = r12
            goto L_0x00c2
        L_0x009d:
            java.lang.String r13 = "is"
            if (r10 == 0) goto L_0x00b7
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            r14.<init>()     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            java.lang.StringBuilder r13 = r14.append(r13)     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            java.lang.String r12 = r11.substring(r12)     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            java.lang.StringBuilder r12 = r13.append(r12)     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            java.lang.String r12 = r12.toString()     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            goto L_0x00bb
        L_0x00b7:
            java.lang.String r12 = gnu.expr.ClassExp.slotToMethodName(r13, r7)     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
        L_0x00bb:
            java.lang.Class[] r13 = gnu.kawa.reflect.SlotGet.noClasses     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            java.lang.reflect.Method r13 = r8.getMethod(r12, r13)     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            r9 = r13
        L_0x00c2:
            r0 = 1
            java.lang.Class[] r12 = new java.lang.Class[r0]     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            java.lang.Class r13 = r9.getReturnType()     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            r14 = 0
            r12[r14] = r13     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            java.lang.reflect.Method r13 = r8.getMethod(r11, r12)     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            r15 = r12[r14]     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            java.lang.Object r15 = r4.coerceFromObject(r15, r3)     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            r0[r14] = r15     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            r13.invoke(r1, r0)     // Catch:{ InvocationTargetException -> 0x0125, IllegalAccessException -> 0x00e0, NoSuchMethodException -> 0x00de }
            return
        L_0x00de:
            r0 = move-exception
            goto L_0x00e3
        L_0x00e0:
            r0 = move-exception
            r5 = 1
        L_0x00e3:
            if (r5 == 0) goto L_0x00fe
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "illegal access for field "
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.StringBuilder r9 = r9.append(r7)
            java.lang.String r9 = r9.toString()
            r0.<init>(r9)
            throw r0
        L_0x00fe:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "no such field "
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.StringBuilder r9 = r9.append(r7)
            java.lang.String r10 = " in "
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r10 = r8.getName()
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            r0.<init>(r9)
            throw r0
        L_0x0125:
            r0 = move-exception
            java.lang.Throwable r9 = r0.getTargetException()
            java.lang.RuntimeException r9 = gnu.mapping.WrappedException.wrapIfNeeded(r9)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.reflect.SlotSet.apply(boolean, java.lang.Object, java.lang.Object, java.lang.Object):void");
    }

    public Object apply3(Object obj, Object fname, Object value) {
        apply(this.isStatic, obj, fname, value);
        return this.returnSelf ? obj : Values.empty;
    }

    public static Member lookupMember(ObjectType clas, String name, ClassType caller) {
        Field field = clas.getField(Compilation.mangleNameIfNeeded(name), -1);
        if (field != null) {
            if (caller == null) {
                caller = Type.pointer_type;
            }
            if (caller.isAccessible(field, clas)) {
                return field;
            }
        }
        Method method = clas.getMethod(ClassExp.slotToMethodName("set", name), type1Array);
        if (method == null) {
            return field;
        }
        return method;
    }

    static void compileSet(Procedure thisProc, ObjectType ctype, Expression valArg, Object part, Compilation comp) {
        Procedure procedure = thisProc;
        Expression expression = valArg;
        Object obj = part;
        Compilation compilation = comp;
        CodeAttr code = comp.getCode();
        Language language = comp.getLanguage();
        boolean isStatic2 = (procedure instanceof SlotSet) && ((SlotSet) procedure).isStatic;
        if (obj instanceof Field) {
            Field field = (Field) obj;
            boolean isStaticField = field.getStaticFlag();
            Type ftype = language.getLangTypeFor(field.getType());
            if (isStatic2 && !isStaticField) {
                compilation.error('e', "cannot access non-static field `" + field.getName() + "' using `" + thisProc.getName() + '\'');
            }
            expression.compile(compilation, CheckedTarget.getInstance(ftype));
            if (isStaticField) {
                code.emitPutStatic(field);
            } else {
                code.emitPutField(field);
            }
        } else if (obj instanceof Method) {
            Method method = (Method) obj;
            boolean isStaticMethod = method.getStaticFlag();
            if (isStatic2 && !isStaticMethod) {
                compilation.error('e', "cannot call non-static getter method `" + method.getName() + "' using `" + thisProc.getName() + '\'');
            }
            expression.compile(compilation, CheckedTarget.getInstance(language.getLangTypeFor(method.getParameterTypes()[0])));
            if (isStaticMethod) {
                code.emitInvokeStatic(method);
            } else {
                code.emitInvoke(method);
            }
            if (!method.getReturnType().isVoid()) {
                code.emitPop(1);
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: gnu.bytecode.Member} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void compile(gnu.expr.ApplyExp r21, gnu.expr.Compilation r22, gnu.expr.Target r23) {
        /*
            r20 = this;
            r0 = r20
            r1 = r22
            r2 = r23
            gnu.expr.Expression[] r3 = r21.getArgs()
            int r4 = r3.length
            r5 = 101(0x65, float:1.42E-43)
            r6 = 3
            if (r4 == r6) goto L_0x0040
            if (r4 >= r6) goto L_0x0015
            java.lang.String r6 = "too few"
            goto L_0x0017
        L_0x0015:
            java.lang.String r6 = "too many"
        L_0x0017:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.StringBuilder r7 = r7.append(r6)
            java.lang.String r8 = " arguments to `"
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = r20.getName()
            java.lang.StringBuilder r7 = r7.append(r8)
            r8 = 39
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            r1.error(r5, r7)
            r5 = 0
            r1.compileConstant(r5, r2)
            return
        L_0x0040:
            r6 = 0
            r7 = r3[r6]
            r8 = 1
            r9 = r3[r8]
            r10 = 2
            r11 = r3[r10]
            boolean r12 = r0.isStatic
            if (r12 == 0) goto L_0x0052
            gnu.bytecode.Type r12 = kawa.standard.Scheme.exp2Type(r7)
            goto L_0x0056
        L_0x0052:
            gnu.bytecode.Type r12 = r7.getType()
        L_0x0056:
            r13 = 0
            boolean r14 = r12 instanceof gnu.bytecode.ObjectType
            if (r14 == 0) goto L_0x015a
            boolean r14 = r9 instanceof gnu.expr.QuoteExp
            if (r14 == 0) goto L_0x015a
            r14 = r9
            gnu.expr.QuoteExp r14 = (gnu.expr.QuoteExp) r14
            java.lang.Object r14 = r14.getValue()
            r15 = r12
            gnu.bytecode.ObjectType r15 = (gnu.bytecode.ObjectType) r15
            gnu.bytecode.ClassType r8 = r1.curClass
            if (r8 == 0) goto L_0x0070
            gnu.bytecode.ClassType r8 = r1.curClass
            goto L_0x0072
        L_0x0070:
            gnu.bytecode.ClassType r8 = r1.mainClass
        L_0x0072:
            boolean r10 = r14 instanceof java.lang.String
            java.lang.String r6 = "' in "
            if (r10 != 0) goto L_0x0098
            boolean r10 = r14 instanceof gnu.lists.FString
            if (r10 != 0) goto L_0x0098
            boolean r10 = r14 instanceof gnu.mapping.Symbol
            if (r10 == 0) goto L_0x0081
            goto L_0x0098
        L_0x0081:
            boolean r10 = r14 instanceof gnu.bytecode.Member
            if (r10 == 0) goto L_0x0094
            r10 = r14
            gnu.bytecode.Member r10 = (gnu.bytecode.Member) r10
            java.lang.String r13 = r10.getName()
            r17 = r4
            r19 = r13
            r13 = r10
            r10 = r19
            goto L_0x00d5
        L_0x0094:
            r10 = 0
            r17 = r4
            goto L_0x00d5
        L_0x0098:
            java.lang.String r10 = r14.toString()
            gnu.bytecode.Member r13 = lookupMember(r15, r10, r8)
            if (r13 != 0) goto L_0x00d3
            gnu.bytecode.ClassType r5 = gnu.bytecode.Type.pointer_type
            if (r12 == r5) goto L_0x00d3
            boolean r5 = r22.warnUnknownMember()
            if (r5 == 0) goto L_0x00d3
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r17 = r4
            java.lang.String r4 = "no slot `"
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.StringBuilder r4 = r4.append(r10)
            java.lang.StringBuilder r4 = r4.append(r6)
            java.lang.String r5 = r15.getName()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r5 = 119(0x77, float:1.67E-43)
            r1.error(r5, r4)
            goto L_0x00d5
        L_0x00d3:
            r17 = r4
        L_0x00d5:
            if (r13 == 0) goto L_0x0157
            int r4 = r13.getModifiers()
            r5 = r4 & 8
            if (r5 == 0) goto L_0x00e2
            r16 = 1
            goto L_0x00e4
        L_0x00e2:
            r16 = 0
        L_0x00e4:
            r5 = r16
            if (r8 == 0) goto L_0x0121
            boolean r16 = r8.isAccessible(r13, r15)
            if (r16 != 0) goto L_0x0121
            r16 = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r18 = r7
            java.lang.String r7 = "slot '"
            java.lang.StringBuilder r4 = r4.append(r7)
            java.lang.StringBuilder r4 = r4.append(r10)
            java.lang.StringBuilder r4 = r4.append(r6)
            gnu.bytecode.ClassType r6 = r13.getDeclaringClass()
            java.lang.String r6 = r6.getName()
            java.lang.StringBuilder r4 = r4.append(r6)
            java.lang.String r6 = " not accessible here"
            java.lang.StringBuilder r4 = r4.append(r6)
            java.lang.String r4 = r4.toString()
            r6 = 101(0x65, float:1.42E-43)
            r1.error(r6, r4)
            goto L_0x0125
        L_0x0121:
            r16 = r4
            r18 = r7
        L_0x0125:
            r4 = 0
            r4 = r3[r4]
            if (r5 == 0) goto L_0x012d
            gnu.expr.Target r6 = gnu.expr.Target.Ignore
            goto L_0x0131
        L_0x012d:
            gnu.expr.Target r6 = gnu.expr.Target.pushValue(r15)
        L_0x0131:
            r4.compile((gnu.expr.Compilation) r1, (gnu.expr.Target) r6)
            boolean r4 = r0.returnSelf
            if (r4 == 0) goto L_0x0143
            gnu.bytecode.CodeAttr r4 = r22.getCode()
            gnu.bytecode.Type r6 = r15.getImplementationType()
            r4.emitDup((gnu.bytecode.Type) r6)
        L_0x0143:
            r4 = 2
            r4 = r3[r4]
            compileSet(r0, r15, r4, r13, r1)
            boolean r4 = r0.returnSelf
            if (r4 == 0) goto L_0x0151
            r2.compileFromStack(r1, r15)
            goto L_0x0156
        L_0x0151:
            gnu.mapping.Values r4 = gnu.mapping.Values.empty
            r1.compileConstant(r4, r2)
        L_0x0156:
            return
        L_0x0157:
            r18 = r7
            goto L_0x015e
        L_0x015a:
            r17 = r4
            r18 = r7
        L_0x015e:
            gnu.expr.ApplyExp.compile(r21, r22, r23)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.reflect.SlotSet.compile(gnu.expr.ApplyExp, gnu.expr.Compilation, gnu.expr.Target):void");
    }
}
