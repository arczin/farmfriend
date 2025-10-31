package gnu.expr;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.lists.FString;
import gnu.mapping.Symbol;
import gnu.mapping.Table2D;
import gnu.mapping.Values;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.IdentityHashMap;
import java.util.regex.Pattern;

public class LitTable implements ObjectOutput {
    static Table2D staticTable = new Table2D(100);
    Compilation comp;
    IdentityHashMap literalTable = new IdentityHashMap(100);
    Literal literalsChain;
    int literalsCount;
    ClassType mainClass;
    int stackPointer;
    Type[] typeStack = new Type[20];
    Object[] valueStack = new Object[20];

    public LitTable(Compilation comp2) {
        this.comp = comp2;
        this.mainClass = comp2.mainClass;
    }

    public void emit() throws IOException {
        for (Literal init = this.literalsChain; init != null; init = init.next) {
            writeObject(init.value);
        }
        for (Literal init2 = this.literalsChain; init2 != null; init2 = init2.next) {
            emit(init2, true);
        }
        this.literalTable = null;
        this.literalsCount = 0;
    }

    /* access modifiers changed from: package-private */
    public void push(Object value, Type type) {
        if (this.stackPointer >= this.valueStack.length) {
            Object[] newValues = new Object[(this.valueStack.length * 2)];
            Type[] newTypes = new Type[(this.typeStack.length * 2)];
            System.arraycopy(this.valueStack, 0, newValues, 0, this.stackPointer);
            System.arraycopy(this.typeStack, 0, newTypes, 0, this.stackPointer);
            this.valueStack = newValues;
            this.typeStack = newTypes;
        }
        this.valueStack[this.stackPointer] = value;
        this.typeStack[this.stackPointer] = type;
        this.stackPointer++;
    }

    /* access modifiers changed from: package-private */
    public void error(String msg) {
        throw new Error(msg);
    }

    public void flush() {
    }

    public void close() {
    }

    public void write(int b) throws IOException {
        error("cannot handle call to write(int) when externalizing literal");
    }

    public void writeBytes(String s) throws IOException {
        error("cannot handle call to writeBytes(String) when externalizing literal");
    }

    public void write(byte[] b) throws IOException {
        error("cannot handle call to write(byte[]) when externalizing literal");
    }

    public void write(byte[] b, int off, int len) throws IOException {
        error("cannot handle call to write(byte[],int,int) when externalizing literal");
    }

    public void writeBoolean(boolean v) {
        push(new Boolean(v), Type.booleanType);
    }

    public void writeChar(int v) {
        push(new Character((char) v), Type.charType);
    }

    public void writeByte(int v) {
        push(new Byte((byte) v), Type.byteType);
    }

    public void writeShort(int v) {
        push(new Short((short) v), Type.shortType);
    }

    public void writeInt(int v) {
        push(new Integer(v), Type.intType);
    }

    public void writeLong(long v) {
        push(new Long(v), Type.longType);
    }

    public void writeFloat(float v) {
        push(new Float(v), Type.floatType);
    }

    public void writeDouble(double v) {
        push(new Double(v), Type.doubleType);
    }

    public void writeUTF(String v) {
        push(v, Type.string_type);
    }

    public void writeChars(String v) {
        push(v, Type.string_type);
    }

