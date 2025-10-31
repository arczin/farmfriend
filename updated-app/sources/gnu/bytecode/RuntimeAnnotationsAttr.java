package gnu.bytecode;

public class RuntimeAnnotationsAttr extends MiscAttr {
    int numEntries = u2(0);

    public RuntimeAnnotationsAttr(String name, byte[] data, AttrContainer container) {
        super(name, data, 0, data.length);
        addToFrontOf(container);
    }

    public void print(ClassTypeWriter dst) {
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.print(getLength());
        dst.print(", number of entries: ");
        dst.println(this.numEntries);
        int saveOffset = this.offset;
        this.offset = saveOffset + 2;
        for (int i = 0; i < this.numEntries; i++) {
            printAnnotation(2, dst);
        }
        this.offset = saveOffset;
    }

    public void printAnnotation(int indentation, ClassTypeWriter dst) {
        int type_index = u2();
        dst.printSpaces(indentation);
        dst.printOptionalIndex(type_index);
        dst.print('@');
        dst.printContantUtf8AsClass(type_index);
        int num_element_value_pairs = u2();
        dst.println();
        int indentation2 = indentation + 2;
        for (int i = 0; i < num_element_value_pairs; i++) {
            int element_name_index = u2();
            dst.printSpaces(indentation2);
            dst.printOptionalIndex(element_name_index);
            dst.printConstantTersely(element_name_index, 1);
            dst.print(" => ");
            printAnnotationElementValue(indentation2, dst);
            dst.println();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00a6, code lost:
        if (r1 != 0) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a8, code lost:
        r1 = 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00a9, code lost:
        if (r1 != 0) goto L_0x00ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00ab, code lost:
        r1 = 6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00ac, code lost:
        if (r1 != 0) goto L_0x00af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00ae, code lost:
        r1 = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00af, code lost:
        if (r1 != 0) goto L_0x00b2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00b1, code lost:
        r1 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00b2, code lost:
        r3 = u2();
        r4 = r12.getCpoolEntry(r3);
        r12.printOptionalIndex(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00bf, code lost:
        if (r0 != 90) goto L_0x00e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00c1, code lost:
        if (r4 == null) goto L_0x00e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00c8, code lost:
        if (r4.getTag() != 3) goto L_0x00e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ca, code lost:
        r5 = (gnu.bytecode.CpoolValue1) r4;
        r6 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00d0, code lost:
        if (r5.value == 0) goto L_0x00d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00d4, code lost:
        if (r6.value != 1) goto L_0x00e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00d8, code lost:
        if (r6.value != 0) goto L_0x00dd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00da, code lost:
        r2 = "false";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00dd, code lost:
        r2 = "true";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00df, code lost:
        r12.print(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00e3, code lost:
        r12.printConstantTersely(r3, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void printAnnotationElementValue(int r11, gnu.bytecode.ClassTypeWriter r12) {
        /*
            r10 = this;
            int r0 = r10.u1()
            int r1 = r12.flags
            r1 = r1 & 8
            if (r1 == 0) goto L_0x0024
            java.lang.String r1 = "[kind:"
            r12.print(r1)
            r1 = 65
            if (r0 < r1) goto L_0x001c
            r1 = 122(0x7a, float:1.71E-43)
            if (r0 > r1) goto L_0x001c
            char r1 = (char) r0
            r12.print(r1)
            goto L_0x001f
        L_0x001c:
            r12.print(r0)
        L_0x001f:
            java.lang.String r1 = "] "
            r12.print(r1)
        L_0x0024:
            r1 = 0
            r2 = 1
            r3 = 0
            r4 = 0
            switch(r0) {
                case 64: goto L_0x00e7;
                case 66: goto L_0x00a3;
                case 67: goto L_0x00a3;
                case 68: goto L_0x00a9;
                case 70: goto L_0x00ac;
                case 73: goto L_0x00a3;
                case 74: goto L_0x00a6;
                case 83: goto L_0x00a3;
                case 90: goto L_0x00a3;
                case 91: goto L_0x0078;
                case 99: goto L_0x0069;
                case 101: goto L_0x002d;
                case 115: goto L_0x00af;
                default: goto L_0x002b;
            }
        L_0x002b:
            goto L_0x00f9
        L_0x002d:
            int r5 = r10.u2()
            int r6 = r10.u2()
            java.lang.String r7 = "enum["
            r12.print(r7)
            int r7 = r12.flags
            r7 = r7 & 8
            if (r7 == 0) goto L_0x0045
            java.lang.String r7 = "type:"
            r12.print(r7)
        L_0x0045:
            r12.printOptionalIndex((int) r5)
            r12.printContantUtf8AsClass(r5)
            int r7 = r12.flags
            r7 = r7 & 8
            if (r7 == 0) goto L_0x0057
            java.lang.String r7 = " value:"
            r12.print(r7)
            goto L_0x005c
        L_0x0057:
            r7 = 32
            r12.print(r7)
        L_0x005c:
            r12.printOptionalIndex((int) r6)
            r12.printConstantTersely((int) r6, (int) r2)
            java.lang.String r2 = "]"
            r12.print(r2)
            goto L_0x00f9
        L_0x0069:
            r2 = r3
            r3 = r4
            r5 = r4
            int r6 = r10.u2()
            r12.printOptionalIndex((int) r6)
            r12.printContantUtf8AsClass(r6)
            goto L_0x00f9
        L_0x0078:
            r2 = r3
            r3 = r4
            r5 = r4
            r6 = r4
            int r7 = r10.u2()
            java.lang.String r8 = "array length:"
            r12.print(r8)
            r12.print(r7)
            r8 = 0
        L_0x0089:
            if (r8 >= r7) goto L_0x00f9
            r12.println()
            int r9 = r11 + 2
            r12.printSpaces(r9)
            r12.print(r8)
            java.lang.String r9 = ": "
            r12.print(r9)
            int r9 = r11 + 2
            r10.printAnnotationElementValue(r9, r12)
            int r8 = r8 + 1
            goto L_0x0089
        L_0x00a3:
            if (r1 != 0) goto L_0x00a6
            r1 = 3
        L_0x00a6:
            if (r1 != 0) goto L_0x00a9
            r1 = 5
        L_0x00a9:
            if (r1 != 0) goto L_0x00ac
            r1 = 6
        L_0x00ac:
            if (r1 != 0) goto L_0x00af
            r1 = 4
        L_0x00af:
            if (r1 != 0) goto L_0x00b2
            r1 = 1
        L_0x00b2:
            int r3 = r10.u2()
            gnu.bytecode.CpoolEntry r4 = r12.getCpoolEntry(r3)
            r12.printOptionalIndex((gnu.bytecode.CpoolEntry) r4)
            r5 = 90
            if (r0 != r5) goto L_0x00e3
            if (r4 == 0) goto L_0x00e3
            int r5 = r4.getTag()
            r6 = 3
            if (r5 != r6) goto L_0x00e3
            r5 = r4
            gnu.bytecode.CpoolValue1 r5 = (gnu.bytecode.CpoolValue1) r5
            r6 = r5
            int r5 = r5.value
            if (r5 == 0) goto L_0x00d6
            int r5 = r6.value
            if (r5 != r2) goto L_0x00e3
        L_0x00d6:
            int r2 = r6.value
            if (r2 != 0) goto L_0x00dd
            java.lang.String r2 = "false"
            goto L_0x00df
        L_0x00dd:
            java.lang.String r2 = "true"
        L_0x00df:
            r12.print(r2)
            goto L_0x00f9
        L_0x00e3:
            r12.printConstantTersely((int) r3, (int) r1)
            goto L_0x00f9
        L_0x00e7:
            r2 = r3
            r3 = r4
            r5 = r4
            r6 = r4
            r12.println()
            int r7 = r11 + 2
            r12.printSpaces(r7)
            int r7 = r11 + 2
            r10.printAnnotation(r7, r12)
        L_0x00f9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.RuntimeAnnotationsAttr.printAnnotationElementValue(int, gnu.bytecode.ClassTypeWriter):void");
    }
}
