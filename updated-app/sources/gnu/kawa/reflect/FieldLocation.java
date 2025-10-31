package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.mapping.Location;
import gnu.mapping.Procedure;
import gnu.mapping.WrappedException;
import kawa.lang.Syntax;

public class FieldLocation extends ClassMemberLocation {
    static final int CONSTANT = 4;
    static final int INDIRECT_LOCATION = 2;
    public static final int KIND_FLAGS_SET = 64;
    public static final int PROCEDURE = 16;
    static final int SETUP_DONE = 1;
    public static final int SYNTAX = 32;
    static final int VALUE_SET = 8;
    Declaration decl;
    private int flags;
    Object value;

    public boolean isIndirectLocation() {
        return (this.flags & 2) != 0;
    }

    public void setProcedure() {
        this.flags |= 84;
    }

    public void setSyntax() {
        this.flags |= 100;
    }

    /* access modifiers changed from: package-private */
    public void setKindFlags() {
        Field fld = getDeclaringClass().getDeclaredField(getMemberName());
        int fflags = fld.getModifiers();
        Type ftype = fld.getType();
        if (ftype.isSubtype(Compilation.typeLocation)) {
            this.flags |= 2;
        }
        if ((fflags & 16) != 0) {
            if ((this.flags & 2) == 0) {
                this.flags |= 4;
                if (ftype.isSubtype(Compilation.typeProcedure)) {
                    this.flags |= 16;
                }
                if ((ftype instanceof ClassType) && ((ClassType) ftype).isSubclass("kawa.lang.Syntax")) {
                    this.flags |= 32;
                }
            } else {
                Location loc = (Location) getFieldValue();
                if (loc instanceof FieldLocation) {
                    FieldLocation floc = (FieldLocation) loc;
                    if ((floc.flags & 64) == 0) {
                        floc.setKindFlags();
                    }
                    this.flags |= floc.flags & 52;
                    if ((floc.flags & 4) == 0) {
                        this.value = floc;
                        this.flags |= 8;
                    } else if ((floc.flags & 8) != 0) {
                        this.value = floc.value;
                        this.flags |= 8;
                    }
                } else if (loc.isConstant()) {
                    Object val = loc.get((Object) null);
                    if (val instanceof Procedure) {
                        this.flags |= 16;
                    }
                    if (val instanceof Syntax) {
                        this.flags |= 32;
                    }
                    this.flags |= 12;
                    this.value = val;
                }
            }
        }
        this.flags |= 64;
    }

    public boolean isProcedureOrSyntax() {
        if ((this.flags & 64) == 0) {
            setKindFlags();
        }
        return (this.flags & 48) != 0;
    }

    public FieldLocation(Object instance, String cname, String fname) {
        super(instance, ClassType.make(cname), fname);
    }

    public FieldLocation(Object instance, ClassType type, String mname) {
        super(instance, type, mname);
    }

    public FieldLocation(Object instance, java.lang.reflect.Field field) {
        super(instance, field);
        this.type = (ClassType) Type.make(field.getDeclaringClass());
    }

    public void setDeclaration(Declaration decl2) {
        this.decl = decl2;
    }

    public Field getField() {
        return this.type.getDeclaredField(this.mname);
    }

