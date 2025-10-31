package kawa.lang;

import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrappedException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Vector;

public class Record {
    public String getTypeName() {
        return getClass().getName();
    }

    public static boolean isRecord(Object obj) {
        return obj instanceof Record;
    }

    public int hashCode() {
        Field[] fields = getClass().getFields();
        int hash = 12345;
        for (Field field : fields) {
            try {
                Object value = field.get(this);
                if (value != null) {
                    hash ^= value.hashCode();
                }
            } catch (IllegalAccessException e) {
            }
        }
        return hash;
    }

    static Field getField(Class clas, String fname) throws NoSuchFieldException {
        for (gnu.bytecode.Field fld = ((ClassType) Type.make(clas)).getFields(); fld != null; fld = fld.getNext()) {
            if ((fld.getModifiers() & 9) == 1 && fld.getSourceName().equals(fname)) {
                return fld.getReflectField();
            }
        }
        throw new NoSuchFieldException();
    }

    public Object get(String fname, Object defaultValue) {
        Class clas = getClass();
        try {
            return getField(clas, fname).get(this);
        } catch (NoSuchFieldException e) {
            throw new GenericError("no such field " + fname + " in " + clas.getName());
        } catch (IllegalAccessException e2) {
            throw new GenericError("illegal access for field " + fname);
        }
    }

    public Object put(String fname, Object value) {
        return set1(this, fname, value);
    }

