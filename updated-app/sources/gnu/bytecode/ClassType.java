package gnu.bytecode;

import androidx.core.internal.view.SupportMenu;
import com.google.appinventor.components.runtime.util.FullScreenVideoUtil;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.Externalizable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class ClassType extends ObjectType implements AttrContainer, Externalizable, Member {
    public static final int JDK_1_1_VERSION = 2949123;
    public static final int JDK_1_2_VERSION = 3014656;
    public static final int JDK_1_3_VERSION = 3080192;
    public static final int JDK_1_4_VERSION = 3145728;
    public static final int JDK_1_5_VERSION = 3211264;
    public static final int JDK_1_6_VERSION = 3276800;
    public static final int JDK_1_7_VERSION = 3342336;
    public static final ClassType[] noClasses = new ClassType[0];
    int Code_name_index;
    int ConstantValue_name_index;
    int LineNumberTable_name_index;
    int LocalVariableTable_name_index;
    int access_flags;
    Attribute attributes;
    int classfileFormatVersion = JDK_1_1_VERSION;
    ConstantPool constants;
    public Method constructor;
    boolean emitDebugInfo = true;
    Member enclosingMember;
    Field fields;
    int fields_count;
    ClassType firstInnerClass;
    int[] interfaceIndexes;
    ClassType[] interfaces;
    Field last_field;
    Method last_method;
    Method methods;
    int methods_count;
    ClassType nextInnerClass;
    SourceDebugExtAttr sourceDbgExt;
    ClassType superClass;
    int superClassIndex = -1;
    int thisClassIndex;

    public short getClassfileMajorVersion() {
        return (short) (this.classfileFormatVersion >> 16);
    }

    public short getClassfileMinorVersion() {
        return (short) (this.classfileFormatVersion & SupportMenu.USER_MASK);
    }

    public void setClassfileVersion(int major, int minor) {
        this.classfileFormatVersion = ((major & SupportMenu.USER_MASK) * 65536) + (SupportMenu.USER_MASK * minor);
    }

    public void setClassfileVersion(int code) {
        this.classfileFormatVersion = code;
    }

    public int getClassfileVersion() {
        return this.classfileFormatVersion;
    }

    public void setClassfileVersionJava5() {
        setClassfileVersion(JDK_1_5_VERSION);
    }

    public static ClassType make(String name) {
        return (ClassType) Type.getType(name);
    }

    public static ClassType make(String name, ClassType superClass2) {
        ClassType type = make(name);
        if (type.superClass == null) {
            type.setSuper(superClass2);
        }
        return type;
    }

    public final Attribute getAttributes() {
        return this.attributes;
    }

    public final void setAttributes(Attribute attributes2) {
        this.attributes = attributes2;
    }

    public final ConstantPool getConstants() {
        return this.constants;
    }

    public final CpoolEntry getConstant(int i) {
        if (this.constants == null || this.constants.pool == null || i > this.constants.count) {
            return null;
        }
        return this.constants.pool[i];
    }

    public final synchronized int getModifiers() {
        if (!(this.access_flags != 0 || (this.flags & 16) == 0 || getReflectClass() == null)) {
            this.access_flags = this.reflectClass.getModifiers();
        }
        return this.access_flags;
    }

    public final boolean getStaticFlag() {
        return (getModifiers() & 8) != 0;
    }

    public final void setModifiers(int flags) {
        this.access_flags = flags;
    }

    public final void addModifiers(int flags) {
        this.access_flags |= flags;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x004f, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.String getSimpleName() {
        /*
            r7 = this;
            monitor-enter(r7)
            int r0 = r7.flags     // Catch:{ all -> 0x0050 }
            r0 = r0 & 16
            if (r0 == 0) goto L_0x0016
            java.lang.Class r0 = r7.getReflectClass()     // Catch:{ all -> 0x0050 }
            if (r0 == 0) goto L_0x0016
            java.lang.Class r0 = r7.reflectClass     // Catch:{ all -> 0x0015 }
            java.lang.String r0 = r0.getSimpleName()     // Catch:{ all -> 0x0015 }
            monitor-exit(r7)
            return r0
        L_0x0015:
            r0 = move-exception
        L_0x0016:
            java.lang.String r0 = r7.getName()     // Catch:{ all -> 0x0050 }
            r1 = 46
            int r1 = r0.lastIndexOf(r1)     // Catch:{ all -> 0x0050 }
            if (r1 <= 0) goto L_0x0029
            int r2 = r1 + 1
            java.lang.String r2 = r0.substring(r2)     // Catch:{ all -> 0x0050 }
            r0 = r2
        L_0x0029:
            r2 = 36
            int r2 = r0.lastIndexOf(r2)     // Catch:{ all -> 0x0050 }
            if (r2 < 0) goto L_0x004e
            int r3 = r0.length()     // Catch:{ all -> 0x0050 }
            int r4 = r2 + 1
        L_0x0037:
            if (r4 >= r3) goto L_0x0049
            char r5 = r0.charAt(r4)     // Catch:{ all -> 0x0050 }
            r6 = 48
            if (r5 < r6) goto L_0x0049
            r6 = 57
            if (r5 > r6) goto L_0x0049
            int r4 = r4 + 1
            goto L_0x0037
        L_0x0049:
            java.lang.String r5 = r0.substring(r4)     // Catch:{ all -> 0x0050 }
            r0 = r5
        L_0x004e:
            monitor-exit(r7)
            return r0
        L_0x0050:
            r0 = move-exception
            monitor-exit(r7)
            goto L_0x0054
        L_0x0053:
            throw r0
        L_0x0054:
            goto L_0x0053
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.ClassType.getSimpleName():java.lang.String");
    }

    public void addMemberClass(ClassType member) {
        ClassType prev = null;
        ClassType entry = this.firstInnerClass;
        while (entry != null) {
            if (entry != member) {
                prev = entry;
                entry = entry.nextInnerClass;
            } else {
                return;
            }
        }
        if (prev == null) {
            this.firstInnerClass = member;
        } else {
            prev.nextInnerClass = member;
        }
    }

    public ClassType getDeclaredClass(String simpleName) {
        addMemberClasses();
        for (ClassType member = this.firstInnerClass; member != null; member = member.nextInnerClass) {
            if (simpleName.equals(member.getSimpleName())) {
                return member;
            }
        }
        return null;
    }

    public ClassType getDeclaringClass() {
        addEnclosingMember();
        if (this.enclosingMember instanceof ClassType) {
            return (ClassType) this.enclosingMember;
        }
        return null;
    }

    public Member getEnclosingMember() {
        addEnclosingMember();
        return this.enclosingMember;
    }

    public void setEnclosingMember(Member member) {
        this.enclosingMember = member;
    }

    /* access modifiers changed from: package-private */
    public synchronized void addEnclosingMember() {
        if ((this.flags & 24) == 16) {
            Class clas = getReflectClass();
            this.flags |= 8;
            Class dclas = clas.getEnclosingClass();
            if (dclas != null) {
                if (!clas.isMemberClass()) {
                    Method rmeth = clas.getEnclosingMethod();
                    if (rmeth != null) {
                        this.enclosingMember = addMethod(rmeth);
                        return;
                    }
                    Constructor rcons = clas.getEnclosingConstructor();
                    if (rcons != null) {
                        this.enclosingMember = addMethod(rcons);
                        return;
                    }
                }
                this.enclosingMember = (ClassType) Type.make(dclas);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void addMemberClasses() {
        /*
            r5 = this;
            monitor-enter(r5)
            int r0 = r5.flags     // Catch:{ all -> 0x002f }
            r0 = r0 & 20
            r1 = 16
            if (r0 == r1) goto L_0x000b
            monitor-exit(r5)
            return
        L_0x000b:
            java.lang.Class r0 = r5.getReflectClass()     // Catch:{ all -> 0x002f }
            int r1 = r5.flags     // Catch:{ all -> 0x002f }
            r1 = r1 | 4
            r5.flags = r1     // Catch:{ all -> 0x002f }
            java.lang.Class[] r1 = r0.getClasses()     // Catch:{ all -> 0x002f }
            int r2 = r1.length     // Catch:{ all -> 0x002f }
            if (r2 <= 0) goto L_0x002d
            r3 = 0
        L_0x001d:
            if (r3 >= r2) goto L_0x002d
            r4 = r1[r3]     // Catch:{ all -> 0x002f }
            gnu.bytecode.Type r4 = gnu.bytecode.Type.make(r4)     // Catch:{ all -> 0x002f }
            gnu.bytecode.ClassType r4 = (gnu.bytecode.ClassType) r4     // Catch:{ all -> 0x002f }
            r5.addMemberClass(r4)     // Catch:{ all -> 0x002f }
            int r3 = r3 + 1
            goto L_0x001d
        L_0x002d:
            monitor-exit(r5)
            return
        L_0x002f:
            r0 = move-exception
            monitor-exit(r5)
            goto L_0x0033
        L_0x0032:
            throw r0
        L_0x0033:
            goto L_0x0032
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.ClassType.addMemberClasses():void");
    }

    public final boolean hasOuterLink() {
        getFields();
        return (this.flags & 32) != 0;
    }

    public ClassType getOuterLinkType() {
        if (!hasOuterLink()) {
            return null;
        }
        return (ClassType) getDeclaredField("this$0").getType();
    }

    public final Field setOuterLink(ClassType outer) {
        if ((this.flags & 16) == 0) {
            Field field = getDeclaredField("this$0");
            if (field == null) {
                field = addField("this$0", outer);
                this.flags |= 32;
                for (Method meth = this.methods; meth != null; meth = meth.getNext()) {
                    if ("<init>".equals(meth.getName())) {
                        if (meth.code == null) {
                            Type[] arg_types = meth.arg_types;
                            Type[] new_types = new Type[(arg_types.length + 1)];
                            System.arraycopy(arg_types, 0, new_types, 1, arg_types.length);
                            new_types[0] = outer;
                            meth.arg_types = new_types;
                            meth.signature = null;
                        } else {
                            throw new Error("setOuterLink called when " + meth + " has code");
                        }
                    }
                }
            } else if (!outer.equals(field.getType())) {
                throw new Error("inconsistent setOuterLink call for " + getName());
            }
            return field;
        }
        throw new Error("setOuterLink called for existing class " + getName());
    }

    public boolean isAccessible(Member member, ObjectType receiver) {
        if (member.getStaticFlag()) {
            receiver = null;
        }
        return isAccessible(member.getDeclaringClass(), receiver, member.getModifiers());
    }

    public boolean isAccessible(ClassType declaring, ObjectType receiver, int modifiers) {
        int cmods = declaring.getModifiers();
        if ((modifiers & 1) != 0 && (cmods & 1) != 0) {
            return true;
        }
        String callerName = getName();
        String className = declaring.getName();
        if (callerName.equals(className)) {
            return true;
        }
        if ((modifiers & 2) != 0) {
            return false;
        }
        int dot = callerName.lastIndexOf(46);
        String classPackage = "";
        String callerPackage = dot >= 0 ? callerName.substring(0, dot) : classPackage;
        int dot2 = className.lastIndexOf(46);
        if (dot2 >= 0) {
            classPackage = className.substring(0, dot2);
        }
        if (callerPackage.equals(classPackage)) {
            return true;
        }
        if ((cmods & 1) != 0 && (modifiers & 4) != 0 && isSubclass(declaring) && (!(receiver instanceof ClassType) || ((ClassType) receiver).isSubclass(this))) {
            return true;
        }
        return false;
    }

    public void setName(String name) {
        this.this_name = name;
        setSignature("L" + name.replace('.', '/') + ";");
    }

    public void setStratum(String stratum) {
        if (this.sourceDbgExt == null) {
            this.sourceDbgExt = new SourceDebugExtAttr(this);
        }
        this.sourceDbgExt.addStratum(stratum);
    }

    public void setSourceFile(String name) {
        if (this.sourceDbgExt != null) {
            this.sourceDbgExt.addFile(name);
            if (this.sourceDbgExt.fileCount > 1) {
                return;
            }
        }
        String name2 = SourceFileAttr.fixSourceFile(name);
        int slash = name2.lastIndexOf(47);
        if (slash >= 0) {
            name2 = name2.substring(slash + 1);
        }
        SourceFileAttr.setSourceFile(this, name2);
    }

    public void setSuper(String name) {
        setSuper(name == null ? Type.pointer_type : make(name));
    }

    public void setSuper(ClassType superClass2) {
        this.superClass = superClass2;
    }

    public synchronized ClassType getSuperclass() {
        if (this.superClass == null && !isInterface() && !"java.lang.Object".equals(getName()) && (this.flags & 16) != 0 && getReflectClass() != null) {
            this.superClass = (ClassType) make(this.reflectClass.getSuperclass());
        }
        return this.superClass;
    }

    public String getPackageName() {
        String name = getName();
        int index = name.lastIndexOf(46);
        return index < 0 ? "" : name.substring(0, index);
    }

    public synchronized ClassType[] getInterfaces() {
        if (!(this.interfaces != null || (this.flags & 16) == 0 || getReflectClass() == null)) {
            Class[] reflectInterfaces = this.reflectClass.getInterfaces();
            int numInterfaces = reflectInterfaces.length;
            this.interfaces = numInterfaces == 0 ? noClasses : new ClassType[numInterfaces];
            for (int i = 0; i < numInterfaces; i++) {
                this.interfaces[i] = (ClassType) Type.make(reflectInterfaces[i]);
            }
        }
        return this.interfaces;
    }

    public void setInterfaces(ClassType[] interfaces2) {
        this.interfaces = interfaces2;
    }

    public void addInterface(ClassType newInterface) {
        int oldCount;
        if (this.interfaces == null || this.interfaces.length == 0) {
            oldCount = 0;
            this.interfaces = new ClassType[1];
        } else {
            oldCount = this.interfaces.length;
            int i = oldCount;
            do {
                i--;
                if (i < 0) {
                    ClassType[] newInterfaces = new ClassType[(oldCount + 1)];
                    System.arraycopy(this.interfaces, 0, newInterfaces, 0, oldCount);
                    this.interfaces = newInterfaces;
                }
            } while (this.interfaces[i] != newInterface);
            return;
        }
        this.interfaces[oldCount] = newInterface;
    }

    public final boolean isInterface() {
        return (getModifiers() & 512) != 0;
    }

    public final void setInterface(boolean val) {
        if (val) {
            this.access_flags |= 1536;
        } else {
            this.access_flags &= -513;
        }
    }

    public ClassType() {
    }

    public ClassType(String class_name) {
        setName(class_name);
    }

    public final synchronized Field getFields() {
        if ((this.flags & 17) == 16) {
            addFields();
        }
        return this.fields;
    }

    public final int getFieldCount() {
        return this.fields_count;
    }

    public Field getDeclaredField(String name) {
        for (Field field = getFields(); field != null; field = field.next) {
            if (name.equals(field.name)) {
                return field;
            }
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0013, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized gnu.bytecode.Field getField(java.lang.String r6, int r7) {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = r5
        L_0x0002:
            gnu.bytecode.Field r1 = r0.getDeclaredField(r6)     // Catch:{ all -> 0x0037 }
            if (r1 == 0) goto L_0x0014
            r2 = -1
            if (r7 == r2) goto L_0x0012
            int r2 = r1.getModifiers()     // Catch:{ all -> 0x0037 }
            r2 = r2 & r7
            if (r2 == 0) goto L_0x0014
        L_0x0012:
            monitor-exit(r5)
            return r1
        L_0x0014:
            gnu.bytecode.ClassType[] r2 = r0.getInterfaces()     // Catch:{ all -> 0x0037 }
            if (r2 == 0) goto L_0x002c
            r3 = 0
        L_0x001b:
            int r4 = r2.length     // Catch:{ all -> 0x0037 }
            if (r3 >= r4) goto L_0x002c
            r4 = r2[r3]     // Catch:{ all -> 0x0037 }
            gnu.bytecode.Field r4 = r4.getField(r6, r7)     // Catch:{ all -> 0x0037 }
            r1 = r4
            if (r1 == 0) goto L_0x0029
            monitor-exit(r5)
            return r1
        L_0x0029:
            int r3 = r3 + 1
            goto L_0x001b
        L_0x002c:
            gnu.bytecode.ClassType r3 = r0.getSuperclass()     // Catch:{ all -> 0x0037 }
            r0 = r3
            if (r0 != 0) goto L_0x0036
            monitor-exit(r5)
            r3 = 0
            return r3
        L_0x0036:
            goto L_0x0002
        L_0x0037:
            r6 = move-exception
            monitor-exit(r5)
            goto L_0x003b
        L_0x003a:
            throw r6
        L_0x003b:
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.ClassType.getField(java.lang.String, int):gnu.bytecode.Field");
    }

    public Field getField(String name) {
        return getField(name, 1);
    }

    public Field addField() {
        return new Field(this);
    }

    public Field addField(String name) {
        Field field = new Field(this);
        field.setName(name);
        return field;
    }

    public final Field addField(String name, Type type) {
        Field field = new Field(this);
        field.setName(name);
        field.setType(type);
        return field;
    }

    public final Field addField(String name, Type type, int flags) {
        Field field = addField(name, type);
        field.flags = flags;
        return field;
    }

    public synchronized void addFields() {
        Field[] fields2;
        Class clas = getReflectClass();
        try {
            fields2 = clas.getDeclaredFields();
        } catch (SecurityException e) {
            fields2 = clas.getFields();
        }
        for (Field field : fields2) {
            if ("this$0".equals(field.getName())) {
                this.flags |= 32;
            }
            addField(field.getName(), Type.make(field.getType()), field.getModifiers());
        }
        this.flags |= 1;
    }

    public final Method getMethods() {
        return this.methods;
    }

    public final int getMethodCount() {
        return this.methods_count;
    }

    /* access modifiers changed from: package-private */
    public Method addMethod() {
        return new Method(this, 0);
    }

    public Method addMethod(String name) {
        return addMethod(name, 0);
    }

    public Method addMethod(String name, int flags) {
        Method method = new Method(this, flags);
        method.setName(name);
        return method;
    }

    public Method addMethod(String name, Type[] arg_types, Type return_type, int flags) {
        return addMethod(name, flags, arg_types, return_type);
    }

    public synchronized Method addMethod(String name, int flags, Type[] arg_types, Type return_type) {
        Method method = getDeclaredMethod(name, arg_types);
        if (method != null && return_type.equals(method.getReturnType()) && (method.access_flags & flags) == flags) {
            return method;
        }
        Method method2 = addMethod(name, flags);
        method2.arg_types = arg_types;
        method2.return_type = return_type;
        return method2;
    }

    public Method addMethod(Method method) {
        int modifiers = method.getModifiers();
        Class[] paramTypes = method.getParameterTypes();
        int j = paramTypes.length;
        Type[] args = new Type[j];
        while (true) {
            j--;
            if (j >= 0) {
                args[j] = Type.make(paramTypes[j]);
            } else {
                return addMethod(method.getName(), modifiers, args, Type.make(method.getReturnType()));
            }
        }
    }

    public Method addMethod(Constructor method) {
        Class[] paramTypes = method.getParameterTypes();
        int modifiers = method.getModifiers();
        int j = paramTypes.length;
        Type[] args = new Type[j];
        while (true) {
            j--;
            if (j < 0) {
                return addMethod("<init>", modifiers, args, (Type) Type.voidType);
            }
            args[j] = Type.make(paramTypes[j]);
        }
    }

    public Method addMethod(String name, String signature, int flags) {
        Method meth = addMethod(name, flags);
        meth.setSignature(signature);
        return meth;
    }

    public Method getMethod(Method method) {
        String name = method.getName();
        Class[] parameterClasses = method.getParameterTypes();
        Type[] parameterTypes = new Type[parameterClasses.length];
        int i = parameterClasses.length;
        while (true) {
            i--;
            if (i < 0) {
                return addMethod(name, method.getModifiers(), parameterTypes, Type.make(method.getReturnType()));
            }
            parameterTypes[i] = Type.make(parameterClasses[i]);
        }
    }

    public final synchronized Method getDeclaredMethods() {
        if ((this.flags & 18) == 16) {
            addMethods(getReflectClass());
        }
        return this.methods;
    }

    public final int countMethods(Filter filter, int searchSupers) {
        Vector vec = new Vector();
        getMethods(filter, searchSupers, vec);
        return vec.size();
    }

    public Method[] getMethods(Filter filter, boolean searchSupers) {
        return getMethods(filter, (int) searchSupers);
    }

    public Method[] getMethods(Filter filter, int searchSupers) {
        Vector<Method> vec = new Vector<>();
        getMethods(filter, searchSupers, vec);
        int count = vec.size();
        Method[] result = new Method[count];
        for (int i = 0; i < count; i++) {
            result[i] = vec.elementAt(i);
        }
        return result;
    }

    public int getMethods(Filter filter, int searchSupers, Method[] result, int offset) {
        Vector<Method> vec = new Vector<>();
        getMethods(filter, searchSupers, vec);
        int count = vec.size();
        for (int i = 0; i < count; i++) {
            result[offset + i] = vec.elementAt(i);
        }
        return count;
    }

    public int getMethods(Filter filter, int searchSupers, List<Method> result) {
        ClassType[] interfaces2;
        int count = 0;
        String inheritingPackage = null;
        for (ClassType ctype = this; ctype != null; ctype = ctype.getSuperclass()) {
            String curPackage = ctype.getPackageName();
            for (Method meth = ctype.getDeclaredMethods(); meth != null; meth = meth.getNext()) {
                if (ctype != this) {
                    int mmods = meth.getModifiers();
                    if ((mmods & 2) == 0) {
                        if ((mmods & 5) == 0 && !curPackage.equals(inheritingPackage)) {
                        }
                    }
                }
                if (filter.select(meth) != 0) {
                    if (result != null) {
                        result.add(meth);
                    }
                    count++;
                }
            }
            inheritingPackage = curPackage;
            if (searchSupers == 0) {
                break;
            }
            if (searchSupers > 1 && (interfaces2 = ctype.getInterfaces()) != null) {
                for (ClassType methods2 : interfaces2) {
                    count += methods2.getMethods(filter, searchSupers, result);
                }
            }
        }
        return count;
    }

    static class AbstractMethodFilter implements Filter {
        public static final AbstractMethodFilter instance = new AbstractMethodFilter();

        AbstractMethodFilter() {
        }

        public boolean select(Object value) {
            return ((Method) value).isAbstract();
        }
    }

    public Method[] getAbstractMethods() {
        return getMethods((Filter) AbstractMethodFilter.instance, 2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0054 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0055 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.bytecode.Method getDeclaredMethod(java.lang.String r10, gnu.bytecode.Type[] r11) {
        /*
            r9 = this;
            java.lang.String r0 = "<init>"
            boolean r0 = r0.equals(r10)
            if (r0 == 0) goto L_0x0010
            boolean r0 = r9.hasOuterLink()
            if (r0 == 0) goto L_0x0010
            r0 = 1
            goto L_0x0011
        L_0x0010:
            r0 = 0
        L_0x0011:
            gnu.bytecode.Method r1 = r9.getDeclaredMethods()
        L_0x0015:
            if (r1 == 0) goto L_0x0059
            java.lang.String r2 = r1.getName()
            boolean r2 = r10.equals(r2)
            if (r2 != 0) goto L_0x0022
            goto L_0x0055
        L_0x0022:
            gnu.bytecode.Type[] r2 = r1.getParameterTypes()
            if (r11 == 0) goto L_0x0058
            if (r11 != r2) goto L_0x002d
            if (r0 != 0) goto L_0x002d
            goto L_0x0058
        L_0x002d:
            int r3 = r11.length
            int r4 = r2.length
            int r4 = r4 - r0
            if (r3 == r4) goto L_0x0033
            goto L_0x0055
        L_0x0033:
            int r3 = r3 + -1
            if (r3 < 0) goto L_0x0052
            int r4 = r3 + r0
            r4 = r2[r4]
            r5 = r11[r3]
            if (r4 == r5) goto L_0x0033
            if (r5 != 0) goto L_0x0042
            goto L_0x0033
        L_0x0042:
            java.lang.String r6 = r4.getSignature()
            java.lang.String r7 = r5.getSignature()
            boolean r8 = r6.equals(r7)
            if (r8 != 0) goto L_0x0051
            goto L_0x0052
        L_0x0051:
            goto L_0x0033
        L_0x0052:
            if (r3 >= 0) goto L_0x0055
            return r1
        L_0x0055:
            gnu.bytecode.Method r1 = r1.next
            goto L_0x0015
        L_0x0058:
            return r1
        L_0x0059:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.ClassType.getDeclaredMethod(java.lang.String, gnu.bytecode.Type[]):gnu.bytecode.Method");
    }

    public synchronized Method getDeclaredMethod(String name, int argCount) {
        Method result;
        result = null;
        int needOuterLinkArg = (!"<init>".equals(name) || !hasOuterLink()) ? 0 : 1;
        for (Method method = getDeclaredMethods(); method != null; method = method.next) {
            if (name.equals(method.getName()) && argCount + needOuterLinkArg == method.getParameterTypes().length) {
                if (result == null) {
                    result = method;
                } else {
                    throw new Error("ambiguous call to getDeclaredMethod(\"" + name + "\", " + argCount + ")\n - " + result + "\n - " + method);
                }
            }
        }
        return result;
    }

    public synchronized Method getMethod(String name, Type[] arg_types) {
        ClassType cl = this;
        do {
            Method method = cl.getDeclaredMethod(name, arg_types);
            if (method != null) {
                return method;
            }
            cl = cl.getSuperclass();
        } while (cl != null);
        ClassType cl2 = this;
        do {
            ClassType[] interfaces2 = cl2.getInterfaces();
            if (interfaces2 != null) {
                for (ClassType declaredMethod : interfaces2) {
                    Method method2 = declaredMethod.getDeclaredMethod(name, arg_types);
                    if (method2 != null) {
                        return method2;
                    }
                }
            }
            cl2 = cl2.getSuperclass();
        } while (cl2 != null);
        return null;
    }

    public synchronized void addMethods(Class clas) {
        Method[] methods2;
        Constructor[] cmethods;
        this.flags |= 2;
        try {
            methods2 = clas.getDeclaredMethods();
        } catch (SecurityException e) {
            methods2 = clas.getMethods();
        }
        for (Method method : methods2) {
            if (method.getDeclaringClass().equals(clas)) {
                addMethod(method);
            }
        }
        try {
            cmethods = clas.getDeclaredConstructors();
        } catch (SecurityException e2) {
            cmethods = clas.getConstructors();
        }
        for (Constructor method2 : cmethods) {
            if (method2.getDeclaringClass().equals(clas)) {
                addMethod(method2);
            }
        }
    }

    public Method[] getMatchingMethods(String name, Type[] paramTypes, int flags) {
        int nMatches = 0;
        Vector matches = new Vector(10);
        for (Method method = this.methods; method != null; method = method.getNext()) {
            if (name.equals(method.getName()) && (flags & 8) == (method.access_flags & 8) && (flags & 1) <= (method.access_flags & 1) && method.arg_types.length == paramTypes.length) {
                nMatches++;
                matches.addElement(method);
            }
        }
        Method[] result = new Method[nMatches];
        matches.copyInto(result);
        return result;
    }

    public void doFixups() {
        if (this.constants == null) {
            this.constants = new ConstantPool();
        }
        if (this.thisClassIndex == 0) {
            this.thisClassIndex = this.constants.addClass((ObjectType) this).index;
        }
        if (this.superClass == this) {
            ClassType classType = null;
            setSuper((ClassType) null);
        }
        if (this.superClassIndex < 0) {
            this.superClassIndex = this.superClass == null ? 0 : this.constants.addClass((ObjectType) this.superClass).index;
        }
        if (this.interfaces != null && this.interfaceIndexes == null) {
            int n = this.interfaces.length;
            this.interfaceIndexes = new int[n];
            for (int i = 0; i < n; i++) {
                this.interfaceIndexes[i] = this.constants.addClass((ObjectType) this.interfaces[i]).index;
            }
        }
        for (Field field = this.fields; field != null; field = field.next) {
            field.assign_constants(this);
        }
        for (Method method = this.methods; method != null; method = method.next) {
            method.assignConstants();
        }
        if (this.enclosingMember instanceof Method) {
            EnclosingMethodAttr attr = EnclosingMethodAttr.getFirstEnclosingMethod(getAttributes());
            if (attr == null) {
                attr = new EnclosingMethodAttr(this);
            }
            attr.method = (Method) this.enclosingMember;
        } else if (this.enclosingMember instanceof ClassType) {
            this.constants.addClass((ObjectType) (ClassType) this.enclosingMember);
        }
        for (ClassType member = this.firstInnerClass; member != null; member = member.nextInnerClass) {
            this.constants.addClass((ObjectType) member);
        }
        InnerClassesAttr innerAttr = InnerClassesAttr.getFirstInnerClasses(getAttributes());
        if (innerAttr != null) {
            innerAttr.setSkipped(true);
        }
        Attribute.assignConstants(this, this);
        for (int i2 = 1; i2 <= this.constants.count; i2++) {
            CpoolEntry entry = this.constants.pool[i2];
            if (entry instanceof CpoolClass) {
                CpoolClass centry = (CpoolClass) entry;
                if ((centry.clas instanceof ClassType) && ((ClassType) centry.clas).getEnclosingMember() != null) {
                    if (innerAttr == null) {
                        innerAttr = new InnerClassesAttr(this);
                    }
                    innerAttr.addClass(centry, this);
                }
            }
        }
        if (innerAttr != null) {
            innerAttr.setSkipped(false);
            innerAttr.assignConstants(this);
        }
    }

    public void writeToStream(OutputStream stream) throws IOException {
        DataOutputStream dstr = new DataOutputStream(stream);
        doFixups();
        dstr.writeInt(-889275714);
        dstr.writeShort(getClassfileMinorVersion());
        dstr.writeShort(getClassfileMajorVersion());
        if (this.constants == null) {
            dstr.writeShort(1);
        } else {
            this.constants.write(dstr);
        }
        dstr.writeShort(this.access_flags);
        dstr.writeShort(this.thisClassIndex);
        dstr.writeShort(this.superClassIndex);
        if (this.interfaceIndexes == null) {
            dstr.writeShort(0);
        } else {
            dstr.writeShort(interfaces_count);
            for (int writeShort : this.interfaceIndexes) {
                dstr.writeShort(writeShort);
            }
        }
        dstr.writeShort(this.fields_count);
        for (Field field = this.fields; field != null; field = field.next) {
            field.write(dstr, this);
        }
        dstr.writeShort(this.methods_count);
        for (Method method = this.methods; method != null; method = method.next) {
            method.write(dstr, this);
        }
        Attribute.writeAll(this, dstr);
        this.flags |= 3;
    }

    public void writeToFile(String filename) throws IOException {
        OutputStream stream = new BufferedOutputStream(new FileOutputStream(filename));
        writeToStream(stream);
        stream.close();
    }

    public void writeToFile() throws IOException {
        writeToFile(this.this_name.replace('.', File.separatorChar) + ".class");
    }

    public byte[] writeToArray() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream(500);
        try {
            writeToStream(stream);
            return stream.toByteArray();
        } catch (IOException ex) {
            throw new InternalError(ex.toString());
        }
    }

    public static byte[] to_utf8(String str) {
        if (str == null) {
            return null;
        }
        int str_len = str.length();
        int utf_len = 0;
        for (int i = 0; i < str_len; i++) {
            int c = str.charAt(i);
            if (c > 0 && c <= 127) {
                utf_len++;
            } else if (c <= 2047) {
                utf_len += 2;
            } else {
                utf_len += 3;
            }
        }
        byte[] buffer = new byte[utf_len];
        int j = 0;
        for (int i2 = 0; i2 < str_len; i2++) {
            int c2 = str.charAt(i2);
            if (c2 > 0 && c2 <= 127) {
                buffer[j] = (byte) c2;
                j++;
            } else if (c2 <= 2047) {
                int j2 = j + 1;
                buffer[j] = (byte) (((c2 >> 6) & 31) | FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PAUSE);
                j = j2 + 1;
                buffer[j2] = (byte) (((c2 >> 0) & 63) | 128);
            } else {
                int j3 = j + 1;
                buffer[j] = (byte) (((c2 >> 12) & 15) | 224);
                int j4 = j3 + 1;
                buffer[j3] = (byte) (((c2 >> 6) & 63) | 128);
                buffer[j4] = (byte) (((c2 >> 0) & 63) | 128);
                j = j4 + 1;
            }
        }
        return buffer;
    }

    public final boolean implementsInterface(ClassType iface) {
        if (this == iface) {
            return true;
        }
        ClassType baseClass = getSuperclass();
        if (baseClass != null && baseClass.implementsInterface(iface)) {
            return true;
        }
        ClassType[] interfaces2 = getInterfaces();
        if (interfaces2 == null) {
            return false;
        }
        int i = interfaces2.length;
        do {
            i--;
            if (i < 0) {
                return false;
            }
        } while (!interfaces2[i].implementsInterface(iface));
        return true;
    }

    public final boolean isSubclass(String cname) {
        ClassType ctype = this;
        while (!cname.equals(ctype.getName())) {
            ctype = ctype.getSuperclass();
            if (ctype == null) {
                return false;
            }
        }
        return true;
    }

    public final boolean isSubclass(ClassType other) {
        if (other.isInterface()) {
            return implementsInterface(other);
        }
        if ((this == toStringType && other == javalangStringType) || (this == javalangStringType && other == toStringType)) {
            return true;
        }
        for (ClassType baseClass = this; baseClass != null; baseClass = baseClass.getSuperclass()) {
            if (baseClass == other) {
                return true;
            }
        }
        return false;
    }

    public int compare(Type other) {
        if (other == nullType) {
            return 1;
        }
        if (!(other instanceof ClassType)) {
            return swappedCompareResult(other.compare(this));
        }
        String name = getName();
        if (name != null && name.equals(other.getName())) {
            return 0;
        }
        ClassType cother = (ClassType) other;
        if (isSubclass(cother)) {
            return -1;
        }
        if (cother.isSubclass(this)) {
            return 1;
        }
        if (this == toStringType) {
            if (cother == Type.javalangObjectType) {
                return -1;
            }
            return 1;
        } else if (cother == toStringType) {
            if (this == Type.javalangObjectType) {
                return 1;
            }
            return -1;
        } else if (isInterface()) {
            if (cother == Type.javalangObjectType) {
                return -1;
            }
            return -2;
        } else if (!cother.isInterface()) {
            return -3;
        } else {
            if (this == Type.javalangObjectType) {
                return 1;
            }
            return -2;
        }
    }

    public String toString() {
        return "ClassType " + getName();
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(getName());
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setName(in.readUTF());
        this.flags |= 16;
    }

    public Object readResolve() throws ObjectStreamException {
        String name = getName();
        HashMap<String, Type> map = mapNameToType;
        synchronized (map) {
            Type found = map.get(name);
            if (found != null) {
                return found;
            }
            map.put(name, this);
            return this;
        }
    }

    public void cleanupAfterCompilation() {
        for (Method meth = this.methods; meth != null; meth = meth.getNext()) {
            meth.cleanupAfterCompilation();
        }
        this.constants = null;
        this.attributes = null;
        this.sourceDbgExt = null;
    }

    public Method checkSingleAbstractMethod() {
        Method result = null;
        for (Method meth : getAbstractMethods()) {
            Method mimpl = getMethod(meth.getName(), meth.getParameterTypes());
            if (mimpl == null || mimpl.isAbstract()) {
                if (result != null) {
                    return null;
                }
                result = meth;
            }
        }
        return result;
    }
}