    public Type getFType() {
        return this.type.getDeclaredField(this.mname).getType();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0063, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized gnu.expr.Declaration getDeclaration() {
        /*
            r9 = this;
            monitor-enter(r9)
            int r0 = r9.flags     // Catch:{ all -> 0x0064 }
            r0 = r0 & 64
            if (r0 != 0) goto L_0x000a
            r9.setKindFlags()     // Catch:{ all -> 0x0064 }
        L_0x000a:
            gnu.expr.Declaration r0 = r9.decl     // Catch:{ all -> 0x0064 }
            if (r0 != 0) goto L_0x0062
            java.lang.String r1 = r9.getMemberName()     // Catch:{ all -> 0x0064 }
            gnu.bytecode.ClassType r2 = r9.getDeclaringClass()     // Catch:{ all -> 0x0064 }
            gnu.bytecode.Field r3 = r2.getDeclaredField(r1)     // Catch:{ all -> 0x0064 }
            if (r3 != 0) goto L_0x001f
            monitor-exit(r9)
            r4 = 0
            return r4
        L_0x001f:
            gnu.expr.ModuleInfo r4 = gnu.expr.ModuleInfo.find(r2)     // Catch:{ all -> 0x0064 }
            gnu.expr.ModuleExp r5 = r4.getModuleExp()     // Catch:{ all -> 0x0064 }
            gnu.expr.Declaration r6 = r5.firstDecl()     // Catch:{ all -> 0x0064 }
        L_0x002b:
            r0 = r6
            if (r0 == 0) goto L_0x0044
            gnu.bytecode.Field r6 = r0.field     // Catch:{ all -> 0x0064 }
            if (r6 == 0) goto L_0x003f
            gnu.bytecode.Field r6 = r0.field     // Catch:{ all -> 0x0064 }
            java.lang.String r6 = r6.getName()     // Catch:{ all -> 0x0064 }
            boolean r6 = r6.equals(r1)     // Catch:{ all -> 0x0064 }
            if (r6 == 0) goto L_0x003f
            goto L_0x0044
        L_0x003f:
            gnu.expr.Declaration r6 = r0.nextDecl()     // Catch:{ all -> 0x0064 }
            goto L_0x002b
        L_0x0044:
            if (r0 == 0) goto L_0x0049
            r9.decl = r0     // Catch:{ all -> 0x0064 }
            goto L_0x0062
        L_0x0049:
            java.lang.RuntimeException r6 = new java.lang.RuntimeException     // Catch:{ all -> 0x0064 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0064 }
            r7.<init>()     // Catch:{ all -> 0x0064 }
            java.lang.String r8 = "no field found for "
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0064 }
            java.lang.StringBuilder r7 = r7.append(r9)     // Catch:{ all -> 0x0064 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0064 }
            r6.<init>(r7)     // Catch:{ all -> 0x0064 }
            throw r6     // Catch:{ all -> 0x0064 }
        L_0x0062:
            monitor-exit(r9)
            return r0
        L_0x0064:
            r0 = move-exception
            monitor-exit(r9)
            goto L_0x0068
        L_0x0067:
            throw r0
        L_0x0068:
            goto L_0x0067
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.reflect.FieldLocation.getDeclaration():gnu.expr.Declaration");
    }

    /* access modifiers changed from: package-private */
    public void setup() {
        synchronized (this) {
            if ((this.flags & 1) == 0) {
                super.setup();
                if ((this.flags & 64) == 0) {
                    setKindFlags();
                }
                this.flags |= 1;
            }
        }
    }

    public Object get(Object defaultValue) {
        Object v;
        try {
            setup();
            if ((this.flags & 8) != 0) {
                v = this.value;
                if ((this.flags & 4) != 0) {
                    return v;
                }
            } else {
                v = getFieldValue();
                if ((this.type.getDeclaredField(this.mname).getModifiers() & 16) != 0) {
                    this.flags |= 8;
                    if ((this.flags & 2) == 0) {
                        this.flags |= 4;
                    }
                    this.value = v;
                }
            }
            if ((this.flags & 2) != 0) {
                Object unb = Location.UNBOUND;
                Location loc = (Location) v;
                v = loc.get(unb);
                if (v == unb) {
                    return defaultValue;
                }
                if (loc.isConstant()) {
                    this.flags |= 4;
                    this.value = v;
                }
            }
            return v;
        } catch (Throwable th) {
            return defaultValue;
        }
    }

    private Object getFieldValue() {
        super.setup();
        try {
            return this.rfield.get(this.instance);
        } catch (Throwable ex) {
            throw WrappedException.wrapIfNeeded(ex);
        }
    }

    public void set(Object newValue) {
        Object v;
        setup();
        if ((this.flags & 2) == 0) {
            try {
                this.rfield.set(this.instance, newValue);
            } catch (Throwable ex) {
                throw WrappedException.wrapIfNeeded(ex);
            }
        } else {
            if ((this.flags & 8) != 0) {
                v = this.value;
            } else {
                this.flags |= 8;
                Object v2 = getFieldValue();
                this.value = v2;
                v = v2;
            }
            ((Location) v).set(newValue);
        }
    }

    public Object setWithSave(Object newValue) {
        Object v;
        if ((this.flags & 64) == 0) {
            setKindFlags();
        }
        if ((this.flags & 2) == 0) {
            return super.setWithSave(newValue);
        }
        if ((this.flags & 8) != 0) {
            v = this.value;
        } else {
            this.flags |= 8;
            Object v2 = getFieldValue();
            this.value = v2;
            v = v2;
        }
        return ((Location) v).setWithSave(newValue);
    }

    public void setRestore(Object oldValue) {
        if ((this.flags & 2) == 0) {
            super.setRestore(oldValue);
        } else {
            ((Location) this.value).setRestore(oldValue);
        }
    }

    public boolean isConstant() {
        Object v;
        if ((this.flags & 64) == 0) {
            setKindFlags();
        }
        if ((this.flags & 4) != 0) {
            return true;
        }
        if (!isIndirectLocation()) {
            return false;
        }
        if ((this.flags & 8) != 0) {
            v = this.value;
        } else {
            try {
                setup();
                Object v2 = getFieldValue();
                this.flags |= 8;
                this.value = v2;
                v = v2;
            } catch (Throwable th) {
                return false;
            }
        }
        return ((Location) v).isConstant();
    }

    public boolean isBound() {
        Object v;
        if ((this.flags & 64) == 0) {
            setKindFlags();
        }
        if ((this.flags & 4) != 0 || (this.flags & 2) == 0) {
            return true;
        }
        if ((this.flags & 8) != 0) {
            v = this.value;
        } else {
            try {
                setup();
                Object v2 = getFieldValue();
                this.flags |= 8;
                this.value = v2;
                v = v2;
            } catch (Throwable th) {
                return false;
            }
        }
        return ((Location) v).isBound();
    }

    public static FieldLocation make(Object instance, Declaration decl2) {
        Field fld = decl2.field;
        FieldLocation loc = new FieldLocation(instance, fld.getDeclaringClass(), fld.getName());
        loc.setDeclaration(decl2);
        return loc;
    }

    public static FieldLocation make(Object instance, String cname, String fldName) {
        return new FieldLocation(instance, ClassType.make(cname), fldName);
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("FieldLocation[");
        if (this.instance != null) {
            sbuf.append(this.instance);
            sbuf.append(' ');
        }
        sbuf.append(this.type == null ? "(null)" : this.type.getName());
        sbuf.append('.');
        sbuf.append(this.mname);
        sbuf.append(']');
        return sbuf.toString();
    }
}