    public static Object set1(Object record, String fname, Object value) {
        Class clas = record.getClass();
        try {
            Field fld = getField(clas, fname);
            Object old = fld.get(record);
            fld.set(record, value);
            return old;
        } catch (NoSuchFieldException e) {
            throw new GenericError("no such field " + fname + " in " + clas.getName());
        } catch (IllegalAccessException e2) {
            throw new GenericError("illegal access for field " + fname);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        Class thisClass = getClass();
        if (obj == null || obj.getClass() != thisClass) {
            return false;
        }
        Object value2 = null;
        for (gnu.bytecode.Field fld = ((ClassType) Type.make(thisClass)).getFields(); fld != null; fld = fld.getNext()) {
            if ((fld.getModifiers() & 9) == 1) {
                try {
                    Field field = fld.getReflectField();
                    Object value1 = field.get(this);
                    try {
                        value2 = field.get(obj);
                        if (!value1.equals(value2)) {
                            return false;
                        }
                    } catch (Exception e) {
                        ex = e;
                        Object obj2 = value2;
                        throw new WrappedException((Throwable) ex);
                    }
                } catch (Exception e2) {
                    ex = e2;
                    Object obj3 = value2;
                    throw new WrappedException((Throwable) ex);
                }
            }
        }
        return true;
    }

    public String toString() {
        Object value;
        StringBuffer buf = new StringBuffer(HttpRequestContext.HTTP_OK);
        buf.append("#<");
        buf.append(getTypeName());
        for (gnu.bytecode.Field fld = ((ClassType) Type.make(getClass())).getFields(); fld != null; fld = fld.getNext()) {
            if ((fld.getModifiers() & 9) == 1) {
                try {
                    value = fld.getReflectField().get(this);
                } catch (Exception e) {
                    value = "#<illegal-access>";
                }
                buf.append(' ');
                buf.append(fld.getSourceName());
                buf.append(": ");
                buf.append(value);
            }
        }
        buf.append(">");
        return buf.toString();
    }

    public void print(PrintWriter ps) {
        ps.print(toString());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v2, resolved type: gnu.lists.LList} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static gnu.bytecode.ClassType makeRecordType(java.lang.String r12, gnu.lists.LList r13) {
        /*
            java.lang.String r0 = "kawa.lang.Record"
            gnu.bytecode.ClassType r0 = gnu.bytecode.ClassType.make(r0)
            java.lang.String r1 = gnu.expr.Compilation.mangleNameIfNeeded(r12)
            gnu.bytecode.ClassType r2 = new gnu.bytecode.ClassType
            r2.<init>(r1)
            r2.setSuper((gnu.bytecode.ClassType) r0)
            r3 = 33
            r2.setModifiers(r3)
            gnu.bytecode.Type[] r3 = gnu.bytecode.Type.typeArray0
            gnu.bytecode.PrimType r4 = gnu.bytecode.Type.voidType
            java.lang.String r5 = "<init>"
            r6 = 1
            gnu.bytecode.Method r3 = r2.addMethod((java.lang.String) r5, (gnu.bytecode.Type[]) r3, (gnu.bytecode.Type) r4, (int) r6)
            gnu.bytecode.Type[] r4 = gnu.bytecode.Type.typeArray0
            gnu.bytecode.PrimType r7 = gnu.bytecode.Type.voidType
            gnu.bytecode.Method r4 = r0.addMethod((java.lang.String) r5, (gnu.bytecode.Type[]) r4, (gnu.bytecode.Type) r7, (int) r6)
            gnu.bytecode.CodeAttr r5 = r3.startCode()
            r5.emitPushThis()
            r5.emitInvokeSpecial(r4)
            r5.emitReturn()
            boolean r7 = r12.equals(r1)
            if (r7 != 0) goto L_0x0051
            gnu.bytecode.Type[] r7 = gnu.bytecode.Type.typeArray0
            gnu.bytecode.ClassType r8 = gnu.expr.Compilation.typeString
            java.lang.String r9 = "getTypeName"
            gnu.bytecode.Method r7 = r2.addMethod((java.lang.String) r9, (gnu.bytecode.Type[]) r7, (gnu.bytecode.Type) r8, (int) r6)
            gnu.bytecode.CodeAttr r5 = r7.startCode()
            r5.emitPushString(r12)
            r5.emitReturn()
        L_0x0051:
            gnu.lists.LList r7 = gnu.lists.LList.Empty
            if (r13 == r7) goto L_0x0079
            r7 = r13
            gnu.lists.Pair r7 = (gnu.lists.Pair) r7
            java.lang.Object r8 = r7.getCar()
            java.lang.String r8 = r8.toString()
            java.lang.String r9 = gnu.expr.Compilation.mangleNameIfNeeded(r8)
            gnu.bytecode.ClassType r10 = gnu.bytecode.Type.pointer_type
            gnu.bytecode.Field r9 = r2.addField(r9, r10, r6)
            java.lang.String r10 = r8.intern()
            r9.setSourceName(r10)
            java.lang.Object r10 = r7.getCdr()
            r13 = r10
            gnu.lists.LList r13 = (gnu.lists.LList) r13
            goto L_0x0051
        L_0x0079:
            byte[][] r7 = new byte[r6][]
            java.lang.String[] r6 = new java.lang.String[r6]
            r8 = 0
            r6[r8] = r1
            byte[] r9 = r2.writeToArray()
            r7[r8] = r9
            gnu.bytecode.ArrayClassLoader r8 = new gnu.bytecode.ArrayClassLoader
            r8.<init>(r6, r7)
            java.lang.Class r9 = r8.loadClass(r1)     // Catch:{ ClassNotFoundException -> 0x0093 }
            gnu.bytecode.Type.registerTypeForClass(r9, r2)     // Catch:{ ClassNotFoundException -> 0x0093 }
            return r2
        L_0x0093:
            r9 = move-exception
            java.lang.InternalError r10 = new java.lang.InternalError
            java.lang.String r11 = r9.toString()
            r10.<init>(r11)
            goto L_0x009f
        L_0x009e:
            throw r10
        L_0x009f:
            goto L_0x009e
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Record.makeRecordType(java.lang.String, gnu.lists.LList):gnu.bytecode.ClassType");
    }

    public static LList typeFieldNames(Class clas) {
        LList list = LList.Empty;
        Vector vec = new Vector(100);
        for (gnu.bytecode.Field field = ((ClassType) Type.make(clas)).getFields(); field != null; field = field.getNext()) {
            if ((field.getModifiers() & 9) == 1) {
                vec.addElement(SimpleSymbol.valueOf(field.getSourceName()));
            }
        }
        int i = vec.size();
        while (true) {
            i--;
            if (i < 0) {
                return list;
            }
            list = new Pair(vec.elementAt(i), list);
        }
    }

    public static LList typeFieldNames(ClassType ctype) {
        return typeFieldNames(ctype.getReflectClass());
    }
}
