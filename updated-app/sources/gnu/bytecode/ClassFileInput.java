package gnu.bytecode;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class ClassFileInput extends DataInputStream {
    ClassType ctype;
    InputStream str;

    public ClassFileInput(InputStream str2) throws IOException {
        super(str2);
    }

    public ClassFileInput(ClassType ctype2, InputStream str2) throws IOException, ClassFormatError {
        super(str2);
        this.ctype = ctype2;
        if (readHeader()) {
            ctype2.constants = readConstants();
            readClassInfo();
            readFields();
            readMethods();
            readAttributes(ctype2);
            return;
        }
        throw new ClassFormatError("invalid magic number");
    }

    public static ClassType readClassType(InputStream str2) throws IOException, ClassFormatError {
        ClassType ctype2 = new ClassType();
        new ClassFileInput(ctype2, str2);
        return ctype2;
    }

    public boolean readHeader() throws IOException {
        if (readInt() != -889275714) {
            return false;
        }
        readFormatVersion();
        return true;
    }

    public void readFormatVersion() throws IOException {
        this.ctype.classfileFormatVersion = (65536 * readUnsignedShort()) + readUnsignedShort();
    }

    public ConstantPool readConstants() throws IOException {
        return new ConstantPool(this);
    }

    public void readClassInfo() throws IOException {
        this.ctype.access_flags = readUnsignedShort();
        this.ctype.thisClassIndex = readUnsignedShort();
        String name = getClassConstant(this.ctype.thisClassIndex).name.string;
        this.ctype.this_name = name.replace('/', '.');
        this.ctype.setSignature("L" + name + ";");
        this.ctype.superClassIndex = readUnsignedShort();
        if (this.ctype.superClassIndex == 0) {
            ClassType classType = null;
            this.ctype.setSuper((ClassType) null);
        } else {
            this.ctype.setSuper(getClassConstant(this.ctype.superClassIndex).name.string.replace('/', '.'));
        }
        int nInterfaces = readUnsignedShort();
        if (nInterfaces > 0) {
            this.ctype.interfaces = new ClassType[nInterfaces];
            this.ctype.interfaceIndexes = new int[nInterfaces];
            for (int i = 0; i < nInterfaces; i++) {
                int index = readUnsignedShort();
                this.ctype.interfaceIndexes[i] = index;
                this.ctype.interfaces[i] = ClassType.make(((CpoolClass) this.ctype.constants.getForced(index, 7)).name.string.replace('/', '.'));
            }
        }
    }

    public int readAttributes(AttrContainer container) throws IOException {
        int count = readUnsignedShort();
        Attribute last = container.getAttributes();
        for (int i = 0; i < count; i++) {
            if (last != null) {
                while (true) {
                    Attribute next = last.getNext();
                    if (next == null) {
                        break;
                    }
                    last = next;
                }
            }
            int index = readUnsignedShort();
            CpoolUtf8 nameConstant = (CpoolUtf8) this.ctype.constants.getForced(index, 1);
            int length = readInt();
            nameConstant.intern();
            Attribute attr = readAttribute(nameConstant.string, length, container);
            if (attr != null) {
                if (attr.getNameIndex() == 0) {
                    attr.setNameIndex(index);
                }
                if (last == null) {
                    container.setAttributes(attr);
                } else {
                    if (container.getAttributes() == attr) {
                        container.setAttributes(attr.getNext());
                        attr.setNext((Attribute) null);
                    }
                    last.setNext(attr);
                }
                last = attr;
            }
        }
        return count;
    }

    public final void skipAttribute(int length) throws IOException {
        int read = 0;
        while (read < length) {
            int skipped = (int) skip((long) (length - read));
            if (skipped == 0) {
                if (read() >= 0) {
                    skipped = 1;
                } else {
                    throw new EOFException("EOF while reading class files attributes");
                }
            }
            read += skipped;
        }
    }

    public Attribute readAttribute(String name, int length, AttrContainer container) throws IOException {
        CodeAttr code;
        Method method;
        int count;
        String str2 = name;
        int i = length;
        AttrContainer attrContainer = container;
        if (str2 == "SourceFile" && (attrContainer instanceof ClassType)) {
            return new SourceFileAttr(readUnsignedShort(), (ClassType) attrContainer);
        }
        if (str2 == "Code" && (attrContainer instanceof Method)) {
            CodeAttr code2 = new CodeAttr((Method) attrContainer);
            code2.fixup_count = -1;
            code2.setMaxStack(readUnsignedShort());
            code2.setMaxLocals(readUnsignedShort());
            byte[] insns = new byte[readInt()];
            readFully(insns);
            code2.setCode(insns);
            int exception_table_length = readUnsignedShort();
            for (int i2 = 0; i2 < exception_table_length; i2++) {
                code2.addHandler(readUnsignedShort(), readUnsignedShort(), readUnsignedShort(), readUnsignedShort());
            }
            readAttributes(code2);
            return code2;
        } else if (str2 == "LineNumberTable" && (attrContainer instanceof CodeAttr)) {
            int count2 = readUnsignedShort() * 2;
            short[] numbers = new short[count2];
            for (int i3 = 0; i3 < count2; i3++) {
                numbers[i3] = readShort();
            }
            return new LineNumbersAttr(numbers, (CodeAttr) attrContainer);
        } else if (str2 == "LocalVariableTable" && (attrContainer instanceof CodeAttr)) {
            CodeAttr code3 = (CodeAttr) attrContainer;
            LocalVarsAttr attr = new LocalVarsAttr(code3);
            Method method2 = attr.getMethod();
            if (attr.parameter_scope == null) {
                attr.parameter_scope = method2.pushScope();
            }
            Scope scope = attr.parameter_scope;
            if (scope.end == null) {
                scope.end = new Label(code3.PC);
            }
            ConstantPool constants = method2.getConstants();
            int count3 = readUnsignedShort();
            int prev_start = scope.start.position;
            int prev_end = scope.end.position;
            int i4 = 0;
            while (i4 < count3) {
                Variable var = new Variable();
                int start_pc = readUnsignedShort();
                int end_pc = readUnsignedShort() + start_pc;
                if (start_pc == prev_start && end_pc == prev_end) {
                    code = code3;
                    method = method2;
                    count = count3;
                } else {
                    while (true) {
                        code = code3;
                        if (scope.parent == null || (start_pc >= scope.start.position && end_pc <= scope.end.position)) {
                            method = method2;
                            Scope scope2 = scope;
                            count = count3;
                            Scope scope3 = new Scope(new Label(start_pc), new Label(end_pc));
                            scope3.linkChild(scope);
                            prev_start = start_pc;
                            prev_end = end_pc;
                            scope = scope3;
                        } else {
                            scope = scope.parent;
                            code3 = code;
                        }
                    }
                    method = method2;
                    Scope scope22 = scope;
                    count = count3;
                    Scope scope32 = new Scope(new Label(start_pc), new Label(end_pc));
                    scope32.linkChild(scope);
                    prev_start = start_pc;
                    prev_end = end_pc;
                    scope = scope32;
                }
                scope.addVariable(var);
                var.setName(readUnsignedShort(), constants);
                var.setSignature(readUnsignedShort(), constants);
                var.offset = readUnsignedShort();
                i4++;
                code3 = code;
                method2 = method;
                count3 = count;
            }
            return attr;
        } else if (str2 == "Signature" && (attrContainer instanceof Member)) {
            return new SignatureAttr(readUnsignedShort(), (Member) attrContainer);
        } else {
            if (str2 == "StackMapTable" && (attrContainer instanceof CodeAttr)) {
                byte[] data = new byte[i];
                readFully(data, 0, i);
                return new StackMapTableAttr(data, (CodeAttr) attrContainer);
            } else if ((str2 == "RuntimeVisibleAnnotations" || str2 == "RuntimeInvisibleAnnotations") && ((attrContainer instanceof Field) || (attrContainer instanceof Method) || (attrContainer instanceof ClassType))) {
                byte[] data2 = new byte[i];
                readFully(data2, 0, i);
                return new RuntimeAnnotationsAttr(str2, data2, attrContainer);
            } else if (str2 == "ConstantValue" && (attrContainer instanceof Field)) {
                return new ConstantValueAttr(readUnsignedShort());
            } else {
                if (str2 == "InnerClasses" && (attrContainer instanceof ClassType)) {
                    int count4 = readUnsignedShort() * 4;
                    short[] data3 = new short[count4];
                    for (int i5 = 0; i5 < count4; i5++) {
                        data3[i5] = readShort();
                    }
                    return new InnerClassesAttr(data3, (ClassType) attrContainer);
                } else if (str2 == "EnclosingMethod" && (attrContainer instanceof ClassType)) {
                    return new EnclosingMethodAttr(readUnsignedShort(), readUnsignedShort(), (ClassType) attrContainer);
                } else {
                    if (str2 == "Exceptions" && (attrContainer instanceof Method)) {
                        Method meth = (Method) attrContainer;
                        int count5 = readUnsignedShort();
                        short[] exn_indices = new short[count5];
                        for (int i6 = 0; i6 < count5; i6++) {
                            exn_indices[i6] = readShort();
                        }
                        meth.setExceptions(exn_indices);
                        return meth.getExceptionAttr();
                    } else if (str2 != "SourceDebugExtension" || !(attrContainer instanceof ClassType)) {
                        byte[] data4 = new byte[i];
                        readFully(data4, 0, i);
                        return new MiscAttr(str2, data4);
                    } else {
                        SourceDebugExtAttr attr2 = new SourceDebugExtAttr((ClassType) attrContainer);
                        byte[] data5 = new byte[i];
                        readFully(data5, 0, i);
                        attr2.data = data5;
                        attr2.dlength = i;
                        return attr2;
                    }
                }
            }
        }
    }

    public void readFields() throws IOException {
        int nFields = readUnsignedShort();
        ConstantPool constants = this.ctype.constants;
        for (int i = 0; i < nFields; i++) {
            int flags = readUnsignedShort();
            int nameIndex = readUnsignedShort();
            int descriptorIndex = readUnsignedShort();
            Field fld = this.ctype.addField();
            fld.setName(nameIndex, constants);
            fld.setSignature(descriptorIndex, constants);
            fld.flags = flags;
            readAttributes(fld);
        }
    }

    public void readMethods() throws IOException {
        int nMethods = readUnsignedShort();
        for (int i = 0; i < nMethods; i++) {
            int flags = readUnsignedShort();
            int nameIndex = readUnsignedShort();
            int descriptorIndex = readUnsignedShort();
            Method meth = this.ctype.addMethod((String) null, flags);
            meth.setName(nameIndex);
            meth.setSignature(descriptorIndex);
            readAttributes(meth);
        }
    }

    /* access modifiers changed from: package-private */
    public CpoolClass getClassConstant(int index) {
        return (CpoolClass) this.ctype.constants.getForced(index, 7);
    }
}
