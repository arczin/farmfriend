package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

public class CpoolClass extends CpoolEntry {
    ObjectType clas;
    CpoolUtf8 name;

    CpoolClass() {
    }

    CpoolClass(ConstantPool cpool, int hash, CpoolUtf8 n) {
        super(cpool, hash);
        this.name = n;
    }

    public int getTag() {
        return 7;
    }

    public final CpoolUtf8 getName() {
        return this.name;
    }

    public final String getStringName() {
        return this.name.string;
    }

    public final String getClassName() {
        return this.name.string.replace('/', '.');
    }

    /* JADX WARNING: type inference failed for: r2v4, types: [gnu.bytecode.Type] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final gnu.bytecode.ObjectType getClassType() {
        /*
            r4 = this;
            gnu.bytecode.ObjectType r0 = r4.clas
            if (r0 == 0) goto L_0x0005
            return r0
        L_0x0005:
            gnu.bytecode.CpoolUtf8 r1 = r4.name
            java.lang.String r1 = r1.string
            r2 = 0
            char r2 = r1.charAt(r2)
            r3 = 91
            if (r2 != r3) goto L_0x001a
            gnu.bytecode.Type r2 = gnu.bytecode.Type.signatureToType(r1)
            r0 = r2
            gnu.bytecode.ObjectType r0 = (gnu.bytecode.ObjectType) r0
            goto L_0x0026
        L_0x001a:
            r2 = 47
            r3 = 46
            java.lang.String r2 = r1.replace(r2, r3)
            gnu.bytecode.ClassType r0 = gnu.bytecode.ClassType.make(r2)
        L_0x0026:
            r4.clas = r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.CpoolClass.getClassType():gnu.bytecode.ObjectType");
    }

    static final int hashCode(CpoolUtf8 name2) {
        return name2.hashCode() ^ 3855;
    }

    public int hashCode() {
        if (this.hash == 0) {
            this.hash = hashCode(this.name);
        }
        return this.hash;
    }

    /* access modifiers changed from: package-private */
    public void write(DataOutputStream dstr) throws IOException {
        dstr.writeByte(7);
        dstr.writeShort(this.name.index);
    }

    public void print(ClassTypeWriter dst, int verbosity) {
        if (verbosity == 1) {
            dst.print("Class ");
        } else if (verbosity > 1) {
            dst.print("Class name: ");
            dst.printOptionalIndex((CpoolEntry) this.name);
        }
        String str = this.name.string;
        int nlen = str.length();
        if (nlen <= 1 || str.charAt(0) != '[') {
            dst.print(str.replace('/', '.'));
        } else {
            Type.printSignature(str, 0, nlen, dst);
        }
    }
}