    public void writeObject(Object obj) throws IOException {
        Literal lit = findLiteral(obj);
        if ((lit.flags & 3) != 0) {
            if (lit.field == null && obj != null && !(obj instanceof String)) {
                lit.assign(this);
            }
            if ((lit.flags & 2) == 0) {
                lit.flags |= 4;
            }
        } else {
            lit.flags |= 1;
            int oldStack = this.stackPointer;
            if ((obj instanceof FString) && ((FString) obj).size() < 65535) {
                push(obj.toString(), Type.string_type);
            } else if (obj instanceof Externalizable) {
                ((Externalizable) obj).writeExternal(this);
            } else if (obj instanceof Object[]) {
                Object[] arr = (Object[]) obj;
                for (Object writeObject : arr) {
                    writeObject(writeObject);
                }
            } else if (obj != null && !(obj instanceof String) && !(lit.type instanceof ArrayType)) {
                if (obj instanceof BigInteger) {
                    writeChars(obj.toString());
                } else if (obj instanceof BigDecimal) {
                    BigDecimal dec = (BigDecimal) obj;
                    writeObject(dec.unscaledValue());
                    writeInt(dec.scale());
                } else if (obj instanceof Integer) {
                    push(obj, Type.intType);
                } else if (obj instanceof Short) {
                    push(obj, Type.shortType);
                } else if (obj instanceof Byte) {
                    push(obj, Type.byteType);
                } else if (obj instanceof Long) {
                    push(obj, Type.longType);
                } else if (obj instanceof Double) {
                    push(obj, Type.doubleType);
                } else if (obj instanceof Float) {
                    push(obj, Type.floatType);
                } else if (obj instanceof Character) {
                    push(obj, Type.charType);
                } else if (obj instanceof Class) {
                    push(obj, Type.java_lang_Class_type);
                } else if (obj instanceof Pattern) {
                    Pattern pat = (Pattern) obj;
                    push(pat.pattern(), Type.string_type);
                    push(Integer.valueOf(pat.flags()), Type.intType);
                } else {
                    error(obj.getClass().getName() + " does not implement Externalizable");
                }
            }
            int nargs = this.stackPointer - oldStack;
            if (nargs == 0) {
                lit.argValues = Values.noArgs;
                lit.argTypes = Type.typeArray0;
            } else {
                lit.argValues = new Object[nargs];
                lit.argTypes = new Type[nargs];
                System.arraycopy(this.valueStack, oldStack, lit.argValues, 0, nargs);
                System.arraycopy(this.typeStack, oldStack, lit.argTypes, 0, nargs);
                this.stackPointer = oldStack;
            }
            lit.flags |= 2;
        }
        push(lit, lit.type);
    }

    public Literal findLiteral(Object value) {
        Literal literal;
        if (value == null) {
            return Literal.nullLiteral;
        }
        Literal literal2 = (Literal) this.literalTable.get(value);
        if (literal2 != null) {
            return literal2;
        }
        if (this.comp.immediate) {
            return new Literal(value, this);
        }
        Class valueClass = value.getClass();
        Type valueType = Type.make(valueClass);
        synchronized (staticTable) {
            literal = (Literal) staticTable.get(value, (Object) null, (Object) null);
            if ((literal == null || literal.value != value) && (valueType instanceof ClassType)) {
                Class fldClass = valueClass;
                ClassType fldType = (ClassType) valueType;
                while (true) {
                    if (staticTable.get(fldClass, Boolean.TRUE, (Object) null) != null) {
                        break;
                    }
                    staticTable.put(fldClass, Boolean.TRUE, fldClass);
                    for (Field fld = fldType.getFields(); fld != null; fld = fld.getNext()) {
                        if ((fld.getModifiers() & 25) == 25) {
                            try {
                                Object litValue = fld.getReflectField().get((Object) null);
                                if (litValue != null) {
                                    if (fldClass.isInstance(litValue)) {
                                        Literal lit = new Literal(litValue, fld, this);
                                        staticTable.put(litValue, (Object) null, lit);
                                        if (value == litValue) {
                                            literal = lit;
                                        }
                                    }
                                }
                            } catch (Throwable ex) {
                                error("caught " + ex + " getting static field " + fld);
                            }
                        }
                    }
                    fldClass = fldClass.getSuperclass();
                    if (fldClass == null) {
                        break;
                    }
                    fldType = (ClassType) Type.make(fldClass);
                }
            }
        }
        if (literal == null) {
            return new Literal(value, valueType, this);
        }
        this.literalTable.put(value, literal);
        return literal;
    }

