package gnu.kawa.reflect;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Member;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.mapping.HasSetter;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongArguments;
import gnu.mapping.WrongType;

public class SlotGet extends Procedure2 implements HasSetter, Inlineable {
    public static final SlotGet field = new SlotGet("field", false, SlotSet.set$Mnfield$Ex);
    static Class[] noClasses = new Class[0];
    public static final SlotGet slotRef = new SlotGet("slot-ref", false, SlotSet.set$Mnfield$Ex);
    public static final SlotGet staticField = new SlotGet("static-field", true, SlotSet.set$Mnstatic$Mnfield$Ex);
    boolean isStatic;
    Procedure setter;

    public SlotGet(String name, boolean isStatic2) {
        super(name);
        this.isStatic = isStatic2;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileReflect:validateApplySlotGet");
    }

    public SlotGet(String name, boolean isStatic2, Procedure setter2) {
        this(name, isStatic2);
        this.setter = setter2;
    }

    public static Object field(Object obj, String fname) {
        return field.apply2(obj, fname);
    }

    public static Object staticField(Object obj, String fname) {
        return staticField.apply2(obj, fname);
    }

    public Object apply2(Object arg1, Object arg2) {
        String name;
        String fname;
        String getName = null;
        String isName = null;
        if (arg2 instanceof Field) {
            fname = ((Field) arg2).getName();
            name = Compilation.demangleName(fname, true);
        } else if (arg2 instanceof Method) {
            String mname = ((Method) arg2).getName();
            name = Compilation.demangleName(mname, false);
            if (mname.startsWith("get")) {
                getName = mname;
            } else if (mname.startsWith("is")) {
                isName = mname;
            }
            fname = null;
        } else if ((arg2 instanceof SimpleSymbol) || (arg2 instanceof CharSequence)) {
            name = arg2.toString();
            fname = Compilation.mangleNameIfNeeded(name);
        } else {
            throw new WrongType((Procedure) this, 2, arg2, PropertyTypeConstants.PROPERTY_TYPE_STRING);
        }
        if ("class".equals(fname)) {
            fname = "class";
        } else if (PropertyTypeConstants.PROPERTY_TYPE_LENGTH.equals(fname)) {
            fname = PropertyTypeConstants.PROPERTY_TYPE_LENGTH;
        }
        return getSlotValue(this.isStatic, arg1, name, fname, getName, isName, Language.getDefaultLanguage());
    }

