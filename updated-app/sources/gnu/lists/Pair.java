package gnu.lists;

import androidx.appcompat.widget.ActivityChooserView;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class Pair extends LList implements Externalizable {
    protected Object car;
    protected Object cdr;

    public Pair(Object carval, Object cdrval) {
        this.car = carval;
        this.cdr = cdrval;
    }

    public Pair() {
    }

    public Object getCar() {
        return this.car;
    }

    public Object getCdr() {
        return this.cdr;
    }

    public void setCar(Object car2) {
        this.car = car2;
    }

    public void setCdr(Object cdr2) {
        this.cdr = cdr2;
    }

    public void setCdrBackdoor(Object cdr2) {
        this.cdr = cdr2;
    }

    public int size() {
        int n = listLength(this, true);
        if (n >= 0) {
            return n;
        }
        if (n == -1) {
            return ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        }
        throw new RuntimeException("not a true list");
    }

    public boolean isEmpty() {
        return false;
    }

    public int length() {
        int n = 0;
        Object slow = this;
        Object fast = this;
        while (fast != Empty) {
            if (fast instanceof Pair) {
                Pair fast_pair = (Pair) fast;
                if (fast_pair.cdr == Empty) {
                    return n + 1;
                }
                if (fast == slow && n > 0) {
                    return -1;
                }
                if (!(fast_pair.cdr instanceof Pair)) {
                    n++;
                    fast = fast_pair.cdr;
                } else if (!(slow instanceof Pair)) {
                    return -2;
                } else {
                    slow = ((Pair) slow).cdr;
                    fast = ((Pair) fast_pair.cdr).cdr;
                    n += 2;
                }
            } else if (!(fast instanceof Sequence)) {
                return -2;
            } else {
                int j = ((Sequence) fast).size();
                return j >= 0 ? n + j : j;
            }
        }
        return n;
    }

    public boolean hasNext(int ipos) {
        if (ipos <= 0) {
            return ipos == 0;
        }
        return PositionManager.getPositionObject(ipos).hasNext();
    }

    public int nextPos(int ipos) {
        if (ipos <= 0) {
            if (ipos < 0) {
                return 0;
            }
            return PositionManager.manager.register(new LListPosition(this, 1, true));
        } else if (((LListPosition) PositionManager.getPositionObject(ipos)).gotoNext()) {
            return ipos;
        } else {
            return 0;
        }
    }

    public Object getPosNext(int ipos) {
        if (ipos <= 0) {
            return ipos == 0 ? this.car : eofValue;
        }
        return PositionManager.getPositionObject(ipos).getNext();
    }

    public Object getPosPrevious(int ipos) {
        if (ipos <= 0) {
            return ipos == 0 ? eofValue : lastPair().car;
        }
        return PositionManager.getPositionObject(ipos).getPrevious();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: gnu.lists.Pair} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final gnu.lists.Pair lastPair() {
        /*
            r3 = this;
            r0 = r3
        L_0x0001:
            java.lang.Object r1 = r0.cdr
            boolean r2 = r1 instanceof gnu.lists.Pair
            if (r2 == 0) goto L_0x000b
            r0 = r1
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
            goto L_0x0001
        L_0x000b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.lists.Pair.lastPair():gnu.lists.Pair");
    }

    public int hashCode() {
        int hash = 1;
        Object list = this;
        while (list instanceof Pair) {
            Pair pair = (Pair) list;
            Object obj = pair.car;
            hash = (hash * 31) + (obj == null ? 0 : obj.hashCode());
            list = pair.cdr;
        }
        if (list == LList.Empty || list == null) {
            return hash;
        }
        return hash ^ list.hashCode();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0039, code lost:
        return r2.equals(r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean equals(gnu.lists.Pair r5, gnu.lists.Pair r6) {
        /*
            r0 = 1
            if (r5 != r6) goto L_0x0004
            return r0
        L_0x0004:
            r1 = 0
            if (r5 == 0) goto L_0x003b
            if (r6 != 0) goto L_0x000a
            goto L_0x003b
        L_0x000a:
            java.lang.Object r2 = r5.car
            java.lang.Object r3 = r6.car
            if (r2 == r3) goto L_0x0019
            if (r2 == 0) goto L_0x0018
            boolean r4 = r2.equals(r3)
            if (r4 != 0) goto L_0x0019
        L_0x0018:
            return r1
        L_0x0019:
            java.lang.Object r2 = r5.cdr
            java.lang.Object r3 = r6.cdr
            if (r2 != r3) goto L_0x0020
            return r0
        L_0x0020:
            if (r2 == 0) goto L_0x003a
            if (r3 != 0) goto L_0x0025
            goto L_0x003a
        L_0x0025:
            boolean r4 = r2 instanceof gnu.lists.Pair
            if (r4 == 0) goto L_0x0035
            boolean r4 = r3 instanceof gnu.lists.Pair
            if (r4 != 0) goto L_0x002e
            goto L_0x0035
        L_0x002e:
            r5 = r2
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            r6 = r3
            gnu.lists.Pair r6 = (gnu.lists.Pair) r6
            goto L_0x000a
        L_0x0035:
            boolean r0 = r2.equals(r3)
            return r0
        L_0x003a:
            return r1
        L_0x003b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.lists.Pair.equals(gnu.lists.Pair, gnu.lists.Pair):boolean");
    }

    public static int compareTo(Pair pair1, Pair pair2) {
        Object x1;
        Object x2;
        if (pair1 == pair2) {
            return 0;
        }
        if (pair1 == null) {
            return -1;
        }
        if (pair2 == null) {
            return 1;
        }
        while (true) {
            int d = ((Comparable) pair1.car).compareTo((Comparable) pair2.car);
            if (d != 0) {
                return d;
            }
            x1 = pair1.cdr;
            x2 = pair2.cdr;
            if (x1 == x2) {
                return 0;
            }
            if (x1 == null) {
                return -1;
            }
            if (x2 == null) {
                return 1;
            }
            if ((x1 instanceof Pair) && (x2 instanceof Pair)) {
                pair1 = (Pair) x1;
                pair2 = (Pair) x2;
            }
        }
        return ((Comparable) x1).compareTo((Comparable) x2);
    }

    public int compareTo(Object obj) {
        if (obj == Empty) {
            return 1;
        }
        return compareTo(this, (Pair) obj);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: gnu.lists.Pair} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object get(int r4) {
        /*
            r3 = this;
            r0 = r3
            r1 = r4
        L_0x0002:
            if (r1 <= 0) goto L_0x0021
            int r1 = r1 + -1
            java.lang.Object r2 = r0.cdr
            boolean r2 = r2 instanceof gnu.lists.Pair
            if (r2 == 0) goto L_0x0012
            java.lang.Object r2 = r0.cdr
            r0 = r2
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
            goto L_0x0002
        L_0x0012:
            java.lang.Object r2 = r0.cdr
            boolean r2 = r2 instanceof gnu.lists.Sequence
            if (r2 == 0) goto L_0x0021
            java.lang.Object r2 = r0.cdr
            gnu.lists.Sequence r2 = (gnu.lists.Sequence) r2
            java.lang.Object r2 = r2.get(r1)
            return r2
        L_0x0021:
            if (r1 != 0) goto L_0x0026
            java.lang.Object r2 = r0.car
            return r2
        L_0x0026:
            java.lang.IndexOutOfBoundsException r2 = new java.lang.IndexOutOfBoundsException
            r2.<init>()
            goto L_0x002d
        L_0x002c:
            throw r2
        L_0x002d:
            goto L_0x002c
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.lists.Pair.get(int):java.lang.Object");
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Pair)) {
            return false;
        }
        return equals(this, (Pair) obj);
    }

    public static Pair make(Object car2, Object cdr2) {
        return new Pair(car2, cdr2);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: gnu.lists.Sequence} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object[] toArray() {
        /*
            r6 = this;
            int r0 = r6.size()
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r3 = r6
        L_0x0008:
            if (r2 >= r0) goto L_0x001d
            boolean r4 = r3 instanceof gnu.lists.Pair
            if (r4 == 0) goto L_0x001d
            r4 = r3
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4
            java.lang.Object r5 = r4.car
            r1[r2] = r5
            java.lang.Object r5 = r4.cdr
            r3 = r5
            gnu.lists.Sequence r3 = (gnu.lists.Sequence) r3
            int r2 = r2 + 1
            goto L_0x0008
        L_0x001d:
            r4 = r2
        L_0x001e:
            if (r2 >= r0) goto L_0x002b
            int r5 = r2 - r4
            java.lang.Object r5 = r3.get(r5)
            r1[r2] = r5
            int r2 = r2 + 1
            goto L_0x001e
        L_0x002b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.lists.Pair.toArray():java.lang.Object[]");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: gnu.lists.Sequence} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object[] toArray(java.lang.Object[] r7) {
        /*
            r6 = this;
            int r0 = r7.length
            int r1 = r6.length()
            if (r1 <= r0) goto L_0x000a
            java.lang.Object[] r7 = new java.lang.Object[r1]
            r0 = r1
        L_0x000a:
            r2 = 0
            r3 = r6
        L_0x000c:
            if (r2 >= r1) goto L_0x0021
            boolean r4 = r3 instanceof gnu.lists.Pair
            if (r4 == 0) goto L_0x0021
            r4 = r3
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4
            java.lang.Object r5 = r4.car
            r7[r2] = r5
            java.lang.Object r5 = r4.cdr
            r3 = r5
            gnu.lists.Sequence r3 = (gnu.lists.Sequence) r3
            int r2 = r2 + 1
            goto L_0x000c
        L_0x0021:
            r4 = r2
        L_0x0022:
            if (r2 >= r1) goto L_0x002f
            int r5 = r2 - r4
            java.lang.Object r5 = r3.get(r5)
            r7[r2] = r5
            int r2 = r2 + 1
            goto L_0x0022
        L_0x002f:
            if (r1 >= r0) goto L_0x0034
            r5 = 0
            r7[r1] = r5
        L_0x0034:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.lists.Pair.toArray(java.lang.Object[]):java.lang.Object[]");
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.car);
        out.writeObject(this.cdr);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.car = in.readObject();
        this.cdr = in.readObject();
    }

    public Object readResolve() throws ObjectStreamException {
        return this;
    }
}
