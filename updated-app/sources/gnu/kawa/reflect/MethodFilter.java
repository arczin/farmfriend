package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Filter;
import gnu.bytecode.ObjectType;

/* compiled from: ClassMethods */
class MethodFilter implements Filter {
    ClassType caller;
    int modifiers;
    int modmask;
    String name;
    int nlen;
    ObjectType receiver;

    public MethodFilter(String name2, int modifiers2, int modmask2, ClassType caller2, ObjectType receiver2) {
        this.name = name2;
        this.nlen = name2.length();
        this.modifiers = modifiers2;
        this.modmask = modmask2;
        this.caller = caller2;
        this.receiver = receiver2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0047, code lost:
        if (r7 != 'X') goto L_0x0049;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean select(java.lang.Object r11) {
        /*
            r10 = this;
            r0 = r11
            gnu.bytecode.Method r0 = (gnu.bytecode.Method) r0
            java.lang.String r1 = r0.getName()
            int r2 = r0.getModifiers()
            int r3 = r10.modmask
            r3 = r3 & r2
            int r4 = r10.modifiers
            r5 = 0
            if (r3 != r4) goto L_0x007b
            r3 = r2 & 4096(0x1000, float:5.74E-42)
            if (r3 != 0) goto L_0x007b
            java.lang.String r3 = r10.name
            boolean r3 = r1.startsWith(r3)
            if (r3 != 0) goto L_0x0020
            goto L_0x007b
        L_0x0020:
            int r3 = r1.length()
            int r4 = r10.nlen
            r6 = 1
            if (r3 == r4) goto L_0x0058
            int r4 = r10.nlen
            int r4 = r4 + 2
            if (r3 != r4) goto L_0x0049
            int r4 = r10.nlen
            char r4 = r1.charAt(r4)
            r7 = 36
            if (r4 != r7) goto L_0x0049
            int r4 = r10.nlen
            int r4 = r4 + r6
            char r4 = r1.charAt(r4)
            r7 = r4
            r8 = 86
            if (r4 == r8) goto L_0x0058
            r4 = 88
            if (r7 == r4) goto L_0x0058
        L_0x0049:
            int r4 = r10.nlen
            int r4 = r4 + 4
            if (r3 != r4) goto L_0x0057
            java.lang.String r4 = "$V$X"
            boolean r4 = r1.endsWith(r4)
            if (r4 != 0) goto L_0x0058
        L_0x0057:
            return r5
        L_0x0058:
            gnu.bytecode.ObjectType r4 = r10.receiver
            boolean r4 = r4 instanceof gnu.bytecode.ClassType
            if (r4 == 0) goto L_0x0063
            gnu.bytecode.ObjectType r4 = r10.receiver
            gnu.bytecode.ClassType r4 = (gnu.bytecode.ClassType) r4
            goto L_0x0067
        L_0x0063:
            gnu.bytecode.ClassType r4 = r0.getDeclaringClass()
        L_0x0067:
            gnu.bytecode.ClassType r7 = r10.caller
            if (r7 == 0) goto L_0x0079
            gnu.bytecode.ClassType r7 = r10.caller
            gnu.bytecode.ObjectType r8 = r10.receiver
            int r9 = r0.getModifiers()
            boolean r7 = r7.isAccessible(r4, r8, r9)
            if (r7 == 0) goto L_0x007a
        L_0x0079:
            r5 = 1
        L_0x007a:
            return r5
        L_0x007b:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.reflect.MethodFilter.select(java.lang.Object):boolean");
    }
}
