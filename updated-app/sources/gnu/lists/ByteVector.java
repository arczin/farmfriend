package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public abstract class ByteVector extends SimpleVector implements Externalizable, Comparable {
    protected static byte[] empty = new byte[0];
    byte[] data;

    public int getBufferLength() {
        return this.data.length;
    }

    public void setBufferLength(int length) {
        int oldLength = this.data.length;
        if (oldLength != length) {
            byte[] tmp = new byte[length];
            System.arraycopy(this.data, 0, tmp, 0, oldLength < length ? oldLength : length);
            this.data = tmp;
        }
    }

    /* access modifiers changed from: protected */
    public Object getBuffer() {
        return this.data;
    }

    public final byte byteAt(int index) {
        if (index <= this.size) {
            return this.data[index];
        }
        throw new IndexOutOfBoundsException();
    }

    public final byte byteAtBuffer(int index) {
        return this.data[index];
    }

    public boolean consumeNext(int ipos, Consumer out) {
        int index = ipos >>> 1;
        if (index >= this.size) {
            return false;
        }
        out.writeInt(intAtBuffer(index));
        return true;
    }

    public void consumePosRange(int iposStart, int iposEnd, Consumer out) {
        if (!out.ignoring()) {
            int end = iposEnd >>> 1;
            if (end > this.size) {
                end = this.size;
            }
            for (int i = iposStart >>> 1; i < end; i++) {
                out.writeInt(intAtBuffer(i));
            }
        }
    }

    public final void setByteAt(int index, byte value) {
        if (index <= this.size) {
            this.data[index] = value;
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    public final void setByteAtBuffer(int index, byte value) {
        this.data[index] = value;
    }

    /* access modifiers changed from: protected */
    public void clearBuffer(int start, int count) {
        while (true) {
            count--;
            if (count >= 0) {
                this.data[start] = 0;
                start++;
            } else {
                return;
            }
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        int size = this.size;
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeByte(this.data[i]);
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        byte[] data2 = new byte[size];
        for (int i = 0; i < size; i++) {
            data2[i] = in.readByte();
        }
        this.data = data2;
        this.size = size;
    }
}
