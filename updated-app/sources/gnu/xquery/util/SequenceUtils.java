package gnu.xquery.util;

import gnu.kawa.xml.KAttr;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.NodeType;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.xml.NodeTree;

public class SequenceUtils {
    public static final NodeType textOrElement = new NodeType("element-or-text", 3);

    public static boolean isZeroOrOne(Object arg) {
        return !(arg instanceof Values) || ((Values) arg).isEmpty();
    }

    static Object coerceToZeroOrOne(Object arg, String functionName, int iarg) {
        if (isZeroOrOne(arg)) {
            return arg;
        }
        throw new WrongType(functionName, iarg, arg, "xs:item()?");
    }

    public static Object zeroOrOne(Object arg) {
        return coerceToZeroOrOne(arg, "zero-or-one", 1);
    }

    public static Object oneOrMore(Object arg) {
        if (!(arg instanceof Values) || !((Values) arg).isEmpty()) {
            return arg;
        }
        throw new IllegalArgumentException();
    }

    public static Object exactlyOne(Object arg) {
        if (!(arg instanceof Values)) {
            return arg;
        }
        throw new IllegalArgumentException();
    }

    public static boolean isEmptySequence(Object arg) {
        return (arg instanceof Values) && ((Values) arg).isEmpty();
    }

    public static boolean exists(Object arg) {
        return !(arg instanceof Values) || !((Values) arg).isEmpty();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002d, code lost:
        if (r14 == r5) goto L_0x002f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void insertBefore$X(java.lang.Object r17, long r18, java.lang.Object r20, gnu.mapping.CallContext r21) {
        /*
            r0 = r17
            r1 = r20
            r2 = r21
            gnu.lists.Consumer r3 = r2.consumer
            r4 = 0
            r5 = 0
            int r7 = (r18 > r5 ? 1 : (r18 == r5 ? 0 : -1))
            if (r7 > 0) goto L_0x0012
            r5 = 1
            goto L_0x0014
        L_0x0012:
            r5 = r18
        L_0x0014:
            boolean r7 = r0 instanceof gnu.mapping.Values
            r8 = 1
            if (r7 == 0) goto L_0x003c
            r7 = r0
            gnu.mapping.Values r7 = (gnu.mapping.Values) r7
            r10 = 0
            r11 = 0
        L_0x0020:
            int r13 = r7.nextPos(r10)
            if (r13 != 0) goto L_0x0028
            if (r4 == 0) goto L_0x002f
        L_0x0028:
            long r14 = r11 + r8
            r11 = r14
            int r16 = (r14 > r5 ? 1 : (r14 == r5 ? 0 : -1))
            if (r16 != 0) goto L_0x0033
        L_0x002f:
            gnu.mapping.Values.writeValues(r1, r3)
            r4 = 1
        L_0x0033:
            if (r13 != 0) goto L_0x0037
            goto L_0x004d
        L_0x0037:
            r7.consumePosRange(r10, r13, r3)
            r10 = r13
            goto L_0x0020
        L_0x003c:
            int r7 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r7 > 0) goto L_0x0043
            gnu.mapping.Values.writeValues(r1, r3)
        L_0x0043:
            r3.writeObject(r0)
            int r7 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r7 <= 0) goto L_0x004d
            gnu.mapping.Values.writeValues(r1, r3)
        L_0x004d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.util.SequenceUtils.insertBefore$X(java.lang.Object, long, java.lang.Object, gnu.mapping.CallContext):void");
    }

    public static void remove$X(Object arg, long position, CallContext ctx) {
        Consumer out = ctx.consumer;
        if (arg instanceof Values) {
            Values values = (Values) arg;
            int ipos = 0;
            long i = 0;
            while (true) {
                int next = values.nextPos(ipos);
                if (next != 0) {
                    long j = i + 1;
                    i = j;
                    if (j != position) {
                        values.consumePosRange(ipos, next, out);
                    }
                    ipos = next;
                } else {
                    return;
                }
            }
        } else if (position != 1) {
            out.writeObject(arg);
        }
    }

    public static void reverse$X(Object arg, CallContext ctx) {
        int n;
        Consumer out = ctx.consumer;
        if (!(arg instanceof Values)) {
            out.writeObject(arg);
            return;
        }
        Values vals = (Values) arg;
        int ipos = 0;
        int[] poses = new int[100];
        int n2 = 0;
        while (true) {
            if (n2 >= poses.length) {
                int[] t = new int[(n2 * 2)];
                System.arraycopy(poses, 0, t, 0, n2);
                poses = t;
            }
            n = n2 + 1;
            poses[n2] = ipos;
            ipos = vals.nextPos(ipos);
            if (ipos == 0) {
                break;
            }
            n2 = n;
        }
        int i = n - 1;
        while (true) {
            i--;
            if (i >= 0) {
                vals.consumePosRange(poses[i], poses[i + 1], out);
            } else {
                return;
            }
        }
    }

    public static void indexOf$X(Object seqParam, Object srchParam, NamedCollator collator, CallContext ctx) {
        Consumer out = ctx.consumer;
        if (seqParam instanceof Values) {
            Values vals = (Values) seqParam;
            int ipos = vals.startPos();
            int i = 1;
            while (true) {
                int nextPos = vals.nextPos(ipos);
                ipos = nextPos;
                if (nextPos != 0) {
                    if (Compare.apply(72, vals.getPosPrevious(ipos), srchParam, collator)) {
                        out.writeInt(i);
                    }
                    i++;
                } else {
                    return;
                }
            }
        } else if (Compare.apply(72, seqParam, srchParam, collator)) {
            out.writeInt(1);
        }
    }

    public static boolean deepEqualChildren(NodeTree seq1, int ipos1, NodeTree seq2, int ipos2, NamedCollator collator) {
        NodeType filter = textOrElement;
        int child1 = seq1.firstChildPos(ipos1, filter);
        int child2 = seq2.firstChildPos(ipos2, filter);
        while (child1 != 0 && child2 != 0) {
            if (!deepEqual(seq1, child1, seq2, child2, collator)) {
                return false;
            }
            child1 = seq1.nextMatching(child1, filter, -1, false);
            child2 = seq2.nextMatching(child2, filter, -1, false);
        }
        if (child1 == child2) {
            return true;
        }
        return false;
    }

    public static boolean deepEqual(NodeTree seq1, int ipos1, NodeTree seq2, int ipos2, NamedCollator collator) {
        String aval2;
        String loc1;
        NodeTree nodeTree = seq1;
        NodeTree nodeTree2 = seq2;
        NamedCollator namedCollator = collator;
        int kind1 = seq1.getNextKind(ipos1);
        int kind2 = seq2.getNextKind(ipos2);
        boolean z = false;
        switch (kind1) {
            case 33:
                if (kind1 == kind2 && (aval2 = seq1.posLocalName(ipos1)) == seq2.posLocalName(ipos2) && seq1.posNamespaceURI(ipos1) == seq2.posNamespaceURI(ipos2)) {
                    int attr1 = seq1.firstAttributePos(ipos1);
                    int nattr1 = 0;
                    while (true) {
                        if (attr1 == 0) {
                            int i = ipos2;
                            loc1 = aval2;
                        } else if (nodeTree.getNextKind(attr1) != 35) {
                            int i2 = ipos2;
                            loc1 = aval2;
                        } else {
                            nattr1++;
                            int i3 = ipos2;
                            int attr2 = nodeTree2.getAttributeI(i3, nodeTree.posNamespaceURI(attr1), nodeTree.posLocalName(attr1));
                            if (attr2 == 0) {
                                return z;
                            }
                            String loc12 = aval2;
                            if (!deepEqualItems(KNode.getNodeValue(nodeTree, attr1), KNode.getNodeValue(nodeTree2, attr2), namedCollator)) {
                                return false;
                            }
                            attr1 = nodeTree.nextPos(attr1);
                            aval2 = loc12;
                            z = false;
                        }
                    }
                    int nattr2 = seq2.getAttributeCount(ipos2);
                    if (nattr1 == nattr2) {
                        String str = loc1;
                        int i4 = nattr1;
                        int nattr12 = nattr2;
                        int nattr22 = i4;
                        break;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            case 34:
                int i5 = ipos2;
                break;
            case 35:
                if (seq1.posLocalName(ipos1) == seq2.posLocalName(ipos2) && seq1.posNamespaceURI(ipos1) == seq2.posNamespaceURI(ipos2)) {
                    return deepEqualItems(KAttr.getObjectValue(seq1, ipos1), KAttr.getObjectValue(seq2, ipos2), namedCollator);
                }
                return false;
            case 37:
                if (!seq1.posTarget(ipos1).equals(seq2.posTarget(ipos2))) {
                    return false;
                }
                return KNode.getNodeValue(seq1, ipos1).equals(KNode.getNodeValue(seq2, ipos2));
            default:
                int i6 = ipos2;
                if (kind1 != kind2) {
                    return false;
                }
                return KNode.getNodeValue(seq1, ipos1).equals(KNode.getNodeValue(seq2, ipos2));
        }
        return deepEqualChildren(seq1, ipos1, seq2, ipos2, collator);
    }

    public static boolean deepEqualItems(Object arg1, Object arg2, NamedCollator collator) {
        if (!NumberValue.isNaN(arg1) || !NumberValue.isNaN(arg2)) {
            return Compare.atomicCompare(8, arg1, arg2, collator);
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00c2, code lost:
        if (r4 != r5) goto L_0x00c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00c4, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00c6, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean deepEqual(java.lang.Object r17, java.lang.Object r18, gnu.xquery.util.NamedCollator r19) {
        /*
            r1 = r17
            r2 = r18
            r0 = 1
            if (r1 != r2) goto L_0x0008
            return r0
        L_0x0008:
            r3 = 0
            if (r1 == 0) goto L_0x00cc
            gnu.mapping.Values r4 = gnu.mapping.Values.empty
            if (r1 != r4) goto L_0x0014
            r8 = r19
            r1 = 0
            goto L_0x00cf
        L_0x0014:
            if (r2 == 0) goto L_0x00c8
            gnu.mapping.Values r4 = gnu.mapping.Values.empty
            if (r2 != r4) goto L_0x001e
            r8 = r19
            goto L_0x00ca
        L_0x001e:
            r4 = 1
            r5 = 1
            boolean r6 = r1 instanceof gnu.mapping.Values
            boolean r7 = r2 instanceof gnu.mapping.Values
            r8 = 0
            if (r6 == 0) goto L_0x002b
            r9 = r1
            gnu.mapping.Values r9 = (gnu.mapping.Values) r9
            goto L_0x002c
        L_0x002b:
            r9 = r8
        L_0x002c:
            if (r7 == 0) goto L_0x0031
            r8 = r2
            gnu.mapping.Values r8 = (gnu.mapping.Values) r8
        L_0x0031:
            r10 = 1
        L_0x0032:
            if (r6 == 0) goto L_0x003e
            if (r10 == 0) goto L_0x003a
            int r4 = r9.startPos()
        L_0x003a:
            int r4 = r9.nextPos(r4)
        L_0x003e:
            if (r7 == 0) goto L_0x004a
            if (r10 == 0) goto L_0x0046
            int r5 = r8.startPos()
        L_0x0046:
            int r5 = r8.nextPos(r5)
        L_0x004a:
            if (r4 == 0) goto L_0x00be
            if (r5 != 0) goto L_0x0054
            r16 = r8
            r8 = r19
            goto L_0x00c2
        L_0x0054:
            if (r6 == 0) goto L_0x005b
            java.lang.Object r11 = r9.getPosPrevious(r4)
            goto L_0x005c
        L_0x005b:
            r11 = r1
        L_0x005c:
            if (r7 == 0) goto L_0x0063
            java.lang.Object r12 = r8.getPosPrevious(r5)
            goto L_0x0064
        L_0x0063:
            r12 = r2
        L_0x0064:
            boolean r13 = r11 instanceof gnu.kawa.xml.KNode
            if (r13 != 0) goto L_0x007c
            boolean r13 = r12 instanceof gnu.kawa.xml.KNode
            if (r13 != 0) goto L_0x007c
            boolean r13 = deepEqualItems(r17, r18, r19)     // Catch:{ all -> 0x0078 }
            if (r13 != 0) goto L_0x0073
            return r3
        L_0x0073:
            r16 = r8
            r8 = r19
            goto L_0x00a3
        L_0x0078:
            r0 = move-exception
            r13 = r0
            r0 = r13
            return r3
        L_0x007c:
            boolean r13 = r11 instanceof gnu.kawa.xml.KNode
            if (r13 == 0) goto L_0x00b8
            boolean r13 = r12 instanceof gnu.kawa.xml.KNode
            if (r13 == 0) goto L_0x00b8
            r13 = r11
            gnu.kawa.xml.KNode r13 = (gnu.kawa.xml.KNode) r13
            r14 = r12
            gnu.kawa.xml.KNode r14 = (gnu.kawa.xml.KNode) r14
            gnu.lists.AbstractSequence r15 = r13.sequence
            gnu.xml.NodeTree r15 = (gnu.xml.NodeTree) r15
            int r0 = r13.ipos
            gnu.lists.AbstractSequence r3 = r14.sequence
            gnu.xml.NodeTree r3 = (gnu.xml.NodeTree) r3
            int r1 = r14.ipos
            r16 = r8
            r8 = r19
            boolean r0 = deepEqual(r15, r0, r3, r1, r8)
            if (r0 != 0) goto L_0x00a2
            r1 = 0
            return r1
        L_0x00a2:
        L_0x00a3:
            if (r10 == 0) goto L_0x00b0
            r0 = 0
            if (r6 != 0) goto L_0x00a9
            r4 = 0
        L_0x00a9:
            if (r7 != 0) goto L_0x00af
            r1 = 0
            r10 = r0
            r5 = r1
            goto L_0x00b0
        L_0x00af:
            r10 = r0
        L_0x00b0:
            r0 = 1
            r3 = 0
            r1 = r17
            r8 = r16
            goto L_0x0032
        L_0x00b8:
            r16 = r8
            r8 = r19
            r1 = 0
            return r1
        L_0x00be:
            r16 = r8
            r8 = r19
        L_0x00c2:
            if (r4 != r5) goto L_0x00c6
            r0 = 1
            goto L_0x00c7
        L_0x00c6:
            r0 = 0
        L_0x00c7:
            return r0
        L_0x00c8:
            r8 = r19
        L_0x00ca:
            r1 = 0
            return r1
        L_0x00cc:
            r8 = r19
            r1 = 0
        L_0x00cf:
            if (r2 == 0) goto L_0x00d8
            gnu.mapping.Values r0 = gnu.mapping.Values.empty
            if (r2 != r0) goto L_0x00d6
            goto L_0x00d8
        L_0x00d6:
            r0 = 0
            goto L_0x00d9
        L_0x00d8:
            r0 = 1
        L_0x00d9:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.util.SequenceUtils.deepEqual(java.lang.Object, java.lang.Object, gnu.xquery.util.NamedCollator):boolean");
    }
}