    /* access modifiers changed from: package-private */
    public Method getMethod(ClassType type, String name, Literal literal, boolean isStatic) {
        long bestArrayArgs;
        Method method;
        int argLength;
        Type[] argTypes;
        boolean mstatic;
        Type[] argTypes2;
        Type[] mParameters;
        int i;
        boolean mstatic2;
        boolean not2;
        Literal literal2 = literal;
        Type[] argTypes3 = literal2.argTypes;
        Method method2 = type.getDeclaredMethods();
        int argLength2 = argTypes3.length;
        Method best = null;
        long bestArrayArgs2 = 0;
        boolean ambiguous = false;
        ArrayType[] bParameters = null;
        while (method2 != null) {
            if (!name.equals(method2.getName())) {
                argTypes = argTypes3;
            } else {
                boolean mstatic3 = method2.getStaticFlag();
                if (isStatic != mstatic3) {
                    argTypes = argTypes3;
                } else {
                    long arrayArgs = 0;
                    Type[] mParameters2 = method2.getParameterTypes();
                    int iarg = 0;
                    int iparam = 0;
                    while (true) {
                        if (iarg != argLength2 || iparam != mParameters2.length) {
                            if (iarg == argLength2) {
                                argTypes = argTypes3;
                                Type[] typeArr = mParameters2;
                                boolean z = mstatic3;
                                break;
                            } else if (iparam == mParameters2.length) {
                                argTypes = argTypes3;
                                break;
                            } else {
                                Type aType = argTypes3[iarg];
                                Type pType = mParameters2[iparam];
                                if (!aType.isSubtype(pType)) {
                                    mParameters = mParameters2;
                                    if ((pType instanceof ArrayType) && iparam < 64) {
                                        if (aType != Type.intType && aType != Type.shortType) {
                                            argTypes = argTypes3;
                                            break;
                                        }
                                        int count = ((Number) literal2.argValues[iarg]).intValue();
                                        if (count < 0) {
                                            Type type2 = aType;
                                            if (type.getName().equals("gnu.math.IntNum")) {
                                                count -= Integer.MIN_VALUE;
                                            }
                                        }
                                        Type elementType = ((ArrayType) pType).getComponentType();
                                        if (count < 0) {
                                            argTypes = argTypes3;
                                            Type type3 = pType;
                                            boolean z2 = mstatic3;
                                            break;
                                        }
                                        Type type4 = pType;
                                        if (iarg + count < argLength2) {
                                            int j = count;
                                            while (true) {
                                                int j2 = j - 1;
                                                if (j2 < 0) {
                                                    argTypes2 = argTypes3;
                                                    int i2 = j2;
                                                    mstatic = mstatic3;
                                                    iarg += count;
                                                    i = 1;
                                                    arrayArgs |= (long) (1 << iparam);
                                                    break;
                                                }
                                                int j3 = j2;
                                                Type t = argTypes3[iarg + j2 + 1];
                                                argTypes = argTypes3;
                                                if (elementType instanceof PrimType) {
                                                    mstatic2 = mstatic3;
                                                    if (elementType.getSignature() != t.getSignature()) {
                                                        break;
                                                    }
                                                } else {
                                                    mstatic2 = mstatic3;
                                                    if (!t.isSubtype(elementType)) {
                                                        break;
                                                    }
                                                }
                                                argTypes3 = argTypes;
                                                j = j3;
                                                mstatic3 = mstatic2;
                                            }
                                        } else {
                                            argTypes = argTypes3;
                                            break;
                                        }
                                    } else {
                                        argTypes = argTypes3;
                                        Type type5 = aType;
                                        Type type6 = pType;
                                        boolean z3 = mstatic3;
                                    }
                                } else {
                                    argTypes2 = argTypes3;
                                    mParameters = mParameters2;
                                    mstatic = mstatic3;
                                    i = 1;
                                }
                                iarg += i;
                                iparam++;
                                String str = name;
                                boolean z4 = isStatic;
                                mParameters2 = mParameters;
                                argTypes3 = argTypes2;
                                mstatic3 = mstatic;
                            }
                        } else if (best == null || (bestArrayArgs2 != 0 && arrayArgs == 0)) {
                            best = method2;
                            bParameters = mParameters2;
                            bestArrayArgs2 = arrayArgs;
                            argTypes = argTypes3;
                        } else if (arrayArgs == 0) {
                            boolean not1 = false;
                            boolean c = false;
                            int j4 = argLength2;
                            while (true) {
                                j4--;
                                if (j4 < 0) {
                                    boolean z5 = not1;
                                    boolean z6 = c;
                                    break;
                                }
                                boolean not22 = c;
                                int c2 = bParameters[j4].compare(mParameters2[j4]);
                                if (c2 != 1) {
                                    not2 = true;
                                    if (not1) {
                                        c = true;
                                        break;
                                    }
                                } else {
                                    not2 = not22;
                                }
                                boolean not12 = not1;
                                if (c2 != -1) {
                                    not1 = true;
                                    if (not2) {
                                        c = not2;
                                        break;
                                    }
                                } else {
                                    not1 = not12;
                                }
                                c = not2;
                                boolean not23 = isStatic;
                            }
                            if (not1) {
                                best = method2;
                                bParameters = mParameters2;
                            }
                            ambiguous = not1 && c;
                            argTypes = argTypes3;
                        } else {
                            argTypes = argTypes3;
                        }
                    }
                }
            }
            method2 = method2.getNext();
            argTypes3 = argTypes;
        }
        if (ambiguous) {
            return null;
        }
        if (bestArrayArgs2 != 0) {
            Object[] args = new Object[bParameters.length];
            Type[] types = new Type[bParameters.length];
            int iarg2 = 0;
            int iparam2 = 0;
            while (iarg2 != argLength2) {
                ArrayType arrayType = bParameters[iparam2];
                if ((((long) (1 << iparam2)) & bestArrayArgs2) == 0) {
                    args[iparam2] = literal2.argValues[iarg2];
                    types[iparam2] = literal2.argTypes[iarg2];
                    method = method2;
                    argLength = argLength2;
                    bestArrayArgs = bestArrayArgs2;
                } else {
                    int count2 = ((Number) literal2.argValues[iarg2]).intValue();
                    boolean isIntNum = type.getName().equals("gnu.math.IntNum");
                    if (isIntNum) {
                        count2 -= Integer.MIN_VALUE;
                    }
                    method = method2;
                    Type elementType2 = arrayType.getComponentType();
                    types[iparam2] = arrayType;
                    argLength = argLength2;
                    args[iparam2] = Array.newInstance(elementType2.getReflectClass(), count2);
                    Object[] argValues = literal2.argValues;
                    if (isIntNum) {
                        int[] arr = (int[]) args[iparam2];
                        for (int j5 = count2; j5 > 0; j5--) {
                            arr[count2 - j5] = ((Integer) argValues[iarg2 + j5]).intValue();
                        }
                        bestArrayArgs = bestArrayArgs2;
                    } else {
                        int j6 = count2;
                        while (true) {
                            int j7 = j6 - 1;
                            if (j7 < 0) {
                                break;
                            }
                            Array.set(args[iparam2], j7, argValues[iarg2 + 1 + j7]);
                            bestArrayArgs2 = bestArrayArgs2;
                            j6 = j7;
                            Literal literal3 = literal;
                        }
                        bestArrayArgs = bestArrayArgs2;
                    }
                    Literal arrayLiteral = new Literal(args[iparam2], (Type) arrayType);
                    if (elementType2 instanceof ObjectType) {
                        arrayLiteral.argValues = (Object[]) args[iparam2];
                    }
                    args[iparam2] = arrayLiteral;
                    iarg2 += count2;
                }
                iarg2++;
                iparam2++;
                literal2 = literal;
                argLength2 = argLength;
                method2 = method;
                bestArrayArgs2 = bestArrayArgs;
            }
            literal2.argValues = args;
            literal2.argTypes = types;
            Method method3 = method2;
            int i3 = argLength2;
            long j8 = bestArrayArgs2;
        } else {
            int i4 = argLength2;
            long j9 = bestArrayArgs2;
        }
        return best;
    }

