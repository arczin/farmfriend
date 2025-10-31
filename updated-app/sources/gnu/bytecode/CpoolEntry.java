package gnu.bytecode;

import androidx.appcompat.widget.ActivityChooserView;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class CpoolEntry {
    int hash;
    public int index;
    CpoolEntry next;

    public abstract int getTag();

    public abstract void print(ClassTypeWriter classTypeWriter, int i);

    /* access modifiers changed from: package-private */
    public abstract void write(DataOutputStream dataOutputStream) throws IOException;

    public int getIndex() {
        return this.index;
    }

    public int hashCode() {
        return this.hash;
    }

    /* access modifiers changed from: package-private */
    public void add_hashed(ConstantPool cpool) {
        CpoolEntry[] hashTab = cpool.hashTab;
        int index2 = (this.hash & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) % hashTab.length;
        this.next = hashTab[index2];
        hashTab[index2] = this;
    }

    protected CpoolEntry() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0052, code lost:
        if (((double) r6.index) >= (r2 * 0.6d)) goto L_0x0054;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public CpoolEntry(gnu.bytecode.ConstantPool r7, int r8) {
        /*
            r6 = this;
            r6.<init>()
            r6.hash = r8
            boolean r0 = r7.locked
            if (r0 != 0) goto L_0x0061
            int r0 = r7.count
            int r0 = r0 + 1
            r7.count = r0
            r6.index = r0
            gnu.bytecode.CpoolEntry[] r0 = r7.pool
            if (r0 != 0) goto L_0x001c
            r0 = 60
            gnu.bytecode.CpoolEntry[] r0 = new gnu.bytecode.CpoolEntry[r0]
            r7.pool = r0
            goto L_0x003b
        L_0x001c:
            int r0 = r6.index
            gnu.bytecode.CpoolEntry[] r1 = r7.pool
            int r1 = r1.length
            if (r0 < r1) goto L_0x003b
            gnu.bytecode.CpoolEntry[] r0 = r7.pool
            int r0 = r0.length
            gnu.bytecode.CpoolEntry[] r1 = r7.pool
            int r1 = r1.length
            int r1 = r1 * 2
            gnu.bytecode.CpoolEntry[] r2 = new gnu.bytecode.CpoolEntry[r1]
            r3 = 0
        L_0x002e:
            if (r3 >= r0) goto L_0x0039
            gnu.bytecode.CpoolEntry[] r4 = r7.pool
            r4 = r4[r3]
            r2[r3] = r4
            int r3 = r3 + 1
            goto L_0x002e
        L_0x0039:
            r7.pool = r2
        L_0x003b:
            gnu.bytecode.CpoolEntry[] r0 = r7.hashTab
            if (r0 == 0) goto L_0x0054
            int r0 = r6.index
            double r0 = (double) r0
            gnu.bytecode.CpoolEntry[] r2 = r7.hashTab
            int r2 = r2.length
            double r2 = (double) r2
            r4 = 4603579539098121011(0x3fe3333333333333, double:0.6)
            java.lang.Double.isNaN(r2)
            double r2 = r2 * r4
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 < 0) goto L_0x0057
        L_0x0054:
            r7.rehash()
        L_0x0057:
            gnu.bytecode.CpoolEntry[] r0 = r7.pool
            int r1 = r6.index
            r0[r1] = r6
            r6.add_hashed(r7)
            return
        L_0x0061:
            java.lang.Error r0 = new java.lang.Error
            java.lang.String r1 = "adding new entry to locked contant pool"
            r0.<init>(r1)
            goto L_0x006a
        L_0x0069:
            throw r0
        L_0x006a:
            goto L_0x0069
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.CpoolEntry.<init>(gnu.bytecode.ConstantPool, int):void");
    }
}
