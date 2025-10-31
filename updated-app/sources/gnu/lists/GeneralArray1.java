package gnu.lists;

public class GeneralArray1 extends GeneralArray implements Sequence {
    public int createPos(int index, boolean isAfter) {
        return (index << 1) | isAfter ? 1 : 0;
    }

    /* access modifiers changed from: protected */
    public int nextIndex(int ipos) {
        return ipos == -1 ? size() : ipos >>> 1;
    }

    public void consumePosRange(int iposStart, int iposEnd, Consumer out) {
        if (!out.ignoring()) {
            int it = iposStart;
            while (!equals(it, iposEnd)) {
                if (hasNext(it)) {
                    this.base.consume(this.offset + (this.strides[0] * (it >>> 1)), 1, out);
                    it = nextPos(it);
                } else {
                    throw new RuntimeException();
                }
            }
        }
    }
}