    /* JADX WARNING: Removed duplicated region for block: B:56:0x00da  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00f3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object getSlotValue(boolean r5, java.lang.Object r6, java.lang.String r7, java.lang.String r8, java.lang.String r9, java.lang.String r10, gnu.expr.Language r11) {
        /*
            if (r5 == 0) goto L_0x0007
            java.lang.Class r0 = coerceToClass(r6)
            goto L_0x000b
        L_0x0007:
            java.lang.Class r0 = r6.getClass()
        L_0x000b:
            java.lang.String r1 = "length"
            if (r8 != r1) goto L_0x001e
            boolean r1 = r0.isArray()
            if (r1 == 0) goto L_0x001e
            int r5 = java.lang.reflect.Array.getLength(r6)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            return r5
        L_0x001e:
            java.lang.String r1 = "class"
            if (r8 != r1) goto L_0x0023
            return r0
        L_0x0023:
            r1 = 39
            r2 = 1
            if (r8 == 0) goto L_0x0070
            java.lang.reflect.Field r3 = r0.getField(r8)     // Catch:{ Exception -> 0x002e }
            goto L_0x0030
        L_0x002e:
            r3 = move-exception
            r3 = 0
        L_0x0030:
            if (r3 == 0) goto L_0x0070
            if (r5 == 0) goto L_0x005a
            int r4 = r3.getModifiers()
            r4 = r4 & 8
            if (r4 == 0) goto L_0x003d
            goto L_0x005a
        L_0x003d:
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "cannot access non-static field '"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L_0x005a:
            java.lang.Class r4 = r3.getType()     // Catch:{ IllegalAccessException -> 0x006c, Exception -> 0x0067 }
            java.lang.Object r3 = r3.get(r6)     // Catch:{ IllegalAccessException -> 0x006c, Exception -> 0x0067 }
            java.lang.Object r5 = r11.coerceToObject(r4, r3)     // Catch:{ IllegalAccessException -> 0x006c, Exception -> 0x0067 }
            return r5
        L_0x0067:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0070
        L_0x006c:
            r3 = move-exception
            r3 = 1
            goto L_0x0071
        L_0x0070:
            r3 = 0
        L_0x0071:
            if (r9 == 0) goto L_0x0076
        L_0x0075:
            goto L_0x007d
        L_0x0076:
            java.lang.String r9 = "get"
            java.lang.String r9 = gnu.expr.ClassExp.slotToMethodName(r9, r7)     // Catch:{ Exception -> 0x008b }
            goto L_0x0075
        L_0x007d:
            java.lang.Class[] r4 = noClasses     // Catch:{ Exception -> 0x008b }
            java.lang.reflect.Method r7 = r0.getMethod(r9, r4)     // Catch:{ Exception -> 0x008b }
            goto L_0x009d
        L_0x0084:
            r5 = move-exception
            goto L_0x00d4
        L_0x0086:
            r5 = move-exception
            goto L_0x00d6
        L_0x0088:
            r5 = move-exception
            goto L_0x011a
        L_0x008b:
            r9 = move-exception
            if (r10 == 0) goto L_0x008f
        L_0x008e:
            goto L_0x0096
        L_0x008f:
            java.lang.String r9 = "is"
            java.lang.String r10 = gnu.expr.ClassExp.slotToMethodName(r9, r7)     // Catch:{ InvocationTargetException -> 0x0088, IllegalAccessException -> 0x0086, NoSuchMethodException -> 0x0084 }
            goto L_0x008e
        L_0x0096:
            java.lang.Class[] r7 = noClasses     // Catch:{ InvocationTargetException -> 0x0088, IllegalAccessException -> 0x0086, NoSuchMethodException -> 0x0084 }
            java.lang.reflect.Method r7 = r0.getMethod(r10, r7)     // Catch:{ InvocationTargetException -> 0x0088, IllegalAccessException -> 0x0086, NoSuchMethodException -> 0x0084 }
            r9 = r10
        L_0x009d:
            if (r5 == 0) goto L_0x00c5
            int r5 = r7.getModifiers()     // Catch:{ InvocationTargetException -> 0x0088, IllegalAccessException -> 0x0086, NoSuchMethodException -> 0x0084 }
            r5 = r5 & 8
            if (r5 == 0) goto L_0x00a8
            goto L_0x00c5
        L_0x00a8:
            java.lang.RuntimeException r5 = new java.lang.RuntimeException     // Catch:{ InvocationTargetException -> 0x0088, IllegalAccessException -> 0x0086, NoSuchMethodException -> 0x0084 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ InvocationTargetException -> 0x0088, IllegalAccessException -> 0x0086, NoSuchMethodException -> 0x0084 }
            r6.<init>()     // Catch:{ InvocationTargetException -> 0x0088, IllegalAccessException -> 0x0086, NoSuchMethodException -> 0x0084 }
            java.lang.String r7 = "cannot call non-static getter method '"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ InvocationTargetException -> 0x0088, IllegalAccessException -> 0x0086, NoSuchMethodException -> 0x0084 }
            java.lang.StringBuilder r6 = r6.append(r9)     // Catch:{ InvocationTargetException -> 0x0088, IllegalAccessException -> 0x0086, NoSuchMethodException -> 0x0084 }
            java.lang.StringBuilder r6 = r6.append(r1)     // Catch:{ InvocationTargetException -> 0x0088, IllegalAccessException -> 0x0086, NoSuchMethodException -> 0x0084 }
            java.lang.String r6 = r6.toString()     // Catch:{ InvocationTargetException -> 0x0088, IllegalAccessException -> 0x0086, NoSuchMethodException -> 0x0084 }
            r5.<init>(r6)     // Catch:{ InvocationTargetException -> 0x0088, IllegalAccessException -> 0x0086, NoSuchMethodException -> 0x0084 }
            throw r5     // Catch:{ InvocationTargetException -> 0x0088, IllegalAccessException -> 0x0086, NoSuchMethodException -> 0x0084 }
        L_0x00c5:
            java.lang.Object[] r5 = gnu.mapping.Values.noArgs     // Catch:{ InvocationTargetException -> 0x0088, IllegalAccessException -> 0x0086, NoSuchMethodException -> 0x0084 }
            java.lang.Object r5 = r7.invoke(r6, r5)     // Catch:{ InvocationTargetException -> 0x0088, IllegalAccessException -> 0x0086, NoSuchMethodException -> 0x0084 }
            java.lang.Class r6 = r7.getReturnType()     // Catch:{ InvocationTargetException -> 0x0088, IllegalAccessException -> 0x0086, NoSuchMethodException -> 0x0084 }
            java.lang.Object r5 = r11.coerceToObject(r6, r5)     // Catch:{ InvocationTargetException -> 0x0088, IllegalAccessException -> 0x0086, NoSuchMethodException -> 0x0084 }
            return r5
        L_0x00d4:
            r2 = r3
            goto L_0x00d8
        L_0x00d6:
        L_0x00d8:
            if (r2 == 0) goto L_0x00f3
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "illegal access for field "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L_0x00f3:
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "no such field "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.String r7 = " in "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = r0.getName()
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L_0x011a:
            java.lang.Throwable r5 = r5.getTargetException()
            java.lang.RuntimeException r5 = gnu.mapping.WrappedException.wrapIfNeeded(r5)
            goto L_0x0124
        L_0x0123:
            throw r5
        L_0x0124:
            goto L_0x0123
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.reflect.SlotGet.getSlotValue(boolean, java.lang.Object, java.lang.String, java.lang.String, java.lang.String, java.lang.String, gnu.expr.Language):java.lang.Object");
    }

    static Class coerceToClass(Object obj) {
        if (obj instanceof Class) {
            return (Class) obj;
        }
        if (obj instanceof Type) {
            return ((Type) obj).getReflectClass();
        }
        throw new RuntimeException("argument is neither Class nor Type");
    }

    public void setN(Object[] args) {
        int nargs = args.length;
        if (nargs == 3) {
            set2(args[0], args[1], args[2]);
            return;
        }
        throw new WrongArguments(getSetter(), nargs);
    }

    public void set2(Object obj, Object name, Object value) {
        SlotSet.apply(this.isStatic, obj, (String) name, value);
    }

    public static Member lookupMember(ObjectType clas, String name, ClassType caller) {
        Field field2 = clas.getField(Compilation.mangleNameIfNeeded(name), -1);
        if (field2 != null) {
            if (caller == null) {
                caller = Type.pointer_type;
            }
            if (caller.isAccessible(field2, clas)) {
                return field2;
            }
        }
        Method method = clas.getMethod(ClassExp.slotToMethodName("get", name), Type.typeArray0);
        if (method == null) {
            return field2;
        }
        return method;
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        Compilation compilation = comp;
        Target target2 = target;
        Expression[] args = exp.getArgs();
        Expression arg0 = args[0];
        boolean isStaticField = true;
        Expression arg1 = args[1];
        Language language = comp.getLanguage();
        Type type = this.isStatic ? language.getTypeFor(arg0) : arg0.getType();
        CodeAttr code = comp.getCode();
        if ((type instanceof ObjectType) && (arg1 instanceof QuoteExp)) {
            ObjectType ctype = (ObjectType) type;
            Object part = ((QuoteExp) arg1).getValue();
            if (part instanceof Field) {
                Field field2 = (Field) part;
                if ((field2.getModifiers() & 8) == 0) {
                    isStaticField = false;
                }
                args[0].compile(compilation, isStaticField ? Target.Ignore : Target.pushValue(ctype));
                if (!isStaticField) {
                    code.emitGetField(field2);
                } else if (0 == 0) {
                    code.emitGetStatic(field2);
                }
                target2.compileFromStack(compilation, language.getLangTypeFor(field2.getType()));
                return;
            } else if (part instanceof Method) {
                Method method = (Method) part;
                int modifiers = method.getModifiers();
                boolean isStaticMethod = method.getStaticFlag();
                args[0].compile(compilation, isStaticMethod ? Target.Ignore : Target.pushValue(ctype));
                if (isStaticMethod) {
                    code.emitInvokeStatic(method);
                } else {
                    code.emitInvoke(method);
                }
                target2.compileFromStack(compilation, method.getReturnType());
                return;
            }
        }
        String name = ClassMethods.checkName(arg1);
        if (!(type instanceof ArrayType) || !PropertyTypeConstants.PROPERTY_TYPE_LENGTH.equals(name) || this.isStatic) {
            ApplyExp.compile(exp, comp, target);
            return;
        }
        args[0].compile(compilation, Target.pushValue(type));
        code.emitArrayLength();
        target2.compileFromStack(compilation, LangPrimType.intType);
    }

    public Type getReturnType(Expression[] args) {
        if (args.length == 2) {
            Expression arg0 = args[0];
            QuoteExp quoteExp = args[1];
            if (quoteExp instanceof QuoteExp) {
                Object part = quoteExp.getValue();
                if (part instanceof Field) {
                    return ((Field) part).getType();
                }
                if (part instanceof Method) {
                    return ((Method) part).getReturnType();
                }
                if (!this.isStatic && (arg0.getType() instanceof ArrayType) && PropertyTypeConstants.PROPERTY_TYPE_LENGTH.equals(ClassMethods.checkName(quoteExp, true))) {
                    return LangPrimType.intType;
                }
            }
        }
        return Type.pointer_type;
    }

    public Procedure getSetter() {
        return this.setter == null ? super.getSetter() : this.setter;
    }

    public static ApplyExp makeGetField(Expression value, String fieldName) {
        return new ApplyExp((Procedure) field, value, new QuoteExp(fieldName));
    }
}
