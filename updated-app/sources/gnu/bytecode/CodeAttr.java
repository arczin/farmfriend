package gnu.bytecode;

import androidx.core.internal.view.SupportMenu;
import com.google.appinventor.components.runtime.util.Ev3Constants;
import com.google.appinventor.components.runtime.util.FullScreenVideoUtil;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CodeAttr extends Attribute implements AttrContainer {
    public static final int DONT_USE_JSR = 2;
    static final int FIXUP_CASE = 3;
    static final int FIXUP_DEFINE = 1;
    static final int FIXUP_DELETE3 = 8;
    static final int FIXUP_GOTO = 4;
    static final int FIXUP_JSR = 5;
    static final int FIXUP_LINE_NUMBER = 15;
    static final int FIXUP_LINE_PC = 14;
    static final int FIXUP_MOVE = 9;
    static final int FIXUP_MOVE_TO_END = 10;
    static final int FIXUP_NONE = 0;
    static final int FIXUP_SWITCH = 2;
    static final int FIXUP_TRANSFER = 6;
    static final int FIXUP_TRANSFER2 = 7;
    static final int FIXUP_TRY = 11;
    static final int FIXUP_TRY_END = 12;
    static final int FIXUP_TRY_HANDLER = 13;
    public static final int GENERATE_STACK_MAP_TABLE = 1;
    public static boolean instructionLineMode = false;
    int PC;
    int SP;
    Attribute attributes;
    byte[] code;
    ExitableBlock currentExitableBlock;
    short[] exception_table;
    int exception_table_length;
    int exitableBlockLevel;
    int fixup_count;
    Label[] fixup_labels;
    int[] fixup_offsets;
    int flags;
    IfState if_stack;
    LineNumbersAttr lines;
    Type[] local_types;
    public LocalVarsAttr locals;
    private int max_locals;
    private int max_stack;
    Label previousLabel;
    SourceDebugExtAttr sourceDbgExt;
    public StackMapTableAttr stackMap;
    public Type[] stack_types;
    TryState try_stack;
    private boolean unreachable_here;
    boolean[] varsSetInCurrentBlock;

    public final Attribute getAttributes() {
        return this.attributes;
    }

    public final void setAttributes(Attribute attributes2) {
        this.attributes = attributes2;
    }

    /* access modifiers changed from: package-private */
    public boolean useJsr() {
        return (this.flags & 2) == 0;
    }

    public final void fixupChain(Label here, Label target) {
        fixupAdd(9, 0, target);
        here.defineRaw(this);
    }

    public final void fixupAdd(int kind, Label label) {
        fixupAdd(kind, this.PC, label);
    }

    /* access modifiers changed from: package-private */
    public final void fixupAdd(int kind, int offset, Label label) {
        if (!(label == null || kind == 1 || kind == 0 || kind == 2 || kind == 11)) {
            label.needsStackMapEntry = true;
        }
        int count = this.fixup_count;
        if (count == 0) {
            this.fixup_offsets = new int[30];
            this.fixup_labels = new Label[30];
        } else if (this.fixup_count == this.fixup_offsets.length) {
            int new_length = count * 2;
            Label[] new_labels = new Label[new_length];
            System.arraycopy(this.fixup_labels, 0, new_labels, 0, count);
            this.fixup_labels = new_labels;
            int[] new_offsets = new int[new_length];
            System.arraycopy(this.fixup_offsets, 0, new_offsets, 0, count);
            this.fixup_offsets = new_offsets;
        }
        this.fixup_offsets[count] = (offset << 4) | kind;
        this.fixup_labels[count] = label;
        this.fixup_count = count + 1;
    }

    private final int fixupOffset(int index) {
        return this.fixup_offsets[index] >> 4;
    }

    private final int fixupKind(int index) {
        return this.fixup_offsets[index] & 15;
    }

    public final Method getMethod() {
        return (Method) getContainer();
    }

    public final int getPC() {
        return this.PC;
    }

    public final int getSP() {
        return this.SP;
    }

    public final ConstantPool getConstants() {
        return getMethod().classfile.constants;
    }

    public final boolean reachableHere() {
        return !this.unreachable_here;
    }

    public final void setReachable(boolean val) {
        this.unreachable_here = !val;
    }

    public final void setUnreachable() {
        this.unreachable_here = true;
    }

    public int getMaxStack() {
        return this.max_stack;
    }

    public int getMaxLocals() {
        return this.max_locals;
    }

    public void setMaxStack(int n) {
        this.max_stack = n;
    }

    public void setMaxLocals(int n) {
        this.max_locals = n;
    }

    public byte[] getCode() {
        return this.code;
    }

    public void setCode(byte[] code2) {
        this.code = code2;
        this.PC = code2.length;
    }

    public void setCodeLength(int len) {
        this.PC = len;
    }

    public int getCodeLength() {
        return this.PC;
    }

    public CodeAttr(Method meth) {
        super("Code");
        addToFrontOf(meth);
        meth.code = this;
        if (meth.getDeclaringClass().getClassfileMajorVersion() >= 50) {
            this.flags |= 3;
        }
    }

    public final void reserve(int bytes) {
        if (this.code == null) {
            this.code = new byte[(bytes + 100)];
        } else if (this.PC + bytes > this.code.length) {
            byte[] new_code = new byte[((this.code.length * 2) + bytes)];
            System.arraycopy(this.code, 0, new_code, 0, this.PC);
            this.code = new_code;
        }
    }

    /* access modifiers changed from: package-private */
    public byte invert_opcode(byte opcode) {
        int iopcode = opcode & 255;
        if ((iopcode >= 153 && iopcode <= 166) || (iopcode >= 198 && iopcode <= 199)) {
            return (byte) (iopcode ^ 1);
        }
        throw new Error("unknown opcode to invert_opcode");
    }

    public final void put1(int i) {
        byte[] bArr = this.code;
        int i2 = this.PC;
        this.PC = i2 + 1;
        bArr[i2] = (byte) i;
        this.unreachable_here = false;
    }

    public final void put2(int i) {
        byte[] bArr = this.code;
        int i2 = this.PC;
        this.PC = i2 + 1;
        bArr[i2] = (byte) (i >> 8);
        byte[] bArr2 = this.code;
        int i3 = this.PC;
        this.PC = i3 + 1;
        bArr2[i3] = (byte) i;
        this.unreachable_here = false;
    }

    public final void put4(int i) {
        byte[] bArr = this.code;
        int i2 = this.PC;
        this.PC = i2 + 1;
        bArr[i2] = (byte) (i >> 24);
        byte[] bArr2 = this.code;
        int i3 = this.PC;
        this.PC = i3 + 1;
        bArr2[i3] = (byte) (i >> 16);
        byte[] bArr3 = this.code;
        int i4 = this.PC;
        this.PC = i4 + 1;
        bArr3[i4] = (byte) (i >> 8);
        byte[] bArr4 = this.code;
        int i5 = this.PC;
        this.PC = i5 + 1;
        bArr4[i5] = (byte) i;
        this.unreachable_here = false;
    }

    public final void putIndex2(CpoolEntry cnst) {
        put2(cnst.index);
    }

    public final void putLineNumber(String filename, int linenumber) {
        if (filename != null) {
            getMethod().classfile.setSourceFile(filename);
        }
        putLineNumber(linenumber);
    }

    public final void putLineNumber(int linenumber) {
        if (this.sourceDbgExt != null) {
            linenumber = this.sourceDbgExt.fixLine(linenumber);
        }
        fixupAdd(14, (Label) null);
        fixupAdd(15, linenumber, (Label) null);
    }

    /* access modifiers changed from: package-private */
    public void noteParamTypes() {
        Method method = getMethod();
        int offset = 0;
        if ((method.access_flags & 8) == 0) {
            Type type = method.classfile;
            if ("<init>".equals(method.getName()) && !"java.lang.Object".equals(type.getName())) {
                type = UninitializedType.uninitializedThis((ClassType) type);
            }
            noteVarType(0, type);
            offset = 0 + 1;
        }
        int arg_count = method.arg_types.length;
        int i = 0;
        while (i < arg_count) {
            Type type2 = method.arg_types[i];
            int offset2 = offset + 1;
            noteVarType(offset, type2);
            int size = type2.getSizeInWords();
            while (true) {
                size--;
                if (size <= 0) {
                    break;
                }
                offset2++;
            }
            i++;
            offset = offset2;
        }
        if ((this.flags & 1) != 0) {
            this.stackMap = new StackMapTableAttr();
            int[] encodedLocals = new int[(offset + 20)];
            int count = 0;
            int i2 = 0;
            while (i2 < offset) {
                int encoded = this.stackMap.encodeVerificationType(this.local_types[i2], this);
                int count2 = count + 1;
                encodedLocals[count] = encoded;
                int tag = encoded & 255;
                if (tag == 3 || tag == 4) {
                    i2++;
                }
                i2++;
                count = count2;
            }
            this.stackMap.encodedLocals = encodedLocals;
            this.stackMap.countLocals = count;
            this.stackMap.encodedStack = new int[10];
            this.stackMap.countStack = 0;
        }
    }

    public void noteVarType(int offset, Type type) {
        Type prev;
        int size = type.getSizeInWords();
        if (this.local_types == null) {
            this.local_types = new Type[(offset + size + 20)];
        } else if (offset + size > this.local_types.length) {
            Type[] new_array = new Type[((offset + size) * 2)];
            System.arraycopy(this.local_types, 0, new_array, 0, this.local_types.length);
            this.local_types = new_array;
        }
        this.local_types[offset] = type;
        if (this.varsSetInCurrentBlock == null) {
            this.varsSetInCurrentBlock = new boolean[this.local_types.length];
        } else if (this.varsSetInCurrentBlock.length <= offset) {
            boolean[] tmp = new boolean[this.local_types.length];
            System.arraycopy(this.varsSetInCurrentBlock, 0, tmp, 0, this.varsSetInCurrentBlock.length);
            this.varsSetInCurrentBlock = tmp;
        }
        this.varsSetInCurrentBlock[offset] = true;
        if (offset > 0 && (prev = this.local_types[offset - 1]) != null && prev.getSizeInWords() == 2) {
            this.local_types[offset - 1] = null;
        }
        while (true) {
            size--;
            if (size > 0) {
                offset++;
                this.local_types[offset] = null;
            } else {
                return;
            }
        }
    }

    public final void setTypes(Label label) {
        setTypes(label.localTypes, label.stackTypes);
    }

    public final void setTypes(Type[] labelLocals, Type[] labelStack) {
        int usedStack = labelStack.length;
        int usedLocals = labelLocals.length;
        if (this.local_types != null) {
            if (usedLocals > 0) {
                System.arraycopy(labelLocals, 0, this.local_types, 0, usedLocals);
            }
            for (int i = usedLocals; i < this.local_types.length; i++) {
                this.local_types[i] = null;
            }
        }
        if (this.stack_types == null || usedStack > this.stack_types.length) {
            this.stack_types = new Type[usedStack];
        } else {
            for (int i2 = usedStack; i2 < this.stack_types.length; i2++) {
                this.stack_types[i2] = null;
            }
        }
        System.arraycopy(labelStack, 0, this.stack_types, 0, usedStack);
        this.SP = usedStack;
    }

    public final void pushType(Type type) {
        if (type.size != 0) {
            if (this.stack_types == null || this.stack_types.length == 0) {
                this.stack_types = new Type[20];
            } else if (this.SP + 1 >= this.stack_types.length) {
                Type[] new_array = new Type[(this.stack_types.length * 2)];
                System.arraycopy(this.stack_types, 0, new_array, 0, this.SP);
                this.stack_types = new_array;
            }
            if (type.size == 8) {
                Type[] typeArr = this.stack_types;
                int i = this.SP;
                this.SP = i + 1;
                typeArr[i] = Type.voidType;
            }
            Type[] typeArr2 = this.stack_types;
            int i2 = this.SP;
            this.SP = i2 + 1;
            typeArr2[i2] = type;
            if (this.SP > this.max_stack) {
                this.max_stack = this.SP;
                return;
            }
            return;
        }
        throw new Error("pushing void type onto stack");
    }

    public final Type popType() {
        if (this.SP > 0) {
            Type[] typeArr = this.stack_types;
            int i = this.SP - 1;
            this.SP = i;
            Type type = typeArr[i];
            if (type.size != 8 || popType().isVoid()) {
                return type;
            }
            throw new Error("missing void type on stack");
        }
        throw new Error("popType called with empty stack " + getMethod());
    }

    public final Type topType() {
        return this.stack_types[this.SP - 1];
    }

    public void emitPop(int nvalues) {
        while (nvalues > 0) {
            reserve(1);
            if (popType().size > 4) {
                put1(88);
            } else if (nvalues > 1) {
                if (popType().size > 4) {
                    put1(87);
                    reserve(1);
                }
                put1(88);
                nvalues--;
            } else {
                put1(87);
            }
            nvalues--;
        }
    }

    public Label getLabel() {
        Label label = new Label();
        label.defineRaw(this);
        return label;
    }

    public void emitSwap() {
        reserve(1);
        Type type1 = popType();
        Type type2 = popType();
        if (type1.size > 4 || type2.size > 4) {
            pushType(type2);
            pushType(type1);
            emitDupX();
            emitPop(1);
            return;
        }
        pushType(type1);
        put1(95);
        pushType(type2);
    }

    public void emitDup() {
        reserve(1);
        Type type = topType();
        put1(type.size <= 4 ? 89 : 92);
        pushType(type);
    }

    public void emitDupX() {
        reserve(1);
        Type type = popType();
        Type skipedType = popType();
        if (skipedType.size <= 4) {
            put1(type.size <= 4 ? 90 : 93);
        } else {
            put1(type.size <= 4 ? 91 : 94);
        }
        pushType(type);
        pushType(skipedType);
        pushType(type);
    }

    public void emitDup(int size, int offset) {
        int kind;
        if (size != 0) {
            reserve(1);
            Type copied1 = popType();
            Type copied2 = null;
            if (size == 1) {
                if (copied1.size > 4) {
                    throw new Error("using dup for 2-word type");
                }
            } else if (size != 2) {
                throw new Error("invalid size to emitDup");
            } else if (copied1.size <= 4) {
                copied2 = popType();
                if (copied2.size > 4) {
                    throw new Error("dup will cause invalid types on stack");
                }
            }
            Type skipped1 = null;
            Type skipped2 = null;
            if (offset == 0) {
                kind = size == 1 ? 89 : 92;
            } else if (offset == 1) {
                kind = size == 1 ? 90 : 93;
                skipped1 = popType();
                if (skipped1.size > 4) {
                    throw new Error("dup will cause invalid types on stack");
                }
            } else if (offset == 2) {
                kind = size == 1 ? 91 : 94;
                skipped1 = popType();
                if (skipped1.size <= 4) {
                    skipped2 = popType();
                    if (skipped2.size > 4) {
                        throw new Error("dup will cause invalid types on stack");
                    }
                }
            } else {
                throw new Error("emitDup:  invalid offset");
            }
            put1(kind);
            if (copied2 != null) {
                pushType(copied2);
            }
            pushType(copied1);
            if (skipped2 != null) {
                pushType(skipped2);
            }
            if (skipped1 != null) {
                pushType(skipped1);
            }
            if (copied2 != null) {
                pushType(copied2);
            }
            pushType(copied1);
        }
    }

    public void emitDup(int size) {
        emitDup(size, 0);
    }

    public void emitDup(Type type) {
        emitDup(type.size > 4 ? 2 : 1, 0);
    }

    public void enterScope(Scope scope) {
        scope.setStartPC(this);
        this.locals.enterScope(scope);
    }

    public Scope pushScope() {
        Scope scope = new Scope();
        if (this.locals == null) {
            this.locals = new LocalVarsAttr(getMethod());
        }
        enterScope(scope);
        if (this.locals.parameter_scope == null) {
            this.locals.parameter_scope = scope;
        }
        return scope;
    }

    public Scope getCurrentScope() {
        return this.locals.current_scope;
    }

    public Scope popScope() {
        Scope scope = this.locals.current_scope;
        this.locals.current_scope = scope.parent;
        scope.freeLocals(this);
        scope.end = getLabel();
        return scope;
    }

    public Variable getArg(int index) {
        return this.locals.parameter_scope.getVariable(index);
    }

    public Variable lookup(String name) {
        for (Scope scope = this.locals.current_scope; scope != null; scope = scope.parent) {
            Variable var = scope.lookup(name);
            if (var != null) {
                return var;
            }
        }
        return null;
    }

    public Variable addLocal(Type type) {
        return this.locals.current_scope.addVariable(this, type, (String) null);
    }

    public Variable addLocal(Type type, String name) {
        return this.locals.current_scope.addVariable(this, type, name);
    }

    public void addParamLocals() {
        Method method = getMethod();
        if ((method.access_flags & 8) == 0) {
            addLocal(method.classfile).setParameter(true);
        }
        for (Type addLocal : method.arg_types) {
            addLocal(addLocal).setParameter(true);
        }
    }

    public final void emitPushConstant(int val, Type type) {
        switch (type.getSignature().charAt(0)) {
            case 'B':
            case 'C':
            case 'I':
            case 'S':
            case 'Z':
                emitPushInt(val);
                return;
            case 'D':
                emitPushDouble((double) val);
                return;
            case 'F':
                emitPushFloat((float) val);
                return;
            case 'J':
                emitPushLong((long) val);
                return;
            default:
                throw new Error("bad type to emitPushConstant");
        }
    }

    public final void emitPushConstant(CpoolEntry cnst) {
        reserve(3);
        int index = cnst.index;
        if (cnst instanceof CpoolValue2) {
            put1(20);
            put2(index);
        } else if (index < 256) {
            put1(18);
            put1(index);
        } else {
            put1(19);
            put2(index);
        }
    }

    public final void emitPushInt(int i) {
        reserve(3);
        if (i >= -1 && i <= 5) {
            put1(i + 3);
        } else if (i >= -128 && i < 128) {
            put1(16);
            put1(i);
        } else if (i < -32768 || i >= 32768) {
            emitPushConstant(getConstants().addInt(i));
        } else {
            put1(17);
            put2(i);
        }
        pushType(Type.intType);
    }

    public void emitPushLong(long i) {
        if (i == 0 || i == 1) {
            reserve(1);
            put1(((int) i) + 9);
        } else if (((long) ((int) i)) == i) {
            emitPushInt((int) i);
            reserve(1);
            popType();
            put1(133);
        } else {
            emitPushConstant(getConstants().addLong(i));
        }
        pushType(Type.longType);
    }

    public void emitPushFloat(float x) {
        int xi = (int) x;
        if (((float) xi) != x || xi < -128 || xi >= 128) {
            emitPushConstant(getConstants().addFloat(x));
        } else if (xi < 0 || xi > 2) {
            emitPushInt(xi);
            reserve(1);
            popType();
            put1(134);
        } else {
            reserve(1);
            put1(xi + 11);
            if (xi == 0 && Float.floatToIntBits(x) != 0) {
                reserve(1);
                put1(118);
            }
        }
        pushType(Type.floatType);
    }

    public void emitPushDouble(double x) {
        int xi = (int) x;
        if (((double) xi) != x || xi < -128 || xi >= 128) {
            emitPushConstant(getConstants().addDouble(x));
        } else if (xi == 0 || xi == 1) {
            reserve(1);
            put1(xi + 14);
            if (xi == 0 && Double.doubleToLongBits(x) != 0) {
                reserve(1);
                put1(119);
            }
        } else {
            emitPushInt(xi);
            reserve(1);
            popType();
            put1(135);
        }
        pushType(Type.doubleType);
    }

    public static final String calculateSplit(String str) {
        int strLength = str.length();
        StringBuffer sbuf = new StringBuffer(20);
        int segmentStart = 0;
        int byteLength = 0;
        for (int i = 0; i < strLength; i++) {
            char ch = str.charAt(i);
            int bytes = ch >= 2048 ? 3 : (ch >= 128 || ch == 0) ? 2 : 1;
            if (byteLength + bytes > 65535) {
                sbuf.append((char) (i - segmentStart));
                segmentStart = i;
                byteLength = 0;
            }
            byteLength += bytes;
        }
        sbuf.append((char) (strLength - segmentStart));
        return sbuf.toString();
    }

    public final void emitPushString(String str) {
        if (str == null) {
            emitPushNull();
            return;
        }
        int length = str.length();
        String segments = calculateSplit(str);
        int numSegments = segments.length();
        if (numSegments <= 1) {
            emitPushConstant(getConstants().addString(str));
            pushType(Type.javalangStringType);
            return;
        }
        if (numSegments == 2) {
            int firstSegment = segments.charAt(0);
            emitPushString(str.substring(0, firstSegment));
            emitPushString(str.substring(firstSegment));
            emitInvokeVirtual(Type.javalangStringType.getDeclaredMethod("concat", 1));
        } else {
            ClassType sbufType = ClassType.make("java.lang.StringBuffer");
            emitNew(sbufType);
            emitDup((Type) sbufType);
            emitPushInt(length);
            emitInvokeSpecial(sbufType.getDeclaredMethod("<init>", new Type[]{Type.intType}));
            Method appendMethod = sbufType.getDeclaredMethod("append", new Type[]{Type.javalangStringType});
            int segStart = 0;
            for (int seg = 0; seg < numSegments; seg++) {
                emitDup((Type) sbufType);
                int segEnd = segments.charAt(seg) + segStart;
                emitPushString(str.substring(segStart, segEnd));
                emitInvokeVirtual(appendMethod);
                segStart = segEnd;
            }
            emitInvokeVirtual(Type.toString_method);
        }
        if (str == str.intern()) {
            emitInvokeVirtual(Type.javalangStringType.getDeclaredMethod("intern", 0));
        }
    }

    public final void emitPushClass(ObjectType ctype) {
        emitPushConstant(getConstants().addClass(ctype));
        pushType(Type.javalangClassType);
    }

    public void emitPushNull() {
        reserve(1);
        put1(1);
        pushType(Type.nullType);
    }

    public void emitPushDefaultValue(Type type) {
        Type type2 = type.getImplementationType();
        if (type2 instanceof PrimType) {
            emitPushConstant(0, type2);
        } else {
            emitPushNull();
        }
    }

    public void emitStoreDefaultValue(Variable var) {
        emitPushDefaultValue(var.getType());
        emitStore(var);
    }

    public final void emitPushThis() {
        emitLoad(this.locals.used[0]);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void emitPushPrimArray(java.lang.Object r16, gnu.bytecode.ArrayType r17) {
        /*
            r15 = this;
            r0 = r15
            gnu.bytecode.Type r1 = r17.getComponentType()
            int r2 = java.lang.reflect.Array.getLength(r16)
            r15.emitPushInt(r2)
            r15.emitNewArray((gnu.bytecode.Type) r1)
            java.lang.String r3 = r1.getSignature()
            r4 = 0
            char r3 = r3.charAt(r4)
            r4 = 0
        L_0x0019:
            if (r4 >= r2) goto L_0x00d4
            r5 = 0
            r7 = 0
            r8 = 0
            r10 = 0
            r12 = 0
            switch(r3) {
                case 66: goto L_0x00a0;
                case 67: goto L_0x0090;
                case 68: goto L_0x0081;
                case 70: goto L_0x0071;
                case 73: goto L_0x0061;
                case 74: goto L_0x0051;
                case 83: goto L_0x0040;
                case 90: goto L_0x0029;
                default: goto L_0x0027;
            }
        L_0x0027:
            goto L_0x00b0
        L_0x0029:
            r10 = r16
            boolean[] r10 = (boolean[]) r10
            boolean[] r10 = (boolean[]) r10
            boolean r10 = r10[r4]
            if (r10 == 0) goto L_0x0036
            r10 = 1
            goto L_0x0037
        L_0x0036:
            r10 = r12
        L_0x0037:
            r5 = r10
            int r10 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            if (r10 != 0) goto L_0x00b0
            r10 = r17
            goto L_0x00d0
        L_0x0040:
            r10 = r16
            short[] r10 = (short[]) r10
            short[] r10 = (short[]) r10
            short r10 = r10[r4]
            long r5 = (long) r10
            int r10 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            if (r10 != 0) goto L_0x00b0
            r10 = r17
            goto L_0x00d0
        L_0x0051:
            r10 = r16
            long[] r10 = (long[]) r10
            long[] r10 = (long[]) r10
            r5 = r10[r4]
            int r10 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            if (r10 != 0) goto L_0x00b0
            r10 = r17
            goto L_0x00d0
        L_0x0061:
            r10 = r16
            int[] r10 = (int[]) r10
            int[] r10 = (int[]) r10
            r10 = r10[r4]
            long r5 = (long) r10
            int r10 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            if (r10 != 0) goto L_0x00b0
            r10 = r17
            goto L_0x00d0
        L_0x0071:
            r12 = r16
            float[] r12 = (float[]) r12
            float[] r12 = (float[]) r12
            r7 = r12[r4]
            double r12 = (double) r7
            int r14 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
            if (r14 != 0) goto L_0x00b0
            r10 = r17
            goto L_0x00d0
        L_0x0081:
            r12 = r16
            double[] r12 = (double[]) r12
            double[] r12 = (double[]) r12
            r8 = r12[r4]
            int r12 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r12 != 0) goto L_0x00b0
            r10 = r17
            goto L_0x00d0
        L_0x0090:
            r10 = r16
            char[] r10 = (char[]) r10
            char[] r10 = (char[]) r10
            char r10 = r10[r4]
            long r5 = (long) r10
            int r10 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            if (r10 != 0) goto L_0x00b0
            r10 = r17
            goto L_0x00d0
        L_0x00a0:
            r10 = r16
            byte[] r10 = (byte[]) r10
            byte[] r10 = (byte[]) r10
            byte r10 = r10[r4]
            long r5 = (long) r10
            int r10 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            if (r10 != 0) goto L_0x00b0
            r10 = r17
            goto L_0x00d0
        L_0x00b0:
            r10 = r17
            r15.emitDup((gnu.bytecode.Type) r10)
            r15.emitPushInt(r4)
            switch(r3) {
                case 66: goto L_0x00c8;
                case 67: goto L_0x00c8;
                case 68: goto L_0x00c4;
                case 70: goto L_0x00c0;
                case 73: goto L_0x00c8;
                case 74: goto L_0x00bc;
                case 83: goto L_0x00c8;
                case 90: goto L_0x00c8;
                default: goto L_0x00bb;
            }
        L_0x00bb:
            goto L_0x00cd
        L_0x00bc:
            r15.emitPushLong(r5)
            goto L_0x00cd
        L_0x00c0:
            r15.emitPushFloat(r7)
            goto L_0x00cd
        L_0x00c4:
            r15.emitPushDouble(r8)
            goto L_0x00cd
        L_0x00c8:
            int r11 = (int) r5
            r15.emitPushInt(r11)
        L_0x00cd:
            r15.emitArrayStore(r1)
        L_0x00d0:
            int r4 = r4 + 1
            goto L_0x0019
        L_0x00d4:
            r10 = r17
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.CodeAttr.emitPushPrimArray(java.lang.Object, gnu.bytecode.ArrayType):void");
    }

    /* access modifiers changed from: package-private */
    public void emitNewArray(int type_code) {
        reserve(2);
        put1(188);
        put1(type_code);
    }

    public final void emitArrayLength() {
        if (popType() instanceof ArrayType) {
            reserve(1);
            put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SEEK);
            pushType(Type.intType);
            return;
        }
        throw new Error("non-array type in emitArrayLength");
    }

    private int adjustTypedOp(char sig) {
        switch (sig) {
            case 'B':
            case 'Z':
                return 5;
            case 'C':
                return 6;
            case 'D':
                return 3;
            case 'F':
                return 2;
            case 'I':
                return 0;
            case 'J':
                return 1;
            case 'S':
                return 7;
            default:
                return 4;
        }
    }

    private int adjustTypedOp(Type type) {
        return adjustTypedOp(type.getSignature().charAt(0));
    }

    private void emitTypedOp(int op, Type type) {
        reserve(1);
        put1(adjustTypedOp(type) + op);
    }

    private void emitTypedOp(int op, char sig) {
        reserve(1);
        put1(adjustTypedOp(sig) + op);
    }

    public void emitArrayStore(Type element_type) {
        popType();
        popType();
        popType();
        emitTypedOp(79, element_type);
    }

    public void emitArrayStore() {
        popType();
        popType();
        emitTypedOp(79, ((ArrayType) popType().getImplementationType()).getComponentType());
    }

    public void emitArrayLoad(Type element_type) {
        popType();
        popType();
        emitTypedOp(46, element_type);
        pushType(element_type);
    }

    public void emitArrayLoad() {
        popType();
        Type elementType = ((ArrayType) popType().getImplementationType()).getComponentType();
        emitTypedOp(46, elementType);
        pushType(elementType);
    }

    public void emitNew(ClassType type) {
        reserve(3);
        Label label = new Label(this);
        label.defineRaw(this);
        put1(187);
        putIndex2(getConstants().addClass((ObjectType) type));
        pushType(new UninitializedType(type, label));
    }

    public void emitNewArray(Type element_type, int dims) {
        int code2;
        if (popType().promote() == Type.intType) {
            if (element_type instanceof PrimType) {
                switch (element_type.getSignature().charAt(0)) {
                    case 'B':
                        code2 = 8;
                        break;
                    case 'C':
                        code2 = 5;
                        break;
                    case 'D':
                        code2 = 7;
                        break;
                    case 'F':
                        code2 = 6;
                        break;
                    case 'I':
                        code2 = 10;
                        break;
                    case 'J':
                        code2 = 11;
                        break;
                    case 'S':
                        code2 = 9;
                        break;
                    case 'Z':
                        code2 = 4;
                        break;
                    default:
                        throw new Error("bad PrimType in emitNewArray");
                }
                emitNewArray(code2);
            } else if (element_type instanceof ObjectType) {
                reserve(3);
                put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_DIALOG_FLAG);
                putIndex2(getConstants().addClass((ObjectType) element_type));
            } else if (element_type instanceof ArrayType) {
                reserve(4);
                put1(197);
                putIndex2(getConstants().addClass((ObjectType) new ArrayType(element_type)));
                if (dims < 1 || dims > 255) {
                    throw new Error("dims out of range in emitNewArray");
                }
                put1(dims);
                do {
                    dims--;
                    if (dims > 0) {
                    }
                } while (popType().promote() == Type.intType);
                throw new Error("non-int dim. spec. in emitNewArray");
            } else {
                throw new Error("unimplemented type in emitNewArray");
            }
            pushType(new ArrayType(element_type));
            return;
        }
        throw new Error("non-int dim. spec. in emitNewArray");
    }

    public void emitNewArray(Type element_type) {
        emitNewArray(element_type, 1);
    }

    private void emitBinop(int base_code) {
        Type type2 = popType().promote();
        Type type1_raw = popType();
        Type type1 = type1_raw.promote();
        if (type1 != type2 || !(type1 instanceof PrimType)) {
            throw new Error("non-matching or bad types in binary operation");
        }
        emitTypedOp(base_code, type1);
        pushType(type1_raw);
    }

    private void emitBinop(int base_code, char sig) {
        popType();
        popType();
        emitTypedOp(base_code, sig);
        pushType(Type.signatureToPrimitive(sig));
    }

    public void emitBinop(int base_code, Type type) {
        popType();
        popType();
        emitTypedOp(base_code, type);
        pushType(type);
    }

    public final void emitAdd(char sig) {
        emitBinop(96, sig);
    }

    public final void emitAdd(PrimType type) {
        emitBinop(96, (Type) type);
    }

    public final void emitAdd() {
        emitBinop(96);
    }

    public final void emitSub(char sig) {
        emitBinop(100, sig);
    }

    public final void emitSub(PrimType type) {
        emitBinop(100, (Type) type);
    }

    public final void emitSub() {
        emitBinop(100);
    }

    public final void emitMul() {
        emitBinop(104);
    }

    public final void emitDiv() {
        emitBinop(108);
    }

    public final void emitRem() {
        emitBinop(112);
    }

    public final void emitAnd() {
        emitBinop(126);
    }

    public final void emitIOr() {
        emitBinop(128);
    }

    public final void emitXOr() {
        emitBinop(130);
    }

    public final void emitShl() {
        emitShift(120);
    }

    public final void emitShr() {
        emitShift(122);
    }

    public final void emitUshr() {
        emitShift(124);
    }

    private void emitShift(int base_code) {
        Type type2 = popType().promote();
        Type type1_raw = popType();
        Type type1 = type1_raw.promote();
        if (type1 != Type.intType && type1 != Type.longType) {
            throw new Error("the value shifted must be an int or a long");
        } else if (type2 == Type.intType) {
            emitTypedOp(base_code, type1);
            pushType(type1_raw);
        } else {
            throw new Error("the amount of shift must be an int");
        }
    }

    public final void emitNot(Type type) {
        emitPushConstant(1, type);
        emitAdd();
        emitPushConstant(1, type);
        emitAnd();
    }

    public void emitPrimop(int opcode, int arg_count, Type retType) {
        reserve(1);
        while (true) {
            arg_count--;
            if (arg_count >= 0) {
                popType();
            } else {
                put1(opcode);
                pushType(retType);
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void emitMaybeWide(int opcode, int index) {
        if (index >= 256) {
            put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION);
            put1(opcode);
            put2(index);
            return;
        }
        put1(opcode);
        put1(index);
    }

    public final void emitLoad(Variable var) {
        if (!var.dead()) {
            int offset = var.offset;
            if (offset < 0 || !var.isSimple()) {
                throw new Error("attempting to load from unassigned variable " + var + " simple:" + var.isSimple() + ", offset: " + offset);
            }
            Type type = var.getType().promote();
            reserve(4);
            int kind = adjustTypedOp(type);
            if (offset <= 3) {
                put1((kind * 4) + 26 + offset);
            } else {
                emitMaybeWide(kind + 21, offset);
            }
            pushType(var.getType());
            return;
        }
        throw new Error("attempting to push dead variable");
    }

    public void emitStore(Variable var) {
        int offset = var.offset;
        if (offset < 0 || !var.isSimple()) {
            throw new Error("attempting to store in unassigned " + var + " simple:" + var.isSimple() + ", offset: " + offset);
        }
        Type type = var.getType().promote();
        noteVarType(offset, type);
        reserve(4);
        popType();
        int kind = adjustTypedOp(type);
        if (offset <= 3) {
            put1((kind * 4) + 59 + offset);
        } else {
            emitMaybeWide(kind + 54, offset);
        }
    }

    public void emitInc(Variable var, short inc) {
        if (!var.dead()) {
            int offset = var.offset;
            if (offset < 0 || !var.isSimple()) {
                throw new Error("attempting to increment unassigned variable" + var.getName() + " simple:" + var.isSimple() + ", offset: " + offset);
            }
            reserve(6);
            if (var.getType().getImplementationType().promote() == Type.intType) {
                if (offset > 255 || inc > 255 || inc < -256) {
                    put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION);
                    put1(132);
                    put2(offset);
                    put2(inc);
                    return;
                }
                put1(132);
                put1(offset);
                put1(inc);
                return;
            }
            throw new Error("attempting to increment non-int variable");
        }
        throw new Error("attempting to increment dead variable");
    }

    private final void emitFieldop(Field field, int opcode) {
        reserve(3);
        put1(opcode);
        putIndex2(getConstants().addFieldRef(field));
    }

    public final void emitGetStatic(Field field) {
        pushType(field.type);
        emitFieldop(field, 178);
    }

    public final void emitGetField(Field field) {
        popType();
        pushType(field.type);
        emitFieldop(field, 180);
    }

    public final void emitPutStatic(Field field) {
        popType();
        emitFieldop(field, 179);
    }

    public final void emitPutField(Field field) {
        popType();
        popType();
        emitFieldop(field, 181);
    }

    private int words(Type[] types) {
        int res = 0;
        int i = types.length;
        while (true) {
            i--;
            if (i < 0) {
                return res;
            }
            if (types[i].size > 4) {
                res += 2;
            } else {
                res++;
            }
        }
    }

    public void emitInvokeMethod(Method method, int opcode) {
        Type t;
        reserve(opcode == 185 ? 5 : 3);
        int arg_count = method.arg_types.length;
        int i = 0;
        boolean is_invokestatic = opcode == 184;
        boolean is_init = opcode == 183 && "<init>".equals(method.getName());
        if (is_invokestatic == ((method.access_flags & 8) != 0)) {
            if (!is_invokestatic && !is_init) {
                arg_count++;
            }
            put1(opcode);
            putIndex2(getConstants().addMethodRef(method));
            if (opcode == 185) {
                put1(words(method.arg_types) + 1);
                put1(0);
            }
            do {
                arg_count--;
                if (arg_count >= 0) {
                    t = popType();
                } else {
                    if (is_init) {
                        Type t2 = popType();
                        if (t2 instanceof UninitializedType) {
                            ClassType ctype = ((UninitializedType) t2).ctype;
                            for (int i2 = 0; i2 < this.SP; i2++) {
                                if (this.stack_types[i2] == t2) {
                                    this.stack_types[i2] = ctype;
                                }
                            }
                            Variable[] used = this.locals.used;
                            int i3 = used == null ? 0 : used.length;
                            while (true) {
                                i3--;
                                if (i3 < 0) {
                                    break;
                                }
                                Variable var = used[i3];
                                if (var != null && var.type == t2) {
                                    var.type = ctype;
                                }
                            }
                            if (this.local_types != null) {
                                i = this.local_types.length;
                            }
                            while (true) {
                                i--;
                                if (i < 0) {
                                    break;
                                } else if (this.local_types[i] == t2) {
                                    this.local_types[i] = ctype;
                                }
                            }
                        } else {
                            throw new Error("calling <init> on already-initialized object");
                        }
                    }
                    if (method.return_type.size != 0) {
                        pushType(method.return_type);
                        return;
                    }
                    return;
                }
            } while (!(t instanceof UninitializedType));
            throw new Error("passing " + t + " as parameter");
        }
        throw new Error("emitInvokeXxx static flag mis-match method.flags=" + method.access_flags);
    }

    public void emitInvoke(Method method) {
        int opcode;
        if ((method.access_flags & 8) != 0) {
            opcode = 184;
        } else if (method.classfile.isInterface()) {
            opcode = 185;
        } else if ("<init>".equals(method.getName())) {
            opcode = 183;
        } else {
            opcode = 182;
        }
        emitInvokeMethod(method, opcode);
    }

    public void emitInvokeVirtual(Method method) {
        emitInvokeMethod(method, 182);
    }

    public void emitInvokeSpecial(Method method) {
        emitInvokeMethod(method, 183);
    }

    public void emitInvokeStatic(Method method) {
        emitInvokeMethod(method, 184);
    }

    public void emitInvokeInterface(Method method) {
        emitInvokeMethod(method, 185);
    }

    /* access modifiers changed from: package-private */
    public final void emitTransfer(Label label, int opcode) {
        label.setTypes(this);
        fixupAdd(6, label);
        put1(opcode);
        this.PC += 2;
    }

    public final void emitGoto(Label label) {
        label.setTypes(this);
        fixupAdd(4, label);
        reserve(3);
        put1(167);
        this.PC += 2;
        setUnreachable();
    }

    public final void emitJsr(Label label) {
        fixupAdd(5, label);
        reserve(3);
        put1(168);
        this.PC += 2;
    }

    public ExitableBlock startExitableBlock(Type resultType, boolean runFinallyBlocks) {
        ExitableBlock bl = new ExitableBlock(resultType, this, runFinallyBlocks);
        bl.outer = this.currentExitableBlock;
        this.currentExitableBlock = bl;
        return bl;
    }

    public void endExitableBlock() {
        ExitableBlock bl = this.currentExitableBlock;
        bl.finish();
        this.currentExitableBlock = bl.outer;
    }

    public final void emitGotoIfCompare1(Label label, int opcode) {
        popType();
        reserve(3);
        emitTransfer(label, opcode);
    }

    public final void emitGotoIfIntEqZero(Label label) {
        emitGotoIfCompare1(label, 153);
    }

    public final void emitGotoIfIntNeZero(Label label) {
        emitGotoIfCompare1(label, 154);
    }

    public final void emitGotoIfIntLtZero(Label label) {
        emitGotoIfCompare1(label, 155);
    }

    public final void emitGotoIfIntGeZero(Label label) {
        emitGotoIfCompare1(label, 156);
    }

    public final void emitGotoIfIntGtZero(Label label) {
        emitGotoIfCompare1(label, 157);
    }

    public final void emitGotoIfIntLeZero(Label label) {
        emitGotoIfCompare1(label, 158);
    }

    public final void emitGotoIfCompare2(Label label, int logop) {
        if (logop < 153 || logop > 158) {
            throw new Error("emitGotoIfCompare2: logop must be one of ifeq...ifle");
        }
        Type type2 = popType().promote();
        Type type1 = popType().promote();
        reserve(4);
        boolean z = false;
        char sig1 = type1.getSignature().charAt(0);
        char sig2 = type2.getSignature().charAt(0);
        if (logop == 155 || logop == 158) {
            z = true;
        }
        boolean cmpg = z;
        if (sig1 == 'I' && sig2 == 'I') {
            logop += 6;
        } else if (sig1 == 'J' && sig2 == 'J') {
            put1(148);
        } else if (sig1 == 'F' && sig2 == 'F') {
            put1(cmpg ? 149 : 150);
        } else if (sig1 == 'D' && sig2 == 'D') {
            put1(cmpg ? 151 : 152);
        } else if ((sig1 == 'L' || sig1 == '[') && ((sig2 == 'L' || sig2 == '[') && logop <= 154)) {
            logop += 12;
        } else {
            throw new Error("invalid types to emitGotoIfCompare2");
        }
        emitTransfer(label, logop);
    }

    public final void emitGotoIfEq(Label label, boolean invert) {
        emitGotoIfCompare2(label, invert ? 154 : 153);
    }

    public final void emitGotoIfEq(Label label) {
        emitGotoIfCompare2(label, 153);
    }

    public final void emitGotoIfNE(Label label) {
        emitGotoIfCompare2(label, 154);
    }

    public final void emitGotoIfLt(Label label) {
        emitGotoIfCompare2(label, 155);
    }

    public final void emitGotoIfGe(Label label) {
        emitGotoIfCompare2(label, 156);
    }

    public final void emitGotoIfGt(Label label) {
        emitGotoIfCompare2(label, 157);
    }

    public final void emitGotoIfLe(Label label) {
        emitGotoIfCompare2(label, 158);
    }

    public final void emitIfCompare1(int opcode) {
        IfState new_if = new IfState(this);
        if (popType().promote() == Type.intType) {
            reserve(3);
            emitTransfer(new_if.end_label, opcode);
            new_if.start_stack_size = this.SP;
            return;
        }
        throw new Error("non-int type to emitIfCompare1");
    }

    public final void emitIfIntNotZero() {
        emitIfCompare1(153);
    }

    public final void emitIfIntEqZero() {
        emitIfCompare1(154);
    }

    public final void emitIfIntLEqZero() {
        emitIfCompare1(157);
    }

    public final void emitIfRefCompare1(int opcode) {
        IfState new_if = new IfState(this);
        if (popType() instanceof ObjectType) {
            reserve(3);
            emitTransfer(new_if.end_label, opcode);
            new_if.start_stack_size = this.SP;
            return;
        }
        throw new Error("non-ref type to emitIfRefCompare1");
    }

    public final void emitIfNotNull() {
        emitIfRefCompare1(198);
    }

    public final void emitIfNull() {
        emitIfRefCompare1(199);
    }

    public final void emitIfIntCompare(int opcode) {
        IfState new_if = new IfState(this);
        popType();
        popType();
        reserve(3);
        emitTransfer(new_if.end_label, opcode);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfIntLt() {
        emitIfIntCompare(162);
    }

    public final void emitIfNEq() {
        IfState new_if = new IfState(this);
        emitGotoIfEq(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfEq() {
        IfState new_if = new IfState(this);
        emitGotoIfNE(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfLt() {
        IfState new_if = new IfState(this);
        emitGotoIfGe(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfGe() {
        IfState new_if = new IfState(this);
        emitGotoIfLt(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfGt() {
        IfState new_if = new IfState(this);
        emitGotoIfLe(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfLe() {
        IfState new_if = new IfState(this);
        emitGotoIfGt(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public void emitRet(Variable var) {
        int offset = var.offset;
        if (offset < 256) {
            reserve(2);
            put1(169);
            put1(offset);
            return;
        }
        reserve(4);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION);
        put1(169);
        put2(offset);
    }

    public final void emitThen() {
        this.if_stack.start_stack_size = this.SP;
    }

    public final void emitIfThen() {
        new IfState(this, (Label) null);
    }

    public final void emitElse() {
        Label else_label = this.if_stack.end_label;
        if (reachableHere()) {
            Label end_label = new Label(this);
            this.if_stack.end_label = end_label;
            int growth = this.SP - this.if_stack.start_stack_size;
            this.if_stack.stack_growth = growth;
            if (growth > 0) {
                this.if_stack.then_stacked_types = new Type[growth];
                System.arraycopy(this.stack_types, this.if_stack.start_stack_size, this.if_stack.then_stacked_types, 0, growth);
            } else {
                this.if_stack.then_stacked_types = new Type[0];
            }
            emitGoto(end_label);
        } else {
            this.if_stack.end_label = null;
        }
        while (this.SP > this.if_stack.start_stack_size) {
            popType();
        }
        this.SP = this.if_stack.start_stack_size;
        if (else_label != null) {
            else_label.define(this);
        }
        this.if_stack.doing_else = true;
    }

    public final void emitFi() {
        boolean make_unreachable = false;
        if (!this.if_stack.doing_else) {
            if (reachableHere() && this.SP != this.if_stack.start_stack_size) {
                throw new Error("at PC " + this.PC + " then clause grows stack with no else clause");
            }
        } else if (this.if_stack.then_stacked_types != null) {
            int then_clause_stack_size = this.if_stack.start_stack_size + this.if_stack.stack_growth;
            if (!reachableHere()) {
                if (this.if_stack.stack_growth > 0) {
                    System.arraycopy(this.if_stack.then_stacked_types, 0, this.stack_types, this.if_stack.start_stack_size, this.if_stack.stack_growth);
                }
                this.SP = then_clause_stack_size;
            } else if (this.SP != then_clause_stack_size) {
                throw new Error("at PC " + this.PC + ": SP at end of 'then' was " + then_clause_stack_size + " while SP at end of 'else' was " + this.SP);
            }
        } else if (this.unreachable_here != 0) {
            make_unreachable = true;
        }
        if (this.if_stack.end_label != null) {
            this.if_stack.end_label.define(this);
        }
        if (make_unreachable) {
            setUnreachable();
        }
        this.if_stack = this.if_stack.previous;
    }

    public final void emitConvert(Type from, Type to) {
        String to_sig = to.getSignature();
        String from_sig = from.getSignature();
        int op = -1;
        if (to_sig.length() == 1 || from_sig.length() == 1) {
            char to_sig0 = to_sig.charAt(0);
            char from_sig0 = from_sig.charAt(0);
            if (from_sig0 != to_sig0) {
                if (from.size < 4) {
                    from_sig0 = Access.INNERCLASS_CONTEXT;
                }
                if (to.size < 4) {
                    emitConvert(from, Type.intType);
                    from_sig0 = Access.INNERCLASS_CONTEXT;
                }
                if (from_sig0 != to_sig0) {
                    switch (from_sig0) {
                        case 'D':
                            switch (to_sig0) {
                                case 'F':
                                    op = 144;
                                    break;
                                case 'I':
                                    op = 142;
                                    break;
                                case 'J':
                                    op = 143;
                                    break;
                            }
                        case 'F':
                            switch (to_sig0) {
                                case 'D':
                                    op = 141;
                                    break;
                                case 'I':
                                    op = 139;
                                    break;
                                case 'J':
                                    op = 140;
                                    break;
                            }
                        case 'I':
                            switch (to_sig0) {
                                case 'B':
                                    op = 145;
                                    break;
                                case 'C':
                                    op = 146;
                                    break;
                                case 'D':
                                    op = 135;
                                    break;
                                case 'F':
                                    op = 134;
                                    break;
                                case 'J':
                                    op = 133;
                                    break;
                                case 'S':
                                    op = 147;
                                    break;
                            }
                        case 'J':
                            switch (to_sig0) {
                                case 'D':
                                    op = 138;
                                    break;
                                case 'F':
                                    op = 137;
                                    break;
                                case 'I':
                                    op = 136;
                                    break;
                            }
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
        if (op >= 0) {
            reserve(1);
            popType();
            put1(op);
            pushType(to);
            return;
        }
        throw new Error("unsupported CodeAttr.emitConvert");
    }

    private void emitCheckcast(Type type, int opcode) {
        reserve(3);
        popType();
        put1(opcode);
        if (type instanceof ObjectType) {
            putIndex2(getConstants().addClass((ObjectType) type));
            return;
        }
        throw new Error("unimplemented type " + type + " in emitCheckcast/emitInstanceof");
    }

    public static boolean castNeeded(Type top, Type required) {
        if (top instanceof UninitializedType) {
            top = ((UninitializedType) top).getImplementationType();
        }
        while (top != required) {
            if ((required instanceof ClassType) && (top instanceof ClassType) && ((ClassType) top).isSubclass((ClassType) required)) {
                return false;
            }
            if (!(required instanceof ArrayType) || !(top instanceof ArrayType)) {
                return true;
            }
            required = ((ArrayType) required).getComponentType();
            top = ((ArrayType) top).getComponentType();
        }
        return false;
    }

    public void emitCheckcast(Type type) {
        if (castNeeded(topType(), type)) {
            emitCheckcast(type, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PAUSE);
            pushType(type);
        }
    }

    public void emitInstanceof(Type type) {
        emitCheckcast(type, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_STOP);
        pushType(Type.booleanType);
    }

    public final void emitThrow() {
        popType();
        reserve(1);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PLAY);
        setUnreachable();
    }

    public final void emitMonitorEnter() {
        popType();
        reserve(1);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SOURCE);
    }

    public final void emitMonitorExit() {
        popType();
        reserve(1);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_FULLSCREEN);
    }

    public final void emitReturn() {
        if (this.try_stack != null) {
            new Error();
        }
        emitRawReturn();
    }

    /* access modifiers changed from: package-private */
    public final void emitRawReturn() {
        if (getMethod().getReturnType().size == 0) {
            reserve(1);
            put1(177);
        } else {
            emitTypedOp(172, popType().promote());
        }
        setUnreachable();
    }

    public void addHandler(int start_pc, int end_pc, int handler_pc, int catch_type) {
        int index = this.exception_table_length * 4;
        if (this.exception_table == null) {
            this.exception_table = new short[20];
        } else if (this.exception_table.length <= index) {
            short[] new_table = new short[(this.exception_table.length * 2)];
            System.arraycopy(this.exception_table, 0, new_table, 0, index);
            this.exception_table = new_table;
        }
        int index2 = index + 1;
        this.exception_table[index] = (short) start_pc;
        int index3 = index2 + 1;
        this.exception_table[index2] = (short) end_pc;
        int index4 = index3 + 1;
        this.exception_table[index3] = (short) handler_pc;
        int i = index4 + 1;
        this.exception_table[index4] = (short) catch_type;
        this.exception_table_length++;
    }

    public void addHandler(Label start_try, Label end_try, ClassType catch_type) {
        int catch_type_index;
        ConstantPool constants = getConstants();
        if (catch_type == null) {
            catch_type_index = 0;
        } else {
            catch_type_index = constants.addClass((ObjectType) catch_type).index;
        }
        fixupAdd(11, start_try);
        fixupAdd(12, catch_type_index, end_try);
        Label handler = new Label();
        handler.localTypes = start_try.localTypes;
        handler.stackTypes = new Type[1];
        handler.stackTypes[0] = catch_type == null ? Type.javalangThrowableType : catch_type;
        setTypes(handler);
        fixupAdd(13, 0, handler);
    }

    public void emitWithCleanupStart() {
        int savedSP = this.SP;
        this.SP = 0;
        emitTryStart(false, (Type) null);
        this.SP = savedSP;
    }

    public void emitWithCleanupCatch(Variable catchVar) {
        Type[] savedTypes;
        emitTryEnd();
        if (this.SP > 0) {
            savedTypes = new Type[this.SP];
            System.arraycopy(this.stack_types, 0, savedTypes, 0, this.SP);
            this.SP = 0;
        } else {
            savedTypes = null;
        }
        this.try_stack.savedTypes = savedTypes;
        this.try_stack.saved_result = catchVar;
        int i = this.SP;
        emitCatchStart(catchVar);
    }

    public void emitWithCleanupDone() {
        Variable catchVar = this.try_stack.saved_result;
        this.try_stack.saved_result = null;
        if (catchVar != null) {
            emitLoad(catchVar);
        }
        emitThrow();
        emitCatchEnd();
        Type[] savedTypes = this.try_stack.savedTypes;
        emitTryCatchEnd();
        if (savedTypes != null) {
            this.SP = savedTypes.length;
            if (this.SP >= this.stack_types.length) {
                this.stack_types = savedTypes;
            } else {
                System.arraycopy(savedTypes, 0, this.stack_types, 0, this.SP);
            }
        } else {
            this.SP = 0;
        }
    }

    public void emitTryStart(boolean has_finally, Type result_type) {
        Type[] startLocals;
        if (result_type != null && result_type.isVoid()) {
            result_type = null;
        }
        Variable[] savedStack = null;
        if (result_type != null || this.SP > 0) {
            pushScope();
        }
        if (this.SP > 0) {
            savedStack = new Variable[this.SP];
            int i = 0;
            while (this.SP > 0) {
                Variable var = addLocal(topType());
                emitStore(var);
                savedStack[i] = var;
                i++;
            }
        }
        TryState try_state = new TryState(this);
        try_state.savedStack = savedStack;
        int usedLocals = this.local_types == null ? 0 : this.local_types.length;
        while (usedLocals > 0 && this.local_types[usedLocals - 1] == null) {
            usedLocals--;
        }
        if (usedLocals == 0) {
            startLocals = Type.typeArray0;
        } else {
            Type[] startLocals2 = new Type[usedLocals];
            System.arraycopy(this.local_types, 0, startLocals2, 0, usedLocals);
            startLocals = startLocals2;
        }
        try_state.start_try.localTypes = startLocals;
        if (result_type != null) {
            try_state.saved_result = addLocal(result_type);
        }
        if (has_finally) {
            try_state.finally_subr = new Label();
        }
    }

    public void emitTryEnd() {
        emitTryEnd(false);
    }

    private void emitTryEnd(boolean fromFinally) {
        if (!this.try_stack.tryClauseDone) {
            this.try_stack.tryClauseDone = true;
            if (this.try_stack.finally_subr != null) {
                this.try_stack.exception = addLocal(Type.javalangThrowableType);
            }
            gotoFinallyOrEnd(fromFinally);
            this.try_stack.end_try = getLabel();
        }
    }

    public void emitCatchStart(Variable var) {
        emitTryEnd(false);
        setTypes(this.try_stack.start_try.localTypes, Type.typeArray0);
        if (this.try_stack.try_type != null) {
            emitCatchEnd();
        }
        ClassType type = var == null ? null : (ClassType) var.getType();
        this.try_stack.try_type = type;
        addHandler(this.try_stack.start_try, this.try_stack.end_try, type);
        if (var != null) {
            emitStore(var);
        }
    }

    public void emitCatchEnd() {
        gotoFinallyOrEnd(false);
        this.try_stack.try_type = null;
    }

    private void gotoFinallyOrEnd(boolean fromFinally) {
        if (reachableHere()) {
            if (this.try_stack.saved_result != null) {
                emitStore(this.try_stack.saved_result);
            }
            if (this.try_stack.end_label == null) {
                this.try_stack.end_label = new Label();
            }
            if (this.try_stack.finally_subr == null || useJsr()) {
                if (this.try_stack.finally_subr != null) {
                    emitJsr(this.try_stack.finally_subr);
                }
                emitGoto(this.try_stack.end_label);
                return;
            }
            if (this.try_stack.exitCases != null) {
                emitPushInt(0);
            }
            emitPushNull();
            if (!fromFinally) {
                emitGoto(this.try_stack.finally_subr);
            }
        }
    }

    public void emitFinallyStart() {
        emitTryEnd(true);
        if (this.try_stack.try_type != null) {
            emitCatchEnd();
        }
        this.try_stack.end_try = getLabel();
        pushScope();
        if (useJsr()) {
            this.SP = 0;
            emitCatchStart((Variable) null);
            emitStore(this.try_stack.exception);
            emitJsr(this.try_stack.finally_subr);
            emitLoad(this.try_stack.exception);
            emitThrow();
        } else {
            setUnreachable();
            int fragment_cookie = beginFragment(new Label(this));
            addHandler(this.try_stack.start_try, this.try_stack.end_try, Type.javalangThrowableType);
            if (this.try_stack.saved_result != null) {
                emitStoreDefaultValue(this.try_stack.saved_result);
            }
            if (this.try_stack.exitCases != null) {
                emitPushInt(-1);
                emitSwap();
            }
            emitGoto(this.try_stack.finally_subr);
            endFragment(fragment_cookie);
        }
        this.try_stack.finally_subr.define(this);
        if (useJsr()) {
            Type ret_addr_type = Type.objectType;
            this.try_stack.finally_ret_addr = addLocal(ret_addr_type);
            pushType(ret_addr_type);
            emitStore(this.try_stack.finally_ret_addr);
        }
    }

    public void emitFinallyEnd() {
        if (useJsr()) {
            emitRet(this.try_stack.finally_ret_addr);
        } else if (this.try_stack.end_label == null && this.try_stack.exitCases == null) {
            emitThrow();
        } else {
            emitStore(this.try_stack.exception);
            emitLoad(this.try_stack.exception);
            emitIfNotNull();
            emitLoad(this.try_stack.exception);
            emitThrow();
            emitElse();
            ExitableBlock exit = this.try_stack.exitCases;
            if (exit != null) {
                SwitchState sw = startSwitch();
                while (exit != null) {
                    ExitableBlock next = exit.nextCase;
                    exit.nextCase = null;
                    exit.currentTryState = null;
                    TryState nextTry = TryState.outerHandler(this.try_stack.previous, exit.initialTryState);
                    if (nextTry == exit.initialTryState) {
                        sw.addCaseGoto(exit.switchCase, this, exit.endLabel);
                    } else {
                        sw.addCase(exit.switchCase, this);
                        exit.exit(nextTry);
                    }
                    exit = next;
                }
                this.try_stack.exitCases = null;
                sw.addDefault(this);
                sw.finish(this);
            }
            emitFi();
            setUnreachable();
        }
        popScope();
        this.try_stack.finally_subr = null;
    }

    public void emitTryCatchEnd() {
        if (this.try_stack.finally_subr != null) {
            emitFinallyEnd();
        }
        Variable[] vars = this.try_stack.savedStack;
        if (this.try_stack.end_label == null) {
            setUnreachable();
        } else {
            setTypes(this.try_stack.start_try.localTypes, Type.typeArray0);
            this.try_stack.end_label.define(this);
            if (vars != null) {
                int i = vars.length;
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    }
                    Variable v = vars[i];
                    if (v != null) {
                        emitLoad(v);
                    }
                }
            }
            if (this.try_stack.saved_result != null) {
                emitLoad(this.try_stack.saved_result);
            }
        }
        if (!(this.try_stack.saved_result == null && vars == null)) {
            popScope();
        }
        this.try_stack = this.try_stack.previous;
    }

    public final TryState getCurrentTry() {
        return this.try_stack;
    }

    public final boolean isInTry() {
        return this.try_stack != null;
    }

    public SwitchState startSwitch() {
        SwitchState sw = new SwitchState(this);
        sw.switchValuePushed(this);
        return sw;
    }

    public void emitTailCall(boolean pop_args, Scope scope) {
        if (pop_args) {
            Method meth = getMethod();
            int arg_slots = (meth.access_flags & 8) != 0 ? 0 : 1;
            int i = meth.arg_types.length;
            while (true) {
                i--;
                int i2 = 2;
                if (i < 0) {
                    break;
                }
                if (meth.arg_types[i].size <= 4) {
                    i2 = 1;
                }
                arg_slots += i2;
            }
            int i3 = meth.arg_types.length;
            while (true) {
                i3--;
                if (i3 < 0) {
                    break;
                }
                arg_slots -= meth.arg_types[i3].size > 4 ? 2 : 1;
                emitStore(this.locals.used[arg_slots]);
            }
        }
        emitGoto(scope.start);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x022c, code lost:
        r4 = invert_opcode(r8);
        r8 = r11 + 1;
        r2[r11] = r4;
        r4 = r8 + 1;
        r2[r8] = r3;
        r11 = r4 + 1;
        r2[r4] = 8;
        r4 = com.google.appinventor.components.runtime.util.Ev3Constants.Opcode.READ8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x0241, code lost:
        r4 = (byte) (r8 + com.google.appinventor.components.runtime.util.Ev3Constants.Opcode.OR16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x0244, code lost:
        r8 = r11 + 1;
        r2[r11] = r4;
        r4 = r8 + 1;
        r2[r8] = (byte) (r5 >> 24);
        r8 = r4 + 1;
        r2[r4] = (byte) (r5 >> 16);
        r4 = r8 + 1;
        r2[r8] = (byte) (r5 >> 8);
        r2[r4] = (byte) (r5 & 255);
        r9 = r9 + 3;
        r11 = r4 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x026b, code lost:
        r4 = (3 - r11) & 3;
        r5 = r11 + 1;
        r14 = r9 + 1;
        r2[r11] = r0.code[r9];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x027a, code lost:
        r4 = r4 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x027c, code lost:
        if (r4 < 0) goto L_0x0284;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x027e, code lost:
        r2[r5] = r3;
        r5 = r5 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x0284, code lost:
        r9 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x0287, code lost:
        if (r10 >= r0.fixup_count) goto L_0x02d0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x0289, code lost:
        r4 = r10 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x0290, code lost:
        if (fixupKind(r4) != 3) goto L_0x02d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x0292, code lost:
        r10 = fixupOffset(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x0297, code lost:
        if (r9 >= r10) goto L_0x02a8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0299, code lost:
        r2[r5] = r0.code[r9];
        r5 = r5 + 1;
        r9 = r9 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x02a8, code lost:
        r3 = r0.fixup_labels[r4].position - r11;
        r10 = r5 + 1;
        r2[r5] = (byte) (r3 >> 24);
        r5 = r10 + 1;
        r2[r10] = (byte) (r3 >> 16);
        r10 = r5 + 1;
        r2[r5] = (byte) (r3 >> 8);
        r5 = r10 + 1;
        r2[r10] = (byte) (r3 & 255);
        r9 = r9 + 4;
        r10 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x02d1, code lost:
        r11 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x02d7, code lost:
        if (r0.stackMap == null) goto L_0x02eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0040, code lost:
        r7 = r5 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x02d9, code lost:
        if (r14 == null) goto L_0x02eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x02dd, code lost:
        if (r14.stackTypes == null) goto L_0x02eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x02e1, code lost:
        if (r14.needsStackMapEntry == false) goto L_0x02eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x02e3, code lost:
        r6 = mergeLabels(r6, r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x02eb, code lost:
        r10 = r10 + 1;
        r4 = fixupOffset(r10);
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x02f5, code lost:
        r5 = r10.first_fixup;
        r6 = (r7 + r6) - fixupOffset(r5);
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0044, code lost:
        if (r7 < r0.fixup_count) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0046, code lost:
        r7 = r0.PC;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x033f, code lost:
        r5 = r5 + 1;
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0049, code lost:
        r7 = fixupOffset(r0.fixup_labels[r7].first_fixup);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0053, code lost:
        r0.fixup_offsets[r5] = (r7 << 4) | 9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x005a, code lost:
        if (r10 != null) goto L_0x02f5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x005c, code lost:
        r1 = r0.PC;
        r2 = 0;
        r5 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0066, code lost:
        if (r2 >= r0.fixup_count) goto L_0x011d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0068, code lost:
        r6 = r0.fixup_offsets[r2];
        r10 = r6 & 15;
        r11 = r0.fixup_labels[r2];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0072, code lost:
        if (r11 == null) goto L_0x0092;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0076, code lost:
        if (r11.position < 0) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0091, code lost:
        throw new java.lang.Error("undefined label " + r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0093, code lost:
        if (r11 == null) goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0095, code lost:
        if (r10 < 4) goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0097, code lost:
        if (r10 > 7) goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009f, code lost:
        if ((r11.first_fixup + 1) >= r0.fixup_count) goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00b2, code lost:
        if (r0.fixup_offsets[r11.first_fixup + 1] != ((r0.fixup_offsets[r11.first_fixup] & 15) | 4)) goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b4, code lost:
        r11 = r0.fixup_labels[r11.first_fixup + 1];
        r0.fixup_labels[r2] = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00c2, code lost:
        r4 = r6 >> 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00c4, code lost:
        switch(r10) {
            case 0: goto L_0x0117;
            case 1: goto L_0x0113;
            case 2: goto L_0x010c;
            case 3: goto L_0x0117;
            case 4: goto L_0x00ee;
            case 5: goto L_0x00ee;
            case 6: goto L_0x00ee;
            case 7: goto L_0x00c7;
            case 8: goto L_0x00e9;
            case 9: goto L_0x00da;
            case 10: goto L_0x00c7;
            case 11: goto L_0x00d0;
            case 12: goto L_0x00c7;
            case 13: goto L_0x00c7;
            case 14: goto L_0x00cd;
            default: goto L_0x00c7;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00cc, code lost:
        throw new java.lang.Error("unexpected fixup");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00cd, code lost:
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00d0, code lost:
        r2 = r2 + 2;
        r0.fixup_labels[r2].position = r4 + r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00da, code lost:
        if (r11 != null) goto L_0x00dd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00dd, code lost:
        r2 = r11.first_fixup;
        r5 = (r4 + r5) - fixupOffset(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00e9, code lost:
        r5 = r5 - 3;
        r1 = r1 - 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ee, code lost:
        r6 = r11.position - (r4 + r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00f4, code lost:
        if (((short) r6) != r6) goto L_0x00fe;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00f6, code lost:
        r0.fixup_offsets[r2] = (r4 << 4) | 7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00fe, code lost:
        r4 = 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0100, code lost:
        if (r10 != 6) goto L_0x0104;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0102, code lost:
        r8 = 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0104, code lost:
        r8 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0105, code lost:
        r5 = r5 + r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0106, code lost:
        if (r10 != 6) goto L_0x0109;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0109, code lost:
        r4 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x010a, code lost:
        r1 = r1 + r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x010c, code lost:
        r4 = (3 - (r4 + r5)) & 3;
        r5 = r5 + r4;
        r1 = r1 + r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0113, code lost:
        r11.position = r4 + r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0118, code lost:
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x011d, code lost:
        r2 = new byte[r1];
        r4 = fixupOffset(r3);
        r6 = null;
        r9 = 0;
        r10 = 0;
        r11 = 0;
        r13 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x012d, code lost:
        if (r9 >= r4) goto L_0x013d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x012f, code lost:
        r2[r11] = r0.code[r9];
        r11 = r11 + 1;
        r9 = r9 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x013d, code lost:
        r4 = r0.fixup_offsets[r10] & 15;
        r14 = r0.fixup_labels[r10];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0147, code lost:
        if (r6 == null) goto L_0x0153;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x014b, code lost:
        if (r6.position >= r11) goto L_0x0153;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x014d, code lost:
        r0.stackMap.emitStackMapEntry(r6, r0);
        r6 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0153, code lost:
        if (r6 == null) goto L_0x0162;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0157, code lost:
        if (r6.position > r11) goto L_0x015a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0161, code lost:
        throw new java.lang.Error("labels out of order");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0162, code lost:
        switch(r4) {
            case 0: goto L_0x02e9;
            case 1: goto L_0x02d3;
            case 2: goto L_0x026b;
            case 3: goto L_0x0165;
            case 4: goto L_0x0223;
            case 5: goto L_0x0223;
            case 6: goto L_0x0223;
            case 7: goto L_0x0203;
            case 8: goto L_0x01fd;
            case 9: goto L_0x01b2;
            case 10: goto L_0x0165;
            case 11: goto L_0x0189;
            case 12: goto L_0x0165;
            case 13: goto L_0x0165;
            case 14: goto L_0x016b;
            default: goto L_0x0165;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x016a, code lost:
        throw new java.lang.Error("unexpected fixup");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x016d, code lost:
        if (r0.lines != null) goto L_0x0176;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x016f, code lost:
        r0.lines = new gnu.bytecode.LineNumbersAttr(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0176, code lost:
        r10 = r10 + 1;
        r4 = fixupOffset(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x017c, code lost:
        if (r4 == r13) goto L_0x0183;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x017e, code lost:
        r0.lines.put(r4, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0183, code lost:
        r13 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0189, code lost:
        r14 = r10 + 2;
        r4 = r0.fixup_labels[r14];
        r15 = r10 + 1;
        r5 = fixupOffset(r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0197, code lost:
        if (r0.stackMap == null) goto L_0x019d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0199, code lost:
        r6 = mergeLabels(r6, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x019d, code lost:
        addHandler(r0.fixup_labels[r10].position, r0.fixup_labels[r15].position, r11, r5);
        r10 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01b2, code lost:
        if (r14 != null) goto L_0x01e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01b5, code lost:
        if (r1 != r11) goto L_0x01c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01b7, code lost:
        r0.PC = r1;
        r0.code = r2;
        r0.fixup_count = r3;
        r0.fixup_labels = null;
        r0.fixup_offsets = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01c2, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x01e5, code lost:
        throw new java.lang.Error("PC confusion new_pc:" + r11 + " new_size:" + r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x01e6, code lost:
        r10 = r14.first_fixup;
        r9 = fixupOffset(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x01ef, code lost:
        if (r14.position != r11) goto L_0x01f5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x01f1, code lost:
        r4 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x01fc, code lost:
        throw new java.lang.Error("bad pc");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x01fd, code lost:
        r9 = r9 + 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0203, code lost:
        r4 = r14.position - r11;
        r5 = r11 + 1;
        r2[r11] = r0.code[r9];
        r8 = r5 + 1;
        r2[r5] = (byte) (r4 >> 8);
        r2[r8] = (byte) (r4 & 255);
        r9 = r9 + 3;
        r11 = r8 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0223, code lost:
        r5 = r14.position - r11;
        r8 = r0.code[r9];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x022a, code lost:
        if (r4 != 6) goto L_0x0241;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void processFixups() {
        /*
            r18 = this;
            r0 = r18
            int r1 = r0.fixup_count
            if (r1 > 0) goto L_0x0007
            return
        L_0x0007:
            int r1 = r0.fixup_count
            r2 = 9
            r3 = 0
            r4 = 0
            r0.fixupAdd(r2, r3, r4)
            r5 = 0
            r6 = 0
        L_0x0013:
            int[] r7 = r0.fixup_offsets
            r7 = r7[r5]
            r8 = r7 & 15
            r9 = 4
            int r7 = r7 >> r9
            gnu.bytecode.Label[] r10 = r0.fixup_labels
            r10 = r10[r5]
            r11 = 32768(0x8000, float:4.5918E-41)
            java.lang.String r12 = "unexpected fixup"
            switch(r8) {
                case 0: goto L_0x033e;
                case 1: goto L_0x0338;
                case 2: goto L_0x0335;
                case 3: goto L_0x033e;
                case 4: goto L_0x030e;
                case 5: goto L_0x030c;
                case 6: goto L_0x0302;
                case 7: goto L_0x0027;
                case 8: goto L_0x033e;
                case 9: goto L_0x0040;
                case 10: goto L_0x0035;
                case 11: goto L_0x0031;
                case 12: goto L_0x0027;
                case 13: goto L_0x0027;
                case 14: goto L_0x002d;
                default: goto L_0x0027;
            }
        L_0x0027:
            java.lang.Error r1 = new java.lang.Error
            r1.<init>(r12)
            throw r1
        L_0x002d:
            int r5 = r5 + 1
            goto L_0x033e
        L_0x0031:
            int r5 = r5 + 2
            goto L_0x033f
        L_0x0035:
            gnu.bytecode.Label[] r8 = r0.fixup_labels
            gnu.bytecode.Label[] r11 = r0.fixup_labels
            int r14 = r5 + 1
            r11 = r11[r14]
            r8[r1] = r11
            r1 = r7
        L_0x0040:
            int r7 = r5 + 1
            int r8 = r0.fixup_count
            if (r7 < r8) goto L_0x0049
            int r7 = r0.PC
            goto L_0x0053
        L_0x0049:
            gnu.bytecode.Label[] r8 = r0.fixup_labels
            r7 = r8[r7]
            int r7 = r7.first_fixup
            int r7 = r0.fixupOffset(r7)
        L_0x0053:
            int[] r8 = r0.fixup_offsets
            int r11 = r7 << 4
            r11 = r11 | r2
            r8[r5] = r11
            if (r10 != 0) goto L_0x02f5
            int r1 = r0.PC
            r2 = 0
            r5 = 0
        L_0x0062:
            int r6 = r0.fixup_count
            r7 = 6
            r8 = 3
            if (r2 >= r6) goto L_0x011d
            int[] r6 = r0.fixup_offsets
            r6 = r6[r2]
            r10 = r6 & 15
            gnu.bytecode.Label[] r11 = r0.fixup_labels
            r11 = r11[r2]
            if (r11 == 0) goto L_0x0092
            int r14 = r11.position
            if (r14 < 0) goto L_0x0079
            goto L_0x0092
        L_0x0079:
            java.lang.Error r1 = new java.lang.Error
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "undefined label "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r11)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0092:
            r14 = 7
            if (r11 == 0) goto L_0x00c2
            if (r10 < r9) goto L_0x00c2
            if (r10 > r14) goto L_0x00c2
            int r15 = r11.first_fixup
            int r15 = r15 + 1
            int r13 = r0.fixup_count
            if (r15 >= r13) goto L_0x00c2
            int[] r13 = r0.fixup_offsets
            int r15 = r11.first_fixup
            int r15 = r15 + 1
            r13 = r13[r15]
            int[] r15 = r0.fixup_offsets
            int r4 = r11.first_fixup
            r4 = r15[r4]
            r4 = r4 & 15
            r4 = r4 | r9
            if (r13 != r4) goto L_0x00c2
            gnu.bytecode.Label[] r4 = r0.fixup_labels
            int r11 = r11.first_fixup
            int r11 = r11 + 1
            r11 = r4[r11]
            gnu.bytecode.Label[] r4 = r0.fixup_labels
            r4[r2] = r11
            r4 = 0
            goto L_0x0092
        L_0x00c2:
            int r4 = r6 >> 4
            switch(r10) {
                case 0: goto L_0x0117;
                case 1: goto L_0x0113;
                case 2: goto L_0x010c;
                case 3: goto L_0x0117;
                case 4: goto L_0x00ee;
                case 5: goto L_0x00ee;
                case 6: goto L_0x00ee;
                case 7: goto L_0x00c7;
                case 8: goto L_0x00e9;
                case 9: goto L_0x00da;
                case 10: goto L_0x00c7;
                case 11: goto L_0x00d0;
                case 12: goto L_0x00c7;
                case 13: goto L_0x00c7;
                case 14: goto L_0x00cd;
                default: goto L_0x00c7;
            }
        L_0x00c7:
            java.lang.Error r1 = new java.lang.Error
            r1.<init>(r12)
            throw r1
        L_0x00cd:
            int r2 = r2 + 1
            goto L_0x0117
        L_0x00d0:
            int r2 = r2 + 2
            gnu.bytecode.Label[] r6 = r0.fixup_labels
            r6 = r6[r2]
            int r4 = r4 + r5
            r6.position = r4
            goto L_0x0118
        L_0x00da:
            if (r11 != 0) goto L_0x00dd
            goto L_0x011d
        L_0x00dd:
            int r2 = r11.first_fixup
            int r6 = r0.fixupOffset(r2)
            int r4 = r4 + r5
            int r5 = r4 - r6
            r4 = 0
            goto L_0x0062
        L_0x00e9:
            int r5 = r5 + -3
            int r1 = r1 + -3
            goto L_0x0118
        L_0x00ee:
            int r6 = r11.position
            int r8 = r4 + r5
            int r6 = r6 - r8
            short r8 = (short) r6
            if (r8 != r6) goto L_0x00fe
            int[] r6 = r0.fixup_offsets
            int r4 = r4 << 4
            r4 = r4 | r14
            r6[r2] = r4
            goto L_0x0118
        L_0x00fe:
            r4 = 5
            r6 = 2
            if (r10 != r7) goto L_0x0104
            r8 = 5
            goto L_0x0105
        L_0x0104:
            r8 = 2
        L_0x0105:
            int r5 = r5 + r8
            if (r10 != r7) goto L_0x0109
            goto L_0x010a
        L_0x0109:
            r4 = 2
        L_0x010a:
            int r1 = r1 + r4
            goto L_0x0118
        L_0x010c:
            int r4 = r4 + r5
            int r4 = 3 - r4
            r4 = r4 & r8
            int r5 = r5 + r4
            int r1 = r1 + r4
            goto L_0x0118
        L_0x0113:
            int r4 = r4 + r5
            r11.position = r4
            goto L_0x0118
        L_0x0117:
        L_0x0118:
            int r2 = r2 + 1
            r4 = 0
            goto L_0x0062
        L_0x011d:
            byte[] r2 = new byte[r1]
            int r4 = r0.fixupOffset(r3)
            r6 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r13 = -1
        L_0x012d:
            if (r9 >= r4) goto L_0x013d
            int r14 = r11 + 1
            byte[] r15 = r0.code
            int r16 = r9 + 1
            byte r9 = r15[r9]
            r2[r11] = r9
            r11 = r14
            r9 = r16
            goto L_0x012d
        L_0x013d:
            int[] r4 = r0.fixup_offsets
            r4 = r4[r10]
            r4 = r4 & 15
            gnu.bytecode.Label[] r14 = r0.fixup_labels
            r14 = r14[r10]
            if (r6 == 0) goto L_0x0153
            int r15 = r6.position
            if (r15 >= r11) goto L_0x0153
            gnu.bytecode.StackMapTableAttr r15 = r0.stackMap
            r15.emitStackMapEntry(r6, r0)
            r6 = 0
        L_0x0153:
            if (r6 == 0) goto L_0x0162
            int r15 = r6.position
            if (r15 > r11) goto L_0x015a
            goto L_0x0162
        L_0x015a:
            java.lang.Error r1 = new java.lang.Error
            java.lang.String r2 = "labels out of order"
            r1.<init>(r2)
            throw r1
        L_0x0162:
            switch(r4) {
                case 0: goto L_0x02e9;
                case 1: goto L_0x02d3;
                case 2: goto L_0x026b;
                case 3: goto L_0x0165;
                case 4: goto L_0x0223;
                case 5: goto L_0x0223;
                case 6: goto L_0x0223;
                case 7: goto L_0x0203;
                case 8: goto L_0x01fd;
                case 9: goto L_0x01b2;
                case 10: goto L_0x0165;
                case 11: goto L_0x0189;
                case 12: goto L_0x0165;
                case 13: goto L_0x0165;
                case 14: goto L_0x016b;
                default: goto L_0x0165;
            }
        L_0x0165:
            java.lang.Error r1 = new java.lang.Error
            r1.<init>(r12)
            throw r1
        L_0x016b:
            gnu.bytecode.LineNumbersAttr r4 = r0.lines
            if (r4 != 0) goto L_0x0176
            gnu.bytecode.LineNumbersAttr r4 = new gnu.bytecode.LineNumbersAttr
            r4.<init>(r0)
            r0.lines = r4
        L_0x0176:
            int r10 = r10 + 1
            int r4 = r0.fixupOffset(r10)
            if (r4 == r13) goto L_0x0183
            gnu.bytecode.LineNumbersAttr r13 = r0.lines
            r13.put(r4, r11)
        L_0x0183:
            r13 = r4
            r8 = -1
            r15 = 3
            goto L_0x02eb
        L_0x0189:
            gnu.bytecode.Label[] r4 = r0.fixup_labels
            int r14 = r10 + 2
            r4 = r4[r14]
            int r15 = r10 + 1
            int r5 = r0.fixupOffset(r15)
            gnu.bytecode.StackMapTableAttr r8 = r0.stackMap
            if (r8 == 0) goto L_0x019d
            gnu.bytecode.Label r6 = r0.mergeLabels(r6, r4)
        L_0x019d:
            gnu.bytecode.Label[] r4 = r0.fixup_labels
            r4 = r4[r10]
            int r4 = r4.position
            gnu.bytecode.Label[] r8 = r0.fixup_labels
            r8 = r8[r15]
            int r8 = r8.position
            r0.addHandler(r4, r8, r11, r5)
            r10 = r14
            r8 = -1
            r15 = 3
            goto L_0x02eb
        L_0x01b2:
            if (r14 != 0) goto L_0x01e6
            if (r1 != r11) goto L_0x01c3
            r0.PC = r1
            r0.code = r2
            r0.fixup_count = r3
            r1 = 0
            r0.fixup_labels = r1
            r0.fixup_offsets = r1
            return
        L_0x01c3:
            java.lang.Error r2 = new java.lang.Error
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "PC confusion new_pc:"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r11)
            java.lang.String r4 = " new_size:"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.String r1 = r1.toString()
            r2.<init>(r1)
            throw r2
        L_0x01e6:
            int r10 = r14.first_fixup
            int r9 = r0.fixupOffset(r10)
            int r4 = r14.position
            if (r4 != r11) goto L_0x01f5
            r4 = r9
            r8 = 3
            goto L_0x012d
        L_0x01f5:
            java.lang.Error r1 = new java.lang.Error
            java.lang.String r2 = "bad pc"
            r1.<init>(r2)
            throw r1
        L_0x01fd:
            int r9 = r9 + 3
            r8 = -1
            r15 = 3
            goto L_0x02eb
        L_0x0203:
            int r4 = r14.position
            int r4 = r4 - r11
            int r5 = r11 + 1
            byte[] r8 = r0.code
            byte r8 = r8[r9]
            r2[r11] = r8
            int r8 = r5 + 1
            int r11 = r4 >> 8
            byte r11 = (byte) r11
            r2[r5] = r11
            int r5 = r8 + 1
            r4 = r4 & 255(0xff, float:3.57E-43)
            byte r4 = (byte) r4
            r2[r8] = r4
            int r9 = r9 + 3
            r11 = r5
            r8 = -1
            r15 = 3
            goto L_0x02eb
        L_0x0223:
            int r5 = r14.position
            int r5 = r5 - r11
            byte[] r8 = r0.code
            byte r8 = r8[r9]
            if (r4 != r7) goto L_0x0241
            byte r4 = r0.invert_opcode(r8)
            int r8 = r11 + 1
            r2[r11] = r4
            int r4 = r8 + 1
            r2[r8] = r3
            int r11 = r4 + 1
            r8 = 8
            r2[r4] = r8
            r4 = -56
            goto L_0x0244
        L_0x0241:
            int r8 = r8 + 33
            byte r4 = (byte) r8
        L_0x0244:
            int r8 = r11 + 1
            r2[r11] = r4
            int r4 = r8 + 1
            int r11 = r5 >> 24
            byte r11 = (byte) r11
            r2[r8] = r11
            int r8 = r4 + 1
            int r11 = r5 >> 16
            byte r11 = (byte) r11
            r2[r4] = r11
            int r4 = r8 + 1
            int r11 = r5 >> 8
            byte r11 = (byte) r11
            r2[r8] = r11
            int r8 = r4 + 1
            r5 = r5 & 255(0xff, float:3.57E-43)
            byte r5 = (byte) r5
            r2[r4] = r5
            int r9 = r9 + 3
            r11 = r8
            r8 = -1
            r15 = 3
            goto L_0x02eb
        L_0x026b:
            int r4 = 3 - r11
            r5 = 3
            r4 = r4 & r5
            int r5 = r11 + 1
            byte[] r8 = r0.code
            int r14 = r9 + 1
            byte r8 = r8[r9]
            r2[r11] = r8
        L_0x027a:
            r8 = -1
            int r4 = r4 + r8
            if (r4 < 0) goto L_0x0284
            int r9 = r5 + 1
            r2[r5] = r3
            r5 = r9
            goto L_0x027a
        L_0x0284:
            r9 = r14
        L_0x0285:
            int r4 = r0.fixup_count
            if (r10 >= r4) goto L_0x02d0
            int r4 = r10 + 1
            int r14 = r0.fixupKind(r4)
            r15 = 3
            if (r14 != r15) goto L_0x02d1
            int r10 = r0.fixupOffset(r4)
        L_0x0297:
            if (r9 >= r10) goto L_0x02a8
            int r14 = r5 + 1
            byte[] r3 = r0.code
            int r17 = r9 + 1
            byte r3 = r3[r9]
            r2[r5] = r3
            r5 = r14
            r9 = r17
            r3 = 0
            goto L_0x0297
        L_0x02a8:
            gnu.bytecode.Label[] r3 = r0.fixup_labels
            r3 = r3[r4]
            int r3 = r3.position
            int r3 = r3 - r11
            int r10 = r5 + 1
            int r14 = r3 >> 24
            byte r14 = (byte) r14
            r2[r5] = r14
            int r5 = r10 + 1
            int r14 = r3 >> 16
            byte r14 = (byte) r14
            r2[r10] = r14
            int r10 = r5 + 1
            int r14 = r3 >> 8
            byte r14 = (byte) r14
            r2[r5] = r14
            int r5 = r10 + 1
            r3 = r3 & 255(0xff, float:3.57E-43)
            byte r3 = (byte) r3
            r2[r10] = r3
            int r9 = r9 + 4
            r10 = r4
            r3 = 0
            goto L_0x0285
        L_0x02d0:
            r15 = 3
        L_0x02d1:
            r11 = r5
            goto L_0x02eb
        L_0x02d3:
            r8 = -1
            r15 = 3
            gnu.bytecode.StackMapTableAttr r3 = r0.stackMap
            if (r3 == 0) goto L_0x02eb
            if (r14 == 0) goto L_0x02eb
            gnu.bytecode.Type[] r3 = r14.stackTypes
            if (r3 == 0) goto L_0x02eb
            boolean r3 = r14.needsStackMapEntry
            if (r3 == 0) goto L_0x02eb
            gnu.bytecode.Label r3 = r0.mergeLabels(r6, r14)
            r6 = r3
            goto L_0x02eb
        L_0x02e9:
            r8 = -1
            r15 = 3
        L_0x02eb:
            int r10 = r10 + 1
            int r4 = r0.fixupOffset(r10)
            r3 = 0
            r8 = 3
            goto L_0x012d
        L_0x02f5:
            int r5 = r10.first_fixup
            int r3 = r0.fixupOffset(r5)
            int r7 = r7 + r6
            int r6 = r7 - r3
            r3 = 0
            r4 = 0
            goto L_0x0013
        L_0x0302:
            int r3 = r0.PC
            if (r3 < r11) goto L_0x030a
            int r6 = r6 + 5
            r4 = 0
            goto L_0x033f
        L_0x030a:
            r4 = 0
            goto L_0x033f
        L_0x030c:
            r4 = 0
            goto L_0x032e
        L_0x030e:
            int r3 = r10.first_fixup
            int r4 = r5 + 1
            if (r3 != r4) goto L_0x032d
            int r3 = r0.fixupOffset(r4)
            int r4 = r7 + 3
            if (r3 != r4) goto L_0x032d
            int[] r3 = r0.fixup_offsets
            int r4 = r7 << 4
            r7 = 8
            r4 = r4 | r7
            r3[r5] = r4
            gnu.bytecode.Label[] r3 = r0.fixup_labels
            r4 = 0
            r3[r5] = r4
            int r6 = r6 + -3
            goto L_0x033f
        L_0x032d:
            r4 = 0
        L_0x032e:
            int r3 = r0.PC
            if (r3 < r11) goto L_0x033f
            int r6 = r6 + 2
            goto L_0x033f
        L_0x0335:
            int r6 = r6 + 3
            goto L_0x033f
        L_0x0338:
            int r3 = r10.position
            int r3 = r3 + r6
            r10.position = r3
            goto L_0x033f
        L_0x033e:
        L_0x033f:
            int r5 = r5 + 1
            r3 = 0
            goto L_0x0013
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.CodeAttr.processFixups():void");
    }

    private Label mergeLabels(Label oldLabel, Label newLabel) {
        if (oldLabel != null) {
            newLabel.setTypes(oldLabel);
        }
        return newLabel;
    }

    public void assignConstants(ClassType cl) {
        if (this.locals != null && this.locals.container == null && !this.locals.isEmpty()) {
            this.locals.addToFrontOf(this);
        }
        processFixups();
        if (this.stackMap != null && this.stackMap.numEntries > 0) {
            this.stackMap.addToFrontOf(this);
        }
        if (instructionLineMode) {
            if (this.lines == null) {
                this.lines = new LineNumbersAttr(this);
            }
            this.lines.linenumber_count = 0;
            int codeLen = getCodeLength();
            for (int i = 0; i < codeLen; i++) {
                this.lines.put(i, i);
            }
        }
        super.assignConstants(cl);
        Attribute.assignConstants(this, cl);
    }

    public final int getLength() {
        return getCodeLength() + 12 + (this.exception_table_length * 8) + Attribute.getLengthAll(this);
    }

    public void write(DataOutputStream dstr) throws IOException {
        dstr.writeShort(this.max_stack);
        dstr.writeShort(this.max_locals);
        dstr.writeInt(this.PC);
        dstr.write(this.code, 0, this.PC);
        dstr.writeShort(this.exception_table_length);
        int count = this.exception_table_length;
        int i = 0;
        while (true) {
            count--;
            if (count >= 0) {
                dstr.writeShort(this.exception_table[i]);
                dstr.writeShort(this.exception_table[i + 1]);
                dstr.writeShort(this.exception_table[i + 2]);
                dstr.writeShort(this.exception_table[i + 3]);
                i += 4;
            } else {
                Attribute.writeAll(this, dstr);
                return;
            }
        }
    }

    public void print(ClassTypeWriter dst) {
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.print(getLength());
        dst.print(", max_stack:");
        dst.print(this.max_stack);
        dst.print(", max_locals:");
        dst.print(this.max_locals);
        dst.print(", code_length:");
        int length = getCodeLength();
        dst.println(length);
        disAssemble(dst, 0, length);
        if (this.exception_table_length > 0) {
            dst.print("Exceptions (count: ");
            dst.print(this.exception_table_length);
            dst.println("):");
            int count = this.exception_table_length;
            int i = 0;
            while (true) {
                count--;
                if (count < 0) {
                    break;
                }
                dst.print("  start: ");
                dst.print(this.exception_table[i] & 65535);
                dst.print(", end: ");
                dst.print(this.exception_table[i + 1] & 65535);
                dst.print(", handler: ");
                dst.print(this.exception_table[i + 2] & 65535);
                dst.print(", type: ");
                int catch_type_index = this.exception_table[i + 3] & SupportMenu.USER_MASK;
                if (catch_type_index == 0) {
                    dst.print("0 /* finally */");
                } else {
                    dst.printOptionalIndex(catch_type_index);
                    dst.printConstantTersely(catch_type_index, 7);
                }
                dst.println();
                i += 4;
            }
        }
        dst.printAttributes(this);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r10v68, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r5v46, types: [byte] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void disAssemble(gnu.bytecode.ClassTypeWriter r18, int r19, int r20) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = 0
            r3 = r19
        L_0x0007:
            r4 = r20
            if (r3 >= r4) goto L_0x03e4
            int r5 = r3 + 1
            byte[] r6 = r0.code
            byte r6 = r6[r3]
            r6 = r6 & 255(0xff, float:3.57E-43)
            java.lang.String r7 = java.lang.Integer.toString(r3)
            r8 = 0
            int r9 = r7.length()
        L_0x001c:
            r10 = 1
            int r9 = r9 + r10
            r11 = 3
            r12 = 32
            if (r9 > r11) goto L_0x0027
            r1.print(r12)
            goto L_0x001c
        L_0x0027:
            r1.print(r7)
            java.lang.String r13 = ": "
            r1.print(r13)
            java.lang.String r14 = "ilfda"
            r15 = 120(0x78, float:1.68E-43)
            r10 = 4
            r16 = 2
            if (r6 >= r15) goto L_0x0131
            r13 = 87
            if (r6 >= r13) goto L_0x010f
            if (r6 >= r11) goto L_0x0045
            java.lang.String r10 = "nop;aconst_null;iconst_m1;"
            r0.print(r10, r6, r1)
            goto L_0x03c5
        L_0x0045:
            r10 = 9
            if (r6 >= r10) goto L_0x0055
            java.lang.String r10 = "iconst_"
            r1.print(r10)
            int r10 = r6 + -3
            r1.print(r10)
            goto L_0x03c5
        L_0x0055:
            r10 = 16
            if (r6 >= r10) goto L_0x007c
            r10 = 11
            if (r6 >= r10) goto L_0x0062
            r10 = 108(0x6c, float:1.51E-43)
            int r6 = r6 + -9
            goto L_0x006f
        L_0x0062:
            r10 = 14
            if (r6 >= r10) goto L_0x006b
            r10 = 102(0x66, float:1.43E-43)
            int r6 = r6 + -11
            goto L_0x006f
        L_0x006b:
            r10 = 100
            int r6 = r6 + -14
        L_0x006f:
            r1.print(r10)
            java.lang.String r11 = "const_"
            r1.print(r11)
            r1.print(r6)
            goto L_0x03c5
        L_0x007c:
            r11 = 21
            if (r6 >= r11) goto L_0x00b2
            r11 = 18
            if (r6 >= r11) goto L_0x00a3
            int r11 = r6 + -16
            java.lang.String r12 = "bipush ;sipush ;"
            r0.print(r12, r11, r1)
            if (r6 != r10) goto L_0x0094
            byte[] r10 = r0.code
            int r11 = r5 + 1
            byte r5 = r10[r5]
            goto L_0x009d
        L_0x0094:
            int r10 = r0.readUnsignedShort(r5)
            short r10 = (short) r10
            int r5 = r5 + 2
            r11 = r5
            r5 = r10
        L_0x009d:
            r1.print(r5)
            r5 = r11
            goto L_0x03c5
        L_0x00a3:
            if (r6 != r11) goto L_0x00a7
            r16 = 1
        L_0x00a7:
            r8 = r16
            int r10 = r6 + -18
            java.lang.String r11 = "ldc;ldc_w;ldc2_w;"
            r0.print(r11, r10, r1)
            goto L_0x03c5
        L_0x00b2:
            r10 = 54
            if (r6 >= r10) goto L_0x00b9
            java.lang.String r10 = "load"
            goto L_0x00bd
        L_0x00b9:
            java.lang.String r10 = "store"
            int r6 = r6 + -33
        L_0x00bd:
            r11 = 26
            if (r6 >= r11) goto L_0x00c5
            r11 = -1
            int r6 = r6 + -21
            goto L_0x00d3
        L_0x00c5:
            r11 = 46
            if (r6 >= r11) goto L_0x00d0
            int r6 = r6 + -26
            int r11 = r6 % 4
            int r6 = r6 >> 2
            goto L_0x00d3
        L_0x00d0:
            r11 = -2
            int r6 = r6 + -46
        L_0x00d3:
            java.lang.String r13 = "ilfdabcs"
            char r13 = r13.charAt(r6)
            r1.print(r13)
            r13 = -2
            if (r11 != r13) goto L_0x00e4
            r13 = 97
            r1.write(r13)
        L_0x00e4:
            r1.print(r10)
            if (r11 < 0) goto L_0x00f2
            r12 = 95
            r1.write(r12)
            r1.print(r11)
            goto L_0x010d
        L_0x00f2:
            r13 = -1
            if (r11 != r13) goto L_0x010d
            if (r2 == 0) goto L_0x00fe
            int r11 = r0.readUnsignedShort(r5)
            int r5 = r5 + 2
            goto L_0x0106
        L_0x00fe:
            byte[] r13 = r0.code
            byte r13 = r13[r5]
            r11 = r13 & 255(0xff, float:3.57E-43)
            int r5 = r5 + 1
        L_0x0106:
            r2 = 0
            r1.print(r12)
            r1.print(r11)
        L_0x010d:
            goto L_0x03c5
        L_0x010f:
            r11 = 96
            if (r6 >= r11) goto L_0x011c
            int r10 = r6 + -87
            java.lang.String r11 = "pop;pop2;dup;dup_x1;dup_x2;dup2;dup2_x1;dup2_x2;swap;"
            r0.print(r11, r10, r1)
            goto L_0x03c5
        L_0x011c:
            int r11 = r6 + -96
            int r11 = r11 % r10
            char r10 = r14.charAt(r11)
            r1.print(r10)
            int r10 = r6 + -96
            int r10 = r10 >> 2
            java.lang.String r11 = "add;sub;mul;div;rem;neg;"
            r0.print(r11, r10, r1)
            goto L_0x03c5
        L_0x0131:
            r15 = 170(0xaa, float:2.38E-43)
            if (r6 >= r15) goto L_0x021d
            r10 = 132(0x84, float:1.85E-43)
            if (r6 >= r10) goto L_0x0150
            r10 = r6 & 1
            if (r10 != 0) goto L_0x0140
            r10 = 105(0x69, float:1.47E-43)
            goto L_0x0142
        L_0x0140:
            r10 = 108(0x6c, float:1.51E-43)
        L_0x0142:
            r1.print(r10)
            int r10 = r6 + -120
            r11 = 1
            int r10 = r10 >> r11
            java.lang.String r11 = "shl;shr;ushr;and;or;xor;"
            r0.print(r11, r10, r1)
            goto L_0x03c5
        L_0x0150:
            if (r6 != r10) goto L_0x0188
            java.lang.String r10 = "iinc"
            r1.print(r10)
            if (r2 != 0) goto L_0x0168
            byte[] r10 = r0.code
            int r11 = r5 + 1
            byte r5 = r10[r5]
            r5 = r5 & 255(0xff, float:3.57E-43)
            byte[] r10 = r0.code
            int r13 = r11 + 1
            byte r10 = r10[r11]
            goto L_0x0179
        L_0x0168:
            int r10 = r0.readUnsignedShort(r5)
            int r5 = r5 + 2
            int r11 = r0.readUnsignedShort(r5)
            short r11 = (short) r11
            int r5 = r5 + 2
            r2 = 0
            r13 = r5
            r5 = r10
            r10 = r11
        L_0x0179:
            r1.print(r12)
            r1.print(r5)
            r1.print(r12)
            r1.print(r10)
            r5 = r13
            goto L_0x03c5
        L_0x0188:
            r10 = 148(0x94, float:2.07E-43)
            if (r6 >= r10) goto L_0x01aa
            int r10 = r6 + -133
            int r10 = r10 / r11
            java.lang.String r11 = "ilfdi"
            char r10 = r11.charAt(r10)
            r1.print(r10)
            r10 = 50
            r1.print(r10)
            int r10 = r6 + -133
            java.lang.String r11 = "lfdifdildilfbcs"
            char r10 = r11.charAt(r10)
            r1.print(r10)
            goto L_0x03c5
        L_0x01aa:
            r10 = 153(0x99, float:2.14E-43)
            if (r6 >= r10) goto L_0x01b7
            int r10 = r6 + -148
            java.lang.String r11 = "lcmp;fcmpl;fcmpg;dcmpl;dcmpg;"
            r0.print(r11, r10, r1)
            goto L_0x03c5
        L_0x01b7:
            r10 = 169(0xa9, float:2.37E-43)
            if (r6 >= r10) goto L_0x0201
            r10 = 159(0x9f, float:2.23E-43)
            if (r6 >= r10) goto L_0x01cc
            java.lang.String r10 = "if"
            r1.print(r10)
            int r10 = r6 + -153
            java.lang.String r11 = "eq;ne;lt;ge;gt;le;"
            r0.print(r11, r10, r1)
            goto L_0x01f0
        L_0x01cc:
            r10 = 167(0xa7, float:2.34E-43)
            if (r6 >= r10) goto L_0x01e9
            r10 = 165(0xa5, float:2.31E-43)
            if (r6 >= r10) goto L_0x01da
            java.lang.String r10 = "if_icmp"
            r1.print(r10)
            goto L_0x01e1
        L_0x01da:
            java.lang.String r10 = "if_acmp"
            r1.print(r10)
            int r6 = r6 + -6
        L_0x01e1:
            int r10 = r6 + -159
            java.lang.String r11 = "eq;ne;lt;ge;gt;le;"
            r0.print(r11, r10, r1)
            goto L_0x01f0
        L_0x01e9:
            int r10 = r6 + -167
            java.lang.String r11 = "goto;jsr;"
            r0.print(r11, r10, r1)
        L_0x01f0:
            int r10 = r0.readUnsignedShort(r5)
            short r10 = (short) r10
            int r5 = r5 + 2
            r1.print(r12)
            int r11 = r3 + r10
            r1.print(r11)
            goto L_0x03c5
        L_0x0201:
            java.lang.String r10 = "ret "
            r1.print(r10)
            if (r2 == 0) goto L_0x020f
            int r10 = r0.readUnsignedShort(r5)
            int r10 = r10 + 2
            goto L_0x0217
        L_0x020f:
            byte[] r10 = r0.code
            byte r10 = r10[r5]
            r10 = r10 & 255(0xff, float:3.57E-43)
            int r5 = r5 + 1
        L_0x0217:
            r2 = 0
            r1.print(r10)
            goto L_0x03c5
        L_0x021d:
            r11 = 172(0xac, float:2.41E-43)
            if (r6 >= r11) goto L_0x02b9
            int r11 = r0.fixup_count
            if (r11 > 0) goto L_0x0229
            int r11 = r5 + 3
            r5 = r11 & -4
        L_0x0229:
            int r11 = r0.readInt(r5)
            int r5 = r5 + r10
            java.lang.String r12 = " default: "
            if (r6 != r15) goto L_0x0279
            java.lang.String r14 = "tableswitch"
            r1.print(r14)
            int r14 = r0.readInt(r5)
            int r5 = r5 + 4
            int r15 = r0.readInt(r5)
            int r5 = r5 + r10
            java.lang.String r10 = " low: "
            r1.print(r10)
            r1.print(r14)
            java.lang.String r10 = " high: "
            r1.print(r10)
            r1.print(r15)
            r1.print(r12)
            int r10 = r3 + r11
            r1.print(r10)
        L_0x025a:
            if (r14 > r15) goto L_0x0278
            int r11 = r0.readInt(r5)
            int r5 = r5 + 4
            r18.println()
            java.lang.String r10 = "  "
            r1.print(r10)
            r1.print(r14)
            r1.print(r13)
            int r10 = r3 + r11
            r1.print(r10)
            int r14 = r14 + 1
            goto L_0x025a
        L_0x0278:
            goto L_0x02b7
        L_0x0279:
            java.lang.String r14 = "lookupswitch"
            r1.print(r14)
            int r14 = r0.readInt(r5)
            int r5 = r5 + 4
            java.lang.String r15 = " npairs: "
            r1.print(r15)
            r1.print(r14)
            r1.print(r12)
            int r12 = r3 + r11
            r1.print(r12)
        L_0x0294:
            int r14 = r14 + -1
            if (r14 < 0) goto L_0x02b7
            int r12 = r0.readInt(r5)
            int r5 = r5 + 4
            int r11 = r0.readInt(r5)
            int r5 = r5 + r10
            r18.println()
            java.lang.String r15 = "  "
            r1.print(r15)
            r1.print(r12)
            r1.print(r13)
            int r15 = r3 + r11
            r1.print(r15)
            goto L_0x0294
        L_0x02b7:
            goto L_0x03c5
        L_0x02b9:
            r11 = 178(0xb2, float:2.5E-43)
            if (r6 >= r11) goto L_0x02d1
            r10 = 177(0xb1, float:2.48E-43)
            if (r6 >= r10) goto L_0x02ca
            int r10 = r6 + -172
            char r10 = r14.charAt(r10)
            r1.print(r10)
        L_0x02ca:
            java.lang.String r10 = "return"
            r1.print(r10)
            goto L_0x03c5
        L_0x02d1:
            r11 = 182(0xb6, float:2.55E-43)
            if (r6 >= r11) goto L_0x02df
            int r10 = r6 + -178
            java.lang.String r11 = "getstatic;putstatic;getfield;putfield;"
            r0.print(r11, r10, r1)
            r8 = 2
            goto L_0x03c5
        L_0x02df:
            r11 = 185(0xb9, float:2.59E-43)
            if (r6 >= r11) goto L_0x02f2
            java.lang.String r10 = "invoke"
            r1.print(r10)
            int r10 = r6 + -182
            java.lang.String r11 = "virtual;special;static;"
            r0.print(r11, r10, r1)
            r8 = 2
            goto L_0x03c5
        L_0x02f2:
            if (r6 != r11) goto L_0x0322
            java.lang.String r10 = "invokeinterface ("
            r1.print(r10)
            int r10 = r0.readUnsignedShort(r5)
            int r5 = r5 + 2
            byte[] r11 = r0.code
            byte r11 = r11[r5]
            r11 = r11 & 255(0xff, float:3.57E-43)
            int r5 = r5 + 2
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.StringBuilder r12 = r12.append(r11)
            java.lang.String r13 = " args)"
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r12 = r12.toString()
            r1.print(r12)
            r1.printConstantOperand(r10)
            goto L_0x03c5
        L_0x0322:
            r11 = 196(0xc4, float:2.75E-43)
            if (r6 >= r11) goto L_0x0360
            int r11 = r6 + -186
            java.lang.String r13 = "186;new;newarray;anewarray;arraylength;athrow;checkcast;instanceof;monitorenter;monitorexit;"
            r0.print(r13, r11, r1)
            r11 = 187(0xbb, float:2.62E-43)
            if (r6 == r11) goto L_0x035e
            r11 = 189(0xbd, float:2.65E-43)
            if (r6 == r11) goto L_0x035e
            r11 = 192(0xc0, float:2.69E-43)
            if (r6 == r11) goto L_0x035e
            r11 = 193(0xc1, float:2.7E-43)
            if (r6 != r11) goto L_0x033e
            goto L_0x035e
        L_0x033e:
            r11 = 188(0xbc, float:2.63E-43)
            if (r6 != r11) goto L_0x03c5
            byte[] r11 = r0.code
            int r13 = r5 + 1
            byte r5 = r11[r5]
            r1.print(r12)
            if (r5 < r10) goto L_0x0359
            r10 = 11
            if (r5 > r10) goto L_0x0359
            int r10 = r5 + -4
            java.lang.String r11 = "boolean;char;float;double;byte;short;int;long;"
            r0.print(r11, r10, r1)
            goto L_0x035c
        L_0x0359:
            r1.print(r5)
        L_0x035c:
            r5 = r13
            goto L_0x03c5
        L_0x035e:
            r8 = 2
            goto L_0x03c5
        L_0x0360:
            r10 = 196(0xc4, float:2.75E-43)
            if (r6 != r10) goto L_0x036b
            java.lang.String r10 = "wide"
            r1.print(r10)
            r2 = 1
            goto L_0x03c5
        L_0x036b:
            r10 = 197(0xc5, float:2.76E-43)
            if (r6 != r10) goto L_0x038d
            java.lang.String r10 = "multianewarray"
            r1.print(r10)
            int r10 = r0.readUnsignedShort(r5)
            int r5 = r5 + 2
            r1.printConstantOperand(r10)
            byte[] r11 = r0.code
            int r13 = r5 + 1
            byte r5 = r11[r5]
            r5 = r5 & 255(0xff, float:3.57E-43)
            r1.print(r12)
            r1.print(r5)
            r5 = r13
            goto L_0x03c5
        L_0x038d:
            r10 = 200(0xc8, float:2.8E-43)
            if (r6 >= r10) goto L_0x03a8
            int r10 = r6 + -198
            java.lang.String r11 = "ifnull;ifnonnull;"
            r0.print(r11, r10, r1)
            int r10 = r0.readUnsignedShort(r5)
            short r10 = (short) r10
            int r5 = r5 + 2
            r1.print(r12)
            int r11 = r3 + r10
            r1.print(r11)
            goto L_0x03c5
        L_0x03a8:
            r10 = 202(0xca, float:2.83E-43)
            if (r6 >= r10) goto L_0x03c2
            int r10 = r6 + -200
            java.lang.String r11 = "goto_w;jsr_w;"
            r0.print(r11, r10, r1)
            int r10 = r0.readInt(r5)
            int r5 = r5 + 4
            r1.print(r12)
            int r11 = r3 + r10
            r1.print(r11)
            goto L_0x03c5
        L_0x03c2:
            r1.print(r6)
        L_0x03c5:
            if (r8 <= 0) goto L_0x03de
            r10 = 1
            if (r8 != r10) goto L_0x03d3
            byte[] r10 = r0.code
            int r11 = r5 + 1
            byte r5 = r10[r5]
            r5 = r5 & 255(0xff, float:3.57E-43)
            goto L_0x03da
        L_0x03d3:
            int r10 = r0.readUnsignedShort(r5)
            int r11 = r5 + 2
            r5 = r10
        L_0x03da:
            r1.printConstantOperand(r5)
            r5 = r11
        L_0x03de:
            r18.println()
            r3 = r5
            goto L_0x0007
        L_0x03e4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.CodeAttr.disAssemble(gnu.bytecode.ClassTypeWriter, int, int):void");
    }

    private int readUnsignedShort(int offset) {
        return ((this.code[offset] & Ev3Constants.Opcode.TST) << 8) | (this.code[offset + 1] & Ev3Constants.Opcode.TST);
    }

    private int readInt(int offset) {
        return (readUnsignedShort(offset) << 16) | readUnsignedShort(offset + 2);
    }

    private void print(String str, int i, PrintWriter dst) {
        int last = 0;
        int pos = -1;
        while (i >= 0) {
            last = pos + 1;
            pos = str.indexOf(59, last);
            i--;
        }
        dst.write(str, last, pos - last);
    }

    public int beginFragment(Label after) {
        return beginFragment(new Label(), after);
    }

    public int beginFragment(Label start, Label after) {
        int i = this.fixup_count;
        fixupAdd(10, after);
        start.define(this);
        return i;
    }

    public void endFragment(int cookie) {
        this.fixup_offsets[cookie] = (this.fixup_count << 4) | 10;
        Label after = this.fixup_labels[cookie];
        fixupAdd(9, 0, (Label) null);
        after.define(this);
    }
}