    /* access modifiers changed from: package-private */
    public void putArgs(Literal literal, CodeAttr code) {
        Type[] argTypes = literal.argTypes;
        int len = argTypes.length;
        for (int i = 0; i < len; i++) {
            Object value = literal.argValues[i];
            if (value instanceof Literal) {
                emit((Literal) value, false);
            } else {
                this.comp.compileConstant(value, new StackTarget(argTypes[i]));
            }
        }
    }

    private void store(Literal literal, boolean ignore, CodeAttr code) {
        if (literal.field != null) {
            if (!ignore) {
                code.emitDup(literal.type);
            }
            code.emitPutStatic(literal.field);
        }
        literal.flags |= 8;
    }

    /* access modifiers changed from: package-private */
    public void emit(Literal literal, boolean ignore) {
        CodeAttr code = this.comp.getCode();
        if (literal.value == null) {
            if (!ignore) {
                code.emitPushNull();
            }
        } else if (literal.value instanceof String) {
            if (!ignore) {
                code.emitPushString(literal.value.toString());
            }
        } else if ((literal.flags & 8) == 0) {
            boolean z = false;
            if (literal.value instanceof Object[]) {
                int len = literal.argValues.length;
                Type elementType = ((ArrayType) literal.type).getComponentType();
                code.emitPushInt(len);
                code.emitNewArray(elementType);
                store(literal, ignore, code);
                for (int i = 0; i < len; i++) {
                    Literal el = (Literal) literal.argValues[i];
                    if (el.value != null) {
                        code.emitDup(elementType);
                        code.emitPushInt(i);
                        emit(el, false);
                        code.emitArrayStore(elementType);
                    }
                }
            } else if (literal.type instanceof ArrayType) {
                code.emitPushPrimArray(literal.value, (ArrayType) literal.type);
                store(literal, ignore, code);
            } else if (literal.value instanceof Class) {
                Class clas = (Class) literal.value;
                if (clas.isPrimitive()) {
                    String cname = clas.getName();
                    if (cname.equals("int")) {
                        cname = PropertyTypeConstants.PROPERTY_TYPE_INTEGER;
                    }
                    code.emitGetStatic(ClassType.make("java.lang." + Character.toUpperCase(cname.charAt(0)) + cname.substring(1)).getDeclaredField("TYPE"));
                } else {
                    this.comp.loadClassRef((ObjectType) Type.make(clas));
                }
                store(literal, ignore, code);
            } else if (!(literal.value instanceof ClassType) || ((ClassType) literal.value).isExisting()) {
                ClassType type = (ClassType) literal.type;
                boolean useDefaultInit = (literal.flags & 4) != 0;
                Method method = null;
                boolean makeStatic = false;
                if (!useDefaultInit) {
                    if (!(literal.value instanceof Symbol)) {
                        method = getMethod(type, "valueOf", literal, true);
                    }
                    if (method == null && !(literal.value instanceof Values)) {
                        String mname = "make";
                        if (literal.value instanceof Pattern) {
                            mname = "compile";
                        }
                        method = getMethod(type, mname, literal, true);
                    }
                    if (method != null) {
                        makeStatic = true;
                    } else if (literal.argTypes.length > 0) {
                        method = getMethod(type, "<init>", literal, false);
                    }
                    if (method == null) {
                        useDefaultInit = true;
                    }
                }
                if (useDefaultInit) {
                    method = getMethod(type, "set", literal, false);
                }
                if (method == null && literal.argTypes.length > 0) {
                    error("no method to construct " + literal.type);
                }
                if (makeStatic) {
                    putArgs(literal, code);
                    code.emitInvokeStatic(method);
                } else if (useDefaultInit) {
                    code.emitNew(type);
                    code.emitDup((Type) type);
                    code.emitInvokeSpecial(type.getDeclaredMethod("<init>", 0));
                } else {
                    code.emitNew(type);
                    code.emitDup((Type) type);
                    putArgs(literal, code);
                    code.emitInvokeSpecial(method);
                }
                Method resolveMethod = (makeStatic || (literal.value instanceof Values)) ? null : type.getDeclaredMethod("readResolve", 0);
                if (resolveMethod != null) {
                    code.emitInvokeVirtual(resolveMethod);
                    type.emitCoerceFromObject(code);
                }
                if (ignore && (!useDefaultInit || method == null)) {
                    z = true;
                }
                store(literal, z, code);
                if (useDefaultInit && method != null) {
                    if (!ignore) {
                        code.emitDup((Type) type);
                    }
                    putArgs(literal, code);
                    code.emitInvokeVirtual(method);
                }
            } else {
                this.comp.loadClassRef((ClassType) literal.value);
                Method meth = Compilation.typeType.getDeclaredMethod("valueOf", 1);
                if (meth == null) {
                    meth = Compilation.typeType.getDeclaredMethod("make", 1);
                }
                code.emitInvokeStatic(meth);
                code.emitCheckcast(Compilation.typeClassType);
                store(literal, ignore, code);
            }
        } else if (!ignore) {
            code.emitGetStatic(literal.field);
        }
    }
}
